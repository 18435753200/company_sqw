<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>UNICORN-新增促销</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<script type="text/javascript" src="${path }/commons/js/promotion_Third_fn.js"></script>
    <link rel="stylesheet" type="text/css" href="${path }/commons/css/promotionsPartThird.css"/>
</head>
<body>
<!-- 导航 start -->
<%@include file="/WEB-INF/views/include/header.jsp"%>
<div class="blank"></div>
<!-- 导航 end -->

<div class="center">
	<!-- 左边 start -->
	<div class="left f_l">
		<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp" %>
	</div>
	<!-- 左边 end -->


	<div class="c2">
		<div class="c21">
			<div class="title">
				<p>我是卖家&nbsp;&gt;&nbsp; </p>
				<p>促销管理&nbsp;&gt;&nbsp; </p>
				<p class="c1">新建</p>
			</div>
		</div>
		<div class="blank10"></div>
		<input type="hidden" id="promotionId" value="${promotions.promotionId }">
		<input type="hidden" id="startTime" value="<fmt:formatDate value="${promotions.startTime }" pattern="yyyy-MM-dd HH:mm:ss"/>">
		<input type="hidden" id="endTime" value="<fmt:formatDate value="${promotions.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/>">
		<div class="cont">
		
		</div>

		<div class="nex"><a href="cuxiao4.html">保存</a></div>
	</div>



</div>

 <div class="blank10"></div>
<!-- 底部 start -->
<%@include file="/WEB-INF/views/include/foot.jsp"%>
<!-- 底部 end -->
</body>
</html>