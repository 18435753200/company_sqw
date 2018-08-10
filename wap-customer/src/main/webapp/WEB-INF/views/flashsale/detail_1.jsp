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
%>
<!doctype html>
<html>
	<head>
        <meta charset="utf-8">
        <title>商品详情</title>
        <meta name="keywords" content="这里是关键词">
        <meta name="description" content="这里是描述">
        <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no">
        <meta name="apple-mobile-web-app-status-bar-style" content=" black ">
        <meta name="apple-mobile-web-app-title" content="众聚猫">
        <meta name="format-detection" content="telephone=no">
       <%--  <link rel="stylesheet" href="${staticFile_s}/commons/css/base.css"> --%>
        <link href="${staticFile_s}/commons/wap/css/newbase.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" href="${staticFile_s}/commons/wap/css/swiper.min.css">
		<link rel="stylesheet" href="${staticFile_s}/commons/wap/css/detail.css">
       <%--  <script src="${staticFile_s}/commons/wap/js/zepto.min.js"></script>			
		<script src="${staticFile_s}/commons/wap/js/swiper.min.js"></script>
        <script src="${staticFile_s}/commons/wap/js/detail.js" type="text/javascript"></script> --%>
        <%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
	</head>
    <body class="xjs_detail">
    
    <div class="download" hidden="hidden">

        	<div class="down_matter clearfix">
            	<a href="javascript:;" class="down_shut"><b></b><i></i></a>
                <span><img src="${staticFile_s}/commons/wap/img/appstore_80px.png"></span>
                <em>欢迎使用众聚猫客户端</em>
                <a href="javascript:;" class="down_taste">立即体验</a>
            </div>
    </div>
    <input id="path" type="hidden" value="<%=path%>">
	<input id="PID" type="hidden" value="${fn:escapeXml(proDetail.productId)}">
	<input id="supply" type="hidden" value="${fn:escapeXml(proDetail.supply)}">
    
		<div class="header">
			<a href="javascript:history.go(-1)" class="bug-go"></a>
			<!-- <a class="goback" href="javascript:goBack();"><span class="icon i-goback"></span></a> -->
			<a href="<%=path%>/" class="home-go"></a>
			商品详情
		</div>
		<input type="hidden" id="serverTime" value="${serverTimes }">
        <div hidden="hidden" class="endtime detail_sg"  value="${endTime }"></div>
        <!-- 轮播 图 -->
         <div class="swipecontainer">
   		<c:if test="${not empty proDetail.showProdAttr.prodAttrVals}">
   		<c:choose>
   			<c:when test="${ 12 == proDetail.supply ||  11 == proDetail.supply}">
   					<c:forEach items="${proDetail.showProdAttr.prodAttrVals}" var="showAttrVal" varStatus="status1">
           		<div id="${showAttrVal.attrValId}" class="swipe top">
     				<div class="swipe-wrap">
	             		<c:if test="${not empty showAttrVal.images}">
	             			<c:forEach items="${showAttrVal.images}" var="imageUrl" varStatus="status2">
	             				<div><a href="javascript:void(0)"><img src="${picUrl1}m0/${imageUrl}"></a></div>
	             			</c:forEach>
	             		</c:if>
              		</div>
     				<div class="dot" id="${showAttrVal.attrValId}-doc"></div>
				</div>
           	</c:forEach>
   			</c:when>
   			<c:otherwise>
   					<c:forEach items="${proDetail.showProdAttr.prodAttrVals}" var="showAttrVal" varStatus="status1">
           		<div id="${showAttrVal.attrValId}" class="swipe top">
     				<div class="swipe-wrap">
	             		<c:if test="${not empty showAttrVal.images}">
	             			<c:forEach items="${showAttrVal.images}" var="imageUrl" varStatus="status2">
	             				<div><a href="javascript:void(0)"><img src="${picUrl1}p0/${imageUrl}"></a></div>
	             			</c:forEach>
	             		</c:if>
              		</div>
     				<div class="dot" id="${showAttrVal.attrValId}-doc"></div>
				</div>
           	</c:forEach>
   			</c:otherwise>
   		</c:choose>
           <%-- 	<c:forEach items="${proDetail.showProdAttr.prodAttrVals}" var="showAttrVal" varStatus="status1">
           		<div id="${showAttrVal.attrValId}" class="swipe top">
     				<div class="swipe-wrap">
	             		<c:if test="${not empty showAttrVal.images}">
	             			<c:forEach items="${showAttrVal.images}" var="imageUrl" varStatus="status2">
	             				<div><a href="javascript:void(0)"><img src="${picUrl1}${imageUrl}"></a></div>
	             			</c:forEach>
	             		</c:if>
              		</div>
     				<div class="dot" id="${showAttrVal.attrValId}-doc"></div>
				</div>
           	</c:forEach> --%>
         </c:if>
    	</div>
		
        <div class="detail_tit"><div>${fn:escapeXml(proDetail.b2cProductName)}</div></div>
		<div class="price">
        	<div class="pricel">
            	<p><span> </span><b><i class="unitPrice"></i></b><em class="d-price">(税率<i class="">${fn:escapeXml(proDetail.tariff)}</i>%)</em></p>
            	<p class="shichang"><span>市场价：</span><em> </em><i class="domesticPrice"></i></p>
            </div>
            <div class="pricer">
            	<div class="guojia"><p><img src="${picUrl1}${proDetail.originplaceImage}"></p><i>${fn:escapeXml(proDetail.originplaceName)}</i></div>
            	<div class="fahuo"><c:if test="${proDetail.supply==1}">
	            	<i>国内发货</i>
            	</c:if>
            	<c:if test="${ 21 == proDetail.supply}">
	            	<i>国内发货</i>
            	</c:if>
            	<c:if test="${ 12 == proDetail.supply || 14 == proDetail.supply}">
	            	<i>保税区发货</i>
            	</c:if>
            	<c:if test="${ 11 == proDetail.supply || 13 ==proDetail.supply}">
	            	<i>海外直邮</i>
            	</c:if>
            	<c:if test="${ 31 == proDetail.supply}">
	            	<i>卓悦(第三方)</i>
            	</c:if></div>
            </div>
        </div>
        <div class="discount"><!--hidden="hidden"  -->
        	<!-- <span>折</span><em>减</em><i>【SALE】折上满999减149</i> -->
        	<div class="promotion">
                <c:if test="${not empty promotion}">
               		<c:forEach items="${promotion}" var="promotion" varStatus="statu">
               			<div class="act-info" promotion_skuId="${promotion.skuId}" <%-- promotion_priceDownFlag="${ empty promotion.priceDowns}"  priceDownDate="${promotion.priceDownDate}" --%> style="display:none;">
               			<%-- 	<c:if test="${promotion.freeShipping}">
               					<span class="fullcut"><a href="javascript:void(0)">包邮</a></span>
               				</c:if> --%>
               				
               				<%-- <c:if test="${promotion.fullReductions}">
               					<span class="fullcut"><a href="javascript:void(0)">满减</a></span>
               				</c:if>	
               				<c:if test="${promotion.withIncreasings}">
               					<span class="fullcut"><a href="javascript:void(0)">满赠</a></span>
               				</c:if>	
               				<c:if test="${promotion.priceDowns}">
               					<span class="fullcut"><a href="javascript:void(0)">直降</a></span>
               				</c:if>
               				<c:if test="${promotion.fullbacks}">
               					<span class="fullcut"><a href="javascript:void(0)">满返</a></span>
               				</c:if>
               				<c:if test="${promotion.buys}">
               					<span class="fullcut"><a href="javascript:void(0)">买赠</a></span>
               				</c:if> --%>
               				<c:if test="${not empty promotion.withIncreasings}">
               					<span class="fullcut"><a href="javascript:void(0)">满赠</a></span>
               				</c:if>	
               				<c:if test="${not empty promotion.fullReductions}">
               					<span class="fullcut"><a href="javascript:void(0)">满减</a></span>
               				</c:if>	
               				<c:if test="${not empty promotion.priceDowns}">
               					<span class="fullcut"><a href="javascript:void(0)">直降</a></span>
               				</c:if>	
               				<c:if test="${not empty promotion.fullbacks}">
               					<span class="fullcut"><a href="javascript:void(0)">满返</a></span>
               				</c:if>	
               				<c:if test="${not empty promotion.buys}">
               					<span class="fullcut"><a href="javascript:void(0)">买赠</a></span>
               				</c:if>	
		                </div>
		                
               		</c:forEach>
                </c:if>
           </div>
                
            <div class="bd">
                <ul class="pro-list">
                	<c:if test="${not empty promotion}">
                		<c:forEach items="${promotion}" var="promotion" varStatus="statu">
                			<li promotion_info_skuId="${promotion.skuId}" style="display: none;">
                			
                				<c:if test="${not empty promotion.withIncreasings}">
                					<span><c:forEach items="${promotion.withIncreasings}" var="withIncreasings">
	                					${withIncreasings.promotionName }&nbsp;&nbsp;
									</c:forEach></span>
                				</c:if>
                				<c:if test="${not empty promotion.fullReductions}">
                					<span><c:forEach items="${promotion.fullReductions}" var="fullReductions">
	                					${fullReductions.promotionName }&nbsp;&nbsp;
									</c:forEach></span>
                				</c:if>	
                				<c:if test="${not empty promotion.priceDowns}">
                					<p><c:forEach items="${promotion.priceDowns}" var="priceDowns" varStatus="ss">
	                					${priceDowns.promotionName }&nbsp;&nbsp;
	                					<%-- ${priceDowns.endTime }&nbsp;&nbsp; --%>
	                					<input class="zhij" value="${priceDowns.endTime }" hidden="hidden">
	                					 <i class="views"></i>&nbsp;&nbsp;
									</c:forEach></p>
                				</c:if>	
                				<c:if test="${not empty promotion.fullbacks}">
                					<span><c:forEach items="${promotion.fullbacks}" var="fullbacks">
	                					${fullbacks.promotionName }&nbsp;&nbsp;
									</c:forEach></span>
                				</c:if>	
                				<c:if test="${not empty promotion.buys}">
                					<span><c:forEach items="${promotion.buys}" var="buys">
	                					${buys.promotionName }&nbsp;&nbsp;
									</c:forEach></span>
                				</c:if>
                			</li>
                		</c:forEach>
                	</c:if>
                </ul>
               
            </div>
        </div>
        <div class="draw">
        	<p><span><img src="${staticFile_s}/commons/wap/img/ico_baozhang.png" /></span><i>100%正品保证</i></p>
        	<p><span><img src="${staticFile_s}/commons/wap/img/ico_baozhang.png" /></span><i>100%海外直供</i></p>
        </div>
        <div class="yet" id="goods">
        	<div class="yetleft">
            	<span>已选 <em>${proDetail.showProdAttr.attrNameCn} ${proDetail.buyAttrNameCn}</em></span>
            </div>
            <div class="yetright">
            	<em></em>
            	<p>
                    <i></i>
                    <b></b>
                </p>
            </div>
        </div>
        <!-- 配送地址 -->
        <input type="hidden" id="areaIdKey" value="${areaIdKey}">
        <div class="yet" id="btn-select-region">
            <div class="yetleft" ><em>送至</em>
					
						<div class="address">
							<span class="provi_city_area_id"></span>
							<img src="${staticFile_s}/commons/wap/img/my_14.png">
							<!-- <i class="icon-location"></i> -->
						</div>
					
					 <!-- <i hidden="hidden" class="">当前区域无货</i> -->
					 <i id="wuh" hidden="hidden" class="wuh">当前区域无货</i>
					 <i id="xianh" hidden="hidden" class="wuh"></i>
			</div>		
			
            <div class="yetright" hidden="hidden">
            	<em></em>
            	<p>
                    <i></i>
                    <b></b>
                </p>
            </div>
        </div>
        <div class="details" id="pull" hidden="hidden">
            <span><i></i><b></b><em>上拉看图文详情</em></span>
        </div>
        <div class="null" id="null" hidden="hidden"></div>
          <div class="navbar navbar-fixed-bottom">
          <div class="navbar-cart"><a href="<%=path%>/cart/index" class="cart-btn">
			    <span class="icon i-cart-b">
			    	<c:if test="${cartTotalQty>0 && null!=cartTotalQty}">
			    		<i class='cart-num'>${cartTotalQty}</i>
			    	</c:if>
			    </span></a>
		 </div>
		 	<div id="goodss" class="navbar-addCart"><a onclick="toCart();" href="javascript:void(0)" class="addcart-btns" sign="true">加入购物车</a></div>
            <div class="navbar-buy"><a href="javascript:void(0)" class="buy-btn" sign="true">立即购买</a></div>
    		
            	<!-- <div class="shopping"></div> -->
            	
                <!-- <p>
                    <input sign="true" id="addcart-btn" type="submit" value="加入购物车">
                    <input type="submit" value="立即购买" class="nomar">
                </p> -->
            </div>
        
