package com.hx.mode.single;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CountDownLatch;

public class SingletonTest {
    private static CountDownLatch latch = new CountDownLatch(1);
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {

        Runnable runnable = () -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Singleton singleton = Singleton.getInstance();
            System.out.println(Thread.currentThread().getName() + ": " + singleton.hashCode());
        };

        Thread threadA = new Thread(runnable, "A");
        Thread threadB = new Thread(runnable, "B");
        latch.countDown();
        threadA.start();
        threadB.start();



        Singleton a = Singleton.getInstance();
        Singleton b = Singleton.getInstance();
        System.out.println(a);
        System.out.println(b);
        // 通过反射机制调用私有构造器
        Constructor<Singleton> constructor = Singleton.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Singleton c = constructor.newInstance();
        System.out.println(c);
        System.out.println(a == b);
        System.out.println(a == c);


        // 实现序列化后，readObject()会创建一个新实例
        a.setContent("单例序列化");
        System.out.println(a.getContent());
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Singleton.obj"))) {
            oos.writeObject(a);
            oos.flush();

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Singleton.obj"));
            Singleton singleton = (Singleton) ois.readObject();
            ois.close();
            System.out.println(singleton.getContent());
            System.out.println(a == singleton);
        } catch (Exception e) {
            e.printStackTrace();
        }


        EnumSingleton enumSingletonA = EnumSingleton.ENUM_SINGLETON;
        EnumSingleton enumSingletonB = EnumSingleton.ENUM_SINGLETON;
        System.out.println(enumSingletonA);
        System.out.println(enumSingletonB);
        System.out.println(enumSingletonA == enumSingletonB);
        Constructor<EnumSingleton> enumSingletonConstructor = EnumSingleton.class.getDeclaredConstructor();
        enumSingletonConstructor.setAccessible(true);
        EnumSingleton enumSingletonC = enumSingletonConstructor.newInstance();
        System.out.println(enumSingletonC);
        System.out.println(enumSingletonA == enumSingletonC);
    }
}
