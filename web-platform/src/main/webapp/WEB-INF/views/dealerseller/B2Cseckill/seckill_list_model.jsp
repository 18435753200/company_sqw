<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<table class="tb-promotion">
	<colgroup>
		<col width="120px;">
		<col width="120px;">
		<col width="50px;">
		<col width="50px;">
		<col width="90px;">
		<col width="90px;">
		<col width="90px;">
		<col width="120px;">
		<col width="120px;">
		<col width="50px;">
	</colgroup>
	<thead>
		<tr class="order_hd">
			<th width="120px;">商品主图</th>
			<th width="120px;">商品编号</th>
			<th width="50px;">创建时间</th>
			<th width="50px;">规格编号</th>
			<th width="90px;">开始时间</th>
			<th width="90px;">结束时间</th>
			<th width="90px;">活动库存</th>
			<th width="120px;">抢购价格</th>
			<th width="120px;">活动状态</th>
			<th width="50px;">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${empty pageBean.result}">
			<tr>
				<td colspan="5">
					<center>
						<img src="${path }/commons/images/no.png" />
					</center>
				</td>
			</tr>
		</c:if>
		<c:forEach items="${pageBean.result}" var="pageBean">
			<tr>
				<td class="name"><img width="50" height="50" src="${pageBean.imageUrl}" /></td>
				<td class="name">${pageBean.pid}</td>
				<td class="tac">
					<fmt:formatDate value="${pageBean.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
				<td class="name">
					${pageBean.skuid}
				</td>
				
				<td class="date">
					<fmt:formatDate value="${pageBean.startTime}" pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
				<td class="date">
					<fmt:formatDate value="${pageBean.endTime }" pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
				<td class="date">
					${pageBean.stock }
				</td>
				<td class="tac">
					${pageBean.price}
				</td>
				<td class="name">
				<c:if test="${pageBean.status == 1}">活动中</c:if>
				<c:if test="${pageBean.status == 2}">禁用</c:if>
				<c:if test="${pageBean.status == 3}">结束</c:if>				
				</td>
				<td class="tac">
					<a href="${path}/secondKill/showSecKillById?id=${pageBean.id}">修改</a>
					<a onclick="deleteSeckillById('${pageBean.id}')">删除</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

	<c:if test="${!empty pageBean.result}">
		<%@include file="/WEB-INF/views/dealerbuy/basePage/basePage.jsp" %>
	</c:if>