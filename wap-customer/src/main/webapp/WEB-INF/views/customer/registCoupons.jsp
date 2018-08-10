<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>

<html>
	<head>
        <title>活动页面</title>
        <meta name="keywords" content="这里是关键词">
        <meta name="description" content="这里是描述">
		<meta name="viewport" content="minimal-ui=yes,width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
        <meta name="apple-mobile-web-app-status-bar-style" content=" black ">
        <meta name="apple-mobile-web-app-title" content="众聚猫">
        <meta name="format-detection" content="telephone=no">
		<%@include file="/WEB-INF/views/commons/user.jsp" %>
        <link href="${staticFile_s}/commons/css/newbese.css" rel="stylesheet" type="text/css">
        <link href="${staticFile_s}/commons/css/activity.css" rel="stylesheet" type="text/css">
        <link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
        <script src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"  type="text/javascript" ></script>
        <%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
	</head>
    <body>
    	<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
    	<div class="activity-m">
			<h2><img src="${staticFile_s}/commons/img/activity1.jpg"></h2>
			<form action="" method="get">
				<input type="hidden" value="${origin }" id="origin">
				<div class="activity-list">
					<ul>
						<li>
							<label>手机号</label>
							<input name="mobile" type="text" id="mobile"  class="tt">
						</li>
						<li>
							<label>验证码</label>
							<input name="" type="text"  class="tt" id="verificationCode">
							<input class="cale" type="button" value="发送验证码" onclick="getRegistCode()" id="reGet">
						</li>
						<li>
							<label>密码</label>
							<input name="password" type="password" id="password"   class="tt">
						</li>
					</ul>
					<!-- <a class="receive">点击领取</a> -->
					<input class="receive" type="button" value="点击领取" onclick="registCoupons()" id="coupons">
				</div>
			</form>
		</div>
		<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
<script src="${staticFile_s}/commons/js/user/registCoupons.js" type="text/javascript"></script>
<script src="${staticFile_s}/commons/js/user/des.js" type="text/javascript"></script>
<script src="${staticFile_s}/commons/js/zepto.min.js" type="text/javascript"></script>
	</body>
</html>


