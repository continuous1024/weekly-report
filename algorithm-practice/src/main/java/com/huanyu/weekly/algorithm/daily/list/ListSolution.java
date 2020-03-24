package com.huanyu.weekly.algorithm.daily.list;

public class ListSolution {
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode p = l1;
        ListNode q = l2;
        ListNode dummyHead = new ListNode(0);
        ListNode m = dummyHead;
        while (p != null && q != null) {
            if (p.val < q.val) {
                ListNode temp = p.next;
                p.next = null;
                m.next = p;
                p = temp;
            } else {
                ListNode temp = q.next;
                q.next = null;
                m.next = q;
                q = temp;
            }
            m = m.next;
        }

        if (p != null) {
            m.next = p;
        }

        if (q != null) {
            m.next = q;
        }
        return dummyHead.next;
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;
        ListNode p = dummyHead;
        ListNode q = dummyHead;
        for (int i = 0; i < n+1; i++) {
            if (q == null) {
                break;
            }
            q = q.next;
        }

        while (q != null) {
            p = p.next;
            q = q.next;
        }

        p.next = p.next.next;
        return dummyHead.next;
    }

    public ListNode removeNthFromEnd2(ListNode head, int n) {
        if (head == null) {
            return null;
        }

        int w = 1;
        ListNode h = head;
        while (h.next != null) {
            h = h.next;
            w++;
        }

        if (w == 1 && n == 1) {
            head = null;
            return null;
        }

        int r = w - n;
        if (r == 0) {
          head.val = head.next.val;
          head.next = head.next.next;
          return head;
        }
        ListNode p = head;
        for (int i = 1; i < r; i++) {
            if (p == null) {
                break;
            }
            p = p.next;
        }

        if (p != null && p.next != null) {
            p.next = p.next.next;
        }
        return head;
    }

    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
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

    public ListNode reverseList(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode p = head;
        int w = 1;
        while (p.next != null) {
            p = p.next;
            w++;
        }

        for (int i=0, middle=w>>1, j=w-1; i<middle; i++, j--) {
            ListNode left = head;
            for (int m = 0; m < i; m++) {
                left = left.next;
            }

            ListNode right = head;
            for (int n = 0; n < j; n++) {
                right = right.next;
            }
            int rightVal = right.val;
            right.val = left.val;
            left.val = rightVal;
        }
        return head;
    }

    // 迭代反转单链表
    public ListNode reverseList3(ListNode head) {
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
        listSolution.createList(4);
        ListSolution listSolution2 = new ListSolution();
        listSolution2.createList(1);
        listSolution2.createList(3);
        listSolution2.createList(4);
        ListNode head = listSolution.mergeTwoLists(listSolution.head, listSolution2.head);
        listSolution.print(head);
    }
}
