package com.mall.utils;  
      
import java.io.File;
import java.io.IOException;  
import java.io.Writer;  
import java.util.Locale;  
import java.util.Map;  

import freemarker.template.Configuration;  
import freemarker.template.DefaultObjectWrapper;  
import freemarker.template.Template;  
import freemarker.template.TemplateException;  
      
public class FreeMarkerUtil {  
      
        private static  Configuration config = new Configuration();
          
        /** 
         * @param templateName 模板名字 
         * @param root 模板根 用于在模板内输出结果集 
         * @param out 输出对象 具体输出到哪里 
         */  
        public static void processTemplate(String templateName, Map<?,?> root, Writer out) throws Exception{  
            try{  
                //获得模板  
                Template template=config.getTemplate(templateName,"utf-8");  
                //生成文件（这里是我们是生成html）  
                template.process(root, out);
                out.flush();     
            } catch (IOException e) {  
                e.printStackTrace();  
            } catch (TemplateException e) {  
                e.printStackTrace();  
            } finally{  
                 try {  
                    out.close();  
                    out=null;  
                } catch (IOException e) {  
                     
                }  
            }  
        }
        /** 
         * 初始化模板配置 
         * @param file 模板位置 
         * @throws IOException 
         */  
        public static void initConfig(File file) throws IOException{  
                config.setLocale(Locale.CHINA);
                config.setDefaultEncoding("utf-8");
                config.setEncoding(Locale.CHINA, "utf-8");  
                config.setDirectoryForTemplateLoading(file);
                config.setObjectWrapper(new DefaultObjectWrapper());
        }
        
}  