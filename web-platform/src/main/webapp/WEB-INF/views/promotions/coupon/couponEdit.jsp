<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>UNICORN-新增优惠券</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>

<link rel="stylesheet" type="text/css"
	href="${path }/commons/css/coupon.css" />
<script src="${path}/commons/js/my97/WdatePicker.js"></script>
<script src="${path}/commons/js/coupon_edit_fn.js"></script>
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
		<form method="post" id="couponAction" enctype="multipart/form-data">
			<!-- 主办方id 一期写死为1 -->
			<input type="hidden" name="sponsorId" id="sponsorid" value="1" />
			<div class="c2">
				<div class="c21">
					<div class="title">
						<p>促销管理&nbsp;&gt;&nbsp;</p>
						<p>优惠券管理&nbsp;&gt;&nbsp;</p>
						<p class="c1">创建优惠券</p>
					</div>
				</div>
				<div class="blank10"></div>
				<div class="coupon-wrap">
					<div class="coupon-bd manage-coupon">
						<div class="form-group">
							<label for="coupon-name"><i class="c_red">*</i> 优惠券名称：</label>
							<div class="field">
								<input type="text" name="couponname" id="couponname"
									class="coupon-txt"> <span
									class="dpl-tip-inline-warning"></span>
							</div>
						</div>

						<div class="form-group">
							<label for="coupon-type"><i class="c_red">*</i> 优惠券类型：</label>
							<div class="field">
								<select name="coupontype" id="coupontype" class="coupon-sel">
									<option selected="" value="1">金券</option>
									<option value="2">现金券</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="coupon-price"><i class="c_red">*</i> 优惠券金额：</label>
							<div class="field">
								<input type="text" name="couponacount" id="couponacount"
									class="coupon-txt"> <span>￥</span> <span
									class="dpl-tip-inline-warning"></span>
							</div>
						</div>

						<div class="form-inline">
							<div class="form-group">
								<label for="coupon-type">是否自动创建：</label>
								<div class="field">
									<select name="isauto" id="isauto" class="coupon-sel">
										<option value="1">是</option>
									</select>
								</div>
							</div>

						</div>
						
						<div class="form-inline">
							<div class="form-group">
								<label for="coupon-section">部门：</label>
								<div class="field">
									<select name="deptid" id="deptid" class="coupon-sel">
										<option selected="" value="1001">运营部</option>
										<option value="1002">客服部</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="coupon-organizers">主办方：</label> <select
									name="sponsortype" id="sponsortype" class="coupon-sel">
									<option selected="" value="1">平台</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="coupon-plus">允许自身叠加使用：</label>
							<div class="field">
								<select name="issuperposition" id="issuperposition"
									class="coupon-sel">
									<option value="0">不允许</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="coupon-startTime"><i class="c_red">*</i>
								开始时间：</label>
							<div class="field">
								<input type="text" name="starttime" id="starttime"
									onClick="WdatePicker({minDate:'%y-%M-%d'})" class="coupon-txt"> <span
									class="dpl-tip-inline-warning">请填写开始时间</span>
							</div>
						</div>
						<div class="form-group">
							<label for="coupon-endTime"><i class="c_red">*</i> 结束时间：</label>
							<div class="field">
								<input type="text" name="endtime" id="endtime"
									onClick="WdatePicker({minDate:'#F{$dp.$D(\'starttime\')}'})" class="coupon-txt"> <span
									class="dpl-tip-inline-warning">请填写结束时间</span>
							</div>
						</div>
						<div class="form-group">
							<label for="coupon-info">券用途说明：</label>
							<textarea name="couponuse" id="couponuse" class="coupon-textarea"></textarea>
							<span class="dpl-tip-inline-warning"
								style="display:block;margin-left:180px;"></span>
						</div>
						
						<div class="form-group">
							<input type="button" name="createCoupon" id="createCoupon"
								class="coupon-btn couponManage-btn" value="创建">
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>

	<div class="blank10"></div>
	<!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
	<!-- 底部 end -->
</body>
</html>