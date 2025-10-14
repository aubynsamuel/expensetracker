package com.aubynsamuel.expensetracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aubynsamuel.expensetracker.data.model.SettingsState
import com.aubynsamuel.expensetracker.data.repository.SettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(private val settingsRepository: SettingsRepository) :
    ViewModel() {

    val settingsState: StateFlow<SettingsState> = settingsRepository.settingsState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SettingsState()
    )

    fun saveSettings(settingsState: SettingsState) {
        viewModelScope.launch {
            settingsRepository.saveSettings(settingsState)
        }
    }
}