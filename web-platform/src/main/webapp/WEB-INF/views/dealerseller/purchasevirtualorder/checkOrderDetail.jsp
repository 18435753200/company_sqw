<%@page import="com.mall.retailer.order.po.Dictionarys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="UTF-8">
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<title>商品采购_查看采购订单(表单)</title>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/purchaseOrder.css">
    <script type="text/javascript">
    function printContract(){
		var content = "${pChaseOrderVO.id}";
		$.ajax({
		type : "post", 
		url : "exportPdf", 
		dataType:"text",
		data:content,
		success : function(msg) { 
			if(msg!="")
			{
				
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	});   
	}
    </script>
</head>
<body>
<form action="${path}/pchaseOrder/printContract" id="print">
	<input type="hidden" name="content" id="content">
</form>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="center">

		<div class="right">

			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp; </p>
					<p style="width:130px;">特卖虚拟库存设置&nbsp;&gt;&nbsp; </p>
					<p class="c1">虚拟库存查看(界面)</p>
				</div>
	        </div>
			
<!-- 			<div class="btn"> -->
<%-- 		     	<a href="${path}/pchaseVirtualOrder/exportPdf?orderId=${pChaseOrderVO.id}" target="_blank">打印</a> --%>
<%-- 		     	<a href="<%=path%>/pchaseOrder/confirmOrder?id=${pChaseOrderVO.id}">审核</a> --%>
<!-- 	        </div> -->
		<form action="" id="purchaseSumit">
			<div class="xia" id="contentVal">
				<p class="tit">基本信息</p>
				<p class="p1">
					<span>虚拟设置编号:</span>
					<input type="text" id="id" name="id" value="${pChaseOrderVO.businessNumberChar }" readonly="readonly">
					
					
					<span>库存类型:</span>
					<input  id="purchaseTypeName" name="purchaseTypeName" value="${pChaseOrderVO.purchaseTypeName}" readonly="readonly"/>
					
					<span>商品类型:</span>
				    <c:forEach items="${productType}" var="productType">
						<c:if test="${pChaseOrderVO.productType eq productType.dictRemark}">
							<input type="text" value="${productType.dictName}" readonly="readonly"/>
						</c:if>
				     </c:forEach>
				    
					<input type="hidden" id="createTime" name="createTime" value="<fmt:formatDate value="${pChaseOrderVO.createTime}" pattern="yyyy-MM-dd"/>" readonly="readonly"> 
					<input type="hidden" id="purChaseMan" name="createBy" value="${pChaseOrderVO.createBy}" readonly="readonly">
				</p>

				<p class="tit">供应商信息</p>
				<p class="p1">
					<span>供应商名称:</span>
					<input type="text" id="supplierName" name="supplierName" onclick="showSupplier()" value="${pChaseOrderVO.supplierName}" readonly="readonly">
					<span>供应商编号:</span>
					<input type="text" id="supplierId" name="supplierId" value="${pChaseOrderVO.supplierId}" readonly="readonly">
					<span>联系人:</span>
					<input type="text" id="supplierBy" name="supplierBy" value="${fn:escapeXml(pChaseOrderVO.supplierBy)}" readonly="readonly">
				</p>
				<p class="p1">
					<span>联系电话:</span>
					<input type="text" id="supplierPhone" name="supplierPhone" value="${pChaseOrderVO.supplierPhone}" readonly="readonly">
				</p>
				
		   </div>
			 
		  <!--采购明细开始-->
		  <div class="pu_w">
		  		<div class="pu_h"><h3>商品明细</h3></div>
		  		<c:if test="${type==1}">
		  		<div class="btn"><button type="button" id ="addProduct">添加</button>
		  		<button type="button" id ="deleteTr">删除</button>
		  		</div>
		  		</c:if>
		  		<div class="pu_b scroll-x">
			  		<table class="ov" id="productTab">
			  			<tr class="order_hd">
			  				<th width="40px" nowrap="nowrap">序号</th>
			  				<th>商品ID</th>
			  				<th>商品条码</th>
			  				<th>商品名称</th>
			  				<th>规格</th>
			  				<th>单位</th>
			  				<th>库存</th>
			  			</tr>
			  			<c:if test="${pChaseOrderVO.pCOItemList!=null}">
			  			<c:forEach items="${pChaseOrderVO.pCOItemList}" var="item" varStatus="index">
			  			<tr class="append">
			  				<td nowrap="nowrap">${index.index+1 }</td>
			  				<td><input type="text" id="skuId" name="skuId" value="${item.businessProdid}" readonly="readonly"  style="width:130px;"></td>
			  				<td><input type="text" id="barCode" name="barCode" value="${item.barCode}" readonly="readonly"  style="width:140px;"></td>
			  				<td><input type="text" id="pName" name="pName" value="${item.pname}" readonly="readonly"  style="width:130px;"></td>
			  				<td><input type="text" id="format" name="format" value="${item.format}" readonly="readonly"  style="width:100px;"></td>
			  				<td><input type="text" id="unit" name="unit" value="${item.unit}" readonly="readonly"></td>
			  				<td><input type="text" id="qty" name="qty" value="${item.qty}"  readonly="readonly"></td>
			  			</tr>
			  			</c:forEach>
			  		</c:if>
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
					</div>
				</div>
				<div class="box2" id="supplierBox">
					
				</div>
				<div class="box3">
					<button type="button" class="bt1" id="supplierclick" onclick="loadSupplier()">确定</button>
				</div>
			</div>
		</div>
		
		<div class="alert_bu" id="skuDiv" style="display: none;">
		  <div class="bg"></div>
			<div class="wrap">
				<div class="pic"></div>
				<div class="box1">
					<div id="boxtitle">
						<h2>选择商品.</h2>
					</div>
					<div id="suppliernametext" style="display: block;">
						<input type="text" id="productName">
						<button type="button" onclick="selService();">查询</button>
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