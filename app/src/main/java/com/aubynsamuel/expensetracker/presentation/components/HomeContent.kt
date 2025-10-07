package com.aubynsamuel.expensetracker.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.aubynsamuel.expensetracker.presentation.screens.ExpensesScreenContent
import com.aubynsamuel.expensetracker.presentation.screens.HomeScreenContent
import com.aubynsamuel.expensetracker.presentation.screens.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    currentScreen: String,
    modifier: Modifier,
    toggleDrawer: () -> Unit,
) {
    Scaffold(
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            when (currentScreen) {
                Screens.HOME_SCREEN -> HomeScreenContent(toggleDrawer = toggleDrawer)
                Screens.EXPENSES_SCREEN -> ExpensesScreenContent()
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewMainScreen() {
    HomeContent("Home", modifier = Modifier, toggleDrawer = {})
}