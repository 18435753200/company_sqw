var _trackDataType = 'wap'; // 标记数据来源，参数是web和wap，可以为空，默认是web
var _Schannel_website_id = '10000006';// 分站编号，不存在可不写此变量或者留空
var _trackData = _trackData || [];// 必须为全局变量，假如之前并没有声明，请加此行代码；
/**
 * 登录
 */
function login() {

	var username = $("#mobile").val();
	var password = $("#password").val();
	var openId = $("#openId").val();
	var productId = $("#productId").val();
	var errorCountVal = $("#errorCountValue").val();
	var captcha = $("#captcha").val();// 验证码
	username = $.trim(username);
	password = $.trim(password);
	openId=$.trim(openId);
	//alert(openId);
	if (!checkUserNameAndPass(username, password)) {
		return;
	}
	var len = password.length;
	if (len < 6 || len > 20) {
		showError("密码为6-20个字符");
		return;
	}
	var ul = username.length;
	if (ul > 11) {
		showError("用户ID号或手机号不正确");
		return;
	}
	
	if (errorCountVal >= 3) {
		if (captcha == "") {
			showError("验证码不能为空！");
			return;
		}
	}
	var enResult = strEnc(password, "13800138001");
	
	var _dataType = "text";
	var _type = "POST";
	var _url = $("#path").val() + "/customer/login";
	var _async = false;
	var _data = "userName=" + username + "&password=" + enResult + "&captcha="+ captcha+"&openId="+openId;
	$("#subId").val("正在登录");
	$("#subId").attr("disabled", "disabled");
	$.ajax({
		dataType : _dataType,
		type : _type,
		url : _url,
		data : _data,
		async : _async,
		success : function(data) {
			var dataBase = data;
			_trackData.push([ "userset", "userid", "" ]);
			_trackData.push([ "userset", "username", username ]);
			if (data.length > 3) {
				var str1 = data.substring(0, 3);
				dataBase = str1;
				var str2 = data.substring(3);
				if (str2 >= 3) {
					$("#codeDiv").show();
				} else {
					$("#codeDiv").hide();
				}
				$("#errorCountValue").attr("value", str2);
			}
			switch (dataBase) {

			// 登录成功
			case '200':
				if(productId == '' || productId =='undefined'){
					location.href = $("#path").val() + "/";
				}else{
					location.href =  $("#path").val()+"/item/get/"+productId;
				}
				
				//成功调转到首页
				location.href = 'http://m.zhongjumall.com';
				break;

			// 用户名或密码不正确
			case '501':
				showError("账号或密码不正确");
				break;
			// 系统错误
			case '503':
				showError("验证码错误");
				break;
			case '500':
				showError("系统繁忙，请稍候重试");
				break;
			case '505':
				showError("非法用户");
				break;	
			case '504':
				$("#codeDiv").show();
				showError("为了您的账户安全，请输入验证码");
				break;
			case '506':
				//路径--为绝对路径 http:/m.zhongjumall.com 登陆改为 https 登陆了
				location.href = "http://m.zhongjumall.com/customer/freeze";
				break;
			// 登录成功
			default:
				location.href = data;
				break;
			}
		},
		error : function() {
			showError("网络连接超时");
			$("#subId").val("登录");
			$("#subId").removeAttr("disabled");
		}
	});

}

/**
 * //之前校验
 * 
 * @param username
 * @param password
 * @returns {Boolean}
 */
function checkUserNameAndPass(username, password) {

	if (isEmpty(username)) {
		showError("用户ID号或手机号不能为空");
		return false;
	}
	if (isEmpty(password)) {
		showError("请输入密码");
		return false;
	}
	
	return true;

}

function showError(str) {
	$.dialog({
		content : str,
		title : '众聚猫提示',
		time : 1000,
	});
	$("#subId").val("登录");
	$("#subId").removeAttr("disabled");
}