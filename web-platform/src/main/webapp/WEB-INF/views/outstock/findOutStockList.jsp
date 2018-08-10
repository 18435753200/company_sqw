<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/Storage.css">
</head>
<body>
<div class="pu_bd">
	        		<table>
	        			<tr class="order_hd">
	        				<th width="40px;">序号</th>
		        			<th width="40px;">选择</th>
		        			<th width="65px;">出库通知<br>单状态</th>
		        			<th width="55px;">出库单<br>状态</th>
		        			<th width="70px;">出库单<br>编号</th>
		        			<th width="70px;">出库通知<br>单编号</th>
		        			<th width="80px;">收件人</th>
		        			<th width="150px;">收件地址</th>
		        			<th width="80px;">电话</th>
		        			<th width="110px;">发货要求<br>(订单留言)</th>	 
		        			<th width="70px;">订单编号</th>	       	
		        		</tr>
		        		<c:forEach items="${pb.result }" var="result" varStatus="status">
						<tr>
							<td>${status.index+1 }</td>
							<td><input type="checkbox" name="outStockBox" class="sm" value="${result.sid}"></td>
							<td>
							<c:choose>
   <c:when test="${result.notificationStatus==100}">  
     <img src="<%=path%>/commons/images/flag_yellow.png" style="width:25px; height:25px;">
   </c:when>
   <c:otherwise> 
    <img src="<%=path%>/commons/images/flag_red.png" style="width:25px; height:25px;">
   </c:otherwise>
  </c:choose>
							
							</td>
							<td>
							<c:choose>
   <c:when test="${result.status==10}">  
     <img src="<%=path%>/commons/images/flag_yellow.png" style="width:25px; height:25px;">
   </c:when>
   <c:otherwise> 
    <img src="<%=path%>/commons/images/flag_red.png" style="width:25px; height:25px;">
   </c:otherwise>
  </c:choose>
							</td>
							<td><a href="<%=path%>/outstock/findOutStockItemList?id=${result.sid }">${result.stockOutChar }</a></td>
							<td>${result.notificationIdChar }</td>
							<td>${result.receiveName }</td>
							<td>${result.receiveAddress }</td>
							<td>${result.receivePhone }</td>
							<td>${result.orderComment }</td>
							<td>${result.orderId }</td>
						</tr>
						</c:forEach>
	        		</table>
	        		<c:if test="${!empty pb.result}">
					<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
				</c:if>
	        	</div>
</body>
</html>