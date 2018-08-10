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
<link href="${staticFile_s}/commons/css/w_base.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/my.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/grzxcss/aui.css" />
<link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/grzxcss/grzx_yjfk_css.css" />
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/user/common.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/person.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/common/common.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/main.js"></script>
<title><spring:message code="title_cusinfo_cusinfo" /></title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
<style>
	em.qd{
	position:absolute;
		right: 10px;
	}
.form-list .field input, select {
    line-height: 2.3rem;
    color: #505050;
    width: 80%;
    background: #ffffff;
    text-align: left;
    height: 2.3rem;
}
.rDialog-ok, .rDialog-ok:hover {
    background: #dd191b;
    color: #fff;
}
.rDialog-wrap {
    position: relative;
    background: #404040;
    opacity: .9;
    background-clip: padding-box;
    border-radius: 10px;
    -moz-border-radius: 10px;
    -o-border-radius: 10px;
    -webkit-border-radius: 10px;
    box-shadow: 1px 1px 1px #000;
    padding: 1em 1em;
}

</style>
</head>

<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>	
	<!-- <div class="headly">
		<span onclick="goBack()"><b></b><i></i></span>
	    <h3>修改邮箱<em class="rd" onclick="checkEmail()">确定</em></h3>
	   
    </div> -->
    <header class="aui-bar aui-bar-nav">
        <a class="aui-pull-left aui-btn" href="javascript:;"  onclick="goBack();">
            <span class="aui-iconfont aui-icon-left"></span>
        </a>
        <div class="aui-title">修改邮箱</div><em class="qd" onclick="checkEmail()">确定</em>
	</header>
	<div class="wrap">
	    <ul class="form-list">
	        <li>
	           
	            <div class="field">
	                <input type="text" class="w"  id="_email" value="${fn:escapeXml(email)}" placeholder="请输入邮箱"/>
	            </div>
	        </li>
       </ul>
	</div>
<%@include file="/WEB-INF/views/commons/footer.jsp" %>
<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
<script src="${staticFile_s}/commons/js/cciginfo/info.js" type="text/javascript"></script>
</body>
</html>	