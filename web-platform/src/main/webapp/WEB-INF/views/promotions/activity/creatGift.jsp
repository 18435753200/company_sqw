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
<script src="${path}/commons/js/creat_gift_edit_fn.js"></script>
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
					<p>赠品管理 &nbsp;&gt;&nbsp;</p>
					<p class="c1">创建赠品</p>
				</div>

				<div class="blank10"></div>

				<div class="promotion-wrap">
					<div class="promotion-bd manage-promotion">
						<div class="promotion-title">创建赠品</div>
						<form method="post" id="activityAction"
							enctype="multipart/form-data">
							<div class="form-inline">
								<div class="form-group">
									<label for="promotion-name">赠品描述：</label>
									<div class="field">
									<input type="hidden" name="ruleId" id="ruleId" value="${ruleId}"/>
									<input type="hidden" name="pType" id="pType" value="${pType}"/>
										<input type="text" name="giftDesc" id="giftDesc"
											class="promotion-txt" value=""> <span
											class="dpl-tip-inline-warning"></span>
									</div>
								</div>
								<div class="form-group">
									<input type="hidden" name="giftType" id="giftType" value="${giftType}"/>
									<label for="promotion-organizersType">赠品类型：</label>
									<select	 id="giftType"	class="promotion-sel">
										<c:if test="${giftType == 1}"><option value="1">金券</option></c:if>
										<c:if test="${giftType == 2}"><option value="2">现金券</option></c:if>
										<c:if test="${giftType == 4}"><option value="4">礼品卡</option></c:if>
										<c:if test="${giftType == 8}"><option value="8">积分</option></c:if>
										<c:if test="${giftType == 16}"><option value="16">现金</option></c:if>
										<c:if test="${giftType == 32}"><option value="32">折扣</option></c:if>
										<c:if test="${giftType == 64}"><option value="64">包邮</option></c:if>
										<c:if test="${giftType == 128}"><option value="128">sku单品</option></c:if>
									</select>
								</div>
							</div>
							
							<c:if test="${giftType == 1 ||giftType == 2 ||giftType == 4  }">
								<div class="form-inline">	
								<input type="hidden" name="couponruleid" id="couponruleid" value="${couponRule.couponruleid}"/>
								
									<table class="tb-promotion">
										<colgroup>
											<col width="120px">
											<col width="137px">
											<col width="137px">
											<col width="250px">
											<col width="250px">
										</colgroup>
										<tbody>
											<tr>
												<td>
													<c:if test="${giftType == 1}">金券</c:if>
													<c:if test="${giftType == 2}">鑫券</c:if>
													<c:if test="${giftType == 4}">礼品卡券</c:if>
													名称：${couponRule.couponrulename}
												</td>
												<td>
													<c:if test="${giftType == 1 ||  giftType == 2}">券面值：</c:if>
													<c:if test="${giftType == 4}">礼品卡券面值：</c:if>
													${couponRule.couponacount}
												</td>
												<td>满足订单金额：${couponRule.meetpiece}</td>
												<td>开始时间：<fmt:formatDate value="${couponRule.starttime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
												<td>结束时间：<fmt:formatDate value="${couponRule.endtime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
											</tr>
										</tbody>
									</table>
									
								</div>
							</c:if>
							
							
							<c:if test="${giftType == 16}">
								<div class="form-inline">
									<div class="form-group">
										<label for="promotion-name">${name}</label>
										<div class="field">
											<input type="text" name="discountAmount" id="discountAmount"
														class="promotion-txt" value=""> <span
													class="dpl-tip-inline-warning"></span>
										</div>
									</div>
									<div class="form-group">
									
									</div>
								</div>
							</c:if>
							
							<c:if test="${giftType == 8}">
								<div class="form-inline">
									<div class="form-group">
										<label for="promotion-name">${name}</label>
										<div class="field">
											<input type="text" name="addPoint" id="addPoint"
														class="promotion-txt" value=""> <span
														class="dpl-tip-inline-warning"></span>
										</div>
									</div>
										
									<div class="form-group">
										<label for="promotion-name">加钱类型：</label>
										<select	 id="addType"	class="promotion-sel">
											<option selected="" value="0">-请选择-</option>
											<option value="1">现金</option>
										</select>	
									</div>	
								</div>
								<div class="form-inline">
									<div class="form-group">
										<label for="promotion-name">加现金值：</label>
										<div class="field">
											<input type="text" name="addQty" id="addQty"
														class="promotion-txt" value=""> <span
														class="dpl-tip-inline-warning"></span>
										</div>
									</div>
								</div>
							</c:if>
							<div class="form-inline">
								<div class="form-group">
									<input type="button" name="button" id="confirmButton"
										class="promotion-btn promotionManage-btn" value="确定">
								</div>
								<div class="form-group">
									<input type="button" name="backButton" id="backButton"
										class="promotion-btn promotionManage-btn" value="取消">
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
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
	<!-- 底部 end -->
	<script type="text/javascript">
		
	</script>
</body>
</html>