import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.aubynsamuel.expensetracker.MainActivity
import com.aubynsamuel.expensetracker.data.model.Expense
import com.aubynsamuel.expensetracker.presentation.components.AddExpenseDialog
import com.aubynsamuel.expensetracker.presentation.components.EditExpenseDialog
import com.aubynsamuel.expensetracker.presentation.components.ExpenseItem
import com.aubynsamuel.expensetracker.presentation.navigation.DrawerState
import com.aubynsamuel.expensetracker.presentation.navigation.Screen
import com.aubynsamuel.expensetracker.presentation.utils.navigate
import com.aubynsamuel.expensetracker.presentation.utils.showToast
import com.aubynsamuel.expensetracker.presentation.viewmodel.ExpensesViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    toggleDrawer: () -> Unit,
    expensesViewModel: ExpensesViewModel,
    backStack: NavBackStack<NavKey>,
    drawerState: DrawerState,
) {
    val expensesList by expensesViewModel.expensesList.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var expenseToDelete by remember { mutableStateOf<Expense?>(null) }
    var showAddExpenseDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var backButtonPressed by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var showEditExpenseDialog by remember { mutableStateOf(false) }
    var expenseToEdit by remember { mutableStateOf<Expense?>(null) }
//    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Expense") },
            text = { Text("Are you sure you want to delete this expense?") },
            confirmButton = {
                Button(
                    onClick = {
                        expenseToDelete?.let { expensesViewModel.deleteExpense(it) }
                        showDeleteDialog = false
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    if (showEditExpenseDialog) {
        expenseToEdit?.let {
            EditExpenseDialog(
                expense = it,
                onUpdateExpense = { expense -> expensesViewModel.updateExpense(expense) },
                onDismiss = { showEditExpenseDialog = false }
            )
        }
    }

    if (showAddExpenseDialog) {
        AddExpenseDialog(
            viewModel = expensesViewModel,
            onDismiss = { showAddExpenseDialog = false }
        )
    }

    BackHandler(enabled = true, onBack = {
        if (drawerState == DrawerState.Opened) {
            toggleDrawer()
        } else {
            if (backButtonPressed) {
                backStack.clear()
                (context as? MainActivity)?.finish()
            } else {
                backButtonPressed = true
                showToast(context, "Press again to exit")
                coroutineScope.launch {
                    delay(2000)
                    backButtonPressed = false
                }
            }
        }
    })

    Scaffold(
//        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text("Home") },
                navigationIcon = {
                    IconButton(onClick = toggleDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
//                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddExpenseDialog = true },
                modifier = Modifier.offset(x = (-10).dp, y = (-20).dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Expense")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Apply padding from the Scaffold
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(2.dp))
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        val totalAmount = expensesList.sumOf { it.amount }
                        Text(
                            text = "Total Expense",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = String.format(Locale.getDefault(), "$%.2f", totalAmount),
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            item {
                SpendingChart(expenses = expensesList)
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Recent Transactions",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = "View All",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable(onClick = { backStack.navigate(Screen.ExpensesScreen) })
                    )
                }
            }

            items(expensesList.take(10), key = { it.id }) { expense ->
                ExpenseItem(
                    expense = expense,
                    onEdit = {
                        expenseToEdit = it
                        showEditExpenseDialog = true
                    },
                    onDelete = {
                        expenseToDelete = it
                        showDeleteDialog = true
                    }
                )
            }
        }
    }
}