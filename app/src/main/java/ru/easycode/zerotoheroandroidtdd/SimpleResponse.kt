package ru.easycode.zerotoheroandroidtdd

import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

data class SimpleResponse(
    @SerializedName("text")
    val text: String
)
