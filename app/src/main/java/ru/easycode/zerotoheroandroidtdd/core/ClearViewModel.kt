package ru.easycode.zerotoheroandroidtdd.core

import androidx.lifecycle.ViewModel
import ru.easycode.zerotoheroandroidtdd.data.Order

interface ClearViewModel {

    fun clearViewModel(clazz: Class<out ViewModel>)

    class Base(private val order: Order) : ClearViewModel {

        override fun clearViewModel(clazz: Class<out ViewModel>) {
        }
    }
}