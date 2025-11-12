package com.aubynsamuel.expensetracker.di

import com.aubynsamuel.expensetracker.data.local.BudgetDao
import com.aubynsamuel.expensetracker.data.local.BudgetItemDao
import com.aubynsamuel.expensetracker.data.local.ExpenseDao
import com.aubynsamuel.expensetracker.data.local.SharedPreferencesManager
import com.aubynsamuel.expensetracker.data.repository.BudgetRepository
import com.aubynsamuel.expensetracker.data.repository.ExpenseRepository
import com.aubynsamuel.expensetracker.data.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideExpenseRepository(
        expenseDao: ExpenseDao,
        sharedPreferencesManager: SharedPreferencesManager,
    ): ExpenseRepository {
        return ExpenseRepository(expenseDao, sharedPreferencesManager)
    }

    @Provides
    @Singleton
    fun provideBudgetRepository(
        budgetDao: BudgetDao,
        budgetItemDao: BudgetItemDao,
    ): BudgetRepository {
        return BudgetRepository(budgetDao, budgetItemDao)
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(sharedPreferencesManager: SharedPreferencesManager): SettingsRepository {
        return SettingsRepository(sharedPreferencesManager)
    }
}