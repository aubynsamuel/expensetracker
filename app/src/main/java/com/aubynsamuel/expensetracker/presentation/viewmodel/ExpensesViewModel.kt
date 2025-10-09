package com.aubynsamuel.expensetracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aubynsamuel.expensetracker.data.model.Expense
import com.aubynsamuel.expensetracker.data.repository.ExpenseRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ExpensesViewModel(private val repository: ExpenseRepository) : ViewModel() {

    val expensesList: StateFlow<List<Expense>> = repository.allExpenses.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun addExpense(amount: String, category: String, description: String, date: String) {
        viewModelScope.launch {
            repository.insert(
                Expense(
                    title = description,
                    amount = amount.toDouble(),
                    category = category,
                    date = date
                )
            )
        }
    }

    fun updateExpense(expense: Expense) {
        viewModelScope.launch {
            repository.update(expense)
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            repository.delete(expense)
        }
    }

    fun filterExpensesByToday(expenses: List<Expense>): List<Expense> {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val today = sdf.format(Date())
        return expenses.filter { it.date == today }
    }

    fun filterExpensesByThisWeek(expenses: List<Expense>): List<Expense> {
        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        val startOfWeek = calendar.time
        calendar.add(Calendar.DAY_OF_WEEK, 6)
        val endOfWeek = calendar.time

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return expenses.filter { expense ->
            val expenseDate = sdf.parse(expense.date)
            expenseDate != null && !expenseDate.before(startOfWeek) && !expenseDate.after(endOfWeek)
        }
    }

    fun filterExpensesByThisMonth(expenses: List<Expense>): List<Expense> {
        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentYear = calendar.get(Calendar.YEAR)

        return expenses.filter { expense ->
            val expenseCalendar = Calendar.getInstance()
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val expenseDate = sdf.parse(expense.date)
            if (expenseDate != null) {
                expenseCalendar.time = expenseDate
                expenseCalendar.get(Calendar.MONTH) == currentMonth && expenseCalendar.get(Calendar.YEAR) == currentYear
            } else {
                false
            }
        }
    }
}

