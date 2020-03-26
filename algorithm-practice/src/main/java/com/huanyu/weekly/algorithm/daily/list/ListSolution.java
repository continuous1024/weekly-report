package com.huanyu.weekly.algorithm.daily.list;

public class ListSolution {
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public boolean hasCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

    public boolean hasCycle2(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }

        if (head.next == head) {
            return true;
        }

        ListNode p = head;
        while (p.next != null) {
            ListNode q = head;
            if (p.next == q) {
                return true;
            }

            while (q != p) {
                q = q.next;
                if (p.next == q) {
                    return true;
                }
            }
            p = p.next;
        }

        return false;
    }

    public void isPalindrome2(ListNode head) {
        // 更好的解决方案，获取总长度，找到中间节点，分成两半，然后把前一变的节点给反转，然后循环判断前后两串是否一样。
    }

    public boolean isPalindrome(ListNode head) {
        int w = 0;
        ListNode m = head;
        while (m != null) {
            m = m.next;
            w++;
        }

        if (w <= 1) {
            return true;
        }

        int i, j = 0;
        if (w % 2 == 1) {
            j = w / 2 + 2;
        } else {
            j = w / 2 + 1;
        }
        i = w / 2;
        return isPalindrome(head, i, j, w);
    }

    public boolean isPalindrome(ListNode head, int i, int j, int w) {
        ListNode p = head;
        ListNode q = head;
        for (int a = 1; a < i; a++) {
            p = p.next;
        }

        for (int b = 1; b < j; b++) {
            q = q.next;
        }

        if (p.val == q.val) {
            if (i == 1 && j == w) {
                return true;
            } else {
                i--;
                j++;
            }
            return isPalindrome(head, i, j, w);
        } else {
            return false;
        }
    }

    // 使用递归来合并
    public ListNode mergeTwoListsUse(ListNode l1, ListNode l2) {
        ListNode newHead = null;
        if (l1 == null) {
            return l2;
        } else if (l2 == null) {
            return l1;
        } else {
            if (l1.val > l2.val) {
                newHead = l2;
                newHead.next = mergeTwoListsUse(l1, l2.next);
            } else {
                newHead = l1;
                newHead.next = mergeTwoListsUse(l1.next, l2);
            }
            return newHead;
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
        listSolution.createList(0);
        listSolution.createList(1);
        Boolean result = listSolution.isPalindrome(listSolution.head);
        System.out.println(result);
    }
}
