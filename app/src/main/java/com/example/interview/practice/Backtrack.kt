package com.example.interview.practice

import android.support.v4.app.INotificationSideChannel

class Backtrack {
    // Backtrack + DP
     val STEPS = arrayOf(
        Pair(-1, 0),
        Pair(1, 0),
        Pair(0, -1),
        Pair(0, 1)
    )

    fun exist(board: Array<CharArray>, word: String): Boolean {
        for (i in board.indices) {
            for (j in board[0].indices) {
                if (checkWordExist(board, word, i, j)) return true
            }
        }
        return false
    }

    private fun checkWordExist(board: Array<CharArray>, word: String, row: Int, col: Int): Boolean {
        if (word.isEmpty()) return true

        val rows = board.size
        val cols = board[0].size
        if (row < 0 || row > rows - 1 || col < 0 || col > cols - 1) return false
        if (word[0] != board[row][col]) return false
        if (word.length == 1) return true

        board[row][col] = '*'
        val newWord = word.substring(1)
        for ((x, y) in STEPS) {
            if (checkWordExist(board, newWord, row + x, col + y)) return true
        }
        board[row][col] = word[0]

        return false
    }

    fun restoreIpAddresses(s: String): List<String> {
        val result = ArrayList<String>()

        return result
    }

    fun totalNQueens(n: Int): Int {
        return nQueenBacktrack(BooleanArray(n), 0, n, HashSet(), HashSet(), 0)
    }

    fun nQueenBacktrack(
        columns: BooleanArray,
        row: Int,
        n: Int,
        hills: HashSet<Int>,
        dales: HashSet<Int>,
        count: Int
    ): Int {
        var result = count
        for (col in 0 until n) {
            if (canPlace(columns, row, col, hills, dales)) {
                columns[col] = true
                hills.add(row + col)
                dales.add(row - col)
                if (row == n - 1) {
                    result++

                } else {
                    result = nQueenBacktrack(columns, row + 1, n, hills, dales, result)
                }
                columns[col] = false
                hills.remove(row + col)
                dales.remove(row - col)
            }
        }
        return result
    }

    fun canPlace(
        columns: BooleanArray, row: Int, col: Int, hills: HashSet<Int>,
        dales: HashSet<Int>
    ): Boolean {
        return !columns[col] && !hills.contains(row + col) && !dales.contains(row - col)
    }
}