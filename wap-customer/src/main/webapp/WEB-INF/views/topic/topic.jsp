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
<!doctype html>
<html >
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="black" name="apple-mobile-web-app-status-bar-style">
	<meta content="telephone=no" name="format-detection">
	<title></title>
	<link rel="stylesheet" type="text/css" href="/commons/css/newbese.css">
	<link rel="stylesheet" type="text/css" href="/commons/css/tip.css">
</head>
<body>

    <div class="header">
		<a href="javascript:history.go(-1)" class="bug-go"></a>
		
	</div>
	<%--需要的一些参数 start--%>
    <input id="path" type="hidden" value="<%=path%>">
	<div class="tipw">
	
		<div class="tip_t">
			<!--焦点图 start-->
			<div id="index-container">
				<ul class="swiper-wrapper">
					<c:forEach items="${bannerList}" var="banner" varStatus="vs">			
						<li class="swiper-slide">
							<a href="${banner.bannerSkipUrl}"><img src="${filePath}${banner.bannerUrl}"/></a>
						</li>
					</c:forEach>
				</ul>
				<div class="swiper-pagination" id="index-pagination"></div>
			</div>	
			<!--焦点图 end-->
			<h2 class="t_title"></h2>
		</div>
		<input id="totalPage" type="hidden" value="${totalPage}">
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
			<!-- 	  <a href="javascript:;"><i class="sh2"></i>${activityTopic.shareNum}</a> -->
				</p>
			</div>
		</c:forEach>
		<div class="loadw" align="center">
	   </div> 
	</div>
  <script type="text/javascript" src="<%=basePath%>commons/js/tips/zepto.min.js"></script>
  <script type="text/javascript" src="<%=basePath%>commons/js/tips/echo.min.js"></script>
  <script type="text/javascript" src="<%=basePath%>commons/js/tips/swiper.min.js"></script>
  <script type="text/javascript" src="<%=basePath%>commons/js/tips/topic.js"></script>
</body>
</html>