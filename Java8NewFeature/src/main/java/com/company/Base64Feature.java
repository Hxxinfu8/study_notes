package com.company;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Base64处理
 */
public class Base64Feature {
    public static void main(String[] args) {
        String base = "Hello World";
        String encoded = Base64.getEncoder().encodeToString(base.getBytes(StandardCharsets.UTF_8));
        System.out.println(encoded);

        String decoded = new String(Base64.getDecoder().decode(encoded), StandardCharsets.UTF_8);
        System.out.println(decoded);
    }
}
