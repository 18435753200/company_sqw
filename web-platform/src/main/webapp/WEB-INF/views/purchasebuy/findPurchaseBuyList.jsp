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
			<div class="pu_wrap">
				<div class="pu_hd"><h3>采购明细</h3></div>
		  		<div class="pu_bd">
		  			<table>
		  				<tr class="order_hd">
		  					<th width="40px;">序号</th>
		  					<th width="50px;">选择</th>
		  					<th width="120px;">商品编码</th>
		  					<th width="120px;">商品条码</th>
		  					<th width="150px;">商品名称</th>
		  					<th width="120px;">采购数量</th>
		  					<th width="120px;">实际入库数量</th>
		  					<td width="120px;">差异数量</td>
		  					<td width="120px;">采购差异</td>
		  					<th width="120px;">采购单号</th>
		  				</tr>
		  				<c:forEach items="${pb.result }" var="result" varStatus="status">
		  				<tr>
		  					<td>${status.index+1 }</td>
			  				<td><input type="checkbox" name="buss" value="${result.purchaseId },${result.skuId },${result.qty },${result.qtyReceived },${result.errorQty },${result.lastEditBy }"></td>
			  				<td><a href="<%=path%>/purchasebuy/findPurchaseBuyItemList?id=${result.skuId }&purchaseId=${result.purchaseId }">${result.businessProdid }</a></td>
			  				<td>${result.barCode }</td>
			  				<td>${result.pname }</td>
			  				<td>${result.qty }</td>
			  				<td>${result.qtyReceived }</td>
			  				<td>${result.errorQty }</td>
			  				<td><c:if test="${result.orderStatus==15 }"><a href="<%=path%>/purchaseError/selectPurchaseError?purchaseId=${result.purchaseId}&skuId=${result.skuId}">查看</a></c:if><c:if test="${result.orderStatus!=15 }"><a href="<%=path%>/purchaseError/findPurchaseError?purchaseId=${result.purchaseId}&skuId=${result.skuId}">差异明细</a></c:if></td>
			  				<td>${result.lastEditBy }</td>
		  				</tr>
		  			</c:forEach>
		  			</table>
		  			<c:if test="${!empty pb.result}">
					<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
				</c:if>
		  		</div>

	        </div>
</body>
</html>