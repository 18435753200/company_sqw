<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/Storage.css">
</head>
<body>
 <%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 end -->
	<div class="center">
	<!-- 左边 start -->
	 <div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp" %>
		</div>
		
		<!-- 左边 end -->
		<div class="right">
<div class="pu_wrap">
				<div class="pu_hd"><h3>入库明细</h3></div>
		  		<div class="pu_bd">
		  			<table>
		  				<tr class="order_hd">
		  					<th width="40px;">序号</th>
		  					<th width="70px;">日期</th>
		  					<th width="110px;">商品ID</th>
		  					<th width="110px;">商品条码</th>
		  					<th width="120px;">商品名称</th>
		  					<th width="80px;">入库数量</th>
		  					<th width="80px;">库房</th>
		  					<th width="110px;">批号</th>
		  				</tr>
		  				<c:forEach items="${pb }" var="result" varStatus="status">
		  				<tr>
		  					<td>${status.index+1 }</td>
			  				<td><fmt:formatDate value="${result.createTime }" pattern="yyyy-MM-dd"/></td>
			  				<td>${result.pcode }</td>
			  				<td>${result.barCode }</td>
			  				<td>${result.skuNameCn }</td>
			  				<td>${result.qtyStorage }</td>
			  				<td>${result.warehouse }</td>
			  				<td>${result.batchNumber }</td>
		  				</tr>
		  			</c:forEach>
		  			</table>
		  			<c:if test="${!empty pb}">
					<%@include file="/WEB-INF/views/dealerbuy/basePage/basePage.jsp" %>	
				</c:if>
		  		</div>

	        </div>
	        </div>
	        </div>
</body>
</html>