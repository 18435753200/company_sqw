<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head>
        <meta charset="utf-8">
        <title>流量宝</title>
        <meta name="keywords" content="这里是关键词">
        <meta name="description" content="这里是描述">
		<meta name="viewport" content="minimal-ui=yes,width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
        <meta name="apple-mobile-web-app-status-bar-style" content=" black ">
        <meta name="apple-mobile-web-app-title" content="众聚猫">
        <meta name="format-detection" content="telephone=no">
        <%@include file="/WEB-INF/views/commons/user.jsp" %>
        <link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
        <link href="${staticFile_s}/commons/css/userActivity/flowRate.css" rel="stylesheet" type="text/css">
        <script src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"  type="text/javascript" ></script>
	</head>
    <body>
    <%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
	   <div class="activity-m">

			<div class="box">
				<h4><img src="${staticFile_s}/commons/img/userActivity/flowRate/b_01.png"></h4> 
				<div class="uli">
					<ul>
						<li>
							<a href="http://m.zhongjumall.com/item/get/3031261901211985">
								<div class="pic"><img src="${staticFile_s}/commons/img/userActivity/flowRate/u_01.jpg"></div>
								<div class="text">
									<h3>自然乐园 芦荟胶补水套装</h3>
									<h2>99</h2>
								</div>
							</a>
						</li>
						<li>
							<a href="http://m.zhongjumall.com/item/get/3031179221659866">
								<div class="pic"><img src="${staticFile_s}/commons/img/userActivity/flowRate/u_02.jpg"></div>
								<div class="text">
									<h3>SNP 竹炭面膜</h3>
									<h2>89</h2>
								</div>
							</a>
						</li>
						<li>
							<a href="http://m.zhongjumall.com/item/get/2424272757363353" class="no">
								<div class="pic"><img src="${staticFile_s}/commons/img/userActivity/flowRate/u_03.jpg" style="width:35px; height:80px"></div>
								<div class="text">
									<h3>Fresh 鲜芬 玫瑰饮料 </h3>
									<h2>18.8</h2>
								</div>
							</a>
						</li>
					</ul>
				</div>	
			</div>
			<form action="" method="get">
				<div class="activity-list">
					<ul>
						<li>
							<input name="" type="tel" placeholder="请输入手机号" class="tet" id="mobile">
						</li>
						<li>
							<input name="" type="tel" placeholder="请输入验证码" class="tet" id="verificationCode">
							<input name="" type="button" value="发送验证码" class="cale" onclick="getRegistCode()" id="reGet">
						</li>
						<li>
							<input name="password" type="password" id="password" placeholder="请输入6-20位数字和字母组合构成的密码" class="tet">
						</li>
						<li class="check-box">
							<input type="checkbox" checked="" name="sku-checkbox" id="agreement"><a href="http://api.zhongjumall.com/register_notes.jsp" class="xi">同意《众聚猫服务协议》</a>
						</li>
					</ul>
					<input name="" type="button" value="注册" class="receive" onclick="registCoupons()" id="coupons">
					<p>注册立领50牛币！首单成功购物满100元，加送150牛币！</p>
				</div>
			</form>
		</div>
		<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
		<script src="${staticFile_s}/commons/js/userActivity/registFlowRate.js" type="text/javascript"></script>
		<script src="${staticFile_s}/commons/js/user/des.js" type="text/javascript"></script>
		<script src="${staticFile_s}/commons/js/zepto.min.js" type="text/javascript"></script>
	</body>
</html>




