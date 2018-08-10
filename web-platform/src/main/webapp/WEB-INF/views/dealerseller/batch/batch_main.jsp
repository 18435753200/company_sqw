<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
<title>UNICORN-库存管理</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link rel="stylesheet" type="text/css"	href="${path}/commons/css/inventory1.css" />
<script src="${path}/commons/js/my97/WdatePicker.js"></script>
<script src="${path}/commons/js/batch_fn.js"></script>
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="center">
		<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>

		<div class="c2">
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp;</p>
					<p>商品管理&nbsp;&gt;&nbsp;</p>
					<p class="c1">批次库存管理</p>
				</div>
			</div>
			<div class="blank10"></div>

			<div class="c22">
				<div class="search">
					<form id="inventory_fm">
						<div class="search-form clearfix">
							<dl id="inventorytypeSpan">
								<dt>商品名称：</dt>
								<dd>
									<input name="pname" type="text" id="batch_pname" class="search-text">
								</dd>
							</dl>
							<dl>
								<dt>商品ID：</dt>
								<dd>
									<input name="pid" type="text" id="batch_pid" class="search-text">
								</dd>
							</dl>
							<dl id="dl_sku_id">
								<dt>商品SKU：</dt>
								<dd>
									<input name="sku_id" type="text" id="batch_sku_id" class="search-text">
								</dd>
							</dl>
							<dl id="dl_batch_No">
								<dt>批次号：</dt>
								<dd>
									<input name="batch_no" type="text" id="batch_no" class="search-text">
								</dd>
							</dl>
							<dl id="to_expiry_days">
								<dt>离过期天数：</dt>
								<dd>
									<input name="to_expiry" type="text" id="to_expiry" class="search-text">
								</dd>
							</dl>
						</div>
					</form>
					<div class="search-sub">
						<input onclick="clickSubmit()" type="button" value="搜索" class="search-btn confirm-btn">
						<input onclick="resetfm()" type="button" id="czhi" value="重置" class="search-btn cancel-btn">
					</div>


				</div>

				<div class="c24" id="c24" style="overflow: auto;">
					<table class="tb" style="width:1200px">
						<tr class="tz">
							<th class="t1">商品名称</th>
							<th class="t1">规格</th>
							<th class="t1">SKU</th>
							<th class="t1">库存数量</th>
							<th class="t1">批次</th>
							<th class="t3">生产日期</th>
							<th class="t3">有效日期</th>
						<tr>
						<tr>
							<td colspan="9">
								<center>请至少选择一个查询条件</center>
							</td>
						</tr>
					</table>
				</div>

			</div>
			<div class="blank20"></div>
		</div>
	</div>
	<%@include file="/WEB-INF/views/include/foot.jsp" %>
</body>
</html>