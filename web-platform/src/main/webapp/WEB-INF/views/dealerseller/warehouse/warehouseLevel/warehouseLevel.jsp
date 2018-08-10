<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>库房级别设置</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/currenry.css" />
</head>
<body>
<%@include file="/WEB-INF/views/include/header.jsp"%>
<div class="center">
	 <div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp" %>
	 </div>
	 <div class="right">
	 	<div class="c21">
			<div class="title">
				<p>卖家中心&nbsp;&gt;&nbsp; </p>
				<p>商品管理&nbsp;&gt;&nbsp; </p>
				<p class="c1">库房级别设置</p>
			</div>
	     </div>
	     <div class="btn">
	     	 <span class="add-ck-btn"><a href="../warehouseInfras/goAddWareHouseLevel" id="addoreditwareHouseLevel">添加库房级别</a></span>
	     </div>

		<div class="pu_wrap">
		 	<table class="pu">
				<tbody>
					<tr class="order-hd">
						<th width="200px">编号</th>
						<th width="236px">库房级别</th>
						<th width="400px">说明</th>
						<th width="200px">操作</th> 
					</tr>	
				  <c:forEach items="${warehouseStorelevel}" var="warehouseStorelevel">
						<tr class="order-bd">
							<td><input type="hidden" name="sid" value="${warehouseStorelevel.sid}">${warehouseStorelevel.levelCode}</td>
							<td>${warehouseStorelevel.levelName}</td>
							<td>${warehouseStorelevel.remark}</td>
							<td>
							<a href="${path}/warehouseInfras/editWareHouseLevel?sid=${warehouseStorelevel.sid}" class="up">修改</a>
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