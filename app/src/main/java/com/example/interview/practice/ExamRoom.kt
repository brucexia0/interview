package com.example.interview.practice

import java.util.*
import kotlin.math.max

class ExamRoom(val N: Int) {
    private val seated = TreeSet<Int>()

    fun seat(): Int {
        if (seated.isEmpty()) {
            seated.add(0)
            return 0
        }
        var maxDist = seated.first()
        var seatNum = 0
        var prev: Int? = null

        for (seat in seated.iterator()) {
            prev?.let {
                val dist = (seat - it) / 2
                if (dist > maxDist) {
                    maxDist = dist
                    seatNum = it + dist
                }
            }
            prev = seat
        }
        if (N - 1 - seated.last() > maxDist) {
            seatNum = N - 1
        }
        seated.add(seatNum)
        return seatNum
    }

    fun leave(p: Int) {
        seated.remove(p)
    }

}
