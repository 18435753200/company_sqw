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
							<th width="185">订单信息</th>
							<th width="80">收货人</th>
							<th width="80">订单总额<i class="rmb-unit">¥</i></th>
							<th width="90">创建时间</th>
							<th width="80">状态</th>
							<th width="100">发票类型</th>
							<th width="">操作</th>
						</tr>
					</tbody>
					
					<c:forEach items="${pageBean.result }" var="retailerOrder">
						<tbody>
							<tr class="tr-th">
								<td colspan="8" style="position: relative;">
								<span class="tcol1">订单编号:${retailerOrder.orderPayVO.payId}&nbsp;&nbsp;</span>
								<span class="tcol1">零售商:${retailerOrder.retailerName}&nbsp;&nbsp;</span>
								<span class="tcol1">零售商ID:${retailerOrder.orders[0].retailerId } </span>
								<c:if test="${!empty retailerOrder.orderPayVO.message }">
									<span class="tcolr"><button type="button" class="lyan">订单留言</button></span>
									<div class="reply" style="display:none;">
										${retailerOrder.orderPayVO.message }
	 				                </div>
								</c:if>
								</td>
							</tr>
							<tr class="tr-td">
							
								<td>	
									<div class="qu">
									<c:if test="${retailerOrder.orderPayVO.orderPlatform eq 1}">
										<a href="#"><span class="at">订单来自-PC</span></a>
									</c:if>
									<c:if test="${retailerOrder.orderPayVO.orderPlatform eq 0}">
										<a href="#"><span class="ac">订单来自-PAD</span></a>
									</c:if>	
									<c:if test="${retailerOrder.orderPayVO.orderPlatform eq 2}">
										<a href="#"><span class="ac">订单来自-WAP</span></a>
									</c:if>	
				                	</div>
								    <ul class="goods-list clearfix">
									    <c:forEach var="productDto" items="${retailerOrder.productDtos }" varStatus="count">
									  	<c:set var="pid" value="${productDto.pid}"></c:set>
										   <li>
					                           <div class="goods-box" >
					                              <div class="goods-pic">
					                                  <a href="#">
					                                   <img src="${productDto.imgUrl }" width="60" height="60" />
					                                  </a>
					                               </div>
					                              <div class="goods-txt" title="">
					                               <span class="sku" >商品：${productDto.pName } </span>
					                               <span class="sku" >数量：${retailerOrder.orders[count.index].qty }</span>
					                              </div>
					                           </div>
						                   </li>
									    </c:forEach>	 
					                </ul> 
								</td>
								<td><div class="u-name">${retailerOrder.receiveName}</div></td>
								<td><span class="ftx-03"><fmt:formatNumber value="${retailerOrder.orderPayVO.price}" pattern="0.00#"></fmt:formatNumber></span></td>
								<td><fmt:formatDate value="${retailerOrder.orderPayVO.createTime}" type="both"/></td>
								<td>
								<c:forEach items="${retailerOrder.status}" var="status">
								
								<c:if test="${status==1 }">待付款</c:if>
								<c:if test="${status==19 }">待付款</c:if>
								<c:if test="${status==20 }">待付款</c:if>
								<c:if test="${status==21 }">待发货</c:if>
								<c:if test="${status==31 }">待发货</c:if>
								<c:if test="${status==32 }">待发货</c:if>
								<c:if test="${status==33 }">待发货</c:if>
								<c:if test="${status==34 }">待发货</c:if>
								<c:if test="${status==41}">待发货</c:if>
								<c:if test="${status==51}">待发货</c:if>
								<c:if test="${status==61}">待发货</c:if>
								<c:if test="${status==71 }">待发货</c:if>
								<c:if test="${status==81 }">待收货</c:if>
								<c:if test="${status==91 }">已完成</c:if>
								<c:if test="${status==101 }">已完成</c:if>
								<c:if test="${status==111 }">已取消</c:if>
								<c:if test="${status==112 }">已取消</c:if>
								<c:if test="${status==100 }">已取消</c:if>
								<c:if test="${status==99 }">已取消</c:if>
								</c:forEach>
								
								</td>
								<td>
								<c:if test="${retailerOrder.orderPayVO.needInvoice eq 1}">
									<c:if test="${retailerOrder.orderPayVO.invoiceType eq 1}">
									增值税普通发票
									</c:if>
									<c:if test="${retailerOrder.orderPayVO.invoiceType eq 3}">
									增值税专用发票
									</c:if>
								</c:if>
								<c:if test="${retailerOrder.orderPayVO.needInvoice eq 0}">无发票</c:if>
								</td>
								<td class="order-doi">
								<c:if test="${!empty buttonsMap['B订单管理-订单详情'] }">
									<a target="_blank" href="${path }/retailerOrder/getRetailerOrderDetailByPayId?payId=${retailerOrder.orderPayVO.payId}">订单详情</a>
								</c:if>	
								<c:if test="${!empty buttonsMap['B订单管理-取消订单'] }">
									<c:if test="${retailerOrder.isCancle eq 1 }">
										<c:forEach items="${retailerOrder.status}" var="status">
											<c:if test="${status eq 1 }">
												<a href="javascript:void(0)" onclick="goCancelOrderByWofe('${retailerOrder.orderPayVO.payId}')">取消订单</a>
											</c:if>
											<c:if test="${status eq 21 }">
												<a href="javascript:void(0)" onclick="goCancelOrderByWofe('${retailerOrder.orderPayVO.payId}')">取消订单</a>
											</c:if>
											<c:if test="${status eq 31 }">
												<a href="javascript:void(0)" onclick="goCancelOrderByWofe('${retailerOrder.orderPayVO.payId}')">取消订单</a>
											</c:if>
											<c:if test="${status eq 32 }">
												<a href="javascript:void(0)" onclick="goCancelOrderByWofe('${retailerOrder.orderPayVO.payId}')">取消订单</a>
											</c:if>
											<c:if test="${status eq 33 }">
												<a href="javascript:void(0)" onclick="goCancelOrderByWofe('${retailerOrder.orderPayVO.payId}')">取消订单</a>
											</c:if>
											<c:if test="${status eq 34 }">
												<a href="javascript:void(0)" onclick="goCancelOrderByWofe('${retailerOrder.orderPayVO.payId}')">取消订单</a>
											</c:if>
											<c:if test="${status eq 41 }">
												<a href="javascript:void(0)" onclick="goCancelOrderByWofe('${retailerOrder.orderPayVO.payId}')">取消订单</a>
											</c:if>
											<c:if test="${status eq 51 }">
												<a href="javascript:void(0)" onclick="goCancelOrderByWofe('${retailerOrder.orderPayVO.payId}')">取消订单</a>
											</c:if>
											<c:if test="${status eq 71 }">
												<a href="javascript:void(0)" onclick="goCancelOrderByWofe('${retailerOrder.orderPayVO.payId}')">取消订单</a>
											</c:if>
										</c:forEach>
									</c:if>
								</c:if>
								<c:forEach items="${retailerOrder.status}" var="status">
									<c:if test="${status eq 41 }">
										<c:if test="${retailerOrder.supplyType eq 11}">
											<a class="t-btn" href="javascript:void(0)" onclick="showWuLiu('${retailerOrder.orderId }', '${retailerOrder.receiveName }','${retailerOrder.receiveMobilePhone }','${retailerOrder.receiveAddress }')">发货</a>
										</c:if>
									</c:if>
								</c:forEach>
								
								
								</td>
							</tr>
						</tbody>
					</c:forEach>
				</table>
			</div>
			<c:if test="${!empty pageBean.result}">
	<%@include file="/WEB-INF/views/dealerbuy/basePage/basePage.jsp" %>
</c:if>
