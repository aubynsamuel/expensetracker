package com.aubynsamuel.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.aubynsamuel.expensetracker.data.local.SharedPreferencesManager
import com.aubynsamuel.expensetracker.presentation.navigation.Navigation
import com.aubynsamuel.expensetracker.ui.theme.ExpenseTrackerTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private var keepSplash = true

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { keepSplash }
        lifecycleScope.launch {
            delay(900)
            keepSplash = false
        }
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val sharedPreferencesManager = SharedPreferencesManager(this)

        setContent {
            val settingsState by sharedPreferencesManager.settingsState.collectAsState()

            WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars =
                !settingsState.darkTheme

            ExpenseTrackerTheme(settingsState = settingsState) {
                Navigation(
                    sharedPreferencesManager = sharedPreferencesManager
                )
            }
        }
    }
}