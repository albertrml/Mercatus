package com.arml.mercatus.data.mock

import com.arml.mercatus.data.model.BrazilianReal
import com.arml.mercatus.data.model.Price
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate

fun real(value: String) = BrazilianReal(
    BigDecimal(value).setScale(2, RoundingMode.CEILING)
)
/*val mockPrices = listOf(
    Price.of(LocalDate.parse("2024-08-31"), real("32.97")),
    Price.of(LocalDate.parse("2024-09-07"), real("32.85")),
    Price.of(LocalDate.parse("2024-09-14"), real("32.85")),
    Price.of(LocalDate.parse("2024-09-21"), real("33.08")),
    Price.of(LocalDate.parse("2024-09-28"), real("33.34")),
    Price.of(LocalDate.parse("2024-10-05"), real("32.94")),
    Price.of(LocalDate.parse("2024-10-12"), real("32.98")),
    Price.of(LocalDate.parse("2024-10-19"), real("33.47")),
    Price.of(LocalDate.parse("2024-10-26"), real("33.31")),
    Price.of(LocalDate.parse("2024-11-02"), real("33.03")),
    Price.of(LocalDate.parse("2024-11-09"), real("33.10")),
    Price.of(LocalDate.parse("2024-11-16"), real("33.13")),
    Price.of(LocalDate.parse("2024-11-23"), real("33.34")),
    Price.of(LocalDate.parse("2024-11-30"), real("32.82")),
    Price.of(LocalDate.parse("2024-12-07"), real("32.89")),
    Price.of(LocalDate.parse("2024-12-14"), real("33.19")),
    Price.of(LocalDate.parse("2024-12-21"), real("33.09")),
    Price.of(LocalDate.parse("2024-12-28"), real("33.43")),
    Price.of(LocalDate.parse("2025-01-04"), real("33.12")),
    Price.of(LocalDate.parse("2025-01-11"), real("33.00")),
    Price.of(LocalDate.parse("2025-01-18"), real("33.71")),
    Price.of(LocalDate.parse("2025-01-25"), real("33.28")),
    Price.of(LocalDate.parse("2025-02-01"), real("33.34")),
    Price.of(LocalDate.parse("2025-02-08"), real("32.94")),
    Price.of(LocalDate.parse("2025-02-15"), real("33.14")),
    Price.of(LocalDate.parse("2025-02-22"), real("33.28")),
    Price.of(LocalDate.parse("2025-03-01"), real("32.94")),
    Price.of(LocalDate.parse("2025-03-08"), real("33.29")),
    Price.of(LocalDate.parse("2025-03-15"), real("33.02")),
    Price.of(LocalDate.parse("2025-03-22"), real("33.06")),
    Price.of(LocalDate.parse("2025-03-29"), real("32.95")),
    Price.of(LocalDate.parse("2025-04-05"), real("33.53")),
    Price.of(LocalDate.parse("2025-04-12"), real("33.03")),
    Price.of(LocalDate.parse("2025-04-19"), real("32.74")),
    Price.of(LocalDate.parse("2025-04-26"), real("33.18")),
    Price.of(LocalDate.parse("2025-05-03"), real("32.64")),
    Price.of(LocalDate.parse("2025-05-10"), real("32.97")),
    Price.of(LocalDate.parse("2025-05-17"), real("32.40")),
    Price.of(LocalDate.parse("2025-05-24"), real("32.54")),
    Price.of(LocalDate.parse("2025-05-31"), real("32.90")),
    Price.of(LocalDate.parse("2025-06-07"), real("33.03")),
    Price.of(LocalDate.parse("2025-06-14"), real("32.88")),
    Price.of(LocalDate.parse("2025-06-21"), real("32.80")),
    Price.of(LocalDate.parse("2025-06-28"), real("32.75")),
    Price.of(LocalDate.parse("2025-07-05"), real("32.46")),
    Price.of(LocalDate.parse("2025-07-12"), real("32.66")),
    Price.of(LocalDate.parse("2025-07-19"), real("32.74")),
    Price.of(LocalDate.parse("2025-07-26"), real("33.13")),
    Price.of(LocalDate.parse("2025-08-02"), real("32.97")),
    Price.of(LocalDate.parse("2025-08-09"), real("32.47")),
    Price.of(LocalDate.parse("2025-08-16"), real("33.02")),
    Price.of(LocalDate.parse("2025-08-23"), real("32.87"))
)*/

val mockPrices = listOf(
    Price.of(LocalDate.parse("2025-07-26"), real("33.13")),
    Price.of(LocalDate.parse("2025-08-02"), real("32.97")),
    Price.of(LocalDate.parse("2025-08-09"), real("32.47")),
    Price.of(LocalDate.parse("2025-08-16"), real("33.02")),
    Price.of(LocalDate.parse("2025-08-23"), real("32.87"))
)
