package com.example.koreanlearningapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun GameHubScreen(navCtrl : NavController) {
    // Biến trạng thái: 0 là đang ở Sảnh chờ, 1 là vào phòng Quiz, 2 là vào phòng Đục lỗ
    var currentGame by remember { mutableIntStateOf(0) }

    when (currentGame) {
        // --- TRẠNG THÁI 0: HIỂN THỊ 2 NÚT CHỌN GAME ---
        0 -> {
            Column(
                modifier = Modifier.fillMaxSize().padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🎮 Trung Tâm Giải Trí",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(40.dp))

                // Nút vào game Quiz
                Button(
                    onClick = { currentGame = 1 },
                    modifier = Modifier.fillMaxWidth().height(65.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("⏳ Trắc Nghiệm Tốc Độ", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Nút vào game Đục lỗ (AI)
                Button(
                    onClick = { currentGame = 2 },
                    modifier = Modifier.fillMaxWidth().height(65.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("🧩 Điền Từ Đục Lỗ (AI)", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
                // Nút Kiểm tra tiếng
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = { navCtrl.navigate("recorder") },
                    modifier = Modifier.fillMaxWidth().height(65.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("🧩 Phát Âm", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // --- TRẠNG THÁI 1: GỌI MÀN HÌNH QUIZ CŨ ---
        // Khi bấm nút "Trở về Menu" ở QuizScreen, nó sẽ gán currentGame = 0 để quay lại Sảnh
        1 -> QuizScreen(onBackClick = { currentGame = 0 })

        // --- TRẠNG THÁI 2: GỌI MÀN HÌNH ĐỤC LỖ MỚI ---
        2 -> FillBlankScreen(onBackClick = { currentGame = 0 })
    }
}