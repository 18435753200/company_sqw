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
<script src="${path}/commons/js/gift_list_fn.js"></script>

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
				<input type="hidden" id="ruleId" value="${ruleId}">
				<input type="hidden" id="pType" value="${pType}">
				<div class="promotion-wrap">
						<div class="promotion-hd">
							<div class="promotion-title">赠品管理</div>
							<div class="promotion-filter">
								<!-- <div class="form-group">
									<label for="promotion-name">规则描述：</label> <input type="text"
										name="promotion" id="promotion-name"
										class="promotion-txt gifts-name large">
								</div> -->
								<c:if test="${giftFlag}">
									<div class="form-group">
										<label for="promotion-date">选择赠品类型：</label> <select name="select"
											id="giftType" class="promotion-sel  gifts-type large">
											<c:if test="${giftType==1}">
												<option selected="" value="1">金券</option>
											</c:if>
											<c:if test="${giftType==2}">
												<option selected="" value="2">现金券</option>
											</c:if>
											<!-- <option value="2">现金券</option> -->
											<!-- <option  value="2">鑫券</option>
											<option  value="4">礼品卡</option>
											<option  value="8">积分</option> -->
											<!-- <option  value="16">现金</option> -->
											<!-- <option  value="32">折扣</option> -->
											<!-- <option  value="64">包邮</option> -->
										  	<!-- <option  value="128">商品</option> -->
											
										</select>
									</div>
								</c:if>
								<div class="form-group">
									<!-- <input type="button" name="button" id="button"
										class="promotion-btn large" value="查询"> -->
									<c:if test="${giftFlag}">
									<input type="button" name="button" id="queryButton"
										onclick="toAddGift();" class="promotion-btn large" value="查询添加赠品">
									</c:if>
									<input type="button" name="button" id="backButton"
										onclick="backButton('${ruleId }')" class="promotion-btn large"
										value="返回">
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