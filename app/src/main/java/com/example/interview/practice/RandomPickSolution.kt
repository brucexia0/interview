package com.example.interview.practice

import kotlin.math.roundToInt

class RandomPickSolution(val w: IntArray) {
    val prefixSum = IntArray(w.size)

    init {
        w.forEachIndexed { index, i -> (if (index == 0) 0 else prefixSum[index - 1]) + i }
    }

    fun pickIndex(): Int {
        val r = (prefixSum.last() * Math.random()).roundToInt()
        val index = prefixSum.binarySearch(r)
        return r
    }
}