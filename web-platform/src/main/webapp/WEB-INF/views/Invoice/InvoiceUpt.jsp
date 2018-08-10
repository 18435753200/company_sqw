<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>采购订单登记</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/inventory.css">
    <!-- <link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/purchaseOrder.css"> -->
     <script src="<%=path%>/commons/js/my97/WdatePicker.js"></script>
     <script type="text/javascript" src="${path}/commons/js/map.js"></script>
</head>
<script type="text/javascript">

function appendNewTr(obj) {
	var th0;
	if($("#sho tr:last").find("td:eq(0)").text()=="")
	{
		th0=1;
	}else{
		th0=parseInt($("#sho tr:last").find("td:eq(0)").text())+1;
	}
	//var html = "<tr><td >"+obj.children('td').eq(0).text()+"</td><td ><input type='checkbox' name='skuName' value='"+obj.children('td').eq(0).text()+"'></td><td >"+obj.children('td').eq(2).text()+"</td><td >"+obj.children('td').eq(3).text()+"</td><td >"+obj.children('td').eq(4).text()+"</td><td >"+obj.children('td').eq(5).text()+"</td><td >"+obj.children('td').eq(6).text()+"</td><td >"+obj.children('td').eq(7).text()+"</td><td ><input type='text' name='allQty' id='allQty"+obj.children('td').eq(0).text()+"'/></td><td >"+obj.children('td').eq(11).text()+"</td><td >"+obj.children('td').eq(8).text()+"</td><td >"+obj.children('td').eq(9).text()+"</td><td >"+obj.children('td').eq(10).text()+"</td><td >"+obj.children('td').eq(12).text()+"</td><td >"+obj.children('td').eq(13).text()+"</td></tr>";
	//var html = "<tr><td>"+obj.children('td').eq(0).text()+"</td><td><input type='checkbox' name='skuName' value='"+obj.children('td').eq(0).text()+"'></td><td><input type='text' id='pcode"+obj.children('td').eq(0).text()+"' name='pcode"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(2).text()+"' readonly='readonly'></td><td><input type='text' id='barCode"+obj.children('td').eq(0).text()+"' name='barCode"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(3).text()+"'readonly='readonly'></td><td><input type='text' id='pname"+obj.children('td').eq(0).text()+"' name='pname"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(4).text()+"'readonly='readonly'></td><td><input type='text' id='format"+obj.children('td').eq(0).text()+"' name='format"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(5).text()+"'readonly='readonly'></td><td><input type='text' id='unit"+obj.children('td').eq(0).text()+"' name='unit"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(6).text()+"'readonly='readonly'></td><td><input type='text' id='stockQty"+obj.children('td').eq(0).text()+"' name='stockQty"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(7).text()+"'readonly='readonly'></td><td><input type='text' id='transferQty"+obj.children('td').eq(0).text()+"' name='transferQty"+obj.children('td').eq(0).text()+"' value=''></td><td><input type='text' id='isgenuine"+obj.children('td').eq(0).text()+"' name='isgenuine"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(11).text()+"'readonly='readonly'></td><td><input type='text' id='batchNumber"+obj.children('td').eq(0).text()+"' name='batchNumber"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(8).text()+"'readonly='readonly'></td><td><input type='text' id='batchId"+obj.children('td').eq(0).text()+"' name='batchId"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(9).text()+"'readonly='readonly'></td><td><input type='text' id='productionDate"+obj.children('td').eq(0).text()+"' name='productionDate"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(10).text()+"'readonly='readonly'></td><td><input type='text' id='skuId"+obj.children('td').eq(0).text()+"' name='skuId"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(12).text()+"'readonly='readonly'></td><td><input type='text' id='pid"+obj.children('td').eq(0).text()+"' name='pid"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(13).text()+"'readonly='readonly'></td></tr>";
	var html = "<tr><td>"+th0+"</td><td><input type='checkbox' class='sm'></td><td><input type='text' readonly='readonly' name='businessNumberChar' id='businessNumberChar"+th0+"' value='"+obj.children('td').eq(2).text()+"'/><td><input type='text' readonly='readonly' name='pcode' id='pcode"+th0+"' value='"+obj.children('td').eq(3).text()+"'/></td><td><input type='text' readonly='readonly' name='pname' id='pname"+th0+"' value='"+obj.children('td').eq(4).text()+"'/></td><td><input type='text' readonly='readonly' name='barCode' id='barCode"+th0+"' value='"+obj.children('td').eq(5).text()+"'/></td><td><input type='text' readonly='readonly' name='format' id='format"+th0+"' value='"+obj.children('td').eq(6).text()+"'/></td><td><input type='text' readonly='readonly' name='unit' id='unit"+th0+"' value='"+obj.children('td').eq(7).text()+"'/></td><td><input type='text' readonly='readonly' name='purchaseQty' id='purchaseQty"+th0+"' value='"+obj.children('td').eq(8).text()+"'/></td><td><input type='text' readonly='readonly' name='receivedQty' id='receivedQty"+th0+"' value='"+obj.children('td').eq(9).text()+"'/></td><td><input type='text' readonly='readonly' name='payQty' id='payQty"+th0+"' value='"+obj.children('td').eq(10).text()+"'/></td><td><input type='text' readonly='readonly' name='receivedPrice' id='receivedPrice"+th0+"' value='"+obj.children('td').eq(11).text()+"'/></td><td><input type='text'  name='localQty' id='localQty"+th0+"' value='"+obj.children('td').eq(12).text()+"' onBlur=\"sumTotalPrice("+th0+")\"/></td><td><input type='text'  name='price' id='price"+th0+"' value='"+(parseFloat(obj.children('td').eq(13).text())/parseFloat(obj.children('td').eq(12).text())).toFixed(8)+"' onBlur=\"sumTotalPrice("+th0+")\"/></td><td><input type='text' readonly='readonly' name='localTotalPrice' id='localTotalPrice"+th0+"' value='"+obj.children('td').eq(13).text()+"'/></td><td><input type='text' name='tax' id='tax"+th0+"' value='0' onBlur=\"sumTaxPrice("+th0+")\"/></td><td><input type='text' readonly='readonly' name='taxPrice' id='taxPrice"+th0+"' value='0'/></td><td><input type='text' readonly='readonly' name='totalPrice' id='totalPrice"+th0+"' value='0'/></td><td><input type='text' readonly='readonly' name='batchNumber' id='batchNumber"+th0+"' value='"+obj.children('td').eq(14).text()+"'/></td><td><input type='text' readonly='readonly' name='inStockTime' id='inStockTime"+th0+"' value='"+obj.children('td').eq(15).text()+"'/></td><td><input type='text' readonly='readonly' name='currencyType' id='currencyType"+th0+"' value='"+obj.children('td').eq(16).text()+"' etl='"+obj.children('td').eq(17).text()+"'/></td><td><input type='text' readonly='readonly' name='skuId' id='skuId"+th0+"' value='"+obj.children('td').eq(18).text()+"'/></td><td><input type='text' readonly='readonly' name='purchaseId' id='purchaseId"+th0+"' value='"+obj.children('td').eq(19).text()+"'/></td></tr>";
	//var html = "<tr class='sotClass'><td>"+obj.children('td').eq(0).text().trim()+"</td><td><input type='checkbox' name='skuName' value='"+obj.children('td').eq(0).text().trim()+"'></td><td><input type='text' id='pcode"+obj.children('td').eq(0).text().trim()+"' name='pcode' value='"+obj.children('td').eq(2).text().trim()+"' readonly='readonly'></td><td><input type='text' id='barCode"+obj.children('td').eq(0).text().trim()+"' name='barCode' value='"+obj.children('td').eq(3).text().trim()+"'readonly='readonly'></td><td><input type='text' id='pname"+obj.children('td').eq(0).text().trim()+"' name='pname' value='"+obj.children('td').eq(4).text().trim()+"'readonly='readonly'></td><td><input type='text' id='format"+obj.children('td').eq(0).text().trim()+"' name='format' value='"+obj.children('td').eq(5).text().trim()+"'readonly='readonly'></td><td><input type='text' id='unit"+obj.children('td').eq(0).text().trim()+"' name='unit' value='"+obj.children('td').eq(6).text().trim()+"'readonly='readonly'></td><td><input type='text' id='stockQty"+obj.children('td').eq(0).text().trim()+"' name='stockQty' value='"+obj.children('td').eq(7).text().trim()+"'readonly='readonly'></td><td><input type='text' id='transferQty"+obj.children('td').eq(0).text().trim()+"' name='transferQty'  onblur='onblurInfo("+obj.children('td').eq(0).text().trim()+");'  value=''></td><td><input type='text' id='isgenuine"+obj.children('td').eq(0).text().trim()+"' name='isgenuine' value='"+obj.children('td').eq(11).text().trim()+"'readonly='readonly'></td><td><input type='text' id='batchNumber"+obj.children('td').eq(0).text().trim()+"' name='batchNumber' value='"+obj.children('td').eq(8).text().trim()+"'readonly='readonly'></td><td><input type='text' id='batchId"+obj.children('td').eq(0).text().trim()+"' name='batchId' value='"+obj.children('td').eq(9).text().trim()+"'readonly='readonly'></td><td><input type='text' id='productionDate"+obj.children('td').eq(0).text().trim()+"' name='productionDate' value='"+obj.children('td').eq(10).text().trim()+"'readonly='readonly'></td><td><input type='text' id='skuId"+obj.children('td').eq(0).text().trim()+"' name='skuId' value='"+obj.children('td').eq(12).text().trim()+"'readonly='readonly'></td><td><input type='text' id='pid"+obj.children('td').eq(0).text().trim()+"' name='pid' value='"+obj.children('td').eq(13).text().trim()+"'readonly='readonly'></td></tr>";
	return html;
}

