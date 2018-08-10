<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>创建促销活动_促销活动</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link href="${path}/commons/css/reset.css" rel="stylesheet"
	type="text/css">
<link href="${path}/commons/css/promotion.css" rel="stylesheet"
	type="text/css">

<link rel="stylesheet" type="text/css"
	href="${path }/commons/css/coupon.css" />
<script src="${path}/commons/js/my97/WdatePicker.js"></script>
<%-- <script src="${path}/commons/js/activitis/activitylist.js"></script> --%>
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
					<p>活动管理&nbsp;&gt;&nbsp;</p>
					<p class="c1">活动列表</p>
				</div>

				<div class="blank10"></div>

				<div class="promotion-wrap">
					<div class="promotion-bd manage-promotion">
						<div class="promotion-title">创建促销活动</div>
						<form method="post" id="activityAction"
							enctype="multipart/form-data">
							<div class="form-inline">
								<div class="form-group">
									<label for="promotion-name">活动名称：</label>
									<div class="field">
										<input type="hidden" name="activeId" id="activeId"
											value="${bean.activeId }"> <input type="text"
											name="activeName" id="activeName" class="promotion-txt"
											value="${bean.activeName }"> <span
											class="dpl-tip-inline-warning"></span>
									</div>
								</div>
							</div>
							<div class="form-inline">
								<div class="form-group">
									<input type="button" name="button" id="confirmButton" onClick="saveActivity()"
										class="promotion-btn promotionManage-btn" value="确定">
								</div>
								<div class="form-group">
									<input type="button" name="backButton" id="backButton"
										class="promotion-btn promotionManage-btn" value="取消">
								</div>
							</div>
					</div>

					</form>
				</div>
			</div>
		</div>
	</div>
	</div>

	<div class="blank10"></div>
	<!-- 底部 start -->
	<div class="t_footer">
				<h1 class="logo_dl"></h1>
		
	</div>
	<!-- 底部 end -->
	<script type="text/javascript">
		
	</script>
</body>
</html>