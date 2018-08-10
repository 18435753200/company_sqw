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
      <c:if test="${not empty contentList }">
      	<c:forEach items="${contentList}" var="content" varStatus="statu">
      			<div class="brand">
            		 	<div class="brand-pic">
            		 		<c:choose>
            		 			<c:when test="${content.type==1}">
            		 				<a href="<%=path%>/item/get/${content.targetUrl}">
            		 			</c:when>
            		 			<c:when test="${content.type==2}">
            		 				<a href="<%=path%>/searchController/toSearchResult?keyword=${content.targetUrl}">
            		 			</c:when>
            		 			<c:otherwise>
            		 				<a href="${content.targetUrl}">
            		 			</c:otherwise>
            		 		</c:choose>
            		 		<img src="${picUrl1}${content.content}">
	            			</a>
            			</div>
            		</div>
      	</c:forEach>
      </c:if>