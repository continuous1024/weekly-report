package com.huanyu.weekly.algorithm.daily.list;

public class ListSolution {
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    ListNode head;

    public void createList(int a) {
        if (head == null) {
            head = new ListNode(a);
            return;
        }

        ListNode p = head;
        while (p.next != null) {
            p = p.next;
        }
        p.next = new ListNode(a);
    }

    // 迭代反转单链表
    public ListNode reverseList(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode p = head;
        int i = 0;
        while (p.next != null) {
            p = p.next;
            i++;
        }

        ListNode nextHead = head.next;
        int k = i;
        for (int w = 0; w < k; w++) {
            ListNode q = head;
            for (int j = 0; j < i; j ++) {
                q = q.next;
            }
            head.next = q.next;
            q.next = head;
            head = nextHead;
            nextHead = nextHead.next;
            i--;
        }
        return head;
    }

    // 递归反转单链接
    public ListNode reverseList2(ListNode head) {
       if (head == null) {
           return null;
       }
       ListNode p = head;
       int i = 0;
       while (p.next != null) {
           p = p.next;
           i++;
       }
       return reverseList(head, i);
    }

    public ListNode reverseList(ListNode head, int i) {
        if (i == 0) {
            return head;
        }

        ListNode next = head.next;
        ListNode p = head;
        for (int j = 0; j < i; j++) {
            p = p.next;
        }
        head.next = p.next;
        p.next = head;
        return reverseList(next, i-1);
    }

    public void print(ListNode head) {
        ListNode p = head;
        while (p != null) {
            System.out.println(p.val);
            p = p.next;
        }
    }

    public static void main(String[] args) {
        ListSolution listSolution = new ListSolution();
        listSolution.createList(1);
        listSolution.createList(2);
        listSolution.createList(3);
        listSolution.createList(4);
        listSolution.print(listSolution.head);
        ListNode head = listSolution.reverseList2(listSolution.head);
        System.out.println("Reverse List: ");
        listSolution.print(head);
    }
}
