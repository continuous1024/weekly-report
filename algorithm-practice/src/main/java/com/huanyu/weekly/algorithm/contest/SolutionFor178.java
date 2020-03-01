package com.huanyu.weekly.algorithm.contest;

import java.util.*;

/**
 * 周赛地址: https://leetcode-cn.com/contest/weekly-contest-178/
 */
public class SolutionFor178 {
    // 有多少小于当前数字的数字
    public int[] smallerNumbersThanCurrent(int[] nums) {
        int[] result = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            int k = 0;
            for (int j = 0; j < nums.length; j++) {
                if (j != i && nums[j] < nums[i]) {
                    k++;
                }
            }
            result[i] = k;
        }
        return result;
    }

    // 通过投票对团队排名
    private static class Rank {
        private Integer[] voteList;
        private Character character;

        public Rank(Integer[] voteList, Character character) {
            this.voteList = voteList;
            this.character = character;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Rank)) return false;
            Rank rank = (Rank) o;
            return voteList.equals(rank.voteList) &&
                    character.equals(rank.character);
        }

        @Override
        public int hashCode() {
            return Objects.hash(voteList, character);
        }

        @Override
        public String toString() {
            return "Rank{" +
                    "voteList=" + Arrays.toString(voteList) +
                    ", character=" + character +
                    '}';
        }
    }

    public String rankTeams(String[] votes) {
        if (votes.length == 0) {
            return "";
        }

        if (votes.length == 1) {
            return votes[0];
        }

        int rankLength = votes[0].length();
        char[] result = new char[rankLength];
        Map<Character, Integer[]> voteMap = new HashMap<>();
        int i;
        for (String vote : votes) {
            i = 0;
            for (char c : vote.toCharArray()) {
                Integer[] voteList = voteMap.get(c);
                int k = 0;
                if (voteList == null) {
                    voteList = new Integer[rankLength];
                    for (int j = 0; j< rankLength; j++) {
                        voteList[j] = 0;
                    }
                } else {
                    if (voteList[i] != null) {
                        k = voteList[i];
                    }
                }

                voteList[i] = k+1;
                voteMap.put(c, voteList);
                i++;
            }
        }

        Map<Rank, Character> voteVerMap = new TreeMap<Rank, Character>(new Comparator<Rank>() {
            @Override
            public int compare(Rank o1, Rank o2) {
                for (int i = 0; i < o1.voteList.length; i++) {
                    if (o1.voteList[i] > o2.voteList[i]) {
                        return -1;
                    } else if (o1.voteList[i] < o2.voteList[i]){
                        return 1;
                    }
                }

                return o1.character.compareTo(o2.character);
            }
        });

        for (Character character : voteMap.keySet()) {
            voteVerMap.put(new Rank(voteMap.get(character), character), character);
        }

        int r = 0;
        for (Map.Entry<Rank, Character> entry : voteVerMap.entrySet()) {
            System.out.println(entry.getKey());
            result[r] = entry.getValue();
            r++;
        }
        return new String(result);
    }

    // 二叉树中的列表
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
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

    public static class Position {
        private Integer x;
        private Integer y;

        public Position(Integer x, Integer y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Position)) return false;
            Position position = (Position) o;
            return x.equals(position.x) &&
                    y.equals(position.y);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Position{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    // 使网格图至少有一条有效路径的最小代价
    public Set<Position> step(int x, int y, int[][] grid, boolean[][] mp) {
        Set<Position> positions = new HashSet<>();
        int row = grid.length;
        int column = grid[0].length;
        while ((x >= 0 && x < row) && (y >= 0 && y < column)) {
            if (positions.contains(new Position(x, y)) || mp[x][y]) {
                break;
            }
            mp[x][y] = true;
            positions.add(new Position(x, y));
            if (x == row - 1 && y == column - 1) {
                return positions;
            }

            switch (grid[x][y]) {
                case 1:
                    y+=1;
                    break;
                case 2:
                    y-=1;
                    break;
                case 3:
                    x+=1;
                    break;
                case 4:
                    x-=1;
                    break;
            }
        }
        return positions;
    }

    public int minCost(int[][] grid) {
        int row = grid.length;
        int column = grid[0].length;
        boolean[][] mp = new boolean[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                mp[i][j] = false;
            }
        }

        Set<Position> positions = step(0, 0, grid, mp);
        if (positions.contains(new Position(row -1,  column- 1))) {
            return 0;
        }

        int d = 0;
        while (true) {
            Set<Position> newPositions = new HashSet<>();
            for (Position position : positions) {
                int newx, newy;
                for (int i = 1; i < 5; i++) {
                    switch (i) {
                        case 1:
                            newx = position.x;
                            newy = position.y + 1;
                            break;
                        case 2:
                            newx = position.x;
                            newy = position.y - 1;
                            break;
                        case 3:
                            newx = position.x + 1;
                            newy = position.y;
                            break;
                        case 4:
                            newx = position.x - 1;
                            newy = position.y;
                            break;
                        default:
                            newx = position.x;
                            newy = position.y;
                    }

                    if (newx >= 0 && newx < row && newy >= 0 && newy < column && !mp[newx][newy]) {
                        Set<Position> newStepPositions = step(newx, newy, grid, mp);
                        if (newStepPositions.contains(new Position(row -1,  column- 1))) {
                            return d + 1;
                        }

                        newPositions.addAll(newStepPositions);
                    }
                }
            }
            positions = newPositions;
            d += 1;
        }
    }
}
