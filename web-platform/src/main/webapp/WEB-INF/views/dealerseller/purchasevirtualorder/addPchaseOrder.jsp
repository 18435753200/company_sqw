<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="UTF-8">
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<title>特卖商品采购_特卖采购订单(表单)</title>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/purchaseOrder.css">
    <script src="${path}/commons/js/my97/WdatePicker.js"></script>
    <script src="${path}/commons/js/purchasevirtualorder_js/purchasevirtual_add.js"></script>
    <script src="${path}/commons/js/purchasevirtualorder_js/purchasevirtual_check.js"></script>
    
</head>

<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="center">

		<div class="right">

			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp; </p>
					<p style="width:130px;">特卖虚拟库存设置&nbsp;&gt;&nbsp; </p>
					<p class="c1">虚拟库存设置(界面)</p>
				</div>
	        </div>
			
			<div class="btn">
		     	<a href="javascript:void(0)" onclick="return gosumitPurchase('save')">保存</a>
		     	<%-- <a href="<%=path%>/pchaseOrder/confirmOrder?id=${pChaseOrderVO.id}">审核</a> --%>
	        </div>
		<form action="" id="purchaseSumit">
			<div class="xia">
				<p class="tit">基本信息</p>
				<p class="p1">
				<span>虚拟设置编号:</span>
					<input type="text" id="purchaseCode1" name="purchaseCode1"  readonly="readonly"   style="background:#d0cdcd;">
					<input type="hidden" id="purchaseCode" name="purchaseCode"  readonly="readonly">
					<input type="hidden" id="id" name="id" value="${ChaseOrderId }" readonly="readonly">
					
					<span><i class="red">*</i>库存类型:</span>
					<select name="purchaseType" id="purchaseType">
					 <c:forEach items="${virtualOrderType}" var="item">
					   <option value="${item.code}">${item.name}</option>
					  </c:forEach>
					</select>
					<input type="hidden" id="purchaseTypeName" name="purchaseTypeName"/>
					
					<span><i class="red">*</i>商品类型:</span>
					<select name="productType" id="productType">
						<option value=''>请选择</option>
						<c:forEach items="${productType}" var="productType">
							<option value="${productType.dictRemark}">${productType.dictName}</option>
					    </c:forEach>
					</select>
					
					<input type="hidden" id="createTime" name="createTime" value="${sysdate}" readonly="readonly" > 
					<input type="hidden" id="purChaseMan" name="createBy" value="${name }" readonly="readonly">
					
				</p>
				
				<p class="tit">供应商信息</p>
				<p class="p1">
					<span><i class="red">*</i>供应商名称:</span>
					<input type="text" id="supplierName" name="supplierName" onclick="showSupplier()" readonly="readonly">
					<span>供应商编号:</span>
					<input type="text" id="supplierId" name="supplierId" readonly="readonly"   style="background:#d0cdcd">
					<span>联系人:</span>
					<input type="text" id="supplierBy" name="supplierBy" readonly="readonly" style="background:#d0cdcd">
				</p>
				<p class="p1">
					<span>联系电话:</span>
					<input type="text" id="supplierPhone" name="supplierPhone" readonly="readonly" style="background:#d0cdcd">
				</p>
		   </div>
			 
		  <!--采购明细开始-->
		  <div class="pu_w">
		  		<div class="pu_h"><h3>商品明细</h3></div>
		  		<div class="btn"><button type="button" id ="addProduct">添加</button>
		  		<button type="button" id ="deleteTr">删除</button>
		  		<input type="hidden" id="chkTrue" name="chkTrue">
		  		</div>
		  		<div class="pu_b scroll-x">
			  		<table class="ov" id="productTab">
			  			<tr class="order_hd">
			  				<th width="60px" nowrap="nowrap">序号</th>
			  				<th width="60px" nowrap="nowrap">选择</th>
			  				<th>商品ID</th>
			  				<th>商品条码</th>
			  				<th>商品名称</th>
			  				<th>规格</th>
			  				<th>单位</th>
			  				<th>库存</th>
			  			</tr>
			  			<tr class="append">
			  				<td nowrap="nowrap">1</td>
			  				<td nowrap="nowrap"><input type="checkbox" class="checkbox sm"></td>
			  				<td><input type="text" id="skuId"  style="width:130px;">
			  				<input type="hidden" id="skuId" name="skuId">
			  				<input type="hidden" id="productId" name="businessProdid">
			  				</td>
			  				<td><input type="text" id="barCode" name="barCode" readonly="readonly" style="width:140px;"></td>
			  				<td><input type="text" id="pName" name="pName" readonly="readonly" style="width:130px;"></td>
			  				<td><input type="text" id="format" name="format" readonly="readonly" style="width:100px;"></td>

			  				<td><input type="text" id="unit" name="unit" readonly="readonly"></td>
			  				
			  				<td><input type="text" id="qty" name="qty"></td>
			  			</tr>
			  		</table>
		  		</div>
		  </div>
		  <!--采购明细结束-->

		</form>
		
		
		<div class="alert_bu" id="serviceDiv" style="display: none;";>
		  <div class="bg"></div>
			<div class="wrap">
				<div class="pic"></div>
				<div class="box1">
					<div id="boxtitle">
						<h2>选择服务商.</h2>
					</div>
					<div id="suppliernametext" style="display: block;">
						<input type="text" id="serName">
						<button type="button" onclick="selService();">查询</button>
						<button type="button" onclick="hideShow();">退出</button>
					</div>				
				</div>
				<div class="box2" id="servicebox">	
				</div>
				<div class="box3">
						<button type="button" class="bt1" id="supplierclick" style="" onclick="loadPurchasePage()">确定</button>
				</div>
			</div>
		</div>
		
		
		<div class="alert_bu" id="supplyDiv" style="display: none;">
		  <div class="bg"></div>
			<div class="wrap">
				<div class="pic"></div>
				<div class="box1">
					<div id="boxtitle">
						<h2>选择供应商.</h2>
					</div>
					<div id="suppliernametext" style="display: block;" >
						<input type="text" id="suppilerName1" value="">
						<button type="button" onclick="showSupplierByName();">查询</button>
						<button type="button" onclick="hideShow();">退出</button>
					</div>
				</div>
				<div class="box2" id="supplierBox">
					
				</div>
				<div class="box3">
					<button type="button" class="bt1" id="supplierclick" onclick="loadSupplier()">确定</button>
				</div>
			</div>
		</div>
		
		
		<div class="alert_bu" id="wareHouseDiv" style="display: none;">
		  <div class="bg"></div>
			<div class="wrap">
				<div class="pic"></div>
				<div class="box1">
					<div id="boxtitle">
						<h2>选择仓库.</h2>	
					</div>				
					<div id="suppliernametext" style="display: block;">
