package com.arml.mercatus.ui.component.chart.exception

sealed class ChartException(override val message: String): Exception() {
    class IllegalHeightChartException : ChartException(
        "The Chart Height must be greater than 0"
    )
    class IllegalWidthChartException : ChartException(
        "The Chart Width must be greater than 0"
    )
}