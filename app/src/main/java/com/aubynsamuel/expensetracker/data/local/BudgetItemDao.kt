package com.aubynsamuel.expensetracker.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.aubynsamuel.expensetracker.data.model.BudgetItem
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetItemDao {

    @Insert
    suspend fun insert(budgetItem: BudgetItem)

    @Update
    suspend fun update(budgetItem: BudgetItem)

    @Delete
    suspend fun delete(budgetItem: BudgetItem)

    @Query("SELECT * FROM budget_items WHERE budgetId = :budgetId")
    fun getBudgetItems(budgetId: Int): Flow<List<BudgetItem>>
}