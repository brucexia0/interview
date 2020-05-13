package com.example.interview.practice

import kotlin.math.min

class SlidingWindow {
    // Return all start indices of p in s
    fun findAnagrams(s: String, p: String): List<Int> {
        if (p.length > s.length) return emptyList()
        val result = ArrayList<Int>()
        val pLen = p.length
        val mapOfChars = HashMap<Char, Int>()
        p.forEach { mapOfChars[it] = mapOfChars.getOrDefault(it, 0) + 1 }

        val map = HashMap<Char, Int>()
        for (start in s.indices) {
            val c = s[start]
            map[c] = map.getOrDefault(c, 0) + 1
            if (start >= pLen) {
                val startC = s[start - pLen]
                if (map[startC] == 1) map.remove(startC)
                else map[startC] = map[startC]!! - 1
            }
            if (map == mapOfChars) result.add(start - pLen + 1)

        }

        return result
    }

    fun minWindow(s: String, t: String): String {
        if (t.length > s.length) return ""
        val tMap = HashMap<Char, Int>()
        val sMap = HashMap<Char, Int>()
        t.forEach { tMap[it] = tMap.getOrDefault(it, 0) + 1 }

        var start = 0
        var end = 0
        var result = s + t//Similiar to set maxLength = Int.MAX_VALUE
        val required = tMap.size
        var filled = 0

        while (end < s.length) {
            val c = s[end]
            if (tMap.containsKey(c)) {//Only need to add the key if it's one that we need
                sMap[c] = sMap.getOrDefault(c, 0) + 1
                if (tMap[c]!! == sMap[c]!!) {
                    filled++
                }
            }
            if (filled == required) {
                if (end - start + 1 < result.length) {
                    result = s.substring(start, end + 1)
                }
                while (start <= end) {//move left bracket forward to reduce the window until we break the result
                    val cStart = s[start]
                    start++
                    if (sMap.containsKey(cStart)) {
                        val cCount = sMap[cStart]!!
                        if (cCount == 1) sMap.remove(cStart)
                        else sMap[cStart] = cCount - 1
                        if (tMap[cStart] == cCount) {
                            filled--
                        }
                    }
                    if (filled != required)
                        break
                    if (end - start + 1 < result.length)
                        result = s.substring(start, end + 1)
                }
            }
            end++
        }

        return if (result == s + t) "" else result
    }
}