package com.example.interview.practice

import com.example.interview.STEPS
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class Graphs {
    //Shortest distance to all the buildings. 1 - building, 0 - empty,
    fun shortestDistance(grid: Array<IntArray>?): Int {
        if (grid.isNullOrEmpty() || grid[0].isEmpty()) {
            return 0
        }
        val rowSize = grid.size
        val colSize: Int = grid[0].size

        val distances =
            Array(rowSize) { IntArray(colSize) } //Distances to all builds from this point
        val reach =
            Array(rowSize) { IntArray(colSize) }//How many building can be reached from this point
        var buildingNumber = 0

        for (i in 0 until rowSize) {
            for (j in 0 until colSize) {
                if (grid[i][j] == 1) {
                    buildingNumber++
                    val queue: Queue<IntArray> = LinkedList()
                    queue.offer(intArrayOf(i, j))
                    val visited = Array(rowSize) { BooleanArray(colSize) }//This is per building
                    var level = 1
                    while (queue.isNotEmpty()) {
                        val size: Int = queue.size
                        for (m in 0 until size) {
                            val pos: IntArray = queue.poll()
                            for ((x, y) in STEPS) {
                                val nextRow = pos[0] + x
                                val nextCol = pos[1] + y
                                if (nextRow in 0 until rowSize && nextCol in 0 until colSize && !visited[nextRow][nextCol] && grid[nextRow][nextCol] == 0) {
                                    visited[nextRow][nextCol] = true
                                    distances[nextRow][nextCol] += level
                                    reach[nextRow][nextCol] += 1
                                    queue.offer(intArrayOf(nextRow, nextCol))
                                }
                            }
                        }
                        level++
                    }
                }
            }
        }

        var min = Int.MAX_VALUE
        for (i in 0 until rowSize) {
            for (j in 0 until colSize) {
                if (grid[i][j] == 0 && reach[i][j] == buildingNumber && distances[i][j] < min) {
                    min = distances[i][j]
                }
            }
        }
        return if (min == Int.MAX_VALUE) -1 else min
    }

    fun findOrder(
        numCourses: Int,
        prerequisites: Array<IntArray>
    ): IntArray {
        val result = ArrayList<Int>()
        val parents = HashMap<Int, ArrayList<Int>>()//map for
        val stack: Deque<Int> = ArrayDeque()
        val visited = IntArray(numCourses)

        prerequisites.filter { it.size > 1 }.forEach { list ->
            for (i in 1 until list.size) {
                val p = parents.getOrDefault(list[i], ArrayList())
                p.add(list[i - 1])
                parents[list[i]] = p
            }
        }

        for (i in 0 until numCourses) {
            if (visited[i] == 0) {
                if (!dfs(i, parents, stack, visited)) {
                    return intArrayOf()
                }
            }
        }
        while (stack.isNotEmpty()) {
            result.add(stack.pop())
        }
        return result.toIntArray()
    }

    fun dfs(
        course: Int,
        parents: HashMap<Int, ArrayList<Int>>,
        stack: Deque<Int>,
        visited: IntArray
    ): Boolean {
        if (!parents.containsKey(course)) {
            stack.push(course)
            visited[course] = 2
            return true
        }
        visited[course] = 1
        for (c in parents[course]!!) {
            when (visited[c]) {
                1 -> return false
                0 -> if (!dfs(c, parents, stack, visited)) return false
                else -> {
                }
            }
        }
        visited[course] = 2
        stack.push(course)
        return true
    }


}