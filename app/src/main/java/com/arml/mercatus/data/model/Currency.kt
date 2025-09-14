package com.arml.mercatus.data.model

import com.arml.mercatus.data.common.exception.CurrencyException.*
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import kotlin.text.format

sealed class Currency (
    val symbol: String,
    val name: String,
    open val value: BigDecimal
): Comparable<Currency> {

    fun formatValue(scale: Int = 2): String {
        val scaledValue = value.setScale(scale, RoundingMode.HALF_UP)
        return String.format("%.${scale}f", scaledValue)
    }

    fun toFloat(): Float {
        return value.toFloat()
    }

    fun toDouble(): Double {
        return value.toDouble()
    }

    fun withValue(value: BigDecimal): Currency {
        return when (this) {
            is BrazilianReal -> BrazilianReal(value.setScale(2, RoundingMode.HALF_UP))
            is AmericanDollar -> AmericanDollar(value.setScale(2, RoundingMode.HALF_UP))
            is Euro -> Euro(value.setScale(2, RoundingMode.HALF_UP))
        }
    }

    operator fun div(other: Currency): Currency {
        if (this::class != other::class) throw IllegalMathOperationException()
        val result = value.divide(
            other.value,
            MathContext.DECIMAL32
        ).setScale(2, RoundingMode.HALF_UP)
        return withValue(result)
    }

    operator fun minus(other: Currency): Currency {
        if (this::class != other::class) throw IllegalMathOperationException()
        val result = value.subtract(
            other.value,
            MathContext.DECIMAL32
        ).setScale(2, RoundingMode.HALF_UP)
        return withValue(result)
    }

    operator fun plus(other: Currency): Currency {
        if (this::class != other::class) throw IllegalMathOperationException()
        val result = value.add(
            other.value,
            MathContext.DECIMAL32
        ).setScale(2, RoundingMode.HALF_UP)
        return withValue(result)
    }

    operator fun times(other: Currency): Currency {
        if (this::class != other::class) throw IllegalMathOperationException()
        val result = value.multiply(
            other.value,
            MathContext.DECIMAL32
        ).setScale(2, RoundingMode.HALF_UP)
        return withValue(result)
    }

    override operator fun compareTo(other: Currency): Int {
        return value.compareTo(other.value)
    }

    override fun toString(): String {
        return "$symbol ${formatValue()}"
    }
}

data class AmericanDollar(
    override val value: BigDecimal
) : Currency(
    symbol = "US$",
    name = "Dollar",
    value = value
){
    override fun toString(): String {
        return "$symbol ${formatValue()}"
    }
}

data class BrazilianReal (
    override val value: BigDecimal
) : Currency(
    symbol = "R$",
    name = "Real",
    value = value
){
    override fun toString(): String {
        return "$symbol ${formatValue()}"
    }
}

data class Euro(
    override val value: BigDecimal
) : Currency (
    symbol = "€",
    name = "Euro",
    value = value
){
    override fun toString(): String {
        return "$symbol ${formatValue()}"
    }
}