/**   
* FileName :ParserOptXml.java
* @Author : zhanglk
* @version :1.0  
* Date     :2015-3-18
*/
package com.mall.utils;

import java.io.File;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/** 
 * Title:解析操作日志功能xml
 * Description:解析操作日志功能xml
 * @Author :zhanglk
 * @Date   :2015-3-18
 * @Version:1.0
 */
public class ParserOptXml {
	/**
	 * Log4J Logger for this class
	 */
	private static Logger log  =  Logger.getLogger(ParserOptXml.class.getClass() );
	
	public static void parserXml(String fileName) {   
		File inputXml=new File(fileName);   
		SAXReader saxReader = new SAXReader();
		OptLogBean optLogBean;
		try {   
			Document document = saxReader.read(inputXml);   
			Element funts=document.getRootElement();  
			for(Iterator i = funts.elementIterator(); i.hasNext();){   
				Element funt = (Element)i.next();   
				optLogBean = new OptLogBean();
				for(Iterator j = funt.elementIterator(); j.hasNext();){   
					Element node=(Element) j.next();
					//设置操作链接
					if("url".equals(node.getName())){
						optLogBean.setOptUrl(node.getText());
					}else if("funtname".equals(node.getName())){//设置功能显示中文名
						optLogBean.setFuntName(node.getText());
					}else if("funttype".equals(node.getName())){//设置功能类型
						optLogBean.setFuntType(node.getText());
					}else if("islog".equals(node.getName())){//设置是否记录日志标示
						optLogBean.setIsLog(node.getText());
					}
				
					Constants.OPT_ISLOG_MAP.put(optLogBean.getOptUrl(), optLogBean);
				  }   
				}   
			} catch (DocumentException e) {   
				System.out.println(e.getMessage());   
			}   
		log.info("Constants.OPT_ISLOG_MAP共计:"+Constants.OPT_ISLOG_MAP.size()+"条。");
		log.info("dom4j parserXml success!");   
	}   
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ParserOptXml.parserXml("D:/workspace/web-platform/resources/optlog.xml");
	}

}
