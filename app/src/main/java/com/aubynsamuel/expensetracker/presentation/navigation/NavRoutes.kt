package com.aubynsamuel.expensetracker.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class Screen : NavKey {
    @Serializable
    object HomeScreen : NavKey

    @Serializable
    object ExpensesScreen : NavKey

    @Serializable
    object SettingsScreen : NavKey

    @Serializable
    object BudgetsScreen : NavKey

    @Serializable
    data class BudgetDetailsScreen(val budgetId: Int) : NavKey
}