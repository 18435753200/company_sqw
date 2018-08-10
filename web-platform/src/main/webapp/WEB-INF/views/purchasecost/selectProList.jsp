<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>选择商品</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/inventory.css">
         <script src="<%=path%>/commons/js/my97/WdatePicker.js"></script>
     <script type="text/javascript" src="${path}/commons/js/map.js"></script>
     
</head>
<body>

		  		 <div class="pu_bd">
		  			<table id="otab">
		  				<tbody>
		  				<tr class="order_hd">
		  					<th width="40px;">序号</th>
		  					<th width="40px;">选择</th>
		  					<th width="120px;">订单编号</th>
		  					<th width="120px;">商品编号</th>
		  					<th width="120px;">商品条码</th>
		  					<th width="100px;">商品名称</th>
		  					<th width="60px;">数量</th>
		  					<th width="60px;">币别</th>
		  					<th width="60px;">金额</th>
		  					<th width="60px;">本币金额</th>
		  					<th width="60px;">分摊金额</th>
		  					<th width="60px;">sku_id</th>
		  				</tr>
		  				<c:forEach items="${maps }" var="map" varStatus="status">
		  				<tr>
		  					<td>${status.index+1 }</td>
			  				<td><input type="checkbox" class="sm" name="order_check" value="${status.index+1 }"></td>
			  				<td>${map.BUSINESS_NUMBER_CHAR }</td>
			  				<td>${map.BUSINESS_PRODID }</td>
			  				<td>${map.BAR_CODE }</td>
			  				<td>${map.PNAME }</td>
			  				<td>${map.QTY_RECEIVED }</td>
			  				<td>${map.CURRENCY_TYPE }</td>
			  				<td>${map.SKU_PRICE }</td>
			  				<td>${map.LOCAL_NUTAX_PRICE }</td>
			  				<td></td>
			  				<td>${map.SKU_ID }</td>
		  				</tr>
		  				</c:forEach>
		  			</tbody>
		  			</table>
		  		</div>
</body>
</html>