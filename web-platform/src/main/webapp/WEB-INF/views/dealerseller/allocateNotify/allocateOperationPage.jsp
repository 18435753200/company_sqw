<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>库房商品明细</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/reset.css">
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/manual.css">
	
	<script src="${path}/commons/js/my97/WdatePicker.js"></script>
	<script type="text/javascript" src="${path}/commons/js/addAllocatePrdNotifyPage.js"></script>
	<script type="text/javascript" src="${path}/commons/js/allocatePrdBz_new.js"></script>	
</head>
<body>
	<!-- 导航 START -->
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 END -->
	
	<div class="center">
		<!-- 左侧菜单 START -->
		<div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		</div>
		<!-- 左侧菜单 END -->
		
		<div class="right">
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp; </p>
					<p>商品储存&nbsp;&gt;&nbsp; </p>
					<p class="c1">库房商品明细</p>
				</div>
	        </div>

		<form id="queryallocateOpePrd"  >
	        <div class="pu_hd">
				<h3>查询条件</h3>
				<input type="hidden" id="transferOutWarehouseCode" name="transferOutWarehouseCode" value="${transferOutWarehouseCode}" >
				<input type="hidden" id="isgenuine" name="isgenuine"> 
			</div>
			
			<div class="xia">
				<p class="p1">
					<span>库房名称：</span> 
					<input type="text" id="transferOutWarehouseName" name="transferOutWarehouseName" value="${transferOutWarehouseName}" readonly="readonly"> 
					
					<span>商品编码：</span> 
					<input type="text" id="pcode" name="pcode"  value="${pcode}"> 
					
					<span>商品条码：</span> 
					<input type="text" id="barCode" name="barCode" value="${barCode}">
				</p>
				
				<p class="p1">
					<span>商品名称：</span> 
					<input type="text" id="pname" name="pname"  value="${pname}">
					
					<span>商品状态：</span> 
					<select id="isgenuines" name="isgenuines" >
						<option value="1">正品</option>
						<!-- <option value="2">残品</option> -->
					</select> 
					
					<span>SKU_ID：</span> 
					<input type="text" id="skuId" name="skuId"  value="${skuId}">
				</p>
				
				<p class="p1">
					<span>仓库编码：</span> 
					<input type="text" id="barSkuId" name="barSkuId"  value="${barSkuId}">
				</p>
				
				<p class="p2">
					<button  type="button" id="select" name="select"  value="查询" onclick="clickSubmit()">查询</button>
				</p>
			</div>
		</form>
			
			<!-- DIV异步调用填充-->
			<div class="pu_bd" id="cbOpeAllocatePrdList" ></div>
			
			
<div class="pu_wrap" style="display:none" id="pud">
	<div class="btn">
		<a href="javascript:void(0)">当前库房：${transferOutWarehouseName}</a> <a
			href="javascript:void(0)" onclick="opetatorCompe()">确定</a>
	</div>

	<div class="pu_bd">
		<table id="sto">
			<tbody>
				<tr class="order_hd">
					<th width="40px;">序号</th>
					<th width="40px;">选择</th>
					<th width="100px;">商品编码</th>
					<th width="100px;">商品条码</th>
					<th width="80px;">商品名称</th>
					<th width="80px;">SKU_ID</th>
					<th width="80px;">仓库条码</th>
					<th width="60px;">规格</th>
					<th width="40px;">单位</th>
					<th width="90px;">可用库存数量</th>
					<th width="80px;" style="display:none">批次号</th>
					<th width="80px;" style="display:none">生产批号</th>
					<th width="70px;" style="display:none">生产日期</th>
					<th width="60px;">商品状态</th>
					<th width="60px;" style="display:none">skuId</th>
					<th width="60px;" >残品库存数量</th>
					<th width="60px;" style="display:none">单价</th>
				</tr>
			</tbody>
		</table>
	</div>
</div>
		</div>
	</div>
</body>
</html>