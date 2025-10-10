package com.aubynsamuel.expensetracker.presentation.screens

import HomeScreenContent
import android.util.Log
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.aubynsamuel.expensetracker.data.local.DataStoreManager
import com.aubynsamuel.expensetracker.data.local.ExpenseDatabase
import com.aubynsamuel.expensetracker.data.model.Expense
import com.aubynsamuel.expensetracker.data.repository.ExpenseRepository
import com.aubynsamuel.expensetracker.presentation.components.DrawerContent
import com.aubynsamuel.expensetracker.presentation.viewmodel.ExpensesViewModel
import com.aubynsamuel.expensetracker.presentation.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val drawerWidth = 280.dp
    val drawerWidthPx =
        with(LocalDensity.current) { drawerWidth.toPx() }
    val scope = rememberCoroutineScope()
    val translationX = remember { Animatable(0f) }
    translationX.updateBounds(0f, drawerWidthPx)
    var currentScreen by remember { mutableStateOf<Screen>(Screen.HomeScreen) }
    val decay = rememberSplineBasedDecay<Float>()
    val context = LocalContext.current
    val database = remember { ExpenseDatabase.getDatabase(context) }
    val dataStoreManager = remember { DataStoreManager(context) }
    val repository = remember { ExpenseRepository(database.expenseDao(), dataStoreManager) }
    val expensesViewModel: ExpensesViewModel = viewModel(
        factory = ViewModelFactory(repository)
    )
    val draggableState = rememberDraggableState { dragAmount ->
        scope.launch {
            translationX.snapTo(translationX.value + dragAmount)
        }
    }
    val shadowColor = MaterialTheme.colorScheme.onBackground
    val backStack = rememberNavBackStack(Screen.HomeScreen)

    fun toggleDrawer() =
        scope.launch {
            if (translationX.value == drawerWidthPx) {
                translationX.animateTo(0f)
            } else {
                translationX.animateTo(drawerWidthPx)
            }
        }

    LaunchedEffect(backStack) {
        Log.d("BackStackMonitor", backStack.toString())
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        var dragAmountPx by remember { mutableFloatStateOf(0f) }
        val drawerDraggableState = rememberDraggableState { dragAmount ->
            scope.launch {
                dragAmountPx = dragAmount
                translationX.animateTo(translationX.value + dragAmount)
            }
        }
        dragAmountPx.coerceIn(-drawerWidthPx, 0f)

        DrawerContent(
            changeScreen = { screen ->
                backStack.add(screen)
                toggleDrawer()
            },
            currentScreen = currentScreen,
            width = drawerWidth,
            modifier = Modifier
                .fillMaxHeight()
                .background(Color.Green)
                .draggable(
                    state = drawerDraggableState,
                    orientation = Orientation.Horizontal,
                    onDragStopped = { velocity ->
                        val decayX = decay.calculateTargetValue(translationX.value, velocity)
                        scope.launch {
                            val targetX = if (decayX > drawerWidthPx * 0.7) {
                                drawerWidthPx
                            } else {
                                0f
                            }
                            val canReachTargetWithDecay =
                                (decayX > targetX && targetX == drawerWidthPx) ||
                                        (decayX < targetX && targetX == 0f)
                            if (canReachTargetWithDecay) {
                                translationX.animateDecay(
                                    initialVelocity = velocity,
                                    animationSpec = decay
                                )
                            } else {
                                translationX.animateTo(targetX, initialVelocity = velocity)
                            }
                        }
                    }
                )
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(x = with(LocalDensity.current) { translationX.value.toDp() })
                .draggable(
                    state = draggableState,
                    orientation = Orientation.Horizontal,
                    onDragStopped = { velocity ->
                        val decayX = decay.calculateTargetValue(translationX.value, velocity)
                        scope.launch {
                            val targetX = if (decayX > drawerWidthPx * 0.5) {
                                drawerWidthPx
                            } else {
                                0f
                            }
                            val canReachTargetWithDecay =
                                (decayX > targetX && targetX == drawerWidthPx) ||
                                        (decayX < targetX && targetX == 0f)
                            if (canReachTargetWithDecay) {
                                translationX.animateDecay(
                                    initialVelocity = velocity,
                                    animationSpec = decay
                                )
                            } else {
                                translationX.animateTo(targetX, initialVelocity = velocity)
                            }
                        }
                    }
                )
                .graphicsLayer {
//                    this.translationX = translationX.value
                    val progress = translationX.value / drawerWidthPx
                    val scale = lerp(1f, 0.9f, progress)
                    this.scaleX = scale
                    this.scaleY = scale
                    this.shadowElevation = lerp(0f, 24f, progress)
                    this.shape = (RoundedCornerShape(translationX.value / 10))
                    this.clip = true
                    this.spotShadowColor = shadowColor
                }
        ) {

            NavDisplay(
                transitionSpec = {
//                    slideInHorizontally(initialOffsetX = { it }) togetherWith
//                            slideOutHorizontally(targetOffsetX = { -it })

                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right
                    ).togetherWith(
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Left
                        )
                    )
                },
                backStack = backStack,
                onBack = { backStack.removeLastOrNull() },
                entryProvider = entryProvider {
                    entry<Screen.HomeScreen> {
                        HomeScreenContent(
                            backStack = backStack,
                            toggleDrawer = { toggleDrawer() },
                            onAddExpense = {
                                backStack.add(Screen.AddExpenseScreen)
                            },
                            onEditExpense = { expense ->
                                backStack.add(Screen.EditExpenseScreen(expense))
                            },
                            expensesViewModel = expensesViewModel
                        )
                    }

                    entry<Screen.ExpensesScreen> {
                        ExpensesScreenContent(
                            expensesViewModel = expensesViewModel,
                            onEditExpense = { expense ->
                                backStack.add(Screen.EditExpenseScreen(expense))
                            },
                            onDeleteExpense = { expense ->
                                expensesViewModel.deleteExpense(expense)
                            },
                            toggleDrawer = { toggleDrawer() }
                        )
                    }

                    entry<Screen.AddExpenseScreen> {
                        AddExpenseScreen(
                            viewModel = expensesViewModel,
                            onAddExpense = { amount, category, description, date ->
                                expensesViewModel.addExpense(amount, category, description, date)
                                backStack.removeLastOrNull()
                            }
                        )
                    }

                    entry<Screen.EditExpenseScreen> { key ->
                        EditExpenseScreen(
                            expense = key.expense,
                            onUpdateExpense = { updatedExpense ->
                                expensesViewModel.updateExpense(updatedExpense)
                                backStack.removeLastOrNull()
                            }
                        )
                    }
                }
            )
        }
    }
}


sealed class Screen : NavKey {
    @Serializable
    object HomeScreen : Screen()

    @Serializable
    object ExpensesScreen : Screen()

    @Serializable
    object AddExpenseScreen : Screen()

    @Serializable
    data class EditExpenseScreen(val expense: Expense) : Screen()
}