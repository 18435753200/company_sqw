<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html style="font-size: 37.5px;">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<%@include file="/WEB-INF/views/commons/base.jsp"%>
<%-- <link href="${staticFile_s}/commons/css/w_base.css" rel="stylesheet" type="text/css"> --%>
<link href="${staticFile_s}/commons/css/zepto.alert.css"
	rel="stylesheet" type="text/css">
<script type="text/javascript"
	src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<title>家庭宝</title>
<style>
@charset "utf-8";
/*全局*/
body,div,p,ul,ol,li,dl,dt,dd,form,input,table,img,h1,h2,h3,h4,h5,h6,header,section,footer,nav,figure,figcaption,article
	{
	margin: 0;
	padding: 0
}

header,section,footer,nav,figure,figcaption,article,menu {
	display: block
}

body {
	font-family: "Microsoft YaHei", "微软雅黑", Arial, Georgia, Verdana, serif;
}

ul,ol {
	list-style: none;
}

a,a:hover {
	text-decoration: none;
	color: #393737;
}

img {
	border: none;
	display: inline-block;
}

h1,h2,h3,h4,h5,h6 {
	font-weight: normal;
}

em,i {
	font-style: normal;
}

.l,.left {
	float: left;
	display: inline-block;
}

.r,.right {
	float: right;
	display: inline-block;
}

.clear:after {
	content: '';
	display: block;
	width: 0;
	height: 0;
	overflow: hidden;
	clear: both;
}

header,nav,section,footer {
	margin: auto;
}

* {
	tap-highlight-color: rgba(0, 0, 0, 0);
	-webkit-tap-highlight-color: rgba(0, 0, 0, 0);
	-ms-tap-highlight-color: rgba(0, 0, 0, 0);
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	-ms-box-sizing: border-box;
	box-sizing: border-box;
}

/************* 公用样式 ****************/
html,body {
	-webkit-user-select: none;
	-o-user-select: none;
	-ms-user-select: none;
	user-select: none;
	display: -webkit-box;
	display: -moz-box;
	-webkit-box-flex: 1;
	-o-box-flex: 1;
	-ms-box-flex: 1;
	box-flex: 1;
	-webkit-box-orient: vertical;
	-o-box-orient: vertical;
	-ms-box-orient: vertical;
	box-orient: vertical;
	height: 100%;
	width: 100%;
	max-width: 640px;
	min-width: 320px;
	margin: 0 auto;
	/*overflow: hidden;*/
}

.opercdbox {
	width: 100%;
}

.opercdbox .header {
	width: 100%;
	max-width: 640px;
	height: 1.3rem;
	line-height: 1.3rem;
	text-align: center;
	font-size: 0.45rem;
	font-weight: bold;
	border-bottom: 1px solid #DDDDDD;
	position: fixed;
	z-index: 99;
	background: #fff;
}

.opercdbox .gobak {
	width: 12%;
	height: 1.3rem;
	line-height: 1.3rem;
	position: absolute;
	top: 0;
	left: 0;
	background: url("<%=path%>/commons/img/gobak.png") no-repeat center
		center;
	background-size: 25%;
}

.opercdbox .opercdbox-wrap {
	width: 100%;
	margin-top: 1.3rem;
	z-index: 9;
}

.opercdbox .fl-cont-box {
	width: 100%;
	padding: 0.5rem 2%;
}

.opercdbox .fl-cont-box>div {
	display: none;
	width: 100%;
}

.opercdbox .fl-cont-box>div.active {
	display: block;
}

.opercdbox .fl-cont-box>div table {
	width: 100%;
}

.opercdbox .fl-cont-box>div table tr {
	width: 100%;
}

.opercdbox .fl-cont-box>div table tr:first-child td {
	height: 1rem;
	line-height: 1rem;
	padding: 0;
	background: #DBDDDC;
	text-align: center;
}

.opercdbox .fl-cont-box>div table tr:first-child td:first-child {
	border-right-color: #fff;
	font-weight: bold;
}

.opercdbox .fl-cont-box>div table tr:first-child td:last-child {
	border-left-color: #fff;
	font-weight: bold;
}

.opercdbox .fl-cont-box>div table tr td {
	padding: 0.35rem;
	font-size: 0.35rem;
	border: 1px solid #EAEAEA;
	text-align: left;
}

.opercdbox .fl-cont-box>div table tr td:first-child {
	width: 40%;
	text-align: center;
}

.opercdbox .fl-cont-box>div table tr td:last-child {
	width: 60%;
}

.jiaTing {
	text-align: center;
	display: block;
	color: red;
}
</style>
</head>
<script type="text/javascript">
	function notice() {
		var jiaTingShangJiaStates = document
				.getElementById("jiaTingShangJiaStates").value;

		if (jiaTingShangJiaStates == 2) {
			$.dialog({
				content : "家庭号被冻结",
				title : '众聚猫提示',
				time : 3000,
			});
		}  else {
			window.location.href = "http://pop.zhongjumall.com/user/loginUI";
			

		}

	}
</script>
<body style="font-size: 12px;">
	<%-- hidden域 --%>
	<input type="hidden" id="path" value="<%=path%>" />
	<input type="hidden" id="basePath" value="<%=basePath%>" />
	<input type="hidden" id="userId" value="${userId }" />
	<input type="hidden" id="jiaTingShangJiaStates"
		value="${jiaTingShangJiaStates }" />
	<div class="opercdbox">
		<div class="header">
			<a class="gobak" href="javascript:goBack();"></a> 家庭宝商品
		</div>
		<div class="opercdbox-wrap">
			<div class="fl-cont-box">
				<div class="active">
					<table>
						<tr>
							<td>类目</td>
							<td>商品明细</td>
						</tr>
						<tr>
							<td>订单号</td>
							<td>${homeNumRecord.orderNo}</td>
						</tr>
						
						
						<tr>
							<td>开始时间</td>
							<td><fmt:formatDate value="${homeNumRecord.startDate}"
									pattern="yyyy.MM.dd " /></td>
						</tr>


						<tr>
							<td>结束时间</td>
							<td><fmt:formatDate value="${homeNumRecord.stopDate}"
									pattern="yyyy.MM.dd " /></td>
						</tr>
						<tr>
							<td>购买金额</td>
							<td>${homeNumRecord.amountOfMoney}</td>
						</tr>
					</table>
					<c:if test="${jiaTingShangJiaStates!=0 }">
					<c:if test="${homeNumStates==2 }">
						<a href="javascript:;" onclick="notice();" class="jiaTing">登陆家庭号</a>
					
					</c:if>
					<c:if test="${homeNumStates==1 }">
						<a href="http://m.zhongjumall.com/searchController/toSearchResult?cdid=007012" class="jiaTing">家庭号过期,请购买商品激活</a>
					
					</c:if>
					</c:if>
					<c:if test="${jiaTingShangJiaStates==0 }">
						<a
							href="http://pop.zhongjumall.com/supplier/registUI2?userid=${userId }"
							class="jiaTing">注册家庭号</a>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/views/commons/footer.jsp"%>
	<script src="${staticFile_s}/commons/js/zepto.min.js"
		type="text/javascript"></script>
	<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
</body>
<script>
	$(function() {
		$('.btn-sure').on('click', function() {
		});
	});
</script>
</html>