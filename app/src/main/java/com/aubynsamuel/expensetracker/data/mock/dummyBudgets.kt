package com.aubynsamuel.expensetracker.data.mock

import com.aubynsamuel.expensetracker.data.model.Budget

val dummyBudgets = listOf(
    Budget(1, "Groceries - October", false, "Monthly", 1727740800000, 1730332800000, false),
    Budget(2, "Weekend Trip", true, "One-time", 1728086400000, 1728345600000, true),
    Budget(3, "Bills & Subscriptions", false, "Monthly", 1727740800000, 1730332800000, false),
    Budget(4, "Home Renovation", false, "One-time", 1727577600000, 1732944000000, true),
    Budget(5, "Car Maintenance", true, "Quarterly", 1725148800000, 1735603200000, false),
    Budget(6, "Health & Fitness", false, "Monthly", 1727740800000, 1730332800000, false),
    Budget(7, "Christmas Gifts", false, "Seasonal", 1732924800000, 1735603200000, true),
    Budget(8, "Personal Development", false, "Monthly", 1727740800000, 1730332800000, false),
    Budget(9, "Office Lunches", true, "Weekly", 1727740800000, 1728345600000, false),
    Budget(10, "Pet Expenses", false, "Monthly", 1727740800000, 1730332800000, false)
)
