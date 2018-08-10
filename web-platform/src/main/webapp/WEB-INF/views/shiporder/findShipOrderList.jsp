<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="/com/ccigmall/tag" prefix="song"  %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/Storage.css">
</head>
<body>
<div class="pu_wrap">
		  		<div class="pu_bd">
		  			<table>
		  				<tr class="order_hd">
		  					<th width="40px;">序号</th>
		  					<th width="50px;">选择</th>
		  					<th width="110px;">采购订单号</th>
		  					<th width="110px;">入库通知单号</th>
		  					<th width="150px;">供应商名称</th>
		  					<th width="100px;">金额</th>
		  					<th width="100px;">紧急程度</th>
		  					<th width="90px;">订单日期</th>
		  					<th width="100px;">订单状态</th>
		  					<th width="100px;">制单人</th>
		  				</tr>
		  				
		  				<c:forEach items="${pb.result }" var="ChaseOrder" varStatus="status">
		  				<tr>
		  					<td>${status.index+1}</td>
			  				<td><input type="checkbox" name="ShipOrderNo" value="${ChaseOrder.businessNumber },${ChaseOrder.id},${ChaseOrder.serviceName}"></td>
			  				<td><a href="<%=path%>/shiporder/findShipOrderItem?id=${ChaseOrder.businessNumber}" target="view_window">${ChaseOrder.businessNumberChar}</a></td>
			  				<td>${ChaseOrder.notificationChar}</td>
			  				<td>${ChaseOrder.supplierName}</td>
			  				<td>${ChaseOrder.totalPrice}</td>
			  				<td><song:ContrastTag type="JJ" objects="${degreeOfEmergency }" value="${ChaseOrder.emergencyCode}" /></td>
			  				<td><fmt:formatDate value="${ChaseOrder.createTime}"  pattern="yyyy-MM-dd"/></td>
			  				<td>
			  				<c:choose>
   <c:when test="${ChaseOrder.status==10}">  
     <img src="<%=path%>/commons/images/flag_yellow.png" style="width:25px; height:25px;">
   </c:when>
   <c:otherwise> 
    <img src="<%=path%>/commons/images/flag_red.png" style="width:25px; height:25px;">
   </c:otherwise>
  </c:choose>
			  				</td>
			  				<td>${ChaseOrder.createBy}</td>
		  				</tr>
		  			</c:forEach>
		  			</table>
		  			<c:if test="${!empty pb.result}">
					<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
				</c:if>
		  		</div>

	        </div>
</body>
</html>