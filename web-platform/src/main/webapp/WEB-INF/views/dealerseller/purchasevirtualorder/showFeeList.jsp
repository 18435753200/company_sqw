<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title></title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
	<title>商品采购_采购订单(表单)</title>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/purchase_s.css">
  </head>
  
  <body>
 <!-- 导航 start -->
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
		  		<div class="pu_hd"><h3>采购费用</h3></div>
		  		<div class="pu_bd">
			  		<table class="ov">
	
			  			<tr class="order_hd">
			  				<th width="40px">序号</th>
			  				<th width="70px">费用日期</th>
			  				<th width="80px">费用名称</th>
			  				<th width="80px">待分摊金额</th>
			  				<th width="80px">分摊方法</th>
							
							<th width="70px">分摊数量</th>
			  				<th width="70px">分摊金额</th>
			  				<th width="70px">币别</th>
			  				<th width="70px">汇率</th>
			  				<th width="70px">本币单价</th>

			  				<th width="70px">本币总价</th>
			  				<th width="70px">凭据影像</th>
			  			</tr>
			  			<c:forEach items="${pb.result }" var="purchaseFee" varStatus="index">
			  			<tr>
			  				<td>${index.index+1 }</td>
			  				<td><fmt:formatDate value="${purchaseFee.costDate}" type="both"/></td>
			  				<td>${purchaseFee.costCode}</td>
			  				<td>${purchaseFee.waitAllocationPrice}</td>
			  				<td>${purchaseFee.allocationCode}</td>
			  				
							<td>${purchaseFee.allocationQty}</td>
			  				<td>${purchaseFee.allocationPrice}</td>
			  				<td>${purchaseFee.currencyCode}</td>
			  				<td>${purchaseFee.exchangeRate}</td>
			  				<td>${purchaseFee.localPrice}</td>

			  				<td>${purchaseFee.localTotalPrice}</td>
			  				<td>${purchaseFee.imagePath}</td>	
			  			</tr>
			  	</c:forEach>
			  		</table>
		  		</div>
		  </div>
		  </div>
		  </div>
  </body>
</html>
