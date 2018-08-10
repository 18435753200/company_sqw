<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>门店详情</title>
<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="no">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<%@include file="/WEB-INF/views/commons/base.jsp" %>
<link rel="stylesheet" href="${staticFile_s}/commons/css/mui.min.css">
<link rel="stylesheet" href="${staticFile_s}/commons/css/shopDetail.css">
<script src="${staticFile_s}/commons/js/jquery-1.8.0.min.js"></script>
<script src="${staticFile_s}/commons/js/qcwd/mui.min.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<script src="${staticFile_s}/commons/js/updateShop/updateShop.js?version=1.0.0" type="text/javascript"></script>
</head>
<script type="text/javascript">
$(document).ready(function(){
	if("${select}"!=1){
		$("#data1").hide();
	} 
	if("${select}"!=2){
		$("#data2").hide();
	} 
	if("${select}"!=3){
		$("#data3").hide();
	} 
})
</script>
<style>
select {
    font-size: 14px;
    height: auto;
    margin-top: 1px;
    background-color: #fff;
    width: 32%;
        text-overflow: ellipsis;
    white-space: nowrap;
}
</style>
<body>
		<input type="hidden" id="staticFile_s" value="${staticFile_s}" />
		<input type="hidden" id="supplierId" value="${ssd.supplierId }">
		<header id="header" class="mui-bar mui-bar-nav">
			<h1 class="mui-title">修改信息</h1>
		<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
		<button class="mui-btn mui-btn-blue mui-btn-link mui-pull-right" onclick="updateData()">确定</button>
		</header>
		<section class="mui-content">
			<ul class="mui-table-view mui-table-view-striped mui-table-view-condensed">
				<li class="mui-content" style="padding:0.5rem 1rem">
		            <div class="mui-table">
		                <div class="mui-table-cell mui-col-xs-10">
		                	<form action="/supplier/updateData" id="form1" method="post" >
			                	<input type="hidden" value="${ssd.supplierId}" name="supplierId" id="supplierId" />
			                	<div id="data1">
				                    <h5>门店名称：<input type="text" value="${ssd.nameJC }" name="nameJC" id="nameJC" /></h5>
				                    <h5>联系人：<input type="text" value="${ssd.contact }" name="contact" id="contact" /></h5>
				                    <h5>电    话：<input type="tel" value="${ssd.contactTel }" name="contactTel" id="contactTel"></h5>
				                    <h5>地    区： </br><select id="provinceId" name="provinceId"   onchange="ProvincesAndCitiesLinkage(this.value)" style="width:28%;    border: 1px solid #cdcdcd;font-size:17px;color:#8f8f94;">  
				                    			<option>请选择省</option>  
									<c:forEach items="${allProvices}" var="allProvice" >
									 	<option  value="${allProvice.provinceid}" <c:if test="${allProvice.provinceid eq ssd.provinceId}" >selected</c:if> >${allProvice.provincename}</option>
									 </c:forEach>
									 </select>
									 <select id="cityId" name="cityId" onchange="ProvincesAndCitie(this.value)" style="font-size:17px;color: #8f8f94;    border: 1px solid #cdcdcd;">    
									   	   <option>请选择市</option>
									   	   <c:forEach items="${allCitys}" var="allCity" >
									 			<option  value="${allCity.cityid}" <c:if test="${allCity.cityid eq ssd.cityId}" >selected</c:if> >${allCity.cityname}</option>
									       </c:forEach>
			                         </select>
			                         <select id="areaId" name="areaId" style="font-size:17px;color: #8f8f94;    border: 1px solid #cdcdcd;">    
			                               <option>请选择县</option>   
			                                <c:forEach items="${allCountys}" var="allCounty" >
									 			<option  value="${allCounty.countyid}" <c:if test="${allCounty.countyid eq ssd.areaId}" >selected</c:if> >${allCounty.countyname}</option>
									       </c:forEach>
			                         </select>  </h5>
			                    </div>
			                    <div id="data2">
				                    <h5>经营特色:<textarea rows="5" cols=""   name="jyTS" id="jyTS">${ssd.jyTS }</textarea></h5>
			                    </div>
			                    <div id="data3">
				                    <h5>经营时间：<textarea rows="5" cols=""   name="jySJ" id="jySJ">${ssd.jySJ }</textarea></h5>
			                    </div>
		                    </form>
		                </div>
		            </div>
		        </li>
		    </ul>
		</section>
</body>
</html>