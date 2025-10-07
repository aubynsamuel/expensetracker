package com.aubynsamuel.expensetracker.presentation.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.rememberSplineBasedDecay
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
import com.aubynsamuel.expensetracker.presentation.components.DrawerContent
import com.aubynsamuel.expensetracker.presentation.components.HomeContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val drawerWidth = 280.dp
    val drawerWidthPx =
        with(LocalDensity.current) { drawerWidth.toPx() }
    val scope = rememberCoroutineScope()
    val translationX = remember { Animatable(0f) }
    translationX.updateBounds(0f, drawerWidthPx)
    var currentScreen by remember { mutableStateOf(Screens.HOME_SCREEN) }
    val decay = rememberSplineBasedDecay<Float>()
    val context = LocalContext.current

    val draggableState = rememberDraggableState { dragAmount ->
        scope.launch {
            translationX.snapTo(translationX.value + dragAmount)
        }
    }
    val shadowColor = MaterialTheme.colorScheme.onBackground

    fun toggleDrawer() =
        scope.launch {
            if (translationX.value == drawerWidthPx) {
                translationX.animateTo(0f)
            } else {
                translationX.animateTo(drawerWidthPx)
            }
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
                currentScreen = screen
                toggleDrawer()
            },
            currentScreen = currentScreen,
            width = drawerWidth,
            dragAmountPx = dragAmountPx,
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

        HomeContent(
            currentScreen = currentScreen,
            toggleDrawer = { toggleDrawer() },
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
        )
    }
}

object Screens {
    const val HOME_SCREEN = "HOME_SCREEN"
    const val EXPENSES_SCREEN = "EXPENSES_SCREEN"
}