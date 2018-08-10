package com.mall.tag;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import com.mall.retailer.order.po.Dictionarys;

public class OrderStatusTag extends TagSupport{
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
			if(type.equals("CG"))
			{
				if(value.equals("1"))
				{
					value="<img src=\"../commons/images/flag_red.png\" style=\"width:25px; height:25px;\">";
				}
				else if(value.equals("10"))
				{
					value="<img src=\"../commons/images/flag_yellow.png\" style=\"width:25px; height:25px;\">";
				}else if(value.equals("15"))
				{
					value="已到齐";
				}else if(value.equals("50"))
				{
					value="已完成";
				}
			}
			out.print(value);
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
