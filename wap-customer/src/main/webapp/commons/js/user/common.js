/**
 * ajax工具
 * 
 * @param _dataType
 * @param _type
 * @param _url
 * @param _async
 * @param _data
 * @returns
 */
function ajaxTool(_dataType, _type, _url, _async, _data) {
	var res;
	$.ajax({
		dataType : _dataType,
		type : _type,
		url : _url,
		data : _data,
		async : _async,
		success : function(data) {
			res = data;
		},
		error : function() {
			$.dialog({
				content : "网络连接超时，请您稍后重试",
				title : '众聚猫提示',
				time : 1000,
			});
		}
	});
	return res;
}

/**
 * 判空 是空返回true
 */ 
function isEmpty(str) {
	if (str != null && str != undefined && str != "") {
		return false;
	}
	return true;
}

/**
 * 判断是否是空
 * 
 * @param value
 */
function isempty(value) {
	if (value == null || value == "" || value == "undefined"
			|| value == undefined || value == "null") {
		return true;
	} else {
		value = value.replace(/\s/g, "");
		if (value == "") {
			return true;
		}
		return false;
	}
}
/**
 * 验证邮箱
 * 
 * @param strEmail
 *            邮箱
 * @returns {Boolean}
 */
function isEmail(strEmail) {
	if (strEmail
			.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) != -1) {
		return true;
	} else {
		return false;
	}
}

function idNickName(strnickname) {
	if (strnickname.search(/^([\u4e00-\u9fa5]|[0-9a-zA-Z_-]{2}){2,10}$/) != -1) {
		return true;
	} else {
		return false;
	}
}

/**
 * 验证身份证号
 * 
 * @param strIdCard 身份证号码
 * @returns {Boolean}
 */
function strIdCard(strIdCard) {
	if (strIdCard.search(/^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/) != -1) {
		return true;
	} else {
		return false;
	}
}

/**
 * 规定字符串的长度
 * 
 * @param str
 *            字符串
 * @param maxLen
 *            最长
 * @param minLen
 *            最短
 * @param res
 *            返回的结果
 * @returns "ok"表示通过
 */
function strLength(str, maxLen, minLen, res) {
	if (str.length > maxLen || str.length < minLen) {
		return res;
	} else {
		return "ok";
	}
}
/**
 * 验证手机号
 * 
 * @param mobile
 *            手机号
 * @returns {String} 结果
 */
function verifyPhoneNumber(mobile) {
	if (mobile.length == 0) {
		return "请输入您的手机号";
	}
	if (mobile.length != 11) {
		return "手机号格式不正确";
	}

	//var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
	var myreg = /^(1\d{10})$/;
	if (!myreg.test(mobile)) {
		return "手机号格式不正确";
	}
	return "ok";
}

/**
 * 代替alert
 * 
 * @param content
 *            显示内容，成功后的地址
 * @param redirectUrl
 */
function okBT(content, redirectUrl) {
	$.dialog({
		content : content,
		title : '众聚猫提示',
		ok : function() {
			if (!isEmpty(redirectUrl)) {
				window.location.href = redirectUrl;
			} else {
			}
		},
		lock : true
	});
}

//设置cookie
function setCookie(c_name, c_value, c_expired_date)  {
	if (isEmpty(c_name)) {
		return;
	}
	
	if (null == c_expired_date ||  c_expired_date == undefined) {
		c_expired_date = new Date();
		c_expired_date.setDate(c_expired_date.getDate()+1);
	}
	
	document.cookie= c_name + "=" + escape(c_value)+";expires=" + c_expired_date.toGMTString() + ";path=/";
}
// 获取cookie
function getCookie(c_name){
	if (document.cookie.length>0){
	  c_start=document.cookie.indexOf(c_name + "=");
	  if (c_start!=-1){ 
	    c_start=c_start + c_name.length+1;
	    c_end=document.cookie.indexOf(";",c_start);
	    if (c_end==-1) c_end=document.cookie.length;  
	    return unescape(document.cookie.substring(c_start,c_end));
	  } 
	}
	return "";
}  