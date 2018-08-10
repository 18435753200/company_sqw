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
<link rel="stylesheet" href="${staticFile_s}/commons/css/grzxcss/public.css">
<script type="text/javascript" src="${staticFile_s}/commons/js/main.js"></script>
<title>${title }</title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>
<style>
     body{background:white;}
     .listTitle{width:100%;display:flex;height:2rem;border-bottom:1px solid #e6e6e6;line-height:2rem;font-size:0.6rem;}
     .listData{width:100%;}
     .listData li{display:flex;width:100%;height:2rem;line-height:2rem;font-size:0.6rem;background:#eff0f0;border-bottom:1px solid white;}
     .zcsj,.yhid,.listData li span,.listData li p{width:30%;}
     .yhmc,.listData li div{width:40%;}
     .yhid,.yhmc,.zcsj,.listData li span,.listData li p,.listData li div{text-align:center;}
     
</style>
<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>	
<input type="hidden" id="page" value="${pageBean.page}"/>	
<input type="hidden" id="totalPage" value="${pageBean.totalPage}"/>	
<input type="hidden" id="account" value="${account}"/>	
    <header class="header" style="border-bottom: 0;">
	    <a class="go_back" href="javascript:;" onclick="goBack();"></a>
	    <div class="title">我的用户</div>
	</header>
	<div class="content">
    <ul class="listTitle">     
           	 <li class="yhid">用户ID</li>
           	 <li class="yhmc">用户名称</li>
           	 <li class="zcsj">注册时间</li>  
    </ul>
    <ul class="listData">
               <c:forEach items="${pageBean.result }" var="detail">
             <li>
                 <span>${detail.userId }</span>
                 <div>${detail.userName }</div>
                 <p><fmt:formatDate value="${detail.createTime}" type="both" pattern="yyyy-MM-dd"/></p>
             </li>
               </c:forEach>
    </ul>
</div>
<a href="javascript:void(0);" class="list-bottom" onclick="more();" id="more" style="width:100%;display:block;text-align:center;">加载更多</a>
</body>
<script type="text/javascript" src="${staticFile_s}/commons/js/rem.js"></script>

<script>
(function(){
	isShowMore();
})();

function isShowMore(){
	var page = $("#page").val();
	var totalPage = $("#totalPage").val();
	if(totalPage == 1 || page == totalPage||totalPage == 0){
		$(".list-bottom").hide();
	}
}

function more(){
	var page = parseInt($("#page").val())+1;
	$.ajax({
		type: 'POST',
		url: '${path}/wealth/supplierUsersMore',
		dataType: 'json',
		data:"page="+ page,
		success: function(data) {
			$("#page").val(data.page);
			isShowMore();
			for(var i in data.result){
				var result = "";
				result += "<li>";
				result +="<span>"+data.result[i].userId+"</span>";
				result += "<div>"+data.result[i].userName+"</div>";
				var dat=dateFtt("yyyy-MM-dd",new Date(data.result[i].createTime));
				 result += "<p>"+dat+"</p></li>";
				 $(".listData li").last().after(result);
			}
		},
	});
}
function dateFtt(fmt,date)   
{ //author: meizz   
  var o = {   
    "M+" : date.getMonth()+1,                 //月份   
    "d+" : date.getDate(),                    //日   
    "h+" : date.getHours(),                   //小时   
    "m+" : date.getMinutes(),                 //分   
    "s+" : date.getSeconds(),                 //秒   
    "q+" : Math.floor((date.getMonth()+3)/3), //季度   
    "S"  : date.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
}
</script>
</html>