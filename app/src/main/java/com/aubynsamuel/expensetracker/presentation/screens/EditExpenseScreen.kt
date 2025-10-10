package com.aubynsamuel.expensetracker.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aubynsamuel.expensetracker.data.model.Expense

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditExpenseScreen(expense: Expense, onUpdateExpense: (Expense) -> Unit) {
    var amount by remember { mutableStateOf(expense.amount.toString()) }
    var category by remember { mutableStateOf(expense.category) }
    var description by remember { mutableStateOf(expense.title) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Edit Expense") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") }
            )
            TextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Category") }
            )
            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") }
            )
            Button(onClick = {
                onUpdateExpense(
                    expense.copy(
                        title = description,
                        amount = amount.toDouble(),
                        category = category
                    )
                )
            }) {
                Text("Update Expense")
            }
        }
    }
}