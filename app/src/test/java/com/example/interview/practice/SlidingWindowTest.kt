package com.example.interview.practice

import org.junit.Assert.assertEquals
import org.junit.Test

class SlidingWindowTest {
    private val underTest = SlidingWindow()

    @Test
    fun testFindAnagrams() {
        val s1 = "bcad"
        val p1 = "abc"
        assertEquals(listOf(0), underTest.findAnagrams(s1, p1))
    }

    @Test
    fun testMinWindow() {
        val s1 = "ADOBECODEBANC"
        val s2 = "aab"
        val s3 = "cabwefgewcwaefgcf"
        val t3 = "cae"
        val t2 = "aab"
        val t1 = "ABC"
        assertEquals("cwae", underTest.minWindow(s3, t3))
        assertEquals("aab", underTest.minWindow(s2, t2))
        assertEquals("BANC", underTest.minWindow(s1, t1))
    }

    @Test
    fun testLongestOnes() {
        val a1 = intArrayOf(1, 0, 0, 0, 1, 1, 0)
        val k1 = 2
        assertEquals(4, underTest.longestOnes(a1, k1))
    }
}