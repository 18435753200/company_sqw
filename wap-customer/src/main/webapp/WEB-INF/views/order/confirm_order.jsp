<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<%@ include file="/WEB-INF/views/order/commons/confirm_order_base.jsp"%>
<title><spring:message code="title_confirm_order" /></title>
<link rel="stylesheet" href="${staticFile_s}/commons/css/gwcjs.css" type="text/css">
<%-- <%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %> --%>
<style>

</style>
</head>

<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<div id="confirmOrder">
	<div class="header head-bg">
		<a href="javascript:goBack();" class="bug-go goback"></a>
		<a href="${path}/" class="home-go"></a>
		结算
	</div>
<div class="wrap order-wrap">
<%@ include file="/WEB-INF/views/order/address/address.jsp"%>
<%@ include file="/WEB-INF/views/order/pay_type/pay_type.jsp"%>
<%@ include file="/WEB-INF/views/order/item/item.jsp"%>
<%-- <%@ include file="/WEB-INF/views/order/invoice/invoice.jsp"%> --%>

</div>
<%-- <div class="navbar navbar-fixed-bottom order-bar">
    <div class="orderbar-total">
    	<input type="hidden" id="sumPrice" value="${cartDTO.sumPrice}"/>
        <p class="order-total">合计：
        	<span class="price">
        		<em> </em>${cartDTO.sumPrice}
        	</span>
        </p>
        <span class="order-price">参考价：<del>${cartDTO.sumDomesticPrice}</del></span></div> 
    <input type="button" value="提交订单" class="order-btn">
</div> --%>

<input type="hidden" id="laiyuanflag" value="${laiyuanflag }"/>
<div class="cart-zc cart-zcno order-bar2">
		    <div class="cart-bt">
				<p class="total-fore1">
				    <span>合计：</span><em> </em>
					<b>
						${cartDTO.sumPrice}
					</b>
				</p>
				<a href="javascript:;" class="order-btnt">提交订单</a>
			</div>
</div>
</div>

<%--商品列表--%>
<%@ include file="/WEB-INF/views/order/item/itemList.jsp"%>

<div id="addressList"></div>
<div id="couponsList"></div>

<div id="useCouponsList">
	<input type="hidden" value=""/>
</div>

<%@ include file="/WEB-INF/views/commons/window_msg.jsp"%>
<%--底部--%>
<%-- <%@ include file="/WEB-INF/views/commons/navigation.jsp"%> --%>
<input type="hidden" id="user_name_val" value="${username}"/>
</body>
</html>