package com.example.interview.practice

import java.util.*

class BSTIterator(val root: TreeNode) {
    val stack = Stack<TreeNode>()

    init {
        pushLefts(root)
    }

    /** @return the next smallest number */
    fun next(): Int {
        val node = stack.pop()
        node.right?.let { pushLefts(it) }
        return node.`val`
    }

    /** @return whether we have a next smallest number */
    fun hasNext(): Boolean {
        return stack.isNotEmpty()
    }

    fun pushLefts(root: TreeNode) {
        var node: TreeNode? = root
        while (node != null) {
            stack.push(node)
            node = node.left
        }
    }
}
