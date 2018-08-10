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
<link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/grzxcss/grzx_pttz_css.css" />

<script type="text/javascript" src="${staticFile_s}/commons/js/main.js"></script>
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
	height: 45px;
	border-bottom: 1px solid #ececec;
	width:100%
}

.title p {
	float: left;
	line-height: 45px;
	width: 15%;
	height: 45px;
	overflow: hidden;
}

.title p {

    width: 50%;
    height: 45px;
    text-align: center;
}

</style>
<body>
	<%-- hidden域 --%>
	<input type="hidden" id="path" value="<%=path%>" />
	<input type="hidden" id="basePath" value="<%=basePath%>" />
	<input type="hidden" id="page" value="${page }" />
	<input type="hidden" id="totalpage" value="${noticels.pages }" />
	<header class="aui-bar aui-bar-nav">
        <a class="aui-pull-left aui-btn" href="javascript:;" onclick="goBack();" class="goback">
            <span class="aui-iconfont aui-icon-left"></span>
        </a>
        <div class="aui-title">平台通知</div>
</header>
	<div class="wrap" id="myinfo">
		<div class="notice">
		      <div  class="title">
		      			<p>时间</p>
						<p>标题</p>
			  </div>
			<c:forEach items="${noticels.cnoticeList}" var="nls">
				<a href="${path }/notice/info?id=${nls.id}">
					<div id="titlelist" class="title">
						<p>
							<fmt:formatDate value="${nls.createTime }"  pattern="yyyy/MM/dd HH:mm" />
						</p>
						<p>[${nls.type }]${nls.title}</p>
					</div>
				</a>
			</c:forEach>

		</div>
		
		<a href="javascript:void(0);" onclick="More()" id="more" style="width:100%;display:block;text-align:center;">加载更多</a>
		<a id="nosuggest" style="width:100%;display:block;text-align:center;">暂无通知</a>
	</div>
</body>
<script type="text/javascript">
(function(){
	isShowMore();
})();

function isShowMore() {
	var totalpage = parseInt($("#totalpage").val());
	var page = parseInt($("#page").val());
	//alert(totalpage);
	//alert(page);
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
		$("#page").val(page);
		$.ajax({
			type : 'GET',
			url : '${path}/notice/infolistmore',
			dataType : 'json',
			data : "page=" + page,
			success : function(data) {
				isShowMore();
				
				var result = [];
				for (var i = 0; i < data.cnoticeList.length;i++) {
					
					var notice = data.cnoticeList[i];
				
					result.push("<a href='${path }/notice/info?id=");
					result.push(notice.id);
					result.push("'><div id=\"titlelist\" class=\"title\"><h4>[");
					result.push(notice.type);
					result.push("]:</h4><h2>");
					result.push(notice.title);
					result.push("</h2>");
					result.push("<h3>");
					var time=notice.createTime;
					var timedate=new Date(time);
					var timefmt=formatDate(timedate);
					result.push(timefmt);
					result.push("</h3></div>");
					result.push("</a>");

				}
				$('.notice').append(result.join(""));
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