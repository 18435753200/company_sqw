<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<%-- <script src="${path}/commons/js/coupon_record_list_fn.js"></script> --%>
<table class="tb-coupon">
	<colgroup>
		<col width="140px">
	    <col width="140px">
		<col width="140px">
		<col width="60px">
		<col width="60px">
		<col width="80px">
		<col width="130px">
		<col width="130px">
		<col width="138px">
		<col width="138px">
		<col width="138px">
		<col width="138px">
		<col width="138px">
	</colgroup>
	<thead>
		<tr>
			<th>优惠券券码</th>
			<th>券规则名称</th>
			<th>零售商ID</th><!-- 金券 鑫券 -->
			<th>零售商名称</th>
			<th>面值金额</th>
			<th>满足金额</th>
			<th>开始时间</th>
			<th>结束时间</th>
			<th>发放时间</th>
			<th>状态<label>(共${totalCoupons }张)</label></th><!-- 已使用 未使用，已过期 -->
			<th>来源</th>
			<th>使用情况</th>
			<th>用户类型</th>
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
				<td class="J-id">${coupon.id}</td>
				<td class="name">${coupon.couponRuleName}</td>
				<td class="type">${coupon.userid }</td>
				<td class="type">${coupon.retailerName }</td>
				<td class="money">${coupon.couponacount}￥</td>
				<td class="money">${coupon.meetpiece}￥</td>
				<td class="date"><fmt:formatDate value="${coupon.starttime }"
						pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td class="date"><fmt:formatDate value="${coupon.endtime }"
						pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td class="date"><fmt:formatDate value="${coupon.lytime }"
						pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td class="">
					<c:choose>
						<c:when test="${coupon.status==0 }">未使用</c:when>
						<c:when test="${coupon.status==1 }">已使用</c:when>
					</c:choose>
				</td><!-- 0. 新建  1.已领用 2.未领用 3.已使用 4.已到期 5.已暂停 6.已作废 -->
				<td class="name">${coupon.orgindesc}</td>
				<td>
				<c:if test="${coupon.status==1 && coupon.type == 1 }">
					<input type="button" id="${coupon.id }" value="金券使用记录" class="J-bn"><input type="hidden" value="${coupon.type }">
					<input type="hidden" value="${coupon.userType1 }">
				</c:if>
				<c:if test="${coupon.type == 2 }">
					<input type="button" id="${coupon.id }" value="现金券使用记录" class="J-bn"><input type="hidden" value="${coupon.type }">
					<input type="hidden" value="${coupon.userType1 }">
				</c:if>
				<c:if test="${coupon.status==0 && coupon.type == 1 }">
					—— ——
				</c:if>
				</td>
				<td>
					<c:if test="${coupon.userType1 == 1 }">
						<label>B2B</label>
					</c:if>
					<c:if test="${coupon.userType1 == 2 }">
						<label>B2C</label>
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
</c:if>