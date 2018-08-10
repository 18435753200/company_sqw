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
	 	byConditionSearchBuy(page);
	 }
</script>
<table id="J_BoughtTable" width="100%" class="bought-table" data-spm="9">
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
					<c:forEach items="${pageBean.result}" var="buyOrder">
						<tr class="sep-row">
							
						</tr> 
						<tr class="order-hd">
							<td class="first" colspan="6">
								<div class="summary" >
									<span class="dealtime jchu">下单时间：<fmt:formatDate value="${buyOrder.createTime}" type="both"/></span>
									<span class="number last-item">采购单号：${buyOrder.poId } <b style="margin:0 10px 0 10px;">供应商：${buyOrder.supplyName}</b>
									（最小起订量<i class="shu">${buyOrder.minWholesaleQty }</i>件）</span>
								</div>
							</td>
						</tr>
						<tr class="order-bd last order-last">
							<td class="baobei"  >
								<a class="pic J_MakePoint">
								<img src="${buyOrder.imgUrl}" alt="查看宝贝详情">
								</a>
								<div class="desc">
									<p class="baobei-name">
									<i title="${fn:escapeXml(buyOrder.pName)}"> ${fn:escapeXml(buyOrder.pName)}</i>
									</p>
								</div>
								</td>
								<td class="price b1">
									<i><span>
									<c:choose>
									<c:when test="${buyOrder.productPriceMin==buyOrder.productPriceMax }">
										${buyOrder.moneyUnitSymbols }
										<fmt:formatNumber pattern="0.0000#" value="${buyOrder.productPriceMin}"></fmt:formatNumber>
									</c:when>
									<c:otherwise>
										${buyOrder.moneyUnitSymbols }<fmt:formatNumber pattern="0.0000#" value="${buyOrder.productPriceMin}"></fmt:formatNumber>~
										<fmt:formatNumber pattern="0.0000#" value="${buyOrder.productPriceMax}"></fmt:formatNumber>
									</c:otherwise>
									</c:choose></span></i>
								</td>
								<td class="quantity b1">
								<i class="special-num">${buyOrder.qty }</i>
								</td>
								<td class="amount b1">
								 <i>${buyOrder.moneyUnitSymbols }<fmt:formatNumber pattern="0.0000#" value="${buyOrder.price}"></fmt:formatNumber></i>
								</td>
								<td class="trade-status">
								<!-- <i class="status tm-h" >已合并</i><br> -->
								<i class="status" style="color:#ff9c2b;">
									 <c:if test="${buyOrder.status==1  }">已合单</c:if> 
									 <c:if test="${buyOrder.status==21 }">已分配</c:if> 
									 <c:if test="${buyOrder.status==31 }">已提交采购单</c:if>
									 <c:if test="${buyOrder.status==32 }">经销商已填写合同</c:if> 
									 <c:if test="${buyOrder.status==33 }">供应商已填写合同</c:if> 
									 <c:if test="${buyOrder.status==34 }">经销商已确认合同</c:if>  
									 <c:if test="${buyOrder.status==62 }">供应商已确认合同</c:if> 
									 <c:if test="${buyOrder.status==71 }">供应商已发货</c:if> 
									 <c:if test="${buyOrder.status==81 }">经销商已收货</c:if>
								</i><br>
								
								</td>
								<td class="trade-operate">
								<c:if test="${!empty buttonsMap['已合并订单-期货分单-查询经销商'] }">
									<c:if test="${buyOrder.status==1}">
										<a href="#">
											<button class="jun" onclick="fendan('${buyOrder.pid }','${buyOrder.poId}')" value="">期货分单</button>
										</a>
									</c:if>
								</c:if>
								<c:if test="${!empty buttonsMap['已合并订单-订单详情'] }">
									<a target="_blank" class="" href="<%=path %>/buyOrder/showBuyOrderDetail?poId=${buyOrder.poId}"> 订单详情 </a>
								</c:if>
								
								</td>
						</tr>	
						</c:forEach>
				  </tbody>  
				</table>
				<c:if test="${!empty pageBean.result}">
					<%@include file="/WEB-INF/views/dealerbuy/basePage/basePage.jsp" %>	
				</c:if>