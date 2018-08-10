<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>商家注册</title>
<meta name="keywords" content="这里是关键词">
<meta name="description" content="这里是描述">
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-status-bar-style" content=" black ">
<meta name="apple-mobile-web-app-title" content="众聚猫">
<meta name="format-detection" content="telephone=no">
<%@include file="/WEB-INF/views/commons/user.jsp"%>
<link href="${staticFile_s}/commons/css/newbese.css" rel="stylesheet"
	type="text/css">
<link rel="stylesheet" type="text/css"
	href="${staticFile_s }/commons/css/login.css">
<script src="${staticFile_s}/commons/js/user/register.js"
	type="text/javascript"></script>
<link href="${staticFile_s}/commons/css/zepto.alert.css"
	rel="stylesheet" type="text/css">
<%-- <link rel="shortcut icon" type="image/x-icon" href="${path}/commons/img/favicon.ico" /> --%>
<script type="text/javascript"
	src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<script src="${staticFile_s}/commons/js/user/des.js"
	type="text/javascript"></script>
<%@include file="/WEB-INF/views/commons/commonsIco.jsp"%>
</head>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp"%>
<body>
	<%-- hidden域 --%>
	<input type="hidden" id="path" value="<%=path%>" />
	<div class="back">
		<img src="${staticFile_s }/commons/img/user/login_bg.jpg">
		<div class="headly">
			<a href="javascript:goBack();" class="goback"><span><b></b><i></i></span></a>
			<h3>商家注册</h3>
		</div>
		<div class="login">
			<div class="login-g">
				<span> 企业信息</span>
			</div>
			<div class="login-group">
				<div class="field">
					 <input type="text" placeholder="企业名称" class="text-box" id="name">
				</div>
			</div>
			<div class="login-g">
				<input type="radio" value="1" class="text-box" name="companyBizType" />企业
					<input type="radio" value="2" class="text-box" name="companyBizType"/>个体工商户
					<input type="radio" value="3" class="text-box" name="companyBizType"/>事业单位
			</div>
		
				<div class="login-group">
				<div class="field">
					<input type="text" placeholder="联系人" class="text-box co"
						id="">
				</div>
			</div>
			<div class="login-group">
				<div class="field">
					<input type="text" placeholder="联系电话"
						class="text-box pa" id="">
				</div>
			</div>
			<div class="login-group">
				<div class="field">
					<select  id="">
					<option>请选择</option>
					 </select>
					<input value="省" style="padding-top: 20px">
					<select  id="">
					<option>请选择</option>
					 </select>
					<input value="市" style="padding-top: 20px">
					<select  id="">
					<option>请选择</option>
					 </select>
					<input value="区(县)" style="padding-top: 20px">
				</div>
			</div>
			<div class="login-group">
				<div class="field">
					<input type="text" placeholder="联系地址"
						class="text-box pa" id="">
				</div>
			</div>
			<div class="login-group">
				<div class="field">
					<input type="text" placeholder="邮箱"
						class="text-box pa" id="">
				</div>
			</div>
			<div class="login-group">
				<div class="field">
					<input type="text" placeholder="企业信息介绍"
						class="text-box pa"  id="">
				</div>
			</div>
			<div class="login-group">
				<div class="field">
					<a  style="padding-top: 18px; display: block;" onclick="zmFile()"  >上传合法的证明文件</a>
					<input type="file" hidden="true" id="companyLegitimacyUrl"
						class="text-box pa" id="">
				</div>
			</div>
			<div class="login-group">
				<div class="field">
					<a  style="padding-top: 18px; display: block;" onclick="xqFile()"  >公司详情文件</a>
					<input type="file" hidden="true" id="companyDetailedUrl"
						class="text-box pa" id="">
				</div>
			</div>
			<div class="login-group">
				<div class="field">
						<a  style="padding-top: 18px; display: block;" onclick="sfzzmFile()"  >法人身份证正面</a>
					<input type="file" hidden="true" id="idCardFront"
						class="text-box pa" id="">
				</div>
			</div>
			<div class="login-group">
				<div class="field">
					<a  style="padding-top: 18px; display: block;" onclick="sfzfmFile()"  >法人身份证反面</a>
					<input type="file" hidden="true" id="idCardVerso"
						class="text-box pa" id="">
				</div>
			</div>
			<div class="login-g">
				<span> 企业会员帐号</span>
			</div>
			<div class="login-group">
				<div class="field">
					<input type="text" placeholder="真实姓名"
						class="text-box pa" id="">
				</div>
			</div>
			<div class="login-group">
				<div class="field">
					<input type="text" placeholder="手机号"
						class="text-box pa" id="">
				</div>
			</div>
			<div class="login-g">
				<span> 企业账户信息</span>
			</div>
			<div class="login-group">
				<div class="field">
					<input type="text" placeholder="用户名"
						class="text-box pa" id="">
				</div>
			</div>
			<div class="login-group">
				<div class="field">
					<input type="password" placeholder="密码"
						class="text-box pa" id="">
				</div>
			</div>
			<div class="login-group">
				<div class="field">
					<input type="password" placeholder="确认密码"
						class="text-box pa" id="">
				</div>
			</div>
			<div class="login-g">
				<span> <input type="checkbox" id="agreement"
					name="sku-checkbox"><a
					href="http://api.zhongjumall.com/register_notes.jsp">同意《众聚猫服务协议》</a>
				</span>
			</div>
			<div class="login-btn">
				<button type="button" class="btn" onclick="doRegister()">注册</button>
			</div>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
	$("#captchaImage").click(
			function() {
				$(this).attr("src",
						"${path}/customer/getImageRegist?date=" + new Date());
			});

	$("#changeImage").click(
			function() {
				$("#captchaImage").attr("src",
						"${path}/customer/getImageRegist?date=" + new Date());
			});
	function zmFile(){
		 document.getElementById("companyLegitimacyUrl").click();
	}
	function xqFile(){
		 document.getElementById("companyDetailedUrl").click();
	}
	function sfzzmFile(){
		 document.getElementById("idCardFront").click();
	}
	function sfzfmFile(){
		 document.getElementById("idCardVerso").click();
	}
	function doRegister(){
		var name = document.getElementById("name").value;//企业名称
		var companyBizType = document.getElementsByName("companyBizType").checked=true;//企业类型
		var name = document.getElementById("name").value;
		var name = document.getElementById("name").value;
		var name = document.getElementById("name").value;
		var name = document.getElementById("name").value;
		var name = document.getElementById("name").value;
		var name = document.getElementById("name").value;
		var name = document.getElementById("name").value;
		var name = document.getElementById("name").value;
		$.ajax({
			dataType:text,
			data:{},
			url:"",
			async:true,
			success:function(){
				
			}
		})
	}
</script>