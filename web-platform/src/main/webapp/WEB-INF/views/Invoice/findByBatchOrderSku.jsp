<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>发票登记查询</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/inventory.css">
     <script src="<%=path%>/commons/js/my97/WdatePicker.js"></script>
</head>
<body>
<div class="pu_b">
	  			<table id="otab">
	  				<tbody>
	  				<tr class="order_hd">
	  					<th width="40px;">序号</th>
	  					<th width="40px;">选择</th>
	  					<th width="120px;">订单编号</th>
	  					<th width="120px;">商品编码</th>
	  					<th width="120px;">商品名称</th>
	  					<th width="120px;">商品条码</th>
	  					<th width="120px;">规格</th>
	  					<th width="100px;">单位</th>
	  					<th width="100px;">订单数量</th>
	  					<th width="100px;">已入库数量</th>
	  					<th width="100px;">已结算数量</th>
	  					<th width="100px;">已结算金额</th>
	  					<th width="100px;">未结算数量</th>
	  					<th width="100px;">未结算金额</th>
	  					<th width="110px;">批次号</th>
	  					<th width="100px;">入库日期</th>
	  					<th width="100px;">币别</th>
	  					<th width="100px;">汇率</th>
	  					<th >SKUID</th>
	  					<th >ORDERID</th>
	  					<th width="100px;">税率(%)</th>
	  				</tr>
	  				<c:forEach items="${maps }" var="map" varStatus="status">
	  				<tr>
	  					<td>${status.index+1 }</td>
		  				<td><input type="checkbox" class="sm" name="order_check" value="${status.index+1 }"></td>
		  				<td>${map.BUSINESS_NUMBER_CHAR }</td>
		  				<td>${map.PCODE }</td>
		  				<td>${map.SKU_NAME_CN }</td>
		  				<td>${map.BAR_CODE }</td>
		  				<td>${map.FORMAT }</td>
		  				<td>${map.UNIT }</td>
		  				<td>${map.QTY }</td>
		  				<td>${map.QTY_RECEIVED }</td>
		  				<td>${map.PAY_QTY }</td>
		  				<td>${map.RECEIVED_PRICE }</td>
		  				<td>${map.QTY_RECEIVED-map.PAY_QTY }</td>
		  				<td>${map.SUBTOTAL_PRICE-map.RECEIVED_PRICE }</td>
		  				<td>${map.BATCH_NUMBER }</td>
		  				<td><fmt:formatDate value="${map.CREATE_TIME }"  pattern="yyyy-MM-dd"/></td>
		  				<td>${map.CURRENCY_TYPE }</td>
		  				<td>${map.EXCHANGE_RATE }</td>
		  				<td>${map.SKU_ID }</td>
		  				<td>${map.ID }</td>
		  				<td>${map.DUTY_RATE }</td>
	  				</tr>
	  				</c:forEach>
	  			</tbody>
	  			</table>
	  		</div>
	</body>
</html>