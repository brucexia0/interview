package com.example.interview.jv;

import java.util.Comparator;
import java.util.PriorityQueue;

public class LinkedListIssues {
    public ListNode reverse(ListNode head) {
        if (head == null) return null;
        ListNode prev = head;
        ListNode next = prev.next;
        prev.next = null;
        while (next != null) {
            ListNode tmp = next.next;
            next.next = prev;
            prev = next;
            next = tmp;
        }
        return prev;
    }

    public ListNode mergeKLists(ListNode[] lists) {

        Comparator<ListNode> comparable = new Comparator<ListNode>() {
            @Override
            public int compare(ListNode o1, ListNode o2) {
                return o1.val - o2.val;
            }
        };
        PriorityQueue<ListNode> queue = new PriorityQueue<>(comparable);
        for (ListNode node : lists) {
            queue.offer(node);
        }
        if (queue.isEmpty()) return null;
        ListNode curr = null;
        ListNode head = null;

        while (!queue.isEmpty()) {
            ListNode node = queue.poll();
            if (curr == null) {
                curr = node;
                head = node;
            } else {
                curr.next = node;
            }
            if (node.next != null) {
                queue.offer(node.next);
            }
            curr = node;
        }
        return head;
    }

    public ListNode findMiddle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    public int sizeOf(ListNode head) {
        int size = 0;
        while (head != null) {
            size++;
            head = head.next;
        }
        return size;
    }

    public void reorderList(ListNode head) {
        if (head == null || head.next == null) return;
        ListNode middle = findMiddle(head);

        ListNode reversed = reverse(middle.next);
        middle.next = null;
        ListNode curr = head;
        while (curr != null) {
            if(reversed==null){
                curr.next = null;
                break;
            }
            ListNode tmp = curr.next;
            ListNode tmpReversed = reversed.next;
            curr.next = reversed;
            reversed.next = tmp;
            curr = tmp;
            reversed = tmpReversed;
        }
        return;
    }
}
