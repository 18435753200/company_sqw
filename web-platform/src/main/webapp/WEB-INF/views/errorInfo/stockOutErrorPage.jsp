<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>库存错误信息查询</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${path}/commons/css/reset.css">
<link rel="stylesheet" type="text/css"
	href="${path}/commons/css/transfer.css">
	<script src="${path}/commons/js/my97/WdatePicker.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		clickSubmit();
	});
	
	function clickSubmit(page) {
		var fm_data = $('#queryInfo').serialize();

		if (page != undefined) {
			fm_data += "&page=" + page;
		}

		$.ajax({
			type : "post",
			url : "${path}/errorInfo/selectStockOutErrorInfo",
			data : fm_data + "&random=" + Math.random(),
			dataType : "html",
			success : function(msg) {
				$("#cbAllocatePrdList").html(msg);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("查询失败 ，请稍后再试。。");
			}
		});
	}
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
					<p class="c1"> 库存错误信息查询</p>
				</div>
			</div>

			<form id="queryInfo">
				<div class="pu_hd">
					<h3>查询条件</h3>
				</div>
				<div class="xia">
					<p class="p1">
						<span>通知单ID:</span> 
						<input type="text" id="notificationId" name="notificationId">
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