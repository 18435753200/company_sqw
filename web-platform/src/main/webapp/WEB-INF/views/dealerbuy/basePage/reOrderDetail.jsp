<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="alert_xqing" id="orderXq" style="display: none;">
		<div class="bg"></div>
		<div class="wrap">
			<div class="hdui1">
				<p class="pic" onclick="closexq()"><img src="../commons/images/close.jpg" class="b_colse"></p>
				<div class="hao">
					<table class="hao1">
						<tr class="t2">
							<th>订单信息</th>
						</tr>
						<tr>
							<td>收货人地址 :${fn:escapeXml(retailerOrder.receiveAddress)}&nbsp;&nbsp;
							${fn:escapeXml(retailerOrder.receiveName)}(收)&nbsp;&nbsp;
							
							联系方式：<c:choose>
									<c:when test="${retailerOrder.receivePhone==null||retailerOrder.receivePhone==''}">
										${fn:escapeXml(retailerOrder.receiveMobilePhone)}
									</c:when>
									
									<c:when test="${retailerOrder.receiveMobilePhone==null||retailerOrder.receiveMobilePhone=='' }">
										${fn:escapeXml(retailerOrder.receivePhone)}
									</c:when>
									
									<c:otherwise>
										${fn:escapeXml(retailerOrder.receivePhone)}或${fn:escapeXml(retailerOrder.receiveMobilePhone)}
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							 <td>买家留言 :${fn:escapeXml(retailerOrder.message)}</td> 
						</tr>
						<tr>
							<td>订单编号 :${retailerOrder.orderId}</td>
						</tr>
						<tr>
							<td>成交时间 :<fmt:formatDate value="${retailerOrder.createTime }"  type="both"/></td>
						</tr>
						<tr>
							<td>状态 :
							<c:if test="${retailerOrder.status==1 }">未支付</c:if>	
								<c:if test="${retailerOrder.status==21 }">已付首款</c:if>
								<c:if test="${retailerOrder.status==31 }">订单已经确认</c:if>
								<c:if test="${retailerOrder.status==32 }">订单已经确认</c:if>
								<c:if test="${retailerOrder.status==33 }">订单已经确认</c:if>
								<c:if test="${retailerOrder.status==34 }">订单已经确认</c:if>
								<c:if test="${retailerOrder.status==41 }">等待供应商发货</c:if>
								<c:if test="${retailerOrder.status==51 }">供应商已发货</c:if>
								<c:if test="${retailerOrder.status==61 }">等待交付余款</c:if>
								<c:if test="${retailerOrder.status==71 }">等待经销商发货</c:if>
								<c:if test="${retailerOrder.status==81 }">经销商已发货</c:if>
								<c:if test="${retailerOrder.status==91 }">订单完成</c:if>
								<c:if test="${retailerOrder.status==100 }">系统取消</c:if>
								<c:if test="${retailerOrder.status==101 }">系统默认完成</c:if>
							</td>
						</tr>
					</table>
				</div>
				 <div class="zg">
					<div class="z3">
						<p>
						  <span class="sp">商品</span>
						  <span class="jg">零售价格(元)</span>
						  <span class="sl">数量</span>
						  <span class="sfk">实付款(元)</span>
						  <span class="ztai">零售商</span>
						</p>
					</div>
				<div class="z4">
						<p>
							<span class="s5">${fn:escapeXml(retailerOrder.pName)}</span>
							<!-- <span class="s1"> -->
							<span class="s1">￥
								<c:choose>
									<c:when test="${findRetailerOrderItemByOrderId.productPriceMin==findRetailerOrderItemByOrderId.productPriceMax }">
										<fmt:formatNumber value="${findRetailerOrderItemByOrderId.productPriceMin }" pattern="0.00#"/>
									</c:when>
									<c:otherwise>
										<fmt:formatNumber value="${findRetailerOrderItemByOrderId.productPriceMin }" pattern="0.00#"/>~
										<fmt:formatNumber value="${findRetailerOrderItemByOrderId.productPriceMax }" pattern="0.00#"/>
									</c:otherwise>
								</c:choose>
							</span>
							<span class="s2">${retailerOrder.qty }</span>
							<span class="s3"><fmt:formatNumber value="${retailerOrder.paidPrice-retailerOrder.realTransferprice}" pattern="0.00#"/>
							<%-- <span class="s3"><fmt:formatNumber value="${retailerOrder.paidPrice}" pattern="0.00#"/> --%>
							</span>
							<span class="s4">${retailer.name }</span>
						</p>
				</div>
			</div>
				<div class="x3">
					<table>
						<tr class="t1">
						    <th>SKU号</th>
							<th>规格</th>
							<th>数量</th>
							<th>零售价格(元)</th>
						</tr>
						<c:forEach items="${skulist }" var="skulist">
						<tr>
							<td>${skulist.skuId }</td>
							<td>${skulist.skuName}</td>
							<td>${skulist.skuQty}</td>
							<td>￥<fmt:formatNumber value="${skulist.price}" pattern="0.00#"/></td>
						</tr>
						</c:forEach>
					</table>
				</div>
			</div>
			
			<div class="x1">
				<%-- <button class="bt1" onclick="fendan1(${orderId})">确定</button> --%>
			</div>
		</div>
	</div>  