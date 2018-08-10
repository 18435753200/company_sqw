<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<table class="tb-promotion">
	<colgroup>
		<col width="150px">
		<col width="160px">
		<col width="160px">
		<col width="160px">
		<col width="280px">
	</colgroup>
	<thead>
		<tr>
			<th>活动名称</th>
			<th>主办方</th>
			<th>部门 </th>
			<th>创建时间</th>
			<th>操作</th>
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
				<td class="name">${fn:escapeXml(bean.activeName)}</td>
				<td class="date">
					<c:choose>
						<c:when test="${bean.organizersType == 1}">平台</c:when>
						<c:when test="${bean.organizersType == 2}">零售商</c:when>
						<c:when test="${bean.organizersType == 3}">经销商</c:when>
						<c:when test="${bean.organizersType == 4}">供应商</c:when>
						<c:otherwise>
							${bean.organizersType}
						</c:otherwise>
					</c:choose>
				</td>
				<td class="date">
					<c:choose>
						<c:when test="${bean.deptId }"></c:when>
						<c:otherwise>其他</c:otherwise>
					</c:choose>
				</td>
				<td class="date"><fmt:formatDate value="${bean.createTime }"
						pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
				<td class="tac">
					<%-- <a href="${path}/activeproduct/activityproductview?activeId=${bean.activeId}">查看</a> --%>
					<c:if test="${!empty buttonsMap['商品活动列表-管理规则']}">
					<a href="${path}/productrule/list?activeId=${bean.activeId}">管理规则</a>
					</c:if>
				</td>

			</tr>
		</c:forEach>
	</tbody>
</table>
<c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
</c:if>