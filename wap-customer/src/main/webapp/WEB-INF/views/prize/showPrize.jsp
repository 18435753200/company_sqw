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
                    <input name="address_autor" id="address_autor" type="text" readonly class="text-p" value=" ${prizeRecorddto.ownName}">
                </li>
                <li>
                    <label>手机号</label>
                    <input name="adress_phone" id="adress_phone" type="text" class="text-p" readonly value="${prizeRecorddto.ownPhone}">
                </li>
                <li>
                    <label>所在地区</label>
                    <select name="provinceId" id="provinceId" class="select" disabled >
                        <option value="${proviName}" selected>${proviName}</option>
                    </select>
                    <select name="cityId" id="cityId" class="select" disabled>
                        <option value="${cityName}" selected>${cityName}</option>
                    </select>
                    <select name="areaId" id="area_id" class="select" disabled>
                        <option value="${areaName}" selected>${areaName}</option>
                    </select>
                </li>
                <li>
                    <label>收货地址</label>
                    <input name="adress_datil" id="adress_datil" type="text" class="text-p" value="${prizeRecorddto.aderess}" readonly>
                </li>
                <li class="last">
                    <!--领奖后状态-->
                    <c:if test="${prizeRecorddto.status eq '3'}">
                        <span class="in-butno"  id="pasPrize">奖品已过期</span>
                    </c:if>
                    <c:if test="${prizeRecorddto.status eq '1'}">
                        <span class="in-butno" id="waite"  >请耐心等待奖品送达~</span>
                    </c:if>
                    <c:if test="${prizeRecorddto.status eq '2'}">
                        <span class="in-butno" id="waite"  >请耐心等待奖品送达~</span>
                    </c:if>
                </li>
            </ul>
    </form>
    <p class="tel">客服电话：<span>010-52675854</span></p>
    <p>中奖后3天内不领取，奖品将过期失效</p>
</div>
<!-- js相关内容 -->
<script type="text/javascript" src="${path}/commons/js/common/common.js"></script>
<script type="text/javascript" src="${path}/commons/js/jqueryAlert/zepto.alert.js"></script>
<script type="text/javascript" src="${path}/commons/js/zepto.min.js"></script>
<script src="${path}/commons/js/main.js"></script>
<script type="text/javascript" src="${path}/commons/js/jquery.min.js"></script>
</body>
</html>