package com.arml.mercatus.ui.component.chart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arml.mercatus.data.common.utils.collection.getMinMax
import com.arml.mercatus.data.mock.mockPrices
import com.arml.mercatus.ui.component.graph.ChartLegends
import com.arml.mercatus.ui.component.graph.PriceChart
import com.arml.mercatus.ui.component.graph.average
import com.arml.mercatus.ui.component.graph.expectedValues
import com.arml.mercatus.ui.theme.dimens

@Preview(showBackground = true)
@Composable
fun Chart2DPreview() {
    ChartLegends(
        modifier = Modifier
            .aspectRatio(1f),
        title = {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.dimens.smallPadding),
                    style = MaterialTheme.typography.titleMedium,
                    text = "Gráfico de Teste",
                    textAlign = TextAlign.Center
                )
            }
        },
        legend = {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.dimens.smallPadding),
                    style = MaterialTheme.typography.titleSmall,
                    text = "Figura 1: Gráfico de Teste que avalia o preço do café ao longo do tempo",
                    textAlign = TextAlign.Center
                )
            }
        },
        xAxis = {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.dimens.smallPadding),
                    style = MaterialTheme.typography.titleSmall,
                    text = "Data",
                    textAlign = TextAlign.Center
                )
            }
        },
        yAxis = {
            Column(
                Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .rotate(-90f),
                    style = MaterialTheme.typography.titleSmall,
                    text = "Preço",
                    textAlign = TextAlign.Center
                )
            }
        }
    ) {
        PriceChart(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 12.dp,
                    start = 12.dp
                ),
            data = mockPrices,
            yLabelData = { data ->
                val (min, max) = data.expectedValues()
                val mean = data.average()
                listOf(min, mean, max)
            },
            xLabelData = { data -> data.map { it.date }
            },
            xBounds = { data ->
                data.getMinMax { it.date }
            },
            yBounds = { data ->
                data.expectedValues(0)
            },
            textStyle = MaterialTheme.typography.labelSmall,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        )
    }
}

