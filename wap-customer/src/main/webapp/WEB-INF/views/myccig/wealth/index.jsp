<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<%@include file="/WEB-INF/views/commons/base.jsp" %>
<link href="${staticFile_s}/commons/css/my.css" rel="stylesheet" type="text/css">
<%-- zepto alert --%>
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/person.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/common/common.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/main.js"></script>
<title><spring:message code="title_wealth" /></title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>
<style>
.wealth{width: 100%;background-size: 100%;overflow: hidden;text-align: center;background-color: #ffffff;}
.wealth >div{margin:24px 0px 24px 0px;}
</style>
<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>	
	<div class="headly">
		<a href="javascript:;" onclick="goBack();" class="goback"><span><b></b><i></i></span></a>
	    <h3>我的财富中心</h3>
    </div>
	<div class="wrap" id="myinfo">
		<div class="wealth">
			<div>
				<p>
				<c:if test="${exitUser.icon == null || exitUser.icon == ''}"> 
              	<img  style="border-radius:50%;" src="${path }/commons/images/default.png" />
              	</c:if>
              	<c:if test="${exitUser.icon != null && exitUser.icon != ''}"> 
              	<img  style="border-radius:50%;" src="${fn:escapeXml(exitUser.icon)}" width="100px" height="100px" />
              	</c:if>
				</p>
				<p class="log"><c:if test="${exitUser !=null }">
				<c:if test="${exitUser.nickName  !=null &&  exitUser.nickName  !='' }">
				${fn:escapeXml(exitUser.nickName)}
				</c:if>
				<c:if test="${exitUser.nickName  ==null ||  exitUser.nickName  =='' }">
				${fn:escapeXml(exitUser.userName)}
				</c:if>
				</c:if>
				</p>
				<p class="log">ID:${fn:escapeXml(user.userId)}</p>
			</div>
		</div>
		 <ul class="form-list">
		 	 <li>
	 	 		<div class="label">我的消费金额</div>
	            <div class="field"><i class="zh">${wealth.consumptionAmount}</i></div>
		     </li>	
		     
		 	<a href="<%=path %>/wealth/detail/2">
		 	<li>
	 	 		<div class="label">消费红旗券</div>
	            <div class="field"><i class="zh">${wealth.consumptionFlag}</i></div>
		     </li>
		     </a>
		 	
		 	 <a href="<%=path %>/wealth/detail/4">
		 	 <li>
	 	 		<div class="label">可用的红旗券</div>
	            <div class="field"><i class="zh">${wealth.arrivedFlag}</i></div>
		     </li>
		     </a>
		     
		     <a href="<%=path %>/wealth/detail/3">
		     <li>
	 	 		<div class="label">未到帐红旗券</div>
	            <div class="field"><i class="zh">${wealth.unArrivedFlag}</i></div>
		     </li>
		     </a>
		 
		 	<a href="<%=path %>/wealth/detail/1">
		 	 <li>
	 	 		<div class="label">可用分红额度</div>
	            <div class="field"><i class="zh">${wealth.moneyReward}</i></div>
		     </li>	
		     </a>
		  </ul> 
		   <div class="form-btn">
		   	 <div class="exit">
		       <a href="javascript:;" onclick="toTransferIndex();"  class="bg" >红旗券转账</a>
		    </div>
		   </div>  	
	</div>
</body>
<script>
function toTransferIndex(){
	var state = '${state}';
	/* if(state != 2){
		showMsg("请您激活账号后再进行转账");
	}else{ */
		window.location = "${path}/wealth/transferIndex"
	/* } */	
}
</script>
</html>