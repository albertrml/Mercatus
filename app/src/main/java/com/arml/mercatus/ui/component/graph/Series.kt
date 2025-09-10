package com.arml.mercatus.ui.component.graph

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path

fun <T> List<T>.toSeries(
    xSelector: (T) -> Any,
    ySelector: (T) -> Any,
    xMapper: (Any) -> Float,
    yMapper: (Any) -> Float,
    xBounds: (List<T>) -> Pair<Float, Float>,
    yBounds: (List<T>) -> Pair<Float, Float>,
    size: Size
): Path {
    val path = Path()
    if (isEmpty() || size.width == 0f || size.height == 0f) return path

    val (minX, maxX) = xBounds(this)
    val (minY, maxY) = yBounds(this)

    val xDelta = maxX - minX
    val yDelta = maxY - minY
    if (xDelta == 0f || yDelta == 0f) return path

    fun mapX(x: Any): Float = (xMapper(x) - minX) / xDelta * size.width
    fun mapY(y: Any): Float = size.height - (yMapper(y) - minY) / yDelta * size.height

    val firstPoint = first()
    path.moveTo(mapX(xSelector(firstPoint)), mapY(ySelector(firstPoint)))

    windowed(2) { (p0, p1) ->
        val x0 = mapX(xSelector(p0))
        val y0 = mapY(ySelector(p0))
        val x1 = mapX(xSelector(p1))
        val y1 = mapY(ySelector(p1))

        val cx = (x0 + x1) / 2
        val c1 = Offset(cx, y0)
        val c2 = Offset(cx, y1)

        path.cubicTo(c1.x, c1.y, c2.x, c2.y, x1, y1)
    }

    return path
}