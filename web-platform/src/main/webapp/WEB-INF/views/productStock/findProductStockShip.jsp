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
   <%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<div class="center">
			<!-- 左边 start -->
	 <div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp" %>
		</div>
		
		<!-- 左边 end -->
		<div class="right">
   <div class="pu_wrap">
	        	<div class="pu_hd"><h3>快递单列表</h3></div>
	        	<div class="pu_bd">
	        		<table>
	        			<tr class="order_hd">
	        				<th width="40px;">序号</th>
		        			<th width="100px;">审核</th>
		        			<th width="100px;">发货单编号</th>
		        			<th width="100px;">快递单编号</th>
		        			<th width="100px;">快递商名称</th>
		        			<th width="100px;">始发地</th>
		        			<th width="100px;">目的地</th>
		        			<th width="100px;">重量(kg)</th>
		        			<th width="100px;">运费(元)</th>
		        		</tr>
		        		<c:forEach items="${ships }" var="result" varStatus="status">
						<tr>
							<td>${status.index+1 }</td>
							<td><c:if test="${result.status==1 }">未审核</c:if><c:if test="${result.status==10 }">已审核</c:if></td>
							<td>${result.outId }</td>
							<td>${result.expressNumber }</td>
							<td>${result.expressName }</td>
							<td>${result.startAddress }</td>
							<td>${result.endAddress }</td>
							<td>${result.weight }</td>
							<td>${result.freightPrice }</td>
						</tr>
						</c:forEach>
	        		</table>
	        	</div>
	        </div>
	        </div>
	        </div>
  </body>
</html>
