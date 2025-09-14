package com.arml.mercatus.data.common.utils

import com.arml.mercatus.data.common.utils.localdate.minus
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateUtilsTest {
    @Test
    fun `minus operator should return correct LocalDate when subtracting two LocalDates`(){
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val date1 = LocalDate.parse("01/01/2022", formatter)
        val date2 = LocalDate.of(2022, 1, 10)
        val result = date2-date1
        assertEquals(9, result)
    }

    @Test
    fun `minus operator should return correct LocalDate when subtracting two LocalDates with different years`(){
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val date1 = LocalDate.parse("01/01/2022", formatter)
        val date2 = LocalDate.of(2023, 1, 1)
        val result = date2-date1
        assertEquals(365, result)

    }
}