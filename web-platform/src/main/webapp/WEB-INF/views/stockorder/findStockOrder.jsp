<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>商品储存_入库单</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/Storage.css">
</head>
<script src="<%=path%>/commons/js/my97/WdatePicker.js"></script>
<script type="text/javascript">
function showService()
{
			$.ajax({
		type : "post", 
		url : "findServiceList", 
		dataType:"html",
		success : function(msg) { 
			$("#servicebox").html(msg);
			$("#serviceDiv").show();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	}); 
	
}
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

function showNotific()
{
			$.ajax({
		type : "post", 
		url : "NotificList", 
		dataType:"html",
		success : function(msg) { 
			$("#Notificbox").html(msg);
			$("#NotificDiv").show();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	}); 
	
}
function selService()
{
	var pro_array  = new Array();
	if($("#serName").val()!=""&&$("#serName").val()!=undefined)
	{
		pro_array.push("serviceName="+$("#serName").val());
	}
	$.ajax({
		type : "post", 
		url : "findServiceList", 
		dataType:"html",
		data:pro_array.join("&"),
		success : function(msg) { 
			$("#servicebox").html(msg);
			$("#serviceDiv").show();
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
function selNotific()
{
	var pro_array  = new Array();
	if($("#NotificId").val()!=""&&$("#NotificId").val()!=undefined)
	{
		pro_array.push("NotificId="+$("#NotificId").val());
	}
	$.ajax({
		type : "post", 
		url : "NotificList", 
		dataType:"html",
		data:pro_array.join("&"),
		success : function(msg) { 
			$("#Notificbox").html(msg);
			$("#NotificDiv").show();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	}); 
	
}
function loadPurchasePage()
{
	var service=$('input[name="service"]:checked').val();
	var services=service.split(",");
	$("#serviceName").val(services[1]);
	$("#serviceDiv").hide();
}
function loadOrderPage()
{
	var service=$('input[name="ChaseOrder"]:checked').val();
	$("#businessNumber").val(service);
	$("#ChaseOrderDiv").hide();
}
function loadNotificPage()
{
	var service=$('input[name="Notific"]:checked').val();
	$("#notificationId").val(service);
	$("#NotificDiv").hide();
}

function addshiporder()
{
	window.location.target="blank";
	window.location.href="<%=path%>/stockorder/saveStockOrder";
}

function showshipList()
{
	var pro_array  = new Array();
	if($("#notificationId").val()!=""&&$("#notificationId").val()!=undefined)
	{
		pro_array.push("notificationId="+$("#notificationId").val());
	}
	if($("#businessNumber").val()!=""&&$("#businessNumber").val()!=undefined)
	{
		pro_array.push("businessNumber="+$("#businessNumber").val());
	}
	if($("#startTime").val()!=""&&$("#startTime").val()!=undefined)
	{
		pro_array.push("startTime="+$("#startTime").val());
	}
	if($("#endTime").val()!=""&&$("#endTime").val()!=undefined)
	{
		pro_array.push("endTime="+$("#endTime").val());
	}
	if($("#serviceName").val()!=""&&$("#serviceName").val()!=undefined)
	{
		pro_array.push("serviceName="+$("#serviceName").val());
	}
	if($("#receiveAddress").val()!=""&&$("#receiveAddress").val()!=undefined)
	{
		pro_array.push("receiveAddress="+$("#receiveAddress").val());
	}
	if(($("#startTime").val()!=""&&$("#startTime").val()!=undefined)&&$("#endTime").val()!=""&&$("#endTime").val()!=undefined)
		{
		 	var d1 = new Date($("#startTime").val().replace(/\-/g, "\/"));  
 			var d2 = new Date($("#endTime").val().replace(/\-/g, "\/"));  
			if(d1 >=d2)
			{
				alert("开始时间不能大于等于结束时间！");  
				return;
			}
		}
	$.ajax({
		type : "post", 
		url : "findStockOrderList", 
		dataType:"html",
		data:pro_array.join("&"),
		success : function(msg) { 
			$("#stockList").html(msg);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	});
	
	    
}

function clickSubmit(page)
{
	var pro_array  = new Array();
	if($("#notificationId").val()!=""&&$("#notificationId").val()!=undefined)
	{
		pro_array.push("notificationId="+$("#notificationId").val());
	}
	if($("#businessNumber").val()!=""&&$("#businessNumber").val()!=undefined)
	{
		pro_array.push("businessNumber="+$("#businessNumber").val());
	}
	if($("#startTime").val()!=""&&$("#startTime").val()!=undefined)
	{
		pro_array.push("startTime="+$("#startTime").val());
	}
	if($("#endTime").val()!=""&&$("#endTime").val()!=undefined)
	{
		pro_array.push("endTime="+$("#endTime").val());
	}
	if($("#serviceName").val()!=""&&$("#serviceName").val()!=undefined)
	{
		pro_array.push("serviceName="+$("#serviceName").val());
	}
	if($("#receiveAddress").val()!=""&&$("#receiveAddress").val()!=undefined)
	{
		pro_array.push("receiveAddress="+$("#receiveAddress").val());
	}
	if(page!=""&&page!=undefined)
	{
		pro_array.push("page="+page);
	}
	if(($("#startTime").val()!=""&&$("#startTime").val()!=undefined)&&$("#endTime").val()!=""&&$("#endTime").val()!=undefined)
		{
		 	var d1 = new Date($("#startTime").val().replace(/\-/g, "\/"));  
 			var d2 = new Date($("#endTime").val().replace(/\-/g, "\/"));  
			if(d1 >=d2)
			{
				alert("开始时间不能大于等于结束时间！");  
				return;
			}
		}
	$.ajax({
		type : "post", 
		url : "findStockOrderList", 
		dataType:"html",
		data:pro_array.join("&"),
		success : function(msg) { 
			$("#stockList").html(msg);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	});
	
}
function writePDF(oo)
{
	var obj=document.getElementsByName('OrderItem');
    var s='';    
    for(var i=0; i<obj.length; i++){    
    if(obj[i].checked){
    s+=obj[i].value+',';  //如果选中，将value添加到变量s中        
    }
    }
    s=s.substring(0, s.length-1);
     if(s=="")
     {
     	alert("请选择入库通知记录!");
     	return ;
     }
     var url="${path}/stockorder/StockOrderExportPDF?id="+s;
     oo.attr("href",url);
     oo.attr("target","_blank");
     oo.click();
}
</script>
<body>
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
					<p class="c1">入库单</p>
				</div>
	        </div>
			
			<div class="xia">
				<p class="p1">
					<span>入库通知单号:</span>
					<input type="text" name="notificationId" id="notificationId" >
					<span>采购订单号:</span>
					<input type="text" name="businessNumber" id="businessNumber" >
					
				</p>
				<p class="p1">
				<span>制单日期:</span>
					<input type="text" name="startTime" id="startTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"><i>至</i>
					<input type="text" name="endTime" id="endTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});">
				</p>
				<p class="p1">
					<span>服务商名称:</span>
					<input type="text" class="inp" name="serviceName" id="serviceName" >
				</p>
				<p class="p1">
				  <span>订单送货地址:</span>
				  <input type="text" class="inp" name="receiveAddress" id="receiveAddress">
				</p>
				<p class="p2">
					<button type="text" onclick="showshipList();">查询</button>
					<button type="text" onclick="addshiporder();">新增入库单</button>
				</p>
			</div>
			
			<!--  -->
			<div id="stockList"></div>
	

		</div>

		


	</div>
	
	<div class="alert_bu" id="serviceDiv" style="display: none;">

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
		
				
			
				<button type="button" class="bt1" id="warehouseclick" style="display: none;">确定</button>
				
			
			
				
			
				<button type="button" class="bt1" id="supplierclick" style="" onclick="loadPurchasePage()">确定</button>
			
			
			
		</div>
		
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


<div class="alert_bu" id="NotificDiv" style="display: none;";>

  <div class="bg"></div>
  
	<div class="wrap">
	
		<div class="pic"></div>
		
		<div class="box1">
		
			<div id="boxtitle">
			
				<h2>选择入库通知单.</h2>
				
			</div>
			
			<div id="suppliernametext" style="display: block;">
			
				<input type="text" id="NotificId">
				
				<button type="button" onclick="selNotific();">查询</button>
			
			</div>
			
		</div>
		
		<div class="box2" id="Notificbox">
			
		</div>
		
		<div class="box3">
		
				
			
				<button type="button" class="bt1" id="warehouseclick" style="display: none;">确定</button>
				
			
			
				
			
				<button type="button" class="bt1" id="supplierclick" style="" onclick="loadNotificPage()">确定</button>
			
			
			
		</div>
		
	</div>
  
</div>
<%@include file="/WEB-INF/views/include/foot.jsp"%>
</body>
</html>