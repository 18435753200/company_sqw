$(document).ready(function(){
	//关闭盒子
	$(".reply-close").live("click",closeReployBox);
	$(".reply-txt").live("keyup",contentWordCount);
	//点击回复出现盒子
	var reploy = "<div class='reply'><div class='reply-close'></div><textarea class='reply-txt'></textarea><span id='isnull'></span><input type='button' class='reply-btn' value='发表回复'> </div>";
	$(".comments-tbody").delegate("ul .td5 .replyComment a","click",function(event){
		$(".reply").remove();
		$(this).parent().parents(".comments-item").find(".td3").append(reploy);
	});
	
	//给评论页面的回复框回复按钮添加点击事件
	$(".comments-tbody").delegate("ul .td3 .reply-btn","click",function(event){
		var id = $(this).parent().parent().find("#id").val();
		var context = $(this).parent().find(".reply-txt").val();
		isLegalcheckReplyContext($.trim(id));
	});	
});

function goexportCommentListByConditionExcel(){
	var pName = $("#pName").val();
	var level = $("#level").val();
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	var comment_array = new Array();
	if($.trim(pName)!=null || $.trim(pName) !=""){
		comment_array.push("pName="+$.trim(pName));
	}
	if($.trim(level)!=null || $.trim(level) !=""){
		comment_array.push("level="+$.trim(level));
	}
	if($.trim(startTime)!=null || $.trim(startTime) !=""){
		comment_array.push("startTime="+$.trim(startTime));
	}
	if($.trim(endTime)!=null || $.trim(endTime) !=""){
		comment_array.push("endTime="+$.trim(endTime));
	}
	comment_array.push("meath="+Math.random());
	window.location.href = "../comment/exportCommentListByConditionExcel?"+comment_array.join("&");
} 



//关闭回复的对话框
var closeReployBox = function(){
	$(".reply").fadeOut(500);
};

//评论列表的分页方法
function clickSubmit(page){
	getCommentListByCondition(page);
}

//根据条件查询评论的列表+分页
function getCommentListByCondition(page){
	var pName = $("#pName").val();
	var level = $("#level").val();
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	var platformType = $("#platformType").val();
	var comment_array = new Array();
	//验证商品ID必须为数字
	/*var matchnum =/^[0-9]*$/;
	if(!matchnum.test($.trim(pid))){
		alert("商品ID不能为空！");
		 $("#pid").val("");
 		 $("#pid").focus();
	}*/
	//时间区间的比较
	if($.trim(pName)!=null || $.trim(pName) !=""){
		comment_array.push("pName="+$.trim(pName));
	}
	if($.trim(level)!=null || $.trim(level) !=""){
		comment_array.push("level="+$.trim(level));
	}
	if($.trim(startTime)!=null || $.trim(startTime) !=""){
		comment_array.push("startTime="+$.trim(startTime));
	}
	if($.trim(endTime)!=null || $.trim(endTime) !=""){
		comment_array.push("endTime="+$.trim(endTime));
	}
	
	if($.trim(platformType)!=null || $.trim(platformType) !=""){
		comment_array.push("platformType="+$.trim(platformType));
	}
	
	comment_array.push("page="+page);
	$.ajax({
		async:false,
		type:"post",
		url:"../comment/getProductCommentListByCondition",
		data:comment_array.join("&"),
		dataType:"html",
		success:function(comments){
			$(".comments-tbody").html(comments);
		},
		error:function(jqXHR,textStatus,errorThrown){
			 alert("网络异常,请稍后再试。。。。");
		}
	});
}

//删除评论执行方法
function deleteComment(id) {
	if(id!=""){
		$.dialog.confirm('你确定要删除此条评论？', function(){
			$.ajax({
				type : "post", 
				url : "../comment/deleteProductComment",
				data:"pid="+id,
				dataType:"html",
				success : function(msg) { 
					 if(msg=="删除成功"){
						 alert(msg);
						 getCommentListByCondition(1);
			    	 }else {
			    		 alert("操作失败 ，请稍后再试");
					}
				},
				error:function(jqXHR,textStatus,errorThrown){
				  	alert("网络异常,请稍后再试。。。。");
				 }
			});
		}, function(){
			
		});
	}
}

//验证回复输入内容是否合法
function isLegalcheckReplyContext(commentId){
	var content = $.trim($(".reply-txt").val());
	if(content==""){
		alert("请输入回复内容");
	}else if(content.length>600){
		alert("回复内容超出！您只可以输入600个字符");
	}else{
		$.ajax({
			type:"post",
			url:"../comment/isLegalCheckReplyContent",
			data:{ 'content':content,"commentId":commentId},
			success:function(msgStr){
				if(msgStr=="content is error"){
					alert("网络异常,回复失败！");
				}else if(msgStr=="添加成功"){
					alert("回复成功");
					closeReployBox();
				}else{
					var LegalStr = "";
					$.each(eval(msgStr),function(i,str){
						if(i==0){
							LegalStr=LegalStr+str;
						}else if(i<2){
							LegalStr = LegalStr + "，" +str;
						}	
					});
					alert("您输入的内容中有["+LegalStr+"]非法字符,请重新填写！");
					$(".reply-txt").val("");
					$(".reply-txt").focus();
				}	
			},
			error:function(){
				alert("网络异常,请稍后再试!");
			},
		});	
	}	
}

function contentWordCount(){
	var content = $.trim($(".reply-txt").val());
	var num =content.length;
	var liunum = 600-Number(num);
	if(num>=600){
    	content=content.substr(0,600);
    	liunum = 0;
    	$(".reply-txt").val(content);
	}
	
	$("#isnull").text("还可以输入"+liunum+"个字数");		
}



/*

//计算文本框可以输入的字符串
function jisuansz(){
	var content = $("#addkf").val();
	var num =content.length;
	var liunum = 500-Number(num);
	if(num>=500){
    	content=content.substr(0,500); 
    	liunum = 0;
    	$("#addkf").val(content);
    	$(".so3").hide();
    	$("#chao").show();
	}
	else{
    	$(".so3").show();
    	$("#chao").hide();
	}
	$("#text1").text(liunum);
}  */























