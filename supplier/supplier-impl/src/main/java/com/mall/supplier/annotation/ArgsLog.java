package com.mall.supplier.annotation;

import java.lang.annotation.*;

/**
 * @Author Zhutaoshen
 * @Date 2017/1/5 0005 6:53
 * 自定义注解，将参数调用JSON输出日志
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface ArgsLog{
    /**
     * true 输出日志
     * @return
     */
    boolean log() default true;
    String[] exclude() default {};
}