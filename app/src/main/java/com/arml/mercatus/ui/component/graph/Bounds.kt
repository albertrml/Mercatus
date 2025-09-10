package com.arml.mercatus.ui.component.graph

import com.arml.mercatus.data.model.Currency
import com.arml.mercatus.data.model.Price
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

fun List<Price>.expectedValues(scale: Int = 2): Pair<Currency, Currency> {
    val values = map { it.currency.value.toDouble() }
    val mean = values.average()

    val variance = values.map { (it - mean).pow(2) }.average()
    val stdDev = sqrt(variance)

    val lower = mean - 2 * stdDev
    val upper = mean + 2 * stdDev

    return with(first().currency){
        val lowerCurrency = BigDecimal(lower).setScale(scale, RoundingMode.FLOOR)
        val upperCurrency = BigDecimal(upper).setScale(scale, RoundingMode.CEILING)
        this.withValue(lowerCurrency) to this.withValue(upperCurrency)
    }
}

fun List<Price>.boundsByMeanStd(scale: Int = 2): Pair<Currency, Currency> {
    val currency = first().currency
    val xs = map { it.currency.value.toDouble() }
    val mu = xs.average()

    val sigma = sqrt(xs.map { (it - mu).pow(2) }.average())
    if (sigma == 0.0) {
        val v = BigDecimal(mu).setScale(scale, RoundingMode.HALF_UP)
        val bound = currency.withValue(v)
        return bound to bound
    }

    val k = xs.maxOf { abs(it - mu) / sigma }
    val l = mu - k * sigma
    val u = mu + k * sigma

    return with(currency){
        val lowerBoundCurrency = BigDecimal(l).setScale(scale, RoundingMode.FLOOR)
        val upperBoundCurrency = BigDecimal(u).setScale(scale, RoundingMode.CEILING)
        withValue(lowerBoundCurrency) to withValue(upperBoundCurrency)
    }
}

fun List<Price>.average(scale: Int = 2): Currency{
    val currency = first().currency
    val mean = map { it.currency.value.toDouble() }.average()
    val result = BigDecimal(mean).setScale(scale, RoundingMode.HALF_UP)
    return currency.withValue(result)
}