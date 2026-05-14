package com.example.koreanlearningapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.koreanlearningapp.models.Vocabulary
import com.example.koreanlearningapp.network.RetrofitClient
import com.example.koreanlearningapp.network.ScoreRequest // Nhớ kiểm tra lại đường dẫn import này
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun QuizScreen(onBackClick: () -> Unit) {
    var vocabList by remember { mutableStateOf<List<Vocabulary>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // Quản lý trạng thái Game
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var options by remember { mutableStateOf<List<String>>(emptyList()) }
    var timeLeft by remember { mutableStateOf(10) }

    var isAnswered by remember { mutableStateOf(false) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var quizFinished by remember { mutableStateOf(false) }

    // Quản lý kết quả sau khi nộp điểm
    var finalLevel by remember { mutableStateOf<Int?>(null) }
    var finalTotalScore by remember { mutableStateOf<Int?>(null) }

    val coroutineScope = rememberCoroutineScope()
    val totalQuestions = 10 // Một ván chơi 10 câu

    // 1. Tải dữ liệu ban đầu (Xin 20 từ để vừa làm câu hỏi, vừa làm đáp án nhiễu)
    LaunchedEffect(Unit) {
        try {
            val response = RetrofitClient.instance.getRandomQuiz(limit = 20)
            if (response.isSuccessful && response.body() != null) {
                vocabList = response.body()!!
            }
        } catch (e: Exception) {
            println("Lỗi tải Quiz: ${e.message}")
        } finally {
            isLoading = false
        }
    }

    // 2. Logic tạo đáp án (Trộn 1 đúng + 3 sai) và đếm ngược thời gian
    LaunchedEffect(currentQuestionIndex, vocabList) {
        if (vocabList.isNotEmpty() && currentQuestionIndex < totalQuestions) {
            val correctWord = vocabList[currentQuestionIndex].vietnameseMeaning
            // Rút 3 đáp án sai từ kho từ vựng
            val wrongPool = vocabList.map { it.vietnameseMeaning }
                .filter { it != correctWord }
                .distinct()
                .shuffled()
                .take(3)

            options = (wrongPool + correctWord).shuffled()
            timeLeft = 10
            isAnswered = false
            selectedAnswer = null
        }
    }

    // 3. Đồng hồ đếm ngược
    LaunchedEffect(timeLeft, isAnswered, quizFinished) {
        if (!isAnswered && !quizFinished && vocabList.isNotEmpty() && timeLeft > 0) {
            delay(1000L)
            timeLeft--
            if (timeLeft == 0) {
                isAnswered = true // Hết giờ tự động khóa đáp án
            }
        }
    }

    // 4. Giao diện Game
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 100.dp))
        } else if (vocabList.isEmpty()) {
            Text("Không đủ từ vựng để tạo Quiz!", color = Color.Red, modifier = Modifier.padding(top = 100.dp))
        } else if (!quizFinished) {
            // ----- MÀN HÌNH ĐANG CHƠI -----
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Câu: ${currentQuestionIndex + 1}/$totalQuestions", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text("⏳ $timeLeft s", fontSize = 20.sp, color = if (timeLeft <= 3) Color.Red else Color.Black, fontWeight = FontWeight.Bold)
                Text("Điểm: $score", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Thẻ hiện từ Tiếng Hàn
            Card(
                modifier = Modifier.fillMaxWidth().height(150.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = vocabList[currentQuestionIndex].koreanWord,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Lưới 4 đáp án
            options.forEach { option ->
                val isCorrect = option == vocabList[currentQuestionIndex].vietnameseMeaning

                // Logic đổi màu nút khi đã chọn
                val buttonColor = if (isAnswered) {
                    when {
                        isCorrect -> Color(0xFF4CAF50) // Xanh lá nếu đúng
                        option == selectedAnswer -> Color(0xFFF44336) // Đỏ nếu chọn sai
                        else -> Color.LightGray // Làm mờ các nút còn lại
                    }
                } else MaterialTheme.colorScheme.secondary

                Button(
                    onClick = {
                        if (!isAnswered) {
                            selectedAnswer = option
                            isAnswered = true
                            if (isCorrect) score += 10 // Cộng 10 điểm nếu đúng

                            // Tự động chuyển câu sau 1.5 giây
                            coroutineScope.launch {
                                delay(1500L)
                                if (currentQuestionIndex < totalQuestions - 1) {
                                    currentQuestionIndex++
                                } else {
                                    quizFinished = true
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).height(55.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = option, fontSize = 18.sp, color = Color.White)
                }
            }
        } else {
            // ----- MÀN HÌNH KẾT QUẢ VÀ NỘP ĐIỂM -----
            LaunchedEffect(Unit) {
                try {
                    // Gửi API cập nhật điểm cho User ID = 1
                    val res = RetrofitClient.instance.updateScore(ScoreRequest(user_id = 1, points = score))
                    if (res.isSuccessful && res.body()?.success == true) {
                        finalTotalScore = res.body()?.new_score
                        finalLevel = res.body()?.new_level
                    }
                } catch (e: Exception) {
                    println("Lỗi nộp điểm: ${e.message}")
                }
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("🎉 HOÀN THÀNH TỐT LẮM! 🎉", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFF9800))
                Spacer(modifier = Modifier.height(16.dp))
                Text("Điểm ván này: $score", fontSize = 20.sp)
                Spacer(modifier = Modifier.height(8.dp))

                if (finalLevel != null) {
                    Text("Cấp độ của bạn: Lvl $finalLevel 🏆", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Text("Tổng điểm tích lũy: $finalTotalScore", fontSize = 16.sp, color = Color.Gray)
                } else {
                    CircularProgressIndicator() // Đang chờ server trả kết quả điểm về
                }

                Spacer(modifier = Modifier.height(40.dp))
                Button(onClick = onBackClick, modifier = Modifier.fillMaxWidth(0.6f)) {
                    Text("Trở về Menu")
                }
            }
        }
    }
}