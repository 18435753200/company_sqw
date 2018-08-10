<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
<title>个人周期分红设置</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link rel="stylesheet" type="text/css" href="${path}/commons/css/jiben.css">
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/B2C_orderlist.css">
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/wuliu_alert.css"/>
	<script src="${path }/commons/js/oneFhCycle.js"></script>
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="wrap">
	 <!-- 左边 start -->
		<div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		</div>
		<!-- 左边 end -->

		<!-- 右边 start -->
		<div class="right">
			<div class="c21">
			<div class="title">
				<p>卖家中心&nbsp;&gt;&nbsp; </p>
				<p>基础设置&nbsp;&gt;&nbsp; </p>
				<p class="c1">个人周期分红设置</p>
			</div>
	     </div>
			<div class="tb-void">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<thead>
						<tr style="display: table; width: 100%; table-layout: fixed;">
							<th width="97">编号</th>
							<th width="197">执行时间</th>
							<th width="97">执行批次</th>
							<th width="127">批次时间</th>
							<th width="97">操作状态</th>
							<th width="177">操作备注</th>
							
						</tr>
					</thead>
					
					<tbody style="display: block; height: 600px; overflow-y: scroll;">
						<c:forEach items="${list}" var="plan" varStatus="status">
							<tr class="tr-th" style="display: table; width: 100%; table-layout: fixed;">
							<td width="100">${status.index+1}</td>
							<td width="200"><fmt:formatDate value="${plan.executeStartTime}" type="both"/></td>
							<td width="100">${plan.executeBatch}</td>
							<td width="130">${plan.executeBatchTime}小时</td>
							<td width="100">
								<c:if test="${plan.executeStartTime.time > plan.planTime.time}">
									待执行
								</c:if>
								<c:if test="${plan.executeEndTime.time < plan.planTime.time}">
									已执行
								</c:if>
								<c:if test="${plan.executeStartTime.time < plan.planTime.time && plan.executeEndTime.time > plan.planTime.time}">
									正在执行
								</c:if>
							</td>
							<td width="160">
								<c:if test="${plan.executeStartTime.time > plan.planTime.time}">
									<a href="javascript:void(0)" onclick="deletePlan(${plan.cyclePlanId})">取消</a>
									<a href="javascript:void(0)" onclick="updatePlan(${plan.cyclePlanId})">修改</a>
									<a href="javascript:void(0)" onclick="selectPlan(${plan.cyclePlanId})">查询</a>
								</c:if>
								<c:if test="${plan.executeEndTime.time < plan.planTime.time}">
									<a href="javascript:void(0)" onclick="selectPlan(${plan.cyclePlanId})">查询</a>
								</c:if>
								<c:if test="${plan.executeStartTime.time < plan.planTime.time && plan.executeEndTime.time > plan.planTime.time}">
									<a href="javascript:void(0)" onclick="selectPlan(${plan.cyclePlanId})">查询</a>
								</c:if>
							</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		</div>
<div class="blank10"></div>
	 <!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
</body>
<script type="text/javascript">
 
</script>
</html>