package com.example.interview.practice

import java.lang.StringBuilder
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.max

class Strings {
    fun myAtoi(str: String): Int {
        var result = 0
        var isNegative = false
        var signFound = false
        loop@ for (c in str) {
            when (c) {
                ' ' -> {
                    if (result != 0) break@loop
                }
                '+' -> if (result != 0 || signFound) break@loop else signFound = true
                '-' -> {
                    if (result == 0 && !signFound) {
                        isNegative = true
                        signFound = true
                    } else {
                        break@loop
                    }
                }
                in '0'..'9' -> {
                    val cVal = c - '0'
                    if (isNegative) {
                        if (-result < (Int.MIN_VALUE + cVal) / 10) return Int.MIN_VALUE
                    } else {
                        if ((Int.MAX_VALUE - cVal) / 10 < result) return Int.MAX_VALUE
                    }
                    result = result * 10 + cVal
                }
                else -> {
                    break@loop
                }
            }
        }
        return if (isNegative) -result else result
    }

    // Given a string, find the length of the longest substring T that contains at most k distinct characters.
    // Solution: Sliding window + HashMap.
    fun lengthOfLongestSubstringKDistinctV1(s: String, k: Int): Int {
        if (s.isEmpty() || k == 0) return 0
        val map = HashMap<Char, Int>()
        var start = 0
        var end = 0
        var maxLength = 0
        while (start <= end && end < s.length) {
            val c = s[end]
            if (map.size < k || (map.size == k && map.containsKey(c))) {
                end++
                map[c] = map.getOrDefault(c, 0) + 1
                maxLength = max(maxLength, end - start)
            } else {
                val startChar = s[start]
                val startCount = map[startChar]!!
                if (startCount == 1) {
                    map.remove(startChar)
                } else {
                    map.put(startChar, startCount - 1)
                }
                start++
            }
        }
        return maxLength
    }

    fun lengthOfLongestSubstringKDistinct(s: String, k: Int): Int {
        if (s.isEmpty() || k == 0) return 0
        val map = HashMap<Char, Int>()
        var start = 0
        var maxLength = 0
        for (end in s.indices) {
            val c = s[end]
            map[c] = map.getOrDefault(c, 0) + 1
            while (map.size > k) {
                val head = s[start]
                if (map[head] == 1) {
                    map.remove(head)
                } else {
                    map[head] = map.getOrDefault(head, 0) - 1
                }
                start++
            }
            maxLength = max(maxLength, end - start + 1)
        }
        return maxLength
    }


    fun reorganizeString(s: String): String {
        return rearrangeStringKDistance(s, 2)
    }

    fun rearrangeStringKDistance(s: String, k: Int): String {
        val size = s.length
        val list = s.chars().toArray().groupBy { it }.map { (k, v) -> k to v.size }
            .sortedBy { it.second }
        val result = CharArray(size)
        var t = k - 1
        for ((num, count) in list) {
            if (count > (size + k - 1) / k) return ""
            for (i in 0 until count) {
                result[t] = num.toChar()
                t += k
                if (t >= size) t = (t + 1) % k
            }
        }
        return String(result)
    }

    fun groupStrings(strings: Array<String>): List<List<String>> {
        val result = HashMap<String, List<String>>()
        strings.forEach {
            val normalized = it.normalize()
            val list = result.getOrDefault(normalized, ArrayList()).toMutableList()
            list.add(it)
            result[normalized] = list
        }
        return result.values.toList()
    }

    private fun String.normalize(): String {
        val diff = this[0] - 'a'
        val sb = StringBuilder()
        for (c in this) {
            sb.append('a' + (c - diff + 26).toInt() % 26)
        }
        return sb.toString()
    }

    // https://leetcode.com/problems/longest-absolute-file-path/
    fun lengthLongestPath(input: String): Int {
        val stack = ArrayDeque<Int>()
        stack.push(0)
        var maxLength = 0
        for (s in input.split("\n")) {
            val level = s.lastIndexOf("\t") + 1
            while (stack.size > level + 1) stack.pop()
            val length = stack.peek() + s.length - level + 1
            stack.push(length)
            if (s.contains(".")) maxLength = max(maxLength, length - 1)
        }
        return maxLength
    }
}