package com.aubynsamuel.expensetracker.data.repository

import com.aubynsamuel.expensetracker.data.local.SharedPreferencesManager
import kotlinx.coroutines.flow.StateFlow

class SettingsRepository(val sharedPreferencesManager: SharedPreferencesManager) {
    val isDarkMode: StateFlow<Boolean> = sharedPreferencesManager.isDarkMode

    fun toggleDarkMode() {
        sharedPreferencesManager.toggleDarkMode()
    }
}