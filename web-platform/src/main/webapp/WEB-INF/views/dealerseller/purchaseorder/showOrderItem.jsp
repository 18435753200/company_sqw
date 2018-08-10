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
  		<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp; </p>
					<p>采购列表&nbsp;&gt;&nbsp; </p>
					<p class="c1">货物明细</p>
				</div>
			</div>
			
  		<div class="pu_w">
		  	<!-- 	<div class="pu_h"><h3>货物明细</h3></div> -->
		  		<div class="pu_b scroll-x"  style="margin-top:10px;">
			  		<table class="ov w2100">
			  			<tr class="order_hd">
			  				<th width="30px">序号</th>
			  				<th width="110px">采购订单号</th>
			  				<th width="110px">商品编码</th>
			  				<th width="110px">商品条码</th>
			  				<th width="110px">商品名称</th>

			  				<th width="110px">规格</th>
			  				<th width="40px">单位</th>
			  				<th width="40px">数量</th>
			  				<th width="100px">实际入库数量</th>
			  				<th width="80px">单价(含税)</th>

			  				<th width="50px">税率(%)</th>
			  				<th width="100px">不含税单价</th>
			  				<th width="100px">不含税总金额</th>
			  				<th width="50px">总金额</th>
			  				<th width="50px">币别</th>

			  				<th width="50px">汇率</th>
			  				<th width="80px">本币单价</th>
			  				<th width="80px">本币总金额</th>
			  				<th width="110px">本币不含税单价</th>
			  				<th width="110px">本币不含税金额</th>

			  				<th width="80px">分摊后单价</th>
			  				<th width="80px">分摊后总价</th>
			  			</tr>
			  			<c:forEach items="${pb.result }" var="pchaseOrderItem" varStatus="index">
			  			<tr>
			  				<td>${index.index+1 }</td>
			  				<td>${pchaseOrderItem.lastEditBy }</td>
			  				<td>${pchaseOrderItem.businessProdid}</td>
			  				<td>${pchaseOrderItem.barCode}</td>
			  				<td>${pchaseOrderItem.pname }</td>
			  				
			  				<td>${pchaseOrderItem.format }</td>
			  				<td>${pchaseOrderItem.unit}</td>
			  				<td>${pchaseOrderItem.qty}</td>
			  				<td>${pchaseOrderItem.qtyReceived }</td>
			  				<td>${pchaseOrderItem.skuPriceTax }</td>
			  				
			  				
			  				<td>${pchaseOrderItem.dutyRate}</td>
			  				<td>${pchaseOrderItem.skuPrice }</td>
			  				<td>${pchaseOrderItem.subtotalPrice }</td>
			  				<td>${pchaseOrderItem.totalPrice }</td>
			  				<td>${pchaseOrderItem.currencyType}</td>
			  				
			  				
			  				<td>${pchaseOrderItem.exchangeRate}</td>
			  				<td>${pchaseOrderItem.localPrice}</td>
			  				<td>${pchaseOrderItem.totalLocalPrice}</td>
			  				<td>${pchaseOrderItem.localNutaxPrice}</td>
			  				<td>${pchaseOrderItem.totalLocalNutaxPrice }</td>
			  				
			  				
			  				<td>${pchaseOrderItem.allocationPrice }</td>
			  				<td>${pchaseOrderItem.allocationPrice}</td>
			  			</tr>
			  			</c:forEach>
			  		</table>
			  		<%-- <c:if test="${!empty pb.result}">
					<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
					</c:if> --%>
		  		</div>
		  </div>
		</div>
		<script type="text/javascript">
	     $(".right .pu_b").css("height",$(window).height()-85+'px');
	  </script>
 </body>
</html>
