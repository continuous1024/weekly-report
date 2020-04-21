package com.huanyu.weekly.algorithm.daily.tree;

import java.util.*;

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

    int maxWidth = 0;
    Map<Integer, Integer> left;
    public int widthOfBinaryTree(TreeNode root) {
        left = new HashMap<>();
        dfs(root, 0, 1);
        return maxWidth;
    }

    public void dfs(TreeNode root, int level, int pos) {
        if (root == null) {
            return;
        }
        left.putIfAbsent(level, pos);
        maxWidth = Math.max(maxWidth, pos - left.get(level) + 1);
        dfs(root.left, level + 1, pos * 2);
        dfs(root.right, level + 1, pos * 2 + 1);
    }

    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return constructMaximumBinaryTree(nums, 0, nums.length - 1);
    }

    public TreeNode constructMaximumBinaryTree(int[] nums, int l, int r) {
        if (nums.length == 0) {
            return null;
        }

        if (l > r) {
            return null;
        }

        int index = -1;
        int max = -1;
        for (int i = l; i <= r; i++) {
            if (nums[i] > max) {
                index = i;
                max = nums[i];
            }
        }

        TreeNode root = new TreeNode(nums[index]);
        root.left = constructMaximumBinaryTree(nums, l, index - 1);
        root.right = constructMaximumBinaryTree(nums, index + 1, r);
        return root;
    }

    public List<List<String>> printTree(TreeNode root) {
        int depth = maxDepth(root);
        int columns = (1 << depth) - 1;
        List<List<String>> result = new ArrayList<>();
        printLevelTree(0, 0, columns, columns, root, result);
        return result;
    }

    public void printLevelTree(int level, int l,  int r, int columns, TreeNode root, List<List<String>> result) {
        if (root == null) {
            return;
        }

        if (level == result.size()) {
            List<String> row = new ArrayList<>();
            for (int i = 0; i < columns; i++) {
                row.add("");
            }
            result.add(row);
        }

        int index = (l + r) / 2;
        result.get(level).set(index, root.val + "");

        printLevelTree(level + 1, l, index, columns, root.left, result);
        printLevelTree(level + 1, index + 1, r, columns, root.right, result);
    }

    public boolean validateBinaryTreeNodesBFS(int n, int[] leftChild, int[] rightChild) {
        int[] indeg = new int[n];
        for (int i = 0; i < n; i++) {
            indeg[i] = 0;
        }

        for (int item : leftChild) {
            if (item != -1) {
                indeg[item]++;
            }
        }

        for (int value : rightChild) {
            if (value != -1) {
                indeg[value]++;
            }
        }

        int root = -1;
        for (int  i = 0; i < n; i++) {
            if (indeg[i] == 0) {
                root = i;
                break;
            }
        }

        if (root == -1) {
            return false;
        }

        LinkedList<Integer> queue = new LinkedList<>();
        Set<Integer> seen = new HashSet<>();
        seen.add(root);
        queue.offer(root);
        while(!queue.isEmpty()) {
            int node = queue.poll();
            if (leftChild[node] != -1) {
                if (seen.contains(leftChild[node])) {
                    return false;
                }

                seen.add(leftChild[node]);
                queue.offer(leftChild[node]);
            }
            if (rightChild[node] != -1) {
                if (seen.contains(rightChild[node])) {
                    return false;
                }

                seen.add(rightChild[node]);
                queue.offer(rightChild[node]);
            }
        }
        return seen.size() == n;
    }

    public boolean validateBinaryTreeNodes(int n, int[] leftChild, int[] rightChild) {
        int[] indeg = new int[n];
        for (int i = 0; i < n; i++) {
            indeg[i] = 0;
        }

        for (int item : leftChild) {
            if (item != -1) {
                indeg[item]++;
            }
        }

        for (int value : rightChild) {
            if (value != -1) {
                indeg[value]++;
            }
        }

        int root = -1;
        for (int  i = 0; i < n; i++) {
            if (indeg[i] == 0) {
                root = i;
                break;
            }
        }

        if (root == -1) {
            return false;
        }

        LinkedList<Integer> stack = new LinkedList<>();
        Set<Integer> seen = new HashSet<>();
        seen.add(root);
        stack.push(root);
        while(!stack.isEmpty()) {
            int node = stack.pop();
            if (leftChild[node] != -1) {
                if (seen.contains(leftChild[node])) {
                    return false;
                }

                seen.add(leftChild[node]);
                stack.push(leftChild[node]);
            }
            if (rightChild[node] != -1) {
                if (seen.contains(rightChild[node])) {
                    return false;
                }

                seen.add(rightChild[node]);
                stack.push(rightChild[node]);
            }
        }
        return seen.size() == n;
    }

    int max = 0;
    public int diameterOfBinaryTreeOffice(TreeNode root) {
        max = 1;
        depth(root);
        return max - 1;
    }

    public int depth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int l = depth(root.left);
        int r = depth(root.right);
        max = Math.max(l + r + 1, max);
        return Math.max(l, r) + 1;
    }


    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int rootDiameter = maxDepth(root.left) + maxDepth(root.right) + 1;
        if (rootDiameter > max) {
            max = rootDiameter;
        }

        diameterOfBinaryTree(root.left);
        diameterOfBinaryTree(root.right);
        return max - 1;
    }

    public TreeNode trimBSTOfficial(TreeNode root, int L, int R) {
        if (root == null) {
            return root;
        }

        if (root.val > R) {
            return trimBSTOfficial(root.left, L, R);
        }

        if (root.val < L) {
            return trimBSTOfficial(root.right, L, R);
        }

        root.left = trimBSTOfficial(root.left, L, R);
        root.right = trimBSTOfficial(root.right, L, R);
        return root;
    }

    public TreeNode trimBST(TreeNode root, int L, int R) {
        if (root == null) {
            return root;
        }

        if (root.val < L || root.val > R) {
            if (root.right != null) {
                TreeNode left = root.left;
                if (left != null) {
                    TreeNode t = root.right;
                    while(t.left != null) {
                        t = t.left;
                    }
                    t.left = left;
                }
                root = root.right;
            } else {
                root = root.left;
            }
            root = trimBST(root, L, R);
        } else {
            root.left = trimBST(root.left, L, R);
            root.right = trimBST(root.right, L, R);
        }
        return root;
    }

    public boolean isCousinsMap(TreeNode root, int x, int y) {
        Map<Integer, Integer> deptMap = new HashMap<>();
        Map<Integer, TreeNode> parentMap = new HashMap<>();
        dfs(root, null, deptMap, parentMap);
        return deptMap.get(x).equals(deptMap.get(y))
                && parentMap.get(x) != parentMap.get(y);
    }

    public void dfs(TreeNode root, TreeNode parent, Map<Integer, Integer> deptMap, Map<Integer, TreeNode> parentMap) {
        if (root == null) {
            return;
        }

        deptMap.put(root.val, parent == null ? 0 : deptMap.get(parent.val) + 1);
        parentMap.put(root.val, parent);
        dfs(root.left, root, deptMap, parentMap);
        dfs(root.right, root, deptMap, parentMap);
    }

    public static class CousinInfo {
        int xLevel;
        int xParent;
        int yLevel;
        int yParent;

        public boolean isCousins() {
            return xLevel == yLevel && xParent != yParent;
        }
    }

    public boolean isCousins(TreeNode root, int x, int y) {
        if (root == null) {
            return false;
        }
        CousinInfo cousinInfo = new CousinInfo();
        getLevel(1, root.left, x, y, root.val, cousinInfo);
        getLevel(1, root.right, x, y, root.val, cousinInfo);
        return cousinInfo.isCousins();
    }

    public void getLevel(int level, TreeNode root, int x, int y, int parent,CousinInfo cousinInfo) {
        if (root == null) {
            return;
        }

        if (root.val == x) {
            cousinInfo.xLevel = level;
            cousinInfo.xParent = parent;
        } else if (root.val == y) {
            cousinInfo.yLevel = level;
            cousinInfo.yParent = parent;
        }

        getLevel(level+1, root.left, x, y, root.val, cousinInfo);
        getLevel(level+1, root.right, x, y, root.val, cousinInfo);
    }

    public int findTilt(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftSum = getSum(root.left);
        int rightSum = getSum(root.right);
        int val = leftSum - rightSum;
        if (val < 0) {
            val = -val;
        }
        return val + findTilt(root.left) + findTilt(root.right);
    }

    public int getSum(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return root.val + getSum(root.left) + getSum(root.right);
    }

    public boolean isUnivalTree(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isUnivalTree(root, root.val);
    }

    public boolean isUnivalTree(TreeNode root, int val) {
        if (root == null) {
            return true;
        }

        return root.val == val
                && isUnivalTree(root.left, val)
                && isUnivalTree(root.right, val);
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
        // 通过index -inStart可以确定左子树的节点个数，从而找到可以在先序遍历中找到右子树的范围。
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
