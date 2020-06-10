package com.example.interview.practice

import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class BST {
    fun reversePairs(nums: IntArray): Int {
        return 0
    }

    fun maxAncestorDiff(root: TreeNode?): Int {
        root ?: return 0
        val t = maxAncesterDiffHelper(root)
        return t.third
    }

    private fun maxAncesterDiffHelper(root: TreeNode?): Triple<Int, Int, Int> {
        root ?: return Triple(Int.MAX_VALUE, Int.MIN_VALUE, 0)
        val left = maxAncesterDiffHelper(root.left)
        val right = maxAncesterDiffHelper(root.right)
        var min = min(left.first, right.first)
        var max = max(left.second, right.second)
        var maxDiff = max(left.third, right.third)
        if (min != Int.MAX_VALUE) {
            maxDiff = max(abs(root.`val` - min), maxDiff)
        }
        if (max != Int.MIN_VALUE) {
            maxDiff = max(abs(root.`val` - max), maxDiff)
        }
        min = min(min, root.`val`)
        max = max(max, root.`val`)
        return Triple(min, max, maxDiff)
    }

    fun closestKValues(root: TreeNode?, target: Double, k: Int): List<Int> {
        root ?: return emptyList()

        val result = ArrayList<Int>()
        val left = PriorityQueue<TreeNode> { o1, o2 -> o2.`val`.compareTo(o1.`val`) }
        val right = PriorityQueue<TreeNode> { o1, o2 -> o1.`val`.compareTo(o2.`val`) }
        closestKValuesHelper(root, target, left, right)
        for (i in 0 until k) {
            val l = left.peek()
            val r = right.peek()
            if (l != null && (r == null || abs(target - l.`val`) < abs(target - r.`val`))) {
                result += l.`val`
                left.poll()
                closestKValuesHelper(l.left, target, left, right)
            } else {
                result += r.`val`
                right.poll()
                closestKValuesHelper(r.right, target, left, right)
            }
        }
        return result
    }

    private fun closestKValuesHelper(
        root: TreeNode?,
        target: Double,
        left: PriorityQueue<TreeNode>,
        right: PriorityQueue<TreeNode>
    ) {
        root ?: return
        if (root.`val` > target) {
            right.offer(root)
            closestKValuesHelper(root.left, target, left, right)
        } else {
            left.offer(root)
            closestKValuesHelper(root.right, target, left, right)
        }
    }

    fun generateTrees(n: Int): List<TreeNode?> {
        return generateTrees(1, n)
    }

    private fun generateTrees(start: Int, end: Int): List<TreeNode?> {
        val result = ArrayList<TreeNode?>()
        if (start > end) {
            result.add(null)
            return result
        }
        for (i in start..end) {
            val left = generateTrees(start, i - 1)
            val right = generateTrees(i + 1, i - 1)
            for (leftRoot in left) {
                for (rightRoot in right) {
                    val node = TreeNode(i)
                    node.left = leftRoot
                    node.right = rightRoot
                    result.add(node)
                }
            }
        }
        return result
    }

    fun nodeDist(root: TreeNode, p: Int, q: Int): Int {
        return nodeDistHelper(root, p, q, 0)
    }

    private fun nodeDistHelper(root: TreeNode?, p: Int, q: Int, height: Int): Int {
        root ?: return 0
        if (p == root.`val` || q == root.`val`) return height
        if (root.`val` > q) return nodeDistHelper(
            root.left,
            p,
            q,
            if (height == 0) 0 else height + 1
        )
        if (root.`val` < p) return nodeDistHelper(
            root.right,
            p,
            q,
            if (height == 0) 0 else height + 1
        )
        //we've found the common root
        return nodeDistHelper(root.left, p, q, height + 1) +
                nodeDistHelper(root.right, p, q, height + 1) + 1
    }

    fun recoverTree(root: TreeNode?) {
        val nodes = Array<TreeNode?>(2) { null }
        recoverTreeHelper(root, nodes)
        println("nodes ${nodes[0]!!.`val`} ${nodes[1]!!.`val`}")
        val tmp = nodes[0]!!
        nodes[0]!!.`val` = nodes[1]!!.`val`
        nodes[1]!!.`val` = tmp.`val`

    }

    var predecesor: TreeNode? = null
    private fun recoverTreeHelper(x: TreeNode?, nodes: Array<TreeNode?>) {
        x ?: return
        recoverTreeHelper(x.left, nodes)
        predecesor?.let {
            if (x.`val` < it.`val`) {
                nodes[1] = x
                if (nodes[0] == null) nodes[0] = predecesor
                else return
            }
        }
        predecesor = x
        recoverTreeHelper(x.right, nodes)
    }
}