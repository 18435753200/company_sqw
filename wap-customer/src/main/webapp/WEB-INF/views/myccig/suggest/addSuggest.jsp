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
<script type="text/javascript" src="${staticFile_s}/commons/js/suggest/suggest.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/common/common.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/main.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/person.js"></script>
<title><spring:message code="title_suggest" /></title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>

<style>
.yjfk{
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
</style>

</head>
<script type="text/javascript">
	function countChar(textareaNamezzjs,spanName){
		if(document.getElementById(textareaNamezzjs).value.length<=100){
			document.getElementById(spanName).innerHTML=100-document.getElementById(textareaNamezzjs).value.length;
		}
		if(document.getElementById(textareaNamezzjs).value.length>100){
			document.getElementById(spanName).innerHTML=0;
		}
	}
</script>
<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>	
	<div class="headly">
		<a href="javascript:;" onclick="goBack();" class="goback"><span><b></b><i></i></span></a>
	    <h3 class="yjfk">意见反馈</h3>
    </div>
    
	<div class="wrap" id="myinfo">
		<form action="" name="suggest-form">
		    <ul class="form-list">
		    
		    	<li>
		            <div class="label">反馈类型：</div>
		            <div class="field">
		                <select name="suggestType" class="se">
		                    <c:forEach items="${suggestTypeMap}" var="suggestType">
		                    	<option value="${suggestType.key}">${suggestType.value}</option>
		                    </c:forEach>
		                </select>
		                <span class="r"></span>
		            </div>
		        </li>
		        
		         <li>
		            <div class="field">
			            <textarea name="suggestContent" id="textarea" class="ar" placeholder="请描述你的问题" onkeydown="countChar('textarea','numcenter');" onkeyup="countChar('textarea','numcenter');"></textarea>
						 <p class="num"><em id="numcenter">100</em>字</p>
		            </div>
		        </li>
		        
		        <li>
		            <div class="field">
		                <input type="text" name="suggestContactWay" class="cell" placeholder="请输入手机号或邮箱" />
		            </div>
		        </li>
		    </ul>
		    
		    <div class="exit">
			    <div class="form-btn">
					<a href="javascript:;" class="btn sub-suggest">提交</a>
				</div>
		    </div>
	    </form>
	</div>
</body>
</html>