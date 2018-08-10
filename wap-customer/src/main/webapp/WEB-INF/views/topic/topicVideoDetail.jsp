<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	request.setAttribute("path", path);
	request.setAttribute("basePath", basePath);
%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="black" name="apple-mobile-web-app-status-bar-style">
	<meta content="telephone=no" name="format-detection">
	<title>${activityTopicDto.title}</title>
	<link rel="stylesheet" type="text/css" href="/commons/css/newbese.css">
	<link rel="stylesheet" type="text/css" href="/commons/css/tip.css">
    <script src="<%=basePath%>commons/js/zepto.min.js"></script>
</head>
<body>
	<div class="header">
		<a href="javascript:history.go(-1)" class="bug-go"></a>
		详情
	</div>
	<%--需要的一些参数 start--%>
    <input id="path" type="hidden" value="<%=path%>">
    <input id="titleUrl" type="hidden" value="${filePath}${activityTopicDto.titleUrl}">
	<div class="tipdetail">
		<h2> ${activityTopicDto.title}</h2>
		<c:if test="${activityTopicDto.activityType eq 3 }">
			<!--视频显示-->
			<div class="video">
				<iframe height=300 width=100% src="${activityTopicDto.viewUrl}" frameborder=0 allowfullscreen></iframe>
				<!-- <div class="video-bg">
					<div class="video-box"><span class="del"></span></div>
				</div> -->
			</div>
		</c:if> 
		<div class="tip_cent">
			<c:catch var="catchExcption">
			     <c:import url="${picUrl1}${activityTopicDto.details}" charEncoding="UTF-8" />
			</c:catch>
		</div>
		<div class="fenx">
			<a href="javascript:;" id="tip_" 
		        <c:if  test="${activityTopicDto.likeStatus eq 1 }">class="selected"</c:if> 
		        <c:if  test="${activityTopicDto.likeStatus !=1 }"> onclick="clicklike('${activityTopicDto.topicId}',${activityTopicDto.likeNum});" class=""</c:if>><i class="z1"></i>赞一下</a>
	<!-- 		<a href="javascript:;" id="share" onclick="share('${activityTopicDto.topicId}',${activityTopicDto.shareNum});"><i class="z2"></i>分 享</a> -->
		</div>
	</div>
	<!--分享弹框-->
	<div class="sha">
		<div class="share_bg"></div>
		<div class="share_cont">
			<div class="share_c">
			   <div class="s_title"><span>分享到</span></div>
			   <ul class="s_coent">
		   			<div><a href="#"  class="jiathis_button_weixin"><i class="fen01"></i><em>微信</em></a></div>
		   			<div><a href="#"  class="jiathis_button_weixin"><i class="fen02"></i><em>朋友圈</em></a></div>
		   			<div><a href="#" onclick="sharetosina('${activityTopicDto.topicId}',${activityTopicDto.shareNum});"><i class="fen03"></i><em>新浪微博</em></a></div>
		   			<div><a href="#" onclick="sharetoqq('${activityTopicDto.topicId}',${activityTopicDto.shareNum});" ><i class="fen04"></i><em>QQ好友</em></a></div>	   
			   </ul>
			   <div class="cancel">取消</div>
			</div>
	   </div>
	</div>	
	<script type="text/javascript" src="http://v3.jiathis.com/code/jia.js" charset="utf-8"></script>
    <script>
        var title='${activityTopicDto.title}';
        var url=window.location.href;
        var picurl='${filePath}'+'${activityTopicDto.titleUrl}';
		$(function(){			
			//分享
			$('#share').click(function(){
				$('.sha').show()
			});
			$('.cancel,.share_bg').click(function(){
				$('.sha').hide()
			});
			
			$('span.del').click(function(){
				$('.video-bg').hide()
			});
           /*$(".tip_cent p").each(function (i) {
				$(this).has('img').addClass("tipimg");
			});
			var wifi=isWife();
			if(wifi){
				$('.video-bg').hide()
			}*/
		})
		function isWife(){
		    var wifi = true;
		    var ua = window.navigator.userAgent;
		    var con = window.navigator.connection;

		    // 如果是微信
		    if(/MicroMessenger/.test(ua)){
		        // 如果是微信6.0以上版本，用UA来判断
		        if(/NetType/.test(ua)){
		            if(ua.match(/NetType\/(\S*)$/)[1] != 'WIFI'){
		                wifi = false;
		            }
		        // 如果是微信6.0以下版本，调用微信私有接口WeixinJSBridge
		        }else{
		            document.addEventListener("WeixinJSBridgeReady",function onBridgeReady(){
		                WeixinJSBridge.invoke('getNetworkType',{},function(e){
		                    if(e.err_msg != "network_type:wifi"){
		                        wifi = false;
		                    }
		                });
		            });
		        }
		    // 如果支持navigator.connection
		    }else if(con){
		        var network = con.type;
		        if(network != "wifi" && network != "2" && network != "unknown"){  // unknown是为了兼容Chrome Canary
		            wifi = false;
		        }
		    }
//			window.networkWIFI = wifi;
			return wifi;
		}
		/*
		    点赞
		*/
		function clicklike(topicId,likeNum){
			var condition = "topicId="+topicId+"&likeNum="+likeNum+"";
			$.ajax({
				type : "post",
				url :$("#path").val()+"/view/topic/clicklike",
				data : condition,
				dataType : "text",
				async : false,
				success : function(addCartFlag){
					if(addCartFlag=='100'){
						$("#tip_").addClass("selected");
					}
				}
			});
		}
		//分享
		function share(topicId,shareNum) { 
			var condition = "topicId="+topicId+"&shareNum="+shareNum+"";
			$.ajax({
				type : "post",
				url :$("#path").val()+"/view/topic/clickShare",
				data : condition,
				dataType : "text",
				async : false,
				success : function(addCartFlag){
				}
			});
		}
		//分享到新浪微博  
		function sharetosina(topicId,shareNum) { 
			var sharesinastring='http://v.t.sina.com.cn/share/share.php?title='+title+'&url='+url+'&content=utf-8&sourceUrl='+url+'&pic='+picurl;  
			window.open(sharesinastring);  
		}
		//分享到QQ好友  
		function sharetoqq(topicId,shareNum) { 
			window.open("http://connect.qq.com/widget/shareqq/index.html?title="+title+"&url="+url+"&desc=&pics="+picurl+"&site="); 
		}
	 </script>
</body>
</html>