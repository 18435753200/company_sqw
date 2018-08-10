package com.mall.search.server;

import com.mall.search.cache.JedisCateAndAttrByOne;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;


 
public class Startup {
	private static final Logger LOGGER = Logger.getLogger(Startup.class);
    private Startup() {
		super();
		// TODO Auto-generated constructor stub
	}


	public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext
        		(new String[] {"applicationContext.xml"});
        context.start();
        LOGGER.info("------------启动成功-------------");
		JedisCateAndAttrByOne.init(context);
		synchronized (Startup.class)
		{
			while (true) 
				try
				{
					Startup.class.wait();
				}
				catch (Throwable e) { 
					e.printStackTrace();
				}
		}

    }
	}