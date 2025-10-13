package com.aubynsamuel.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import com.aubynsamuel.expensetracker.data.local.DataStoreManager
import com.aubynsamuel.expensetracker.data.local.ExpenseDatabase
import com.aubynsamuel.expensetracker.data.repository.ExpenseRepository
import com.aubynsamuel.expensetracker.presentation.navigation.Navigation
import com.aubynsamuel.expensetracker.presentation.viewmodel.SettingsViewModel
import com.aubynsamuel.expensetracker.presentation.viewmodel.ViewModelFactory
import com.aubynsamuel.expensetracker.ui.theme.ExpenseTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val dataStoreManager = DataStoreManager(this)
        val database = ExpenseDatabase.getDatabase(this)
        val repository = ExpenseRepository(database.expenseDao(), dataStoreManager)
        val settingsViewModel: SettingsViewModel by viewModels {
            ViewModelFactory(repository)
        }
        setContent {
            val isDarkMode by settingsViewModel.isDarkMode.collectAsState()
            WindowCompat.getInsetsController(window, window.decorView)
                .isAppearanceLightStatusBars = !isDarkMode

            ExpenseTrackerTheme(darkTheme = isDarkMode) {
                Navigation()
            }
        }
    }
}