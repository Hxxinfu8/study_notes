package com.company;

import java.util.*;

/**
 * Leetcode
 * @author hx
 */
public class Solution {
    /**
     * 查找常用字符
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

    public static void main(String[] args) {
        commonChars(new String[]{"bella","label","roller"});
    }
}
