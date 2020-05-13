package com.example.interview.practice

class DoubledLinkedNode(val key: Int, val value: Int) {
    var pre: DoubledLinkedNode? = null
    var next: DoubledLinkedNode? = null
}

class LURCache(private val capacity: Int) {
    val map = HashMap<Int, DoubledLinkedNode>()
    var head: DoubledLinkedNode? = null
    var tail: DoubledLinkedNode? = null

    operator fun get(key: Int): Int {
        val node = map.get(key)
        return node?.let {
            update(node)
            node.value
        } ?: -1
    }

    fun put(key: Int, value: Int) {
        if (map.containsKey(key)) {
            delete(map[key]!!)
        }
        val node = DoubledLinkedNode(key, value)
        map[key] = node
        if (map.size > capacity) {
            map.remove(tail!!.key)
            delete(tail!!)
        }
        add(node)
    }

    fun update(node: DoubledLinkedNode) {
        delete(node)
        add(node)
    }

    private fun delete(node: DoubledLinkedNode) {
        node.pre?.next = node.next
        tail = node.pre
    }

    private fun add(node: DoubledLinkedNode) {
        if (head == null) {
            head = node
            tail = node
        } else {
            // Add to front
            head!!.pre = node
            node.next = head!!
            head = node
        }
    }
}