package com.company;

public class OperandStackTest {
    public static void add(){
        //第1类问题：
        int i1 = 10;
        i1++;
        System.out.println(i1);//11

        int i2 = 10;
        ++i2;
        System.out.println(i2);//11

        //第2类问题：
        int i3 = 10;
        int i4 = i3++;
        System.out.println(i3);//11
        System.out.println(i4);//10

        int i5 = 10;
        int i6 = ++i5;
        System.out.println(i5);//11
        System.out.println(i6);//11

        //第3类问题：
        int i7 = 10;
        i7 = i7++;
        System.out.println(i7);//10

        int i8 = 10;
        i8 = ++i8;
        System.out.println(i8);//11

        //第4类问题：
        int i9 = 10;
        int i10 = i9++ + ++i9;//10+12
        System.out.println(i9);//12
        System.out.println(i10);//22
    }

    public static void main(String[] args) {
        add();
    }
}
