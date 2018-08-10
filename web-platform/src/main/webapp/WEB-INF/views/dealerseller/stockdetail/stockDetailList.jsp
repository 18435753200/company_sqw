<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>

<meta charset="UTF-8">
<title>库存明细表</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<script src="${path}/commons/js/my97/WdatePicker.js"></script>
<script src="${path}/commons/js/stockdetail_list_fn.js"></script>
<link rel="stylesheet" type="text/css"
	href="${path }/commons/css/stockDetail.css" />

</head>

<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<!-- 导航 end -->
	<div class="blank10"></div>
	<div class="center">
		<!-- 左边 start -->
		<div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		</div>
		<!-- 左边 end -->


		<div class="right">
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp;</p>
					<p>商品储存&nbsp;&gt;&nbsp;</p>
					<p class="c1">库房明细</p>
				</div>
			</div>

			<div class="xia">
				<p class="p1">
					<span>库房名称：</span><select id="warehouseName">
					</select>
				</p>
				<p class="p1">
					<span>时间：</span><input type="text" id="beginTime"
						onClick="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\',{d:-1});}'})"><i>至</i><input
						id="endTime"
						onClick="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\',{d:1});}'})">
				</p>
				<div class="p1">
					<span>商品类目：</span>
					<div class="str">
						<strong>一级类目:</strong> <select class="w90" id="firstcategory">
						</select> <strong>二级类目:</strong> <select class="w90" id="secondcategory">
						</select> <strong>三级类目:</strong> <select class="w90" id="thirdcategory">
						</select> <strong>四级类目:</strong> <select class="w90" id="fourthcategory">
						</select>
					</div>
				</div>
				<p class="p1">
					<span>商品状态：</span> <select id="pstatus">
						<option value="9">全部</option>
						<option value="1">正品</option>
						<option value="2">残品</option>
					</select>
				</p>
				<p class="p1">
					<span>查询方式：</span> <select id="queryType"><option value="1">按批次</option>
						<option value="2">按商品</option>
					</select> <span id="batchspan">批次：</span> <input type="text" class="w100" id="batchNumber"> 
					<select id="batchNum"></select>
				</p>
				<p class="p2">
					<button type="button" onclick="clickSubmit()">查询</button>
				</p>
			</div>


			<div class="pu_wrap" id="pu_wrap">
				
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