<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>商品储存_入库单(表单)</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/Storage.css">
</head>
<body>
<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 end -->
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
					<p>商品储存&nbsp;&gt;&nbsp; </p>
					<p class="c1">入库单(表单)</p>
				</div>
	        </div>
			
			<div class="xia">
				<p class="p1">
					<span>入库单编号:</span>
					<input type="text">
					<span>入库通知单编号:</span>
					<input type="text">
					<span>入库日期:</span>
					<input type="text">
				</p>
				<p class="p1">
			      <span>库管员:</span>
				  <input type="text">
				  <span>物流通知单编号:</span>
				  <input type="text">
				  <span>库房名称:</span>
				  <input type="text">
				</p>
				
			</div>
			
			<div class="pu_wrap">
			
				<div class="pu_hd">
				<h3>货物入库明细</h3>
					<div class="btn">
			     	<a href="#">保存</a>
			     	<a href="#">审核</a>
			    </div>
				</div>
		  		<div class="pu_bd">
		  			<table>
		  				<tr class="order_hd">
		  					<th width="40px;">序号</th>
		  					<th width="50px;">选择</th>
		  					<th width="110px;">商品ID</th>
		  					<th width="110px;">商品条码</th>
		  					<th width="60px;">规格</th>
		  					<th width="60px;">单位</th>
		  					<th width="80px;">本次通知数量</th>
		  					<th width="80px;">已入库数量</th>
		  					<th width="80px;">本次入库数量</th>
		  					<th width="60px;">库房库位</th>
		  					<th width="110px;">批号</th>
		  				</tr>
		  				<tr>
		  					<td>1</td>
			  				<td><input type="checkbox"></td>
			  				<td>12345678901234567</td>
			  				<td>12345678901234567</td>
			  				<td>小鱼</td>
			  				<td>M</td>
			  				<td>14</td>
			  				<td>12</td>
			  				<td>23</td>
			  				<td>22</td>
			  				<td>12345678901234567</td>
		  				</tr>
		  			
		  			</table>
		  		</div>

	        </div>

		</div>

		


	</div>
</body>
</html>