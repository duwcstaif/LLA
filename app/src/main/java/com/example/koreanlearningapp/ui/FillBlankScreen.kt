package com.example.koreanlearningapp.ui

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
import com.example.koreanlearningapp.network.FillBlankQuestion
import com.example.koreanlearningapp.network.RetrofitClient
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun FillBlankScreen(onBackClick: () -> Unit) {
    var quizList by remember { mutableStateOf<List<FillBlankQuestion>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var currentQuestionIndex by remember { mutableStateOf(0) }

    var isAnswered by remember { mutableStateOf(false) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var score by remember { mutableStateOf(0) }
    var quizFinished by remember { mutableStateOf(false) }
    val context = androidx.compose.ui.platform.LocalContext.current

    LaunchedEffect(Unit) {
        try {
            val response = RetrofitClient.instance.getFillBlankQuiz()
            if (response.isSuccessful && response.body() != null) {
                quizList = response.body()!!
            }
        } catch (e: Exception) {
            println("Lỗi tải Đục lỗ: ${e.message}")
        } finally {
            isLoading = false
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp) .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            Spacer(modifier = Modifier.height(100.dp))
            Text("AI đang sáng tác đề thi, chờ xíu nha...", fontSize = 16.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        } else if (quizList.isEmpty()) {
            Text("Không tải được câu hỏi!", color = Color.Red, modifier = Modifier.padding(top = 100.dp))
        } else if (!quizFinished) {
            val currentQ = quizList[currentQuestionIndex]

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Câu: ${currentQuestionIndex + 1}/${quizList.size}", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("Điểm: $score", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // KHUNG CÂU HỎI & DỊCH NGHĨA
            Card(
                modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val displaySentence = if (isAnswered && selectedAnswer == currentQ.correctAnswer) {
                        currentQ.sentence.replace("___", "[ ${currentQ.correctAnswer} ]")
                    } else {
                        currentQ.sentence
                    }

                    Text(text = displaySentence, fontSize = 22.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)

                    // BÍ MẬT: CHỈ HIỆN NGHĨA SAU KHI ĐÃ CHỌN ĐÁP ÁN
                    if (isAnswered) {
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "💡 Nghĩa: ${currentQ.vietnameseTranslation}",
                            fontSize = 16.sp,
                            color = Color(0xFFE65100), // Màu cam nổi bật
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 4 NÚT ĐÁP ÁN
            currentQ.options.forEach { option ->
                val isCorrect = option == currentQ.correctAnswer
                val buttonColor = if (isAnswered) {
                    when {
                        isCorrect -> Color(0xFF4CAF50) // Xanh lá cây cho đáp án ĐÚNG
                        option == selectedAnswer -> Color(0xFFF44336) // Đỏ cho đáp án bị chọn SAI
                        else -> Color.LightGray // Các nút còn lại bị mờ đi
                    }
                } else MaterialTheme.colorScheme.secondary

                Button(
                    onClick = {
                        if (!isAnswered) {
                            selectedAnswer = option
                            isAnswered = true
                            if (isCorrect) {
                                score += 10
                                // GHI VÀO SỔ LỊCH SỬ NGAY TẠI ĐÂY NÈ
                                com.example.koreanlearningapp.network.HistoryManager.saveWord(
                                    context = context,
                                    word = currentQ.correctAnswer,
                                    sentence = currentQ.vietnameseTranslation
                                )
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp).height(55.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                    shape = RoundedCornerShape(12.dp)
                ){
                    Text(text = option, fontSize = 18.sp, color = Color.White)
                }
            }

            // NÚT "CÂU TIẾP THEO" - CHỈ XUẤT HIỆN SAU KHI ĐÃ TRẢ LỜI
            if (isAnswered) {
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        if (currentQuestionIndex < quizList.size - 1) {
                            currentQuestionIndex++
                            isAnswered = false
                            selectedAnswer = null
                        } else {
                            quizFinished = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.fillMaxWidth().height(60.dp)
                ) {
                    Text(if (currentQuestionIndex < quizList.size - 1) "Câu tiếp theo ➡️" else "Xem kết quả 🎉", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }

        } else {
            Text("🎉 HOÀN THÀNH!", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))
            Text("Điểm của bạn: $score", fontSize = 22.sp, color = MaterialTheme.colorScheme.primary)
            Button(onClick = onBackClick, modifier = Modifier.padding(top = 30.dp).height(50.dp)) {
                Text("Trở về Sảnh Game", fontSize = 16.sp)
            }
        }
    }
}