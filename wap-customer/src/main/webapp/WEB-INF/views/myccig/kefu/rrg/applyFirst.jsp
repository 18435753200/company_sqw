<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<title>申请退换货-众聚猫</title>
<%@include file="/WEB-INF/views/commons/base.jsp"%>
<link href="${staticFile_s}/commons/css/base.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/return.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/webuploaderframe.css">
<link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/js/ueditor/third-party/webuploader/webuploader.css">
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>

</head>

<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>	
	<header id="header" class="head">
		<a href="<%=path%>/kf/rrg/list/1" class="goback"> <span
			class="icon i-goback"></span></a>
		<h2 class="h-title">商品退换方式</h2>
	</header>
	<div class="return">
		<dl class="apply-cent">
			<c:forEach var="orderItem" items="${orderDto.orderItemDTOs}">
				<c:if test="${orderItem.skuId == skuId}">
					<c:set value="${orderItem }" var="order"></c:set>
				</c:if>
			</c:forEach>
			<dt>
				<img src="${picUrl2 }${order.imgUrl }">
			</dt>
			<dd>
				<h2>${order.pName}</h2>
				<span>数量：${order.skuQty }</span>
			</dd>
		</dl>
		<form action="<%=path%>/kf/rrg/${orderDto.id}-${skuId}-2" onsubmit="return false" method="post">
			<dl class="apply-type">
				<h2>服务类型</h2>
				<ul class="list-radio">
					<li><input type="radio" name="applyType" value="1"
						class="radio" checked="checked"> <label name="applyTypeLabel" value="1">退货</label>
					</li>
					<li><input type="radio" name="applyType" value="2"
						class="radio"> <label name="applyTypeLabel" value="2">换货</label>
					</li>
				</ul>
			</dl>
			<dl class="apply-type">
				<h2>申请数量</h2>
				<div class="quantity-form">
					<a href="javascript:void(0);" class="decrement" id="J-amount-down"></a>
					<input autocomplete="off" type="text" class="itxt" value="${order.skuQty - order.rrgQty}" name="applyRrgQty"
						id="J-amount"> <input type="hidden" id="applyNum_hidden"
						value="${order.skuQty - order.rrgQty}"> <a href="javascript:void(0);"
						class="increment" id="J-amount-up"></a>
				</div>
			</dl>
			<dl class="apply-type">
				<h2>问题描述</h2>
				<div class="texta">
					<textarea name="desc" cols="" rows="" id="desc"
						placeholder="请您在此描述详细问题" class="fm-area" maxlength="500"></textarea>
				</div>
			</dl>
			<dl class="apply-type">
				<h2>上传图片</h2>
				<div class="img-main">
					<div id="uploader_00" class="wu-example">
						<span id="returnImg" class='dpl-tip-inline-warning'></span>
					</div>
				</div>
				<div class="msg-text">最多上传3张，每张不超过3M，支持JPG、BMP、PNG</div>
			</dl>
			<dl class="apply-type">
				<h2>检测报告</h2>
				<ul class="list-radio">
					<li><input type="radio" name="credential" value="2" id="credential_2"
						class="radio" checked="checked"> <label for="credential_2">尚无检测报告</label>
					</li>
					<li><input type="radio" name="credential" value="1" id="credential_1"
						class="radio"> <label for="credential_1" >已有检测报告</label>
					</li>
				</ul>
			</dl>
			<dl class="apply-type">
				<h2>商品返回方式</h2>
				<ul class="list-radio">
					<li><input type="radio" name="backProductType" value="1" id="backProductType_1"
						class="radio" checked="checked"> <label for="backProductType_1">上门取货</label>
					</li>
					<li><input type="radio" name="backProductType" value="2" id="backProductType_2"
						class="radio"> <label for="backProductType_2">快递至众聚猫</label>
					</li>
				</ul>
			</dl>
			<div class="jd-btns">
			    <input type="submit" class="btn-h3" onclick="onSubmitFirst()"  value="下一步">
			</div>
		</form>
	</div>
	<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
	<script type="text/javascript" src="${staticFile_s}/commons/js/jquery.min.js"></script>
	<script type="text/javascript">
       $.noConflict();
    </script>
	<script type="text/javascript" src='${staticFile_s}/commons/js/ueditor/third-party/webuploader/webuploader.js'></script>
	<script type="text/javascript" src="${staticFile_s}/commons/js/webuploaderhandler.js"></script>
	<script src="${staticFile_s}/commons/js/kefu/apply_first.js" type="text/javascript"></script>
</body>
</html>