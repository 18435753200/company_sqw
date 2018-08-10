<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<meta charset="UTF-8">
	<title>通知发货</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/Storage.css">
</head>
<script src="<%=path%>/commons/js/my97/WdatePicker.js"></script>
<script type="text/javascript">
	function saveOutStock()
	{
		window.location.href="<%=path%>/outstock/saveOutStock";
	}
		function clickSubmit(page)
	{
		var pro_array  = new Array();
	if($("#outId").val()!=""&&$("#outId").val()!=undefined)
	{
		pro_array.push("outId="+$("#outId").val());
	}
	if(page!=""&&page!=undefined)
	{
		pro_array.push("page="+page);
	}
	if($("#status").val()!=""&&$("#status").val()!=undefined)
	{
		pro_array.push("status="+$("#status").val());
	}
	if($("#outType").val()!=""&&$("#outType").val()!=undefined)
	{
		pro_array.push("outType="+$("#outType").val());
	}
	if($("#warehouseId").val()!=""&&$("#warehouseId").val()!=undefined)
	{
		pro_array.push("warehouseId="+$("#warehouseId").val());
	}
	if($("#startTime").val()!=""&&$("#startTime").val()!=undefined)
	{
		pro_array.push("startTime="+$("#startTime").val());
	}
	if($("#endTime").val()!=""&&$("#endTime").val()!=undefined)
	{
		pro_array.push("endTime="+$("#endTime").val());
	}
	if($("#notificationId").val()!=""&&$("#notificationId").val()!=undefined)
	{
		pro_array.push("notificationId="+$("#notificationId").val());
	}
	if($("#notificationStatus").val()!=""&&$("#notificationStatus").val()!=undefined)
	{
		pro_array.push("notificationStatus="+$("#notificationStatus").val());
	}
	if($("#orderId").val()!=""&&$("#orderId").val()!=undefined)
	{
		pro_array.push("orderId="+$("#orderId").val());
	}
	if($("#orderType").val()!=""&&$("#orderType").val()!=undefined)
	{
		pro_array.push("orderType="+$("#orderType").val());
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
		url : "findOutStockList", 
		dataType:"html",
		data:pro_array.join("&"),
		success : function(msg) { 
			$("#outOrder").html(msg);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	});   
	}
	function showOutList()
	{
		var pro_array  = new Array();
	if($("#outId").val()!=""&&$("#outId").val()!=undefined)
	{
		pro_array.push("outId="+$("#outId").val());
	}
	if($("#status").val()!=""&&$("#status").val()!=undefined)
	{
		pro_array.push("status="+$("#status").val());
	}
	if($("#outType").val()!=""&&$("#outType").val()!=undefined)
	{
		pro_array.push("outType="+$("#outType").val());
	}
	if($("#warehouseId").val()!=""&&$("#warehouseId").val()!=undefined)
	{
		pro_array.push("warehouseId="+$("#warehouseId").val());
	}
	if($("#startTime").val()!=""&&$("#startTime").val()!=undefined)
	{
		pro_array.push("startTime="+$("#startTime").val());
	}
	if($("#endTime").val()!=""&&$("#endTime").val()!=undefined)
	{
		pro_array.push("endTime="+$("#endTime").val());
	}
	if($("#notificationId").val()!=""&&$("#notificationId").val()!=undefined)
	{
		pro_array.push("notificationId="+$("#notificationId").val());
	}
	if($("#notificationStatus").val()!=""&&$("#notificationStatus").val()!=undefined)
	{
		pro_array.push("notificationStatus="+$("#notificationStatus").val());
	}
	if($("#orderId").val()!=""&&$("#orderId").val()!=undefined)
	{
		pro_array.push("orderId="+$("#orderId").val());
	}
	if($("#orderType").val()!=""&&$("#orderType").val()!=undefined)
	{
		pro_array.push("orderType="+$("#orderType").val());
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
		url : "findOutStockList", 
		dataType:"html",
		data:pro_array.join("&"),
		success : function(msg) { 
			$("#outOrder").html(msg);
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
	if($("#outId").val()!=""&&$("#outId").val()!=undefined)
	{
		pro_array.push("outId="+$("#outId").val());
	}
	if($("#status").val()!=""&&$("#status").val()!=undefined)
	{
		pro_array.push("status="+$("#status").val());
	}
	if($("#outType").val()!=""&&$("#outType").val()!=undefined)
	{
		pro_array.push("outType="+$("#outType").val());
	}
	if($("#warehouseId").val()!=""&&$("#warehouseId").val()!=undefined)
	{
		pro_array.push("warehouseId="+$("#warehouseId").val());
	}
	if($("#startTime").val()!=""&&$("#startTime").val()!=undefined)
	{
		pro_array.push("startTime="+$("#startTime").val());
	}
	if($("#endTime").val()!=""&&$("#endTime").val()!=undefined)
	{
		pro_array.push("endTime="+$("#endTime").val());
	}
	if($("#notificationId").val()!=""&&$("#notificationId").val()!=undefined)
	{
		pro_array.push("notificationId="+$("#notificationId").val());
	}
	if($("#notificationStatus").val()!=""&&$("#notificationStatus").val()!=undefined)
	{
		pro_array.push("notificationStatus="+$("#notificationStatus").val());
	}
	if($("#orderId").val()!=""&&$("#orderId").val()!=undefined)
	{
		pro_array.push("orderId="+$("#orderId").val());
	}
	if($("#orderType").val()!=""&&$("#orderType").val()!=undefined)
	{
		pro_array.push("orderType="+$("#orderType").val());
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
	function writePDF(oo)
	{
	var obj=document.getElementsByName('outStockBox');
    var s='';    
    for(var i=0; i<obj.length; i++){    
    if(obj[i].checked){
    s+=obj[i].value+',';  //如果选中，将value添加到变量s中        
    }
    }
    s=s.substring(0, s.length-1);
     if(s=="")
     {
     	alert("请选择出库单记录!");
     	return ;
     }
     var url="${path}/outstock/StockOutOrderExportPDF?id="+s;
     oo.attr("href",url);
     oo.attr("target","_blank");
     oo.click();
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
					<p>销售出库&nbsp;&gt;&nbsp; </p>
					<p class="c1">通知发货</p>
				</div>
	        </div>

	        <div class="xia">
	        	<p class="p1">
					<span>出库单编号:</span>
					<input type="text" name="outId" id="outId">
					<span>出库单状态:</span>
					<select name="status" id="status"><option value="">所有</option><option value="10">已审核</option><option value="1">未审核</option></select>
					<span>订单来源:</span>
					<select name="outType" id="outType"><option value="">所有</option><option value="201">B2B销售出库</option><option value="202">B2C销售出库</option><option value="203">换货出库</option></select>
				</p>
				<p class="p1">
					<span>库房名称:</span>
					<select name="warehouseId" id="warehouseId"><option value="">所有</option><c:forEach items="${channels }" var="channel" ><option value="${channel.warehouseCode }">${channel.warehouseName}</option></c:forEach></select>
					<span>出库时间: 从</span>
					<input type="text" name="startTime" id="startTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"><i>至</i><input type="text" name="endTime" id="endTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});">
				</p>
				<p class="p1">
					<span>出库通知单编号:</span>
					<input type="text" name="notificationId" id="notificationId">
					<span>出库通知单状态:</span>
					<select name="notificationStatus" id="notificationStatus"><option value="">所有</option><option value="1">待出库</option><option value="2">已打包</option><option value="100">已出库</option></select>
					<span>订单编号:</span>
					<input type="text" name="orderId" id="orderId">
				</p>
				<p class="p1">
					<span>出库类型:</span>
					<select id="orderType" name="orderType"><option value="">所有</option><option value="1">销售</option></select>
					<span>交易时间: 从</span>
					<input type="text" name="oStartTime" id="oStartTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"><i>至</i><input type="text" name="oEndTime" id="oEndTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});">
				</p>
				<p class="p2">
					<button type="button">扩展查询</button>
					<button type="button" onclick="showOutList();">查询</button>
				</p>
	        </div>
		
			<div class="pu_hd">
	        	<h3>发货通知单列表</h3>
	        	<div class="btn">
	        		<a href="javascript:void(0)" onclick="AllCheck();" >全选</a>
	        		<a href="javascript:void(0)" onclick="unAllCheck();">反选</a>
	        		<a href="javascript:void(0)" onclick="outPutExcel();">导出</a>
	        		<a href="javascript:void(0)" onclick="writePDF($(this));">打印</a>
					<!-- <a href="#">推送WMS</a> 
					<a href="javascript:void(0)" onclick="saveOutStock();">新增发货单</a>-->
	        	</div>
	        	</div>
	        <div class="pu_wrap">
	        	<div id="outOrder"></div>
	        	<!--  -->
	        </div>

	        
			<!--  -->
	        </div>


		</div>

	</div>
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
</body>
</html>