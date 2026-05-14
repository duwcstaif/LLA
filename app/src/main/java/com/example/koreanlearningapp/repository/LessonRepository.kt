package com.example.koreanlearningapp.repository

import com.example.koreanlearningapp.network.RetrofitClient

class LessonRepository {
    // Cập nhật: Anh bồi bàn giờ phải cầm theo cái topicId (mã chủ đề) để lấy đúng bài học
    suspend fun getLessonsByTopic(topicId: Int) = RetrofitClient.instance.getLessons(topicId)
}