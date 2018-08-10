<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
request.setAttribute("path",path);
%>
<div class="goodsWrap">
	    <table class="goodsTable">
	        <thead>
	            <tr class="goods-tr">
	                <th width="360">商品</th>
	                <th width="120">金额(元)</th>
	                <th width="120">数量</th>
	                <th width="120">交易状态</th>
	                <th width="">交易操作</th>
	            </tr>
	        </thead>
	        <tbody>
	            <tr class="f-h10">
	                <td colspan="5"></td>
	            </tr>
	           <c:forEach items="${pageBean.result }" var="shipOrderDTO" varStatus="count">
	               <tr class="order-hd">
				   <td class="first"  colspan="2">
						<div class="summary" ><%-- ${shipOrderDTO.warehouseName } --%>
							<span class="number last-item">
								<c:choose>
									<c:when test="${shipOrderDTO.orderType eq 1}">
										订单号：${shipOrderDTO.payId}
									</c:when>
									<c:otherwise>
										订单号：${shipOrderDTO.orderId}
									</c:otherwise>
								</c:choose>
							</span>
							<span>下单时间：<fmt:formatDate value="${shipOrderDTO.createTime}" type="both"/></span>
						</div>
					</td>
					<c:choose>
						<c:when test="${shipOrderDTO.orderType eq 1}">
							<td class="column" colspan="1"  title="${fn:escapeXml(shipOrderDTO.retailerName)}" >
								<span class="co">零售商：${fn:escapeXml(shipOrderDTO.retailerName)}</span>
							</td>
							
						</c:when>
						<c:otherwise>
							<td class="column" colspan="1"  title="${fn:escapeXml(shipOrderDTO.userName)}" >
								<span class="co">用户名：${fn:escapeXml(shipOrderDTO.userName)}</span>
							</td>
						</c:otherwise>
					</c:choose>
					
					<td class="last" colspan="2">
						<span class="co1">经销商：${fn:escapeXml(shipOrderDTO.dealerName)}</span>
					</td>
					</tr>	
			            <tr class="goods-tr">
			                <td class="f-tl">
			                	<div class="qu">
			                		<a href="#">
			                		<c:choose>
										<c:when test="${shipOrderDTO.orderType eq 1}">
											<c:if test="${shipOrderDTO.isFuture eq 0}">现货</c:if><c:if test="${shipOrderDTO.isFuture eq 1}">期货</c:if>包裹
										</c:when>
										<c:otherwise>
											国内包裹
										</c:otherwise>
									</c:choose>
			                		</a>
			                		<span>包裹号：${shipOrderDTO.id}</span>
			                	</div>
				                <ul class="goods-list clearfix">
				                <c:forEach items="${shipOrderDTO.orderProductList }" var="product">
				                        <li>
				                           <div class="goods-box">
				                              <div class="goods-pic">
				                                  <a href="#">
				                                   <img src="${product.imgurl }" width="60" height="60" />
				                                  </a>
				                               </div>
				                              <div class="goods-txt" title="${product.pname }">
				                               <span class="sku" >${product.pname }</span>
				                              </div>
				                           </div>
				                        </li>
				                </c:forEach>	
				                </ul>
		                    </td>
			               
			                <td title=""><span class="co2"><fmt:formatNumber pattern="0.00#">${shipOrderDTO.price }</fmt:formatNumber></span></td>
			                <td title=""><span class="co2">${shipOrderDTO.qty }</span></td>
			                <td>
			                <c:if test="${shipOrderDTO.status eq 1 }">待发货</c:if>
							<c:if test="${shipOrderDTO.status eq 2 }">已发货 </c:if>
							<c:if test="${shipOrderDTO.status eq 3 }">已完成 </c:if>
							<c:if test="${shipOrderDTO.status eq 9 }">已取消 </c:if>
			                </td>
			                <td>
								<c:if test="${!empty buttonsMap['包裹查询-包裹详情'] }">
							 		<a class="#" href="<%=path %>/orderPackage/getOrderPackageDetailsById?id=${shipOrderDTO.id}" > 订单详情 </a>
								</c:if>
			                </td>
			            </tr>
			           <tr class="f-h10">
		                <td colspan="5"></td>
		            </tr>
		            </c:forEach>
	        </tbody>
	    </table>
</div>

<div class="goodsWrap" style="display:none;">
   2
</div>

<div class="blank10"></div>
<c:if test="${!empty pageBean.result}">
	<%@include file="/WEB-INF/views/dealerbuy/basePage/basePage.jsp" %>
</c:if>
