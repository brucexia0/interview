package com.example.interview.practice

import com.example.interview.practice.DP
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class DPTest {
    private val underTest = DP()

    @Test
    fun testDecodeWays() {
        val input1 = "226"
        val input2 = "206"
        val input3 = "2066"
        val input4 = "00"
        val input5 = "27"
        val input6 = "05"
        val input7 = "230"
        val input8 = "10"
        val input9 = "301"
        assertEquals(3, underTest.numDecodings(input1))
        assertEquals(1, underTest.numDecodings(input2))
        assertEquals(1, underTest.numDecodings(input3))
        assertEquals(0, underTest.numDecodings(input4))
        assertEquals(1, underTest.numDecodings(input5))
        assertEquals(0, underTest.numDecodings(input6))
        assertEquals(0, underTest.numDecodings(input7))
        assertEquals(1, underTest.numDecodings(input8))
        assertEquals(0, underTest.numDecodings(input9))
    }

    @Test
    fun testCheckArraySum() {
        val a1 = intArrayOf(23, 2, 4, 6, 7)
        val a2 = intArrayOf(0, 1, 0)
        val k1 = 6
        val k2 = 0
        assertEquals(false, underTest.checkSubarraySum(a2, k2))
        assertEquals(true, underTest.checkSubarraySum(a1, k1))
    }

    @Test
    fun testLongestPalindrome() {
        val s1 = "caba"
        val s2 = "cbbd"
        assertEquals("bb", underTest.longestPalindrome(s2))
    }

    @Test
    fun testWildCardMatch() {
        val s1 = "aa"
        val s2 = "adk"
        val p1 = "a"
        val p2 = "a?k"
        assertTrue(underTest.isMatch(s2, p2))
        assertFalse(underTest.isMatch(s1, p1))
    }

    @Test
    fun testMaxSumOfThreeSubarrays() {
        val s1 = intArrayOf(4, 5, 10, 6, 11, 17, 4, 11, 1, 3)
        val k1 = 1
        val s2 = intArrayOf(1, 2, 1, 2, 6, 7, 5, 1)
        val k2 = 2
        val s3 = intArrayOf(1, 2, 1, 2, 1, 2, 1, 2, 1)
        val k3 = 2
        assertTrue(intArrayOf(4, 5, 7).contentEquals(underTest.maxSumOfThreeSubarrays(s1, k1)))
    }

    @Test
    fun randomTest() {
        val map1 = TreeMap<Long, String>()
        val map2 = TreeMap<Long, String>()
        map1[1] = "1"
        map1[2] = "2"
        map2[2] = "2"

        val map3 = map1.keys.intersect(map2.keys)
        map3.forEach { print(it) }
        println()
    }
}