package com.company;

import java.net.URLEncoder;

/**
 * 大问题 推荐使用org.apache.catalina.util.URLEncoder
 */
public class URLEncoderFeature {
    public static void main(String[] args) {
        String text = "你是水啊  啊大苏打";
        String encoded = URLEncoder.encode(text);
        System.out.println(encoded);
        System.out.println(URLEncoderFeature.class.getClassLoader());

    }
}
