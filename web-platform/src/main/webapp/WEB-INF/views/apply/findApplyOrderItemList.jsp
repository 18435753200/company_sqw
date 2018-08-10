<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="/com/ccigmall/tag" prefix="song"  %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/manual.css">
</head>
<body>
<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 end -->
	<div class="center">
	<!-- 左边 start -->
	 <div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp" %>
		</div>
		
		<!-- 左边 end -->
		<div class="right">
    <div class="pu_hd">
				<h3>申请出库明细</h3>
			</div>
	        <div class="pu_wrap">
		  		<div class="pu_bd">
		  			<table>
		  				<tbody><tr class="order_hd">
		  					<th width="40px;">序号</th>
		  					<th width="100px;">商品编号</th>
		  					<th width="100px;">商品条码</th>
		  					<th width="100px;">商品名称</th>
		  					<th width="70px;">规格</th>
		  					<th width="40px;">单位</th>
		  					<th width="60px;">申请数量</th>
		  					<th width="100px;">批次</th>
		  					<th width="100px;">批号</th>
		  					<th width="65px;">生产日期</th>
		  					<th width="60px;">商品状态</th>
		  				</tr>
		  				<c:forEach items="${itemDtos }" var="result" varStatus="status">
		  				<tr>
		  					<td>${status.index+1}</td>
			  				<td>${result.pcode }</td>
			  				<td>${result.barCode }</td>
			  				<td>${result.pname }</td>
			  				<td>${result.format }</td>
			  				<td>${result.unit }</td>
			  				<td>${result.applyQty }</td>
			  				<td>${result.batchNumber }</td>
			  				<td>${result.batchId }</td>
			  				<td><fmt:formatDate value="${result.productionDate }" pattern="yyyy-MM-dd"/></td>
			  				<td><c:if test="${result.isgenuine==1 }">正品</c:if><c:if test="${result.isgenuine==2 }">残品</c:if></td>
		  				</tr>
		  				</c:forEach>
		  			</tbody>
		  			</table>
		  		</div>
	        </div>
	        </div>
	        </div>
  </body>
</html>
