package com.example.koreanlearningapp.repository

import com.example.koreanlearningapp.models.LoginRequest
import com.example.koreanlearningapp.network.RetrofitClient

class AuthRepository {
    // Gọi anh bồi bàn Retrofit đi lấy dữ liệu
    suspend fun login(request: LoginRequest) = RetrofitClient.instance.loginUser(request)
    suspend fun register(request: LoginRequest) = RetrofitClient.instance.registerUser(request)
}