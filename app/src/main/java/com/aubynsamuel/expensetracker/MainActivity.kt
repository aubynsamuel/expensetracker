package com.aubynsamuel.expensetracker

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.aubynsamuel.expensetracker.presentation.navigation.Navigation
import com.aubynsamuel.expensetracker.presentation.screens.AuthenticationScreen
import com.aubynsamuel.expensetracker.presentation.theme.ExpenseTrackerTheme
import com.aubynsamuel.expensetracker.presentation.theme.LocalSettingsState
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
                    onSuccess = {
                        isAuthenticated = true
                    },
                    onFailure = {
//                                        finish()
                    }
                )
            }

            WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars =
                !settingsState.darkTheme

            CompositionLocalProvider(LocalSettingsState provides settingsState) {
                ExpenseTrackerTheme(settingsState = settingsState) {
                    if (settingsState.appLock && !isAuthenticated) {
                        AuthenticationScreen(onUnlock = { authenticateUser() })
                        // Trigger biometric authentication
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

    private fun authenticateUser(
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    ) {
        val biometricManager = BiometricManager.from(this)

        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                showBiometricPrompt(onSuccess, onFailure)
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Log.d("BiometricAuth", "No biometric hardware available")
                onFailure()
            }

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                Log.d("BiometricAuth", "Biometric hardware unavailable")
                onFailure()
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                Log.d("BiometricAuth", "No biometrics enrolled")
                promptEnrollBiometric()
            }

            else -> {
                onFailure()
            }
        }
    }

    private fun showBiometricPrompt(
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    ) {
        val executor = ContextCompat.getMainExecutor(this)

        val biometricPrompt = BiometricPrompt(
            this,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Log.d("BiometricAuth", "Error: $errString")

                    if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON ||
                        errorCode == BiometricPrompt.ERROR_USER_CANCELED
                    ) {
                        onFailure()
                    } else {
                        onFailure()
                    }
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Log.d("BiometricAuth", "Authentication succeeded!")
                    onSuccess()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Log.d("BiometricAuth", "Authentication attempt failed")
                    // Don't call onFailure here - let user retry
                }
            }
        )

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Unlock Expensify")
            .setSubtitle("Authenticate to access your expenses")
            .setAllowedAuthenticators(
                BiometricManager.Authenticators.BIOMETRIC_STRONG or
                        BiometricManager.Authenticators.DEVICE_CREDENTIAL or
                        BiometricManager.Authenticators.BIOMETRIC_WEAK
            )
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}

context(context: Context)
fun promptEnrollBiometric() {
    try {
        val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
            putExtra(
                Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                BiometricManager.Authenticators.BIOMETRIC_STRONG
            )
        }
        if (context is FragmentActivity) {
            context.startActivity(enrollIntent)
        }
    } catch (_: ActivityNotFoundException) {
        val fallbackIntent = Intent(Settings.ACTION_SECURITY_SETTINGS)
        if (context is FragmentActivity) {
            context.startActivity(fallbackIntent)
        }
    }
}

context(context: Context)
fun isBiometricAvailable(): BiometricAvailability {
    val biometricManager = BiometricManager.from(context)
    return when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
        BiometricManager.BIOMETRIC_SUCCESS -> BiometricAvailability.AVAILABLE
        BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
            Log.d("BiometricAuth", "No biometric hardware available.")
            BiometricAvailability.NO_HARDWARE_UNSUPPORTED
        }

        BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
            Log.d("BiometricAuth", "Biometric hardware unavailable.")
            BiometricAvailability.BIOMETRICS_UNAVAILABLE
        }

        BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
            Log.d("BiometricAuth", "No biometrics enrolled.")
            BiometricAvailability.NO_BIOMETRICS_ENROLLED
        }

        else -> {
            Log.d("BiometricAuth", "Unknown biometric availability.")
            BiometricAvailability.UNKNOWN
        }
    }
}

enum class BiometricAvailability {
    AVAILABLE,
    NO_HARDWARE_UNSUPPORTED,
    BIOMETRICS_UNAVAILABLE,
    NO_BIOMETRICS_ENROLLED,
    UNKNOWN
}