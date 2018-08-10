<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<% response.setHeader("cache-control","public"); %>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
	<title>UNICORN-会员手机号修改</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/reset.css">
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/kuc1.css"/>
	<script src="${path}/commons/js/my97/WdatePicker.js"></script>
	
	<script type="text/javascript">
		function toGetList(){
			$("#SearchFrom").submit();
		}
		$(function(){
			var message='${message}'; 
			if(message=='ok'){
				alert("操作成功!");
			}
			if(message=='1'){
				alert("缺少参数,操作失败!");
			}
			if(message=='2'){
				alert("上传失败,请重新选择上传文件!"); 		
			}
			if(message=='3'){
				alert("上传文件到图片服务器出错,请稍后再试!"); 	
			}
			if(message=='4'){
				alert("服务器忙,请稍后再试!"); 	
			}
			
		});
	</script>
</head>
<body>

 <%@include file="/WEB-INF/views/include/header.jsp"%>
	
	<div class="center">
		<!-- 左边 start -->
		<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		<!-- 左边 end -->
		<div class="c2" style="margin-top:10px;">
			<div class="c21">
			<div class="title">
				<p>平台管理&nbsp;>&nbsp; </p>
				<p>平台会员管理&nbsp;&gt;</p>
				<p class="c1">会员手机号修改</p>
			</div>
			</div>
			<div class="blank10"></div>

			<div class="c22">
			    <div class="c21">
						<ul class="top">
							<li id="supplier"><a href="javascript:void(0)" >用户中心</a></li>
						</ul>
				 </div>
				<div class="xia" style="height: 80px;">
					<form id="SearchFrom"  action="${path}/platform/userUpdateMobilelist" method="post" >
						<p class="p1">
						    <strong>用户ID：</strong><input type="text" id="userId" name="userId" value="${page.parameter.userId}" class="text1 dzf-txt">
							&nbsp;
						    <strong>用户名：</strong><input type="text" id="userName" name="userName" value="${page.parameter.userName}" class="text1 dzf-txt">
	                        &nbsp;&nbsp;
							<strong>手机号 ：</strong>
							<input type="text" class="text1 dzf-txt" name="mobile" value="${page.parameter.mobile}">
							&nbsp;<strong>状态：</strong>
						    <select name="freezeStatus" style="height: 30px;">
						        <option value="">所有</option> 
						    	<option value="0"  <c:if test="${page.parameter.freezeStatus==0}"> selected="selected" </c:if>>未冻结</option>
						        <option value="1" <c:if test="${page.parameter.freezeStatus==1}"> selected="selected" </c:if>>冻结</option>
						    </select>
						</p>
						</br>
						<p>
					    <button type="button" onclick="toGetList()" style="margin-left: 670px;">搜索</button>
						<a href="#" id="czhi" onclick="resetfm()">重置</a>
						</p>
					</form>
				</div>
			</div>
			<div class="blank10"></div>
			<div class="c24" id="c24">
				<table>
					<tr>
					 <th class="ar1">用户ID</th>
					 <th class="ar1">用户名</th>
					 <th class="ar1">手机号</th>
					 <th class="ar1">邀请码</th>
					 <th class="ar2">状态</th>
					 <th class="ar4">操作</th>
					</tr>
					<c:if test="${empty page.result}">
						<tr>
							<td  <c:if test="${2==param.source}">colspan="8"</c:if><c:if test="${1==param.source }">colspan="7"</c:if><c:if test="${3==param.source ||empty param.source}">colspan="6"</c:if>>
								<center><img src="${path }/commons/images/no.png" /></center>
							</td>
						</tr>
					</c:if>
						<c:forEach items="${page.result }" var="data">
							<tr>
								<td>${fn:escapeXml(data.userId)}</td>
							 	<td>
								 	${fn:escapeXml(data.userName)}
								</td>
								<td>
					  	 			${data.mobile }
					 			 </td>
								<td>
					  	 			${data.tjMobile }
					 			 </td>
								<td>
									<c:choose>
										<c:when test="${data.freezeStatus eq 0 }">未冻结</c:when>
										<c:otherwise><span style="color: red;">已冻结</span></c:otherwise>
									</c:choose>
					 			 </td>
								<td>
			 						<span class="ckan" style="cursor: pointer;color: #ff4800;" onclick="toUserInfo('${data.userId}')">修改</span>
			 						 <form id="toUserInfoId" action="${path}/platform/toUserInfoById" method="post">
								        <input type="hidden" id="toId" name="newUserId" class="text1">
										 <input type="hidden" id="page" name="page" value="${page.page}">
							  		 </form>
								</td>
							</tr>
						</c:forEach>
				</table>
				<c:if test="${!empty page.result}">
					<%@include file="/WEB-INF/views/include/page.jsp"%>
				</c:if>
			</div>
		  
		</div>
		<div class="blank20"></div>
	</div>

	 <div class="blank10"></div>
	 <!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
</body>
<script type="text/javascript">
	function toUserInfo(userId){
		$("#toId").val(userId);
		$("#toUserInfoId").submit();
	}
</script>
</html>