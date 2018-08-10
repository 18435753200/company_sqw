<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="UTF-8">
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<title>商品采购_采购订单(表单)</title>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/purchaseOrder.css">
    <script src="${path}/commons/js/my97/WdatePicker.js"></script>
    <script src="${path}/commons/js/purchaseorder_js/purchase_add.js"></script>
    <script src="${path}/commons/js/purchaseorder_js/purchase_check.js"></script>
    
</head>
<script type="text/javascript">
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
 window.onload=function(){
 	clickBox();
 };
</script>
<body>
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
		     	<a href="javascript:void(0)" onclick="gosumitPurchase('save')">保存</a>
		     	<%-- <a href="<%=path%>/pchaseOrder/confirmOrder?id=${pChaseOrderVO.id}">审核</a> --%>
	        </div>
		<form action="" id="purchaseSumit">
			<div class="xia">
				<p class="tit">基本信息</p>
				<p class="p1">
				<span>采购订单号:</span>
					<input type="text" id="purchaseCode1" name="purchaseCode1"  readonly="readonly"   style="background:#d0cdcd;">
					<input type="hidden" id="purchaseCode" name="purchaseCode"  readonly="readonly">
					<input type="hidden" id="id" name="id" value="${ChaseOrderId }" readonly="readonly">
					<span>订单日期:</span>
					<input type="text" id="createTime" name="createTime" value="${sysdate}" readonly="readonly" > 
					<span>采购员:</span>
					<input type="text" id="purChaseMan" name="createBy" value="${name }" readonly="readonly">
				</p>
				<p class="p1">
					<span>送达日期:</span>
					<input type="text" name="sendate" id="sendate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
					<span><i class="red">*</i>紧急程度:</span>
					<select name="emergency" id="emergency">
						<c:forEach items="${degreeOfEmergency}" var="degreeOfEmergency">
							<c:if test="${degreeOfEmergency.dictValue eq '002'}">
								<option value="${degreeOfEmergency.dictValue}" selected="selected">${degreeOfEmergency.dictName}</option>
							</c:if>
							<c:if test="${degreeOfEmergency.dictValue ne '002'}">
							<option value="${degreeOfEmergency.dictValue}">${degreeOfEmergency.dictName}</option>
							</c:if>
					    </c:forEach>
					</select>
					<span><i class="red">*</i>采购商品类型:</span>
					<select name="productType" id="productType">
						<option value=''>请选择</option>
						<c:forEach items="${productType}" var="productType">
							<option value="${productType.dictRemark}">${productType.dictName}</option>
					    </c:forEach>
					</select>
				
				</p>
				<p class="p1">
					<span>采购商品总价:</span>
					    <input type="text" id="itemTotalPrice" readonly="readonly"   style="text-align: right;font-weight:bold; background:#d0cdcd;">
					<input type="hidden" name='itemTotalPrice'>
					<span><i class="red">*</i>采购合同号:</span>
					<input type="text" name="contractNumber" id="contractNumber">
				</p>
				<p class="p1">
					<span><i class="red">*</i>采购公司:</span>
					<select id ="purchaseEntity" name="purchaseEntity" onchange="change1()">
					<option value="">请选择</option>
					<c:forEach items="${purchaseEntity}" var="purchaseEntity">
					   <option value="${purchaseEntity.dictValue}">${purchaseEntity.dictName}</option>
					 </c:forEach>
					</select>
					<input type="hidden" id="entityName" name="entityName"/>
					<span><i class="red">*</i>采购订单类型:</span>
					<select name="purchaseType" id="purchaseType" onchange="change()">
					<option value="">请选择</option>
					 <c:forEach items="${purchaseTypeList}" var="purchaseType">
					   <option value="${purchaseType.code}">${purchaseType.purchaseName}</option>
					  </c:forEach>
					</select>
					<input type="hidden" id="purchaseTypeName" name="purchaseTypeName"/>
				</p>
				<p class="p1">
					<span>备注:</span>
					<input type="text" style="width:600px;" id="remark" name="remark">
					
				</p>
				
				<div id="con">
				<p class="p1">
					<span><i class="red">*</i>成交条款:</span>
					<select name="article" id="article">
					<option value="">请选择</option>
					<c:forEach items="${articles}" var="article">
					<option value="${article.dictValue}">${article.dictName}</option>
					</c:forEach>
					</select>
					<span><i class="red">*</i>付款方式:</span>
					<select name="payment" id="payment">
					<option value="">请选择</option>
					<c:forEach items="${payments}" var="payment">
					<option value="${payment.dictValue}">${payment.dictName}</option>
					</c:forEach>
					</select>
						<span><i class="red">*</i>币别:</span>
					<select  name="currencyType" id="currencyType">
					   <c:forEach items="${currencys}" var="currency">
					   <c:if test="${currency.code eq 'RMB'}">
					    <option value="${currency.code}" exchangeRate="${currency.exchangeRate}" selected="selected">${currency.currencyType}</option>
					   </c:if>
					   <c:if test="${currency.code ne 'RMB'}">
					   <option value="${currency.code}"  exchangeRate="${currency.exchangeRate}">${currency.currencyType}</option>
					   </c:if>
					   </c:forEach>
					</select>
				</p>
				<p class="p1">
					<span><i class="red">*</i>汇率:</span>
					<input class="radiol" type="radio"  name="exchangeredio" value="1" onclick="cleanredio()"/><span class="rsp">合同约定汇率</span>
					<input class="radiol1" type="radio"  name="exchangeredio" value="2" onclick="cleanredio()" checked="checked"/><span class="rsp">月度基准汇率</span>
					<input class="inpdd"type="text" name="exchangeRate" id="exchangeRate" onchange="getExchange()" readonly="readonly"/>
				</p>
				</div>
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
					<span>账期:</span>
					<input type="text" id="paymentDays" name="paymentDays" style="width:140px; ">
					<i>天</i>
					<span><i class="red">*</i>账期计算方法:</span>
					<select id="paymentFunction" name="paymentFunction">
					<option value='' id="qxz">请选择</option>
					  <c:forEach items="${zhangPeriodCalculation}" var="zhang">
					  <c:if test="${zhang.dictValue eq '002'}">
					   <option value="${zhang.dictValue}" selected="selected">${zhang.dictName}</option>
					   </c:if>
					    <c:if test="${zhang.dictValue ne '002'}">
					   <option value="${zhang.dictValue}">${zhang.dictName}</option>
					   </c:if>
					   </c:forEach>
					</select>
				</p>
				
		   </div>
			 
		  <!--采购明细开始-->
		  <div class="pu_w">
		  		<div class="pu_h"><h3>采购明细</h3></div>
		  		<div class="btn"><button type="button" id ="addProduct">添加</button>
		  		<button type="button" id ="deleteTr">删除</button>
		  		<input type="hidden" id="chkTrue" name="chkTrue">
		  		</div>
		  		<div class="pu_b scroll-x">
			  		<table class="ov" id="productTab">
			  			<tr class="order_hd">
			  				<th width="60px" nowrap="nowrap">序号</th>
			  				<th width="60px" nowrap="nowrap">选择</th>
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
			  				
			  			</tr>
			  			<tr class="append">
			  				<td nowrap="nowrap">1</td>
			  				<td nowrap="nowrap"><input type="checkbox" class="checkbox sm"></td>
			  				<td><input type="text" id="skuId"  style="width:130px;">
			  				<input type="hidden" id="skuId" name="skuId">
			  				<input type="hidden" id="businessProdid" name="businessProdid">
			  				</td>
			  				<td><input type="text" id="barCode" name="barCode" readonly="readonly" style="width:140px;"></td>
			  				<td><input type="text" id="pName" name="pName" readonly="readonly" style="width:130px;"></td>
			  				<td><input type="text" id="format" name="format" readonly="readonly" style="width:100px;"></td>

			  				<td><input type="text" id="unit" name="unit" readonly="readonly"></td>
			  				
			  				<td><input type="text" id="qty" name="qty"></td>
			  				<td><input type="checkbox"  id="isGive" name="isGive"   class="checkbox sm" style="width:110px;" ></td>
			  				<td><input type="text" id="skuPriceTax" name="skuPriceTax"  style="width:80px;"></td>
			  				<td>
			  				<select id='dutyRate' name='dutyRate'>
			  				<c:forEach items='${taxs}' var='taxFee' varStatus="count">
			  				<c:if test="${count.index eq 0 }">
			  				<option value='' dutyCode='0'>请选择</option>
			  				<option value='${taxFee.tax}' dutyCode='${taxFee.code}'>${taxFee.tax}</option>
			  				</c:if>
			  				<c:if test="${count.index ne 0 }">
			  				<option value='${taxFee.tax}' dutyCode='${taxFee.code}'>${taxFee.tax}</option>
			  				</c:if>
					   		</c:forEach>
			  				</select>
			  				<input type="hidden" name="dutyCode"  id="dutyCode" value="">
			  				</td>
			  				<td><input type="text" id="skuPrice" name="skuPrice" readonly="readonly"  style="width:80px;"></td>
			  				<td><input type="text" id="subtotalPrice" name="subtotalPrice" readonly="readonly"  style="text-align: right; width:100px;"></td>
			  				<td><input type="text" id="totalPrice1" name="totalPrice1" readonly="readonly" style="text-align: right"></td>
			  				<td><input type="text" id="currencyType1" name="currencyType1" value="RMB" readonly="readonly"></td>
			  				<td><input type="text" id="exchangeRate1" name="exchangeRate1" value="1" readonly="readonly"></td>
			  				<td><input type="text" id="localPrice" name="localPrice" readonly="readonly" style="width:80px;"></td>
			  				<td><input type="text" id="totalLocalPrice" name="totalLocalPrice" readonly="readonly" style="text-align: right; width:80px;"></td>
			  				<td><input type="text" id="localNuTaxPrice" name="localNuTaxPrice" readonly="readonly" style="width:105px;"></td>
			  				<td><input type="text" id="totalLocalNuTaxPrice" name="totalLocalNuTaxPrice" readonly="readonly" style="text-align: right; width:105px;"></td>
			  				
			  			</tr>
			  		</table>
		  		</div>
		  </div>
		  <!--采购明细结束-->
		
		 <!--运输信息开始-->
		  <div class="pu_hd"><h3>运输信息</h3></div>
		  <div class="xia">
		  	   <p class="p1">
					<span><i class="red">*</i>承运方:</span>
					<select name="shipper" id="shipper" onchange="ship();">
					<c:forEach items="${shipper}" var="shipper">
						<c:if test="${shipper.dictName eq '001'}">
					   <option value="${shipper.dictValue}" selected="selected">${shipper.dictName}</option>
					   </c:if>
					   <c:if test="${shipper.dictName ne '001'}">
					   <option value="${shipper.dictValue}">${shipper.dictName}</option>
					   </c:if>
					 </c:forEach>
					</select>
					<span id='addShipType'>承运方式:</span>
					<select name="shipperType">
					<option value="" selected='selected'>请选择</option>
					<c:forEach items="${transportWay}" var="transportWay">
					   <option value="${transportWay.dictValue}">${transportWay.dictName}</option>
					 </c:forEach>
					</select>
			   </p>

			   <p class="p1 inputnot">
			   	    <span id='addServiceName'>服务商名称:</span>
					<input type="text" class="inp" name="serviceName" id=serviceName onclick="showService()" readonly="readonly" style="background:#d0cdcd">
					<span>服务商编号:</span>
					<input type="text" name="serviceCode" id="serviceCode" readonly="readonly" style="background:#d0cdcd">
			   </p>
				<p class="p1 inputnot">
			   	    <span>联系人:</span>
					<input type="text" class="inp" name="servicePeople" id="servicePeople" readonly="readonly" style="background:#d0cdcd">
					<span>联系电话:</span>
					<input type="text" name="serviceTel" id="serviceTel" readonly="readonly" style="background:#d0cdcd">
			   </p>
			   
			   <p class="p1">
					<span><i class="red">*</i>接货地址:</span>
					<input type="text" class="int" name="receiveAddress" id="receiveAddress">
			   </p>
		  </div>
		  
		  <div class="pu_wrap">
		  <div class="btn">
		  <button type="button" id = "addWareHouse">添加</button>
		  <button type="button" id = "deleteTrKC">删除</button>
		  </div>
		  		<div class="pu_bd">
			  		<table class="ov" id="wareHouseTab">
			  			<tr class="order_hd">
			  				<th width="60px">序号</th>
			  				<th width="60px">选择</th>
			  				<th width="200px"><i class="red">*</i>仓库名称</th>
			  				<th width="340px">地址</th>
			  				<th width="150px">联系人</th>
			  				<th width="150px">电话</th>
			  				<th width="40px" ></th>
			  			</tr>
			  			<tr class="appendWareHouse">
			  				<td>1</td>
			  				<td><input type="checkbox" class="checkbox1 sm" ></td>
			  				<td><input type="text" name="wareHouseName"  onclick="showWareHouse()" readonly="readonly"   style="width:200px;" ></td>
			  				<td><input type="text" name="address" readonly="readonly"  style="width:340px;" ></td>
			  				<td><input type="text" name="contact" style="width:150px;" readonly="readonly" ></td>
			  				<td><input type="text" name="telephone"  style="width:150px;" readonly="readonly"></td>
			  				<td><input type="hidden" name="wareHouseCode" id="wareHouseCode" style="width:40px;" ></td>

			  			</tr>
			  			
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
						<select id="supplyType" style="height: 28px;border: 1px solid #c8c8c8;margin-top:-2px;">
							<option value="1" selected>普通</option>
							<option value="2">海外特卖</option>
						</select>
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