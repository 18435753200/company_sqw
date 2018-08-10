<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<div class="pu_mix">
	<table>
		<tr class="order_hd">
			<th width="150px;">商品ID</th>
			<th width="150px;">商品条码</th>
			<th width="180px;">商品名称</th>
			<th width="180px;">商品图片</th>
			<th width="120px;">操作</th>
		</tr>
		<c:forEach items="${pb.result}" var="mix" varStatus="status">
		<tr>
			<td>${mix.productId}</td>
			<td>${mix.skuCode}<i class="skuId">${mix.skuId}</i></td>
			<td>${mix.prodName} ${mix.skuNameCn}</td>
			<td class="img"><img src="${mix.imageUrl}" title="${fn:escapeXml(mix.prodName)}" height="100px"> </td>
			<td class="verify">确认</td>
		</tr>
		</c:forEach>
	</table>
	<c:if test="${!empty pb.result}">
		<%@include file="/WEB-INF/views/dealerseller/productMix/modelpage/paging.jsp"%>
	</c:if>
</div>