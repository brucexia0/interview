package com.example.interview.init

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class FBTest {
    private val underTest = FB()

    @Test
    fun testFindTarget() {
        val a1 = intArrayOf(1, 3, 1, 4, 23)
        val a2 = intArrayOf(1, 3, 1, 8, 23)
        val a3 = intArrayOf(1, 3, 1, 9, 1, 7, 23)
        val a4 = intArrayOf(1, 3, 1, 9, 7, 23)
        val a5 = intArrayOf(1, 3, 1, 9, 7, 1)
        val a6 = intArrayOf()
        val t1 = 8
        assertTrue(underTest.findTarget(a1, t1))
        assertTrue(underTest.findTarget(a2, t1))
        assertTrue(underTest.findTarget(a3, t1))
        assertFalse(underTest.findTarget(a4, t1))
        assertTrue(underTest.findTarget(a5, t1))
        assertFalse(underTest.findTarget(a6, t1))
    }
}