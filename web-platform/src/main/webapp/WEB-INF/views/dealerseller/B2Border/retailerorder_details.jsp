<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>订单查询_查看</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="${path }/commons/css/order_details.css"/>
	<link rel="stylesheet" type="text/css" href="${path }/commons/css/reset.css"/>
</head>
<body>
<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="w">
    <div class="order-detail">
        <div class="order-detail-main">
            <div class="od-hd">
                <dl>
                    <dt>订单号：</dt>
                    <dd>${orderPayDetailDto.orderPayVO.payId }</dd>
                    <dt>状态：</dt>
                    <dd>
                    <strong>
						<c:if test="${orderPayDetailDto.order.status eq 1}">待付款</c:if>
						<c:if test="${orderPayDetailDto.order.status eq 19 }">待付款</c:if>
						<c:if test="${orderPayDetailDto.order.status eq 20 }">待付款</c:if>
						<c:if test="${orderPayDetailDto.order.status eq 21 }">待发货</c:if>
						<c:if test="${orderPayDetailDto.order.status eq 31 }">待发货</c:if>
						<c:if test="${orderPayDetailDto.order.status eq 32 }">待发货</c:if>
						<c:if test="${orderPayDetailDto.order.status eq 33 }">待发货</c:if>
						<c:if test="${orderPayDetailDto.order.status eq 34 }">待发货</c:if>
						<c:if test="${orderPayDetailDto.order.status eq 41}">待发货</c:if>
						<c:if test="${orderPayDetailDto.order.status eq 51}">待发货</c:if>
						<c:if test="${orderPayDetailDto.order.status eq 61}">待发货</c:if>
						<c:if test="${orderPayDetailDto.order.status eq 21 }">待发货</c:if>
						<c:if test="${orderPayDetailDto.order.status eq 71 }">待发货</c:if>
						<c:if test="${orderPayDetailDto.order.status eq 81 }">待收货</c:if>
						<c:if test="${orderPayDetailDto.order.status eq 91 }">已完成</c:if>
						<c:if test="${orderPayDetailDto.order.status eq 101 }">已完成</c:if>
						<c:if test="${orderPayDetailDto.order.status eq 111 }">已取消</c:if>
						<c:if test="${orderPayDetailDto.order.status eq 112 }">已取消</c:if>
						<c:if test="${orderPayDetailDto.order.status eq 100 }">已取消</c:if>
						<c:if test="${orderPayDetailDto.order.status eq 99 }">已取消</c:if>
                    </strong>
                    </dd>
                </dl>
            </div>
            <div class="od-bd">
            
             <div class="area" id="payInfo">
                    <div class="area-hd">
                        <h3>收货人信息</h3>
                    </div>
                    <div class="area-bd clearfix">
                        <div class="clr"></div>
                        <dl>
                            <dt>收货人：</dt>
                            <dd>${fn:escapeXml(orderPayDetailDto.order.receiveName)}</dd>
                        </dl>
                        <dl>
                            <dt>收货地址：</dt>
                            <dd>${fn:escapeXml(orderPayDetailDto.order.receiveAddress)}</dd>
                        </dl>
                        <dl>
                            <dt>移动电话：</dt>
                            <dd>${fn:escapeXml(orderPayDetailDto.order.receiveMobilePhone)}</dd>
                        </dl>
                        <dl>
                            <dt>固定电话：</dt>
                            <dd>${fn:escapeXml(orderPayDetailDto.order.receivePhone)}</dd>
                        </dl>
                    </div>
                </div>
                
                <div class="area" id="payInfo">
                    <div class="area-hd">
                        <h3>零售商信息</h3>
                    </div>
                    <div class="area-bd clearfix">
                        <div class="clr"></div>
                        <dl>
                            <dt>商家名称：</dt>
                            <dd>${fn:escapeXml(retailer.name)}</dd>
                        </dl>
                        <dl>
                            <dt>联系电话：</dt>
                            <dd>${fn:escapeXml(retailer.mobile)}</dd>
                        </dl>
                        <dl>
                            <dt>电子邮箱：</dt>
                            <dd>${fn:escapeXml(retailer.email)}</dd>
                        </dl>
                        <dl>
                            <dt>详细地址：</dt>
                            <dd>${fn:escapeXml(retailer.address)}</dd>
                        </dl>
                    </div>
                </div>
                
                <div class="area" id="payInfo">
                    <div class="area-hd">
                        <h3>支付信息</h3>
                    </div>
                    <div class="area-bd clearfix">
                        <div class="clr"></div>
	                        <dl>
	                            <dt>订单金额：</dt>
	                            <dd>
	                            	<em>¥</em>
	                            	<fmt:formatNumber value="${orderPayDetailDto.orderPayVO.orderPrice}" pattern="0.00#"></fmt:formatNumber>
	                            </dd>
	                        </dl>
	                        <dl>
		                      <c:if test="${ orderPayDetailDto.orderPayVO.transferPrice -orderPayDetailDto.orderPayVO.realTransferprice >0 }">
			 					<dt>商品运费：</dt>
	                            <dd>
		                            <em>¥</em>
		 							<fmt:formatNumber value="${orderPayDetailDto.orderPayVO.transferPrice}" pattern="0.00#"></fmt:formatNumber>
	                            </dd>	
			 				 </c:if>
			 				 <c:if test="${ orderPayDetailDto.orderPayVO.realTransferprice -orderPayDetailDto.orderPayVO.transferPrice >=0 }">
			 					<dt>实际运费：</dt>
	                            <dd>
		                            <em>¥</em>
		 							<fmt:formatNumber value="${orderPayDetailDto.orderPayVO.realTransferprice}" pattern="0.00#"></fmt:formatNumber>
	                            </dd>	
			 				 </c:if>
	                        </dl>
	                        <dl>
	                            <dt>折扣金额：</dt>
	                            <dd>
	                            <em>-¥</em>
	 							   <c:if test="${(orderPayDetailDto.orderPayVO.transferPrice-orderPayDetailDto.orderPayVO.realTransferprice)<=0}">
	 							    <fmt:formatNumber value="${orderPayDetailDto.orderPayVO.discountPrice}" pattern="#0.00"/> 
	 							   </c:if>
	 							   <c:if test="${(orderPayDetailDto.orderPayVO.transferPrice-orderPayDetailDto.orderPayVO.realTransferprice)>0}">
		 							   <fmt:formatNumber value="${orderPayDetailDto.orderPayVO.discountPrice+orderPayDetailDto.orderPayVO.transferPrice-orderPayDetailDto.orderPayVO.realTransferprice}" pattern="#0.00"/>
		 							   (含运费折扣:<fmt:formatNumber value="${orderPayDetailDto.orderPayVO.transferPrice-orderPayDetailDto.orderPayVO.realTransferprice}" pattern="#0.00"/>)
	 							   </c:if>
	                            </dd>
	                        </dl>
	                        <dl>
	                            <dt>应付总额：</dt>
	                            <dd>
	                            <em>¥</em>
	                             <fmt:formatNumber value="${orderPayDetailDto.orderPayVO.price}" pattern="0.00#"></fmt:formatNumber>
	                            </dd>
	                        </dl>
	                       	<dl>
	                            <dt>已付金额：</dt>
	                            <dd>
	                            <em>¥</em>
	                             <fmt:formatNumber value="${orderPayDetailDto.orderPayVO.paidPrice}" pattern="0.00#"></fmt:formatNumber>
	                            </dd>
	                        </dl>
	                        <dl>
	                            <dt>交易余额：</dt>
	                            <dd>
	                            <em>¥</em>
	                             <fmt:formatNumber value="${orderPayDetailDto.orderPayVO.price-orderPayDetailDto.orderPayVO.paidPrice}" pattern="0.00#"></fmt:formatNumber>
	                            </dd>
	                        </dl>
                    	</div>
               		</div>
               		
               		
               		 <div class="area" id="payInfo">
                    <div class="area-hd">
                        <h3>发票信息</h3>
                    </div>
                    <div class="area-bd clearfix">
                        <div class="clr"></div>
                        <c:if test="${orderPayDetailDto.order.needInvoice == 1}">
                        <dl>
	                            <dt>发票类型：</dt>
	                            <dd>
	                            	<em></em>
	                            	<c:if test="${orderPayDetailDto.order.invoiceType eq 1}">
	                            	增值税普通发票
	                            	</c:if>
	                            	<c:if test="${orderPayDetailDto.order.invoiceType eq 3}">
	                            	增值税专用发票
	                            	</c:if>
	                            </dd>
	                        </dl>
	                        <c:if test="${orderPayDetailDto.order.invoiceType eq 1 || orderPayDetailDto.order.invoiceType eq 3}">
	                        <dl>
	                            <dt>单位名称：</dt>
	                            <dd>
		                            <em></em>${orderPayDetailDto.order.invoiceTitle}
	                            </dd>
	                        </dl>
	                        <dl>
	                            <dt>纳税人识别号：</dt>
	                            <dd>
	                            <em></em>${orderPayDetailDto.order.invoiceTaxpayerId}
	                            </dd>
	                        </dl>
	                        <dl>
	                            <dt>注册地址：</dt>
	                            <dd>
	                            <em></em>${orderPayDetailDto.order.invoiceRegisteredAddr}
	                            </dd>
	                        </dl>
	                        <dl>
	                            <dt>注册电话：</dt>
	                            <dd>
	                            <em></em>${orderPayDetailDto.order.invoiceRegisteredPhone}
	                            </dd>
	                        </dl>
	                       	<dl>
	                            <dt>开户银行：</dt>
	                            <dd>
	                            <em></em>${orderPayDetailDto.order.invoiceBankName}
	                            </dd>
	                        </dl>
	                        <dl>
	                            <dt>银行账户：</dt>
	                            <dd>
	                            <em></em>${orderPayDetailDto.order.invoiceBankNo}
	                            </dd>
	                        </dl>
	                        </c:if>
	                        <c:if test="${orderPayDetailDto.order.invoiceType eq 1}">
	                        <dl>
	                            <dt>发票抬头：</dt>
	                            <dd>
	                            <em></em>${fn:escapeXml(orderPayDetailDto.order.invoiceTitle)}
	                            </dd>
	                        </dl>
	                        </c:if>
	                        
	                         <dl>
	                            <dt>发票明细：</dt>
	                            <dd>
	                            <em></em>
	                            ${fn:escapeXml(orderPayDetailDto.order.invoiceDetails)}
	                            </dd>
	                        </dl>
	                        </c:if>
	                        <c:if test="${orderPayDetailDto.order.needInvoice == 0}">
	                        	无发票
	                        </c:if>
                    	</div>
               		</div>

                <div class="area" id="logisticsInfo">
                    <div class="area-hd">
                        <h3>物流信息</h3>
                    </div>
                    <div class="area-bd">
                     <c:choose>
                     	<c:when test="${empty orderPayDetailDto.shipOrderDTO}">
                           <div class="no-logistics"> 暂无包裹信息</div>  
                        </c:when>
                        <c:otherwise>
	 						<c:forEach items="${orderPayDetailDto.shipOrderDTO }" var="shipOrderDTO">
	                        <div class="item">
	                            <div class="sub"> <span class="year">
	                            <fmt:formatDate value="${shipOrderDTO.createTime}" pattern="yyyy"/></span> <span class="month">
	                            <fmt:formatDate value="${shipOrderDTO.createTime}" pattern="MM/dd"/></span>
	                                <div class="clr"></div>
	                                <span class="total"><em>¥</em><fmt:formatNumber value="${shipOrderDTO.price }" pattern="0.00#"/></span> </div>
	                            <div class="main">
	                                <div class="main-content">
	                                    <div class="mc-hd"> 
	                                    <span class="tips">
	                                    <c:if test="${shipOrderDTO.isFuture eq 1}">期货包裹</c:if>
	                                    <c:if test="${shipOrderDTO.isFuture eq 0}">现货包裹</c:if>
	                                    </span>
	                                     <span class="consignor">发货人：${shipOrderDTO.retailerName }</span> 
	                                     <span class="consignor">包裹号：${shipOrderDTO.id }</span> 
	                                    </div>
	                                    <div class="mc-bd">
	                                        <ul class="goods-list clearfix">
	                                        <c:forEach items="${shipOrderDTO.shipOrderItemMap }" var="shipItemMap">
		                                        <c:forEach items="${shipItemMap.value }" var="shipItemMapValue">
		                                            <li>
		                                                <div class="goods-box">
		                                                    <div class="goods-pic"><a href="javascript:void(0)"><img src="${shipItemMapValue.productUrl }" width="60" height="60" /></a><font style="color: red"><c:if test="${shipItemMapValue.productType eq 1 }">(赠品)</c:if></font></div>
		                                                    <div class="goods-txt">
			                                                    <span class="sku" title="${shipItemMapValue.skuName}">规格：${shipItemMapValue.skuName}</span>
			                                                    <span class="amount"  title="${shipItemMapValue.qty}">数量：${shipItemMapValue.qty}</span>
		                                                    </div>
		                                                </div>
		                                            </li>
		                                        </c:forEach>
	                                        </c:forEach>
	                                        </ul>
	                                    </div>
	                                </div>
	                                <div class="main-bar">
	                                    <div class="box"> 
	                                    <c:if test="${shipOrderDTO.logisticsCarriersName != null }">
	                                    	<span class="kd-name">${shipOrderDTO.logisticsCarriersName }</span> 
		                                    <span class="kd-code">快递单号：<br><a href="javascript:void(0)"><em>${shipOrderDTO.logisticsNumber }</em></a></span>
	                                    </c:if>
	                                     <c:if test="${shipOrderDTO.logisticsCarriersName == null }">
	                                    	<span class="kd-name">暂无物流信息</span>
	                                    </c:if>
	                                    </div>
	                                </div>
	                            </div>
	                        </div>
	                    </c:forEach>
                    	</c:otherwise>
                     </c:choose>
            </div>
                  <div class="area" id="goodsInfo">
                    <div class="area-hd">
                        <h3>商品信息</h3>
                    </div>
                    <div class="area-bd">
                        <table class="goodsTable">
                            <thead>
                                <tr>
                                    <th width="300">商品信息</th>
                                    <th width="137"><span class="y-line"></span>期货/现货</th>
                                    <th width="140"><span class="y-line"></span>规格</th>
                                    <th width="140"><span class="y-line"></span>单价</th>
                                    <th width="140"><span class="y-line"></span>数量</th>
                                    <th width="140"><span class="y-line"></span>合计</th>
                                   <!--  <th width=""><span class="y-line"></span>操作</th> -->
                                </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${orderPayDetailDto.orderItemVOs }" var="orderItemVOmap">
                            	
                            	<c:forEach items="${orderItemVOmap.value }" var="orderItemVOmapValue" varStatus="count">
                            		<c:set value="0" var="totalPrice"></c:set>
                            		<tr>
	                                    <td class="rbBorder f-tl">
											<div class="p-goods">
												<div class="p-img"><a href="javascript:void(0)"><img src="${orderPayDetailDto.imageUrlMap[orderItemVOmap.key]}" alt="${orderItemVOmapValue.imgAlt}" width="60" height="60"></a></div>
												<div class="p-name"><p><a href="javascript:void(0)">${orderItemVOmapValue.order.pName}</a></p></div>
											</div>
										</td>
	                                    <td colspan="6" class="bBorder">
	                                    	<table width="100%" height="100%">
	                                            <colgroup>
	                                            <col width="110">
	                                            <col width="110">
	                                            <col width="110">
	                                            <col width="110">
	                                            <col width="110">
	                                            <col>
	                                            </colgroup>
	                                        <tbody>
												<tr>
	                                                <td></td>
	                                                <td></td>
	                                                <td></td>
	                                                <td></td>
	                                                <td></td>
	                                                <td></td>
	                                            </tr>
	
												<tr>
													<td class="rBorder" width="137">
														<c:if test="${orderItemVOmapValue.order.isFutures eq 1}">期货</c:if>
														<c:if test="${orderItemVOmapValue.order.isFutures eq 0}">现货</c:if>
														<div class="rb_d">
														<c:choose>
														<c:when test="${orderItemVOmapValue.productSourceType eq 0}">
														(自采)
														</c:when>
														<c:when test="${orderItemVOmapValue.productSourceType eq 1}">
														(国内经销商)
														</c:when>
														</c:choose>
														
														</div>
													</td>
													<td colspan="3">
														<table class="t-sku" width="100%" height="100%">
															<colgroup>
															<col width="140">
															<col width="140">
															<col>
															</colgroup>
															<tbody>
															<c:forEach items="${orderItemVOmapValue.orderItemList}" var="orderItemDetail">
															<c:set var="totalPrice" value="${totalPrice+orderItemDetail.price*orderItemDetail.skuQty}"/>
							                     			<c:set var="sum" value="${sum+orderItemDetail.price*orderItemDetail.skuQty}"/>
																<c:if test="${orderItemDetail.productType eq 1}">
																<tr>
																		<td><span class="rb">${orderItemDetail.skuName}<p class="red">赠品</p></span></td>
																		<td><span class="rb">¥<fmt:formatNumber value="${orderItemDetail.price}" pattern="0.00#"></fmt:formatNumber></span></td>
																		<td><span class="rb">${orderItemDetail.skuQty}</span></td>
																</tr>
																</c:if>
																<c:if test="${orderItemDetail.productType ne 1}">
																<tr>
																		<td><span class="rb">${orderItemDetail.skuName}</span></td>
																		<td><span class="rb">¥<fmt:formatNumber value="${orderItemDetail.price}" pattern="0.00#"></fmt:formatNumber></span></td>
																		<td><span class="rb">${orderItemDetail.skuQty}</span></td>
																</tr>
																</c:if>
															</c:forEach>	 
															</tbody>
														</table>
														
														
													</td>
													<td class="lrBorder"><span class="rb">¥<fmt:formatNumber value="${totalPrice }" pattern="0.00#"></fmt:formatNumber></span></td>
												</tr>
	                                        </tbody>
											</table>
										</td>
	                                </tr>
	                                </c:forEach>
                            	</c:forEach>
                            </tbody>
                        </table>
                        <div class="table-footer">
                            <p class="money-total">金额合计：<span><em>¥<fmt:formatNumber value="${sum}" pattern="0.00#"/></em></span></p>
                        </div> 
                    </div>
                </div>
        </div>
    </div>
</div>
</body>
</html>
