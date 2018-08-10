<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>商品活动列表</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link href="${path}/commons/css/reset.css" rel="stylesheet"	type="text/css">
<link href="${path}/commons/css/promotion.css" rel="stylesheet"	type="text/css">
<script src="${path}/commons/js/my97/WdatePicker.js"></script>
<script src="${path}/commons/js/activityproduct_list_fn.js"></script>

<link rel="stylesheet" type="text/css" href="${path }/commons/css/coupon.css" />

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
					<p>活动管理&nbsp;&gt;&nbsp;</p>
					<p class="c1">商品活动列表</p>
				</div>

				<div class="blank10"></div>

				<div class="promotion-wrap">
					<div class="promotion-hd">
						<div class="promotion-title">商品活动列表</div>
						<div class="promotion-filter">
						<!-- <form action="activeForm" ></form> -->
							<div class="form-group">
								<label for="promotion-name">活动名称：</label> <input type="text"
									name="activityName" id="activityName"
									class="promotion-txt promotion-name large">
							</div>
							<div class="form-group">
								<input type="button" name="button" id="button"
									onclick="clickSubmit(1)" class="promotion-btn large" value="查询">
							</div>
						</div>
					</div>
					<div class="promotion-bd"></div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="blank10"></div>
	<!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>

	<script type="text/javascript">
		
	</script>
</body>
</html>