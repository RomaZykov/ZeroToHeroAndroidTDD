package ru.easycode.zerotoheroandroidtdd

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val COUNTER = stringPreferencesKey("counter")
    val IS_INC_ENABLED = booleanPreferencesKey("is_inc_enabled")
    val IS_DEC_ENABLED = booleanPreferencesKey("is_dec_enabled")
}