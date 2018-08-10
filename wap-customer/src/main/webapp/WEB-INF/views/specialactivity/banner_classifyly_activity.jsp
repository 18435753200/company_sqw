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
		<link rel="stylesheet" href="../../../commons/css/xsj.css"  type="text/css">
		<script type="text/javascript" src="${staticFile_s}/commons/js/jquery.min.js"></script>
		<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>
<input type="hidden" id="path" value="<%=path %>"/>
<input id="activityId" type="hidden" value="${activityId}">
<body class="sg_list">
 		<div class="header">
			<a class="goback" href="javascript:goBack();"><span class="icon i-goback"></span></a>
			<a href="<%=path %>/" class="home-go"></a>
			${title}
		</div>
		<section class="column sg_pic">
			<img src="${activityUrl}" />
    	</section>  
    	<section class="column classifyly">
	    	<c:if test="${not empty tips}">
		    	<c:forEach items="${tips}"  var="tip" varStatus="t" >
		    		<c:if test="${activityId==tip.activity_id}">
		        		<a href="javascript:changeTips('${tip.activity_id}');" class="cur" id="tip${tip.activity_id}">${tip.tip_name}</a>
		        	</c:if>
		        	<c:if test="${activityId!=tip.activity_id}">
		        		<a href="javascript:changeTips('${tip.activity_id}');" id="tip${tip.activity_id}">${tip.tip_name}</a>
		        	</c:if>
        		</c:forEach>
        	</c:if>
        </section>
        <section class="column ">
			<div class="column_body">
			<input id="totalPage" type="hidden" value="${totalPage}">
				<c:if test="${not empty productList.result}">
					<c:forEach items="${productList.result}"  var="productList" varStatus="pro" >
						<c:if test="${productList.productTitle!=null&&productList.productPrice!=null&&productList.productImageUrl!=null}">
						<dl class="dl_temp">
							<dt>
							<a href="<%=path%>/item/get/${productList.productid}">
							<img class="lazy" data-original="${productList.productImageUrl }" src=""/></a></dt>
							<dd>
								<h3><a href="<%=path%>/item/get/${productList.productid}">${productList.productTitle}</a></h3>
								
								<p class="domeprice">
									   <c:if test="${productList.marketPrice != null}">
									   <span >市场价： <i><fmt:formatNumber value="${fn:escapeXml(productList.marketPrice)}" pattern="#0.00" /></i></span>
								      </c:if>
								    </p>
								
								<p><span> <i>${productList.productPrice}</i></span>
									<!-- <a href="#">加入购物车</a> -->
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
				
				var len = $(".classifyly a").length;
				$(".classifyly a").css({"width":(100/len)+"%"});
				
			});  
			//滑动翻页
			window.product_list_loadFlag = true;
			var pageNum =2;
			var totalPage =1;
		 	$(document).ready(function(){
					
				    $(window).scroll(function(){
				        var totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop());     //浏览器的高度加上滚动条的高度
				        if (($(document).height() <= totalheight)&&window.product_list_loadFlag)     //当文档的高度小于或者等于总的高度的时候，开始动态加载数据
				        {
				        	totalPage = Number($("#totalPage").val());
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
				    	var activityId = $('#activityId').val();
				    	$.ajax({
				    		//type : "post",
				    		url :"<%=path%>/view/activity/getspecialscroll/"+activityId,
				    		data : condition,
				    		dataType : "html",
				    		success : function(commentInfo){
				    			$(".column_body").append(commentInfo);
				    			 pageNum++;
				    			 if(pageNum<=totalPage){
				    				 window.product_list_loadFlag = true;
				    			 }
				    			 $("img.lazy").lazyload();
				    		}
				    	
				    	});
				   } 
			});
			function changeTips(actid){
		    	$.ajax({
		    		//type : "post",
		    		url :"<%=path%>/view/activity/getspecialscroll/"+actid,
		    		//data : condition,
		    		dataType : "html",
		    		success : function(commentInfo){
		    			$(".column_body").empty();
		    			$(".column_body").append(commentInfo);
		    			window.product_list_loadFlag = true;
		    			pageNum=2;
		    			$("img.lazy").lazyload();
		    		}
		    	});
				$('#activityId').val(actid);
				$('a.cur').removeClass('cur');
				$('#tip'+actid).addClass('cur');
				
			}
	 	</script> 
</body>
</html>