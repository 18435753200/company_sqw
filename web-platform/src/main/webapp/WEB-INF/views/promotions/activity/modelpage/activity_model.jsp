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
			<th>开始时间</th>
			<th>结束时间</th>
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
				<td class="date"><fmt:formatDate value="${bean.expiringFrom }"
						pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
				<td class="date"><fmt:formatDate value="${bean.expiringTo }"
						pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
				<td class="date"><fmt:formatDate value="${bean.createTime }"
						pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
				<td class="tac">
				<c:if test="${!empty buttonsMap['活动列表-查看']}">
				<a
					href="${path}/activity/toActivityView?activeId=${bean.activeId}">查看</a>
				</c:if>
					<c:if test="${bean.status==4 }">
						<%-- <a href="${path}/activity/deleteActivity?activeId=${bean.activeId}">删除</a> --%>
					</c:if> <c:if
						test="${bean.status!=4 }">
						<%-- <a
							href="${path}/activity/toActivityEdit?activeId=${bean.activeId}">编辑</a> --%>
						<c:if test="${!empty buttonsMap['活动列表-管理规则']}">
						<a href="${path}/platformRule/toRuleList?activeId=${bean.activeId}">管理规则</a>
						</c:if>
						<c:if test="${!empty buttonsMap['活动列表-停用启用']}">
						<a id="stopOrEnableActivity"
							onclick="stopOrEnableActivity('${bean.activeId}','${bean.status }')"
							href="javascript:void(0)"> <c:if test="${bean.status==0}">
					启用
					</c:if> <c:if test="${bean.status==1}">
					停用
					</c:if> </a>
					</c:if>
						<c:if test="${bean.status==0}">
							<%-- <a
								href="${path}/activity/deleteActivity?activeId=${bean.activeId}">删除</a> --%>
						</c:if>
					</c:if>
				</td>

			</tr>
		</c:forEach>
	</tbody>
</table>
<c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
</c:if>