package com.company;

import lombok.Data;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@ClassAnno(table = "class")
@Data
public class AnnoClass {
    @FiledAnno(name = "hx")
    private String name = "zz";

    @MethodAnno(filedAnno = @FiledAnno(name = "mu"), method="test")
    public String test(String name) {
        return name;
    }


    public static void main(String[] args) throws Exception{
        Class<AnnoClass> clazz = AnnoClass.class;
        AnnoClass annoClass = clazz.newInstance();
        for(Field field : clazz.getDeclaredFields()) {
            String key = field.getName();
            Annotation[] annotations = field.getAnnotations();
            FiledAnno filedAnno = (FiledAnno)annotations[0];
            System.out.println(key);
            PropertyDescriptor descriptor = new PropertyDescriptor(key, clazz);
            System.out.println(descriptor.getReadMethod().invoke(annoClass));
            System.out.println(filedAnno.name());
        }

        Annotation annotation = clazz.getAnnotation(ClassAnno.class);
        System.out.println(((ClassAnno) annotation).table());


        for (Method method : clazz.getMethods()) {
            if ("test".equals(method.getName())) {
                MethodAnno methodAnno = method.getDeclaredAnnotation(MethodAnno.class);
                System.out.println(method.getName());
                System.out.println(methodAnno.filedAnno().name());
                System.out.println(methodAnno.method());
                Parameter[] parameters = method.getParameters();
                System.out.println(parameters[0].getName());
                System.out.println(method.invoke(annoClass, "123"));
            }
        }
    }
}
