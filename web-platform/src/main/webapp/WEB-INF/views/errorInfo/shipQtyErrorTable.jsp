<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>

<div class="pu_wrap">
	<div class="pu_hd">
		<h3>物流错误信息查询列表</h3>
		<div class="btn">
		</div>
	</div>
	<div class="pu_bd">
		<table>
			<tbody>
				<tr class="order_hd">
					<th width="60px;">序号</th>
					<th width="160px;">ID</th>
					<th width="160px;">订单号</th>
					<th width="160px;">规格skuId</th>
					<th width="60px;">订单数量</th>
					<th width="60px;">包裹数量</th>
					<th width="120px;">创建时间</th>
					<th width="70px;">业务类型</th>
				</tr>
				<c:forEach items="${pb.result }" var="shipQtyError" varStatus="status">
				<tr>
					<td>${status.index + 1 }</td>
					<td>${shipQtyError.id}</td>
					<td>${shipQtyError.orderId}</td>
					<td>${shipQtyError.skuId}</td>
					<td>${shipQtyError.orderQty}</td>
					<td>${shipQtyError.shipQty}</td>
					<td><fmt:formatDate value="${shipQtyError.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>${shipQtyError.businessType}</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:if test="${!empty pb.result}">
			<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
		</c:if>
	</div>
</div>

