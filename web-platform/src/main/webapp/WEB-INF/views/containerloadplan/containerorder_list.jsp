<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>UNICORN-装箱管理</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/B2C_packinglist.css"/>
    <script src="${path}/commons/js/loadplan/loadplan_list.js"></script>
    <script src="${path}/commons/js/my97/WdatePicker.js"></script>
    <script type="text/javascript">
		$(document).ready(function(){
			$.ajax({ 
			     async:false,
			     type : "post", 
			     url : "<%=path%>/containerLoadPlan/getCLPOrderPageBeanByCondition", 
			     dataType:"html",
			     success : function(purchaseOrders){
			     	$(".goodsWrap").html(purchaseOrders);
				 },
				 error:function(jqXHR,textStatus,errorThrown){
				  	alert("网络异常,请稍后再试。。。。");
				  }
		     }); 
		});
	
	function toPage(page){
		byConditionSearchPurchaseOrder(page);
	}
	
	//全选
	$(function(){
		$("#quanxuan").click(function(){
			 $('input[name="ck"]').attr("checked",this.checked); 
		});
		var $subBox = $("input[name='ck']");
		
		$subBox.click(function(){
			$("#quanxuan").attr("checked",$subBox.length == $("input[name='ck']:checked").length ? true : false);
		});
	});
	
	
	</script>
</head>
<body>
<%@include file="/WEB-INF/views/include/header.jsp"%>
<div class="center">
<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		<div class="right">
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp; </p>
					<p>订单管理&nbsp;&gt;&nbsp; </p>
					<p class="c1">装箱列表</p>
				</div>
			</div>
		<div class="xia">
		<form action="">
			<p class="p1">
				<span>装箱单号:</span><input type="text" id="planId" >
				<span>装箱时间:</span><input type="text" class="rl" id="startTime" onClick="WdatePicker()"><i>至</i>
				<input type="text" class="rl" id="endTime" onClick="WdatePicker()">
			</p>
			<p class="p1">
				<span>创建人:</span><input type="text" id="createBy">
				<span>提单号:</span><input type="text" id="loadCode">
				<!-- <span>状态:</span>
				<select id="status">
				   <option value="">全部</option>
			       <option value="0">创建</option>
			       <option value="1">发运</option>
				</select> -->
				<!-- <span>提单号:</span><input type="text" id="createBy"> -->
			</p>
			<p class="p2">
				<button type="button" class="searchBtn" onclick="getLoadPlanListByCondition(1)">搜索</button>
				<a href="javascript:void(0)" id="czhi" onclick="resetfm()">重置</a>
			</p>
			</form>
		</div>
	
		<div class="ck-hd">
			<span class="add-ck-btn">
			<a href="javascript:void(0)" onclick="exportSheet()">导出</a>
			</span>
			
		</div>
	    <div class="goodsWrap">
			
        </div>
	</div>
	</div>
	 <!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
	
</body>
</html>
