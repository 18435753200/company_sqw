<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>登录</title>
    <meta name="keywords" content="众聚猫">
    <meta name="description" content="众聚猫">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no">
    <meta name="apple-mobile-web-app-status-bar-style" content=" black ">
    <meta name="apple-mobile-web-app-title" content="众聚猫">
    <meta name="format-detection" content="telephone=no">
    <%@include file="/WEB-INF/views/commons/user.jsp" %>
    <script type="text/javascript">
    $(document).ready(function(){
		var openType = openType();
		var openId='<%=request.getAttribute("openId")%>';

        if (openType == 'weixin' && openId == 'null') {
            var url = $("#path").val() + "/customer/userToPay?isWeiXin=true&isUser=true";
            window.location.href = url;
        }

		if(openType!='weixin'){
			window.location.href=$("#path").val()+"/Qr/isNoWx";
		}

		function openType() {
			var ua = navigator.userAgent.toLowerCase();
			if (ua.match(/MicroMessenger/i) == "micromessenger") {
				return 'weixin';
			}
		}
		})
</script>
<style type="text/css">
  
.load2 {padding:15px 0;text-align:center;font-size:14px;background:#ffffff;height: 25px;}  
  
@-webkit-keyframes loading {  
  from {opacity: 1;}  
  to {opacity: 0.2;}  
}  
.loading {display: inline-block;margin-right:20px;position: relative;width: 33px;height:33px;vertical-align:7px;}  
.loading span {display:inline-block;width: 20%;height: 40%;color:#e60012;background: #e60012;position: absolute;left: 100%;top: 100%;opacity: 0;-webkit-animation: loading 0.9s linear infinite;-webkit-border-radius: 30px;}  
.loading .bar1 {-webkit-transform:rotate(0deg) translate(0, -142%); -webkit-animation-delay: 0s;color:#e60012;}  
.loading .bar2 {-webkit-transform:rotate(30deg) translate(0, -142%); -webkit-animation-delay: -0.9167s;color:#e60012;}  
.loading .bar3 {-webkit-transform:rotate(60deg) translate(0, -142%); -webkit-animation-delay: -0.833s;color:#e60012;}  
.loading .bar4 {-webkit-transform:rotate(90deg) translate(0, -142%); -webkit-animation-delay: -0.75s;color:#e60012;}  
.loading .bar5 {-webkit-transform:rotate(120deg) translate(0, -142%); -webkit-animation-delay: -0.667s;color:#e60012;}  
.loading .bar6 {-webkit-transform:rotate(150deg) translate(0, -142%); -webkit-animation-delay: -0.5833s;color:#e60012;}  
.loading .bar7 {-webkit-transform:rotate(180deg) translate(0, -142%); -webkit-animation-delay: -0.5s;color:#e60012;}  
.loading .bar8 {-webkit-transform:rotate(210deg) translate(0, -142%); -webkit-animation-delay: -0.41667s;}  
.loading .bar9 {-webkit-transform:rotate(240deg) translate(0, -142%); -webkit-animation-delay: -0.333s;color:#e60012;}  
.loading .bar10 {-webkit-transform:rotate(270deg) translate(0, -142%); -webkit-animation-delay: -0.25s;color:#e60012;}  
.loading .bar11 {-webkit-transform:rotate(300deg) translate(0, -142%); -webkit-animation-delay: -0.1667s;color:#e60012;}  
.loading .bar12 {-webkit-transform:rotate(330deg) translate(0, -142%); -webkit-animation-delay: -0.0833s;color:#e60012;}  
</style>
    <%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
    <%@include file="/WEB-INF/views/commons/commonsIco.jsp" %>
</head>
<body>
 <input type="hidden" id="path" value="<%=path %>"/>
	<div class="load2">  
    <span class="loading">  
        <span class="bar1"></span>  
        <span class="bar2"></span>  
        <span class="bar3"></span>  
        <span class="bar4"></span>  
        <span class="bar5"></span>  
        <span class="bar6"></span>  
        <span class="bar7"></span>  
        <span class="bar8"></span>  
        <span class="bar9"></span>  
        <span class="bar10"></span>  
        <span class="bar11"></span>  
        <span class="bar12"></span>  
    </span>
</div>
</body>
</html>