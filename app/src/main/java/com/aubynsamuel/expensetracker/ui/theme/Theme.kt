package com.aubynsamuel.expensetracker.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
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
import com.materialkolor.PaletteStyle
import com.materialkolor.rememberDynamicMaterialThemeState

val DarkColorScheme = darkColorScheme()
val LightColorScheme = lightColorScheme()

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ExpenseTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val dynamicThemeState = rememberDynamicMaterialThemeState(
        isDark = darkTheme,
        style = PaletteStyle.TonalSpot,
        seedColor = Color(0xFF68A500),
    )

    MaterialExpressiveTheme(
        colorScheme = dynamicThemeState.colorScheme,
        typography = Typography,
        content = content,
        motionScheme = MotionScheme.expressive()
    )
}