<!--配送货地址-->
<div class="filter-wrap" id="J_filterWrap">
    <header id="header" class="head"><a href="javascript:;" class="goback closeBtn"><i></i><b></b></a>
        <h2 class="h-title">配送至</h2>
    </header>
    <div class="filter-bd">
		<!-- 省份 -->
		<div class="filter-item J_filterContents" id="stock_province_item">
			 <ul>
					<!-- <li><a href="javascript:void(0);" provice_id="330000" flag = "provice" class="data_city">浙江</a></li> -->
             </ul>
		</div>
		
		<!-- 市 -->
		 <div id="stock_city_item" class="filter-item J_filterContents"  style="display: none;">
				<ul id="show_city" >
					<!-- <li><a href="javascript:void(0);" city_id="330100" flag = "city" class="data_area">杭州市</a></li> -->
				</ul>
         </div>
		 
		 <!-- 县 -->
		<div id="stock_area_item"  class="filter-item J_filterContents"  style="display: none;">
				<ul id="show_area" >
					<!-- <li><a href="javascript:void(0);" area_id="330203" flag = "area" class="select_area_js">海曙区</a></li> -->
				</ul>
         </div>
		
    </div>
</div>

<div class="come">
    <div class="colour">
        <div class="colour_top"></div>
        <div class="colour_bot">
            <div class="colour_one clearfix">
                <div class="pic">
                    <span class="show"><img id="oop" src=""></span>
                    <%-- <span><img src="${staticFile_s}/commons/wap/img/pic/list_01.jpg"></span>
                    <span><img src="${staticFile_s}/commons/wap/img/pic/img_03.jpg"></span> --%>
                </div>
                <div class="txt">
                    <p class="txt_top"><i> </i><span class="unitPrice">611.00</span></p>
                    <p class="txt_bot"><b>商品编号：</b><em>${proDetail.productId} </em></p>
                </div>
            </div>
            <div class="colour_two clearfix">
                <p class="two_top">${proDetail.showProdAttr.attrNameCn}</p><br>
                <%-- <p class="two_bot"><span class="cur"><em><i></i><b></b></em><img src="${staticFile_s}/commons/wap/img/pic/detail_01.jpg"></span><span><em><i></i><b></b></em><img src="img/pic/list_01.jpg"></span><span><em><i></i><b></b></em><img src="img/pic/img_03.jpg"></span></p> --%>
                <div class="two_bot">
                <ul class="color-list clearfix">
                    <c:if test="${not empty proDetail.showProdAttr.prodAttrVals}">
                    	<c:forEach items="${proDetail.showProdAttr.prodAttrVals}" var="showAttrVal" varStatus="status">
                    		<c:forEach items="${showAttrVal.images}" var="image" varStatus="statu">
                    			<c:if test="${statu.index == 0 }">
                    				<li showAttrValId="${showAttrVal.attrValId}" colorshow="${showAttrVal.attrValNameCn}"><em><i></i><b></b></em><a href="javascript:void(0)"><img id="ooo" src="${picUrl1}p0/${image}" /></a></li>
                    			</c:if>
                    		</c:forEach>
                    	</c:forEach>
                    </c:if>
                </ul>
            </div>
            </div>
            <div class="colour_two clearfix">
                <p class="two_top">${proDetail.buyAttrNameCn}</p>
                <!-- <p class="two_bot2"><span>S</span><span>M</span><span>L</span><span>XL</span></p> -->
                 <div class="two_bot2">
                <ul class="size-list clearfix">
                    <c:if test="${not empty proDetail.skuShowMap}">
                    	<c:forEach items="${proDetail.skuShowMap}"  var="skuMap" varStatus="statu1">
                    		<c:forEach items="${skuMap.value}" var="sku" varStatus="statu2">
                    			<li showAttrValId_sku="${skuMap.key}" skuprcie="${sku.unitPrice }" measureName="${proDetail.measureName}"  skuId="${sku.skuId}" skuQty="${sku.skuQty}" unitPrice="${sku.unitPrice}" domesticPrice="${sku.domesticPrice}" foreignPrice="${sku.foreignPrice}" tar="${sku.tar}" notTar="${sku.notTar}" style="display:none;"><a href="javascript:void(0)">${sku.skuNameCn}</a></li>
                    		</c:forEach>
                    	</c:forEach>
                    </c:if>
                </ul>
                </div>
            </div>
            <div class="colour_four clearfix">
               <!--  <p class="two_top">数量</p>
                <div class="four_bot">
                    <a href="javascript:;" class="subtract">-</a>
                    <span><input type="text" value="0" autocomplete="off" class="amount-input" name="" maxlength="10"></span>
                    <a href="javascript:;" class="add">+</a>
                    <span class="stock-tips" style="display: none;">库存不足</span>
                     <div class="amount-control"><a class="amount-down" href="javascript:void(0)">—</a>
                    <input type="text" value="0" autocomplete="off" class="amount-input" name="" maxlength="10">
                    <a class="amount-up" href="javascript:void(0)">+</a></div>
                    <span class="stock-tips" style="display: none;">库存不足</span>
                </div> -->
                <h2 class="two_top">数量</h2>
                <div class="amount-control"><a class="amount-down" href="javascript:void(0)">—</a>
                    <input id="num" type="tel" value="0" autocomplete="off" class="amount-input" name="" maxlength="10" onclick="openView()">
                    <a class="amount-up" href="javascript:void(0)">+</a></div>
                    <span class="stock-tips" style="display: none;">库存不足</span>
                
            </div>
           <div class="null"></div>
            <div class="navbar navbar-fixed-bottom">
            <div class="navbar-cart"><a href="<%=path%>/cart/index" class="cart-btn">
			    <span class="icon i-cart-b">
			    	<c:if test="${cartTotalQty>0 && null!=cartTotalQty}">
			    		<i class='cart-num'>${cartTotalQty}</i>
			    	</c:if>
			    </span></a></div>
    		<div class="navbar-addCart"><a href="javascript:void(0)" class="addcart-btn" sign="true">加入购物车</a></div>
            <div class="navbar-buy"><a href="javascript:void(0)" class="buy-btn" sign="true">立即购买</a></div>
            	<!-- <div class="shopping"></div> -->
            	
                <!-- <p>
                    <input sign="true" id="addcart-btn" type="submit" value="加入购物车">
                    <input type="submit" value="立即购买" class="nomar">
                </p> -->
            </div>
        </div>
    </div>
    
    
