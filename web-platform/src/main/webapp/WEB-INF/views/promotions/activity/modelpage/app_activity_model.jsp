<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<table class="tb-promotion">
	<colgroup>
		<col width="160px">
		<col width="160px">
		<col width="160px">
		<col width="160px">
		<col width="160px">
		<col width="280px">
	</colgroup>
	<thead>
		<tr>
			<th>活动ID</th>
			<th>活动名称</th>
			<th>活动类型</th>
			<th>开始时间</th>
			<th>结束时间</th>
			<%--<th>主标题</th>
			<th>二级标题</th>
			<th>创建时间</th>--%>
			<%--<th>创建人</th>--%>
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
				<td class="name" style="width:10px">${fn:escapeXml(bean.activityId)}</td>
				<td class="name">${fn:escapeXml(bean.activityName)}</td>
				<td class="name">
				<c:if test="${bean.status == 1 }">
				     限时抢购
				</c:if>
				<c:if test="${bean.status != 1 }">
				     其他
				</c:if>
				</td>
				<td class="date"><fmt:formatDate value="${bean.startTime }"
						pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
				<td class="date"><fmt:formatDate value="${bean.endTime }"
						pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
				<%--<td class="date">${fn:escapeXml(bean.mainTitle)}</td>
				<td class="date">${fn:escapeXml(bean.title)}</td>
				<td class="date"><fmt:formatDate value="${bean.createTime }"
						pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
				
				<td class="date">${fn:escapeXml(bean.createBy)}</td>
				--%>
				<td class="tac">
				    <a href="${path}/appActivity/toAPPActivityView?activityId=${bean.activityId}">查看</a>
				    <a href="${path}/appActivity/createAPPActivity?activityId=${bean.activityId}">编辑</a>
				    <a href="javascript:;" onclick="deleteActivity(${bean.activityId})">删除</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
</c:if>