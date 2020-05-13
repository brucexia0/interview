package com.example.interview.practice

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GraphsTest {
    private val underTest = Graphs()

    @Test
    fun testFindOrder() {
        val s1 = arrayOf(intArrayOf(1, 0))
        assertTrue(intArrayOf(0, 1).contentEquals(underTest.findOrder(2, s1)))
    }
}