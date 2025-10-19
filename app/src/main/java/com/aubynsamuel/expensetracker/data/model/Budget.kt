package com.aubynsamuel.expensetracker.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "budgets")
data class Budget(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    @ColumnInfo(defaultValue = "0")
    val completed: Boolean = false,
    @ColumnInfo(defaultValue = "")
    val timeFrame: String,
    @ColumnInfo(defaultValue = "0")
    val startDate: Long,
    @ColumnInfo(defaultValue = "0")
    val endDate: Long,
    @ColumnInfo(defaultValue = "0")
    val isOneTime: Boolean = false,
)