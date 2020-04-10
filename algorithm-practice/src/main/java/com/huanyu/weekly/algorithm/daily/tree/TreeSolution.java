package com.huanyu.weekly.algorithm.daily.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TreeSolution {
    // Definition for singly-linked list.
    public class ListNode {
       int val;
       ListNode next;
       ListNode(int x) { val = x; }
    }
    // Definition for a binary tree node.
    public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
    }

    // 从上往下的视角
    public TreeNode invertTreeTop(TreeNode root) {
        if (root == null) return null;
        TreeNode right = root.right;
        root.right = invertTreeTop(root.left);
        root.left = invertTreeTop(right);
        return root;
    }

    // 从下往上的视角
    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        TreeNode temp = left;
        root.left = right;
        root.right = temp;
        return root;
    }

    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) {
            return null;
        }
        int val = 0;
        if (t1 != null) {
            val += t1.val;
        }

        if (t2 != null) {
            val += t2.val;
        }

        TreeNode newT = new TreeNode(val);
        newT.left = mergeTrees(t1 != null ? t1.left : null, t2 != null ? t2.left : null);
        newT.right = mergeTrees(t1 != null ? t1.right : null, t2 != null ? t2.right : null);
        return newT;
    }

    public boolean match(ListNode head, TreeNode root) {
        boolean result = false;
        if (root != null && root.val == head.val) {
            if (head.next == null) {
                return true;
            }

            result = match(head.next, root.left);
            if (!result) {
                result = match(head.next, root.right);
            }
        }

        return result;
    }

    public boolean isSubPath(ListNode head, TreeNode root) {
        boolean result = false;
        if (root.val == head.val) {
            result = match(head, root);
            if (result) {
                return true;
            }
        }

        if (root.left != null) {
            result = isSubPath(head, root.left);
            if (result) {
                return true;
            }
        }

        if (root.right != null) {
            result = isSubPath(head, root.right);
        }
        return result;
    }

    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }

        int leftMaxHeight = maxHeight(root.left);
        int rightMaxHeight = maxHeight(root.right);
        int result = leftMaxHeight - rightMaxHeight;
        return ((result >= 0 && result <= 1) || (result == -1))
                && isBalanced(root.left) && isBalanced(root.right);
    }

    public int maxHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftHeight = maxHeight(root.left) + 1;
        int rightHeight = maxHeight(root.right) + 1;
        if (leftHeight > rightHeight) {
            return leftHeight;
        }
        return rightHeight;
    }

    public int kthSmallestScan(TreeNode root, int k) {
        List<Integer> nodeList = new ArrayList<>();
        inorderTraverse(root, nodeList);
        int i = 1;
        for (Integer value : nodeList) {
            if (i == k) {
                return value;
            }
            i++;
        }
        return 0;
    }

    public void inorderTraverse(TreeNode root, List<Integer> nodeList) {
        if (root == null) return;
        inorderTraverse(root.left, nodeList);
        nodeList.add(root.val);
        inorderTraverse(root.right, nodeList);
    }

    public int kthSmallest(TreeNode root, int k) {
        TreeNode node = kthSmallestNode(root, k);
        if (node == null) {
            return 0;
        }
        return node.val;
    }

    public TreeNode kthSmallestNode(TreeNode root, int k) {
        if (root == null) {
            return null;
        }

        int leftCount = getLeftCount(root.left) + 1;
        if (k < leftCount) {
            return kthSmallestNode(root.left, k);
        } else if (k > leftCount) {
            return kthSmallestNode(root.right, k - leftCount);
        } else {
            return root;
        }
    }

    public int getLeftCount(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return getLeftCount(root.left) + getLeftCount(root.right) + 1;
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return buildTree(0, 0, inorder.length-1, preorder, inorder);
    }

    private TreeNode buildTree(int preStart, int inStart, int inEnd, int[] preorder, int[] inorder) {
        if (preStart >= inorder.length || inStart > inEnd) {
            return null;
        }

        TreeNode root = new TreeNode(preorder[preStart]);
        int index = 0;
        for (int i = inStart; i <= inEnd; i++) {
            if (inorder[i] == preorder[preStart]) {
                index = i;
                break;
            }
        }
        root.left = buildTree(preStart + 1, inStart, index - 1, preorder, inorder);
        root.right = buildTree(preStart + 1 + index - inStart, index + 1, inEnd, preorder, inorder);
        return root;
    }

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        zigzagLevelOrder(root, result, 0);
        for (int i = 0; i < result.size(); i++) {
            if (i % 2 != 0) {
                result.set(i, reverseList(result.get(i)));
            }
        }
        return result;
    }

    private List<Integer> reverseList(List<Integer> list) {
        if (list == null) {
            return null;
        }
        int len = list.size();
        for (int i = 0, j = len - 1; i < len>>1; i++, j--) {
            Integer tmp = list.get(i);
            list.set(i, list.get(j));
            list.set(j, tmp);
        }
        return list;
    }

    public void zigzagLevelOrder(TreeNode root, List<List<Integer>> result, int level) {
        if (root == null) {
            return;
        }

        if (level == result.size()) {
            result.add(new ArrayList<>());
        }

        result.get(level).add(root.val);
        zigzagLevelOrder(root.left, result, level+1);
        zigzagLevelOrder(root.right, result, level+1);
    }

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        result.addAll(inorderTraversal(root.left));
        result.add(root.val);
        result.addAll(inorderTraversal(root.right));
        return result;
    }

    public List<Integer> inorderTraversalByIteration(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        LinkedList<TreeNode> linkedStack = new LinkedList<>();
        TreeNode t = root;
        while (t != null || !linkedStack.isEmpty()) {
            while (t != null) {
                linkedStack.push(t);
                t = t.left;
            }
            if (!linkedStack.isEmpty()) {
                t = linkedStack.pop();
                result.add(t.val);
                t = t.right;
            }
        }

        return result;
    }

    public TreeNode sortedArrayToBST(int[] nums) {
        return sortedArrayToBST(nums, 0, nums.length-1);
    }

    public TreeNode sortedArrayToBST(int[] nums, int start, int end) {
        if (end < start) return null;
        int middle = start + (end - start) / 2;
        TreeNode root = new TreeNode(nums[middle]);
        root.left = sortedArrayToBST(nums, start, middle - 1);
        root.right = sortedArrayToBST(nums, middle + 1, end);
        return root;
    }

    public static void main(String[] args) {
        TreeSolution treeSolution = new TreeSolution();
        int[] nums = new int[]{-10,-3,0,5,9};
        TreeNode root = treeSolution.sortedArrayToBST(nums);
        List<Integer> result = treeSolution.inorderTraversal(root);
        System.out.println(result);
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        return levelOrder(0, root, result);
    }

    public List<List<Integer>> levelOrder(int level, TreeNode root, List<List<Integer>> result) {
        if (root != null) {
            if (level == result.size()) {
                result.add(new ArrayList<>());
            }

            result.get(level).add(root.val);
            levelOrder(level+1, root.left, result);
            levelOrder(level+1, root.right, result);
        }

        return result;
    }

    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }

        return isSymmetric(root.left, root.right);
    }

    public boolean isSymmetric(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }

        if (left == null || right == null) {
            return false;
        }

        return left.val == right.val && isSymmetric(left.left, right.right) && isSymmetric(left.right, right.left);
    }

    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, null, null);
    }

    public boolean isValidBST(TreeNode root, TreeNode min, TreeNode max) {
        if (root == null) {
            return true;
        }

        if ((min != null && root.val <= min.val)
                || (max != null && root.val >= max.val)) {
            return false;
        }

        boolean leftResult = isValidBST(root.left, min, root);
        boolean rightResult = isValidBST(root.right, root, max);
        return leftResult && rightResult;
    }

    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int maxLeft = maxDepth(root.left) + 1;
        int maxRight = maxDepth(root.right) + 1;
        if (maxLeft > maxRight) {
            return maxLeft;
        }
        return maxRight;
    }

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    };

    public Node connect(Node root) {
        if (root == null || root.left == null) {
            return root;
        }
        root.left.next = root.right;
        if (root.next != null) {
            root.right.next = root.next.left;
        }
        connect(root.left);
        connect(root.right);
        return root;
    }
}
