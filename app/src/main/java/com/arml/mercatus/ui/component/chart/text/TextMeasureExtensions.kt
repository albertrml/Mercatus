package com.arml.mercatus.ui.component.chart.text

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.toSize

fun String.toTextLayoutResult(
    textMeasurer: TextMeasurer,
    textStyle: TextStyle,
): TextLayoutResult {
    return textMeasurer.measure(
        text = this,
        style = textStyle
    )
}

fun <T> T.measure(
    textMeasurer: TextMeasurer,
    textStyle: TextStyle,
): Size = toString().toTextLayoutResult(textMeasurer, textStyle).size.toSize()

fun <T> List<T>.maxWidth(
    textMeasurer: TextMeasurer,
    textStyle: TextStyle
): Float {
    return maxOfOrNull { it.measure(textMeasurer, textStyle).width } ?: 0f
}

fun <T> List<T>.maxHeight(
    textMeasurer: TextMeasurer,
    textStyle: TextStyle,
): Float {
    return maxOfOrNull { it.measure(textMeasurer, textStyle).height } ?: 0f
}