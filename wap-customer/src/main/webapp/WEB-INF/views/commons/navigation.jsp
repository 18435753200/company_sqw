<%@page import="com.mall.category.po.SysOperateLog"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<!--公用底部Start--> 
     
	 <div class="no-foot"></div>
	 <!-- 当渠道是bestpay时 导航底部如下 -->
	 <c:choose>
	     <c:when test="${cookie.c_r_.value!=null&&cookie.c_r_.value=='bestpay'}">
	         <ul class="footer yzffooter">	
				<li class="set">
					<a href="${path}/" class="foot1"><i></i><span>首页</span></a>
				</li>	
				<li class="catset">
					<a href="${path}/cart/index" class="foot3"><i></i><span>购物车</span></a>
				</li>
				<li class="myset">
					<a href="${path}/customer/toMy" class="foot4"><i></i><span>我</span></a>
				</li>
		   </ul>
	     </c:when>
	     <c:otherwise>
			  <c:choose>
			 线上导航 
			    <c:when test="${path==null || path==''}">
			         <ul class="footer">	
						<li>
							<a href="${path}/" class="foot1"><i></i><span>首页</span></a>
						</li>	
					    <li>
							<a href="${path}/wap/act/fenlei.html" class="foot2"><i></i><span>分类</span></a>
						</li>
						<li class="ico_search"><a href="${path}/wap/act/guojiaguan.html"></a></li>
						<li class="catset">
							<a href="${path}/cart/index" class="foot3"><i></i><span>购物车</span></a>
						</li>
						<li class="myset">
							<a href="${path}/customer/toMy" class="foot4"><i></i><span>我</span></a>
						</li>
				      </ul>
			     </c:when>
			     <c:otherwise>
			      线下导航 供测试、回归、开发使用  
				      <ul class="footer">	
						<li>
							<a href="http://192.168.0.197:8080/wap/index.html" class="foot1"><i></i><span>首页</span></a>
						</li>	
					    <li>
							<a href="http://192.168.0.197:8080/wap/act/fenlei.html" class="foot2"><i></i><span>分类</span></a>
						</li>
						<li class="ico_search"><a href="http://192.168.0.197:8080/wap/act/guojiaguan.html"></a></li>
						<li class="catset">
							<a href="${path}/cart/index" class="foot3"><i></i><span>购物车</span></a>
						</li>
						<li class="myset">
							<a href="${path}/customer/toMy" class="foot4"><i></i><span>我</span></a>
						</li>
					  </ul>
			     </c:otherwise>
			  </c:choose>
	   </c:otherwise>
	 </c:choose>	
<!--底部End-->

--%>
<link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/grzxcss/aui_grzx_icon.css" />
<div class="no-foot"></div>
<div class="footer-m">
	 <c:choose>
	     <c:when test="${cookie.c_r_.value!=null&&cookie.c_r_.value=='bestpay'}">
	         <ul class="footer yzffooter">	
				<li class="set">
					<a href="${path}/" class="nav1"><i></i></a>
				</li>	
				<li class="catset">
					<a href="${path}/cart/index" class="nav4"><i></i></a>
				</li>
				<li class="myset">
					<a href="${path}/customer/toMy" class="nav5"><i></i></a>
				</li>
		   </ul>
	     </c:when>
	     <c:otherwise>
			  <c:choose>
<%-------------------------------------- 线上导航 -------------%>
			     <c:when test="${path==null || path==''}">
			         <ul class="footer">	
						<li>
							<a href="${path}/" class="nav1"><i></i></a>
						</li>	
					    <li>
							<a href="${path}/act/fenlei.html" class="nav2"><i></i></a>
						</li>
						<li>
							<a href="${path}/act/3fenlei.html" class="nav3" style="width:49px"><i></i></a>
						</li>
						<li class="catset">
							<a href="${path}/cart/index" class="nav4"><i></i></a>
						</li>
						<li class="set">
							<a href="${path}/customer/toMy" class="nav5"><i></i></a>
						</li>
				      </ul>
			     </c:when>
			     <c:otherwise>
<%-------------------------------------- 线下导航 供测试、回归、开发使用 -------------%>
				      <ul class="footer">	
						<li>
							<a href="${path}/wap/index.html" class="nav1"><i></i></a>
						</li>	
					    <li>
							<a href="${path}/wap/act/fenlei.html" class="nav2"><i></i></a>
						</li>
						<li>
							<a href="${path}/act/3fenlei.html" class="nav3" style="width:49px"><i></i></a>
						</li>
						<li class="catset">
							<a href="${path}/cart/index" class="nav4"><i></i></a>
						</li>
						<li class="myset set">
							<a href="${path}/customer/toMy" class="nav5"><i></i></a></li></ul>
						<!-- </li>
						<li class="ico_search"><a href="http://192.168.0.197:8080/wap/act/guojiaguan.html" class="nav3"></a></li>
					  </ul> -->
			     </c:otherwise>
			  </c:choose>
	   </c:otherwise>
	 </c:choose>
</div>
<script type="text/javascript" src="${staticFile_s}/commons/js/api.js"></script>
<script type="text/javascript">
    apiready = function () {
    }
</script>