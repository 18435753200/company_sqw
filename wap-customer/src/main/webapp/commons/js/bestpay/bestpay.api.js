var Payment = {
	onPay : function(params, callback, fail) {
		var json = {
			'params' : params
		};
		return exec_asyn("Payment", "onPay", json, callback, fail);
	}
}

var Scanner = {
	scan : function(params, callback, fail) {
		var json = {
			'params' : params
		};
		return exec_asyn("Scanner", "scan", json, callback,
			fail);
	}
}

var User = {
	// 获取用户产品号，也就是电话号码
	getProduct : function() {
		return exec("User", "getProduct", {});
	}
}

var App = {
	getClientVersion: function(){
		return exec("App", "getClientVersionName", {});
	},
	/**
	 * 验证SessionKey超时后调用自动登录函数
	 *
	 * @return {"sessionKey":"EEDSSDSD"} 失败，sessionKey为""
	 */
	autoLogin : function(callback) {
		// return exec("App", "autoLogin", null);
		var json = {
			"productNo" : "",
			"location" : ""
		};
		exec_asyn("App", "autoLogin", json, callback);
	},

	/**
	 * 获取SessionKey
	 */
	getSessionKey : function() {
		return exec("App", "getSessionKey", {});
	},

	// 截断android 返回键事件，用户在按返回键的时候就不会退出html5渲染界面了。
	overrideBackPressed : function(bound) {
		var json = {
			"bound" : bound
		}
		return exec("App", "overrideBackPressed", json);
	},

	// 退出应用，html5渲染界面关闭
	exitApp : function() {
		return exec("App", "exitApp", {});
	},

	// 设置硬键盘监听，当用户按下返回，菜单，搜索按键的时候，onKeyEvent回调函数会被触发。
	// 事件如上 backpress、menupress、searchpress
	setKeyEventListener : function(listener) {
		App.listener = listener;
	},

	// 按键事件监听。如若setKeyEventListener设置了监听器，则当用户按下返回，菜单，搜索按键的时候，此函数会被触发
	// 事件如上 backpress、menupress、searchpress
	onKeyEvent : function(event) {
		if (typeof App.listener == 'function') {
			App.listener(event);
		}
	},

	// 设置html5应用标题内容。html5应用的标题是由android实现。
	setTitle : function(title) {
		var json = {
			"title" : title
		}
		exec("App", "setTitle", json);
	},

	/**
	 * 跳转到原生页面
	 *
	 * @param viewName -
	 *            预定义的页面名称
	 * @param args －
	 *            bundle参数
	 * @param callback -
	 *            调用成功返回时调用的函数
	 * @param fail －
	 *            调用失败时执行的函数
	 * @since 1.0.8
	 */
	jumpToNativeView : function(viewName, args, callback, fail) {
		if (typeof args == 'undefined')
			args = {};
		var json = args;
		json["viewName"] = viewName;
		exec_asyn("App", "jumpToNativeView", json, callback,
			fail);
	},
	/**
	 *打开安全键盘  add by leon.eng 15/04/24
	 */
	openSafeKeyBoard:function(len,title,success_callback, fail_callback){
		len = len || 6;
		title = title || "";
		var _json = {len:len,title:title};
		exec_asyn("App", "openSafeKeyBoard",_json,success_callback, fail_callback);
	}
}

var Dialog = {
	// 消失对话框
	// id: showDialog调用后返回的id。
	dismissDialog : function(id) {
		var json = {
			'id' : id
		};
		return exec("Dialog", "dismissDialog", json);
	},
	// 弹出等待对话框
	// title:对话框标题
	// msg:对话框内容
	// return : 动态分配的id，供取消等待
	showProgressDialog : function(title, msg) {
		var json = {
			'title' : title,
			'msg' : msg
		};
		return exec("Dialog", "showProgressDialog", json);
	},
	alert : function(msg) {
		window.alert(msg);
	}
}

var Contacts = {
	// 打开联系人
	openContacts : function(success, fail) {
		exec_asyn("Contacts", "openContacts", '{}', success, fail);
	},
	// 打电话
	tel : function(tel)
	{
		var json = {

			'tel' : tel
		}
		return exec("Contacts", "call", json);
	}
}

var DEVICE_IS_IOS = /iP(ad|hone|od)/.test(navigator.userAgent);

