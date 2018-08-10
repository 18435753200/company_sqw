<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>平台活动规则信息-促销活动</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link href="${path}/commons/css/reset.css" rel="stylesheet"
	type="text/css">
<link href="${path}/commons/css/promotion.css" rel="stylesheet"
	type="text/css">

<link rel="stylesheet" type="text/css"
	href="${path }/commons/css/coupon.css" />
<script src="${path}/commons/js/my97/WdatePicker.js"></script>
<script src="${path}/commons/js/platformRule_edit_fn.js"></script>
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
						<div class="promotion-title">平台活动规则信息</div>
						<div class="form-inline">
							<div class="form-group">
							<input type="hidden" id="activeId" value="${bean.activeId }">
								<label for="promotion-name">规则名称：</label>
								<div class="field">
									<input readonly="readonly" type="text" name="ruleName" id="ruleName"
										class="promotion-txt" value="${bean.ruleName }">
								</div>
							</div>
							<!-- <div class="form-group">
								<label for="promotion-type">赠品类型：</label>
								<div class="field">
									<select disabled="disabled" name="pType" id="pType" class="promotion-sel">
										<option value="1">金券</option>
										<option value="赠礼品卡">赠礼品卡</option>
									</select>
								</div>
							</div> -->
						</div>
						<div class="form-inline">
							<div class="form-group">
								<label for="promotion-vipType">会员类型：</label>
								<div class="field">
									<select disabled="disabled" name="cpsType" id="cpsType" class="promotion-sel">
										<option value="0">自有平台</option>
										<!-- <option value="第三方">第三方</option>
								<option value="第三方">不限</option> -->
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="promotion-vip">会员等级：</label>
								<div class="field">
									<select disabled="disabled" name="userGrade" id="userGrade" class="promotion-sel">
										<option value="0">不限</option>
										<!-- <option value="注册会员">注册会员</option>
								<option value="白银会员">白银会员</option>
								<option value="金牌会员">金牌会员</option> -->
									</select>
								</div>
							</div>
						</div>
						<div class="form-inline">
							<div class="form-group">
								<label for="promotion-visit">访问方式：</label>
								<div class="field">
									<select disabled="disabled" name="platform" id="platform" class="promotion-sel">
										<option value="0">不限</option>
										<!-- <option value="手机">手机</option> -->
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="promotion-amount">城市地区：</label>
								<div class="field">
									<select disabled="disabled" name="areaId" id="areaId" class="promotion-sel">
										<option value="0">不限</option>
										<!-- <option value="上海">上海</option> -->
									</select>
								</div>
							</div>
						</div>
						<div class="form-inline">
							<div class="form-group">
								<label for="promotion-pay">支付方式：</label>
								<div class="field">
									<select disabled="disabled" name="payment" id="payment" class="promotion-sel">
										<option value="0">不限</option>
										<!-- <option value="中行">中行</option> -->
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="promotion-trigger">触发条件：</label>
								<div class="field">
								
										<input  type="radio" name="triggerCondition"
											id="triggerCondition" class="promotion-checkbox"
												<c:if test="${bean.triggerCondition == 2 }">checked="checked" </c:if>
												<c:if test="${bean.triggerCondition == 98}">checked="checked"</c:if> 
												<c:if test="${bean != null }"> disabled="disabled" </c:if>		
										value="2">
												
											 <label
											for="promotion-trigger">注册</label>
										<input  type="radio" name="triggerCondition" 
												<c:if test="${bean.triggerCondition == 1}">checked="checked"</c:if>
												<c:if test="${bean.triggerCondition == 99}">checked="checked"</c:if>
												<c:if test="${bean != null }"> disabled="disabled" </c:if>
											id="triggerCondition" class="promotion-checkbox"
											 value="1"> <label
											for="promotion-trigger">订单确认收货</label>
										<input  type="radio" name="triggerCondition" 
												<c:if test="${bean.triggerCondition == 100}">checked="checked"</c:if>
												<c:if test="${bean != null }"> disabled="disabled" </c:if>
											id="triggerCondition" class="promotion-checkbox"
											 value="1">
											<label>好评送券</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="promotion-des">规则描述：</label>
							<textarea readonly="readonly" name="description" id="description"
								class="promotion-textarea">${bean.description }</textarea>
						</div>
						<div class="form-group">
							<input type="button" name="button" id="backButton"
								class="promotion-btn promotionManage-btn" value="返回">
						</div>
					</div>
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