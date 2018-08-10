<%@page import="java.math.BigDecimal"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
		  <!-- <div class="pu_wrap" id="modelPurchaseOrderItemList"> -->
		  		<div class="pu_bd">
		  			<table  id="orderItem">
		  				<tr class="order_hd">
		  					<th width="40px;">序号</th>
		  					<th width="130px;">商品编码</th>
		  					<th width="110px;">商品条码</th>
		  					<th width="150px;">商品名称</th>
		  					<th width="100px;">规格</th>
		  					<th width="90px;">单位</th>
		  					<th width="100px;">订单数量</th>
		  					<th width="110px;">本次通知送货数量</th>
		  					
		  				</tr>
		  				<c:forEach items="${pb.result }" var="notificationItem" varStatus="status">
		  					<tr class="sot">
			  					<td>${status.index + 1 }</td>
				  				<td>${notificationItem.pcode }</td>
				  				<td>${notificationItem.barCode } </td>
				  				<td>${notificationItem.skuNameCn } </td>
				  				<td>${notificationItem.format } </td>
				  				<td>${notificationItem.unit } </td>
				  				<td>${notificationItem.qty } </td>
				  				<td><span style="color: red">*</span><input style="width:110px" type="text" id="notifySendQty" name="notifySendQty" value="${notificationItem.qtyReceived }" onblur="checkReceivedQty('${notificationItem.skuId }','${notificationItem.qty }',this)"/>
				  					<input type="hidden" id="qty" name="qty" value="${notificationItem.qty }">
				  					<input type="hidden" id="itemId" name="itemId" value="${notificationItem.id }">
				  					<input type="hidden" id="skuId" name="skuId" value="${notificationItem.skuId } ">
				  					
				  				</td>
				  				
				  				
			  				</tr>
		  				</c:forEach>
		  			</table>
		  		</div>
		 <!--  </div> -->
<c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
</c:if>