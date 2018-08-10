<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-status-bar-style" content=" black ">
<meta name="apple-mobile-web-app-title" content="众聚猫">
<meta name="format-detection" content="telephone=no">
<link rel="stylesheet" href="${staticFile_s}/commons/css/sousuocss/base.css" type="text/css">
<link rel="stylesheet" href="${staticFile_s}/commons/css/sousuocss/details.css" type="text/css">
<link rel="stylesheet" href="${staticFile_s}/commons/css/sousuocss/swiper.min.css" type="text/css">
<link rel="stylesheet" href="${staticFile_s}/commons/css/sousuocss/zepto.alert.css" type="text/css">
<link rel="stylesheet" href="${staticFile_s}/commons/css/sousuocss/spxq_xg.css" type="text/css">
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp"%>
<%@include file="/WEB-INF/views/commons/commonsIco.jsp"%>
</head>
<style>

</style>
<body class="xjs_detail">

	<input type="hidden" id="path" value="<%=path%>" />
	<input type="hidden" id="basePath" value="<%=basePath%>" />

	<%--<div class="download">
     	<div class="down_matter clearfix">
        	<a href="javascript:;" class="down_shut"><b></b><i></i></a>
	            <span><img src="${staticFile_s}/commons/wap/img/appstore_80px.png"></span>
	            <em>全球尖货限时抢<br />下载APP查看更多优惠</em>
             <a href="http://www.91xsj.com/app/download/" class="down_taste">立即下载</a>
         </div>
    </div>
     
   --%>
	<%--需要的一些参数 start--%>
	<input id="path" type="hidden" value="<%=path%>">
	<input id="PID" type="hidden"
		value="${fn:escapeXml(proDetail.productId)}">
	<input id="supply" type="hidden"
		value="${fn:escapeXml(proDetail.supply)}">
	<input type="hidden" id="serverTime" value="${serverTimes }">
	<input id="companyRegion" type="hidden"
		value="${supplier.companyRegion}">
	<input id="PID" type="hidden"
		value="${fn:escapeXml(proDetail.productId)}">
	<input type="hidden" value="${limitnum}" id="limitnum" />
	<input type="hidden" value="${xjzfblbq }" id="xjzfblbq" />
	<input type="hidden" value="${homeShangPin }" id="homeShangPin" />
	<input type="hidden" value="${homeStates }" id="homeStates" />
	<input type="hidden" value="${cbusStates }" id="cbusStates" />
	<%--需要的一些参数 end--%>
	<div class="head">
		<a href="javascript:goBack()" class="bug-go"></a>
	 		<c:if test="${empty comments.comments }">
				<ul class="tabs tabstow">
					<li class="on"><a href="javascript:;">商品</a></li>
					<li id="xing"><a href="javascript:;">详情</a></li>
					<%--<li class="ping"><a href="javascript:;" onclick="commentInfo('${proDetail.productId}');">评价</a></li>
					--%>
				</ul>
			</c:if>
			<c:if test="${not empty comments.comments }">
				<ul class="tabs">
					<li class="on"><a href="javascript:;">商品</a></li>
					<li id="xing"><a href="javascript:;">详情</a></li>
					<%-- <li class="ping"><a href="javascript:;" onclick="commentInfo('${proDetail.productId}');">评价</a></li> --%>
				</ul>
			</c:if>
			<%--<a href="#" class="share"></a>--%>
	</div>
	<!--税费说明框-->
		<!--<div class="sha">
			<div class="share_bg"></div>
				<div class="share_cont">
					<div class="share_c">
						<div class="s_title">
							<span>分享到</span>
						</div>
						<ul class="s_coent">
							<div>
								<a href="#"><i class="fen01"></i><em>微信</em></a>
							</div>
							<div>
								<a href="#"><i class="fen02"></i><em>朋友圈</em></a>
							</div>
							<div>
								<a href="#"><i class="fen03"></i><em>新浪微博</em></a>
							</div>
							<div>
								<a href="#"><i class="fen04"></i><em>QQ好友</em></a>
							</div>
						</ul>
						<div class="cancel">取消</div>
				  </div>
			</div>
			</div>-->
	<div class="wrap">
		<!--商品全部-->
		<!--商品轮播图-->
		<div class="divw" id="fist">
			<div class="swipecontainer">
				<c:if test="${not empty proDetail.showProdAttr.prodAttrVals}">
					<c:choose>
						<c:when test="${ 12 == proDetail.supply}">
							<c:forEach items="${proDetail.showProdAttr.prodAttrVals}"
								var="showAttrVal" varStatus="status1">
								<div id="${showAttrVal.attrValId}" class="swipe top">
									<div class="swipe-wrap">
										<c:if test="${not empty showAttrVal.images}">
											<c:forEach items="${showAttrVal.images}" var="imageUrl"
												varStatus="status2">
												<div>
													<a href="javascript:void(0)"><img
														src="${picUrl1}m0/${imageUrl}"></a>
												</div>
											</c:forEach>
										</c:if>
									</div>
									<div class="dot" id="${showAttrVal.attrValId}-doc"></div>
								</div>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<c:forEach items="${proDetail.showProdAttr.prodAttrVals}"
								var="showAttrVal" varStatus="status1">
								<div id="${showAttrVal.attrValId}" class="swipe top">
									<div class="swipe-wrap">
										<c:if test="${not empty showAttrVal.images}">
											<c:forEach items="${showAttrVal.images}" var="imageUrl"
												varStatus="status2">
												<div>
													<a href="javascript:void(0)"><img
														src="${picUrl1}p1/${imageUrl}"></a>
												</div>
											</c:forEach>
										</c:if>
									</div>
									<div class="dot" id="${showAttrVal.attrValId}-doc"></div>
								</div>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</c:if>
			</div>
			<!--商品 价格-->
			<div class="det">
				<div class="de_top">
					<div class="de_txt">
