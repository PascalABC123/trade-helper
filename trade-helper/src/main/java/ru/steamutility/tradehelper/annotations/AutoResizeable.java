package ru.steamutility.tradehelper.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)

@Target({ElementType.LOCAL_VARIABLE, ElementType.FIELD})
public @interface AutoResizeable {
    double width() default 1;
}
