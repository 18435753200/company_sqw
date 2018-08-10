package com.mall.InterceptorUtils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

import com.mall.utils.ParserOptXml;


public class InitSystemServlet extends HttpServlet
{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8298371945476642300L;
	
	/**
	 * Log4J Logger for this class
	 */
	protected transient final Logger log  =  Logger.getLogger(this.getClass() );
	
	protected String realpath = null;	
	
	/**
	 * Constructor of the object.
	 */
	public InitSystemServlet(){
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occure
	 */
	public void init() throws ServletException{
		
		try {
			//加载optlog.xml 用该方法取class下文件路径与平台无关
			ClassPathResource resource = new ClassPathResource("optlog.xml");
			String xmlPath = resource.getFile().getPath();
			log.info("=============加载optlog.xml 文件路经======="+xmlPath);
			ParserOptXml.parserXml(xmlPath); 
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			//System.out.println("未发现文件");
			log.info("=============加载optlog.xml 未发现文件=======");
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{			
			init();	
		}catch(Exception e){}
	}

	protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		doGet(arg0,arg1);
	}
}
