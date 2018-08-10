<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	<%-- <header id="header" class="head">
		<a href="javascript:;" onclick="javascript:;" class="goback selectCoupons-goback"><span class="icon i-goback"></span></a>
    	<h2 class="h-title">选择优惠券</h2>
    	<a href="#" class="gohome">使用说明</a> 
    </header> --%>
    
    <div class="header head-bg">
		<a href="javascript:;" class="bug-go goback selectCoupons-goback"></a>
		选择优惠券
	</div>
    
    <div class="wrap" id="coupon">
    <div class="mycenter-bd"> 
        <%--优惠券列表--%>
        <div class="coupon-box unused J_mycenterContents">
            <c:if test="${not empty myCouponStockB2CDTOList}">
				<c:forEach items="${myCouponStockB2CDTOList}" var="myCouponStockB2CDTO">
					<div class="coupon-item ${classVal}" id="data_${myCouponStockB2CDTO.couponstockid}_${myCouponStockB2CDTO.price}_${myCouponStockB2CDTO.couponType}">
						<label class="chk">
							<input type="checkbox" value="${myCouponStockB2CDTO.couponstockid}" name="coupons" id="checkbox" />
							<div class="unusedly">
								<ul>
						        	<li>
						            	<span class="unusedly_l">
						                	<i class="right1"></i><i class="right2"></i><i class="right3"></i><i class="right4"></i><i class="right5"></i><i class="right6"></i><i class="right7"></i><i class="right8"></i>
						                    <strong>
						                    	<c:if test="${type == 2 }"><span class="guoqi"><b></b><em></em></span></c:if>
						                    	<span class="qian"> ${myCouponStockB2CDTO.price}</span>
						                    </strong>
						                </span>
						                <span class="unusedly_r">
					                	<i class="right1"></i><i class="right2"></i><i class="right3"></i><i class="right4"></i><i class="right5"></i><i class="right6"></i><i class="right7"></i><i class="right8"></i>
					                    <p class="unusedly_top clearfix">
					                        <strong>
												<c:choose>
													<c:when test="${myCouponStockB2CDTO.couponType eq 1}">优惠券</c:when>
													<c:when test="${myCouponStockB2CDTO.couponType eq 2}">现金券</c:when>
												</c:choose>
					                        </strong>
					                        <strong>
					                            <b>满${myCouponStockB2CDTO.orderLimitPrice }   </b>
					                            <em>减${myCouponStockB2CDTO.price}元</em>
					                        </strong>
					                    </p>
					                    <p class="unusedly_bott">
											截止日期：<fmt:formatDate value="${myCouponStockB2CDTO.endTime}" type="time" timeStyle="full" pattern="yyyy-MM-dd"/>
						               	</p>
						               	<p class="unusedly_bott">使用范围：
										   <!-- 0是全场  1是类目 2是品牌 4是单品 -->
										   <c:choose>
										   <c:when test="${myCouponStockB2CDTO.useType eq 0 }">全场</c:when>
										   <c:when test="${myCouponStockB2CDTO.useType eq 1 }">类目</c:when>
										   <c:when test="${myCouponStockB2CDTO.useType eq 2 }">品牌</c:when>
										   <c:when test="${myCouponStockB2CDTO.useType eq 4 }">单品 </c:when>
										   </c:choose>
									</p>
			                        <p class="unusedly_bott">使用限制：
										<c:forEach items="${myCouponStockB2CDTO.limitName }" var="limitName">
											${limitName}
										</c:forEach>
									</p>
					                </span>
					                </li>
				                </ul>
							</div>
						</label>
					</div>
				</c:forEach>
			</c:if>
            <%--无优惠券--%>
            <c:if test="${empty myCouponStockB2CDTOList}">
	            <div class="null">
	                <div class="null-icon"><span class="icon i-coupon"></span></div>
	                <div class="null-text">
	                    <p>您暂时没有可使用的优惠券</p>
	                </div>
	            </div>
            </c:if>
            <%-- 确认 --%>
            <div class="form-btn">
                <input type="button" value="确 认" class="btn coupons-ensure" id="J_regNext_btn">
            </div>
        </div>
    </div>
</div>
