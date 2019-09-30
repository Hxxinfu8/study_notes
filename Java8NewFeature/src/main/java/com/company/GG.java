package com.company;

/**
 * 静态代码块在构造方法之前执行
 */
public class GG {

    public static void main(String[] args) {
        System.out.println(Father.str);
        GG.class.getDeclaredMethods();
    }
}
