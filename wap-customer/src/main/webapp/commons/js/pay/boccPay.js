var path;

$(document).ready(function(){
	// 初始化
	init();
	
	// 关闭选择账号弹框
	$(".bgnone").live("click",closebgone);
	
	// 使用其他账号
	$(".hm_add").live("click",useNewAccount);
	
	// 选择某个卡号来支付
	$(".hm_num").live("click",doChooseAccount);
	
	$("form[id='form_payInfo']").submit(beforeSubmit);
	
	time = 60;
	var intervalId;
	
});

function beforeSubmit(){
	
	var messageCode = $("#messageCode").val();
	
	if(isEmpty(messageCode)){
		showMsg("请输入验证码");
		return false;
	}
	
	return true;
}

function doChooseAccount(){
	
}

function init(){
	path = $("#domain").val();
}

function useNewAccount(){
	location.href=path+"/cusPay/toBocCrossSign";
}

function closebgone(){
	$(".bgnone-w").remove();
}

function next(){
	$("form[name='form_payInfo']").submit();
	
	/*if(check() == true){
		doSubmit();
	}*/
	return false;
}

function sendMobileCode(){
	var req_url = $("#domain").val() + "/orderPay/sendMobileCode";
	$.ajax({
		type: 	"post",
        url:	req_url,
        dataType:"text",
        success: function (result) {
        	 disabledBtn();
        	 if(result.indexOf("success") != -1){
        		 //showMsg("msgCode-->" + result);
        	 }else if(result.indexOf("codeNotLoseEfficacy") != -1){
        		 showMsg("发送短信验证码过于频繁.");
        	 }else{
        		 showMsg("获取验证码失败.");
        	 }
         }
     });
	
	
	
}

function disabledBtn(){
	
	$(".yzm-btn").attr("disabled",true); 
	$(".yzm-btn").attr("onclick",""); 
	$(".yzm-btn").css("background","#cdcdcb");
	intervalId = setInterval(function(){
		if(time<=0){
			$(".yzm-btn").attr("disabled",false); 
			$(".yzm-btn").attr("onclick","sendMobileCode()");
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



function doSubmit(){
	$("form[name='form_payInfo']").submit();
}

function chooseAccount(){
	var req_url = $("#domain").val()+"/cusPay/chooseAccount";
	$.ajax({
		type:"post",
		url:req_url,
		dataType:"json",
		success:function (result){
			var chooseAccountTag = '<div class="bgnone-w"><div class="bgnone"></div>';
			chooseAccountTag += '<div class="bomb-box">';
			if(result != null && result != undefined && result != ''){
				chooseAccountTag += '<div class="kahao_list">';
				chooseAccountTag += '<div class="hm_num">'+result[0].draccNo+'</div>';
				if(result.length > 1){
					chooseAccountTag += '<span class="hm-down" style="display:none;"></span><span class="hm-up"></span>';
					chooseAccountTag += '<ul class="hm_numli">';
					for ( var i = 1; i < result.length; i++) {
						var bocCrossBorder = result[i];
						chooseAccountTag += '<li>'+bocCrossBorder.draccNo+'</li>';
					}
					chooseAccountTag += '</ul>';
				}
				chooseAccountTag += '</div>';
			}
			chooseAccountTag += '<div class="kahao_list"><div class="hm_add">使用其他账号<span class="add_btn"></span></div></div>';
			chooseAccountTag += '</div></div>';
		
			$("body").append(chooseAccountTag);
		}
	
	});
}



