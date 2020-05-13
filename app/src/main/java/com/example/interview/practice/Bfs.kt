package com.example.interview.practice

import com.example.interview.STEPS
import java.util.*

class Bfs {
    val INF = Int.MAX_VALUE

    fun wallsAndGatesV1(rooms: Array<IntArray>) {
        val queue: Queue<Pair<Int, Int>> = LinkedList()

        for (i in rooms.indices) {
            for (j in rooms[0].indices) {
                if (rooms[i][j] == 0) {
                    queue.offer(i to j)
                }
            }
        }
        while (queue.isNotEmpty()) {
            val size = queue.size
            for (i in 0 until size) {
                val (row, col) = queue.poll()
                for ((x, y) in STEPS) {
                    val nRow = row + x
                    val nCol = col + y
                    if (nRow !in rooms.indices || nCol !in rooms[0].indices || rooms[nRow][nCol] != INF) continue
                    rooms[nRow][nCol] = rooms[row][col] + 1
                    queue.offer(nRow to nCol)
                }
            }
        }
    }

    fun wallsAndGates(rooms: Array<IntArray>) {
        for (i in rooms.indices) {
            for (j in rooms[0].indices) {
                if (rooms[i][j] == INF) {
                    val steps = bfs(rooms, i, j)
                    if (steps != INF) rooms[i][j] = steps
                }
            }
        }
    }

    fun bfs(rooms: Array<IntArray>, row: Int, col: Int): Int {
        val queue: Queue<Pair<Int, Int>> = LinkedList()
        val visited = Array(rooms.size) { BooleanArray(rooms[0].size) }
        queue.offer(row to col)
        visited[row][col] = true

        var steps = 0
        while (queue.isNotEmpty()) {
            val size = queue.size
            for (i in 0 until size) {
                val (cR, cC) = queue.poll()
                for ((r, c) in STEPS) {
                    val nr = cR + r
                    val nc = cC + c
                    if (nr !in rooms.indices || nc !in rooms[0].indices || rooms[nr][nc] == -1 || visited[nr][nc]) {
                        continue
                    }

                    if (rooms[nr][nc] == 0) return steps + 1
                    queue.offer(nr to nc)
                    visited[nr][nc] = true
                }
            }
            steps++
        }
        return Int.MAX_VALUE
    }
}