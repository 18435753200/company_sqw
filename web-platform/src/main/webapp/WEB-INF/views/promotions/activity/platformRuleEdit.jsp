<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>创建平台活动规则-促销活动</title>
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
						<div class="promotion-title">创建平台活动规则</div>

						<form method="post" id="platformRuleAction"
							enctype="multipart/form-data">
							<input type="hidden" name="activeId" id="activeId"
								value="${activeId }">
							<div class="form-inline">
								<div class="form-group">
									<label for="promotion-name">规则名称：</label>
									<div class="field">
										<input type="text" name="ruleName" id="ruleName"
											class="promotion-txt" value="${bean.ruleName }">
									</div>
								</div>
								<div class="form-group">
									<label for="promotion-type">赠品类型：</label>
									<div class="field">
										<select name="pType" id="pType" class="promotion-sel">
											<option selected="" value="1">金券</option>
											<option value="2">现金券</option>
											<!-- <option value="赠礼品卡">赠礼品卡</option> -->
										</select>
									</div>
								</div>
							</div>
							<div class="form-inline">
								<div class="form-group">
									<label for="promotion-vipType">会员类型：</label>
									<div class="field">
										<select name="cpsType" id="cpsType" class="promotion-sel">
											<option selected="" value="0">自有平台</option>
											<!-- <option value="第三方">第三方</option>
								<option value="第三方">不限</option> -->
										</select>
									</div>
								</div>
								<div class="form-group">
									<label for="promotion-vip">会员等级：</label>
									<div class="field">
										<select name="userGrade" id="userGrade" class="promotion-sel">
											<option selected="" value="0">不限</option>
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
										<select name="platform" id="platform" class="promotion-sel">
											<option selected="" value="0">不限</option>
											<!-- <option value="手机">手机</option> -->
										</select>
									</div>
								</div>
								<div class="form-group">
									<label for="promotion-amount">城市地区：</label>
									<div class="field">
										<select name="areaId" id="areaId" class="promotion-sel">
											<option selected="" value="0">不限</option>
											<!-- <option value="上海">上海</option> -->
										</select>
									</div>
								</div>
							</div>
							<div class="form-inline">
								<div class="form-group">
									<label for="promotion-pay">支付方式：</label>
									<div class="field">
										<select name="payment" id="payment" class="promotion-sel">
											<option selected="" value="0">不限</option>
											<!-- <option value="中行">中行</option> -->
										</select>
									</div>
								</div>
								<div class="form-group">
									<label for="promotion-trigger">触发条件：</label>
									<div class="field J-field">
										<input  type="radio" name="triggerCondition"
											id="triggerCondition" class="promotion-checkbox"
											checked="checked" value="2"> <label
											for="promotion-trigger">注册</label>
										<input  type="radio" name="triggerCondition"
											id="triggerCondition" class="promotion-checkbox"
											 value="1"> <label
											for="promotion-trigger">订单确认收货</label>
										<!-- <input  type="radio" name="triggerCondition"
											id="triggerCondition" class="promotion-checkbox"
											 value="3"> <label
											for="promotion-trigger">订单满赠(如果跟订单金额相关，则选择)</label> -->
									</div>
								</div>
							</div>
							<div class="form-inline">
								<div class="form-group" style="display:none;" id="promotion-condition">
									<label for="promotion-visit">是否自动阶梯送：</label>
									<div class="field">
										<select name="isrepeat" id="isrepeat" class="promotion-sel">
											<option value="99">请选择</option>
											<option value="0">否</option>
											<option value="1">是</option>
										</select><span></span>
									</div>
								</div>
								<div class="form-group" id="promotion-conditions" style="display:none;">
									<label for="promotion-conditions">满足金额：</label>
									<div class="field">
										<input id="condition1" name="condition1" value="" class="promotion-txt"><span></span>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="promotion-des">规则描述：</label>
								<textarea name="description" id="description"
									class="promotion-textarea">${bean.description }</textarea>
							</div>
							<div class="form-inline">
								<div class="form-group">
									<input type="button" name="button" id="confirmButton"
										class="promotion-btn promotionManage-btn" value="确定">
								</div>
								<div class="form-group">
									<input type="button" name="button" id="backButton"
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

	<script type="text/javascript">
		
	</script>
</body>
</html>