<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
      <meta charset="UTF-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
      <title>UNICORN-交易账务</title>
      <%@include file="/WEB-INF/views/include/base.jsp"%>
      <link rel="stylesheet" type="text/css" href="${path }/commons/css/zhanghu.css"/>
  	  <script type="text/javascript">
 	 	$(document).ready(function(){
 	 		clickSubmit();
		});
  	</script>
</head>
<body>
	<!-- 导航 start -->
	
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	 <div class="blank"></div>
	<!-- 导航 end -->
	<div class="center">
         <!-- 左边 start -->
		<div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp" %>
		</div>
		<!-- 左边 end -->

	<!--右边开始-->
	<div class="c2">
		<div class="c21">
			<div class="title">
				<p>卖家中心&nbsp;&gt;&nbsp; </p>
				<p>账务管理&nbsp;&gt;&nbsp; </p>
				<p class="c1">交易账务</p>
				<div class="clear"></div>
			</div>
		</div>
		<div class="blink10"></div>

		<div class="c22">
			<h2>交易账务</h2>
			<form>
			<p class="p1">
				
				交易时间 ：<input type="text" id="startTime" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\',{d:-1});}'})"> 到 <input type="text" id="endTime" onClick="WdatePicker({minDate:'#F{$dp.$D(\'startTime\',{d:1});}'})"><br>
				<span>支付号 ：<input type="text" id="payId"></span><br>
				<span>订单号 ：<input type="text" id="orderId"></span><br>
				金额范围 ：<input type="text" id="smallAmount" class="te2"> 
				到 <input type="text" id="bigAmount" class="te2"><br>
				<button type="button" onclick="clickSubmit()">搜索</button>
				<a href="#" id="czhi" onclick="resetfm()">重置</a>
			</p>
			</form>
		</div>
		<div id="viewList">
		</div>
		</div>
	</div>
	
	<!--右边结束-->



 <!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
	<!-- 底部 end -->
	<script src="${path}/commons/js/my97/WdatePicker.js"></script>
	<script src="${path}/commons/js/accounting.js"></script>
</body>
</html>