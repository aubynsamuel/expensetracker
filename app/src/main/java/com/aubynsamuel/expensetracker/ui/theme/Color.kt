package com.aubynsamuel.expensetracker.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

private val LightPrimary = Color(0xFF6C63FF) // Vibrant Purple
private val LightOnPrimary = Color(0xFFFFFFFF)
private val LightPrimaryContainer = Color(0xFFE5E3FF)
private val LightOnPrimaryContainer = Color(0xFF1B0065)

private val LightSecondary = Color(0xFFFF6584) // Coral Pink
private val LightOnSecondary = Color(0xFFFFFFFF)
private val LightSecondaryContainer = Color(0xFFFFD9E0)
private val LightOnSecondaryContainer = Color(0xFF3E001E)

private val LightTertiary = Color(0xFF00D9A3) // Mint Green
private val LightOnTertiary = Color(0xFFFFFFFF)
private val LightTertiaryContainer = Color(0xFFB3FFE8)
private val LightOnTertiaryContainer = Color(0xFF003828)

private val LightError = Color(0xFFFF5449)
private val LightOnError = Color(0xFFFFFFFF)
private val LightErrorContainer = Color(0xFFFFDAD6)
private val LightOnErrorContainer = Color(0xFF410002)

private val LightBackground = Color(0xFFFCFCFF)
private val LightOnBackground = Color(0xFF1B1B1F)
private val LightSurface = Color(0xFFFCFCFF)
private val LightOnSurface = Color(0xFF1B1B1F)
private val LightSurfaceVariant = Color(0xFFF4F4FF)
private val LightOnSurfaceVariant = Color(0xFF46464F)

private val LightOutline = Color(0xFF777680)
private val LightOutlineVariant = Color(0xFFC7C6D0)

// Dark Theme Colors - Rich and Vibrant
private val DarkPrimary = Color(0xFF9D92FF) // Lighter vibrant purple
private val DarkOnPrimary = Color(0xFF2C0085)
private val DarkPrimaryContainer = Color(0xFF4A3FD6)
private val DarkOnPrimaryContainer = Color(0xFFE5E3FF)

private val DarkSecondary = Color(0xFFFF8FA3) // Lighter coral
private val DarkOnSecondary = Color(0xFF5E0032)
private val DarkSecondaryContainer = Color(0xFFE54169)
private val DarkOnSecondaryContainer = Color(0xFFFFD9E0)

private val DarkTertiary = Color(0xFF5DFFCB) // Brighter mint
private val DarkOnTertiary = Color(0xFF00513D)
private val DarkTertiaryContainer = Color(0xFF00D9A3)
private val DarkOnTertiaryContainer = Color(0xFFB3FFE8)

private val DarkError = Color(0xFFFFB4AB)
private val DarkOnError = Color(0xFF690005)
private val DarkErrorContainer = Color(0xFF93000A)
private val DarkOnErrorContainer = Color(0xFFFFDAD6)

private val DarkBackground = Color(0xFF1B1B1F)
private val DarkOnBackground = Color(0xFFE4E1E6)
private val DarkSurface = Color(0xFF1B1B1F)
private val DarkOnSurface = Color(0xFFE4E1E6)
private val DarkSurfaceVariant = Color(0xFF2B2B33)
private val DarkOnSurfaceVariant = Color(0xFFC7C6D0)

private val DarkOutline = Color(0xFF91909A)
private val DarkOutlineVariant = Color(0xFF46464F)

val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = LightOnPrimary,
    primaryContainer = LightPrimaryContainer,
    onPrimaryContainer = LightOnPrimaryContainer,
    secondary = LightSecondary,
    onSecondary = LightOnSecondary,
    secondaryContainer = LightSecondaryContainer,
    onSecondaryContainer = LightOnSecondaryContainer,
    tertiary = LightTertiary,
    onTertiary = LightOnTertiary,
    tertiaryContainer = LightTertiaryContainer,
    onTertiaryContainer = LightOnTertiaryContainer,
    error = LightError,
    onError = LightOnError,
    errorContainer = LightErrorContainer,
    onErrorContainer = LightOnErrorContainer,
    background = LightBackground,
    onBackground = LightOnBackground,
    surface = LightSurface,
    onSurface = LightOnSurface,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightOnSurfaceVariant,
    outline = LightOutline,
    outlineVariant = LightOutlineVariant,
)

val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
    primaryContainer = DarkPrimaryContainer,
    onPrimaryContainer = DarkOnPrimaryContainer,
    secondary = DarkSecondary,
    onSecondary = DarkOnSecondary,
    secondaryContainer = DarkSecondaryContainer,
    onSecondaryContainer = DarkOnSecondaryContainer,
    tertiary = DarkTertiary,
    onTertiary = DarkOnTertiary,
    tertiaryContainer = DarkTertiaryContainer,
    onTertiaryContainer = DarkOnTertiaryContainer,
    error = DarkError,
    onError = DarkOnError,
    errorContainer = DarkErrorContainer,
    onErrorContainer = DarkOnErrorContainer,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariant,
    outline = DarkOutline,
    outlineVariant = DarkOutlineVariant,
)