<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
request.setAttribute("path",path);
%>
<script type="text/javascript">
 //去分页显示（参数page）
	 function toPage(page){
	 	byConditionSearch(page);
	 }
</script>

<table id="J_BoughtTable"  width="100%" class="bought-table" data-spm="9">
					<thead>
						<tr class="col-name">
							<th>商品</th>
							<th>区间单价</th>
							<th>数量</th>
							<!-- <th>商品操作</th> -->
							<th>应付款</th>
							<th>交易状态</th>
							<th>交易操作</th>
						</tr>
					</thead>	
					<c:if test="${empty pageBean.result}">
						<tr>
							<td colspan="6">
								<center>
									<img src="${path }/commons/images/no.png" />
								</center></td>
						</tr>
						</c:if>	     
					<tbody class="data">  
				<c:forEach items="${pageBean.result}" var="waitOrder">
						<tr class="sep-row">
							<td colspan="6"></td>
						</tr> 
						<tr class="order-hd">
							<td class="first" colspan="6">
								<div class="summary" >
									<span class="dealtime jchu">目前累计数量：${waitOrder.qty }</span>
									<span class="number last-item">(最小起订量<i class="shu">${waitOrder.minWholesaleQty }</i>件)</span>&nbsp;&nbsp;&nbsp;
									<span class="number last-item">供应商：${waitOrder.supplyName}</span>
								</div>
							</td>
						</tr>
						<tr class="order-bd last order-last">
							<td class="baobei"  >
								<a class="pic J_MakePoint">
								<img src="${waitOrder.imgUrl }" alt="查看宝贝详情">
								</a>
								<div class="desc">
									<p class="baobei-name">
									  <i title="${waitOrder.pName }"> ${waitOrder.pName } </i>
									</p>
								</div>
								</td>
								<td class="price b1">
									<i>
										<span>
											<c:choose>
												<c:when test="${waitOrder.productPriceMin==waitOrder.productPriceMax }">
													${waitOrder.moneyUnitSymbols }
													<fmt:formatNumber pattern="0.0000#" value="${waitOrder.productPriceMin}"></fmt:formatNumber>
												</c:when>
												<c:otherwise>
													${waitOrder.moneyUnitSymbols }	<fmt:formatNumber pattern="0.0000#" value="${waitOrder.productPriceMin}"></fmt:formatNumber>~
													<fmt:formatNumber pattern="0.0000#" value="${waitOrder.productPriceMax}"></fmt:formatNumber>
												</c:otherwise>
											</c:choose>
										</span>
									</i>
								</td>
								<td class="quantity b1">
								   <i class="special-num">${waitOrder.qty }</i>
								</td>
								<td class="quantity b1">
								   <%-- <i>${waitOrder.moneyUnitSymbols }<fmt:formatNumber value="${waitOrder.paidPrice-waitOrder.transferPrice}" pattern="0.0000#"/>
								   </i> --%>
								    <i>${waitOrder.moneyUnitSymbols }<fmt:formatNumber pattern="0.0000#" value="${waitOrder.paidPrice}"></fmt:formatNumber>
								   </i>
								</td>
								<td class="trade-status">
								<i class="status tm-h" > 待合并 </i>
								</td>
								<td class="trade-operate">
									<c:if test="${!empty buttonsMap['待合并订单-合并订单'] }">
										<c:if test="${waitOrder.qty>=waitOrder.minWholesaleQty}">
											<button  class="gb"  id="joinOrderBtn"  value="${waitOrder.pid }" onclick="location.href='<%=path %>/waitOrder/showOrderDetail?pid=${waitOrder.pid}&micQty=${waitOrder.minWholesaleQty}'">合并订单</button>
										</c:if>
									</c:if>
									<i class="status tm-h"><br>
										<c:choose>
											<c:when test="${!empty buttonsMap['待合并订单-订单详情'] }">
												<c:if test="${waitOrder.qty>=waitOrder.minWholesaleQty}">
												 <a class="" href="<%=path %>/waitOrder/showOrderDetail?pid=${waitOrder.pid}&micQty=${waitOrder.minWholesaleQty}"> 订单详情 </a>
												</c:if>
											</c:when>
											<c:otherwise>
												<a>未达到起订量</a>
											</c:otherwise>
										</c:choose>
								    </i>
								</td>
						</tr>
						</c:forEach>	
				  </tbody>
				</table>
				<c:if test="${!empty pageBean.result}">
					<%@include file="/WEB-INF/views/dealerbuy/basePage/basePage.jsp" %>	
				</c:if>
