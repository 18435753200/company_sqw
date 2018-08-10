<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>

<table class="tb-coupon">
	<colgroup>
		<col width="150px">
		<col width="110px">
		<col width="150px">
		<col width="150px">
		<col width="150px">
		<col width="128px">
	</colgroup>
	<thead>
		<tr>
			<th>使用规则名称</th>
			<th>优惠券类型</th>
			<th>面值金额</th>
			<th>满足金额</th>
			<th>状态</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${empty pb.result}">
			<tr>
				<td colspan="6">
					<center>
						<img src="${path }/commons/images/no.png" />
					</center></td>
			</tr>
		</c:if>
		<c:forEach items="${pb.result}" var="couponRule">
			<tr>
				<td class="name">${fn:escapeXml(couponRule.couponrulename)}</td>
				<td class="type"><c:if test="${couponRule.coupontype==1}">
				金券
				</c:if> <c:if test="${couponRule.coupontype==2}">
				鑫券
				</c:if>
				</td>
				<td class="money">100￥</td>
				<td class="money">${couponRule.meetpiece}100￥</td>
				<td class=""><c:if test="${couponRule.status==1}">
				已启用
				</c:if> <c:if test="${couponRule.status==0}">
				已停用
				</c:if></td>
				<td class="tac">
					<a href="javascript:void(0);"
					onclick="updateStatus(${fn:escapeXml(couponRule.couponruleid)},${fn:escapeXml(couponRule.status)})">
						<c:if test="${couponRule.status==0}">启用
							</c:if> <c:if test="${couponRule.status==1}">停用
							</c:if> 
				    </a>
			    </td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
</c:if>