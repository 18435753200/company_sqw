$(document).ready(function() {
	$(".commstar a").on('click', function() {
		$(this).addClass("active").siblings().removeClass("active");
		var i = $(this).parent().attr("itemIndex");
		$("#level" + i).val($(this).attr("level"));
	});

	$("input[type='button']").bind("click", function() {
		submitComment();
	});
	
	//评论内容折叠
	   var comp = $('.com-box');
	   comp.each(function(){
		   var conh = $(this).find('p').height();
		   if(conh>60){
				 $(this).find('.d').show();
				 $(this).append("<div class='comd'><span class='down'></span><span class='up'></span></div>");
				 $(this).find('.up').hide();
			 }else{
				 $(this).find('.d').hide(); 	  
			 }	
	   });
	   $('.down').bind('click', function(){
			var ph = $(this).parent().siblings().height();
			$(this).parent().parent().css({'max-height':+ ph +'px'}); 
			$(this).hide();
			$(this).parent().find('.up').show();
	   });
	   $('.up').bind('click', function(){
			$(this).hide();
			$(this).parent().find('.down').show();
			$(this).parent().parent().css({'max-height':'50px'});
	   });	
	 //评论内容折叠
});

/**
 * 提交评论
 */
function submitComment() {
	var orderId = $("#orderId").val();
	var no = $("#comentType").val();
	var content = $("#contextId").val();
	$.ajax({
		type : "post",
		url : $("#path").val() + "/comment/commit/" + orderId + "-" + no,
		data : $('form').serialize(),
		dataType : "json",
		success : function(code) {
			if (code == 0) {
				showMsg("评论失败！不能填写表情哦");
			}
			if (code == 1) {
				showMsg("评论成功！");
				location.href=$("#path").val()+"/cusOrder/toMyAllOrder?pageNow=" + 1;
			}
			if (code == 2) {
				showMsg("您的评价内容不合规范，请重新编辑！");
			}
			if (code == 3) {
				showMsg("请填写追评内容！");
			}
			if (code == 4) {
				showMsg("已达到字数上限！");
			}
		},
		error : function() {
			showMsg("系统错误");
			location.href=$("#path").val()+"/cusOrder/toMyAllOrder?pageNow=" + 1;
		}
	});
}
/**
 * 
 * @param content
 */
function showMsg(content){
	$.dialog({
        content : content,
        title : '众聚猫提示',
        time: 2000,
	});
}