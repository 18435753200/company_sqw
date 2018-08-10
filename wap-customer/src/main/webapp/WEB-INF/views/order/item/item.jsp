<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="item goods">
  <c:forEach items="${cartDTO.cartGroupVOList}" var="skuGroupDTO" varStatus="i" >
		<div class="item-group st-ment">
		<h2>${skuGroupDTO.title}</h2>
		<input type="hidden" value="${i.index}" name="group_index"/><%-- 分组下标 --%>
		<input type="hidden" value="1" name="findCouponsQtyFlg"/><%-- 是否获取优惠券数量标识，1：获取，0：不获取 --%>
		 <%-- 循环商品活动 --%>
		
			<div class="ment-main itemlist">
				<div class="ment-cent">
					<ul class="swiper-wrapper mt_country">
						<c:forEach items="${skuGroupDTO.activityGroupList }" var="promotionDTO">
							<c:if test="${not empty promotionDTO.skuList}">
								<c:forEach items="${promotionDTO.skuList}" var="sku">
									<li class="swiper-slide item-sku"
										id="data_${sku.pid }_${sku.skuId }_${sku.price - sku.straightDownPrice }_${sku.price }
											_${sku.qty }_${sku.stockQty }_${skuGroupDTO.sumPrice}_${skuGroupDTO.sumItemPrice}_${sku.subTotalPrice}_${sku.isSoldOut }_${sku.productType}">
										<a href="javascript:;"> 
											<img src="${sku.imgUrl}"/>
										</a>
									</li>
									<li>
	    								${sku.pName}
                                   		${sku.skuName}
									</li>
								</c:forEach>
							</c:if>
						</c:forEach>
					</ul>
			</div>
			</div>

			<%-- <c:forEach items="${skuGroupDTO.activityGroupList }" var="promotionDTO">
				<c:if test="${not empty promotionDTO.giftList}">
					循环赠品 
					<c:forEach items="${promotionDTO.giftList}" var="giftProduct">
						<div class="goods-item">
							<div class="zp-box">
								<span></span>
								<p>${giftProduct.pName}
									${giftProduct.skuName}×${giftProduct.qty}</p>
							</div>
						</div>
					</c:forEach>
				</c:if>
			</c:forEach> --%>
			
		</div>
		<div class="goods-bar">
<%--  				<p class="use-coupons">
					<a href="javascript:;">优惠券
						<span>
							<c:if test="${not empty skuGroupDTO.couponPrice }">- ${skuGroupDTO.couponPrice }</c:if>
							<c:if test="${empty skuGroupDTO.couponPrice }">
								<c:if test="${not empty skuGroupDTO.couponStockB2CDTOs }">
									${fn:length(skuGroupDTO.couponStockB2CDTOs)}
								</c:if>
								<c:if test="${empty skuGroupDTO.couponStockB2CDTOs }">
									0
								</c:if>
								张
							</c:if>
						</span>
					</a>
				</p>  --%>
				<%-- <input type="hidden" name="couponIdTxt" value="${skuGroupDTO.myCoupon.couponstockid}">
				<input type="hidden" name="couponTypeTxt" value="${skuGroupDTO.myCoupon.couponType}"> --%>
				<p class="goods-price"><b>商品金额：</b>  ${skuGroupDTO.sumItemPrice + skuGroupDTO.discountPrice}</p>
				<%-- <p class="discount-price">活动优惠金额：  ${skuGroupDTO.discountPrice }</p> --%>
				<p class="freigth"><b>运费：</b>  ${skuGroupDTO.transferPrice }</p>
<%-- 				<c:if test="${skuGroupDTO.groupProductType ne 1 and skuGroupDTO.groupProductType ne 21 and skuGroupDTO.groupProductType ne 11}">
					<c:set var="sumTax" value="${skuGroupDTO.sumTax}"/>
					<p class="tax">代缴税费：  ${sumTax}</p>
				</c:if> --%>
				<p class="subtotal red"><b>支付金额：</b>  ${skuGroupDTO.sumPrice }</p>
			</div>
	</c:forEach>
	</div>
<div class="item remark">
        <label for="textarea">备注：</label>
        <textarea name="message" id="textarea" class="textarea" cols="45" ></textarea>
    </div>