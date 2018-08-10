<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	request.setAttribute("path", path);
	request.setAttribute("basePath", basePath);
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<link href="${staticFile_s}/commons/css/newbese.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/my.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="${staticFile_s}/commons/js/zepto.min.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/main.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/user/common.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>

<title><spring:message code="title_address" /></title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
<%@include file="/WEB-INF/views/commons/commonsIco.jsp" %>
<link rel="stylesheet" href="${staticFile_s}/commons/css/gwcjs.css" type="text/css">
<style>
	.shdz{
		background-color: #e60012;
		color: #ffffff;	
	}
	.headly span b {
    position: absolute;
    width: 1.1rem;
    height: 0;
    border-top: 0.1rem solid #ffffff;
    transform: rotate(-45deg);
    -webkit-transform: rotate(-45deg);
    top: 35%;
    left: 0.05rem;
}
.headly span i {
    position: absolute;
    width: 0;
    height: 1rem;
    border-left: 0.1rem solid #ffffff;
    transform: rotate(-45deg);
    -webkit-transform: rotate(-44deg);
    top: 50%;
    left: 0.6rem;
}
.goods_item {
    background: #ffffff;
    margin-bottom:0;
    padding: 0 1.5rem;
    border-bottom: 1px solid #f3f3f3;
}
</style>
</head>
<body>

 <div id="addressList">
	<input type='hidden' value="${referenceAddressId}" id="referenceAddressId"/>

	<div class="headly" style="
    width: 100%;
    height: 4.4rem;
    line-height: 4.4rem;
    font-size: 1.9rem;
    text-align: center;
    color: #505050;
    position: fixed;
    background: #fff;
    z-index: 99">
		<a href="javascript:checkGoBack();" class="bug-go goback"><span><b></b><i></i></span></a>
		<h3 class="shdz" style="    background: #e60012;   color: #fff;">收货地址</h3>
	</div>
	
	<div class="wrap" id="address" style="position: relative; top:4.4rem;    padding-bottom: 10rem; z-index: 1;">
		<c:forEach var="addr" items="${addresses}" varStatus="i">
			<div class="goods_item"  id="addr${addr.addressId}">
				<div class="name seadd" onclick="checkGoBack('${addr.addressId}')"  style="">${fn:escapeXml(addr.contactor)}<span>${fn:escapeXml(addr.mobile)}</span></div>
				<div class="address1 seadd" onclick="checkGoBack('${addr.addressId}')" data-animal-type="${addr.addressId }"  style="font-size:1.3rem;color: #616161;">${fn:escapeXml(addr.provinceCityCountyName)} ${fn:escapeXml(addr.address)}</div>
			
				<%-- 				<c:if test="${not empty referenceAddressId}">
					<p class="edit">
						<input type="radio" name="radio" class="do" onclick="checkGoBack()" value="${addr.addressId}" <c:if test="${referenceAddressId eq addr.addressId}">checked="checked"</c:if> />送到这里去
					</p> 
				</c:if> --%>
<%-- 				<p class="edit">
				<c:if test="${not empty referenceAddressId}">
					<span class="ed1">
						<label>
							<i class="or">
								<input type="radio" name="radio" class="ra" onclick="checkGoBack()" value="${addr.addressId}" <c:if test="${referenceAddressId eq addr.addressId}">checked="checked"</c:if> />
							</i>
						</label>
					送到这</span>
				</c:if>
				<c:if test="${empty referenceAddressId}">
					<span class="ed1">
						<label>
							<i class="t1">
								<input type="radio" name="radio" class="ra" onclick="checkGoBack()" value="${addr.addressId}" <c:if test="${!referenceAddressId eq addr.addressId}"></c:if> />
							</i>
						</label>
					送到这</span>
				</c:if>
				</p> --%>
				<p class="edit">
					<c:if test="${addr.isDefault}">
						<span class="ed1">
							<label>
								<i class="or">
									<input type="radio" name="radio" class="ra"/>
								</i><b class="defaultadd">默认地址</b>
							</label>
						</span>
					</c:if>
					<c:if test="${!addr.isDefault}">
						<span class="ed1" >
							<label><i class="t1">
								<input type="radio" name="radio" onclick="defaultAddr(${addr.addressId})" class="ra" >
							</i><b class="undefaultadd">设为默认地址</b>
							</label>
						</span>
					</c:if>
				<a onclick="deleteAddrBt('${addr.addressId}')"><span class="ed3"><i class="t3"></i></span></a>
				<a onclick="updateAddrBt(${addr.addressId})"><span class="ed2"><i class="t2"></i></span></a>
				</p>
			</div>
		</c:forEach>
		<c:if test="${fn:length(addresses) <= 9  }">
		<div class="exit" id="add-address">
			<a href="javascript:addAddrBt('add');" style="width: 90%;margin: 0 5%;height: 3.7rem;line-height: 3.7rem;font-size: 1.5rem;border-radius: 30px;"><i class="new"></i>新建地址</a>
		</div>
		</c:if>
	</div>

<!-- 添加或修改 -->
 <div class="wrap" id="addressAddOrUpdate" style="display: none;position: relative;
    top: 5rem;">
<form action="" id="updateOrAddAddrssForm" method="post" >
 <input type="hidden"  name="addressId" id="AUaddressId" value="${addr.addressId }">
    <ul class="form-list">
        <li>
            <div class="label">收货人：</div>
            <div class="field">
                <input type="text" class="text" id="contactorF" name="contactor" value="${addr.contactor }" maxlength="20" />
            </div>
        </li>
        <li>
            <div class="label">联系方式：</div>
            <div class="field"> 
                <input type="tel" class="text" name="mobile"  id="mobileF" value="${addr.mobile }" maxlength="11" />
            </div>
        </li>
        <li>
            <div class="label">所在地区：</div>
            <div class="field">
                 <select name="provinceId" id="provinceId"  class="select" onchange="getCity()" >
                   <option value="">请选择</option>
                </select>
            </div>
            <div class="field">
              <select name="cityId" id="cityId" class="select"   onchange="getCount()">
                      <option value="">请选择</option>
                </select>
            </div>
             <div class="field">
	             <select name="areaId" id="area_id" class="select" onchange="reAddress()">
	            	 <option value="">请选择</option>
	            </select>
            </div>
        </li>
        <li>
            <div class="label">详细地址：</div>
            <div class="field">
                 <input type="text" class="w" name="address" id="addressF" value="${addr.address }">
            </div>
        </li>
    </ul>
     <div class="error_tips hide"></div>
    <div class="exit">
       <a href="javascript:AddOrUpdateAddr();" class="bg" id="addressOperBtn" style="width: 90%;
    margin: 0 5%;
    height: 3.5rem;
    line-height: 3.5rem;
    font-size: 1.5rem;
    border-radius: 10px;">保存</a>
    </div>
</form>
</div>
</div>
<input type="hidden" value="<%=path%>" id="path">
<%@include file="/WEB-INF/views/commons/footer.jsp" %>
<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
<script src="${staticFile_s}/commons/js/address/address.js" type="text/javascript"></script>
</body>
</html>