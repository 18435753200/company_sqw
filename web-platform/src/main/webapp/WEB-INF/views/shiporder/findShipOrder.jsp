<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>商品储存_物流通知</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/Storage.css">
</head>
<script src="<%=path%>/commons/js/my97/WdatePicker.js"></script>
<script type="text/javascript">
	function showshipList()
	{
		var pro_array  = new Array();
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
		if($("#createBy").val()!=""&&$("#createBy").val()!=undefined)
		{
			pro_array.push("createBy="+$("#createBy").val());
		}
		if($("#price").val()!=""&&$("#price").val()!=undefined)
		{
			pro_array.push("price="+$("#price").val());
		}
		if($("#status").val()!=""&&$("#status").val()!=undefined)
		{
			pro_array.push("status="+$("#status").val());
		}
		if($("#emergency").val()!=""&&$("#emergency").val()!=undefined)
		{
			pro_array.push("emergency="+$("#emergency").val());
		}
		if($("#supplierName").val()!=""&&$("#supplierName").val()!=undefined)
		{
			pro_array.push("supplierName="+$("#supplierName").val());
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
			url : "findShipOrderList", 
			dataType:"html",
			data:pro_array.join("&"),
			success : function(msg) { 
				$("#shipList").html(msg);
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("对不起，数据异常请稍后再试!");
			}
		});    
	}
	function clickSubmit(page)
	{
		var pro_array  = new Array();
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
		if($("#createBy").val()!=""&&$("#createBy").val()!=undefined)
		{
			pro_array.push("createBy="+$("#createBy").val());
		}
		if($("#price").val()!=""&&$("#price").val()!=undefined)
		{
			pro_array.push("price="+$("#price").val());
		}
		if($("#status").val()!=""&&$("#status").val()!=undefined)
		{
			pro_array.push("status="+$("#status").val());
		}
		if($("#emergency").val()!=""&&$("#emergency").val()!=undefined)
		{
			pro_array.push("emergency="+$("#emergency").val());
		}
		if($("#supplierName").val()!=""&&$("#supplierName").val()!=undefined)
		{
			pro_array.push("supplierName="+$("#supplierName").val());
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
			url : "findShipOrderList", 
			dataType:"html",
			data:pro_array.join("&"),
			success : function(msg) { 
				$("#shipList").html(msg);
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("对不起，数据异常请稍后再试!");
			}
		});   
	}
	function addshiporder()
	{
	var obj=document.getElementsByName('ShipOrderNo');
    var s='';    
    for(var i=0; i<obj.length; i++){    
    if(obj[i].checked){
    s+=obj[i].value+':';  //如果选中，将value添加到变量s中        
    }
    }
    s=s.substring(0, s.length-1);
     if(s=="")
     {
     	alert("请选择入库通知记录!");
     	return ;
     }
     if(s.split(":").length>1)
     {
     	alert("请选择一条入库通知记录!");
     	return ;
     }
    	if(s.split(":").length==1)
    	{
    		var str=s.split(":")[0].split(",");
    		checkBussStatus(str[0],str[1]);
			
    	}
		
	}
	function checkBusiness(businessNumber,notificationOrder)
	{
		$.ajax({
		type : "post", 
		url : "checkBusiness", 
		dataType:"html",
		data:"businessNumber="+businessNumber+"&notificationOrder="+notificationOrder,
		success : function(msg) { 
		if(msg=='OK')
		{
			checkStatus(businessNumber,notificationOrder);
		}else{
			alert("此张采购单为供应商送货无需新建物流单!");
		}
			
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	}); 
	}
	function checkStatus(businessNumber,notificationOrder)
	{
		$.ajax({
		type : "post", 
		url : "checkStatus", 
		dataType:"html",
		data:"businessNumber="+businessNumber+"&notificationOrder="+notificationOrder,
		success : function(msg) { 
		if(msg=='OK')
		{
			checkShip(businessNumber,notificationOrder);
		}else{
			alert("您有未审核的物流通知单!");
		}
			
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	}); 
	}
	function checkShip(businessNumber,notificationOrder)
	{
		$.ajax({
		type : "post", 
		url : "checkShip", 
		dataType:"html",
		data:"businessNumber="+businessNumber+"&notificationOrder="+notificationOrder,
		success : function(msg) { 
		if(msg=='OK')
		{
			window.location.href="<%=path%>/shiporder/saveShipOrder?businessNumber="+businessNumber+"&notificationOrder="+notificationOrder;
			window.location.target="_blank";
		}else{
			alert("可通知入库数量为'0'，已不允许新增物流通知单");
		}
			
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	}); 
	}
	function checkBussStatus(businessNumber,notificationOrder)
	{
		$.ajax({
		type : "post", 
		url : "checkBussStatus", 
		dataType:"html",
		data:"businessNumber="+businessNumber+"&notificationOrder="+notificationOrder,
		success : function(msg) { 
		if(msg=='OK')
		{
			checkBusiness(businessNumber,notificationOrder);
		}else{
			alert("可通知入库数量为'0'，已不允许新增物流通知单");
		}
			
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	}); 
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
	var sers=service.split(",");
	$("#businessNumber").val(sers[0]);
	$("#createBy").val(sers[1]);
	$("#createTime").val(sers[2]);
	$("#ChaseOrderDiv").hide();
}

function loadPurchasePage()
{
	var service=$('input[name="service"]:checked').val();
	var services=service.split(",");
	$("#supplierName").val(services[1]);
	$("#serviceDiv").hide();
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
					<p class="c1">物流通知</p>
				</div>
	        </div>
			
			<div class="xia">
				<p class="p1">
					<span>采购订单号:</span>
					<input type="text" name="businessNumber" id="businessNumber" >
					<span>订单日期:</span>
					<input type="text" name="startTime" id="startTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"><i>至</i>
					<input type="text" name="endTime" id="endTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});">
				</p>
				<p class="p1">
					<span>供应商名称:</span>
					<input type="text" class="inp" name="supplierName" id="supplierName" >
					<span>金额:</span>
				    <input type="text" name="price" id="price">
				</p>
				<p class="p1">
				  <span>订单状态:</span>
				  <select name="status" id="status"><option value="">所有</option><option value="20">已完成</option>
				  <option value="10">未完成</option>
				  </select> 
				  <span>紧急程度:</span>
				  <select name="emergency" id="emergency"><option value="">所有</option><c:forEach items="${degreeOfEmergency }" var="result"><option value="${result.dictValue }">${result.dictName }</option></c:forEach>
				  </select>
				  	<span>制单人:</span>
					<input type="text" name="createBy" id="createBy">
				</p>
				<p class="p2">
					<button type="text" onclick="showshipList();">查询</button>
					<button type="text" onclick="addshiporder();">新增物流单</button>
				</p>
			</div>
			<div id="shipList"></div>
			<!-- <div class="pu_wrap">
		  		<div class="pu_bd">
		  			<table>
		  				<tr class="order_hd">
		  					<th width="40px;">序号</th>
		  					<th width="50px;">选择</th>
		  					<th width="110px;">采购订单号</th>
		  					<th width="150px;">供应商名称</th>
		  					<th width="100px;">金额</th>
		  					<th width="100px;">紧急程度</th>
		  					<th width="90px;">订单日期</th>
		  					<th width="100px;">订单状态</th>
		  					<th width="100px;">制单人</th>
		  				</tr>
		  				<tr>
		  					<td>1</td>
			  				<td><input type="checkbox"></td>
			  				<td>12345678901234567</td>
			  				<td>小鱼小鱼</td>
			  				<td>200</td>
			  				<td>一般</td>
			  				<td>2015.5.7</td>
			  				<td>已完成</td>
			  				<td>夏鸥</td>
		  				</tr>
		  			
		  			</table>
		  		</div>

	        </div> -->
			
			<!-- <div class="pu_wrap">
				<div class="pu_hd"><h3>物流通知列表</h3></div>
		  		<div class="pu_bd">
		  			<table>
		  				<tr class="order_hd">
		  					<th width="40px;">序号</th>
		  					<th width="110px;">物流通知单号</th>
		  					<th width="130px;">服务商名称</th>
		  					<th width="80px;">承运方式</th>
		  					<th width="130px;">订单送货地址</th>
		  					<th width="80px;">联系人</th>
		  					<th width="80px;">联系电话</th>
		  					<th width="80px;">制单日期</th>
		  					<th width="110px;">采购订单号</th>
		  				</tr>
		  				<tr>
		  					<td>1</td>
			  				<td><input type="checkbox"></td>
			  				<td>12345678901234567</td>
			  				<td>快递</td>
			  				<td>众聚商城</td>
			  				<td>小鱼</td>
			  				<td>13261111111</td>
			  				<td>2015.5.7</td>
			  				<td>12345678901234567</td>
		  				</tr>
		  			
		  			</table>
		  		</div>

	        </div> -->

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