package com.company;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented()
public @interface FiledAnno {
    String name() default "";
}
