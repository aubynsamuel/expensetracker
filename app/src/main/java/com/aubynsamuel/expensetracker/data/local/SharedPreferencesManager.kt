package com.aubynsamuel.expensetracker.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.ui.graphics.Color
import androidx.core.content.edit
import com.aubynsamuel.expensetracker.data.model.SettingsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedPreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    private val _settingsState = MutableStateFlow(
        SettingsState(
            darkTheme = sharedPreferences.getBoolean(DARK_THEME_KEY, false),
            seedColor = Color(
                sharedPreferences.getInt(
                    SEED_COLOR_KEY,
                    SeedColors[0].value.toInt()
                )
            ),
            blackTheme = sharedPreferences.getBoolean(BLACK_THEME_KEY, false)
        )
    )
    val settingsState = _settingsState.asStateFlow()


    private val _expenseCategories = MutableStateFlow(
        sharedPreferences.getStringSet(CATEGORIES_KEY, defaultCategories) ?: defaultCategories
    )
    val categories: StateFlow<Set<String>> = _expenseCategories

    fun saveSettings(settingsState: SettingsState) {
        _settingsState.value = settingsState
        sharedPreferences.edit {
            putBoolean(DARK_THEME_KEY, settingsState.darkTheme)
            putInt(SEED_COLOR_KEY, settingsState.seedColor.value.toInt())
            putBoolean(BLACK_THEME_KEY, settingsState.blackTheme)
        }
    }

    fun addCategory(category: String) {
        val currentCategories = categories.value.toMutableSet()
        currentCategories.add(category)
        sharedPreferences.edit { putStringSet(CATEGORIES_KEY, currentCategories) }
        _expenseCategories.value = currentCategories
    }

    fun removeCategory(category: String) {
        val currentCategories = categories.value.toMutableSet()
        currentCategories.remove(category)
        sharedPreferences.edit { putStringSet(CATEGORIES_KEY, currentCategories) }
        _expenseCategories.value = currentCategories
    }

    companion object {
        const val DARK_THEME_KEY = "dark_theme"
        const val SEED_COLOR_KEY = "seed_color"
        const val BLACK_THEME_KEY = "black_theme"
        const val CATEGORIES_KEY = "categories"
        val defaultCategories =
            setOf("Food", "Transport", "Shopping", "Bills", "Entertainment", "Health")
    }
}