package com.mall.supplier.annotation;

import com.alibaba.dubbo.common.json.JSON;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by Zhutaoshen on 2017/1/5 0005.
 * 注解实现
 */
@Aspect
@Component
public class ArgsLogContract {

    private static final Logger logger = Logger.getLogger(ArgsLogContract.class);

    @Before("@annotation(com.mall.supplier.annotation.ArgsLog)")
    public void assembleParams(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //Method method = methodSignature.getMethod();
        //URLCopy urlCopy = method.getAnnotation(URLCopy.class);
        Object[] args = joinPoint.getArgs();
        //String[] exclude = urlCopy.exclude();
        String outInfo = joinPoint.getTarget().getClass().getName()+":"+methodSignature.getName()+":";
        for (int i = 0; i < args.length; i++) {
            if(args[i]==null){
                outInfo += Integer.toString(i+1)+":"+methodSignature.getParameterNames()[i]+" IS NULL! ";
            }else{
                try {
                    outInfo += Integer.toString(i+1)+":"+methodSignature.getParameterNames()[i]+" "+JSON.json(args[i]);
                } catch (IOException e) {
                    outInfo += Integer.toString(i+1)+":"+methodSignature.getParameterNames()[i]+" "+args[i].toString();
                }
            }
        }
        logger.info(outInfo);
    }
}
