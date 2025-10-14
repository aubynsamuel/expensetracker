package com.aubynsamuel.expensetracker.presentation.components.settings

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
                contentColor = Color.White,
                checkedContentColor = Color.White,
            ),
        modifier = Modifier
            .size(60.dp)
    ) {
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
            )
        }
    }
}