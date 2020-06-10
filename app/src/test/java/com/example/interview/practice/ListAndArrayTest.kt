package com.example.interview.practice

import com.example.interview.practice.ListAndArray
import org.junit.Assert.assertEquals
import org.junit.Test

class ListAndArrayTest {
    private val underTest = ListAndArray()

    @Test
    fun testSearchRotatedArray() {
        val a1 = intArrayOf(4, 5, 6, 7, 0, 1, 2)
        val t1 = 6
        val a2 = intArrayOf(1, 3)
        val t2 = 3
        val a3 = intArrayOf(3, 1)
        val t3 = 3
        val a4 = intArrayOf(4, 5, 1, 2, 3)
        val t4 = 1
        val a5 = intArrayOf(3, 1)
        val t5 = 1
        val a6 = intArrayOf(8, 9, 2, 3)
        val t6 = 9
        assertEquals(1, underTest.searchRotatedSortedArray(a6, t6))
        assertEquals(2, underTest.searchRotatedSortedArray(a4, t4))
        assertEquals(2, underTest.searchRotatedSortedArray(a1, t1))
        assertEquals(1, underTest.searchRotatedSortedArray(a2, t2))
        assertEquals(0, underTest.searchRotatedSortedArray(a3, t3))
        assertEquals(1, underTest.searchRotatedSortedArray(a5, t5))
    }

    @Test
    fun testShortestSubarray() {
        val a1 = intArrayOf(1)
        val k1 = 1

        val a2 = intArrayOf(2, 4, 5)
        val k2 = 5
        assertEquals(1, underTest.shortestSubarray(a2, k2))
        assertEquals(1, underTest.shortestSubarray(a1, k1))
    }

    @Test
    fun testLeastIntervals() {
        val a1 = charArrayOf('A', 'A', 'A', 'B', 'B', 'B')
        val n1 = 2
        assertEquals(8, underTest.leastInterval(a1, n1))
    }

    @Test
    fun testKthSmallest() {
        val a = arrayOf(intArrayOf(1, 2), intArrayOf(1, 3))
        assertEquals(2, underTest.kthSmallest(a, 3))
    }
}