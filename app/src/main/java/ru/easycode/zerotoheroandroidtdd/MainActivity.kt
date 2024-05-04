package ru.easycode.zerotoheroandroidtdd

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val title = findViewById<TextView>(R.id.titleTextView)
        lifecycleScope.launch {
            val data = dataStore.data
                .catch { exception ->
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }.map { preferences ->
                    val currentTitle = preferences[PreferencesKeys.CURRENT_TITLE] ?: "Hello World!"
                    MainPage(currentTitle)
                }.first {
                    title.text = it.savedTitle
                    true
                }
        }

        val button = findViewById<Button>(R.id.changeButton)
        button.setOnClickListener {
            title.text = "I am an Android Developer!"
            lifecycleScope.launch {
                dataStore.edit { preferences ->
                    preferences[PreferencesKeys.CURRENT_TITLE] = title.text.toString()
                }
            }
        }
    }
}
