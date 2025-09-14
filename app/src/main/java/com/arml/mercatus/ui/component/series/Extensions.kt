package com.arml.mercatus.ui.component.series

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import com.arml.mercatus.ui.component.chart.exception.ChartException.*

fun <T> List<T>.toSmoothPath(
    xSelector: (T) -> Any,
    ySelector: (T) -> Any,
    xMapper: (Any) -> Float,
    yMapper: (Any) -> Float,
    xBounds: (List<T>) -> Pair<Float, Float>,
    yBounds: (List<T>) -> Pair<Float, Float>,
    chartSize: Size
): Path {
    val path = Path()
    val points = toChartPoints(
        xSelector, ySelector,
        xMapper, yMapper,
        xBounds, yBounds,
        chartSize
    )

    if (points.size < 2) return path

    val first = points.first()
    path.moveTo(first.x, first.y)

    points.windowed(2) { (p0, p1) ->
        val cx = (p0.x + p1.x) / 2
        val c1 = Offset(cx, p0.y)
        val c2 = Offset(cx, p1.y)
        path.cubicTo(c1.x, c1.y, c2.x, c2.y, p1.x, p1.y)
    }

    return path
}

fun <T> List<T>.toChartPoints(
    xSelector: (T) -> Any,
    ySelector: (T) -> Any,
    xMapper: (Any) -> Float,
    yMapper: (Any) -> Float,
    xBounds: (List<T>) -> Pair<Float, Float>,
    yBounds: (List<T>) -> Pair<Float, Float>,
    chartSize: Size
): List<Offset> {
    if (chartSize.width == 0f) throw IllegalWidthChartException()
    if (chartSize.height == 0f) throw IllegalHeightChartException()
    if (isEmpty()) return emptyList()

    val (minX, maxX) = xBounds(this)
    val (minY, maxY) = yBounds(this)

    val xDelta = maxX - minX
    val yDelta = maxY - minY
    if (xDelta == 0f || yDelta == 0f) return emptyList()

    fun mapX(x: Any): Float = (xMapper(x) - minX) / xDelta * chartSize.width
    fun mapY(y: Any): Float = chartSize.height - (yMapper(y) - minY) / yDelta * chartSize.height

    return map { item ->
        Offset(
            mapX(xSelector(item)),
            mapY(ySelector(item))
        )
    }
}