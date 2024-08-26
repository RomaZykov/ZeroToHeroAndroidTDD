package ru.easycode.zerotoheroandroidtdd.core

import androidx.lifecycle.ViewModel

interface ClearViewModel {

    fun clearViewModel(clazz: Class<out ViewModel>)
}