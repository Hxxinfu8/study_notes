package com.hx.mode.proxy;

public class Autistic implements MessageSender{
    @Override
    public void sendMsg() {
        System.out.println("我自闭了");
    }
}
