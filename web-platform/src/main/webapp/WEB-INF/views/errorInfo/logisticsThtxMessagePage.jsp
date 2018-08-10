<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>WMS推送错误信息查询</title>
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
		var messageTypes = $("#messageTypes").val();
		$("#messageType").val(messageTypes);

		var fm_data = $('#queryInfo').serialize();

		if (page != undefined) {
			fm_data += "&page=" + page;
		}

		$.ajax({
			type : "post",
			url : "${path}/errorInfo/selectLogisticsThtxMessageInfo",
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

	function selectById(messageId) {

		window.location.href = "${path}/errorInfo/selectByPrimaryKey?messageId="
				+ messageId;
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
					<p class="c1">WMS推送错误信息查询</p>
				</div>
			</div>

			<form id="queryInfo">
				<div class="pu_hd">
					<h3>查询条件</h3>
					<input type="hidden" id="messageType" name="messageType">
				</div>
				<div class="xia">
					<p class="p1">
						<span>时间:</span> <input type="text" name="firstDate"
							id="firstDate"
							onClick="WdatePicker({maxDate:'#F{$dp.$D(\'lastDate\',{d:-1});}',dateFmt:'yyyy-MM-dd HH:mm:ss'})">
						<i>至</i> <input type="text" name="lastDate" id="lastDate"
							onClick="WdatePicker({minDate:'#F{$dp.$D(\'firstDate\',{d:1});}',dateFmt:'yyyy-MM-dd HH:mm:ss'})">

						<span>消息类型:</span> <select id="messageTypes" name="messageTypes">
							<option value="">请选择</option>
							<option value="1">入库</option>
							<option value="2">出库</option>
							<option value="3">物流</option>
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