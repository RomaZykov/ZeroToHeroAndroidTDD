package ru.easycode.zerotoheroandroidtdd

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import java.io.Serializable

interface UiState : Serializable {

    fun apply(textView: TextView, progressBar: ProgressBar, actionButton: Button)

    object ShowProgress : UiState {
        override fun apply(textView: TextView, progressBar: ProgressBar, actionButton: Button) {
            textView.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            actionButton.isEnabled = false
        }
    }

    object ShowData : UiState {
        override fun apply(textView: TextView, progressBar: ProgressBar, actionButton: Button) {
            textView.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            actionButton.visibility = View.VISIBLE
            actionButton.isEnabled = true
        }
    }
}