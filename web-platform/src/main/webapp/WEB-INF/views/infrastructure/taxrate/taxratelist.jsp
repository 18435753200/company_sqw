<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>税率设置</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/currenry.css" />
	<script type="text/javascript" src="${path }/commons/js/infrastructure/infrastructure.js"></script>
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
				<p class="c1">税率设置</p>
			</div>
	     </div>

	     <div class="btn">
	     	 <span class="add-ck-btn"><a href="../infrastructure/goAddTaxrate" id="addoreditTaxrate">添加税率</a></span>
<!-- 	     	<a href="#">保存</a> -->
<!-- 	     	<a href="#">放弃</a> -->
	     </div>
		<div class="pu_wrap">
		 	<table class="pu">
				<tbody>
					<tr class="order-hd">
						<th width="200px">编号</th>
						<th width="236px">税率%</th>
						<th width="400px">说明</th>
						<th width="400px">状态</th>
						<th width="200px">操作</th>
					</tr>	
					<c:forEach items="${taxList}" var="taxList">
								<tr class="order-bd">
								<input type="hidden" name="sid" value="${taxList.sid}">
									<td>${taxList.code}</td>
									<td style="text-align:right;">${taxList.tax}</td>
									<td>${taxList.remark}</td>
									<td>
										<c:if test="${taxList.status==1}">启用</c:if>
										<c:if test="${taxList.status==0}">禁用</c:if>
									</td>
								<td>
									<a href="${path}/infrastructure/editTaxrate?sid=${taxList.sid}" class="up">修改</a>
								</td>
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