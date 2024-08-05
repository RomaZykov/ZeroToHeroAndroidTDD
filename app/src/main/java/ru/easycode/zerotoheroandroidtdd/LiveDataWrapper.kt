package ru.easycode.zerotoheroandroidtdd

import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface LiveDataWrapper {

    fun update(value: UiState)

    fun liveData(): LiveData<UiState>

    class Base : LiveDataWrapper {
        private val _uiState = MutableLiveData<UiState>()

        override fun update(value: UiState) {
            _uiState.value = value
        }

        override fun liveData(): LiveData<UiState> {
            return _uiState
        }
    }
}