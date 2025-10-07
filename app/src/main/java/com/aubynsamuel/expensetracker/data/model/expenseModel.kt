package com.aubynsamuel.expensetracker.data.model

data class Expense(
    val id: String,
    val name: String,
    val price: String,
    val date: Long = System.currentTimeMillis(),
    val category: String,
)

val defaultCategories = listOf("food", "clothes", "groceries", "transport", "health")