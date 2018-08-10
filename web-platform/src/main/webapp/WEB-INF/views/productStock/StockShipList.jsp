<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>运费核对</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/Storage.css">
</head>
<script src="<%=path%>/commons/js/my97/WdatePicker.js"></script>

<body>
	

			   <div class="pu_wrap">
	        	<div class="pu_hd"><h3>快递单列表</h3>
	        		<div class="btn">
	       			  	<a href="javascript:void(0)" onclick="AllCheck();" >全选</a>
		        		<a href="javascript:void(0)" onclick="unAllCheck();">反选</a>
		        		<a href="javascript:void(0)" onclick="outPutExcel();">导出</a>
	       			<!-- <a href="#">审核</a> -->
	       		</div>
	        	</div>
	       		
	        	<div class="pu_bd">
	        		<table>
	        			<tr class="order_hd">
	        				<th width="40px;">序号</th>
		        			<th width="50px;">审核</th>
		        			<th width="110px;">发货单编号</th>
		        			<th width="110px;">快递单编号</th>
		        			<th width="110px;">快递商名称</th>
		        			<th width="110px;">始发地</th>
		        			<th width="110px;">目的地</th>
		        			<th width="100px;">重量(kg)</th>
		        			<th width="100px;">运费(元)</th>
		        		</tr>
		        		<c:forEach items="${pb.result }" var="result" varStatus="status">
						<tr>
							<td>${status.index+1 }</td>
							<td><input type="checkbox" name="outStockBox"></td>
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
	        		<c:if test="${!empty pb.result}">
					<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
				</c:if>
	        	</div>
	        </div>
</body>
</html>