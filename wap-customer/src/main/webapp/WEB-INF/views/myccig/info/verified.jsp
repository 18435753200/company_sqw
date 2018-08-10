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
<link href="${staticFile_s}/commons/css/w_base.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/grzxcss/aui.css" />
<link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/grzxcss/grzx_yjfk_css.css" />
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/person.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/common/common.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/main.js"></script>
<title><spring:message code="title_cusinfo_name_verify" /></title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
<style>
	.errorp p {
    margin-top: 0.2rem;
    color: #999999;
    font-size: 0.7rem;
    line-height: 1.5rem;
    }
    .form-list .fieldd input, select {
    line-height: 2.3rem;
    color: #505050;
    width: 80%;
    background: #ffffff;
    text-align: left;
    height: 2.3rem;
}
.exitt a {
    background: #e63232;
    color: #ffffff;
    font-size: 1rem;
    width: 35%;
    display: inline-block;
    text-align: center;
    height: 2rem;
    line-height: 2rem;
     margin-top: 2rem;
     margin-left: 32%;
}
</style>
</head>

<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>	
	<!-- <div class="headly">
		<span onclick="goBack()"><b></b><i></i></span>
	    <h3>实名认证</h3>
    </div> -->
    <header class="aui-bar aui-bar-nav">
        <a class="aui-pull-left aui-btn" href="javascript:;"  onclick="goBack();" class="goback">
            <span class="aui-iconfont aui-icon-left"></span>
        </a>
        <div class="aui-title">实名认证</div>
	</header>
    <form action="" method="post" id="veryForm">
    <input type="hidden" name="currentPage" value="${currentPage }">
	<div class="aui-content aui-margin-b-15">
    <ul class="aui-list aui-form-list">
        <li class="aui-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-label">
                   	 姓名
                </div>
	            <div class="aui-list-item-input">
	                 <input type="text" class="w" id="realName" name="realName" value="${user.realName }" placeholder="2-8个字" />
	            </div>
	        </li>
	         <li class="aui-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-label">
                   	 身份证
                </div>
	            <div class="aui-list-item-input">
	                <input type="tel" class="w" name="idCard" id="idCard" value="${idCard }" placeholder="15至18位身份证号" />
	            </div>
	        </li>
       </ul>
		<div class="error">
		</div>
	    <div class="exitt">	
			<a href="javascript:" onclick="subVerify()" id="save">保存</a>
	    </div>
	</div>
	</form>
<%@include file="/WEB-INF/views/commons/footer.jsp" %>
<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
<script src="${staticFile_s}/commons/js/common/common.js" type="text/javascript"></script>
<script src="${staticFile_s}/commons/js/cciginfo/info.js" type="text/javascript"></script>

</body>
</html>