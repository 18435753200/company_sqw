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
    
    
    $(function(){
		    var aCheck=$(':checkbox[name=isGive]');
			for(var i=0;i<aCheck.length;i++){
				if(aCheck[i].value==1){
					//aCheck[i].attr("checked","true");
					aCheck[i].checked="checked";
				}
			}
		     
        });
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
					<p>商品采购&nbsp;&gt;&nbsp; </p>
					<p class="c1">采购订单(表单)</p>
				</div>
	        </div>
			
			<div class="btn">
		     	<a href="${path}/pchaseOrder/exportPdf?orderId=${pChaseOrderVO.id}" target="_blank">打印</a>
		     	<%-- <a href="<%=path%>/pchaseOrder/confirmOrder?id=${pChaseOrderVO.id}">审核</a> --%>
	        </div>
		<form action="" id="purchaseSumit">
			<div class="xia" id="contentVal">
				<p class="tit">基本信息</p>
				<p class="p1">
				<span>采购订单号:</span>
					<input type="text" id="id" name="id" value="${pChaseOrderVO.businessNumberChar }" readonly="readonly">
					<span>订单日期:</span>
					<input type="text" id="createTime" name="createTime" value="<fmt:formatDate value="${pChaseOrderVO.createTime}" pattern="yyyy-MM-dd"/>" readonly="readonly"> 
					<span>采购员:</span>
					<input type="text" id="purChaseMan" name="createBy" value="${pChaseOrderVO.createBy}" readonly="readonly">
				</p>
				<p class="p1">
					<span>送达日期:</span>
					<input type="text" name="sendate" id="sendate" onClick="WdatePicker()" value="<fmt:formatDate value="${pChaseOrderVO.sendDate}" pattern="yyyy-MM-dd"/>" readonly="readonly">
					<span>紧急程度:</span>
					
					    <c:forEach items="${degreeOfEmergency }" var="deg">
					    <c:choose>
  					 <c:when test="${deg.dictValue==pChaseOrderVO.emergency }">
  					 <input type="text" value="${deg.dictName }" readonly="readonly"/>
  					 </c:when>
  					</c:choose>

					    </c:forEach>
					    
					    
				<span>采购商品类型:</span>
				  <c:forEach items="${productType}" var="productType">
							<c:if test="${pChaseOrderVO.productType eq productType.dictRemark}">
							<input type="text" value="${productType.dictName}" readonly="readonly"/>
							</c:if>
					    </c:forEach>
				
				</p>
				<p class="p1">
					<span>采购商品总价:</span>
					<input type="text" name="itemTotalPrice" id="itemTotalPrice" value='<fmt:formatNumber value="${pChaseOrderVO.itemTotalPrice}" pattern="#0.00"/>' readonly="readonly" style="text-align: right;font-weight:bold;">
					<span>采购合同号:</span>
					<input type="text" name="contractNumber" id="contractNumber" value="${pChaseOrderVO.contractNumber}"  readonly="readonly">
				</p>
				<p class="p1">
					<span>采购公司:</span>
					<c:forEach items="${purchaseEntity}" var="purchaseEntity" >
					<c:choose>
				   <c:when test="${purchaseEntity.dictValue==pChaseOrderVO.purchaseEntity }"><input value="${purchaseEntity.dictName}" readonly="readonly"/>
				   </c:when>
				  </c:choose>
					   
					 </c:forEach>
					<input type="hidden" id="entityName" name="entityName" readonly="readonly"/>
					<span>采购订单类型:</span>
					<input  id="purchaseTypeName" name="purchaseTypeName" value="${pChaseOrderVO.purchaseTypeName}" readonly="readonly"/>
				</p>
				<p class="p1">
					<span>备注:</span>
					<input type="text" style="width:600px;" id="remark" name="remark" value="${pChaseOrderVO.remark}" readonly="readonly">
					
				</p>
				<c:if test="${pChaseOrderVO.purchaseType==2}">
				<div id="con">
				<p class="p1">
					<span>成交条款:</span>
					<c:forEach items="${articles}" var="article" >
					<c:choose>
				   <c:when test="${article.dictValue==pChaseOrderVO.articleChar }"><input value="${article.dictName}" readonly="readonly"/>
				   </c:when>
				  </c:choose>
					   
					 </c:forEach>
					<span>付款方式:</span>
					
					<c:forEach items="${payments}" var="payment" >
					<c:choose>
				   <c:when test="${payment.dictValue==pChaseOrderVO.paymentChar }"><input value="${payment.dictName}" readonly="readonly"/>
				   </c:when>
				  </c:choose>
					   
					 </c:forEach>
					 
						<span>币别:</span>
					   <c:forEach items="${currencys}" var="currency">
					   <c:choose>
				   <c:when test="${currency.code==pChaseOrderVO.currencyType }"><input type="text" value="${currency.currencyType }" readonly="readonly"/>
				   </c:when>
				  </c:choose>
					   </c:forEach>
				</p>
				<p class="p1" value="${pChaseOrderVO.purchaseType}" readonly="readonly">
					<span>
					<c:if test="${pChaseOrderVO.radioType==1}">
					(合同约定)
					</c:if>
					<c:if test="${pChaseOrderVO.radioType==2}">
					(月度基准)
					</c:if>
					汇率:</span>
			
					<input  type="text" name="exchangeRate" id="exchangeRate" onchange="getExchange()" value="${pChaseOrderVO.exchangeRate}" readonly="readonly"/>
				</p>
				</div> 
				</c:if>
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
					<span>账期:</span>
					<input type="text" id="paymentDays" name="paymentDays" value="${pChaseOrderVO.paymentDays}" readonly="readonly" style="width:140px;"><i>天</i>
					<span>账期计算方法:</span>
					  <c:forEach items="${zhangPeriodCalculation}" var="zhang">
					  	<c:choose>
							   <c:when test="${zhang.dictValue==pChaseOrderVO.paymentFunction}">
							   <input type="text" value="${zhang.dictName}" readonly="readonly"/>
							   
							   </c:when>
							  </c:choose>
					   </c:forEach>
					</select>
				</p>
				
		   </div>
			 
		  <!--采购明细开始-->
		  <div class="pu_w">
		  		<div class="pu_h"><h3>采购明细</h3></div>
		  		<c:if test="${type==1}">
		  		<div class="btn"><button type="button" id ="addProduct">添加</button>
		  		<button type="button" id ="deleteTr">删除</button>
		  		</div>
		  		</c:if>
		  		<div class="pu_b scroll-x">
			  		<table class="ov" id="productTab">
			  			<tr class="order_hd">
			  				<th width="40px" nowrap="nowrap">序号</th>
			  				<th>商品编码</th>
			  				<th>商品条码</th>
			  				<th>商品名称</th>
			  				<th>规格</th>
			  				<th>单位</th>
			  				<th>数量</th>
			  				<th>是否赠品</th>
			  				<th>单价(含税)</th>
			  				<th>税率(%)</th>
			  				<th>不含税单价</th>
			  				<th>不含税总金额</th>
			  				<th>总金额</th>
			  				<th>币别</th>
			  				<th>汇率</th>
			  				<th>本币单价</th>
			  				<th>本币总金额</th>
			  				<th>本币不含税单价</th>
			  				<th>本币不含税金额</th>
			  				<th>分摊后单价</th>
			  				<th>分摊后总价</th>
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
			  				
			  				<td><input type="checkbox"  id="isGive" name="isGive"  value ="${item.isGive}" class="checkbox sm" style="width:110px;" ></td>
			  				
			  				<td><input type="text" id="skuPriceTax" name="skuPriceTax" value='<fmt:formatNumber value="${item.skuPriceTax}" pattern="#0.00000000"/>' readonly="readonly"  style="width:80px;"></td>
			  				<td>
			  				<input type="text" value="${item.dutyRate}" readonly="readonly">
			  				
			  				</td>
			  				<td><input type="text" id="skuPrice" name="skuPrice"  value='<fmt:formatNumber value="${item.skuPrice}" pattern="#0.00000000"/>' readonly="readonly"  style="width:80px;"></td>

			  				<td><input type="text" id="subtotalPrice" name="subtotalPrice"  value='<fmt:formatNumber value="${item.subtotalPrice}"  pattern="#0.00"/>' readonly="readonly" style="width:80px; text-align: right;"></td>
			  				<td><input type="text" id="totalPrice1" name="totalPrice1"  value='<fmt:formatNumber value="${item.totalPrice}"  pattern="#0.00"/>'   readonly="readonly" style="text-align: right;"></td>
			  				<td><input type="text" id="currencyType1" name="currencyType1" value="${item.currencyType}" readonly="readonly" ></td>
			  				<td><input type="text" id="exchangeRate1" name="exchangeRate1" value="${item.exchangeRate}" readonly="readonly"></td>
			  				<td><input type="text" id="localPrice" name="localPrice"  value='<fmt:formatNumber value="${item.localPrice}" pattern="#0.00000000"/>' readonly="readonly"  style="width:75px;"></td>

			  				<td><input type="text" id="totalLocalPrice" name="totalLocalPrice" value='<fmt:formatNumber value="${item.totalLocalPrice}" pattern="#0.00"/>' readonly="readonly"   style="width:75px; text-align: right;"></td>
			  				<td><input type="text" id="localNuTaxPrice" name="localNuTaxPrice"   readonly="readonly" value='<fmt:formatNumber value="${item.localNutaxPrice}" pattern="#0.00000000"/>'  style="width:105px;"></td>
			  				<td><input type="text" id="totalLocalNuTaxPrice" name="totalLocalNuTaxPrice" value='<fmt:formatNumber value="${item.totalLocalNutaxPrice}" pattern="#0.00"/>' readonly="readonly" style="width:105px;"></td>
			  				<td><input type="text" id="allocationPrice" name="allocationPrice" value="${item.allocationPrice}" readonly="readonly"  style="width:80px;"></td>
			  				<td><input type="text" id="allocationPrice1" name="allocationPrice1" value="${item.allocationPrice}" readonly="readonly"  style="width:80px;"></td> 
			  				
			  			</tr>
			  			</c:forEach>
			  		</c:if>
			  		</table>
		  		</div>
		  </div>
		  <!--采购明细结束-->
		
		 <!--运输信息开始-->
		  <div class="pu_hd"><h3>运输信息</h3></div>
		  <div class="xia">
		  	   <p class="p1">
					<span>承运方:</span>
					
					<c:forEach items="${shipper}" var="shipper">
						 <c:choose>
					   <c:when test="${shipper.dictValue==pChaseOrderVO.shipperChar }">
					   
					   <input type="text" value="${shipper.dictName}" readonly="readonly"/>
					   </c:when>
					  
					  </c:choose>
					 </c:forEach>
					<span>承运方式:</span>
					
					<c:forEach items="${transportWay}" var="transportWay">
					 <c:choose>
					   <c:when test="${transportWay.dictValue==pChaseOrderVO.shipperType}">
					   <input type="text" value="${transportWay.dictName}" readonly="readonly"/>
					   </c:when>
					  </c:choose>
					 </c:forEach>
			   </p>

			   <p class="p1 inputnot" >
			   	    <span>服务商名称:</span>
					<input type="text" class="inp" name="serviceName" id=serviceName onclick="showService()" value="${pChaseOrderVO.serviceName }" readonly="readonly">
					<span>服务商编号:</span>
					<input type="text" name="serviceCode" id="serviceCode" value="${pChaseOrderVO.serviceCode }" readonly="readonly">
			   </p>
				<p class="p1 inputnot">
			   	    <span>联系人:</span>
					<input type="text" class="inp" name="servicePeople" id="servicePeople" value="${pChaseOrderVO.servicePeople }" readonly="readonly">
					<span>联系电话:</span>
					<input type="text" name="serviceTel" id="serviceTel" value="${pChaseOrderVO.serviceTel }" readonly="readonly">
			   </p>
			   
			   <p class="p1">
					<span>接货地址:</span>
					<input type="text" class="int" name="receiveAddress" id="receiveAddress" value="${pChaseOrderVO.receiveAddress }" readonly="readonly">
			   </p>
		  </div>
		  
		  <div class="pu_wrap">
		  <c:if test="${type==1}">
		  <div class="btn">
		  <button type="button" id = "addWareHouse">添加</button>
		   <button type="button" id = "deleteTrKC">删除</button>
		  </div>
		  </c:if>
		  		<div class="pu_bd">
			  		<table class="ov" id="wareHouseTab">
			  			<tr class="order_hd">
			  				<th width="60px">序号</th>
			  				<th width="260px">仓库名称</th>
			  				<th width="340px">地址</th>
			  				<th width="150px">联系人</th>
			  				<th width="150px">电话</th>
			  				<th width="40px"></th>
			  			</tr>
			  			<c:if test="${pChaseOrderVO.wareHouseDTOs!=null}">
			  			<c:forEach items="${pChaseOrderVO.wareHouseDTOs}" var="wareHouseDto" varStatus="index">
			  			<tr class="appendWareHouse">
			  				<td>${index.index+1 }</td>
			  				<td><input type="text" name="wareHouseName"  onclick="showWareHouse()" value="${wareHouseDto.storeName }" readonly="readonly"  style="width:260px;"></td>
			  				<td><input type="text" name="address" value="${wareHouseDto.address }" readonly="readonly"  style="width:340px;"></td>
			  				<td><input type="text" name="contact" value="${wareHouseDto.contact }" readonly="readonly"  style="width:150px;"></td>
			  				<td><input type="text" name="telephone" value="${wareHouseDto.telephone }" readonly="readonly"   style="width:150px;"></td>
			  				<td width="0px"><input type="hidden" name="wareHouseCode" id="wareHouseCode"/  style="width:40px;"></td>
			  			</tr>
			  			</c:forEach>
			  			</c:if>
			  		</table>
		  		</div>
		  </div>
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
		
		
		<div class="alert_bu" id="wareHouseDiv" style="display: none;">
		  <div class="bg"></div>
			<div class="wrap">
				<div class="pic"></div>
				<div class="box1">
					<div id="boxtitle">
						<h2>选择仓库.</h2>	
					</div>				
					<div id="suppliernametext" style="display: block;">
						<input type="text" id="WareHouseName">
						<button type="button" onclick="wareHouseByWareHouseName();">查询</button>
					</div>
				</div>
				<div class="box2" id="wareHousebox">
					
				</div>
				<div class="box3">
						<button type="button" class="bt1" id="supplierclick" onclick="loadWareHouse()">确定</button>
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