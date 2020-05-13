package com.example.interview.practice

import org.junit.Test

class BfsTest {
    private val underTest = Bfs()

    @Test
    fun testWallsGates() {
        val s1 = arrayOf(
            intArrayOf(2147483647, -1, 0, 2147483647),
            intArrayOf(2147483647, 2147483647, 2147483647, -1),
            intArrayOf(2147483647, -1, 2147483647, -1),
            intArrayOf(0, -1, 2147483647, 2147483647)
        )
        underTest.wallsAndGates(s1)
        for (arr in s1) {
            for (e in arr) {
                print("$e,")
            }
            println()
        }
    }
}