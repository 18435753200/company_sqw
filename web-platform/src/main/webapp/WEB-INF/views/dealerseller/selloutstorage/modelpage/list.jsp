<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
	<!-- <div class="pu_bd" id="outStockList_model"> -->
      		<table>
      			<tr class="order_hd">
      			<th width="40px;">序号</th>
       			<th width="40px;">选择</th>
       			<th width="120px;">出库通知单编号</th>
       			<th width="120px;">订单编号</th>
       			<th width="120px;">第三方订单号</th>
       			<th width="110px;">交易时间</th>
       			<th width="100px;">订单来源</th>
       			<th width="150px;">订单留言</th>
       			<th width="50px;">状态</th>
       			<th width="100px;">销售总价(￥)</th>
       			<!-- <th width="80px;">操作</th>	   -->      		
       		</tr>
       		<c:forEach items="${pb.result }" var="notificationOutOrder" varStatus="stauts">
       			<tr  >
					<td>${stauts.index + 1 }</td>
					<td><input type="checkbox" id="notifyId" name="notifyId" value="${notificationOutOrder.sid }" ></td>
					<td><a href="<%=path %>/selloutstorage/getNotiticationOutItemList?notificationId=${notificationOutOrder.sid }" target="_blank">${notificationOutOrder.notificationOutChar }</a></td>
					<td>${notificationOutOrder.orderNumber }</td>
					<td><c:if test="${notificationOutOrder.thirdOrderId == 'undefined'}"></c:if>
					<c:if test="${notificationOutOrder.thirdOrderId != 'undefined'}">${notificationOutOrder.thirdOrderId }</c:if>
					</td>
					<td><fmt:formatDate value="${notificationOutOrder.outTime }" pattern="yyyy-MM-dd HH:mm"/></td>
					<td><c:if test="${notificationOutOrder.businessType=='201' }">B2B商品销售</c:if><c:if test="${notificationOutOrder.businessType=='202' }">B2C商品销售</c:if><c:if test="${notificationOutOrder.businessType=='203' }">换货单</c:if><c:if test="${notificationOutOrder.businessType=='205' }">调拨单</c:if><c:if test="${notificationOutOrder.businessType=='209' }">申请出库单</c:if></td>
					<td>${notificationOutOrder.orderComment }</td>
					<td><c:if test="${notificationOutOrder.status =='10' }"><img src="../commons/images/flag_yellow.png" style="width:25px; height:25px;"></c:if><c:if test="${notificationOutOrder.status =='1' }">待出库</c:if><c:if test="${notificationOutOrder.status =='2' }">已打包</c:if><c:if test="${notificationOutOrder.status =='100' }">已出库</c:if></td>
					<td><fmt:formatNumber value="${notificationOutOrder.totalPrice }" pattern="0.00"/></td>
					<!-- <td><input type="button" id="checkOperation" name="checkOperation" onclick="checkNotiticationOutOrder('${notificationOutOrder.outNotificationNumber}')" value="审核"></td> -->
				</tr>
       		</c:forEach>
			
      		</table>
      	<!-- </div> -->
<c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
</c:if>