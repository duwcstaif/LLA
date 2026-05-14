package com.example.koreanlearningapp.models

import com.google.gson.annotations.SerializedName

data class Vocabulary(
    @SerializedName("id") val id: Int,
    @SerializedName("lesson_id") val lessonId: Int,
    @SerializedName("korean_word") val koreanWord: String,
    @SerializedName("vietnamese_meaning") val vietnameseMeaning: String,
    @SerializedName("pronunciation") val pronunciation: String?
)