package com.aubynsamuel.expensetracker.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.aubynsamuel.expensetracker.presentation.navigation.DrawerState
import com.aubynsamuel.expensetracker.presentation.viewmodel.SettingsViewModel
import kotlinx.coroutines.Job

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel,
    drawerState: DrawerState,
    toggleDrawer: () -> Job,
    goBack: () -> Unit,
) {
    val isDarkMode by settingsViewModel.isDarkMode.collectAsState()
    val context = LocalContext.current

    BackHandler(
        enabled = drawerState == DrawerState.Opened,
        onBack = { toggleDrawer() }
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Settings") })
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Dark Mode", modifier = Modifier.weight(1f))
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = { settingsViewModel.toggleDarkMode() }
                )
            }
        }
    }
}