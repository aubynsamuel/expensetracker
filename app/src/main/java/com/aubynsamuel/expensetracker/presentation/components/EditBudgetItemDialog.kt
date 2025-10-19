package com.aubynsamuel.expensetracker.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aubynsamuel.expensetracker.data.model.BudgetItem

@Composable
fun EditBudgetItemDialog(
    budgetItem: BudgetItem,
    onUpdate: (BudgetItem) -> Unit,
    onDismiss: () -> Unit,
) {
    var name by remember { mutableStateOf(budgetItem.name) }
    var price by remember { mutableStateOf(budgetItem.price.toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Budget Item") },
        text = {
            Column {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Item Name") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Price") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onUpdate(budgetItem.copy(name = name, price = price.toDouble()))
                    onDismiss()
                }
            ) {
                Text("Update")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}