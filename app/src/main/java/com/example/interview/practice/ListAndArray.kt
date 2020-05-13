package com.example.interview.practice

import java.lang.StringBuilder
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet
import kotlin.math.max
import kotlin.math.min

class Node(
    var `val`: Int,
    var next: Node? = null
)

class ListAndArray {
    // Insert into circular linkedlist
    fun insert(head: Node?, insertVal: Int): Node? {
        if (head == null) {
            val node = Node(insertVal)
            node.next = node
            return node
        }
        var prev = head!!
        var curr = head.next!!
        do {
            if (insertVal >= prev.`val` && insertVal < curr.`val`) {
                prev.next = Node(insertVal)
                    .apply { next = curr }
                return head
            }
            if (prev.`val` > curr.`val` && (prev.`val` <= insertVal || curr.`val` >= insertVal)) {
                prev.next = Node(insertVal)
                    .apply { next = curr }
                return head
            }
            prev = curr
            curr = prev.next!!
        } while (prev != head)

        prev.next = Node(insertVal)
            .apply { next = curr }
        return head
    }

    fun lengthOfLongestSubstring(s: String): Int {
        val size = s.length
        var count = 0
        val set = HashSet<Char>()
        var start = 0
        var end = 0
        while (end < size) {
            if (!set.contains(s[end])) {
                set.add(s[end++])
                count = max(count, end - start)
            } else {
                set.remove(s[start++])
            }
        }
        return count
    }

    fun lengthOfLongestSubstringV1(s: String): Int {
        var count = 0
        val map = HashMap<Char, Int>()
        var start = 0
        for (i in s.indices) {
            if (map.contains(s[i])) {
                //Notice here we only reset the start if the index is greater
                start = max(start, map[s[i]]!! + 1)
            }
            count = max(count, i - start + 1)

            map.put(s[i], i)
        }
        return count
    }

    fun searchRotatedSortedArray(nums: IntArray, target: Int): Int {
        val size = nums.size
        if (nums.isEmpty() || (target < nums[0] && target > nums[size - 1]))
            return -1
        var left: Int
        var right: Int
        if (nums[size - 1] < nums[0]) {//find the rotation index
            val rotationPos = findRotationPos(nums)
            println("rotationpos $rotationPos")
            if (target == nums[0]) return 0
            if (target > nums[0]) {
                left = 0
                right = rotationPos - 1
            } else {
                left = rotationPos
                right = size - 1
            }
        } else {
            left = 0
            right = size - 1
        }
        // found the notation point, now do binary search
        while (left <= right) {
            val mid = (right + left) / 2
            if (target == nums[mid]) return mid
            if (nums[mid] < target) {
                left = mid + 1
            } else {
                right = mid - 1
            }
        }
        return -1
    }

    private fun findRotationPos(nums: IntArray): Int {
        var left = 0
        var right = nums.size - 1
        while (left <= right) {
            var mid = (right + left) / 2
            if (nums[mid] > nums[mid + 1]) return mid + 1
            if (nums[mid] > nums[right]) {
                left = mid + 1
            } else {
                right = mid
            }
        }
        return 0
    }

    fun shortestSubarray(A: IntArray, K: Int): Int {
        if (A.isEmpty()) return -1
        if (A.size == 1 && A[0] >= K) {
            return 1
        }
        val sum = Array(A.size + 1) { 0 }
        val queue: Deque<Int> = LinkedList()
        var result = Integer.MAX_VALUE
        for (i in 1 until A.size + 1) {
            sum[i] = sum[i - 1] + A[i - 1]
        }
        queue.offerLast(0)
        for (i in 1 until A.size) {
            while (queue.isNotEmpty() && sum[i] <= sum[queue.peekLast()]) {
                queue.pollLast()
            }
            queue.offerLast(i)
            while (queue.isNotEmpty() && sum[i + 1] - sum[queue.peekFirst()] >= K) {
                val x = queue.pollFirst()
                result = min(result, i - x + 1)
            }
        }
        if (result == Integer.MAX_VALUE) return -1
        return result
    }

    // list of tasks and minimum idle time of n
    fun leastInterval(tasks: CharArray, n: Int): Int {
        if (n == 0) return tasks.size
        if (tasks.isEmpty()) return 0
        val groupedChars =
            tasks.groupBy { it }.values.map { it.size }.toMutableList()
        var cycles = 0
        groupedChars.sortDescending()
        while (groupedChars[0] > 0) {
            for (i in 0 until n + 1) {
                if (groupedChars[0] == 0) break
                if (i < groupedChars.size && groupedChars[i] > 0) {
                    groupedChars[i]--
                }
                cycles++
            }
            groupedChars.sortDescending()
        }
        return cycles
    }

