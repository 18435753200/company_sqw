<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>

<meta charset="UTF-8">
<title>UNICORN-优惠券列表</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<script src="${path}/commons/js/my97/WdatePicker.js"></script>
<script src="${path}/commons/js/coupon_list_fn.js"></script>
<link rel="stylesheet" type="text/css"
	href="${path }/commons/css/coupon.css" />
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<!-- 导航 end -->
<div class="blank10"></div>
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
					<p class="c1">优惠券列表</p>
				</div>
			</div>
			<div class="blank10"></div>
			<!--  -->
			<div class="coupon-wrap">
				<div class="coupon-hd">
					<div class="coupon-filter">
						<div class="form-group">
							<label for="coupon-name">优惠券名称：</label> <input type="text"
								name="couponname" id="couponname" class="coupon-txt large">
						</div>
						<div class="form-group">
							<label for="coupon-type">优惠券类型：</label> <select name="coupontype"
								id="coupontype" class="coupon-sel large">
								<option selected="" value="0">请选择</option>
								<option value="1">金券</option>
								<option value="2">现金券</option>
							</select>
						</div>
						<div class="form-group">
							<label for="coupon-value">面值：</label>
							<input type="text" name="couponacount" id="couponacount" class="coupon-txt w100 large">
						</div>
						<div class="form-group">
							<label for="coupon-bTime">开始时间：</label>
							<input type="text" name="starttime" id="starttime" onClick="WdatePicker()" class="coupon-txt w200 large">
						</div>
						<div class="form-group">
							<label for="coupon-eTime">结束时间：</label>
							<input type="text" name="endtime" id="endtime" onClick="WdatePicker({minDate:'#F{$dp.$D(\'starttime\')}'})" class="coupon-txt w200 large">
						</div>
						<div class="form-group">
							<input type="button" name="button" id="button" onclick="clickSubmit()"
								class="coupon-btn large" value="查询">
							<input type="button" name="button2" id="button2" onclick="downCheckListExcel()"
								class="coupon-btn large" value="导出表格">
						</div>
					</div>
				</div>
				<div class="coupon-bd">
					
				</div>
			</div>
			<!--  -->
		</div>
	</div>

	<div class="blank10"></div>
	<!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>

	<script type="text/javascript">
        
    </script>


</body>
</html>