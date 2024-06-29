package ru.easycode.zerotoheroandroidtdd

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var counterTv: TextView
    private lateinit var incrementButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        counterTv = findViewById<TextView>(R.id.countTextView)
        incrementButton = findViewById<Button>(R.id.incrementButton)
        lifecycleScope.launch {
            dataStore.data
                .catch { exception ->
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }.map { preferences ->
                    val counterTv =
                        preferences[PreferencesKeys.COUNTER] ?: "0"
                    val isEnabled =
                        preferences[PreferencesKeys.IS_ENABLED] ?: true
                    MainPage(counterTv, isEnabled)
                }.first {
                    counterTv.text = it.text
                    incrementButton.isEnabled = it.isEnabled
                    true
                }
        }

        incrementButton.setOnClickListener {
            Count.Base(2, 4).increment(counterTv.text.toString()).apply(counterTv, incrementButton)
            lifecycleScope.launch {
                dataStore.edit { preferences ->
                    preferences[PreferencesKeys.COUNTER] = counterTv.text.toString()
                    preferences[PreferencesKeys.IS_ENABLED] = incrementButton.isEnabled
                }
            }
        }
    }
}

interface Count {

    fun increment(number: String): UiState

    class Base(private val step: Int, private val max: Int) : Count {

        private var counter = step

        init {
            if (step < 1)
                throw IllegalStateException("step should be positive, but was $step")
            if (max < 0)
                throw IllegalStateException("max should be positive, but was $max")
            if (max == 0)
                throw IllegalStateException()
            if (step > max)
                throw IllegalStateException("max should be more than step")
        }

        override fun increment(number: String): UiState {
            counter += number.toInt()
            if (counter >= max || counter + step > max) {
                val currentMax = if (counter + step > max) counter else counter - step
                return UiState.Max(currentMax.toString())
            }
            return UiState.Base(counter.toString())
        }
    }
}

interface UiState {

    fun apply(textView: TextView, button: Button)

    data class Base(private val text: String) : UiState {
        override fun apply(textView: TextView, button: Button) {
            textView.text = text
        }
    }

    data class Max(private val text: String) : UiState {
        override fun apply(textView: TextView, button: Button) {
            textView.text = text
            button.isEnabled = false
        }
    }
}