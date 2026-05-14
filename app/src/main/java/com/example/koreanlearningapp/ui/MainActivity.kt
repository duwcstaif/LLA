package com.example.koreanlearningapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect // THÊM DÒNG NÀY NỮA NHÉ
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.koreanlearningapp.ui.theme.KoreanLearningAppTheme
import com.example.koreanlearningapp.viewmodel.AuthViewModel
import com.example.koreanlearningapp.viewmodel.HomeViewModel

class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoreanLearningAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "login") {

                        // Màn hình Đăng nhập
                        composable("login") {
                            LoginScreen(
                                viewModel = authViewModel,
                                onLoginSuccess = {
                                    // SỬA Ở ĐÂY: Đăng nhập xong nhảy vào màn 'main' (có menu)
                                    navController.navigate("main") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                },
                                onNavigateToRegister = {
                                    navController.navigate("register")
                                }
                            )
                        }

                        // Màn hình Đăng ký
                        composable("register") {
                            RegisterScreen(
                                viewModel = authViewModel,
                                onRegisterSuccess = {
                                    navController.navigate("login") {
                                        popUpTo("register") { inclusive = true }
                                    }
                                },
                                onBackToLogin = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        // THÊM MỚI: Màn hình chính chứa thanh Menu Bottom
                        composable("main") {
                            MainScreen(
                                onNavigateToLessons = { clickedTopicId ->
                                    // Bấm vào chủ đề ở tab Home -> Chuyển sang danh sách bài học
                                    navController.navigate("home/$clickedTopicId")
                                },
                                onLogout = {
                                    // Bấm đăng xuất -> Xóa sạch lịch sử, văng ra login
                                    navController.navigate("login") {
                                        popUpTo(0) { inclusive = true }
                                    }
                                }
                            )
                        }

                        // SỬA LẠI: Màn hình Home (Danh sách bài học) giờ phải kẹp thêm ID chủ đề
                        composable("home/{topicId}") { backStackEntry ->
                            val idString = backStackEntry.arguments?.getString("topicId")
                            val topicId = idString?.toIntOrNull() ?: 1

                            LaunchedEffect(topicId) {
                                homeViewModel.loadLessonsByTopic(topicId)
                            }

                            HomeScreen(
                                viewModel = homeViewModel,
                                onLessonClick = { clickedLessonId, lessonNumber ->
                                    // Đã sửa: Trạm trung tâm nhận 2 thông số và ra lệnh chuyển trang
                                    navController.navigate("detail/$clickedLessonId/$lessonNumber")
                                },
                                onBackClick = { navController.popBackStack() }
                            )
                        }

                        // Đã sửa: Khai báo đường ray nhận cả 2 biến
                        composable("detail/{lessonId}/{lessonNumber}") { backStackEntry ->
                            val lessonId = backStackEntry.arguments?.getString("lessonId")?.toIntOrNull() ?: 1
                            val lessonNumber = backStackEntry.arguments?.getString("lessonNumber")?.toIntOrNull() ?: 1

                            DetailScreen(
                                lessonId = lessonId,
                                lessonNumber = lessonNumber,
                                onBackClick = { navController.popBackStack() }
                            )
                        }

                        composable("quiz") {
                            QuizScreen(
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}