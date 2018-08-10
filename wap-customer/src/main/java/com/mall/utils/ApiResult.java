package com.mall.utils;

/**
 * ApiResult类
 */
public class ApiResult
{
  private int code = -1;
  private Object data;
  private String message;
  private Exception exp = null;
  
  public Exception getExp()
  {
    return this.exp;
  }
  
  public void setExp(Exception exp)
  {
    this.exp = exp;
  }
  
  public int getCode()
  {
    return this.code;
  }
  
  public void setCode(int code)
  {
    this.code = code;
  }
  
  public Object getData()
  {
    return this.data;
  }
  
  public void setData(Object data)
  {
    this.data = data;
  }
  
  public String getMessage()
  {
    return this.message;
  }
  
  public void setMessage(String message)
  {
    this.message = message;
  }
}
