package ru.easycode.zerotoheroandroidtdd

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val CURRENT_TITLE = stringPreferencesKey("current_title")
    val TITLE_VISIBILITY = booleanPreferencesKey("title_visibility")
}