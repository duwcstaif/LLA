package com.example.koreanlearningapp.models

import com.google.gson.annotations.SerializedName

data class Topic(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("image_url") val imageUrl: String?
)