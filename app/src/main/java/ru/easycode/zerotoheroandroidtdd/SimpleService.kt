package ru.easycode.zerotoheroandroidtdd

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface SimpleService {
    @GET
    suspend fun fetch(@Url url: String): SimpleResponse
}
