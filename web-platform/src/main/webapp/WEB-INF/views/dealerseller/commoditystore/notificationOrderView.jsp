<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="/com/ccigmall/tag" prefix="song"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="UTF-8">
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<title>商品储存_入库通知(表单)</title>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/Storage.css">
    <script src="${path}/commons/js/my97/WdatePicker.js"></script>
    <script type="text/javascript" src="${path}/commons/js/notification_in_item_view_fn.js"></script>
    
</head>
<body>
	<!-- 导航 start -->
	<%@include file="/WEB-INF/views/include/header.jsp"%>
		<div class="blank10"></div>
	<!-- 导航 end -->
	<div class="center">
	
	<div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		</div>
		
		<div class="right">
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp; </p>
					<p>商品储存&nbsp;&gt;&nbsp; </p>
					<p class="c1">入库通知(表单)</p>
				</div>
	        </div>
			<form id="notificationorder_fm" action="<%=path %>/commoditystore/addNotificationOrder" method="post">
			<input type="hidden" id="businessNumber" name="businessNumber" value="${notificationOrder.businessNumber }">
			<div class="xia">
				<p class="p1">
					<span>入库通知单号:</span>
					
					<input type="hidden" id="notificationId" name="notificationId" value="${notificationOrder.id }">
					<input type="text" id="notificationChar" name="notificationChar" value="${notificationOrder.notificationChar }" readonly="true">
					<span>制单日期:</span>
					<input type="text" id="createTime" name="createTime" value="<fmt:formatDate value="${notificationOrder.createTime }" pattern="yyyy-MM-dd"/>"  readonly="true">
					<span>采购订单号:</span>
					<input type="text" id="businessNumberChar" name="businessNumberChar" value="${notificationOrder.businessNumberChar }" readonly="true">
				</p>
				<p class="p1">
					<span>预计送达日期:</span>
					<input type="text" id="estimateDate" name="estimateDate" value="<fmt:formatDate value="${notificationOrder.estimateDate }" pattern="yyyy-MM-dd"/>"  readonly="true">
					<span>服务商名称:</span>
					<input type="text" id="serviceName" name="serviceName" value="${notificationOrder.serviceName}" readonly="true">
					<span>供应商编号:</span>
					<input type="text" id="supplierCode" name="supplierCode" value="${notificationOrder.supplierCode}" readonly="true">
				</p>
				<p class="p1">
					<span>承运方式:</span>
					<input value='<song:ContrastTag type="CY" objects="${transportWays }" value="${notificationOrder.shipperType}" />'/>
					<span>送货地址:</span>
					<input type="text" id="sendAdd" name="sendAdd" value="${notificationOrder.sendAddress}" readonly="true">
					<span>联系人:</span>
					<input type="text" id="receiveName" name="receiveName" value="${notificationOrder.receiveName}" readonly="true">
				</p>
				<p class="p1">
					<span>联系电话:</span>
					<input type="text" id="receivePhone" name="receivePhone" value="${notificationOrder.receivePhone}" readonly="true">
				</p>
				
			</div>
			
			<div class="pu_wrap" id="modelNotificationItemList">

	        </div>
		</form>
			
	</div>
</body>
</html>