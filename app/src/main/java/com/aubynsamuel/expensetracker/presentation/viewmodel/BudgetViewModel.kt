package com.aubynsamuel.expensetracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aubynsamuel.expensetracker.data.model.Budget
import com.aubynsamuel.expensetracker.data.repository.BudgetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BudgetViewModel(private val budgetRepository: BudgetRepository) : ViewModel() {

    private val _budgetsList = MutableStateFlow<List<Budget>>(emptyList())
    val budgetsList = _budgetsList.asStateFlow()

    init {
        viewModelScope.launch {
            budgetRepository.allBudgets.collect {
                _budgetsList.value = it
            }
        }
    }

    fun addBudget(amount: String, category: String, description: String, date: Long) {
        viewModelScope.launch {
            budgetRepository.insert(
                Budget(
                    title = description,
                    amount = amount.toDouble(),
                    category = category,
                    date = date
                )
            )
        }
    }

    fun updateBudget(budget: Budget) {
        viewModelScope.launch {
            budgetRepository.update(budget)
        }
    }

    fun deleteBudget(budget: Budget) {
        viewModelScope.launch {
            budgetRepository.delete(budget)
        }
    }
}