package com.example.interview.practice

import java.util.*

class BSTCodec {
    // Encodes a tree to a single string.
    val SEPERATOR = ","
    val NULL_STR = "null"
    fun serialize(root: TreeNode?): String {
        root ?: return NULL_STR
        val sb = StringBuilder()
        sb.append(root.`val`)
        sb.append(SEPERATOR).append(serialize(root.left))
        sb.append(SEPERATOR).append(serialize(root.right))
        return sb.toString()
    }

    // Decodes your encoded data to tree.
    fun deserialize(data: String?): TreeNode? {
        if (data.isNullOrEmpty()) return null
        val arr = data.split(SEPERATOR)
        val length = arr.size
        if (length == 0) return null
        return deserialize(LinkedList(arr))
    }

    fun deserialize(list: LinkedList<String>): TreeNode? {
        if (list.isEmpty()) {
            return null
        }
        val head = list.removeFirst()

        if (head == NULL_STR) {
            return null
        }
        val node = TreeNode(Integer.parseInt(head))
        node.left = deserialize(list)
        node.right = deserialize(list)
        return node
    }

    fun serializeV1(root: TreeNode?): String {
        root ?: return NULL_STR
        val queue = LinkedList<TreeNode>()
        queue.offer(root)
        val sb = StringBuilder()
        while (queue.isNotEmpty()) {
            val node = queue.poll()
            if (node == null) {
                sb.append(NULL_STR).append(SEPERATOR)
            } else {
                sb.append(node.`val`).append(SEPERATOR)
                queue.offer(node)
            }
        }
        sb.deleteCharAt(sb.length - 1)
        return sb.toString()
    }

    // Decodes your encoded data to tree.
    fun deserializeV1(data: String?): TreeNode? {
        if (data.isNullOrEmpty()) return null
        val arr = LinkedList(data.split(SEPERATOR))
        val length = arr.size
        if (length == 0) return null
        val root = if (arr[0] == NULL_STR) {
            null
        } else {
            TreeNode(Integer.parseInt(arr[0]))
        }
        root ?: return null
        val levelNodes = LinkedList<TreeNode>()
        levelNodes.offer(root)
        var index = 1
        while (levelNodes.isNotEmpty()) {
            for (i in levelNodes.indices) {
                val node = levelNodes.poll()
                val left = arr[index++]
                val right = arr[index++]
                if (left != NULL_STR) {
                    node.left = TreeNode(Integer.parseInt(left))
                    levelNodes.offer(node.left)
                }
                if (right != NULL_STR) {
                    node.right = TreeNode(Integer.parseInt(right))
                    levelNodes.offer(node.right)
                }
            }
        }

        return root
    }

    fun constructMaximumBinaryTree(nums: IntArray): TreeNode? {
        if (nums.isEmpty()) return null
        val lklist: LinkedList<TreeNode> = LinkedList()
        for (num in nums) {
            val cur = TreeNode(num)
            while (!lklist.isEmpty() && lklist.peekFirst().`val` < cur.`val`) {
                cur.left = lklist.pop()
            }
            if (!lklist.isEmpty()) {
                lklist.peekFirst().right = cur
            }
            lklist.push(cur)
            println("list ${lklist.joinToString(transform = { it.`val`.toString() })}")
        }

        return lklist.peekLast()
    }
}