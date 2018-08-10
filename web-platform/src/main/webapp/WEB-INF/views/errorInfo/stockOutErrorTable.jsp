<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>

<div class="pu_wrap">
	<div class="pu_hd">
		<h3>库房错误信息查询列表</h3>
		<div class="btn">
		</div>
	</div>
	<div class="pu_bd">
		<table>
			<tbody>
				<tr class="order_hd">
					<th width="60px;">序号</th>
					<th width="160px;">ID</th>
					<th width="160px;">通知单ID</th>
					<th width="350px;">错误消息</th>
				</tr>
				<c:forEach items="${pb.result }" var="stockOutError" varStatus="status">
				<tr>
					<td>${status.index + 1 }</td>
					<td>${stockOutError.id}</td>
					<td>${stockOutError.notificationId}</td>
					<td>${stockOutError.errorMessage}</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:if test="${!empty pb.result}">
			<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
		</c:if>
	</div>
</div>

