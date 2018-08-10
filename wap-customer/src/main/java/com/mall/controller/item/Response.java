package com.mall.controller.item;

import java.io.Serializable;



public class Response implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Result result=new Result();
	private Object data;
	
	
	public Response() {
		super();
	}

	
	public Response(Result result, Object data) {
		super();
		this.result = result;
		this.data = data;
	}
	
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	/**
     * 对服务名进行标准化处理：如book.upload转换为book-upload，
     *
     * @param method
     * @return
     */
    protected String transform(String method) {
        if(method != null){
            method = method.replace(".", "-");
            return method;
        }else{
            return "LACK_METHOD";
        }
    }
    
	public class Result implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String result = "0";
		private String message;
		//默认值
		private String errorCode="999";
		
		public String getResult() {
			return result;
		}
		public void setResult(String result) {
			this.result = result;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getErrorCode() {
			return errorCode;
		}
		public void setErrorCode(String errorCode) {
			this.errorCode = errorCode;
		}
		
	
	}
}
