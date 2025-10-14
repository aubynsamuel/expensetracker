@file:OptIn(ExperimentalMaterial3Api::class)

package com.aubynsamuel.expensetracker.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aubynsamuel.expensetracker.data.local.SeedColors
import com.aubynsamuel.expensetracker.data.model.SettingsState
import com.aubynsamuel.expensetracker.presentation.navigation.DrawerState
import com.aubynsamuel.expensetracker.presentation.viewmodel.SettingsViewModel
import com.aubynsamuel.expensetracker.ui.theme.ExpenseTrackerTheme
import kotlinx.coroutines.Job

@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel,
    drawerState: DrawerState,
    toggleDrawer: () -> Job,
    goBack: () -> Unit,
) {
    val settingsState by settingsViewModel.settingsState.collectAsState()

    SettingsContent(
        settingsState = settingsState,
        onStateChange = settingsViewModel::saveSettings,
        drawerState = drawerState,
        toggleDrawer = { toggleDrawer() },
        goBack = goBack,
    )
}

@Composable
fun SettingsContent(
    settingsState: SettingsState,
    onStateChange: (SettingsState) -> Unit,
    drawerState: DrawerState,
    goBack: () -> Unit,
    toggleDrawer: () -> Unit,
) {
    BackHandler(
        enabled = drawerState == DrawerState.Opened,
        onBack = { toggleDrawer() }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back button"
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Dark Theme
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Dark Theme", modifier = Modifier.weight(1f))
                Switch(
                    checked = settingsState.darkTheme,
                    onCheckedChange = { onStateChange(settingsState.copy(darkTheme = it)) }
                )
            }

            // Black Theme
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Black Theme", modifier = Modifier.weight(1f))
                Switch(
                    checked = settingsState.blackTheme,
                    onCheckedChange = { onStateChange(settingsState.copy(blackTheme = it)) },
                    enabled = settingsState.darkTheme
                )
            }

            // Seed Color
            Column {
                Text(text = "Theme Color")
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(SeedColors) { color ->
                        ColorPickerItem(
                            color = color,
                            isSelected = color == settingsState.seedColor,
                            onClick = { onStateChange(settingsState.copy(seedColor = color)) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ColorPickerItem(color: Color, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(color)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "selected",
                tint = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    ExpenseTrackerTheme(settingsState = SettingsState()) {
        SettingsContent(
            settingsState = SettingsState(), onStateChange = {}, goBack = {},
            drawerState = DrawerState.Opened,
            toggleDrawer = {}
        )
    }
}
