<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>UNICORN_海关订单重推</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/B2C_orderlist.css">
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/wuliu_alert.css"/>
	<script type="text/javascript" src="${path}/commons/js/order_js/b2c_order_list.js"></script>
	<script src="${path}/commons/js/my97/WdatePicker.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		$(".lyan").live("hover",onMouseOver);
		$(".lyan").live("mouseleave",onMouseLeave);
		$.ajax({ 
		     async:false,
		     type : "post", 
		     data:{"statusList":"67,68,69,70,","pageType":"orderFaile"},
		     url : "../customerOrder/getCustomerOrderPageBeanByCondition", 
		     dataType:"html",
		     success : function(customerOrder){
		     	$(".tab-bd").html(customerOrder);
			 },
			 error:function(jqXHR,textStatus,errorThrown){
			  	alert("网络异常,请稍后再试。。。。");
			  }
	     }); 
	});

	function toPage(page){
		byConditionSearchCustomerOrderPush(page,"orderType");
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
<%@include file="/WEB-INF/views/include/header.jsp"%>
<div class="center">
	<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp" %>
	<div class="right">
	
	<!-- 补录物流信息开始 -->
  	<div class="logw">
		<div class="bg"></div>
		<div class="log-c">
			<div class="log-c1">
				<h4>补录物流信息</h4><img src="${path }/commons/images/close.jpg" class="w_close">
			</div> 
			<div class="log-c2">
				<div class="item">
					<span>物流商：</span>
					<select id="logistic" class="">
				</select>
				</div>
				<div class="item">
					<span>物流单号:</span>
					<input type="text" id="logisticCode" class="">
				</div>
				<input type="hidden" value="" id="shipOrderId">
				<div class="item">
					<i class="error"></i>
					<!-- <i class="pass">通过</i> -->
				</div></div>
			
			<div class="log-c3">
				<button type="button" class="bt1" onclick="goUpdateShipOrderLogistic()">确定</button>
				<button type="button" class="bt2">取消</button>
			</div>
		</div>
	</div>
	<!-- 补录物流信息结束 -->
	
	<div id="addDiv" class="alert_user2" style="">
		<div class="bg"></div>
		<div class="w">
			<div class="box1" onclick="closeUpdateXinxi()">
				<img src="../commons/images/close.jpg" class="w_close">
			</div>
			<div class="box3">
			   <h2>修改订单状态</h2>
				<p>
				<span class="sh">订单状态：
				
				 </span>
				 <select id="upStatus">
				<option value="67">海关审核订单失败(待退款)</option>
				<option value="68">海关支付单审核失败(待退款)</option>
				<option value="69">海关物流单审核失败(待退款)</option>
				<option value="70">海关审核失败(待退款)</option>
				</select>
				</p>
				<p><button type="button" class="bt1" onclick="updateB2cOrderStatus()">确定</button></p>
				<input type="hidden" id="upOrderId">
				<br/>
			</div>
			
		</div>
	</div>
	
	
		<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp; </p>
					<p>订单管理&nbsp;&gt;&nbsp; </p>
					<p class="c1">海关订单重推</p>
				</div>
		</div>
			
		<div class="xia" style="height:196px">
		<form action="">
		
		
			<p class="p1">
				<span>订单编号 :</span><input type="text" id="orderId">
				<span>下单时间 :</span>
				 <input type="text" id="startTime" class="rl" onClick="WdatePicker()"> <i>至</i>
				 <input type="text" id="endTime" class="rl" onClick="WdatePicker()">
			</p>
			<p class="p1">
				<span>商品名称 :</span><input type="text" id="pName">
				<span>买家昵称 :</span><input type="text" id="userName">
				<span>收货人 :</span><input type="text" id="receiveName">
			</p>
			<p class="p1">
				<span>订单状态 :</span>
				<!-- 67,68,69 -->
				<select id="status">
				   <option value="67,68,69,70,">全部</option>
			       <option value="67">海关审核订单失败(待退款)</option>
			       <option value="68">海关支付单审核失败</option>
			       <option value="69">海关物流单审核失败</option>
			       <option value="70">海关审核失败</option>
				</select>
				<!-- 11.宁波海外直邮  12.宁波保税区发货 13.郑州海外直邮 14.郑州保税区发货  1.国内发货 21.韩国直邮 -->
				<span>货源种类 :</span>
				<select id="supplyType">
				<option value="">全部方式</option>
				 <option value="1">国内发货</option>
				 <option value="21">韩国直邮</option>
				 <option value="11">海外直邮</option>
				 <option value="12">保税区发货</option>
				 <option value="31">卓悦发货</option>
				 <!-- <option value="13">郑州海外直邮</option>
				 <option value="14">郑州保税区发货</option> -->
				 </select>
			</p>
			<p class="p1">
			     <span>配送方式 :</span>
				 <select id="shipType">
				     <option value="">全部</option>
				     <option value="0">普通</option>
				     <option value="1">自提</option>
				     <%--<option value="2">货到付款</option>--%>
				 </select>
			     <span>支付方式 :</span>
				 <select id="payType">
				     <option value="">全部</option>
				     <option value="0">线上</option>
				     <option value="1">货到付款</option>
				 </select>
			</p>
			</form>
			<p class="p2">
				<button type="button" onclick="byConditionSearchCustomerOrderPush(1,'orderPush')">搜索</button>
				<a href="javascript:void(0)" id="czhi" onclick="resetfm()">重置</a>
				<button onclick="exportCustomerOrderFailExcel()" type="button" class="dc-btn">导出</button>
			</p>
		</div>
		<div class="tab-bd">
		
		</div>
	</div>
</div>
<%@include file="/WEB-INF/views/include/foot.jsp" %>	
</body>
</html>