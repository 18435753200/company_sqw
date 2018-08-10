<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.mall.mybatis.utility.PageBean" %>
<%@include file="/WEB-INF/views/include/base.jsp"%>

<table class="tb-stock" width="100%">
	<colgroup>
	<col width="60%">
	<col width="40%">
	</colgroup>
	<c:if test="${empty pb.result}">
		<tr>
			<td colspan="2">
				<center><img src="${path }/commons/images/no.png" /></center>
			</td>
		</tr>
	</c:if>
	
	<c:forEach items="${data}" var="stockProd">
		<thead>
			<tr>
				<th colspan="2" class="title">
				<h3>供应商名称:${stockProd.value[0].supplierName }</h3>
				<h3>商品名称:${stockProd.key}</h3>
				<p>最大预定量：<span>${stockProd.value[0].sumQty }</span></p>
				<p>订单预定总量：<span>${stockProd.value[0].sumPresubQty }</span></p>
				</th>
			</tr>
			<tr>
				<th>商品规格</th>
				<th>订单预定量</th>
			</tr>
		</thead>
		<tbody>
				<c:forEach items="${stockProd.value}" var="prodSku">
					<tr>
						<td>${prodSku.skuName}</td>
						<td>${prodSku.presubQty}</td>
					</tr>
				</c:forEach>
		
		</tbody>
	</c:forEach>
	
</table>
<c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp" %>
</c:if>
