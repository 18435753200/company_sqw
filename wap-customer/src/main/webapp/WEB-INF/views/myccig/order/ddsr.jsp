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
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/person.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/common/common.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/main.js"></script>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>商户中心</title>
    <link rel="stylesheet" href="${staticFile_s}/commons/css/public.css">
    <link rel="stylesheet" href="${staticFile_s}/commons/css/ddsr.css">
</head>
<body>
<header class="header">
    <a href="javascript:;" class="go_back" onclick="goBack();"></a>
    <div class="title">订单收入</div>
</header>
<div class="content">
    <ul class="moneyList">
        <li>
            <img src="../commons/images/sr_03.png" alt="">
            <p>收入金额</p>
            <span>￥${ddzsr }</span>
            <a href="${path}/wealth/detail_srje" class="next"></a>
        </li>
        <li>
            <img src="../commons/images/yjs_08.png" alt="">
            <p>已结算金额</p>
            <span>￥${ddyjs }</span>
            <a href="${path}/wealth/detail_yjsje" class="next"></a>
        </li>
        <li>
            <img src="../commons/images/wjs_06.png" alt="">
            <p>未结算金额</p>
            <span>￥${accountBalance}</span>
            <a href="${path}/wealth/detail_wjsje" class="next"></a>
        </li>
    </ul>
</div>
</body>
</html>
<script src="${staticFile_s}/commons/js/rem.js"></script>