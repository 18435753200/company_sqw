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
        <title>${title }</title>
        <meta name="keywords" content="这里是关键词">
        <meta name="description" content="这里是描述">
        <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no">
        <meta name="apple-mobile-web-app-status-bar-style" content=" black ">
        <meta name="apple-mobile-web-app-title" content="众聚猫">
        <meta name="format-detection" content="telephone=no">
        <meta name="baidu-tc-cerfication" content="30f757b11897dc4f697eb568cb7eb2a3" />
        <meta name="baidu-site-verification" content="SgTbeKNm8i" />
		<link rel="stylesheet" href="${staticFile_s}/commons/css/xsj.css"  type="text/css">
		<script type="text/javascript" src="${staticFile_s}/commons/js/jquery.min.js"></script>
		<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>
<input type="hidden" id="path" value="<%=path %>/"/>
<input id="totalPage" type="hidden" value="${totalPage}">
<body class="xsj_prolist">
    <div class="header">
		<a class="goback" href="javascript:goBack();"><span class="icon i-goback"></span></a>
		<a href="<%=path %>/" class="home-go"></a>
		${title }
	</div>
    	<section class="column detail">
			<img src="${staticFile_s}/commons/newimg/zhuoyue.jpg" />
			<div class="detail_cnt">
				<h3>${title }</h3>
			</div>
    	</section> 
    
    	<section class="column ">
			<div class="column_body">
				<c:if test="${not empty productList.items}">
					<c:forEach items="${productList.items}"  var="product" varStatus="pro" >
						<c:if test="${product.pname!=null&&product.unit_price!=null&&product.imageurl!=null}">
							<dl class="dl_temp">
								<dt>
									<a href="<%=path%>/item/get/${product.pid}">
									<img   class="lazy"  src="" data-original="${imgurl}${product.imageurl }"/></a></dt>
								<dd>
									<h3>
									<a href="<%=path%>/item/get/${product.pid}">
									${product.pname}</a></h3>
									<p><span> <i>${product.unit_price}</i></span>
									</p>
								</dd>
							</dl>
						</c:if>
					</c:forEach>
				</c:if>
				
			</div>
    	</section>
    	
	<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
	
 	<script type="text/javascript" src="${staticFile_s}/commons/js/lazy/jquery.lazyload.js"></script>
	<script type="text/javascript">
		$(function(){
			$("img.lazy").lazyload();
		});
		
	 	$(document).ready(function(){
	 		
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
		    	var condition = "pageno="+pageNum;
		    	$.ajax({
		    		//type : "post",
		    		url :"<%=path%>/view/zhuoyue/toZhuoYuescroll",
		    		data : condition,
		    		dataType : "html",
		    		success : function(commentInfo){
		    			$(".column_body").append(commentInfo);
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