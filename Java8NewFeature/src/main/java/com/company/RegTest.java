package com.company;

import java.util.regex.Pattern;

public class RegTest {
    public static void main(String[] args) {
        Integer result = 0;
        String username = "你是sb_12吗";
        Pattern pattern = Pattern.compile("[\\u0391-\\uFFE5]");
        for (int i = 0; i < username.length(); i ++) {
            Character c = username.charAt(i);
            if (pattern.matcher(c.toString()).matches()) {
                result += 2;
            } else {
                result ++;
            }
        }
        System.out.println(result);

        Pattern pa = Pattern.compile("^[\\w\\u0391-\\uFFE5]+$");
        System.out.println(pa.matcher(username).matches());
    }
}
