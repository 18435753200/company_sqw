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
			<div class="pu_hd"><h3>入库列表</h3></div>
	  		<div class="pu_bd">
	  			<table>
	  				<tr class="order_hd">
	  					<th width="40px;">序号</th>
	  					<th width="110px;">商品编码</th>
	  					<th width="110px;">商品条码</th>
	  					<th width="110px;">商品名称</th>
	  					<th width="60px;">规格</th>
	  					<th width="60px;">单位</th>
	  					<th width="80px;">本次通知数量</th>
	  					<th width="80px;">已入库数量</th>
	  					<th width="80px;">库房</th>
	  					<th width="110px;">是否正品</th>
	  				</tr>
	  				<c:forEach items="${pageBean.result }" var="stock" varStatus="Status">
	  				<tr>
	  					<td>${Status.index+1 }</td>
		  				<td>${stock.pcode }</td>
		  				<td>${stock.barCode }</td>
		  				<td>${stock.skuNameCn }</td>
		  				<td>${stock.format }</td>
		  				<td>${stock.unit }</td>
		  				<td>${stock.qty }</td>
		  				<td>${stock.qtyStorage }</td>
		  				<td>${stock.warehouse }</td>
		  				<td><c:if test="${stock.isgenuine==1 }">正品</c:if><c:if test="${stock.isgenuine!=1 }">残品</c:if></td>
	  				</tr>
	  			</c:forEach>
	  			</table>
	  			
	  		</div>

        </div>
        </div>
        </div>
</body>
</html>