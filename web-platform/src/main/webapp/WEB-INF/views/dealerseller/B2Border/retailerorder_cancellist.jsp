<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>订单查询_列表</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="${path }/commons/css/order.css"/>
	<link rel="stylesheet" type="text/css" href="${path }/commons/css/reset.css"/>
	<script src="${path}/commons/js/order_js/retailerorder_spotandfutures_cancel.js"></script>
	<script src="<%=path %>/commons/js/my97/WdatePicker.js"></script>
	<script type="text/javascript">
	//初步加载此项目这个页面时
	$(document).ready(function(){
			$(".lyan").live("hover",onMouseOver);
			$(".lyan").live("mouseleave",onMouseLeave);
			$.ajax({ 
			     async:false,
			     type : "post", 
			     data:{"statusList":"21,41,81,"},
			     url : "<%=path%>/retailerOrder/getRetailerOrderListByCancel", 
			     dataType:"html",
			     success : function(retailerOrder) {
			     	$(".tab-bd").html(retailerOrder);
				 },
				 error:function(jqXHR,textStatus,errorThrown){
				  	alert("网络异常,请稍后再试。。。。");
				 }
		     });
		});
		
		//去分页显示（参数page）
	function toPage(page){
		getRetailerOrderList(page);
	}


	function onMouseOver(){
		var td = $(this);
		td.parent(".tcolr").next().show();
	}
	
	function onMouseLeave(){
		var td = $(this);
		td.parent(".tcolr").next().hide();
	}
	</script>
</head>
<body>

<div id="addDiv" class="alert_user2" style="">
	<div class="bg"></div>
	<div class="w">
		<div class="box1" onclick="hideIsfuture()">
			<img src="../commons/images/close.jpg" class="w_close">
		</div>
		<div class="box3">
		   <h2>请选择订单类型</h2>
			<p>
			<span class="sh">期货：<input type="radio" value="1" checked="checked" name="isfuture" id="isfuture"> </span>
			<span class="sh">现货：<input type="radio" value="0" name="isfuture" id="isfuture"></span>
			</p>
			<p><button type="button" class="bt1" onclick="goExportOrderListByConditionExcel()">确定</button></p>
			<p class="tex">众聚商城温馨提示:<br>为了保证您的查询性能，请保证选择的时间间隔不要过大!<br>可能会造成导出失败!</p>
		</div>
		
	</div>
</div>

<%@include file="/WEB-INF/views/include/header.jsp"%>
<div class="center">
<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp" %>
	<div class="right">
			
		<div class="c2">
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp; </p>
					<p>订单管理&nbsp;&gt;&nbsp; </p>
					<p class="c1">B订单取消(未发货)</p>
				</div>
			</div>

			<div class="c22">
				   <div class="xia">
				  <form>
					<p class="p1">
						<span>订单编号 :</span> <input type="text" class="text1" id="payId">
						<span>下单时间 :</span>
						<input type="text" class="text1 rl" id="createTime" onClick="WdatePicker()"><i> 到 </i>
						<input type="text" class="text1 rl" id="endTime" onClick="WdatePicker()">
					</p>
					<p class="p1">
						<span>商品名称 :</span> <input type="text" class="text1" id="pName">
						<span>零售商名称 :</span> <input type="text" class="text1" id="retailerName">
					</p>
					<p class="p1">
						<span>订单状态 :</span> 
						<select id="status">
							<option value="-1">全部</option>
							<option value="21">已支付订单状态</option>
				            <option value="41">待发货状态</option>
				            <option value="81">已打包状态</option>
				<!-- 			<option value="1">待付款 </option>
							<option value="21"> 待付余款</option>
							<option value="81">待收货</option>
							<option value="91"> 已完成</option>
							<option value="100">已取消 </option> -->
						</select>
						<span>发票类型 :</span> 
						<select id="invoiceType">
							<option value="" selected="selected">全部</option>
							<option value="1">增值税普通发票</option>
							<option value="3">增值税专用发票</option>
							<!-- 1.普通发票 2.增值税普通发票 3.增值税专用发票 -->
						</select>
					</p>
					<p class="p1">
						<span>订单来源 :</span> 
						<select id="orderPlatform">
							<option value="" selected="selected">全部</option>
							<option value="1">PC</option>
							<option value="0">PAD</option>
						</select>
						<span>&nbsp;</span> &nbsp;
					</p>
					<p class="p2">
						<button type="button" onclick="getRetailerOrderList(1)" class="bt">搜索</button>
						<a href="#" id="czhi" onclick="resetfm()">重置</a>
						<button onclick="showIsfuture()" type="button" class="dc-btn">导出</button>			
					</p>
					
					
				   </form>
			</div>
			</div>
		</div>
		<div class="tab-bd">	
		</div>
	</div>
</div>
<%@include file="/WEB-INF/views/include/foot.jsp"%>
</body>
</html>