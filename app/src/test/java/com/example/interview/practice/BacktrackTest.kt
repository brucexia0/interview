package com.example.interview.practice

import org.junit.Assert.*
import org.junit.Test

class BacktrackTest {
    private val underTest = Backtrack()

    @Test
    fun testWordExist() {
        val grid1 = arrayOf(
            charArrayOf('A', 'B', 'C', 'E'), charArrayOf('S', 'F', 'C', 'S'),
            charArrayOf('A', 'D', 'E', 'E')
        )
        val word1 = "SEE"
        assertTrue(underTest.exist(grid1, word1))
    }

    @Test
    fun testNQueens() {
        assertEquals(2, underTest.totalNQueens(4))
    }

    @Test
    fun testCombinationSum3() {
        val e1 = listOf(listOf(1, 2, 4))
        assertEquals(e1, underTest.combinationSum3(3, 7))
    }

    @Test
    fun testGetFactor() {
        val e1 = listOf(listOf(2, 3))
        assertEquals(e1, underTest.getFactors(6))
    }

    @Test
    fun testIsAdditiveNumber() {
        val s1 = "111"
        assertFalse(underTest.isAdditiveNumber(s1))
    }

    @Test
    fun testWordSquares() {
        val w1 = arrayOf("area", "lead", "wall", "lady", "ball")
        val e1 = listOf("wall", "area", "lead", "lady")
        val e2 = listOf("ball", "area", "lead", "lady")
        val w2 = arrayOf("ab", "ba", "cd")
        val e21 = listOf("ab", "ba")
        val e22 = listOf("ba", "ab")
        assertEquals(underTest.wordSquares(w1), listOf(e1, e2))
//        assertEquals(underTest.wordSquares(w2), listOf(e21, e22))
    }

    @Test
    fun testSplitFibonacci() {
        val s1 = "11112"
        val r1 = underTest.splitIntoFibonacci(s1)
        println("$r1")
    }
}