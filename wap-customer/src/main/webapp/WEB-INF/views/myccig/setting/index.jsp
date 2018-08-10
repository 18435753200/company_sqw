<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<%@include file="/WEB-INF/views/commons/base.jsp" %>
<link href="${staticFile_s}/commons/css/my.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/grzxcss/aui.css" />
<script type="text/javascript" src="${staticFile_s}/commons/js/main.js"></script>
<c:set var="path" value="<%=path %>"></c:set>
<title>账户设置</title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>
<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>	
    <header class="aui-bar aui-bar-nav">
        <a class="aui-pull-left aui-btn" href="javascript:;" onclick="goBack();">
            <span class="aui-iconfont aui-icon-left"></span>
        </a>
        <div class="aui-title">账户设置</div>
</header>
<section class="aui-content">
        <ul class="aui-list aui-list-in">
            <a href="${path }/cusInfo/cusInfo">
            	<li class="aui-list-item">
	                <div class="aui-list-item-label-icon">
	                    <i class="aui-iconfont"><img src="${path }/commons/img/grzxtp/gywm.png" style="width: 30px"></i>
	                </div>
	                <div class="aui-list-item-inner aui-list-item-arrow">
	                    <div class="aui-list-item-title">个人信息</div>
	                    <div class="aui-list-item-right">
	                    </div>
	                </div>
				</li>
			</a>
		 	<a href="${path}/setting/about"><li class="aui-list-item">
                <div class="aui-list-item-label-icon">
                    <i class="aui-iconfont"><img src="${path }/commons/img/grzxtp/grxx.png" style="width: 30px"></i>
                </div>
                <div class="aui-list-item-inner aui-list-item-arrow">
                    <div class="aui-list-item-title">关于我们</div>
                    <div class="aui-list-item-right">
                    </div>
                </div>
            </li>
				</a>
        </ul>
    </section>
       </ul>
	</div>
</body>
</html>