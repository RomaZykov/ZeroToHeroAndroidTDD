package ru.easycode.zerotoheroandroidtdd

import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface LiveDataWrapper {

    fun update(value: UiState)

    fun liveData(): LiveData<UiState>

    class Base(
        private val textView: TextView,
        private val progressBar: ProgressBar,
        private val actionButton: Button
    ) : LiveDataWrapper {
        private val _uiState = MutableLiveData<UiState>()

        override fun update(value: UiState) {
            _uiState.value = value
            _uiState.value?.apply(textView, progressBar, actionButton)
        }

        override fun liveData(): LiveData<UiState> {
            return _uiState
        }
    }
}
