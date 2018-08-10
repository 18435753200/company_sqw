<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<c:if test="${not empty suggestionList}">
	<ul class="auto-tip">
		<c:forEach items="${suggestionList}" var="suggestion" varStatus="statu">
			<li><span>约有${suggestion.count}条</span><a href="<%=path%>/searchController/toSearchResult?keyword=${suggestion.keyword}">${suggestion.keyword}</a></li>
		</c:forEach>
	</ul>
</c:if>