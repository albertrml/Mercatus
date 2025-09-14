package com.arml.mercatus.ui.component.chart.container

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Constraints

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
fun ChartWithLegends(
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