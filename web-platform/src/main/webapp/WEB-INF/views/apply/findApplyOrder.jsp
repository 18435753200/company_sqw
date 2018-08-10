<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>出库申请(通知)</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/manual.css">
    <script src="<%=path%>/commons/js/my97/WdatePicker.js"></script>
    <script type="text/javascript" src="${path}/commons/js/map.js"></script>
</head>
<script type="text/javascript">
	function saveApply(oo)
	{
		oo.attr("href","${path}/Apply/saveApply");
    	oo.attr("target","view_window");
    	oo.click();
	}
	function findApply()
	{
		var obj=document.getElementsByName('apply');
		var sub=0;
		var map = new Map();
		for(var i=0; i<obj.length; i++){
		if(obj[i].checked)
		{
			map.put(obj[i].value,obj[i].value);
		}
		if(map.size()>0)
		{
			alert("请选择一张申请单!");
			return ;
		}
		if(map.size()==0)
		{
			alert("请选择申请单!");
			return ;
		}
	}
	}
	function showshipList()
	{
		var pro_array  = new Array();
		if($("#applyChar").val()!=""&&$("#applyChar").val()!=undefined)
		{
			pro_array.push("applyChar="+$("#applyChar").val());
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
		if($("#warehouseId").val()!=""&&$("#warehouseId").val()!=undefined)
		{
			pro_array.push("warehouseId="+$("#warehouseId").val());
		}
		if($("#status").val()!=""&&$("#status").val()!=undefined)
		{
			pro_array.push("status="+$("#status").val());
		}
		if($("#outType").val()!=""&&$("#outType").val()!=undefined)
		{
			if($("#outType").val()==2){
				pro_array.push("outType="+$("#otherCusType").val());
			}else{
				pro_array.push("outType="+$("#outType").val());
			}
		}else{
			pro_array.push("outType=");
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
			url : "findApplyOrderList", 
			dataType:"html",
			data:pro_array.join("&"),
			success : function(msg) { 
				$("#ApplyList").html(msg);
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("对不起，数据异常请稍后再试!");
			}
		});    
	}
	function clickSubmit(page)
	{
		var pro_array  = new Array();
		if($("#applyChar").val()!=""&&$("#applyChar").val()!=undefined)
		{
			pro_array.push("applyChar="+$("#applyChar").val());
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
		if($("#warehouseId").val()!=""&&$("#warehouseId").val()!=undefined)
		{
			pro_array.push("warehouseId="+$("#warehouseId").val());
		}
		if($("#status").val()!=""&&$("#status").val()!=undefined)
		{
			pro_array.push("status="+$("#status").val());
		}
		if($("#outType").val()!=""&&$("#outType").val()!=undefined)
		{
			if($("#outType").val()==2){
				pro_array.push("outType="+$("#otherCusType").val());
			}else{
				pro_array.push("outType="+$("#outType").val());
			}
		}else{
			pro_array.push("outType=");
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
			url : "findApplyOrderList", 
			dataType:"html",
			data:pro_array.join("&"),
			success : function(msg) { 
				$("#ApplyList").html(msg);
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("对不起，数据异常请稍后再试!");
			}
		});    
	}
	
	function isShowOtherType(dat){
		var outTypeChecked = $(dat).val();
		if(outTypeChecked==2){
			$("#outTypeIsOut").show();
		}else{
			$("#outTypeIsOut").hide();
		}
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
					<p class="c1">出库申请(通知)</p>
				</div>
	        </div>

	        <div class="pu_hd">
				<h3>查询条件</h3>
			</div>
			
			<div class="xia">
				<p class="p1">
					<span>出库申请单编号：</span><input type="text" name="applyChar" id="applyChar">
					<span>申请日期：</span><input type="text" name="startTime" id="startTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"><i>至</i>
					<input type="text" name="endTime" id="endTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});">
				</p>
				<p class="p1">
					<span>申请人：</span>
					<input type="text" name="createBy" id="createBy">
					<span>库房名称：</span>
					<select name="warehouseId" id="warehouseId"><option value="">请选择</option><c:forEach items="${warehouses }" var="w"><option value="${w.warehouseCode }">${w.warehouseName }</option></c:forEach></select>
					<!-- <span>出库来源：</span>
					<select name="businessType" id="businessType" class="w90"><option value="">请选择</option><option value="99">其他</option></select> -->
					
				</p>
				<p class="p1">
					<span>单据审核：</span>
					<select name="status" id="status" class="w90"><option value="">请选择</option><option value="10">已审核</option><option value="1">未审核</option></select>
					<span>出库类型：</span>
						<select name="outType" id="outType" class="w90" onchange="isShowOtherType(this)">
							<option value="">请选择</option>
							<option value="1">领用出库</option>
							<option value="2">销售出库</option>
							<option value="4">特殊出库</option>
						</select>
					<span id="outTypeIsOut" style="display: none;">
						<span style="padding-left: 35px;">客户名称：</span>
							<select name="otherCusType" id="otherCusType" class="w90">
								<option value="2">请选择</option>
							 	<c:forEach items="${outTypeMap }" var="outTypeMap" varStatus="outType">
							 		<option value="${outTypeMap.key}">${outTypeMap.value}</option>
							 	</c:forEach>
						</select>
					</span>
				</p>
				<p class="p2">
					<button type="button" onclick="showshipList();">查询</button>
				</p>
			</div>
			<div class="pu_hd">
				<h3>出库申请单列表</h3>
				<div class="btn">
				    <a href="javascript:void(0)" target="_blank" onclick="saveApply($(this));">新增出库申请</a>
				    <!-- <a href="javascript:void(0)" target="_blank" onclick="findApply();">查看出库申请</a> -->
				</div>
			</div>
			<div id="ApplyList"></div>
			
			

		</div>


	</div>
</body>
</html>