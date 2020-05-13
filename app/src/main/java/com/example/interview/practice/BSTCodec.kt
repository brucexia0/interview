package com.example.interview.practice

import java.lang.StringBuilder
import java.util.*
import kotlin.collections.ArrayList

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
}