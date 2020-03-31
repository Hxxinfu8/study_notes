package com.company.single;

/**
 * 单例 静态内部类懒汉式
 * @author Upoint0002
 */
public class SingleThree {
    private SingleThree () {

    }

    private static class LazyHolder {
        private final static SingleThree INSTANCE = new SingleThree();
    }

    private static SingleThree getInstance() {
        return LazyHolder.INSTANCE;
    }
}
