package com.aubynsamuel.expensetracker.data.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class SettingsState(
    val darkTheme: Boolean = false,
    val seedColor: Color = Color.White,
    val blackTheme: Boolean = true,
)