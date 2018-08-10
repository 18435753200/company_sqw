<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>特卖虚拟库存设置_库存设置(界面)</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/purchase_s.css">
    <script type="text/javascript" src="${path}/commons/js/purchasevirtualorder_js/purchasevirtual_list.js"></script> 
    <script src="${path}/commons/js/my97/WdatePicker.js"></script>
    
   <script type="text/javascript">


</script>
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
		<div class="right">
		<form id="purchaseorder_fm">
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp; </p>
					<p style="width:130px;">特卖虚拟库存设置&nbsp;&gt;&nbsp; </p>
					<p class="c1">虚拟库存设置列表(界面)</p>
				</div>
	        </div>
			
			<div class="xia">
				<p class="p1">
					<span>虚拟设置编号:</span>
					<input type="text" name="businessNumberChar" id="businessNumberChar">
					<span>操作日期:</span>
					<input type="text" name="firstDate" id="firstDate" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'lastDate\',{d:-1});}'})"><i>至</i>
					<input type="text" name="lastDate" id="lastDate" onClick="WdatePicker({minDate:'#F{$dp.$D(\'firstDate\',{d:1});}'})">
					
				</p>
				<p class="p1">
					<span>操作人:</span>
					<input type="text" name="createBy">
					<span>供应商名称:</span>
					<input type="text" name="supplierName">
				</p>
				<p class="p2">
					<button type="button" onclick="clickSubmit()">查询</button>
				</p>
				
				</div>
				
				
			</form>
			
		   </div>
		   <div class="btn">
		   		<!--  <button type="button" onclick="exportPdf()">导出报表</button> -->
				<button type="button" onclick="addOrder()">新增订单</button>
		  </div>
		   <div id="cs" class="right">
		   
				
				
			</div>
	</div>
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
</body>
</html>