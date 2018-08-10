<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
    <meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />
    <meta name="x5-orientation" content="portrait">
    <link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/pay_jp_css/aui.css"/>
    <style>
        a{text-decoration: none}
        .footer li {
            width: 20%;
            float: left;
            margin-top: 5px;
            list-style: none;
        }
        *{margin: 0;padding: 0;font-size: 1rem;}
        .aui-bar.aui-bar-light {
            border: none;
            background: #e60012;
        }
        .aui-bar-nav .aui-btn {
            position: relative;
            z-index: 20;
            padding-top: 0;
            padding-bottom: 0;
            margin: 0;
            line-height: 3rem;
            height: 3rem;

        }.aui-bar-nav .aui-title {

             line-height: 3rem;
             color: #fff;
         }
        .aui-list .aui-list-item-right, .aui-list-item-title-row em {
            max-width: 50%;
            position: relative;
            font-size: 0.9rem;
            color: #454545;
            margin-left: 0.25rem;
        }
        .aui-list .aui-list-item-title {
            font-size: 0.9rem;
            color: #747474;
            width: 30%;}
        .aui-bar-nav .aui-btn .aui-iconfont {
            font-size: 1rem;
            line-height: 3rem;
            padding: 0;
            margin: 0;
            color: #ffffff;
        }
        .aui-list .aui-list-item-label-icon {
            width: auto;
            padding-right: 0.75rem;
            height: 4rem;
        }
        .aui-content-padded {text-align: center;}
        .aui-content-padded h1{line-height: 2rem;font-weight: 500;}
        .aui-content-padded h2{line-height: 3rem;font-size: 2rem;font-weight: 500;}
        .aui-content-padded b{font-size: 1.5rem;}
    </style>
    <title>交易记录</title>
</head>
<body>
<header class="aui-bar aui-bar-nav aui-bar-light">
    <a class="aui-pull-left aui-btn" >
        <span class="aui-iconfont aui-icon-left"></span>
    </a>
    <div class="aui-title">交易记录</div>
</header>
<%  String targetSys = (String) request.getAttribute("targetSys");
    String payType = "";
    if ("WXPay".equals(targetSys)){
        payType = "微信支付";
    }else if ("Alipay".contains(targetSys)){
        payType = "支付宝支付";
    }else {
        payType = "其他支付";
    }
    String err = (String) request.getAttribute("err");
%>

<section class="aui-content">
    <ul class="aui-list aui-list-in">
        <li class="aui-list-item">
            <div class="aui-list-item-label-icon">
                <i class="aui-iconfont"><img src="${staticFile_s}/commons/img/pay/dg.png" style="width: 30px"></i>
            </div>
            <div class="aui-list-item-inner">
                <div class="aui-list-item-title">订单支付成功</div>
            </div>
        </li>
    </ul>
</section>
<section class="aui-content-padded">
    <h1>订单金额:</h1>
    <h2>
        <b>¥</b>${totalAmount}<b style="font-weight: 500">元</b>
    </h2>
</section>
<section class="aui-content">
    <% if (err == null){%>
    <ul class="aui-list">
        <li class="aui-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-title">实际支付</div>
                <div class="aui-list-item-right">${buyerPayAmount}</div>
            </div>
        </li>
        <a href="/wealth/detail_djq">
            <li class="aui-list-item">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-title">M券</div>
                    <div class="aui-list-item-inner aui-list-item-arrow">
                        <div class="aui-list-item-title"></div>
                        <div class="aui-list-item-right">${earning}</div>
                    </div>
                </div>
            </li>
        </a>
        <li class="aui-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-title">收款方</div>
                <div class="aui-list-item-right">${shopName}</div>
            </div>
        </li>
        <li class="aui-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-title ">支付方式</div>
                <div class="aui-list-item-right"><%=payType%></div>
            </div>
        </li>
        <li class="aui-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-title">订单编号</div>
                <div class="aui-list-item-right">${orderId}</div>
            </div>
        </li>
        <li class="aui-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-title">交易时间</div>
                <div class="aui-list-item-right">${payTime}</div>
            </div>
        </li>
    </ul>
    <%} %>
</section>
<div class="footer-m">
    <%@include file="/WEB-INF/views/commons/navigation.jsp"%>
</div>
</body>
</html>