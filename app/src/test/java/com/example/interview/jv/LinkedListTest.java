package com.example.interview.jv;

import org.junit.Assert;
import org.junit.Test;

public class LinkedListTest {
    LinkedListIssues undertest = new LinkedListIssues();

    ListNode arrayToList(int[] arr) {
        ListNode head = null;
        ListNode curr = null;
        for (int i : arr) {
            ListNode node = new ListNode(i);
            if (head == null) {
                head = node;
            } else {
                curr.next = node;
            }
            curr = node;
        }
        return head;
    }

    @Test
    public void testFindMiddle() {
        int[] a1 = {1, 2, 3};
        int[] a2 = {1, 2, 3, 4};
        ListNode r1 = undertest.findMiddle(arrayToList(a1));
        Assert.assertEquals(2, r1.val);
        ListNode r2 = undertest.findMiddle(arrayToList(a2));
        Assert.assertEquals(2, r2.val);
    }

    @Test
    public void testReverse() {
        int[] a1 = {1, 2, 3};
        int[] a2 = {1, 2, 3, 4};
        ListNode r1 = undertest.findMiddle(arrayToList(a1));
        Assert.assertEquals(2, r1.val);
        ListNode r2 = undertest.findMiddle(arrayToList(a1));
        Assert.assertEquals(2, r2.val);
    }
}
