package com.aubynsamuel.expensetracker.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.aubynsamuel.expensetracker.data.local.UserPreferencesKeys.IS_DARK_MODE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DataStoreManager(context: Context) {

    private val dataStore = context.dataStore

    val isDarkMode: Flow<Boolean> = dataStore.data.map {
        it[IS_DARK_MODE] ?: false
    }

    suspend fun toggleDarkMode() {
        dataStore.edit {
            it[IS_DARK_MODE] = !(it[IS_DARK_MODE] ?: false)
        }
    }

    companion object {
        val CATEGORIES_KEY = stringSetPreferencesKey("categories")
        val defaultCategories =
            setOf("Food", "Transport", "Shopping", "Bills", "Entertainment", "Health")
    }

    val categories: Flow<Set<String>> = dataStore.data.map { preferences ->
        preferences[CATEGORIES_KEY] ?: defaultCategories
    }

    suspend fun addCategory(category: String) {
        dataStore.edit { settings ->
            val currentCategories = settings[CATEGORIES_KEY] ?: defaultCategories
            settings[CATEGORIES_KEY] = currentCategories + category
        }
    }
}