package com.arml.mercatus.ui.component.chart.drawing

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import com.arml.mercatus.ui.component.chart.layout.calculateXAxisPosition
import com.arml.mercatus.ui.component.chart.layout.calculateYAxisPosition
import com.arml.mercatus.ui.component.chart.text.toTextLayoutResult
import kotlin.collections.forEach

fun <T: Comparable<T>> DrawScope.drawXAxisLabels(
    data: List<T>,
    bounds: Pair<T, T>,
    relativeValue: (min: T, value: T) -> Float,
    labelFormatter: (T) -> String,
    textMeasurer: TextMeasurer,
    textStyle: TextStyle,
    axisSize: Size,
) {
    val (min, max) = bounds.run { if (second < first) second to first else this }

    data.forEach { element ->
        val xPosition = calculateXAxisPosition(
            value = element,
            bounds = min to max,
            relativeValue = { first, current -> relativeValue(first, current) },
            size = axisSize,
            textStyle = textStyle,
            textMeasurer = textMeasurer
        )

        val text = labelFormatter(element)

        drawText(
            textLayoutResult = text.toTextLayoutResult(textMeasurer, textStyle),
            topLeft = Offset(xPosition, 0f)
        )
    }
}

fun <T: Comparable<T>> DrawScope.drawYAxisLabels(
    data: List<T>,
    bounds: Pair<T, T>,
    relativeValue: (min: T, value: T) -> Float,
    labelFormatter: (T) -> String,
    textMeasurer: TextMeasurer,
    textStyle: TextStyle,
    axisSize: Size
) {
    val fixedBounds = bounds.run { if (second < first) second to first else this }

    data.forEach { element ->
        val yPosition = calculateYAxisPosition(
            value = element,
            bounds = fixedBounds,
            relativeValue = { min, value -> relativeValue(min, value) },
            textStyle = textStyle,
            textMeasurer = textMeasurer,
            size = axisSize
        )

        val text = labelFormatter(element)

        drawText(
            textLayoutResult = text.toTextLayoutResult(textMeasurer, textStyle),
            topLeft = Offset(0f, yPosition)
        )
    }
}

/*fun DrawScope.drawXAxisLabels(
    data: List<LocalDate>,
    bounds: Pair<LocalDate, LocalDate>,
    dateFormatter: DateTimeFormatter = DateTimeFormatter
        .ofPattern("dd/MM/yy"),
    textMeasurer: TextMeasurer,
    textStyle: TextStyle,
    axisSize: Size,
) {
    val (min, max) = if (bounds.second < bounds.first) bounds.second to bounds.first else bounds
    val relativeValue = { min: LocalDate, value: LocalDate -> (value-min).toFloat() }

    data.forEach { date ->
        val xPosition = calculateXAxisPosition(
            value = date.format(dateFormatter),
            bounds = min.format(dateFormatter) to max.format(dateFormatter),
            relativeValue = { firstDate, currentDate ->
                val first = LocalDate.parse(
                    firstDate,
                    dateFormatter
                )
                val current = LocalDate.parse(
                    currentDate,
                    dateFormatter
                )
                relativeValue(first, current)
            },
            size = axisSize,
            textStyle = textStyle,
            textMeasurer = textMeasurer
        )

        drawText(
            textLayoutResult = date
                .format(dateFormatter)
                .toTextLayoutResult(textMeasurer, textStyle),
            topLeft = Offset(xPosition, 0f)
        )
    }

}

fun DrawScope.drawYAxisLabels(
    data: List<Currency>,
    bounds: Pair<Currency, Currency>,
    textMeasurer: TextMeasurer,
    textStyle: TextStyle,
    axisSize: Size
) {
    val fixedBounds = bounds.run { if (second < first) second to first else this }

    data.forEach { currency ->
        val yPosition = calculateYAxisPosition(
            value = currency,
            bounds = fixedBounds,
            relativeValue = { min, value -> (value - min).toFloat() },
            textStyle = textStyle,
            textMeasurer = textMeasurer,
            size = axisSize
        )

        drawText(
            textLayoutResult = currency.toString().toTextLayoutResult(textMeasurer, textStyle),
            topLeft = Offset(0f, yPosition)
        )
    }
}*/