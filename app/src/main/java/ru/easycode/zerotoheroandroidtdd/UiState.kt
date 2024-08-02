package ru.easycode.zerotoheroandroidtdd

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView

interface UiState {

    fun apply(textView: TextView, progress: ProgressBar, loadButton: Button)

    data class Base(private val text: String) : UiState {
        override fun apply(textView: TextView, progress: ProgressBar, loadButton: Button) {
            textView.visibility = View.GONE
            progress.visibility = View.GONE
            loadButton.isEnabled = true
        }
    }

    data class Load(private val text: String) : UiState {
        override fun apply(textView: TextView, progress: ProgressBar, loadButton: Button) {
            textView.visibility = View.GONE
            progress.visibility = View.VISIBLE
            loadButton.isEnabled = false
        }
    }

    data class Result(private val text: String) : UiState {
        override fun apply(textView: TextView, progress: ProgressBar, loadButton: Button) {
            textView.text = text
            textView.visibility = View.VISIBLE
            progress.visibility = View.GONE
            loadButton.isEnabled = true
        }
    }
}