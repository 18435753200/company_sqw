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
<table>
			
				
				
					<tr>
						<th class="t1">单选</th>
						<th class="t2">采购单号</th>
					</tr>
					
				
				<c:forEach items="${ChaseOrder}" var="order">
				<tr><td class="t1">
				<input type="radio" name="ChaseOrder" supplierid="1" value="${order.id }">
				</td>
				<td class="t2" title="${order.id }">
				<span>${order.id }</span>
				</td>
				</tr>
				</c:forEach>
				
			</table>
</body>
</html>