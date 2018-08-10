<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%  
    String path = request.getContextPath();
	request.setAttribute("path",path);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>UNICORN-登录</title>
    
	<link href="${path}/commons/images/favicon1.ico" rel="shortcut icon"/>

    <link type="text/css" rel="stylesheet" href="${path}/commons/css/login.css"/>
    <script type="text/javascript" src="${path}/commons/js/jquery-1.8.1.min.js"></script>
    <style type="text/css">
  body{
    color: #333333;
    font-family: Arial,"宋体",Lucida,Verdana,Helvetica,sans-serif;
    font-size: 12px;
    line-height: 150%;
    background: none repeat scroll 0 0 #F2F2F2;
}
    </style>
    <script type="text/javascript">
      function valid(){
          var submit = true;
          if($("#loginname").val() == ""){
            //  $("#loginname_error").addClass("error");
             // $("#loginname_error").removeClass("hide");
              $("#loginname_error").fadeIn("slow");
             // $("#loginname").get(0).focus();
              submit = false;
          } 
          if ($("#password").val() ==""){
              //$("#loginpwd_error").addClass("error");
             // $("#loginpwd_error").removeClass("hide");
              $("#loginpwd_error").fadeIn(1000);
              //$("#password").get(0).focus();
              submit = false;
          }
         return submit;
      }
      function clearError(){
         $("#loginname_error").fadeOut("slow");
         $("#loginpwd_error").fadeOut("slow");
         $("#loginpwd_error1").fadeOut("slow");
      }
      $(function(){
    	 
    	var path=  $("#isError").val();
    	if(path!=""){
    		$("#loginpwd_error1").html(path);
    	    $("#loginpwd_error1").fadeIn(1000);
    	}
      })
    </script>
       <%
        request.getSession().invalidate();//清空session
   		Cookie[] cookies = request.getCookies();//获取cookie
   		if(cookies!=null&&cookies.length>0){
   			Cookie cookie = cookies[0];
   			cookie.setMaxAge(0);//让cookie过期
   		}
       %>
</head>
<body>
<div class="w">
    <div id="logo"><a href="index.html" clstag="passport|keycount|login|01">
    <img src="${path}/commons/images/login-logo.png" alt="<spring:message code="logo" />" width="170" height="60"/></a>
    <b style="background: url('${path}/commons/images/login-wel-<spring:message code="language" />.png') no-repeat scroll 0 0 !important;"></b></div>
    <%-- <span style="display:block; float:right; margin:-92px 50px 0 0;">  
		 <a href="?locale=zh_CN">中文</a>  &nbsp;
		 <a href="?locale=en">Enlish</a>  
	</span> 
--%>
</div>
<form id="formlogin"  action="${path}/user/login" method="post" >
 <input id="language" type="hidden" name="language" value="<spring:message code="language" />" />
    <div class=" w1" id="entry">
        <div class="mc " id="bgDiv">
            <div id="entry-bg" clstag="passport|keycount|login|02" style="width: 511px; height: 455px; 
             background: url('${path}/commons/images/login.png') no-repeat scroll 0px 0px transparent; position: absolute;">

            </div>
            <div class="form ">
                <div class="item fore1">
                    <span><spring:message code="name" /></span>
                    <div class="item-ifo">
                        <input type="text" id="loginname" name="loginname" class="text"   tabindex="1"
                               autocomplete="off"/>
                               <div class="i-name ico"></div>
                        <label id="loginname_error" onclick="clearError()" class="hide error"><b><spring:message code="login.name" /></b></label>
                    </div>
                </div>
                <div class="item fore2">
                    <span><spring:message code="password" /></span>
                    <div class="item-ifo">
                        <input type="password" id="password" name="password" onfocus="clearError()" class="text" tabindex="2" autocomplete="off"/>
                        <div class="i-pass ico"></div>
                        <label id="loginpwd_error" onclick="clearError()" class="hide error" ><spring:message code="login.password" /></label>
                        <label id="loginpwd_error1" onclick="clearError()" class="hide error" ><spring:message code="login.repassword" /></label>
                    </div>
                </div>   
                <div class="item login-btn2013">
                    <input type="submit" class="btn-img btn-entry-<spring:message code="language" />" onclick="return valid();"  id="loginsubmit" value="<spring:message code="login" />" tabindex="8" clstag="passport|keycount|login|06"/>
                </div>
            </div>

        </div><%--
        <div class="free-regist">
            <span><a href="${path}/platform/registUI" clstag="passport|keycount|login|08"><spring:message code="regist" />&gt;&gt;</a></span>
        </div>
    --%></div> 
     <input id="isError" type="hidden" value="${fn:escapeXml(message)}" />
</form>
 <!-- 底部 start -->
<%@include file="/WEB-INF/views/include/foot.jsp"%>
</body>
</html>