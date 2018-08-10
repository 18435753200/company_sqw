<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<%@include file="/WEB-INF/views/commons/base.jsp" %>
<link href="${staticFile_s}/commons/css/my.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>

<script type="text/javascript">

//获取优惠券
function getCou(type){
	window.location="${path}/cusCoupon/getCouponByType?type="+type;
	return ;
}

</script>
<title><spring:message code="title_coupons" /></title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>

<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>	
	<div class="headly">
		<a href="javascript:goBack();" class="goback"><span><b></b><i></i></span></a>
	    <h3>优惠券&nbsp;&nbsp;&nbsp;&nbsp;<a href="${path }/cusCoupon/couponRule" class="gohome">使用说明</a></h3>
	</div>
	<div class="wrap" id="coupon">
		<div class="usely">
			<ul class="clearfix">
		    	<li class="<c:if test="${type == 0 }">active</c:if>" onclick="getCou(0)"><a  >未使用</a></li>
		          <li class="<c:if test="${type == 1 }">active</c:if>" onclick="getCou(1)"><a  >已使用</a></li>
		   	<li class="<c:if test="${type == 2 }">active</c:if>" onclick="getCou(2)"><a  >已过期</a></li>
		    </ul>
		</div>
		<input type="hidden" name="type" value="${type }" id="couType">
		<!-- couponMain -->
		<div class="coupon-box J_mycenterContents">
	        <c:choose>
		        <c:when test="${ coupons != null and fn:length(coupons) > 0 }">
			        <c:forEach items="${coupons }" var="cou" varStatus="copIndex"  >
			        	<div class="unusedly">
					    	<ul>
					        	<li>
					            	<span class="unusedly_l
					            		 <c:choose>
							              <c:when test="${type == 1 }">greyly</c:when>
							              <c:when test="${type == 2 }">lightly</c:when>
							              <c:otherwise></c:otherwise>
							              </c:choose>">
					                	<i class="right1"></i><i class="right2"></i><i class="right3"></i><i class="right4"></i><i class="right5"></i><i class="right6"></i><i class="right7"></i><i class="right8"></i>
					                    <strong>
					                    	<c:if test="${type == 2 }"><span class="guoqi"><b></b><em></em></span></c:if>
					                    	<span class="qian"> ${fn:escapeXml(cou.price)}</span>
					                    </strong>
					                </span>
					            	<span class="unusedly_r">
					                	<i class="right1"></i><i class="right2"></i><i class="right3"></i><i class="right4"></i><i class="right5"></i><i class="right6"></i><i class="right7"></i><i class="right8"></i>
					                    <p class="unusedly_top clearfix">
					                        <strong>
												<c:choose>
													<c:when test="${ cou.couponType==1}">优惠券</c:when>
													<c:when test="${ cou.couponType==2}">现金券</c:when>
												</c:choose>
					                        </strong>
					                        <strong>
					                            <b>满${cou.orderLimitPrice }元</b>
					                            <em>减${fn:escapeXml(cou.price)}元</em>
					                        </strong>
					                    </p>
					                    <p class="unusedly_bott">
											截止日期：<fmt:formatDate value="${cou.endTime}" type="time" timeStyle="full" pattern="yyyy-MM-dd"/>
						               	</p>
						               	<p class="unusedly_bott">使用范围：
										   <!-- 0是全场  1是类目 2是品牌 3是单品 -->
										   <c:choose>
										   <c:when test="${cou.useType == 0 }">全场</c:when>
										   <c:when test="${cou.useType == 1 }">类目</c:when>
										   <c:when test="${cou.useType == 2 }">品牌</c:when>
										   <c:when test="${cou.useType == 4 }">单品 </c:when>
										   </c:choose>
									</p>
			                        <p class="unusedly_bott">使用限制：
										<c:forEach items="${cou.limitName }" var="limit">
											${limit}
										</c:forEach>
									</p>
					                </span>
					            </li>
					        </ul>
					    </div>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<div class="wholely">
					    <div class="noly"><span><img src="${staticFile_s}/commons/img/order_no2.png"></span></div>
					    <div class="without">暂无优惠券</div>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
    </div> 
    </div>
<%@include file="/WEB-INF/views/commons/footer.jsp" %>
<script src="${staticFile_s}/commons/js/zepto.min.js" type="text/javascript"></script> 
<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script> 
<script src="${staticFile_s}/commons/js/mycenter.js" type="text/javascript"></script>
</body>
</html>