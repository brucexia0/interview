package com.example.interview.practice

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class Misc {
    fun largestNumber(nums: IntArray): String {
        val comparator = object : Comparator<Int> {
            override fun compare(o1: Int, o2: Int): Int {
                if (o1 == o2) return 0
                val s1 = o1.toString()
                val s2 = o2.toString()
                val combined1 = (s1 + s2).toLong()
                val combined2 = (s2 + s1).toLong()
                return if (combined1 > combined2) -1 else 1
            }
        }
        return nums.sortedWith(comparator).fold("") { acc, i -> acc + i }
    }

    fun ladderLength(beginWord: String, endWord: String, wordList: List<String>): Int {
        //Prerpocess the words
        val size = beginWord.length
        val allComDict = HashMap<String, ArrayList<String>>()
        wordList.forEach {
            for (i in it.indices) {
                val newWord = it.substring(0, i) + "*" + it.substring(i + 1, size)
                val list = allComDict.getOrDefault(newWord, ArrayList())
                list.add(it)
                allComDict.put(newWord, list)
            }
        }

        val queue = LinkedList<Pair<String, Int>>()
        val visited = HashMap<String, Boolean>()
        visited.put(beginWord, true)
        queue.add(beginWord to 1)
        while (queue.isNotEmpty()) {
            val node = queue.remove()
            val word = node.first
            var level = node.second
            for (i in word.indices) {
                val newWord = word.substring(0, i) + "*" + word.substring(i + 1, size)
                for (adjacent in allComDict.getOrDefault(newWord, ArrayList())) {
                    if (adjacent.equals(endWord)) {
                        return level + 1
                    }
                    if (visited[adjacent] != true) {
                        visited[adjacent] = true
                        queue.add(adjacent to level + 1)
                    }
                }
            }
        }
        return 0
    }

    private fun fastPow(x: Double, n: Long): Double {
        if (n == 0L) {
            return 1.0
        }
        val half = fastPow(x, n / 2)
        return if (n % 2 == 0L) {
            half * half
        } else {
            half * half * x
        }
    }

    fun myPow(x: Double, n: Int): Double {
        var x = x
        var N = n.toLong()
        if (N < 0) {
            x = 1 / x
            N = -N
        }
        return fastPow(x, N)
    }

    fun checkValidString(s: String): Boolean {
        var lo = 0
        var hi = 0
        for (c in s.toCharArray()) {
            lo += if (c == '(') 1 else -1
            hi += if (c != ')') 1 else -1
            if (hi < 0) break
            lo = Math.max(lo, 0)
        }
        return lo == 0
    }

    fun multiplyString(num1: String, num2: String): String {
        if(num1=="0" || num2=="0")return "0"
        val len1 = num1.length
        val len2 = num2.length
        val s = StringBuilder()
        val result = IntArray(len1 + len2)

        for (i in len1 - 1 downTo 0) {
            for (j in len2 - 1 downTo 0) {
                val idx1 = i + j
                val idx2 = i + j + 1
                val product = (num1[i] - '0') * (num2[j] - '0') + result[idx2]
                result[idx2] = product % 10
                result[idx1] += product / 10
            }
        }

        if (result[0] > 0) s.append(result[0])
        for (i in 1 until result.size) s.append(result[i])
        return s.toString()
    }

    fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {

        return null
    }

    fun spiralOrder(matrix: Array<IntArray>): List<Int> {
        val result = ArrayList<Int>()
        return result
    }

    fun firstMissingPositive(nums: IntArray): Int {
        val size = nums.size
        val tracker = Array(size + 1) { false }
        for (i in nums.indices) {
            if (nums[i] in 0..size) {
                tracker[nums[i]] = true
            }
        }

        for (i in 1 until tracker.size) {
            if (!tracker[i]) return i
        }

        return size + 1
    }

    fun intToRoman(num: Int): String? {
        return null
    }

    fun divide(dividend: Int, divisor: Int): Int {
        if (dividend == Int.MIN_VALUE && divisor == -1) return Int.MAX_VALUE
        var negatives = 2
        var nDividend = dividend
        var nDivisor = divisor
        var quotient = 0
        if (dividend > 0) {
            nDividend = -dividend
            negatives--
        }
        if (divisor > 0) {
            nDivisor = -divisor
            negatives--
        }
        while (nDividend - nDivisor <= 0) {
            nDividend -= nDivisor
            quotient++
        }
        return if (negatives == 1) -quotient else quotient
    }

    fun divideV1(dividend: Int, divisor: Int): Int {
        if (dividend == Int.MIN_VALUE && divisor == -1) return Int.MAX_VALUE
        var negatives = 2
        var nDividend = dividend
        var nDivisor = divisor
        var quotient = 0
        if (dividend > 0) {
            nDividend = -dividend
            negatives--
        }
        if (divisor > 0) {
            nDivisor = -divisor
            negatives--
        }

        val halfMin = Int.MIN_VALUE / 2
        var multiDividend = nDividend
        while (multiDividend <= nDivisor) {
            var multis = 1
            var multiDivisor = nDivisor
            while (multiDivisor >= halfMin && multiDividend <= multiDivisor + multiDivisor) {
                multiDivisor += multiDivisor
                multis += multis
            }
            quotient += multis
            multiDividend -= multiDivisor
        }
        return if (negatives == 1) -quotient else quotient
    }

    val BILLION = Math.pow(10.0, 9.0)
    val MILLION = Math.pow(10.0, 6.0)
    val THOUSAND = 1000
    val BILLION_STR = "Billion"
    val MILLION_STR = "Million"
    val THOUSAND_STR = "Thousand"
    val HUNDRED = "Hundred"
    val DIGITS = arrayOf("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine")
    val TEENS = arrayOf(
        "Eleven",
        "Twelve",
        "Thirteen",
        "Fourteen",
        "Fifteen",
        "Sixteen",
        "Seventeen",
        "Eighteen",
        "Nineteen"
    )
    val TENS = arrayOf(
        "Ten",
        "Twenty",
        "Thirty",
        "Fourty",
        "Fifty",
        "Sixty",
        "Seventy",
        "Eighty",
        "Ninety"
    )

    fun numberToWords(num: Int): String {
        if (num == 0) return "Zero"
        val billions = num / BILLION
        val millions = num % BILLION / MILLION
        val thousands = num % BILLION % MILLION / THOUSAND
        val hundreds = num % BILLION % MILLION % THOUSAND
        val result = StringBuilder()
        if (billions > 0) {

        }
        return ""
    }

    fun hundredsNum(value: Int) {
        val h = value / 100
        val tens = value % 100
        val sb = StringBuilder()
        if (h > 0) {
            sb.append(DIGITS[h - 1]).append(" ").append(HUNDRED)
        }
        if (tens > 19) {
            sb.appendSpaceIfNotEmpty()
            sb.append(TENS[tens / 10 - 1])
        }
        if (tens >= 10) {
            sb.appendSpaceIfNotEmpty()
            sb.append(TEENS[tens - 10])
        } else {
            val n = value % 10
            if (n > 0) {
                sb.appendSpaceIfNotEmpty()
                sb.append(DIGITS[n])
            }
        }
    }

    fun StringBuilder.appendSpace() = append(" ")
    fun StringBuilder.appendSpaceIfNotEmpty() {
        if (isNotEmpty()) {
            append(" ")
        }
    }
}