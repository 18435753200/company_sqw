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


		<div class="c2">
			<div class="c21">
				<div class="title">
					<p>促销管理&nbsp;&gt;&nbsp;</p>
					<p>优惠券管理&nbsp;&gt;&nbsp;</p>
					<p class="c1">优惠券规则列表</p>
				</div>
			</div>
			<div class="blank10"></div>
			<div class="coupon-wrap">
				<div class="coupon-hd">
					<div class="coupon-filter rule">
						<input type="hidden" value="${couponId}" id="couponId"
							name="couponId" />
						<input type="hidden" value="${coupontype}" id="coupontype"
							name="coupontype" />
						<div class="form-group">
							<label for="rule-name">使用规则名称：</label> <input type="text"
								name="couponrulename" id="couponrulename" class="coupon-txt large w200">
						</div>
						<div class="form-group">
							<input type="button" name="button" id="find"
								onclick="clickSubmit()" class="coupon-btn large" value="查询">
							<c:if test="${!empty buttonsMap['优惠券列表-创建券规则']}">
							<input type="button" name="button" id="create" 
								class="coupon-btn large" value="创建B2B优惠券规则">
							</c:if>
							<c:if test="${!empty buttonsMap['优惠券列表-创建券规则'] && coupontype == 1}">
							<input type="button" name="button" id="createB2C" 
								class="coupon-btn large" value="创建B2C优惠券规则">
							</c:if>
							<c:if test="${!empty buttonsMap['优惠券列表-给指定用户发券']}">
							<input type="button" name="button" id="send" 
								class="coupon-btn large" value="给指定B2B用户发券" onclick="showtable('${couponId}',0)">
							</c:if>
							<c:if test="${!empty buttonsMap['优惠券列表-给指定用户发券'] && coupontype == 1}">
							<input type="button" name="button" id="send" 
								class="coupon-btn large" value="给指定B2C用户发券" onclick="showtable('${couponId}',1)">
							</c:if>
						</div>
					</div>
				</div>
				<div class="coupon-bd"></div>
			</div>
		</div>



	</div>
	
	<div class="blank10"></div>
	<!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
	<!-- 底部 end -->
</body>
</html>