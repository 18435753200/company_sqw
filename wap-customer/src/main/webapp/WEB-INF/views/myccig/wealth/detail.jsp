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
<link href="${staticFile_s}/commons/css/my.css" rel="stylesheet" type="text/css">
<%-- zepto alert --%>
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/person.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/common/common.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/main.js"></script>
<title>${title }</title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>
<style>
		.wrap .wrap-item {
            width: 100%;
            background-color: #ffffff;
            margin-bottom: 1rem;
            border-bottom: 1px solid #E0DFE5;
        }

        .wrap-item > div {
            padding: 0px 0.5rem;
            overflow: hidden;
        }

        .wrap-item > div > div:nth-child(1) {
            float: left;
        }

        .wrap-item > div > div:nth-child(2) {
            float: right;
        }

        .wrap-item .wi-num {
            border-bottom: 1px solid #EBEBEB;
        }

        .wrap-item .wi-num > div {
            line-height: 2.5rem;
            height: 2.5rem;
            color: #929292;
            font-size: 1rem;
        }

        .wrap-item .wi-cont {
            position: relative;
            padding: 0.5rem 0.5rem 0.5rem;
        }

        .wrap-item .wi-cont > div {
            float: left;
        }

        .wrap-item .wi-cont > div:nth-child(1) {
            width: 65%;
            font-size: 1rem;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            min-height: 3rem;
            overflow: hidden;
        }

        .wrap-item .wi-cont > div:nth-child(2) {
            color: #e26c64;
            position: absolute;
            right: 1rem;
            bottom: 0.5rem;
            line-height: 2rem;
        }

        .wrap-item .wi-cont > div:nth-child(2) > span {
            font-size: 0.8rem;
        }

        .wrap-item .wi-id {
            padding: 0 0.5rem 0.2rem;
        }

        .wrap-item .wi-id > div {
            color: #929292;
            font-size: 0.8rem;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        .wrap-item .wi-id > div:nth-child(1) {
            width: 30%;
        }

        .wrap-item .wi-id > div:nth-child(2) {
            width: 30%;
            float: left;
            margin-left: 2%;
        }
        .list-bottom{text-align:center;width: 100%;}
</style>
<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>	
<input type="hidden" id="page" value="${pageBean.page}"/>	
<input type="hidden" id="totalPage" value="${pageBean.totalPage}"/>	
<input type="hidden" id="account" value="${account}"/>	
	<div class="headly">
		<a href="javascript:;" onclick="goBack();" class="goback"><span><b></b><i></i></span></a>
	    <h3>${title }</h3>
    </div>
	<div class="wrap">
		<c:forEach items="${pageBean.result }" var="detail">
		<div class="wrap-item">
            <div class="wi-num">
                <div>${detail.date}</div>
                <div>订单号：${detail.orderId}</div>
            </div>
            <div class="wi-cont">
                <div>
                <c:if test="${detail.pNameList != null && fn:length(detail.pNameList) > 0 }">
                	  ${detail.pNameList[0] }
                </c:if>  
                 <c:if test="${detail.pNameList != null && fn:length(detail.pNameList) > 1 }">
                	  <br>
                	  ${detail.pNameList[1] }...
                </c:if>
                </div>
                <div><span>${detail.price}</span>红旗劵</div>
            </div>
           <c:if test="${account != 3 }">
           <div class="wi-id">
                <div>用户id：${detail.userId}</div>
                <div>用户姓名：${detail.userName}</div>
                <div>
                <c:if test="${account == 4 || account == 1 }">
                	备注： ${detail.memo}
                </c:if>
               </div>
            </div>
            </c:if>
        </div>
        </c:forEach>
	</div>
	<div class="list-bottom">
    	<a href="javascript:void(0);" onclick="more()">加载更多</a>
    </div>
</body>
<script>
(function(){
	isShowMore();
})();

function isShowMore(){
	var page = $("#page").val();
	var totalPage = $("#totalPage").val();
	if(totalPage == 0 || page == totalPage){
		$(".list-bottom").hide();
	}
}

function more(){
	var page = parseInt($("#page").val())+1;
	var account = $("#account").val();
	$.ajax({
		type: 'GET',
		url: '${path}/wealth/more',
		dataType: 'json',
		data:"page="+ page+"&account="+account,
		success: function(data) {
			$("#page").val(data.page);
			isShowMore();
			var result = [];
			for(var i in data.result){
				result.push("<div class=\"wrap-item\">");
				result.push("<div class=\"wi-num\">");
				result.push("<div>"+data.result[i].date+"</div>");
				result.push("<div>订单号："+data.result[i].orderId+"</div>");
				result.push("</div>");
				
				result.push("<div class=\"wi-cont\">");
				result.push("<div>");
				if(data.result[i] && data.result[i].length > 0){
					result.push(data.result[i].pNameList[0]);
				}
				if(data.result[i] && data.result[i].length > 1){
					result.push("<br>");
					result.push(data.result[i].pNameList[1]);
					result.push("...");
				}
				result.push("</div>");
				
				result.push("<div><span>"+data.result[i].price+"</span>红旗劵</div>");
				result.push("</div>");
				
				if(account != 3){
					result.push("<div class='wi-id'>");
					result.push("<div>用户id："+data.result[i].userId+"</div>");
					result.push("<div>用户姓名："+data.result[i].userName+"</div>");
					if(account == 4 || account == 1){
						result.push("<div>备注："+data.result[i].memo+"</div>");
					}
					result.push("</div>");
					result.push("</div>");
				}
			}
			$('.wrap').append(result.join(""));
		},
		error: function() {
		}
	});
}
</script>
</html>