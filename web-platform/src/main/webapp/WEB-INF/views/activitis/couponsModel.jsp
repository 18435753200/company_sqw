<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<table class="tb-coupons">
		<tr>
			<th width="40px;">序</th>
			<th width="40px;">选择</th>
			<th width="170px;">优惠券名称</th>
			<th width="150px;">优惠券规则名称</th>
			<th width="120px;">优惠券类型</th>
			<th width="130px;">开始时间</th>
			<th width="130px;">结束时间</th>
		</tr>
		<%int rowNumber = 0; %>
		<c:forEach items="${pb.result}" var="bean">
			<tr>
				<% rowNumber++; %>
				<td><%=rowNumber %></td>
				<td><input type="radio" name="skus" class="fr" id="${bean.couponRuleId}"></td>
				<td class="name">${bean.couponName}</td>
				<td class="name">${bean.couponRuleName}</td>
				<td class="name">
					<c:if test="${bean.couponType == '1'}">金券</c:if>
				</td>
				<td class="date"><fmt:formatDate value="${bean.startDate }"
						pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td class="date"><fmt:formatDate value="${bean.endDate }"
						pattern="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
		</c:forEach>
</table>
<c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/activitis/pagingCouponsCommon.jsp"%>
</c:if>