  <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
</head>
<body>
</br>
<font  style="font-size: 13px" color="red">提示：请优先勾选“商品条码”与“拣货码”相同的商品，避免仓库重新贴条码！</font>
<table>
	<tr>
		<th class="t1">单选</th>
		<th class="t2">商品名称</th>
		<th class="t2">商品条码</th>
		<th class="t2">拣货码</th>
		<th class="t2">商品编码</th>
	</tr>		
	<c:forEach items="${skuList}" var="product">
		<tr>
			<td class="t1">
				<input type="radio" name="productRadio" value="${product.skuId }" 
				skuCode = "${product.skuCode}" skuNameCn = "${product.skuNameCn}"
				prodName= "${product.supplierProdName }" measure="${product.measure.cname}"
				businessProdId="${product.businessProdId }"
				>
			</td>
			<td class="t2" title="${product.supplierProdName }${product.skuNameCn }">
				<span>${product.supplierProdName }${product.skuNameCn }</span>
			</td>

			<td class="t2" title="${product.skuCode}">
				<span><c:if test="${product.skuCode==product.barSkuId}">
					<font color="red">${product.skuCode}</font>
				</c:if><c:if test="${product.skuCode!=product.barSkuId}">
					${product.skuCode}
				</c:if>
				</span>
			</td>
			<td class="t2" title="${product.barSkuId }">
				<span><c:if test="${product.skuCode==product.barSkuId}">
					<font color="red">${product.barSkuId}</font>
				</c:if><c:if test="${product.skuCode!=product.barSkuId}">
					${product.barSkuId}
				</c:if></span>
			</td>

			<td class="t2" title="${product.businessProdId }">
				<span>${product.businessProdId }</span>
			</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>