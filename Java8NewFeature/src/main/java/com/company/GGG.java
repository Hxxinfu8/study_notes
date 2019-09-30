package com.company;

/**
 * 父类静态代码块 -> 子类静态代码块 -> 父类构造函数 -> 子类构造函数
 */
public class GGG extends GG {
    public GGG () {
        System.out.println("GGG" + 1);
    }

    static {
        System.out.println("GGG" + 2);
    }

    public static void main(String[] args) {
        new GGG();
        System.out.println("qwer".substring(2));
    }
}
