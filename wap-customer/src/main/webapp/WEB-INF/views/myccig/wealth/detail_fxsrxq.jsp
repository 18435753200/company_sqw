<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
    	<meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
    	<%@include file="/WEB-INF/views/commons/base.jsp" %>
		<link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/grzxcss/aui.css" />
		<title>交易详情</title>
		<style>
			.aui-list .aui-list-header {
    background-color: #ffffff;
    color: #212121;
        border-bottom: 1px solid #eaeaea;
    position: relative;
    font-size: 0.6rem;
    padding: 0.4rem 0.75rem;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    display: -webkit-box;
    display: -webkit-flex;
    display: flex;
    -webkit-box-pack: justify;
    -webkit-justify-content: space-between;
    justify-content: space-between;
    -webkit-box-align: center;
    -webkit-align-items: center;
    align-items: center;
}
.aui-list .aui-list-item-right, .aui-list-item-title-row em {
    max-width: 50%;
    position: relative;
    font-size: 0.7rem;
   
}
		</style>
	</head>
	<body>
		<header class="aui-bar aui-bar-nav">
        	<a class="aui-pull-left aui-btn" href="javascript:;" onclick="goBack();">
            	<span class="aui-iconfont aui-icon-left"></span>
        	</a>
        	<div class="aui-title">交易详情</div>
		</header>
<section class="aui-content" style="width: 94%;margin: 0 3%;border-radius: 5px    ;position: relative;  top: 0.5rem;box-shadow: 2px 2px 2px #efefef;">
        <ul class="aui-list aui-list-noborder">
            <div class="aui-list-header">
                    <div class="aui-list-item-title"><img src="${path }/commons/img/grzxtp/fxsrxq.png" style="width: 30px ;   display: inline-block;  vertical-align: middle;"><b style="    line-height: 1.5rem;
    margin-left: 0.2rem;position: relative;top: 0.1rem;">分润收入</b></div>
                    <div class="aui-list-item-right" style=" color: #e60012; margin-left: 0.25rem;font-size: 1rem; font-weight: 500;">￥${detailByRecordId.earning }</div>
            </div>
        </ul>
    </section>
    <section class="aui-content" style="width: 94%;margin: 0 3%;border-radius: 5px    ;position: relative;  top: 0.8rem;box-shadow: 2px 2px 2px #efefef;">
        <ul class="aui-list aui-list-noborder">
          <div class="aui-list-header">
                    <div class="aui-list-item-title">订单号</div>
                    <div class="aui-list-item-right">${detailByRecordId.orderId }</div>
            </div>
            <div class="aui-list-header">
                    <div class="aui-list-item-title">日期</div>
                    <div class="aui-list-item-right"><fmt:formatDate value="${detailByRecordId.time }" pattern="yyyy-MM-dd HH:mm:ss"/></div>
            </div>
            <div class="aui-list-header">
                    <div class="aui-list-item-title">订单金额</div>
                    <div class="aui-list-item-right">￥${detailByRecordId.price }</div>
            </div>
            <div class="aui-list-header">
                    <div class="aui-list-item-title">商家编号</div>
                    <div class="aui-list-item-right">${detailByRecordId.supplierCode }</div>
            </div>
            <div class="aui-list-header">
                    <div class="aui-list-item-title">商家名称</div>
                    <div class="aui-list-item-right">${detailByRecordId.supplierName }</div>
            </div>
            <div class="aui-list-header">
                    <div class="aui-list-item-title">地区</div>
                    <div class="aui-list-item-right">${detailByRecordId.supplierProvince }&nbsp;${detailByRecordId.supplierCity }&nbsp;${detailByRecordId.supplierCounty }</div>
            </div>
            <div class="aui-list-header">
                    <div class="aui-list-item-title">地址</div>
                    <div class="aui-list-item-right">${detailByRecordId.address }</div>
            </div>
            <div class="aui-list-header">
                    <div class="aui-list-item-title">消费者ID</div>
                    <div class="aui-list-item-right">${detailByRecordId.userId }</div>
            </div>
            <div class="aui-list-header">
                    <div class="aui-list-item-title">消费者昵称</div>
                    <div class="aui-list-item-right">${detailByRecordId.userNickName }</div>
            </div>
        </ul>
    </section>
	</body>
</html>
  <!-- js -->
  <script src="${staticFile_s}/commons/js/bscroll.js"></script>
  <script type="text/javascript" src="${staticFile_s}/commons/js/main.js"></script>
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
</script>
</html>