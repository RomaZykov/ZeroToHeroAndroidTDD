package ru.easycode.zerotoheroandroidtdd

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val ENABLE = booleanPreferencesKey("enable")
    val COUNTER = intPreferencesKey("counter")
}