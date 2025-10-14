package com.aubynsamuel.expensetracker.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SharedPreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    private val _isDarkMode = MutableStateFlow(sharedPreferences.getBoolean(IS_DARK_MODE, false))
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    private val _categories = MutableStateFlow(
        sharedPreferences.getStringSet(CATEGORIES_KEY, defaultCategories) ?: defaultCategories
    )
    val categories: StateFlow<Set<String>> = _categories

    fun toggleDarkMode() {
        val newValue = !isDarkMode.value
        sharedPreferences.edit { putBoolean(IS_DARK_MODE, newValue) }
        _isDarkMode.value = newValue
    }

    fun addCategory(category: String) {
        val currentCategories = categories.value.toMutableSet()
        currentCategories.add(category)
        sharedPreferences.edit { putStringSet(CATEGORIES_KEY, currentCategories) }
        _categories.value = currentCategories
    }

    fun removeCategory(category: String) {
        val currentCategories = categories.value.toMutableSet()
        currentCategories.remove(category)
        sharedPreferences.edit { putStringSet(CATEGORIES_KEY, currentCategories) }
        _categories.value = currentCategories
    }

    companion object {
        const val IS_DARK_MODE = "is_dark_mode"
        const val CATEGORIES_KEY = "categories"
        val defaultCategories =
            setOf("Food", "Transport", "Shopping", "Bills", "Entertainment", "Health")
    }
}