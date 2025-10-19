package com.aubynsamuel.expensetracker.data.repository

import com.aubynsamuel.expensetracker.data.local.SharedPreferencesManager
import com.aubynsamuel.expensetracker.data.model.SettingsState
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager,
) {

    fun getSettings(): SettingsState {
        return sharedPreferencesManager.getSettings()
    }

    fun saveSettings(settingsState: SettingsState) {
        sharedPreferencesManager.saveSettings(settingsState)
    }
}