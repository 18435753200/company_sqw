<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>商品储存_物流通知(表单)</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/Storage.css">
	<script src="<%=path%>/commons/js/my97/WdatePicker.js"></script>
</head>
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
			pro_array.push("id="+$("#ChaseOrderId").val());
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
		$("#serviceName").val(services[2]);
		$("#serviceCode").val(services[1]);
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
	function saveOrder()
	{
		var formdate = $('#myform').serialize();
		var s='';
		var obj = document.getElementsByName("tabdetil");
		for(var i=0;i<obj.length;i++){
                 s+=obj[i].value+',';   //如果选中，将value添加到变量s中    
         }
         if(s.indexOf("1")==-1)
		{
			alert("请选择需要提交的商品明细");
			return;
		}
		$.ajax({
			type : "post", 
			url : "doUpdateShipOrder", 
			data:formdate+"&checkBox="+s,
			success : function(msg) { 
				window.location.href="<%=path%>/shiporder/findShipOrder";
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("对不起，数据异常请稍后再试!");
			}
		});  
	}
	
	function checkShip()
	{
		var formdate = $('#myform').serialize();
		var s='';
		var obj = document.getElementsByName("tabdetil");
		for(var i=0;i<obj.length;i++){
                 s+=obj[i].value+',';   //如果选中，将value添加到变量s中    
         }
         if(s.indexOf("1")==-1)
		{
			alert("请选择需要提交的商品明细");
			return;
		}
		$.ajax({
			type : "post", 
			url : "checkShipOrder", 
			data:formdate+"&checkBox="+s,
			success : function(msg) { 
				window.location.href="<%=path%>/shiporder/findShipOrder";
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("对不起，数据异常请稍后再试!");
			}
		});  
	}
	
	function checkQty(obj)
	{
		var qty=obj.attr("qty");
		var myvalue=obj.attr("myvalue");
		var qtyReceived=obj.attr("qtyReceived");
		var value=obj.val();
		if(parseInt(value)>(parseInt(qtyReceived)+parseInt(myvalue)))
		{
			alert("本次入库数量不能大于总数量");
			result=1;
			return;
		}else{
			result=0;
		}
	}
	
	function writeInput(row,checked)
	{
		if(checked=='checked')
		{
			$("#qtyReceived"+row).attr('readonly',false);
			$("#tabdetil"+row).val(1);
		}else{
			$("#qtyReceived"+row).attr('readonly',true);
			$("#tabdetil"+row).val(0);
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
					<p class="c1">物流通知(表单)</p>
				</div>
	        </div>
	        <div class="btn">
		     	<a href="javascript:void(0)" onclick="checkShip();">审核</a>
		     	<a href="javascript:void(0)" onclick="saveOrder();">保存</a>
	        </div>
	        <form id="myform">
			<div class="xia">
				<p class="p1">
					<span>物流通知单编号:</span>
					<input type="text" name="id" id="id" value="${orderDto.shipChar}" readonly="readonly">
					<input type="hidden" name="keyChar" id="keyChar" value="${orderDto.id}" readonly="readonly">
					<input type="hidden" name="notificationId" id="notificationId" value="${orderDto.notificationId }"/>
					<input type="hidden" name="businessNumber" id="businessNumber" value="${orderDto.businessNumber}"/>
					<span>采购订单编号:</span>
					<input type="text" name="bussChar" id="bussChar" value="${orderDto.businessNumberChar }" readonly="readonly">
					<span>入库通知编号:</span>
					<input type="text" name="noChar" id="noChar" value="${orderDto.notificationIdChar}" readonly="readonly">
				</p>
				<p class="p1">
					<span>预计送达时间:</span>
					<input type="text" name="arriveDate" id="arriveDate" onclick="WdatePicker();" value="<fmt:formatDate value="${orderDto.arriveDate }" pattern="yyyy-MM-dd"/>">
					<span>服务商名称:</span>
				    <input type="text" id="serviceName" name="serviceName"  value="${orderDto.serviceName }" readonly="readonly">
				    
				  
				</p>
				<p class="p1">
					  <span>服务商编号:</span>
				    <input type="text" id="serviceCode" name="serviceCode" value="${orderDto.serviceCode }" readonly="readonly">
					<span>运输方式:</span>
					<input type="text" id="shipperType" name="shipperType" value="${dictionary.dictName }" readonly="readonly">
					<input type="hidden" id="shipperCode" name="shipperCode" value="${dictionary.dictValue }" readonly="readonly">
				</p>
				<p class="p1">
					<span>送货地址:</span>
					<input type="text" class="inp" id="receiveAddress" name="receiveAddress" value="${orderDto.receiveAddress }" readonly="readonly">
				</p>
				<p class="p1">
					<span>联系人:</span>
					<input type="text" id="receiveName" name="receiveName" value="${orderDto.receiveName }" readonly="readonly">
					<span>联系电话:</span>
					<input type="text" id="receivePhone" name="receivePhone" value="${orderDto.receivePhone }" readonly="readonly">
				</p>
			</div>
			
			<div class="pu_w">
				<div class="pu_h"><h3>货物明细</h3></div>
		  		<div class="pu_b">
		  			<table>
		  				<tr class="order_hd">
		  					<th width="40px;">序号</th>
		  					<th width="50px;">选择</th>
		  					<th width="110px;">商品编码</th>
		  					<th width="110px;">商品条码</th>
		  					<th width="110px;">商品名称</th>
		  					<th width="110px;">规格</th>
		  					<th width="100px;">单位</th>
		  					<th width="100px;">订单数量</th>
		  					<th width="100px;">可通知送货数量</th>
		  					<th width="110px;">本次通知送货数量</th>
		  					<th style="display:none;">单价</th>
		  					<th style="display:none;">skuid</th>
		  				</tr>
		  				<c:forEach var="result" varStatus="status" items="${orderDto.itemDtos }">
		  				<tr>
		  					<td>${status.index+1 }</td>
			  				<td><input type="checkbox" id="tabdetil${status.index+1 }" name="tabdetil" value="0" onclick="writeInput(${status.index+1 },$(this).attr('checked'));" class="sm"></td>
			  				<td><input type="text" name="pcode" id="pcode" value="${result.pcode }" readonly="readonly" style="border:0px; text-align:center;"/></td>
			  				<td><input type="text" name="barCode" id="barCode" value="${result.barCode }" readonly="readonly" style="border:0px;text-align:center;"/></td>
			  				<td><input type="text" name="skuNameCn" id="skuNameCn" value="${result.skuNameCn }" readonly="readonly" style="border:0px;text-align:center;"/></td>
			  				<td><input type="text" name="format" id="format" value="${result.format }" readonly="readonly" style="border:0px;text-align:center;"/></td>
			  				<td><input type="text" name="unit" id="unit" value="${result.unit }" readonly="readonly" style="border:0px;text-align:center;"/></td>
			  				<td><input type="text" name="qty" id="qty" value="${result.pid }" readonly="readonly" style="border:0px;text-align:center;"/></td>
			  				<td><input type="text" name="tallQty" id="tallQty" value="${result.qty }" readonly="readonly" style="border:0px;text-align:center;"/></td>
			  				<td><input type="text" name="qtyReceived" id="qtyReceived${status.index+1 }" value="${result.qtyReceived}" qty="${result.qty }" qtyReceived=${result.skuNameEn } myvalue="${result.qtyReceived}" style="border:0px;text-align:center;" onblur="checkQty($(this));" readonly="readonly"/></td>
			  				<td style="display:none;"><input type="text" name="price" id="price" value="${result.price}" style="border:0px; text-align:right;"/></td>
		  					<td style="display:none;"><input type="text" name="skuId" id="skuId" value="${result.skuId}" style="border:0px; text-align:right;"/></td>
		  				</tr>
		  				</c:forEach>
		  				<c:if test="${!empty pageBean.result}">
					<%@include file="/WEB-INF/views/dealerbuy/basePage/basePage.jsp" %>	
				</c:if>
		  			</table>
		  		</div>
	        </div>
	        </form>
		</div>

		


	</div>
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
</body>
</html>