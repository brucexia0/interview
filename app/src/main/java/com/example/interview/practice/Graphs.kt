package com.example.interview.practice

import com.example.interview.STEPS
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.min

open class Graphs {

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

    //https://leetcode.com/problems/course-schedule-ii/
    // Course Schedule II
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
                val parentList = parents.getOrDefault(list[i], ArrayList())
                parentList.add(list[i - 1])
                parents[list[i]] = parentList
            }
        }

        for (i in 0 until numCourses) {
            if (visited[i] == 0) {
                if (!findOrderHelper(i, parents, stack, visited)) {
                    return intArrayOf()
                }
            }
        }
        while (stack.isNotEmpty()) {
            result.add(stack.pop())
        }
        return result.toIntArray()
    }

    fun findOrderHelper(
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
                0 -> if (!findOrderHelper(c, parents, stack, visited)) return false
                else -> {
                }
            }
        }
        visited[course] = 2
        stack.push(course)
        return true
    }

    fun isBipartite(graph: Array<IntArray>): Boolean {
        val colors = IntArray(graph.size) { -1 }
        for (i in graph.indices) {
            if (colors[i] == -1) {
                colors[i] = 0
                val stack = Stack<Int>()
                stack.push(i)
                while (stack.isNotEmpty()) {
                    val v = stack.pop()
                    for (neighbor in graph[v]) {
                        if (colors[neighbor] == -1) {
                            colors[neighbor] = colors[v] xor 1
                            stack.push(v)
                        } else if (colors[neighbor] == colors[v]) return false
                    }
                }
            }
        }
        return true
    }

    fun countComponents(n: Int, edges: Array<IntArray>): Int {
        val disjointSet = DisjointSet(n)
        for (edge in edges) {
            disjointSet.union(edge[0], edge[1])
        }
        return disjointSet.disjointSetCount()
    }

    class DisjointSet(val n: Int) {
        val parents = IntArray(n).apply { for (i in indices) this[i] = i }
        val weights = IntArray(n) { 1 }

        fun union(a: Int, b: Int) {
            if (a == b) return
            val aParent = find(a)
            val bParent = find(b)
            if (aParent != bParent) {
                if (weights[aParent] >= weights[bParent]) {
                    parents[b] = aParent
                    weights[aParent] += weights[bParent]
                } else {
                    parents[a] = bParent
                    weights[bParent] += weights[aParent]
                }
            }
            union(aParent, bParent)
        }

        fun find(x: Int): Int {
            var r = x
            while (parents[r] != r) {
                r = parents[r]
            }
            return r
        }

        fun disjointSetCount() =
            parents.withIndex()
                .sumBy { indexedValue -> if (indexedValue.index != indexedValue.value) 0 else 1 }
    }

    fun networkDelayTime(times: Array<IntArray>, N: Int, K: Int): Int {
        val edges = times.groupBy { it[0] }.mapValues { it.value.map { it[1] to it[2] } }
        val node = edges[K]
        val visitStatus = IntArray(N + 1)
        val timeToNode = IntArray(N + 1) { Int.MAX_VALUE }
        visitStatus[0] = 2
        timeToNode[0] = 0
        node ?: return -1
        visitStatus[K] = 1
        timeToNode[K] = 0
        networkDelayTimeDfs(edges, node, 0, visitStatus, timeToNode)
        visitStatus[K] = 2
        if (visitStatus.any { it in 0..1 }) return -1
        return timeToNode.max()!!
    }

    private fun networkDelayTimeDfs(
        nodes: Map<Int, List<Pair<Int, Int>>>,
        edges: List<Pair<Int, Int>>,
        currTime: Int,
        visitStatus: IntArray,
        timeToNode: IntArray
    ) {
        for ((num, time) in edges) {
            if (visitStatus[num] == 1) continue
            visitStatus[num] = 1
            timeToNode[num] = min(currTime + time, timeToNode[num])
            val nextEdges = nodes[num]
            nextEdges?.let {
                networkDelayTimeDfs(nodes, it, timeToNode[num], visitStatus, timeToNode)
            }
            visitStatus[num] = 2
        }
    }

    // https://leetcode.com/problems/optimize-water-distribution-in-a-village/
    // 1168. Optimize Water Distribution in a Village
    fun minCostToSupplyWater(n: Int, wells: IntArray, pipes: Array<IntArray>): Int {
        val heap = PriorityQueue<IntArray> { o1, o2 -> o1[2].compareTo(o2[2]) }
        heap.addAll(pipes)
        heap.addAll(wells.mapIndexed { index, i ->
            intArrayOf(
                0,
                index + 1,
                i
            )
        })//Virtual 0 to house to make it an edge
        val unionFind = UnionFind(n + 1)//+1 for virtual 0
        while (heap.isNotEmpty()) {
            val arr = heap.poll()!!
            unionFind.union(arr[0], arr[1], arr[2])
        }
        return unionFind.totalCost
    }

    class UnionFind(val n: Int) {
        val parents = IntArray(n).apply { for (i in 0 until n) this[i] = i }
        val sizes = IntArray(n) { 1 }
        var totalCost = 0
        fun find(i: Int): Int {
            var p = i
            while (parents[p] != p) {
                p = parents[p]
            }
            return p
        }

        fun union(p: Int, q: Int, cost: Int) {
            val pParent = find(p)
            val qParent = find(q)
            if (pParent == qParent) {
                return//This is critical to make sure we don't add the cost if there house is already covered
            }
            if (sizes[pParent] > sizes[qParent]) {
                parents[qParent] = parents[pParent]
                sizes[pParent] += sizes[qParent]
            } else {
                parents[pParent] = parents[qParent]
                sizes[qParent] += sizes[pParent]
            }
            totalCost += cost
        }
    }

    fun eventualSafeNodes(graph: Array<IntArray>): List<Int> {
        val result = ArrayList<Int>()
        val queue = LinkedList<Int>()
        val reverseGraph = Array(graph.size) { ArrayList<Int>() }
        val mutableGraph = graph.map { it.toList().toMutableList() }

        for ((index, arr) in graph.withIndex()) {
            for (value in arr) {
                reverseGraph[value].add(index)
            }
            if (arr.isEmpty()) {
                queue.offer(index)
            }
        }
        while (queue.isNotEmpty()) {

            val node = queue.poll()
            result.add(node)
            val mutableList = reverseGraph[node]
            for (i in mutableList) {
                mutableGraph[i].remove(node)
                if (mutableGraph[i].isEmpty()) queue.offer(i)
            }
        }

        return result
    }

    fun canFinish(numCourses: Int, prerequisites: Array<IntArray>): Boolean {
        val parents = prerequisites.groupBy { it[0] }
            .mapValues { (key, list) -> list.map { it[1] }.toMutableList() }.toMutableMap()
        val children = prerequisites.groupBy { it[1] }.toMutableMap()
            .mapValues { (key, list) -> list.map { it[0] }.toMutableList() }
        val queue = LinkedList<Int>()
        for (i in 0 until numCourses) {
            if (!parents.containsKey(i)) {
                queue.offer(i)
            }
        }
        while (queue.isNotEmpty()) {
            val curr = queue.poll()
            val dependents = children[curr]
            dependents?.forEach {
                val pList = (parents[it] ?: error("parents of $it shouldn't be null"))
                pList.remove(curr)
                if (pList.isEmpty()) {
                    parents.remove(it)
                    queue.offer(it)
                }
            }
        }
        if (parents.isNotEmpty()) return false
        return true
    }

    fun findMinHeightTrees(n: Int, edges: Array<IntArray>): List<Int> {
        val graph = HashMap<Int, ArrayList<Int>>()
        val degree = IntArray(n)
        edges.forEach {
            val l0 = graph.getOrDefault(it[0], ArrayList())
            l0 += it[1]
            graph[it[0]] = l0
            val l1 = graph.getOrDefault(it[1], ArrayList())
            l1 += it[0]
            graph[it[1]] = l1
            degree[it[0]]++
            degree[it[1]]++
        }
        println("graph $graph, degree ${degree.joinToString()}")
        var nodeCount = n
        while (nodeCount > 2) {
            val queue = LinkedList<Int>()
            for (i in 0 until n) {
                if (degree[i] == 1) {
                    queue.offer(i)
                    nodeCount--
                    degree[i] = -1
                }
            }
            while (queue.isNotEmpty()) {
                val curr = queue.poll()
                val neighbors = graph[curr]
                neighbors?.forEach { degree[it]-- }
            }
        }
        val result = ArrayList<Int>()
        degree.forEachIndexed { index, value ->
            if (value in 0..1) result.add(
                index
            )
        }
        return result
    }
}