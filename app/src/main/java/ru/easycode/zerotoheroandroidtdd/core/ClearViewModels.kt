package ru.easycode.zerotoheroandroidtdd.core

import androidx.lifecycle.ViewModel

interface ClearViewModels {

    fun clear(vararg viewModelClasses: Class<out ViewModel>)
}
