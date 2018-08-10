<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<script type="text/javascript" src="${path}/commons/js/shipOrderEdit.js"></script>

<div class="pu_wrap">
	<div class="pu_hd">
		<h3>物流查询列表</h3>
		<div class="btn">
		</div>
	</div>
	<div class="pu_bd">
		<table>
			<tbody>
				<tr class="order_hd">
					<th width="60px;">序号</th>
					<th width="160px;">ID</th>
					<th width="160px;">业务单号</th>
					<th width="90px;">业务单类型</th>
					<th width="140px;">通知单ID</th>
					<th width="120px;">操作人</th>
					<th width="120px;">创建时间</th>
				</tr>
				<c:forEach items="${pb.result }" var="shipOrder" varStatus="status">
				<tr>
					<td>${status.index + 1 }</td>
					<td>${shipOrder.id}</td>
					<td>${shipOrder.businessNumber}</td>
					<!-- <td>${shipOrder.businessType}</td> -->
					<c:choose>
						<c:when test="${shipOrder.businessType==101}">
							<td>采购单业务</td>
						</c:when>
						<c:when test="${shipOrder.businessType==102}">
							<td>换货单业务</td>
						</c:when>
						<c:when test="${shipOrder.businessType==103}">
							<td>退货单业务</td>
						</c:when>
						<c:otherwise>
							<td>调拨单业务</td>
						</c:otherwise>
					</c:choose>
						
					<td>${shipOrder.notificationId}</td>
					<td>${shipOrder.createBy}</td>
					<td><fmt:formatDate value="${shipOrder.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:if test="${!empty pb.result}">
			<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
		</c:if>
	</div>
</div>

