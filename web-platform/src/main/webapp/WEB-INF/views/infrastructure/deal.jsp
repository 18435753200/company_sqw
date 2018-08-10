<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>成交条款</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css"href="${path}/commons/css/currenry.css" />
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
				<p class="c1">成交条款设置</p>
			</div>
	     </div>

	     <div class="btn">
<!-- 	     	<a href="#">新增</a> -->
<!-- 	     	<a href="#">保存</a> -->
<!-- 	     	<a href="#">放弃</a> -->
	     </div>

		<div class="pu_wrap">
		 	<table class="pu">
				<tbody>
					<tr class="order-hd">
						<th width="200px">编号</th>
						<th width="236px">成交条款</th>
						<th width="400px">说明</th>
					</tr>	
			        <c:forEach items="${dictionarys}" var="dictionarys">
						<tr class="order-bd">
							<td>${dictionarys.dictValue}</td>
							<td>${dictionarys.dictName}</td>
							<td>${dictionarys.dictRemark}</td>
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