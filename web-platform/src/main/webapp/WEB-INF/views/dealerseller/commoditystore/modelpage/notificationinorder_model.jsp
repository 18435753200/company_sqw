<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
	<div class="pu_bd">
	<table>
		<tr class="order_hd">
			<th width="40px;">序号</th>
			<th width="100px;">入库通知单号</th>
			<th width="100px;">服务商名称</th>
			<th width="70px;">承运方式</th>
			<th width="150px;">订单送货地址</th>
			<th width="70px;">联系人</th>
			<th width="80px;">联系电话</th>
			<th width="70px;">制单日期</th>
			<th width="100px;">采购订单号</th>
			<th width="60px;">操作</th>
		</tr>
	
	<c:forEach items="${notificationOrderList }" var="notificationOrder" varStatus="status">
		<tr class="order-bd">
				<td><span>${status.index + 1 }</span></td>
				<td><span>${notificationOrder.id}</span></td>
				<td><span>${notificationOrder.serviceName}</span></td>
				<td><span>${notificationOrder.shipperType}</span></td>
				<td><span>${notificationOrder.sendAddress}</span></td>
				<td><span>${notificationOrder.receiveName}</span></td>
				<td><span>${notificationOrder.receivePhone}</span></td>
				<td>
				 <span><fmt:formatDate value="${notificationOrder.createTime}" pattern="yyyy-MM-dd HH:mm"/></span>
				 </td>
				 <td><span>${notificationOrder.businessNumber}</span></td>
				 <td><span>
				 <c:if test="${notificationOrder.status == '1'}">
				 	<input type="button" id="checkNotify" name="checkNotify" value="确认通知" notificationId="${notificationOrder.id}" onclick="checkNotifyOrder(this)">
				 </c:if>
				 
				 
				 </span></td>
					
			</tr>
		</c:forEach>
</table>
</div>
<%-- <c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
</c:if> --%>