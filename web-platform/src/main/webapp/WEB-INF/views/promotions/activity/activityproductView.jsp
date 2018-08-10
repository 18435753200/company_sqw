<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看商品活动详情</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link href="${path}/commons/css/reset.css" rel="stylesheet"
	type="text/css">
<link href="${path}/commons/css/promotion.css" rel="stylesheet"
	type="text/css">

<link rel="stylesheet" type="text/css"
	href="${path }/commons/css/coupon.css" />
<script src="${path}/commons/js/my97/WdatePicker.js"></script>
<script src="${path}/commons/js/activityproduct_edit_fn.js"></script>
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
					<p class="c1">商品活动详情</p>
				</div>

				<div class="blank10"></div>

				<div class="promotion-wrap">
					<div class="promotion-bd manage-promotion">
						<div class="promotion-title">商品活动详情</div>
						<form method="post" id="activityAction"
							enctype="multipart/form-data">
							<div class="form-inline">
								<div class="form-group">
									<label for="promotion-name">活动名称：</label>
									<div class="field">
										<label>${bean.activeName }</label>
									</div>
								</div>
								<div class="form-group">
									<label for="promotion-section">部门：</label>
									<div class="field">
										<c:choose>
											<c:when test="${bean.deptId == 1 }">
												<label>运营部</label>
											</c:when>
											<c:otherwise><label>运营部</label></c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
							<div class="form-inline">
								<div class="form-group">
									<label for="promotion-organizersType">主办方类型：</label> 
									<div class="field">
										<c:choose>
											<c:when test="${bean.organizersType == 1 }">
												<label>运营部</label>
											</c:when>
											<c:otherwise><label>其他</label></c:otherwise>
										</c:choose>
									</div>
								</div>
								<div class="form-group">
									<label for="promotion-organizers">主办方：</label>
									<div class="field">	
										<c:choose>
											<c:when test="${bean.organizersId == 1 }">
												<label>运营部</label>
											</c:when>
											<c:otherwise><label>其他</label></c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
							
							<div class="form-group">
								<label for="promotion-organizers">活动类型：</label> 
								<div class="field" >
									<%-- <c:choose>
										<c:when test="${bean.activeType == 1 }">
											<label>单一促销</label>
										</c:when>
										<c:otherwise><label>其他</label></c:otherwise>
									</c:choose> --%>
								<select disabled="disabled"
									name="activeType" id="activeType">
									<option value="1">单一促销</option>
								</select>
								</div>
							</div>
							<div class="form-group">
								<label for="promotion-des">活动描述：</label>
								<textarea readonly="readonly" name="textarea" id="description"
									class="promotion-textarea">${bean.description }</textarea>
								<span class="dpl-tip-inline-warning"></span>
							</div>
							<div class="form-group">
								<label for="promotion-ad">广告语：</label>
								<textarea readonly="readonly" name="textarea" id="activeMsg"
									class="promotion-textarea">${bean.activeMsg }</textarea>
								<span class="dpl-tip-inline-warning"></span>
							</div>
							<div class="form-group">
								<input type="button" name="button" id="backButton"
									class="promotion-btn promotionManage-btn" value="返回">
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