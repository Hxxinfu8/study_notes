package com.company;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Leetcode
 *
 * @author hx
 */
public class Solution {
    /**
     * 查找常用字符
     *
     * @param A
     * @return
     */
    public static List<String> commonChars(String[] A) {
        List<String> result = new ArrayList<>();

        Map<Character, Integer> map = new HashMap<>();
        for (char s : A[0].toCharArray()) {
            map.put(s, map.getOrDefault(s, 0) + 1);
        }

        for (int i = 1; i < A.length; i++) {
            Iterator<Character> iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                Character c = iterator.next();
                if (A[i].indexOf(c) == -1) {
                    iterator.remove();
                } else {
                    int num = 0;
                    for (char s : A[i].toCharArray()) {
                        if (s == c) {
                            num++;
                        }
                    }
                    map.put(c, Math.min(map.get(c), num));
                }
            }
        }

        for (char c : map.keySet()) {
            for (int i = 0; i < map.get(c); i++) {
                result.add(String.valueOf(c));
            }
        }
        return result;
    }

    /**
     * 划分字母区间
     *
     * @param S
     * @return
     */
    public static List<Integer> partitionLabels(String S) {
        List<Integer> result = new ArrayList<>();
        int[] a = new int[26];

        for (int i = 0; i < S.length(); i++) {
            a[S.charAt(i) - 'a'] = i;
        }
        int start = 0, end = 0;
        for (int i = 0; i < S.length(); i++) {
            end = Math.max(end, a[S.charAt(i) - 'a']);
            if (i == end) {
                result.add(end - start + 1);
                start = end + 1;
            }
        }
        return result;
    }

    /**
     * 计算器
     *
     * @param s
     * @return
     */
    public int calculate(String s) {
        int x = 1, y = 0;
        for (int i = 0; i < s.length(); i++) {
            if (String.valueOf(s.charAt(i)).equals("A")) {
                x = 2 * x + y;
            }
            if (String.valueOf(s.charAt(i)).equals("B")) {
                y = 2 * y + x;
            }
        }
        return x + y;
    }

    public static String reverseWords(String s) {
        if (s == null) {
            return "";
        }
        String a = s.trim();
        String[] strArr = a.split(" ");
        String result = "";
        for (int i = strArr.length - 1; i >= 0; i--) {
            if (!"".equals(strArr[i])) {
                result = result + strArr[i].trim() + " ";
            }
        }
        return result.trim();
    }

    public char firstUniqChar(String s) {
        Map<Character, Integer> map = new HashMap<>();
        char[] cArr = s.toCharArray();
        for (char c : cArr) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        for (char c : cArr) {
            if (map.get(c) == 1) {
                return c;
            }
        }

        return " ".charAt(0);
    }

    /**
     * 独一无二的出现次数
     *
     * @param arr
     * @return
     */
    public static boolean uniqueOccurrences(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int a : arr) {
            map.put(a, map.getOrDefault(a, 0) + 1);
        }
        Set<Integer> set = new HashSet<>(map.values());
        return map.values().size() == set.size();
    }

    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> set1 = new HashSet<>();
        Set<Integer> set2 = new HashSet<>();
        for (int num1 : nums1) {
            set1.add(num1);
        }
        for (int num2 : nums2) {
            set2.add(num2);
        }

        Set<Integer> result = new HashSet<>();
        for (Integer a : set1) {
            if (set2.contains(a)) {
                result.add(a);
            }
        }
        return result.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * 距离顺序排列矩阵单元格
     *
     * @param R
     * @param C
     * @param r0
     * @param c0
     * @return
     */
    public static int[][] allCellsDistOrder(int R, int C, int r0, int c0) {
        int[][] result = new int[R * C][2];
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                int t = i * C + j;
                result[t][0] = i;
                result[t][1] = j;
            }
        }

        Arrays.sort(result, Comparator.comparingInt(a -> dist(a, r0, c0)));
        return result;
    }

    private static int dist(int[] arr, int r2, int c2) {
        return Math.abs(arr[0] - r2) + Math.abs(arr[1] - c2);
    }

    /**
     * 定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring(String s) {
        int result = 0, rk = -1;
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            if (i > 0) {
                set.remove(s.charAt(i - 1));
            }

            while (rk + 1 < s.length() && !set.contains(s.charAt(rk + 1))) {
                set.add(s.charAt(rk + 1));
                rk++;
            }

            result = Math.max(result, rk - i + 1);
        }
        return result;
    }

    /**
     * 给你一个字符串 s ，请你根据下面的算法重新构造字符串：
     * <p>
     * 从 s 中选出 最小 的字符，将它 接在 结果字符串的后面。
     * 从 s 剩余字符中选出 最小 的字符，且该字符比上一个添加的字符大，将它 接在 结果字符串后面。
     * 重复步骤 2 ，直到你没法从 s 中选择字符。
     * 从 s 中选出 最大 的字符，将它 接在 结果字符串的后面。
     * 从 s 剩余字符中选出 最大 的字符，且该字符比上一个添加的字符小，将它 接在 结果字符串后面。
     * 重复步骤 5 ，直到你没法从 s 中选择字符。
     * 重复步骤 1 到 6 ，直到 s 中所有字符都已经被选过。
     *
     * @param s
     * @return
     */
    public static String sortString(String s) {
        StringBuilder result = new StringBuilder();
        int[] arr = new int[26];
        for (char c : s.toCharArray()) {
            arr[c - 'a']++;
        }

        while (result.length() < s.length()) {
            for (int i = 0; i < 26; i++) {
                if (arr[i] > 0) {
                    result.append((char) (i + 'a'));
                    arr[i]--;
                }
            }

            for (int i = 25; i >= 0; i--) {
                if (arr[i] > 0) {
                    result.append((char) (i + 'a'));
                    arr[i]--;
                }
            }
        }

        return result.toString();
    }

    /**
     * 给定一个无序的数组，找出数组在排序之后，相邻元素之间最大的差值。
     * <p>
     * 如果数组元素个数小于 2，则返回 0。
     *
     * @param nums
     * @return
     */
    public static int maximumGap(int[] nums) {
        if (nums.length < 2) {
            return 0;
        }

        List<Integer> list = Arrays.stream(nums).boxed().sorted(Integer::compareTo).collect(Collectors.toList());
        int[] dis = new int[nums.length - 1];
        for (int i = 0; i < list.size() - 1; i++) {
            dis[i] = list.get(i + 1) - list.get(i);
        }

        return Arrays.stream(dis).max().getAsInt();
    }

    /**
     * 有一个二维矩阵 A 其中每个元素的值为 0 或 1 。
     * <p>
     * 移动是指选择任一行或列，并转换该行或列中的每一个值：将所有 0 都更改为 1，将所有 1 都更改为 0。
     * <p>
     * 在做出任意次数的移动后，将该矩阵的每一行都按照二进制数来解释，矩阵的得分就是这些数字的总和。
     * <p>
     * 返回尽可能高的分数。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/score-after-flipping-matrix
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param A
     * @return
     */
    public static int matrixScore(int[][] A) {
        int m = A.length;
        int n = A[0].length;
        // 默认第一列都变成1
        int result = m * (1 << (n - 1));
        for (int i = 1; i < n; i++) {
            // 每列为1的个数
            int a = 0;
            for (int j = 0; j < m; j++) {
                if (A[j][0] == 1) {
                    a += A[j][i];
                } else {
                    // 第一列不是1就翻转
                    a += (1 - A[j][i]);
                }
            }
            int k = Math.max(a, m - a);
            result += k * (1 << (n - i - 1));
        }
        return result;
    }

    public static int reverse(int x) {
        int result = 0;
        while (x != 0) {
            int p = x % 10;
            x /= 10;
            if (result > 214748364 || result < -214748364) {
                return 0;
            }
            result = result * 10 + p;
        }
        return result;
    }

    /**
     * 给定一个整数数组 prices，其中第 i 个元素代表了第 i 天的股票价格 ；非负整数 fee 代表了交易股票的手续费用。
     * <p>
     * 你可以无限次地完成交易，但是你每笔交易都需要付手续费。如果你已经购买了一个股票，在卖出它之前你就不能再继续购买股票了。
     * <p>
     * 返回获得利润的最大值。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * 一般的动态规划题目思路三步走：
     * <p>
     * 定义状态转移方程
     * 给定转移方程初始值
     * 写代码递推实现转移方程
     *
     * @param prices
     * @param fee
     * @return
     */
    public static int maxProfit(int[] prices, int fee) {
        int n = prices.length;
        int[] d = new int[2];
        d[0] = 0;
        d[1] = -prices[0];
        for (int i = 0; i < n; i++) {
            int temp = d[0];
            d[0] = Math.max(temp, d[1] + prices[i] - fee);
            d[1] = Math.max(d[1], temp - prices[i]);
        }
        return d[0];
    }

    public static char findTheDifference(String s, String t) {
        int ret = 0;
        for (int i = 0; i < s.length(); ++i) {
            ret ^= s.charAt(i);
        }
        for (int i = 0; i < t.length(); ++i) {
            ret ^= t.charAt(i);
        }
        return (char) ret;
    }

    public static boolean isPalindrome(int x) {
        if (x < 0 || (x != 0 && x % 10 == 0)) {
            return false;
        }

        int reverseNumber = 0;
        while (x > reverseNumber) {
            reverseNumber  = reverseNumber * 10 +  x % 10;
            x /= 10;
        }

        return x == reverseNumber || x == reverseNumber / 10;
    }

    /**
     * 斐波拉契数
     * 动态规划
     * @param n
     * @return
     */
    public static int fib(int n) {
        if (n <= 1) {
            return n;
        }

        int  p =0, q = 0, r = 1;
        for (int i = 2; i <= n; i ++) {
            p = q;
            q = r;
            r = p + q;
        }
        return r;
    }

    public static int maxArea(int[] height) {
        int l = 0, r = height.length - 1;
        int area = 0;
        while (l < r) {
            int ans = Math.min(height[l], height[r]) * (r - l);
            area = Math.max(area, ans);
            if (height[l] <= height[r]) {
              l ++;
            } else {
                r --;
            }
        }

        return area;
    }

    /**
     * 雪糕的最大数量
     * @param costs
     * @param coins
     * @return
     */
    public int maxIceCream(int[] costs, int coins) {
        int count = 0;
        Arrays.sort(costs);
        for (int i  = 0; i < costs.length; i ++) {
            int cost = costs[i];
            if (cost <= coins) {
                coins -= cost;
                count ++;
            } else {
                break;
            }
        }
        return count;
    }

    public static boolean containsDuplicate(int[] nums) {
        return Arrays.stream(nums).distinct().count() != nums.length;
    }

    public static boolean containsDuplicate1(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j ++) {
                if (nums[i] == nums[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(commonChars(new String[]{"bella", "label", "roller"}));
        System.out.println(partitionLabels("abacsddfejjjh"));
        System.out.println(reverseWords("a good   example"));
        int[] arr = new int[]{26, 2, 16, 16, 5, 5, 26, 2, 5, 20, 20, 5, 2, 20, 2, 2, 20, 2, 16, 20, 16, 17, 16, 2, 16, 20, 26, 16};
        System.out.println(uniqueOccurrences(arr));
        System.out.println(Arrays.deepToString(allCellsDistOrder(2, 2, 0, 0)));
        System.out.println(lengthOfLongestSubstring("asbbbbcade"));
        System.out.println(sortString("aaaabbbbcccc"));
        System.out.println(reverse(123));
        String sql = "DELETE FROM USER WHERE name='nn'";
        int whereIndex = sql.indexOf(" WHERE");
        System.out.println(sql.substring(whereIndex));
        System.out.println(sql.substring(sql.indexOf("FROM") + 4, whereIndex));
        System.out.println("SELECT * FROM" + sql.substring(sql.indexOf("FROM") + 4));
        System.out.println(maxProfit(new int[]{1, 2, 9, 4, 8}, 2));
        System.out.println(findTheDifference("abcd", "abcde"));
        System.out.println(fib(2));
        int[] nums = new int[] {1, 2, 3, 4, 5, 1, 2, 31, 23, 12,2,13,3,4};
        long start = System.currentTimeMillis();
        System.out.println(containsDuplicate(nums));
        long middle = System.currentTimeMillis();
        System.out.println(middle - start);
        containsDuplicate1(nums);
        System.out.println(System.currentTimeMillis() - middle);
        List<Task> list = new ArrayList<>();
        Task base = new Task(Status.CLOSED, 1);
        Task base1 = new Task(Status.CLOSED, 21);
        list.add(base);
        list.add(base1);
        list = list.stream().peek(item -> item.setPoints(1231)).collect(Collectors.toList());
        System.out.println(list.toString());
    }
}
