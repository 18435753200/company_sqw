package com.mall.dsearch.vo;

import java.io.Serializable;

 
 

/**
 * @author dell
 * @date 2014.3.14
 * 
 * 
 * **/
public class Suggestion implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6170779219348500536L;
	/**提示词**/
	 
	private String keyword;
	/**命中次数**/	
 
	private int  count;
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return  "提示词: "+keyword+"  命中次数:  "+count;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}
   
	 public Suggestion() {
		// TODO Auto-generated constructor stub
	}

	public Suggestion(String keyword,int count) {
		super();
		this.keyword = keyword;
		this.count = count;
	}
	
	
 
	
	

}
