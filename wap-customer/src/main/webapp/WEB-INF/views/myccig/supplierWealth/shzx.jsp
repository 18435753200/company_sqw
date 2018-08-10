<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<%@include file="/WEB-INF/views/commons/base.jsp" %>
<%-- <script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/person.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/common/common.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/main.js"></script> --%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>商家中心</title>
<link rel="stylesheet" href="${staticFile_s}/commons/css/public.css">
<link rel="stylesheet" href="${staticFile_s}/commons/css/shzx.css">
<style type="text/css">
	img {
		max-width: 100%;
		display:inline;
	}
	p{color:#000}
</style>
</head>
<body>
<header class="header">
    <a href="${path }/customer/toMy" class="go_back" ></a>
    <div class="title">商家中心</div>
</header>
<div class="content">
    <div class="banner">
        <img src="${path }/commons/img/shzxtp/banner.png">
    </div>

    <div class="navList1">
        <div class="h3">我的钱包</div>
        <div class="ul">
            <ul>
                <li><a href="<%=path %>/wealth/detail_shzxddsr"><img src="${path }/commons/img/shzxtp/ddsr.png"/><p>订单收入</p></a></li>
                <li style="border-right: 0;"><a href="${path}/wealth/detail_shzxdjq"><img src="${path }/commons/img/shzxtp/djq.png"/><p>M券</p></a></li>
            </ul>
        </div>
    </div>
    <div class="navList3">
        <div class="h3">业务中心</div>
        <ul>
            <li><a href="${path }/cusOrder/toMyAllSupplierOrder?pageNow=1&status="><img src="${path }/commons/img/shzxtp/u1.png"/><p>门店订单</p></a></li>
            <li><a href="${path }/Qr/shqrCode?rcodeType=2"><img src="${path }/commons/img/shzxtp/code_03.png"/><p>收款二维码</p></a></li>
            <li style="border-right: 0;"><a href="${path }/wealth/tosupplierUsers"><img src="${path }/commons/img/shzxtp/u3.png"/><p>我的用户</p></a></li>
            <li style="border-bottom: 1px solid #e6e6e6;"><a href="${path }/wealth/tolistBusinessPartner"><img src="${path }/commons/img/shzxtp/ywhb.png"/><p>业务伙伴</p></a></li>
            <c:if test="${user.type eq 12}" >
            <li style="border-right: 1px solid #e6e6e6;border-bottom:1px solid #e6e6e6"><a href="${path }/wealth/tosupplierShouyinList"><img src="${path }/commons/img/shzxtp/syy.png"/><p>收银员</p></a></li> 
            <li style="border-bottom:1px solid #e6e6e6;"><a href="${path }/supplier/toMyStore"><img src="${path }/commons/img/mendian.png"/><p>门店管理</p></a></li>
            <li style="border-bottom: 1;"><a href="${path}/mallRegister/selectMall"><img src="${path }/commons/img/shzxtp/u4.png"/><p>商家信息</p></a></li>
            </c:if>
        </ul>
    </div>
    <p style="height:3rem">&nbsp;</p>
</div>
<div class="footer-m">
 <%@include file="/WEB-INF/views/commons/navigation.jsp"%>
</div>
</body>
</html>
<script src="${staticFile_s}/commons/js/rem.js"></script>