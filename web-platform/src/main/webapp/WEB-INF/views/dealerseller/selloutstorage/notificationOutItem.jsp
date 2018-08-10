<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>通知出库</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/Sales.css">
	 <script src="${path}/commons/js/my97/WdatePicker.js"></script>
	<script type="text/javascript" src="${path}/commons/js/notification_out_list.js"></script>
	
</head>
<body>
	<!-- 导航 start -->
	<%@include file="/WEB-INF/views/include/header.jsp"%>
		<div class="blank10"></div>
	<!-- 导航 end -->
    <div class="blank"></div> 
	<div class="center">
		<div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		</div>
		<div class="right">
		<div class="pu_wrap">
	        	<div class="pu_hd"><h3>待出库商品列表</h3></div>
	        	<div class="pu_bd">
	        		<table>
	        			<tr class="order_hd">
	        				<th width="40px;">序号</th>
		        			<th width="120px;">出库通知单编号</th>
		        			<th width="110px;">商品条码</th>
		        			<th width="110px;">商品编号</th>
		        			<th width="110px;">商品PID</th>
		        			<th width="130px;">商品名称</th>
		        			<th width="100px;">规格</th>
		        			<th width="40px;">单位</th>
		        			<th width="40px;">数量</th>
		        			<th width="80px;">已出库数量</th>
		        			<th width="70px;">未出库数量</th>
		        			<th width="70px;">WMS业务单据号</th>		
		        		</tr>
		        		<c:forEach items="${notificationOutItemList }" var="notificationOutItem" varStatus="status">
		        			<tr>
							<td>${status.index+1 }</td>
							<td>${notificationOutItem.batchId }</td>
							<td>${notificationOutItem.barCode }</td>
							<td>${notificationOutItem.pcode }</td>
							<td>${notificationOutItem.pid }</td>
							<td>${notificationOutItem.pname }</td>
							<td>${notificationOutItem.format }</td>
							<td>${notificationOutItem.unit }</td>
							<td>${notificationOutItem.outNotificationQty }</td>
							<td>${notificationOutItem.alreadyQty }</td>
							<td>${notificationOutItem.outNotificationQty-notificationOutItem.alreadyQty }</td>
							<td>${notificationOutItem.notificationId }</td>
						</tr>
		        		</c:forEach>
						
	        		</table>
	        	</div>
	        </div>
		</div>
	</div>
</body>
</html>