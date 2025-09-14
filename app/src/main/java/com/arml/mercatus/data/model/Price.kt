package com.arml.mercatus.data.model

import java.math.BigDecimal
import java.time.LocalDate
@ConsistentCopyVisibility
data class Price private constructor(
    val date: LocalDate,
    val currency: Currency
): Comparable<Price> {
    override fun compareTo(other: Price): Int {
        return this.currency.value.compareTo(other.currency.value)
    }
    companion object{
        private fun currencyValidation(currency: Currency){
            if(currency.value < BigDecimal.ZERO)
                throw IllegalArgumentException("Currency value cannot be negative")
        }

        fun of(
            date: LocalDate,
            currency: Currency
        ): Price {
            currencyValidation(currency)
            return Price(date, currency)
        }
    }
}