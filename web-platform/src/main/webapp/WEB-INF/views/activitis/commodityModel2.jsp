<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<table class="tb-commodity">
	<tr>
		<th width="40px;">序</th>
		<th width="40px;">选择</th>
		<th width="120px;">商品ID</th>
		<th width="120px;">单品条码</th>
		<th width="150px;">商品名称</th>
		<th width="120px;">规格</th>
		<th width="65px;">商品编码</th>
		<th width="95px;">供应商</th>
	</tr>
	<%int rowNumber = 0; %>
	<c:forEach items="${pb.result}" var="bean">
		<tr>
			<% rowNumber++; %>
			<td><%=rowNumber %></td>
			<td><input type="radio" name="skus" class="fr" id="${bean.skuId }" value="${bean.productId }"></td>
			<td class="name">${bean.productId}</td>
			<td class="name">${bean.skuCode}</td>
			<c:if test="${b2cType==0 }">
				<td class="name">${bean.b2bProdName}</td>
			</c:if>
			<c:if test="${b2cType==1 }">
				<td class="name">${bean.prodName}</td>
			</c:if>
			<td class="name">${bean.skuNameCn}</td>
			<td class="name">${bean.businessProdId}</td>
			<td class="name">${bean.supplierName}</td>
		</tr>
	</c:forEach>
</table>
<c:if test="${!empty pb.result}">
		<%@include file="/WEB-INF/views/activitis/paging2.jsp"%>
</c:if>