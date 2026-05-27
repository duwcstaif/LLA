package com.example.koreanlearningapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.koreanlearningapp.network.HistoryManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current

    // Lấy dữ liệu từ SharedPreferences lên
    var wordsList by remember { mutableStateOf(HistoryManager.getLearnedWords(context)) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Từ Vựng Đã Học", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                actions = {
                    // Nút xóa lịch sử (Tùy chọn)
                    TextButton(onClick = {
                        HistoryManager.clearHistory(context)
                        wordsList = emptyList()
                    }) {
                        Text("Xóa hết", color = Color.Red)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (wordsList.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Bạn chưa học từ nào cả. Vào chơi game đi!", color = Color.Gray, fontSize = 16.sp)
                }
            } else {
                Text("Tổng cộng: ${wordsList.size} từ", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(10.dp))

                // Dùng LazyColumn để cuộn danh sách siêu mượt
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(wordsList) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = item.word, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E88E5))
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = "Ngữ cảnh: ${item.contextSentence}", fontSize = 14.sp, color = Color.DarkGray)
                            }
                        }
                    }
                }
            }
        }
    }
}