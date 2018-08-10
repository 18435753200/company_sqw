<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>采购单</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link type="text/css" rel="stylesheet" href="${path}/commons/css/purchase.css">
	 <script src="${path}/commons/js/my97/WdatePicker.js"></script>
	<script type="text/javascript" src="${path}/commons/js/purchase_list_fn.js"></script>
</head>
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
		<div class="c2" >
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp;</p>
					<p>商品管理&nbsp;&gt;&nbsp;</p>
					<p class="c1">采购单列表</p>
				</div>
			</div>

			<div class="xia">
			
				<form id="purchaseorder_fm">
					<p class="p1">
						<span>采购单单号：</span> <input type="text" name="id" id="id"> 
						
						<span class="st">制单时间：</span> 
						<input type="text" name="firstDate" id="firstDate" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'lastDate\',{d:-1});}'})">
						&nbsp;&nbsp;至&nbsp;&nbsp;
						<input type="text" name="lastDate" id="lastDate" onClick="WdatePicker({minDate:'#F{$dp.$D(\'firstDate\',{d:1});}'})">
						
					</p>
	
					<p class="p1">
					
						<span>　　供应商：</span> 
						<input type="text" name="supplierName">
						 <span class="sv">仓 库：</span> 
						 <select name="warehouseCode">
						 	<option value="">所有仓库</option>
							<c:forEach items="${warehouseEnum }" var="warehouseEnum">
								<option value="${warehouseEnum.warehouseCode }">${warehouseEnum.warehouseName }</option>
							</c:forEach>
						</select>
						
				
					</p>
	
					<p class="p2">
						<button type="button" onclick="clickSubmit()">搜索</button>
						<a href="#" id="czhi" onclick="resetfm()">重置</a>
					</p>
					
				</form>
				
			</div>
			
			<div class="ck-hd" style="margin: 8px auto;">
   				<c:if test="${! empty buttonsMap['采购单管理-添加采购单'] }">		
   					 <span class="add-ck-btn"><a href="${path }/purchaseorder/addpurchaseorder?target=1" target="_blank">添加采购单</a></span>
   				</c:if>
   			</div>
			<div class="blank"></div>
			<div  id="modellist">
			</div>
		</div>

	</div>



</body>
</html>