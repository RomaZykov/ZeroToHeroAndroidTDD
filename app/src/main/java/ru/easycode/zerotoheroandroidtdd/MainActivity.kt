package ru.easycode.zerotoheroandroidtdd

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var title: TextView
    private lateinit var progress: ProgressBar
    private lateinit var actionButton: Button

    private var isStateSaved = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = (application as App).viewModel
        title = findViewById(R.id.titleTextView)
        progress = findViewById(R.id.progressBar)
        actionButton = findViewById(R.id.actionButton)

        actionButton.setOnClickListener {
            viewModel.load()
            isStateSaved = true
        }

        viewModel.liveData().observe(this) { innerUiState ->
            innerUiState.apply(title, progress, actionButton)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) { // Here You have to save count value
        super.onSaveInstanceState(outState)

        outState.putBoolean("isSaved", isStateSaved)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState.getBoolean("isSaved")) {
            title.visibility = View.VISIBLE
            progress.visibility = View.GONE
            actionButton.isEnabled = true
        }
    }
}