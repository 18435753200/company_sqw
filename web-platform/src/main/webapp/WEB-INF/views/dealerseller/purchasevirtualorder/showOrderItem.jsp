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
					<p style="width:130px;">特卖虚拟库存设置&nbsp;&gt;&nbsp; </p>
					<p class="c1">商品明细（界面）</p>
				</div>
			</div>
			
  		<div class="pu_w">
		  	<!-- 	<div class="pu_h"><h3>货物明细</h3></div> -->
		  		<div class="pu_b scroll-x"  style="margin-top:10px;">
			  		<table class="ov" style="width:840px;">
			  			<tr class="order_hd">
			  				<th width="120px">虚拟设置编号</th>
			  				<th width="120px">商品ID</th>
			  				<th width="150px">商品条码</th>
			  				<th width="150px">商品名称</th>
			  				<th width="110px">规格</th>
			  				<th width="40px">单位</th>
			  				<th width="40px">数量</th>
			  			</tr>
			  			<c:forEach items="${pb.result }" var="pchaseOrderItem" varStatus="index">
			  			<tr>
			  				<td>${pchaseOrderItem.lastEditBy }</td>
			  				<td>${pchaseOrderItem.businessProdid}</td>
			  				<td>${pchaseOrderItem.barCode}</td>
			  				<td>${pchaseOrderItem.pname }</td>
			  				<td>${pchaseOrderItem.format }</td>
			  				<td>${pchaseOrderItem.unit}</td>
			  				<td>${pchaseOrderItem.qty}</td>
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
