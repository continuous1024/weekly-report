package com.huanyu.weekly.algorithm.daily.tree;

public class TreeSolution {
    // Definition for a binary tree node.
    public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
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

        if (!(left != null && right != null && left.val == right.val)) {
            return false;
        }

        return isSymmetric(left.left, right.right) && isSymmetric(left.right, right.left);
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
