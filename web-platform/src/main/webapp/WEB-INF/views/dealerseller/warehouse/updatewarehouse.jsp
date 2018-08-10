<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>库房设置</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/currenry.css"> 
</head>
<script type="text/javascript" src="${path }/commons/js/order_js/warehouse_update.js"></script>
<script type="text/javascript" src="${path }/commons/js/order_js/province_and_city.js"></script>
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
					<p>基础设置&nbsp;&gt;&nbsp; </p>
					<p class="c1">库房设置</p>
				</div>
	       </div>

		  <div class="btn">
	     	<a href="javascript:void(0)" onclick="gosumitWarehouse()">保存</a>
	     	<a href="../warehouse/getWarehouse">放弃</a>
	     </div>
		<form action="" id="warehouseForm">
		<input type="hidden" name="id" value="${warehouse.id }"> 
		<input type="hidden" name="warehouseCode" value="${warehouse.warehouseCode }"> 
		<div class="cont">

		<div class="item">
			<label><i>*</i>仓库类型：</label>
			<div class="itemright">
			
				<select class="sm" name="warehouseType">
					<option value="0">请选择</option>
					<c:forEach items="${warehouseStoretypes}" var="storetypes">
						<option value="${storetypes.typeCode }">${storetypes.typeName }</option>
					</c:forEach>	
				</select>
				<span class="error"></span>
			</div>
		</div>
		<input type="hidden" value="${warehouse.warehouseType}" id="warehouseTypeInput">
		<div class="item">
			<label><i>*</i>仓库级别：</label>
			<div class="itemright">
				<select class="sm" name="warehouseLevelCode">
					<option value="0">请选择</option>
					<c:forEach items="${warehouseStorelevels}" var="storelevels">
						<option value="${storelevels.levelCode }">${storelevels.levelName }</option>
					</c:forEach>
				</select>
				<span class="error"></span>
				<input type="hidden" value="${warehouse.warehouseLevelCode}" id="warehouseLevelCode">
				
			</div>
		</div>
		
		<div class="item"> 
			<label><i>*</i>仓库状态：</label>
			<div class="itemright">
				<span>
				<c:if test="${warehouse.warehouseStatus==0 }">
					<input type="radio" name="warehouseStatus" checked="checked" value="0">启用</span>
					<span><input type="radio" name="warehouseStatus" value="1">禁用</span>
				</c:if>
				
				<c:if test="${warehouse.warehouseStatus==1 }">
					<input type="radio" name="warehouseStatus" value="0">启用</span>
					<span><input type="radio" name="warehouseStatus" checked="checked" value="1">禁用</span>
				</c:if>
			</div>
		</div>

		<div class="item" id="warehouseName1">
		<label><i>*</i>仓库名称：</label>
			<div class="itemright">
				<input type="text" class="it" name="warehouseName" value="${warehouse.warehouseName }"> 
				<span class="error"></span>
			</div>
		</div>
		<div class="item" id="dealerId">
			<label><i>*</i>经销商名称：</label>
			<div class="itemright">
			<input type="hidden" value="${warehouse.dealerId}" id="dealerIdInput"> 
			<select class="sm" name="dealerId">
			<option value="0">请选择</option>
			<c:forEach items="${dealers}" var="dealers">
				<option value="${dealers.dealerId }">${dealers.companyName }</option>
			</c:forEach>
			</select>
			<span class="error"></span>
			</div>
		</div>
		
		<c:if test="${warehouse.warehouseLevelCode ne 1}">
			<div class="item" id="warehouseRemove">
			<label><i>*</i>上级仓库名称：</label>
			<div class="itemright">
			<select class="sm" name="parentCode">
				<option value="0">请选择</option>
				<c:forEach items="${skuWarehourseList}" var="parents">
						<option value="${parents.warehouseCode }">${parents.warehouseName }</option>
				</c:forEach>
			</select>
			<span class="error"></span>
			<input type="hidden" value="${warehouse.parentCode }" id="parentCode"> 
			
			</div>
			</div>
		</c:if>
		
		<div class="item">
			<label><i>*</i>仓库地址：</label>
			<div class="itemright">
			<select class="si" id="provinceId" name="provinceId">
				<option value="0">请选择</option>
				<c:forEach items="${provinces}" var="province">
						<option value="${province.provinceid }">${province.provincename }</option>
				</c:forEach>
			
			</select> 
			<input type="hidden" value="${warehouse.provinceId }" id="provinceId2">
			<select class="si" id="cityId" name="cityId">
				<option value="0">请选择</option>
				<c:forEach items="${citys}" var="city">
						<option value="${city.cityid }">${city.cityname }</option>
				</c:forEach>
			</select>
			<input type="hidden" value="${warehouse.cityId }" id="cityId2">
			<select class="si" id="arealId" name="areaId">
			<option value="0">请选择</option>
				<c:forEach items="${countys}" var="county">
						<option value="${county.countyid }">${county.countyname }</option>
				</c:forEach>
			</select>
			<span class="error"></span>
			<input type="hidden" value="${warehouse.areaId }" id="areaId2">
			
			</div>
		</div>

		<div class="item">
		<label><i>*</i>详细地址：</label>
			<div class="itemright">
				<input type="text" id="address"  class="it" name="address" value="${warehouse.address }"> 
				<span class="error"></span>
			</div>
		</div>
		<div class="item">
		<label><i>*</i>联系人：</label>
			<div class="itemright">
				<input type="text" class="it" name="contact" id="contact" value="${warehouse.contact }"> 
				<span class="error"></span>
			</div>
		</div>

		<div class="item">
		<label><i>*</i>电话：</label>
			<div class="itemright">
				<input type="text" class="it" id="telephone" name="telephone" value="${warehouse.telephone }"> 
				<span class="error"></span>
			</div>
		</div>
		
		<div class="item" id="addAreaSelect">
			<label><i>*</i>发货渠道：</label>
			<div class="itemright">
			<select class="sm" name="channelCode">
				<option value="0">请选择</option>
				<c:forEach items="${warehouseChannel}" var="channel">
					<option value="${channel.channelCode }">${channel.channelName }</option>
				</c:forEach>
			</select>
			<span class="error"></span>
			<input type="hidden" value="${warehouse.channelCode}" id="channelCode">
			
			</div>
		</div>
		
		
		<c:if test="${!empty warehouse.skuStockWarehouseAreaList}">
			<div class="item" id="select1">
				<label><i>*</i>负责区域：</label>
				<div class="itemright">
				     <a href="javascript:void(0)" class="addSelect">添加</a>
				     <a href="javascript:void(0)" class="deleteSelect">删除</a>
		        </div>
			</div>
			<div class='item' id="parent">
			<c:forEach items="${warehouse.skuStockWarehouseAreaList}" var="areaList">
			
				<div class="item" id="select">
					<label><input type="checkbox" class="checkbox"></label>
					<div class="itemright">
						<input type="hidden"  id="provinceIdInput" class="provinceIdInput" value="${areaList.provinceId }">
					    <select class="si" id="provinceId1" name="provinceId1">
					    <option value="0">请选择</option>
						<c:forEach items="${provinces}" var="province">
								<option value="${province.provinceid }">${province.provincename }</option>
						</c:forEach>
					    </select>
					
						<input type="hidden"  id="cityIdInput" class="cityIdInput" value="${areaList.cityId }">
					   	<select class="si" id="cityId1" name="cityId1">
						   <option value="0">请选择</option>
					   	</select>
					   	<input type="hidden" id="arealIdInput" class="arealIdInput" value="${areaList.districtId }">
					   	<select class="si" id="arealId1" name="arealId1">
					   		<option value="0">请选择</option>
					   	</select>
					</div>
				</div>
			
			</c:forEach>
			</div>
		</c:if>
       </div>
       </form>
	</div>
	</div>
	<%@include file="/WEB-INF/views/include/foot.jsp" %>
</body>
</html>
