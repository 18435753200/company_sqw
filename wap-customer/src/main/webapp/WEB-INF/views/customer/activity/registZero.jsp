<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head>
        <meta charset="utf-8">
        <title>众聚猫</title>
        <meta name="keywords" content="这里是关键词">
        <meta name="description" content="这里是描述">
		<meta name="viewport" content="minimal-ui=yes,width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
        <meta name="apple-mobile-web-app-status-bar-style" content=" black ">
        <meta name="apple-mobile-web-app-title" content="众聚猫">
        <meta name="format-detection" content="telephone=no">
        	<%@include file="/WEB-INF/views/commons/user.jsp" %>
        <link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
        <link href="${staticFile_s}/commons/css/userActivity/zero.css" rel="stylesheet" type="text/css">
        <script src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"  type="text/javascript" ></script>
        <script>
			var _hmt = _hmt || [];
			(function() {
			  var hm = document.createElement("script");
			  hm.src = "//hm.baidu.com/hm.js?6c3319111b7d876b61953bfbe8570dbf";
			  var s = document.getElementsByTagName("script")[0];
			  s.parentNode.insertBefore(hm, s);
			})();
		</script>
	</head>
	<style>

	</style>
    <body>
    <%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
	   <div class="activity-m">
			<h2><img src="${staticFile_s}/commons/img/userActivity/zero/banner.jpg"></h2>
			<form action="" method="get">
				<input id="skuId" name="skuId" value="${skuId }" style="display: none;">
				<input id="number" name="number" value="${number }" style="display: none;">
				<input id="pid" name="pid" value="${pid }" style="display: none;">
				<div class="activity-list">
					<ul>
						<li>
							<input name="mobile" type="tel" placeholder="请输入手机号" class="tet" id="mobile">
						</li>
						<li>
							<input name="" type="tel" placeholder="请输入验证码" class="tet" id="verificationCode">
							<input name="" type="button" value="发送验证码" class="cale" onclick="getRegistCode()" id="reGet">
						</li>
						<li>
							<input name="password" type="password" id="password"   placeholder="请输入6-20位数字和字母组合构成的密码" class="tet">
						</li>
					</ul>
					<input name="" type="button" value="注册登录" class="receive" onclick="registCoupons()" id="coupons">
				</div>
			</form>
		</div>
		<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
		<script src="${staticFile_s}/commons/js/userActivity/registZero.js" type="text/javascript"></script>
		<script src="${staticFile_s}/commons/js/user/des.js" type="text/javascript"></script>
		<script src="${staticFile_s}/commons/js/zepto.min.js" type="text/javascript"></script>
	</body>
</html>




