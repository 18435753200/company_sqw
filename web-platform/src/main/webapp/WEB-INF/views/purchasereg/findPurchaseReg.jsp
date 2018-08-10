<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>费用归集</title>
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

function loadOrderPage()
{
	var service=$('input[name="ChaseOrder"]:checked').val();
	$("#businessNumber").val(service);
	$("#ChaseOrderDiv").hide();
}
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
function showShip()
{
			$.ajax({
		type : "post", 
		url : "findShipList", 
		dataType:"html",
		success : function(msg) { 
			$("#shipbox").html(msg);
			$("#ShipDiv").show();
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
function loadNotificPage()
{
	var service=$('input[name="Notific"]:checked').val();
	$("#notificationId").val(service);
	$("#NotificDiv").hide();
}
function loadPurchasePage()
{
	var service=$('input[name="service"]:checked').val();
	var services=service.split(",");
	$("#serviceName").val(services[1]);
	$("#serviceDiv").hide();
}
function selShip()
{
	var pro_array  = new Array();
	if($("#shipid").val()!=""&&$("#shipid").val()!=undefined)
	{
		pro_array.push("id="+$("#shipid").val());
	}
	$.ajax({
		type : "post", 
		url : "findShipList", 
		dataType:"html",
		data:pro_array.join("&"),
		success : function(msg) { 
			$("#shipbox").html(msg);
			$("#ShipDiv").show();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	}); 
	
}

function loadShipPage()
{
	var service=$('input[name="shipOrder"]:checked').val();
	$("#shipId").val(service);
	$("#ShipDiv").hide();
}
	function showRegList()
	{
		var pro_array  = new Array();
	if($("#shipId").val()!=""&&$("#shipId").val()!=undefined)
	{
		pro_array.push("shipId="+$("#shipId").val());
	}
	if($("#notificationId").val()!=""&&$("#notificationId").val()!=undefined)
	{
		pro_array.push("notificationId="+$("#notificationId").val());
	}
	if($("#serviceName").val()!=""&&$("#serviceName").val()!=undefined)
	{
		pro_array.push("serviceName="+$("#serviceName").val());
	}
	if($("#businessNumber").val()!=""&&$("#businessNumber").val()!=undefined)
	{
		pro_array.push("businessNumber="+$("#businessNumber").val());
	}
	$.ajax({
		type : "post", 
		url : "findPurchaseRegList", 
		dataType:"html",
		data:pro_array.join("&"),
		success : function(msg) { 
			$("#regDiv").html(msg);
			$("#regfee").show();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	});  
	}
	function clickSubmit(page)
	{
		var pro_array  = new Array();
	if($("#shipId").val()!=""&&$("#shipId").val()!=undefined)
	{
		pro_array.push("shipId="+$("#shipId").val());
	}
	if($("#notificationId").val()!=""&&$("#notificationId").val()!=undefined)
	{
		pro_array.push("notificationId="+$("#notificationId").val());
	}
	if($("#serviceName").val()!=""&&$("#serviceName").val()!=undefined)
	{
		pro_array.push("serviceName="+$("#serviceName").val());
	}
	if($("#businessNumber").val()!=""&&$("#businessNumber").val()!=undefined)
	{
		pro_array.push("businessNumber="+$("#businessNumber").val());
	}
	if(page!=""&&page!=undefined)
	{
		pro_array.push("page="+page);
	}
	$.ajax({
		type : "post", 
		url : "findPurchaseRegList", 
		dataType:"html",
		data:pro_array.join("&"),
		success : function(msg) { 
			$("#regDiv").html(msg);
			$("#regfee").show();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	});  
	}
	function CreateRow() {
            var t01 = $("#regDetil tr").length;
            var html="<tr><td>"+t01+"</td><td><input type=\"text\" name=\"createDate\" id=\"createDate"+t01+"\" style=\"border:0px;\"/></td><td><input type=\"text\" name=\"freightCode\" id=\"freightCode"+t01+"\" style=\"border:0px;\"/></td><td><input type=\"text\" name=\"receiveAddress\" id=\"receiveAddress"+t01+"\" style=\"border:0px;\"/></td><td><input type=\"text\" name=\"freightPrice\" id=\"freightPrice"+t01+"\" style=\"border:0px;\"/></td><td><select name=\"allocationCode\" id=\"allocationCode"+t01+"\"><c:forEach items='${Dictionarys }' var='Dictionary'><option value='${Dictionary.dictValue }'>${Dictionary.dictName }</option></c:forEach></select></td><td><input type=\"text\" name=\"allocationQty\" id=\"allocationQty"+t01+"\" style=\"border:0px;\"/></td><td><input type=\"text\" name=\"allocationPrice\" id=\"allocationPrice"+t01+"\" style=\"border:0px;\"/></td><td><select name=\"currencyCode\" id=\"currencyCode"+t01+"\"><c:forEach items='${Currency }' var='currency'><option value='${currency.code }'>${currency.currencyType }</option></c:forEach></select></td><td><input type=\"text\" name=\"exchangeRate\" id=\"exchangeRate"+t01+"\" style=\"border:0px;\"/></td><td><input type=\"text\" name=\"localPrice\" id=\"localPrice"+t01+"\" style=\"border:0px;\"/></td><td><input type=\"text\" name=\"localTotalPrice\" id=\"localTotalPrice"+t01+"\" style=\"border:0px;\"/></td><td><input type=\"text\" name=\"imagePath\" id=\"imagePath"+t01+"\" style=\"border:0px;\"/></td></tr>";
            var tab=$("#regDetil");
            tab.append(html);
        }
        function editDetil()
    {
    	var tab=$("#regDetil");
    	tab.show();
    }
    
    
    function saveDetil()
    {
    	var value=$("#regMess").serialize();
    	var s=''; 
    	var obj=document.getElementsByName('regid'); 
		for(var i=0; i<obj.length; i++){ 
		if(obj[i].checked) {
		s+=obj[i].value+':'; //如果选中，将value添加到变量s中 
		} 
		//那么现在来检测s的值就知道选中的复选框的值了 
		} 
		s=s.substring(0, s.length-1);
     if(s=="")
     {
     	alert("请选择入库记录!");
     	return ;
     }
     if(s.split(":").length>1)
     {
     	alert("请选择一调入库记录!");
     	return ;
     }
    	if(s.split(":").length==1)
    	{
    		var str=s.split(":")[0].split(",");
    		
			$.ajax({
		type : "post", 
		url : "savePurchaseRegDetil", 
		dataType:"text",
		data:value+"&shipId="+str,
		success : function(msg) { 
			var tab=$("#regDetil");
    		tab.hide();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	});    
    	}
		
    	
    	
    }
    
    function checkPurchase()
    {
    	var s=''; 
    	var obj=document.getElementsByName('regid'); 
		for(var i=0; i<obj.length; i++){ 
		if(obj[i].checked) {
		s+=obj[i].value+':'; //如果选中，将value添加到变量s中 
		} 
		//那么现在来检测s的值就知道选中的复选框的值了 
		} 
		s=s.substring(0, s.length-1);
     if(s=="")
     {
     	alert("请选择入库记录!");
     	return ;
     }
     if(s.split(":").length>1)
     {
     	alert("请选择一调入库记录!");
     	return ;
     }
    	if(s.split(":").length==1)
    	{
    		var str=s.split(":")[0].split(",");
    		
			$.ajax({
		type : "post", 
		url : "editPurchaseRegDetil", 
		dataType:"text",
		data:"&shipId="+str,
		success : function(msg) { 
			var tab=$("#regDetil");
    		tab.hide();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	});  
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
					<p>商品采购&nbsp;&gt;&nbsp; </p>
					<p class="c1">费用归集</p>
				</div>
	        </div>

			
			<!--采购明细开始-->
	
			<!--采购明细结束-->

			<!--采购费用登记开始-->
			<!--采购费用登记结束-->

			<div class="pu_hd"><h3>国内运费</h3></div>
	  		<div class="xia">
  				<p class="p1">
  					<span>物流通知单号:</span>
  					<input type="text" name="shipId" id="shipId" onfocus="showShip();">
  					<span>入库通知单编号:</span>
  					<input type="text" name="notificationId" id="notificationId" onfocus="showNotific();">
  				</p>
  				<p class="p1">
  					<span>服务商名称:</span>
  					<input type="text" name="serviceName" id="serviceName" onfocus="showService();">
  					<span>采购订单编号:</span>
  					<input type="text" id="businessNumber" name="businessNumber" onfocus="showOrder();">
  				</p>
  				<p class="p2">
  					<button type="text" onclick="showRegList();">查询</button>
  				</p>
	  		</div>
			<div id="regDiv"></div>
			<div class="pu_wrap">
	  		

	  		
		   <!--国内运费登记结束-->
</div>

	</div>
	</div>




		<div class="alert_bu" id="ShipDiv" style="display: none;">

  <div class="bg"></div>
  
	<div class="wrap">
	
		<div class="pic"></div>
		
		<div class="box1">
		
			<div id="boxtitle">
			
				<h2>选择物流单.</h2>
				
			</div>
			
			<div id="suppliernametext" style="display: block;">
			
				<input type="text" id="shipid">
				
				<button type="button" onclick="selShip();">查询</button>
			
			</div>
			
		</div>
		
		<div class="box2" id="shipbox">
			
		</div>
		
		<div class="box3">
		
				
			
				<button type="button" class="bt1" id="warehouseclick" style="display: none;">确定</button>
				
			
			
				
			
				<button type="button" class="bt1" id="supplierclick" style="" onclick="loadShipPage()">确定</button>
			
			
			
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
<%@include file="/WEB-INF/views/include/foot.jsp"%>
</body>
</html>