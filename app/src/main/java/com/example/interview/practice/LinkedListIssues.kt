package com.example.interview.practice

class LinkedListIssues {
    fun swapPairs(head: ListNode?): ListNode? {
        return swapPairHelper(head)
    }

    private fun swapPairHelper(head: ListNode?): ListNode? {
        head ?: return null
        head.next ?: return head

        val newHead = head.next!!
        val t = newHead.next
        newHead.next = head

        head.next = swapPairHelper(t)
        return newHead
    }
}