<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>库存调整查询(界面)</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/adj_finish.css">
    <script type="text/javascript" src="${path}/commons/js/adjFinish_list.js"></script> 
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
			<form id="adjFinish_fm">
			
				<div class="c21">
					<div class="title">
						<p>卖家中心&nbsp;&gt;&nbsp; </p>
						<p>商品存储&nbsp;&gt;&nbsp; </p>
						<p class="c1">库存调整查询(界面)</p>
					</div>
		        </div>
				
				<div class="xia">
					<p class="p1">
						<span>商品编号:</span>
						<input type="text" name="skuId" id="skuId">
						<span>库存调整日期:</span>
						<input type="text" name="firstDate" id="firstDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"><i>至</i>
						<input type="text" name="lastDate" id="lastDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});">
					</p>
					<p class="p1">
						<span>商品名称 :</span>
						<input type="text" name="pName" id="pName">
						<span>sku:</span>
						<input type="text" name="sku" id="sku">
						<span>wms调整人:</span>
						<input type="text" name="addwho" id="addwho">
					</p>
					<p class="p2">
						<button type="button" onclick="clickSubmit()">查询</button>
						<button onclick="exportAdjFinishExcel()" type="button" class="dc-btn">导出</button>
					</p>				
				</div>				
			</form>
	   </div>
	   
	   <div id="cs" class="right">
	   </div>
	</div>
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
</body>
</html>