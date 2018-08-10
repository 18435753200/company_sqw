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
<html >
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="black" name="apple-mobile-web-app-status-bar-style">
	<meta content="telephone=no" name="format-detection">
	<title>详情</title>
	<link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/newbase-tip.css">
	<link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/tip.css">
</head>
<body>
	<div class="header">
		<a href="javascript:history.go(-1)" class="bug-go"></a>
		详情
	</div>
    <%--需要的一些参数 start--%>
    <input id="path" type="hidden" value="<%=path%>">
	<div class="tipdetail">
		<h2>夏天你用对洗面奶了吗？夏天你用对洗面奶了吗？夏天你用对洗面奶了吗？</h2>
		<div class="tip_cent">
		    <p class="tipimg"><img src="../commons/img/pic/tip01.jpg"></p>
		    <p><a href="#">韩国 Nature Republic  芦荟泡沫洗面奶   19.90</a></p>
			<p class="tipimg"><img src="../commons/img/pic/tip01.jpg"></p>
			<p>韩国 Nature Republic 自然乐园 芦荟泡沫洗面奶 <a href="#">蕴含芦荟</a>成分，有效清洁的同时，修复受损肌肤，还 会散发淡淡芦荟清香，轻松包裹肌肤面部多余油脂...</p>
			<p class="tipimg"><img src="../commons/img/pic/tip01.jpg"></p>
			<p>韩国 Nature Republic 自然乐园 芦荟泡沫洗面奶 蕴含芦荟成分，有效清洁的同时，修复受损肌肤，还 会散发淡淡芦荟清香，轻松包裹肌肤面部多余油脂...</p>
			<p class="tipimg"><img src="../commons/img/pic/tip01.jpg"></p>
			<p>韩国 Nature Republic 自然乐园 芦荟泡沫洗面奶 蕴含芦荟成分，有效清洁的同时，修复受损肌肤，还 会散发淡淡芦荟清香，轻松包裹肌肤面部多余油脂...</p>
			<p class="tipimg"><img src="../commons/img/pic/tip01.jpg"></p>
			<p>韩国 Nature Republic 自然乐园 芦荟泡沫洗面奶 蕴含芦荟成分，有效清洁的同时，修复受损肌肤，还 会散发淡淡芦荟清香，轻松包裹肌肤面部多余油脂...</p>
		</div>
		<div class="fenx">
		    <!--赞后显示的-->
<!-- 			<a href="javascript:;" class="selected" style="display:none;"><i class="z1"></i>赞一下</a> -->
			<!--赞后显示的 end-->
			<a href="javascript:;" id="tip_" onclick="clicklike(100,20);" class=""><i class="z1"></i>赞一下</a>
			<a href="javascript:;" id="share"><i class="z2"></i>分 享</a>
		</div>
	</div>
	<!--分享弹框-->
	<div class="sha">
		<div class="share_bg"></div>
		<div class="share_cont">
			<div class="share_c">
			   <div class="s_title"><span>分享到</span></div>
			   <ul class="s_coent">
		   			<div><a href="#"><i class="fen01"></i><em>微信</em></a></div>
		   			<div><a href="#"><i class="fen02"></i><em>朋友圈</em></a></div>
		   			<div><a href="#"><i class="fen03"></i><em>新浪微博</em></a></div>
		   			<div><a href="#"><i class="fen04"></i><em>QQ好友</em></a></div>
			   </ul>
			   <div class="cancel">取消</div>
			</div>
	   </div>
	</div>	
	<script src="<%=basePath%>commons/js/zepto.min.js"></script>
    <script>
		$(function(){
			//分享
			$('#share').click(function(){
				$('.sha').show()
			});
			$('.cancel,.share_bg').click(function(){
				$('.sha').hide()
			});
		})
		/*
		    点赞
		*/
		function clicklike(id,num){
			var condition = "id="+id+"&num="+num+"";
			$.ajax({
				type : "post",
				url :$("#path").val()+"/tip/clicklike",
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
	 </script>
</body>
</html>