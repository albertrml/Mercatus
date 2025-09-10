package com.arml.mercatus.ui.component.graph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.toSize


@Composable
fun Chart(
    modifier: Modifier = Modifier,
    xAxisElementSize: Size,
    yAxisElementSize: Size,
    drawYInfoChart: DrawScope.(Size) -> Unit,
    drawXInfoChart: DrawScope.(Size) -> Unit,
    drawChart: DrawScope.(Size) -> Unit,
) {

    Canvas(modifier = modifier) {
        val xAxisWidthPx = xAxisElementSize.width
        val xAxisHeightPx = xAxisElementSize.height
        val yAxisWidthPx = yAxisElementSize.width

        val chartSize = Size(
            width = size.width - yAxisWidthPx - xAxisWidthPx,
            height = size.height - xAxisHeightPx - xAxisWidthPx/2
        )
        val yAxisSize = Size(width = yAxisWidthPx, height = chartSize.height)
        val xAxisSize = Size(width = chartSize.width, height = xAxisHeightPx)

        drawYInfoChart(yAxisSize)

        withTransform({
            translate( left = (yAxisWidthPx + xAxisWidthPx/2) )
        }) { drawChart(chartSize) }

        withTransform({
            translate(
                left = (yAxisWidthPx + xAxisWidthPx/2),
                top = chartSize.height + xAxisWidthPx/2
            )
        }) { drawXInfoChart(xAxisSize) }
    }

}


@Composable
fun ChartLegends(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
    legend: @Composable () -> Unit = {},
    xAxis: @Composable () -> Unit = {},
    yAxis: @Composable () -> Unit = {},
    chart: @Composable () -> Unit,
) {
    SubcomposeLayout(modifier) { constraints ->
        val titlePlaceable = subcompose("title", title).map {
            it.measure(constraints.copy(minHeight = 0))
        }
        val legendPlaceable = subcompose("legend", legend).map {
            it.measure(constraints.copy(minHeight = 0))
        }
        val xAxisPlaceable = subcompose("xAxis", xAxis).map {
            it.measure(constraints.copy(minHeight = 0))
        }

        val titleHeight = titlePlaceable.maxOfOrNull { it.height } ?: 0
        val legendHeight = legendPlaceable.maxOfOrNull { it.height } ?: 0
        val xAxisHeight = xAxisPlaceable.maxOfOrNull { it.height } ?: 0

        val yAxisHeight = maxOf(
            0,
            constraints.maxHeight - xAxisHeight - titleHeight - legendHeight
        )
        val yAxisPlaceable = subcompose("yAxis") {
            Box(
                modifier = Modifier
                    .width(IntrinsicSize.Max)
                    .height(yAxisHeight.toDp())
            ) {
                yAxis()
            }
        }.map {
            it.measure(
                Constraints(
                    minWidth = 0,
                    maxWidth = constraints.maxWidth,
                    minHeight = 0,
                    maxHeight = yAxisHeight
                )
            )
        }
        val yAxisWidth = yAxisPlaceable.maxOfOrNull { it.width } ?: 0

        val chartHeight = maxOf(
            0,
            constraints.maxHeight - xAxisHeight - titleHeight - legendHeight
        )
        val chartWidth = maxOf(
            0,
            constraints.maxWidth - yAxisWidth
        )

        val chartPlaceable = subcompose("chart") {
            Box(
                modifier = Modifier
                    .size(
                        chartWidth.toDp(),
                        chartHeight.toDp()
                    )
            ) {
                chart()
            }
        }.map { it.measure(Constraints.fixed(chartWidth, chartHeight)) }

        layout(constraints.maxWidth, constraints.maxHeight) {
            var y = 0

            titlePlaceable.forEach {
                it.placeRelative(0, y)
            }
            y += titleHeight

            val chartY = y
            yAxisPlaceable.forEach {
                it.placeRelative(0, chartY)
            }

            chartPlaceable.forEach {
                it.placeRelative(yAxisWidth, chartY)
            }
            y += chartHeight

            xAxisPlaceable.forEach {
                it.placeRelative(yAxisWidth, y)
            }

            y += xAxisHeight
            legendPlaceable.forEach {
                it.placeRelative(0, y)
            }
        }
    }
}

fun <T> DrawScope.drawLinesAtYAxis(
    data: List<T>,
    bounds: Pair<T, T>,
    relativeValue: (min: T, value: T) -> Float,
    color: Color = Color.Black,
    pathEffect: PathEffect? = null,
    strokeWidth: Float = 2f,
    size: Size = this.size,
) {
    val (min, max) = bounds.run {
        if (relativeValue(first, second) < 0) second to first else this
    }
    val range = relativeValue(min, max)
    val segment = size.width / range

    data.forEach { element ->
        val xPosition = relativeValue(min, element) * segment

        drawLine(
            color = color,
            start = Offset(xPosition, 0f),
            end = Offset(xPosition, size.height),
            strokeWidth = strokeWidth,
            pathEffect = pathEffect
        )
    }
}

fun <T> DrawScope.drawLinesAtXAxis(
    data: List<T>,
    bounds: Pair<T, T>,
    relativeValue: (min: T, value: T) -> Float,
    color: Color = Color.Black,
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
            color = color,
            start = Offset(0f, yPosition),
            end = Offset(size.width, yPosition),
            strokeWidth = strokeWidth,
            pathEffect = pathEffect
        )
    }
}

fun <T : Comparable<T>> DrawScope.placementOnXAxis(
    value: T,
    bounds: Pair<T, T>,
    relativeValue: (min: T, value: T) -> Float,
    textStyle: TextStyle,
    textMeasurer: TextMeasurer,
    size: Size = this.size
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

fun <T : Comparable<T>> DrawScope.placementOnYAxis(
    value: T,
    bounds: Pair<T, T>,
    relativeValue: (min: T, value: T) -> Float,
    textStyle: TextStyle,
    textMeasurer: TextMeasurer,
    size: Size = this.size
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