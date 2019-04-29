package com.cst.xinhe.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by lifeng on 2018/7/25.
 */


@SuppressWarnings("unchecked")
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MapperClass {
    /**
     * 指定 MapperClass
     */
    Class value();
}
