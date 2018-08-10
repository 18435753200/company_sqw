<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<table>
	<tr class="order-hd">
		<th class="head8">促销名称</th>
		<th class="head5">开始时间</th>
		<th class="head5">结束时间</th>
		<th class="head3">促销类型</th>
		<th class="head4">描述</th>
		<th class="head7">操作</th>
	</tr>
	<c:if test="${empty pb.result}">
		<tr>
			<td colspan="6">
				<center><img src="${path }/commons/images/no.png" /></center>
			</td>
		</tr>
	</c:if>
	<c:forEach items="${pb.result }" var="promotion">
		<tr class="order-bd">
			<td class="cxiao"><p class="ov" title="${fn:escapeXml(promotion.promotionName) }">${fn:escapeXml(promotion.promotionName) }</p></td>
			<td class="time"><fmt:formatDate value="${promotion.startTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td class="time"><fmt:formatDate value="${promotion.endTime }"   pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td class="Price">
				<c:if test="${promotion.type==1 }">
					直降
				</c:if>
			</td>
			<td class="suh"><p class="ov" title="${fn:escapeXml(promotion.info) }">${fn:escapeXml(promotion.info) }</p></td>
			<td class="czou"><a href="${promotion.promotionId }">编辑</a></td>
		</tr>
	</c:forEach>
</table>
<c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
</c:if>