    //Interval intersection. Arrays are sorted by first element
    //[[0,2],[5,10],[13,23],[24,25]]
    //[[1,5],[8,12],[15,24],[25,26]]
    fun intervalIntersection(A: Array<IntArray>, B: Array<IntArray>): Array<IntArray> {
        val result = ArrayList<IntArray>()
        var indexA = 0
        var indexB = 0
        while (indexA < A.size && indexB < B.size) {
            val biggerStart = Math.max(A[indexA][0], B[indexB][0])
            val smallerEnd = Math.min(A[indexA][1], B[indexB][1])
            if (smallerEnd >= biggerStart) {
                result.add(intArrayOf(biggerStart, smallerEnd))
            }
            if (smallerEnd == A[indexA][1]) indexA++
            else indexB++
        }
        return result.toTypedArray()
    }

    fun mergeKLists(lists: Array<ListNode?>): ListNode? {
        if (lists.isNullOrEmpty()) return null
        val queue = PriorityQueue<ListNode>(object : Comparator<ListNode> {
            override fun compare(o1: ListNode, o2: ListNode): Int {
                if (o1 == o2) return 0
                return o1.`val`.compareTo(o2.`val`)
            }
        })
        for (node in lists) {
            node?.let { queue.offer(it) }
        }
        var root: ListNode? = null
        var head: ListNode? = null
        while (queue.isNotEmpty()) {
            if (head == null) {
                head = queue.poll()
                root = head
            } else {
                head.next = queue.poll()
                head = head?.next
            }

            head?.next?.let { queue.offer(it) }
        }
        return root
    }

    fun missingElement(nums: IntArray, k: Int): Int {
        if (nums.isEmpty()) return k
        if (nums.size == 1) return nums[0] + k
        var missingK = k
        for (i in 1 until nums.size) {
            val gap = nums[i] - nums[i - 1] - 1
            if (gap >= missingK) {
                return nums[i - 1] + missingK
            } else {
                missingK -= gap
            }
        }
        return nums[nums.size - 1] + missingK
    }

    fun maximumSwap(num: Int): Int {
        val numStr = num.toString()
        val length = numStr.length
        if (length < 2) return num
        val dp = Array<Pair<Char, Int>?>(length) { null }
        dp[length - 1] = numStr[length - 1] to length - 1
        for (i in numStr.length - 2 downTo 1) {
            val value = dp[i + 1]!!.first
            dp[i] = if (numStr[i] > value) numStr[i] to i else dp[i + 1]
        }
        for (i in 0 until numStr.length - 1) {
            val (value, index) = dp[i + 1]!!
            if (value > numStr[i]) {
                val sb = StringBuilder(numStr)
                val tmp = sb[i]
                sb[i] = sb[index]
                sb[index] = tmp
                return Integer.parseInt(sb.toString())
            }
        }
        return num
    }

    fun findClosestElements(arr: IntArray, k: Int, x: Int): List<Int> {
        val size = arr.size
        if (k >= size) return arr.toList()
        if (x <= arr[0]) return arr.slice(0 until k)
        if (x >= arr[size - 1]) return arr.slice(size - k until size)
        val index = arr.binarySearch(x, 0, size - 1)
        val trueIndex = if (index > 0) index else -index - 1
        var low = max(0, trueIndex - k)
        var high = min(size - 1, trueIndex + k - 1)

        while (high - low >= k) {
            if (low == 0 || x - arr[low] <= arr[high] - x) high--
            else low++
        }
        return arr.slice(low until high)
    }

    fun countBattleships(board: Array<CharArray>): Int {
        var count = 0
        val dot = '.'
        val x = 'X'
        for (i in board.indices) {
            for (j in board.indices) {
                if (board[i][j] == x) {
                    if (i == 0 && j == 0) count++
                    else if (i == 0 && board[i][j - 1] == dot) count++
                    else if (j == 0 && board[i - 1][j] == dot) count++
                    else if (i > 0 && j > 0 && board[i - 1][j] == dot && board[i][j - 1] == dot) {
                        count++
                    }
                }
            }
        }
        return count
    }
}