<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>商品情景组合_添加</title>
		<%@include file="/WEB-INF/views/include/base.jsp"%>
    <link rel="stylesheet" type="text/css" href="${path }/commons/css/comm.css">
    <script type="text/javascript" src="${path }/commons/js/productMixAdd_list_fn.js"></script>
</head>
<body>
	<div class="center">
		<div class="right">
			
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp; </p>
					<p>商品管理&nbsp;&gt;&nbsp; </p>
					<p class="c1">添加</p>
				</div>
	        </div>
			
			<div class="pu_hd">
				<h3>查询条件:</h3>
			</div>
			<div class="xia">
<!-- 				<p class="p1 mr">
					<span>商品类目:</span>
					<select><option>一级类目</option></select>
					<select><option>二级类目</option></select>
					<select><option>三级类目</option></select>
					<select><option>四级类目</option></select>
				</p> -->
				<form action="javascript:void(0)">
				<p class="p1">
					<span>商品编码:</span>
					<input type="text" id="businessProdId">
					<span>商品条码:</span>
					<input type="text" id="skuCode">
				</p>
				<p class="p1">
					<span>商品名称:</span>
					<input type="text" id="productName">
					<span>供应商名称:</span>
					<input type="text" id="supplierName">
				</p>
				
				<p class="p2">
					<button type="submit" id="subfm" onclick="addClickSubmit()">查询</button>
				</p>
				</form>
			</div>


			<div class="pu_wrap">
				<div class="pu_hd">
				   <h3>商品列表:</h3>

				</div>

			<div id="pu_wrap"></div>

			</div>

		</div>
	</div>
    
    
</body>
</html>
