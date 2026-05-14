package com.example.koreanlearningapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koreanlearningapp.models.Lesson
import com.example.koreanlearningapp.network.RetrofitClient
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    val lessonList = MutableLiveData<List<Lesson>>()
    val errorMessage = MutableLiveData<String>()

    // Hàm lấy danh sách bài học theo ID Chủ đề
    fun loadLessonsByTopic(topicId: Int) {
        viewModelScope.launch {
            try {
                // Truyền topicId vào hàm getLessons
                val response = RetrofitClient.instance.getLessons(topicId)

                if (response.isSuccessful && response.body() != null) {
                    lessonList.postValue(response.body()!!)
                } else {
                    errorMessage.postValue("Lỗi Server: ${response.code()}")
                }
            } catch (e: Exception) {
                errorMessage.postValue("Lỗi kết nối: ${e.message}")
            }
        }
    }
}