<%-- 					<b class="sp_type">${regionText}</b> --%>	
					    <b class="sp_type" >${tuBiao } </b>${fn:escapeXml(proDetail.b2cProductName)}
					    <!-- <p style="color: #989898;font-weight: 500;font-size: 1.5rem;">国产车厘子500g 果径26-28mm</p> -->
					</div>
					<div class="de_price">
						<span class="pl" style="float:left;">
							¥<i class="unitPrice" style="font-weight: 600;font-size:2.5rem"></i>
						</span>
					</div>
					<div class="gins_money cashHqj">
						<label>可用</label>
						<span id="hhzfhqq">&nbsp;</span>
						<i>M券,券后<span id="hhzfxz"></span>元</i>
					</div>
					<c:if test="${proDetail.supply == 12}">
						<div class="de_taxation">
							<em class="d-price" shuilv="${fn:escapeXml(proDetail.tariff)}">
								<span class="pb"> <!-- ( 税费：<em> </em><em class="shuifei"></em> ) -->
							</span> <i></i>
							</em>
						</div>
					</c:if>
				</div>
				<div class="de_region">
					<div class="de_ret">
						<c:if test="${proDetail.supply==1 || 21 == proDetail.supply}">
			   				<a href="#" class="rl"><i></i>100%正品</a>
							<a href="#" class="rl"><i></i>满79包邮</a>
							<a href="#" class="rl"><i></i>7天退换</a>
			    			<a href="#" class="rr"><i></i></a>
			  			</c:if>
			  			<c:if test="${12 == proDetail.supply || 14 == proDetail.supply}">
			    			<a href="#" class="rl"><i></i>100%正品</a>
							<a href="#" class="rl"><i></i>满79包邮</a>
							<a href="#" class="rl"><i></i>闪电发货</a>
			    			<a href="#" class="rr"><i></i></a>
			  			</c:if>
						<c:if test="${51 == proDetail.supply || 11 == proDetail.supply}">
							<a href="#" class="rl"><i></i>线上线下</a>
							<a href="#" class="rl"><i></i>千企汇聚</a>
							<a href="#" class="rl"><i></i>千品华冠</a>
							<a href="#" class="rr"><i></i></a>
						</c:if>
					</div>
					<!-- 促销信息展示 start-->
					<div class="promotion-box">
						<c:if test="${not empty promotion}">
							<c:forEach items="${promotion}" var="promotion" varStatus="statu">
								<div class="de_reb" promotion_skuId="${promotion.skuId}"
									style="display:none;">
									<c:if test="${not empty promotion.withIncreasings}">
										<c:forEach items="${promotion.withIncreasings}"
											var="withIncreasings">
											<p>
												<span class="wl"><i class="on3"></i>${withIncreasings.promotionName }</span>
											</p>
										</c:forEach>
									</c:if>
									<c:if test="${not empty promotion.fullReductions}">
										<c:forEach items="${promotion.fullReductions}"
											var="fullReductions">
											<p>
												<span class="wl"><i class="on1"></i>${fullReductions.promotionName }</span>
											</p>
										</c:forEach>
									</c:if>
									<c:if test="${not empty promotion.priceDowns}">
										<c:forEach items="${promotion.priceDowns}" var="priceDowns"
											varStatus="ss">
											<p>
												<span class="wl"><i class="on2"></i>${priceDowns.promotionName }</span>
												<span class="wr">倒计时: <input class="zhij"
													value="${priceDowns.endTime }" hidden="hidden"> <b
													class="views"></b>
												</span>
											</p>
										</c:forEach>
									</c:if>
									<c:if test="${not empty promotion.fullbacks}">
										<!-- 满返没有设计图标   ！！！！！！！！！！！！ -->
										<c:forEach items="${promotion.fullbacks}" var="fullbacks">
											<p>
												<span class="wl"><i class="on4"></i>${fullbacks.promotionName }</span>
											</p>
										</c:forEach>
									</c:if>
									<c:if test="${not empty promotion.buys}">
										<c:forEach items="${promotion.buys}" var="buys">
											<p>
												<span class="wl"><i class="on3"></i>${buys.promotionName }</span>
											</p>
										</c:forEach>
									</c:if>
								</div>
							</c:forEach>
						</c:if>
					</div>
					<!-- 促销信息展示 end-->
				</div>
				<c:if test="${!empty starTip }">
					<div class="tip">
						<h2><span>星级提示：</span><span id="startip">此商品仅限${starTip }会员购买</span></h2>
					</div>
				</c:if>
				<c:if test="${!empty startTime}">
					<div class="limit_tip">
						<h2>限购提示：${limitWords }</h2>
					</div>
				</c:if>
			</div>
			<c:if test="${ null != supplierStore && supplierStore.onlineContent != null}">
					<div class="de_count" style="font-size: 62.5%">
						<div style="float: left;width:22%;height:100%"><img src="${picUrl3}${supplier.logoImgurl}" width="80%" class="spxq_dplogo"></div>
						<div style="float:left;width: 56%;font-size: 1.2rem;">
						<p style="color: #101010;    font-size: 1.5rem;    line-height: 4rem;     padding-left: 2rem;  font-weight: 600;">
						${fn:escapeXml(proDetail.originplaceName)}${fn:escapeXml(supplier.name)}
						</p> 

						<p style="display:blosck;color: #BCBCBC;font-size: 1.3rem;line-height: 2rem;padding-left: 2rem; "><a href="tel:${supplier.kfTel}">客服电话:${supplier.kfTel}010-56487453</a></p>
						</div>
						<div style="margin: 2rem 0;float:right;width:20%;color:#e60012;border-radius:20px;border:1px solid #e60012;font-size:1.2rem;text-align: center;line-height: 2.5rem"><a href="<%=path%>${supplierStore.storeUrl}" class="jin" style="color:#e60012">进入店铺</a></div>
					</div>
			</c:if>
			<!--商品 商品参数 选择规格-->
			<div class="gins">
				<div class="gins_t gbo" id="gin1">
					商品参数<i></i>
				</div>
				<div class="gins_t" id="gin2">
					<label>已选:</label><span>${proDetail.showProdAttr.attrNameCn}${proDetail.buyAttrNameCn}</span><i></i>
				</div>
				<!--<div class="gins_t gtop money" style="padding:0.3rem 0px;">
					<label>现金支付:</label>
				</div>
				 <div class="gins_t gtop money" style="border-top:0rem;">
					<label>奖励红旗券</label><span
						style="padding:0rem 1.3rem 0rem 7.8rem;display:inline;position:absolute;left:0;font-weight:bold;"
						id="moneyHqj" class="hqj"></span><label
						style="padding:0rem 1.3rem 0rem 19.4rem">分红额度</label><span
						style="padding:0rem 1.3rem 0rem 11.8rem;display:inline;position:absolute;left:14rem;font-weight:bold;"
						id="moneyFhed" class="fhed"></span>
				</div>


				<div class="gins_t gtop hqq" style="padding:0.3rem 0px;">
					<label>红旗券支付:</label>
				</div>
				<div class="gins_t gtop hqq" style="border-top:0rem;">
					<label>奖励红旗券</label> <span
						style="padding:0rem 1.3rem 0rem 7.8rem;display:inline;position:absolute;left:0;font-weight:bold;"
						id="hqHqj" class="hqj"></span> <label
						style="padding:0rem 1.3rem 0rem 19.4rem">分红额度</label> <span
						style="padding:0rem   1.3rem 0rem 11.8rem;display:inline;position:absolute;left:14rem;font-weight:bold;"
						id="hqFhed" class="fhed"></span>
				</div>

				 <div class="gins_t gtop hh" style="padding:0.3rem 0px;">
					<label>混合支付:</label>
				</div> -->
				
			</div>

			<!--商品 商品评价-->
			<c:if test="${not empty comments.comments}">
				<c:forEach items="${comments.comments}" var="comment"
					varStatus="statu" end="0">
					<div class="review">
						<div id="setData"></div>
						<div class="rev_title">商品评价 ( ${comments.totalCount } )</div>
						<div class="rev_cont">
							<p class="e1">
								<i></i> <span class="user">${comment.userInfo.userName}</span> <span
									class="r_star"><em class="r_ystar star-${comment.level}"></em></span>
							</p>
							<p class="e2">${fn:escapeXml(comment.context)}</p>
							<p class="e3">
								购买时间：
								<time>
									<fmt:formatDate value="${comment.date}"
										pattern="yyyy-MM-dd HH:mm:ss" />
								</time>
							</p>
						</div>
						<div class="rev_more">
							<a href="javascript:;"
								onclick="commentInfo('${proDetail.productId}');">查看更多评价</a>
						</div>
					</div>
				</c:forEach>
			</c:if>


			<!-- 图文详情 -->
			<div class="picture" id="picture">
			<h3 class="spxq_bt">商品详情</h3>
				<c:catch var="catchExcption">
					<c:import url="${picUrl1}${proDetail.b2cDescription}"
						charEncoding="UTF-8" />
				</c:catch>
			</div>

			<!--商品 商品推荐-->
			<div class="commodity">
				<h2>商品推荐</h2>
				<ul class="commodity-list">
				</ul>
			</div>

			<!-- 规格 属性 -->
			<div class="m_size">
				<div class="m_bg"></div>
				<div class="m_cont">
					<div class="cont-bb">
						<div class="mto1">
							<div class="pic">
								<span class="show"><img id="oop" src=""></span>
							</div>
							<div class="txt">
								<p class="t1 txt_top">
									<i> </i><span class="unitPrice"></span>
								</p>
								<p class="t2">
									库存:<i id="currentSkuQty"></i>件
								</p>
								<p class="t3">
									已选:<i id="colorGu"></i><i id="sizeGu"></i>
								</p>
							</div>
							<div class="mclose"></div>
						</div>
						<div class="m-box">
							<!--展示属性-->
							<div class="mto2 colour_two clearfix">
								<c:set var="isMatch0" scope="page">1</c:set>
								<c:if test="${'默认' ne proDetail.showProdAttr.attrNameCn}">
									<div class="bcolor">${proDetail.showProdAttr.attrNameCn}</div>
									<c:set var="isMatch0" scope="page">0</c:set>
								</c:if>
								<input type="hidden" value="${isMatch0}" id="isMatch0" />
								<div class="blist">
									<ul class="color-list clearfix">
										<c:if test="${not empty proDetail.showProdAttr.prodAttrVals}">
											<c:forEach items="${proDetail.showProdAttr.prodAttrVals}"
												var="showAttrVal" varStatus="status">
												<c:choose>
													<c:when test="${'默认' eq proDetail.showProdAttr.attrNameCn}">
														<c:if test="${status.index == 0 }">
															<c:forEach items="${showAttrVal.images}" var="image"
																varStatus="statu">
																<c:if test="${statu.index == 0 }">
																	<li showAttrValId="${showAttrVal.attrValId}"
																		colorshow="${showAttrVal.attrValNameCn}"><em><i></i><b></b></em><a
																		href="javascript:void(0)"><img id="ooo"
																			src="${picUrl1}p0/${image}" /></a></li>
																</c:if>
															</c:forEach>
														</c:if>
													</c:when>
													<c:otherwise>
														<c:forEach items="${showAttrVal.images}" var="image"
															varStatus="statu">
															<c:if test="${statu.index == 0 }">
																<li showAttrValId="${showAttrVal.attrValId}"
																	colorshow="${showAttrVal.attrValNameCn}"><em><i></i><b></b></em><a
																	href="javascript:void(0)"><img id="ooo"
																		src="${picUrl1}p0/${image}" /></a></li>
															</c:if>
														</c:forEach>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:if>
									</ul>
								</div>
							</div>
							<!-- 规格属性-->
							<c:set var="isMatch" scope="page">0</c:set>
							<c:forEach items="${proDetail.saleAttrList }" var="saleAttrs"
								varStatus="statu2">
								<c:if test="${'默认' eq saleAttrs.saleAttrNameCn && isMatch==0}">
									<c:set var="isMatch" scope="page">1</c:set>
								</c:if>
							</c:forEach>
							<div
								<c:if test="${isMatch==0}">class="mto3 colour_two clearfix"</c:if>>
								<input type="hidden"
									value="${fn:length(proDetail.saleAttrList)}" id="listSize" />
								<input type="hidden" value="${isMatch}" id="isMatch" />

								<c:forEach items="${proDetail.saleAttrList }" var="saleAttrs"
									varStatus="statu2">
									<div class="sizet">
										<c:if test="${isMatch==0}">${saleAttrs.saleAttrNameCn}</c:if>
									</div>
									<div class="sizeb two_bot2">
										<ul class="size-list clearfix" id="${statu2.index}"
											<c:if test="${isMatch==1}">style="display: none;"</c:if>>
											<c:if test="${not empty saleAttrs.attrVals}">
												<c:forEach items="${saleAttrs.attrVals}" var="attrVals"
													varStatus="statu1">
													<li attrValIdV="${attrVals.attrValIdV}"
														measureName="${proDetail.measureName}"><a
														href="javascript:void(0)">${attrVals.attrValNameCn}</a></li>
												</c:forEach>
											</c:if>
										</ul>
									</div>
								</c:forEach>
								<c:forEach items="${proDetail.skuShowList }" var="skuShow">
									<input type="hidden"
										ticketRation="${oneDividendRatio.ticketRation }"
										cashRation="${oneDividendRatio.cashRation }"
										moneyAndHqHqj="${moneyAndHqOneDividend.giftHqj}"
										moneyAndHqFhed="${moneyAndHqOneDividend.giftFhed}"
										moneyFhed="${moneyOneDividend.giftFhed}"
										moneyHqj="${moneyOneDividend.giftHqj}"
										hqFhed="${hqOneDividend.giftFhed}"
										hqHqj="${hqOneDividend.giftHqj}" skuid="${skuShow.skuId }"
										fhed="${skuShow.fhed}" hqj="${skuShow.hqj}"
										attrValIdVs="${skuShow.attrValIdV }"
										unitPrice="${skuShow.unitPrice }" skuQty="${skuShow.skuQty }"
										domesticPrice="${skuShow.domesticPrice }"
										bestoayPrice="${skuShow.bestoayPrice }"
										foreignPrice="${skuShow.foreignPrice }"
										promotionPrice="${skuShow.promotionPrice }"
										cashHqj="${skuShow.cashHqj }" />
								</c:forEach>
							</div>

							<div class="mto4">
								<div class="numbert two_top">数量</div>
								<div class="numberb amount-control">
									<a href="javascript:void(0)" class="reduce amount-down">-</a> <input
										id="num" type="tel" value="0" autocomplete="off"
										class="amount-input" name="" maxlength="10"
										onclick="openView()"> <a href="javascript:void(0)"
										class="plus amount-up">+</a> <span class="stock-tips"
										style="display: none;">库存不足</span> <span class="stock-tips1"
										style="display: none;">超出限购数量</span>
								</div>
							</div>
						</div>
					</div>
					<div class="good ">
						<div class="good-addCart">
							<a href="javascript:void(0)" class="addcart-btn" sign="true">加入购物车</a>
						</div>
						<div class="good-buy">
							<a href="javascript:void(0)" class="buy-btn" sign="true">立即购买</a>
						</div>
					</div>

				</div>

			</div>



			<!-- 商品参数----框 -->
			<div class="parameter" style="">
				<div class="para_bg"></div>
				<div class="para_w">
					<div class="para_box">
						<div class="para_title">
							<span>商品参数</span><i id="close2"></i>
						</div>
						<div class="para_coent">
							<p>
								<span class="pl">商品名称</span> <span class="pr">${proDetail.b2cProductName}</span>
							</p>
							<p>
								<span class="pl">品牌名称</span> <span class="pr">${proDetail.brandName}</span>
							</p>
							<p>
								<span class="pl">商家名称</span> <span class="pr">${supplier.name}</span>
							</p>
							<p>
								<span class="pl">保质期</span> <span class="pr">${proDetail.sheilLife}
									<c:choose>
										<c:when test="${proDetail.sheilLifeType eq 0}">日</c:when>
										<c:when test="${proDetail.sheilLifeType eq 4}">周</c:when>
										<c:when test="${proDetail.sheilLifeType eq 1}">月</c:when>
										<c:when test="${proDetail.sheilLifeType eq 2}">年</c:when>
									</c:choose>
								</span>
							</p>
							<c:if test="${not empty proDetail.saleAttrList}">
								<c:forEach items="${proDetail.saleAttrList}" var="proCommAttr">
									<c:if test="${'默认' ne proCommAttr.saleAttrNameCn}">
										<p>
											<span class="pl">${proCommAttr.saleAttrNameCn}</span>
											<c:if test="${not empty proCommAttr.attrVals}">
												<span class="pr"> <c:forEach
														items="${proCommAttr.attrVals}" var="proCommAttrVals"
														varStatus="status">
														<i>${proCommAttrVals.attrValNameCn}</i>
													</c:forEach>
												</span>
											</c:if>
										</p>
									</c:if>
								</c:forEach>
							</c:if>
						</div>
					</div>
				</div>
			</div>


			<!--税费说明框 start 翼支付不出售保税区商品-->
			<!-- 	<div class="tax_mask" style="display:none">
		<div class="tax_frame"></div>
		<div class="tax_good">
			<div class="tax_c">
			   <div class="k_title"><span>税费说明</span><i id="close"></i></div>
			   <div class="k_coent">
			   		<p>
				   		保税区商品需要向海关缴纳税费。<br>
				   		商品价格计算方法为： <br>
				   		订单价=商品价*数量+邮费+税费。</p>
			   		<p>
				   		税费计算方法为：<br>
						税费=应征消费税+应征增值税<br>
						应征消费税=法定计征的消费税*0.7；<br>
						应征增值税=法定计征的消费税*0.7；
					</p>
					<p>
					    法定计征的消费税=（完税价格/（1-消费税税率））*消费税税率；<br>
                        法定计征的增值税=（完税价格+正常计征的消费税税额）*增值税税率。
					</p>
					<p>
						温馨提示：4月8日起，保税区商品均开始征收11.9%的增值税，如果购买彩妆、香水等商品，在增值税的基础上，还会额外征收30%的消费税。
					</p>
			   </div>
			 </div>
	   </div>
	</div> -->
			<!--税费说明框 end-->

			<!--保税区----国内--第三发国际-->
			<div class="explain" id="exp1">
				<div class="exp_bg"></div>
				<div class="exp_w">
					<div class="exp_box">
						<div class="exp_title">
							<span>保证内容</span><i class="clos"></i>
						</div>
						<div class="exp_coent">
							<%-- 				 <c:if test="${proDetail.supply==1 || 21 == proDetail.supply}">
				    <p>
					  <i></i>
					  <span><em>100%正品</em><br>全球精选 海外直采 全程监控 正品保障。</span>
					</p>
					<p>
					  <i></i>
					  <span><em>满79包邮</em><br>全场购物实付满79元包邮。</span>
					</p>
					<p>
					  <i></i>
					  <span><em>7天退换</em><br>自收货日起 7天内可无条件退货 特殊品类及商品除外。</span>
					</p>
				  </c:if>
				  <c:if test="${12 == proDetail.supply || 14 == proDetail.supply}">
				    <p>
					  <i></i>
					  <span><em>100%正品</em><br>全球精选 海外直采 全程监控 正品保障。</span>
					</p>
					<p>
					  <i></i>
					  <span><em>满79包邮</em><br>全场购物实付满79元包邮。</span>
					</p>
					<p>
					  <i></i>
					  <span><em>闪电发货</em><br>保税区发货 闪电物流 更快更省钱。</span>
					</p>
				  </c:if> --%>
							<c:if test="${51 == proDetail.supply || 11 == proDetail.supply}">
								<p>
									<i></i> <span><em>线上线下</em><br>线上众聚商城，线下众聚猫；“M”券线上线下共享消费优惠，省心方便；千城万店，星罗棋布，到家服务。</span>
								</p>
								<p>
									<i></i> <span><em>千企汇聚</em><br>优质工厂源头直供，品牌云集。</span>
								</p>
								<p>
									<i></i> <span><em>千品华冠</em><br>品质产品，品质生活；物有所值、物超所值。</span>
								</p>
							</c:if>

						</div>
					</div>
				</div>
			</div>
		</div>



		<!--详情 图文详情和购买须知-->
		<div class="divw" style="display:none" id="two">

			<div class="notice">
				<ul class="notop">
					<li class="on"><a href='javascript:0'>图文详情</a></li>
					<li><a href='javascript:0'>购买须知</a></li>
				</ul>
				<div class="nowrap">
					<div class="nodiv">
						<c:catch var="catchExcption">
							<c:import url="${picUrl1}${proDetail.b2cDescription}"
								charEncoding="UTF-8" />
						</c:catch>
					</div>
					<div class="nodiv" style="display:none">
						<div class="detail_abot">
							<div class="detail_xuzhi has show">
								<!-- <div class="xuzhi_1">众聚猫商品类型</div>
			            <div class="xuzhi_2">众聚猫商品发货分为保税区发货、第三方发货和国内发货三种类型。</div> -->
								<div class="xuzhi_3">
									<div class="part_left">
										<i>01</i>
									</div>
									<div class="part_right">检验商品外包装：商品抵达，请先检验外包装是否完好，如果外包装破损、变形，您可以选择拒收商品，也可以在配送单上注明“包装箱破损”字样后开箱验货；</div>
								</div>
								<div class="xuzhi_3">
									<div class="part_left">
										<i>02</i>
									</div>
									<div class="part_right">开箱验货：开箱验货时必须要求送货人员在场，主要检验商品型号是否正确、商品是否存在表面质量问题、备件是否有短缺等，如果存在异常请在送货单上注明，同时您选择拒收或与子公司客服中心联系；</div>
								</div>
								<div class="xuzhi_3">
									<div class="part_left">
										<i>03</i>
									</div>
									<div class="part_right">对于您已经打开包装的商品则不可拒收，但商品短缺或错误、商品存在表面质量问题的除外；</div>
								</div>
								<div class="xuzhi_3">
									<div class="part_left">
										<i>04</i>
									</div>
									<div class="part_right">如您签收外包装有明显损坏迹象的商品且没有在配送单上注明，后续再投诉商品有误或有损坏，恕我们不能受理；</div>
								</div>
								<%-- 			            <div class="xuzhi_2 xuzhi_x">温馨提示：</div>
			            <div class="xuzhi_2 xuzhi_y">保税区发货&amp;国际发货无质量问题不支持退换货，海外直邮商品不支持货到付款，因国际物流公司的信息无法确保实时同步，故系统显示待发货状态并不代表商品未发出，请参考预估时间，等待国内物流信息更新。商品一般到货时间是8-21天（海关清关时间3-15个工作日）。</div>
			            <div class="xuzhi_1" style="display: none ;">众聚猫商品购买流程</div>
			            <div class="xuzhi_4" style="display: none ;"><img src="${staticFile_s}/commons/images/revision20160606/xuzhi.jpg"></div> --%>
								<!-- 			            <div class="xuzhi_1">消费者告知书</div>
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
			            <div class="xuzhi_3">
			                <div class="part_left"><i>05</i></div>
			                <div class="part_right">您首次在本（公司）网站商品购买的保税区发货商品，需要进行身份证实名认证，用于海关清关。否则会影响海关审放，再次购买不需要重复注册。</div>
			            </div>
			            <div class="xuzhi_2">
			                <div class="part_left"><i>06</i></div>
			                <div class="part_right">您在本（公司）网站上购买的国际发货商品，海关对邮政清关进境的物品是实行抽检措施的，抽检到的商品需顾客自行缴纳税款。</div>
			            </div> -->
								<!-- 			            <div class="xuzhi_1">常见问题</div>
			            <div class="xuzhi_3 padd botd">
			                <dl>
			                    <dt>Q:为什么消费者结算时要实名身份认证？</dt>
			                    <dd>A:根据海关总署规定，保税区发货商品需要办理入境申报，因此个人消费者在下单前，须在众聚猫网站进行个人姓名、身份证等真实信息注册，身份和订单信息用户递送给海关进行审核认证，请放心填写。众聚猫向您承诺，身份证明只用于办理保税区发货商品清关手续，不做其他用途。</dd>
			                </dl>
			                <dl>
			                    <dt>Q:行邮税如何征收？消费者购物金额和数量有限制吗？ </dt>
			                    <dd>A: 根据财政部，海关总署及国家税务总局的要求，经国务院批准，我国将在2016年4月8日起实施跨境电子商务零售（企业对消费者 即B2C）进口税收政策,并同步调整行邮税政策。
									政策将对跨境电子商务零售进口商品按照货物征收关税和进口环节增值税 消费税。跨境电子商务零售进口商品的单次交易限值为人民币 2000 元，个人年度交易限值为人民币 20000 元。在限值以内进口的跨境电子商务零售进口商品，关税税率暂设为0%；进口环节增值税、消费税取消免征税额，暂按法定应纳税额的 70%征收。超过单次
									限值、累加后超过个人年度限值的单次交易，以及完税价格超过 2000 元限值的单个不可分割商品，均按照一般贸易方式全额征税。注：按照上述标准，您在我平台的付款价格=售价+增值税+消费税。
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
			             </div> -->

							</div>
						</div>
					</div>
				</div>
			</div>
