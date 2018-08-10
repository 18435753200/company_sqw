<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
	<title>UNICORN-期货订单</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<script src="${path}/commons/js/my97/WdatePicker.js"></script>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/maichu.css"/>
	<script type="text/javascript" src="${path}/commons/js/order_js/order_qi_list_fn.js"></script>
</head>
<body>
<!-- 导航 start -->
	 <%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 end -->
	
<!--消息弹框开始-->
	<div class="alert_xiaox">
		<div class="bg"></div>
		<div class="wrap">
			<div class="box1">
				<p class="pic"><img src="${path}/commons/images/close.jpg" class="b_colse"></p>
				<h2>买家留言</h2>
			</div>
			<div class="box2">
				<div class="xinx">
				
				</div>
			</div>
		</div>
	</div>
<!--消息弹框结束-->


	
	<div class="center">
		<!-- 左边 start -->
	 <div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp" %>
		</div>
		
		<!-- 左边 end -->
		
	 <!--左边右边-->
	 <div class="c2">
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp; </p>
					<p>订单管理&nbsp;&gt;&nbsp; </p>
					<p class="c1">期货订单</p>
				</div>
			</div>
			<div class="blank10"></div>
			
			<div class="c22">
			<!-- 	<div class="c21">
					<ul class="top">
						<li class="list" id="suoyou"><a href="javascript:void(0)" onclick="getOrderStatu(0)">所有期货订单</a></li>
						<li id="wancheng"><a href="javascript:void(0)" onclick="getOrderStatu(91)">已完成</a></li> 
					</ul>
				</div> -->
				
				<div class="xia">
				  <form>
					<p class="p1">
						<strong>订单编号 ：</strong> <input type="text" class="text1" id="orderId">
						<strong style="margin-left:38px;">下单时间 ：  </strong>
						<input type="text" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\',{d:-1});}'})" class="text1" id="startTime">
						到
						<input type="text" onClick="WdatePicker({minDate:'#F{$dp.$D(\'startTime\',{d:1});}'})" class="text1" id="endTime">
					</p>
					<p class="p2">
						<strong>商品名称 ：</strong> <input type="text" class="text1" id="pName">
						<strong class="st">零售商名称 ：</strong> <input type="text" class="text1" id="retailerName">
					</p>
					<p class="p3">
					
						<strong class="sk">订单状态 ：</strong> 
						
						<select id="status">
						
							<option value="">所有订单</option>
							<option value="1">待支付</option>
							<option value="21">已付首款</option>
							<option value="31">等待UNICORN合单</option>
							<option value="41">等待供应商发货</option>
							<option value="51">供应商已发货</option>
							<option value="61">等待零售商交付余款</option>
							<option value="71">等待经销商发货</option>
							<option value="81">经销商已发货</option>
							<option value="91">订单完成</option>
							<option value="100">系统自动取消</option>
							<option value="111">买家取消</option>
							<option value="112">客服取消</option>
							
						</select>
						
						<strong class="st">开票类型 ：</strong>
						
						<select id="invoiceType">
						
							<option value="">所有内容</option>
							<option value="1">增值税普通发票</option>
							<option value="3">增值税专用发票</option>
							
						</select>
					</p>
					<p class="p4">
						<button type="button"  onclick="clickSubmit()">搜索</button>
						<a href="#" id="czhi" onclick="resetfm()">重置</a>
					</p>
				   </form>
				</div>
				<div class="clear:both;"></div>
	
			<!-- 所有订单 -->
			<div class="c3">
            	
			</div>
			
		</div>

  </div>
	  <div class="blank10"></div>
	<!--下一页-->
	
</div>
 <div class="blank10"></div>
 <!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
	<script>
	$(document).ready(function(){
		xiao();
	});
	
	
	 var xiao=function(){
	 $(".alert_xiaox").hide();
	 $(".lyan").click(function(){
		$(".alert_xiaox").show();
	 });
	 $(".b_colse").click(function(){
		$(".alert_xiaox").hide();
	 });
	 $(".bt2").click(function(){
		$(".alert_xiaox").hide();
	 });
     };
	</script>
	
</body>
</html>