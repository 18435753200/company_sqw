<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="path" value="<%=request.getContextPath() %>"/>
<script type="text/javascript">
	function login_submit(){
		$("#loginForm").submit();
	}
	function quit (){
		setTimeout(function(){
			window.location.href = '${path }/customer/logout';
		},2000);
		
		$.dialog({
				content : "退出登录成功",
				title : '众聚猫提示',
				time:2000,
		});

	}
	
</script>
<footer id="footer" style="display: none;" class="footer clearfix">
<form action="${path }/customer/toLogin" method="get" id="loginForm">
    <nav class="footer-nav">
	    <a href="${path }/index/index.html">首页</a>
	    <a href="${path }/cart/index">购物车</a>
	    <a href="${path }/cusInfo/clientIndex">客户端</a> 
	    <c:if test="${exitUser != null }">
		    <a href="${path }/customer/toMy">个人中心</a>
		   <%--  <a href="${path }/customer/logout">退出</a> --%>
		    <a href="javascript:;" onclick="quit();">退出</a>
	    </c:if>
	    <c:if test="${exitUser == null }">
			   <a href="javascript:login_submit()">登录</a>
	    </c:if>
	 </nav>
		<input type="hidden" name="url" value="${fn:escapeXml(returnUrl)}"  >
	</form>
    <p>COPYRIGHT@2016 北京三旗企业管理有限公司版权所有 <br />
        京ICP备案 16055863号</p>
</footer>