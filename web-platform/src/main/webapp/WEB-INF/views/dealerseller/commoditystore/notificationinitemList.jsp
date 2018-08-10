<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
    <%@taglib uri="/com/ccigmall/tag" prefix="song"  %>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>商品储存_入库通知</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/Storage.css">
	 <script src="${path}/commons/js/my97/WdatePicker.js"></script>
	<script type="text/javascript">
		var  CONTEXTPATH  = $("#conPath").val();

		function view(notifyId){
			var url = CONTEXTPATH+"/commoditystore/getNotificationInOrderView?notifyId=" + notifyId;
			window.open(url, "_blank");
		}

		function update(notifyId){
			window.location.href = CONTEXTPATH+"/commoditystore/getNotificationInOrderUpdate?notifyId=" + notifyId;
		}
	</script>
	
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
			
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp; </p>
					<p>商品储存&nbsp;&gt;&nbsp; </p>
					<p class="c1">入库通知</p>
				</div>
	        </div>
	        <div class="blank"></div> 
	        <div class="pu_wrap">
	        
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
							<td><span>${notificationOrder.notificationChar}</span></td>
							<td><span>${notificationOrder.serviceName}</span></td>

							<td><span><song:ContrastTag type="CY" objects="${transportWays }" value="${notificationOrder.shipperType}" /></span></td>
							<td><span>${notificationOrder.sendAddress}</span></td>
							<td><span>${notificationOrder.receiveName}</span></td>
							<td><span>${notificationOrder.receivePhone}</span></td>
							<td>
							 <span><fmt:formatDate value="${notificationOrder.createTime}" pattern="yyyy-MM-dd HH:mm"/></span>
							 </td>
							 <td><span>${notificationOrder.businessNumberChar}</span></td>
							 <td><span>
							 <c:if test="${notificationOrder.status == '10'}">
							 	<input type="button" id="viewNotify" name="viewNotify" value="查看" onclick="view('${notificationOrder.id}')">
							 </c:if>
							 <c:if test="${notificationOrder.status == '1'}">
							 	<input type="button" id="modifyNotify" name="modifyNotify" value="修改"  onclick="update('${notificationOrder.id}')">
							 </c:if>
							 
							 
							 </span></td>
								
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>




<%-- <c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
</c:if> --%>