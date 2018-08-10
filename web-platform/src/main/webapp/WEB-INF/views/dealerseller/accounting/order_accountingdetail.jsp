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

	<!--现货分单弹框开始-->
	<div class="alert_user">
		<div class="bg"></div>
	</div>
	<!--现货分单弹框结束-->
	<!--logo开始-->
	<div class="wrap">
		<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		<div class="rg">
				<div class="c21">
			<div class="title">
				<p>卖家中心&nbsp;&gt;&nbsp; </p>
				<p>账务管理&nbsp;&gt;&nbsp; </p>
				<p class="c1">订单信息</p>
				<div class="clear"></div>
			</div>
		</div>
		<div class="blink10"></div>
			<!--logo结束-->


			<!--订单信息开始-->
			<div class="xing">
				<div class="xing1">
					<p>订单信息</p>
				</div>
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
							<td></td>
						</tr>
						<tr>
							<td>商家：${fn:escapeXml(retailer.name)}</td>
							<td>联系电话：${fn:escapeXml(retailer.phone)}</td>
						</tr>
						<tr class="t1">
							<td>订单信息</td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td>订单编号：${fn:escapeXml(orderItem.order.orderId)}</td>
							<td>下单时间：
							<fmt:formatDate value="${orderItem.order.createTime }" pattern="yyyy年MM月dd日HH点mm分" />
							</td>
						</tr>
						<tr>
					<td>
					发票类型：
						<c:if test="${orderItem.order.invoiceType==1 }">普通发票</c:if>
						<c:if test="${orderItem.order.invoiceType==2 }">增值税发票</c:if>	
					</td>
				</tr>
				<tr>
					<td><p class="over" title="${fn:escapeXml(orderItem.order.invoiceTitle)}">发票抬头：${fn:escapeXml(orderItem.order.invoiceTitle)}</p></td>
				</tr>
				<tr>
					<td>
						<p class="over" title="
							<c:choose>
								<c:when test="${orderItem.order.invoiceDetails==0 }">
									明细
								</c:when>
								<c:otherwise>
									${fn:escapeXml(orderItem.order.invoiceDetails)}
								</c:otherwise>
							</c:choose>
						">
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
									</tr>
									<c:forEach items="${orderItem.orderItemList }" var="orderSku"
										varStatus="i">

										<tr>
											<td>
											<img src="
													<c:forEach items='${orderItem.skuImgUrlList }' var='orderSkuImg' begin='${i.index }' end='${i.index }'>
														${orderSkuImg }				
													</c:forEach>
													">

											</td>
											<td style="padding:40px 0 0 0;">${fn:escapeXml(orderSku.skuId)}</td>
											<td style="padding:40px 0 0 0;">${fn:escapeXml(orderSku.skuName)}</td>
											<td style="padding:40px 0 0 0;">${fn:escapeXml(orderSku.skuQty)}</td>
											<td style="padding:40px 0 0 0;"><fmt:formatNumber value="${fn:escapeXml(orderSku.price)}" type="currency" pattern="#0.00"/></td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</div>
				</div>

				<div class="bink"></div>
				<div class="xing5">
					<p>
					实付款
						<span>
							<fmt:formatNumber value="${fn:escapeXml(orderItem.order.paidPrice)}" type="currency" pattern="#0.00"/> 
						</span>元
						<%-- <c:if test="${orderItem.order.status!=1 && orderItem.order.status!=100 && orderItem.order.transferPrice>0}">
							(含运费<fmt:formatNumber value="${fn:escapeXml(orderItem.order.transferPrice)}" type="currency" pattern="#0.00"/>元)
						</c:if> --%>
					</p>
					
				</div>
			</div>
		</div>
	</div>
	<!--订单信息结束-->
	<p class="bink10"></p>
	<!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
	<!-- 底部 end -->
	
	<script>
	 	$(document).ready(function(){
			//heb();	
			fdan();
		});
		
		/*var heb=function(){
			$(".two2").hide();
			$(".two").toggle(function(){
                $(this).find(".two2").show();
			    $(this).css("border","2px solid #ff4f00");
            },function(){
                $(this).find(".two2").hide();
				$(this).css("border","none");
              });
		  };*/
    	var fdan=function(){
		 $(".alert_user").hide();
			 $(".jun").click(function(){
				$(".alert_user").show();
			 });
			 
			 $(".bt2").click(function(){
				$(".alert_user").hide();
			  });
			  
			$(".b_colse").click(function(){
				$(".alert_user").hide();
			 });
		 
		 };
		function closeBox(){
			$(".alert_user").hide(300);
		} 
	</script>
</body>
</html>