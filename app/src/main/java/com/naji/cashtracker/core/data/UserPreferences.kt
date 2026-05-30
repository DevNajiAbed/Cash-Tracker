package com.naji.cashtracker.core.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "cash_tracker_prefs")

class UserPreferences(
    private val context: Context
) {
    private val firstLaunchKey = booleanPreferencesKey("is_first_launch")
    private val darkModeKey = booleanPreferencesKey("is_dark_mode")
    private val currencyKey = stringPreferencesKey("currency")

    val isFirstLaunch: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[firstLaunchKey] ?: true
    }

    val isDarkMode: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[darkModeKey] ?: false
    }

    val currencyCode: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[currencyKey] ?: "USD"
    }

    suspend fun setFirstLaunchComplete() {
        context.dataStore.edit { prefs ->
            prefs[firstLaunchKey] = false
        }
    }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[darkModeKey] = enabled
        }
    }

    suspend fun setCurrencyCode(code: String) {
        context.dataStore.edit { prefs ->
            prefs[currencyKey] = code
        }
    }
}
