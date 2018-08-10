var time = 0;

$(function() {
	// 将验证提示异常
	$("#codeId2").css("display", "none");

	var idCard = $("#idCard").val();
	// 已认证不显示保存按钮
	if (idCard != "" && idCard != null) {
		$("#save").css("display", "none");
		// 输入修改验证信息时，显示保存按钮
		$("#realName").keydown(function() {
			$("#save").css("display", "block");
		});

		$("#idCard").keydown(function() {
			$("#save").css("display", "block");
		});
	}
	// 手机验证
	$("#mobileCheck").live("click", function() {
		getCodePas();
	});

	// 修改昵称
	$("#_nickName").live("click ", function() {
		console.log($("#path").val() + "/cusInfo/changeNickName")
		location.href = $("#path").val() + "/cusInfo/changeNickName";
	});
	// 选择性别
	$("#gender").live("change", function() {
		var g = $(this);
		g.attr("selected", "selected").siblings().attr("selected", null);
		var _gender = {
			"gender" : g.val()
		};
		updateInfo(_gender);
	});

	// 选择政治面貌
	$("#politicalStatus").live("change", function() {
		var g = $(this);
		g.attr("selected", "selected").siblings().attr("selected", null);
		var _politicalStatus = {
			"politicalStatus" : g.val()
		};
		updateInfo(_politicalStatus);
	});

	// 生日
	$("#birth").live("change", function() {
		var startTime = $("#birth").val();
		var mydate = new Date();
		var endTime = mydate.toLocaleDateString(); // 获取当前日期
		if (!checkEndTime(startTime, endTime)) {
			showErrorMsg("生日时间不能大于当前时间");
			return;
		}
		;

		startTime = startTime.split("-").join("/");
		var birthData = {
			"birth" : startTime
		};
		updateInfo(birthData);
	});
});
// 比较日期
function checkEndTime(begin, over) {
	var start = new Date(begin.replace("-", "/").replace("-", "/"));
	var end = new Date(over.replace("-", "/").replace("-", "/"));
	if (end < start) {
		return false;
	} else {
		return true;
	}

}
// 校验邮箱
function checkEmail() {
	var em = $("#_email").val();
	if (isEmpty(em)) {
		showErrorMsg("邮箱不能为空");
		return;
	}
	if (!isEmail(em)) {
		showErrorMsg("邮箱格式不正确");
		return;
	}
	var emailData = {
		"email" : em
	};
	updateInfo(emailData);
}
// 校验微信
function checkWeixin() {
	var em = $("#_weixin").val();
	if (isEmpty(em)) {
		showErrorMsg("微信不能为空");
		return;
	}
	// if (!isEmail(em)) {
	// showErrorMsg("邮箱格式不正确");
	// return;
	// }
	var weixinData = {
		"weixin" : em
	};
	updateInfo(weixinData);
}
// 校验身份证号
function checkIdCard() {
	var em = $("#_idCard").val();
	if (isEmpty(em)) {
		showErrorMsg("身份证号不能为空");
		return;
	}
	if (!strIdCard(em)) {
		showErrorMsg("身份证号不正确");
		return;
	}
	var idCardData = {
		"idCard" : em
	};
	updateInfo(idCardData);
}
// 校验学历
function checkEducational() {
	var em = $("#_educational").val();
	if (isEmpty(em)) {
		showErrorMsg("学历不能为空");
		return;
	}
	// if (!strIdCard(em)) {
	// showErrorMsg("身份证号不正确");
	// return;
	// }
	var educationalData = {
		"educational" : em
	};
	updateInfo(educationalData);
}
// 返回我的账户页面
function goBackInfo() {
	location.href = $("#path").val() + "/cusInfo/cusInfo";
}

