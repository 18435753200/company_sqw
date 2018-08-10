<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>赠品管理_促销活动</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link href="${path}/commons/css/reset.css" rel="stylesheet"
	type="text/css">
<link href="${path}/commons/css/promotion.css" rel="stylesheet"
	type="text/css">
	
<script src="${path}/commons/js/my97/WdatePicker.js"></script>
<script src="${path}/commons/js/gift_add_fn.js"></script>

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
					<p>赠品管理&nbsp;&gt;&nbsp;</p>
					<p class="c1">赠品列表</p>
				</div>

				<div class="blank10"></div>

				<div class="promotion-wrap">
					<div class="promotion-hd">
						<div class="promotion-title">赠品管理</div>
						<div class="promotion-filter">
							<!-- <div class="form-group">
								<label for="promotion-name">规则描述：</label> <input type="text"
									name="promotion" id="promotion-name"
									class="promotion-txt gifts-name large">
							</div> -->
							<input type="hidden" id="ruleId" value="${ruleId }">
							<input type="hidden" id="pType" value="${pType }">
							<div class="form-group">
								<label for="promotion-date">赠品类型：</label> <select name="select"
									id="giftType" class="promotion-sel  gifts-type large">
									<c:if test="${giftType == 1 }">
										<option selected="selected" value="1">金券</option>
									</c:if>
									<c:if test="${giftType == 2 }">
										<option value="2">现金券</option>
									</c:if>
								</select>
							</div>
							<div class="form-group">
								<input type="button" name="button" id="backButton()" onclick="backButton('${ruleId }')"
									class="promotion-btn large" value="返回">
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