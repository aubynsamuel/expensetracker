package com.aubynsamuel.expensetracker

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.aubynsamuel.expensetracker.presentation.navigation.Navigation
import com.aubynsamuel.expensetracker.presentation.screens.AuthenticationScreen
import com.aubynsamuel.expensetracker.presentation.theme.ExpenseTrackerTheme
import com.aubynsamuel.expensetracker.presentation.theme.LocalSettingsState
import com.aubynsamuel.expensetracker.presentation.utils.authenticateUser
import com.aubynsamuel.expensetracker.presentation.viewmodel.BudgetViewModel
import com.aubynsamuel.expensetracker.presentation.viewmodel.ExpensesViewModel
import com.aubynsamuel.expensetracker.presentation.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
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

        setContent {
            val settingsViewModel: SettingsViewModel = hiltViewModel()
            val expensesViewModel: ExpensesViewModel = hiltViewModel()
            val budgetViewModel: BudgetViewModel = hiltViewModel()
            val settingsState by settingsViewModel.settingsState.collectAsState()

            var isAuthenticated by remember { mutableStateOf(false) }
            var showAuthPrompt by remember { mutableStateOf(true) }

            fun authenticateUser() {
                showAuthPrompt = false
                authenticateUser(
                    onSuccess = { isAuthenticated = true },
                    onFailure = { /*finish()*/ }
                )
            }

            WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars =
                !settingsState.darkTheme

            CompositionLocalProvider(LocalSettingsState provides settingsState) {
                ExpenseTrackerTheme(settingsState = settingsState) {
                    if (settingsState.appLock && !isAuthenticated) {
                        AuthenticationScreen(onUnlock = { authenticateUser() })
                        if (showAuthPrompt) {
                            LaunchedEffect(Unit) {
                                authenticateUser()
                            }
                        }
                    } else {
                        Navigation(
                            settingsViewModel = settingsViewModel,
                            expensesViewModel = expensesViewModel,
                            budgetViewModel = budgetViewModel
                        )
                    }
                }
            }
        }
    }
}