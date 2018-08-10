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
		  		<div class="pu_wrap">
		  		<div class="pu_hd"><h3>国内运费</h3></div>
		  		<div class="pu_bd">
			  		<table class="ov">
			  			<tr class="order_hd">
			  				<th width="40px">序号</th>
			  				<th width="70px">发生日期</th>
			  				<th width="60px">运单单号</th>
			  				<th width="60px">接货地址</th>
			  				<th width="60px">送货地址</th>
							
							<th width="80px">运费金额(元)</th>
			  				<th width="60px">分摊方法</th>
			  				<th width="60px">分摊数量</th>
			  				<th width="80px">分摊金额(元)</th>
			  				<th width="45px">币别</th>

			  				<th width="45px">汇率</th>
			  				<th width="60px">本币单价</th>
			  				<th width="60px">本币总价</th>
			  				<th width="60px">凭据影像</th>
			  			</tr>
			  			<c:forEach items="${pb.result }" var="inLandFee" varStatus="index">
			  			<tr>
			  				<td>${index.index+1 }</td>
			  				<td><fmt:formatDate value="${inLandFee.createDate}" type="both"/></td>
			  				<td>${inLandFee.feightCode}</td>
			  				<td>${inLandFee.receiveAddress}</td>
			  				<td>${inLandFee.sendAddress}</td>

			  				<td>${inLandFee.feightPrice}</td>
			  				<td>${inLandFee.allocationCode}</td>
			  				<td>${inLandFee.allocationQty}</td>
			  				<td>${inLandFee.allocationPrice}</td>
			  				<td>${inLandFee.currencyCode}</td>

			  				<td>${inLandFee.exchangeRate}</td>
			  				<td>${inLandFee.localPrice}</td>
			  				<td>${inLandFee.localTotalPrice}</td>
			  				<td>${inLandFee.imagePath}</td>		
			  			</tr>
			  		</c:forEach>
			  		</table>
		  		</div>
		  </div>

		  </div>
		  </div>
		  </div>
  </body>
</html>
