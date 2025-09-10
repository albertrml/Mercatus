package com.arml.mercatus.data.model

import java.time.LocalDate
data class Price (
    val date: LocalDate,
    val currency: Currency
): Comparable<Price> {
    override fun compareTo(other: Price): Int {
        return this.currency.value.compareTo(other.currency.value)
    }
}