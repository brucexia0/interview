package com.example.interview.practice

import java.lang.StringBuilder
import java.util.*

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

}