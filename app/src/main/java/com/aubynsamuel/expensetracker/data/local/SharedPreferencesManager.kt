package com.aubynsamuel.expensetracker.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.ui.graphics.Color
import androidx.core.content.edit
import com.aubynsamuel.expensetracker.data.model.SettingsState

class SharedPreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun getSettings(): SettingsState {
        val storedColorValue = sharedPreferences.getLong(SEED_COLOR_KEY, -1L)
        val seedColor = if (storedColorValue == -1L) {
            SeedColors[1]
        } else {
            Color(storedColorValue.toULong())
        }

        return SettingsState(
            darkTheme = sharedPreferences.getBoolean(DARK_THEME_KEY, false),
            seedColor = seedColor,
            blackTheme = sharedPreferences.getBoolean(BLACK_THEME_KEY, false),
            currency = sharedPreferences.getString(CURRENCY_KEY, "$") ?: "$"
        )
    }

    fun saveSettings(settingsState: SettingsState) {
        sharedPreferences.edit {
            putBoolean(DARK_THEME_KEY, settingsState.darkTheme)
            putLong(SEED_COLOR_KEY, settingsState.seedColor.value.toLong())
            putBoolean(BLACK_THEME_KEY, settingsState.blackTheme)
            putString(CURRENCY_KEY, settingsState.currency)
        }
    }

    fun getCategories(): Set<String> {
        return sharedPreferences.getStringSet(CATEGORIES_KEY, defaultCategories)
            ?: defaultCategories
    }

    fun addCategory(category: String) {
        val currentCategories = getCategories().toMutableSet()
        currentCategories.add(category)
        sharedPreferences.edit { putStringSet(CATEGORIES_KEY, currentCategories) }
    }

    fun removeCategory(category: String) {
        val currentCategories = getCategories().toMutableSet()
        currentCategories.remove(category)
        sharedPreferences.edit { putStringSet(CATEGORIES_KEY, currentCategories) }
    }

    companion object {
        const val DARK_THEME_KEY = "dark_theme"
        const val SEED_COLOR_KEY = "seed_color"
        const val BLACK_THEME_KEY = "black_theme"
        const val CURRENCY_KEY = "currency"
        const val CATEGORIES_KEY = "categories"
        val defaultCategories =
            setOf("Food", "Transport", "Shopping", "Bills", "Entertainment", "Health")
    }
}