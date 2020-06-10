package com.example.interview.practice

import java.util.*

class MedianFinder {
    val lower = PriorityQueue<Int> { o1, o2 -> o2.compareTo(o1) }
    val higher = PriorityQueue<Int>()
    fun addNum(num: Int) {
        lower.add(num)
        val biggest = lower.poll()
        higher.offer(biggest)
        if (higher.size > lower.size) {
            lower.offer(higher.poll())
        }
    }

    fun findMedian(): Double {
        return if (lower.size > higher.size) lower.peek() * 1.0
        else (lower.peek() + higher.peek()) / 2.0
    }
}