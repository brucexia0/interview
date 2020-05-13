package com.example.interview

fun IntArray.swap(i: Int, j: Int) {
    val tmp = this[i]
    this[i] = this[j]
    this[j] = tmp
}
val STEPS = arrayOf(
    Pair(-1, 0),
    Pair(1, 0),
    Pair(0, -1),
    Pair(0, 1)
)