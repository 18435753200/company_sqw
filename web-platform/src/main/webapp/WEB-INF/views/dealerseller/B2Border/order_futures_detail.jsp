<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
	<title>UNICORN-订单详情</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/dingdan1.css"/>
</head>
<body>
<!-- 导航 start -->
<%@include file="/WEB-INF/views/include/header.jsp"%>
<div class="blank10"></div>
			
		
	<!-- 导航 end -->
	<!--logo开始-->
	<div class="wrap">
	<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp" %>
	<div class="rg">
	<div class="c21">
		<div class="title">
			<p>卖家中心&nbsp;&gt;&nbsp; </p>
			<p>订单管理&nbsp;&gt;&nbsp; </p>
			<p>期货订单&nbsp;&gt;&nbsp; </p>
			<p class="c1">订单信息</p>
		</div>
	</div>
	<div class="blank10"></div>
	<!--logo结束-->
	

	<!--订单信息开始-->
	<div class="xing">
		<div class="xing1"><p>订单信息</p></div>
		<div class="xing2">
			<dl>
				<dt>收货人信息</dt>
				<dd>
					<ul>
						<li>收&nbsp;货&nbsp;人：${fn:escapeXml(orderItem.order.receiveName)}</li>
						<li>地&nbsp;&nbsp;&nbsp;&nbsp;址：${fn:escapeXml(orderItem.order.receiveAddress)}</li>
						<li>手机号码：${fn:escapeXml(orderItem.order.receiveMobilePhone)}</li>
					</ul>
				</dd>
			</dl>
		</div>
		<div class="xing3">
			<table>
				<tr class="t1">
					<td>买家信息</td>
					<td></td>
				</tr>
				<tr>
					<td><p class="over" title="${fn:escapeXml(retailer.name)}">商家：${fn:escapeXml(retailer.name)}</p></td>
					<td>联系电话：${fn:escapeXml(retailer.phone)}</td>
				</tr>
				<tr>
					<td>管理员名称：${fn:escapeXml(retailerUser.name)}</td>
				</tr>
			</table>
		</div>
		<div class="xing3">
			<table>
				<tr class="t1">
					<td>订单信息</td>
					<td></td>
					<!-- <td></td> -->
				</tr>
				<tr>
					<td>订单编号：${fn:escapeXml(orderItem.order.payId)}<input type="hidden" id="resetOrderId" value="${fn:escapeXml(orderItem.order.orderId)}"/></td>
					<td>下单时间：<fmt:formatDate value="${orderItem.order.createTime }" pattern="yyyy-MM-dd HH:mm"/></td>
				</tr>
				<tr><td>订单金额：<i>￥<fmt:formatNumber value="${fn:escapeXml(orderItem.order.orderPrice)}" type="currency" pattern="#0.00"/></i> </td></tr>
				<tr><td>折扣金额：<i>￥-<fmt:formatNumber value="${fn:escapeXml(orderItem.order.discountPrice)}" type="currency" pattern="#0.00"/> </i></td></tr>
				<tr><td>实际运费：<i>￥<fmt:formatNumber value="${fn:escapeXml(orderItem.order.realTransferprice)}" type="currency" pattern="#0.00"/></i> </td></tr> 
				<tr><td>应付金额：<i>￥<fmt:formatNumber value="${fn:escapeXml(orderItem.order.price)}" type="currency" pattern="#0.00"/></i> </td></tr>
				<c:if test="${orderItem.order.needInvoice eq 1}">
				<tr>
					<td>
					发票类型：
						<c:if test="${orderItem.order.invoiceType==1 }">增值税普通发票</c:if>
						<c:if test="${orderItem.order.invoiceType==3 }">增值税专用发票</c:if>	
					</td>
				</tr>
				
				<c:if test="${orderItem.order.invoiceType==1 || orderItem.order.invoiceType==3 }">
					<tr><td>单位名称：${fn:escapeXml(orderItem.order.invoiceTitle)}</td></tr>
					<tr><td>纳税人识别号：${fn:escapeXml(orderItem.order. invoiceTaxpayerId)}</td></tr>
					<tr><td>注册地址：${fn:escapeXml(orderItem.order.invoiceRegisteredAddr)}</td></tr>
					<tr><td>注册电话：${fn:escapeXml(orderItem.order.invoiceRegisteredPhone)}</td></tr>
					<tr><td>开户银行：${fn:escapeXml(orderItem.order.invoiceBankName)}</td></tr>
					<tr><td>银行帐户：${fn:escapeXml(orderItem.order.invoiceBankNo)}</td></tr>
				</c:if>	
				
				<tr>
					<td><p class="over" title="${fn:escapeXml(orderItem.order.invoiceTitle)}">发票抬头：${fn:escapeXml(orderItem.order.invoiceTitle)}</p></td>
				</tr>
				<tr>
					<td>
						<p class="over" title="<c:choose><c:when test="${orderItem.order.invoiceDetails==0 }">明细</c:when><c:otherwise>${fn:escapeXml(orderItem.order.invoiceDetails)}</c:otherwise></c:choose>">
							发票明细：
							<c:choose>
								<c:when test="${orderItem.order.invoiceDetails==0 }">
									明细
								</c:when>
								<c:otherwise>
									${fn:escapeXml(orderItem.order.invoiceDetails)}
								</c:otherwise>
							</c:choose>
						</p>
					</td>
				</tr>
				</c:if>
			</table>
		</div>
	
		<!--商品清单开始-->
		<div class="xing4">
		
			<div class="c3">
				
				<div class="two">

					<div class="blink"></div>
					<div class="two21">
					   <table>
					   <tr class="t1">
					   		<th>商品</th>
						    <th>SKU号</th>
							<th>规格</th>
							<th>数量</th>
							<th>价格</th>
							<th>状态</th>
						</tr>
					   <c:forEach items="${orderItem.orderItemList }" var="orderSku" varStatus="i">
					   
						<tr>
							<td>
								<img src="
								<c:forEach items='${orderItem.skuImgUrlList }' var='orderSkuImg' begin='${i.index }' end='${i.index }'>
									${orderSkuImg }				
								</c:forEach>
								" >
								
							</td>
						    <td style="padding:40px 0 0 0;">${fn:escapeXml(orderSku.skuId)}</td>
							<td style="padding:40px 0 0 0;">${fn:escapeXml(orderSku.skuName)}</td>
							<td style="padding:40px 0 0 0;">${fn:escapeXml(orderSku.skuQty)}</td>
							<td style="padding:40px 0 0 0;"><fmt:formatNumber value="${fn:escapeXml(orderSku.price)}" type="currency" pattern="#0.00"/>元</td>
							
							<td style="padding:40px 0 0 0;">
									<c:if test="${orderSku.status==1 }">
										<i class="status" > 待支付 </i>
									</c:if>
									<c:if test="${orderSku.status==100 }">
										<i class="status">系统自动取消</i>
									</c:if> 
									<c:if test="${orderSku.status==111 }">
										<i class="status">买家取消</i>
									</c:if> 
									<c:if test="${orderSku.status==112 }">
										<i class="status">客服取消</i>
									</c:if>
									<c:if test="${orderSku.status==21 }">
										<i class="status" >已付首款</i>
									</c:if>
									<c:if test="${orderSku.status==31||orderSku.status==32||orderSku.status==33||orderSku.status==34 }">
										<i class="status" >等待UNICORN合单</i>
									</c:if>
									<c:if test="${orderSku.status==41 }">
										<i class="status" >等待供应商发货</i>
									</c:if>
									<c:if test="${orderSku.status==51 }">
										<i class="status" > 供应商已发货</i>
									</c:if>
									<c:if test="${orderSku.status==61 }">
										<i class="status" >等待零售商交付余款</i>
									</c:if>
									<c:if test="${orderSku.status==71 }">
										<i class="status" >等待经销商发货 </i>
									</c:if>
									<c:if test="${orderSku.status==81 }">
										<i class="status" > 经销商已发货</i>
									</c:if>
									<c:if test="${orderSku.status==91 }">
										<i class="status" >订单完成 </i>
									</c:if>
									<c:if test="${orderSku.status==101 }">
										<i class="status" > 系统默认完成</i>
									</c:if>
							</td>
						</tr>
						</c:forEach>
						</table>
					</div>
				</div>

			
			</div>
          
		
		</div>
	
		<div class="bink"></div>
		
		<div class="xing5">
			<p>实付款<span>
			<fmt:formatNumber value="${fn:escapeXml(orderItem.order.paidPrice)}" type="currency" pattern="#0.00"/> </span>元
			</p>
		</div>
	</div>
	</div>
	</div>
	<!--订单信息结束-->
<p class="bink10"></p>
   <!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
</body>
</html>