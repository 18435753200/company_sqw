<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--头部-->
	<div id="itemlist" class="hide">
	<div class="header head-bg">
		<a href="javascript:;" class="bug-go return-settlement"></a>
		<a href="javascript:;" class="edit-go group-item-number" >共${cartDTO.qty}件</a>
		商品清单
	</div>

      <div class="god-main" style=" position: relative;  top: 4rem;">
	
	<c:forEach items="${cartDTO.cartGroupVOList}" var="cartGroupVO" varStatus="i" >
		<c:forEach items="${cartGroupVO.activityGroupList }" var="activityGroup">
			<c:forEach items="${activityGroup.skuList}" var="sku">
				<div class="pic-list" id="data_${sku.pid }_${sku.skuId }_${sku.price - sku.straightDownPrice }_${sku.price }_${sku.qty }_${sku.stockQty }_${skuGroupDTO.sumPrice}_${sku.isSoldOut }_${sku.productType}">
					<div class="goods-pic2">
						<a href="<%=request.getContextPath()%>/item/get/${sku.pid}"><img src="${sku.imgUrl}" width="80" height="80"></a>
					</div>
					<div class="goods-info2">
						<p class="goods-name2">
						  <a href="<%=request.getContextPath()%>/item/get/${sku.pid}">${sku.pName}</a>
						</p>
						<p class="goods-name3">规格：${sku.skuName}</p>
						<p class="goods-price2">
							<em></em>
							<span>
								${sku.price}
							</span>
						</p>
						<p class="goods-sku2">
							<span>×${sku.qty }</span>
						</p>
					 </div>
				</div>
			</c:forEach>
		</c:forEach>
	</c:forEach>
   </div>
   </div>