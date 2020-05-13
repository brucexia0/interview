package com.example.interview.practice

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
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
}