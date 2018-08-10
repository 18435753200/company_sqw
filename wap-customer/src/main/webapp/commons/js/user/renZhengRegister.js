var doing=false;
var time;
function createDom(paramsObj) {
    var obj = paramsObj;
    var $body = $('body');
    switch (obj.type) {
        case 4:
            /*
             type:               固定值4          必填项
             msg:                弹出的消息        必填项
             cancelCallback:     取消按钮回调      非必填项
             sureCallback:       确定按钮回调      非必填项
             */
            var objCancelSureFunc = {};
            var htmlSureCancel = [];
            htmlSureCancel.push('<div class="rl-dialog-wp" style="display: block;position: fixed;top: 0;left: 0;background: rgba(0, 0, 0, 0.5);width: 100%;height: 100%;text-align: center;z-index: 9999;">');
            htmlSureCancel.push('<div class="shw-msg" style="display: block;width: 80%;padding: 0 0;text-align: center;position: absolute;top: 50%;left: 50%;-webkit-transform: translate(-50%, -50%);-moz-transform: translate(-50%, -50%);-ms-transform: translate(-50%, -50%);-o-transform: translate(-50%, -50%);transform: translate(-50%, -50%);">');
            htmlSureCancel.push('<div class="sure-txt" style="padding: 8% 5%;background: #fff;color: #000;border-bottom: 1px solid #DCDCDF;border-radius: 15px 15px 0 0;word-break: break-all;">' + obj.msg + '</div>');
            htmlSureCancel.push('<div class="cancel-btn" style="box-sizing: border-box;width: 50%;padding: 3.5% 0 3.5% 5%;float: left;background: #fff;color: #057CFF;font-weight: bolder;border-radius: 0 0 0 15px;border-right: 1px solid #DCDCDF;-webkit-user-select: none;-moz-user-select: none;-ms-user-select: none;user-select: none; cursor:pointer;">立即购买</div>');
            htmlSureCancel.push('<div class="sure-btn" style="box-sizing: border-box;width: 50%;padding: 3.5% 5% 3.5% 0;float: right;background: #fff;color: #057CFF;font-weight: bolder;border-radius: 0 0 15px 0;-webkit-user-select: none;-moz-user-select: none;-ms-user-select: none;user-select: none; cursor:pointer;">再逛逛</div>');
            htmlSureCancel.push('</div>');
            htmlSureCancel.push('</div>');
            $body.append(htmlSureCancel.join(''));

            if (!(obj.cancelCallback && Object.prototype.toString.call(obj.cancelCallback).indexOf('Function') > -1)) {
                obj.cancelCallback = function () {
                };
            }

            if (!(obj.sureCallback && Object.prototype.toString.call(obj.sureCallback).indexOf('Function') > -1)) {
                obj.sureCallback = function () {
                };
            }

            objCancelSureFunc.btnCancelTapEvent = function (ckTmp) {
                $body.find('.rl-dialog-wp .cancel-btn').on('mousedown', function () {
                    $(this).css({"background": "#DCDCDF"});
                    return false;
                }).on('mouseup', function () {
                    $(".rl-dialog-wp").remove();
                    ckTmp();
                    return false;
                });
            };

            objCancelSureFunc.btnSureTapEvent = function (ckTmp) {
                $body.find('.rl-dialog-wp .sure-btn').on('mousedown', function () {
                    $(this).css({"background": "#DCDCDF"});
                    return false;
                }).on('mouseup', function () {
                    $(".rl-dialog-wp").remove();
                    ckTmp();
                    return false;
                });
            };

            objCancelSureFunc.btnCancelTapEvent(obj.cancelCallback);
            objCancelSureFunc.btnSureTapEvent(obj.sureCallback);
            break;
    }
}
$(function() {
	$("#codeId2").css("display", "none");
});

var chcek = true;

/**
 * 校验用户名验证码
 */
function register(){

	if (!chcek){
		return;
	}

    chcek = false;

	var captcha=$("#captcha").val();
	var openId=$("#openId").val();
	var mobile = $("#mobile").val();
	var msgReqCode = $("#mobileCodeValue").val();
	var url =$("#url").val();
	if(isEmpty(captcha)){
		showErrorMsg("图形验证码不能为空");
		return ;
	}
	if(!beforeSub(mobile, msgReqCode)){
		return ;
	}
	if(isEmpty(msgReqCode)){
		showErrorMsg("手机验证码不能为空");
		return ;
	}
	var validData="mobile="+mobile+"&msgReqCode="+msgReqCode+"&captcha=" + captcha+"&openId="+openId;
	$.ajax({
	   dataType:"text",
	   type: "post",
	   url: $("#path").val()+"/customer/renZhengValidRegCode",
	   data: validData,
	   async:true,
	   success: function(result){
		    switch(result){
		    case '200':
	    		var _dataType="json";
	    		var _type="POST";
	    		var  _url=$("#path").val()+"/customer/renZhengsaveCustomer";
	    		var _async =true;
	    		var _data="mobile="+mobile+"&pass=738F80DC8C76EFBD0E2687B5DAD4E19E"+"&openId="+openId;
	    		$.ajax({
	    			   dataType:_dataType,
	    			   type: _type,
	    			   url: _url,
	    			   data: _data,
	    			   async:_async,
	    			   success: function(data){
	    				   window.location.href =data;
	    				   },
	    				error:function(){
	    					showErrorMsg("系统繁忙，请稍后重试");
	    			   }
	    			});   
		    	break;
		    case '505':
		    	showErrorMsg("用户已经存在");
		    	break;
		    case '501':
		    	showErrorMsg("用户名和验证码不匹配");
		    	break;
		    case '502':
		    	showErrorMsg("图形验证码错误");
		    	break;
		    case '500':
		    	showErrorMsg("手机验证码错误");
		    	break;
		    }
	   },
	   error:function(){
			showErrorMsg("系统繁忙，请稍后重试");
	   }
	});
}
/**
 * 获取验证码
 */
