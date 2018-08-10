<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>查询商品调拨界面</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/reset.css">
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/transfer.css">
	
	<script src="${path}/commons/js/my97/WdatePicker.js"></script>
	<script type="text/javascript" src="${path}/commons/js/allocateNotifyEdit.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
		clickSubmit();
	});
	</script>
</head>
<body>
	<!-- 导航 START -->
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 END -->

	<div class="blank"></div>
	
	<div class="center">
		<!-- 左侧菜单 START -->
		<div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		</div>
		<!-- 左侧菜单 END -->
		
		<div class="right">
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp;</p>
					<p>商品储存&nbsp;&gt;&nbsp;</p>
					<p class="c1">查询商品调拨界面</p>
				</div>
			</div>

		<form id="queryallocateProductInfo"  >
			<div class="pu_hd">
				<h3>查询条件</h3>
					<input type="hidden" id="transferOutWarehouseName" name="transferOutWarehouseName">
					<input type="hidden" id="transferInWarehouseName" name="transferInWarehouseName">
					<input type="hidden" id="transferOutWarehouseCode" name="transferOutWarehouseCode">
					<input type="hidden" id="transferInWarehouseCode" name="transferInWarehouseCode">
					<input type="hidden" id="status" name="status">
			</div>
			<div class="xia">
				<p class="p1">
					<span>调拨单编号:</span> 
					<input type="text" id="transferNoChar" name="transferNoChar">
					<span>调拨时间:</span>
					<input type="text" name="firstDate" id="firstDate" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'lastDate\',{d:-1});}',dateFmt:'yyyy-MM-dd'})">
						<i>至</i>
					<input type="text" name="lastDate" id="lastDate" onClick="WdatePicker({minDate:'#F{$dp.$D(\'firstDate\',{d:1});}',dateFmt:'yyyy-MM-dd'})">
				</p>

				<p class="p1">
					<span>调出库房:</span> 
					<select id="transferOutWarehouseCodes" name="transferOutWarehouseCodes">
						<option value="">请选择</option>
						<c:forEach items="${warehouseList }" var="warehouse">
							<option value="${warehouse.warehouseCode }">${warehouse.warehouseName}</option>
						</c:forEach>
					</select> 
					<span>调入库房:</span> 
					<select id="transferInWarehouseCodes" name="transferInWarehouseCodes">
						<option value="">请选择</option>
						<c:forEach items="${warehouseList}" var="warehouse">
							<option value="${warehouse.warehouseCode }">${warehouse.warehouseName}</option>
						</c:forEach>
					</select> 
					<span>调拨单状态:</span> 
					<select id="statusCode" name="statusCode">
						<option value="">请选择</option>
						<option value="1">未审核</option>
						<option value="10">审核</option>
						<option value="15">完成调拨</option>
						<option value="20">休眠</option>
					</select>
				</p>
				<p class="p2">
					<button  type="button" id="selectPrd" name="selectPrd"  value="查询" onclick="clickSubmit()">查询</button>
				</p>
			</div>
		</form>
		
		<!-- DIV异步调用填充-->
		<div class="pu_bd" id="cbAllocatePrdList" >
	
		</div>
	</div>
</body>
</html>