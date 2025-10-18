package com.aubynsamuel.expensetracker.presentation.components.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.aubynsamuel.expensetracker.data.local.SeedColors

@Composable
fun ColorSchemePicker(onDismiss: () -> Unit, seedColor: Color, onClick: (Color) -> Unit) {
    val seedColors = remember { SeedColors.chunked(4) }

    Dialog(onDismissRequest = onDismiss) {
        Card {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text("Choose Color Scheme", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(20.dp))
                seedColors.forEach {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        it.forEach { color ->
                            ColorItem(
                                color = color,
                                isSelected = color.value.toString() == seedColor.value.toString(),
                                onClick = { onClick(color) }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss, modifier = Modifier.padding(end = 10.dp)) {
                        Text("Done")
                    }
                }
            }
        }
    }
}