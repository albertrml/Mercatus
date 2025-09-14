package com.arml.mercatus.ui.component.series

import com.arml.mercatus.data.model.Currency
import com.arml.mercatus.data.model.Price
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

fun List<Price>.boundsByStdDev(scale: Int = 2): Pair<Currency, Currency> {
    val currency = first().currency
    val values = map { it.currency.toDouble() }

    val mean = values.average()
    val variance = values.map { (it - mean).pow(2) }.average()
    val stdDev = sqrt(variance)

    if (stdDev == 0.0) {
        val scaledValue = BigDecimal(mean).setScale(scale, RoundingMode.HALF_UP)
        val bound = currency.withValue(scaledValue)
        return bound to bound
    }

    // Maximum number of standard deviations to include in the bounds
    val maxZScoreMagnitude = values.maxOf { abs(it - mean) / stdDev }

    val lowerBound = mean - maxZScoreMagnitude * stdDev
    val upperBound = mean + maxZScoreMagnitude * stdDev

    return with(currency){
        val lowerBoundCurrency = BigDecimal(lowerBound)
            .setScale(scale, RoundingMode.FLOOR)
        val upperBoundCurrency = BigDecimal(upperBound)
            .setScale(scale, RoundingMode.CEILING)
        withValue(lowerBoundCurrency) to withValue(upperBoundCurrency)
    }
}

fun List<Price>.meanCurrency(scale: Int = 2): Currency{
    val currency = first().currency
    val mean = map { it.currency.toDouble() }.average()
    val result = BigDecimal(mean).setScale(scale, RoundingMode.HALF_UP)
    return currency.withValue(result)
}