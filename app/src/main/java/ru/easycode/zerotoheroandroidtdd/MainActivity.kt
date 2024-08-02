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

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.titleTextView)
        progress = findViewById(R.id.progressBar)
        actionButton = findViewById(R.id.actionButton)

        uiState = Load.Base(textView.text.toString()).initial()
        uiState.apply(textView, progress, actionButton)

        actionButton.setOnClickListener {
            lifecycleScope.launch {
                val result = withTimeout(3500) {
                    uiState = Load.Base(textView.text.toString()).load()
                    uiState.apply(textView, progress, actionButton)
                    delay(3000)
                    "completed"
                }
                if (result == "completed") {
                    uiState = Load.Base(textView.text.toString()).result()
                    uiState.apply(textView, progress, actionButton)
                }
            }
        }
    }
}
