package com.aubynsamuel.expensetracker.presentation.components.settings

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.material3.ToggleButtonShapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ColorItem(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    ToggleButton(
        checked = isSelected,
        onCheckedChange = { onClick() },
        shapes = ToggleButtonShapes(
            shape = CircleShape,
            pressedShape = RoundedCornerShape(20.dp),
            checkedShape = RoundedCornerShape(20.dp)
        ),
        colors = ToggleButtonDefaults.toggleButtonColors()
            .copy(
                containerColor = color,
                checkedContainerColor = color,
                contentColor = if (color.value == Color.White.value) Color.Black else Color.White,
                checkedContentColor = if (color.value == Color.White.value) Color.Black else Color.White,
            ),
        modifier = Modifier
            .padding(8.dp)
            .size(50.dp)
    ) {
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
            )
        }
    }
}