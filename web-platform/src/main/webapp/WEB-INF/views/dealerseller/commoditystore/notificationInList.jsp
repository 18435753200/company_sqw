<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>商品储存_入库通知</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/Storage.css">
	 <script src="${path}/commons/js/my97/WdatePicker.js"></script>
	<script type="text/javascript" src="${path}/commons/js/notification_in_list.js"></script>
	
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
			<form id="notificationorder_fm">
			<input type="hidden" id="orderStatusValue" name="orderStatusValue" value="${orderStatusValue }">
			<input type="hidden" id="emergencyValue" name="emergencyValue" value="${emergencyValue }">
			<input type="hidden" id="businessTypeCode" name="businessTypeCode">
			<input type="hidden" id="supplyTypeCode" name="supplyTypeCode">
			<input type="hidden" id="warehouseCode" name="warehouseCode">
			<input type="hidden" id="warehouseNameText" name="warehouseNameText">
			<div class="xia">
				
					<p class="p1">
						<span>库房名称:</span>
						<select id="warehouseName" name="warehouseName">
							<option value="">请选择</option>
							<c:forEach items="${warehouseList }" var="warehouse">
								<option value="${warehouse.warehouseCode }">${warehouse.warehouseName }</option>
							</c:forEach>
						</select>
						<span>制单时间：</span> 
						<input type="text" name="firstDate" id="firstDate" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'lastDate\',{d:-1});}'})">
						<i>至</i>
						<input type="text" name="lastDate" id="lastDate" onClick="WdatePicker({minDate:'#F{$dp.$D(\'firstDate\',{d:1});}'})">
					</p>
					<p class="p1">
						<span class="st">制单人:</span>
						<input type="text" id="createBy" name="createBy">
						<span>采购订单号:</span>
						<input type="text" id="purchaseId" name="purchaseId">
						<span>金额:</span>
						<input type="text" id="itemTotalPrice" name="itemTotalPrice">
						
					</p>
					<p class="p1">
						<span>订单状态:</span>
							<select id="orderStatus" name="orderStatus">
									<option value="">请选择</option>
									<option value="1">创建</option>
									<option value="10">已完成</option>
									
							</select>
						<span>供应商名称:</span>
						<input type="text" id="supplierName" name="supplierName">
						<span>紧急程度:</span>
						<select id="emergency" name="emergency">
							<option value="">请选择</option>
							<c:forEach items="${emergencyEnum }" var="emergencyEnum">
								<option value="${emergencyEnum.dictValue }">${emergencyEnum.dictName }</option>
							</c:forEach>
						</select>
					</p>
					
					<p class="p1">
						<span>订单来源:</span>
						<select id="businessType" name="businessType">
							<option value="">请选择</option>
							<c:forEach items="${businessTypeEnum }" var="businessTypeEnum">
								<option value="${businessTypeEnum.dictValue }">${businessTypeEnum.dictName }</option>
							</c:forEach>
						</select>
						<span>入库类型:</span>
						<select id="supplyType" name="supplyType">
							<option value="">请选择</option>
							<c:forEach items="${supplyTypeEnum }" var="supplyTypeEnum">
								<option value="${supplyTypeEnum.dictValue }">${supplyTypeEnum.dictName }</option>
							</c:forEach>
						</select>
					</p>
	
					<p class="p2">
						<button type="button"  onclick="clickSubmit()">查询</button>
						<button type="button" onclick="addNotify()">新增通知单</button>
					</p>
				
			</div>
			
			<div class="pu_wrap" id="modelPurchaseList">
		  		
		  </div>
		</form>
		</div>
	</div>
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
</body>
</html>