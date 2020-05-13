package com.example.interview.practice

import kotlin.math.max

class TrieNode {
    var children = HashMap<Char, TrieNode>()
}

class StringTrie {
    val root = TrieNode()
    fun insert(s: String) {
        var currNode = root
        for (c in s) {
            if (currNode.children.contains(c)) {
                currNode = currNode.children[c]!!
            } else {
                val newNode = TrieNode()
                currNode.children[c] = newNode
                currNode = newNode
            }
        }
    }
}

class Trie {

    fun findMaximumXOR(nums: IntArray): Int {
        // Compute length L of max number in a binary representation
        var maxNum = nums.max()!!
        val length = Integer.toBinaryString(maxNum).length

        // zero left-padding to ensure L bits for each number
        val n = nums.size
        val bitmask = 1 shl length
        val strNums = Array(n) { "" }
        for (i in 0 until n) {
            strNums[i] = Integer.toBinaryString(bitmask or nums[i]).substring(1)
        }
        val trie = TrieNode()
        var maxXor = 0
        for (num in strNums) {
            var node: TrieNode = trie
            var xorNode: TrieNode = trie
            var currXor = 0
            for (bit in num.toCharArray()) {
                // insert new number in trie
                val bitNode = node.children.getOrDefault(bit,
                    TrieNode()
                )
                node.children[bit] = bitNode
                node = bitNode

                // compute max xor of that new number
                // with all previously inserted
                val toggledBit = if (bit == '1') '0' else '1'
                if (xorNode.children.containsKey(toggledBit)) {
                    currXor = currXor shl 1 or 1
                    xorNode = xorNode.children[toggledBit]!!
                } else {
                    currXor = currXor shl 1
                    xorNode = xorNode.children[bit]!!
                }
            }
            maxXor = max(maxXor, currXor)
        }
        return maxXor
    }

    fun findWords(board: Array<CharArray>, words: Array<String>): List<String> {
        val result = ArrayList<String>()
        //construct trie
        val trie = StringTrie()
        for (word in words) {
            trie.insert(word)
        }
        //Backtrack traverse the grid
        for (i in board.indices) {

        }
        return result
    }
}