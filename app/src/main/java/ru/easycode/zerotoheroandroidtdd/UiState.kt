package ru.easycode.zerotoheroandroidtdd

import android.widget.Button
import android.widget.TextView

interface UiState {

    fun apply(textView: TextView, incButton: Button, decButton: Button)

    data class Base(private val text: String) : UiState {
        override fun apply(textView: TextView, incButton: Button, decButton: Button) {
            textView.text = text
            incButton.isEnabled = true
            decButton.isEnabled = true
        }
    }

    data class Max(private val text: String) : UiState {
        override fun apply(textView: TextView, incButton: Button, decButton: Button) {
            textView.text = text
            incButton.isEnabled = false
            decButton.isEnabled = true
        }
    }

    data class Min(private val text: String) : UiState {
        override fun apply(textView: TextView, incButton: Button, decButton: Button) {
            textView.text = text
            incButton.isEnabled = true
            decButton.isEnabled = false
        }
    }
}