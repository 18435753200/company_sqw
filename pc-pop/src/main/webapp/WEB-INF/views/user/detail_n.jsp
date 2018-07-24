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
              <div>
              	<fmt:formatDate value="${detail.operatorTime}" type="both" pattern="yyyy-MM-dd hh:mm"/>
              </div> 
                <div>订单号：${detail.refObjId}</div>
            </div>
          <div class="wi-cont">
                  <%-- <div>
                <c:if test="${detail.pNameList != null && fn:length(detail.pNameList) > 0 }">
                	  ${detail.pNameList[0] }
                </c:if>  
                 <c:if test="${detail.pNameList != null && fn:length(detail.pNameList) > 1 }">
                	  <br>
                	  ${detail.pNameList[1] }...
                </c:if>
                </div>--%>
                <div>
                <c:if test="${detail.earning>0}">
               	 	<span>${detail.earning}</span>代金券
                </c:if>
                <c:if test="${detail.expenditure>0}">
               	 	<span>${detail.expenditure}</span>代金券
                </c:if>
                
                </div>
            </div> 
           <c:if test="${account == 3 }">
           <div class="wi-id">
                <div>用户id：${detail.userId}</div>
                <%-- <div>用户姓名：${detail.userName}</div> --%>
                <div>
                <c:if test="${account == 3 || account == 5 }">
                	备注： ${detail.memo}
                </c:if>
               </div>
            </div>
            </c:if>
           <c:if test="${account == 5 }">
           <div class="wi-id">
                <div>用户id：${detail.userId}</div>
               <%--  <div>用户姓名：${detail.userName}</div> --%>
                <div>
                <c:if test="${account == 3 || account == 5 }">
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
	if(totalPage == 1 || page == totalPage){
		$(".list-bottom").hide();
	}
}

function more(){
	var page = parseInt($("#page").val())+1;
	var account = $("#account").val();
	$.ajax({
		type: 'POST',
		url: '${path}/wealth/more_n',
		dataType: 'json',
		data:"page="+ page+"&account="+account,
		success: function(data) {
			$("#page").val(data.page);
			isShowMore();
			var result = [];
			for(var i in data.result){
				result.push("<div class=\"wrap-item\">");
				result.push("<div class=\"wi-num\">");
				var dat=dateFtt("yyyy-MM-dd hh:mm:ss",new Date(data.result[i].operatorTime));
				result.push("<div>"+dat+"</div>");
				result.push("<div>订单号："+data.result[i].refObjId+"</div>");
				result.push("</div>");
				
				result.push("<div class=\"wi-cont\">");
				/* result.push("<div>");
				  if(data.result[i] && data.result[i].length > 0){
					result.push(data.result[i].pNameList[0]);
				}
				 if(data.result[i] && data.result[i].length > 1){
					result.push("<br>");
					result.push(data.result[i].pNameList[1]);
					result.push("...");
				}
				result.push("</div>");*/
				if(data.result[i].earning>0){
				result.push("<div><span>"+data.result[i].earning+"</span>代金券</div>");	
				}else{
				result.push("<div><span>"+data.result[i].expenditure+"</span>代金券</div>");
				}
				result.push("</div>");  
				
				if(account == 3){
					result.push("<div class='wi-id'>");
					result.push("<div>用户id："+data.result[i].userId+"</div>");
					/* result.push("<div>用户姓名："+data.result[i].userName+"</div>"); */
					if(account == 3 || account == 5){
						result.push("<div>备注："+data.result[i].memo+"</div>");
					}
					result.push("</div>");
					result.push("</div>");
				} 
				if(account == 5){
					result.push("<div class='wi-id'>");
					result.push("<div>用户id："+data.result[i].userId+"</div>");
					/* result.push("<div>用户姓名："+data.result[i].userName+"</div>"); */ 
					if(account == 3 || account == 5){
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