package com.example.interview.practice

import android.util.Log
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.max
import kotlin.math.min

class DP {
    fun numDecodings(s: String): Int {
        return numDecodings(s, 0, Array(s.length) { 0 });
    }

    private val UPPER = 26
    private fun numDecodings(s: String, startIndex: Int, dp: Array<Int>): Int {
        if (dp[startIndex] > 0) return dp[startIndex]
        if (s[startIndex] == '0') return 0
        if (startIndex == s.length - 1) {
            return 1
        }
        val twoDigit = s.substring(startIndex, startIndex + 2).toInt()
        if (startIndex == s.length - 2) {
            if (s[startIndex + 1] == '0') {
                return if (twoDigit > UPPER) 0 else 1
            }
            if (twoDigit > UPPER) return 1
            return 2
        }
        if (s[startIndex + 1] == '0') {
            dp[startIndex] = if (twoDigit > UPPER) 0 else
                numDecodings(s, startIndex + 2, dp)
            return dp[startIndex]
        }
        if (twoDigit < 27) {
            dp[startIndex] =
                numDecodings(s, startIndex + 2, dp) + numDecodings(s, startIndex + 1, dp)
        } else dp[startIndex] = numDecodings(s, startIndex + 1, dp)

        return dp[startIndex]
    }

    fun checkSubarraySum(nums: IntArray, k: Int): Boolean {
        val size = nums.size
        val dp = Array(size) { ArrayList<Int>() }
        dp[0].add(nums[0])
        for (i in 1 until size) {
            dp[i].add(nums[i])
            for (j in i - 1 downTo 0) {
                val sum = nums[i] + dp[i - 1][j]
                if (k == 0) {
                    if (sum == 0) return true
                } else if (sum % k == 0) {
                    return true
                }
                dp[i].add(0, sum)
            }
        }
        return false
    }

    fun longestPalindrome(s: String): String {
        val size = s.length
        if (size < 2) return s
        val dp = Array(size) { ArrayList<Boolean>() }
        dp[0].add(true)
        var maxStart = 0
        var maxEnd = 0
        for (i in 1 until size) {
            for (j in 0 until i) {
                val isPalindrome = if (j == i - 1) s[i] == s[i - 1] else
                    s[i] == s[j] && dp[i - 1][j + 1]
                dp[i].add(isPalindrome)
                if (isPalindrome && maxEnd - maxStart < i - j) {
                    maxStart = j
                    maxEnd = i
                }
            }
            dp[i].add(true)
        }
        return s.substring(maxStart, maxEnd + 1)
    }

    fun canJump(nums: IntArray): Boolean {
        return false
    }

    /**
     * Wildcard match using DP.
     */
    fun isMatch(s: String, p: String): Boolean {
        if (s == p || p == "*") {
            return true
        }
        if (s.isEmpty() || p.isEmpty()) return false
        val p1 = removeDuplicateStars(p)
        if (p1 == "*") return true

        val pLen = p1.length
        val sLen = s.length
        val dp = Array(pLen + 1) { Array(sLen + 1) { false } }
        dp[0][0] = true
        for (pIdx in 1 until pLen + 1) {
            var firstMatchIdxBeforeStar = false
            for (sIdx in 1 until sLen + 1) {
                when (p1[pIdx - 1]) {
                    '*' -> {
                        if (!firstMatchIdxBeforeStar && dp[pIdx - 1][sIdx - 1]) {
                            dp[pIdx][sIdx - 1] = true
                            firstMatchIdxBeforeStar = true
                        }
                        if (firstMatchIdxBeforeStar) {
                            dp[pIdx][sIdx] = true
                        }
                    }
                    '?' -> {
                        dp[pIdx][sIdx] = dp[pIdx - 1][sIdx - 1]
                    }
                    else -> {
                        dp[pIdx][sIdx] = dp[pIdx - 1][sIdx - 1] && p1[pIdx - 1] == s[sIdx - 1]
                    }
                }
            }
        }
        return dp[pLen][sLen]
    }

