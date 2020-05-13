package com.example.interview.practice

import org.junit.Assert.assertEquals
import org.junit.Test

class StringsTest {
    private val underTest = Strings()

    @Test
    fun testAtoi() {
        val s1 = "42"
        val s2 = "  -42"
        val s3 = "  -428739737297379227729"
        val s4 = "  428739737297379227729"
        val s5 = " +-42"
        assertEquals(-42, underTest.myAtoi(s2))
        assertEquals(42, underTest.myAtoi(s1))
        assertEquals(Int.MIN_VALUE, underTest.myAtoi(s3))
        assertEquals(Int.MAX_VALUE, underTest.myAtoi(s4))
        assertEquals(0, underTest.myAtoi(s5))
    }

    @Test
    fun testRearrangeStringKDistance() {
        val s1 = "aabbcc"
        assertEquals("abcabc", underTest.rearrangeStringKDistance(s1, 3))
    }
}