package com.aubynsamuel.expensetracker.presentation.theme

import android.os.Build
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.aubynsamuel.expensetracker.data.model.SettingsState
import com.materialkolor.dynamiccolor.ColorSpec
import com.materialkolor.rememberDynamicMaterialThemeState

private val DarkColorScheme = darkColorScheme()
private val LightColorScheme = lightColorScheme()


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ExpenseTrackerTheme(
    settingsState: SettingsState,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val darkTheme = settingsState.darkTheme
    val blackTheme = settingsState.blackTheme
    val seedColor = settingsState.seedColor

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(
                context
            )
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val dynamicThemeState = rememberDynamicMaterialThemeState(
        seedColor = when (seedColor) {
            Color.White -> colorScheme.primary
            else -> seedColor
        },
        specVersion = if (blackTheme && darkTheme) ColorSpec.SpecVersion.SPEC_2021 else ColorSpec.SpecVersion.SPEC_2025,
        isDark = darkTheme,
        isAmoled = blackTheme && darkTheme,
    )
    val scheme =
        if (seedColor == Color.White && !(blackTheme && darkTheme)) colorScheme
        else dynamicThemeState.colorScheme

    MaterialExpressiveTheme(
        colorScheme = scheme,
        typography = Typography,
        content = content,
        motionScheme = MotionScheme.expressive()
    )
}