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
<link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/grzxcss/aui.css" />
<link rel="stylesheet" href="${staticFile_s}/commons/css/grzxcss/public.css">

<script type="text/javascript" src="${staticFile_s}/commons/js/main.js"></script>
<title>${title }</title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>
<style>
body{background:white;}
.content,.list{width:100%;}
.list li{width:100%;display:flex;border-bottom:1px solid #e6e6e6;height:2.2rem;line-height:1.4rem;}
.list li span{width:30%;height:1.4rem;margin-top:0.4rem;display:inline-block;border-right:1px solid #e6e6e6;padding-left:0.75rem;}
.list li div{width:70%;height:1.4rem;margin-top:0.4rem;padding-left:0.75rem;}
</style>
<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>	
<input type="hidden" id="page" value="${pageBean.page}"/>	
<input type="hidden" id="totalPage" value="${pageBean.totalPage}"/>	
<input type="hidden" id="account" value="${account}"/>	
    <header class="header">
	    <a class="go_back" href="javascript:;" onclick="goBack();"></a>
	    <div class="title">业务伙伴</div>
	</header>
	<div class="content">
	
	<ul class="list">
	  <li>
	     <span>联系人</span>
	     <div>${parenSupplier.contact }</div>
	  </li>
	  <li>
	     <span>联系电话</span>
	     <div>${parenSupplier.phone }</div>
	  </li>
	</ul>
   <%--  <ul class="userList">
        <li>
           	 <div class="lxr">联系人</div>
           	 <div class="lxrsj">联系人手机</div>
        </li>
    </ul>
      <ul class="userList">
        <li>
           	 <div class="lxr">${parenSupplier.contact }</div>
           	 <div class="lxrsj">${parenSupplier.phone }</div>
        </li>
    </ul> --%>
</div>
</body>
</html>