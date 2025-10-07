package com.aubynsamuel.expensetracker.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Garage
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Poll
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aubynsamuel.expensetracker.presentation.screens.Screens

@Composable
fun DrawerContent(
    changeScreen: (String) -> Unit,
    currentScreen: String,
    width: Dp,
    modifier: Modifier,
    dragAmountPx: Float,
) {
    Column(
        modifier = modifier
            .width(width)
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Text(dragAmountPx.toString())
        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile",
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(12.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = "Expensify",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "user@example.com",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Navigation Items
        NavigationDrawerItem(
            label = { Text("Home") },
            selected = currentScreen == Screens.HOME_SCREEN,
            onClick = { changeScreen(Screens.HOME_SCREEN) },
            modifier = Modifier.fillMaxWidth(),
            icon = { Icon(Icons.Default.Garage, contentDescription = "Home") },
            colors = NavigationDrawerItemDefaults.colors(
                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                unselectedContainerColor = Color.Transparent
            )
        )

        NavigationDrawerItem(
            label = { Text("Expenses") },
            selected = currentScreen == Screens.EXPENSES_SCREEN,
            onClick = { changeScreen(Screens.EXPENSES_SCREEN) },
            modifier = Modifier.fillMaxWidth(),
            icon = { Icon(Icons.Default.Poll, contentDescription = "Expenses") },
            colors = NavigationDrawerItemDefaults.colors(
                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                unselectedContainerColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        // Settings at bottom
        NavigationDrawerItem(
            label = { Text("Settings") },
            selected = false,
            onClick = { /* Handle settings */ },
            modifier = Modifier.fillMaxWidth(),
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            colors = NavigationDrawerItemDefaults.colors(
                unselectedContainerColor = Color.Transparent
            )
        )
    }
}