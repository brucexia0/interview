package com.example.interview.practice

import java.lang.StringBuilder
import java.util.*
import kotlin.collections.ArrayList

class BracketIssues {
    fun minRemoveToMakeValid(s: String): String {
        val result = StringBuilder()
        var balance = 0
        for (c in s) {
            when (c) {
                '(' -> {
                    result.append(c)
                    balance++
                }
                ')' -> {
                    if (balance > 0) {
                        result.append(c)
                        balance--
                    }
                }
                else -> result.append(c)
            }
        }

        if (balance > 0) {
            val s1 = result.toString()
            result.clear()
            balance = 0
            for (i in s1.length - 1 downTo 0) {
                val c = s1[i]
                when (c) {
                    ')' -> {
                        balance++
                        result.append(c)
                    }
                    '(' -> {
                        if (balance > 0) {
                            result.append(c)
                            balance--
                        }
                    }
                    else -> result.append(c)
                }
            }
            result.reverse()
        }
        return result.toString()
    }

    fun minRemoveToMakeValidV1(s: String): String {
        val result = StringBuilder()
        val stack = Stack<Int>()
        var balance = 0
        for (c in s) {
            when (c) {
                '(' -> {
                    result.append(c)
                    balance++
                }
                ')' -> {
                    if (balance > 0) {
                        result.append(c)
                        balance--
                    }
                }
                else -> result.append(c)
            }
        }

        if (balance > 0) {
            val s1 = result.toString()
            result.clear()
            balance = 0
            for (i in s1.length - 1 downTo 0) {
                val c = s1[i]
                when (c) {
                    ')' -> {
                        balance++
                        result.append(c)
                    }
                    '(' -> {
                        if (balance > 0) {
                            result.append(c)
                            balance--
                        }
                    }
                    else -> result.append(c)
                }
            }
            result.reverse()
        }
        return result.toString()
    }

    fun addOperators(num: String, target: Int): List<String> {
        val result = ArrayList<String>()
        if (num.isEmpty()) return result
        addOperatorHelper(num, 0, 0, target, result, "")
        return result
    }

    fun addOperatorHelper(
        s: String,
        start: Int,
        currentValue: Int,
        target: Int,
        list: ArrayList<String>,
        currentStr: String
    ) {
        if (start == s.length) {
            if (currentValue == target)
                list += currentStr
            return
        }
        if (s[start] == '0') return

        for (i in start until s.length) {
            val curr = Integer.parseInt(s.substring(start, i + 1))
            if (start == 0) {
                addOperatorHelper(s, i + 1, curr, target, list, "$curr")
            }
            addOperatorHelper(s, i + 1, currentValue + curr, target, list, "$currentStr+$curr")
            addOperatorHelper(s, i + 1, currentValue - curr, target, list, "$currentStr-$curr")
            addOperatorHelper(s, i + 1, currentValue * curr, target, list, "$currentStr*$curr")
        }

    }
}