</div>


<div class="detail_all">
    <ul class="detail_atit">
        <li class="cur">商品详情</li>
        <li class="centre">规格参数</li>
        <li>购买须知</li>
        
        <li id=""><span id="comment1" onclick="commentInfo('${proDetail.productId}')">商品评价</span></li>
    </ul> 
    
    <div class="detail_abot">
        <div class="detail_xiang show has">
        	
            <div class="goods-intro-pic">
                <!-- <h3 class="dc_tit">商品图片</h3> -->
                <%-- <img src="<%=path%>/commons/images/detail_big_01.jpg" /> <img src="<%=path%>/commons/images/detail_big_02.jpg" />--%>
                <c:catch var="catchExcption">
					<c:import url="${picUrl1}${proDetail.b2cDescription}" charEncoding="UTF-8" />
				</c:catch>
            </div> 
            <div class="goods-intro-sale">
                <h3 class="dc_tit">售后说明</h3>
                <p>温馨提示：请您放心，众聚猫上所售卖的商品均为原装进口正品，并由中国太平洋财产保险股份有限公司为你购买的每一件商品进行承保。</p>
            </div>
        </div>
        
        <div class="detail_canshu has">
             <ul >
             	<c:if test="${not empty proDetail.prodAttrs}">
                		<c:forEach items="${proDetail.prodAttrs}" var="proCommAttr">
                			<li><tr>
                        		<i><th scope="row">${proCommAttr.attrNameCn} :</th></i>
                        		<c:if test="${not empty proCommAttr.prodAttrVals}">
                        			<c:forEach items="${proCommAttr.prodAttrVals}" var="proCommAttrVals" varStatus="status">
                        				<c:choose>
                        					<c:when test="${status.index == 0}">
                        						<span><td>${proCommAttrVals.attrValNameCn}
                        					</c:when>
                        					<c:otherwise>
                        						${proCommAttrVals.attrValNameCn}
                        					</c:otherwise>
                        				</c:choose>
                        				</td></span>
                        			</c:forEach>
                        		</c:if>
                        		
                   			</tr></li>
                		</c:forEach>
                </c:if>
            </ul>
        </div>
        
        <div class="detail_xuzhi has">
            
            <div class="xjskong"></div>
            <div class="xuzhi_1">众聚猫商品类型</div>
            <div class="xuzhi_2">众聚猫商品发货分为保税区发货和国内发货两种类型。</div>
            <div class="xuzhi_3 clearfix" style="display: none ;">
                <div class="part_left"><span class="xjslc"><img src="${staticFile_s}/commons/wap/img/001.gif"></span></div>
                <div class="part_right">众聚猫为确保中国消费者能购买到优质海外商品，在海外成立分公司以及搭建专业团队，并直接在海外建成物流仓储中心（意大利、德国、法国、卢森堡等国家），全球化布局正逐步完善。为了降低中国消费者国际购物中途掉包和物流报关的繁琐门槛，众聚猫采用国际航班飞机运输，大大缩短了国内用户收到国际包裹的时间，并提供高于国家标准的售后服务，带给用户最优的购物享受。</div>
            </div>
            <div class="xuzhi_3 clearfix">
                <div class="part_left"><span class="xjslc"><img src="${staticFile_s}/commons/wap/img/002.gif"></span></div>
                <div class="part_right">众聚猫在海外成立分公司及搭建专业团队，并直接在海外建成物流仓储中心，一部分商品采用空运，一部分商品批量海运，在海关和国检部门的监控下，商品直接从国内保税区仓库用海关指定的快递公司寄出。众聚猫的海外直采模式，加上保税区的海关监管，充分保障了商品的品质和物流的速度。</div>
            </div>
            <div class="xuzhi_3 clearfix">
                <div class="part_left"><span class="xjslc"><img src="${staticFile_s}/commons/wap/img/003.gif"></span></div>
                <div class="part_right">众聚猫以负责的态度、严格的程序、专业的方法，为用户甄选符合需求的高品质商品，直接从全国各大仓库发货，并始终致力于向用户提供高效、快捷、周到的配送及售后服务。</div>
            </div>
            <div class="xuzhi_1" style="display: none ;">众聚猫商品购买流程</div>
            <div class="xuzhi_4" style="display: none ;"><img src="${staticFile_s}/commons/wap/img/xuzhi.jpg" /></div>
            <div class="xuzhi_1">消费者告知书</div>
            <div class="xuzhi_3">
                <p>尊敬的客户：</p>
                <p>在您选购境外商品前，请您仔细阅读此文，同意本文所告知内容后再进行下单购买。</p>
            </div>
            <div class="xuzhi_3">
                <div class="part_left"><i>01</i></div>
                <div class="part_right">您在本（公司）网站上购买的保税区发货商品等同于原产地直接购买，商品本身可能无中文标签。您可以使用在线翻译软件或直接咨询本站客服。</div>
            </div>
            <div class="xuzhi_3">
                <div class="part_left"><i>02</i></div>
                <div class="part_right">您购买的保税区发货商品适用的品质、健康、安全、卫生、环保、标识等项目可能与我国质量安全标准不同，所以在使用过程中由此可能产生的危害或损失以及其他风险，将由您个人承担。</div>
            </div>
            <div class="xuzhi_3">
                <div class="part_left"><i>03</i></div>
                <div class="part_right">您在本（公司）网站上购买的商品为“个人自用”，不得进行再销售。</div>
            </div>
            <div class="xuzhi_3">
                <div class="part_left"><i>04</i></div>
                <div class="part_right">您在本（公司）网站上购买的保税区发货商品，自动视为众聚猫代您向海关进行申报和代缴税款。</div>
            </div>
            <div class="xuzhi_2">
                <div class="part_left"><i>05</i></div>
                <div class="part_right">您首次在本（公司）网站商品购买的保税区发货商品，需要进行身份证实名认证，用于海关清关。否则会影响海关审放，再次购买不需要重复注册。</div>
            </div>
            <div class="xuzhi_1">常见问题</div>
            <div class="xuzhi_3 padd botd">
                <dl>
                    <dt>Q:为什么消费者结算时要实名身份认证？</dt>
                    <dd>A:根据海关总署规定，保税区发货商品需要办理入境申报，因此个人消费者在下单前，须在众聚猫网站进行个人姓名、身份证等真实信息注册，身份和订单信息用户递送给海关进行审核认证，请放心填写。众聚猫向您承诺，身份证明只用于办理保税区发货商品清关手续，不做其他用途。</dd>
                </dl>
                <dl>
                    <dt>Q:行邮税如何征收？消费者购物金额和数量有限制吗？ </dt>
                    <dd>A: 根据财政部，海关总署及国家税务总局的要求，经国务院批准，我国将在2016年4月8日起实施跨境电子商务零售（企业对消费者 即B2C）进口税收政策,并同步调整进口税政策。
							政策将对跨境电子商务零售进口商品按照货物征收关税和进口环节增值税 消费税。跨境电子商务零售进口商品的单次交易限值为人民币 2000 元，个人年度交易限值为人民币 20000 元。在限值以内进口的跨境电子商务零售进口商品，关税税率暂设为0%；进口环节增值税、消费税取消免征税额，暂按法定应纳税额的 70%征收。超过单次
							`限值、累加后超过个人年度限值的单次交易，以及完税价格超过 2000 元限值的单个不可分割商品，均按照一般贸易方式全额征税。注：按照上述标准，您在我平台的付款价格=售价+增值税+消费税。
					</dd>
                </dl>
                <dl>
                    <dt>Q:请问商品有没有发票？购买商品后能提供什么凭证？</dt>
                    <dd>A:保税区发货的商品不提供发票。包裹内会附加商品配货单，以方便消费者核对商品信息。</dd>
                </dl>
                <dl class="botd">
                    <dt>Q:众聚猫保税区发货商品与海淘代购商品有什么区别？</dt>
                    <dd>A:众聚猫保税区发货商品与海淘代购的商品相比，价格优惠的同时品质更有保证，是经过海关商检部门严格审查的，商品更安全可靠，并且加贴了防伪溯源码，可以查到进口详细信息并可验证真伪。</dd>
                </dl>
            </div>
            
        </div>
        <div class="detail_xuzhi has" id="commentInfo" style="height:1000px;">
        
        
        </div>
    </div>
    
</div>

<!-- 库存 触发  -->
    <div class="countly">
		<div class="changely">
	    	<div class="cha_top">修改购买数量</div>
	        <div class="cha_mid">
		         <div class="amount-control"><a class="amount-down" href="javascript:void(0)">—</a>
	                    <input id="numCon" type="tel" value="0" autocomplete="off" class="amount-input" name="" maxlength="10" >
	                    <a class="amount-up" href="javascript:void(0)">+</a>
	             </div>
                 <span class="stock-tips" style="display: none;">库存不足</span>
            </div>
	    	<div class="cha_bot">
	        	<a href="javascript:;" class="cancely">取消</a>
	        	<a href="javascript:;" class="surely">确定</a>            
	        </div>
	    </div>
    
	</div>
<!-- js相关内容 -->
<%-- <script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script> --%>
<script src="${staticFile_s}/commons/js/swipe.min.js" type="text/javascript"></script> 
<script src="${staticFile_s}/commons/wap/js/zepto.min.js"></script>			
<script src="${staticFile_s}/commons/wap/js/swiper.min.js"></script>
<script src="${staticFile_s}/commons/wap/js/detail.js" type="text/javascript"></script>
<script src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js" type="text/javascript"></script>
<script type="text/javascript">
//-------闪购倒计时 
   
//var serverTime = ${endTime}*1000000;
//服务器时间，毫秒数 
var d= $("#serverTime").val();
var serverTime = d* 1000;

$(function(){
    var dateTime = new Date();
    
    var difference = dateTime.getTime() - serverTime;//客户端与服务器时间偏移量 
    setInterval(function(){
        var obj =$(".endtime");
        var endTime = new Date(parseInt(obj.attr('value')) * 1000);//结束时间
        var nowTime = new Date();
        var nMS=endTime.getTime() - nowTime.getTime() + difference;//当前变化时间
        var myD=Math.floor(nMS/(1000 * 60 * 60 * 24));
        var myH=Math.floor(nMS/(1000*60*60)) % 24;
        var myM=Math.floor(nMS/(1000*60)) % 60;
        var myS=Math.floor(nMS/1000) % 60;
        var myMS=Math.floor(nMS/100) % 10;
        if(myD>= 0){
			var str = myD+"天"+myH+"小时"+myM+"分"+myS+"秒";
        }else{
			var str = "";	
		}
		obj.html(str);
    }, 1000);//每个0.1秒执行一次 
	
});

$(function(){
	
	//---闪购 显示倒计时
//	alert($(".detail_sg").attr("value"));
	if($(".detail_sg").attr("value")>0){
		$(".endtime").show();
	}
	//购物车 显示 问题
	$.ajax({
		type : "post",
		url :$("#path").val()+"/cart/qty",
		dataType : "text",
		success : function(totalQty){
			if(regint(totalQty)&&Number(totalQty)>0){
				//动态添加标签
				$(".icon.i-cart-b").html("<i class='cart-num'>"+totalQty+"</i>");
			}
		}
	});
	
})

$(function(){
	
    var dateTime = new Date();
    var difference = dateTime.getTime()/1000 - serverTime/1000;//客户端与服务器时间偏移量 
  
    setInterval(function(){
       $("ul.pro-list li p").each(function(){   
		      //获取直降时间
				var priceDownDate = $(this).find(".zhij").val();
				
				//转为date格式 "2015-08-31 17:58:24"
				var priceDownDate = new Date(priceDownDate.replace(/-/g,"/"));
				//console.log(priceDownDate);
				var time_stamp = priceDownDate.getTime()/1000;
				console.log(time_stamp);
		        var endTime = new Date(parseInt(time_stamp) * 1000);//结束时间
		        var nowTime = new Date();
		        var nMS=endTime.getTime() - nowTime.getTime() + difference;//当前变化时间
		        var myD=Math.floor(nMS/(1000 * 60 * 60 * 24));
		        var myH=Math.floor(nMS/(1000*60*60)) % 24;
		        var myM=Math.floor(nMS/(1000*60)) % 60;
		        var myS=Math.floor(nMS/1000) % 60; 
		        var myMS=Math.floor(nMS/100) % 10;
		        if(myD>= 0){
					/* var str = myD+"天"+myH+"小时"+myM+"分"+myS+"."+myMS+"秒"; */
					var str = myD+"天"+myH+"小时"+myM+"分"+myS+"秒";
					//console.log(str);
		        }else{
					var str = "";	
				}
				$(this).find(".views").html(str);
		    }, 1000);//每个0.1秒执行一次 
      });
    
  });

function openView(){
	var isIOS = navigator.userAgent.match('iPad')||navigator.userAgent.match('iPhone')||navigator.userAgent.match('iPod');
	var isAndroid = navigator.userAgent.match('Android');
	var oDiv=document.querySelector('.countly');
	var oCan=document.querySelector('.cancely');
	var oSave=document.querySelector('.surely');
	
	if(isIOS){
		oDiv.style.display='none';
	}else{
		oDiv.style.display='block';
		oCan.onclick=function(){
			oDiv.style.display='none';
		};
		oSave.onclick=function(){
			$("#num").val($("#numCon").val());
			oDiv.style.display='none';
		};		
	}

	
}
</script>
</body>
</html>