// 校验昵称
function checkNickName() {
	var nickName = $("#nickName").val();
	if (isEmpty(nickName)) {
		showErrorMsg("请输入正确的昵称格式");
		return false;
	}
	var nickNameLength = strlen(nickName);
	if (nickNameLength < 1 || nickNameLength > 10) {
		showErrorMsg("昵称长度为1-10个字符，请输入正确的昵称格式");
		return false;
	}
	if (nickName.search(/^([\u4e00-\u9fa5a-zA-Z0-9])+$/) == -1) {
		showErrorMsg("昵称格式错误，请重新输入");
		return false;
	}
	var _info = {
		"nickName" : nickName
	};
	updateInfo(_info);
}
// 更新个人信息
function updateInfo(info) {

	var _dataType = "text";
	var _type = "POST";
	var _url = $("#path").val() + "/cusInfo/updateCusInfo";
	var _async = true;
	var _data = info;
	$.ajax({
		dataType : _dataType,
		type : _type,
		url : _url,
		data : _data,
		async : _async,
		success : function(res) {
			if (res == "updateSuccess") {
				var redirectUrl = $("#path").val() + "/cusInfo/cusInfo";
				okBT("更新个人信息成功", redirectUrl);
				return;
			} else if (res.indexOf("updateErrorMsg") != -1) {
				var msgs = res.split(":");
				showErrorMsg(msgs[1]);
			} else if (res == "userNameNull" || res == "userNotExist") {
				window.location = $("#path").val() + "/customer/toLogin";
			} else if (res == "mobileIsNull") {
				showErrorMsg("用户手机号不存在");
			} else if (res == "repeatSend") {
				showErrorMsg("请不要重复发送");
			}
		},
		error : function() {
			showErrorMsg("网络连接超时，请您稍后重试");
		}
	});
}
/**
 * 验证码回调
 */
function callback() {
	$("#checkCode").attr("disabled", true);
	time = time - 1;
	$("#checkCode").text(time + "秒");
	if (time == 0) {
		$("#checkCode").attr("disabled", null);
		$("#checkCode").text("重新发送");
		return;
	}
	setTimeout(callback, 1000);
}
/**
 * 获取验证码
 */
function getCodePas() {
	var textVal = $("#checkCode").text();
	if (textVal != null && textVal != undefined && textVal != "重新发送") {
		return;
	}
	var username = $("#mobile").val();
	var checkFrom = $("#faa").val();

	var _dataType = "text";
	var _type = "POST";
	var _url = $("#path").val() + "/customer/toGetCode";
	var _async = true;
	var _data = "username=" + username + "&" + "from=" + checkFrom;
	$.ajax({
		dataType : _dataType,
		type : _type,
		url : _url,
		data : _data,
		async : _async,
		success : function(res) {
			if (res == "success") {
				time = 120;
				$("#checkCode").text(time + "秒");
				$("#checkCode").attr("disabled", true);
				window.setTimeout(callback, 1000);
				showErrorMsg("验证码已成功发送至手机，请注意查收");
			} else if (res == "userNameNull") {
				showErrorMsg("用户名不能为空");
			} else if (res == "userNotExist") {
				showErrorMsg("您的手机号还没有注册，请先注册");
			} else if (res == "mobileIsNull") {
				showErrorMsg("用户手机号不存在");
			} else if (res == "repeatSend") {
				showErrorMsg("请不要重复发送");
			} else {
				showErrorMsg("发送失败，请稍后重试");
			}
		},
		error : function() {
			showErrorMsg("网络连接超时，请您稍后重试");
		}
	});
}

/**
 * 去修改收手机号页面
 */
