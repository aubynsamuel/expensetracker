package com.aubynsamuel.expensetracker.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Garage
import androidx.compose.material.icons.filled.Poll
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import com.aubynsamuel.expensetracker.presentation.navigation.Screen

@Composable
fun DrawerContent(
    changeScreen: (NavKey) -> Unit,
    currentScreen: NavKey,
    width: Dp,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .width(width)
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "Expensify",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSurface
        )

        // Navigation section
        Column(
            verticalArrangement = Arrangement.Center, modifier = Modifier.weight(1f)
        ) {
            DrawerItem(
                label = "Home",
                icon = Icons.Default.Garage,
                selected = currentScreen == Screen.HomeScreen,
                onClick = { changeScreen(Screen.HomeScreen) }
            )
            DrawerItem(
                label = "Expenses",
                icon = Icons.Default.Poll,
                selected = currentScreen == Screen.ExpensesScreen,
                onClick = { changeScreen(Screen.ExpensesScreen) }
            )
        }

        // Settings at bottom
        DrawerItem(
            label = "Settings",
            icon = Icons.Default.Settings,
            selected = currentScreen == Screen.SettingsScreen,
            onClick = { changeScreen(Screen.SettingsScreen) }
        )
    }
}