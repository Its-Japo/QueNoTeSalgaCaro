package com.example.quenotesalgacaro.ui.view.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.example.quenotesalgacaro.ui.theme.black
import com.example.quenotesalgacaro.ui.theme.gray
import com.example.quenotesalgacaro.ui.theme.white
import kotlin.math.roundToInt


@Composable
fun BarChart(
    inputList: List<BarChartInput>,
    modifier: Modifier = Modifier,
    showDescription: Boolean
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        //sum of chart lines
        val listSum by remember {
            mutableStateOf(inputList.sumOf { it.value })
        }
        inputList.forEach { input ->
            val percentage = input.value / listSum.toFloat()

            Bar(
                modifier = Modifier
                    .height(120.dp * percentage * inputList.size)
                    .width(40.dp),
                primaryColor = input.color,
                percentage = percentage,
                description = input.description,
                showDescription = showDescription
            )
        }
    }
}

@Composable
fun Bar(
    modifier: Modifier = Modifier,
    primaryColor: Color,
    percentage: Float,
    description: String,
    showDescription: Boolean
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val width = size.width
            val height = size.height
            val barWidth = width / 5 * 3
            var barHeight = height / 8 * 7
            //until here is enough for 2D chart
            val barHeight3DPart = height - barHeight
            val barWidth3DPart = (width - barWidth) * (height * 0.002f)

            var path = Path().apply {
                moveTo(0f, height)
                lineTo(barWidth, height)
                lineTo(barWidth, height - barHeight)
                lineTo(0f, height - barHeight)
                close()
            }
            drawPath(
                path,
                brush = Brush.linearGradient(
                    colors = listOf(gray, primaryColor)
                )
            )
            //here is starting 3D draw
            path = Path().apply {
                moveTo(barWidth, height - barHeight)
                lineTo(barWidth3DPart + barWidth, 0f)
                lineTo(barWidth3DPart + barWidth, barHeight)
                lineTo(barWidth, height)
                close()
            }

            drawPath(
                path,
                brush = Brush.linearGradient(
                    colors = listOf(primaryColor, gray)
                )
            )
            path = Path().apply {
                moveTo(0f, barHeight3DPart)
                lineTo(barWidth, barHeight3DPart)
                lineTo(barWidth + barWidth3DPart, 0f)
                lineTo(barWidth3DPart, 0f)
                close()
            }
            drawPath(
                path,
                brush = Brush.linearGradient(
                    colors = listOf(gray, primaryColor)
                )
            )
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    "${(percentage * 100).roundToInt()} %",
                    barWidth / 5f,
                    height + 55f,
                    android.graphics.Paint().apply {
                        color = white.toArgb()
                        textSize = 11.dp.toPx()
                        isFakeBoldText = true
                    }
                )
            }
            if (showDescription) {
                drawContext.canvas.nativeCanvas.apply {
                    rotate(-50f, pivot = Offset(barHeight3DPart - 20, 0f)) {
                        drawText(
                            description,
                            0f,
                            0f,
                            android.graphics.Paint().apply {
                                color = black.toArgb()
                                textSize = 14.dp.toPx()
                                isFakeBoldText = true
                            }
                        )
                    }
                }
            }
        }
    }
}
//defining model of barchart input
data class BarChartInput(
    val value: Int,
    val description: String,
    val color: Color
)