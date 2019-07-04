package com.company;

import java.util.regex.Pattern;

public class PatternTest {
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile(". + (.mp4|.rmvb|.flv|.mpeg|.avi)$");
        System.out.println(pattern.matcher("123.mp4").find());
    }
}
