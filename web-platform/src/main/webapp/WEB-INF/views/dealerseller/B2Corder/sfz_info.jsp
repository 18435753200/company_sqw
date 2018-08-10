<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>身份证查询</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/B2C_orderlist.css">
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/wuliu_alert.css"/>
</head>
<body>
<%@include file="/WEB-INF/views/include/header.jsp"%>
<div class="center">
	<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp" %>
	<div class="right">
	<div class="c21">
			<div class="title">
				<p>卖家中心&nbsp;&gt;&nbsp; </p>
				<p>基础设置&nbsp;&gt;&nbsp; </p>
				<p class="c1">身份证查询</p>
			</div>
	     </div>
		<div class="xia" style="height:60px">
		<form action="">
			<p class="p1">
				<span>订单编号 :</span><input type="text" id="orderId">&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" onclick="byConditionSearchOrderSfz()">搜索</button>
			</p>
			</form>
		</div>
		<div class="tab-bd">
		
		</div>
	</div>
</div>
<%@include file="/WEB-INF/views/include/foot.jsp" %>	
</body>
<script type="text/javascript">
function byConditionSearchOrderSfz(){
	var orderId = $.trim($("#orderId").val());
	
	var customerOrder_array = new Array();
	var matchnum = /^[0-9]*$/;

	if(!matchnum.test(orderId)){
  		alert("订单编号只能是数字！");
  		 $("#orderId").val("");
  		 $("#orderId").focus();
  		 return false;
  	}
	if(orderId != ""){
		customerOrder_array.push("orderId="+orderId);
	}
	
	 console.log("------" + orderId);
	$.ajax({
		 type : "post", 
    	 url : "../customerOrder/getSfz", 
    	 data:customerOrder_array.join('&')+"&flag=1&random="+Math.random(),
    	 dataType:"html",
    	 success : function(customerOrder) {
			$(".tab-bd").html(customerOrder);
    	 },
		 error: function(jqXHR, textStatus, errorThrown){
	        alert("查询失败 ，请稍后再试。。");
		 }
	}); 
}
</script>
</html>