package ru.easycode.zerotoheroandroidtdd

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val COUNTER = stringPreferencesKey("counter")
    val IS_ENABLED = booleanPreferencesKey("is_enabled")
}