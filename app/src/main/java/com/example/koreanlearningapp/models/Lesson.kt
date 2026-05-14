package com.example.koreanlearningapp.models

import com.google.gson.annotations.SerializedName

data class Lesson(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("level") val level: String
)