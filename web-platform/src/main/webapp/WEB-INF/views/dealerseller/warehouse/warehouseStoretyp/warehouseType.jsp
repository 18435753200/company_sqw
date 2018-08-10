<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>库房类型设置</title>
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
				<p class="c1">库房类型设置</p>
			</div>
	     </div>

	     <div class="btn">
	     	 <span class="add-ck-btn"><a href="../warehouseInfras/goAddWarehouseStoretype" id="addoreditwarehouseStoretype">添加库房类型</a></span>
	     </div>
		<div class="pu_wrap">
		 	<table class="pu">
				<tbody>
					<tr class="order-hd">
						<th width="200px">编号</th>
						<th width="236px">库房类型</th>
						<th width="400px">说明</th>
						<th width="200px">操作</th>
					</tr>	
			       <c:forEach items="${warehouseStoretype}" var="warehouseStoretype">
						<tr class="order-bd">
							<td><input type="hidden" name="sid" value="${warehouseStoretype.sid}" >${warehouseStoretype.typeCode}</td>
							<td>${warehouseStoretype.typeName}</td>
							<td>${warehouseStoretype.remark}</td>
								<td><a href="${path}/warehouseInfras/editWarehouseStoretype?sid=${warehouseStoretype.sid}" class="up">修改</a></td>
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
