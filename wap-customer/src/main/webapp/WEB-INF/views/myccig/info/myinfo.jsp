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
<link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/grzxcss/aui.css" />
<link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/grzxcss/grzx_yjfk_css.css" />
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/user/common.js"></script>
<title><spring:message code="title_cusinfo_cusinfo" /></title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>

<style type="text/css">
.fieldd{
	width: 100%;
}
	.zh{
		font-size: 0.8rem;
		top: 50%;
	}

.rDialog-ok, .rDialog-ok:hover {
    background: #dd191b;
    color: #fff;
}
.rDialog-wrap {
    position: relative;
    background: #404040;
    opacity: .9;
    background-clip: padding-box;
    border-radius: 10px;
    -moz-border-radius: 10px;
    -o-border-radius: 10px;
    -webkit-border-radius: 10px;
    box-shadow: 1px 1px 1px #000;
    padding: 1em 1em;
}
.aui-list .aui-list-item-label, .aui-list .aui-list-item-label-icon {
    color: #212121;
    width: 50%;
    min-width: 1.5rem;
    margin: 0;
    padding: 0;
    padding-right: 0.25rem;
    line-height: 2.2rem;
    position: relative;
    overflow: hidden;
    white-space: nowrap;
    max-width: 100%;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    display: -webkit-box;
    display: -webkit-flex;
    display: flex;
    -webkit-align-items: center;
    align-items: center;
}
.fieldd {
    width: 200%;
}
select {
    font: inherit;
    color: inherit;
    line-height: 0.9rem;
    border-radius: 10px;
    padding:0;
    font-size: 0.8rem;
}

</style>
</head>