function modified() {
	var number = $("#mobile").val();
	var veriCode = $("#v_code").val();
	var fData = $("#fro").val();
	if (isEmpty(veriCode)) {
		showErrorMsg("您还没有输入验证码");
		return;
	}
	if (isNaN(veriCode) || veriCode.length > 10) {
		showErrorMsg("验证码错误或者已经失效");
		return;
	}
	var validData = "username=" + number + "&" + "msgCode=" + veriCode + "&"
			+ "from=" + fData;
	$.ajax({
		dataType : "text",
		type : "post",
		url : $("#path").val() + "/customer/validCode",
		data : validData,
		async : true,
		success : function(result) {
			switch (result) {
			case 'success':
				location.href = $("#path").val()
						+ "/cusInfo/reToUpdateCusMobile";
				break;
			case '505':
				showErrorMsg("验证码已过期，请重新获取");
				break;
			case '501':
				showErrorMsg("验证码输入不正确");
				break;
			case '500':
				showErrorMsg("验证码输入不正确");
				break;
			case 'validNull':
				showErrorMsg("用户名和验证码不符");
				break;
			}
		},
		error : function() {
			showErrorMsg("系统繁忙，请稍后重试");
		}
	});

}
/**
 * 从新认证
 */
/*
 * function reveryAgin() { $("#verified").css("display", "block");
 * $("#reveryId").css("display", "none"); }
 */

/**
 * 提交身份验证
 */
function subVerify() {
	var realName = $("#realName").val();
	var idCard = $("#idCard").val();
	var res1 = strLength(realName, 8, 2, "输入的姓名必须为2-8个字");
	if (res1 != "ok") {
		showErrorMsg(res1);
		return;
	}
	// var res2=strLength(idCard, 18, 15, "输入的身份证号必须为15-18个字");

	if (isEmpty(idCard)) {

	} else {
		var validIdCarRes = IdCardValidate(idCard);
		if (!validIdCarRes) {
			showErrorMsg("请输入正确的身份证号");
			return;
		}
	}
	var _dataType = "text";
	var _type = "POST";
	var _url = $("#path").val() + "/cusInfo/verify";
	var _async = true;
	var _data = $("#veryForm").serialize();
	$.ajax({
		dataType : _dataType,
		type : _type,
		url : _url,
		data : _data,
		async : _async,
		success : function(data) {
			if (data == "ok") {
				// 个人中心验证成功
				var redirectUrl = $("#path").val() + "/customer/toMy";
				okBT("实名验证成功", redirectUrl);
				return;
			} else if (data.indexOf("/cart/settlement") != -1) {
				// 购物车实名验证回跳路径
				okBT("验证成功", data);
				return;
			} else if (data.indexOf("/cart/index") != -1) {
				// 购物车实名验证回跳路径
				okBT("验证成功", data);
				return;
			} else if (data == "realError") {
				showErrorMsg("真实姓名不能为空");
				return;
			} else if (data == "idError") {
				showErrorMsg("身份证号不能为空");
				return;
			} else {
				showErrorMsg("请确认输入的姓名和身份证相同");
				return;
			}
		},
		error : function() {
			showErrorMsg("网络连接超时");
		}
	});
}

/**
 * 提交信息修改
 */
/*
 * function subUpdate() { var subOrnot = beforeSubUpdate(); if (!subOrnot) {
 * return; } var _dataType = "text"; var _type = "POST"; var _url =
 * $("#path").val() + "/cusInfo/updateCusInfo"; var _async = true; var _data =
 * $("#updateForm").serialize(); $.ajax({ dataType : _dataType, type : _type,
 * url : _url, data : _data, async : _async, success : function(res) { if (res ==
 * "updateSuccess") { var redirectUrl = $("#path").val() + "/customer/toMy";
 * okBT("更新个人信息成功", redirectUrl); return; } else if
 * (res.indexOf("updateErrorMsg") != -1) { var msgs = res.split(":");
 * showErrorMsg(msgs[1]); } else if (res == "userNameNull" || res ==
 * "userNotExist") { window.location = $("#path").val() + "/customer/toLogin"; }
 * else if (res == "mobileIsNull") { showErrorMsg("用户手机号不存在"); } else if (res ==
 * "repeatSend") { showErrorMsg("请不要重复发送"); }
 * 
 * 
 * var msg = ""; switch(res){ case '500': case '501': msg = "验证码输入不正确"; break;
 * case '505': msg = "验证码已过期，请重新获取。"; break; }
 * 
 * showErrorMsg(msg); }, error : function() { showErrorMsg("网络连接超时，请您稍后重试"); }
 * }); }
 */

