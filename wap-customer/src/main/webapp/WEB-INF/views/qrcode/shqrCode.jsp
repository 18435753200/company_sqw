<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<%@include file="/WEB-INF/views/commons/base.jsp" %>
<link href="${staticFile_s}/commons/css/my.css" rel="stylesheet" type="text/css">
<%-- zepto alert --%>
<script type="text/javascript" src="${staticFile_s}/commons/js/main.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/qrcode/qrcode.js"></script>
<title>${title }</title>
<link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/grzxcss/aui.css" />
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
<style>
    body{background:url(${path }/commons/img/grzxtp/login_bg1.jpg) repeat-y;background-size:100%;}
    img{display: inline;}
    *{margin: 0;padding: 0;font-size: 1rem;}
    a{color: #de4339;
    	text-decoration: none;}
	.aui-bar.aui-bar-light {
	    border: none;
	    background: rgb(255,255,255,0);
	}
	.aui-bar-nav .aui-btn {
	    position: relative;
	    z-index: 20;
	    padding-top: 0;
	    padding-bottom: 0;
	    margin: 0;
	    line-height: 3rem;
	    height: 3rem;
	}
	.aui-bar-nav .aui-title {
		font-size: 0.8rem;
	    line-height: 3rem;
	    color: #fff;
	}
	.aui-list .aui-list-item-right, .aui-list-item-title-row em {
	    max-width: 50%;
	    position: relative;
	    font-size: 0.6rem;
	    color: #454545;
	    margin-left: 0.25rem;
	}
	.aui-list .aui-list-item-title {
    	font-size: 0.8rem;color: #2e2e2e;    
    	line-height: 3rem;    
    	width: 100%;   
    	text-align: center;
    }
	.aui-bar-nav .aui-btn .aui-iconfont {
	    font-size: 0.8rem;
	    line-height: 3rem;
	    padding: 0;
	    margin: 0;
	    color: #ffffff;
	}
	
	.aui-content {
	    height: 40%;
	    background: rgba(255,255,255,0.1);
	    box-shadow:0px 2px 2px #e6e6e6;
	    width: 70%;
        margin-top:20%;
        margin-left:15%;
	    border-radius: 5px;
	}
	.aui-list {
	    position: relative;
	    font-size: 0.8rem;
	    background-color: #f3f3f3;
	    border-top: 1px solid #dddddd;
	}
	.bot_img{
		text-align: center;
		position: fixed; 
		bottom: 2rem; 
		top: 83%;    
		width: 100%;
	}
	.aui-content h3,img{
		text-align: center;
		color: #e5493d;
		line-height: 3rem;
	    margin-top:-1.4rem;
	}
	.fkewm{text-align: center;margin:5%}
	.ewm_text{
		text-align: center;
	    margin: 5%;
	    color: #de4339;
	}
    .l_a_href{padding-right: 3rem;font-size: 0.7rem;}
    .r_a_href{font-size: 0.7rem;}

</style>
</head>
<body>
	<header class="aui-bar aui-bar-nav aui-bar-light" style="background-color:#DE4339;">
        <a class="aui-pull-left aui-btn" href="javascript:;" onclick="goBack();">
            <span class="aui-iconfont aui-icon-left"></span>
        </a>
        <div class="aui-title">二维码收款</div>
    </header>
     <div class="bot_img"><img src="${path }/commons/img/grzxtp/zjm_lg.png" width="40%" style="display: inline" ;/></div>
     
	<section class="aui-content">
        <!-- <ul class="aui-list aui-list-in">
         <li class="aui-list-item">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-title" style="color: #e5493d">扫一扫，向我付款</div>
                </div>
         </li>
        </ul> -->
        <div style="width:100%;text-align:center;font-size:0.75rem;color:white;margin-top:0.75rem;">扫一扫  向我付款</div>
          <div class="fkewm"><input id="qrUrl" type="hidden" style="width: 80%"><br>
			<div id="qrcode"></div>
   		</div>
   </section>
	
<script type="text/javascript">
	var qrcode = new QRCode(document.getElementById("qrcode"), {
	
	});
	
	 function getUrl(){
		 var userId =${userId};
		var rcodeType =${rcodeType};
		$.ajax({
			type : "POST",
			url : "${staticFile_s}/Qr/createSelectQr",
			data : {bizid:userId,rcodeType:rcodeType},
			async : false,
			dataType : "json",
			success : function(data) {
				document.getElementById("qrUrl").value=data.rcodeImgTxt;
			}
		});
	}
	
	function makeCode () {		
		var elText = document.getElementById("qrUrl");
		if (!elText.value) {
			elText.focus();
			return;
		}
		
		qrcode.makeCode(elText.value);
	}
	getUrl();
	makeCode();
	
	$("#qrUrl").
		on("blur", function () {
			makeCode();
		}).
		on("keydown", function (e) {
			if (e.keyCode == 13) {
				makeCode();
			}
		});
</script>
</body>
</html>