<!-- 						<input type="text" id="WareHouseName"> -->
<!-- 						<button type="button" onclick="wareHouseByWareHouseName();">查询</button> -->
<!-- 						<button type="button" onclick="hideShow();">退出</button> -->
					</div>
				</div>
				<div class="box2" id="wareHousebox">
					
				</div>
				<div class="box3">
						<button type="button" class="bt1" id="supplierclick" onclick="loadWareHouse()">确定</button>
						<button type="button" class="bt1" style="margin-left:10px;" onclick="hideShow();">退出</button>
				</div>
			</div>
		</div>
		
		
		<div class="alert_c" id="skuDiv" style="display:none;">
		  <div class="bg"></div>
			<div class="wrap">
				<div class="pic"></div>
				<div class="box1">
					<div id="boxtitle">
						<h2>选择商品.</h2>
					</div>
					<div id="suppliernametext" style="display: block;">
						<input type="text" id="productName">
						<button type="button" onclick="searchSku();">查询</button>
						<button type="button" onclick="hideShow();">退出</button>
					</div>
				</div>
				<div class="box2" id="skubox">
				
				</div>
				<div class="box3">
					<button type="button" class="bt1" id="supplierclick" onclick="loadSku()">确定</button>
				</div>
			</div>
		</div>
		</div>
		</div>
	<!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
	<!-- 底部 end -->		
</body>
</html>