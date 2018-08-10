<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>发货跟踪</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/Storage.css">
</head>
  
  <body>
  
    <%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<div class="center">
		<div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp" %>
		</div>
		
		<!-- 左边 end -->
		
		<div class="right">
		<div class="pu_wrap">
	        	<div class="pu_hd"><h3>快递列表</h3></div>
	        	<div class="pu_bd">
	        		<table>
	        			<tr class="order_hd">
	        				<th width="40px;">处理时间</th>
		        			<th width="110px;">处理信息</th>
		        			
		        		</tr>
		        		<c:forEach items="${msg }" var="m" varStatus="status">
						<tr>
							<td style="text-align: left;" ><fmt:formatDate value="${m.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td style="text-align: left;">${m.expressMessage }</td>
							
						</tr>
						</c:forEach>
	        		</table>
	        		
	        	</div>
	        </div>
		
		</div>
		</div>
  </body>
</html>
