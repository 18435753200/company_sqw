<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<table>
<colgroup>
	<col width="155" />
	<col width="200" />
	<col width="140" />
	<col width="140" />
	<col width="140" />
	<col width="140" />
	<col width="140" />
	<col width="80" />
</colgroup>
	<tr>
		<th>品牌名称</th>
		<th>供应商名称</th>
		<th>品牌类型</th>
		<th>创建时间</th>
		<th>审核时间</th>
		<th>当前状态</th>
		<th>操作</th>
	</tr>
	<c:if test="${empty pb.result}">
		<tr>
			<td colspan="8">
				<center><img src="${path }/commons/images/no.png" /></center>
			</td>
		</tr>
	</c:if>
	<tr></tr>
	<c:forEach items="${pb.result }" var="pbr">
		<tr>
			<td><p class="brand-td" title="${fn:escapeXml(pbr.name) }">${fn:escapeXml(pbr.name) }</p></td>
			<td><p title="${fn:escapeXml(pbr.supplierName) }">${fn:escapeXml(pbr.supplierName) }</p></td>
			<td><p class="brand-td" title='<c:if test="${pbr.type==0 }">普通代理</c:if><c:if test="${pbr.type==1 }">独家代理</c:if><c:if test="${pbr.type==2 }">自主品牌</c:if>'><c:if test="${pbr.type==0 }">普通代理</c:if><c:if test="${pbr.type==1 }">独家代理</c:if><c:if test="${pbr.type==2 }">自主品牌</c:if></p></td>
			
			<fmt:formatDate value="${pbr.createTime}" pattern="yyyy-MM-dd" var="time"/>
			<c:if test="${fn:containsIgnoreCase(time, '1970-01-01')}" var="rs"><td><p class="brand-td" title='-'>-</p></td></c:if>
			<c:if test="${!rs}"><td><p class="brand-td" title='<fmt:formatDate value="${pbr.createTime }" pattern="yyyy-MM-dd"/>'><fmt:formatDate value="${pbr.createTime }" pattern="yyyy-MM-dd"/></p></td></c:if>
			
			
			
			<fmt:formatDate value="${pbr.auditTime}" pattern="yyyy-MM-dd" var="time"/>
			<c:if test="${fn:containsIgnoreCase(time, '1970-01-01')}" var="rsa"><td><p class="brand-td" title='-'>-</p></td></c:if>
			<c:if test="${!rsa}"><td><p class="brand-td" title='<fmt:formatDate value="${pbr.auditTime }" pattern="yyyy-MM-dd"/>'><fmt:formatDate value="${pbr.auditTime }" pattern="yyyy-MM-dd"/></p></td></c:if>
			
			<td><p class="brand-td" title='<c:if test="${pbr.status==0 }">未审核</c:if><c:if test="${pbr.status==1 }">审核通过</c:if><c:if test="${pbr.status==2 }">审核不通过</c:if>'><c:if test="${pbr.status==0 }">未审核</c:if><c:if test="${pbr.status==1 }">审核通过</c:if><c:if test="${pbr.status==2 }">审核不通过</c:if></p></td>
			<td class="t4">
				<a href="${path}/brand/getBrandDetail?brandId=${pbr.brandId}"><span class="cjian">查看</span></a>
			</td>
		</tr>
	</c:forEach>
</table>

<c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp" %>
</c:if>