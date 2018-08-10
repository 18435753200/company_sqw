<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
<title>Welcome_众聚商城</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<style>
html{overflow-y:scroll;}
#welcome{clear:both;width:1000px;padding-top:60px;margin:0 auto;overflow:hidden;}
.col{float:left;width:328px;height:auto;}
.mlr8{margin-left:8px;margin-right:8px;}
.col-list{text-align:justify;}
.col-list li{display:inline-block;margin-bottom:8px;}
.col-list img{vertical-align:top;}
</style>
<%
//防止会话攻击
request.getSession().invalidate();//清空session
Cookie cookie = request.getCookies()[0];//获取cookie
cookie.setMaxAge(0);//让cookie过期
%>
</head>

<body>
<%@include file="/WEB-INF/views/include/header.jsp"%>
<div id="welcome">
	<div class="col">
		<ul class="col-list">
			<li><a href="#"><img src="${path }/commons/images/welocme_1_01.jpg" width="328" height="144" /></a></li>
			<li><a href="#"><img src="${path }/commons/images/welocme_1_02.jpg" width="160" height="144" /></a></li>
			<li><a href="#"><img src="${path }/commons/images/welocme_1_03.jpg" width="160" height="144" /></a></li>
			<li><a href="#"><img src="${path }/commons/images/welocme_1_04.jpg" width="328" height="144" /></a></li>
		</ul>
	</div>
	<div class="col mlr8">
		<ul class="col-list">
			<li><a href="#"><img src="${path }/commons/images/welocme_2_01.jpg" width="160" height="296" /></a></li>
			<li><a href="#"><img src="${path }/commons/images/welocme_2_02.jpg" width="160" height="296" /></a></li>
			<li><a href="#"><img src="${path }/commons/images/welocme_2_03.jpg" width="328" height="144" /></a></li>
		</ul>
	</div>
	<div class="col">
		<ul class="col-list">
			<li><a href="#"><img src="${path }/commons/images/welocme_3_01.jpg" width="160" height="144" /></a></li>
			<li><a href="#"><img src="${path }/commons/images/welocme_3_02.jpg" width="160" height="144" /></a></li>
			<li><a href="#"><img src="${path }/commons/images/welocme_3_03.jpg" width="328" height="144" /></a></li>
			<li><a href="#"><img src="${path }/commons/images/welocme_3_04.jpg" width="328" height="144" /></a></li>
		</ul>
	</div>
</div>
</body>
</html>