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
<table>			
	<tr>
		<th class="t1">单选</th>
		<th class="t2">商品名称</th>
		<th class="t2">商品编码</th>
	</tr>		
	<c:forEach items="${skuList}" var="product">
		<tr>
			<td class="t1">
				<input type="radio" name="productRadio" value="${product.skuId }" 
				skuCode = "${product.skuCode}" skuNameCn = "${product.skuNameCn}"
				prodName= "${product.supplierProdName }" measure="${product.measure.cname}"
				businessProdId="${product.businessProdId }" productId="${product.productId}"
				>
			</td>
			<td class="t2" title="${product.supplierProdName }${product.skuNameCn }">
				<span>${product.supplierProdName }${product.skuNameCn }</span>
			</td>
			<td class="t2" title="${product.productId }">
				<span>${product.productId }</span>
			</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>