var BestpayHtml5 = {
	idCounter : 0,
	INPUT_CMDS : {},
	INPUT_ARGS : {},
	OUTPUT_RESULTS : {},
	CALL_STATUS:{
		SUCCESS: 0
	},
	CALLBACK_SUCCESS : function(result) {
		console.log(result);
		return;
	},
	CALLBACK_FAIL : function(result) {
		console.log(result);
		return;
	},
	callNative : function(cmd, args, success, fail) {
		var key = "ID_" + (++this.idCounter);
		if(DEVICE_IS_IOS){
			this.INPUT_CMDS[key] = cmd;
			this.INPUT_ARGS[key] = args;
			if (typeof success !='undefined') this.CALLBACK_SUCCESS[key] = success;
			if (typeof fail !='undefined') this.CALLBACK_FAIL[key] = fail;
		}else{
			this.INPUT_CMDS = cmd;
			this.INPUT_ARGS = args;
			window.nintf.setCmds(this.getInputCmd(), key);
			window.nintf.setArgs(this.getInputArgs(), key);
		}

		var iframe = document.createElement("IFRAME");
		iframe.setAttribute("src", "bestpayhtml://ready?id=" + key);
		document.documentElement.appendChild(iframe);
		iframe.parentNode.removeChild(iframe);
		iframe = null;

		console.log("return this.OUTPUT_RESULTS:" + this.OUTPUT_RESULTS);
		if(DEVICE_IS_IOS){
			return this.OUTPUT_RESULTS[key]; //同步调用时返回值
		}else{
			return this.OUTPUT_RESULTS;
		}
	},
	getInputCmd : function(key) {
		if(DEVICE_IS_IOS){
			return this.INPUT_CMDS[key];
		}else{
			return JSON.stringify(this.INPUT_CMDS);
		}
	},
	getInputArgs : function(key) {
		if(DEVICE_IS_IOS){
			return this.INPUT_ARGS[key];
		}else {
			return this.INPUT_ARGS;
		}
	},

	callBackJs : function(result, key) {
		if(DEVICE_IS_IOS){
			this.OUTPUT_RESULTS[key] = result;
			var obj = JSON.parse(result);
			var message = obj.message;
			var status = obj.status;
			if(status == BestpayHtml5.CALL_STATUS.SUCCESS){
				if (typeof this.CALLBACK_SUCCESS[key] !="undefined")
					setTimeout("BestpayHtml5.CALLBACK_SUCCESS['" + key + "']('"+message+"')",0);
			}else{
				if (typeof this.CALLBACK_FAIL[key] !="undefined")
					setTimeout("BestpayHtml5.CALLBACK_FAIL['" + key + "']('"+message+"')",0);
			}
		}else{
			this.OUTPUT_RESULTS = result;
			var obj = JSON.parse(result);
			var message = obj.message;
			var status = obj.status;
			if (status == BestpayHtml5.CALL_STATUS.SUCCESS) {
				if (typeof this.CALLBACK_SUCCESS != "undefined")
					setTimeout("BestpayHtml5.CALLBACK_SUCCESS('" + message + "')", 0);
			} else {
				if (typeof this.CALLBACK_FAIL != "undefined")
					setTimeout("BestpayHtml5.CALLBACK_FAIL('" + message + "')", 0);
			}
		}
	}
};

var exec = function(service, action, args) {
	var json = {
		"service" : service,
		"action" : action
	};
	if(DEVICE_IS_IOS){
		var result_str = BestpayHtml5.callNative(JSON.stringify(json), JSON.stringify(args));
		var result = {};
		try{
			result = JSON.parse(result_str);
			var status = result.status;
			var message = result.message;
			return message;
		}catch(e){
			console.error(e.message);
			return null;
		}
	}else{
		var result_str = prompt(JSON.stringify(json), JSON.stringify(args));
		var result = {};
		try {
			result = JSON.parse(result_str);
		} catch (e) {
			console.error(e.message);
		}
		var status = result.status;
		var message = result.message;
		if (status == 0) {
			return message;
		} else {
			console.error("service:" + service + " action:" + action + " error:"
				+ message);
		}
	}

}

var exec_asyn = function(service, action, args, success, fail) {
	var json = {
		"service" : service,
		"action" : action
	};

	if(DEVICE_IS_IOS){
		BestpayHtml5.callNative(JSON.stringify(json), JSON.stringify(args), success, fail);
	}else{
		if (typeof fail != 'success')
			BestpayHtml5.CALLBACK_SUCCESS = success;
		if (typeof fail != 'undefined') {
			BestpayHtml5.CALLBACK_FAIL = fail;
		} else {
			BestpayHtml5.CALLBACK_FAIL = function() {};
		}
		var result = BestpayHtml5.callNative(json, JSON.stringify(args));
	}
}