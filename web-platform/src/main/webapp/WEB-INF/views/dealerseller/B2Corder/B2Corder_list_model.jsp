<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
request.setAttribute("path",path);
%>
	<div class="tb-void">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tbody>
						<tr>
							<th width="250">商品名称</th>
							<th width="100">收货人</th>
							<th width="100">数量</th>
							<th width="100">实付现金<i class="rmb-unit">¥</i></th>
							<th width="100">实付红旗卷<i class="rmb-unit">¥</i></th>
							<th width="100">支付方式</th>
							<th width="100">状态</th>
							<th width="80">操作</th>
						</tr>
					</tbody>
					
					<tbody>
					
						<c:forEach items="${pageBean.result }" var="orderList">
							<tr class="tr-th">
								<td colspan="10" style="position: relative;">
									<span class="fl"><fmt:formatDate value="${orderList.createTime }" type="both"/></span>
									<span class="fc"> 订单号:<a href="javascript:void(0)">${orderList.id }</a></span>
									<span class="fr"> 买家:<i>${orderList.userName } </i>     &nbsp;&nbsp;&nbsp;买家ID:<i>${orderList.userId }</i> </span>
									<c:if test="${!empty orderList.message }">
										<span class="tcolr"><button type="button" class="lyan">订单留言</button></span>
										<div class="reply" style="display:none;">
											${orderList.message }
		 				                </div>
									</c:if>
								</td>
							</tr>
							<tr>
							  <td>
						   <div class="qu">
						   <!-- 11.宁波海外直邮  12.宁波保税区发货 13.郑州海外直邮 14.郑州保税区发货  1.国内发货 21.韩国直邮 -->
			                			<c:if test="${orderList.supplyType eq 1}"><a href="javascript:void(0)"><span class="at">国内发货</span></a></c:if>
		        						<c:if test="${orderList.supplyType eq 21}"><a href="javascript:void(0)"><span class="ac">海外直邮</span></a></c:if>
		        						<c:if test="${orderList.supplyType eq 12}"><a href="javascript:void(0)"><span class="ah">保税区发货</span></a></c:if>
		        						<c:if test="${orderList.supplyType eq 11}"><a href="javascript:void(0)"><span  style="background:#F02146 ">海外直邮</span></a></c:if>
		        						<c:if test="${orderList.supplyType eq 31}"><a href="javascript:void(0)"><span  style="background:#FC0EE0 ">卓悦发货</span></a></c:if>
							   			<c:if test="${orderList.supplyType eq 50}"><a href="javascript:void(0)"><span  style="background:#FC0EE0 ">特卖商品</span></a></c:if>
							   			<c:if test="${orderList.supplyType eq 51}"><a href="javascript:void(0)"><span  style="background:#ff4800 ">${orderList.merchantName}</span></a></c:if>
		        						<%-- <c:if test="${orderList.supplyType eq 13}"><a href="javascript:void(0)"><span  style="background:#F02146 ">郑州海外直邮</span></a></c:if>
		        						<c:if test="${orderList.supplyType eq 14}"><a href="javascript:void(0)"><span  style="background:#F02146 ">郑州保税区发货</span></a></c:if> --%>


