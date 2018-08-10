<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>UNICORN-优惠券规则列表</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<script src="${path}/commons/js/couponRule_list_fn.js"></script>
<link rel="stylesheet" type="text/css"
	href="${path }/commons/css/coupon.css" />
<link href="${path}/commons/css/reset.css" rel="stylesheet"	type="text/css">
<link href="${path}/commons/css/gift_coupon.css" rel="stylesheet"	type="text/css">
<style type="text/css">
	label {color:red}
</style>
</head>
<body>
	<!-- 导航 start -->
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 end -->

	<div class="center">
		<!-- 左边 start -->
		<div class="left f_l">
			<%@include file="/WEB-INF/views/promotions/leftPage.jsp"%>
		</div>
		<!-- 左边 end -->


		<div class="right">
			<div class="gift_title">
				<span>向指定用户赠送赠品</span>
			</div>
			<input type="hidden" id="couponId" value="${couponId }">
			<div class="gift_one">
				<div class="gift_onet">
					<span>输入要赠劵的零售商Id进行查询</span>
					<input type="text" id="userId">
					<button type="button" id="findUsers" onclick="findUserByName()">查询</button><span></span>
				</div>
				<div class="gift_oneb">
				</div>
			</div>
	
			<div class="gift_two">
				<div class="gift_twot">
					<span>请选择赠品种类</span>
					<select id="type">
						<option value="0">请选择</option>
						<option value="1">金券</option>
						<option value="2">现金券</option>
					</select><span></span><label>以下显示的优惠券均为有效的且已启用的优惠券</label>
				</div>
				<div class="gift_twob">
				</div>
			</div>
			
			<div class="gift_three">
				<span class="thl">来源描述</span>
				<span class="thr">
					<textarea rows="3" cols="20" id="desc">
	               </textarea>
				</span><br/><span id="des"></span>
			</div>
			<div class="bu">
				<button type="button" onclick="giveCouponToUser()">提交</button>
			</div>
			</div>
	
		</div>
	
	<div class="blank10"></div>
	<!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
	<!-- 底部 end -->
</body>
</html>