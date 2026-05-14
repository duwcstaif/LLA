package com.example.koreanlearningapp.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    // Đảm bảo BASE_URL là địa chỉ IP của máy ảo gọi xuống XAMPP nhé
    private const val BASE_URL = "http://10.0.2.2/korean_api/"

    // TẠO BỘ TĂNG THỜI GIAN CHỜ (TIMEOUT) CHO AI
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS) // Cho phép chờ kết nối 60 giây
        .readTimeout(60, TimeUnit.SECONDS)    // Cho phép chờ AI suy nghĩ 60 giây
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient) // Gắn bộ đếm thời gian mới vào đây
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}