<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<table>
	<tr>
		<th style="width:155px;">付款商户</th>
		<th style="width:155px;">设备ID</th>
		<th style="width:140px;">付款人姓名</th>
		<th style="width:140px;">状态</th>
		<th style="width:150px;">操作</th>
	</tr>
	<c:if test="${empty pb.result}">
		<tr>
			<td colspan="5">
				<center><img src="${path }/commons/images/no.png" /></center>
			</td>
		</tr>
	</c:if>
	<tr></tr>
	<c:forEach items="${pb.result }" var="pbr">
		<tr>
			<td><p class="over" title="${fn:escapeXml(pbr.payCompany) }">${fn:escapeXml(pbr.payCompany) }</p></td>
			<td><p class="over" title="${pbr.deviceId }">${pbr.deviceId}</p></td>
			<td><p class="over" title="${fn:escapeXml(pbr.payUser) }">${fn:escapeXml(pbr.payUser) }</p></td>
			<td>
				<c:if test="${pbr.payStatus==0 }">待审核</c:if>
				<c:if test="${pbr.payStatus==1 }">审核通过</c:if>
				<c:if test="${pbr.payStatus==-1 }">审核不通过</c:if>
			</td>
			<td class="t4">
				<c:if test="${pbr.payStatus==0 }">
					<span class="cjian" onclick="valuationChangeStatus('${pbr.payId}',1,'${pbr.equipmentId}')">通过</span>
					<span class="xiug" onclick="valuationChangeStatus('${pbr.payId}',-1,'${pbr.equipmentId}')">不通过</span>
				</c:if>
				<c:if test="${pbr.payStatus!=0 }">
					<span class="cjian" onclick="valuationToView('${pbr.remark}')">查看</span>
				</c:if>
			</td>
		</tr>
	</c:forEach>
</table>

<c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp" %>
</c:if>