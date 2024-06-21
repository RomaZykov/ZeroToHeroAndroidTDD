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
                    MainPage(counter)
                }.first {
                    counterTv.text = it.counter.toString()
                    true
                }
        }

        var counter = counterTv.text.toString().toInt()
        incrementButton = findViewById<Button>(R.id.incrementButton)
        incrementButton.setOnClickListener {
            counter += 2
            counterTv.text = Count.Base(2).increment(counterTv.text.toString())
            lifecycleScope.launch {
                dataStore.edit { preferences ->
                    preferences[PreferencesKeys.COUNTER] = counter
                }
            }
        }
    }
}

interface Count {

    fun increment(number: String): String
    class Base(private val step: Int) : Count {

        private var counter = 0 + step

        init {
            if (step < 1)
                throw IllegalStateException("step should be positive, but was $step")
        }

        override fun increment(number: String): String {
            counter += number.toInt()
            return counter.toString()
        }
    }
}