<!-- 
			商品 商品推荐
			<div class="commodity">
				<h2>商品推荐</h2>
				<ul class="commodity-list">
				</ul>
			</div> -->

		</div>

		<!--评价 全部评价 好评 中评 差评-->
		<!-- <div class="divw" style="display:none" id="d">

	
</div> -->
		<!-- 评价 end -->


	</div>
	</div>

	<div class='no50'></div>
	<!--商品详情的购物车-->
	<div class="navbar navbar-fixed">
		<div class="navbar-cart">
			<a href="<%=path%>/cart/index" class="cart-btn"> <span
				class="icon i-cart-b"><i class="cart-num"></i></span>
			</a>
		</div>
		<!-- 电信充值的商品，商品详情页不显示加入购物车和立即购买按钮 -->
		<div class="navbar-addCart">
			<a href="javascript:void(0)" class="addcart-btns addcart-btn">加入购物车</a>
		</div>
		<div class="navbar-buy">
			<a href="javascript:void(0)" class="directBuy-btns buy-btn">立即购买</a>
		</div>
	</div>
	<div class="homeTips">该商品不能购买</div>

	<script src="${staticFile_s}/commons/js/swipe.min.js"
		type="text/javascript"></script>
	<script src="${staticFile_s}/commons/js/zepto.min.js"></script>
	<script src="${staticFile_s}/commons/js/item/detail.js"
		type="text/javascript"></script>
	<script src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"
		type="text/javascript"></script>
	<script src="${staticFile_s}/commons/js/revision20160606/det.js"
		type="text/javascript"></script>
		<script type="text/javascript" src="${staticFile_s}/commons/js/jquery-1.8.0.min.js"></script>
	<script>
		$(function() {
			$(".commodity").hide();
			getCommodityList();
			if ($('.swipe').is('.top')) {
				$('.top').siblings().hide();
			}
			var h = $(window).height();
			$('.m-box').css({
				"max-height" : +(h - 240) + "px",
				"overflow" : "auto"
			})
		}); 
	</script>
</body>
</html>