package com.example.interview.practice

import kotlin.math.max
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

    // Given a string S and a string T, find the minimum window in S which will contain all the characters in T in complexity O(n).
    fun minWindow(s: String, t: String): String {
        if (t.length > s.length) return ""
        val tMap = HashMap<Char, Int>()
        val sMap = HashMap<Char, Int>()
        t.forEach { tMap[it] = tMap.getOrDefault(it, 0) + 1 }

        var start = 0
        var result = s + t//Similiar to set maxLength = Int.MAX_VALUE
        val required = tMap.size
        var filled = 0

        for (end in s.indices) {
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
                        if (tMap[cStart] == cCount) {
                            filled--
                        }
                        if (cCount == 1) sMap.remove(cStart)
                        else sMap[cStart] = cCount - 1
                    }
                    if (filled != required)
                        break
                    if (end - start + 1 < result.length)
                        result = s.substring(start, end + 1)
                }
            }
        }

        return if (result == s + t) "" else result
    }

    fun longestOnes(A: IntArray, K: Int): Int {
        var start = 0
        var end = 0
        var maxLength = 0
        var flips = K
        while (end < A.size) {
            if (A[end] == 1) {
                maxLength = max(maxLength, end - start + 1)
            } else {
                flips--
                if (flips >= 0) {
                    maxLength = max(maxLength, end - start + 1)
                } else {
                    while (start <= end) {
                        if (A[start] == 0 && flips < 0) {
                            start++
                            flips++
                            break
                        }
                        start++
                    }
                }
            }
            end++
        }
        return maxLength
    }

    // check if s2 has any of s1 permutation
    fun checkInclusion(s1: String, s2: String): Boolean {
        val map1 = s1.fold(HashMap<Char, Int>()) { m, e ->
            m[e] = m.getOrDefault(e, 0) + 1
            m
        }
        val map2 = HashMap<Char, Int>()
        for (i in s2.indices) {
            val c = s2[i]
            if (i < s1.length - 1) {
                map2[c] = map2.getOrDefault(c, 0) + 1
            } else {
                map2[c] = map2.getOrDefault(c, 0) + 1

                if (i > s1.length - 1) {
                    val head = s2[i - s1.length]
                    if (map2[head] == 1) map2.remove(head)
                    else
                        map2[head] = map2[head]!! - 1
                }
                if (map1 == map2) return true
            }
        }
        return false
    }

    fun subarrayK(nums: IntArray, k: Int): Boolean {
        var start = 0
        var sum = 0
        for (i in nums.indices) {
            sum += nums[i]
            while (sum > k) {
                sum -= nums[start]
                start++
            }
            if (sum == k) return true
        }
        return false
    }

    fun subarraySum(nums: IntArray, k: Int): Int {
        val sums = HashMap<Int, Int>()
        var count = 0
        var sum = 0
        for (i in nums.indices) {
            sum += nums[i]
            if (sums.containsKey(sum - k)) {
                count += sums[sum - k]!!
            }
            sums[sum] = sums.getOrDefault(sum, 0) + 1
            if (sum == k) count++
        }
        return count
    }
}