<%@page import="java.math.BigDecimal"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<div class="pu_hd"><h3>待出库商品列表</h3></div>
	<div class="pu_bd">
	<table>
	 		<tr class="order_hd">
	 				<th width="40px;">序号</th>
	  			<th width="100px;">出库通知单编号</th>
	  			<th width="100px;">商品ID</th>
	  			<th width="100px;">商品编号</th>
	  			<th width="140px;">商品名称</th>
	  			<th width="100px;">规格</th>
	  			<th width="50px;">单位</th>
	  			<th width="50px;">数量</th>
	  			<th width="80px;">已出库数量</th>
	  			<th width="80px;">未出库数量</th>	
	  		</tr>
	  		<c:forEach items="${notificationOutItemList }" var="notificationOutItem" varStatus="status">
	   			<tr>
					<td>${status.index+1 }</td>
					<td>${notificationOutItem.notificationId }</td>
					<td>${notificationOutItem.pid }</td>
					<td>${notificationOutItem.pcode }</td>
					<td>${notificationOutItem.pname }</td>
					<td>${notificationOutItem.format }</td>
					<td>${notificationOutItem.unit }</td>
					<td>${notificationOutItem.outNotificationQty }</td>
					<td>${notificationOutItem.alreadyQty }</td>
					<td>${notificationOutItem.outNotificationQty - notificationOutItem.alreadyQty}</td>
				</tr>
	    	</c:forEach>
	 </table>
</div>
<%-- <c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
</c:if> --%>