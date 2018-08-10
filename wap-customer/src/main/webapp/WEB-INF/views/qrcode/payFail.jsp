<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
    <meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
    <title>交易记录</title>
    <link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/pay_jp_css/aui.css" />
    <style>
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
            font-size: 0.6rem;
            color: #454545;
            margin-left: 0.25rem;
        }
        .aui-list .aui-list-item-title {
            font-size: 0.9rem;color: #747474;}
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
        .aui-content-padded {text-align: center;position: fixed;bottom: 50%;top: 10%;}
        .aui-content-padded h1{line-height: 2rem;font-weight: 500;margin-bottom: 1rem;}
        .aui-content-padded h2{line-height: 2rem;font-size: 1.1rem;font-weight: 500;    color: gray;}
        .aui-content-padded b{font-size: 1rem;}
        img{display: inline;width: 30%;}
        p{    font-weight: 600;}
    </style>
</head>
<body>
<header class="aui-bar aui-bar-nav aui-bar-light">
    <a class="aui-pull-left aui-btn">
        <span class="aui-iconfont aui-icon-left"></span>
    </a>
    <div class="aui-title">交易记录</div>
</header>
<section class="aui-content">
    <ul class="aui-list aui-list-in">

    </ul>
</section>
<section class="aui-content-padded">
    <h1><img src="${staticFile_s}/commons/img/pay/zfsb.png"/><p>支付失败</p></h1>
    <h2>
        对不起,您当前订单未成功支付,请返回重新下单,或者直接返回
    </h2>
   <%-- <div class="aui-btn aui-btn-danger aui-btn-block" style="margin-top: 2rem;">知道了</div>--%>
</section>

</body>
</html>
