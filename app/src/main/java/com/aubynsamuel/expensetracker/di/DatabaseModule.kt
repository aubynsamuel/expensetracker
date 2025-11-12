package com.aubynsamuel.expensetracker.di

import android.content.Context
import com.aubynsamuel.expensetracker.data.local.BudgetDao
import com.aubynsamuel.expensetracker.data.local.BudgetItemDao
import com.aubynsamuel.expensetracker.data.local.ExpenseDao
import com.aubynsamuel.expensetracker.data.local.ExpenseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideExpenseDatabase(@ApplicationContext context: Context): ExpenseDatabase {
        return ExpenseDatabase.getDatabase(context)
    }

    @Provides
    fun provideExpenseDao(database: ExpenseDatabase): ExpenseDao = database.expenseDao()

    @Provides
    fun provideBudgetDao(database: ExpenseDatabase): BudgetDao = database.budgetDao()

    @Provides
    fun provideBudgetItemDao(database: ExpenseDatabase): BudgetItemDao = database.budgetItemDao()
}