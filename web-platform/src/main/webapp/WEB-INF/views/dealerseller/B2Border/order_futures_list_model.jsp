<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>

<table id="J_BoughtTable" class="bought-table" data-spm="9" style="width:100%;">
	<colgroup>
			<col width="198px">
			<col width="90px">
			<col width="80px">
			<col width="80px">
			<col width="80px">
			<col width="70px">
			<col width="80px">
			<col width="80px">
			<col width="80px">
	</colgroup>

	<thead>
		<tr class="col-name">
			<th>商品</th>
			<th>价格(元)</th>
			<th>数量</th>
			<th>应付款(元)</th>
			<th>实付款(元)</th>
		    <th>下单时间</th>
			<th>交易状态</th>
			<th>发票类型</th>
			<th>交易操作</th>
		</tr>
	</thead>
	<c:if test="${empty pb.result}">
		<tr>
			<td colspan="7"><center><img src="${path }/commons/images/no.png" /></center></td>
		</tr>
	</c:if>

	<tbody class="data">
		<c:forEach items="${pb.result}" var="o">
			<tr class="sep-row"></tr>
			<tr class="order-hd">
				<td class="first" colspan="4">
					<div class="summary">
						<span class="number last-item">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;订单号：${fn:escapeXml(o.payId)}</span>
						<span class="number last-item">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商品单号:${fn:escapeXml(o.orderId)}</span>
					</div>
				</td>
				<td class="column" colspan="2">零售商:${fn:escapeXml(o.retailerName)}</td>
				<td class="column" colspan="2">零售商ID::${fn:escapeXml(o.retailerId)}</td>
				<td class="last" >
				<c:if test="${! empty buttonsMap['期货订单-买家留言'] }">	
					<c:if test="${!empty o.message }">
						<button class="lyan" onclick="showMessage('${o.message}')">买家留言</button>
					</c:if>
				</c:if>
				</td>

			</tr>
			<tr class="order-bd last order-last">
				<td class="baobei">
				   <a class="pic J_MakePoint"><img src="${o.imgUrl }"></a>
					<div class="desc">
						<p class="baobei-name"><i title="${fn:escapeXml(o.pName)}"> ${fn:escapeXml(o.pName)}</i></p>
					</div>
				</td>
				<td class="price ">
					<i class="special-num">
						<span>
						<fmt:formatNumber value="${fn:escapeXml(o.productPriceMin)}" type="currency" pattern="#0.00"/>元
						<br>
						至
						<br>
						<fmt:formatNumber value="${fn:escapeXml(o.productPriceMax)}" type="currency" pattern="#0.00"/>元
						</span>
					</i>
				</td>
				<td class="quantity ">
					<i class="special-num">${fn:escapeXml(o.qty)}</i>
				</td>
				
				<td class="amount " >
					<i class="real-price special-num">
						<fmt:formatNumber value="${fn:escapeXml(o.price)}" type="currency" pattern="#0.00"/>元 <br>
					</i>
				</td>
				
				<td class="amount " >
					<i class="real-price special-num">
						<fmt:formatNumber value="${fn:escapeXml(o.paidPrice)}" type="currency" pattern="#0.00"/>元 <br>
					</i>
				</td>
			    <td><i><fmt:formatDate value="${o.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></i></td>
				<td class="trade-status" rowspan="1">
					<c:if test="${o.status==1 }">
						<i class="status"> 待支付 </i>
					</c:if> 
					<c:if test="${o.status==100 }">
						<i class="status">系统自动取消</i>
					</c:if> 
					<c:if test="${o.status==111 }">
						<i class="status">买家取消</i>
					</c:if> 
					<c:if test="${o.status==112 }">
						<i class="status">客服取消</i>
					</c:if> 
					<c:if test="${o.status==21 }">
						<i class="status">已付首款</i>
					</c:if> 
					<c:if test="${o.status==31||o.status==32||o.status==33||o.status==34 }">
						<i class="status">等待UNICORN合单</i>
					</c:if> 
					<c:if test="${o.status==41 }">
						<i class="status">等待供应商发货</i>
					</c:if> 
					<c:if test="${o.status==51 }">
						<i class="status">供应商已发货</i>
					</c:if> 
					<c:if test="${o.status==61 }">
						<i class="status">等待零售商交付余款</i>
					</c:if> 
						<c:if test="${o.status==71 }">
						<i class="status">等待经销商发货</i>
					</c:if> 
					<c:if test="${o.status==81 }">
						<i class="status"> 经销商已发货</i>
					</c:if> 
					<c:if test="${o.status==91 }">
						<i class="status">订单完成 </i>
					</c:if> 
					<c:if test="${o.status==101 }">
						<i class="status"> 系统默认完成</i>
					</c:if> 
					
					<br> 
				</td>
				<td class="trade-status" rowspan="1">
				<c:choose>
					<c:when test="${o.needInvoice eq 0}">无发票</c:when>
					<c:otherwise>
						<c:if test="${o.invoiceType==1 }">
							<i class="status">增值税普通发票</i>
						</c:if> 
						<c:if test="${o.invoiceType==3 }">
							<i class="status">增值税专用发票</i>
						</c:if> 
						<c:if test="${o.invoiceType==0 }">
							<i class="status"> - </i>
						</c:if> 
					</c:otherwise>
				</c:choose>	
				</td>
				<td class="trade-operate">
				<c:if test="${! empty buttonsMap['期货订单-催缴余款'] }">	
					<c:if test="${o.status==51}">
						<a href="javascript:void(0)" onclick="confirmOrder('${o.orderId}')">催缴余款</a>
					</c:if> 
				</c:if>
				
				<c:if test="${! empty buttonsMap['期货订单-订单详情'] }">	
					<a class="tm-h" href="${path}/orderqihuo/getOrderMinute?orderId=${o.orderId}" target="_blank"> 订单详情 </a>
				</c:if>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>


<c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp" %>
</c:if>