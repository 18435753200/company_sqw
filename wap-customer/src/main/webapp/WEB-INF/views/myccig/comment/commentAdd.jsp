<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<%@include file="/WEB-INF/views/commons/base.jsp" %>
<link href="${staticFile_s}/commons/css/base.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/user.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>

<title><spring:message code="title_add_comment" /></title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>

<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>
<header id="header" class="head"><a href="javascript:goBack();"  class="goback"><span class="icon i-goback"></span></a>
    <h2 class="h-title">我要评价</h2>
</header>

<form action="" method="post" id="commentSubForm">
<input type="hidden" name="id" id="itemId" value="${item.id }">
<input type="hidden" name="orderId" id="orderId" value="${item.orderId }">
 
<div class="wrap" id="commentAdd">
    <div class="comment-main">
        <div class="comment-item"><a href="${path }/item/get/${item.pid}" class="link">
            <div class="comment-pic"><img src="${picUrl2 }${item.imgUrl}"></div>
            <div class="comment-info">
                <p class="name">${item.pName }</p>
                <p class="price"><span>   <fmt:formatNumber value="${fn:escapeXml(item.price)}" pattern="#0.00" /> </span> </p>
            </div>
            </a> </div>
        <div class="comment-score"><span class="score-name">评分：</span>
        <span class="commstar">
        <a href="javascript:;" class="star1" val="1"></a>
        <a href="javascript:;" class="star2" val="2"> </a>
        <a href="javascript:;" class="star3" val="3"></a>
        <a href="javascript:;" class="star4" val="4"></a>
        <a href="javascript:;" class="star5 active" val="5"></a>
        </span></div>
    </div>
    <div class="comment-box">
        <textarea class="textarea" name="context" id="contextId" placeholder="请您写下此商品的使用感受，对哪些地方满意或不满意？(1-1000字)"></textarea>
    </div>
     <div class="error_tips hide"></div>
    <div>
	    <span class="anony chk">
			<input type="checkbox" value="1" name="isAnonymity" id="checkbox" checked="checked">
			<label for="checkbox">匿名评价</label>
		</span>
	</div>
    <div class="form-btn">
        <input type="button" value="我要评价" onclick="subComment()"  class="btn">
    </div>
</div>
</form>
<%@include file="/WEB-INF/views/commons/footer.jsp" %>
<script src="${staticFile_s}/commons/js/zepto.min.js" type="text/javascript"></script> 
<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script> 
<script>
$(".commstar a").on('click',function(){
	$(this).addClass("active").siblings().removeClass("active");
});

//提交评论
function subComment(){
	var id=$("#itemId").val();
	var orderId=$("#orderId").val();
	var contextId=$("#contextId").val();
	var isAnonymity=$("input[name='isAnonymity']:checked").val();
	
	var level=null;
	if(contextId!=null){
	$(".commstar a").each(function(){
		if($(this).hasClass("active")){
			level= $(this).attr("val");
		}
	});
	
	var res=checkBeforSub(id,orderId,contextId,level);
	if(!res){
		return ;
	} 
	var _dataType = "text";
	var _type = "POST";
	var _url = $("#path").val() + "/cusComment/addCusComment";
	var _async = true;
	var _data = {
			id:id,
			orderId:orderId,
			context:contextId,
			isAnonymity:isAnonymity,
			level:level
	};
	$.ajax({
		dataType : _dataType,
		type : _type,
		url : _url,
		data : _data,
		async : _async,
		success : function(data) {
			// data 为 res+" "+orderId
			var daId=data.split(" ");
			var res=daId[0];
			var orderId=daId[1];
			if(res == "ok"){
			var redirectUrl = $("#path").val()+"/cusOrder/toMyAllOrder?pageNow=1";
			okBT("评论成功", redirectUrl);
			}else{
				showErrorMsg("评论失败");
			}
		},error: function(){
			showErrorMsg("评论失败");
		}
	});
	}else{
		alert("评价内容为空！");
		return;
	}
}

/**
 * 检验提交评论的数据
 */
function checkBeforSub(id,orderId,contextId,level){
	
	if(isEmpty(id)){
		showErrorMsg("系统错误");
		return false;
	}
	if(isEmpty(orderId)){
		showErrorMsg("系统错误");
		return false;
	}
	var res=strLength(contextId, 100, 10, "用户输入的内容必须大于10个字小于100");
	if(res!="ok"){
		showErrorMsg(res);
		return false;
	}
	if(isEmpty(level)){
		showErrorMsg("您还没有评分");
		return false;
	}
	return true;
}
	
function showErrorMsg(str){
	//  $(".error_tips").removeClass("hide");
//	$(".error_tips").html("<font color=red>&nbsp;&nbsp;&nbsp;"+str+"</font>");  
	$.dialog({
            content : str,
            title : '众聚猫提示',
            time: 2000,
	});
	return;
}
	
</script>
</body>
</html>