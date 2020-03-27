package com.huanyu.weekly.algorithm.daily.tree;

public class TreeSolution {
    // Definition for a binary tree node.
    public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
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
