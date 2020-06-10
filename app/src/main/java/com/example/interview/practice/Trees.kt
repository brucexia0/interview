package com.example.interview.practice

import java.util.*
import java.util.concurrent.LinkedBlockingQueue
import kotlin.collections.ArrayList
import kotlin.math.max

class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

class Trees {
    fun rightSideView(root: TreeNode): List<Int> {
        val result = ArrayList<Int>()
        val queue = LinkedBlockingQueue<TreeNode>()
        root.let { queue.add(root) }

        while (queue.isNotEmpty()) {
            var size = queue.size
            for (i in 0 until size) {
                val node = queue.remove()
                if (i == 0) result.add(node.`val`)
                size--
                node.right?.let { queue.add(it) }
                node.left?.let { queue.add(it) }
            }
        }
        return result
    }

    fun isValidBST(root: TreeNode?): Boolean {
        return isValidBst(root, null, null)
    }

    private fun isValidBst(root: TreeNode?, min: TreeNode?, max: TreeNode?): Boolean {
        root ?: return true
        min?.let { if (root.`val` <= min.`val`) return false }
        max?.let { if (root.`val` >= max.`val`) return false }
        return isValidBst(root.left, min, root) && isValidBst(root.right, root, max)
    }

    var count = 0

    class NodeData(
        var isBst: Boolean = false,
        var size: Int = 0,
        var min: Int? = null,
        var max: Int? = null
    )

    fun largestBSTSubtree(root: TreeNode?): Int {
        isValidBstV1(root)
        return count
    }

    private fun isValidBstV1(root: TreeNode?): NodeData {
        root ?: return NodeData(true)
        val nodeData = NodeData()
        val leftNodeData = isValidBstV1(root.left)
        val rightNodeData = isValidBstV1(root.right)
        if ((leftNodeData.isBst && (leftNodeData.max == null || root.`val` > leftNodeData.max!!))
            && (rightNodeData.isBst && (rightNodeData.min == null || root.`val` < rightNodeData.min!!))
        ) {
            val myCount = leftNodeData.size + rightNodeData.size + 1
            count = max(count, myCount)
            nodeData.isBst = true
            nodeData.max = rightNodeData.max ?: root.`val`
            nodeData.min = leftNodeData.min ?: root.`val`
            nodeData.size = myCount
        }

        return nodeData
    }

    fun verticalTraversal(root: TreeNode?): List<List<Int>> {
        val result = ArrayList<ArrayList<Int>>()
        val nodesList = ArrayList<NodeLocation>()
        if (root == null) return result
        verticalTraversal(root, 0, 0, nodesList)
        nodesList.sort()
        var list = ArrayList<Int>()
        list.add(nodesList[0].value)
        result.add(list)
        for (i in 1 until nodesList.size) {
            if (nodesList[i].x != nodesList[i - 1].x) {
                list = ArrayList<Int>()
                result.add(list)
            }
            list.add(nodesList[i].value)
        }
        return result
    }

    private fun verticalTraversal(
        root: TreeNode,
        x: Int,
        y: Int,
        nodeList: ArrayList<NodeLocation>
    ) {
        nodeList.add(
            NodeLocation(
                x,
                y,
                root.`val`
            )
        )
        root.left?.let { verticalTraversal(it, x - 1, y + 1, nodeList) }
        root.right?.let { verticalTraversal(it, x + 1, y + 1, nodeList) }
    }

    class NodeLocation(val x: Int, val y: Int, val value: Int) : Comparable<NodeLocation> {
        override fun compareTo(other: NodeLocation): Int {
            if (x != other.x) return x.compareTo(other.x)
            if (y != other.y) return y.compareTo(other.y)
            return value.compareTo(other.value)
        }
    }

    //Iterative solution
    fun inOrder(root: TreeNode?) {
        root ?: return
        val stack = Stack<TreeNode>()
        var node = root
        while (node != null || stack.isNotEmpty()) {
            while (node != null) {
                stack.push(node)
                node = node.left
            }

            node = stack.pop()
            print(node.`val`)
            node = node.right
        }
    }

