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
<c:if test="${not empty page.result }">
	<table>
		<tr>
			<th class="t1">单选</th>
			<th class="t2" style="width: 50px;text-align: center;">企业ID</th>
			<th class="t2" style="width: 50px;text-align: center;">企业代码</th>
			<th class="t2">企业名称</th>
		</tr>
		<c:forEach items="${page.result}" var="page">
		<tr>
			<td class="t1">
				<input type="radio" <c:if test="${empty page.userId }">disabled="disabled" title="该企业无企业小号，无法分配!"</c:if> name="checkSupplierCode" id="checkSupplierCodeId" value="${page.supplierCode}">
			</td>
			<td class="t2" title="${page.supplierId}">
				<span style="width: 50px;text-align: center;">${page.supplierId}</span>
			</td>
			<td class="t2" title="${page.supplierCode}">
				<span style="width: 50px;text-align: center;">${page.supplierCode}</span>
			</td>
			<td class="t2" title="${page.name}">
				<span style="text-align: center;">${page.name}</span>
			</td>
		</tr>
		</c:forEach>
	</table>
</c:if>
<c:if test="${empty page.result}">
	<tr>
		<td>
			<center><img src="${path }/commons/images/no.png" /></center>
		</td>
	</tr>
</c:if>
<c:if test="${!empty page.result}">
	<%@include file="/WEB-INF/views/include/page2.jsp"%>
</c:if>
</body>
</html>