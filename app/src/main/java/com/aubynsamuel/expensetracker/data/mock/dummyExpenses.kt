package com.aubynsamuel.expensetracker.data.mock

import com.aubynsamuel.expensetracker.data.model.Expense
import java.util.concurrent.TimeUnit

val dummyExpenses = listOf(
    // 1-5: Food 🍔
    Expense(
        id = 0,
        title = "Groceries (Week 1)",
        amount = 75.50,
        category = "Food",
        date = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(8)
    ),
    Expense(
        id = 0,
        title = "Lunch at Cafe",
        amount = 15.20,
        category = "Food",
        date = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7)
    ),
    Expense(
        id = 0,
        title = "Dinner with Friends",
        amount = 45.00,
        category = "Food",
        date = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(4)
    ),
    Expense(
        id = 0,
        title = "Coffee & Pastry",
        amount = 6.75,
        category = "Food",
        date = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(2)
    ),
    Expense(
        id = 0,
        title = "Takeout Pizza",
        amount = 22.99,
        category = "Food",
        date = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1)
    ),

    // 6-10: Bills & Utilities 💡
    Expense(
        id = 0,
        title = "Monthly Rent",
        amount = 1200.00,
        category = "Bills",
        date = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(8)
    ),
    Expense(
        id = 0,
        title = "Electricity Bill",
        amount = 85.30,
        category = "Utilities",
        date = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(5)
    ),
    Expense(
        id = 0,
        title = "Internet Service",
        amount = 60.00,
        category = "Bills",
        date = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1)
    ),
    Expense(
        id = 0,
        title = "Mobile Phone Payment",
        amount = 45.50,
        category = "Bills",
        date = System.currentTimeMillis()
    ),
    Expense(
        id = 0,
        title = "Water & Sewage",
        amount = 32.10,
        category = "Utilities",
        date = System.currentTimeMillis()
    ),

    // 11-15: Transportation 🚗
    Expense(
        id = 0,
        title = "Gas Fill-up",
        amount = 55.70,
        category = "Transportation",
        date = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(6)
    ),
    Expense(
        id = 0,
        title = "Bus Pass (Monthly)",
        amount = 70.00,
        category = "Transportation",
        date = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(8)
    ),
    Expense(
        id = 0,
        title = "Uber Ride Home",
        amount = 18.50,
        category = "Transportation",
        date = System.currentTimeMillis()
    ),
    Expense(
        id = 0,
        title = "Car Wash",
        amount = 12.00,
        category = "Transportation",
        date = System.currentTimeMillis()
    ),
    Expense(
        id = 0,
        title = "Parking Fee",
        amount = 8.00,
        category = "Transportation",
        date = System.currentTimeMillis()
    ),

    // 16-20: Entertainment 🎉
    Expense(
        id = 0,
        title = "Movie Tickets (2)",
        amount = 30.00,
        category = "Entertainment",
        date = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(3)
    ),
    Expense(
        id = 0,
        title = "Netflix Subscription",
        amount = 15.49,
        category = "Entertainment",
        date = System.currentTimeMillis()
    ),
    Expense(
        id = 0,
        title = "Concert Ticket",
        amount = 85.00,
        category = "Entertainment",
        date = System.currentTimeMillis()
    ),
    Expense(
        id = 0,
        title = "Video Game Purchase",
        amount = 59.99,
        category = "Entertainment",
        date = System.currentTimeMillis()
    ),
    Expense(
        id = 0,
        title = "Bookstore Visit",
        amount = 28.50,
        category = "Entertainment",
        date = System.currentTimeMillis()
    ),

    // 21-25: Shopping 🛍️
    Expense(
        id = 0,
        title = "New Shoes",
        amount = 95.00,
        category = "Shopping",
        date = System.currentTimeMillis()
    ),
    Expense(
        id = 0,
        title = "T-shirt Sale",
        amount = 25.00,
        category = "Shopping",
        date = System.currentTimeMillis()
    ),
    Expense(
        id = 0,
        title = "New Laptop Bag",
        amount = 40.00,
        category = "Shopping",
        date = System.currentTimeMillis()
    ),
    Expense(
        id = 0,
        title = "Haircut",
        amount = 35.00,
        category = "Personal",
        date = System.currentTimeMillis()
    ),
    Expense(
        id = 0,
        title = "Gift for Birthday",
        amount = 30.00,
        category = "Shopping",
        date = System.currentTimeMillis()
    ),

    // 26-30: Miscellaneous/Health 🩺
    Expense(
        id = 0,
        title = "Gym Membership",
        amount = 49.99,
        category = "Health",
        date = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(8)
    ),
    Expense(
        id = 0,
        title = "Doctor Co-pay",
        amount = 20.00,
        category = "Health",
        date = System.currentTimeMillis()
    ),
    Expense(
        id = 0,
        title = "Pet Food Supply",
        amount = 38.00,
        category = "Pet",
        date = System.currentTimeMillis()
    ),
    Expense(
        id = 0,
        title = "Dry Cleaning",
        amount = 15.50,
        category = "Miscellaneous",
        date = System.currentTimeMillis()
    ),
    Expense(
        id = 0,
        title = "Amazon Order (Gadget)",
        amount = 125.00,
        category = "Miscellaneous",
        date = System.currentTimeMillis()
    )
)