package com.aubynsamuel.expensetracker.presentation.theme

import androidx.compose.runtime.compositionLocalOf
import com.aubynsamuel.expensetracker.data.model.SettingsState

val LocalSettingsState = compositionLocalOf { SettingsState() }