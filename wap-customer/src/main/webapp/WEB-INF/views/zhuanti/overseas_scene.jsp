<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%
	String path = request.getContextPath();
	request.setAttribute("path", path);
	request.setAttribute("url", request.getServletPath());
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html>
	<head>
        <meta charset="utf-8">
        <title>海外直采</title>
        <meta name="keywords" content="这里是关键词">
        <meta name="description" content="这里是描述">
        <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no">
        <meta name="apple-mobile-web-app-status-bar-style" content=" black ">
        <meta name="apple-mobile-web-app-title" content="众聚猫">
        <meta name="format-detection" content="telephone=no">
        <meta name="baidu-tc-cerfication" content="30f757b11897dc4f697eb568cb7eb2a3" />
        <meta name="baidu-site-verification" content="SgTbeKNm8i" />
		<link rel="stylesheet" href="${staticFile_s}/commons/css/swiper.min.css">
        <link rel="stylesheet" href="${staticFile_s}/commons/css/xsj.css">
        <%-- <link rel="shortcut icon" type="image/x-icon" href="${path}/commons/img/favicon.ico" /> --%>
        <script type="text/javascript" src="${staticFile_s}/commons/js/jquery.min.js"></script>
        <%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
		<script type="text/javascript" src="<%=basePath%>/commons/js/shareuser/shareuserid.js"></script>
		<%@include file="/WEB-INF/views/commons/commonsIco.jsp" %>
	</head>
    <body class="sg_air">
    	
	    <div class="header">
			<a class="goback" href="javascript:goBack();"><span class="icon i-goback"></span></a>
			<a href="<%=path %>/" class="home-go"></a>
			海外直采
		</div>
    	<section class="column sg_pic">
				<img src="${staticFile_s}/commons/newimg/img_12.jpg" />
    	</section>  
    	<section class="column">
		    <div id="col-nav" >
		     <c:if test="${not empty tcCountries }">
				<ul class="swiper-wrapper sg_country">
		           	<c:forEach items="${tcCountries}" var="country" varStatus="statu">
		           		<li class="swiper-slide"><a href="<%=path%>/view/free/tax?countryName=${country.name}&enName=${country.description}">
		           		<em><img src="${picUrl}${country.img}" /></em><i class="coun_txt">${country.name}</i><span class="ico_barr"><i></i></span></a></li>
		           	</c:forEach>
				</ul>
				</c:if>
			</div>
    	</section>
		<input id="picUrl" type="hidden" value="${picUrl}">
		<input id="totalPage" type="hidden" value="${totalPage}">
		<input id="countryName" type="hidden" value="${countryName}">
    	<section class="column shop_list1">
			<div class="column_body">
				<div class="column_tit">${curCountryName}</div>
				
				<div id="pro_list">
					<c:if test="${not empty searchResponse.items }">
		 				<c:forEach items="${searchResponse.items}" var="item" varStatus="statu">
							<dl class="dl_temp">
								<dt><a href="<%=path%>/item/get/${item.pid}"><img  class="lazy"  src="" data-original="${picUrl}${item.imageurl}" /></a></dt>
								<dd>
									<h3><a href="<%=path%>/item/get/${item.pid}">${item.b2cPname}</a></h3>
								
									<p class="domeprice">
									   <c:if test="${item.domestic_price != null}">
									   <span >市场价： <i><fmt:formatNumber value="${fn:escapeXml(item.domestic_price)}" pattern="#0.00" /></i></span>
								      </c:if>
								    </p>
									<p><span class="unprice"> <i><fmt:formatNumber value="${fn:escapeXml(item.unit_price)}" pattern="#0.00" /></i></span></p>
								</dd>
							</dl>
							
						</c:forEach>
		 			</c:if>
				</div>
			</div>
    	</section>
		<script src="${staticFile_s}/commons/js/zepto.min.js"></script>			
		<script src="${staticFile_s}/commons/js/swiper.min.js"></script>
		<script src="${staticFile_s}/commons/js/news.js"></script>	
		<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
		<script type="text/javascript" src="${staticFile_s}/commons/js/lazy/jquery.lazyload.js"></script>
		<script type="text/javascript">
		$(function(){
			$("img.lazy").lazyload();
		});
		
		$(function(){
    
	    //滑动翻页
	    window.product_list_loadFlag = true;
	    var totalPage = Number($("#totalPage").val());
	    var pageNum =2;
	    $(window).scroll(function(){
	        var totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());     //浏览器的高度加上滚动条的高度
	        if (($(document).height() <= totalheight)&&window.product_list_loadFlag)     //当文档的高度小于或者等于总的高度的时候，开始动态加载数据
	        {
	            window.product_list_loadFlag = false;
	            //如果totalPage<2,不请求数据
	            if(totalPage>=2){
	            	loadDataPager();
	            }
	        }   
	   });
	    
	    function loadDataPager(){
	    	//获取搜索keyword
	    	var condition = "isB2C="+true+"&pageno="+pageNum+"&countryName=${countryName}&picUrl=${picUrl}";
	    	$.ajax({
	    		type : "post",
	    		url :"<%=path%>/view/free/toOverseasSceneScroll",
	    		data : condition,
	    		dataType : "html",
	    		success : function(commentInfo){
	    			$("#pro_list").append(commentInfo);
	    			$("img.lazy").lazyload();
	    			 pageNum++;
	    			 if(pageNum<=totalPage){
	    				 window.product_list_loadFlag = true;
	    			 }
	    			 
	    		}
	    	
	    	});
	   } 
	});
			
		</script>	
		
	</body>
</html>