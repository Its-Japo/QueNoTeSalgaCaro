package com.example.quenotesalgacaro.ui.view.composables


import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.nativeCanvas
import kotlin.math.cos
import kotlin.math.sin


data class PieChartEntry(val color: Color, val percentage: Float, val texto: String)

fun calculateStartAngles(entries: List<PieChartEntry>): List<Float> {
    var totalPercentage = 0f
    return entries.map { entry ->
        val startAngle = totalPercentage * 360
        totalPercentage += entry.percentage
        startAngle
    }
}

@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    entries: List<PieChartEntry>,
){
    Canvas(modifier = modifier.size(150.dp)) {
        val startAngles = calculateStartAngles(entries)
        entries.forEachIndexed { index, entry ->
            val startAngle = startAngles[index]
            val sweepAngle = entry.percentage * 360f

            drawArc(
                color = entry.color,
                startAngle = startAngles[index],
                sweepAngle = entry.percentage * 360f,
                useCenter = true,
                topLeft = Offset.Zero,
                size = this.size,
            )

            val halfAngle = startAngle + sweepAngle / 2
            val radius = size.minDimension / 4
            val textX = center.x + radius * cos(Math.toRadians(halfAngle.toDouble())).toFloat()
            val textY = center.y + radius * sin(Math.toRadians(halfAngle.toDouble())).toFloat()

            val text = entry.texto
            drawContext.canvas.nativeCanvas.drawText(
                text,
                textX,
                textY,
                android.graphics.Paint().apply {
                    color = android.graphics.Color.BLACK
                    textSize = 40f
                    textAlign = android.graphics.Paint.Align.CENTER
                    isFakeBoldText = true
                }
            )

        }
    }
}