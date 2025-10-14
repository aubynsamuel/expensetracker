package com.aubynsamuel.expensetracker.data.repository

import com.aubynsamuel.expensetracker.data.local.SharedPreferencesManager
import com.aubynsamuel.expensetracker.data.model.SettingsState
import kotlinx.coroutines.flow.StateFlow

class SettingsRepository(private val sharedPreferencesManager: SharedPreferencesManager) {
    val settingsState: StateFlow<SettingsState> = sharedPreferencesManager.settingsState

    fun saveSettings(settingsState: SettingsState) {
        sharedPreferencesManager.saveSettings(settingsState)
    }
}