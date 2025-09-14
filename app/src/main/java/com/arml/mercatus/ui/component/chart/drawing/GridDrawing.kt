package com.arml.mercatus.ui.component.chart.drawing

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope

fun <T> DrawScope.drawHorizontalGridLines(
    data: List<T>,
    bounds: Pair<T, T>,
    relativeValue: (min: T, value: T) -> Float,
    brush: Brush = Brush.linearGradient(colors = listOf(Color.DarkGray, Color.Black)),
    pathEffect: PathEffect? = null,
    strokeWidth: Float = 2f,
    size: Size = this.size,
) {
    val (min, max) = bounds.run {
        if (relativeValue(first,second) < 0) second to first else this
    }
    val range = relativeValue(min, max)
    val segment = size.height / range

    data.forEach { element ->
        val yPosition = size.height - relativeValue(min,element) * segment

        drawLine(
            brush = brush,
            start = Offset(0f, yPosition),
            end = Offset(size.width, yPosition),
            strokeWidth = strokeWidth,
            pathEffect = pathEffect
        )
    }
}

fun <T> DrawScope.drawVerticalGridLines(
    data: List<T>,
    bounds: Pair<T, T>,
    relativeValue: (min: T, value: T) -> Float,
    brush: Brush = Brush.linearGradient(colors = listOf(Color.DarkGray, Color.Black)),
    pathEffect: PathEffect? = null,
    strokeWidth: Float = 2f,
    size: Size,
) {
    val (min, max) = bounds.run {
        if (relativeValue(first, second) < 0) second to first else this
    }
    val range = relativeValue(min, max)
    val segment = size.width / range

    data.forEach { element ->
        val xPosition = relativeValue(min, element) * segment

        drawLine(
            brush = brush,
            start = Offset(xPosition, 0f),
            end = Offset(xPosition, size.height),
            strokeWidth = strokeWidth,
            pathEffect = pathEffect
        )
    }
}