package com.aubynsamuel.expensetracker.data.model

import androidx.compose.ui.graphics.Color

data class SettingsState(
    val darkTheme: Boolean = false,
    val seedColor: Color = Color.White,
    val blackTheme: Boolean = false,
)