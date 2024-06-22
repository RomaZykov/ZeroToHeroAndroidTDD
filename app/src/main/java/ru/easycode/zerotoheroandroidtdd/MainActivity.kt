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
        lifecycleScope.launch {
            dataStore.data
                .catch { exception ->
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }.map { preferences ->
                    val counter = preferences[PreferencesKeys.COUNTER] ?: 0
                    val isEnable = preferences[PreferencesKeys.ENABLE] ?: true
                    MainPage(counter, isEnable)
                }.first {
                    counterTv.text = it.counter.toString()
                    incrementButton.isEnabled = it.isEnable
                    true
                }
        }

        incrementButton = findViewById<Button>(R.id.incrementButton)
        incrementButton.setOnClickListener {
            counterTv.text = Count.Base(2).increment(counterTv.text.toString())
            incrementButton.isEnabled = Count.Base(2).isOverlap(counterTv.text.toString())
            lifecycleScope.launch {
                dataStore.edit { preferences ->
                    preferences[PreferencesKeys.COUNTER] = counterTv.text.toString().toInt()
                    preferences[PreferencesKeys.ENABLE] = counterTv.text.toString().toInt() < 5
                }
            }
        }
    }
}

interface Count {

    fun increment(number: String): String
    fun isOverlap(number: String): Boolean
    class Base(private val step: Int) : Count {

        private var counter = 0 + step

        init {
            if (step < 1)
                throw IllegalStateException("step should be positive, but was $step")
        }

        override fun isOverlap(number: String): Boolean {
            return counter < 4
        }

        override fun increment(number: String): String {
            counter += number.toInt()
            if (counter > 4) {
                return number
            }
            return counter.toString()
        }
    }
}