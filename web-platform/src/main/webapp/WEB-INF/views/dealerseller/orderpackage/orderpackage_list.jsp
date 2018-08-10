<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>UNICORN-包裹查询</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
    <link href="${path}/commons/css/reset.css" rel="stylesheet" type="text/css">
	<link href="${path}/commons/css/b_c_orderpackage.css" rel="stylesheet" type="text/css">
	<script src="<%=path %>/commons/js/order_js/orderpackage.js"></script>
	<script src="<%=path %>/commons/js/my97/WdatePicker.js"></script>
</head>
<body>
<%@include file="/WEB-INF/views/include/header.jsp"%>
<div class="center">
	<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp" %>
	<div class="right">
		<div class="c21">
			<div class="title">
				<p>卖家中心 ></p>
				<p>订单管理 ></p>
				<p class="c1">包裹查询</p>
			</div>
		</div>
		<div class="xia">
			<p class="p1">
				<span>包裹类型:</span>
				<select id="orderType" class="text1">
					<option value="1">B2B包裹</option>
					<option value="2">B2C包裹</option>
				</select>
				<span>订单号:</span><input type="text" class="text1" id="payId">
				<span>包裹号:</span><input type="text" class="text1" id="id">
			</p>
			<p class="p1" id="p1">
			<span>状态:</span>
				<select id="status" class="text1">
				<option value="">全部</option>
				<option value="1">待发货</option>
				<option value="2">已发货</option>
				<option value="3">已完成</option>
				<option value="9">已取消</option>
				</select>
				<span>经销商:</span><input type="text" id="dealerName" class="text1">				
				<span id = "retailerNameSpan">零售商:</span><input type="text" class="text1" id="retailerName">
			</p>
			<p class="p1" id="isFuturesp">
			<span id = "retailerNameSpan">期现货:</span>
				<select id="isFutures" class="text1">
					<option value="">全部</option>
					<option value="0">现货</option>
					<option value="1">期货</option>
				</select>
			</p>
			<p class="p2">
				<button type="button" onclick="getOrderPackageListByCondition(1)" class="bt">搜索</button>
				<a href="#" id="czhi" onclick="resetfm()">重置</a>
				<c:if test="${!empty buttonsMap['包裹查询-导出包裹'] }">
					<button type="button" onclick="showIsfuture()" class="dc-btn">导出</button>
				</c:if>
			</p>
		</div>
		<div class="c3">
	          	
		</div>	
	</div>
</div>
<!-- 导出提示开始 -->
<div class="alert_user2">
	<div class="bg"></div>
	<div class="w">
		<div class="box1" onclick="hideIsfuture()">
			<img src="../commons/images/close.jpg" class="w_close">
		</div>
		<div class="box3">
		    <h2>请选择时间段</h2> 
		    <p><span class="sh">销售类型：</span>
			    <select id="type">
				    <option value="2" selected="selected">销售明细</option>
				    <option value="1">销售汇总</option>
			    </select>
		    </p>
			<p>
			  <span class="sh">成交时间：</span><input type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="startTime"><i>至</i><input type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="endTime">
			</p>
			<p><button type="button" class="bt1" onclick="exportPackageListByConditionExcel()">确定</button></p>
			<p class="tex">众聚商城温馨提示:<br>
			为了保证您的查询性能，请保证选择的时间间隔不要过大!<br>可能会造成导出失败!
			</p>
		</div>
	</div>
</div>
<!-- 导出提示提示 -->
</body>
<%@include file="/WEB-INF/views/include/foot.jsp"%>
</html>