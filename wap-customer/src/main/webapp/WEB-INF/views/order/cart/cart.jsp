﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
		<meta content="telephone=no" name="format-detection">
		<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		<title><spring:message code="title_cart_index" /></title> 
		
		<!-- css -->
		<link href="${staticFile_s}/commons/css/newbese.css" rel="stylesheet" type="text/css">
		<link href="${staticFile_s}/commons/css/revision20160606/base.css" rel="stylesheet" type="text/css">
		<link href="${staticFile_s}/commons/css/newcart.css" rel="stylesheet" type="text/css">
		<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
		<link href="${staticFile_s}/commons/css/cart_gwc.css" rel="stylesheet" type="text/css">
		<%-- <link rel="shortcut icon" type="image/x-icon" href="${path}/commons/img/favicon.ico" /> --%>
		<c:set var="path" value="<%=path %>"/>
		
		<!-- js -->
		<script type="text/javascript" src="${staticFile_s}/commons/js/zepto.min.js"></script>
		<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
		<script type="text/javascript" src="${staticFile_s}/commons/js/cart/cart.js"></script>
		<script type="text/javascript" src="${staticFile_s}/commons/js/common/common.ajax.js"></script>
		<script type="text/javascript" src="${staticFile_s}/commons/js/main.js"></script>
		<script type="text/javascript" src="${staticFile_s}/commons/js/user/common.js"></script>
		<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
		<%@include file="/WEB-INF/views/commons/commonsIco.jsp" %>
		<style>
		#cartContent {
    position: relative;
    top: 4.6rem;
    z-index: 10;
}
.cart_no p a {
    font-size: 1.6rem;
    background: #e60012;
    color: #ffffff;
    width: 15rem;
    height: 3.5rem;
    line-height: 3.5rem;

    display: inline-block;
}
.cart-zc {
    width: 100%;
    height: 6.3rem;
    background: #fff;
    border-top: 1px solid #cbcbcd;
    position: fixed;
    bottom: 4.3rem;
    left: 0px;
}

.cart_no img {
    width: 12rem;
    height: 12rem;
}
.cart_no {
    text-align: center;
    width: 180px;
    height: 260px;
    position: absolute;
    left: 50%;
    top: 50%;
    margin: -80px 0px 0px -90px;
    top: 17.6rem;
}
.cart-group h2 {
    height: 3.8rem;
    width: 100%;
    border-bottom: 1px solid #ececf0;
    font-size: 1.5rem;
    line-height: 3.8rem;
    color: #000;
    padding: 0 1.5rem 0px 1.5rem;
    position: relative;
    font-weight: 600;
}
.goods-name {
    font-size: 14PX;
    color: #505050;
    height: 38px;
    overflow: hidden;
    padding-right: 0; 
     font-weight: 600;
}
.goods-price {
    position: absolute;
    top: 9rem;
    left: 1rem;
}
.goods-price span {
    font-size: 1.5rem;
    color: #e63232;
    font-weight: 600;
}
.goods-sku {
    margin: 0.5rem 0;
    height: 19px;
    display: -webkit-box;
    -webkit-line-clamp: 1;
    -webkit-box-orient: vertical;
    word-break: break-all;
   overflow: visible;
}

.cart-list li.cart-item {
    padding: 1rem;
    overflow: hidden;
    border-bottom: 1px solid #ececf0;
}
.amount-control {
    width: 9.1rem;
    height: 2.4rem;
    border: 1px solid #dedede;
    overflow: hidden;
    position: absolute;
    border-radius: 2px;
    bottom: -2.5rem;
    right: 0;
}
.amount-control a.amount-down {
    border-right: 1px solid #dedede;
        background: #f6f6f6;
}
.amount-control a.amount-up {
    border-left: 1px solid #dedede;
        background: #f6f6f6;
}
.cart-wen {
    height: 11.5rem;
}

		</style>
	</head>
    <body class="cart-body">
    	
	    <%--头部--%>

		<div class="header head-bg">
			<a href="javascript:goBack();" class="bug-go goback"></a>
			<a href="javascript:;" class="edit-go show-edit"
				<c:if test="${empty cartDTO}">
					style="display:none;"
				</c:if>
			>编辑</a>
			<a href="javascript:;" class="edit-go edit-finished" style="display:none;">完成</a>
			购物车
		</div>
		
		<div id="cartContent">
			
<%--未登陆状态--%>
<c:if test="${empty exitUser}">
	<div class="cart-not">
	    <div class="cart-bx">
			<p>您还未登录哦，登录后可以显示您之前加入的商品。</p>
			<a href="<%=request.getContextPath()%>/customer/toLogin">立即登录</a>
		</div>
	</div>
</c:if>
<%--购物车为空--%>
<c:if test="${empty cartDTO}">
	<%@ include file="/WEB-INF/views/order/cart/cartNull.jsp"%>
</c:if>

