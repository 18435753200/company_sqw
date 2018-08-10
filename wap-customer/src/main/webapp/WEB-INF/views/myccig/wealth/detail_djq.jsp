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
<%-- zepto alert --%>
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
.aui-card-list {
    position: relative;
    margin: 0;
    padding: 1rem 0;
    border-bottom: 1px solid #e4e4e4;
    background: #ffffff;
    width: 100%;
}
</style>
<body>
<%-- hidden域 --%>

<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>	
<input type="hidden" id="page" value="${pageBean.page}"/>	
<input type="hidden" id="totalPage" value="${pageBean.totalPage}"/>	
<input type="hidden" id="account" value="${account}"/>
<header class="aui-bar aui-bar-nav">
        <a class="aui-pull-left aui-btn" href="javascript:;" onclick="goBack();" class="goback">
            <span class="aui-iconfont aui-icon-left"></span>
        </a>
        <div class="aui-title">${title }</div>
</header>
<div class="yexs">
<div class="djqye"><b>余额:</b>${accountBalance }</div>
<h4 style="text-align: center;background: #fff;border-bottom: 1px solid #F7F4F4;line-height: 2rem;font-size: 0.8rem;">交易记录</h4>
</div>
<a href="javascript:;" onclick="toTransferIndex();" style="color:#fff"><div class="aui-btn aui-btn-danger aui-btn-block">转账</div></a>
<div class="list-wrapper list-wrapper-hook">
    <div>
      <!-- 顶部提示信息 -->
      <div class="top-tip">
        <span class="refresh-hook">下拉刷新</span>
      </div>
      <!-- 列表 -->
      <ul class="list-content list-content-hook" id="thelist">
      <c:forEach items="${pageBean.result }" var="detail" varStatus="var">
        <li class="list-item">
          <div class="aui-card-list">
       		            <div class="aui-card-list-header">
			                 <div class="aui-info-item"><span class="aui-margin-l-0"><fmt:formatDate value="${detail.operatorTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></div>
                             <div class="aui-info-item" style="text-align:right">交易号：${detail.refObjId}</div>
			            </div>
			            <div class="aui-card-list-content-padded">
							<%-- <b style="font-weight: 500;">${detail.recordTypeString}</b> --%>
							<b style="color: red;margin-left:0.5rem;font-size: 0.8rem;font-weight: 500">
				                <c:if test="${detail.earning>0}">
				               	 	+<span>${detail.earning}</span>M券
				                </c:if>
				                <c:if test="${detail.expenditure>0}">
				               	 	-<span>${detail.expenditure}</span>M券
				                </c:if>
							</b>
							&nbsp;&nbsp;<b>余额：${detail.balance }</b>
			            </div>
			             <%-- <div class="aui-card-list-content-padded">
							<b  style="color:#B8B8B8;font-weight: 500;font-size: 0.6rem">用户ID：</b>
		                    <b style="margin-left: 1rem;font-weight:500">${detail.userId}</b>
		                    <b  style="color:#000;font-weight: 500;font-size: 0.6rem; float: right;">用户名称：${detail.name}</b>
			            </div> --%>
			            <div class="aui-card-list-content-padded">
			               <b>备注:</b>
			               <b style="color:#B8B8B8;font-weight: 500;padding-left:0.5rem;font-size: 0.6rem">${detail.memo}</b>
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
  <div class="alert alert-hook" style="display: none;">刷新成功</div>
  </body>
</html>
  <!-- js -->
  <script src="${staticFile_s}/commons/js/bscroll.js"></script>
  <script src="${staticFile_s}/commons/js/index.js"></script>   	
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
function toTransferIndex(){
	
	var state = '${state}';
		window.location = "${path}/wealth/transferIndex_new";
}

</script>
</html>