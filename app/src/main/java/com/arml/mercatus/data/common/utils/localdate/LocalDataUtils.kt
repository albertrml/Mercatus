package com.arml.mercatus.data.common.utils.localdate

import java.time.LocalDate
import java.time.temporal.ChronoUnit

operator fun LocalDate.minus(from: LocalDate): Long{
    return ChronoUnit.DAYS.between(from,this)
}