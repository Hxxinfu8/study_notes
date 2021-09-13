package com.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.openjdk.jol.info.ClassLayout;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.UUID;

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
                if ("name".equals(key)) {
                    System.out.println(11);
                    break;
                }
            }
            System.out.println(UUID.randomUUID().toString());
            BigDecimal bigDecimal = new BigDecimal(111);
            BigDecimal fenmu = new BigDecimal(3123);
            fenmu = fenmu.add(new BigDecimal(111))
                    .add(new BigDecimal(222));
            System.out.println(fenmu.toString());

            Object o = new Object();
            System.out.println(ClassLayout.parseInstance(base).toPrintable());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
