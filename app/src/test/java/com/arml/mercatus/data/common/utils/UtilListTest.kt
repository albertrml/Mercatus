package com.arml.mercatus.data.common.utils

import com.arml.mercatus.data.common.utils.collection.getMinMax
import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class UtilListTest {

    @Test
    fun `getMaxMin should return correct min and max values when list has more than one element`(){
        val list = listOf(1, 2, 3, 4, 5)
        val (min, max) = list.getMinMax { it }
        assertEquals(1,min)
        assertEquals(5,max)
    }

    @Test
    fun `getMaxMin should the same element when list has only one element`(){
        val list = listOf(1)
        val (min, max) = list.getMinMax { it }
        assertEquals(1,min)
        assertEquals(1,max)
    }

    @Test
    fun `getMaxMin should throw exception when list is empty`(){
        val list = emptyList<Int>()
        assertThrows(NoSuchElementException::class.java) {
            list.getMinMax { it }
        }
    }
}