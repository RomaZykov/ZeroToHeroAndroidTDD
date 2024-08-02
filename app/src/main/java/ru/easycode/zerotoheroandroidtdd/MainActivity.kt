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

        counterTv = findViewById(R.id.countTextView)
        decrementButton = findViewById(R.id.decrementButton)
        incrementButton = findViewById(R.id.incrementButton)

        val state = Count.Base(2, 4, 0)
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
                        preferences[PreferencesKeys.COUNTER] ?: counterTv.text.toString()
                    val isIncEnabled =
                        preferences[PreferencesKeys.IS_INC_ENABLED] ?: incrementButton.isEnabled
                    val isDecEnabled =
                        preferences[PreferencesKeys.IS_DEC_ENABLED] ?: decrementButton.isEnabled
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
            saveToDataStore()
        }

        decrementButton.setOnClickListener {
            state.decrement(counterTv.text.toString())
                .apply(counterTv, incrementButton, decrementButton)
            saveToDataStore()
        }
    }

    private fun saveToDataStore() {
        lifecycleScope.launch {
            dataStore.edit { preferences ->
                preferences[PreferencesKeys.COUNTER] = counterTv.text.toString()
                preferences[PreferencesKeys.IS_INC_ENABLED] = incrementButton.isEnabled
                preferences[PreferencesKeys.IS_DEC_ENABLED] = decrementButton.isEnabled
            }
        }
    }
}
