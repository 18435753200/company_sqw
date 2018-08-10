<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>


 <!DOCTYPE html>
 <html lang="en">
 <head>
 	<meta charset="UTF-8">
 	<title>服务商档案</title>
 	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/currenry.css" />
 	<script type="text/javascript" src="${path }/commons/js/infrastructure/infrastructure.js"></script>
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
					<p class="c1">服务商档案</p>
				</div>
		     </div>

		     <div class="btn">
		     	 <span class="add-ck-btn"><a href="../infrastructure/goAddServiceArchives" id="addoreditgoAddServiceArchives">添加服务商档案</a></span>

		     </div>
			
			<div class="pu_wrap">
			 	<table class="pu">
					<tbody>
						<tr class="order-hd">
							<th width="200px">服务商编号</th>
							<th width="236px">服务商名称</th>
							<th width="200px">联系人</th>
							<th width="200px">联系电话</th>
							<th width="200px">说明</th>
							<th width="200px">操作</th> 
						</tr>	
						<c:forEach items="${serviceArchives}" var="serviceArchives">
								<tr class="order-bd">
								<input type="hidden" name="sid" value="${serviceArchives.sid}">
									<td>${serviceArchives.serviceCode}</td>
									<td>${serviceArchives.serviceName}</td>
									<td>${serviceArchives.serviceContact}</td>
									<td>${serviceArchives.servicePhone}</td>
									<td>${serviceArchives.serviceComment}</td>
							<td><a href="${path}/infrastructure/editServiceArchives?sid=${serviceArchives.sid}" class="up">修改</a>
							</td>
								</tr>
							</c:forEach>
			       </tbody>
			   </table>
		   </div>
		   
		</div>
	</div>
<%@include file="/WEB-INF/views/include/foot.jsp" %>
 </body>
 </html>