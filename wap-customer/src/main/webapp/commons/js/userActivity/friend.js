function submitFriend() {
	var mobile = $("#mobile").val();
	if(!beforeSub(mobile)){
		return ;
	}
	location.href = $("#path").val() + "/customerActivity/toDownLoad?mobile=" + mobile;
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
 

 

 