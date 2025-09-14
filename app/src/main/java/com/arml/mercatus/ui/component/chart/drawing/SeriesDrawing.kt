package com.arml.mercatus.ui.component.chart.drawing

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import com.arml.mercatus.data.model.Currency
import com.arml.mercatus.data.model.Price
import com.arml.mercatus.ui.component.series.toSmoothPath
import java.time.LocalDate

fun DrawScope.drawLineSeries(
    data: List<Price>,
    xBounds: (List<Price>) -> Pair<LocalDate, LocalDate>,
    yBounds: (List<Price>) -> Pair<Currency, Currency>,
    color: Color,
    style: DrawStyle,
    size: Size,
) {

    val path = data.toSmoothPath(
        xSelector = { it.date },
        ySelector = { it.currency },
        xMapper = { (it as LocalDate).toEpochDay().toFloat() },
        yMapper = { (it as Currency).toFloat() },
        xBounds = {
            xBounds(data).run {
                first.toEpochDay().toFloat() to second.toEpochDay().toFloat()
            }
        },
        yBounds = { data ->
            yBounds(data).let { it.first.toFloat() to it.second.toFloat() }
        },
        chartSize = size
    )

    drawPath(
        path = path,
        color = color,
        style = style
    )
}