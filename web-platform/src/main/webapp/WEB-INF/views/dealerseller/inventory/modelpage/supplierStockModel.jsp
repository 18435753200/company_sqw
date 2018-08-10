<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<table class="tb-stock" width="100%">

	<colgroup>
	
		<col width="40%">
		
		<col width="20%">
		
		<col width="20%">
	
	</colgroup>
	
	<c:if test="${empty pb.result}">
		<tr>
			<td colspan="3">
				<center><img src="${path }/commons/images/no.png" /></center>
			</td>
		</tr>
	</c:if>
	
	
	<c:forEach items="${data}" var="stockProd">
	
		<thead>
		
			<tr>
			
				<th colspan="3" class="title">
				<h3>供应商名称:${stockProd.value[0].supplierName }</h3>
				<h3>商品名称:${stockProd.key}</h3>
				</th>
				
			</tr>
			
			<tr>
			
				<th>商品规格</th>
				
				<th>现货库存</th>
				
				<th>订单预定量</th>
				
				
			</tr>
			
		</thead>
		
		<tbody>
		
			<c:forEach items="${stockProd.value}" var="prodSku">
			
				<tr>
				
					<td>${prodSku.skuName}</td>
					
					<td>${prodSku.qty}</td>
					
					<td>${prodSku.presubQty}</td>
					
				</tr>
				
			</c:forEach>
		
		</tbody>
		
	</c:forEach>
	
</table>
<c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp" %>
</c:if>

