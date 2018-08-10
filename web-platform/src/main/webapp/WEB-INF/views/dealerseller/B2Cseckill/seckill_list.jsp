<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>APP秒杀活动</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link href="${path}/commons/css/reset.css" rel="stylesheet"	type="text/css">
<link href="${path}/commons/css/promotion.css" rel="stylesheet"	type="text/css">
<script src="${path}/commons/js/my97/WdatePicker.js"></script>
<script src="${path}/commons/js/order_js/b2c_seckill_add.js"></script>
<link rel="stylesheet" type="text/css" href="${path }/commons/css/coupon.css" />
<link rel="stylesheet" type="text/css" href="${path }/commons/css/prom.css" />

<script type="text/javascript">
$(document).ready(function(){
	$.ajax({ 
	     async:false,
	     type : "post", 
	     url : "../secondKill/getSecondKillListByContion", 
	     dataType:"html",
	     success : function(secondKill){
	     	$(".promotion-bd").html(secondKill);
		 },
		 error:function(jqXHR,textStatus,errorThrown){
		  	alert("网络异常,请稍后再试。。。。");
		  }
     }); 
});
</script>
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<!-- 导航 end -->
	<div class="blank10"></div>
	<div class="center">
		<!-- 左边 start -->
		<div class="left f_l">
			<%@include file="/WEB-INF/views/promotions/leftPage.jsp"%>
		</div>
		<!-- 左边 end -->
		<div class="center">
			<div class="right">
				<div class="c21">
					<div class="title">
						<p>促销管理&nbsp;&gt;&nbsp; </p>
						<p>活动管理&nbsp;&gt;&nbsp; </p>
						<p class="c1">秒杀活动</p>
					</div>
		        </div>
				<div class="pu_hd"><h3>查询条件</h3></div>
		        <div class="xia">
		        	<p class="p1">
		        		<span>商品编号:</span>
		        		<input type="text" name="expiringFrom" id="pid" class="promotion-txt"/>
		        		
		        		<span>规格编号:</span>
		        		<input type="text" name="expiringFrom" id="skuid" class="promotion-txt"/>
		        	</p>
		        	<p class="p1">
		        		<span>开始时间:</span>
		        		<input type="text" onClick="WdatePicker()" name="expiringFrom" id="startTime" class="promotion-txt"/>
		        		
		        		<span>结束时间:</span>
		        		<input type="text" onClick="WdatePicker()" name="expiringFrom" id="endTime" class="promotion-txt"/>
		        	</p>
		        	
		        	<p class="p2"><button type="button" onclick="byConditionSearchCustomerOrder(1)">查询</button> 
		        				  <button type="button" onclick="reset()">重置</button></p>
		        </div>
		        <div class="pu_wrap">
		        	<div class="pu_hd"><h3>秒杀列表</h3>
						<div class="btn">
			        		<a href="../secondKill/goAddecondKillProduct">创建秒杀</a>	
			        	</div>
					</div>
					<div class="promotion-bd">
						
					</div>
		        </div>
			</div>
		</div>
	</div>
	
	<div class="blank10"></div>
	<!-- 底部 start -->
	<div class="t_footer">
			<h1 class="logo_dl"></h1>
	</div>
</body>
</html>