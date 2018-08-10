<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<table class="tb-promotion">
	<colgroup>
		<col width="137px">
		<col width="137px">
		<col width="137px">
		<col width="137px">
		<col width="141px">
		<col width="141px">
		<col width="120px">
	</colgroup>
	<thead>
		<tr>
			<th>规则编号</th>
			<th>规则名称</th>
			<th>券面值</th>
			<th>满足订单金额</th>
			<th>开始时间</th>
			<th>结束时间</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${empty pb.result}">
			<tr>
				<td colspan="4">
					<center>
						<img src="${path }/commons/images/no.png" />
					</center>
				</td>
			</tr>
		</c:if>
		<c:forEach items="${pb.result}" var="gift">
			<tr>
				<td class="couponruleid">${fn:escapeXml(gift.couponruleid)}</td>
				<td class="name">${fn:escapeXml(gift.couponrulename)}</td>
				<td class="money">${fn:escapeXml(gift.couponacount)}</td>
				<td class="money">${fn:escapeXml(gift.meetpiece)}</td>
				<td class="date"><fmt:formatDate
						value="${gift.starttime }" pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
				<td class="date"><fmt:formatDate
						value="${gift.endtime }" pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
				<td class="button"><input type="button" onclick="addGift('${fn:escapeXml(gift.couponruleid)}','${gift.couponrulename}','${pType }')"
					target="_blank" value="确认"></a> 
				</td>

			</tr>
		</c:forEach>
	</tbody>
</table>
<c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
</c:if>