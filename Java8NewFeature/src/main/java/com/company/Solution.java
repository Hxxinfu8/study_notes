package com.company;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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
     * 搜索字符
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
        for (int i = 0; i < R; i ++) {
            for (int j = 0; j < C; j ++) {
                int t =  i * C + j;
                result[t][0] = i;
                result[t][1] = j;
            }
        }

        Arrays.sort(result, Comparator.comparingInt(a -> dist(a, r0, c0)));
        return result;
    }

    private static int dist(int[] arr, int r2,int c2) {
        return Math.abs(arr[0] - r2) + Math.abs(arr[1] - c2);
    }

    public static int lengthOfLongestSubstring(String s) {
        int result = 0, rk = -1;
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < s.length(); i ++) {
            if (i > 0) {
                set.remove(s.charAt(i - 1));
            }

            while (rk + 1 < s.length() && !set.contains(s.charAt(rk + 1))) {
                set.add(s.charAt(rk + 1));
                rk ++;
            }

            result = Math.max(result, rk - i + 1);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(commonChars(new String[]{"bella", "label", "roller"}));
        System.out.println(partitionLabels("abacsddfejjjh"));
        System.out.println(reverseWords("a good   example"));
        int[] arr = new int[]{26, 2, 16, 16, 5, 5, 26, 2, 5, 20, 20, 5, 2, 20, 2, 2, 20, 2, 16, 20, 16, 17, 16, 2, 16, 20, 26, 16};
        System.out.println(uniqueOccurrences(arr));
        System.out.println(Arrays.deepToString(allCellsDistOrder(2, 2, 0, 0)));
        System.out.println(lengthOfLongestSubstring("asbbbbcade"));
    }
}
