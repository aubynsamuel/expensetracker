package com.aubynsamuel.expensetracker.data.repository

import com.aubynsamuel.expensetracker.data.local.DataStoreManager
import com.aubynsamuel.expensetracker.data.local.ExpenseDao
import com.aubynsamuel.expensetracker.data.model.Expense
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExpenseRepository(
    private val expenseDao: ExpenseDao,
    val dataStoreManager: DataStoreManager,
) {

    val allExpenses: Flow<List<Expense>> = expenseDao.getAllExpenses()

    val categories: Flow<List<String>> = dataStoreManager.categories.map { it.toList() }

    suspend fun addCategory(category: String) {
        dataStoreManager.addCategory(category)
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