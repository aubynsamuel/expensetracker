package com.aubynsamuel.expensetracker.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.aubynsamuel.expensetracker.presentation.utils.showToast
import com.aubynsamuel.expensetracker.presentation.viewmodel.BudgetViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBudgetDialog(
    viewModel: BudgetViewModel,
    onDismiss: () -> Unit,
) {
    var name by remember { mutableStateOf("") }
    val context = LocalContext.current
    var isOneTime by remember { mutableStateOf(false) }
    var timeFrame by remember { mutableStateOf("Month") } // Default to Month
    val datePickerState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Picker
    )

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 24.dp, horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()), // Make content scrollable
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Dialog Title
                Text(
                    text = "Add New Budget",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))

                // 1. Name Field
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Budget Name") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Description,
                            "Name",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                // One-time budget switch
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("One-time budget")
                    Switch(
                        checked = isOneTime,
                        onCheckedChange = { isOneTime = it }
                    )
                }

                if (isOneTime) {
                    DatePicker(
                        state = datePickerState,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    // Time frame selection
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        listOf("Day", "Week", "Month").forEach { option ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = timeFrame == option,
                                    onClick = { timeFrame = option }
                                )
                                Text(option)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    // Cancel Button
                    TextButton(onClick = { onDismiss() }) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    // Add Expense Button
                    Button(
                        onClick = {
                            if (name.isBlank()) {
                                showToast(context, "Please enter a name")
                            } else {
                                val calendar = Calendar.getInstance()
                                val startDate: Long
                                val endDate: Long

                                if (isOneTime) {
                                    startDate = datePickerState.selectedDateMillis
                                        ?: System.currentTimeMillis()
                                    endDate = startDate
                                } else {
                                    when (timeFrame) {
                                        "Day" -> {
                                            startDate = calendar.timeInMillis
                                            calendar.add(Calendar.DAY_OF_YEAR, 1)
                                            endDate = calendar.timeInMillis
                                        }

                                        "Week" -> {
                                            calendar.set(
                                                Calendar.DAY_OF_WEEK,
                                                calendar.firstDayOfWeek
                                            )
                                            startDate = calendar.timeInMillis
                                            calendar.add(Calendar.WEEK_OF_YEAR, 1)
                                            endDate = calendar.timeInMillis
                                        }

                                        else -> { // Month
                                            calendar.set(Calendar.DAY_OF_MONTH, 1)
                                            startDate = calendar.timeInMillis
                                            calendar.add(Calendar.MONTH, 1)
                                            endDate = calendar.timeInMillis
                                        }
                                    }
                                }

                                viewModel.addBudget(name, isOneTime, timeFrame, startDate, endDate)
                                onDismiss()
                            }
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier.height(48.dp)
                    ) {
                        Text("Add Budget")
                    }
                }
            }
        }
    }
}
