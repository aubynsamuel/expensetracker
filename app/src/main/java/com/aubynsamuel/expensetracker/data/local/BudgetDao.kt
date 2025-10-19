package com.aubynsamuel.expensetracker.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aubynsamuel.expensetracker.data.model.Budget
import com.aubynsamuel.expensetracker.data.model.BudgetTotals
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {

    @Insert
    suspend fun insert(budget: Budget)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(budgets: List<Budget>)

    @Update
    suspend fun update(budget: Budget)

    @Delete
    suspend fun delete(budget: Budget)

    @Query("SELECT * FROM budgets ORDER BY startDate DESC")
    fun getAllBudgets(): Flow<List<Budget>>

    @Query(
        """
        SELECT
            SUM(CASE WHEN bi.budgetId IS NOT NULL THEN bi.price ELSE 0 END) as total,
            SUM(CASE WHEN bi.isChecked = 1 THEN bi.price ELSE 0 END) as checkedTotal
        FROM budgets b
        LEFT JOIN budget_items bi ON b.id = bi.budgetId
        WHERE b.startDate >= :startOfMonth AND b.endDate <= :endOfMonth
    """
    )
    fun getBudgetTotalsForMonth(startOfMonth: Long, endOfMonth: Long): Flow<BudgetTotals>
}