package com.aubynsamuel.expensetracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.aubynsamuel.expensetracker.data.model.Expense
import com.aubynsamuel.expensetracker.data.model.defaultCategories
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ExpensesViewModel : ViewModel() {
    private val _expensesList = MutableStateFlow<List<Expense>>(emptyList())
    val expensesList: StateFlow<List<Expense>> = _expensesList


    init {
        getExpenses()
    }

    fun addToExpenses(expense: Expense) {
        // TODO: Add to expense database
    }


    fun getExpenses(limit: Int = 10) {
        _expensesList.value = listOf<Expense>(
            Expense(
                id = "12",
                name = "Hand bag",
                price = "239",
                date = 120120023L,
                category = defaultCategories[1]
            )
        )
        // TODO: Return expenses from database
    }


}

