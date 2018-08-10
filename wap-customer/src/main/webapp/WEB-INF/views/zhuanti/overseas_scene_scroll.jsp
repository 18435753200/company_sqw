<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%
	String path = request.getContextPath();
	request.setAttribute("path", path);
	request.setAttribute("url", request.getServletPath());
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<c:choose>
	<c:when test="${not empty searchResponse.items}">
		<c:forEach items="${searchResponse.items}" var="item" varStatus="statu">
			<dl class="dl_temp">
				<dt><a href="<%=path%>/item/get/${item.pid}"><img src="${picUrl}${item.imageurl}" /></a></dt>
				<dd>
				<h3><a href="<%=path%>/item/get/${item.pid}">${item.b2cPname}</a></h3>
				<p class="domeprice">
					<c:if test="${item.domestic_price != null}">
						<span >市场价： <i><fmt:formatNumber value="${fn:escapeXml(item.domestic_price)}" pattern="#0.00" /></i></span>
					</c:if>
				</p>
				<p><span class="unprice"> <i><fmt:formatNumber value="${fn:escapeXml(item.unit_price)}" pattern="#0.00" /></i></span></p>
				</dd>
			</dl>
							
		</c:forEach>
	</c:when>
	<c:otherwise>
		<div>--------------无更多信息--------------<div>
	</c:otherwise>
</c:choose>