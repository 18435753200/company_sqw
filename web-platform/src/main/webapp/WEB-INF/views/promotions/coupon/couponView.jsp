<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>UNICORN-查看优惠券</title>
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
					<div class="form-group">
						<label for="coupon-name">优惠券名称：</label>
						<div class="field">
							<input type="text" name="coupon" id="couponname"
								value="${fn:escapeXml(coupon.couponname)}" class="coupon-txt"
								disabled>
						</div>
					</div>
					<div class="form-inline">
						<div class="form-group">
							<label for="coupon-type">优惠券类型：</label>
							<div class="field">
								<select name="coupontype" id="coupontype" class="coupon-sel"
									disabled>
									<c:if test="${coupon.coupontype==1}">
										<option selected="" value="1">金券</option>
									</c:if>
									<c:if test="${coupon.coupontype==2}">
										<option selected="" value="2">现金券</option>
									</c:if>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="coupon-price">优惠券金额：</label>
							<div class="field">
								<input type="text" name="couponacount" id="couponacount"
									value="${fn:escapeXml(coupon.couponacount)}" class="coupon-txt"
									disabled> <span>￥</span>
							</div>
						</div>
					</div>
					<div class="form-inline">
						<div class="form-group">
							<label for="coupon-type">是否自动创建：</label>
							<div class="field">
								<select name="isauto" id="isauto" class="coupon-sel" disabled>
									<c:if test="${coupon.isauto==1}">
										<option selected="" value="1">是</option>
									</c:if>
									<c:if test="${coupon.isauto==0}">
										<option selected="" value="0">否</option>
									</c:if>
								</select>
							</div>
						</div>

					</div>

					<div class="form-inline">
						<div class="form-group">
							<label for="coupon-section">部门：</label>
							<div class="field">
								<select name="deptid" id="deptid" class="coupon-sel" disabled>
									<c:if test="${coupon.deptid==1001}">
										<option selected="" value="1001">运营部</option>
									</c:if>
									<c:if test="${coupon.deptid==1002}">
										<option selected="" value="1002">客服部</option>
									</c:if>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="coupon-organizers">主办方：</label> <select
								name="sponsortype" id="sponsortype" class="coupon-sel" disabled>
								<c:if test="${coupon.sponsortype==1}">
									<option selected="" value="1">平台</option>
								</c:if>
							</select>

						</div>
					</div>
					<div class="form-group">
						<label for="coupon-plus">允许自身叠加使用：</label>
						<div class="field">
							<select name="issuperposition" id="issuperposition"
								class="coupon-sel" disabled>
								<c:if test="${coupon.issuperposition==1}">
									<option selected="" value="1">允许</option>
								</c:if>
								<c:if test="${coupon.issuperposition==0}">
									<option selected="" value="0">不允许</option>
								</c:if>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="coupon-startTime">开始时间：</label>
						<div class="field">
							<input type="text" name="starttime" id="starttime"
								value="<fmt:formatDate
									value="${coupon.starttime}"
									pattern="yyyy-MM-dd HH:mm:ss" />"
								class="coupon-txt" disabled>
						</div>
					</div>
					<div class="form-group">
						<label for="coupon-endTime">结束时间：</label>
						<div class="field">
							<input type="text" name="endtime" id="endtime"
								value="<fmt:formatDate
									value="${coupon.endtime}"
									pattern="yyyy-MM-dd HH:mm:ss" />"
								class="coupon-txt" disabled>
						</div>
					</div>
					<div class="form-group">
						<label for="coupon-info">券用途说明：</label>
						<textarea name="couponuse" id="couponuse" class="coupon-textarea"
							disabled>${fn:escapeXml(coupon.couponuse)}</textarea>
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