<c:if test="${not empty cartDTO}">
	<div class="cart-home">
				
				<c:forEach items="${cartDTO.cartGroupVOList }" var="cartGroupVO">
			<div class="cart-group">
				<h2 <c:if test="${cartGroupVO.groupProductType ne 1 and cartGroupVO.groupProductType ne 31}">class="hiawai"</c:if>>
					${cartGroupVO.title}
					<c:if test="${cartGroupVO.freeTransFeeTip ne 'default'}">
						<font style="float:right; margin-right:55px;">${cartGroupVO.freeTransFeeTip}</font>
					</c:if>
				</h2>
				<ul class="cart-list">
				    <%--促销活动--%>
				    <c:forEach items="${cartGroupVO.activityGroupList}" var="activityGroup" varStatus="activityGroupIndex" >
				     	<div class="cart-activity-group">
					     	<%-- 循环商品SKU --%>
				    		<c:forEach items="${activityGroup.skuList}" var="sku" varStatus="skuIndex">
				    		
				    			<c:if test="${skuIndex.index eq 0 and not empty sku.mainRuleId and sku.mainRuleId ne -3}">
								    <li class="cart-activity">
										<i>
											<c:choose>
												<c:when test="${sku.mainRuleTerm eq 102 or sku.mainRuleTerm eq 104}">满减</c:when>
												<c:when test="${sku.mainRuleTerm eq 100}">满赠</c:when>
												<c:when test="${sku.mainRuleTerm eq 101}">买赠</c:when>
												<c:when test="${sku.mainRuleTerm eq 103 or sku.mainRuleTerm eq 105}">直降</c:when>
											</c:choose>
										</i>${sku.mainRuleName}
									</li>
				    			</c:if>
								<%--商品列表--%>
								<li class="cart-item cart-bd" id="data_${sku.pid }_${sku.skuId }_${sku.price - sku.straightDownPrice}_${sku.price }_${sku.qty }_${sku.stockQty }_
					        			 ${cartGroupVO.sumPrice - cartGroupVO.transferPrice}_${sku.isSoldOut }_${sku.productType}_${cartGroupVO.sumTax}_${sku.subTotalPrice}">
								     <div class="cart-wen">
										<div class="check-box">
											<input type="checkbox" isSoldout=${sku.isSoldOut} 
												<c:if test="${sku.isSelect}">checked="checked"</c:if> name="sku-checkbox">
										</div>
										<div class="goods-pic">
											<a href="<%=request.getContextPath()%>/item/get/${sku.pid }">
												<img src="${sku.imgUrl }" width="100" height="100">
											</a>
											<%-- 下架商品样式 --%>
											<c:if test="${sku.isSoldOut eq 0}">
												<span class="yxj"></span>
											</c:if>
										</div>
										<div class="goods-info">
											<p class="goods-name">
												<a href="<%=request.getContextPath()%>/item/get/${sku.pid }">${sku.pName}</a>
											</p>
											<p class="goods-sku">
												<span>${sku.skuName}</span>
											</p>
											<p class="goods-price">
												<span> ${sku.price - (sku.straightDownPrice < 0 ? 0 : sku.straightDownPrice)} </span>
											</p>
											<div class="amount-control">
												<a class="amount-down" href="javascript:;">—</a>
												<input type="text" value="${sku.qty }" autocomplete="off" class="amount-input" name="" maxlength="10">
												<a class="amount-up" href="javascript:;">+</a>
											</div>
										</div>
									</div>
									<%-- <c:if test="${not empty sku.ruleList and fn:length(sku.ruleList) gt 1}">
										<div class="promotion">
										  <!-- <h3 class="pro-down">促销优惠<i></i></h3>
											 <h3 class="pro-up" style="display:none;">促销优惠<i></i></h3> -->
											 <div class="promotion-list">
											 	<c:forEach items="${sku.ruleList }" var="rule">
												 	<P>
												 		<label>
															 <span class="check-pr">
															 	<input type="checkbox"  ruleId="${rule.filteredRuleId}" ruleTerm="${rule.ruleTerm }" ruleName="${rule.ruleName}"
															 	<c:if test="${sku.mainRuleId eq rule.filteredRuleId}">checked="checked"</c:if> name="activity-checkbox">
															 </span>
														 	${rule.ruleName}
														 </label>
													</P>
												</c:forEach>
											 </div>
										 </div>
									 </c:if> --%>
								</li>
							</c:forEach>
						<%--赠品--%>
						<%-- <c:if test="${not empty activityGroup.giftList }">
						    <div class="cart-gift">
								<span></span>
								<c:forEach items="${activityGroup.giftList}" var="giftProduct">
									<p>${giftProduct.pName} ${giftProduct.skuName}×${giftProduct.qty}</p>
								</c:forEach>
							</div>
						</c:if> --%>
						</div>
					</c:forEach>
					<%--商品列表 end--%>
				</ul>
				
				<%--小计--%>
			   <%--  <div class="cart-bar">
			    	<c:if test="${cartGroupVO.groupProductType eq 11 or cartGroupVO.groupProductType eq 12}">
			    		<span><em>税费：</em> ${cartGroupVO.sumTax}
			    			<c:if test="${cartGroupVO.sumTax <= 50 }"><i>免税</i></c:if>
							<c:if test="${cartGroupVO.sumTax > 50 }"><i>征税</i></c:if>
			    		</span>
			    	</c:if>
					<span><em>合计：</em> ${cartGroupVO.sumPrice - cartGroupVO.transferPrice}</span>
				</div> --%>
			</div>
		</c:forEach>
	</div>
