package com.aubynsamuel.expensetracker.data.mock

import com.aubynsamuel.expensetracker.data.model.BudgetItem

val dummyBudgetItems = listOf(
    // Budget 1: Groceries
    BudgetItem(1, 1, "Fruits & Vegetables", 75.20, true),
    BudgetItem(2, 1, "Cereals & Snacks", 42.10, false),
    BudgetItem(3, 1, "Beverages", 60.00, true),
    BudgetItem(4, 1, "Household Items", 45.00, true),
    BudgetItem(5, 1, "Frozen Foods", 38.50, false),

    // Budget 2: Weekend Trip
    BudgetItem(6, 2, "Hotel", 200.00, true),
    BudgetItem(7, 2, "Transport", 100.00, true),
    BudgetItem(8, 2, "Food & Drinks", 80.00, true),
    BudgetItem(9, 2, "Souvenirs", 50.00, false),
    BudgetItem(10, 2, "Entertainment", 60.00, true),

    // Budget 3: Bills & Subscriptions
    BudgetItem(11, 3, "Electricity Bill", 120.00, false),
    BudgetItem(12, 3, "Internet", 60.00, true),
    BudgetItem(13, 3, "Netflix", 45.00, false),
    BudgetItem(14, 3, "Phone Bill", 55.00, true),
    BudgetItem(15, 3, "Spotify", 25.00, true),

    // Budget 4: Home Renovation
    BudgetItem(16, 4, "Paint & Brushes", 130.00, false),
    BudgetItem(17, 4, "Tiles", 300.00, false),
    BudgetItem(18, 4, "Furniture", 600.00, false),
    BudgetItem(19, 4, "Lighting Fixtures", 150.00, false),
    BudgetItem(20, 4, "Labor", 400.00, false),

    // Budget 5: Car Maintenance
    BudgetItem(21, 5, "Oil Change", 80.00, true),
    BudgetItem(22, 5, "Tire Replacement", 200.00, true),
    BudgetItem(23, 5, "Brake Pads", 120.00, true),
    BudgetItem(24, 5, "Car Wash", 25.00, true),
    BudgetItem(25, 5, "Battery Replacement", 180.00, true),

    // Budget 6: Health & Fitness
    BudgetItem(26, 6, "Gym Membership", 60.00, true),
    BudgetItem(27, 6, "Protein Supplements", 80.00, false),
    BudgetItem(28, 6, "Running Shoes", 100.00, false),
    BudgetItem(29, 6, "Yoga Classes", 50.00, true),
    BudgetItem(30, 6, "Massage Therapy", 70.00, false),

    // Budget 7: Christmas Gifts
    BudgetItem(31, 7, "Gift for Mom", 120.00, false),
    BudgetItem(32, 7, "Gift for Dad", 110.00, false),
    BudgetItem(33, 7, "Gift for Sibling", 80.00, false),
    BudgetItem(34, 7, "Gift Wrapping", 20.00, false),
    BudgetItem(35, 7, "Decorations", 60.00, true),

    // Budget 8: Personal Development
    BudgetItem(36, 8, "Online Course", 75.00, true),
    BudgetItem(37, 8, "Books", 50.00, true),
    BudgetItem(38, 8, "Conference Ticket", 150.00, false),
    BudgetItem(39, 8, "Software Subscription", 30.00, true),
    BudgetItem(40, 8, "Mentorship Program", 200.00, false),

    // Budget 9: Office Lunches
    BudgetItem(41, 9, "Monday Lunch", 10.00, true),
    BudgetItem(42, 9, "Tuesday Lunch", 12.50, true),
    BudgetItem(43, 9, "Wednesday Lunch", 9.80, true),
    BudgetItem(44, 9, "Thursday Lunch", 11.20, true),
    BudgetItem(45, 9, "Friday Lunch", 13.00, true),

    // Budget 10: Pet Expenses
    BudgetItem(46, 10, "Pet Food", 60.00, true),
    BudgetItem(47, 10, "Vet Checkup", 90.00, false),
    BudgetItem(48, 10, "Grooming", 45.00, true),
    BudgetItem(49, 10, "Toys", 25.00, false),
    BudgetItem(50, 10, "Supplements", 30.00, false)
)
