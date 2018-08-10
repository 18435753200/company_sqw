<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<%@include file="/WEB-INF/views/commons/base.jsp" %>
<link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/grzxcss/aui.css" />
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<c:set var="path" value="<%=path %>"></c:set>
<title><spring:message code="title_call_center" /></title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
<style>
a{color:#000}
.aui-list .aui-list-item-right, .aui-list-item-title-row em {
    max-width: 60%;
    position: relative;
    font-size: 0.6rem;
    color: #757575;
    margin-left: 0.25rem;
}
</style>
</head>

<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>	
	<header class="aui-bar aui-bar-nav">
        <a class="aui-pull-left aui-btn" href="${path }/customer/toMy">
            <span class="aui-iconfont aui-icon-left"></span>
        </a>
        <div class="aui-title">联系客服</div>
</header>
<section class="aui-content">
        <ul class="aui-list aui-list-in">
            <li class="aui-list-item">
                <div class="aui-list-item-label-icon">
                    <i class="aui-iconfont"><img src="${path }/commons/img/grzxtp/kfdh.png" style="width: 30px"></i>
                </div>
                <div class="aui-list-item-inner aui-list-item-arrow">
                    <div class="aui-list-item-title">客服电话</div>
                    <div class="aui-list-item-right"><a href="tel:18911951885">18911951885</a>
                    </div>
                </div>
            </li>
            <li class="aui-list-item">
                <div class="aui-list-item-label-icon">
                    <i class="aui-iconfont"><img src="${path }/commons/img/grzxtp/yj.png" style="width: 30px"></i>
                </div>
                <div class="aui-list-item-inner aui-list-item-arrow">
                    <div class="aui-list-item-title">电子邮箱</div>
                    <div class="aui-list-item-right"><a href="mailto:www.zhongjumall@163.com">www.zhongjumall@163.com</a>
                    </div>
                </div>
            </li>
        </ul>
    </section>
<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
<script src="${staticFile_s}/commons/js/mainsetclass.js" type="text/javascript"></script>

<script type="text/javascript">
var u = navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端

if(isAndroid){
	$(".form-list").find("a").prop("href","javascript:;");
}

</script>

</body>
</html>