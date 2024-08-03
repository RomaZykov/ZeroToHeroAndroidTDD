package ru.easycode.zerotoheroandroidtdd

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

class MainActivity : AppCompatActivity() {

    private lateinit var uiState: UiState

    private lateinit var textView: TextView
    private lateinit var progress: ProgressBar
    private lateinit var actionButton: Button
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.titleTextView)
        progress = findViewById(R.id.progressBar)
        actionButton = findViewById(R.id.actionButton)

        val repository = Repository.Base()
        val liveDataWrapper = LiveDataWrapper.Base(textView, progress, actionButton)
        viewModel = MainViewModel(liveDataWrapper, repository)

        actionButton.setOnClickListener {
            viewModel.load()
        }
    }
}
