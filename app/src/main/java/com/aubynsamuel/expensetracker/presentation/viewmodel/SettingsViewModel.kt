package com.aubynsamuel.expensetracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aubynsamuel.expensetracker.data.model.SettingsState
import com.aubynsamuel.expensetracker.data.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(private val settingsRepository: SettingsRepository) : ViewModel() {

    private val _settingsState = MutableStateFlow(settingsRepository.getSettings())
    val settingsState: StateFlow<SettingsState> = _settingsState.asStateFlow()

    fun saveSettings(settingsState: SettingsState) {
        viewModelScope.launch {
            settingsRepository.saveSettings(settingsState)
            _settingsState.value = settingsRepository.getSettings()
        }
    }
}