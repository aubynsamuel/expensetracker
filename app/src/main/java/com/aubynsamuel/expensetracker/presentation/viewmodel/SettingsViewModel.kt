package com.aubynsamuel.expensetracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aubynsamuel.expensetracker.data.local.SharedPreferencesManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(private val sharedPreferencesManager: SharedPreferencesManager) :
    ViewModel() {

    val isDarkMode: StateFlow<Boolean> = sharedPreferencesManager.isDarkMode.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = false
    )

    fun toggleDarkMode() {
        viewModelScope.launch {
            sharedPreferencesManager.toggleDarkMode()
        }
    }
}