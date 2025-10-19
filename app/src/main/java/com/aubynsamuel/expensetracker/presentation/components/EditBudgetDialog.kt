package com.aubynsamuel.expensetracker.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.aubynsamuel.expensetracker.data.model.Budget
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditBudgetDialog(budget: Budget, onUpdateBudget: (Budget) -> Unit, onDismiss: () -> Unit) {
    var name by remember { mutableStateOf(budget.name) }
    var isOneTime by remember { mutableStateOf(budget.isOneTime) }
    var timeFrame by remember { mutableStateOf(budget.timeFrame) }
    val isNameValid by remember(name) { mutableStateOf(name.isNotBlank()) }

    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = budget.startDate)
    var showDatePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    text = "Edit Budget",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Budget Name") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

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
                    Button(
                        onClick = { showDatePicker = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = datePickerState.selectedDateMillis?.let {
                                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(it))
                            } ?: "Select Date"
                        )
                    }
                } else {
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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { onDismiss() }) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            val calendar = Calendar.getInstance()
                            val newStartDate: Long
                            val newEndDate: Long

                            if (isOneTime) {
                                newStartDate =
                                    datePickerState.selectedDateMillis ?: budget.startDate
                                newEndDate = newStartDate
                            } else {
                                when (timeFrame) {
                                    "Day" -> {
                                        newStartDate = calendar.timeInMillis
                                        calendar.add(Calendar.DAY_OF_YEAR, 1)
                                        newEndDate = calendar.timeInMillis
                                    }

                                    "Week" -> {
                                        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
                                        newStartDate = calendar.timeInMillis
                                        calendar.add(Calendar.WEEK_OF_YEAR, 1)
                                        newEndDate = calendar.timeInMillis
                                    }

                                    else -> { // Month
                                        calendar.set(Calendar.DAY_OF_MONTH, 1)
                                        newStartDate = calendar.timeInMillis
                                        calendar.add(Calendar.MONTH, 1)
                                        newEndDate = calendar.timeInMillis
                                    }
                                }
                            }

                            onUpdateBudget(
                                budget.copy(
                                    name = name,
                                    isOneTime = isOneTime,
                                    timeFrame = if (isOneTime) "" else timeFrame,
                                    startDate = newStartDate,
                                    endDate = newEndDate
                                )
                            )
                            onDismiss()
                        },
                        enabled = isNameValid
                    ) {
                        Text("Update")
                    }
                }
            }
        }
    }
}
