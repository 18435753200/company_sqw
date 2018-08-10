/**
 * 
 */
var test = [];
$(function (){
	
});
function initSetPage() {
//	var id = (Math.random()  + "").split(".")[1];
	adduploadimg("开屏", "startup", test);
	
	if("display" == $("input[name='type']:checked").val() ){
		$("#jumpLinkTr").hide();
	}
	initTypeRadioClick();
}
function initTypeRadioClick() {
	
	$("input[name='type']").bind('click', typeRadioClick);
}
function typeRadioClick() {
	if ("display" ==$("input[name='type']:checked").val()){
		$("#jumpLinkTr").hide();
	}
	else {
		$("#jumpLinkTr").show();
	}
}
function goAddStartUpPage(id) {
	var url = $("#conPath").val();
	if (null != id && "" != id) {
		url += "/infrastructure/setStartupPage?id=" + id;
	}
	else {
		url += "/infrastructure/setStartupPage";
	}
	
	location.href=url;
}

function formValid(){
	var title = $("input[name='title']").val();
	if (null == title || "" == title) {
		alert("请填写说明");
		return false;
	}
	var title = $("input[name='imgUrl']").val();
	if (null == title || "" == title) {
		alert("请上传图片");
		return false;
	}
	var title = $("input[name='displayDuration']").val();
	if (null == title || "" == title) {
		alert("请填写展示时长");
		return false;
	}
	var title = $("input[name='startTime']").val();
	if (null == title || "" == title) {
		alert("请选择开始时间");
		return false;
	}
	var title = $("input[name='endTime']").val();
	if (null == title || "" == title) {
		alert("请选择结束时间");
		return false;
	}
	return true;
}

function add() {
	var path=$("#conPath").val();
	var data = $('#startupForm').serialize();
	var flag = formValid();
	
	var id = $("input[name='id']").val();
	
	var url = path+"/infrastructure/addStartup";
	if (null != id && "" != id) {
		var url = path+"/infrastructure/updateStartup";
	}
	
	if(flag){
		$.ajax({
			type : "post", 
			url  : url, 
			data :data+"&random="+Math.random(),
			success : function(msg) {
				if ("success" == msg) {
					alert("保存成功");
					location.href= path + "/infrastructure/setStartupPageList";
				}
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("保存失败 ，请稍后再试。。");
			}
		});
	}	
}
function modifyStartup(id) {
	goAddStartUpPage(id);
}
function deleteStartup(id){
	
	var isDel = confirm("确定要删除吗?");
	if (isDel){
		
		var path=$("#conPath").val();
		var url = path+"/infrastructure/deleteStartup";
		$.ajax({
			type : "post", 
			url  : url, 
			data : "id="+id,
			success : function(msg) {
				if ("success" == msg) {
					alert("删除成功");
					window.location.reload();
				}
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("保存失败 ，请稍后再试。。");
			}
		});
	}
	
}

//图片处理函数
function pre(event) {
	var but = event.target;
	var li = $(but).parent().parent();
	var id = li.attr("class");
	var theimg = li.find(".p-img");
	var src = li.find("img").attr("src");
	if (src != null && id != "img-1") {
		if (li.prev().children(".p-img").children().is("img")) {
			var imgboxpre = li.prev().children(".p-img");
			li.prev().find(".p-img").remove();
			li.prev().prepend(theimg);
			li.find(".p-img").remove();
			li.prepend(imgboxpre);
		} else {
			li.find(".operate").hide();
			li.prev().find(".p-img").remove();
			li.prev().prepend(theimg);
			li.prepend($("<div class='p-img'></div>"));
			li.prev().children(".operate").show();
		}    
	} else {
		return false;
	}
}


function adduploadimg(value, id, url) {
	var isnew = true;
	var p_img = $("<div class='imgUrlDiv'></div>");
	var operate =$("<div class='operate'><i class='toleft'>左移</i><i class='toright'>右移</i><i class='del'>删除</i><i class='down'>删除</i></div>");
	
	var ul;
	if(url.length == 0){
		var img1 = $("<li class='img-1'></li>").append(p_img);
		ul = $("<ul id='"+ id +"_img'></ul>").append(img1);
	}else{
		ul = $("<ul id='"+ id +"_img'></ul>");
		
		ul.append($("<li class='img-1'></li>").append(p_img.append("<img src='"+ url[i]+"'><input type='hidden' name='imgUrl' value='" + id+"_"+url[i]+"'/></img>")));

		if(url.length<1){
			ul.append($("<li></li>").append(p_img));
		}			
	}
	
	var div = isnew ? $('<div></div>').append('<div class=\'wenzi\'><span class=\'sctp-txt\' >' + value + '的图片:</span><input id=\'' + id + '_upload\' name=\'button\' type=\'submit\'  value=\'图片上传\' /><span class=\'dpl-tip-inline-warning\'>请至少上传一张图片</span></div>').append(ul):
	$('<div></div>') .append('<div class=\'wenzi\'><span class=\'sctp-txt\' >' + value + '的图片:</span><input id=\'' + id + '_upload\' name=\'button\' type=\'submit\'  value=\'图片上传\' /><span class=\'dpl-tip-inline-warning\'>请至少上传一张图片</span></div>') .append(ul) ;
	var object = $('<div class=\'z\' id=\'' + id + '_xianshi\'></div>') .append(div);
	$('.jinben').append(object);
	if (flashDetect()) {
		// 探测到已经安装了flash插件 则初始化上传按钮和提示
		// 初始化swfUpload组件
		$('#' + id + '_upload') .uploadify({
			// File Upload Settings
			'height': 30,
			'swf': '../commons/js/uploadify/uploadify.swf',
			'uploader': '../product/imageUp',
			'width': 100,
			'cancelImg': '../js/img/uploadify-cancel.png',
			'auto': true,
			'buttonText': '上传图片',
			file_size_limit: '100',
			file_queue_limit: 6,
			fileTypeExts: '*.gif;*.jpg;*.jpeg;*.png',
			file_types: '*.jpg;*.png;*.jpeg;',
			file_types_description: '*.jpg;*.jpeg;*.png;*.JPG;*.JPEG;*.PNG;',
			file_dialog_start_handler: fileDialogStart,
			file_queued_handler: fileQueued,
			file_queue_error_handler: fileQueueError,
			file_dialog_complete_handler: fileDialogComplete,
			upload_start_handler: uploadStart,
			upload_progress_handler: uploadProgress,
			upload_error_handler: uploadError,
			upload_success_handler: uploadSuccess,
			upload_complete_handler: uploadComplete,
			queue_complete_handler: queueComplete
		});
		
	} else {
		// 探测到没有flash支持，给出提示。
		$('.ifile') .html('<span class="no-flash-tip">' +
				'Hi，您的浏览器OUT了，它未安装新版的Flash Player，' +
				'<a href="http://get.adobe.com/flashplayer/" target="_blank">去安装>></a>' +
		'</span>');
	}
}
