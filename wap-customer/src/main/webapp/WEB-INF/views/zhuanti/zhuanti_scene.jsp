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
	<%-- 	<link rel="stylesheet" href="${staticFile_s}/commons/css/xsj.css"  type="text/css"> --%>
		<link rel="stylesheet" href="../../../commons/css/xsj.css"  type="text/css">
		<script type="text/javascript" src="${staticFile_s}/commons/js/jquery.min.js"></script>
		<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
		<script type="text/javascript" src="<%=basePath%>/commons/js/shareuser/shareuserid.js"></script>
</head>
<input type="hidden" id="path" value="<%=path %>/"/>
<input type="hidden"  id="nowdate" value="${nowdate}"/>
<input id="totalPage" type="hidden" value="${totalPage}">
<input id="activityId" type="hidden" value="${activityId}">
 <body class="${bodyclass}">
 	
    <div class="header">
		<a class="goback" href="javascript:goBack();"><span class="icon i-goback"></span></a>
		<a href="<%=path %>/" class="home-go"></a>
		${title}
	</div>
	<c:if test="${activityType==1}">
		<section class="column sg_pic">
			<img src="${activityUrl}" />
    	</section> 
    	<section class="column sg_time"><i class="ico_time"></i>
    	<span id="timer" class="time_txt">${timestr}<input type="hidden" id="endtime" value='<fmt:formatDate value="${endTime}" pattern="yyyy-MM-dd HH:mm:ss" />' /></span></section>
    </c:if>	
	<c:if test="${activityType=='2'}">
    	<section class="column detail">
			<img src="${activityUrl}" />
			<div class="detail_cnt">
				<h3>${titleMsg}</h3>
			</div>
    	</section> 
    </c:if>	
    
    	<section class="column">
			<div class="column_body">
				<c:if test="${not empty productList.result}">
					<c:forEach items="${productList.result}"  var="productList" varStatus="pro" >
						<c:if test="${productList.productTitle!=null&&productList.productPrice!=null&&productList.productImageUrl!=null}">
							<dl class="dl_temp">
								<dt>
									<c:if test="${activityType==1}">
									<a href="<%=path%>/item/get/${productList.productid}?endtime=${endTimes}">
									</c:if>
									<c:if test="${activityType==2}">
									<a href="<%=path%>/item/get/${productList.productid}">
									</c:if>
									<img   class="lazy"  src="" data-original="${productList.productImageUrl }"/></a></dt>
								<dd>
									<h3>
									<c:if test="${activityType==1}">
									<a href="<%=path%>/item/get/${productList.productid}?endtime=${endTimes}">
									</c:if>
									<c:if test="${activityType==2}">
									<a href="<%=path%>/item/get/${productList.productid}">
									</c:if>
									${productList.productTitle}</a></h3>
									
									<p class="domeprice">
									   <c:if test="${productList.marketPrice != null}">
									   <span >市场价： <i><fmt:formatNumber value="${fn:escapeXml(productList.marketPrice)}" pattern="#0.00" /></i></span>
								      </c:if>
								    </p>
									<p><span> <i>${productList.productPrice}</i></span>
										<%-- <c:if test="${scene_flag=='falshsale'}"><a href="#">加入购物车</a> </c:if>	 --%>
									</p>
								</dd>
							</dl>
						</c:if>
					</c:forEach>
				</c:if>
				
			</div>
			<div class="loadw">
			<c:choose>
				<c:when test="${totalPage > 1}">
					<span>加载中...</span>
				</c:when>
				<c:otherwise>
					<span>已经到底啦!</span>
				</c:otherwise>
			</c:choose>
			</div>
    	</section>
    	
	<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
	
 	<script type="text/javascript" src="${staticFile_s}/commons/js/lazy/jquery.lazyload.js"></script>
	<script type="text/javascript">
		$(function(){
			$("img.lazy").lazyload();
		});
		
	 	$(document).ready(function(){
	 		if(${activityType==1}){
				var time =  $('#endtime').val();
				(function(){  
				      window['timer']=setInterval(function(){  
				    	 TimeTo('timer', time);//定义倒计时的结束时间，注意格式  
				    	var n = parseInt($('#nowdate').val());
						$('#nowdate').val((n+1000)+"");
				      },1000);//定义计时器，每隔1000毫秒 也就是1秒 计算并更新 div的显示  
				})();
	 		}	
	 		
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
		    		url :"<%=path%>/view/activity/getscroll/${activityId}",
		    		data : condition,
		    		dataType : "html",
		    		success : function(commentInfo){
		    			$(".column_body").append(commentInfo);
		    			$("img.lazy").lazyload();
		    			 pageNum++;
		    			 if(pageNum<=totalPage){
		    				 window.product_list_loadFlag = true;
		    			 }else{
		    				 $(".loadw").find("span").html("已经到底啦!");
		    			 }
		    		}
		    	});
		   } 
			
		});
		function TimeTo(divid,starttime){
			var t = new Date(Date.parse(starttime.replace(/-/g, "/"))).getTime();//取得指定时间的总毫秒数  
			//var   n = new Date().getTime();//取得当前毫秒数  
			var n = parseInt($('#nowdate').val());
		    var  c = t - n;//得到时间差  
		  if(c<=0){//如果差小于等于0  也就是过期或者正好过期，则推出程序  
			  $('#'+divid).html('活动已经结束');  
		      clearInterval(window['timer']);//清除计时器  
		      return;//结束执行  
		  }
		  var ds = 60*60*24*1000,//一天共多少毫秒  
		      d = parseInt(c/ds),//总毫秒除以一天的毫秒 得到相差的天数  
		      h = parseInt((c-d*ds)/(3600*1000)),//然后取完天数之后的余下的毫秒数再除以每小时的毫秒数得到小时  
		      m = parseInt((c - d*ds - h*3600*1000)/(60*1000)),//减去天数和小时数的毫秒数剩下的毫秒，再除以每分钟的毫秒数，得到分钟数  
		      s = parseInt((c-d*ds-h*3600*1000-m*60*1000)/1000);//得到最后剩下的毫秒数除以1000 就是秒数，再剩下的毫秒自动忽略即可  
		      var hour = h+''; 
		      var min = m+'';   
		      var sec = s+'';
		          if(hour.length==1)hour='0'+hour;
		          if(min.length==1)min='0'+min;
		          if(sec.length==1)sec='0'+sec;
		      $('#'+divid).html( '闪购倒计时  '+d+'<i class="color_gray">天</i>'+hour+'<i class="color_gray">时</i>'+min+'<i class="color_gray">分</i>'+sec+'<i class="color_gray">秒</i>');//最后这句讲定义好的显示 更新到 ID为 timer的 div中  
		}  
	</script>
</body>
</html>