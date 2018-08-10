<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>商品储存_采购对齐</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/Storage.css">
</head>
<script type="text/javascript">

function showOrder()
{
			$.ajax({
		type : "post", 
		url : "PChaseOrderList", 
		dataType:"html",
		success : function(msg) { 
			$("#orderbox").html(msg);
			$("#ChaseOrderDiv").show();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	}); 
	
}

function showProduct()
{
			$.ajax({
		type : "post", 
		url : "ProductList", 
		dataType:"html",
		success : function(msg) { 
			$("#productbox").html(msg);
			$("#productDiv").show();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	}); 
	
}
function selOrder()
{
	var pro_array  = new Array();
	if($("#ChaseOrderId").val()!=""&&$("#ChaseOrderId").val()!=undefined)
	{
		pro_array.push("businessNumber="+$("#ChaseOrderId").val());
	}
	$.ajax({
		type : "post", 
		url : "PChaseOrderList", 
		dataType:"html",
		data:pro_array.join("&"),
		success : function(msg) { 
			$("#orderbox").html(msg);
			$("#ChaseOrderDiv").show();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	}); 
	
}

function selProduct()
{
	var pro_array  = new Array();
	if($("#productId").val()!=""&&$("#productId").val()!=undefined)
	{
		pro_array.push("skuName="+$("#productId").val());
	}
	$.ajax({
		type : "post", 
		url : "ProductList", 
		dataType:"html",
		data:pro_array.join("&"),
		success : function(msg) { 
			$("#productbox").html(msg);
			$("#productDiv").show();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	}); 
	
}
function loadOrderPage()
{
	var service=$('input[name="ChaseOrder"]:checked').val();
	$("#businessNumber").val(service);
	$("#ChaseOrderDiv").hide();
}
function loadProductPage()
{
	var service=$('input[name="productrad"]:checked').val();
	$("#skuId").val(service.split(',')[0]);
	$("#barCode").val(service.split(',')[1]);
	$("#pname").val(service.split(',')[2]);
	$("#productDiv").hide();
}
function showPurchaseList(){
	var pro_array  = new Array();
	if($("#businessNumberChar").val()!=""&&$("#businessNumberChar").val()!=undefined)
	{
		pro_array.push("businessNumberChar="+$("#businessNumberChar").val());
	}
	if($("#skuId").val()!=""&&$("#skuId").val()!=undefined)
	{
		pro_array.push("skuId="+$("#skuId").val());
	}
	if($("#barCode").val()!=""&&$("#barCode").val()!=undefined)
	{
		pro_array.push("barCode="+$("#barCode").val());
	}
	if($("#pname").val()!=""&&$("#pname").val()!=undefined)
	{
		pro_array.push("pname="+$("#pname").val());
	}
	if($("#status").val()!=""&&$("#status").val()!=undefined)
	{
		pro_array.push("status="+$("#status").val());
	}
	$.ajax({
		type : "post", 
		url : "findPurchaseBuyList", 
		dataType:"html",
		data:pro_array.join("&"),
		success : function(msg) { 
			$("#Purchase").html(msg);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	}); 
}
function clickSubmit(page)
{
	var pro_array  = new Array();
	if($("#businessNumberChar").val()!=""&&$("#businessNumberChar").val()!=undefined)
	{
		pro_array.push("businessNumberChar="+$("#businessNumberChar").val());
	}
	if($("#skuId").val()!=""&&$("#skuId").val()!=undefined)
	{
		pro_array.push("skuId="+$("#skuId").val());
	}
	if($("#barCode").val()!=""&&$("#barCode").val()!=undefined)
	{
		pro_array.push("barCode="+$("#barCode").val());
	}
	if($("#pname").val()!=""&&$("#pname").val()!=undefined)
	{
		pro_array.push("pname="+$("#pname").val());
	}
	if($("#status").val()!=""&&$("#status").val()!=undefined)
	{
		pro_array.push("status="+$("#status").val());
	}
	if(page!=""&&page!=undefined)
	{
		pro_array.push("page="+page);
	}
	$.ajax({
		type : "post", 
		url : "findPurchaseBuyList", 
		dataType:"html",
		data:pro_array.join("&"),
		success : function(msg) { 
			$("#Purchase").html(msg);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	}); 
}
function savePurchase(){
	
	var str="";
	var obj=document.getElementsByName('buss');
	for(var i=0; i<obj.length; i++){    
	    if(obj[i].checked) str+=obj[i].value+':';  //如果选中，将value添加到变量s中    
	 }
	 str=str.substring(0, str.length-1);
	if(str=="")
	{
		alert("请选择采购订单!");
		return;
	}else{
		var strlist=str.split(":");
		var message="";
		for(var i=0;i<strlist.length;i++)
		{
			var lastEditBy=strlist[i].split(",")[5];
			var qty=strlist[i].split(",")[2];
			var qtyReceived=strlist[i].split(",")[3];
			var errorQty=strlist[i].split(",")[4];
			var count=parseInt(qty)-parseInt(qtyReceived)-parseInt(errorQty);
			if(count>0)
			{
				message=message+lastEditBy+"采购单差异数量≠采购数量-实际入库数量,";
			}
		}
		if(message=="")
		{
			$.ajax({
		type : "post", 
		url : "savePurchaseStatus", 
		dataType:"text",
		data:"id="+str,
		success : function(msg) {
/* 		if(msg=='OK')
		{
			alert("采购订单已到齐！");	
		}else{
			alert("操作失败！");
		}  */
			alert(msg);
			
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	});
		}else{
			if(confirm(message+"确定要提交吗?"))
 			{
 				$.ajax({
		type : "post", 
		url : "savePurchaseStatus", 
		dataType:"text",
		data:"id="+str,
		success : function(msg) {
/* 		if(msg=='OK')
		{
			alert("采购订单已到齐！");	
		}else{
			alert("操作失败！");
		}  */
			alert(msg);
			
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	});
 			}
		}
	}
	
}
</script>
<body>
<!-- 导航 start -->
	 <%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 end -->
	<div class="center">
	<!-- 左边 start -->
	 <div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp" %>
		</div>
		
		<!-- 左边 end -->
		<div class="right">
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp; </p>
					<p>商品储存&nbsp;&gt;&nbsp; </p>
					<p class="c1">采购到齐</p>
				</div>
	        </div>
			
			<div class="xia">
				<p class="p1">
					<span>采购订单号:</span>
					<input type="text" name="businessNumberChar" id="businessNumberChar" >
				</p>
				<p class="p1">
					<span>商品ID:</span>
					<input type="text" name="skuId" id="skuId" >
					<span>商品条码:</span>
				    <input type="text" name="barCode" id="barCode">
				    <span>商品名称:</span>
				    <input type="text" name="pname" id="pname">
				</p>
				<p class="p1">
					<span>状态:</span>
					<select name="status" id="status">
					<option value="1">未到齐</option>
					<option value="50">已到齐</option>
					</select>
				</p>
				<p class="p2">
					<button type="text" onclick="showPurchaseList();">查询</button>
					<button type="text" onclick="savePurchase();">采购到齐</button>
				</p>
			</div>
			
			<div id="Purchase">
			
			</div>

	        <!--  -->

		</div>

		


	</div>
	
	
	
	
	<div class="alert_bu" id="ChaseOrderDiv" style="display: none;";>

  <div class="bg"></div>
  
	<div class="wrap">
	
		<div class="pic"></div>
		
		<div class="box1">
		
			<div id="boxtitle">
			
				<h2>选择采购单.</h2>
				
			</div>
			
			<div id="suppliernametext" style="display: block;">
			
				<input type="text" id="ChaseOrderId">
				
				<button type="button" onclick="selOrder();">查询</button>
			
			</div>
			
		</div>
		
		<div class="box2" id="orderbox">
			
		</div>
		
		<div class="box3">
		
				
			
				<button type="button" class="bt1" id="warehouseclick" style="display: none;">确定</button>
				
			
			
				
			
				<button type="button" class="bt1" id="supplierclick" style="" onclick="loadOrderPage()">确定</button>
			
			
			
		</div>
		
	</div>
  
</div>



	<div class="alert_bu" id="productDiv" style="display: none;";>

  <div class="bg"></div>
  
	<div class="wrap">
	
		<div class="pic"></div>
		
		<div class="box1">
		
			<div id="boxtitle">
			
				<h2>选择商品.</h2>
				
			</div>
			
			<div id="suppliernametext" style="display: block;">
			
				<input type="text" id="productId">
				
				<button type="button" onclick="selProduct();">查询</button>
			
			</div>
			
		</div>
		
		<div class="box2" id="productbox">
			
		</div>
		
		<div class="box3">
		
				
			
				<button type="button" class="bt1" id="warehouseclick" style="display: none;">确定</button>
				
			
			
				
			
				<button type="button" class="bt1" id="supplierclick" style="" onclick="loadProductPage()">确定</button>
			
			
			
		</div>
		
	</div>
  
</div>
<%@include file="/WEB-INF/views/include/foot.jsp"%>
</body>
</html>