package com.aubynsamuel.expensetracker.presentation.navigation

import HomeScreenContent
import android.util.Log
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.animation.scaleOut
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
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.motionScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.aubynsamuel.expensetracker.presentation.components.DrawerContent
import com.aubynsamuel.expensetracker.presentation.screens.ExpensesScreen
import com.aubynsamuel.expensetracker.presentation.screens.SettingsScreen
import com.aubynsamuel.expensetracker.presentation.utils.navigate
import com.aubynsamuel.expensetracker.presentation.viewmodel.ExpensesViewModel
import com.aubynsamuel.expensetracker.presentation.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun Navigation(
    settingsViewModel: SettingsViewModel,
    expensesViewModel: ExpensesViewModel,
) {
    val density = LocalDensity.current
    val drawerWidth = 280.dp
    val drawerWidthPx = with(density) { drawerWidth.toPx() }
    val scope = rememberCoroutineScope()
    val translationX = remember { Animatable(0f) }
    translationX.updateBounds(0f, drawerWidthPx)
    val decay = rememberSplineBasedDecay<Float>()

    val draggableState = rememberDraggableState { dragAmount ->
        scope.launch {
            translationX.snapTo(translationX.value + dragAmount)
        }
    }

    val shadowColor = MaterialTheme.colorScheme.onBackground
    val backStack = rememberNavBackStack(Screen.HomeScreen)
    val motionScheme = motionScheme
    var drawerState by remember {
        mutableStateOf(DrawerState.Closed)
    }

    fun toggleDrawer() =
        scope.launch {
            if (translationX.value == drawerWidthPx) {
                translationX.animateTo(0f)
                drawerState = DrawerState.Closed
            } else {
                translationX.animateTo(drawerWidthPx)
                drawerState = DrawerState.Opened
            }
        }

    LaunchedEffect(drawerState.name) {
        Log.d("BackStackMonitor", drawerState.name)
    }

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
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
                backStack.navigate(screen)
                toggleDrawer()
            },
            currentScreen = backStack.last(),
            width = drawerWidth,
            modifier = Modifier
                .fillMaxHeight()
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
                            drawerState = if (targetX == drawerWidthPx) {
                                DrawerState.Opened
                            } else {
                                DrawerState.Closed
                            }
                        }
                    }
                )
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(x = with(density) { translationX.value.toDp() })
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
                            drawerState = if (targetX == drawerWidthPx) {
                                DrawerState.Opened
                            } else {
                                DrawerState.Closed
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
                backStack = backStack,
                onBack = { backStack.removeLastOrNull() },
                transitionSpec = {
                    ContentTransform(
                        fadeIn(motionScheme.defaultEffectsSpec()),
                        fadeOut(motionScheme.defaultEffectsSpec())
                    )
                },
                popTransitionSpec = {
                    ContentTransform(
                        fadeIn(motionScheme.defaultEffectsSpec()),
                        fadeOut(motionScheme.defaultEffectsSpec())
                    )
                },
                predictivePopTransitionSpec = {
                    ContentTransform(
                        fadeIn(motionScheme.defaultEffectsSpec()),
                        fadeOut(motionScheme.defaultEffectsSpec()) +
                                scaleOut(targetScale = 0.7f),
                    )
                },
                entryProvider = entryProvider {
                    entry<Screen.HomeScreen> {
                        HomeScreenContent(
                            toggleDrawer = { toggleDrawer() },
                            expensesViewModel = expensesViewModel,
                            backStack = backStack,
                            drawerState = drawerState
                        )
                    }

                    entry<Screen.ExpensesScreen> {
                        ExpensesScreen(
                            expensesViewModel = expensesViewModel,
                            toggleDrawer = { toggleDrawer() },
                            drawerState = drawerState
                        )
                    }

                    entry<Screen.SettingsScreen> {
                        SettingsScreen(
                            settingsViewModel = settingsViewModel,
                            toggleDrawer = { toggleDrawer() },
                            drawerState = drawerState
                        )
                    }
                }
            )
        }
    }
}