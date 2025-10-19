package com.aubynsamuel.expensetracker.data.local

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.aubynsamuel.expensetracker.data.model.Budget
import com.aubynsamuel.expensetracker.data.model.BudgetItem
import com.aubynsamuel.expensetracker.data.model.Expense

@Database(
    entities = [Expense::class, Budget::class, BudgetItem::class],
    version = 5,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 4, to = 5)
    ]
)
abstract class ExpenseDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao
    abstract fun budgetDao(): BudgetDao
    abstract fun budgetItemDao(): BudgetItemDao

    companion object {
        @Volatile
        private var INSTANCE: ExpenseDatabase? = null

        fun getDatabase(context: Context): ExpenseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExpenseDatabase::class.java,
                    "expense_database"
                ).addMigrations(MIGRATION_2_3, MIGRATION_3_4).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // 1. Create new "budgets_temp" table matching new schema
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `budgets_temp` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `name` TEXT NOT NULL
            )
        """.trimIndent()
        )

        // 2. Copy compatible data (map old "title" → new "name")
        db.execSQL(
            """
            INSERT INTO budgets_temp (id, name)
            SELECT id, title FROM budgets
        """.trimIndent()
        )

        // 3. Remove the old "budgets" table
        db.execSQL("DROP TABLE budgets")

        // 4. Rename the new table to "budgets"
        db.execSQL("ALTER TABLE budgets_temp RENAME TO budgets")

        // 5. Create the new "budget_items" table
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `budget_items` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `budgetId` INTEGER NOT NULL,
                `name` TEXT NOT NULL,
                `price` REAL NOT NULL,
                `isChecked` INTEGER NOT NULL DEFAULT 0,
                FOREIGN KEY(`budgetId`) REFERENCES `budgets`(`id`) ON DELETE CASCADE
            )
        """.trimIndent()
        )
    }
}


val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `budgets` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `title` TEXT NOT NULL,
                `amount` REAL NOT NULL,
                `category` TEXT NOT NULL,
                `date` INTEGER NOT NULL
            )
        """.trimIndent()
        )
    }
}
