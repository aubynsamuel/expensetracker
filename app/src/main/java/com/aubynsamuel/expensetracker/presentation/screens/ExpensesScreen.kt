package com.aubynsamuel.expensetracker.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Scaffold
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
import com.aubynsamuel.expensetracker.presentation.viewmodel.ExpensesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesScreenContent(
    expensesViewModel: ExpensesViewModel,
    onEditExpense: (Expense) -> Unit,
    onDeleteExpense: (Expense) -> Unit,
) {
    val expensesList by expensesViewModel.expensesList.collectAsState()
    var selectedTypeFilter by remember { mutableStateOf("All") }
    var selectedDateFilter by remember { mutableStateOf("All") }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var expenseToDelete by remember { mutableStateOf<Expense?>(null) }

    val filteredExpensesByType = when (selectedTypeFilter) {
        "Income" -> expensesList.filter { it.amount > 0 }
        "Expense" -> expensesList.filter { it.amount < 0 }
        else -> expensesList
    }

    val filteredExpenses = when (selectedDateFilter) {
        "Today" -> expensesViewModel.filterExpensesByToday(filteredExpensesByType)
        "Week" -> expensesViewModel.filterExpensesByThisWeek(filteredExpensesByType)
        "Month" -> expensesViewModel.filterExpensesByThisMonth(filteredExpensesByType)
        else -> filteredExpensesByType
    }

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

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Expenses") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Filter Chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedTypeFilter == "All",
                    onClick = { selectedTypeFilter = "All" },
                    label = { Text("All") }
                )
                FilterChip(
                    selected = selectedTypeFilter == "Income",
                    onClick = { selectedTypeFilter = "Income" },
                    label = { Text("Income") }
                )
                FilterChip(
                    selected = selectedTypeFilter == "Expense",
                    onClick = { selectedTypeFilter = "Expense" },
                    label = { Text("Expense") }
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedDateFilter == "All",
                    onClick = { selectedDateFilter = "All" },
                    label = { Text("All") }
                )
                FilterChip(
                    selected = selectedDateFilter == "Today",
                    onClick = { selectedDateFilter = "Today" },
                    label = { Text("Today") }
                )
                FilterChip(
                    selected = selectedDateFilter == "Week",
                    onClick = { selectedDateFilter = "Week" },
                    label = { Text("This Week") }
                )
                FilterChip(
                    selected = selectedDateFilter == "Month",
                    onClick = { selectedDateFilter = "Month" },
                    label = { Text("This Month") }
                )
            }

            // Expenses List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredExpenses) { expense ->
                    ExpenseItem(
                        expense = expense,
                        onEdit = { onEditExpense(it) },
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