    fun preOrder(root: TreeNode?) {
        root ?: return
        val stack = Stack<TreeNode>()
        var node = root
        while (node != null) {
            stack.push(node)
            node = node.left
        }
        while (stack.isNotEmpty()) {
            val node = stack.pop()
            print(node.`val`)
            node.right?.let { stack.push(it) }
        }
    }

    //Using two stacks
    fun postOrder(root: TreeNode?) {
        root ?: return
        val stack1 = Stack<TreeNode>()
        val stack2 = Stack<TreeNode>()
        stack1.push(root)
        while (stack1.isNotEmpty()) {
            val node = stack1.pop()
            stack2.push(node)
            node.left?.let { stack1.push(it) }
            node.right?.let { stack1.push(it) }
        }
        while (stack2.isNotEmpty()) {
            print(stack2.pop().`val`)
        }
    }

    // Using one stack
    fun postOrderV2(root: TreeNode?) {
        root ?: return
        val stack = Stack<TreeNode>()
        stack.push(root)
        while (stack.isNotEmpty()) {
            var node = stack.pop()
            if (node.right == stack.peek()) {
                stack.pop()
                stack.push(node)
                node = node.right
            }
            while (node != null) {
                node.right?.let { stack.push(it) }
                stack.push(node)
                node = node.left
            }
        }
    }

    // Lowest common ancestor of deepest leaves in binary tree
    var deepest = 0
    var lca: TreeNode? = null

    fun lcaDeepestLeaves(root: TreeNode?): TreeNode? {
        helper(root, 0)
        return lca
    }

    private fun helper(node: TreeNode?, depth: Int): Int {
        deepest = Math.max(deepest, depth)
        if (node == null) {
            return depth
        }
        val left = helper(node.left, depth + 1)
        val right = helper(node.right, depth + 1)
        if (left == deepest && right == deepest) {
            lca = node
        }
        return Math.max(left, right)
    }


    fun widthOfBinaryTree(root: TreeNode?): Int {
        root ?: return 0
        val queue: Queue<Pair<TreeNode, Int>> = LinkedList()
        queue.offer(root to 1)
        var maxWidth = 0
        var depth = 0
        while (queue.isNotEmpty()) {
            val size = queue.size
            var firstPos = Int.MIN_VALUE
            for (i in 0 until size) {
                val (node, pos) = queue.poll()
                if (i == 0) {
                    firstPos = pos
                }
                if (i == size - 1) {
                    maxWidth = max(maxWidth, pos - firstPos + 1)
                }
                node.left?.let { queue.offer(it to pos * 2) }
                node.right?.let { queue.offer(it to pos * 2 + 1) }
            }
            depth++

        }
        return maxWidth
    }

    var maxLen = 0
    fun longestConsecutive(root: TreeNode?): Int {

        return maxLen
    }

    fun longestPath(root: TreeNode?): Pair<Int, Int> {
        root ?: return 0 to 0
        var decrease = 1
        var increase = 1
        root.left?.let {
            val (inc, dec) = longestPath(root.left)
            if (root.`val` == it.`val` + 1) {
                increase += inc
            } else if (root.`val` == it.`val` - 1) {
                decrease += dec
            }
        }
        root.right?.let {
            val (inc, dec) = longestPath(root.right)
            if (root.`val` == it.`val` + 1) {
                increase = max(increase, inc + 1)
            } else if (root.`val` == it.`val` - 1) {
                decrease = max(dec, dec + 1)
            }
        }
        maxLen = max(maxLen, increase + decrease - 1)
        return increase to decrease
    }

    var maxSum = 0
    fun maxPathSum(root: TreeNode?): Int {
        maxGain(root)
        return maxSum
    }

    private fun maxGain(root: TreeNode?): Int {
        root ?: return 0
        val leftGain = maxGain(root.left)
        val rightGain = maxGain(root.right)
        val sum = leftGain + rightGain + root.`val`
        maxSum = max(maxSum, sum)
        return max(leftGain, rightGain) + root.`val`
    }
}