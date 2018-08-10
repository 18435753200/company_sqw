<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>运费核对</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/Storage.css">
</head>
<script src="<%=path%>/commons/js/my97/WdatePicker.js"></script>
<script type="text/javascript">
function showList()
	{
		var pro_array  = new Array();
	if($("#expressNumber").val()!=""&&$("#expressNumber").val()!=undefined)
	{
		pro_array.push("expressNumber="+$("#expressNumber").val());
	}
	if($("#startTime").val()!=""&&$("#startTime").val()!=undefined)
	{
		pro_array.push("startTime="+$("#startTime").val());
	}
	if($("#endTime").val()!=""&&$("#endTime").val()!=undefined)
	{
		pro_array.push("endTime="+$("#endTime").val());
	}
	
	$.ajax({
		type : "post", 
		url : "findStockShipList", 
		dataType:"html",
		data:pro_array.join("&"),
		success : function(msg) { 
			$("#StockShipList").html(msg);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	});
	}
	function clickSubmit(page)
	{
		var pro_array  = new Array();
	if($("#expressNumber").val()!=""&&$("#expressNumber").val()!=undefined)
	{
		pro_array.push("expressNumber="+$("#expressNumber").val());
	}
	if($("#startTime").val()!=""&&$("#startTime").val()!=undefined)
	{
		pro_array.push("startTime="+$("#startTime").val());
	}
	if($("#endTime").val()!=""&&$("#endTime").val()!=undefined)
	{
		pro_array.push("endTime="+$("#endTime").val());
	}
	if(page!=""&&page!=undefined)
	{
		pro_array.push("page="+page);
	}
	$.ajax({
		type : "post", 
		url : "findStockShipList", 
		dataType:"html",
		data:pro_array.join("&"),
		success : function(msg) { 
			$("#StockShipList").html(msg);
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
	if($("#expressNumber").val()!=""&&$("#expressNumber").val()!=undefined)
	{
		pro_array.push("expressNumber="+$("#expressNumber").val());
	}
	if($("#startTime").val()!=""&&$("#startTime").val()!=undefined)
	{
		pro_array.push("startTime="+$("#startTime").val());
	}
	if($("#endTime").val()!=""&&$("#endTime").val()!=undefined)
	{
		pro_array.push("endTime="+$("#endTime").val());
	}
	var iframe = document.createElement("iframe");
            iframe.src = "OutPutShipExcel?" + pro_array.join("&");
            iframe.style.display = "none";
            document.body.appendChild(iframe);
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
					<p class="c1">运费核对</p>
				</div>
	        </div>
			<div class="pu_hd"><h3>查询条件</h3></div>
	        <div class="xia">
	        	<p class="p1">
					<span>快递单号:</span>
					<input type="text" name="expressNumber" id="expressNumber">
					<span>发货时间: 从</span>
					<input type="text" name="startTime" id="startTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"><i>至</i>
					<input type="text" name="endTime" id="endTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});">
				</p>
			

				<p class="p2">
					<button type="button" onclick="showList();">查询</button>
				</p>
	        </div>
			<div id="StockShipList"></div>
	     
			
		
		</div>

	</div>
</body>
</html>