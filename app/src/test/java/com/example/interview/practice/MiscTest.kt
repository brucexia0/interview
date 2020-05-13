package com.example.interview.practice

import com.example.interview.practice.Misc
import org.junit.Assert.assertEquals
import org.junit.Test

class MiscTest {
    private val underTest = Misc()

    @Test
    fun testLargestNumber() {
        val input1 = intArrayOf(10, 2)
        val output1 = "210"
        val input2 = intArrayOf(3, 30, 34, 5, 9)
        val output2 = "9534330"
        val input3 = intArrayOf(3, 30, 34, 5, 9)
        val output3 = "9534330"
        assertEquals(output1, underTest.largestNumber(input1))
        assertEquals(output2, underTest.largestNumber(input2))
    }

    @Test
    fun testLadderLength() {
        val begin1 = "hit"
        val end1 = "cog"
        val words1 = listOf("hot", "dot", "dog", "lot", "log", "cog")
        assertEquals(5, underTest.ladderLength(begin1, end1, words1))
    }

    @Test
    fun testDivide() {
        assertEquals(3, underTest.divideV1(10, 3))
    }

    @Test
    fun testMultiplyString() {
        val s1 = "33"
        val t1 = "43"
        val s2 = "330"
        val t2 = "11"
        assertEquals("1419", underTest.multiplyString(s1, t1))
        assertEquals("3630", underTest.multiplyString(s2, t2))
    }
}