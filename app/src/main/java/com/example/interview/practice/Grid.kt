package com.example.interview.practice

const val UP_RIGHT = 0
const val DOWN_LEFT = 1

class Grid {
    fun findDiagonalOrder(matrix: Array<IntArray>): IntArray {
        if (matrix.isEmpty()) return IntArray(0)
        val rows = matrix.size
        val cols = matrix[0].size
        var row = 0
        var col = 0
        var isUpRight = true
        val result = IntArray(rows * cols)
        var arrayIndex = 0
        while (row in 0 until rows && col in 0 until cols) {
            result[arrayIndex++] = matrix[row][col]

            if (isUpRight) {
                if (row == 0) {
                    if (col < cols - 1) {
                        //move right and change direction
                        col++
                    } else {
                        row++
                    }
                    isUpRight = false
                } else {
                    if (col == cols - 1) {
                        row++
                        isUpRight = false
                    } else {
                        row--
                        col++
                    }
                }

            } else {
                if (col == 0) {
                    if (row < rows - 1) {
                        //move right and change direction
                        row++
                    } else {
                        col++
                    }
                    isUpRight = true
                } else {
                    if (row == rows - 1) {
                        col++
                        isUpRight = true
                    } else {
                        row++
                        col--
                    }
                }
            }
        }
        return result
    }
}