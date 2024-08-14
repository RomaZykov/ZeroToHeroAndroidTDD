package ru.easycode.zerotoheroandroidtdd.core

import androidx.lifecycle.ViewModel

interface ClearViewModel {
    fun clear(viewModelClass: Class<out ViewModel>)

    class Base() : ClearViewModel {
        override fun clear(viewModelClass: Class<out ViewModel>) {
        }
    }
}
