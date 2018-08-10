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
		<th class="t2">供应商名称</th>
	</tr>
	<c:forEach items="${supplierList}" var="service">
	<tr>
		<td class="t1">
			<input type="radio" name="service"
			supplierId = "${fn:escapeXml(service.supplierId)}"
			contact = "${fn:escapeXml(service.contact)}"
			phone = "${service.phone}"
			value="${fn:escapeXml(service.name)}">
		</td>
		<td class="t2" title="${service.name }">
			<span>${service.name }</span>
		</td>
	</tr>
	</c:forEach>
</table>
</body>
</html>