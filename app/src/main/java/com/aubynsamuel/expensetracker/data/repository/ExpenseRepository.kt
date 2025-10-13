package com.aubynsamuel.expensetracker.data.repository

import com.aubynsamuel.expensetracker.data.local.ExpenseDao
import com.aubynsamuel.expensetracker.data.local.SharedPreferencesManager
import com.aubynsamuel.expensetracker.data.model.Expense
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExpenseRepository(
    private val expenseDao: ExpenseDao,
    val sharedPreferencesManager: SharedPreferencesManager,
) {
    val allExpenses: Flow<List<Expense>> = expenseDao.getAllExpenses()
    val expenseCategories: Flow<List<String>> =
        sharedPreferencesManager.categories.map { it.toList() }

    fun addCategory(category: String) {
        sharedPreferencesManager.addCategory(category)
    }

    suspend fun insertAll(expenses: List<Expense>) {
        expenseDao.insertAll(expenses)
    }

    suspend fun insert(expense: Expense) {
        expenseDao.insert(expense)
    }

    suspend fun update(expense: Expense) {
        expenseDao.update(expense)
    }

    suspend fun delete(expense: Expense) {
        expenseDao.delete(expense)
    }
}