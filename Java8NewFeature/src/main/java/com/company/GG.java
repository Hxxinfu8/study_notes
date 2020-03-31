package com.company;

/**
 * 静态代码块在构造方法之前执行
 */
public class GG {

    static {
        System.out.println("father static");
    }

    public GG () {
        System.out.println("father builder");
    }

    public static void main(String[] args) {
        System.out.println(Father.str);
        GG.class.getDeclaredMethods();
    }
}
