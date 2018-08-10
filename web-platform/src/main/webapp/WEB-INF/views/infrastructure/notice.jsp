<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
<title>平台通知</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link rel="stylesheet" type="text/css" href="${path}/commons/css/jiben.css">
<script type="text/javascript" src="${path}/commons/js/notice.js"></script>
</head>
<body onload="PageInit()">
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	 <div class="wrap">
		<!-- 左边 start -->
		<div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		</div>
		<!-- 左边 end -->

		<!-- 右边 start -->
		<div class="right">
			<div class="c21">
			<div class="title">
				<p>卖家中心&nbsp;&gt;&nbsp; </p>
				<p>基础设置&nbsp;&gt;&nbsp; </p>
				<p class="c1">平台通知</p>
			</div>
	     </div>
			<div class="blank5"></div>
			<div class="cont">
			<input type="hidden" name="id" id="noticeId" value="${notice.id}"/>
				<ul class="ul_vertical">
					<li>
						<p class="p1">通知内容：</p>
						<textarea id="txtF_Content" rows="10" cols="50" name="content" style="outline:none;resize:none;" 
						onkeydown="countChar('txtF_Content','counter',1000);">${notice.content}</textarea>
					</li>
					<li class="blank20" style="visibility:visible;">
						<p class="p1"></p>
						<p>最多输入1000字，目前已经输入<span SPAN id="counter" style="COLOR:red;FONT-WEIGHT:bold">0</span>字</p>
					</li>
					<li><p class="p1"></p></li>
					<li>
					<c:if test="${!empty notice}">
						<input type="button"  class="fabu_btn" onclick="operateNotice(1)" value="修改并发布" ></input>
						<input type="button"  class="fabu_btn" onclick="operateNotice(2)" value="删除" ></input>
					</c:if>
					<c:if test="${empty notice}">
						<input type="button"  class="fabu_btn" onclick="operateNotice(3)" value="发布" ></input>
					</c:if>
					</li>
				</ul>
				
			</div>
			
		
		</div>			 
		<!-- 右边 end -->
	</div>


		
<div class="blank10"></div>
	 <!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
   </body>
</html>