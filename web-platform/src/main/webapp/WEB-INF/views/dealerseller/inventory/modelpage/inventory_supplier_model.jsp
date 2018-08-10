<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.mall.mybatis.utility.PageBean" %>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<table class="tb">
	<tr class="gong">
		<th class="go1">供应商名称</th>
		<th class="go1">商品名称</th>
		<th class="go1">规格</th>
		<th class="go2">现货库存</th>
		<th class="go2">最大见单生产量</th>
		<th class="go2">订单占用数量</th>
	<tr>
	<c:if test="${empty pb.result}">
		<tr>
			<td colspan="6">
				<center><img src="${path }/commons/images/no.png" /></center>
			</td>
		</tr>
	</c:if>

		<c:forEach items="${pb.result }" var="inv">
			<tr>
			    <td class="go1" title="${fn:escapeXml(inv.supplierName)}" >
			    	<p class="yn1">${fn:escapeXml(inv.supplierName)}</p>
			    </td>
				<td class="go1">
					<p title="${fn:escapeXml(inv.pName)}" class="yn1">${fn:escapeXml(inv.pName)}</p>
				</td>
				<td class="go1">
					<p title="${fn:escapeXml(inv.skuName)}" class="yn1">${fn:escapeXml(inv.skuName)}</p>
				</td>
				<td class="go2">
					<p class="yn2" title="${fn:escapeXml(inv.sumQty)}">${fn:escapeXml(inv.sumQty)}</p>
				</td>
				<td class="go2">
					<p class="yn2" title="${fn:escapeXml(inv.productionQty)}">${fn:escapeXml(inv.productionQty)}</p>
				</td>
				<td class="go2">
					<p class="yn2" title="${fn:escapeXml(inv.presubQty)}">${fn:escapeXml(inv.presubQty)}</p>
				</td>
			</tr>
		</c:forEach>
	</table>

<c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp" %>
</c:if>
