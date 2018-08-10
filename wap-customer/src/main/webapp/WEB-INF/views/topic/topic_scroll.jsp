<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	request.setAttribute("path", path);
	request.setAttribute("basePath", basePath);
%>
<script type="text/javascript" src="<%=basePath%>commons/js/tips/echo.min.js"></script>
<c:choose>
	<c:when test="${not empty list}">
	        <c:forEach items="${list}" var="activityTopic" varStatus="vs">			
				<div class="tip_c">
			    <a 
			        <c:if  test="${activityTopic.activityType eq 2 }">href="${activityTopic.viewUrl}"</c:if> 
			        <c:if  test="${activityTopic.activityType != 2 }">href="<%=path%>/view/topic/topicDetail?topicId=${activityTopic.topicId}"</c:if> 
			    >
					<h2>${activityTopic.title}</h2>
					<h3><img class="lazy" src="../../commons/img/loading3.gif" data-echo="${filePath}${activityTopic.titleUrl}"/>
					<c:if  test="${activityTopic.activityType eq 3 }"><i></i></c:if></h3>
					<p class="txt">${activityTopic.sketich}</p>
				</a>
				<p class="share">
				  <a href="javascript:;" id="tip_${activityTopic.topicId}"  
				      <c:if  test="${activityTopic.likeStatus eq 1 }">class="selected"</c:if> 
					  <c:if  test="${activityTopic.likeStatus !=1 }"> onclick="clicklike('${activityTopic.topicId}',${activityTopic.likeNum});" class=""</c:if>><i class="sh1"></i><span id="num_${activityTopic.topicId}">${activityTopic.likeNum}</span></a>
				<!--   <a href="javascript:;"><i class="sh2"></i>${activityTopic.shareNum}</a> -->
				</p>
			    </div>
			</c:forEach>
	</c:when>
	<c:otherwise>
	</c:otherwise>
</c:choose>	
<script>
$(function(){
	 //图片懒加载
     Echo.init({
       offset: 0,
       throttle: 0
     });
});	
</script>