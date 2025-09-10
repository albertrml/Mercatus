package com.arml.mercatus.ui.component.graph

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arml.mercatus.data.common.utils.collection.getMinMax
import com.arml.mercatus.data.common.utils.minus
import com.arml.mercatus.data.mock.mockPrices
import com.arml.mercatus.data.model.Currency
import com.arml.mercatus.data.model.Price
import com.arml.mercatus.ui.theme.dimens
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun PriceChart(
    modifier: Modifier = Modifier,
    data: List<Price>,
    xLabelData: (List<Price>) -> List<LocalDate>,
    xBounds: (List<Price>) -> Pair<LocalDate, LocalDate>,
    yLabelData: (List<Price>) -> List<Currency>,
    yBounds: (List<Price>) -> Pair<Currency, Currency>,
    borderColor: Color = Color.Black,
    borderStyle: DrawStyle = Stroke(2.dp.value),
    pathEffect: PathEffect? = null,
    textStyle: TextStyle = TextStyle(
        color = Color.Black,
        fontSize = 10.sp
    )
) {
    val textMeasurer = rememberTextMeasurer()
    val xSeriesBounds = xBounds(data)
    val ySeriesBounds = yBounds(data)
    val xSeries = xLabelData(data)
    val ySeries = yLabelData(data)

    val maxXAxisElementSize = Size(
        xSeries.maxWidth(textMeasurer, textStyle),
        xSeries.maxHeight(textMeasurer, textStyle)
    )

    val maxYAxisElementSize = Size(
        ySeries.maxWidth(textMeasurer, textStyle),
        ySeries.maxHeight(textMeasurer, textStyle)
    )

    Chart(
        modifier = modifier,
        xAxisElementSize = maxXAxisElementSize,
        yAxisElementSize = maxYAxisElementSize,
        drawYInfoChart = { yChartSize ->
            drawCurrencyAxis(
                data = ySeries,
                bounds = ySeriesBounds,
                textMeasurer = textMeasurer,
                textStyle = textStyle,
                axisSize = yChartSize
            )
        },
        drawXInfoChart = { xChartSize ->
            drawDateAxis(
                data = xSeries,
                bounds = xSeriesBounds,
                textMeasurer = textMeasurer,
                textStyle = textStyle,
                axisSize = xChartSize
            )
        }
    ) { chartSize ->
        drawRect(
            color = borderColor,
            size = chartSize,
            style = borderStyle
        )
        drawPriceSeries(
            data = data,
            xBounds = { xSeriesBounds },
            yBounds = { ySeriesBounds },
            size = chartSize
        )
        drawLinesAtYAxis(
            data = xSeries,
            bounds = xSeriesBounds,
            relativeValue = { min, value ->
                value.minus(min).toEpochDay().toFloat()
            },
            pathEffect = pathEffect,
            size = chartSize
        )
        drawLinesAtXAxis(
            data = ySeries,
            bounds = ySeriesBounds,
            relativeValue = { min, value -> (value - min).toFloat() },
            pathEffect = pathEffect,
            size = chartSize
        )
    }
}

fun DrawScope.drawPriceSeries(
    data: List<Price>,
    xBounds: (List<Price>) -> Pair<LocalDate, LocalDate>,
    yBounds: (List<Price>) -> Pair<Currency, Currency>,
    color: Color = Color.Black,
    style: DrawStyle = Stroke(width = 3.dp.toPx()),
    size: Size = this.size,
) {

    val path = data.toSeries(
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
        size = size
    )

    drawPath(
        path = path,
        color = color,
        style = style
    )

}

fun DrawScope.drawCurrencyAxis(
    bounds: Pair<Currency, Currency>,
    data: List<Currency>,
    textMeasurer: TextMeasurer,
    textStyle: TextStyle,
    axisSize: Size = this.size,
    xOffSet: Float = 0f,
) {
    val fixedBounds = bounds.run { if (second < first) second to first else this }

    data.forEach { currency ->
        val yPosition = placementOnYAxis(
            value = currency,
            bounds = fixedBounds,
            relativeValue = { min, value -> (value - min).toFloat() },
            textStyle = textStyle,
            textMeasurer = textMeasurer,
            size = axisSize
        )

        drawText(
            textLayoutResult = currency.toString().toTextLayoutResult(textMeasurer, textStyle),
            topLeft = Offset(xOffSet, yPosition)
        )
    }
}

fun DrawScope.drawDateAxis(
    data: List<LocalDate>,
    bounds: Pair<LocalDate, LocalDate>,
    dateFormatter: DateTimeFormatter = DateTimeFormatter
        .ofPattern("dd/MM/yy"),
    textMeasurer: TextMeasurer,
    textStyle: TextStyle,
    axisSize: Size = this.size,
) {
    val (min, max) = if (bounds.second < bounds.first) bounds.second to bounds.first else bounds
    val relativeValue = { min: LocalDate, value: LocalDate ->
        value.minus(min).toEpochDay().toFloat()
    }

    data.forEach { date ->
        val xPosition = placementOnXAxis(
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

@Preview(showBackground = true)
@Composable
fun LinearChartPreview() {
    PriceChart(
        modifier = Modifier
            .aspectRatio(3 / 2f)
            .padding(MaterialTheme.dimens.mediumPadding),
        data = mockPrices,
        yLabelData = { data ->
            val (min, max) = data.expectedValues()
            val mean = data.average()
            listOf(min, mean, max)
        },
        xLabelData = { data -> data.map { it.date } },
        xBounds = { data -> data.getMinMax { it.date } },
        yBounds = { data -> data.expectedValues(0) },
        textStyle = MaterialTheme.typography.labelSmall,
        pathEffect = PathEffect.dashPathEffect(
            floatArrayOf(10f, 10f),
            0f
        )
    )
}