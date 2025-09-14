package com.arml.mercatus.ui.component.chart.layout

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import com.arml.mercatus.ui.component.chart.text.measure

fun <T> calculateXAxisPosition(
    value: T,
    bounds: Pair<T, T>,
    relativeValue: (min: T, value: T) -> Float,
    textStyle: TextStyle,
    textMeasurer: TextMeasurer,
    size: Size
): Float {
    val (min, max) = if (relativeValue(bounds.first, bounds.second) < 0)
        bounds.second to bounds.first else bounds

    if (relativeValue(min, value) < 0)
        throw IllegalArgumentException("value must be greater than min")

    if (relativeValue(value, max) < 0)
        throw IllegalArgumentException("value must be lesser than max")

    val segment = size.width / relativeValue(min, max)
    val relativeValue = relativeValue(min, value)
    val xPosition = relativeValue * segment
    val contentWidth = value.measure(textMeasurer, textStyle).width

    return xPosition - contentWidth / 2
}

fun <T> calculateYAxisPosition(
    value: T,
    bounds: Pair<T, T>,
    relativeValue: (min: T, value: T) -> Float,
    textStyle: TextStyle,
    textMeasurer: TextMeasurer,
    size: Size
): Float {
    val (min, max) = if (relativeValue(bounds.first, bounds.second) < 0)
        bounds.second to bounds.first else bounds

    if (relativeValue(min, max) < 0)
        throw IllegalArgumentException("max must be greater than min")

    if (relativeValue(min, value) < 0)
        throw IllegalArgumentException("value must be greater than min")

    if (relativeValue(value, max) < 0)
        throw IllegalArgumentException("value must be lesser than max")

    val range = relativeValue(min,max)
    val segment = size.height / range
    val relativeValue = relativeValue(min, value)

    val yPosition = size.height - relativeValue * segment
    val contentHeight = value.measure(textMeasurer, textStyle).height

    val yCentralizedPosition = yPosition - contentHeight / 2

    return yCentralizedPosition
}