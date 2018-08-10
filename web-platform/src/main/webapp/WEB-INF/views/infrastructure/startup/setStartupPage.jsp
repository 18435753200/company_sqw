<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>


<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>开屏设置</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" href="${path}/commons/css/shang.css">
	<link rel="stylesheet" href="${path}/commons/js/uploadify/uploadify.css">
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/currenry.css" />
		<script type="text/javascript" src="${path}/commons/js/uploadify/jquery.uploadify.js"></script>
	<script type="text/javascript" src="${path}/commons/js/uploadify/queue.js"></script>
	
    <script src="${path}/commons/js/my97/WdatePicker.js"></script>
	<script type="text/javascript" src="${path}/commons/js/swfUploadEventHandler.js"></script>
	<script type="text/javascript" src="${path}/commons/js/swfUploadEventHandlerStartup.js"></script>
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
<!-- 	     	<a href="#">新增</a> -->
<!-- 	     	<a href="#">保存</a> -->
<!-- 	     	<a href="#">放弃</a> -->
	     </div>

		<div class="pu_wrap">
			
		  
			<style>
			#startup_img{display:none;}
			tr.order-hd {
			    line-height: 35px;
			}
			tr.order-hd input[type="text"] {
				width:200px;
				height:25px;
				line-height:25px;
			}
			#submitBtn{
			    width: 50px;
			    height: 25px;
			    margin-left: 300px;
			}
			.jinben label{ position: absolute;left: 200px;top: 35px;color: #f00;}
			</style>
			
			<div class="jinben" style="position: relative;">
				<label>(图片要求：大小不可超过100kb的jpg或gif图片)</label>
			</div>
			<br/>
			<img id="adfaasdfa" src="${startup.imgUrl}" />
			
			<br />
			<br />
			<br />
			<br />
			<br />
			<br />
			
			<form id="startupForm" action="">
				<input type="hidden" name="id" value="${startup.id }"/>
			 	<table>
					<tbody>
						<tr class="order-hd">
							<th width="100px">说明</th>
							<th width="500px"><input type="text" name="title" value="${startup.title }"/></th>
						</tr>
						<tr class="order-hd" style="display:none">
							<th>图片</th>
							<th><input type="text" name="imgUrl" value="${startup.imgUrl }"/></th>
						</tr>
						<tr class="order-hd">
							<th>类型</th>
							<th>
								<input type="radio" name="type" value="display" checked="checked"
									<c:if test="${startup.type == 'display'}">
									  <c:out value="checked"/>
									</c:if>
								/> 固定显示图片  
								<input type="radio" name="type" value="activity"  <c:if test="${startup.type == 'activity'}">
									  <c:out value="checked"/>
									</c:if>/> 活动可跳转
							
							</th>
						</tr>
						<tr class="order-hd" id="jumpLinkTr">
							<th>活动路径</th>
							<th><input type="text" name="jumpLink" value="${startup.jumpLink }"/></th>
						</tr>
						<tr class="order-hd">
							<th>展示时长(秒)</th>
							<th><input type="text" name="displayDuration" value="${startup.displayDuration }"/></th>
						</tr>
						<tr class="order-hd">
							<th>开始时间</th>
							<th><input type="text" class="text1 rl" id="startTime" name="startTime" onclick="WdatePicker()" value="${startup.startTime}"/></th>
						</tr>
						<tr class="order-hd">
							<th>结束时间</th>
							<th><input type="text" class="text1 rl" id="endTime" name="endTime" onclick="WdatePicker()" value="${startup.endTime}"/></th>
						</tr>
						<tr class="order-hd">
							<th>使用平台</th>
							<th>
								<input type="radio" name="platform" value="2" checked="checked" <c:if test="${startup.platform == '2'}">
									  <c:out value="checked"/>
									</c:if>/> android 
								<input type="radio" name="platform" value="4" <c:if test="${startup.platform == '4'}">
									  <c:out value="checked"/>
									</c:if>/> ios
							</th>
						</tr>
					</tbody>
			   </table>
			   
			</form>
			
			<button id="submitBtn" onclick="javascript:add();">提交</button>
	   </div>
	   
	</div>
</div>


 <div class="blank10"></div>
 <!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
</body>
</html>
<script type="text/javascript">
	initSetPage();
</script>