</c:if>

<div class="no100"></div>
	<c:if test="${not empty cartDTO}">
		<%--结算--%>
		<div class="cart-zc settlement-cart">
		    <div class="cart-bt clearfix">
				<p class="check-p cart-all">
					<label><input type="checkbox" name="checkAll">全选</label>
				</p>
				<div class="zongje">
				 <c:if test="${cartDTO.sumTax > 0.00 }">
					<p class="total-fore1">
					    <span>总金额：</span><em></em>
						<b>
							${cartDTO.sumPrice - cartDTO.transferPrice}
						</b>
					</p>
					<p class="shuifei">
							<span>包含税费：</span>
							<b><c:if test="${cartDTO.sumTax >50 }"> ${cartDTO.sumTax }</c:if><c:if test="${cartDTO.sumTax <=50 }"><em> ${cartDTO.sumTax }</em> <!-- ≤50免征 --></c:if><u></u></b>
					</p>
				 </c:if>
				 <c:if test="${cartDTO.sumTax <= 0.00 }">
					<p class="total-fore2">
					    <span style="font-weight: 600">总金额：</span>
						<b>
							${cartDTO.sumPrice - cartDTO.transferPrice}
						</b>
					</p>
					<div style="display: none">
					<p class="shuifei">
							<span>包含税费：</span>
							<b><c:if test="${cartDTO.sumTax >50 }"> ${cartDTO.sumTax }</c:if><c:if test="${cartDTO.sumTax <=50 }"><em> ${cartDTO.sumTax }</em> <!-- ≤50免征 --></c:if><u></u></b>
					</p>
					</div>
				 </c:if>
				</div>
				<a href="javascript:;">结算</a>
			</div>
		</div>
	
	<div style="display:none" id="tax">
		<div class="tax_frame"></div>
		<div class="tax_content">
			<h3>税费说明</h3>
		    <div class="tax_explain">
		    	<p>保税区商品需向海关缴纳进口行邮税。</p>
		        <p>商品价格计算方法为：<br/>订单价=商品价*数量+邮费+税费。</p>
		        <p>税费计算方法为：<br/>税费=应征消费税+应征增值税；应征消费税=法定计征的消费税*0.7； 应征增值税=法定计征的增值税*0.7；法定计征的消费税=（完税价格/(1-消费税税率)）*消费税税率；法定计征的增值税=（完税价格+正常计征的消费税税额）*增值税税率；</p>
		        <p>温馨提示：4月8日起，保税区商品均开始征收11.9%的增值税，如果购买彩妆、香水等商品，在增值税的基础上，还会额外征收30%的消费税。</p>
		    </div>
		    <div class="ensure">确定</div>
		</div>
	</div>
		<%--编辑购物车--%>
		<div class="cart-zc edit-cart" style="display:none;">
		    <div class="cart-bt">
				<p class="check-p cart-all">
					<input type="checkbox" name="checkAll">全选
				</p>
				<a href="javascript:;" class="del-btn">删除</a>
			</div>
		</div>
	</c:if>
	<script type="text/javascript">
	var _trackDataType = 'wap'; //标记数据来源，参数是web和wap，可以为空，默认是web
	var _Schannel_website_id = '10000006';//分站编号，不存在可不写此变量或者留空
	var _Schannel_webshop_id = 'shop_id'; //商铺编号，不存在可不写此变量或者留空
	var _trackData = _trackData || [];//必须为全局变量，假如之前并没有声明，请加此行代码；
	
	var checkedSku = $("input[name='sku-checkbox']");
	$.each(checkedSku,function(i,sku){
		var data = $(sku).parents("[id*='data']").attr("id").split("_");
	_trackData.push(['addcartitem',data[1]+"",data[4],data[5],'${username}']);
	});
	
	$(function(){
		var oA=document.querySelector('.shuifei b u');
		var oDiv=document.querySelector('#tax');
		var oEn=document.querySelector('.ensure');
		
		oA.onclick=function(){
			oDiv.style.display='block';
		};
		
		oEn.onclick=function(){
			oDiv.style.display='none';
		};
	});
	
	$("input[name='activity-checkbox']").each(function() {
		var check = $(this).attr("checked");
		if(check == "checked"){
			$(this).parent().parent().parent().attr("class", "red");
		}
	});
	</script>
		</div>
		<%--底部--%>
		<%@ include file="/WEB-INF/views/commons/navigation.jsp"%>
		
		<%-- hidden域 --%>
		<input type="hidden" id="path" value="<%=path %>"/>
		
	</body>
</html>