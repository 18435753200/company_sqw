<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<% response.setHeader("cache-control","public"); %>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
	<title>UNICORN-下级会员信息</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/reset.css">
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/kuc1.css"/>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/jiben.css">
	<script src="${path}/commons/js/my97/WdatePicker.js"></script>
	<style type="text/css">
	</style>
	
	<script type="text/javascript">
		function toGetList(){
			$("#SearchFrom").submit();
		}
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
				<p>基础设置&nbsp;>&nbsp; </p>
				<p class="c1">下级会员信息</p>
			</div>
			</div>
			<div class="blank10"></div>

			<div class="c22">
			    <div class="c21">
						<ul class="top">
							<li id="supplier"><a href="javascript:void(0)" >下级会员信息</a></li>
						</ul>
				 </div>
				<div class="xia" style="height: 80px;">
					<form id="SearchFrom"  action="${path}/platform/toQiYeJuniorMember" method="post" >
						<p class="p1">
						    <strong>用户ID：</strong><input type="text" id="uuId" name="uuId" value="" class="text1 dzf-txt">
						    <input type="hidden" value="${userId}" name="userId">
	                        &nbsp;&nbsp;
							<strong>手机号 ：</strong>
							<input type="text" class="text1 dzf-txt" name="mobile" value="">
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
<%-- 				<table>
					<tr>
					 <th class="ar1">姓名</th>
					 <th class="ar1">用户ID</th>
					 <th class="ar1">邀请码</th>
					 <th class="ar1">手机号</th>
					 <th class="ar2">类型</th>
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
							 	<td>
							 	<c:choose>
							 		<c:when test="${user.realName != null && user.realName != ''}">
							 			${fn:escapeXml(user.realName)}
							 		</c:when>
							 		<c:otherwise>
							 			${fn:escapeXml(user.userName)}
							        </c:otherwise>
							      </c:choose>
								</td>
								<td>${fn:escapeXml(user.userId)}</td>
								<td>
					  	 			${user.tjMobile }
					 			 </td>
								<td>
					  	 			${user.mobile }
					 			</td>
								<td>
									<span class="ckan" style="cursor: pointer;color: #ff4800;" onclick="toAccountDetail('${user.userId}')">查看账目</span>
					 			</td>
							</tr>
					</c:if>
				</table> --%>
				<div class="blank10"></div>
				<table>
					<tr>
					 <th class="ar1">姓名</th>
					 <th class="ar1">用户ID</th>
					 <th class="ar1">邀请码</th>
					 <th class="ar1">手机号</th>
					 <th class="ar2">类型</th>
					</tr>
					<c:if test="${empty page.result}">
						<tr>
							<td colspan="10">
								<center><img src="${path }/commons/images/no.png" /></center>
							</td>
						</tr>
					</c:if>
					<c:forEach items="${page.result}" var="data">
						<tr>
						 	<td>
						 		<c:choose>
							 		<c:when test="${data.realName != null && data.realName != ''}">
							 			${fn:escapeXml(data.realName)}
							 		</c:when>
							 		<c:otherwise>
							 			${fn:escapeXml(data.userName)}
							        </c:otherwise>
							      </c:choose>
							</td>
							<td>${fn:escapeXml(data.userId)}</td>
							<td>
				  	 			${data.tjMobile }
				 			 </td>
							<td>
				  	 			${data.mobile }
				 			</td>
							<td>
								<span class="ckan" style="cursor: pointer;color: #ff4800;" onclick="toAccountDetail('${data.userId}')">查看账目</span>
								<form id="toAccountDetailId" action="${path}/platform/toAccountDetail" method="post">
							        <input type="hidden" id="uId" name="userId" class="text1">
							        <input type="hidden" id="typeId" name="type" value="1" class="text1">
					  		    </form>
				 			</td>
						</tr>
					</c:forEach>
				</table>
				</table>
				<c:if test="${!empty page.result}">
					<%@include file="/WEB-INF/views/include/page.jsp"%>
				</c:if>
			</div>
		</div>
	</div>
	 <div class="blank10"></div>
	 <!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
</body>
<script type="text/javascript">
	function toAccountDetail(userId){
		$("#uId").val(userId);
		$("#toAccountDetailId").submit();
	}
</script>
<script type="text/javascript">
</script>
</html>