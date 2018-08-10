<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>


<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>开屏设置</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/currenry.css" />
    <script type="text/javascript" src="${path}/commons/js/infrastructure/startup.js"></script>
</head>
<body>
<%@include file="/WEB-INF/views/include/header.jsp"%>

		
	<div class="center">
	 <!-- 左边 start -->
		
	 <div class="left f_l">
		<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp" %>
	 </div>

	 <div class="right">
	 	<div class="c21">
			<div class="title">
				<p>卖家中心&nbsp;&gt;&nbsp; </p>
				<p>基础设置&nbsp;&gt;&nbsp; </p>
				<p class="c1">开屏设置</p>
			</div>
	     </div>

	     <div class="btn">
	     	<a href="javascript:goAddStartUpPage('');">新增</a>
<!-- 	     	<a href="#">保存</a> -->
<!-- 	     	<a href="#">放弃</a> -->
	     </div>

		<div class="pu_wrap">
		 	<table class="pu">
				<tbody>
					<tr class="order-hd">
						<th>说明</th>
						<th>图片</th>
						<th>显示时长</th>
						<th>开始时间</th>
						<th>结束时间</th>
						<th>平台</th>
						<th>状态</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${startupList}" var="startup">
						<tr class="order-bd">
							<td>${startup.title}</td>
							<td>	
								<img src="${startup.imgUrl }"  height="30px;"/>
							</td>
							<td>${startup.displayDuration}s</td>
							<td><fmt:formatDate value="${startup.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td><fmt:formatDate value="${startup.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td>
							<c:if test="${startup.platform==2}">android</c:if>
							<c:if test="${startup.platform==4}">IOS</c:if>
							</td>
							<td>
							<c:if test="${startup.status==1}">已启用</c:if>
							<c:if test="${startup.status==0}">已停用</c:if>
							</td>
							<td>
								<input type="button" value="修改" onclick="modifyStartup('${startup.id}')"/>
								<input type="button" value="删除" onclick="deleteStartup('${startup.id}')"/>
							</td>
						</tr>
					</c:forEach>
				</tbody>
		   </table>
	   </div>
	   
	</div>
</div>


 <div class="blank10"></div>
 <!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
</body>
</html>