package com.company;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.PriorityQueue;
import java.util.Queue;

@Data
@AllArgsConstructor
public class Base {
    private Integer age;
    private String name;
    private static Integer num = 1;
    static {
        System.out.println("Base" + num);
    }

    public static Integer getNum() {
        return num;
    }

    public static void setNum(Integer num) {
        Base.num = num;
    }

    public static void main(String[] args) {
        Queue<String> queue = new PriorityQueue<>();
//        System.out.println(Base.num);
//        new Base();
//        Class base = Base.class;
//        System.out.println(base.getName());
        try {
            Base base = new Base(11, "22");
            Class base1 = Base.class;
            Field[] fields = base1.getDeclaredFields();
            PropertyDescriptor descriptor = new PropertyDescriptor("name", Base.class);
            descriptor.getWriteMethod().invoke(base, "1212");
            for (Field field : fields) {
                String key = field.getName();
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(key, Base.class);
                Method readMethod = propertyDescriptor.getReadMethod();
                Object value = readMethod.invoke(base);
                System.out.println(key + ":" + value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
