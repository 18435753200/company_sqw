package com.mall.annotation;

import java.lang.annotation.*;

/**
 * @Author Zhutaoshen
 * @Date 2017/1/5 0005 6:53
 * 自定义注解，将request的params存储到一个固定param中，方便下一个view调用
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface URLCopy{
    String target() default "urlcopy";
    /**
     * 需求类型
     * 0 仅params
     * 1 仅args
     * 2 params+args
     * @return
     */
    int requier() default 2;
    /**
     * true 输出日志
     * @return
     */
    boolean log() default true;
    /**
     * 运行模式
     * 1 日志模式，不组合参数拷贝
     * 2 组合参数拷贝输出到model
     * @return
     */
    int mode() default 1;
    String[] exclude() default {};

    /**
     * URI路径，空串无,@开头为注解
     * @return
     */
    String uri() default "@RequestMapping";
}