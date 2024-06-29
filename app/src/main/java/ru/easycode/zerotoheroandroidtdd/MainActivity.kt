package ru.easycode.zerotoheroandroidtdd

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.contains
import androidx.core.view.isVisible
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val linearLayout = findViewById<LinearLayout>(R.id.rootLayout)

        val title = findViewById<TextView>(R.id.titleTextView)
        lifecycleScope.launch {
            dataStore.data
                .catch { exception ->
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }.map { preferences ->
                    val currentTitle = preferences[PreferencesKeys.CURRENT_TITLE] ?: "Hello World!"
                    val titleVisibility = preferences[PreferencesKeys.TITLE_VISIBILITY] ?: true
                    val titleExist =
                        preferences[PreferencesKeys.TITLE_EXIST] ?: linearLayout.contains(title)
                    MainPage(currentTitle, titleVisibility, titleExist)
                }.first {
                    title.text = it.savedTitle
                    title.visibility = if (it.titleVisibility) View.VISIBLE else View.GONE
                    if (!it.titleShouldExist) linearLayout.removeView(title)
                    true
                }
        }

        val removeButton = findViewById<Button>(R.id.removeButton)
        removeButton.setOnClickListener {
            linearLayout.removeView(title)
            lifecycleScope.launch {
                dataStore.edit { preferences ->
                    preferences[PreferencesKeys.TITLE_EXIST] = linearLayout.contains(title)
                }
            }
        }
    }
}
