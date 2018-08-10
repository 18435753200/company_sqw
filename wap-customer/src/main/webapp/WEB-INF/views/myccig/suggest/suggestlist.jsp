<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<%@include file="/WEB-INF/views/commons/base.jsp"%>
<link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/grzxcss/aui.css" />
<link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/grzxcss/grzx_yjfk_css.css" />
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/suggest/suggest.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/main.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/common/common.js"></script>
	
<title><spring:message code="title_notice" /></title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp"%>
</head>
<style>
.notice {
	width: 100%;
	background-size: 100%;
	overflow: hidden;
	background-color: #ffffff;
}

.title {
	height: 30px;
	border-bottom: 0px solid #ececec;
	border-top: 0rem solid #ececec;
	
}



.title ul li {
	float: left;
	margin-left: 5px;
	line-height: 50px;
	width: 50%;
	overflow: hidden;
	height: 50px;
	font-size: 20px;
}

.title span {
    float: right;
    line-height: 50px;
    width: 45%;
    height: 33px;
    color: #e51c23;
    overflow: hidden;
    text-align: right;
}
.titlelist {
	height:67px;
	overflow: hidden;
    width: 100%;
    border: 0.01rem solid #ececec;
}


.titlelist p {
	margin-left: 5px;
	overflow: hidden;
	height: 67px;
	    padding: 0.3rem;
	word-wrap:break-word;
	word-break:normal;
}

.titlelist span {
	float: left;
	margin-right: 10px;
	line-height: 23px;
	width: 23%;
	height: 45px;
}
.homeTips {
	position: fixed;
	left: 0;
	right: 0;
	bottom: 0;
	z-index: 997;
	text-align: center;
	height: 4rem;
	line-height: 4rem;
	font-size: 1.6rem;
	background: #fe4646;
	color: #fff;
}
        body{background:url(../commons/images/bg.jpg) repeat ;background-size: 100%}
.zjm_yjfk{margin: 0.5rem;
    padding: 1rem;
    border: 1px dashed #757575;
}
#id {
    position: fixed;
    left: 80%;
    top: 90%;
}
</style>
<body>
	<%-- hidden域 --%>
	<input type="hidden" id="path" value="<%=path%>" />
	<input type="hidden" id="basePath" value="<%=basePath%>" />
	<input type="hidden" id="page" value="${page }" />
	<input type="hidden" id="totalpage" value="${findSuggest.count }" />
	
	<input type="hidden" id="states" value="${states }" />
	<div class="hid_block"
		style="display:none;width:90%;min-height:50px;margin: 0 auto;border-radius:8px;background-color:#fff;position:absolute;top:45%;left:5%;">


	</div>
	<header class="aui-bar aui-bar-nav">
        <a class="aui-pull-left aui-btn" href="javascript:;"  onclick="goBack();">
            <span class="aui-iconfont aui-icon-left"></span>
        </a>
        <div class="aui-title">意见反馈</div>
</header>
	<div class="wrap" id="myinfo">
		<div class="notice">
			
			<c:forEach items="${findSuggest.platformComplaint}" var="nls">
			<div class="zjm_yjfk">
			<div class="title" >
				<ul>
					<li><b>我的问题</b></li>
				</ul>
					<span><fmt:formatDate value="${nls.complaintTime  }"  pattern="yyyy/MM/dd HH:ss" /></span>
			</div>
			<c:if test="${nls.replyContent!=null }">
				<a href="${path }/suggest/findById?id=${nls.complaintId}">
					<div id="titlelist" class="titlelist">
						<p>${nls.complaintContent}</p>
					</div>
						<div style="width:100%;height: 22px;">
							<h3 style="float:right;width:22%;color:green">查看回复</h3></div>
				</a>

			</c:if>
			<c:if test="${nls.replyContent==null}">
					
					<div id="titlelist" class="titlelist">
						<p>${nls.complaintContent}</p>
					</div>
					<div style="width:100%;height: 22px;">
						<span style="float: right;width: 30%;text-align: right;margin-top: 0.5rem;">暂未回复</span>
					</div>
			</c:if>
		</div>
			</c:forEach>
		</div>
		
		<a href="javascript:void(0);" onclick="More()" id="more" style="width:100%;display:block;text-align:center;">加载更多</a>
		<a id="nosuggest" style="width:100%;display:block;text-align:center;color: black;">暂无意见反馈</a>
	</div>

<div id="id"><a href="${path }/suggest/initSuggest"><img src="${path }/commons/images/suggestred.png"></a></div>
</body>
<script type="text/javascript">

   (function(){
		isShowMore();
	})();

   function isShowMore() {
		var totalpage = parseInt($("#totalpage").val());
		var page = parseInt($("#page").val());
		if(totalpage==0){
			$("#nosuggest").show();
		}else{
			$("#nosuggest").hide();
		}
		if(totalpage==0||page==totalpage){
			$("#more").hide();
			
		}else{
			$("#more").show();
		}
	};
	function More() {
		var page = parseInt($("#page").val()) + 1;
		var states=parseInt($("#states").val());
		$("#page").val(page);
		$.ajax({
			type : 'GET',
			url : '${path}/suggest/findmore',
			dataType : 'json',
			data : "page=" + page,
			success : function(data) {
				isShowMore();
				var result = "";
				for (var i = 0; i < data.platformComplaint.length;i++) {
					
					var complaint = data.platformComplaint[i];
					result+=("<div class='zjm_yjfk'>");
					result+=("<div class='title'>");
					result+=("<ul>");
					result+=("<li><b>我的问题</b></li>");
					result+=("</ul>");
					var time=complaint.complaintTime;
					var timedate=new Date(time);
					var timefmt=formatDate(timedate);
					result+=("<span>"+timefmt+"</span>");
					result+=("</div>");
					if(complaint.replyContent!=null){
						result+=("<a href='${path }/suggest/findById?id='"+complaint.complaintId+">");
						result+=("<div id='titlelist' class='titlelist'>");
						result+=("<p>"+complaint.complaintContent+"</p>");
						result+=("</div>");
						result+=("<div style='width:100%;height: 22px;'>");
						result+=("<h3 style='float:right;width:22%;color:green'>查看回复</h3></div>");
						result+=("</a>");
					}else{
						result+=("<div id='titlelist' class='titlelist'>");
						result+=("<p>"+complaint.complaintContent+"</p>");
						result+=("</div>");
						result+=("<div style='width:100%;height: 22px;'>");
						result+=("<span style='float: right;width: 30%;text-align: right;margin-top: 0.5rem;'>暂未回复</span>");
						result+=("</div>");
					}
					result+=("</div>");
				}
				$('.notice').last().append(result);
				function formatDate(now) {
					　　var year=now.getFullYear();
					　　var month=now.getMonth()+1;
					　　var date=now.getDate();
					　　var hour=now.getHours();
					　　var minute=now.getMinutes();
					　　var second=now.getSeconds();
					　　return year+"/"+month+"/"+date+" "+hour+":"+minute;
					　　}
			},
			error : function() {
			}
		});
	}
	
</script>
</html>