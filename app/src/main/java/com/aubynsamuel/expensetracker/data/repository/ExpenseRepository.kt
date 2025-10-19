package com.aubynsamuel.expensetracker.data.repository

import com.aubynsamuel.expensetracker.data.local.ExpenseDao
import com.aubynsamuel.expensetracker.data.local.SharedPreferencesManager
import com.aubynsamuel.expensetracker.data.model.Expense
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExpenseRepository @Inject constructor(
    private val expenseDao: ExpenseDao,
    private val sharedPreferencesManager: SharedPreferencesManager,
) {
    val allExpenses: Flow<List<Expense>> = expenseDao.getAllExpenses()

    fun getExpenseCategories(): List<String> {
        return sharedPreferencesManager.getCategories().toList()
    }

    fun addCategory(category: String) {
        sharedPreferencesManager.addCategory(category)
    }

    fun removeCategory(category: String) {
        sharedPreferencesManager.removeCategory(category)
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