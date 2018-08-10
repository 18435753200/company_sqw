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
<script type="text/javascript" src="${path }/commons/js/order_js/warehouse_add.js"></script>
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
		<div class="cont">

		<div class="item">
			<label><i>*</i>仓库类型：</label>
			<div class="itemright">
				<select class="sm" name="warehouseType" id="warehouseTypeSelect">
					<option value="0">请选择</option>
				</select>
				<span class="error"></span>
			</div>
		</div>
		
		<div class="item">
			<label><i>*</i>仓库级别：</label>
			<div class="itemright">
				<select class="sm" name="warehouseLevelCode">
					<option value="0">请选择</option>
				</select>
				<span class="error"></span>
			</div>
		</div>
		
		<div class="item">
			<label><i>*</i>仓库状态：</label>
			<div class="itemright">
				<span class="r"><input type="radio" name="warehouseStatus" checked="checked" value="0">启用</span>
				<span class="r"><input type="radio" name="warehouseStatus" value="1">禁用</span>
			</div>
		</div>

		<div class="item" id="warehouseName1">
			<label><i>*</i>仓库名称：</label>
			<div class="itemright">
				<input type="text" class="it" name="warehouseName" id="warehouseName" placeholder="手工填写仓库名称"> 
				<span class="error"></span>
			</div>
		</div>
		
		<div class="item" id="dealerId">
			<label><i>*</i>经销商名称：</label>
			<div class="itemright">
			<select class="sm" name="dealerId">
				<option value="0">请选择</option>
			</select>
			<span class="error"></span>
			</div>
		</div>
		
		<div class="item" id="warehouseRemove">
			<label><i>*</i>上级仓库名称：</label>
			<div class="itemright">
			<select class="sm" name="parentCode">
				<option value="0">请选择</option>
			</select>
			<span class="error"></span>
			</div>
		</div>
		
		<div class="item" id="warehouseAddress">
			<label><i>*</i>仓库地址：</label>
			<div class="itemright">
			<select class="si" id="provinceId" name="provinceId"><option value="0">请选择</option></select> 
			<select class="si" id="cityId" name="cityId"><option value="0">请选择</option></select>
			<select class="si" id="arealId" name="areaId"><option value="0">请选择</option></select>
			<span class="error"></span>
			</div>
		</div>

		<div class="item">
			<label><i>*</i>详细地址：</label>
			<div class="itemright">
				<input type="text" class="it" id="address" name="address" placeholder="手工填写"> 
				<span class="error"></span>
			</div>
		</div>
		<div class="item">
			<label><i>*</i>联系人：</label>
			<div class="itemright">
				<input type="text" class="it" name="contact" id="contact" placeholder="手工填写"> 
				<span class="error"></span>
			</div>
		</div>

		<div class="item">
			<label><i>*</i>电话：</label>
			<div class="itemright">
				<input type="text" class="it" id="telephone" name="telephone" placeholder="手工填写"> 
				<span class="error"></span>
			</div>
		</div>
		
		<div class="item" id="addAreaSelect">
			<label><i>*</i>发货渠道：</label>
			<div class="itemright">
			<select class="sm" name="channelCode"><option value="0">请选择</option></select>
			<span class="error"></span>
			</div>
		</div>
         </div>
         </form>
		</div>
	</div>
	<%@include file="/WEB-INF/views/include/foot.jsp" %>
</body>

</html>
