package com.aubynsamuel.expensetracker.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aubynsamuel.expensetracker.data.model.Expense
import com.aubynsamuel.expensetracker.presentation.components.EditExpenseDialog
import com.aubynsamuel.expensetracker.presentation.components.ExpenseItem
import com.aubynsamuel.expensetracker.presentation.navigation.DrawerState
import com.aubynsamuel.expensetracker.presentation.viewmodel.ExpensesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesScreen(
    expensesViewModel: ExpensesViewModel,
    toggleDrawer: () -> Unit,
    drawerState: DrawerState,
) {
    val expensesList by expensesViewModel.filteredExpensesList.collectAsState()
    var selectedDateFilter by remember { mutableStateOf("All") }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var expenseToDelete by remember { mutableStateOf<Expense?>(null) }
    var showEditExpenseDialog by remember { mutableStateOf(false) }
    var expenseToEdit by remember { mutableStateOf<Expense?>(null) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Expense") },
            text = { Text("Are you sure you want to delete this expense?") },
            confirmButton = {
                Button(
                    onClick = {
                        expenseToDelete?.let { expensesViewModel.deleteExpense(it) }
                        showDeleteDialog = false
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    if (showEditExpenseDialog) {
        expenseToEdit?.let {
            EditExpenseDialog(
                expense = it,
                onUpdateExpense = { expense -> expensesViewModel.updateExpense(expense) },
                onDismiss = { showEditExpenseDialog = false }
            )
        }
    }

    BackHandler(
        enabled = drawerState == DrawerState.Opened,
        onBack = { toggleDrawer() }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Expenses") },
                navigationIcon = {
                    IconButton(onClick = toggleDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedDateFilter == "All",
                    onClick = {
                        selectedDateFilter = "All"
                        expensesViewModel.filterExpenses("All")
                    },
                    label = { Text("All") },
                    colors = chipColors()
                )
                FilterChip(
                    selected = selectedDateFilter == "Today",
                    onClick = {
                        selectedDateFilter = "Today"
                        expensesViewModel.filterExpenses("Today")
                    },
                    label = { Text("Today") },
                    colors = chipColors()
                )
                FilterChip(
                    selected = selectedDateFilter == "Week",
                    onClick = {
                        selectedDateFilter = "Week"
                        expensesViewModel.filterExpenses("Week")
                    },
                    label = { Text("This Week") },
                    colors = chipColors()
                )
                FilterChip(
                    selected = selectedDateFilter == "Month",
                    onClick = {
                        selectedDateFilter = "Month"
                        expensesViewModel.filterExpenses("Month")
                    },
                    label = { Text("This Month") },
                    colors = chipColors()
                )
            }

            // Expenses List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(expensesList.sortedByDescending { it.date }) { expense ->
                    ExpenseItem(
                        expense = expense,
                        onEdit = {
                            expenseToEdit = it
                            showEditExpenseDialog = true
                        },
                        onDelete = {
                            expenseToDelete = it
                            showDeleteDialog = true
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun chipColors(): SelectableChipColors {
    return FilterChipDefaults.filterChipColors()
        .copy(
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
}