function checkAdd(config)
{
	var Tablen=$("#sho tr").length;
	var map = new Map();
	for(var i=1;i<Tablen;i++)
    {
		var tr = $("#sho tr").eq(i);
		var businessNumberChar = tr.children('td').eq(2).find("input").val();
		var skuId = tr.children('td').eq(21).find("input").val();
		var batchNumber = tr.children('td').eq(18).find("input").val();
		map.put(businessNumberChar+skuId,batchNumber);
	}
	if(map.get(config.children('td').eq(2).text()+config.children('td').eq(17).text())==config.children('td').eq(14).text())
	{
		return false;
	}else{
		return true;
	}
}
function doThingsAfterAdd(config){
	
	var len = $("#sho tr").length;
	//alert(config);
	if(checkAdd(config))
	{
		var htm=appendNewTr(config);	
	//alert(htm);
		$("#sho").append(htm);
	}else{
		alert("已经存在此商品信息");
	}
}
function showSupplier(){
	$.ajax({
		type : "post", 
		url : "${path}/pchaseOrder/findSupplierList", 
		dataType:"html",
		success : function(msg) { 
			$("#supplierBox").html(msg);
			$("#supplyDiv").show();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	});
}
function hideShow(){
	

	$(".alert_bu").hide();
	$(".alert_c").hide();
}
function showSupplierByName(){
	var supplierName = $.trim($("#suppilerName1").val());
	$.ajax({
		type : "post", 
		url : "${path}/pchaseOrder/findSupplierList", 
		dataType:"html",
		data:{"supplierName":supplierName},
		success : function(msg) { 
			$("#supplierBox").html(msg);
			$("#supplyDiv").show();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	}); 
}
function loadSupplier(){
	var checked = $('input[name="service"]:checked');
	if(checked.length>0){
		var contact = checked.attr("contact");
		var phone = checked.attr("phone");
		var supplierId = checked.attr("supplierId");
		var name = checked.val();

		$("#supplierName").val(name);
		$("#supplierId").val(supplierId);

		$("#supplyDiv").hide();
	}else{
		alert("请务必勾选供应商!");
	}
}
function toNewOrder()
{
	if($("#supplierName").val()=="")
	{
		alert("请选择供应商！！！！");
		return ;
	}
	showDialog("${path}/Invoice/saveInvoiceOrder");
	//window.location.href="${path}/Invoice/saveInvoiceOrder";
}
function showDialog(url)
{
	var isChrome = window.navigator.userAgent.indexOf("Chrome") !=-1;
	window.open(url,"登记发票","height="+window.screen.height*0.8+", width="+window.screen.width*0.8+", top="+(window.screen.availHeight-30-window.screen.height*0.8)/2+", left="+ (window.screen.availWidth-10-window.screen.width*0.8)/2+", toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");
	
}

function sumTotalPrice(index)
{
	$("#localTotalPrice"+index).val(($("#localQty"+index).val()*$("#price"+index).val()).toFixed(2));
	sumTaxPrice(index);
}
function sumTaxPrice(index)
{
	$("#taxPrice"+index).val((($("#tax"+index).val()/100)*$("#localTotalPrice"+index).val()).toFixed(2));
	$("#totalPrice"+index).val((parseFloat($("#taxPrice"+index).val())+parseFloat($("#localTotalPrice"+index).val())).toFixed(2));
	sumTaxPriceDetail(index);
}

function sumTaxPrice_blur(index){
	$("#totalPrice"+index).val((parseFloat($("#taxPrice"+index).val())+parseFloat($("#localTotalPrice"+index).val())).toFixed(2));
	sumTaxPriceDetail(index);
}

function sumTaxPriceDetail(index){
	var Tablen=$("#sho tr").length;
	var totalLoacl=0.0;
	var totalTax=0.0;
	var totalPrice=0.0;
    for(var i=1;i<Tablen;i++)
    {
		var tr = $("#sho tr").eq(i);
		var st14 = tr.children('td').eq(14).find("input").val();
		var st20 = tr.children('td').eq(20).find("input").attr("etl");
		totalLoacl+=parseFloat(st14);
		var st16 = tr.children('td').eq(16).find("input").val();
		totalTax+=parseFloat(st16);
		var st17 = tr.children('td').eq(17).find("input").val();
		totalPrice+=parseFloat(st17)*parseFloat(st20);
	}
	$("#invoiceTotalPrice").val(parseFloat(totalLoacl.toFixed(2))+parseFloat(totalTax.toFixed(2)));
	$("#invoiceTotalTax").val(totalTax.toFixed(2));
	$("#invoiceLocalPrice").val(totalPrice.toFixed(2));
}

function update()
{
if($("#invoiceNumber").val()=="")
	{
		alert("请输入发票号码!");
		return;
	}
var reg = /^\+?[1-9][0-9]*$/;
	var req=/^\+{0,1}\d+(\.\d{1,2})?$/;
	var taxreg=/^\+?[1-9][0-9]*$/;
	var Tablen=$("#sho tr").length;
	for(var i=1;i<Tablen;i++)
    {
		var tr = $("#sho tr").eq(i);
		var st12 = tr.children('td').eq(12).find("input").val();
		if(st12!="0"&&!reg.test(st12))
		{
			alert("第"+i+"行本次结算数量请输入正整数!!!!");
			return ;
		}
		var st13 = tr.children('td').eq(13).find("input").val();
		/*if(st12!="0"&&!req.test(st13))
		{
			alert("第"+i+"行结算单价请输入正浮点数!!!!");
			return ;
		}*/
		var st15 = tr.children('td').eq(15).find("input").val();
		if(st12!="0"&&!req.test(st15))
		{
			alert("第"+i+"行税率请输入正整数!!!!");
			return ;
		}
		var st9 = tr.children('td').eq(9).find("input").val();
		var st10 = tr.children('td').eq(10).find("input").val();
		/*if((parseInt(st9)-parseInt(st10))<parseInt(st12))
		{
			alert("第"+i+"行本次填写数量不能大于已到数量与已结算数量的差额");
			return;
		}*/
	}
	var data=$("#InvoiceForm").serialize();
	//data=data+"&purchase_name="+$("#purchase_entity").find("option:selected").text();
	
	$.ajax({
		type : "post", 
		url : "doUpdate", 
		dataType:"text",
		data:data,
		success : function(msg) { 
		if(msg=='OK')
		{
			window.location.href="${path}/Invoice/findInvoiceOrder";
		}else{
			alert("数据异常!");
		}
			
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
		});
}
function check()
{
var reg = /^\+?[1-9][0-9]*$/;
	var req=/^\+{0,1}\d+(\.\d{1,2})?$/;
	var taxreg=/^\+?[1-9][0-9]*$/;
	var Tablen=$("#sho tr").length;
	if($("#invoiceNumber").val()=="")
	{
		alert("请输入发票号码!");
		return;
	}
	for(var i=1;i<Tablen;i++)
    {
		var tr = $("#sho tr").eq(i);
		var st12 = tr.children('td').eq(12).find("input").val();
		if(st12!="0"&&!reg.test(st12))
		{
			alert("第"+i+"行本次结算数量请输入正整数!!!!");
			return ;
		}
		var st13 = tr.children('td').eq(13).find("input").val();
		/*if(st12!="0"&&!req.test(st13))
		{
			alert("第"+i+"行结算单价请输入正浮点数!!!!");
			return ;
		}*/
		var st15 = tr.children('td').eq(15).find("input").val();
		if(st12!="0"&&!req.test(st15))
		{
			alert("第"+i+"行税率请输入正整数!!!!");
			return ;
		}
		var st9 = tr.children('td').eq(9).find("input").val();
		var st10 = tr.children('td').eq(10).find("input").val();
		/*if((parseInt(st9)-parseInt(st10))<parseInt(st12))
		{
			alert("第"+i+"行本次填写数量不能大于已到数量与已结算数量的差额");
			return;
		}*/
	}
	var data=$("#InvoiceForm").serialize();
	//data=data+"&purchase_name="+$("#purchase_entity").find("option:selected").text();
	$.ajax({
		type : "post", 
		url : "doCheck", 
		dataType:"text",
		data:data,
		success : function(msg) { 
		if(msg=='OK')
		{
			window.location.href="${path}/Invoice/findInvoiceOrder";
		}else{
			alert("数据异常!");
		}
			
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
		});
}
</script>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<div class="center">
		<div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp" %>
		</div>
		
		<!-- 左边 end -->
		<div class="right">
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp; </p>
					<p>商品储存&nbsp;&gt;&nbsp; </p>
					<p class="c1">采购订单登记</p>
				</div>
		    </div>
			
			<div class="pu_hd">
				<div class="btn" style="text-align:right;">
					<!-- <a href="javascript:0">修改</a> -->
					<a href="javascript:void(0);" onclick="update();">保存</a>
					<a href="javascript:void(0);" onclick="check();">审核</a>
				</div>
			</div>
			<form id="InvoiceForm">
			<div class="xia">
				<p class="p1">
					<span><em>*</em>公司名称：</span>
					<!-- <select class="w242" name="purchase_entity" id="purchase_entity"><c:forEach items="${Entity }" var="e"><option value="${e.dictValue }">${e.dictName }</option></c:forEach></select> -->
					<input type="text" name="purchase_name" id="purchase_name" value="${orderDto.companyName }"/>
					<input type="hidden" name="purchase_entity" id="purchase_entity" value="${orderDto.companyCode }"/>
					<input type="hidden" name="sid" id="sid" value="${orderDto.sid }"/>
				</p>
				<p class="p1">
					<span>发票登记编号：</span>
					<input type="text" name="invoiceChar" id="invoiceChar" value="${orderDto.invoiceChar }">
					<span>登记日期：</span>
					<input type="text" name="createTime" id="createTime" readonly="readonly" value="<fmt:formatDate value="${orderDto.createTime}"  pattern="yyyy-MM-dd"/>">
					<span>制单人：</span>
					<input type="text" name="createBy" id="createBy" readonly="readonly" value="${orderDto.createby}">
				</p>
				<p class="p1">
					<span>供应商名称：</span>
					<input type="text" id="supplierName" name="supplierName"  readonly="readonly" value="${orderDto.supplierName}">
					<span>供应商编码：</span>
					<input type="text" id="supplierId" name="supplierId" readonly="readonly" value="${orderDto.supplierCode}">
					<span class="w140"><em>*</em>账期结算方法：</span>
					<!-- <select name="payment_function" id="payment_function"><option value="001">整批计算</option><option value="002">分批计算</option></select> -->
					<c:if test="${orderDto.balanceType=='001'}"><input type="text" id="payment_name" name="payment_name" readonly="readonly" value="分批计算"></c:if>
					<c:if test="${orderDto.balanceType=='002'}"><input type="text" id="payment_name" name="payment_name" readonly="readonly" value="整批计算"></c:if>
					<input type="hidden" id="payment_function" name="payment_function" readonly="readonly" value="${orderDto.balanceType}">
				</p>
				<p class="p1">
					<span>发票号码：</span>
					<input type="text" class="w428" name="invoiceNumber" id="invoiceNumber" value="${orderDto.invoiceNumber}">
					<span><em>*</em>是否申请付款：</span>
					<select class="w80" name="isPay" id="isPay"><c:if test="${orderDto.isPay==1 }"><option value="1" selected="selected">是</option><option value="0">否</option></c:if>
					<c:if test="${orderDto.isPay==0 }"><option value="0" selected="selected">否</option><option value="1" selected="selected">是</option></c:if>
					</select>
				</p>
				<p class="p1">
					<span>摘要：</span>
					<input type="text" class="w428" name="remark" id="remark" value="${orderDto.remark }">
				</p>
				<p class="p1">
					<span>发票总金额：</span>
					<input type="text" name="invoiceTotalPrice" id="invoiceTotalPrice" value="${orderDto.invoiceTotalPrice }">
					<span>税额：</span>
					<input type="text" name="invoiceTotalTax" id="invoiceTotalTax" value="${orderDto.invoiceTotalTax }">
					<span class="w140">发票本位币总金额：</span>
					<input type="text" name="invoiceLocalPrice" id="invoiceLocalPrice" value="${orderDto.invoiceLocalPrice }">
				</p>
			</div>
			
			<div class="pu_h">
				<h3>订单商品列表：</h3>
				<div class="btn">
				       
				       <a href="javascript:void(0);" onclick="toNewOrder();">添加</a>
				   </div>
			</div>
			<div class="pu_w">
				
	  		<div class="pu_b">
	  			<table id="sho">
	  				<tbody>
	  				<tr class="order_hd">
	  					<th width="40px;">序号</th>
	  					<th width="40px;">选择</th>
	  					<th width="110px;">订单编号</th>
	  					<th width="110px;">商品编码</th>
	  					<th width="110px;">商品名称</th>

	  					<th width="110px;">商品条码</th>
	  					<th width="110px;">规格</th>
	  					<th width="50px;">单位</th>
	  					<th width="80px;">订单数量</th>
	  					<th width="100px;">已入库数量</th>

	  					<th width="100px;">已结算数量</th>
	  					<th width="100px;">已结算金额</th>
	  					<th width="100px;">本次结算数量</th>
						<th width="100px;">结算单价</th>
						<th width="100px;">本次结算金额</th>

						<th width="50px;">税率(%)</th>
						<th width="50px;">税额</th>
						<th width="100px;">总金额</th>
	  					<th width="110px;">批次号</th>
	  					<th width="80px;">入库日期</th>

	  					<th width="50px;">币别</th>
	  					<th >SKUID</th>
	  					<th >ORDERID</th>
	  				</tr>
	  				<c:forEach items="${orderDto.itemDtos }" var="item" varStatus="status">
	  				<tr>
	  					<td>${status.index+1 }</td>
		  				<td><input type="checkbox" class="sm"></td>
		  				<td><input type="text" readonly="readonly" name="businessNumberChar" id="businessNumberChar${status.index+1 }" value="${item.businessNumberChar }"/></td>
		  				<td><input type="text" readonly="readonly" name="pcode" id="pcode${status.index+1 }" value="${item.pcode }"/></td>
		  				<td><input type="text" readonly="readonly" name="pname" id="pname${status.index+1 }" value="${item.pname }"/></td>
		  				
		  				<td><input type="text" readonly="readonly" name="barCode" id="barCode${status.index+1 }" value="${item.barCode }"/></td>
		  				<td><input type="text" readonly="readonly" name="format" id="format${status.index+1 }" value="${item.format }"/></td>
		  				<td><input type="text" readonly="readonly" name="unit" id="unit${status.index+1 }" value="${item.unit }"/></td>
		  				<td><input type="text" readonly="readonly" name="purchaseQty" id="purchaseQty${status.index+1 }" value="${item.purchaseQty }"/></td>
		  				<td><input type="text" readonly="readonly" name="receivedQty" id="receivedQty${status.index+1 }" value="${item.receivedQty }"/></td>
		  				
		  				<td><input type="text" readonly="readonly" name="payQty" id="payQty${status.index+1 }" value="${item.payQty }"/></td>
		  				<td><input type="text" readonly="readonly" name="receivedPrice" id="receivedPrice${status.index+1 }" value="${item.receivedPrice }"/></td>
		  				<td><input type="text" name="localQty" id="localQty${status.index+1 }" value="${item.localQty }" onBlur="sumTotalPrice(${status.index+1 })"/></td>
		  				<td><input type="text" name="price" id="price${status.index+1 }" value="${item.price }" onBlur="sumTotalPrice(${status.index+1 })"/></td>
		  				<td><input type="text" name="localTotalPrice" id="localTotalPrice${status.index+1 }" value="${item.localTotalPrice }"  onBlur="sumTaxPrice(${status.index+1 })"/></td>
		  				
		  				<td><input type="text" name="tax" id="tax${status.index+1 }" value="${item.tax }" onBlur="sumTaxPrice(${status.index+1 })"/></td>
		  				<td><input type="text" name="taxPrice" id="taxPrice${status.index+1 }" value="${item.taxPrice }" onBlur="sumTaxPrice_blur(${status.index+1 })"/></td>
		  				<td><input type="text" readonly="readonly" name="totalPrice" id="totalPrice${status.index+1 }" value="${item.totalPrice }"/></td>
		  				<td><input type="text" readonly="readonly" name="batchNumber" id="batchNumber${status.index+1 }" value="${item.batchNumber }"/></td>
		  				<td><input type="text" readonly="readonly" name="inStockTime" id="inStockTime${status.index+1 }" value="<fmt:formatDate value="${item.inStockTime }"  pattern="yyyy-MM-dd"/>"/></td>
		  				
		  				<td><input type="text" readonly="readonly" name="currencyType" id="currencyType${status.index+1 }" value="${item.currencyType }" etl="${item.exchangeRate }"/></td>
		  				<td><input type="text" readonly="readonly" name="skuId" id="skuId${status.index+1 }" value="${item.skuId }"/></td>
		  				<td><input type="text" readonly="readonly" name="purchaseId" id="purchaseId${status.index+1 }" value="${item.purchaseId }"/></td>
	  				</tr>
	  				</c:forEach>
	  				<!-- 
	  				<tr>
	  					<td>1</td>
		  				<td><input type="checkbox" class="sm"></td>
		  				<td><input type="text" readonly="readonly" name="businessNumberChar" id="businessNumberChar"/></td>
		  				<td><input type="text" readonly="readonly" name="pcode" id="pcode"/></td>
		  				<td><input type="text" readonly="readonly" name="pname" id="pname"/></td>
		  				<td><input type="text" readonly="readonly" name="barCode" id="barCode"/></td>
		  				<td><input type="text" readonly="readonly" name="format" id="format"/></td>
		  				<td><input type="text" readonly="readonly" name="unit" id="unit"/></td>
		  				<td><input type="text" readonly="readonly" name="purchaseQty" id="purchaseQty"/></td>
		  				<td><input type="text" readonly="readonly" name="receivedQty" id="receivedQty"/></td>
		  				<td><input type="text" readonly="readonly" name="payQty" id="payQty"/></td>
		  				<td><input type="text" readonly="readonly" name="receivedPrice" id="receivedPrice"/></td>
		  				<td><input type="text"  name="localQty" id="localQty"/></td>
		  				<td><input type="text"  name="price" id="price"/></td>
		  				<td><input type="text" readonly="readonly" name="localTotalPrice" id="localTotalPrice"/></td>
		  				<td><input type="text" readonly="readonly" name="tax" id="tax"/></td>
		  				<td><input type="text" readonly="readonly" name="taxPrice" id="taxPrice"/></td>
		  				<td><input type="text" readonly="readonly" name="totalPrice" id="totalPrice"/></td>
		  				<td><input type="text" readonly="readonly" name="batchNumber" id="batchNumber"/></td>
		  				<td><input type="text" readonly="readonly" name="inStockTime" id="inStockTime"/></td>
		  				<td><input type="text" readonly="readonly" name="currencyType" id="currencyType"/></td>
		  				<td><input type="text" readonly="readonly" name="skuId" id="skuId"/></td>
		  				<td><input type="text" readonly="readonly" name="purchaseId" id="purchaseId"/></td>
	  				</tr>
	  				 -->
	  			</tbody>
	  			</table>
	  		</div>
        </div>


		</div>
	</div>
	
	</form>
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
</body>
</html>