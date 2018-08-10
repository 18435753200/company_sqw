<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<%@include file="/WEB-INF/views/commons/base.jsp" %>
<link href="${staticFile_s}/commons/css/base.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/regLogin.css" rel="stylesheet" type="text/css">

<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<c:if test="${from == 'cus' }"> 
<script type="text/javascript">
	$(function(){
		getCode();
	});
</script>
</c:if>
<title>
<c:choose>
    <c:when test="${from == 'cus' }"><spring:message code="title_cusinfo_updatePass" /></c:when>
    <c:otherwise><spring:message code="title_find_pass" /></c:otherwise>
 </c:choose>
</title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>

<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>
<header id="header" class="head"><a href="javascript:goBack();" class="goback"><span class="icon i-goback"></span></a>
    <h2 class="h-title">
    <c:choose>
    <c:when test="${from == 'cus' }">修改密码</c:when>
    <c:otherwise>找回密码</c:otherwise>
    </c:choose>
    </h2>
</header>
<form action="<%=path %>/customer/validCode" method="post" id="formSubmit" >
<input type="hidden" name="from" id="J_forgotPassFrom" value="${from}"> 
<div class="wrap rlf-wrap">
    <div class="rlf-form">
        <div class="from-box">
        
        
          
            <c:if test="${ from == null or from =='' }">
             <div class="form-group" >
                <label for="J_forgotPassAccount">帐　号：</label>
                <div class="field">
                    <input id="J_forgotPassAccount" class="text-box" type="text" value="${username }" name="username" placeholder="请输入手机号">
                </div>
            </div>
            </c:if>
               
            
             <c:if test="${from == 'cus' }"> 
            <div class="form-group"   >
                <label for="J_forgotPassAccount">已验证手机号：${mobile}</label>
                <div class="field">
                     <input id="J_forgotPassAccount" class="text-box" type="text" style="display:none" value="${username }" name="username" placeholder="请输入用户名">
                     <input id="J_forgotPassMobile" class="text-box" type="text" style="display:none" value="${mobile}" name="mobile" placeholder="请输入手机号">  
                </div>
            </div>
            </c:if>
            <div class="form-group">
                <label for="J_forgotPassCode">验证码：</label>
                <div class="field">
                    <input id="J_forgotPassCode" class="text-box" type="text" name="msgCode" placeholder="请输入验证码">
                    <span class="code-btn" id="codeId" onclick="getCode()">获取验证码</span> 
                     <span class="code-btn" id="codeId2" ></span>  
                    </div>
                   
            </div>
        </div>
        <div class="tips hide" id="J-tips">
            <p>短信已经发送到您的手机，如在60秒之内还没有收到短信验证，请重新获取验证码。</p>
        </div>
        <c:choose>
        <c:when test="${msg !=null }">
        	<div class="error_tips"> ${msg }</div>
        </c:when>
		<c:otherwise>
			<div class="error_tips hide"> </div>
		</c:otherwise>        	
        </c:choose>
        	
        <div class="form-btn">
            <input type="button" value="下一步" class="btn" id="J_forgotPassNext_btn" onclick="subValid()" />
        </div>
    </div>
</div>
</form>
<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
<script src="${staticFile_s}/commons/js/user/getPass.js" type="text/javascript"></script>
</body>
</html>