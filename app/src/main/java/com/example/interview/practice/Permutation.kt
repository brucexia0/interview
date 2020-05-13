package com.example.interview.practice

import com.example.interview.swap
import java.lang.Integer.max
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class Permutation {
    fun nextPermutation(nums: IntArray): Unit {
        val size = nums.size
        if (nums.size < 2) return
        val s = Stack<Int>()
        var i: Int = nums.size - 1
        //Find the first "Ascending" pair of digits. I will store the index of the bigger number
        while (i > 0) {
            if (nums[i] > nums[i - 1]) break
            i--
        }
        if (i == 0) {
            nums.reverse()
            return
        }

        //Find the first number on right of i(including i) that's bigger than nums[i-1]
        for (j in nums.size - 1 downTo i) {
            if (nums[j] > nums[i - 1]) {
                nums.swap(j, i - 1)
                break
            }
        }
        // Reverse the subarray from i(including i)
        for (j in i until (size + i) / 2) {
            nums.swap(j, size - 1 - j + i)
        }
    }

    fun mergeIntervals(intervals: Array<IntArray>): Array<IntArray> {
        if (intervals.size < 2) return intervals
        intervals.sortBy { it[0] }
        val result = ArrayList<IntArray>()
        var current = intervals[0]
        for (i in 1 until intervals.size) {
            val item = intervals[i]
            if (current[1] >= item[0]) {
                if (item[1] > current[1])
                    current[1] = item[1]
                if (i == intervals.size - 1) {
                    result.add(current)
                }
            } else {
                result.add(current)
                current = item
            }
            if (i == intervals.size - 1) result.add(current)
        }
        return result.toTypedArray()
    }

    fun addBinary(a: String, b: String): String {
        var carry = 0
        val maxSize = max(a.length, b.length)
        var i = 0
        val result = StringBuilder()
        while (i < maxSize) {
            val aItem = if (i < a.length) a[a.length - i - 1] - '0' else 0
            val bItem = if (i < b.length) b[b.length - i - 1] - '0' else 0
            val sum = (aItem + bItem + carry)
            carry = sum / 2
            val value = sum % 2

            result.insert(0, "$value")
            i++
        }
        if (carry == 1) {
            result.insert(0, '1')
        }
        return result.toString()
    }

    fun wordBreak(s: String, wordDict: List<String>): Boolean {
        val wordSet: Set<String> = HashSet(wordDict)
        val dp = Array<Boolean>(s.length) { false }
        return wordBreak(s, 0, wordSet, dp)
    }

    // If a string can be broken into words in the set
    private fun wordBreak(
        s: String,
        start: Int,
        wordSet: Set<String>,
        dp: Array<Boolean>
    ): Boolean {
        if (start == s.length || dp[start]) return true
        for (i in start until s.length) {
            if (wordSet.contains(s.substring(start, i + 1)) && wordBreak(
                    s,
                    i + 1,
                    wordSet,
                    dp
                )
            ) {
                dp[start] = true
                return true
            }
        }
        return false
    }

    fun wordBreak2(s: String, wordDict: List<String>): List<String> {
        val wordSet: Set<String> = HashSet(wordDict)
        val dp = HashMap<Int, List<String>>()

        return wordBreak2(s, 0, wordSet, dp)
    }

    private fun wordBreak2(
        s: String,
        start: Int,
        wordSet: Set<String>,
        dp: HashMap<Int, List<String>>
    ): List<String> {
        if (dp.containsKey(start)) {
            return dp[start]!!
        }
        val result = ArrayList<String>()
        if (start == s.length) result.add("")
        for (i in start until s.length) {
            val substr = s.substring(start, i + 1)
            if (wordSet.contains(substr)) {
                val list = wordBreak2(
                    s,
                    i + 1,
                    wordSet,
                    dp
                )
                if (!list.isEmpty()) {
                    list.forEach {
                        val str = if (it.isEmpty()) substr else substr + " " + it
                        result.add(str)
                    }
                    dp[start] = result
                }
            }
        }

        return result
    }
}