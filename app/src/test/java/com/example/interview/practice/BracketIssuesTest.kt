package com.example.interview.practice

import org.junit.Assert.assertEquals
import org.junit.Test

class BracketIssuesTest {
    val underTest = BracketIssues()

    @Test
    fun testMinRemoveToMakeValid() {
        val s1 = "a)b(c)d"
        val r1 = "ab(c)d"
        assertEquals(r1, underTest.minRemoveToMakeValid(s1))
    }
}