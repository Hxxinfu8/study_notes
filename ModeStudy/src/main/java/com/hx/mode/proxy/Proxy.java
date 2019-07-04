package com.hx.mode.proxy;

public class Proxy implements MessageSender{
    private Autistic autistic;

    public Proxy() {
        autistic = new Autistic();
    }

    @Override
    public void sendMsg() {
        autistic.sendMsg();
    }

    public static void main(String[] args) {
        Proxy proxy = new Proxy();
        proxy.sendMsg();
    }
}