<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>
<input type="hidden" value="cus" id="faa">	
    <header class="aui-bar aui-bar-nav">
        <a class="aui-pull-left aui-btn" href="javascript:;" onclick="goBack();">
            <span class="aui-iconfont aui-icon-left"></span>
        </a>
        <div class="aui-title">个人信息</div>
	</header>
	<div class="aui-content aui-margin-b-15">
    <ul class="aui-list aui-form-list">
       <%--  <li class="aui-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-label">
                   	 用户名
                </div>
                <div class="aui-list-item-input">
                    <input type="text" class="w" readonly="readonly" id="userName" value="${fn:escapeXml(userInfo.userName)}" placeholder="请输入用户名">
                </div>
            </div>
        </li> --%>
        <li class="aui-list-item">
	            <div class="aui-list-item-inner">
	                <div class="aui-list-item-label">
	                    ID
	                </div>
	                <div class="aui-list-item-input">
	                    <input type="text" class="w" name="userId" readonly="readonly"  value="${fn:escapeXml(userInfo.userId)}" placeholder="请输入ID">
	                </div>
	            </div>
        	</li>
	        <a href="<%=path %>/cusInfo/changeNickName">
		       <li class="aui-list-item">
	            <div class="aui-list-item-inner">
	                <div class="aui-list-item-label">
	                	    昵称
	                </div>
	                <div class="aui-list-item-inner aui-list-item-arrow">
	                    <div class="aui-list-item-title"> <input type="text" class="text" readonly="readonly"  id="nickName" value="${fn:escapeXml(userInfo.nickName)}" placeholder="请输入昵称"></div>
	                    <div class="aui-list-item-right">
	                    </div>
	                </div>
	            </div>
	       	 </li>
	       	</a>
	        
	        <li class="aui-list-item">
	            <div class="aui-list-item-inner">
	                <div class="aui-list-item-label">
	                    	性别
	                </div>
	                <div class="aui-list-item-inner aui-list-item-arrow">
	                    <div class="aui-list-item-input">
	                     <select name="gender" id="gender" class="select">
		                    <option value="1" <c:if test="${userInfo.gender == 1}"> selected = "selected" </c:if> >男</option>
		                    <option value="2" <c:if test="${userInfo.gender == 2}"> selected = "selected" </c:if> >女</option>
		                    <option value="0" <c:if test="${userInfo.gender == 0 || userInfo.gender == null }"> selected = "selected" </c:if> >保密</option>
               		 	</select>
               		 	</div>
	                    <div class="aui-list-item-right">
	                    </div>
	                </div>
	            </div>
        	</li>
	        <li class="aui-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-label">
                  	  生日
                </div>
                <div class="aui-list-item-inner aui-list-item-arrow">
	                    <div class="aui-list-item-input"  style="padding-top: 1rem;">
	                    	 <input type="date" class="text" name="birth" id="birth" value="<fmt:formatDate value='${userInfo.birthday }' pattern='yyyy-MM-dd'/>" placeholder="请选择年月日">
	                    </div>
	                    <div class="aui-list-item-right">
	                    </div>
	             </div>
            </div>
        </li>
	        <li class="aui-list-item">
	            <div class="aui-list-item-inner">
	                <div class="aui-list-item-label">
	                   	 手机
	                </div>
	                <div class="aui-list-item-input">
	                    <input type="text" class="text" readonly="readonly" name="mobile" id="mobile" value="${fn:escapeXml(userInfo.mobile)}" placeholder="请输入手机号码">
	                    
	                </div>
	            </div>
        	</li>
	        <a href="<%=path %>/cusInfo/changeEmail">
		        <li class="aui-list-item">
		            <div class="aui-list-item-inner">
		                <div class="aui-list-item-label">
		                  	  邮箱
		                </div>
		                <div class="aui-list-item-inner aui-list-item-arrow">
	                    <div class="aui-list-item-input">
	                    	 <input type="email" class="text" value="${fn:escapeXml(userInfo.email)}" placeholder="请输入邮箱" style="font-size: 0.7rem;>
	                     </div>
	                    <div class="aui-list-item-right">
	                    </div>
	                </div>
		            </div>
	        	</li>
        	</a>
	        <%-- <li class="aui-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-label">
                    	邀请码
                </div>
                <div class="aui-list-item-input">
                    <input type="text" class="w" readonly="readonly" id="tjMobile" value="${fn:escapeXml(userInfo.tjMobile)}" placeholder="请输入邀请码">
                </div>
            </div>
        </li> --%>
	        <a href="<%=path %>/cusInfo/changeEducational">
		        <li class="aui-list-item">
		            <div class="aui-list-item-inner">
		                <div class="aui-list-item-label">
		                   	 学历
		                </div>
		                <div class="aui-list-item-inner aui-list-item-arrow">
	                    <div class="aui-list-item-input">
	                    	 <input type="text" class="text" value="${fn:escapeXml(userInfo.educational)}"/>
	                     </div>
	                    <div class="aui-list-item-right">
	                    </div>
	                </div>
	       	 	</li>
        	</a>
	        <li class="aui-list-item">
	            <div class="aui-list-item-inner">
	                <div class="aui-list-item-label">
	                   	 政治面貌
	                </div>
	                
	                <div class="aui-list-item-inner aui-list-item-arrow">
	                    <div class="aui-list-item-input">
	                    	 <select name="politicalStatus" id="politicalStatus" class="select">
		                    <option value="1" <c:if test="${userInfo.politicalStatus == 1}"> selected = "selected" </c:if> >党员</option>
		                    <option value="2" <c:if test="${userInfo.politicalStatus == 2}"> selected = "selected" </c:if> >团员</option>
		                    <option value="4" <c:if test="${userInfo.politicalStatus == 4}"> selected = "selected" </c:if> >民主人士</option>
		                    <option value="3" <c:if test="${userInfo.politicalStatus == 3 || userInfo.politicalStatus == null }"> selected = "selected" </c:if> >群众</option>
		                     </select> 
	                     </div>
	                    <div class="aui-list-item-right">
	                    </div>
	                </div>

        </li>
	        <%-- <a href="<%=path %>/cusInfo/changeWeixin">
		        <li class="aui-list-item">
		            <div class="aui-list-item-inner">
		                <div class="aui-list-item-label">
		                   	微信号
		                </div>
		                <div class="aui-list-item-input">
		                    <input type="text" class="text" value="${fn:escapeXml(userInfo.weixin)}" placeholder="请输入微信号">
		                </div>
		            </div>
	        	</li>
        	</a> --%>
	        
	        <%-- <c:if test="${userInfo.idCard == null || userInfo.idCard == ''}">
	        	<a href="<%=path %>/cusInfo/changeIdCard">
			         <li class="aui-list-item">
			            <div class="aui-list-item-inner">
			                <div class="aui-list-item-label">
			                 	   身份证号
			                </div>
			                <div class="aui-list-item-input">
			                    <input type="text" class="text" value="${fn:escapeXml(userInfo.idCard)}" placeholder="请输入身份证号">
			                    <span class="r"></span>
			                </div>
			            </div>
			        </li>
		        </a>
              	${fn:escapeXml(userInfo.idCard)}
              </c:if>
	        <c:if test="${userInfo.idCard != null && userInfo.idCard != ''}">
			        <li class="aui-list-item">
			            <div class="aui-list-item-inner">
			                <div class="aui-list-item-label">
			                 	   身份证号
			                </div>
			                <div class="aui-list-item-input">
			                    <input type="text" class="text" readonly="readonly" value="${fn:escapeXml(userInfo.idCard)}">
			                </div>
			            </div>
				     </li>
	        </c:if> --%>
	        <a href="<%=path %>/customer/toGetpass?from=cus">
		        <li class="aui-list-item">
		            <div class="aui-list-item-inner">
		                <div class="aui-list-item-label">
		                    	修改登录密码
		                </div>
		                 <div class="aui-list-item-inner aui-list-item-arrow">
	                    <div class="aui-list-item-input">
	                    	
	                     </div>
	                    <div class="aui-list-item-right">
	                    </div>
	                </div>
		            </div>
	       	 	</li>
       	 	</a>
	       	<%-- <a href="<%=path %>/cusInfo/toVerify">
		      	<li>
					<li class="aui-list-item">
			                <div class="aui-list-item-label">
			                  	实名认证
			                </div> 
						<c:choose>
							<c:when test="${empty flag }">
					            <div class="aui-list-item-input">
					                <i class="aui-list-item-inner">未认证</i>
					            </div>
				            </c:when>
				            <c:otherwise>
					            <div class="aui-list-item-input">
					                <i class="aui-list-item-inner">已认证</i>
					            </div>
				            </c:otherwise>
			            </c:choose>
	            	</li>
	        	</li>
	        </a> --%>
       </ul>
	</div>
<%@include file="/WEB-INF/views/commons/footer.jsp" %>
<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
<script src="${staticFile_s}/commons/js/cciginfo/info.js" type="text/javascript"></script>
</body>
</html>	