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
<link href="${staticFile_s}/commons/css/base.css" rel="stylesheet"
	type="text/css">
<link href="${staticFile_s}/commons/css/return.css" rel="stylesheet"
	type="text/css">
	<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
	<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>

<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>	
	<header id="header" class="head">
		<a href="javascript:goBack();" class="goback"><span
			class="icon i-goback"></span></a>
		<h2 class="h-title">商品返回方式</h2>
	</header>
	<div class="return">
		<form action="<%=path%>/kf/rrg/${orderDto.id}-${skuId}-3" onsubmit="return false" method="post">
			<dl class="apply-type">
				<h2>服务类型</h2>
				<ul class="list-check-box">
				    <c:if test="${orderRrg.backProductType == 1}">
				       <li class="list-item active" id="item-sm">
					    	<div class="linein">上门取货</div>
					   </li>
					   <li class="list-item" id="item-kd">
						    <div class="linein">快递至众聚猫</div>
					   </li>
				    </c:if>	
				    <c:if test="${orderRrg.backProductType == 2}">
				       <li class="list-item" id="item-sm">
					    	<div class="linein">上门取货</div>
					   </li>
					   <li class="list-item active" id="item-kd">
						    <div class="linein">快递至众聚猫</div>
					   </li>
				    </c:if>		
				</ul>
				<p class="msg-text">商品返回地址将在服务单审核通过后，以短信的方式告知</p>
			</dl>
	        <input type="hidden" id="provinceId" value="${orderDto.receiveProvinceId }">
	        <input type="hidden" id="cityId" value="${orderDto.receiveCityId }">
	        <input type="hidden" id="countyId" value="${orderDto.receiveAreaId }">
	        <input type="hidden" name="desc" value="${orderRrg.desc }"/>
	        <input type="hidden" name="applyType" value="${orderRrg.applyType }"/>
	        <input type="hidden" name="applyRrgQty" value="${orderRrg.applyRrgQty }"/>
	        <input type="hidden" name="credential" value="${orderRrg.credential }"/>
	        <input type="hidden" name="backProductType" value="${orderRrg.backProductType }"/>
	        <c:if test="${!empty orderRrg.url}">
		        <c:forEach  var="_url" items="${orderRrg.url }"
					varStatus="url_status">
		        	<input type="hidden" name="url" value="${_url }"/>
		        </c:forEach>
	        </c:if>
			<dl class="apply-type app-bx">
				<h2>收货信息</h2>
				<div class="item-col">
					<span class="fm-sele-box"> <span class="fm-select-text" id="provinceName"></span>
						<select data-desc="取件地址所在省份" id="province" name="provinceId" class="fm-select" onchange="changeArea('city');">
					    </select>
					</span> 
					<span class="fm-sele-box"> <span class="fm-select-text" id="cityName" ></span>
						<select data-desc="取件地址所在省份" id="city" name="cityId" class="fm-select" onchange="changeArea('county');">
				     	</select>
					</span> <span class="fm-sele-box"> <span class="fm-select-text" id="countyName" ></span>
						<select data-desc="取件地址所在省份" id="county" name="countyId" class="fm-select" onchange="changeArea('changeCounty');">
					    </select>
					</span>
				</div>
				<div class="texta">
					<textarea name="address" id="address" cols="" rows="" placeholder="填写详细地址"
						class="fm-area">${orderDto.receiveAddress }</textarea>
				</div>
			</dl>
			<dl class="apply-type">
				<div class="fm-item">
					<p>
						<label>联系人：</label> <input type="text" class="fm-text"
							value="${orderDto.receiveName }" data-desc="联系人" id="consignor" name="consignor">
					</p>
					<p class="fm-itemp">
						<label>联系电话：</label> <input type="text" class="fm-text"
							value="${orderDto.receiveMobilePhone }" data-desc="联系人" id="phone" maxlength="12" name="phone">
					</p>
				</div>
			</dl>
	
			<div class="jd-btns">
				<input type="submit" class="btn-h3" onclick="onSubmitSecond()"  value="提交">
<!-- 				<a href="javascript:;" class="btn-h3">提 交</a> -->
			</div>
		</form>
	</div>
	<script src="${staticFile_s}/commons/js/kefu/apply_second.js" type="text/javascript"></script>
	<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
</body>
</html>