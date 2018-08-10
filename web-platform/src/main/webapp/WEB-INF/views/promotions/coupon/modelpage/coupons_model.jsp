<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<table class="tb-coupon">
	<colgroup>
	    <col width="140px">
		<col width="140px">
		<col width="80px">
		<col width="80px">
		<col width="130px">
		<col width="130px">
		<col width="138px">
	</colgroup>
	<thead>
		<tr>
			<th>优惠券编码</th>
			<th>优惠券名称</th>
			<th>类型</th>
			<th>面值金额</th>
			<th>开始时间</th>
			<th>结束时间</th>
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
		<c:forEach items="${pb.result}" var="coupon">
			<tr>
				<td class="">${fn:escapeXml(coupon.couponid)}</td>
				<td class="name">${fn:escapeXml(coupon.couponname)}</td>
				<td class="type"><c:if test="${coupon.coupontype==1}">
				金券
				</c:if> <c:if test="${coupon.coupontype==2}">
				现金券
				</c:if>
				</td>
				<td class="money">${coupon.couponacount}￥</td>
				<td class="date"><fmt:formatDate value="${coupon.starttime }"
						pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td class="date"><fmt:formatDate value="${coupon.endtime }"
						pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td class="tac"><a
					href="${path}/coupon/toShowUI?couponId=${coupon.couponid}"
					target="_blank">查看</a> 
					<c:if test="${!empty buttonsMap['优惠券列表-查看使用规则']}">
						<a
						href="${path}/couponRule/toShowRuleList?couponId=${coupon.couponid}&coupontype=${coupon.coupontype}"
						target="_blank">查看使用规则</a>
					</c:if>

				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
</c:if>