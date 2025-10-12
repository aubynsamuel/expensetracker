package com.aubynsamuel.expensetracker.presentation.utils

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

fun NavBackStack<NavKey>.navigate(targetScreen: NavKey) {
    if (this.last() != targetScreen)
        this.add(targetScreen)

}