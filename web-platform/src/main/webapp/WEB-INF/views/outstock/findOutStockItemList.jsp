<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/Storage.css">
</head>
<body>
<!-- 导航 start -->
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
		<div class="pu_wrap">
	        	<div class="pu_hd"><h3>待发货商品列表</h3></div>
	        	<div class="pu_bd">
	        		<table>
	        			<tr class="order_hd">
	        				<th width="40px;">序号</th>
		        			<th width="100px;">出库单编号</th>
		        			<th width="100px;">商品编号</th>
		        			<th width="100px;">商品条码</th>
		        			<th width="100px;">商品名称</th>
		        			<th width="100px;">规格</th>
		        			<th width="100px;">单位</th>	 
		        			<th width="100px;">出库数量</th>
		        			<th width="100px;">未发货数量</th>	       	
		        		</tr>
		        		<c:forEach items="${pageBean.result }" var="result" varStatus="status">
						<tr>
							<td>${status.index+1 }</td>
							<td>${result.batchId }</td>
							<td>${result.pcode }</td>
							<td>${result.barCode }</td>
							<td>${result.pname }</td>
							<td>${result.format }</td>
							<td>${result.unit }</td>
							<td>${result.localQty }</td>
							<td>${result.unOutNumber }</td>
						</tr>
						</c:forEach>
	        		</table>
	        	</div>
	    </div>
		</div>
	</div>

</body>
</html>