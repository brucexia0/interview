package com.example.interview.practice

import com.example.interview.practice.Trie
import org.junit.Assert.assertEquals
import org.junit.Test

class BitwiseTrieTest {
    private val underTest = Trie()

    @Test
    fun testMaxXor() {
        val a1 = intArrayOf(3, 10, 5, 25, 2, 8)
        val res1 = 28
        assertEquals(res1, underTest.findMaximumXOR(a1))
    }
}