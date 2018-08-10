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
<title>代金券转优惠券</title>
</head>
<body>
	<div class="headly">
		<a href="javascript:;" onclick="goBack();" class="goback"><span><b></b><i></i></span></a>
	    <h3>代金券转优惠券</h3>
    </div>
    <div style="text-align: center;margin:5% 0;">
    <img src="${staticFile_s}/commons/images/qdzy.png" width="30%" height="30%">
    <p style="line-height: 32px;font-size: 20px;margin-top: 2%">代金券转优惠券兑换比例为100%</p>
    </div>
<div class="wrap" id="step01">
<form action="${staticFile_s}/wealth/transferAccountFrom" id="tradeForm" method="post">
    <ul class="form-list">
        <li>

          <div class="field">
                <input type="text" class="w code" onmousemove="zhuan()"  id="account" name="account" maxlength="11" placeholder="请输入代金券数量">
            </div>
        </li>
        
        <li style="color: #219d55">
            <div style="line-height:3rem;">
            获得的优惠券数量:<input id="yhqaccount" name="yhqaccount" disabled="disabled" />    
            </div>
        </li>
        <li style="color: #219d55">
            <div style="line-height:3rem;">企业代金券余额:${accountBalance }</div>
        </li>
    </ul>
    <div style="padding:0 2% ">*注:此申请一旦提交不能撤回</div>
    <div class="error_tips hide"></div>
    <div class="form-btn">
	    <div class="exit">
          <a href="javascript:" class="bg" onclick="qrdh()" id="next" style="background: #219d55;border-radius: 5px">确认兑换</a>
	    </div>
    </div>
</form>
</div>


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
	
	function zhuan(){
		var djq=$("#account").val();
		var yhq=djq*1;
		$("#yhqaccount").val(yhq);
	}
	
	function qrdh(){
		var djq=$("#account").val();
		var yhq=$("#yhqaccount").val();
		var djqye=${accountBalance };
		if(djq>djqye){
			showMsg("余额不足");
			return;
		}
		$.ajax({
			type: 'POST',
			url: '${staticFile_s}/wealth/redTransfer_n',
			dataType: 'json',
			data:"account="+ djq+"&yhqaccount="+yhq,
			success: function(data) {
					if(data==0){
						showMsg("转账成功");
						setInterval(function(){
							window.location = "${staticFile_s}/cusOrder/toweaithCenter";
						}, 2000);
					}else{
						showMsg("转账失败");
						setInterval(function(){
							window.location = "${staticFile_s}/wealth/redTransfer";
						}, 2000);
					}
				},
			error: function() {
				showMsg("转账失败");
				setInterval(function(){
					window.location = "${staticFile_s}/wealth/redTransfer";
				}, 2000);
			}
		});
	}
</script>
</body></html>