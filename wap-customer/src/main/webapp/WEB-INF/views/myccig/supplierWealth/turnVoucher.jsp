<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<%@include file="/WEB-INF/views/commons/base.jsp" %>
<link rel="stylesheet" href="${staticFile_s}/commons/css/grzxcss/public.css">
<link rel="stylesheet" href="${staticFile_s}/commons/css/grzxcss/djqzz.css">
<script src="${staticFile_s}/commons/js/rem.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/common/common.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/main.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/person.js"></script>
<title>企业代金券转个人代金券</title>
<style type="text/css">
	.qrdh{
		background-color: #e51c23;
		display: inline;
	    width: 90%;
	    margin: 0 5%;
	    height: 2rem;
	    line-height: 2rem;
	    margin-bottom: 0;
	    font-size: 0.9rem;
	    border-radius: 10px;
	    position: fixed;
	    z-index: 10;
	    bottom: 0.5rem;
	    text-align: center;
	}
</style>
</head>
<body>
<header class="header">
    <a class="go_back" href="javascript:;" onclick="goBack();"></a>
    <div class="title">转换个人M券</div>
</header>
<div class="content">
    <div class="banner">
        <div class="bl">企业M券转个人M券比例<span>100</span>%</div>
    </div>
    <ul class="countList">
        <li>
            <div>请输入M券数量：</div>
            <input type="number" class="text code" onkeyup="zhuan()"  id="account" name="account" maxlength="11">
        </li>
        <li>
            <p>获得个人M券数量：</p>
            <input type="text" id="djqaccount" name="djqaccount" disabled>
        </li>
        <li>
            <div>企业M券余额:</div>
            <input type="text" value="${accountBalance }" disabled>
        </li>
        <div class="qrdh">
        	<a href="javascript:" onclick="qrdhz()" id="next" style="color: #ffffff">确认兑换</a>
        </div>
    </ul>
</div>
<script>
num($(".code"));

	function zhuan(){
		var djq=$("#account").val();
		var djqz=djq*1;
		$("#djqaccount").val(djqz);
	}
	
	function qrdhz(){
		var djq=$("#account").val();
		var djqz=$("#djqaccount").val();
		var djqye=${accountBalance };
		if(djq==""||djq<=0){
			showMsg("请输入转账金额");
			return;
		}
		if(djq>djqye){
			showMsg("余额不足");
			return;
		}
		$.ajax({
			type: 'POST',
			url: '${staticFile_s}/wealth/turnVoucher_n',
			dataType: 'json',
			data:"account="+ djq+"&djqaccount="+djqz,
			success: function(data) {
					if(data==0){
						showMsg("转账成功");
						setInterval(function(){
							window.location = "${staticFile_s}/wealth/toweaithCenter";
						}, 2000);
					}else if(data==1){
						showMsg("转换金额为0");
						setInterval(function(){
							window.location = "${staticFile_s}/wealth/turnVoucher";
						}, 2000);
					}else if(data==4){
						showMsg("余额不足");
						setInterval(function(){
							window.location = "${staticFile_s}/wealth/turnVoucher";
						}, 2000);
					}
					else{
						showMsg("转账失败");
						setInterval(function(){
							window.location = "${staticFile_s}/wealth/turnVoucher";
						}, 2000);
					}
				},
			error: function() {
				showMsg("转账失败");
				setInterval(function(){
					window.location = "${staticFile_s}/wealth/turnVoucher";
				}, 2000);
			}
		});
	}
</script>
</body>
</html>
