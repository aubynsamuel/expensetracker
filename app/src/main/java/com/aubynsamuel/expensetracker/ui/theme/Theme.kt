package com.aubynsamuel.expensetracker.ui.theme

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.runtime.Composable
import com.aubynsamuel.expensetracker.data.model.SettingsState
import com.materialkolor.rememberDynamicMaterialThemeState

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ExpenseTrackerTheme(
    settingsState: SettingsState,
    content: @Composable () -> Unit,
) {
    val dynamicThemeState = rememberDynamicMaterialThemeState(
        isDark = settingsState.darkTheme,
        seedColor = settingsState.seedColor,
        isAmoled = settingsState.blackTheme,
    )

    MaterialExpressiveTheme(
        colorScheme = dynamicThemeState.colorScheme,
        typography = Typography,
        content = content,
        motionScheme = MotionScheme.expressive()
    )
}