package com.arml.mercatus.ui.component.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arml.mercatus.data.common.utils.localdate.minus
import com.arml.mercatus.data.model.Currency
import com.arml.mercatus.data.model.Price
import com.arml.mercatus.ui.component.chart.container.Chart
import com.arml.mercatus.ui.component.chart.drawing.drawYAxisLabels
import com.arml.mercatus.ui.component.chart.drawing.drawXAxisLabels
import com.arml.mercatus.ui.component.chart.drawing.drawHorizontalGridLines
import com.arml.mercatus.ui.component.chart.drawing.drawVerticalGridLines
import com.arml.mercatus.ui.component.chart.drawing.drawLineSeries
import com.arml.mercatus.ui.component.chart.text.maxHeight
import com.arml.mercatus.ui.component.chart.text.maxWidth
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun PriceChart(
    modifier: Modifier = Modifier,
    data: List<Price>,
    xAxisLabelsProvider: (List<Price>) -> List<LocalDate>,
    yAxisLabelsProvider: (List<Price>) -> List<Currency>,
    xBoundsProvider: (List<Price>) -> Pair<LocalDate, LocalDate>,
    yBoundsProvider: (List<Price>) -> Pair<Currency, Currency>,
    axisPathEffect: PathEffect? = null,
    borderColor: Color = Color.Black,
    borderStyle: DrawStyle = Stroke(4.dp.value),
    dataColor: Color = Color.Black,
    dataStyle: DrawStyle = Stroke(4.dp.value),
    gridLineThickness: Float = 2f,
    gridLineBrush: Brush = Brush.linearGradient(colors = listOf(Color.DarkGray, Color.Black)),
    textLegendStyle: TextStyle = TextStyle(
        color = Color.Black,
        fontSize = 10.sp
    )
) {
    val textMeasurer = rememberTextMeasurer()
    val xSeriesBounds = xBoundsProvider(data)
    val ySeriesBounds = yBoundsProvider(data)
    val xSeries = xAxisLabelsProvider(data)
    val ySeries = yAxisLabelsProvider(data)

    val maxXAxisElementSize = Size(
        xSeries.maxWidth(textMeasurer, textLegendStyle),
        xSeries.maxHeight(textMeasurer, textLegendStyle)
    )

    val maxYAxisElementSize = Size(
        ySeries.maxWidth(textMeasurer, textLegendStyle),
        ySeries.maxHeight(textMeasurer, textLegendStyle)
    )

    Chart(
        modifier = modifier,
        xAxisElementSize = maxXAxisElementSize,
        yAxisElementSize = maxYAxisElementSize,
        drawYInfoChart = { yChartSize ->
            drawYAxisLabels(
                data = ySeries,
                bounds = ySeriesBounds,
                relativeValue = { min, value -> (value - min).toFloat() },
                labelFormatter = { currency -> currency.toString() },
                textMeasurer = textMeasurer,
                textStyle = textLegendStyle,
                axisSize = yChartSize
            )
        },
        drawXInfoChart = { xChartSize ->
            drawXAxisLabels(
                data = xSeries,
                bounds = xSeriesBounds,
                relativeValue = { min, value -> (value - min).toFloat() },
                labelFormatter = { date ->
                    date.format(
                        DateTimeFormatter.ofPattern("dd/MM/yy")
                    )
                },
                textMeasurer = textMeasurer,
                textStyle = textLegendStyle,
                axisSize = xChartSize
            )
        }
    ) { chartSize ->
        drawRect(
            color = borderColor,
            size = chartSize,
            style = borderStyle
        )
        drawLineSeries(
            data = data,
            xBounds = { xSeriesBounds },
            yBounds = { ySeriesBounds },
            color = dataColor,
            style = dataStyle,
            size = chartSize
        )
        drawVerticalGridLines(
            data = xSeries,
            bounds = xSeriesBounds,
            relativeValue = { min, value -> (value - min).toFloat() },
            brush = gridLineBrush,
            pathEffect = axisPathEffect,
            strokeWidth = gridLineThickness,
            size = chartSize
        )
        drawHorizontalGridLines(
            data = ySeries,
            bounds = ySeriesBounds,
            relativeValue = { min, value -> (value - min).toFloat() },
            brush = gridLineBrush,
            pathEffect = axisPathEffect,
            strokeWidth = gridLineThickness,
            size = chartSize
        )
    }
}