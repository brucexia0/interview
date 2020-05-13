package com.example.interview

import com.example.interview.practice.SearchNSort
import org.junit.Assert.assertEquals
import org.junit.Test

class SearchNSortTest {
    private val underTest = SearchNSort()

    @Test
    fun testLongestDupString() {
        val s1 = "banana"
        val e1 = "ana"
        assertEquals(e1, underTest.longestDupSubstring(s1))
    }

    @Test
    fun testMaxProfit() {
        val a1 = intArrayOf(3, 2, 6, 5, 0, 3)
        val a2 = intArrayOf(3, 3, 5, 0, 0, 3, 1, 4)
        val p1 = 7
        val p2 = 6
        assertEquals(p1, underTest.maxProfit(2, a1))
        assertEquals(p2, underTest.maxProfit(2, a2))
    }

    @Test
    fun testMaxValue() {
        val max: Int = Int.MAX_VALUE
        val maxJava: Int = Integer.MAX_VALUE
        val min: Int = Int.MIN_VALUE
        val minJava: Int = Integer.MIN_VALUE
        assertEquals(max, maxJava)
        assertEquals(min, minJava)
    }
}