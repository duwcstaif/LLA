package com.example.koreanlearningapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun MainScreen(
    onNavigateToLessons: (Int) -> Unit,
    onLogout: () -> Unit,
    navCtrl : NavController
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Học tập") },
                    label = { Text("Học tập") },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Star, contentDescription = "Giải trí") },
                    label = { Text("Giải trí") },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Tài khoản") },
                    label = { Text("Tài khoản") },
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {
                0 -> TopicScreen(onTopicClick = onNavigateToLessons)

                // --- ĐÃ THAY ĐỔI Ở ĐÂY ---
                // Gọi thẳng GameHubScreen thay vì QuizScreen
                1 -> GameHubScreen(navCtrl)
                // -------------------------

                2 -> ProfileScreen(onLogout = onLogout, navController = navCtrl)
            }
        }
    }
}

@Composable
fun ProfileScreen(onLogout: () -> Unit, navController: NavController) { // Thêm navController vào tham số
    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Thay "Học viên" bằng tên cho xịn
            Text("Xin chào học viên!", style = MaterialTheme.typography.headlineMedium, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)

            Spacer(modifier = Modifier.height(30.dp))

            // --- NÚT XEM LỊCH SỬ GHÉP VÀO ĐÂY ---
            Button(
                onClick = {
                    navController.navigate("history") // Lệnh mở màn hình lịch sử
                },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "📖", fontSize = 24.sp, modifier = Modifier.padding(end = 8.dp))
                    Text(text = "Xem lịch sử từ đã học", fontSize = 18.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                }
            }
            // -----------------------------------

            Spacer(modifier = Modifier.height(16.dp))

            // Nút đăng xuất cũ
            Button(
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
            ) {
                Text("Đăng xuất", fontSize = 16.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
        }
    }
}