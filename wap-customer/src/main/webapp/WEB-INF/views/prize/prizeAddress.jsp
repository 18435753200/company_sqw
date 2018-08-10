<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";

    request.setAttribute("path", path);
    request.setAttribute("basePath", basePath);
%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <link rel="shortcut icon" type="image/x-icon" href="${path}/commons/img/favicon.ico" />
    <title>领奖</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <link rel="stylesheet" href="${path}/commons/css/newbase.css">
    <link rel="stylesheet" href="${path}/commons/css/draw.css">
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/zepto.alert.css">
</head>

<body>
<input id="path" type="hidden" value="<%=path%>">
<div class="header">
    <a href="javascript:history.go(-1)" class="bug-go"></a>
    领奖
</div>
<div class="draw">
    <form action="" method="get">
        <ul class="draw-box">
            <li>
                <label>收货人</label>
                    <input name="address_autor" id="address_autor" type="text" class="text-p" >
            </li>
            <li>
                <label>手机号</label>
                <input name="adress_phone" id="adress_phone" type="text" class="text-p" maxlength="11">
            </li>
            <li>
                <label>所在地区</label>
                <select name="provinceId" id="provinceId" class="select"onchange="getCity()" >
                    <option value="">请选择</option>
                </select>
                <select name="cityId" id="cityId" class="select" onchange="getArea()" >
                    <option value="">请选择</option>
                </select>
                <select name="areaId" id="area_id" class="select" >
                    <option value="">请选择</option>
                </select>
            </li>
            <li>
                <label>收货地址</label>
                <input name="adress_datil" id="adress_datil" type="text" class="text-p">
            </li>
            <li class="last">
                <!--确认领奖-->
                <span id="linj"><input name="gprize" id="gprize" type="button" onclick="savePrize()" value="确认领奖" class="in-but" ></span>
                <!--过期状态-->
                <span style="display:none;" id="pasPrize">已过期</span>
                <!--领奖后状态-->
                <span class="in-butno" id="waite" style="display:none;" >请耐心等待奖品送达~</span>
            </li>
        </ul>
    </form>
    <p class="tel">客服电话：<span>010-60646987</span></p>
    <p>中奖后3天内不领取，奖品将过期失效</p>
</div>
<input type="hidden" id="record_id" name="record_id" value="${recordId}">
<!-- js相关内容 -->
<script src="${path}/commons/js/zepto.min.js"></script>
<script src="${path}/commons/js/main.js"></script>
<script type="text/javascript" src="${path}/commons/js/common/common.js"></script>
<script type="text/javascript" src="${path}/commons/js/jqueryAlert/zepto.alert.js"></script>
<%--<script type="text/javascript" src="${path}/commons/js/jquery.min.js"></script>--%>
<script src="${path}/commons/js/prize/prizeAddress.js"></script>
</body>
</html>