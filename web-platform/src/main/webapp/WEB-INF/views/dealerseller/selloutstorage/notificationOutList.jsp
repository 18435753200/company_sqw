<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>通知出库</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${path}/commons/css/reset.css">
<link rel="stylesheet" type="text/css"
	href="${path}/commons/css/Sales.css">
<script src="${path}/commons/js/my97/WdatePicker.js"></script>
<script type="text/javascript"
	src="${path}/commons/js/notification_out_list.js"></script>
</head>
<script type="text/javascript">
	function writePDF(oo) {
		var obj = document.getElementsByName('notifyId');
		var s = '';
		for ( var i = 0; i < obj.length; i++) {
			if (obj[i].checked) {
				s += obj[i].value + ','; //如果选中，将value添加到变量s中        
			}
		}
		s = s.substring(0, s.length - 1);
		if (s == "") {
			alert("请选择出库通知单记录!");
			return;
		}
		var url = "${path}/selloutstorage/NotificationOutOrderPDF?id=" + s;
		oo.attr("href", url);
		oo.attr("target", "_blank");
		oo.click();
	}

	function showIsfuture() {
		$(".alert_user2").show();
	}

	function hideIsfuture() {
		$(".startTime").val("");
		$(".endTime").val("");
		$(".alert_user2").hide();
	}
	
	function isShowOtherType(dat){
		var outTypeChecked = $(dat).val();
		if(outTypeChecked==2){
			$("#outTypeIsOut").show();
		}else{
			$("#outTypeIsOut").hide();
		}
	}
</script>
<body>
	<!-- 导航 start -->
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 end -->
	<div class="blank"></div>
	<div class="center">
		<div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		</div>
		<div class="right">

			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp;</p>
					<p>销售出库&nbsp;&gt;&nbsp;</p>
					<p class="c1">通知出库</p>
				</div>
			</div>
			<form id="notificationoutorder_fm">
				<input type="hidden" id="statusValue" name="statusValue"
					value="${statusValue }"> <input type="hidden"
					id="businessTypeValue" name="businessTypeValue"
					value="${businessTypeValue }"> <input type="hidden"
					id="notificationId" name="notificationId"
					value="${notificationId }"> <input type="hidden"
					id="warehouseCode" name="warehouseCode">
					<input type="hidden"
					id="Comment" name="Comment">
				<div class="xia">
					<p class="p1">
						<span>库房名称:</span> <select id="warehouseName" name="warehouseName">
							<option value="">所有</option>
							<c:forEach items="${warehouseList }" var="warehouse">
								<option value="${warehouse.warehouseCode }">${warehouse.warehouseName
									}</option>
							</c:forEach>
						</select> <span>出库通知单编号:</span> <input type="text"
							id="outNotificationNumber" name="outNotificationNumber"
							value="${outNotificationNumber }"> <span>出库通知单状态:</span>
						<select id="status" name="status">
							<option value="">所有</option>
							<option value="1">待出库</option>
							<option value="2">已打包</option>
							<option value="100">已出库</option>
						</select>
					</p>
					<p class="p1">
						<span>订单编号:</span> <input type="text" id="orderNumber"
							name="orderNumber" value="${orderNumber }"> <span>交易时间:
							从</span> <input type="text" id="beginOutTime" name="beginOutTime"
							onclick="WdatePicker();"
							value="${beginOutTime }"><i>至</i><input type="text"
							id="endOutTime" name="endOutTime"
							onclick="WdatePicker();"
							value="${endOutTime }">


					</p>
					<p class="p1">
						<span>订单来源:</span> <select id="businessType" name="businessType">
							<option value="">全部</option>
							<option value="201">B2B订单</option>
							<option value="202">B2C订单</option>
							<option value="203">换货订单</option>
						</select>
						<span>订单留言:</span> <input type="text" id="orderComment"
							name="orderComment" value="">
						<span>第三方订单号:</span> <input type="text" id="thirdOrderId"
							name="thirdOrderId" value="">
<!-- 						<span>物流单号:</span> <input type="text" id="logisticsOrder" -->
<!-- 							name="logisticsOrder" value=""> -->
						</p>
						<p class="p1">
						<span>出库类型：</span>
						<select name="outType" id="outType" class="w90" onchange="isShowOtherType(this)">
							<option value="">请选择</option>
							<option value="1">领用出库</option>
							<option value="2">销售出库</option>
							<option value="4">特殊出库</option>
						</select>
						<span id="outTypeIsOut" style="display: none;">
						<span style="padding-left: 35px;">客户名称：</span> <select name="otherCusType" id="otherCusType" class="w90">
								<option value="2">请选择</option>
								<c:forEach items="${outTypeMap }" var="outTypeMap" varStatus="outType">
								 		<option value="${outTypeMap.key}">${outTypeMap.value}</option>
								</c:forEach>
						</select>
						</span>
						</p>
					<p class="p2">
						<button type="button" onclick="clickSubmit()">查询</button>
					</p>
				</div>
			</form>


			<div class="pu_wrap">
				<div class="pu_hd">
					<h3>出库通知单列表</h3>
					<div class="btn">
						<!-- <a href="#">全选</a>
	        		<a href="#">反选</a> -->
						 <a href="javascript:void(0)" onclick="exportNotifyOutOrderList()">导出</a> 
						<a	href="javascript:void(0)" onclick="writePDF($(this))" >打印</a>
					</div>

				</div>

				<div class="pu_bd" id="outStorageNotifyOrderList_model"></div>
			</div>

			<!-- <div class="pu_wrap"  id="notificationOutItem_model">
	        
	        </div> -->

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
				<p>
					<span class="sh">订单时间：</span><input name="firstDate" id="firstDate"
						type="text"
						onClick="WdatePicker({maxDate:'#F{$dp.$D(\'lastDate\',{d:-1});}',dateFmt:'yyyy-MM-dd'})"
						class="startTime"><i>至</i> <input name="lastDate"
						id="lastDate" type="text"
						onClick="WdatePicker({minDate:'#F{$dp.$D(\'firstDate\',{d:1});}',dateFmt:'yyyy-MM-dd'})"
						class="endTime">
				</p>
				<p>
					<button type="button" class="bt1"
						onclick="exportNotifyOutOrderList()">确定</button>
				</p>
				<p class="tex">
					众聚商城温馨提示:<br> 为了保证您的查询性能，请保证选择的时间间隔不要过大!<br>可能会造成导出失败!
				</p>
			</div>
		</div>
	</div> 
	<!-- 导出提示提示 -->


</body>
</html>