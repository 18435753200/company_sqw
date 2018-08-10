$(function(){
	// 提交订单
	//$(".next").live("click",next);
	
	//发送验证码
	//$(".yzm-btn").live("click",sendMobileCode("n"));
	time = 60;
	var intervalId;
	$("#agreementFlag").attr("checked",true);
});

function next(){
	if(check() == true){
		doSubmit();
	}
	return false;
}

function sendCheckCode(){
	if(check(1) == true){
		var submitForm = $("form[name='form_fillSigningInfo']");
		var frm_data = submitForm.serialize();
		frm_data += "&target=1";
		var req_url = $("#domain").val() + "/cusPay/bocCrossSign";
		$.ajax({
	         type: "post",
	         url:	req_url,
	         data:frm_data,
	         dataType:"text",
	         success: function (result) {
	        	 disabledBtn();
	        	 if(result != null){
	        		 $("#serNo").val(result);
	        	 }else{
	        		 alert("获取验证码失败.");
	        	 }
	         }
	     });
	}
	
	
}

function disabledBtn(){
	
	$(".yzm-btn").attr("disabled",true); 
	$(".yzm-btn").attr("onclick",""); 
	$(".yzm-btn").css("background","#cdcdcb");
	intervalId = setInterval(function(){
		if(time<=0){
			$(".yzm-btn").attr("disabled",false); 
			$(".yzm-btn").attr("onclick","sendCheckCode()");
			$(".yzm-btn").css("background","#e63233");
			time = 60;
			clearInterval(intervalId);
			$("#yzm-btn").html("重新获取验证码");
			return;
		}
		time--;
		$("#yzm-btn").html(time+'s');
	},1000);
}

function check(step){
	var isErr = true;
	var DRACC_NO = $("#DRACC_NO").val();
	var DBT_NAME = $("#DBT_NAME").val();
	var DBT_CODE = $("#DBT_CODE").val();
	var DBT_PHONE = $("#DBT_PHONE").val();
	var msgCode = $("#messageCode").val();
	
	if(check_mobile(DBT_PHONE) == false){
		message = "手机号不合法";
		isErr = false;
	}
	if(IdCardValidate(DBT_CODE) === false)  
	{
		message = "身份证输入不合法";  
		isErr = false;  
	}
	if(checkLength(DBT_NAME) > 20){
		message = "姓名超长";
		isErr = false;
	}
	if(isNumber(DRACC_NO) == false){
		message = "储蓄卡号不合法";
		isErr = false;
	}
	
	
	if(step == 2){
		if(isEmpty(msgCode) == true){
			message = "验证码不能为空";
			isErr = false;
		}
	}
	if(isEmpty(DBT_PHONE) == true){
		message = "手机号不能为空";
		isErr = false;
	}
	if(isEmpty(DBT_CODE) == true){
		message = "身份证号不能为空";
		isErr = false;
	}
	if(isEmpty(DBT_NAME) == true){
		message = "姓名不能为空";
		isErr = false;
	}
	if(isEmpty(DRACC_NO) == true){
		message = "储蓄卡号不能为空";
		isErr = false;
	}
	if($("#agreementFlag").is(':checked') == false){
		message = "请勾选支付客户协议";
		isErr = false;
	}
	
	if(isErr == false){
		showMsg(message);
		return false;
	}
	return true;
}

function doSubmit(){
	if(check(2) == true){
		var submitForm = $("form[name='form_fillSigningInfo']");
		var frm_data = submitForm.serialize();
		frm_data += "&target=1";
		var req_url = $("#domain").val() + "/cusPay/checkAuthCode";
		var orderId = $("#orderId").val();
		var act = $("#act").val();
		var pay_req_url = $("#domain").val() + "/orderPay/boccPayInfo?orderId="+orderId+"&act="+act;
		$.ajax({
	        type: "post",
	        url:	req_url,
	        data:frm_data,
	        dataType:"text",
	        success: function (result) {
	        	if(result == 'success'){
	        		window.location.href=pay_req_url;
	        	}else{
	        		showMsg("签约失败，请重试...");
	        	}
	        	
	        }
	    });
	}
}

function doAgreement(){
	windows.location.href = $("#domain").val() + "/cusPay/agreement";
}



