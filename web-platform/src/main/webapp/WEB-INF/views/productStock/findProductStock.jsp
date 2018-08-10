<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>商品发货</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/Storage.css">
</head>
<script src="<%=path%>/commons/js/my97/WdatePicker.js"></script>
<script type="text/javascript">
	function showList()
	{
		var pro_array  = new Array();
	if($("#sid").val()!=""&&$("#sid").val()!=undefined)
	{
		pro_array.push("sid="+$("#sid").val());
	}
	if($("#status").val()!=""&&$("#status").val()!=undefined)
	{
		pro_array.push("status="+$("#status").val());
	}
	if($("#orderType").val()!=""&&$("#orderType").val()!=undefined)
	{
		pro_array.push("orderType="+$("#orderType").val());
	}
	if($("#outType").val()!=""&&$("#outType").val()!=undefined)
	{
		pro_array.push("outType="+$("#outType").val());
	}
	if($("#notificationId").val()!=""&&$("#notificationId").val()!=undefined)
	{
		pro_array.push("notificationId="+$("#notificationId").val());
	}
	if($("#startTime").val()!=""&&$("#startTime").val()!=undefined)
	{
		pro_array.push("startTime="+$("#startTime").val());
	}
	if($("#endTime").val()!=""&&$("#endTime").val()!=undefined)
	{
		pro_array.push("endTime="+$("#endTime").val());
	}
	if($("#notificationStatus").val()!=""&&$("#notificationStatus").val()!=undefined)
	{
		pro_array.push("notificationStatus="+$("#notificationStatus").val());
	}
	if($("#notificationStatus").val()!=""&&$("#notificationStatus").val()!=undefined)
	{
		pro_array.push("notificationStatus="+$("#notificationStatus").val());
	}
	if($("#warehouseName").val()!=""&&$("#warehouseName").val()!=undefined)
	{
		pro_array.push("warehouseName="+$("#warehouseName").val());
	}
	if($("#orderId").val()!=""&&$("#orderId").val()!=undefined)
	{
		pro_array.push("orderId="+$("#orderId").val());
	}
	if($("#oStartTime").val()!=""&&$("#oStartTime").val()!=undefined)
	{
		pro_array.push("oStartTime="+$("#oStartTime").val());
	}
	if($("#oEndTime").val()!=""&&$("#oEndTime").val()!=undefined)
	{
		pro_array.push("oEndTime="+$("#oEndTime").val());
	}
	$.ajax({
		type : "post", 
		url : "findProductStockList", 
		dataType:"html",
		data:pro_array.join("&"),
		success : function(msg) { 
			$("#ProductStock").html(msg);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	});
	}
	function clickSubmit(page)
	{
		var pro_array  = new Array();
	if($("#sid").val()!=""&&$("#sid").val()!=undefined)
	{
		pro_array.push("sid="+$("#sid").val());
	}
	if(page!=""&&page!=undefined)
	{
		pro_array.push("page="+page);
	}
	if($("#status").val()!=""&&$("#status").val()!=undefined)
	{
		pro_array.push("status="+$("#status").val());
	}
	if($("#orderType").val()!=""&&$("#orderType").val()!=undefined)
	{
		pro_array.push("orderType="+$("#orderType").val());
	}
	if($("#outType").val()!=""&&$("#outType").val()!=undefined)
	{
		pro_array.push("outType="+$("#outType").val());
	}
	if($("#notificationId").val()!=""&&$("#notificationId").val()!=undefined)
	{
		pro_array.push("notificationId="+$("#notificationId").val());
	}
	if($("#startTime").val()!=""&&$("#startTime").val()!=undefined)
	{
		pro_array.push("startTime="+$("#startTime").val());
	}
	if($("#endTime").val()!=""&&$("#endTime").val()!=undefined)
	{
		pro_array.push("endTime="+$("#endTime").val());
	}
	if($("#notificationStatus").val()!=""&&$("#notificationStatus").val()!=undefined)
	{
		pro_array.push("notificationStatus="+$("#notificationStatus").val());
	}
	if($("#notificationStatus").val()!=""&&$("#notificationStatus").val()!=undefined)
	{
		pro_array.push("notificationStatus="+$("#notificationStatus").val());
	}
	if($("#warehouseName").val()!=""&&$("#warehouseName").val()!=undefined)
	{
		pro_array.push("warehouseName="+$("#warehouseName").val());
	}
	if($("#orderId").val()!=""&&$("#orderId").val()!=undefined)
	{
		pro_array.push("orderId="+$("#orderId").val());
	}
	if($("#oStartTime").val()!=""&&$("#oStartTime").val()!=undefined)
	{
		pro_array.push("oStartTime="+$("#oStartTime").val());
	}
	if($("#oEndTime").val()!=""&&$("#oEndTime").val()!=undefined)
	{
		pro_array.push("oEndTime="+$("#oEndTime").val());
	}
	$.ajax({
		type : "post", 
		url : "findProductStockList", 
		dataType:"html",
		data:pro_array.join("&"),
		success : function(msg) { 
			$("#ProductStock").html(msg);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	});
	}
	function AllCheck()
	{
		var obj=document.getElementsByName('outStockBox');
   		for(var i=0;i<obj.length;i++) 
			{ 
				obj[i].checked=true;
			} 
	}
	function unAllCheck()
	{
		var obj=document.getElementsByName('outStockBox');
   		for(var i=0;i<obj.length;i++) 
			{ 
				obj[i].checked=false;
			} 
	}
	
	function outPutExcel()
	{
		var pro_array  = new Array();
	if($("#sid").val()!=""&&$("#sid").val()!=undefined)
	{
		pro_array.push("sid="+$("#sid").val());
	}
	if($("#status").val()!=""&&$("#status").val()!=undefined)
	{
		pro_array.push("status="+$("#status").val());
	}
	if($("#orderType").val()!=""&&$("#orderType").val()!=undefined)
	{
		pro_array.push("orderType="+$("#orderType").val());
	}
	if($("#outType").val()!=""&&$("#outType").val()!=undefined)
	{
		pro_array.push("outType="+$("#outType").val());
	}
	if($("#notificationId").val()!=""&&$("#notificationId").val()!=undefined)
	{
		pro_array.push("notificationId="+$("#notificationId").val());
	}
	if($("#startTime").val()!=""&&$("#startTime").val()!=undefined)
	{
		pro_array.push("startTime="+$("#startTime").val());
	}
	if($("#endTime").val()!=""&&$("#endTime").val()!=undefined)
	{
		pro_array.push("endTime="+$("#endTime").val());
	}
	if($("#notificationStatus").val()!=""&&$("#notificationStatus").val()!=undefined)
	{
		pro_array.push("notificationStatus="+$("#notificationStatus").val());
	}
	if($("#notificationStatus").val()!=""&&$("#notificationStatus").val()!=undefined)
	{
		pro_array.push("notificationStatus="+$("#notificationStatus").val());
	}
	if($("#warehouseName").val()!=""&&$("#warehouseName").val()!=undefined)
	{
		pro_array.push("warehouseName="+$("#warehouseName").val());
	}
	if($("#orderId").val()!=""&&$("#orderId").val()!=undefined)
	{
		pro_array.push("orderId="+$("#orderId").val());
	}
	if($("#oStartTime").val()!=""&&$("#oStartTime").val()!=undefined)
	{
		pro_array.push("oStartTime="+$("#oStartTime").val());
	}
	if($("#oEndTime").val()!=""&&$("#oEndTime").val()!=undefined)
	{
		pro_array.push("oEndTime="+$("#oEndTime").val());
	}
	var iframe = document.createElement("iframe");
            iframe.src = "OutPutExcel?" + pro_array.join("&");
            iframe.style.display = "none";
            document.body.appendChild(iframe);
	}
	function StockShip(oo)
	{
		oo.attr("href","${path}/productStock/StockShip");
		oo.click();
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
					<p>销售出库&nbsp;&gt;&nbsp; </p>
					<p class="c1">商品发货</p>
				</div>
	        </div>

	        <div class="xia">
	        	<p class="p1">
					<span>发货单号:</span>
					<input type="text" name="sid" id="sid">
					<span>发货单状态:</span>
					<select name="status" id="status"><option value="">所有</option><option value="10">已审核</option><option value="1">未审核</option></select>
					<span>出库单号:</span>
					<input type="text" name="">
				</p>
				<p class="p1">
					<!--<span>出库单状态:</span>
					<select name="status" id="status"><option value="10">已审核</option><option value="1">未审核</option></select>  -->
					<span>库房名称:</span>
					<input type="text" name="warehouseName" id="warehouseName">
					<span>出库通知单编号:</span>
					<input type="text" name="notificationId" id="notificationId">
				</p>
				<p class="p1">
					<span>出库通知单状态:</span>
					<select name="notificationStatus" id="notificationStatus"><option value="">所有</option><option value="100">已出库</option><option value="2">已打包</option><option value="1">待出库</option></select>
					<span>出库时间: 从</span>
					<input type="text" name="startTime" id="startTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"><i>至</i><input type="text" name="endTime" id="endTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"> 
				</p>
				<p class="p1">
					<span>订单编号:</span>
					<input type="text" name="orderId" id="orderId">
					<span>交易时间: 从</span>
					<input type="text" name="oStartTime" id="oStartTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"><i>至</i><input type="text" name="oEndTime" id="oEndTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});">
				</p>
				
				<p class="p1">
					<span>订单来源:</span>
					<select id="outType" name="outType"><option value="">所有</option><option value="201">B2B销售出库</option><option value="202">B2C销售出库</option><option value="203">换货出库</option></select>
				</p>

				<p class="p2">
					<button type="button">扩展查询</button>
					<button type="button" onclick="showList();">查询</button>
				</p>
	        </div>
			
	        <div class="pu_wrap">
	        	<div class="pu_hd">
		        	<h3>发货单列表</h3>
		        	<div class="btn">
		       			<a href="javascript:void(0)" onclick="AllCheck();" >全选</a>
		        		<a href="javascript:void(0)" onclick="unAllCheck();">反选</a>
		        		<a href="javascript:void(0)" onclick="outPutExcel();">导出</a>
		       			<!-- <a href="#">获取WMS</a> -->
		       			<!-- <a href="#">查看发货单</a> -->
		       			<a href="javascript:void(0)" target="_blank" onclick="StockShip($(this));">运费核对</a>
		       		</div>
	        	</div>
	       	
	       		<div id="ProductStock"></div>
	        
	        </div>
			
			 

		</div>

	</div>
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
</body>
</html>