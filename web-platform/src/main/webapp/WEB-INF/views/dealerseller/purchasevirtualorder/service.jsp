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
						<th class="t2">服务商名称</th>
					</tr>
					
				
				<c:forEach items="${serviceList}" var="service">
				<tr><td class="t1">
				<input type="radio" name="serviceSupply" supplierid="1" 
				sid="${service.sid }" serviceCode="${service.serviceCode}" serviceName="${service.serviceName}"
				serviceContact="${service.serviceContact}", servicePhone="${service.servicePhone}">
				</td>
				<td class="t2" title="${service.serviceName }">
				<span>${service.serviceName }</span>
				</td>
				</tr>
				</c:forEach>
				
			</table>
</body>
</html>