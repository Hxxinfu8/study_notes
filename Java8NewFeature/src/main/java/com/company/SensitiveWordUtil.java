package com.company;

import java.util.*;

public class SensitiveWordUtil {
    public static Map sensitiveWordMap;

    public static void init(Set<String> sensitiveWordSet) {
        sensitiveWordMap = new HashMap(sensitiveWordSet.size());
        Iterator<String> iterable = sensitiveWordSet.iterator();
        String key;
        Map nowMap;
        Map<String, String> newWordMap;
        while (iterable.hasNext()) {
            key = iterable.next();
            nowMap = sensitiveWordMap;
            for(int i = 0; i < key.length(); i ++) {
                char charKey = key.charAt(i);
                if (nowMap.containsKey(charKey)) {
                    nowMap = (Map) nowMap.get(charKey);
                } else {
                    newWordMap = new HashMap<>();
                    newWordMap.put("isEnd", "0");
                    nowMap.put(charKey, newWordMap);
                    nowMap = newWordMap;
                }

                if (i == key.length() - 1) {
                    nowMap.put("isEnd", "1");
                }
            }
        }
    }

    public static void main(String[] args) {
        Set<String> sensitiveWordSet = new HashSet<>();
        sensitiveWordSet.add("台湾");
        sensitiveWordSet.add("草泥马");
        sensitiveWordSet.add("煞笔");

        sensitiveWordSet.add("草人");

        init(sensitiveWordSet);
        sensitiveWordMap.size();
    }
}
