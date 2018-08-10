<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>请输入支付密码</title>
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<!-- <meta http-equiv="content-type" content="text/html;charset=utf8">
<meta id="viewport" name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1; user-scalable=no;"> -->
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<script type="text/javascript" src="${staticFile_s}/commons/js/zepto.min.js"></script>
   <script src="${staticFile_s}/commons/js/flexible/flexible_css.js"></script>
   <script src="${staticFile_s}/commons/js/flexible/flexible.js"></script>
<%@include file="/WEB-INF/views/commons/base.jsp" %>
<link href="${staticFile_s}/commons/css/reset.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/md5.js"></script>

<title><spring:message code="title_pay_type" /></title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
<script type="text/javascript">
function curGoBack(){
	var lastUrl = document.referrer;
	if(lastUrl.indexOf("directBuy")){
		history.go(-2);
	}else{
		goBack();
	}
}
function goBack(){var a=window.location.href;if(/#top/.test(a)){window.history.go(-2);window.location.load(window.location.href)}else{window.history.back();window.location.load(window.location.href)}}

</script>
   <style>
   body{
       font-size: 12px;
    background: #f9f9f9;}
       .section {
           width: 100%;
       }

       .top-header {
           width: 100%;
           height: 1.3rem;
           line-height: 1.3rem;
           text-align: center;
           font-size: 0.5rem;
           border:0;
           position: relative;
           background: #e60012;
    color: #fff;
       }

       .top-header span {
           width: 0.3rem;
           height: 0.56rem;
           background: url("${staticFile_s}/commons/img/gobak.png") no-repeat center center;
           background-size: contain;
           position: absolute;
           top: 0.35rem;
           left: 0.5rem;
       }

       .wrap {
           width: 100%;
       }

       .goods-nam {
           width: 100%;
           margin-top: 1rem;
           text-align: center;
           font-size: 0.4rem;
       }

       .goods-price {
           width: 100%;
           height: 1.5rem;
           line-height: 1.5rem;
           text-align: center;
           font-size: 0.8rem;
           color: #da251e;
       }

       .pwd-box {
           width: 100%;
           padding: 0 5%;
       }

       .am-password-handy {
           overflow: hidden;
           position: relative;
           margin: 0;
           width: 100%;
           padding-bottom: 1px;
       }
input{outline:none;font-size: 0}
       input[type=tel] {
           position: absolute;
           opacity: .01;
           border: 0 none !important;
           left: 0;
           outline:none;
           -webkit-appearance: none;
           -ms-box-sizing: content-box !important;
           box-sizing: content-box !important;
           outline:none;
       }

       input[type=tel] {
           -ms-box-sizing: border-box;
           box-sizing: border-box;
           display: block;
           width: 100%;
           height: 44px;
           overflow: hidden;
           padding: 0 !important;
           border: 1px solid #ddd;
           border-radius: 4px;
           background-clip: padding-box;
           font-family: Courier, monospace;
           font-size: 24px;
           line-height: 32px;
           outline:none;
       }

       .am-password-handy .am-password-handy-security {
           display: -webkit-box !important;
           display: -ms-flexbox !important;
           display: box !important;
           padding: 0;
           background-color: #fff;
       }

       .am-password-handy .am-password-handy-security, .am-password-handy input[type=password], .am-password-handy input[type=tel] {
           -ms-box-sizing: border-box;
           box-sizing: border-box;
           display: block;
           width: 100%;
           height: 44px;
           overflow: hidden;
           padding: 0 !important;
           border: 1px solid #ddd;
           border-radius: 4px;
           background-clip: padding-box;
           font-family: Courier, monospace;
           font-size: 24px;
           line-height: 32px;
       }

       .am-password-handy .am-password-handy-security li {
           -webkit-box-flex: 1;
           -ms-flex: 1;
           box-flex: 1;
           margin-right: -1px;
           border-right: 1px solid #ddd;
           overflow: hidden;
           text-align: center;
       }

       .am-password-handy .am-password-handy-security i:empty {
           margin: 16px 2px 0;
           width: 10px;
           height: 10px;
           border-radius: 10px;
           background-clip: padding-box;
           background-color: #000;
       }

       .am-password-handy .am-password-handy-security i {
           display: inline-block;
           width: 14px;
           overflow: hidden;
           line-height: 42px;
           font-style: normal;
           visibility: hidden;
       }

       .forgetpwd {
           margin: 0.4rem 0 0;
           padding: 0 5%;
           text-align: right;
       }

       .forgetpwd a {
           color: #878787;
           text-decoration: underline;
       }

       .btn-sure {
           width: 90%;
           height: 1.3rem;
           line-height: 1.3rem;
           margin: 0.8rem auto;
           text-align: center;
           background: #da251e;
           color: #fff;
           font-size: 0.5rem;
       }

       .shw-txt {
           font-size: 0.4rem;
       }
</style>
</head>
<body>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>
<div class="section">
	<input type="hidden" id="act" value="${act}"/>
    <div class="top-header" >
        请输入支付密码
       <a href="javascript:;" onclick="goBack();" ><span></span></a>
    </div>
    <div class="wrap">
        <div class="goods-nam">商品购买</div>
        <div class="goods-price"> ${payAmount}</div>
        <div class="pwd-box">
            <div class="J-keyboard am-password-handy">
                <input type="tel" class="J-pwd J-needsclick J-needsfocus " id="spwd_unencrypt" name="spwd_unencrypt"
                       maxlength="7" pattern="\d*" autocomplete="off">
                <input type="hidden" id="spwd" name="spwd" class="J-encryptpwd" value="">
                <ul class="am-password-handy-security">
                    <li><i style="visibility: hidden;"></i></li>
                    <li><i style="visibility: hidden;"></i></li>
                    <li><i style="visibility: hidden;"></i></li>
                    <li><i style="visibility: hidden;"></i></li>
                    <li><i style="visibility: hidden;"></i></li>
                    <li><i style="visibility: hidden;"></i></li>
                    <li><i style="visibility: hidden;"></i></li>
                </ul>
            </div>
        </div>
        <div class="forgetpwd"><a href="<%=path %>/trade/toRetrieve?uri=${path}/orderPay/tradePwdPage?orderId=${orderId}&act=${act}">忘记密码?</a></div>
        <div class="btn-sure">确&nbsp;&nbsp;&nbsp;&nbsp;认</div>
    </div>
</div>
<%@include file="/WEB-INF/views/commons/footer.jsp" %>


<script type="text/javascript">
$(function () {

	$("#spwd_unencrypt").on('input', function () {
		var $this = $(this);
		if($this.val().length){
			if (!/^[0-9]*$/.test($this.val()[$this.val().length - 1])) {
				$this.val($this.val().substr(0,$this.val().length - 1));
				return false;
			}
		}
		var valLength = $this.val().length;
		var lis = $('.am-password-handy-security li');
		lis.find('i').css({"visibility": "hidden"});
		for (var i = 0; i < valLength; i++) {
			$(lis[i]).find('i').css({"visibility": "visible"});
		}
	});


    $(".btn-sure").on('click', function () {
    	var flag = $(this).hasClass('btn-click');
    	var pwd = $('#spwd_unencrypt').val();
    	pwd = hex_md5(pwd);
    	var orderId = "${orderId}";
    	var hqPay = $("#act").val();
		$.ajax({
			type : "post",
			url :$("#path").val()+"/orderPay/checkTradePwd",
			data:{"pwd":pwd,"act":hqPay,"orderId":orderId},
			dataType : "json",
			success : function(data){
				var showMessage = "";
				switch(data.retCode){
				case "0":	// 支付密码不正确，你还可以输入1次
			        createDom({
			            type: 2,
			            msg: data.retInfo,
			            timeout: 1500,
			            callback: function () {
			            	$("#spwd_unencrypt").val("");
			            	$("#spwd").val("");
							var lis = $('.am-password-handy-security li');
							lis.find('i').css({"visibility": "hidden"});
			            }
			        });	
					break;
				case "1":	 
					var url = $("#path").val()+"/orderPay/hqPayInfo?orderId="+orderId+"&act="+hqPay+"&pwd="+pwd;
					var confirmTag = "<form name='billPayment-form' action=" + url + " method='post'></form>";
					$("body").find("form[name='billPayment-form']").remove();
					$("body").append(confirmTag);
					$("form[name='billPayment-form']").submit(); 
					break;
				case "2":	
					//showMessage = "首次购物,请先购买激活区商品哦！";
/* 			        createDom({
			            type: 2,
			            msg: data.retInfo,
			            timeout: 1500
			        });		 */
			        location.href="${customer_domain}/trade/toSetting?returnUrl=/orderPay/tradePwdPage&orderId=${orderId}&act=${act}";
					break;
				
				case "3":	 
					if(!flag){	
				        createDom({
				            type: 2,
				            msg: data.retInfo,
				            timeout: 1500
				        });	
				        inptForbidden();
					}
					break;
				case "4":	 
			        createDom({
			            type: 2,
			            msg: data.retInfo,
			            timeout: 1500
			        });		
					break;
				}
			}	
	    });
    });
});

//禁止输入密码
function inptForbidden(){
	$('#spwd_unencrypt').attr({"readonly":"readonly"});
	$('.am-password-handy-security').css({"background":"#ccc"});
	$('.btn-sure').addClass("btn-click");
}

//解除禁止输入
function inptUnForbidden(){
	$('#spwd_unencrypt').removeAttr("readonly");
	$('.am-password-handy-security').css({"background":"#fff"});
}

function createDom(paramsObj) {
    var obj = paramsObj;
    var $body = $('body');
    switch (obj.type) {
        case 2:
            var objMsgFunc = {};
            var htmlMsg = [];
            htmlMsg.push('<div class="rl-dialog-wp" style="display: block;position: fixed;top: 0;left: 0;background: rgba(0, 0, 0, 0.7);width: 100%;height: 100%;text-align: center;z-index: 9999;">');
            htmlMsg.push('<div class="shw-msg" style="display: block;width: 80%;padding: 0 10%;text-align: center;position: absolute;top: 50%;left: 50%;-webkit-transform: translate(-50%, -50%);-moz-transform: translate(-50%, -50%);-ms-transform: translate(-50%, -50%);-o-transform: translate(-50%, -50%);transform: translate(-50%, -50%);">');
            htmlMsg.push('<div class="shw-txt" style="display: inline-block;padding: 9%;background: #fff;color: #000;border-radius: 5px;word-break: break-all;">' + obj.msg + '</div>');
            htmlMsg.push('</div>');
            htmlMsg.push('</div>');
            $body.append(htmlMsg.join(''));

            if (!(obj.callback && Object.prototype.toString.call(obj.callback).indexOf('Function') > -1)) {
                obj.callback = function () {
                };
            }
            obj.timeout = obj.timeout ? obj.timeout : 1500;

            objMsgFunc.timer = void 0;

            objMsgFunc.hideDialog = function (timeoutTmp, cbkTmp) {
                objMsgFunc.timer = setTimeout(function () {
                    objMsgFunc.removeDialog(cbkTmp);
                }, timeoutTmp);
            };

            objMsgFunc.bindRemoveDialogEvent = function (cbkTmp) {
                $('.rl-dialog-wp').on('tap', function () {
                    objMsgFunc.removeDialog(cbkTmp);
                });
            };

            objMsgFunc.removeDialog = function (cbkTmp) {
                $(".rl-dialog-wp").remove();
                clearTimeout(objMsgFunc.timer);
                objMsgFunc.timer = null;
                cbkTmp();
            };

            objMsgFunc.hideDialog(obj.timeout, obj.callback);
            //objMsgFunc.bindRemoveDialogEvent(obj.callback);
            break;
    }
}
</script>
</body>
</html>