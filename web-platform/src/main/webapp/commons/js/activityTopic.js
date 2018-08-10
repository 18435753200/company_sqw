var  CONTEXTPATH  = $("#conPath").val();

$(document) .ready(function () {


/**
 * 创建话题
 */
$("#saveActivityTopic").click(function(){
//	$("#productAction").action(CONTEXTPATH + "/Activity/saveActivity");
	if($("#title").val() == ''){
		$("#titleHidden").text("标题不能为空!");
		$("#titleHidden").show();
		return;
	}
	
	if($("#title").val().length > 20){
		$("#titleHidden").text("最多显示20个字");
		$("#titleHidden").show();
		return;
	}
	
	
	if($("#activityUrlLi").is(":hidden")){
		$("#activityUrl").val("");
	}else{
		if($("#activityUrl").val() == ''){
			$("#activityUrlHidden").show();
			return;
		}
	}
	
	if($("#viewUrlLi").is(":hidden")){
		$("#viewUrl").val("");
	}else{
		if($("#viewUrl").val() == ''){
			$("#viewUrlHidden").show();
			return;
		}
	}
	
	if($("#sketich").val() == ''){
		$("#sketichHidden").text("简叙不能为空!");
		$("#sketichHidden").show();
		return;
	}
	
	if($("#sketich").val().length > 500){
		$("#sketichHidden").text("文字控制在500之内!");
		$("#sketichHidden").show();
		return;
	}
	
	$.ajax({
		type:'post',
		url:CONTEXTPATH+'/activitytop/saveActivityTopic',
		data: $('#productAction').serialize(),
		success:function(data){
			if(data=='1'){
				tipMessage("保存成功，关闭当前页面！",function(){
					reloadOpennerPage();
				});
			}else{
				tipMessage("保存失败，请检查后重试！",function(){
					$(".fabu_btn").removeAttr("disabled");
				});
			}

		}
	});
	
});

/**
 * 修改话题
 */
$("#updateActivityTopic").click(function(){
	if($("#title").val() == ''){
		$("#titleHidden").text("标题不能为空!");
		$("#titleHidden").show();
		return;
	}
	
	if($("#title").val().length > 20){
		$("#titleHidden").text("最多显示20个字");
		$("#titleHidden").show();
		return;
	}
	
	
	if($("#activityUrlLi").is(":hidden")){
		$("#activityUrl").val("");
	}else{
		if($("#activityUrl").val() == ''){
			$("#activityUrlHidden").show();
			return;
		}
	}
	
	if($("#viewUrlLi").is(":hidden")){
		$("#viewUrl").val("");
	}else{
		if($("#viewUrl").val() == ''){
			$("#viewUrlHidden").show();
			return;
		}
	}
	
	if($("#sketich").val() == ''){
		$("#sketichHidden").text("简叙不能为空!");
		$("#sketichHidden").show();
		return;
	}
	
	if($("#sketich").val().length > 500){
		$("#sketichHidden").text("文字控制在500之内!");
		$("#sketichHidden").show();
		return;
	}
	
	$.ajax({
		type:'post',
		url:CONTEXTPATH+'/activitytop/updateActivityTopic',
		data: $('#productAction').serialize(),
		success:function(data){
			if(data=='1'){
				tipMessage("保存成功，关闭当前页面！",function(){
					reloadOpennerPage();
				});
			}else{
				tipMessage("保存失败，请检查后重试！",function(){
					$(".fabu_btn").removeAttr("disabled");
				});
			}

		}
	});
	
});
});

//保存banner
function saveBannerTitle(bannerStatus){
	
	var status = false;
	
	$("#00_img li").each(function(i){
		if($(this).find("img").attr("src") != '' && $(this).find("img").attr("src") != undefined){
			if($(this).find(".linkUrl input").val() == undefined || $(this).find(".linkUrl input").val() == ''){
				$("#imgHidden").show();
				status = true;
			}
		}
		
		if($(this).find(".linkUrl input").val() != undefined && $(this).find(".linkUrl input").val() != ''){
			if($(this).find("img").attr("src") == '' || $(this).find("img").attr("src") == undefined){
				$("#imgHidden").show();
				status = true;
			}
		}
	});
	
	
	if(status == false){
		$.ajax({
			type:'post',
			url:CONTEXTPATH+'/activitytop/saveBanner',
			data: $('#productAction').serialize()+"&bannerStatus="+bannerStatus,
			success:function(data){
				if(data=='1'){
					tipMessage("保存成功，关闭当前页面！",function(){
						reloadOpennerPage();
					});
				}else{
					tipMessage("保存失败，请检查后重试！",function(){
						$(".fabu_btn").removeAttr("disabled");
					});
				}
	
			}
		});
	}
}

//banner预览
function reviewBanner(){
	
	window.open('http://m.zhongjumall.com/view/topic/previewtopic','_blank');
}


function reloadOpennerPage(){
	var elnode = window.opener.document.getElementById("pageContext"); 
	var page = $(elnode).val();
	window.opener.location.href="javascript:clickSubmit("+page+")";
	
	window.opener=null;
	window.open('','_self');
	window.close();
}
