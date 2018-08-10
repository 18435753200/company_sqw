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
<link href="${staticFile_s}/commons/css/my.css" rel="stylesheet" type="text/css">
<%-- zepto alert --%>
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/person.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/common/common.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/main.js"></script>
<title><spring:message code="title_wealth" /></title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>
<style>
.wealth{width: 100%;background-size: 100%;overflow: hidden;text-align: center;background-color: #ffffff;}
.wealth >div{margin:24px 0px 24px 0px;}
</style>
<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>	
	<div class="headly">
		<a href="javascript:;" onclick="goBack();" class="goback"><span><b></b><i></i></span></a>
	    <h3>我的反馈</h3>
    </div>
	<div class="wrap" id="myinfo">

		 <ul class="form-list">
		 <a href="<%=path %>/suggest/find?states=1">
		 	 <li>
	 	 		<div class="label">已处理</div>
		     </li>	
		</a>     
		 	<a href="<%=path %>/suggest/find?states=0">
		 	<li>
	 	 		<div class="label">未处理</div>
		     </li>
		     </a>
		 	
		 	
		  </ul> 
		   <div class="form-btn">
		   	 <div class="exit">
		       <a href="${path }/suggest/initSuggest"  class="bg" >提交反馈</a>
		    </div>
		   </div>  	
	</div>
</body>
<script>
function toTransferIndex(){
	var state = '${state}';
		window.location = "${path}/wealth/transferIndex"
}
</script>
</html>