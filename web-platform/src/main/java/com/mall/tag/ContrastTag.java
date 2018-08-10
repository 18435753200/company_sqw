package com.mall.tag;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import com.mall.retailer.order.po.Dictionarys;

public class ContrastTag extends TagSupport{

	private String type;
	
	private List<Object> objects;
	
	private String value;
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Object> getObjects() {
		return objects;
	}

	public void setObjects(List<Object> objects) {
		this.objects = objects;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		try {
			JspWriter out = this.pageContext.getOut();
			if(type.equals("JJ"))
			{
				List<Object> JJ=objects;
				for(Object object:JJ)
				{
					Dictionarys dictionarys=(Dictionarys)object;
					int len=dictionarys.getDictValue().length()-value.length();
					for(int i=0;i<len;i++)
					{
						value="0"+value;
					}
					if(dictionarys.getDictValue().equals(value))
					{
						out.print(dictionarys.getDictName());
					}
				}
			}
			if(type.equals("CY"))
			{
				List<Object> JJ=objects;
				for(Object object:JJ)
				{
					Dictionarys dictionarys=(Dictionarys)object;
					int len=dictionarys.getDictValue().length()-value.length();
					for(int i=0;i<len;i++)
					{
						value="0"+value;
					}
					if(dictionarys.getDictValue().equals(value))
					{
						out.print(dictionarys.getDictName());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SKIP_BODY;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		// TODO Auto-generated method stub
		super.setPageContext(pageContext);
	}
	
}
