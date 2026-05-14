package com.example.koreanlearningapp.network

import com.example.koreanlearningapp.models.Lesson
import com.example.koreanlearningapp.models.LoginRequest
import com.example.koreanlearningapp.models.LoginResponse
import com.example.koreanlearningapp.models.Topic
import retrofit2.Response
import com.example.koreanlearningapp.models.Vocabulary
import retrofit2.http.Query
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiService {
    // API Đăng nhập (Chỉ để đúng tên file php thôi)
    @POST("login.php")
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginResponse>

    @POST("register.php")
    suspend fun registerUser(@Body request: LoginRequest): Response<LoginResponse>

    @GET("get_topics.php")
    suspend fun getTopics(): Response<List<Topic>>
    // API Lấy danh sách bài học
    @GET("get_lessons.php")
    suspend fun getLessons(@Query("topic_id") topicId: Int): Response<List<Lesson>>

    @GET("get_lesson_detail.php")
    suspend fun getLessonDetail(@Query("lesson_id") lessonId: Int): Response<List<Vocabulary>>


    @POST("update_score.php")
    suspend fun updateScore(@Body request: ScoreRequest): Response<UpdateScoreResponse>

    @GET("get_random_quiz.php")
    suspend fun getRandomQuiz(
        @Query("limit") limit: Int = 10 // Số lượng câu hỏi 1 ván (bạn có thể đổi thành 20, 30 tuỳ thích)
    ): Response<List<Vocabulary>>

    @GET("get_fill_blank_quiz.php")
    suspend fun getFillBlankQuiz(
        @Query("topic_id") topicId: Int = 1 // Có thể truyền chủ đề để AI biết đường ra đề
    ): Response<List<FillBlankQuestion>>
}

data class UpdateScoreResponse(
    val success: Boolean,
    val message: String,
    val new_score: Int?,
    val new_level: Int?
)

// Data class để gửi dữ liệu lên
data class ScoreRequest(
    val user_id: Int,
    val points: Int
)

data class FillBlankQuestion(
    val sentence: String,            // Ví dụ: "아침에 빵과 ___를 마십니다."
    val vietnameseTranslation: String, // Ví dụ: "(Buổi sáng tôi ăn bánh mì và uống ___)"
    val options: List<String>,       // Ví dụ: ["우유", "사과", "책", "물"]
    val correctAnswer: String        // Ví dụ: "우유"
)