<%-- 								<c:forEach items="${_orderTypeList}" var="__orderTypeList">
									<c:if test="${orderList.orderType eq __orderTypeList.value}"><a href="javascript:void(0)"><span  style="background:#F02146 ">${__orderTypeList.key}</span></a></c:if>
								</c:forEach> --%>


			                		<span>
	        						</span>
	        					
	        						
			                	</div> 
			                	       <c:if test="${orderList.companyQy==10 }">
			                	       	家庭号订单
			                	       </c:if>                          
			                	       <c:if test="${orderList.orderType==39 }">
			                	       	幸福购订单
			                	       </c:if>                          
			                      <ul class="goods-list clearfix">
			                      <c:forEach items="${orderList.productList }" var="productList">
			                         <li>
			                            <div class="goods-box">
			                          
			                                <div class="goods-pic"><a href="${path}/user/viewInfo?source=1&&id2=${productList.supplierId}" target="_blank">
			                                <img src="${productList.imgUrl }" width="60" height="60" ></a></div>
			                                <div class="goods-txt">
				                              <span class="sku">商品:${productList.pName }</span>
				                               <span class="amount">数量:${productList.count }</span>
			                                </div>
			                            </div>
			                         </li>
			                       
								  </c:forEach>
			                       </ul>
	                            </td>
								<td>${orderList.receiveName }</td>
								<%-- ${orderList.receiveName } --%>
								<td>${orderList.totalQty }</td>
								<td>
								<c:if test="${orderList.mixPayStatus==1 && orderList.paidPrice>0 && !((orderList.cashPrice < 0) or (orderList.cashPrice > 0))}">
									<fmt:formatNumber value="${orderList.paidPrice*orderList.cashRatio }" pattern="0.00#"></fmt:formatNumber>
								</c:if>
								<c:if test="${orderList.mixPayStatus==1&&orderList.paidPrice>0&&orderList.cashPrice>0}">
									<fmt:formatNumber value="${orderList.cashPrice}" pattern="0.00#"></fmt:formatNumber>
								</c:if>
								<c:if test="${orderList.mixPayStatus!=1&&orderList.payWay!=3 && orderList.paidPrice>0 && !((orderList.cashPrice < 0) or (orderList.cashPrice > 0))}">
								<fmt:formatNumber value="${orderList.paidPrice }" pattern="0.00#"></fmt:formatNumber>
								</c:if><%-- ${orderList.mixPayStatus!=1&&orderList.payWay!=3&&orderList.paidPrice>0&&orderList.cashPrice==0}&${orderList.paidPrice } --%>
								</td>
								<td>
								<c:if test="${orderList.mixPayStatus==1&&orderList.paidPrice>0&&orderList.cashPrice>0}">
									<fmt:formatNumber value="${orderList.hqjPrice}" pattern="0.00#"></fmt:formatNumber>
								</c:if>
								<c:if test="${orderList.mixPayStatus==1&&orderList.paidPrice>0 && !((orderList.cashPrice < 0) or (orderList.cashPrice > 0))}">
									<fmt:formatNumber value="${orderList.paidPrice*orderList.hqRatio }" pattern="0.00#"></fmt:formatNumber>
								</c:if>
								<c:if test="${orderList.payWay=='3'&&orderList.mixPayStatus!=1 && orderList.paidPrice>0 && !((orderList.cashPrice < 0) or (orderList.cashPrice > 0))}">
								<fmt:formatNumber value="${orderList.paidPrice }" pattern="0.00#"></fmt:formatNumber>
								</c:if><%-- ${orderList.id}&${orderList.cashPrice.unscaledValue()==0}&${orderList.cashPrice}
								${orderList.payWay==3&&orderList.mixPayStatus!=1&&orderList.paidPrice>0&&orderList.cashPrice!=0}&${orderList.paidPrice } --%>
								</td>
								<td>
									<c:if test="${orderList.payWay eq  2}">微信</c:if>
									<c:if test="${orderList.payWay eq  3}">红旗劵支付</c:if>
									<c:if test="${orderList.payWay eq  4}">京东快捷支付</c:if>
									<c:if test="${orderList.payWay eq  5}">京东网银支付</c:if>
									<c:if test="${orderList.payWay eq  6}">支付宝</c:if>
								</td>
								<td>
									<c:if test="${orderList.status eq  1}">待支付</c:if>
									<c:if test="${orderList.status eq  21}">已支付</c:if>
									<c:if test="${orderList.status eq  31}">待海关审核</c:if>
									<c:if test="${orderList.status eq  41}">待发货</c:if>
									<c:if test="${orderList.status eq  67}">海关审核订单失败(待退款)</c:if>
									<c:if test="${orderList.status eq  68}">海关支付单审核失败(待退款)</c:if>
									<c:if test="${orderList.status eq  69}">海关物流单审核失败(待退款)</c:if>
									<c:if test="${orderList.status eq  70}">海关审核失败(待退款)</c:if>
									<c:if test="${orderList.status eq  79}">已退款</c:if>
									<c:if test="${orderList.status eq  81}">已发货</c:if>
									<c:if test="${orderList.status eq  91}">已收货</c:if>
									<c:if test="${orderList.status eq  99}">用户取消</c:if>
									<c:if test="${orderList.status eq  100}">自动取消</c:if>
								</td>
								<td class="order-doi">
								<c:if test="${!empty buttonsMap['C订单管理-订单详情'] }">
									<a href="../customerOrder/getCustomerOrderDetailsById?orderId=${orderList.id }" target="_blank">订单详情</a>
									<c:if test="${orderList.portType eq 'guofeiyan' && orderList.status ne 91 and orderList.status ne 99 and orderList.status ne 100 and orderList.status ne 79}">
										<a href="javascript:void(0)" onclick="showUpdateXinxi('${orderList.id }')">修改订单</a>
									</c:if>
									<c:if test="${orderList.portType eq 'guofeiyan' && orderList.status eq 81 && orderList.logisticsCarriersName==null &&orderList.logisticsCarriersId==null &&orderList.logisticsNumber ==null}">
										<a class="t-btn" href="javascript:void(0)" onclick="showWuLiu('${orderList.id }')">补录物流</a>
									</c:if>
								</c:if>	
								<c:if test="${!empty pageType}">
									<a href="javascript:void(0)" onclick="pushOrderAgain('${orderList.id }','${orderList.status }')">海关订单重推</a>
								</c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<c:if test="${!empty pageBean.result}">
				<%@include file="/WEB-INF/views/dealerbuy/basePage/basePage.jsp" %>
			</c:if>