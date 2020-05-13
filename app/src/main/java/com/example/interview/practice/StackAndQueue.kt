package com.example.interview.practice

import java.util.*

class StackAndQueue {
    fun simplifyPath(path: String): String {
        val paths = path.split("/")
        val s = Stack<String>()
        for (path in paths) {
            when (path) {
                ".", "" -> {
                }
                ".." -> if (s.isNotEmpty()) s.pop()
                else -> s.push(path)
            }
        }
        if (s.isEmpty()) return "/"
        var result = StringBuilder()
        while (s.isNotEmpty()) {
            result.insert(0, "/" + s.pop())
        }
        return result.toString()
    }

    fun calculate(s: String): Int {
        val normalizedS = s.replace("\\s+", "")
        val operatorStack = Stack<Char>()
        val valueStack = Stack<Int>()
        var index = 0
        var currentNumber: Int? = null
        while (index < normalizedS.length) {
            val c = normalizedS[index]
            when (c) {
                '*', '/', '-', '+', '(', ')' -> {
                    currentNumber?.let {
                        valueStack.push(it)
                    }
                    currentNumber = null
                    if (c == ')') {
                        //calculate the entire section
                        while (operatorStack.isNotEmpty() && operatorStack.peek() != '(') {
                            val r = valueStack.pop()
                            val l = valueStack.pop()
                            val result = applyOperator(operatorStack.pop(), r, l)
                            valueStack.push(result)
                        }
                        operatorStack.pop()//remove the '('
                    } else {
                        while (operatorStack.isNotEmpty() && hasPrecedence(
                                c,
                                operatorStack.peek()
                            )
                        ) {
                            val result = applyOperator(
                                operatorStack.pop(),
                                valueStack.pop(),
                                valueStack.pop()
                            )
                            valueStack.push(result)
                        }
                    }
                    operatorStack.push(c)
                }
                in '0'..'9' -> {
                    currentNumber = currentNumber?.let {
                        it * 10 + (c - '0')
                    } ?: (c - '0')
                }
                else -> {
                }
            }
        }
        while (operatorStack.isNotEmpty()) {
            val r = valueStack.pop()
            val l = valueStack.pop()
            val result = applyOperator(operatorStack.pop(), r, l)
            valueStack.push(result)
        }
        operatorStack.pop()
        return valueStack.pop()
    }

    fun hasPrecedence(op1: Char, op2: Char): Boolean {
        if (op2 == '(' || op2 == ')') return false
        return (op2 == '*' || op2 == '/')
    }

    fun applyOperator(operator: Char, r: Int, l: Int): Int {
        return when (operator) {
            '*' -> l * r
            '/' -> l / r
            '-' -> l - r
            '+' -> l + r
            else -> {
                //log error
                0
            }
        }
    }

    class TaskStartData(val id: Int, val start: Int) {
        var occupied: Int = 0
    }

    // login format taskId:[start|end]:time
    fun exclusiveTime(n: Int, logs: List<String>): IntArray {
        val START = "start"
        val stack = Stack<TaskStartData>()//Stack of task start log
        val result = IntArray(n)
        for (log in logs) {
            val arr = log.split(":")
            val id = Integer.parseInt(arr[0])
            val type = arr[1]
            val time = Integer.parseInt(arr[2])
            if (type == START) {
                stack.push(TaskStartData(id, time))
            } else {
                val top = stack.pop()
                if (top.id == id) {
                    val duration = time - top.start + 1
                    result[id] += duration- top.occupied
                    if (stack.isNotEmpty()) {
                        stack.peek().occupied += duration
                    }
                }
            }
        }
        return result
    }
}