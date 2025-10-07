package com.aubynsamuel.expensetracker.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FoodBank
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aubynsamuel.expensetracker.presentation.viewmodel.ExpensesViewModel
import com.aubynsamuel.expensetracker.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomeScreenContent(toggleDrawer: () -> Unit) {
    val expensesViewModel = viewModel { ExpensesViewModel() }
    val expensesList by expensesViewModel.expensesList.collectAsState()
    var selectedFilter by remember { mutableStateOf("expense") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Dashboard",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            navigationIcon = {
                IconButton(onClick = toggleDrawer) {
                    Icon(Icons.Default.Menu, contentDescription = "")
                }
            }
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            ElevatedFilterChip(
                selected = selectedFilter == "expense",
                onClick = { selectedFilter = "expense" },
                label = { Text("Expenses") }
            )
            Spacer(modifier = Modifier.width(10.dp))
            ElevatedFilterChip(
                selected = selectedFilter == "budget",
                onClick = { selectedFilter = "budget" },
                label = { Text("Budgets") }
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        RoundedCornerShape(20.dp)
                    )
                    .padding(20.dp),
            ) {
                Text("March Spending", style = Typography.bodySmall)
                Spacer(modifier = Modifier.height(10.dp))
                Text("$789.00", style = Typography.headlineSmallEmphasized)
                Spacer(modifier = Modifier.height(10.dp))
                Box {
                    Row(
                        modifier = Modifier.fillMaxWidth(0.7f),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Travel", style = Typography.bodyMedium)
                        Text("$129", style = Typography.bodyMedium)
                    }
                    LinearProgressIndicator(progress = { 0.5f })
                }
                Spacer(modifier = Modifier.height(10.dp))
                Box {
                    Row(
                        modifier = Modifier.fillMaxWidth(0.7f),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Food", style = Typography.bodyMedium)
                        Text("$129", style = Typography.bodyMedium)
                    }
                    LinearProgressIndicator(progress = { 0.5f })
                }
            }
        }


        Text(
            "Top Spending",
            style = Typography.titleMediumEmphasized,
            modifier = Modifier.padding(10.dp)
        )
        LazyRow {
            items(10, key = { it }) { spending ->
                Column(modifier = Modifier.padding(20.dp)) {
                    IconButton(
                        onClick = {},
                        modifier = Modifier
                            .background(
                                color =
                                    MaterialTheme.colorScheme.primaryContainer,
                                shape = MaterialShapes.Square.toShape()
                            )
                    ) {
                        Icon(Icons.Default.FoodBank, contentDescription = "")
                    }

                    Text("Num$spending")

                }
            }
        }
        Text(
            "Monthly Budget",
            style = Typography.titleMediumEmphasized,
            modifier = Modifier.padding(10.dp)
        )


    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewHomeScreen() {
    MaterialTheme {
        HomeScreenContent(toggleDrawer = {})
    }
}