<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<link rel="stylesheet" href="${staticFile_s}/commons/css/mui.min.css">
<link rel="stylesheet" href="${staticFile_s}/commons/css/add_syy.css">
  <script type="text/javascript" src="${staticFile_s}/commons/js/main.js"></script>
  <script type="text/javascript" src="${staticFile_s}/commons/js/jquery-1.8.0.min.js"></script>  
  <script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert2.js"></script> 
  <script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<title>收银员</title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp"%>
<%@include file="/WEB-INF/views/commons/commonsIco.jsp"%>
<%@include file="/WEB-INF/views/commons/base.jsp" %>
<style type="text/css">
body {
    font-family: 'Helvetica Neue',Helvetica,sans-serif;
    font-size: 17px;
    line-height: 21px;
    color: #000;
    background-color: #f5f5f5;
    -webkit-overflow-scrolling: touch;
}
.add_syy {
    position: relative;
   bottom: 0;
    margin:0;
    width: 100%;

}
.mui-card {
    margin: 0 0;
    top: 3.5rem;
}
.title{    position: relative;
    top: 4rem;
    line-height:6rem;
    text-align: center;
    font-size: 1.5rem;}
    .sys_note{position: fixed;
    bottom: 1rem;
    width: 100%;
    text-align: center;}
    .red{    color: #e60012;
    font-size: 2rem;
    vertical-align: middle;}
    .note{font-size:1.6rem}
		</style>

</head>
	<body>
	<input type="hidden" id="path" value="<%=path %>"/>
		<header id="header" class="mui-bar mui-bar-nav">
		<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left" href="${path }/wealth/toweaithCenter" style="color: #fff";></a>
		<h1 class="mui-title">收银</h1>
	</header>
	
	<section class="add_syy">
		<div class="title">
			收银记录
		</div>
				<div class="mui-card">
					<div class="mui-card-content">
							<ul class="mui-table-view">
								<c:forEach items="${cashierList }" var="cashierList">
									<li class="mui-table-view-cell">
										<a class="mui-navigate-right" href="${path}/wealth/userMoneyJL?supplierId=${cashierList.supplierId }">
											${cashierList.supplierName }
										</a>
									</li>
								</c:forEach>
							</ul>
					</div>
				</div>
	</section>
	<button type="button" id="scanQRCode" class="mui-btn mui-btn-danger mui-btn-block"	style="position: fixed; bottom: 5rem; background: #e60012; 	z-index: 2; width: 90%; margin: 0 5%; border-radius: 50px; height: 3.5rem;">
		扫一扫
	</button>
	
<div class="sys_note"><p class="note"><b class="red">*</b>扫描商家二维码,登录收银</p></div>
</body>

<script type="text/javascript">
$(document).ready(function(){
var appId = "${signResultMap.appid}";
var timestamp = "${signResultMap.timestamp}";
var nonceStr = "${signResultMap.noncestr}";
var signature = "${signResultMap.signature}";
wx.config({
    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
    appId: appId, // 必填，公众号的唯一标识
    timestamp: timestamp, // 必填，生成签名的时间戳
    nonceStr: nonceStr, // 必填，生成签名的随机串
    signature: signature,// 必填，签名，见附录1
    jsApiList: ['scanQRCode'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
});
});
$("#scanQRCode").click(function() {
    wx.scanQRCode({
        needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
        scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
        success: function (res) {
               var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
               location.href=$("#path").val()+"/wealth/openShouYin?url="+result;
        }
    });
});
	function start(supplierId){
			$.ajax({
				type: 'POST',
				url: $("#path").val()+'/wealth/startOnline',
				dataType: 'json',
				async:false,
				data:"supplierId="+ supplierId,
				success: function(data) {
					if(data==1){
						showMsg("开始收银成功");
						
					}else if(data==2){
						showMsg("您已经在其他店铺收银");
					}
					},
				error: function() {
				}
			});
	}
	function nostart(supplierId){
		$.ajax({
			type: 'POST',
			url: $("#path").val()+'/wealth/startNoOnline',
			dataType: 'json',
			async:false,
			data:"supplierId="+ supplierId,
			success: function(data) {
				if(data==1){
					showMsg("退出收银成功");
				}else {
					showMsg("服务器异常");
					
				}
				},
			error: function() {
			}
		});
	}
	function showMsg(content){
		$.dialog({
	        content : content,
	        title : '众聚猫提示',
	        time: 2000,
		});
	}
</script>
</html>
