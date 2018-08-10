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
<script src="${path}/commons/js/activity_edit_fn.js"></script>
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
									<input type="hidden" name="activeId" id="activeId" value="${bean.activeId }">
										<input type="text" name="activeName" id="activeName"
											class="promotion-txt" value="${bean.activeName }"> <span
											class="dpl-tip-inline-warning"></span>
									</div>
								</div>
								<div class="form-group">
									<label for="promotion-section">部门：</label>
									<div class="field">
										<select name="select" id="promotion-section"
											class="promotion-sel">
											<option selected="" value="1">运营部</option>
										</select>
									</div>
								</div>
							</div>
							<div class="form-inline">
								<div class="form-group">
									<label for="promotion-startTime">开始时间：</label>
									<div class="field">
										<input type="text" onClick="WdatePicker()" name="expiringFrom"
											id="expiringFrom" class="promotion-txt"
											value="<fmt:formatDate
									value="${bean.expiringFrom }"
									pattern="yyyy-MM-dd HH:mm:ss" />">
										<span class="dpl-tip-inline-warning">请填写开始时间</span>
									</div>
								</div>
								<div class="form-group">
									<label for="promotion-endTime">结束时间：</label>
									<div class="field">
										<input type="text" onClick="WdatePicker()" name="expiringTo"
											id="expiringTo" class="promotion-txt"
											value="<fmt:formatDate
									value="${bean.expiringTo }"
									pattern="yyyy-MM-dd HH:mm:ss" />">
										<span class="dpl-tip-inline-warning">请填写开始时间</span>
									</div>
								</div>
							</div>
							<div class="form-inline">
								<div class="form-group">
									<label for="promotion-organizersType">主办方类型：</label> <select
										name="organizersType" id="organizersType"
										class="promotion-sel">
										<option selected="" value="1">平台</option>
									</select>
								</div>
								<div class="form-group">
									<label for="promotion-organizers">主办方：</label> <select
										name="organizersId" id="organizersId" class="promotion-sel">
										<option selected="" value="1">运营部</option>
									</select>
								</div>
							</div>
							<%-- <div class="form-inline">
								<div class="form-group">
									<label for="promotion-assume">承担比例：</label>
									<div class="field">
										<input type="text" name="ratio" id="ratio" value="${bean.ratio }"
											class="promotion-txt"><span>%</span>
											<span
											class="dpl-tip-inline-warning"></span>
									</div>
								</div>
								<div class="form-group">
									<label for="promotion-picLink">图像链接：</label>
									<div class="field">
										<input type="text" name="imageUrl" id="imageUrl"
											class="promotion-txt" value="${bean.imageUrl }">
									</div>
								</div>
							</div> --%>
							<div class="form-inline">
								<%-- <div class="form-group">
									<label for="promotion-actLink">活动链接地址：</label>
									<div class="field">
										<input type="text" name="linkUrl" id="linkUrl"
											class="promotion-txt" value="${bean.linkUrl }">
									</div>
								</div>
								<div class="form-group">
									<label for="promotion-actCode">活动连接代码：</label>
									<div class="field">
										<input type="text" name="linkCode" id="linkCode"
											class="promotion-txt" value="${bean.linkCode }">
									</div>
								</div> --%>
								<div class="form-group">
									<label for="promotion-organizers">活动类型：</label> <select
										name="activeType" id="activeType" class="promotion-sel">
										<option selected="" value="1">单一促销</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="promotion-des">活动描述：</label>
								<textarea name="description" id="description"
									class="promotion-textarea">${bean.description }</textarea>
								<span class="dpl-tip-inline-warning"></span>
							</div>
							<div class="form-group">
								<label for="promotion-ad">广告语：</label>
								<textarea name="activeMsg" id="activeMsg"
									class="promotion-textarea">${bean.activeMsg }</textarea>
								<span class="dpl-tip-inline-warning"></span>
							</div>
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