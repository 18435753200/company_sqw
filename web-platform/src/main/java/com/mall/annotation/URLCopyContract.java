package com.mall.annotation;

import com.alibaba.dubbo.common.json.JSON;
import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.utils.Common;

import javassist.bytecode.SignatureAttribute;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zhutaoshen on 2017/1/5 0005.
 * 注解实现
 */
@Aspect
@Component
public class URLCopyContract {
    private static final Log logger = LogFactory.getLogger(URLCopyContract.class);

    private String getURIByRequestMapping(RequestMapping requestMapping){
        if(requestMapping==null) return "";
        if(requestMapping.value().length<=0) return "";
        return requestMapping.value()[0];
    }

    @After("@annotation(com.mall.annotation.URLCopy)")
    public void assembleParams(JoinPoint joinPoint){
        HttpServletRequest request = null;
        Model model = null;
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        URLCopy urlCopy = method.getAnnotation(URLCopy.class);
        String url = "";
        Object[] args = joinPoint.getArgs();
        String[] exclude = urlCopy.exclude();
        Map<String,String> map= new HashMap<String, String>();
        for (int i = 0; i < args.length; i++) {
            if(args[i]==null) continue;
            if(args[i] instanceof HttpServletRequest){
                request = (HttpServletRequest) args[i];
            }else if(args[i] instanceof  Model){
                model = (Model) args[i];
            }else if(urlCopy.requier()>0){
                if(Common.isBaseDataType(args[i].getClass())){
                    map.put(methodSignature.getParameterNames()[i],args[i].toString());
                }else{
                    try {
                        map.put(methodSignature.getParameterNames()[i],JSON.json (args[i]));
                    } catch (IOException e) {
                        map.put(methodSignature.getParameterNames()[i],args[i].toString());
                    }
                }
            }
        }
        if((urlCopy.requier()!=1)&&(request!=null)){
            Enumeration enumeration = request.getParameterNames();
            while(enumeration.hasMoreElements()){
                String paramName = enumeration.nextElement().toString();
                map.put(paramName,request.getParameter(paramName));
            }
        }
        try {
            if(urlCopy.log()) logger.info(method.getName()+":"+JSON.json(map));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if((urlCopy.mode()==2)&&(model!=null)){
            for (int i = 0; i < exclude.length; i++) {
                map.remove(exclude[i]);
            }
            if(urlCopy.uri().equals("@RequestMapping")){
                String uri = this.getURIByRequestMapping(AnnotationUtils.findAnnotation(joinPoint.getTarget().getClass(),RequestMapping.class));
                uri+=this.getURIByRequestMapping(method.getAnnotation(RequestMapping.class));
                map.put("uri",uri);
            }else{
                if(urlCopy.uri().length()>0){
                    map.put("uri",urlCopy.uri());
                }
            }
            try {
                model.addAttribute(urlCopy.target(),JSON.json(map));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
