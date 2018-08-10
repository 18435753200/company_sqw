<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%
	String path2 = request.getContextPath();
	request.setAttribute("path", path2);
	request.setAttribute("url", request.getServletPath());
	String basePath2 = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path2 + "/";
	
%>
<!doctype html>
<html>
<head>
		<meta charset="utf-8">
        <title>闪购</title>
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
<input type="hidden" id="path" value="<%=path2 %>/"/>
<input type="hidden" id="nowdate" value="${nowdate}"/>
<body>
		<div class="header">
			<a class="goback" href="javascript:goBack();"><span class="icon i-goback"></span></a>
			<a href="<%=path2 %>/" class="home-go"></a>
			闪购
		</div>
		<div id="box1">
    	<section class="column sg_beginTit">
			<ul class="boxFlex sp-nav">
	            <li id="beginning1" class="cur"><a href="javascript:changeSaleCur(beginning);">火热进行</a></li>
			    <li id="unbegin1"><a href="javascript:changeSaleCur(unbegin);">即将开始</a></li>
	        </ul>
    	</section> 
    	<section class="column sp-body">
			
			<div class="column_body" id="beginning">
				<c:if test="${!empty curActivites}">
					<c:forEach items="${curActivites}" var="act">
						<dl class="dl_temp1">
							<dt><a href="<%=path2 %>/view/activity/get/${act.activityId}"><img  class="lazy" src="" data-original="${picUrl}${act.mainPicUrl}" /><i class="ico_tab">${act.activityName}</i></a></dt>
							<dd>
								<h3>${act.mainTitle}</h3>
								<p class="time_txt"><i class="ico_time"></i>
								<span id="${act.activityId}" name="timer"><input type="hidden"  
								value="<fmt:formatDate value="${act.endTime}" pattern="yyyy-MM-dd HH:mm:ss" />"/>
								还剩<i class="color_red">00</i>天<i class="color_red">00</i>时<i class="color_red">00</i>分<i class="color_red">00</i>秒</span></p>
							</dd>
						</dl>
					</c:forEach>
				</c:if>
			</div>
			
			<div class="column_body" id="unbegin"  style="display:none">
					<c:if test="${!empty comingActivites}">
					<c:forEach items="${comingActivites}" var="act">
					<dl class="dl_temp1">
						<dt>
							<a href="<%=path2 %>/view/activity/get/${act.activityId}"><img  class="lazy"  src="" data-original="${picUrl}${act.mainPicUrl}"/><i class="ico_tab">${act.activityName}</i></a>
							<span class="dl_bg"></span>
							<span class="dl_wen"><i></i>即将开始</span>
						</dt>	
						<dd>
							<h3>${act.mainTitle}</h3>
							<p class="time_txt"><i class="ico_time"></i><span id="${act.activityId}" name="untimer"><input type="hidden"  value="${act.startTime}" /></span></p>
						</dd>
					
					</dl>
				</c:forEach>
				</c:if>
			</div>
			
    	</section>   
	</div>
	
    	<script src="${staticFile_s}/commons/js/zepto.min.js"></script>			
		<script src="${staticFile_s}/commons/js/swiper.min.js"></script>
		<script src="${staticFile_s}/commons/js/news.js"></script>	
		<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
        <%@include file="/WEB-INF/views/commons/navigation.jsp" %> 
		
    	<script type="text/javascript" src="${staticFile_s}/commons/js/lazy/jquery.lazyload.js"></script>
	<script type="text/javascript">
		$(function(){
			$("img.lazy").lazyload();
		});
		
    	$(document).ready(function(){
    		(function(){  
				window['nowdate']=setInterval(function(){
				 	var n = parseInt($('#nowdate').val());
					$('#nowdate').val((n+1000)+"");
				 },1000);//定义计时器，每隔1000毫秒 也就是1秒 计算并更新 div的显示  
			})();
    		
    		$("span[name='timer']").each(function(){ 
				var id = $(this).attr("id"); 
				var time =  $(this).find("input").val();
				(function(){  
				      window[id]=setInterval(function(){  
				    	  TimeTo(id, time);//定义倒计时的结束时间，注意格式  
				      },1000);//定义计时器，每隔1000毫秒 也就是1秒 计算并更新 div的显示  
				})();
				
			});
			 
			
			$("span[name='untimer']").each(function(){
				var id = $(this).attr("id");
				var time =  $(this).find("input").val();
				var t = new Date(time);
				var month = t.getUTCMonth()+1;
				var day = t.getUTCDate();
				$('#'+id).html('<i class="color_red">'+t.getFullYear()+'</i>年<i class="color_red">'+month+'</i>月<i class="color_red">'+day+'</i>日开始');
			});
			
		});
	
		
		function TimeTo(divid,starttime){ 
		  var t = new Date(Date.parse(starttime.replace(/-/g, "/"))).getTime();//取得指定时间的总毫秒数  
		  //var   n = new Date().getTime();//取得当前毫秒数  
		  var n = parseInt($('#nowdate').val());
		  var  c = t - n;//得到时间差  
		  if(c<=0){//如果差小于等于0  也就是过期或者正好过期，则推出程序  
			  $('#'+divid).html('活动已经结束');  
		      clearInterval(window[divid]);//清除计时器  
		      clearInterval(window['nowdate']);
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
		      $('#'+divid).html( '还剩<i class="color_red">'+d+'</i>天<i class="color_red">'+hour+'</i>时<i class="color_red">'+min+'</i>分<i class="color_red">'+sec+'</i>秒');//最后这句讲定义好的显示 更新到 ID为 timer的 div中  
		}  
		
		function changeSaleCur(flag){
			for (var I=1; I<2; I=I+1){
				scroll(1+0,I+0); 
			}
			if("unbegin"==flag){
				$('#unbegin1').addClass("cur");
				$('#beginning1').removeClass("cur");
				$('#unbegin').style.display="block";
				$('#beginning').style.display="none";
			}else if("beginning"==flag){
				$('#unbegin1').removeClass("cur");
				$('#beginning1').addClass("cur");
				$('#unbegin').style.display="none";
				$('#beginning').style.display="block";
			}
		}
	  
    </script>  
	</body>
</html>