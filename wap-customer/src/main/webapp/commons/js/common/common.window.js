
/**
 * 显示alert窗口
 * @param title
 * @param content
 * @param button
 * @param url
 * @param jsFunction
 */
function showAlert(title,content,button,url,jsFunction){
	$("#alertWindow").find(".pop-msg-title").text(title);	// 弹窗标题
	$("#alertWindow").find(".pop-goods-list").text(content);// 弹窗内容
	$("#alertWindow").find(".pop-btn").text(button);// 按钮文字
	
	if(url != null){
		$("#alertWindow").find(".pop-btn").attr("href",url); // 点击按钮跳转地址
	}
	
	jsFunction = jsFunction == null ? "hideAlert()" : jsFunction+"hideAlert();";
	$("#alertWindow").find(".pop-btn").attr("onclick",jsFunction); // 点击按钮执行js方法

	$("#alertWindow").removeClass("hide"); // 显示弹窗
}

/**
 * 显示confirm窗口
 * @param title
 * @param content
 * @param cancelButton
 * @param sureButton
 * @param url
 * @param jsFunction
 */
function showConfirm(title,content,cancelButton,sureButton,url,jsFunction){
	$("#confirmWindow").find(".pop-msg-title").text(title);	// 弹窗标题
	$("#confirmWindow").find(".pop-msg-cnt p").text(content);// 弹窗内容
	$("#confirmWindow").find(".cancelBtn").text(cancelButton);// 取消按钮文字
	$("#confirmWindow").find(".cancelBtn").attr("href","javascript:hideConfirm();"); // 点击取消按钮执行方法
	$("#confirmWindow").find(".sureBtn").text(sureButton);// 确定按钮文字
	
	if(url != null){
		$("#confirmWindow").find(".sureBtn").attr("href",url); // 点击确定按钮跳转地址
	}
	
	jsFunction = jsFunction == null ? "hideConfirm()" : jsFunction+"hideConfirm();";
	$("#confirmWindow").find(".pop-btn").attr("onclick",jsFunction); // 点击确定按钮执行js方法

	$("#confirmWindow").removeClass("hide"); // 显示弹窗
}

/**
 * 隐藏alert窗口
 */
function hideAlert(){
	$("#alertWindow").addClass("hide");
}

/**
 * 隐藏confirm窗口
 */
function hideConfirm(){
	$("#confirmWindow").addClass("hide");
}