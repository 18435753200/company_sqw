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
			<th width="120px;">活动ID</th>
			<th width="120px;">活动名称</th>
			<th width="50px;">活动兼容性</th>
			<th width="50px;">活动类型</th>
			<th width="90px;">开始时间</th>
			<th width="90px;">结束时间</th>
			<th width="90px;">创建时间</th>
			<th width="120px;">创建部门</th>
			<th width="120px;">创建人员</th>
			<th width="50px;">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${empty pb.result}">
			<tr>
				<td colspan="5">
					<center>
						<img src="${path }/commons/images/no.png" />
					</center>
				</td>
			</tr>
		</c:if>
		<c:forEach items="${pb.result}" var="bean">
			<tr>
				<td class="name">${bean.id}</td>
				<td class="name">${bean.ruleName}</td>
				<td class="name">
					<c:if test="${fn:escapeXml(bean.dispAttrVal == 1)}">叠加</c:if>
					<c:if test="${fn:escapeXml(bean.dispAttrVal == 2)}">排它</c:if>
					<c:if test="${fn:escapeXml(bean.dispAttrVal == 3)}">排己</c:if>
				</td>
				<td class="name">
					<c:if test="${fn:escapeXml(bean.ruleTerm == 20)||fn:escapeXml(bean.ruleTerm == 21)}">满减</c:if>
					<c:if test="${fn:escapeXml(bean.ruleTerm == 22)||fn:escapeXml(bean.ruleTerm == 23)||fn:escapeXml(bean.ruleTerm == 30)}">满返</c:if>
					<c:if test="${fn:escapeXml(bean.ruleTerm == 24)||fn:escapeXml(bean.ruleTerm == 25)||fn:escapeXml(bean.ruleTerm == 29)}">满赠</c:if>
					<c:if test="${fn:escapeXml(bean.ruleTerm == 27)||fn:escapeXml(bean.ruleTerm == 28)}">限时直降</c:if>
					<c:if test="${fn:escapeXml(bean.ruleTerm == 26)}">买赠</c:if>
				</td>
				<td class="date">
					<fmt:formatDate value="${bean.startdate}"
						pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
				<td class="date">
					<fmt:formatDate value="${bean.enddate }"
						pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
				<td class="date">
					<fmt:formatDate value="${bean.createTime }"
						pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
				<td class="tac">
					${bean.createBy}
				</td>
				<td class="tac">
					${bean.categoryName}
				</td>
				<td class="tac">
					<a onclick="viewRule('${bean.id}')" style="cursor:pointer;">查看</a>
					<c:if test="${bean.status == 0}"><a onclick="stopOrEnableRule('${bean.id}','${bean.status }')" style="cursor:pointer;">启用</a></c:if>
					<c:if test="${bean.status == 1}"><a onclick="stopOrEnableRule('${bean.id}','${bean.status }')" style="cursor:pointer;">停用</a></c:if>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
</c:if>