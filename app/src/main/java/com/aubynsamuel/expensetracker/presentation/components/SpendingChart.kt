import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aubynsamuel.expensetracker.data.model.Expense
import ir.ehsannarmani.compose_charts.PieChart
import ir.ehsannarmani.compose_charts.models.Pie

@Composable
fun SpendingChart(expenses: List<Expense>) {
    val spendingByCategory = remember(expenses) {
        expenses.groupBy { expense -> expense.category }
            .mapValues { groupedExpenses -> groupedExpenses.value.sumOf { it.amount } }
    }

    val totalSpending = spendingByCategory.values.sum()

    if (totalSpending == 0.0) {
        Text("No spending data available.", modifier = Modifier.padding(16.dp))
        return
    }

    // Define colors for categories
    val categoryColors = remember {
        listOf(
            Color(0xFFE91E63), // Pink
            Color(0xFF9C27B0), // Purple
            Color(0xFF3F51B5), // Indigo
            Color(0xFF2196F3), // Blue
            Color(0xFF00BCD4), // Cyan
            Color(0xFF009688), // Teal
            Color(0xFF4CAF50), // Green
            Color(0xFFFF9800), // Orange
            Color(0xFFFF5722), // Deep Orange
            Color(0xFF795548), // Brown
        )
    }

    var pieData by remember(spendingByCategory) {
        mutableStateOf(
            spendingByCategory.entries.mapIndexed { index, (category, amount) ->
                Pie(
                    label = category,
                    data = amount,
                    color = categoryColors[index % categoryColors.size],
                    selectedColor = categoryColors[index % categoryColors.size].copy(alpha = 0.7f)
                )
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Spending Breakdown",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Row {
//            LineChart(
//                data = expenses.mapIndexed { index, expense ->
//                    Line(
//                        values = listOf(expense.amount),
//                        color = Brush.linearGradient(colors = categoryColors),
//                        label = expense.title
//                    )
//                }
//            )

            PieChart(
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp),
                data = pieData,
                onPieClick = {
                    val pieIndex = pieData.indexOf(it)
                    pieData = pieData.mapIndexed { mapIndex, pie ->
                        pie.copy(selected = pieIndex == mapIndex)
                    }
                },
                selectedScale = 1.2f,
                scaleAnimEnterSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                ),
                colorAnimEnterSpec = tween(300),
                colorAnimExitSpec = tween(300),
                scaleAnimExitSpec = tween(300),
                spaceDegreeAnimExitSpec = tween(300),
                style = Pie.Style.Stroke()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Legend/Summary
            Column {
                pieData.forEach { pie ->
                    val percentage = (pie.data / totalSpending * 100).toFloat()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .background(pie.color, shape = CircleShape)
                            )
                            pie.label?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
//                    Text(
//                        text = "$%.2f (%.1f%%)".format(pie.data, percentage),
//                        style = MaterialTheme.typography.bodyMedium,
//                        fontWeight = FontWeight.Medium
//                    )
                    }
                }
            }
        }
    }
}
