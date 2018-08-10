<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>采购订单登记</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/inventory.css">
   <!-- <link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/purchaseOrder.css">-->
     <script src="<%=path%>/commons/js/my97/WdatePicker.js"></script>
     <script type="text/javascript" src="${path}/commons/js/map.js"></script>
</head>
<script type="text/javascript">

/*function appendNewTr(obj) {
	var th0;
	if($("#sho tr:last").find("td:eq(0)").text()=="")
	{
		th0=1;
	}else{
		th0=parseInt($("#sho tr:last").find("td:eq(0)").text())+1;
	}
	//var html = "<tr><td >"+obj.children('td').eq(0).text()+"</td><td ><input type='checkbox' name='skuName' value='"+obj.children('td').eq(0).text()+"'></td><td >"+obj.children('td').eq(2).text()+"</td><td >"+obj.children('td').eq(3).text()+"</td><td >"+obj.children('td').eq(4).text()+"</td><td >"+obj.children('td').eq(5).text()+"</td><td >"+obj.children('td').eq(6).text()+"</td><td >"+obj.children('td').eq(7).text()+"</td><td ><input type='text' name='allQty' id='allQty"+obj.children('td').eq(0).text()+"'/></td><td >"+obj.children('td').eq(11).text()+"</td><td >"+obj.children('td').eq(8).text()+"</td><td >"+obj.children('td').eq(9).text()+"</td><td >"+obj.children('td').eq(10).text()+"</td><td >"+obj.children('td').eq(12).text()+"</td><td >"+obj.children('td').eq(13).text()+"</td></tr>";
	//var html = "<tr><td>"+obj.children('td').eq(0).text()+"</td><td><input type='checkbox' name='skuName' value='"+obj.children('td').eq(0).text()+"'></td><td><input type='text' id='pcode"+obj.children('td').eq(0).text()+"' name='pcode"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(2).text()+"' readonly='readonly'></td><td><input type='text' id='barCode"+obj.children('td').eq(0).text()+"' name='barCode"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(3).text()+"'readonly='readonly'></td><td><input type='text' id='pname"+obj.children('td').eq(0).text()+"' name='pname"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(4).text()+"'readonly='readonly'></td><td><input type='text' id='format"+obj.children('td').eq(0).text()+"' name='format"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(5).text()+"'readonly='readonly'></td><td><input type='text' id='unit"+obj.children('td').eq(0).text()+"' name='unit"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(6).text()+"'readonly='readonly'></td><td><input type='text' id='stockQty"+obj.children('td').eq(0).text()+"' name='stockQty"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(7).text()+"'readonly='readonly'></td><td><input type='text' id='transferQty"+obj.children('td').eq(0).text()+"' name='transferQty"+obj.children('td').eq(0).text()+"' value=''></td><td><input type='text' id='isgenuine"+obj.children('td').eq(0).text()+"' name='isgenuine"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(11).text()+"'readonly='readonly'></td><td><input type='text' id='batchNumber"+obj.children('td').eq(0).text()+"' name='batchNumber"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(8).text()+"'readonly='readonly'></td><td><input type='text' id='batchId"+obj.children('td').eq(0).text()+"' name='batchId"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(9).text()+"'readonly='readonly'></td><td><input type='text' id='productionDate"+obj.children('td').eq(0).text()+"' name='productionDate"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(10).text()+"'readonly='readonly'></td><td><input type='text' id='skuId"+obj.children('td').eq(0).text()+"' name='skuId"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(12).text()+"'readonly='readonly'></td><td><input type='text' id='pid"+obj.children('td').eq(0).text()+"' name='pid"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(13).text()+"'readonly='readonly'></td></tr>";
	var html = "<tr><td>"+th0+"</td><td><input type='checkbox' class='sm'></td><td><input type='text' readonly='readonly' name='businessNumberChar' id='businessNumberChar"+th0+"' value='"+obj.children('td').eq(2).text()+"'/><td><input type='text' readonly='readonly' name='pcode' id='pcode"+th0+"' value='"+obj.children('td').eq(3).text()+"'/></td><td><input type='text' readonly='readonly' name='pname' id='pname"+th0+"' value='"+obj.children('td').eq(4).text()+"'/></td><td><input type='text' readonly='readonly' name='barCode' id='barCode"+th0+"' value='"+obj.children('td').eq(5).text()+"'/></td><td><input type='text' readonly='readonly' name='format' id='format"+th0+"' value='"+obj.children('td').eq(6).text()+"'/></td><td><input type='text' readonly='readonly' name='unit' id='unit"+th0+"' value='"+obj.children('td').eq(7).text()+"'/></td><td><input type='text' readonly='readonly' name='purchaseQty' id='purchaseQty"+th0+"' value='"+obj.children('td').eq(8).text()+"'/></td><td><input type='text' readonly='readonly' name='receivedQty' id='receivedQty"+th0+"' value='"+obj.children('td').eq(9).text()+"'/></td><td><input type='text' readonly='readonly' name='payQty' id='payQty"+th0+"' value='"+obj.children('td').eq(10).text()+"'/></td><td><input type='text' readonly='readonly' name='receivedPrice' id='receivedPrice"+th0+"' value='"+obj.children('td').eq(11).text()+"'/></td><td><input type='text'  name='localQty' id='localQty"+th0+"' value='0' onBlur=\"sumTotalPrice("+th0+")\"/></td><td><input type='text'  name='price' id='price"+th0+"' value='0' onBlur=\"sumTotalPrice("+th0+")\"/></td><td><input type='text' readonly='readonly' name='localTotalPrice' id='localTotalPrice"+th0+"' value='0'/></td><td><input type='text' name='tax' id='tax"+th0+"' value='0' onBlur=\"sumTaxPrice("+th0+")\"/></td><td><input type='text' readonly='readonly' name='taxPrice' id='taxPrice"+th0+"' value='0'/></td><td><input type='text' readonly='readonly' name='totalPrice' id='totalPrice"+th0+"' value='0'/></td><td><input type='text' readonly='readonly' name='batchNumber' id='batchNumber"+th0+"' value='"+obj.children('td').eq(14).text()+"'/></td><td><input type='text' readonly='readonly' name='inStockTime' id='inStockTime"+th0+"' value='"+obj.children('td').eq(15).text()+"'/></td><td><input type='text' readonly='readonly' name='currencyType' id='currencyType"+th0+"' value='"+obj.children('td').eq(16).text()+"'/></td><td><input type='text' readonly='readonly' name='skuId' id='skuId"+th0+"' value='"+obj.children('td').eq(17).text()+"'/></td><td><input type='text' readonly='readonly' name='purchaseId' id='purchaseId"+th0+"' value='"+obj.children('td').eq(18).text()+"'/></td></tr>";
	//var html = "<tr class='sotClass'><td>"+obj.children('td').eq(0).text().trim()+"</td><td><input type='checkbox' name='skuName' value='"+obj.children('td').eq(0).text().trim()+"'></td><td><input type='text' id='pcode"+obj.children('td').eq(0).text().trim()+"' name='pcode' value='"+obj.children('td').eq(2).text().trim()+"' readonly='readonly'></td><td><input type='text' id='barCode"+obj.children('td').eq(0).text().trim()+"' name='barCode' value='"+obj.children('td').eq(3).text().trim()+"'readonly='readonly'></td><td><input type='text' id='pname"+obj.children('td').eq(0).text().trim()+"' name='pname' value='"+obj.children('td').eq(4).text().trim()+"'readonly='readonly'></td><td><input type='text' id='format"+obj.children('td').eq(0).text().trim()+"' name='format' value='"+obj.children('td').eq(5).text().trim()+"'readonly='readonly'></td><td><input type='text' id='unit"+obj.children('td').eq(0).text().trim()+"' name='unit' value='"+obj.children('td').eq(6).text().trim()+"'readonly='readonly'></td><td><input type='text' id='stockQty"+obj.children('td').eq(0).text().trim()+"' name='stockQty' value='"+obj.children('td').eq(7).text().trim()+"'readonly='readonly'></td><td><input type='text' id='transferQty"+obj.children('td').eq(0).text().trim()+"' name='transferQty'  onblur='onblurInfo("+obj.children('td').eq(0).text().trim()+");'  value=''></td><td><input type='text' id='isgenuine"+obj.children('td').eq(0).text().trim()+"' name='isgenuine' value='"+obj.children('td').eq(11).text().trim()+"'readonly='readonly'></td><td><input type='text' id='batchNumber"+obj.children('td').eq(0).text().trim()+"' name='batchNumber' value='"+obj.children('td').eq(8).text().trim()+"'readonly='readonly'></td><td><input type='text' id='batchId"+obj.children('td').eq(0).text().trim()+"' name='batchId' value='"+obj.children('td').eq(9).text().trim()+"'readonly='readonly'></td><td><input type='text' id='productionDate"+obj.children('td').eq(0).text().trim()+"' name='productionDate' value='"+obj.children('td').eq(10).text().trim()+"'readonly='readonly'></td><td><input type='text' id='skuId"+obj.children('td').eq(0).text().trim()+"' name='skuId' value='"+obj.children('td').eq(12).text().trim()+"'readonly='readonly'></td><td><input type='text' id='pid"+obj.children('td').eq(0).text().trim()+"' name='pid' value='"+obj.children('td').eq(13).text().trim()+"'readonly='readonly'></td></tr>";
	
	return html;
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
}*/
//初始化加载所有服务商
function showService(){
	$.ajax({
		type : "post", 
		url : "${path}/pchaseOrder/findServiceList", 
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


//条件检索服务商
function selService(){
	var pro_array  = new Array();
	if($("#serName").val()!=""&&$("#serName").val()!=undefined)
	{
		pro_array.push("serviceName="+$("#serName").val());
	}
	$.ajax({
		type : "post", 
		url : "${path}/pchaseOrder/findServiceList", 
		dataType:"html",
		data:pro_array.join("&"),
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

//选择服务商之后赋值到文本框中
function loadPurchasePage(){
	var service=$('input[name="serviceSupply"]:checked');
	if(service.length>0){
	var serviceCode = service.attr("serviceCode");
	var serviceName = service.attr("serviceName");
	var serviceContact = service.attr("serviceContact");
	var servicePhone = service.attr("servicePhone");
	$("#supplierName").val(serviceName);
	$("#supplierId").val(serviceCode);
	$("#supplyDiv").hide();
	}else{
		alert("请选择服务商");
	}
}
/*function toNewOrder()
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
	
}*/

function sumTotalPrice(index)
{
	$("#localTotalPrice"+index).val(($("#allocationQty"+index).val()*$("#localPrice"+index).val()).toFixed(2));
	var Tablen=$("#sho tr").length;
	var totalLoacl=0.0;
    for(var i=1;i<Tablen;i++)
    {
		var tr = $("#sho tr").eq(i);
		var st5 = tr.children('td').eq(5).find("input").val();
		if(st5!="")
		{
			totalLoacl+=parseFloat(st5);
		}
		
	}
	$("#invoiceTotalPrice").val(totalLoacl.toFixed(2));
}
function sumTaxPrice(index)
{
	$("#allocationPrice"+index).val((($("#exchangeRate"+index).val()/100)*$("#localTotalPrice"+index).val()).toFixed(2));
	//$("#totalPrice"+index).val((parseFloat($("#taxPrice"+index).val())+parseFloat($("#localTotalPrice"+index).val())).toFixed(2));
	
}
function editInput(index)
{
	if($("#costBox"+index).attr("checked")=="checked")
	{
		$("#allocationQty"+index).attr("readonly",false);
		$("#exchangeRate"+index).attr("readonly",false);
		$("#localPrice"+index).attr("readonly",false);
	}else{
		$("#allocationQty"+index).val("");
		$("#exchangeRate"+index).val("");
		$("#localPrice"+index).val("");
		$("#allocationQty"+index).attr("readonly",true);
		$("#exchangeRate"+index).attr("readonly",true);
		$("#localPrice"+index).attr("readonly",true);
	}
}
function Share(index)
{
	var costName=$("#costName"+index).val();
	var costCode=$("#costCode"+index).val();
	var functionCode=$("#allocationCode"+index).val();
	var functionText=$("#allocationCode"+index).find("option:selected").text();
	var invoiceTotalPrice=$("#localTotalPrice"+index).val();
	var sid=$("#sid").val();
	var href="${path}/purchasecost/setUpCost?costCode="+costCode+"&functionCode="+functionCode+"&sid="+sid+"&invoiceTotalPrice="+invoiceTotalPrice;
	var share=$("#share"+index);
	//var newHalf=encodeURI(href);
	share.attr("href",href);
	share.click();
}
function save()
{
if($("#supplierName").val()=="")
{
	alert("请选择服务商！！！！");
	return ;
}
if($("#invoiceNumber").val()=="")
	{
		alert("请输入发票号码!");
		return;
	}
	if(!checkBoxed())
	{
		alert("请选择商品！！！！！");
		return;
	}
	var reg = /^\+?[1-9][0-9]*$/;
	var req=/^\+{0,1}\d+(\.\d{1,2})?$/;
	var taxreg=/^\+?[1-9][0-9]*$/;
	var Tablen=$("#sho tr").length;
	var chked="";
	for(var i=1;i<Tablen;i++)
    {
		var tr = $("#sho tr").eq(i);
		var chk=tr.children('td').eq(1).find('input[name="costBox"]').attr("checked");
		if(chk=="checked")
		{
			var st3 = tr.children('td').eq(3).find("input").val();
		if(!reg.test(st3))
		{
			alert("第"+i+"行数量请输入正整数!!!!");
			return ;
		}
		var st4 = tr.children('td').eq(4).find("input").val();
		if(!req.test(st4))
		{
			alert("第"+i+"行单价请输入正浮点数!!!!");
			return ;
		}
		var st6 = tr.children('td').eq(6).find("input").val();
		if(!req.test(st6))
		{
			alert("第"+i+"行税率请输入正整数!!!!");
			return ;
		}
		chked+="1,";
		
		}else{
			chked+="0,";
		}
	}
	var data=$("#InvoiceForm").serialize();
	data=data+"&purchase_name="+$("#purchase_entity").find("option:selected").text();
	data=data+"&chk="+chked.substring(0, chked.length-1);
	$.ajax({
		type : "post", 
		url : "doSave", 
		dataType:"text",
		data:data,
		success : function(msg) { 
		if(msg=='OK')
		{
			window.location.href="${path}/purchasecost/findPurchaseCost";
		}else{
			alert("数据异常!");
		}
			
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
		});

}
function checkBoxed()
{
	var Tablen=$("#sho tr").length;
	var chk="";
	for(var i=1;i<Tablen;i++)
	{
		var tr = $("#sho tr").eq(i);
		var ch=tr.children('td').eq(1).find('input[name="costBox"]').attr("checked");
		if(ch=="checked")
		{
			chk=chk+"1";
		}
	}
	if(chk=="")
	{
		return false;
	}else{
		return true;
	}
}
function check()
{
if($("#supplierName").val()=="")
{
	alert("请选择服务商！！！！");
	return ;
}
if($("#invoiceNumber").val()=="")
	{
		alert("请输入发票号码!");
		return;
	}
	if(!checkBoxed())
	{
		alert("请选择商品！！！！！");
		return;
	}
	var reg = /^\+?[1-9][0-9]*$/;
	var req=/^\+{0,1}\d+(\.\d{1,2})?$/;
	var taxreg=/^\+?[1-9][0-9]*$/;
	var Tablen=$("#sho tr").length;
	var chked="";
	for(var i=1;i<Tablen;i++)
    {
		var tr = $("#sho tr").eq(i);
		var chk=tr.children('td').eq(1).find('input[name="costBox"]').attr("checked");
		if(chk=="checked")
		{
			var st3 = tr.children('td').eq(3).find("input").val();
		if(!reg.test(st3))
		{
			alert("第"+i+"行数量请输入正整数!!!!");
			return ;
		}
		var st4 = tr.children('td').eq(4).find("input").val();
		if(!req.test(st4))
		{
			alert("第"+i+"行单价请输入正浮点数!!!!");
			return ;
		}
		var st6 = tr.children('td').eq(6).find("input").val();
		if(!req.test(st6))
		{
			alert("第"+i+"行税率请输入正整数!!!!");
			return ;
		}
		chked+="1,";
		
		}else{
			chked+="0,";
		}
	}
	var data=$("#InvoiceForm").serialize();
	data=data+"&purchase_name="+$("#purchase_entity").find("option:selected").text();
	data=data+"&chk="+chked.substring(0, chked.length-1);
	$.ajax({
		type : "post", 
		url : "doCheck", 
		dataType:"text",
		data:data,
		success : function(msg) { 
		if(msg=='OK')
		{
			window.location.href="${path}/purchasecost/findPurchaseCost";
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
					<a href="javascript:void(0);" onclick="save();">保存</a>
				</div>
			</div>
			<form id="InvoiceForm">
			<div class="xia">
				<p class="p1">
					<span><em>*</em>公司名称：</span>
					<select class="w242" name="purchase_entity" id="purchase_entity"><c:forEach items="${Entity }" var="e"><option value="${e.dictValue }">${e.dictName }</option></c:forEach></select>
					<span>发票类型：</span>
					<input type="text" value="费用发票" readonly="readonly">
				</p>
				<p class="p1">
					<span>发票登记编号：</span>
					<input type="text" name="invoiceChar" id="invoiceChar" value="${code }">
					<input type="hidden" name="sid" id="sid" value="${sid }"/>
					<span>登记日期：</span>
					<input type="text" name="createTime" id="createTime" readonly="readonly" value="${now}">
					<span>制单人：</span>
					<input type="text" name="createBy" id="createBy" readonly="readonly" value="${user}">
				</p>
				<p class="p1">
					<span>服务商名称：</span>
					<input type="text" id="supplierName" name="supplierName" onclick="showService()" readonly="readonly">
					<span>服务商编码：</span>
					<input type="text" id="supplierId" name="supplierId" readonly="readonly">
					<!-- <span class="w140"><em>*</em>账期结算方法：</span>
					<select name="payment_function" id="payment_function"><option value="001">整批计算</option><option value="002">分批计算</option></select> -->
				</p>
				<p class="p1">
					<span>发票号码：</span>
					<input type="text" class="w428" name="invoiceNumber" id="invoiceNumber">
					<!-- <span><em>*</em>是否申请付款：</span>
					<select class="w80" name="isPay" id="isPay"><option value="1">是</option><option value="0">否</option></select> -->
				</p>
				<p class="p1">
					<span>摘要：</span>
					<input type="text" class="w428" name="remark" id="remark">
				</p>
				<p class="p1">
					<span>发票总金额：</span>
					<input type="text" name="invoiceTotalPrice" id="invoiceTotalPrice" readonly="readonly">
					<!-- <span>税额：</span>
					<input type="text" name="invoiceTotalTax" id="invoiceTotalTax">
					<span class="w140">发票本位币总金额：</span>
					<input type="text" name="invoiceLocalPrice" id="invoiceLocalPrice"> -->
				</p>
			</div>
			
			
			<div class="pu_w">
				
	  		<div class="pu_b">
	  			<table id="sho">
	  				<tbody>
	  				<tr class="order_hd">
	  					<th width="40px;">序号</th>
	  					<th width="40px;">选择</th>
	  					<th width="120px;">费用名称</th>
	  					<th width="100px;">数量</th>
	  					<th width="100px;">单价</th>
	  					<th width="100px;">金额</th>
	  					<th width="80px;">税率</th>
	  					<th width="80px;">税额</th>
	  					<th width="140px;">分摊方法</th>
	  					<th width="80px;">操作</th>
	  				</tr>
	  				<c:forEach items="${Cost }" var="c" varStatus="status">
	  				<tr>
	  					<td>${status.index+1 }</td>
	  					<td><input type="checkbox" class="sm" name="costBox" id="costBox${status.index+1 }" onclick="editInput(${status.index+1 });"></td>
		  				<td>
		  				<input type="text" name="costName" id="costName${status.index+1 }" value="${c.costName }" readonly="readonly"/>
		  				<input type="hidden" name="costCode" id="costCode${status.index+1 }" value="${c.code }"/>
		  				</td>
		  				<td><input type="text" name="allocationQty" id="allocationQty${status.index+1 }" value="" readonly="readonly"/></td>
		  				<td><input type="text" name="localPrice" id="localPrice${status.index+1 }" value="" onblur="sumTotalPrice(${status.index+1 });" readonly="readonly"/></td>
		  				<td><input type="text" name="localTotalPrice" id="localTotalPrice${status.index+1 }" value="" readonly="readonly" readonly="readonly"/></td>
		  				<td><input type="text" name="exchangeRate" id="exchangeRate${status.index+1 }" value="" onblur="sumTaxPrice(${status.index+1 });" readonly="readonly"/></td>
		  				<td><input type="text" name="allocationPrice" id="allocationPrice${status.index+1 }" value="" readonly="readonly"/></td>
		  				<td>
		  				<select name="allocationCode" id="allocationCode${status.index+1 }" style="width:120px">
			  				<c:forEach items="${Function }" var="f" >
			  				<option value="${f.dictValue }">${f.dictName }</option>
			  				</c:forEach>
		  				</select>
		  				</td>
		  				
		  				 <td>
			  				<a href="javascript:void(0);" onclick="Share(${status.index+1 });" target="_blank" id="share${status.index+1 }">分摊</a>
		  				</td>
		  			
	  				</tr>
	  				</c:forEach>
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
						<input type="text" id="serName" value="">
						<button type="button" onclick="selService();">查询</button>
						<button type="button" onclick="hideShow();">退出</button>
					</div>
				</div>
				<div class="box2" id="supplierBox">
					
				</div>
				<div class="box3">
					<button type="button" class="bt1" id="supplierclick" onclick="loadPurchasePage()">确定</button>
				</div>
			</div>
		</div>
</body>
</html>