package com.example.quenotesalgacaro.ui.view.composables


import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.nativeCanvas
import kotlin.math.cos
import kotlin.math.sin
import androidx.compose.ui.graphics.toArgb
import android.graphics.Color as AndroidColor

data class PieChartEntry(val percentage: Float, val texto: String, val color: Color? = null)

private fun generateColorForIndex(index: Int, totalEntries: Int): Color {
    val hue = (360f / totalEntries) * index
    val androidColor = AndroidColor.HSVToColor(floatArrayOf(hue, 1f, 1f))
    return Color(androidColor)
}

fun calculateStartAngles(entries: List<PieChartEntry>): List<Float> {
    var totalPercentage = 0f
    return entries.map { entry ->
        val startAngle = totalPercentage * 360
        totalPercentage += entry.percentage
        startAngle
    }
}

private fun getPieChartEntriesWithColors(entries: List<PieChartEntry>): List<PieChartEntry> {
    return entries.mapIndexed { index, entry ->
        if (entry.color == null) {
            entry.copy(color = generateColorForIndex(index, entries.size))
        } else {
            entry
        }
    }
}

@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    entries: List<PieChartEntry>,
){
    val coloredEntries = getPieChartEntriesWithColors(entries)

    Canvas(modifier = modifier.size(150.dp)) {
        val startAngles = calculateStartAngles(entries)
        coloredEntries.forEachIndexed { index, entry ->

            drawArc(
                color = entry.color ?: Color.Unspecified,
                startAngle = startAngles[index],
                sweepAngle = entry.percentage * 360f,
                useCenter = true,
                topLeft = Offset.Zero,
                size = this.size,
            )
        }
    }
}

@Composable
fun PieChartLegend(entries: List<PieChartEntry>) {
    val coloredEntries = getPieChartEntriesWithColors(entries)
    Column {
        coloredEntries.forEach { entry ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier
                    .size(10.dp)
                    .background(entry.color ?: Color.Black, shape = MaterialTheme.shapes.extraLarge)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "${entry.texto} (${(entry.percentage * 100).toInt()}%)")
            }
        }
    }
}

@Composable
fun PieChartWithLegend(
    modifier: Modifier = Modifier,
    entries: List<PieChartEntry>,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PieChart(entries = entries)
        Spacer(modifier = Modifier.width(16.dp))
        PieChartLegend(entries = entries)
    }
}