/**
 * 修改用户手机
 */
function updateMobile() {
	var newMobile = $("#newMobile").val();
	var validCode = $("#validCode").val();
	if (isEmpty(newMobile)) {
		showErrorMsg("手机号不能为空");
		return;
	}
	if (isEmpty(validCode)) {
		showErrorMsg("验证码不能为空");
		return;
	}
	var _dataType = "text";
	var _type = "POST";
	var _url = $("#path").val() + "/cusInfo/updateCusMobile";
	var _async = true;
	var _data = "newMobile=" + newMobile + "&validCode=" + validCode;
	$.ajax({
		dataType : _dataType,
		type : _type,
		url : _url,
		data : _data,
		async : _async,
		success : function(res) {
			if (res == "updateSuccess") {
				var redirectUrl = $("#path").val() + "/customer/toMy";
				okBT("更新个人信息成功", redirectUrl);
				return;
			} else if (res.indexOf("updateErrorMsg") != -1) {
				var msgs = res.split(":");
				showErrorMsg(msgs[1]);
			} else if (res == "userNameNull" || res == "userNotExist") {
				window.location = $("#path").val() + "/customer/toLogin";
			} else if (res == "mobileIsNull") {
				showErrorMsg("用户手机号不存在");
			} else if (res == "repeatSend") {
				showErrorMsg("请不要重复发送");
			}

			switch (res) {
			case '500':
			case '501':
				showErrorMsg("验证码错误，请重新输入");
				break;
			case '505':
				showErrorMsg("验证码已过期，请重新获取。");
				break;
			case 'validNull':
				showErrorMsg("用户名和验证码不符");
				break;
			}

		},
		error : function() {
			showErrorMsg("网络连接超时，请您稍后重试");
		}
	});
}
/**
 * 用户个人信息修改提交前进行的检验
 */
/*
 * function beforeSubUpdate() { // var msgCode=$("#validCode").val(); var
 * userName = $("#userName").val(); var nickName = $("#nickName").val(); var
 * mobile = $("#mobile").val(); var email = $("#email").val(); // var
 * gender=$("#gender").val(); // var msgCode=$("#validCode").val();
 * 
 * 
 * if(isEmpty(msgCode)){ showErrorMsg("验证码不能为空"); return false; }
 * if(isNaN(msgCode)){ showErrorMsg("验证码输入不正确"); return false; }
 * 
 * if (isEmpty(userName)) { showErrorMsg("用户名不能为空"); return false; } if
 * (isEmpty(nickName)) { showErrorMsg("请输入正确的昵称格式"); return false; } // var
 * nickNameLength = nickName.length; var nickNameLength = strlen(nickName); if
 * (nickNameLength < 4 || nickNameLength > 20) {
 * showErrorMsg("昵称长度为4-20个字符，请输入正确的昵称格式"); return false; } if
 * (!idNickName(nickName)) { showErrorMsg("昵称格式错误，请重新输入"); return false; } if
 * (isEmpty(mobile)) { showErrorMsg("手机号不能为空"); return false; } var resphon =
 * verifyPhoneNumber(mobile); if (resphon != "ok") { showErrorMsg(resphon);
 * return false; } if (isEmpty(email)) { showErrorMsg("邮箱不能为空"); return false; }
 * if (!isEmail(email)) { showErrorMsg("邮箱格式不正确"); return false; }
 * 
 * return true; }
 */
/**
 * 判断字符串长度，英文为1个字符，汉字为两个字符。
 * 
 * @param str
 * @returns {Number}
 */
function strlen(str) {
	var len = 0;
	for (var i = 0; i < str.length; i++) {
		var c = str.charCodeAt(i);
		// 单字节加1
		if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
			len++;
		} else {
			len += 2;
		}
	}
	return len;
}
/**
 * 修改手机获取验证码
 */
