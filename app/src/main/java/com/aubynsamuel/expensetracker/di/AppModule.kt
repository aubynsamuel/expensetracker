package com.aubynsamuel.expensetracker.di

import android.content.Context
import androidx.room.Room
import com.aubynsamuel.expensetracker.data.local.BudgetDao
import com.aubynsamuel.expensetracker.data.local.BudgetItemDao
import com.aubynsamuel.expensetracker.data.local.ExpenseDao
import com.aubynsamuel.expensetracker.data.local.ExpenseDatabase
import com.aubynsamuel.expensetracker.data.local.SharedPreferencesManager
import com.aubynsamuel.expensetracker.data.repository.BudgetRepository
import com.aubynsamuel.expensetracker.data.repository.ExpenseRepository
import com.aubynsamuel.expensetracker.data.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideExpenseDatabase(@ApplicationContext context: Context): ExpenseDatabase {
        return Room.databaseBuilder(
            context,
            ExpenseDatabase::class.java,
            "expense_database"
        ).fallbackToDestructiveMigration(false).build()
    }

    @Provides
    fun provideExpenseDao(database: ExpenseDatabase): ExpenseDao = database.expenseDao()

    @Provides
    fun provideBudgetDao(database: ExpenseDatabase): BudgetDao = database.budgetDao()

    @Provides
    fun provideBudgetItemDao(database: ExpenseDatabase): BudgetItemDao = database.budgetItemDao()

    @Provides
    @Singleton
    fun provideSharedPreferencesManager(@ApplicationContext context: Context): SharedPreferencesManager {
        return SharedPreferencesManager(context)
    }

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