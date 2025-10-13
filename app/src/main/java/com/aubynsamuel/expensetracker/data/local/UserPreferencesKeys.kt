package com.aubynsamuel.expensetracker.data.local

import androidx.datastore.preferences.core.booleanPreferencesKey

object UserPreferencesKeys {
    val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
}