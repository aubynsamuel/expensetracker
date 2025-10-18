package com.aubynsamuel.expensetracker.presentation.components.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aubynsamuel.expensetracker.data.local.currencies

@Composable
fun CurrencyPicker(
    onDismiss: () -> Unit,
    selectedCurrency: String,
    onCurrencySelected: (String) -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Currency") },
        text = {
            LazyColumn(modifier = Modifier.height(400.dp)) {
                items(currencies) { (code, symbol) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onCurrencySelected(symbol) },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "$code ($symbol)")
                        RadioButton(
                            selected = symbol == selectedCurrency,
                            onClick = { onCurrencySelected(symbol) }
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Done")
            }
        }
    )
}