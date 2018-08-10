<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>评价管理</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link href="${path}/commons/css/reset.css" rel="stylesheet" type="text/css">
<link href="${path}/commons/css/comments.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
$(document).ready(function(){
	$(".btn-reply fr").click(function(){
		goAddCommentReply();
	});
});

function clickSubmit(page){
	var commentId = $("#commentId").val();
	window.location.href="<%=path %>/comment/getReplyListByCommentId?commentId="+commentId+"&page="+page;
}

//回复页面关闭显示回复框
function openOrCloseInput(){
	if($(".reply-box").hasClass("show")==false){
		$(".reply-box").addClass("show");
	}else{
		$(".reply-box").removeClass("show");
	}
}

//验证回复输入内容是否合法
function isLegalcheckReplyContext(commentId){
	var content = $.trim($(".commentRepl_context").val());

	if(content==""){
		alert("请输入回复内容");
	}else if(content.length>600){
		alert("回复内容超出！");
	}else{
		$.ajax({
			type:"post",
			url:"../comment/isLegalCheckReplyContent",
			data:{"content":content,"commentId":commentId},	
			success:function(msgStr){
				if(msgStr=="content is error"){
					alert("网络异常,回复失败！");
				}else if(msgStr=="添加成功"){
					alert("回复成功");
				}else{
					var LegalStr = "";
					$.each(eval(msgStr),function(i,str){
						if(i==0){
							LegalStr=LegalStr+str;
						}else if(i<2){
							LegalStr = LegalStr + "，" +str;
						}	
					});
					alert("您输入的内容中有["+LegalStr+"]非法字符,保存失败,请重新填写！");
					$(".commentRepl_context").val("");
					$(".commentRepl_context").focus();
				}	
			},
			error:function(){
				alert("网络异常,请稍后再试!");
			},
		});	
	}	
}
   
   
function goAddCommentReply(){
	var id = $.trim($("#commentId").val());
	isLegalcheckReplyContext(id);
}


function contentWordCount(){
	var content = $.trim($(".commentRepl_context").val());
	var num =content.length;
	var liunum = 600-Number(num);
	if(num>=600){
    	content=content.substr(0,600);
    	liunum = 0;
    	$(".commentRepl_context").val(content);
	}
	
	$(".number").text("还可以输入"+liunum+"个字数");		
}
</script>
</head>
<body>
<%@include file="/WEB-INF/views/include/header.jsp"%>
<div class="center">
	<div class="p-comment">
		<div class="comment-item">
			<div class="user">
			<input type="hidden" id="commentId" value="${fn:escapeXml(comment.id)}">
			<div class="u-icon"><img src="/web-platform/commons/images/user.jpg" width="50" height="50"></div>
			<div class="u-name">${fn:escapeXml(comment.userInfo.userName)}</div>
		</div>
		<div class="detail">
			<div class="topic">
			<span class="icon-star star${comment.level }"></span>
			<span class="date">
				<fmt:formatDate value="${comment.date }" type="both"/>
			</span>
		</div>
		<div class="comment-content">
			<dl>
				<dt>购买总数：</dt>
				<dd>
				${fn:escapeXml(comment.orderInfo.skuTotalNum)}
				</dd>
			</dl>
			<dl>
				<dt>购买日期：</dt>
				<dd>
				<fmt:formatDate value="${comment.orderInfo.buyDate }" type="both"/>
				</dd>
			</dl>
			<dl>
				<dt>心得：</dt>
				<dd>${fn:escapeXml(comment.context)}</dd>
			</dl>
			<dl>
				<c:forEach items="${comment.orderInfo.skuInfoList}" var="skus">
					<dt>${fn:escapeXml(skus.skuName)}：</dt>
					<dd>${fn:escapeXml(skus.skuValue)}</dd>
				</c:forEach>
			</dl>
		</div>
		
		<div class="btns">
			<c:if test="${! empty buttonsMap['评价列表-回复解释'] }">	
				<a class="btn-reply fr" href="javascript:;" onclick="openOrCloseInput()">
				回复(<em>${fn:escapeXml(comment.repliesCount)}</em>)
				</a>
			</c:if>
		</div>
		<!-- UNICORN回复输入框 -->
				<div class="reply-box">
					<p>
					<em>回复</em>
					<span class="reply-name">${fn:escapeXml(comment.userInfo.userName)}：</span><span class="number"></span>
					</p>
					
					<div class="reply-input">
						<div class="fl">
							<input type="hidden" class="commentRepl_commentid">
							<textarea class="commentRepl_context" onkeyup="contentWordCount()"></textarea>
						</div>
						<a href="javascript:;" class="btn-reply fr" onclick="goAddCommentReply()" >回复</a>
					</div>
					<div class="clr">  </div>
				</div>		
		<div class="commentreply_list">
			<div class="reply-wrap">
				<div class="reply-list">
					<c:if test="${comment.repliesCount eq 0 }">
								暂无回复内容
					</c:if>
					<c:forEach items="${pb.result }" var="replies">
						<div class="reply-content">
							<div class="reply-con">
								<span class="reply-name">${fn:escapeXml(replies.userInfo.userName)}</span>:
								<%-- <em>回复</em>
								<span class="reply-name">
								${comment.userInfo.userName}：
								</span> --%>
								<span class="reply-con">
								${fn:escapeXml(replies.context)}
								</span>
							</div>
							<div class="reply-op">
								<span class="date">
								<fmt:formatDate value="${replies.date }" type="both"/>
								</span>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
		<%@include file="/WEB-INF/views/dealerseller/comment/model/paging.jsp" %>				
	</div>
</div>
</div>
</div>
<!-- 底部 start -->
<%@include file="/WEB-INF/views/include/foot.jsp"%>
<!-- 底部 end -->
</body>
</html>