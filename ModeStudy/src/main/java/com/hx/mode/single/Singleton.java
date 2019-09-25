package com.hx.mode.single;

import java.io.Serializable;

public class Singleton implements Serializable {
    private static volatile Singleton singleton;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
       this.content = content;
    }

    private Singleton() {}

    public static Singleton getInstance() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
