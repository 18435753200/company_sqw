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
<script type="text/javascript" src="${staticFile_s}/commons/js/main.js"></script>
<title>${title }</title>
<link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/grzxcss/aui.css" />
<link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/grzxcss/index.css" />
<link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/grzxcss/grzx_djq_css.css" />
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>
<style>
body{font-family:"微软雅黑";}		
a{color:#fff}
.yexs{position:fixed;width:100%;margin:0 auto}

.aui-card-list-header {
    color: #AFAFAF;
    font-size: 0.7rem;
    width: 100%;
}
.aui-info-item {
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    display: -webkit-box;
    display: -webkit-flex;
    display: flex;
    -webkit-box-align: center;
    -webkit-align-items: center;
    align-items: center;
    
    font-size: 0.6rem;
}
.list-wrapper {
    position: fixed;
    z-index: 1;
    top:6.5rem;
    bottom: 0px;
    left: 0;
    width: 100%;
    background: #f9f9f9;
    overflow: hidden;
    border-top: 1px solid #eaeaea;
}
.fxedd {
    font-size: 0.2rem;
    color: #464444;
    background: #ff0000;
    border-radius: 10px;
    line-height: 1rem;
    float: left;
    margin: 0;
    padding: 0.2rem 0.5rem;
    position: fixed;
    top: 5.5rem;
    right: 1rem;
}
.fxedd a{color:#fff}
.aui-card-list {
    position: relative;
    margin: 0;
    padding: 1rem 0;
    border-bottom: 1px solid #e4e4e4;
    background: #ffffff;
    width: 100%;
}
.aui-card-list {
    position: relative;
    margin: 0;
    padding: 0.2rem 0;
    border-bottom: 1px solid #f1f1f1;
    background: #ffffff;
    width: 100%;
}
.jyjl{width: 100%;     text-align: center;   background: #f9f9f9;padding: 0.5rem 0;    font-family: 微软雅黑;border-top: 1px solid #f1f1f1;position: relative;
    top: 2rem;}
.jyjl_xx{line-height: 1.6rem;font-weight: 500;font-size: 0.8rem;}
.jyjl_rjs{float: right;width: 27%;color: #757575;    padding-bottom: 1rem;     padding-top: 1.5rem;   padding-right: 0.5rem;  font-size: 0.7rem;   text-align: right;}
.djqye {color: red;font-size: 1.4rem;text-align: center; margin: 0; padding: 3rem; padding-bottom: 1rem; background: #fff;}
.list-item{margin-bottom:0.3rem}
.jyxq_n{    font-size: 0.7rem;
    color: #565656;
    width: 92%;
    margin: 0 4%;
    margin-top: 2%;
    border-top: 1px solid #eaeaea;
    line-height: 2rem;}
 .text{
  border: none;
    background-color: transparent;
    border-radius: 0;
    box-shadow: none;
    display: inline;
    padding: 0;
    width:40%;
    text-align: center;
    margin: 0;
    height: 2.2rem;
    line-height: normal;
    color: #424242;
    font-size: 0.8rem;
    font-family: inherit;
    box-sizing: border-box;
    -webkit-user-select: text;
    user-select: text;
    -webkit-appearance: none;
    appearance: none;
    margin-top:1rem;
 }
</style>
<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>	
<input type="hidden" id="page" value="${pageBean.page}"/>	
<input type="hidden" id="totalPage" value="${pageBean.totalPage}"/>	
<input type="hidden" id="today" value="${today}"/>
<header class="aui-bar aui-bar-nav">
        <a class="aui-pull-left aui-btn" href="${path }/wealth/tosupplierShouyinList">
            <span class="aui-iconfont aui-icon-left"></span>
        </a>
        <div class="aui-title">${title }</div>
</header>
<div class="yexs">
<div class="jyjl">
		<h4 class="jyjl_xx" >
		<p><input type="date"  class="text"  name="birth" id="birth" value="<fmt:formatDate value='${today}' pattern='yyyy-MM-dd' />" placeholder="请选择年月日" style="padding-top: 0.5rem;
    padding-left: 1rem;"/></p>
		<p style="position: relative;   top: -0.3rem;">收银金额:<b style="color:#e60012;font-size: 0.9rem;">${supplierAndUserAndDay }</b></p></h4>
		<input type="hidden" name="id" value="${id }" id="id">
</div>
</div>
<div class="list-wrapper list-wrapper-hook">
    <div>
     <div class="top-tip">
        <span class="refresh-hook">下拉刷新</span>
      </div>
      <!-- 列表 -->
      <ul class="list-content list-content-hook" id="thelist">
      <c:forEach items="${pageBean.result }" var="detail" varStatus="var">
         <li class="list-item">
          	<div class="aui-card-list">
	    		<div class="aui-card-list-header">
	                 <div class="aui-info-item"><span class="aui-margin-l-0"><fmt:formatDate value="${detail.recordTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></div>
	                          <div class="aui-info-item" style="text-align:right">交易号：${detail.orderId}</div>
	            </div>
	            <div class="aui-card-list-content-padded">
					<b style="color:#000;font-weight: 500;font-size: 0.8rem">${detail.cashierName}</b>
	                   <b style="margin-left: 1rem;font-weight:500;color:#b4b5b4">${detail.cashierMobile}</b>
	                   <b style="color:#000;font-weight: 500;font-size: 1.3rem; float: right;line-height: 1;">${detail.orderPrice }</b>
	            </div>
			</div>
         </li>
 </c:forEach>
      </ul>
      <!-- 
        1、底部提示信息 
        2、这里有一种情况,就是没有更多数据时,这里的文本应该显示"暂无更多数据"
      -->	 
      <div class="bottom-tip">
        <span class="loading-hook">上拉加载更多</span>
      </div>
    </div>
  </div>
  <!-- content end  -->

  <!-- footer -->
 
  <!-- alert -->
  <div class="alert alert-hook">刷新成功</div>
  </body>
</html>
  <!-- js -->
  <script src="${staticFile_s}/commons/js/bscroll.js"></script>
  <script src="${staticFile_s}/commons/js/index_shouYinJiLu.js"></script> 
  <script type="text/javascript" src="${staticFile_s}/commons/js/main.js"></script>
  <script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>  	
<script>

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
$("#birth").live("change", function() {
	var startTime = $("#birth").val();
	var id = $("#id").val();
	/* var mydate = new Date();
	var endTime = mydate.toLocaleDateString(); // 获取当前日期
	if (!checkEndTime(startTime, endTime)) {
		showErrorMsg("日期不能大于当前时间");
		return;
	}; */
	window.location ="${path }/wealth/supplierMoneyJL?day="+startTime+"&id="+id; 
});
//比较日期
function checkEndTime(begin, over) {
var start = new Date(begin.replace("-", "/").replace("-", "/"));
var end = new Date(over.replace("-", "/").replace("-", "/"));
if (end < start) {
	return false;
} else {
	return true;
}

}
function showErrorMsg(str) {
	$.dialog({
		content : str,
		title : '众聚猫提示',
		time : 1000,
	});
	return;
}
</script>
</html>