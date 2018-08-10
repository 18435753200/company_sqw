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
<link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/grzxcss/aui.css" />
<link rel="stylesheet" href="${staticFile_s}/commons/css/grzxcss/style.css" type="text/css">
<script type="text/javascript" src="${staticFile_s}/commons/js/main.js"></script>
<title>操作记录</title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>
<style>
body{background: #ffffff;}
.fl-typ-ul {
            width: 100%;
            height: 1.3rem;
            font-size: 0;
        }

         .fl-typ-ul li {
            display: inline-block;
            box-sizing: border-box;
            width: 32.3232%;
            height: 3rem;
            line-height: 3rem;
            text-align: center;
            font-size: 0.8rem;
            color: #000;
            border-bottom: 1px solid #fff;
            float: left;
        }

         .fl-typ-ul li.active {
            /* color: #E26C64;
            border-bottom: 1px solid #E26C64;
            margin-bottom: 5px; */
            background: #fff;
    color: #e60012;
    border-bottom: 2px solid #e60012;
        }

         .fl-cont-box {
            width: 100%;
        }

         .fl-cont-box > div {
            display: none;
            width: 100%;
        }

         .fl-cont-box > div.active {
            display: block;
            text-align: center;
        }

		.list-header{float:left;width: 100%;margin: 0 auto;}
		.list-header ul{width:100%;}
		.list-header li{float:left;background: #ffffff;solid #DFDFDF;line-height: 3rem;height: 3rem;font-weight: bold;font-size: 1rem;}
		.list-header li:first-child{width:50%;margin-left: 0.15rem;}
		.list-header li:last-child{width:49%;border-left-width: inherit;text-align:center}
		.list-header2 ul{width:100%;}
		.list-header2 li{float:left;background: #ffffff; border: 0.1rem solid #DFDFDF;line-height: 3rem;height: 3rem;width:24.2%;font-weight: bold;}
		.list-header2 li:first-child{margin-left: 0.15rem;}
		.list-header2 li:last-child{border-left-width: inherit;}
		.list-content{float:left;width: 100%;margin-top: 0.1rem;}
		.list-content ul{width:100%;}
		.list-content li{float:left;background: #fff;solid #DFDFDF;line-height: 0.5rem;height: 2rem;font-size:0.8rem;color: #727272; }
		.list-content li:first-child{width:50%;margin-left: 0.15rem;}
		.list-content li:last-child{width:49%;border-left-width: inherit;}
		.list-bottom{float:left;width: 100%;line-height: 0.5rem;}
		.list-header li:last-child {
    width: 49%;
    border-left-width: inherit;
    text-align: center;
}
</style>
<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>	
<input type="hidden" id="page" value="${pageBean.page}"/>	
<input type="hidden" id="totalPage" value="${pageBean.totalPage}"/>	
<input type="hidden" id="type" value="${type}"/>	
<header class="aui-bar aui-bar-nav">
        <a class="aui-pull-left aui-btn" href="javascript:;" onclick="goBack();" class="goback">
            <span class="aui-iconfont aui-icon-left"></span>
        </a>
        <div class="aui-title">操作记录</div>
</header>
    </div>
	<div class="wrap" id="myinfo">
		<ul class="fl-typ-ul">
            <li flag="login" class="<c:if test="${type == 'login' }">active</c:if>">登录记录</li>
            <li flag="consume" class="<c:if test="${type == 'consume'}" >active</c:if>">消费记录</li>
            <li flag="transferAccounts" class="<c:if test="${type == 'transferAccounts' }">active</c:if>">转账记录</li>
        </ul>
        <div class="fl-cont-box">
            <div class="active">
                <c:if test="${type == 'transferAccounts' }">
                 <div class="list-header2" id="list-header">
	                <ul>
		                <li>转向人ID</li>
		                <li>转向人姓名</li>
		                <li>金额</li>
	                </ul>
                 </div>
                 <div class="list-content">
	             <c:forEach items="${pageBean.result }" var="record">
	                <ul>
						<li style="width:32%;">${record.toId }</li>
						<li style="width:32%;">${record.description }</li>
						<li style="width:32%;">${record.totalPrice }</li>
					</ul>
	              </c:forEach>
	              </div>
	          </c:if>  
              <c:if test="${type == 'consume'||type=='login' }">
               <div class="list-header" id="list-header">
	                
	                <ul>
		                <li>日期</li>
		                <li>操作记录</li>
	                </ul>
                </div>
               <div class="list-content">
                 <c:forEach items="${pageBean.result }" var="record">
	                <ul>
		                <li>${record.formatDate }</li>
		                <li <c:if test="${type == 'consume'}">style="line-height: 1.5rem;"</c:if>>${record.remark }</li>
	                </ul>
	                </c:forEach>
	           </div>
	          </c:if>
	              
                
                <div class="list-bottom">
                 <a href="javascript:void(0);" onclick="more()" style="color: black;">点击加载更多</a>
                </div>
            </div>
        </div>
	</div>
</body>
<script>
(function(){
	isShowMore();
	$(".fl-typ-ul li").on("click",function(){
		$(".fl-typ-ul .active").removeClass("active");
		var flag = $(this).attr("flag");
		$(this).addClass("active");
		$("#type").val(flag);
		var page =  1;
		var type = flag;
		$.ajax({
			type: 'GET',
			url: '${path}/operate/data',
			dataType: 'json',
			data:"page="+ page + "&type=" + type,
			success: function(data) {
				$("#page").val(data.page);
				$("#totalPage").val(data.totalPage);
				isShowMore();
				var result = [];
				for(var i in data.result){
					if(type == 'transferAccounts'){
						$('#list-header').html(getTransferAccountsHeader())
						result.push("<ul>");
						result.push("<li style='width:32%;'>"+data.result[i].toId+"</li>");
						result.push("<li style='width:32%;'>"+data.result[i].description+"</li>");
						result.push("<li style='width:32%;'>"+data.result[i].totalPrice+"</li>");
						result.push("</ul>");
					}else{
						$('#list-header').html(getOtherHeader())
						result.push("<ul>");
						result.push(" <li>"+data.result[i].formatDate+"</li>");
						if(type == 'consume'){
							result.push(" <li style='line-height: 1.5rem;'>"+data.result[i].remark+"</li>");
						}else{
							result.push(" <li>"+data.result[i].remark+"</li>");
						}
						result.push("</ul>");
					}
				}
				$('.list-content').html(result.join(""));
			},
			error: function() {
			}
		});
	});
})();

function isShowMore(){
	var page = $("#page").val();
	var totalPage = $("#totalPage").val();
	if(totalPage == 0 || page == totalPage){
		$(".list-bottom").hide();
	}else{
		$(".list-bottom").show();
	}
}

function getOtherHeader(){
	return "<ul><li>日期</li><li>操作记录</li></ul>";
}

function getTransferAccountsHeader(){
	return "<ul><li style='width:32%;'>转向人ID</li><li style='width:32%;'>转向人姓名</li><li style='width:32%;'>金额</li></ul>";
}

function more(page){
	var page =  parseInt($("#page").val()) + 1;
	var type = $("#type").val();
	$.ajax({
		type: 'GET',
		url: '${path}/operate/data',
		dataType: 'json',
		data:"page="+ page + "&type=" + type,
		success: function(data) {
			$("#page").val(data.page);
			isShowMore();
			var result = [];
			for(var i in data.result){
				if(type == 'transferAccounts'){
					$('#list-header').html(getTransferAccountsHeader())
					result.push("<ul>");
					result.push("<li style='width:32%;'>"+data.result[i].toId+"</li>");
					result.push("<li style='width:32%;'>"+data.result[i].description+"</li>");
					result.push("<li style='width:32%;'>"+data.result[i].totalPrice+"</li>");
					result.push("</ul>");
				}else{
					$('#list-header').html(getOtherHeader());
					result.push("<ul>");
					result.push(" <li>"+data.result[i].formatDate+"</li>");
					if(type == 'consume'){
						result.push(" <li style='line-height: 1.5rem;'>"+data.result[i].remark+"</li>");
					}else{
						result.push(" <li>"+data.result[i].remark+"</li>");
					}
					result.push("</ul>");
				}
			}
			$('.list-content').append(result.join(""));
		},
		error: function() {
		}
	});
}
</script>
</html>