package com.company.single;

/**
 * 单例模式 双重检测机制懒汉式
 * @author Upoint0002
 */
public class SingleTwo {
    private SingleTwo() {

    }

    private volatile static SingleTwo singleTwo = null;

    public static SingleTwo getInstance() {
        if (singleTwo == null) {
            synchronized (SingleTwo.class) {
                if (singleTwo == null) {
                    singleTwo = new SingleTwo();
                }
            }
        }
        return singleTwo;
    }
}
