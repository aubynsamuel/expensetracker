package com.aubynsamuel.expensetracker.presentation.components.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun SettingsCard(cardTitle: String = "", content: @Composable () -> Unit) {
    Column {
        Text(cardTitle, modifier = Modifier.padding(start = 16.dp))
        Spacer(modifier = Modifier.height(5.dp))
        Card(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(8.dp)
                .clip(RoundedCornerShape(30.dp)),
            colors = CardDefaults.cardColors()
                .copy(containerColor = MaterialTheme.colorScheme.background)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                content()
            }
        }
    }
}