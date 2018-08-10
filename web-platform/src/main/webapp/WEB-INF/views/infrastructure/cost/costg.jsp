<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>货物费用名称设置</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css"href="${path}/commons/css/currenry.css" />
</head>
<body>
 <%@include file="/WEB-INF/views/include/header.jsp"%>
<div class="center">
		<!-- 左边 start -->
		
	 <div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp" %>
	 </div>

	 <div class="right">
	 	<div class="c21">
			<div class="title">
				<p>卖家中心&nbsp;&gt;&nbsp; </p>
				<p>基础设置&nbsp;&gt;&nbsp; </p>
				<p class="c1">货物费用名称</p>
			</div>
	     </div>

	     <div class="btn">
	     	 <span class="add-ck-btn"><a href="../infrastructure/goAddCostg" id="addoreditCostg">添加货物费用名称</a></span>

	     </div>

		<div class="pu_wrap">
		 	<table class="pu">
				<tbody>
					<tr class="order-hd">
						<th width="200px">编号</th>
						<th width="200px">状态</th>
						<th width="236px">货物费用名称</th>
						<th width="400px">说明</th>
						<th width="200px">操作</th>
					</tr>	
			        <c:forEach items="${cost}" var="cost">
						<tr class="order-bd">
							<td>${cost.code}<input type="hidden" name="sid" value="${cost.sid}"></td>
							<c:if test="${cost.status==1}">
							<td>启用</td>
							</c:if>
							<c:if test="${cost.status==0}">
							<td>禁用</td>
							</c:if>
							<td>${cost.costName}</td>
							<td>${cost.remark}</td>
						<td><a href="${path}/infrastructure/editCost?sid=${cost.sid}" class="up">修改</a></td>	
						</tr>
					</c:forEach>
		       </tbody>

		   </table>
	   </div>

	</div>
</div>
<%@include file="/WEB-INF/views/include/foot.jsp" %>
</body>
</html>