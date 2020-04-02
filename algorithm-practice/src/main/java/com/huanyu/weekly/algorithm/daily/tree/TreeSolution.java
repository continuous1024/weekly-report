package com.huanyu.weekly.algorithm.daily.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TreeSolution {
    // Definition for a binary tree node.
    public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
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
}