function getCode(){
	if(doing) return;
	doing = true;
	var openId=$("#openId").val();
	var mobile = $("#mobile").val();
	var captcha=$("#captcha").val();
	if(!beforeSub(mobile)){
		doing = false;
		return ;
	}
	if(isEmpty(captcha)){
		showErrorMsg("图形验证码不能为空");
		doing = false;
		return ;
	}
	
	var _dataType = "text";
	var _type = "POST";
	var _url = $("#path").val()+"/customer/toRegGetCode";
	var _async = true;
	var _data = "mobile="+mobile + "&captcha=" + captcha+"&openId="+openId+"&renzheng="+1;
	$.ajax({
		dataType : _dataType,
		type : _type,
		url : _url,
		data : _data,
		async : _async,
		success : function(res) {
			if (res == "success") {
				time = 60;
				
				$("#mobileCode").val("重新发送("+ time +"秒)" );
				$("#mobileCode").attr("disabled",true);
				window.setTimeout(callbackCode, 1000); // 1秒
				$("#J-tips").show();
			}else if (res == "mobileExist") {
				time = 60;
				$("#mobileCode").val("重新发送("+ time +"秒)" );
				$("#mobileCode").attr("disabled",true);
				window.setTimeout(callbackCode, 1000); // 1秒
				$("#J-tips").show();
			} else if (res == "repeatSend") {
				showErrorMsg("请不要重复发送");
			} else if(res == "captchaerror") {
				$("#captchaImage").click();
				showErrorMsg("图形验证码错误");
			} else if(res == "ipcountFull") {
				showErrorMsg("您注册次数过多，请明天再注册");
			} else {
				showErrorMsg("发送失败，请稍后重试");
			}
			
			doing = false;
		},
		error : function() {
			showErrorMsg("网络连接超时，请您稍后重试");
			doing = false;
		}
	});

}




function callbackCode() {
	time = time - 1;
	$("#mobileCode").val("重新发送(" + time +"秒)" );
	if (time == 0) {
		$("#mobileCode").attr("disabled", null);
		$("#mobileCode").val("重新发送");
		return;
	}
	setTimeout(callbackCode, 1000);
}


/**
 * 校验用户名验证码
 */
function subReg(){

	var mobile = $("#J_forgotPassAccount").val();
	var msgCode = $("#J_forgotPassCode").val();
	var origin = $("#origin").val();
	var str = "";
	if(!beforeSub(mobile, msgCode)){
		return ;
	}
	if(isEmpty(msgCode)){
		showErrorMsg("验证码不能为空");
		return ;
	}
	if(!isempty(origin)){
		str = "<input type='hidden' name='origin' value='"+origin+"'/>";
	}else{
		origin="";
		str = "<input type='hidden' name='origin' value='"+origin+"'/>";
	}
	$.ajax({
	   dataType:"text",
	   type: "post",
	   url: $("#path").val()+"/customer/validRegCode",
	   data: $("#formSubmit").serialize(),
	   async:true,
	   success: function(result){
		    switch(result){
		    case '200':
		    	$("form[name='setPasswordForm']").remove();
		    	var toSetPasswordTag = "<form method='post' action='"+$("#path").val()+"/customer/toSetPassword' name='setPasswordForm'>";
		    	toSetPasswordTag += "<input type='hidden' name='mobile' value='"+mobile+"'/>";
		    	toSetPasswordTag += str;
		    	toSetPasswordTag += "</form>";
		    	$("body").append(toSetPasswordTag);
		    	$("form[name='setPasswordForm']").submit();
		    	break;
		    case '505':
		    	showErrorMsg("用户已经存在");
		    	break;
		    case '501':
		    	showErrorMsg("用户名和验证码不匹配");
		    	break;
		    case '500':
		    	showErrorMsg("验证码错误");
		    	break;
		    }
	   },
	   error:function(){
			showErrorMsg("系统繁忙，请稍后重试");
	   }
	});
}
/**
 *  提交之前检验
 * @param mobile
 * @param code
 */
 function beforeSub(mobile,code){
	if ( isEmpty(mobile) ) {
		showErrorMsg("请输入您的手机号");
		return false;
	}
	//目前只验证手机号
	var res = verifyPhoneNumber(mobile);
	if(res != "ok"){
		showErrorMsg(res);
		return false;
	}else {
		$(".error_tips").addClass("hide");
		return true;
	}
}

function showErrorMsg(str) {
	//$(".error_tips").removeClass("hide");
	//$(".error_tips").html(str);
	$.dialog({
        content : str,
        title : '众聚猫提示',
        time: 1000,
	});
	return;
}

 