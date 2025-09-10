package com.arml.mercatus.data.model

import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.text.format

sealed class Currency(
    val symbol: String,
    val name: String,
    open val value: BigDecimal
): Comparable<Currency> {
    override fun toString(): String {
        return "$symbol ${formatValue()}"
    }

    operator fun minus(other: Currency): Currency {
        val diff = value - other.value
        return withValue(diff)
    }

    operator fun plus(other: Currency): Currency {
        val diff = value + other.value
        return withValue(diff)
    }

    operator fun div(other: Currency): Currency {
        val diff = value / other.value
        return withValue(diff)
    }

    operator fun times(other: Currency): Currency {
        val diff = value * other.value
        return withValue(diff)
    }

    override operator fun compareTo(other: Currency): Int {
        return value.compareTo(other.value)
    }

    fun toFloat(): Float {
        return value.toFloat()
    }

    fun formatValue(scale: Int = 2): String {
        val scaledValue = value.setScale(scale, RoundingMode.HALF_UP)
        return String.format("%.${scale}f", scaledValue)
    }

    fun withValue(value: BigDecimal): Currency {
        return when (this) {
            is BrazilianReal -> BrazilianReal(value)
            is AmericanDollar -> AmericanDollar(value)
            is Euro -> Euro(value)
        }
    }
}

class BrazilianReal(
    override val value: BigDecimal
) : Currency(
    symbol = "R$",
    name = "Real",
    value = value
)

data class AmericanDollar(
    override val value: BigDecimal
) : Currency(
    symbol = "US$",
    name = "Dollar",
    value = value
)

data class Euro(
    override val value: BigDecimal
) : Currency (
    symbol = "€",
    name = "Euro",
    value = value
)