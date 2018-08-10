package com.mall.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

public class ApplyTag extends TagSupport{
	private static final long serialVersionUID = 1L;
	private String value;

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
		try {
			JspWriter out = this.pageContext.getOut();
			if(value.equals("1"))
			{
				out.print("领用出库");
			}else if(value.equals("2"))
			{
				out.print("销售出库");
			}else if(value.equals("3"))
			{
				out.print("销售出库");
			}else if(value.equals("4")){
				out.print("特殊出库");
			}else if(value.equals("9"))
			{
				out.print("销售出库");
			}else if(value.equals("21"))
			{
				out.print("销售出库");
			}else if(value.equals("22"))
			{
				out.print("销售出库");
			}else if(value.equals("23"))
			{
				out.print("销售出库");
			}else if(value.equals("24"))
			{
				out.print("销售出库");
			}else if(value.equals("25")){
				out.print("销售出库");
			}else if(value.equals("26")){
				out.print("销售出库");
			}else if(value.equals("27")){
				out.print("销售出库");
			}else if(value.equals("28")){
				out.print("销售出库");
			}else if(value.equals("29")){
				out.print("销售出库");
			}else if(value.equals("30")){
				out.print("销售出库");
			}else if(value.equals("31")){
				out.print("销售出库");
			}else if(value.equals("32")){
				out.print("销售出库");
			}else if(value.equals("38")){
				out.print("销售出库");
			}else if(value.equals("39")){
				out.print("销售出库");
			}else if(value.equals("40")){
				out.print("销售出库");
			}else if(value.equals("41")){
				out.print("销售出库");
			}else if(value.equals("42")){
				out.print("销售出库");
			}else if(value.equals("43")){
				out.print("销售出库");
			}else if(value.equals("45")){
				out.print("销售出库");
			}else if(value.equals("46")){
				out.print("销售出库");
			}else if(value.equals("47")){
				out.print("销售出库");
			}else{
				out.print(" ");
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
