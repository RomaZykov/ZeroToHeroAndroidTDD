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
    private lateinit var decrementButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        counterTv = findViewById<TextView>(R.id.countTextView)
        incrementButton = findViewById<Button>(R.id.incrementButton)
        decrementButton = findViewById<Button>(R.id.decrementButton)
        val state = Count.Base(2, 4, 2)
        state.initial(counterTv.text.toString()).apply(counterTv, incrementButton, decrementButton)
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
                    val isIncEnabled =
                        preferences[PreferencesKeys.IS_INC_ENABLED] ?: true
                    val isDecEnabled =
                        preferences[PreferencesKeys.IS_DEC_ENABLED] ?: true
                    MainPage(counterTv, isIncEnabled, isDecEnabled)
                }.first {
                    counterTv.text = it.text
                    incrementButton.isEnabled = it.isIncrementEnabled
                    decrementButton.isEnabled = it.isDecrementEnabled
                    true
                }
        }

        incrementButton.setOnClickListener {
            state.increment(counterTv.text.toString())
                .apply(counterTv, incrementButton, decrementButton)
            lifecycleScope.launch {
                dataStore.edit { preferences ->
                    preferences[PreferencesKeys.COUNTER] = counterTv.text.toString()
                    preferences[PreferencesKeys.IS_INC_ENABLED] = incrementButton.isEnabled
                    preferences[PreferencesKeys.IS_DEC_ENABLED] = decrementButton.isEnabled
                }
            }
        }

        decrementButton.setOnClickListener {
            state.decrement(counterTv.text.toString())
                .apply(counterTv, incrementButton, decrementButton)
            lifecycleScope.launch {
                dataStore.edit { preferences ->
                    preferences[PreferencesKeys.COUNTER] = counterTv.text.toString()
                    preferences[PreferencesKeys.IS_INC_ENABLED] = incrementButton.isEnabled
                    preferences[PreferencesKeys.IS_DEC_ENABLED] = decrementButton.isEnabled
                }
            }
        }
    }
}

interface Count {

    fun initial(number: String): UiState

    fun increment(number: String): UiState

    fun decrement(number: String): UiState

    class Base(private val step: Int, private val max: Int, private val min: Int) : Count {

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

        override fun initial(number: String): UiState {
            return if (number <= min.toString()) {
                UiState.Min(min.toString())
            } else if (number >= max.toString()) {
                UiState.Max(number)
            } else {
                UiState.Base(number)
            }
        }

        override fun decrement(number: String): UiState {
            counter -= number.toInt()
            if (counter < min || counter - step < min) {
                val currentMin = if (counter - step < min) min else counter
                return UiState.Min(currentMin.toString())
            }
            return UiState.Base(counter.toString())
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

    fun apply(textView: TextView, incButton: Button, decButton: Button)

    data class Base(private val text: String) : UiState {
        override fun apply(textView: TextView, incButton: Button, decButton: Button) {
            textView.text = text
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