<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<table>
	<tr>
		<th width="60px;">序</th>
		<th width="60px;">选择</th>
		<th width="130px;">单品编码</th>
		<th width="130px;">单品名称</th>
		<th width="130px;">商品编码</th>
		<th width="140px;">商品名称</th>
	</tr>
	<c:forEach items="${pagebean.result}" var="bean">
		<tr>
			<td></td>
			<td><input type="checkbox" name="skus" class="fr"></td>
			<td>${bean.skuId }</td>
			<td class="name">${bean.skuNameCn}</td>
			<td>${bean.productId }</td>
			<td class="name">${fn:escapeXml(bean.prodName)}</td>
		</tr>
	</c:forEach>
</table>
<c:if test="${!empty pagebean.result}">
	<%@include file="/WEB-INF/views/promotions/paging.jsp"%>
</c:if>