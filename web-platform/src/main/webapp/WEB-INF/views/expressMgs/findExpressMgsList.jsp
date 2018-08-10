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
  
    <div class="pu_wrap">
	        	<div class="pu_hd"><h3>快递单列表</h3>
	        		<div class="btn"><a href="javascript:void(0);" onclick="outPutExcel();">导出</a></div>
	        	</div>
	        	<div class="pu_bd">
	        		<table>
	        			<tr class="order_hd">
	        				<th width="40px;">序号</th>
		        			<th width="110px;">订单编号</th>
		        			<th width="110px;">快递单号</th>
		        			<th width="100px;">快递商名称</th>
		        			<th width="100px;">始发地</th>
		        			<th width="100px;">目的地</th>
		        			<th width="80px;">重量(kg)</th>
		        			<th width="100px;">物流信息</th>
		        			<th width="100px;">物流链接</th>
		        		</tr>
		        		<c:forEach items="${pb.result }" var="result" varStatus="status">
						<tr>
							<td>${status.index+1 }</td>
							<td>${result.orderId }</td>
							<td>${result.expressNumber }</td>
							<td>${result.expressName }</td>
							<td>${result.startAddress }</td>
							<td>${result.endAddress }</td>
							<td>${result.weight }</td>
							<td><a href="<%=path%>/expressMgs/detilMsg?expressNumber=${result.expressNumber }&expressId=${result.expressId}&orderId=${result.orderId }" target="_blank">查看明细</a></td>
							<td><a href="${result.expressLink }" target="_blank">进入官网查询</a></td>
						</tr>
						</c:forEach>
	        		</table>
	        		<c:if test="${!empty pb.result}">
					<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
				</c:if>
	        	</div>
	        </div>
  </body>
</html>
