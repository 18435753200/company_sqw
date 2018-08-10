<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>UNICORN-查看优惠券规则/title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${path }/commons/css/coupon.css" />

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
					<p class="c1">查看优惠券</p>
				</div>
			</div>
			<div class="blank10"></div>
			<div class="coupon-wrap">
				<div class="coupon-bd manage-coupon">
					<div class="form-inline">
						<div class="form-group">
							<label for="coupon-name">优惠券使用规则名称：</label>
							<div class="field">
								<input type="text" name="couponrulename" id="couponrulename"
									class="coupon-txt" disabled>
							</div>
						</div>
						<div class="form-group">
							<label for="coupon-name">优惠券名称：</label>
							<div class="field">
								<input type="text" name="couponname" id="couponname"
									class="coupon-txt" disabled>
							</div>
						</div>
					</div>
					<div class="form-inline">
						<div class="form-group">
							<label for="coupon-type">优惠券类型：</label>
							<div class="field">
								<select name="coupontype" id="coupontype" class="coupon-sel"
									disabled>
									    <c:if test="${couponRule.coupontype==1}">
											<option selected="" value="1">金券</option>
										</c:if>
										<c:if test="${couponRule.coupontype==2}">
											<option selected="" value="2">鑫券</option>
										</c:if>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="coupon-limit">一级限制条件：</label>
							<div class="field">
								<select name="mainruletype" id="mainruletype" class="coupon-sel"
									disabled>
									<option selected="" value="1">全场</option>
								</select>
							</div>
						</div>
					</div>
					<div class="form-inline">
						<div class="form-group">
							<label for="coupon-name">优惠券金额：</label>
							<div class="field">
								<input type="text" name="couponacount" id="couponacount"
									class="coupon-txt" disabled> <span>￥</span>
							</div>
						</div>
						<div class="form-group">
							<label for="coupon-meet">消费满足金额：</label>
							<div class="field">
								<input type="text" name="meetpiece" id="meetpiece"
									class="coupon-txt" disabled>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>



	</div>

	<div class="blank10"></div>
	<!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
	<!-- 底部 end -->
</body>
</html>