    fun removeDuplicateStars(s: String): String {
        val result = StringBuilder()
        result.append(s[0])
        for (i in 1 until s.length) {
            if (s[i] != '*' || s[i] != s[i - 1]) result.append(s[i])
        }
        return result.toString()
    }

    fun maxSumOfThreeSubarrays(nums: IntArray, k: Int): IntArray {
        val size = nums.size
        val sums = IntArray(size - k + 1)
        val left = IntArray(size - k + 1)
        val right = IntArray(size - k + 1)
        var sum = 0
        for (i in 0 until size) {
            sum += nums[i]
            if (i >= k) {
                sum -= nums[i - k]
            }
            if (i >= k - 1) {
                sums[i - k + 1] = sum
            }
        }
        //fill the left largest sum index
        var leftLargestIndex = 0
        for (i in sums.indices) {
            if (sums[i] > sums[leftLargestIndex]) {
                leftLargestIndex = i
            }
            left[i] = leftLargestIndex
        }

        var rightLargestIndex = sums.size - 1
        for (i in sums.size - 1 downTo 0) {
            if (sums[i] >= sums[rightLargestIndex]) {
                rightLargestIndex = i
            }
            right[i] = rightLargestIndex
        }
        var result = IntArray(3)
        var max = Int.MIN_VALUE
        for (i in k until sums.size - k) {
            val leftIndex = left[i - k]
            val rightIndex = right[i + k]
            val sum = sums[leftIndex] + sums[rightIndex] + sums[i]
            if (sum > max) {
                max = sum
                result = intArrayOf(leftIndex, i, rightIndex)
            }
        }
        return result
    }

    fun trapWater(height: IntArray): Int {
        val size = height.size
        if (size < 3) return 0
        val maxHeightLeft = IntArray(size)
        val maxHeightRight = IntArray(size)
        maxHeightLeft[0] = height[0]
        maxHeightRight[size - 1] = height[size - 1]
        var sum = 0

        for (i in 1 until size) {
            maxHeightLeft[i] = max(height[i], maxHeightLeft[i - 1])
        }
        for (i in size - 2 downTo 0) {
            maxHeightRight[i] = max(height[i], maxHeightRight[i + 1])
        }
        for (i in 1 until size - 1) {
            sum += min(maxHeightLeft[i], maxHeightRight[i]) - height[i]
        }
        return sum
    }

    fun trapRainWater2(heightMap: Array<IntArray>): Int {
        val rows = heightMap.size
        val cols = heightMap[0].size
        val maxHeightLeft = Array<IntArray>(rows) { IntArray(cols) }
        val maxHeightRight = Array<IntArray>(rows) { IntArray(cols) }
        val maxHeightAbove = Array(cols) { IntArray(rows) }
        val maxHeightBelow = Array(cols) { IntArray(rows) }

        var sum = 0

        for (i in 0 until rows) {
            for (j in 0 until cols) {
                maxHeightLeft[i][j] =
                    if (j == 0) heightMap[i][0] else max(maxHeightLeft[i][j - 1], heightMap[i][j])
            }
            for (j in cols - 1 downTo 0) {
                maxHeightLeft[i][j] =
                    if (j == cols - 1) heightMap[i][j - 1] else max(
                        maxHeightLeft[i][j + 1],
                        heightMap[i][j]
                    )
            }
        }
        for (i in 0 until cols) {
            for (j in 0 until rows) {
                maxHeightAbove[i][j] =
                    if (j == 0) heightMap[0][i] else max(maxHeightAbove[j-1][i], heightMap[j][i])
            }
            for (j in rows-1 downTo 0) {
                maxHeightBelow[i][j] =
                    if (j == 0) heightMap[0][i] else max(maxHeightAbove[j-1][i], heightMap[j][i])
            }
        }
        return sum
    }
}