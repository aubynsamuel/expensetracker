package com.aubynsamuel.expensetracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aubynsamuel.expensetracker.data.mock.dummyBudgetItems
import com.aubynsamuel.expensetracker.data.mock.dummyBudgets
import com.aubynsamuel.expensetracker.data.model.Budget
import com.aubynsamuel.expensetracker.data.model.BudgetItem
import com.aubynsamuel.expensetracker.data.model.BudgetTotals
import com.aubynsamuel.expensetracker.data.repository.BudgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val budgetRepository: BudgetRepository,
) : ViewModel() {

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
//        insertSampleBudgetsData()
//        insertSampleBudgetItemsData()
    }

    fun getBudgetItems(budgetId: Int) {
        viewModelScope.launch {
            budgetRepository.getBudgetItems(budgetId).collect {
                _budgetItems.value = it
            }
        }
    }

    fun getBudgetTotals(budgetId: Int): Flow<BudgetTotals> {
        return budgetRepository.getBudgetItems(budgetId).map { items ->
            val total = items.sumOf { it.price }
            val checkedTotal = items.filter { it.isChecked }.sumOf { it.price }
            BudgetTotals(total, checkedTotal)
        }
    }

    fun getBudgetTotalsForCurrentMonth(): Flow<BudgetTotals> {
        val calendar = Calendar.getInstance()
        val startOfMonth = calendar.apply { set(Calendar.DAY_OF_MONTH, 1) }.timeInMillis
        val endOfMonth = calendar.apply {
            add(Calendar.MONTH, 1); set(
            Calendar.DAY_OF_MONTH,
            1
        ); add(Calendar.DATE, -1)
        }.timeInMillis
        return budgetRepository.getBudgetTotalsForMonth(startOfMonth, endOfMonth)
    }

    fun addBudget(
        name: String,
        isOneTime: Boolean,
        timeFrame: String,
        startDate: Long,
        endDate: Long,
    ) {
        viewModelScope.launch {
            budgetRepository.insert(
                Budget(
                    name = name,
                    isOneTime = isOneTime,
                    timeFrame = timeFrame,
                    startDate = startDate,
                    endDate = endDate
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

    @Suppress("unused")
    fun insertSampleBudgetsData() {
        viewModelScope.launch {
            budgetRepository.insertAllBudgets(dummyBudgets)
        }
    }

    @Suppress("unused")
    fun insertSampleBudgetItemsData() {
        viewModelScope.launch {
            budgetRepository.insertAllBudgetItems(dummyBudgetItems)
        }
    }
}