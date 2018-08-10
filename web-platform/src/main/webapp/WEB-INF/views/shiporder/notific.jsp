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
						<th class="t2">入库通知单号</th>
					</tr>
					
				
				<c:forEach items="${test}" var="t">
				<tr><td class="t1">
				<input type="radio" name="Notific" supplierid="1" value="${t.id }">
				</td>
				<td class="t2" title="${t.id }">
				<span>${t.id }</span>
				</td>
				</tr>
				</c:forEach>
				
			</table>
</body>
</html>