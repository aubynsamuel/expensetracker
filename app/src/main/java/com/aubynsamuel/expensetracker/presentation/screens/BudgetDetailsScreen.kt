package com.aubynsamuel.expensetracker.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aubynsamuel.expensetracker.presentation.components.AddBudgetItemDialog
import com.aubynsamuel.expensetracker.presentation.viewmodel.BudgetViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetDetailsScreen(
    budgetViewModel: BudgetViewModel,
    budgetId: Int,
    goBack: () -> Unit,
) {
    val budgetItems by budgetViewModel.budgetItems.collectAsState()
    var showAddBudgetItemDialog by remember { mutableStateOf(false) }

    budgetViewModel.getBudgetItems(budgetId)

    val totalBudget = budgetItems.sumOf { it.price }
    val remainingBudget = totalBudget - budgetItems.filter { it.isChecked }.sumOf { it.price }

    if (showAddBudgetItemDialog) {
        AddBudgetItemDialog(
            viewModel = budgetViewModel,
            budgetId = budgetId,
            onDismiss = { showAddBudgetItemDialog = false }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Budget Details") },
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back button"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddBudgetItemDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Budget Item")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Total Budget: $$totalBudget")
                Text(text = "Remaining: $$remainingBudget")
            }
            LazyColumn {
                items(budgetItems) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = item.isChecked,
                                onCheckedChange = {
                                    budgetViewModel.updateBudgetItem(item.copy(isChecked = it))
                                }
                            )
                            Text(text = item.name)
                        }
                        Text(text = "$${item.price}")
                    }
                }
            }
        }
    }
}