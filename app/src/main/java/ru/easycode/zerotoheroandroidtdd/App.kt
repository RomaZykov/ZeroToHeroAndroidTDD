package ru.easycode.zerotoheroandroidtdd

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class App : Application() {

    val viewModel: MainViewModel = MainViewModel(listLiveDataWrapper = ListLiveDataWrapper.Base())
}