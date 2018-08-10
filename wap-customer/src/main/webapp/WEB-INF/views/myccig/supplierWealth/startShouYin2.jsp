<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<link rel="stylesheet" href="${staticFile_s}/commons/css/mui.min.css">
<link rel="stylesheet" href="${staticFile_s}/commons/css/add_syy.css">
  <script type="text/javascript" src="${staticFile_s}/commons/js/main.js"></script>
  <script type="text/javascript" src="${staticFile_s}/commons/js/jquery-1.8.0.min.js"></script>  
  <script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert2.js"></script> 

<title>收银员</title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp"%>
<%@include file="/WEB-INF/views/commons/commonsIco.jsp"%>
<%@include file="/WEB-INF/views/commons/base.jsp" %>
<style type="text/css">
.mui-bar {
    position: fixed;
    z-index: 10;
    right: 0;
    left: 0;
    height: 44px;
    padding-right: 10px;
    padding-left: 10px;
    border-bottom: 0;
    background-color: #e60012;
    -webkit-box-shadow: 0 0 1px rgba(0,0,0,.85);
    box-shadow: 0 0 1px rgba(0,0,0,.85);
    -webkit-backface-visibility: hidden;
    backface-visibility: hidden;
}.mui-title {
    color: #fff;
}
a {
    text-decoration: none;
    color: #fff;
}
a:active {
    color: #ffffff;
}
.mui-card {
    margin: 10px 0;
    top: 3rem;
        
}
	.mui-card-header {
    font-size: 17px;
    border-radius: 2px 2px 0 0;
    display: block;
    text-align: center;
}
.mui-btn-block {
    font-size: 18px;
    display: block;
    width: 90%;
    margin: 0 auto;
    margin-bottom: 10px;
    padding: 15px 0;
    line-height: 0.5rem;
    height: 3rem;
    border-radius: 50px;
    background: #e60012;
}
.mui-col-xs-6 {
    width: 24%;
}
.mui-table-view.mui-grid-view .mui-table-view-cell .mui-media-body {
    font-size: 15px;
    line-height: 15px;
    display: block;
    width: 100%;
    height: 15px;
    margin-top: 8px;
    text-overflow: ellipsis;
    color: #ababab;
}
.mui-table-view.mui-grid-view .mui-table-view-cell {
    font-size: 17px;
    display: inline-block;
    margin-right: -4px;
    padding: 10px 0 0 2px;
    text-align: center;
    vertical-align: middle;
    background: 0 0;
}
.mui-card-content-inner {
    position: relative;
    padding: 15px 0;
}
.no_syy{
text-align: center;
    line-height: 5rem;
    font-size: 1.5rem;
    border-bottom: 1px solid #e4e4e6;}
		</style>

</head>
	<body>
	<input type="hidden" id="path" value="<%=path %>"/>
		<header id="header" class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">收银</h1>
		</header>
		
		<div class="mui-card">
				<div class="mui-card-header">${supplierName }
					<c:if test="${empty cShoukuanWRcode.wName }">
						<h3 class="no_syy">主窗口</h3>
					</c:if>
					<c:if test="${!empty cShoukuanWRcode.wName }">
						${cShoukuanWRcode.wName }
					</c:if>
				</div>
				<div class="mui-card-content">
				<c:if test="${empty userShouyin}">
					<h3 class="no_syy">当前窗口无在线收银员</h3>
				</c:if>
				<c:if test="${!empty userShouyin}">
					<div class="mui-card-content-inner">
						<ul class="mui-table-view mui-grid-view">
		        <li class="mui-table-view-cell mui-media mui-col-xs-6">
		                <div class="mui-media-body">收银员</div></li>
		        <li class="mui-table-view-cell mui-media mui-col-xs-6">
		                <div class="mui-media-body" style="color: #000;">${userShouyin.kfName }</div></li>
		        <li class="mui-table-view-cell mui-media mui-col-xs-6">
		                <div class="mui-media-body">手机号</div></a></li>
		        <li class="mui-table-view-cell mui-media mui-col-xs-6" style="width: 30%;">
		                <div class="mui-media-body" style="color: #000;">${userShouyin.mobile }</div></li>
		    </ul>    
					</div>
				</c:if>
					<input type="hidden" value="${supplierId }" id="supplierId">
					<input type="hidden" value="${rcodeid }" id="rcodeid">
						<c:if test="${!empty userShouyin && wxopenId eq userShouyin.wxgzhOpenId }">
							<a onclick="nostart()"><button type="button" class="mui-btn mui-btn-danger mui-btn-block">退出收银</button></a>
						</c:if>
						<c:if test="${empty userShouyin||!wxopenId eq userShouyin.wxgzhOpenId }">
							<a onclick="start()"><button type="button" class="mui-btn mui-btn-danger mui-btn-block">开始收银</button></a>
						</c:if>
				</div>
		</div>
	
</body>

<script type="text/javascript">

	function start(){
		var supplierId = $("#supplierId").val();
		var rcodeid = $("#rcodeid").val();
			$.ajax({
				type: 'POST',
				url: $("#path").val()+'/wealth/startOnline',
				dataType: 'json',
				async:false,
				data:"supplierId="+ supplierId +"&rcodeId="+rcodeid,
				success: function(data) {
					if(data==1){
						showMsg("开始收银成功");
						
					}else if(data==2){
						showMsg("您已经在其他店铺收银");
					}
					},
				error: function() {
				}
			});
	}
	function nostart(){
		var supplierId = $("#supplierId").val();
		$.ajax({
			type: 'POST',
			url: $("#path").val()+'/wealth/startNoOnline',
			dataType: 'json',
			async:false,
			data:"supplierId="+ supplierId,
			success: function(data) {
				if(data==1){
					showMsg("退出收银成功");
				}else {
					showMsg("服务器异常");
					
				}
				},
			error: function() {
			}
		});
	}
	function showMsg(content){
		$.dialog({
	        content : content,
	        title : '众聚猫提示',
	        time: 2000,
		});
	}
</script>
</html>
