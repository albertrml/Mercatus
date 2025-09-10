package com.arml.mercatus.data.common.utils

import java.time.LocalDate
import java.time.temporal.ChronoUnit

operator fun LocalDate.minus(from: LocalDate): LocalDate{
    return LocalDate.ofEpochDay(
        ChronoUnit.DAYS.between(from,this)
    )
}