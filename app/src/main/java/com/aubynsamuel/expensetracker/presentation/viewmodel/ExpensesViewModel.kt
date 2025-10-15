package com.aubynsamuel.expensetracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aubynsamuel.expensetracker.data.local.dummyExpenses
import com.aubynsamuel.expensetracker.data.model.Expense
import com.aubynsamuel.expensetracker.data.repository.ExpenseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class ExpensesViewModel(private val expenseRepository: ExpenseRepository) : ViewModel() {

    private val _expensesList = MutableStateFlow<List<Expense>>(emptyList())
    val expensesList = _expensesList.asStateFlow()

    private val _filteredExpensesList = MutableStateFlow<List<Expense>>(emptyList())
    val filteredExpensesList = _filteredExpensesList.asStateFlow()

    private var _expenseCategories = MutableStateFlow(expenseRepository.getExpenseCategories())
    val expenseCategories: StateFlow<List<String>> = _expenseCategories

    init {
        viewModelScope.launch {
            expenseRepository.allExpenses.collect {
                _expensesList.value = it
                _filteredExpensesList.value = it
            }
        }
    }

    fun filterExpenses(filter: String) {
        when (filter) {
            "Today" -> _filteredExpensesList.value = filterExpensesByToday(expensesList.value)
            "Week" -> _filteredExpensesList.value = filterExpensesByThisWeek(expensesList.value)
            "Month" -> _filteredExpensesList.value = filterExpensesByThisMonth(expensesList.value)
            else -> _filteredExpensesList.value = expensesList.value
        }
    }

    fun insertSampleData() {
        viewModelScope.launch {
            expenseRepository.insertAll(dummyExpenses)
        }
    }

    fun addExpense(amount: String, category: String, description: String, date: Long) {
        viewModelScope.launch {
            expenseRepository.insert(
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
            expenseRepository.update(expense)
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            expenseRepository.delete(expense)
        }
    }

    fun addCategory(category: String) {
        viewModelScope.launch {
            expenseRepository.addCategory(category)
            _expenseCategories.value = expenseRepository.getExpenseCategories()
        }
    }

    fun removeCategory(category: String) {
        viewModelScope.launch {
            expenseRepository.removeCategory(category)
            _expenseCategories.value = expenseRepository.getExpenseCategories()
        }
    }

    fun filterExpensesByToday(expenses: List<Expense>): List<Expense> {
        val calendar = Calendar.getInstance()
        val startOfDay = calendar.apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
        val endOfDay = calendar.apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }.timeInMillis

        return expenses.filter { it.date in startOfDay..endOfDay }
    }

    fun filterExpensesByThisWeek(expenses: List<Expense>): List<Expense> {
        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfWeek = calendar.timeInMillis

        calendar.add(Calendar.DAY_OF_WEEK, 6)
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        val endOfWeek = calendar.timeInMillis

        return expenses.filter { it.date in startOfWeek..endOfWeek }
    }

    fun filterExpensesByThisMonth(expenses: List<Expense>): List<Expense> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfMonth = calendar.timeInMillis

        calendar.add(Calendar.MONTH, 1)
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        val endOfMonth = calendar.timeInMillis

        return expenses.filter { it.date in startOfMonth..endOfMonth }
    }
}

