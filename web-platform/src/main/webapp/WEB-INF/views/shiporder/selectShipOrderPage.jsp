<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>物流查询</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${path}/commons/css/reset.css">
<link rel="stylesheet" type="text/css"
	href="${path}/commons/css/transfer.css">
	<script type="text/javascript" src="${path}/commons/js/shipOrderEdit.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		clickSubmit();
	});
</script>
</head>
<body>
	<!-- 导航 START -->
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 END -->

	<div class="blank"></div>

	<div class="center">
		<!-- 左侧菜单 START -->
		<div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		</div>
		<!-- 左侧菜单 END -->

		<div class="right">
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp;</p>
					<p>商品储存&nbsp;&gt;&nbsp;</p>
					<p class="c1">物流查询</p>
				</div>
			</div>

			<form id="queryInfo">
				<div class="pu_hd">
					<h3>查询条件</h3>
					<input type="hidden" id="businessType" name="businessType">
				</div>
				<div class="xia">
					<p class="p1">
						<span>业务单号:</span> 
						<input type="text" id="businessNumber" name="businessNumber">
							
						<span>业务单类型:</span>
						<select id="businessTypes" name="businessTypes">
							<option value="">请选择</option>
							<option value="101">采购单业务</option>
							<option value="102">换货单业务</option>
							<option value="103">退货单业务</option>
							<option value="105">调拨单业务</option>
						</select>
					</p>
					<p class="p2">
						<button type="button" id="selectPrd" name="selectPrd" value="查询"
							onclick="clickSubmit()">查询</button>
					</p>
				</div>
			</form>

			<!-- DIV异步调用填充-->
			<div class="pu_bd" id="cbAllocatePrdList"></div>
		</div>
	</div>
</body>
</html>