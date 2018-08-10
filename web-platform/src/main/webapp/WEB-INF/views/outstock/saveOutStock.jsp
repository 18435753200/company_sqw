<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>发货单</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/Storage.css">
</head>
<body>
<!-- 导航 start -->
	 <%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<div class="center">
		<!-- 左边 start -->
	 <div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp" %>
		</div>
		
		<!-- 左边 end -->
		<div class="right">

			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp; </p>
					<p>销售出库&nbsp;&gt;&nbsp; </p>
					<p class="c1">发货单</p>
				</div>
	        </div>
			
			<div class="btn" style="text-align: right;">
				<a href="#">修改</a>
				<a href="#">保存</a>
				<a href="#">审核</a>
				<a href="#">打印</a>
			</div>

	        <div class="xia">
	        	<p class="p1">
					<span>发货单编号:</span>
					<input type="text">
					<span>发货时间:</span>
					<input type="text">
					<span>快递商:</span>
					<select><option>顺风快递</option></select>
				</p>
				<p class="p1">
					<span>出库单编号:</span>
					<input type="text">
					<span>出库时间:</span>
					<input type="text">
					<span>快递单号:</span>
					<input type="text">
				</p>
				<p class="p1">
					<span>订单编号:</span>
					<input type="text">
					<span>出库通知单编号:</span>
					<input type="text">
					<span>快递重量:</span>
					<input type="text" class="w60"><i>KG</i>
				</p>
				<p class="p1">
					<span>收件人:</span>
					<input type="text">
					<span>电话:</span>
					<input type="text">
				</p>
				<p class="p1">
					<span>地址:</span>
					<input type="text" class="inp">
					<span>邮政编码:</span>
					<input type="text">
				</p>
	        </div>

	        <div class="pu_wrap">
	        	<div class="pu_hd"><h3>发货商品明细</h3></div>
	       		<div class="xia">
		       		<p class="p1">
						<span>金额合计:</span>
						<input type="text"><i>元</i>
					</p>
				</div>
	        	<div class="pu_bd">
	        		<table>
	        			<tr class="order_hd">
	        				<th width="40px;">序号</th>
		        			<th width="100px;">商品ID</th>
		        			<th width="100px;">商品编号</th>
		        			<th width="100px;">商品名称</th>
		        			<th width="50px;">规格</th>
		        			<th width="50px;">单位</th>
		        			<th width="80px;">出库数量</th>
		        			<th width="80px;">未发货数量</th>
		        			<th width="80px;">本次发货数量</th>
		        			<th width="80px;">销售单价</th> 
		        			<th width="80px;">销售金额</th>   	
		        		</tr>
						<tr>
							<td>1</td>
							<td>1212121212121212</td>
							<td>1212121212121212</td>
							<td>12</td>
							<td>20</td>
							<td>cm</td>
							<td>12</td>
							<td>12</td>
							<td>12</td>
							<td>200</td>
							<td>200</td>
						</tr>
	        		</table>
	        	</div>
	        </div>
			
			<div class="xia">
		       		<p class="p1">
						<span>制单人:</span>
						<input type="text">
					</p>
			</div>
	       
		</div>

	</div>
</body>
</html>