function getCodeMobile() {
	var username = $("#userName").val();
	var newMobile = $("#newMobile").val();
	if (isEmpty(username)) {
		showErrorMsg("用户名不能为空");
		return;
	} else {
		$(".error_tips").addClass("hide");
	}
	if (isEmpty(newMobile)) {
		showErrorMsg("手机号不能为空");
		return;
	} else {
		$(".error_tips").addClass("hide");
	}
	if (verifyPhoneNumber(newMobile) != ("ok")) {
		showErrorMsg("手机号格式不正确");
		return;
	}
	var _dataType = "text";
	var _type = "POST";
	var _url = $("#path").val() + "/cusInfo/getCusInfoCodeMobile";
	var _async = true;
	var _data = "newMobile=" + newMobile + "&userName=" + username;
	$.ajax({
		dataType : _dataType,
		type : _type,
		url : _url,
		data : _data,
		async : _async,
		success : function(res) {
			if (res == "success") {
				time = 60;
				$("#codeId2").css("display", "block");
				$("#codeId2").html(time + "秒后重新发送");
				$("#codeId").css("display", "none");
				window.setTimeout(callbackCode, 1000); // 1秒
			} else if (res == "userNameNull") {
				// showErrorMsg("用户名不能为空");
				window.location = $("#path").val() + "/customer/toLogin";
			}
			// else if(res =="userNotExist"){
			// showErrorMsg("用户不存在"); }
			else if (res == "mobileIsNull") {
				showErrorMsg("用户手机号不存在");
			} else if (res == "mobileisexist") {
				showErrorMsg("用户手机号已被注册");
			} else if (res == "repeatSend") {
				showErrorMsg("请不要重复发送");
			} else {
				showErrorMsg("发送失败，请稍后重试");
			}
		},
		error : function() {
			showErrorMsg("网络连接超时，请您稍后重试");
		}
	});

}
/**
 * 修改个人信息获取验证码
 */

function getCode() {
	var username = $("#userName").val();
	if (isEmpty(username)) {
		showErrorMsg("用户名不能为空");
		return;
	} else {
		$(".error_tips").addClass("hide");
	}
	var _dataType = "text";
	var _type = "POST";
	var _url = $("#path").val() + "/cusInfo/getCusInfoCode";
	var _async = true;
	// var _data="username="+username;
	var _data = null;
	$.ajax({
		dataType : _dataType,
		type : _type,
		url : _url,
		data : _data,
		async : _async,
		success : function(res) {
			if (res == "success") {
				time = 60;
				$("#codeId2").css("display", "block");
				$("#codeId2").html(time + "秒后重新发送");
				$("#codeId").css("display", "none");
				window.setTimeout(callbackCode, 1000); // 1秒
			} else if (res == "userNameNull") {
				// showErrorMsg("用户名不能为空");
				window.location = $("#path").val() + "/customer/toLogin";
			}
			// else if(res =="userNotExist"){
			// showErrorMsg("用户不存在"); }
			else if (res == "mobileIsNull") {
				showErrorMsg("用户手机号不存在");
			} else if (res == "repeatSend") {
				showErrorMsg("请不要重复发送");
			} else {
				showErrorMsg("发送失败，请稍后重试");
			}
		},
		error : function() {
			showErrorMsg("网络连接超时，请您稍后重试");
		}
	});

}

/**
 * 验证码回调
 * 
 */
function callbackCode() {
	$("#codeId2").css("display", "block");
	time = time - 1;
	$("#codeId2").html(time + "秒后重新发送");
	if (time <= 0) {
		$("#codeId2").css("display", "none");
		$("#codeId").html("重新获取");
		$("#codeId").css("display", "block");
		return;
	}
	setTimeout(callbackCode, 1000);
}

