package com.mall.supplier.host;

import org.springframework.context.support.ClassPathXmlApplicationContext;
 
public class Supplier {
 
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
        
        System.out.println("连接成功");
        context.start();
        synchronized (Supplier.class)
		{
			while (true) 
				try
				{
					Supplier.class.wait();
				}
				catch (Throwable e) { 
					e.printStackTrace();
				}
		}
      
    }
    
}