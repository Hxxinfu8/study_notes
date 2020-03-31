package com.company.single;

/**
 * 单例模式 简单懒汉式 单线程环境下
 * @author Upoint0002
 */
public class SingleOne {
    private static SingleOne singleOne = null;

    private SingleOne() {

    }

    public static SingleOne getInstance() {
        if (singleOne == null) {
            singleOne = new SingleOne();
        }
        return singleOne;
    }

    public static void main(String[] args) {
        SingleOne singleOne = SingleOne.getInstance();
        SingleOne singleTwo = SingleOne.getInstance();
        System.out.println(singleOne.equals(singleTwo));
    }
}
