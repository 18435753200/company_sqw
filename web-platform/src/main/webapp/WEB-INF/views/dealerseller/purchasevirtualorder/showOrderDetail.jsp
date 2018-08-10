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
    <script type="text/javascript" src="${path}/commons/js/purchasevirtualorder_js/purchasevirtual_add.js"></script>
    <script src="${path}/commons/js/purchasevirtualorder_js/purchasevirtual_check.js"></script>
    
</head>
<script type="text/javascript">
$(function(){
  //change();
  if($("#purchaseType").val()==2){
  	
  	$("#con").show();
  }
  
   if($("#purchaseType").val()==1){
  	
  	$("#con").hide();
  }
  ship();
});
 function change()
 {
 	var  typeName=$("#purchaseType").find("option:selected").text();
 	$("#purchaseTypeName").val(typeName);
 	if($("#purchaseType").val()=="2")
 	{
 		$("#con").show();
       	var exchangeRate = $("#currencyType option:selected").attr("exchangeRate");
		$("#exchangeRate").val(exchangeRate);
		$("select[name='dutyRate']").html("<option value='0' dutyCode='0' selected='selected'>无税率</option>");
		batchOperation(2);
 		
 	}else{
 		$("#con").hide();
 		$("input[name='exchangeRate1']").val(1);
 		var optionList=findTax();
 		$("select[name='dutyRate']").html(optionList);
 		batchOperation(1);
 	}
 }
 
 function change1(){
 	
 		var  typeName=$("#purchaseEntity").find("option:selected").text();
 		$("#entityName").val(typeName);
 }
 
 function cleanredio()
 {
 	var Rate=$("input[name='exchangeredio']:checked").val();
	if(Rate!="2")
	{
		
		$("#exchangeRate").val("");
	}
	if(Rate=="2"){
	var exchangeRate = $("#currencyType option:selected").attr("exchangeRate");
	$("#exchangeRate").val(exchangeRate);
	$("#exchangeRate").attr("readonly",true);
	if(exchangeRate!=undefined)
		$("input[name='exchangeRate1']").val(exchangeRate);
	}
	
	if(Rate=="1"){
	
		$("#exchangeRate").attr("readonly",false);
	}
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
<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="center">

		<div class="right">

			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp; </p>
					<p>特卖采购&nbsp;&gt;&nbsp; </p>
					<p class="c1">特卖采购订单(表单)</p>
				</div>
	        </div>
			
			<div class="btn">
			<c:if test="${type==1}">
		     	<a href="javascript:void(0)" onclick="gosumitPurchase('update')">保存</a>
		     	<a href="javascript:void(0)" onclick="gosumitPurchase('check')">审核</a>
		     </c:if>
	        </div>
		<form action="" id="purchaseSumit">
			<div class="xia">
				<p class="tit">基本信息</p>
				<p class="p1">
				<span>采购订单号:</span>
					<input type="text" id="purchaseCode1" name="purchaseCode1"  readonly="readonly"  value='${ pChaseOrderVO.businessNumberChar}' style="background:#d0cdcd">
					<input type="hidden" id="purchaseCode" name="purchaseCode" value='${ pChaseOrderVO.businessNumberChar}' readonly="readonly" >
					<input type="hidden" id="id" name="id" value="${pChaseOrderVO.id}" readonly="readonly">
					
					<span><i class="red">*</i>采购订单类型:</span>
					<select name="purchaseType" id="purchaseType" onchange="change()">
					
					 <c:forEach items="${purchaseTypeList}" var="purchaseType">
					 <c:choose>
					   <c:when test="${purchaseType.code==pChaseOrderVO.purchaseType }"><option value="${purchaseType.code }" selected="selected">${purchaseType.purchaseName }</option>
					   </c:when>
					   <c:otherwise>
					    <option value="${purchaseType.code}">${purchaseType.purchaseName}</option>
					   </c:otherwise>
					  </c:choose>
					  
					  </c:forEach>
					</select>
					<input type="hidden" id="purchaseTypeName" name="purchaseTypeName" value='${pChaseOrderVO.purchaseTypeName}'/>
					
					<span>采购商品总价:</span>
					<input type="text" id="itemTotalPrice" value='<fmt:formatNumber value="${pChaseOrderVO.itemTotalPrice}" pattern="#0.00"/>' readonly="readonly"  style="text-align: right;font-weight:bold; background:#d0cdcd;">
					<input type="hidden" name='itemTotalPrice' value='<fmt:formatNumber value="${pChaseOrderVO.itemTotalPrice}" pattern="#0.00"/>'/>
					
					
<!-- 					<span>订单日期:</span> -->
					<input type="hidden" id="createTime" name="createTime" value="<fmt:formatDate value="${pChaseOrderVO.createTime}" pattern="yyyy-MM-dd"/>" readonly="readonly"> 
<!-- 					<span>采购员:</span> -->
					<input type="hidden" id="purChaseMan" name="createBy" value="${pChaseOrderVO.createBy}" readonly="readonly">
				</p>
<!-- 				<p class="p1"> -->
<!-- 					<span>送达日期:</span> -->
<%-- 					<input type="text" name="sendate" id="sendate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value="${pChaseOrderVO.sendDate}" pattern="yyyy-MM-dd "/>"> --%>
<!-- 					<span><i class="red">*</i>紧急程度:</span> -->
<!-- 					<select name="emergency" id="emergency"> -->
					    
<%-- 					    <c:forEach items="${degreeOfEmergency }" var="deg"> --%>
<%-- 					    <c:choose> --%>
<%-- 						   <c:when test="${deg.dictValue==pChaseOrderVO.emergency }"><option value="${deg.dictValue }" selected="selected">${deg.dictName }</option> --%>
<%-- 						   </c:when> --%>
<%-- 						   <c:otherwise> --%>
<%-- 						   <option value="${deg.dictValue }">${deg.dictName }</option> --%>
<%-- 						   </c:otherwise> --%>
<%-- 						  </c:choose> --%>
					    	

<%-- 					    </c:forEach> --%>
<!-- 					</select> -->
				
<!-- 				<span><i class="red">*</i>采购商品类型:</span> -->
<!-- 					<select name="productType" id="productType"> -->
<%-- 						<c:forEach items="${productType}" var="productType"> --%>
<%-- 							<c:if test="${pChaseOrderVO.productType eq productType.dictRemark}"> --%>
<%-- 							<option value="${productType.dictRemark}" selected="selected">${productType.dictName}</option> --%>
<%-- 							</c:if> --%>
<%-- 							<c:if test="${pChaseOrderVO.productType ne productType.dictRemark}"> --%>
<%-- 							<option value="${productType.dictRemark}">${productType.dictName}</option> --%>
<%-- 							</c:if> --%>
<%-- 					    </c:forEach> --%>
<!-- 					</select> -->
<!-- 				</p> -->
				
<!-- 				<p class="p1"> -->
<!-- 					<span>采购商品总价:</span> -->
<%-- 					<input type="text" id="itemTotalPrice" value='<fmt:formatNumber value="${pChaseOrderVO.itemTotalPrice}" pattern="#0.00"/>' readonly="readonly"  style="text-align: right;font-weight:bold; background:#d0cdcd;"> --%>
<%-- 					<input type="hidden" name='itemTotalPrice' value='<fmt:formatNumber value="${pChaseOrderVO.itemTotalPrice}" pattern="#0.00"/>'/> --%>
<!-- 					<span><i class="red">*</i>采购合同号:</span> -->
<%-- 					<input type="text" name="contractNumber" id="contractNumber" value="${pChaseOrderVO.contractNumber}" > --%>
<!-- 				</p> -->
<!-- 				<p class="p1"> -->
<!-- 					<span><i class="red">*</i>采购公司:</span> -->
<!-- 					<select id ="purchaseEntity" name="purchaseEntity" onchange="change1()"> -->
					
<%-- 					<c:forEach items="${purchaseEntity}" var="purchaseEntity"> --%>
<%-- 					<c:choose> --%>
<%-- 				   <c:when test="${purchaseEntity.dictValue==pChaseOrderVO.purchaseEntity }"><option value="${purchaseEntity.dictValue }" selected="selected">${purchaseEntity.dictName }</option> --%>
<%-- 				   </c:when> --%>
<%-- 				   <c:otherwise> --%>
<%-- 				   <option value="${purchaseEntity.dictValue }">${purchaseEntity.dictName }</option> --%>
<%-- 				   </c:otherwise> --%>
<%-- 				  </c:choose> --%>
					   
<%-- 					 </c:forEach> --%>
<!-- 					</select> -->
<!-- 					<input type="hidden" id="entityName" name="entityName"/> -->
					
<!-- 				</p> -->
				<p class="p1">
					<span>备注:</span>
					<input type="text" style="width:600px;" id="remark" name="remark" value="${pChaseOrderVO.remark}">
					
				</p>
				
<!-- 				<div id="con"> -->
<!-- 				<p class="p1"> -->
<!-- 					<span><i class="red">*</i>成交条款:</span> -->
<!-- 					<select name="article" id="article"> -->
<%-- 					<c:forEach items="${articles}" var="article"> --%>
<%-- 					<c:if test="${article.dictValue eq pChaseOrderVO.articleChar}">  pChaseOrderVO.articleChar  article.dictValue --%>
<%-- 					<option value="${article.dictValue}" selected='selected'>${article.dictName}</option> --%>
<%-- 					</c:if> --%>
<%-- 					<c:if test="${article.dictValue ne pChaseOrderVO.articleChar}"> --%>
<%-- 					<option value="${article.dictValue}">${article.dictName}</option> --%>
<%-- 					</c:if> --%>
<%-- 					</c:forEach> --%>
<!-- 					</select> -->
<!-- 					<span><i class="red">*</i>付款方式:</span> -->
<!-- 					<select name="payment" id="payment"> -->
<%-- 					<c:forEach items="${payments}" var="payment"> --%>
<%-- 					<c:if test="${payment.dictValue eq pChaseOrderVO.paymentChar}"> --%>
<%-- 					<option value="${payment.dictValue}" selected='selected'>${payment.dictName}</option> --%>
<%-- 					</c:if> --%>
<%-- 					<c:if test="${payment.dictValue ne pChaseOrderVO.paymentChar}"> --%>
<%-- 					<option value="${payment.dictValue}">${payment.dictName}</option> --%>
<%-- 					</c:if> --%>
<%-- 					</c:forEach> --%>
<!-- 					</select> -->
<!-- 						<span><i class="red">*</i>币别:</span> -->
<%-- 					<select  name="currencyType" id="currencyType" value="${pChaseOrderVO.purchaseType}" > --%>
<%-- 					   <c:forEach items="${currencys}" var="currency"> --%>
<%-- 					   <c:choose> --%>
<%-- 				   <c:when test="${currency.code==pChaseOrderVO.currencyType }"><option value="${currency.code }" selected="selected" exchangeRate="${currency.exchangeRate}">${currency.currencyType }</option> --%>
<%-- 				   </c:when> --%>
<%-- 				   <c:otherwise> --%>
<%-- 				      <option value="${currency.code}" exchangeRate="${currency.exchangeRate}">${currency.currencyType}</option> --%>
<%-- 				   </c:otherwise> --%>
<%-- 				  </c:choose> --%>
<%-- 					   </c:forEach> --%>
<!-- 					</select> -->
<!-- 				</p> -->
<%-- 				<p class="p1" value="${pChaseOrderVO.purchaseType}" readonly="readonly"> --%>
<!-- 					<span><i class="red">*</i>汇率:</span> -->
<%-- 					<c:if test="${pChaseOrderVO.radioType==1}"> --%>
<!-- 					<input class="radiol" type="radio"  name="exchangeredio" checked="checked" value="1" onclick="cleanredio()" /> -->
<%-- 					</c:if> --%>
<%-- 					<c:if test="${pChaseOrderVO.radioType==2 ||pChaseOrderVO.radioType==null}"> --%>
<!-- 					<input class="radiol" type="radio"  name="exchangeredio"  value="1" onclick="cleanredio()" /> -->
<%-- 					</c:if> --%>
<!-- 					<span class="rsp">合同约定汇率</span> -->
<%-- 					<c:if test="${pChaseOrderVO.radioType==2 ||pChaseOrderVO.radioType==null}"> --%>
<!-- 					<input class="radiol1" type="radio"  name="exchangeredio" checked="checked" value="2" onclick="cleanredio()"/> -->
<%-- 					</c:if> --%>
<%-- 					<c:if test="${pChaseOrderVO.radioType==1}"> --%>
<!-- 					<input class="radiol1" type="radio"  name="exchangeredio"  value="2" onclick="cleanredio()"/> -->
<%-- 					</c:if> --%>
<!-- 					<span class="rsp">月度基准汇率</span> -->
<%-- 					<input class="inpdd" type="text" name="exchangeRate" id="exchangeRate" onchange="getExchange()" value="${pChaseOrderVO.exchangeRate}"/> --%>
<!-- 				</p> -->
<!-- 				</div> -->
				<p class="tit"><i class="red">*</i>供应商信息</p>
				<p class="p1">
					<span><i class="red">*</i>供应商名称:</span>
					<input type="text" id="supplierName" name="supplierName" onclick="showSupplier()" value="${pChaseOrderVO.supplierName}">
					<span>供应商编号:</span>
					<input type="text" id="supplierId" name="supplierId" value="${pChaseOrderVO.supplierId}" readonly="readonly">
					<span>联系人:</span>
					<input type="text" id="supplierBy" name="supplierBy" value="${fn:escapeXml(pChaseOrderVO.supplierBy)}" readonly="readonly">
				</p>
				<p class="p1">
					<span>联系电话:</span>
					<input type="text" id="supplierPhone" name="supplierPhone" value="${pChaseOrderVO.supplierPhone}" readonly="readonly">
<!-- 					<span>账期:</span> -->
<%-- 					<input type="text" id="paymentDays" name="paymentDays" value="${pChaseOrderVO.paymentDays}"  style="width:140px;"><i>天</i> --%>
<!-- 					<span><i class="red">*</i>账期计算方法:</span> -->
<!-- 					<select id="paymentFunction" name="paymentFunction" > -->
<!-- 					<option value='' id="qxz">请选择</option> -->
<%-- 					  <c:forEach items="${zhangPeriodCalculation}" var="zhang"> --%>
<%-- 					  	<c:choose> --%>
<%-- 							   <c:when test="${zhang.dictValue==pChaseOrderVO.paymentFunction}"><option value="${zhang.dictValue}" selected="selected" >${zhang.dictName}</option> --%>
<%-- 							   </c:when> --%>
<%-- 							   <c:otherwise> --%>
<%-- 							     <option value="${zhang.dictValue}">${zhang.dictName}</option> --%>
<%-- 							   </c:otherwise> --%>
<%-- 							  </c:choose> --%>
<%-- 					   </c:forEach> --%>
<!-- 					</select> -->
				</p>
				
		   </div>
			 
		  <!--采购明细开始-->
		  <div class="pu_w">
		  		<div class="pu_h"><h3>采购明细</h3></div>
		  		<c:if test="${type==1}">
		  		<div class="btn"><button type="button" id ="addProduct">添加</button>
		  		<button type="button" id ="deleteTr">删除</button>
		  		<input type="hidden" id="chkTrue" name="chkTrue">
		  		</div>
		  		</c:if>
		  		<div class="pu_b scroll-x">
			  		<table class="ov" id="productTab">
			  			<tr class="order_hd">
			  				<th width="40px" nowrap="nowrap">序号</th>
			  				<th width="40px" nowrap="nowrap">/选择</th>
			  				<th width="40px">商品编码</th>
			  				<th width="60px">商品条码</th>
			  				<th width="60px">商品名称</th>
			  				<th width="30px">规格</th>
			  				<th width="30px">单位</th>
			  				<th width="30px">数量</th>
<!-- 			  				<th width="40px">是否赠品</th> -->
			  				<th width="40px">单价(含税)</th>
<!-- 			  				<th width="50px">税率(%)</th> -->
<!-- 			  				<th width="50px">不含税单价</th> -->
<!-- 			  				<th width="40px">不含税总金额</th> -->
			  				<th width="50px">总金额</th>
<!-- 			  				<th width="30px">币别</th> -->
<!-- 			  				<th width="30px">汇率</th> -->
<!-- 			  				<th width="40px">本币单价</th> -->
<!-- 			  				<th width="50px">本币总金额</th> -->
<!-- 			  				<th width="50px">本币不含税单价</th> -->
<!-- 			  				<th width="50px">本币不含税金额</th> -->
			  				
			  			</tr>
			  			<c:if test="${pChaseOrderVO.pCOItemList!=null}">
			  			<c:forEach items="${pChaseOrderVO.pCOItemList}" var="item">
			  			<tr class="append">
			  				<td nowrap="nowrap">1</td>
			  				<td nowrap="nowrap"><input type="checkbox" class="checkbox sm"></td>
			  				<td><input type="text" id="skuId" value="${item.businessProdid}" readonly="readonly"  style='width:130px; '>
			  				<input type="hidden" id="skuId" name="skuId" value="${item.skuId}">
			  				<input type="hidden" id="businessProdid" name="businessProdid" value="${item.businessProdid}">
			  				</td>
			  				<td><input type="text" id="barCode" name="barCode" value="${item.barCode}" readonly="readonly" style='width:140px;'></td>
			  				<td><input type="text" id="pName" name="pName" value="${item.pname}" readonly="readonly" style='width:130px;'></td>
			  				<td><input type="text" id="format" name="format" value="${item.format}" readonly="readonly" style='width:100px;'></td>

			  				<td><input type="text" id="unit" name="unit" value="${item.unit}" readonly="readonly"></td>
			  				<td><input type="text" id="qty" name="qty" value="${item.qty}" ></td>
			  				
<%-- 			  				<td><input type="checkbox"  id="isGive" name="isGive"  value ="${item.isGive}" class="checkbox sm" style="width:110px;" ></td> --%>
			  				
			  				<td><input type="text" id="skuPriceTax" name="skuPriceTax" value='<fmt:formatNumber value="${item.skuPriceTax}" pattern="#0.00000000"/>' style='width:80px;'></td>
<!-- 			  				<td> -->
<!-- 			  				<select id='dutyRate' name='dutyRate'> -->
<%-- 			  				<c:if test="${pChaseOrderVO.purchaseType==1}"> --%>
<%-- 			  				<c:forEach items='${taxs}' var='taxFee' > --%>
<%-- 			  				<c:choose> --%>
<%-- 							   <c:when test="${taxFee.code==item.dutycode }"><option value="${taxFee.tax }" selected="selected" >${item.dutyRate}</option> --%>
<%-- 							   </c:when> --%>
<%-- 							   <c:otherwise> --%>
<%-- 							      <option value="${taxFee.tax}"  dutyCode="${taxFee.code }">${taxFee.tax}</option> --%>
<%-- 							   </c:otherwise> --%>
<%-- 							  </c:choose> --%>
<%-- 					   		</c:forEach> --%>
<%-- 					   		</c:if> --%>
<%-- 					   		<c:if test="${pChaseOrderVO.purchaseType==2}"> --%>
<!-- 					   		<option value='0' dutyCode='0' selected='selected'>无税率</option> -->
<%-- 					   		</c:if> --%>
<!-- 			  				</select> -->
<%-- 			  				<input type="hidden" name="dutyCode"  id="dutyCode" value="${item.dutycode}"> --%>
<!-- 			  				</td> -->
<%-- 			  				<td><input type="text" id="skuPrice" name="skuPrice" value='<fmt:formatNumber value="${item.skuPrice}" pattern="#0.00000000"/>' readonly="readonly"  style='width:80px;'></td> --%>

<%-- 			  				<td><input type="text" id="subtotalPrice" name="subtotalPrice" value='<fmt:formatNumber value="${item.subtotalPrice}"  pattern="#0.00"/>' readonly="readonly"  style='text-align: right; width:80px;'></td> --%>
			  				<td><input type="text" id="totalPrice1" name="totalPrice1" value='<fmt:formatNumber value="${item.totalPrice}"  pattern="#0.00"/>' readonly="readonly" style='text-align: right'></td>
<%-- 			  				<td><input type="text" id="currencyType1" name="currencyType1" value="${item.currencyType}" readonly="readonly"></td> --%>
<%-- 			  				<td><input type="text" id="exchangeRate1" name="exchangeRate1" value="${item.exchangeRate}" readonly="readonly"></td> --%>
<%-- 			  				<td><input type="text" id="localPrice" name="localPrice" value='<fmt:formatNumber value="${item.localPrice}" pattern="#0.00000000"/>' readonly="readonly"  style='width:75px;'></td> --%>

<%-- 			  				<td><input type="text" id="totalLocalPrice" name="totalLocalPrice" value='<fmt:formatNumber value="${item.totalLocalPrice}" pattern="#0.00"/>' readonly="readonly" style='text-align: right; width:75px;'></td> --%>
<%-- 			  				<td><input type="text" id="localNuTaxPrice" name="localNuTaxPrice" value='<fmt:formatNumber value="${item.localNutaxPrice}" pattern="#0.00000000"/>' readonly="readonly" style='width:105px;'></td> --%>
<%-- 			  				<td><input type="text" id="totalLocalNuTaxPrice" name="totalLocalNuTaxPrice" value='<fmt:formatNumber value="${item.totalLocalNutaxPrice}" pattern="#0.00"/>' readonly="readonly" style='text-align: right; width:105px;'></td> --%>
			  				
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
					<span><i class="red">*</i>承运方:</span>
					<select name="shipper" id="shipper" onchange="ship();" value="${pChaseOrderVO.shipper }" readonly="readonly" >
					<c:forEach items="${shipper}" var="shipper">
						 <c:choose>
					   <c:when test="${shipper.dictValue==pChaseOrderVO.shipperChar}"><option value="${shipper.dictValue }" selected="selected">${shipper.dictName}</option>
					   </c:when>
					   <c:otherwise>
					    <option value="${shipper.dictValue}">${shipper.dictName}</option>
					   </c:otherwise>
					  </c:choose>
					 </c:forEach>
					</select>
					<span id='addShipType'>承运方式:</span>
					
					<select name="shipperType" value="${pChaseOrderVO.shipperType}" readonly="readonly">
					<c:forEach items="${transportWay}" var="transportWay">
					 <c:choose>
					   <c:when test="${transportWay.dictValue==pChaseOrderVO.shipperType}"><option value="${transportWay.dictValue }" selected="selected">${transportWay.dictName}</option>
					   </c:when>
					   <c:otherwise>
					    <option value="${transportWay.dictValue}">${transportWay.dictName}</option>
					   </c:otherwise>
					  </c:choose>
					 </c:forEach>
					</select>
			   </p>

			   <p class="p1 inputnot" >
			   	    <span id='addServiceName'>服务商名称:</span>
					<input type="text" class="inp" name="serviceName" id=serviceName onclick="showService()" value="${pChaseOrderVO.serviceName }" readonly="readonly" style="background:#d0cdcd">
					<span>服务商编号:</span>
					<input type="text" name="serviceCode" id="serviceCode" value="${pChaseOrderVO.serviceCode }" readonly="readonly" style="background:#d0cdcd">
			   </p>
				<p class="p1 inputnot">
			   	    <span>联系人:</span>
					<input type="text" class="inp" name="servicePeople" id="servicePeople" value="${pChaseOrderVO.servicePeople }" readonly="readonly" style="background:#d0cdcd">
					<span>联系电话:</span>
					<input type="text" name="serviceTel" id="serviceTel" value="${pChaseOrderVO.serviceTel }" readonly="readonly" style="background:#d0cdcd">
			   </p>
			   
			   <p class="p1">
					<span><i class="red">*</i>接货地址:</span>
					<input type="text" class="int" name="receiveAddress" id="receiveAddress" value="${pChaseOrderVO.receiveAddress }">
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
			  				<th width="60px">选择</th>
			  				<th width="200px"><i class="red">*</i>仓库名称</th>
			  				<th width="340px">地址</th>
			  				<th width="150px">联系人</th>
			  				<th width="150px">电话</th>
			  				<th width="40px"></th>
			  			</tr>
			  			<c:if test="${pChaseOrderVO.wareHouseDTOs!=null}">
			  			<c:forEach items="${pChaseOrderVO.wareHouseDTOs}" var="wareHouseDto">
			  			<tr class="appendWareHouse">
			  				<td>1</td>
			  				<td><input type="checkbox" class="checkbox1"></td>
			  				<td><input type="text" name="wareHouseName"  onclick="showWareHouse()" value="${wareHouseDto.storeName }" readonly="readonly"  style="width:200px;"></td>
			  				<td><input type="text" name="address" value="${wareHouseDto.address }" readonly="readonly"  style="width:340px;"></td>
			  				<td><input type="text" name="contact" value="${wareHouseDto.contact }" style="width:150px;" readonly="readonly" ></td>
			  				<td><input type="text" name="telephone" value="${wareHouseDto.telephone }" style="width:150px;" readonly="readonly" ></td>
			  				<td><input type="hidden" name="wareHouseCode" id="wareHouseCode" value="${wareHouseDto.storeCode }"/   style="width:40px;"></td>

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
						<input type="text" id="WareHouseName">
						<button type="button" onclick="wareHouseByWareHouseName();">查询</button>
						<button type="button" onclick="hideShow();">退出</button>
					</div>
				</div>
				<div class="box2" id="wareHousebox">
					
				</div>
				<div class="box3">
						<button type="button" class="bt1" id="supplierclick" onclick="loadWareHouse()">确定</button>
				</div>
			</div>
		</div>
		
		
		<div class="alert_c" id="skuDiv" style="display: none;">
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