function showErrorMsg(str) {
	// $(".error_tips").removeClass("hide");
	// $(".error_tips").html("<font
	// color=red>&nbsp;&nbsp;&nbsp;"+str+"</font>");
	$.dialog({
		content : str,
		title : '众聚猫提示',
		time : 1000,
	});
	return;
}

// //////////////////////身份证校验的一些玩意

var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ]; // 加权因子
var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ]; // 身份证验证位值.10代表X
function IdCardValidate(idCard) {
	idCard = trim(idCard.replace(/ /g, "")); // 去掉字符串头尾空格
	if (idCard.length == 15) {
		return isValidityBrithBy15IdCard(idCard); // 进行15位身份证的验证
	} else if (idCard.length == 18) {
		var a_idCard = idCard.split(""); // 得到身份证数组
		if (isValidityBrithBy18IdCard(idCard)
				&& isTrueValidateCodeBy18IdCard(a_idCard)) { // 进行18位身份证的基本验证和第18位的验证
			return true;
		} else {
			return false;
		}
	} else {
		return false;
	}
}
/**
 * 判断身份证号码为18位时最后的验证位是否正确
 * 
 * @param a_idCard
 *            身份证号码数组
 * @return
 */
function isTrueValidateCodeBy18IdCard(a_idCard) {
	var sum = 0; // 声明加权求和变量
	if (a_idCard[17].toLowerCase() == 'x') {
		a_idCard[17] = 10; // 将最后位为x的验证码替换为10方便后续操作
	}
	for (var i = 0; i < 17; i++) {
		sum += Wi[i] * a_idCard[i]; // 加权求和
	}
	valCodePosition = sum % 11; // 得到验证码所位置
	if (a_idCard[17] == ValideCode[valCodePosition]) {
		return true;
	} else {
		return false;
	}
}
/**
 * 验证18位数身份证号码中的生日是否是有效生日
 * 
 * @param idCard
 *            18位书身份证字符串
 * @return
 */
function isValidityBrithBy18IdCard(idCard18) {
	var year = idCard18.substring(6, 10);
	var month = idCard18.substring(10, 12);
	var day = idCard18.substring(12, 14);
	var temp_date = new Date(year, parseFloat(month) - 1, parseFloat(day));
	// 这里用getFullYear()获取年份，避免千年虫问题
	if (temp_date.getFullYear() != parseFloat(year)
			|| temp_date.getMonth() != parseFloat(month) - 1
			|| temp_date.getDate() != parseFloat(day)) {
		return false;
	} else {
		return true;
	}
}
/**
 * 验证15位数身份证号码中的生日是否是有效生日
 * 
 * @param idCard15
 *            15位书身份证字符串
 * @return
 */
function isValidityBrithBy15IdCard(idCard15) {
	var year = idCard15.substring(6, 8);
	var month = idCard15.substring(8, 10);
	var day = idCard15.substring(10, 12);
	var temp_date = new Date(year, parseFloat(month) - 1, parseFloat(day));
	// 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法
	if (temp_date.getYear() != parseFloat(year)
			|| temp_date.getMonth() != parseFloat(month) - 1
			|| temp_date.getDate() != parseFloat(day)) {
		return false;
	} else {
		return true;
	}
}
// 去掉字符串头尾空格
function trim(str) {
	return str.replace(/(^\s*)|(\s*$)/g, "");
}

/**
 * 通过身份证判断是男是女
 * 
 * @param idCard
 *            15/18位身份证号码
 * @return 'female'-女、'male'-男
 */
function maleOrFemalByIdCard(idCard) {
	idCard = trim(idCard.replace(/ /g, "")); // 对身份证号码做处理。包括字符间有空格。
	if (idCard.length == 15) {
		if (idCard.substring(14, 15) % 2 == 0) {
			return 'female';
		} else {
			return 'male';
		}
	} else if (idCard.length == 18) {
		if (idCard.substring(14, 17) % 2 == 0) {
			return 'female';
		} else {
			return 'male';
		}
	} else {
		return null;
	}
}