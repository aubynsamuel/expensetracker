package com.aubynsamuel.expensetracker.presentation.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.aubynsamuel.expensetracker.data.model.Budget
import com.aubynsamuel.expensetracker.data.model.BudgetTotals
import com.aubynsamuel.expensetracker.presentation.components.AddBudgetDialog
import com.aubynsamuel.expensetracker.presentation.components.BudgetItem
import com.aubynsamuel.expensetracker.presentation.components.EditBudgetDialog
import com.aubynsamuel.expensetracker.presentation.viewmodel.BudgetViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetsScreen(
    budgetViewModel: BudgetViewModel,
    goBack: () -> Unit,
    navigateToBudgetDetails: (Int) -> Unit,
) {
    val budgetsList by budgetViewModel.budgetsList.collectAsState()
//    val budgetsList = remember { dummyBudgets }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var budgetToDelete by remember { mutableStateOf<Budget?>(null) }
    var showEditBudgetDialog by remember { mutableStateOf(false) }
    var budgetToEdit by remember { mutableStateOf<Budget?>(null) }
    var showAddBudgetDialog by remember { mutableStateOf(false) }
    var selectedDateFilter by rememberSaveable { mutableStateOf("Today") }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Budget") },
            text = { Text("Are you sure you want to delete this budget?") },
            confirmButton = {
                Button(
                    onClick = {
                        budgetToDelete?.let { budgetViewModel.deleteBudget(it) }
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

    if (showEditBudgetDialog) {
        budgetToEdit?.let {
            EditBudgetDialog(
                budget = it,
                onUpdateBudget = { budget -> budgetViewModel.updateBudget(budget) },
                onDismiss = { showEditBudgetDialog = false }
            )
        }
    }

    if (showAddBudgetDialog) {
        AddBudgetDialog(
            viewModel = budgetViewModel,
            onDismiss = { showAddBudgetDialog = false }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Budgets") },
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back button"
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddBudgetDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Budget")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedDateFilter == "Today",
                    onClick = {
                        selectedDateFilter = "Today"
                    },
                    label = { Text("Today") },
                    colors = chipColors()
                )
                FilterChip(
                    selected = selectedDateFilter == "Week",
                    onClick = {
                        selectedDateFilter = "Week"
                    },
                    label = { Text("This Week") },
                    colors = chipColors()
                )
                FilterChip(
                    selected = selectedDateFilter == "Month",
                    onClick = {
                        selectedDateFilter = "Month"
                    },
                    label = { Text("This Month") },
                    colors = chipColors()
                )
                FilterChip(
                    selected = selectedDateFilter == "Past",
                    onClick = {
                        selectedDateFilter = "Past"
                    },
                    label = { Text("Past") },
                    colors = chipColors()
                )
            }

            val filteredBudgets = budgetsList.filter { budget ->
                val calendar = Calendar.getInstance()
                when (selectedDateFilter) {
                    "Today" -> { // Today
                        val todayStart = Calendar.getInstance().apply {
                            set(Calendar.HOUR_OF_DAY, 0)
                            set(Calendar.MINUTE, 0)
                            set(Calendar.SECOND, 0)
                            set(Calendar.MILLISECOND, 0)
                        }.timeInMillis
                        val todayEnd = Calendar.getInstance().apply {
                            set(Calendar.HOUR_OF_DAY, 23)
                            set(Calendar.MINUTE, 59)
                            set(Calendar.SECOND, 59)
                            set(Calendar.MILLISECOND, 999)
                        }.timeInMillis
                        budget.startDate >= todayStart && budget.endDate <= todayEnd
                    }

                    "Week" -> { // This Week
                        val weekStart = Calendar.getInstance().apply {
                            firstDayOfWeek = calendar.firstDayOfWeek
                            set(Calendar.DAY_OF_WEEK, firstDayOfWeek)
                            set(Calendar.HOUR_OF_DAY, 0)
                            set(Calendar.MINUTE, 0)
                            set(Calendar.SECOND, 0)
                            set(Calendar.MILLISECOND, 0)
                        }.timeInMillis
                        val weekEnd = Calendar.getInstance().apply {
                            firstDayOfWeek = calendar.firstDayOfWeek
                            set(Calendar.DAY_OF_WEEK, firstDayOfWeek)
                            add(Calendar.WEEK_OF_YEAR, 1)
                            add(Calendar.MILLISECOND, -1)
                        }.timeInMillis
                        budget.startDate >= weekStart && budget.endDate <= weekEnd
                    }

                    "Month" -> { // This Month
                        val monthStart = Calendar.getInstance().apply {
                            set(Calendar.DAY_OF_MONTH, 1)
                            set(Calendar.HOUR_OF_DAY, 0)
                            set(Calendar.MINUTE, 0)
                            set(Calendar.SECOND, 0)
                            set(Calendar.MILLISECOND, 0)
                        }.timeInMillis
                        val monthEnd = Calendar.getInstance().apply {
                            add(Calendar.MONTH, 1)
                            set(Calendar.DAY_OF_MONTH, 1)
                            add(Calendar.MILLISECOND, -1)
                        }.timeInMillis
                        budget.startDate >= monthStart && budget.endDate <= monthEnd
                    }

                    "Past" -> { // Past
                        budget.endDate < Calendar.getInstance().timeInMillis
                    }

                    else -> false
                }
            }

            if (filteredBudgets.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "No budgets yet",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Tap + to add your first budget",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.clip(
                        shape = RoundedCornerShape(20.dp)
                    ),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(
                        top = 16.dp,
                        bottom = paddingValues.calculateBottomPadding() + 10.dp
                    ),
                ) {
                    items(filteredBudgets) { budget ->
                        val budgetTotals by budgetViewModel.getBudgetTotals(budget.id)
                            .collectAsState(BudgetTotals(0.0, 0.0))
                        BudgetItem(
                            budget = budget,
                            onEdit = {
                                budgetToEdit = it
                                showEditBudgetDialog = true
                            },
                            onDelete = {
                                budgetToDelete = it
                                showDeleteDialog = true
                            },
                            onClick = {
                                navigateToBudgetDetails(budget.id)
                            },
                            purchasedAmount = budgetTotals.checkedTotal,
                            totalAmount = budgetTotals.total
                        )
                    }
                }
            }
        }
    }
}