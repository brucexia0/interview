package com.example.interview.practice

import java.lang.StrictMath.min
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sqrt

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

        restoreIpAddressHelper(s, 0, "", result)
        return result
    }

    fun restoreIpAddressHelper(s: String, start: Int, curr: String, list: ArrayList<String>) {
        if (start == s.length) {
            list.add(curr)
        }
        for (i in start..start + 2) {
            val s = s.substring(start, i + 1)
            if (s.isValidIpSection()) {
                val newCurr = if (curr.isEmpty()) s else "$curr.$s"
                restoreIpAddressHelper(s, i + 1, newCurr, list)
            }
        }
    }

    private fun String.isValidIpSection(): Boolean {
        if (length > 1 && this[0] == '0') return false
        val num = Integer.parseInt(this)
        if (num > 255) return false
        return true
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

    fun subsets(nums: IntArray): List<List<Int>> {
        val result = ArrayList<List<Int>>()
        val list = ArrayList<Int>()
        subsetBacktrack(nums, 0, result, list)
        return result
    }

    fun subsetBacktrack(
        nums: IntArray,
        start: Int,
        result: ArrayList<List<Int>>,
        list: ArrayList<Int>
    ) {
        result.add(ArrayList(list))
        for (i in start until nums.size) {
            if (!list.contains(nums[i])) {
                list.add(nums[i])
                subsetBacktrack(nums, i + 1, result, list)
                list.remove(nums[i])
            }
        }
    }

    fun subsetsWithDup(nums: IntArray): List<List<Int>> {
        val result = ArrayList<List<Int>>()
        val list = ArrayList<Int>()
        nums.sort()
        subsetBacktrackDup(nums, 0, result, list)
        return result
    }

    fun subsetBacktrackDup(
        nums: IntArray,
        start: Int,
        result: ArrayList<List<Int>>,
        list: ArrayList<Int>
    ) {
        result.add(ArrayList(list))
        for (i in start until nums.size) {
            if (i > start && nums[i] == nums[i - 1]) continue
            list.add(nums[i])
            subsetBacktrackDup(nums, i + 1, result, list)
            list.remove(list.size - 1)
        }
    }

    fun generateParenthesis(n: Int): List<String> {
        if (n == 0) return emptyList()
        val result = ArrayList<String>()
        generateParenthesisHelper(n, 0, 0, "", result)
        return result
    }

    private fun generateParenthesisHelper(
        n: Int,
        open: Int,
        close: Int,
        str: String,
        list: ArrayList<String>
    ) {
        if (str.length == 2 * n) list.add(str)
        if (open < n) {
            generateParenthesisHelper(n, open + 1, close, str + "(", list)
        }
        if (close < open) {
            generateParenthesisHelper(n, open, close + 1, str + ")", list)
        }
    }

    fun solveSudoku(board: Array<CharArray>) {
        val fillList = LinkedList<Pair<Int, Int>>()
        val rowFlag = Array(9) { BooleanArray(9) }
        val colFlag = Array(9) { BooleanArray(9) }
        val boxFlag = Array(9) { BooleanArray(9) }
        for (i in board.indices) {
            for (j in board.indices) {
                val c = board[i][j]
                if (c == '.') {
                    fillList.add(i to j)
                } else {
                    val digit = c - '1'
                    rowFlag[i][digit] = true
                    colFlag[j][digit] = true
                    boxFlag[boxIndex(i, j)][digit] = true
                }
            }
        }
        solveSudokuHelper(board, rowFlag, colFlag, boxFlag, fillList)
    }

    private fun boxIndex(i: Int, j: Int) = 3 * (i / 3) + j / 3
    fun solveSudokuHelper(
        board: Array<CharArray>,
        rowFlag: Array<BooleanArray>,
        colFlag: Array<BooleanArray>,
        boxFlag: Array<BooleanArray>,
        list: LinkedList<Pair<Int, Int>>
    ): Boolean {
        if (list.isEmpty()) return true
        val (row, col) = list.poll()
        for (c in '1'..'9') {
            val d = c - '1'

            val canPlace =
                !rowFlag[row][d] && !colFlag[col][d] && !boxFlag[boxIndex(row, col)][d]
            if (canPlace) {
                board[row][col] = c
                rowFlag[row][d] = true
                colFlag[col][d] = true
                boxFlag[boxIndex(row, col)][d] = true
                if (solveSudokuHelper(
                        board,
                        rowFlag,
                        colFlag,
                        boxFlag,
                        list
                    )
                ) return true
                board[row][col] = '.'
                rowFlag[row][d] = false
                colFlag[col][d] = false
                boxFlag[boxIndex(row, col)][d] = false
            }

        }
        list.offerFirst(row to col)
        return false
    }

    fun combinationSum3(k: Int, n: Int): List<List<Int>> {
        val result = ArrayList<List<Int>>()
        combinationSum3Helper(k, 1, 0, n, ArrayList(), result)
        return result
    }

    fun combinationSum3Helper(
        k: Int,
        start: Int,
        curr: Int,
        n: Int,
        list: ArrayList<Int>,
        result: ArrayList<List<Int>>
    ) {
        if (k == 0) {
            if (curr == n) {
                result.add(ArrayList(list))
            }
            return
        }

        for (i in start until min(n, 10)) {
            val s = curr + i
            if (s <= n) {
                list.add(i)
                combinationSum3Helper(k - 1, i + 1, curr + i, n, list, result)
                list.removeAt(list.size - 1)
            }
        }
    }

    fun partition(s: String): List<List<String>> {
        val result = ArrayList<List<String>>()
        getFactorsHelper(s, ArrayList<String>(), result)
        return result
    }

    fun getFactorsHelper(s: String, list: ArrayList<String>, result: ArrayList<List<String>>) {
        if (s.isEmpty()) {
            result.add(ArrayList(list))
            return
        }
        for (i in s.indices) {
            val t = s.substring(0, i + 1)
            if (t.isPalindrome()) {
                list.add(t)
                getFactorsHelper(s.substring(i + 1, s.length), list, result)
                list.removeAt(list.size - 1)
            }
        }
    }

    fun String.isPalindrome(): Boolean {
        for (i in 0..length / 2) {
            if (this[i] != this[length - i - 1]) return false
        }
        return true
    }

    fun getFactors(n: Int): List<List<Int>> {
        val result = ArrayList<List<Int>>()
        getFactorsHelper(n, 2, ArrayList(), result)
        return result
    }

    private fun getFactorsHelper(
        n: Int,
        base: Int,
        list: ArrayList<Int>,
        result: ArrayList<List<Int>>
    ) {
        for (i in base..sqrt(n.toDouble()).toInt()) {
            if (n % i == 0) {
                val d = n / i
                list.add(i)
                list.add(d)
                result.add(ArrayList(list))
                list.removeAt(list.size - 1)
                getFactorsHelper(d, i, list, result)
                list.removeAt(list.size - 1)
            }
        }
    }

    // https://leetcode.com/problems/maximum-length-of-a-concatenated-string-with-unique-characters/
    //    Given an array of strings arr. String s is a concatenation of a sub-sequence of arr which have unique characters.
    //    Return the maximum possible length of s.
    var maxLength = 0
    fun maxLength(arr: List<String>): Int {
        maxLengthHelper(arr, 0, 0, HashSet())
        return maxLength
    }

    private fun maxLengthHelper(arr: List<String>, start: Int, len: Int, set: HashSet<Char>) {
        for (i in start until arr.size) {
            var canAdd = true
            val myChars = HashSet<Char>()

            for (c in arr[i]) {
                if (set.contains(c) || !myChars.add(c)) {
                    canAdd = false
                    break
                }
            }
            if (canAdd) {
                set.addAll(arr[i].asIterable())
                val l = arr[i].length + len
                maxLength = max(maxLength, l)
                maxLengthHelper(arr, i + 1, l, set)
                set.removeAll(arr[i].asIterable())
            }
        }
    }

    var numberOfPatterns = 0
    fun numberOfPatterns(m: Int, n: Int): Int {
        val board = Array(3) { BooleanArray(3) }
        numberOfPatternsHelper(board, 0, 0, 0, m, n)
        return numberOfPatterns
    }

    fun numberOfPatternsHelper(
        board: Array<BooleanArray>,
        selected: Int,
        lastX: Int,
        lastY: Int,
        m: Int,
        n: Int
    ) {
        if (selected in m..n) numberOfPatterns++
        if (selected > n) return
        for (x in 0..2) {
            for (y in 0..2) {
                if (selected == 0 || canConnect(board, lastX, lastY, x, y)) {
                    board[x][y] = true
                    numberOfPatternsHelper(board, selected + 1, x, y, m, n)
                    board[x][y] = false
                }
            }
        }
    }

    private fun canConnect(
        board: Array<BooleanArray>,
        lastX: Int,
        lastY: Int,
        x: Int,
        y: Int
    ): Boolean {
        if (board[x][y]) return false
        val xDist = abs(lastX - x)
        val yDist = abs(lastY - y)

        return ((xDist == 1 && yDist == 1) || xDist + yDist == 1) ||
                board[(x + lastX) / 2][(y + lastY) / 2]
    }

    // https://leetcode.com/problems/additive-number/
    fun isAdditiveNumber(num: String): Boolean {
        if (num.length < 3) return false
        return isAdditiveNumberHelper(num, 0, ArrayList())
    }

    private fun isAdditiveNumberHelper(num: String, start: Int, list: ArrayList<Int>): Boolean {
        for (i in start until num.length) {
            val d = Integer.parseInt(num.substring(start, i + 1))
            if (list.size < 2 || d == list[list.size - 1] + list[list.size - 2]) {
                list.add(d)
                if (i == num.length - 1 && list.size > 2) return true// Terminal condition
                if (isAdditiveNumberHelper(num, i + 1, list)) return true
                list.removeAt(list.size - 1)
            }
        }
        return false
    }

    //https://leetcode.com/problems/word-squares/
    fun wordSquares(words: Array<String>): List<List<String>> {
        if (words.isEmpty()) return emptyList()
        val result = ArrayList<List<String>>()
        wordSquaresHelper(ArrayList(words.toList()), words[0].length, ArrayList(), result)
        return result
    }

    private fun wordSquaresHelper(
        words: ArrayList<String>,
        len: Int,
        list: ArrayList<String>,
        result: ArrayList<List<String>>
    ) {
        if (len == list.size) {
            result.add(ArrayList(list))
            return
        }
        for ((index, curr) in words.withIndex()) {
            var canAdd = true
            for ((j, w) in list.withIndex()) {
                if (list.size > 0 && curr[j] != w[list.size]) {
                    canAdd = false
                    break
                }
            }
            if (canAdd) {
                list.add(curr)
                val remaining = ArrayList(words).apply { removeAt(index) }
                wordSquaresHelper(remaining, len, list, result)
                list.removeAt(list.size - 1)
            }
        }
    }

    var minStickers = Int.MAX_VALUE
    fun minStickers(stickers: Array<String>, target: String): Int {
        return minStickers
    }

    fun countArrangement(N: Int): Int {
        val nums = IntArray(N).apply {
            for (i in indices) this[i] = i + 1
        }

        countArrangementHelper(ArrayList(nums.toList()), 1)
        return countArrangementCount
    }

    var countArrangementCount = 0
    fun countArrangementHelper(nums: ArrayList<Int>, start: Int) {
        if (nums.isEmpty()) {
            countArrangementCount++
            return
        }
        for ((i, d) in nums.withIndex()) {
            if (start % d == 0 || d % start == 0) {
                val nList = ArrayList(nums).apply { removeAt(i) }
                countArrangementHelper(nList, start + 1)
            }
        }
    }

    // https://leetcode.com/problems/split-array-into-fibonacci-sequence/
    fun splitIntoFibonacci(S: String): List<Int> {
        val result = ArrayList<Int>()
        val s = S.substring(0, min(10, S.length))
        if (splitIntoFibonacciHelper(s, 0, result))
            return result
        else return emptyList()
    }

    private fun splitIntoFibonacciHelper(s: String, start: Int, result: ArrayList<Int>): Boolean {
        if (s.length == start && result.size >= 3) return true
        if (s[start] == '0') {
            if (result.size < 2) {
                result.add(0)
                if (splitIntoFibonacciHelper(s, start + 1, result)) return true
                result.removeAt(result.size - 1)
            }
        } else
            for (i in start until Math.min(s.length, start + 10)) {
                val l = s.substring(start, i + 1).toLong()
                if (l > Int.MAX_VALUE) {
                    return false
                }
                val d = l.toInt()
                if (result.size < 2 || d == result[result.size - 1] + result[result.size - 2]) {
                    result.add(d)
                    if (splitIntoFibonacciHelper(s, i + 1, result)) return true
                    result.removeAt(result.size - 1)
                }
            }
        return false
    }

    // https://leetcode.com/problems/minimum-unique-word-abbreviation/
    fun minAbbreviation(target: String, dictionary: Array<String>): String {
        return ""
    }


}