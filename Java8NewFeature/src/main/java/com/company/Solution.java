package com.company;

import java.util.*;

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
     * @param s
     * @return
     */
    public int calculate(String s) {
        int x = 1, y = 0;
        for (int i = 0; i < s.length(); i ++) {
            if (String.valueOf(s.charAt(i)).equals("A")) {
                x = 2 * x + y;
            }
            if (String.valueOf(s.charAt(i)).equals("B")) {
                y = 2 * y + x;
            }
        }
        return x + y;
    }

    public static void main(String[] args) {
        System.out.println(commonChars(new String[]{"bella", "label", "roller"}));
        System.out.println(partitionLabels("abacsddfejjjh"));
    }
}
