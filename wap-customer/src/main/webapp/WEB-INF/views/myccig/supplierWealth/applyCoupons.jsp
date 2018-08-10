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
<link href="${staticFile_s}/commons/css/newbese.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/my.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/common/common.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/main.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/person.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/md5.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/wealth/transfer.js"></script>
<title>申请优惠券</title>
</head>
<body>
	<div class="headly">
		<a href="javascript:;" onclick="goBack();" class="goback"><span><b></b><i></i></span></a>
	    <h3 style="font-size:20px">申请优惠券</h3>
    </div>
	    <div style="text-align: center;margin:5% 0;">
	    <img src="${staticFile_s}/commons/images/qsqyhq.png" width="30%" height="30%">
	    <!--<p style="line-height: 32px;font-size: 20px;margin-top: 2%">申请优惠券</p>-->
    </div>
	<div class="wrap" id="step01">
<form action="" id="tradeForm" method="post">
    <ul class="form-list">
        <li>
          <div class="field">
                <input type="text" class="w code" id="account"  maxlength="11" placeholder="请输入申请数量">
            </div>
        </li>
    </ul>
    <div class="error_tips hide"></div>
    <div class="form-btn">
	    <div class="exit">
          <a href="javascript:" class="bg" onclick="sqy()"   style="background: #219d55;border-radius: 5px">点击申请</a>
          <!-- <a href="javascript:" class="bg" onclick="qrdh()" id="next" style="background: #219d55;border-radius: 5px">确认兑换</a> -->
	    </div>
    </div>
</form>
</div>
<script type="text/javascript">
function sqy(){
	var djq=$("#account").val();
	if(djq==""){
		showMsg("请输入申请数量");
		return;
	}
	$.ajax({
		type: 'POST',
		url: '${staticFile_s}/wealth/applyCoupons_n',
		dataType: 'json',
		data:"applyVal="+ djq,
		success: function(data) {
				if(data==1){
					showMsg("申请成功");
					setInterval(function(){
						window.location = "${staticFile_s}/cusOrder/toweaithCenter";
					}, 2000);
				}else{
					showMsg("申请失败");
					setInterval(function(){
						window.location = "${staticFile_s}/wealth/applyCoupons";
					}, 2000);
				}
			},
		error: function() {
			showMsg("申请失败");
			setInterval(function(){
				window.location = "${staticFile_s}/wealth/applyCoupons";
			}, 2000);
		}
	});
	
} 
</script>

<script>
	num($(".code"));
	$("#pwd").on('input', function () {
        var $this = $(this);
        var valLength = $this.val().length;
        var lis = $('.am-password-handy-security li');
        lis.find('i').css({"visibility": "hidden"});
        for (var i = 0; i < valLength; i++) {
            $(lis[i]).find('i').css({"visibility": "visible"});
        }
    });
	
	/*  function shen(){
		var yhq=$("#account").val();
		$("#yhqaccount").val(yhq);
	}  */
	
</script>
</body>
</html>