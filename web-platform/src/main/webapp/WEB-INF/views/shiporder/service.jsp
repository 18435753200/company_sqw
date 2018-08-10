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
						<th class="t2">服务商名称</th>
					</tr>
					
				
				<c:forEach items="${serviceList}" var="service">
				<tr><td class="t1">
				<input type="radio" name="service" supplierid="1" value="${service.sid },${service.serviceCode},${service.serviceName}">
				</td>
				<td class="t2" title="${service.serviceName }">
				<span>${service.serviceName }</span>
				</td>
				</tr>
				</c:forEach>
				
			</table>
</body>
</html>