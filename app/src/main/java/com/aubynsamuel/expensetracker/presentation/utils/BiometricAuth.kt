package com.aubynsamuel.expensetracker.presentation.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity


context(context: Context)
fun authenticateUser(
    onSuccess: () -> Unit,
    onFailure: () -> Unit,
) {
    val biometricManager = BiometricManager.from(context)

    when (biometricManager.canAuthenticate(
        BiometricManager.Authenticators.BIOMETRIC_STRONG or
                BiometricManager.Authenticators.DEVICE_CREDENTIAL
    )) {
        BiometricManager.BIOMETRIC_SUCCESS -> {
            showBiometricPrompt(onSuccess, onFailure)
        }

        BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
            Log.d("BiometricAuth", "No biometric hardware available")
            onSuccess()
        }

        BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
            Log.d("BiometricAuth", "Biometric hardware unavailable")
            onSuccess()
        }

        BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
            Log.d("BiometricAuth", "No biometrics enrolled")
            onSuccess()
        }

        else -> {
            onFailure()
        }
    }
}

context(context: Context)
fun showBiometricPrompt(
    onSuccess: () -> Unit,
    onFailure: () -> Unit,
) {
    val executor = ContextCompat.getMainExecutor(context)

    val biometricPrompt = BiometricPrompt(
        context as FragmentActivity,
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

context(context: Context)
fun promptEnrollBiometric() {
    val enrollIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
            putExtra(
                Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                BiometricManager.Authenticators.BIOMETRIC_STRONG or
                        BiometricManager.Authenticators.DEVICE_CREDENTIAL
            )
        }
    } else Intent(Settings.ACTION_SECURITY_SETTINGS)

    try {
        if (context is FragmentActivity)
            context.startActivity(enrollIntent)
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
    return when (biometricManager.canAuthenticate(
        BiometricManager.Authenticators.BIOMETRIC_STRONG or
                BiometricManager.Authenticators.DEVICE_CREDENTIAL
    )) {
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

