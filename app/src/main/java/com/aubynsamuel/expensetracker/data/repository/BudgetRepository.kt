package com.aubynsamuel.expensetracker.data.repository

import com.aubynsamuel.expensetracker.data.local.BudgetDao
import com.aubynsamuel.expensetracker.data.local.BudgetItemDao
import com.aubynsamuel.expensetracker.data.model.Budget
import com.aubynsamuel.expensetracker.data.model.BudgetItem
import kotlinx.coroutines.flow.Flow

class BudgetRepository(
    private val budgetDao: BudgetDao,
    private val budgetItemDao: BudgetItemDao,
) {
    val allBudgets: Flow<List<Budget>> = budgetDao.getAllBudgets()

    suspend fun insert(budget: Budget) {
        budgetDao.insert(budget)
    }

    suspend fun update(budget: Budget) {
        budgetDao.update(budget)
    }

    suspend fun delete(budget: Budget) {
        budgetDao.delete(budget)
    }

    fun getBudgetItems(budgetId: Int): Flow<List<BudgetItem>> {
        return budgetItemDao.getBudgetItems(budgetId)
    }

    suspend fun insertBudgetItem(budgetItem: BudgetItem) {
        budgetItemDao.insert(budgetItem)
    }

    suspend fun updateBudgetItem(budgetItem: BudgetItem) {
        budgetItemDao.update(budgetItem)
    }

    suspend fun deleteBudgetItem(budgetItem: BudgetItem) {
        budgetItemDao.delete(budgetItem)
    }
}