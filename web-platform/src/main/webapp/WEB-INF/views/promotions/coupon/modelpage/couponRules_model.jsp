<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>

<table class="tb-coupon">
	<colgroup>
	    <col width="155px">
		<col width="155px">
		<col width="100px">
		<col width="100px">
		<col width="100px">
		<col width="100px">
		<col width="100px">
		<col width="128px">
		<col width="128px">
	</colgroup>
	<thead>
		<tr>
			<th>使用规则编码</th>
			<th>使用规则名称</th>
			<th>优惠券类型</th>
			<th>面值金额</th>
			<th>满足金额</th>
			<th>剩余券总数量</th>
			<th>状态</th>
			<th>B2B / B2C</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${empty pb.result}">
			<tr>
				<td colspan="6">
					<center>
						<img src="${path }/commons/images/no.png" />
					</center>
				</td>
			</tr>
		</c:if>
		<c:forEach items="${pb.result}" var="couponRule">
			<tr>
				<td class="">${fn:escapeXml(couponRule.couponruleid)}</td>
				<td class="name">${fn:escapeXml(couponRule.couponrulename)}</td>
				<td class="type"><c:if test="${couponRule.coupontype==1}">
				金券
				</c:if> <c:if test="${couponRule.coupontype==2}">
				现金券
				</c:if></td>
				<td class="money">${couponRule.couponacount}￥</td>
				<td class="money">${couponRule.meetpiece}￥</td>
				<td class="money">${couponRule.qty}</td>
				<td class=""><c:if test="${couponRule.status==1}">
				已启用
				</c:if> <c:if test="${couponRule.status==0}">
				已停用
				</c:if>
				</td>
				<td>
					<c:if test="${couponRule.isb2c == 1}">
						B2C
					</c:if>
					<c:if test="${couponRule.isb2c == null}">
						B2B
					</c:if>
				</td>
				<td class="tac">
				<c:if test="${!empty buttonsMap['优惠券列表-券规则停用启用']}">
					<a href="javascript:void(0);"
						onclick="updateStatus('${fn:escapeXml(couponRule.couponruleid)}','${fn:escapeXml(couponRule.status)}');">
							<c:if test="${couponRule.status==0}">启用</c:if> 
							<c:if test="${couponRule.status==1}">停用</c:if>
					</a>
				</c:if>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
</c:if>

