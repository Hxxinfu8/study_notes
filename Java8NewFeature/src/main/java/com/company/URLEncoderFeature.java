package com.company;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 大问题 推荐使用org.apache.catalina.util.URLEncoder
 */
public class URLEncoderFeature {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String text = "你是水啊  啊大苏打";
        String encoded = URLEncoder.encode(text);
        System.out.println(encoded);
        System.out.println(URLEncoderFeature.class.getClassLoader());
        String encodeStr = URLEncoder.encode("今天 天气~!@#$%^&*", "UTF-8");
        System.out.println(encodeStr);
        System.out.println(URLDecoder.decode(encodeStr, "UTF-8"));
    }
}
