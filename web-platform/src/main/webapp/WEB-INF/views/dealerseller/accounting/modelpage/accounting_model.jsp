<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
		<div class="c23">
			<c:if test="${! empty buttonsMap['交易账务-下载账务'] }">	
				<div class="r1">
				   <h2>收入明细</h2>
				   <button type="button" onclick="downAccountByPage(${pb.page})">下载对账单</button>
				   <input type="hidden" id="nowpage" value="${pb.page }"/>
				</div>
			</c:if>	
			<div class="r3">
			   	 <span class="ddan">支付号</span>
				 <span class="s3">首付流水号</span>
				 <span class="s3">尾款流水号</span>
				 <span class="ddan">订单号</span>
				 <span class="s4">日期</span>
				 <span class="s5">零售商</span>
				 <span class="s6">收入</span>
				 <span class="s9">详情</span>
			</div>
			<div class="blink"></div>
				<c:if test="${empty pb.result}">
						<tr>
							<td colspan="6">
								<center><img src="${path }/commons/images/no.png" /></center>
							</td>
						</tr>
				</c:if>
			<c:forEach items="${pb.result}" var="account">
				<div class="r4">
					<p class="ddan1" title="${fn:escapeXml(account.payId)}">
						${fn:escapeXml(account.payId)}
					</p>
					<p class="p3" title="${fn:escapeXml(account.firstBankNo)}">
						<c:if test="${empty account.firstBankNo }">
							-
						</c:if>
						${fn:escapeXml(account.firstBankNo)}
					</p>
					<p class="p3" title="${fn:escapeXml(account.lastBankNo)}">	
						<c:if test="${empty account.lastBankNo }">
							-
						</c:if>
						${fn:escapeXml(account.lastBankNo)}
					</p>
					<p class="ddan1" title="${fn:escapeXml(account.orderId)}">
						<c:if test="${empty account.orderId }">
							<c:out value="_"></c:out>
						</c:if>
					${fn:escapeXml(account.orderId)}
					
					</p>
					<p class="p4">
						<c:if test="${empty account.createTime }">
							<c:out value="_"></c:out>
						</c:if>
					<fmt:formatDate value='${account.createTime}' pattern="yyyy-MM-dd HH:mm"/>
					
					</p>
					<p class="p5" title="${fn:escapeXml(account.retailerName)}">
						<c:if test="${empty account.retailerName }">
							<c:out value="_"></c:out>
						</c:if>
						${fn:escapeXml(account.retailerName)}
					</p>
					<p class="p6" title="${fn:escapeXml(account.paidPrice)}">
						<fmt:formatNumber value="${fn:escapeXml(account.paidPrice)}" type="currency" pattern="#0.00"/>元
					</p>
		            <p class="p9">
		            	<a href="../accounting/getOrderInfo?orderId=${account.orderId}" target="_blank">查看</a>
		            </p>
				</div>
			
			</c:forEach>
	</div>
	<c:if test="${!empty pb.result}">
		<%@include file="/WEB-INF/views/dealerseller/paging.jsp" %>
	</c:if>