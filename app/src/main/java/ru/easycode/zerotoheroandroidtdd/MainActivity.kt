package ru.easycode.zerotoheroandroidtdd

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

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var title: TextView
    private lateinit var progress: ProgressBar
    private lateinit var actionButton: Button
    private val viewModel = MainViewModel(LiveDataWrapper.Base(), Repository.Base())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = findViewById(R.id.titleTextView)
        progress = findViewById(R.id.progressBar)
        actionButton = findViewById(R.id.actionButton)

        lifecycleScope.launch {
            dataStore.data
                .catch { exception ->
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }.map { preferences ->
                    val loadStateConsumed = preferences[PreferencesKeys.LOAD_STATE_CONSUMED] ?: false
                    MainPage(loadStateConsumed)
                }.first {
                    if (it.loadStateConsumed) {
                        UiState.ShowData.apply(title, progress, actionButton)
                    }
                    true
                }
        }

        actionButton.setOnClickListener {
            viewModel.load()
        }

        viewModel.liveData().observe(this) { innerUiState ->
            innerUiState.apply(title, progress, actionButton)
            lifecycleScope.launch {
                dataStore.edit { preferences ->
                    preferences[PreferencesKeys.LOAD_STATE_CONSUMED] = true
                }
            }
        }
    }
}