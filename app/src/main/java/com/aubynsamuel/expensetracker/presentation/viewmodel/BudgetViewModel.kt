package com.aubynsamuel.expensetracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aubynsamuel.expensetracker.data.model.Budget
import com.aubynsamuel.expensetracker.data.model.BudgetItem
import com.aubynsamuel.expensetracker.data.repository.BudgetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BudgetViewModel(private val budgetRepository: BudgetRepository) : ViewModel() {

    private val _budgetsList = MutableStateFlow<List<Budget>>(emptyList())
    val budgetsList = _budgetsList.asStateFlow()

    private val _budgetItems = MutableStateFlow<List<BudgetItem>>(emptyList())
    val budgetItems = _budgetItems.asStateFlow()

    init {
        viewModelScope.launch {
            budgetRepository.allBudgets.collect {
                _budgetsList.value = it
            }
        }
    }

    fun getBudgetItems(budgetId: Int) {
        viewModelScope.launch {
            budgetRepository.getBudgetItems(budgetId).collect {
                _budgetItems.value = it
            }
        }
    }

    fun addBudget(name: String) {
        viewModelScope.launch {
            budgetRepository.insert(Budget(name = name))
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

    fun addBudgetItem(budgetItem: BudgetItem) {
        viewModelScope.launch {
            budgetRepository.insertBudgetItem(budgetItem)
        }
    }

    fun updateBudgetItem(budgetItem: BudgetItem) {
        viewModelScope.launch {
            budgetRepository.updateBudgetItem(budgetItem)
        }
    }

    fun deleteBudgetItem(budgetItem: BudgetItem) {
        viewModelScope.launch {
            budgetRepository.deleteBudgetItem(budgetItem)
        }
    }
}