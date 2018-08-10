<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mall.supplier.model.Supplier" %>
<%@include file="/WEB-INF/views/commons/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <meta name="x5-orientation" content="portrait">
    <meta name="browsermode" content="application">
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />

    <title>付款</title>
    <link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/pay_jp_css/weui.min.css">
    <link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/pay_jp_css/showTips.css">
    <link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/pay_jp_css/spay_scan.css">
    <link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/pay_jp_css/aui.css">
    <link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/pay_jp_css/style.css">
    <style>
        .sydq{color:#E60012;
            float:right;
        margin-right: 1rem}
        .ye{
            color: #E60012;
        }
    </style>

</head>
<% Supplier supplier = (Supplier) request.getAttribute("supplier");%>
<body>
<div class="container swiper-container-vertical ">
    <div id="z-top" style="padding-top: 1px; width: 100%; height: 100%; background: #000; opacity: 0.9; z-index: 99999; color: #fff; font-size: 1rem; position: fixed; top: 0px; left: 0px; display:none;">
        <p style="width:266px;margin: 15% auto; color: #fff;">您将手机拿正轻晃至竖屏显示</p>
    </div>
<div  class="layout-flex" >
    <input type="hidden" id="qrCodeId" value="${qrCodeId}">
    <input type="hidden" id="userId" value="${userId}">
    <input type="hidden" id="openId" value="${openId}">
    <input type="hidden" id="payType" value="${payType}">
    <input type="hidden" id="supplierId" value="<%=supplier.getSupplierId()%>">
    <input type="hidden" id="timestamp" value="${timestamp}">
    <input type="hidden" id="staticFile_s" value="${staticFile_s}">
    <input type="hidden" id="accountBalance" />

    <header class="aui-bar aui-bar-nav aui-bar-light">
        <a class="aui-pull-left aui-btn"><span class="aui-iconfont aui-icon-left"></span></a>
        <div class="aui-title">向商户付款</div>
    </header>
    <div class="content">
        <div class="amount_title">
            <h4>付款给商户</h4>
            <span id="companyShortName" name="companyName">名称:<%=supplier.getNameJC() %></span>
            <img style="" src="${staticFile_s}/commons/img/pay/store.png">
        </div>
        <div class="set_amount">
            <%--<font id="totalPrice" class="totalPrice">金额：</font>--%>
            <div class="amount_bd">
                <span class="i_money">¥</span>
                <span class="input_simu" id="amount"></span>
                <span class="line_simu" id="line"></span>
            </div>
        </div>
        <div id="paySumpayContent" class="paySumpayContent" style="display: none;">
			<span>
				<font>实付</font>
					<code id="payTotalPrice" name="payTotalPrice">0</code>
					<font>元</font>，<font>奖</font>
					<code id="returnCoupon" name="returnCoupon">0</code>
					<font>M券</font>
			</span>
        </div>
        <div id="payDiscountpayContent" class="payDiscountpayContent" style="display: none;">
			<span>
				<font>实付</font>
					<code id="payDiscountPrice" name="payDiscountPrice">0</code>
					<em>元</em><b>＋</b>
					<code id="payCoupon" name="payCouwepon">0</code>
					<em>M券</em>
			</span>
        </div>
        <div id="selectPay" class="selectPay" style="display: block;">
			<span>
				请选择付款方式
			</span>
        </div>
        <div class="weui-cells weui-cells_checkbox">
            <label class="weui-cell weuiCellS11 weui-check__label" for="s11" id="weuiCellS11" >
                <div class="weui-cell__hd">
                    <input type="radio" class="weui-check" name="checkbox1" id="s11" value="03">
                    <i class="weui-icon-checked" ></i>
                </div>
                <div >
                    <p>全现金付款</p>
                </div>
            </label>
            <label class="weui-cell weuiCellS12 weui-check__label" for="s12" id="weuiCellS12">
                <div class="weui-cell__hd">
                    <input type="radio" class="weui-check" name="checkbox1" id="s12" value="04">
                    <i class="weui-icon-checked"></i>
                </div>
                <div class="weui-cell__bd">
                    <p>M券优惠付款<b id="sydq" class="sydq">余额：<b id="mq" class="ye">0</b> M券</b></p>
                </div>
            </label>
        </div>
    </div>
    <div class="keyboard">
        <table class="key_table" id="keyboard" style="touch-action: pan-y; user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
            <tbody>
            <tr>
                <td class="key border b_rgt_btm b_top_btm" data-value="1" name="num">1</td>
                <td class="key border b_rgt_btm b_top_btm" data-value="2" name="num">2</td>
                <td class="key border b_rgt_btm b_top_btm" data-value="3" name="num">3</td>
                <td class="key border b_btm b_top_btm clear" id="detNum" data-value="delete"  name="num" style="touch-action: pan-y; user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></td>
            </tr>
            <tr>
                <td class="key border b_rgt_btm" data-value="4" name="num">4</td>
                <td class="key border b_rgt_btm" data-value="5" name="num">5</td>
                <td class="key border b_rgt_btm" data-value="6" name="num">6</td>
                <td class="pay_btn disable" rowspan="3" id="payBtn" style="touch-action: pan-y; user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">付款</td>
            </tr>
            <tr>
                <td class="key border b_rgt_btm" data-value="7" name="num">7</td>
                <td class="key border b_rgt_btm" data-value="8" name="num">8</td>
                <td class="key border b_rgt_btm" data-value="9" name="num">9</td>
            </tr>
            <tr>
                <td colspan="2" class="key border b_rgt" data-value="0" name="num">0</td>
                <td class="key border b_rgt" data-value="dot">.</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
    <div class="pop_wrapper none"><div class="pop_outer"><div class="pop_cont"><div class="pop_tip"></div><p class="border b_top"><span class="pop_wbtn">我知道了</span></p></div></div></div>
</div>
</body>
</html>
<script type="text/javascript" src="${staticFile_s}/commons/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/pay_jp_js/zepto.min.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/pay_jp_js/showTips.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/pay_jp_js/hammer.min.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/pay_jp_js/jweixin-1.2.0.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/pay_jp_js/payMoney.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/pay_jp_js/detailedPayMoney.js"></script>
<script language="javascript">
        //防止页面后退
        history.pushState(null, null, document.URL);
        window.addEventListener('popstate', function () {
            history.pushState(null, null, document.URL);
        });

        window.addEventListener("onorientationchange" in window ? "orientationchange" : "resize", function() {
            if (window.orientation === 180 || window.orientation === 0) {
                $("#z-top").css("display","none");
            }

            if (window.orientation === 90 || window.orientation === -90 ){
                $("#z-top").css("display","block");
            }
        }, false);
</script>