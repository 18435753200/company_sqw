<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
        <%@taglib uri="/com/ccigmall/tag" prefix="song"  %>
<%@include file="/WEB-INF/views/include/base.jsp"%>
		  <div class="pu_wrap">
		  		<div class="pu_bd">
		  			<table>
		  				<tr class="order_hd">
		  					<th width="40px;">序号</th>
		  					<th width="50px;">选择</th>
		  					<th width="150px;">采购订单号</th>
		  					<th width="150px;">供应商名称</th>
		  					<th width="90px;">金额</th>
		  					<th width="90px;">紧急程度</th>
		  					<th width="90px;">订单日期</th>
		  					<th width="90px;">订单状态</th>
		  					<th width="90px;">制单人</th>
		  				</tr>
			  			<c:forEach items="${pb.result }" var="pChaseOrderVO" varStatus="status">
			  				<tr>
			  				<td >${status.index + 1 }</td>
			  				<td><input type="checkbox" name="purchaseOrderId" orderStatus="${pChaseOrderVO.orderStatus}" value="${pChaseOrderVO.id}" onclick="getNotificationOrder(this)"></td>
			  				<td><a href="javascript:notificationOrder('${pChaseOrderVO.id}')" >${pChaseOrderVO.businessNumberChar}</a></td>
			  				<td>${pChaseOrderVO.supplierName}</td>
			  				<td>${pChaseOrderVO.itemTotalPrice}</td>
			  				<td><song:ContrastTag type="JJ" objects="${degreeOfEmergency }" value="${pChaseOrderVO.emergency}" /></td>

			  				<td><fmt:formatDate value="${pChaseOrderVO.createTime}" pattern="yyyy-MM-dd"/></td>
			  				<td><song:OrderStatusTag type="CG" value="${pChaseOrderVO.orderStatus}"/></td>
			  				<td>${pChaseOrderVO.createBy}</td>
			  			</tr>
			  			</c:forEach>
			  			
			  		</table>
		  		</div>
		  </div>
<c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
</c:if>