@file:OptIn(ExperimentalMaterial3Api::class)

package com.aubynsamuel.expensetracker.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.NightlightRound
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aubynsamuel.expensetracker.data.model.SettingsState
import com.aubynsamuel.expensetracker.presentation.components.settings.ColorSchemePicker
import com.aubynsamuel.expensetracker.presentation.components.settings.CurrencyPicker
import com.aubynsamuel.expensetracker.presentation.components.settings.SettingItem
import com.aubynsamuel.expensetracker.presentation.components.settings.SettingsCard
import com.aubynsamuel.expensetracker.presentation.navigation.DrawerState
import com.aubynsamuel.expensetracker.presentation.theme.ExpenseTrackerTheme
import com.aubynsamuel.expensetracker.presentation.viewmodel.SettingsViewModel
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
    var showSelectColorDialog by remember { mutableStateOf(false) }
    var showCurrencyPickerDialog by remember { mutableStateOf(false) }

    if (showSelectColorDialog) {
        ColorSchemePicker(
            onDismiss = { showSelectColorDialog = false },
            seedColor = settingsState.seedColor,
            onClick = { color ->
                onStateChange(settingsState.copy(seedColor = color))
            }
        )
    }

    if (showCurrencyPickerDialog) {
        CurrencyPicker(
            onDismiss = { showCurrencyPickerDialog = false },
            selectedCurrency = settingsState.currency,
            onCurrencySelected = { currency ->
                onStateChange(settingsState.copy(currency = currency))
            }
        )
    }

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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SettingsCard(cardTitle = "Appearance") {
                // Dark Theme
                SettingItem(
                    title = "Dark Theme",
                    subTitle = "Use a dark appearance",
                    icon = if (settingsState.darkTheme) Icons.Default.DarkMode
                    else Icons.Default.LightMode,
                    checked = settingsState.darkTheme,
                    onCheckedChange = { it -> onStateChange(settingsState.copy(darkTheme = it)) },
                    onClick = { onStateChange(settingsState.copy(darkTheme = !settingsState.darkTheme)) }
                )

                // Black Theme
                SettingItem(
                    title = "Black Theme",
                    subTitle = "Use a pure black background",
                    icon = if (settingsState.blackTheme) Icons.Default.NightlightRound
                    else Icons.Default.NightsStay,
                    checked = settingsState.blackTheme,
                    onCheckedChange = { it -> onStateChange(settingsState.copy(blackTheme = it)) },
                    onClick = { onStateChange(settingsState.copy(blackTheme = !settingsState.blackTheme)) },
                    enabled = settingsState.darkTheme
                )

                // Seed Color
                SettingItem(
                    title = "Color Scheme",
                    icon = Icons.Default.Palette,
                    subTitle = "Pick your theme color",
                    onClick = { showSelectColorDialog = true }
                )

                // Currency
                SettingItem(
                    title = "Currency",
                    icon = Icons.Default.AttachMoney,
                    subTitle = "Select your currency",
                    onClick = { showCurrencyPickerDialog = true }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_9_PRO)
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
