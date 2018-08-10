$(function(){
	// 提交反馈
	$(".sub-suggest").live("click",submitSuggest);
});

function submitSuggest(){
	// 检查反馈信息
	if(!checkSubmitSuggest()){
		return;
	}
	
	// 提交反馈信息
	doSubmitSuggest();
}

function checkSubmitSuggest(){

	var suggestForm = $("form[name='suggest-form']");
	// 反馈类型
	var suggestType = suggestForm.find("select[name='suggestType']");
	if(isEmpty(suggestType.val())){
		showMsg("请选择反馈类型");
		suggestType.focus();
		return false;
	}
	
	// 反馈内容
	var suggestContent = suggestForm.find("textarea[name='suggestContent']");
	if(isEmpty(suggestContent.val())){
		showMsg("请填写反馈内容");
		return false;
	}
	if(checkLength(suggestContent.val()) > 200){
		showMsg("反馈内容为1-100个字");
		return false;
	}
	
	// 反馈人联系方式，手机号或邮箱
	var suggestContactWay = suggestForm.find("input[name='suggestContactWay']");
	if(isEmpty(suggestContactWay.val())){
		showMsg("请输入联系方式");
		suggestContactWay.focus();
		return false;
	}
	if(!check_email(suggestContactWay.val()) && !check_mobile(suggestContactWay.val())){
		showMsg("联系方式只能为手机号或邮箱");
		suggestContactWay.focus();
		return false;
	}
	
	return true;
}

function doSubmitSuggest(){
	var suggestForm = $("form[name='suggest-form']");
	var suggestData = suggestForm.serialize();
	var url = $("#path").val()+"/suggest/add";
	var year = $("#year1").val();
	var month = $("#month1").val();
	var day = $("#day1").val();
	var suggestTime = year+"-"+month+"-"+day;
	
	$.ajax({
		type: 'post',
		url:url,
		data:suggestData+"&suggestTime="+suggestTime,
		async:false,
		dataType: 'json',
		success: function(result){
			if(!checkResult4Suggest(result)){
				return;
			}
			if(result == "200"){
				showMsg("感谢您的反馈！");
				setInterval(function(){
					location.href = $("#path").val()+"/customer/toMy";
				}, 2000);
			}else{
				showMsg("系统繁忙，请稍候重试！");
				
			}
		},error:function(result){
			showMsg("系统繁忙，请稍候重试！");
		}
	});
}

function checkResult4Suggest(result){
	var path = $("#path").val();
	var loginUrl = path+"/customer/toLogin";
	var noErrors = true;
	if(result.status == "needLogin"){
		location.href=loginUrl;
		noErrors = false;
	}
	if(result.errorMessage && result.errorMessage.length > 0){
		noErrors = false;
		$.dialog({
            content : '系统繁忙，请稍后重试',
            title : '众聚猫提示',
            time: 2000,
		});
	}
	
	return noErrors;
}