package com.example.interview.practice


class LinkedListCopy {
    class Node(val `val`: Int) {
        var next: Node? = null
        var random: Node? = null
    }

    val copiedNodesMap = HashMap<Node, Node>()
    fun copyRandomList(head: Node?): Node? {
        head ?: return null
        if (copiedNodesMap.containsKey(head)) return copiedNodesMap[head]!!
        val node = Node(head.`val`)
        copiedNodesMap.put(head, node)
        node.next = copyRandomList(head.next)
        node.random = copyRandomList(head.random)

        return node
    }

    fun getClonedNode(head: Node?): Node? {
        head ?: return null
        if (copiedNodesMap.containsKey(head)) {
            return copiedNodesMap[head]
        } else {
            val node = Node(head.`val`)
            copiedNodesMap[head] = node
            return node
        }
    }

    fun copyRandomListIterative(head: Node?): Node? {
        head ?: return null

        var oldNode = head
        val newNode = Node(head.`val`)
        copiedNodesMap.put(head, newNode)

        while (oldNode != null) {
            newNode.next = getClonedNode(oldNode.random)
            newNode.random = getClonedNode(oldNode.random)
            oldNode = oldNode.next
        }
        return newNode
    }
}