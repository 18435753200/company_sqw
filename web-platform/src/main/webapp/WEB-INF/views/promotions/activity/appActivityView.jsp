<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询APP活动</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link href="${path}/commons/css/reset.css" rel="stylesheet"
	type="text/css">
<link href="${path}/commons/css/promotion.css" rel="stylesheet"
	type="text/css">
<link rel="stylesheet" type="text/css"
	href="${path }/commons/css/coupon.css" />
<script src="${path}/commons/js/my97/WdatePicker.js"></script>
<script src="${path}/commons/js/app_activity_edit_fn.js"></script>
<script src="${path}/commons/js/plupload-master/js/plupload.full.min.js"></script>
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
					<p>APP活动管理&nbsp;&gt;&nbsp;</p>
					<p class="c1">查询APP活动</p>
				</div>

				<div class="blank10"></div>

				<div class="promotion-wrap">
					<div class="promotion-bd manage-promotion">
						<div class="promotion-title">查询APP活动</div>
						<form method="post" id="activityAPPAction" enctype="multipart/form-data">
							<div class="form-inline">
								<div class="form-group">
									<label for="promotion-name">活动名称：</label>
									<div class="field">
										<input type="hidden" name="activityId" id="activityId" value="${bean.activityId }">
										<input type="text" name="activityName" id="activityName" class="promotion-txt" value="${bean.activityName}" disabled="disabled">
										<span class="dpl-tip-inline-warning"></span>
									</div>
								</div>
								<div class="form-group">
									<label for="promotion-organizers">活动类型：</label> 
									<input type="hidden" id="statuss" value="${bean.status }">
									<select name="status" id="status" class="promotion-sel" disabled="disabled">
									  <c:if test="${bean.status == 1}">
										<option select="" value="1">限时抢购</option>
									  </c:if>
									  <c:if test="${bean.status == 2}">
										<option select="" value="2">其他</option>
									  </c:if>
									</select>
								</div>
							</div>
							<div class="form-inline" id="mainPic">
								<div class="form-group">
									<label for="promotion-name">一级主图：</label>
									<div class="field">
										<img alt="" src="${imgto}${bean.mainPicUrl}" height="50px" width="60px">
									</div>
								</div>
								<div class="form-group">
									<label for="promotion-organizers">一级主图标题：</label> 
									<input type="text" name="mainTitle" id="mainTitle" class="promotion-txt" value="${bean.mainTitle }" disabled="disabled">
								</div>
							</div>
							<div class="form-inline" id="mainPic">
								<div class="form-group">
									<label for="promotion-name">pc一级主图：</label>
									<div class="field">
										<img alt="" src="${imgto}${config.custText01}" height="50px" width="60px">
									</div>
								</div>
							</div>
							<div class="form-inline">
								<div class="form-group">
									<label for="promotion-name">二级主图：</label>
									<div class="field">
										<img alt="" src="${imgto}${bean.picUrl}" height="50px" width="60px">
									</div>
								</div>
								<div class="form-group">
									<label for="promotion-organizers">二级主图标题：</label> 
									<input type="text" name="title" id="title" class="promotion-txt" value="${bean.title }" disabled="disabled">
								</div>
							</div>
							<div class="form-inline" id="mainPic">
								<div class="form-group">
									<label for="promotion-name">pc二级主图：</label>
									<div class="field">
										<img alt="" src="${imgto}${config.custText02}" height="50px" width="60px">
									</div>
								</div>
							</div>
							<div class="form-inline" id="timeTo">
								<div class="form-group">
									<label for="promotion-startTime">开始时间：</label>
									<div class="field">
										<input type="text" onClick="WdatePicker()" name="startTime" id="startTime" class="promotion-txt"
											value="<fmt:formatDate value="${bean.startTime }" pattern="yyyy-MM-dd HH:mm:ss" />" disabled="disabled">
									</div>
								</div>
								<div class="form-group" style="padding-bottom: 38px;">
									<label for="promotion-endTime">结束时间：</label>
									<div class="field">
										<input type="text" onClick="WdatePicker()" name="endTime" id="endTime" class="promotion-txt"
											value="<fmt:formatDate value="${bean.endTime }" pattern="yyyy-MM-dd HH:mm:ss" />" disabled="disabled">
									</div>
								</div>
							</div>
							
							
							
							<%--<div class="form-group">
								<label for="promotion-des">活动描述：</label>
								<textarea name="description" id="description" class="promotion-textarea">${bean.description }</textarea>
								<span class="dpl-tip-inline-warning"></span>
							</div>
							--%>
							
							
							<table class="tb-promotion">
								<colgroup>
									<col width="200px">
									<col width="200px">
									<col width="450px">
								</colgroup>
								<thead>
									<tr>
										<th>活动名称</th>
										<th>关联商品（PID）</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${empty products}">
										<tr>
											<td colspan="5">
												<center>
													<img src="${path }/commons/images/no.png" />
												</center>
											</td>
										</tr>
									</c:if>
									<c:forEach items="${products}" var="pro">
										<tr>
											<td class="name">${fn:escapeXml(pro.actName)}</td>
											
											<td class="name">${fn:escapeXml(pro.gPid)}</td>
											<td class="tac">
											    <a href="javascript:;" onclick="deleteActivityPro(${pro.actproId},${bean.activityId })">删除</a>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							
							
							<div class="form-inline">
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
	<div class="t_footer">
			<h1 class="logo_dl"></h1>
	</div>
	<!-- 底部 end -->
	<script type="text/javascript">

    
	</script>
</body>
</html>