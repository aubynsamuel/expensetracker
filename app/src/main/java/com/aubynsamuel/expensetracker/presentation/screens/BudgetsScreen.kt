package com.aubynsamuel.expensetracker.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aubynsamuel.expensetracker.data.model.Budget
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
    var showDeleteDialog by remember { mutableStateOf(false) }
    var budgetToDelete by remember { mutableStateOf<Budget?>(null) }
    var showEditBudgetDialog by remember { mutableStateOf(false) }
    var budgetToEdit by remember { mutableStateOf<Budget?>(null) }
    var showAddBudgetDialog by remember { mutableStateOf(false) }
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Today", "This Week", "This Month", "Past")

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
        ) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }

            val filteredBudgets = budgetsList.filter { budget ->
                val calendar = Calendar.getInstance()
                when (selectedTabIndex) {
                    0 -> { // Today
                        val todayStart =
                            calendar.apply { set(Calendar.HOUR_OF_DAY, 0) }.timeInMillis
                        val todayEnd = calendar.apply { set(Calendar.HOUR_OF_DAY, 23) }.timeInMillis
                        budget.startDate >= todayStart && budget.endDate <= todayEnd
                    }

                    1 -> { // This Week
                        val weekStart = calendar.apply {
                            set(
                                Calendar.DAY_OF_WEEK,
                                firstDayOfWeek
                            )
                        }.timeInMillis
                        val weekEnd = calendar.apply { add(Calendar.WEEK_OF_YEAR, 1) }.timeInMillis
                        budget.startDate >= weekStart && budget.endDate <= weekEnd
                    }

                    2 -> { // This Month
                        val monthStart =
                            calendar.apply { set(Calendar.DAY_OF_MONTH, 1) }.timeInMillis
                        val monthEnd = calendar.apply { add(Calendar.MONTH, 1) }.timeInMillis
                        budget.startDate >= monthStart && budget.endDate <= monthEnd
                    }

                    3 -> { // Past
                        budget.endDate < Calendar.getInstance().timeInMillis
                    }

                    else -> false
                }
            }

            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(
                    top = 16.dp,
                    bottom = paddingValues.calculateBottomPadding() + 10.dp
                ),
            ) {
                items(filteredBudgets) { budget ->
                    val totalAmount by budgetViewModel.getBudgetTotal(budget.id)
                        .collectAsState(initial = 0.0)
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
                        onUpdate = { budgetViewModel.updateBudget(it) },
                        totalAmount = totalAmount
                    )
                }
            }
        }
    }
}