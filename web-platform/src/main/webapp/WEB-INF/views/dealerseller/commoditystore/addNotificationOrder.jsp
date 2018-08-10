<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="UTF-8">
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<title>商品储存_入库通知(表单)</title>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/Storage.css">
    <script src="${path}/commons/js/my97/WdatePicker.js"></script>
    <script type="text/javascript" src="${path}/commons/js/notification_in_add_fn.js"></script>
    
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
			<input type="hidden" id="emergencyCode" name="emergencyCode" value="${pChaseOrderVO.emergency }">
			<input type="hidden" id="emergencyValue" name="emergencyValue" value="${pChaseOrderVO.emergencyValue }">
			<input type="hidden" id="supplierCode" name="supplierCode" value="${pChaseOrderVO.supplierId }">
			<input type="hidden" id="supplierName" name="supplierName" value="${pChaseOrderVO.supplierName }">
			<%-- <input type="hidden" id="businessStatus" name="businessStatus" value="${pChaseOrderVO.businessStatus }"> --%>
			<input type="hidden" id="sendAddress" name="sendAddress">
			<input type="hidden" id="storeName" name="storeName">
			<input type="hidden" id="storeCode" name="storeCode">
			<input type="hidden" id="serviceCode" name="serviceCode" value="${pChaseOrderVO.serviceCode }">
			<input type="hidden" id="businessNumber" name="businessNumber" value="${pChaseOrderVO.id }">
			<input type="hidden" id="chkTrue" name="chkTrue">
			
			
			
			<div class="xia">
				
				<p class="p2">
					<div class="btn">
						<a href="javascript:void(0);"  onclick="saveNotificationOrder()">保存</a>
						
					</div>
				</p>
				
				
				<p class="p1">
					<span>入库通知单号:</span>
					<input type="text" id="notificationId" name="notificationId" value="${notificationId }" readonly="readonly">
					<span>制单日期:</span>
					<input type="text" id="createTime" name="createTime" value="<fmt:formatDate value="${pChaseOrderVO.createTime }" pattern="yyyy-MM-dd"/>" readonly="readonly">
					<span>采购订单号:</span>
					<input type="text" id="businessNumberChar" name="businessNumberChar" value="${pChaseOrderVO.businessNumberChar }"  readonly="readonly">
					
				</p>
				<p class="p1">
					<span><i class="red">*</i>预计送达日期:</span>
					<input type="text" id="estimateDate" name="estimateDate" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'estimateDate\',{d:-1});}',dateFmt:'yyyy-MM-dd'})">
					<span>服务商名称:</span>
					<input type="text" id="serviceName" name="serviceName" value="${pChaseOrderVO.serviceName}" readonly="readonly">
					<span>供应商编号:</span>
					<input type="text" id="supplierCode" name="supplierCode" value="${pChaseOrderVO.supplierId}" readonly="readonly">
				</p>
				<p class="p1">
					<span>承运方式:</span>
					<input type="hidden" id="shipperType" name="shipperType" value="${shippyTypeVO.dictValue}" readonly="readonly">
					<input type="text" id="shipperTypeName" name="shipperTypeName" value="${shippyTypeVO.dictName}" readonly="readonly">
					<span>送货地址:</span>
					<select id="sendAdd" onchange="clickSendAddress()">
						<option value="">请选择</option>
						<c:forEach items="${pChaseStoreList}" var="pChaseStore" varStatus="status">
							<c:choose>
								<c:when test="${status } == 0">
									<option value="${ pChaseStore.storeCode}" storeName="${ pChaseStore.storeName}" contact="${ pChaseStore.contact}" telephone="${ pChaseStore.telephone}" selected="selected">${pChaseStore.address}</option>
								</c:when>
								<c:otherwise>
									<option value="${ pChaseStore.storeCode}"  storeName="${ pChaseStore.storeName}"  contact="${ pChaseStore.contact}" telephone="${ pChaseStore.telephone}" >${pChaseStore.address}</option>
								</c:otherwise>
							</c:choose>
							
						</c:forEach>
					</select>
					<span>联系人:</span>
					<input type="text" id="receiveName" name="receiveName" readonly="readonly">
				</p>
				<p class="p1">
					<span>联系电话:</span>
					<input type="text" id="receivePhone" name="receivePhone" readonly="readonly">
				</p>
				
			</div>
			
			<div class="pu_wrap" id="modelPurchaseOrderItemList">

	        </div>
		</form>
			
	</div>
</body>
</html>