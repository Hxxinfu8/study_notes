package com.company;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodAnno {
    String method() default "method";
    FiledAnno filedAnno() default @FiledAnno;
}
