<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<% response.setHeader("cache-control","public"); %>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
	<title>UNICORN-会员账号设置</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/reset.css">
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/kuc1.css"/>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/jiben.css">
	<script src="${path}/commons/js/my97/WdatePicker.js"></script>
	<script src="${path}/commons/js/userAccountSet.js"></script>
	<style type="text/css">
		
	</style>
	
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
				<p class="c1">会员账号设置</p>
			</div>
			</div>
			<div class="blank10"></div>

			<div class="c22">
			    <div class="c21">
						<ul class="top">
							<li id="supplier"><a href="javascript:void(0)" >账号设置</a></li>
						</ul>
				 </div>
				<div class="xia" style="height: 80px;">
					<form id="SearchFrom"  action="${path}/platform/toUserAccountSet" method="post" >
						<p class="p1">
						    <strong>用户ID：</strong><input type="text" id="userId" name="userId" value="${user.userId}" class="text1 dzf-txt">
	                        &nbsp;&nbsp;
							<strong>手机号 ：</strong>
							<input type="text" class="text1 dzf-txt" name="mobile" value="${user.mobile}">
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
					 <th class="ar1">红旗券当日已调整数</th>
					 <th class="ar1">本次调整数</th>
					 <th class="ar1">当前分红额度</th>
					 <th class="ar1">本次调整分红额度</th>
					 <th class="ar2">状态</th>
<!-- 					 <th class="ar4">操作</th> -->
					</tr>
					<c:if test="${user == null}">
						<tr>
							<td colspan="10">
								<center><img src="${path }/commons/images/no.png" /></center>
							</td>
						</tr>
					</c:if>
					<c:if test="${user != null}">
							<tr>
								<td>${fn:escapeXml(user.userId)}</td>
							 	<td>
								 	${fn:escapeXml(user.userName)}
								</td>
								<td>
					  	 			${user.mobile }
					 			 </td>
								<td>
					  	 			${user.tjMobile }
					 			 </td>
								<td>
					  	 			${hqq}
					 			 </td>
								<td>
					  	 			<input type="text" id="hqqModifyId" placeholder="1-500万" onblur="checkHqq('${hqq}')" style="width: 80px;"/>
					  	 			<input value="${hqq}" id="oldHqqId" type="hidden"/>
					 			 </td>
								<td>
					  	 			${fenhong}
					 			 </td>
								<td>
<%-- 									<c:choose> --%>
<%-- 										<c:when test="${fenhong > 60000000 }"> --%>
<%-- 						 			 			${user.realName }会员已达到最额度，无法设定 --%>
<%-- 						 			 	</c:when> --%>
<%-- 						 			 	<c:otherwise> --%>
								  	 			<input type="text" id="fenHongId" placeholder="1-6000万" onblur="checkFenHong('${fenhong}')" value="" style="width: 60px;"/>
								  	 			<input value="${fenhong}" id="oldFenHongId" type="hidden"/>
<%-- 						 			 	</c:otherwise> --%>
<%-- 					 			 	</c:choose> --%>
					 			 </td>
								<td>
									<c:choose>
										<c:when test="${user.freezeStatus eq 0 }">未冻结</c:when>
										<c:otherwise><span style="color: red;">已冻结</span></c:otherwise>
									</c:choose>
					 			 </td>
							</tr>
					</c:if>
<%-- 						<c:forEach items="${page.result }" var="data"> --%>
<%-- 						</c:forEach> --%>
				</table>
<%-- 				<c:if test="${!empty page.result}"> --%>
<%-- 					<%@include file="/WEB-INF/views/include/page.jsp"%> --%>
<%-- 				</c:if> --%>
			</div>
		  <div class="blank10"></div>
			<c:if test="${user != null}">
				<div  class="right" style="min-height: 2px;">
					<div class="cont" style="padding-top: 5px;border: 1px solid #e2e9ee;">
						<ul class="ul_vertical">
							<li>
								<p class="p1">手机号：</p>
								<input type="text" placeholder="请输入手机号" onblur="checkIsMobile()" id="userMobileId"  name="userMobileVal" value="${currentUserMobile}" disabled="disabled" style="margin-left: 10px;height: 30px;"/>&nbsp;
							</li>
							 <div class="blank10"></div>
							<li>
								<p class="p1">验证码：</p>
								<input type="text" placeholder="请输入图片验证码" style="height:30px;margin-top: -28px;margin-left: 10px;" class="text-box" id="captcha" name="captcha" maxLength="7">	
								<img src="${path }/platform/getImageRegist?date="+new Date() id="captchaImage"  alt="换一张"  width="100px"/>
								<a id="changeImage" title="换一张" class="ch">换一张</a>
								<br/><br/>
							</li>
							<li>
								<p class="p1">手机验证码：</p>
								<input type="text" style="height:30px;margin-left: 10px;" placeholder="请输入短信验证码" class="text-box co" id="mobileCodeValue">
								<input type="button" id="mobileCode" value="获取验证码" onclick="getCode()" class="second" >
							</li>
							<li style="margin-left: 30px;">
							<c:if test="${userStatus eq 2 }">
								<input type="button" id="buttonId"  class="fabu_btn" onclick="modifyAccount('${user.userId}','${user.freezeStatus}')" value="调整"></input>
							</c:if>
							<c:if test="${userStatus ne 2 }">
								<p class="p1" style="margin-left: 80px;margin-top: 20px;"><label style="color: red;">该账户未激活!</label></p>
							</c:if>
							</li>
							<div class="blank15"></div>
						</ul>
					</div>
				</div>
			</c:if>
		</div>
		<div class="blank20"></div>
	</div>

	 <div class="blank10"></div>
	 <!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
</body>
<script type="text/javascript">
</script>
<script type="text/javascript">
$("#captchaImage").click(function(){
	$(this).attr("src","${path}/platform/getImageRegist?date="+new Date());
});
$("#changeImage").click(function(){
	$("#captchaImage").attr("src","${path}/platform/getImageRegist?date="+new Date());
});

</script>
</html>