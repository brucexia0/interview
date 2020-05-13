package com.example.interview

import com.example.interview.practice.Permutation
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PermutationTest {
    private val underTest = Permutation()

    @Test
    fun testNextPermutation() {
        val input1 = intArrayOf(1, 3, 2)
        val res1 = intArrayOf(2, 1, 3)
        underTest.nextPermutation(input1)
        assertTrue(res1.contentEquals(input1))
    }

    @Test
    fun testAddBinary() {
        val a1 = "11"
        val b1 = "1"
        val res1 = "100"
        assertEquals(res1, underTest.addBinary(a1, b1))
    }

    @Test
    fun testWordBreak() {
        val input1 = "Leetcode"
        val words1 = listOf("Leet", "code")
        assertTrue(underTest.wordBreak(input1, words1))
    }

    @Test
    fun testWordBreak2() {
        val input1 = "Leetcode"
        val words1 = listOf("Leet", "code")
        val res1 = listOf( "Leet code")
        assertEquals(res1,underTest.wordBreak2(input1, words1))
    }
}