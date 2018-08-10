<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<table class="tb-promotion">
	<colgroup>
		<col width="230px">
		<col width="230px">
		<col width="230px">
		<col width="150px">
	</colgroup>
	<thead>
		<tr>
			<!-- <th>编号</th> -->
			<th>赠品描述</th>
			<th>赠品类型</th>
			<th>赠品其它描述</th>
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
				<%-- <td class="name">${fn:escapeXml(gift.couponruleid)}</td> --%>
				<td class="name">${fn:escapeXml(gift.giftDesc)}</td>
				<td class="type">
					<c:if test="${gift.giftType == 1}">金券</c:if>
					<c:if test="${gift.giftType == 2}">现金券</c:if>
					<c:if test="${gift.giftType == 4}">礼品卡</c:if>
					<c:if test="${gift.giftType == 8}">积分</c:if>
					<c:if test="${gift.giftType == 16}">现金</c:if>
					<c:if test="${gift.giftType == 32}">折扣</c:if>
					<c:if test="${gift.giftType == 64}">包邮</c:if>
					<c:if test="${gift.giftType == 128}">sku单品</c:if>
					
				</td>
				<td>
					<c:if test="${gift.giftType == 1 || gift.giftType == 2 || gift.giftType == 4}">金券</c:if>
					<c:if test="${gift.giftType == 8}">积分值：${gift.addPoint}</c:if>
					<c:if test="${gift.giftType == 16}">直降金额：${gift.discountAmount}</c:if>
					<c:if test="${gift.giftType == 32}">折扣比：${gift.discount}%</c:if>
					<c:if test="${gift.giftType == 64}">服务【包邮】</c:if>
				</td>
				<td class="tac">
					
				</td>

			</tr>
		</c:forEach>
	</tbody>
</table>
<c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
</c:if>