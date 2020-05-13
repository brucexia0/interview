package com.example.interview.practice

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class BST {
    fun reversePairs(nums: IntArray): Int {
        return 0
    }

    fun maxAncestorDiff(root: TreeNode?): Int {
        root ?: return 0
        val t = helper(root)
        return t.third
    }

    private fun helper(root: TreeNode?): Triple<Int, Int, Int> {
        root ?: return Triple(Int.MAX_VALUE, Int.MIN_VALUE, 0)
        val left = helper(root.left)
        val right = helper(root.right)
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

}