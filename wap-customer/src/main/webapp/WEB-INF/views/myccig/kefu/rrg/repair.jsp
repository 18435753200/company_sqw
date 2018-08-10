<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<title>退换货-众聚猫</title>
<%@include file="/WEB-INF/views/commons/base.jsp"%>
<link href="${staticFile_s}/commons/css/my.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/return.css" rel="stylesheet"
	type="text/css">
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>

<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>	
	<div class="headly">
		<span onclick="javascript:document.location.href='${path}/customer/toMy'"><b></b><i></i></span>
	    <h3>退换货</h3>
    </div>
    <div class="order_top">
          <ul id="J_mycenterTabs">
            <li ><a href="${path }/cusOrder/toMyAllOrder?pageNow=1">全部订单</a></li>
            <li ><a href="${path }/cusOrder/toMyAllOrder?pageNow=1&status=1">待支付</a></li>
            <li ><a href="${path }/cusOrder/toMyAllOrder?pageNow=1&status=81">待收货</a></li>
            <!-- <li ><a href="${path }/cusOrder/toMyAllOrder?pageNow=1&status=91&isEvaluate=0">待评价</a></li> -->
            <li class="at"><a href="${path }/kf/rrg/list/1" class="at">退换货</a></li>
         </ul>
       </div>
	<div class="return">
		<c:if test="${repairType==1 }">
			<c:if test="${!empty pageBean.result}">
				<c:forEach var="orderDto" items="${pageBean.result }"
					varStatus="order_status">
					<div class="return-list">
						<h4 class="ret-title">
							<span>订单编号：${orderDto.id }</span><i>已完成</i>
						</h4>
						<ul class="ret-lit">
							<c:set var="isApply" value="0"/>
						   <c:forEach var="product" items="${orderDto.productList }">
						    <c:if test="${product.productType==0}">
							<li>
								<dl class="ret-cent">
									<dt>
										<a href="<%=path%>/item/get/${product.productId}" title="${product.productName }"><img src="${picUrl2 }${product.imgUrl }"></a>
									</dt>
									<dd>
										<h2>
											<a href="<%=path%>/item/get/${product.productId}" title="${product.productName }">${product.productName }</a>
										</h2>
									</dd>
								</dl>
								
								<c:forEach var="pro" items="${orderDto.productList }">
							     <c:if test="${pro.productType==0}">
							     	<c:if test="${!pro.rrgStatus}">
							     		<c:set var="isApply" value="1"/>
							     	</c:if>
							     </c:if>
						     	</c:forEach>
								
								<c:if test="${!product.rrgStatus}">
									<a  class="apply">已完成</a>
								</c:if>
								<c:if test="${product.rrgStatus}">
								
									<c:set value="<%=new Date() %>" var="nowTime"/>
               						<c:set value="${nowTime.time - orderDto.lastEditTime.time}" var="diffTime"/>
               						
									<c:choose>
										<c:when test="${isApply eq 1 &&  (diffTime/1000/60/60/24) > 7}">
											<a  class="apply">申 请</a>
										</c:when>
										<c:otherwise>
											<a href="<%=path%>/kf/rrg/${orderDto.id}-${product.pid}-1" class="apply2 apply">申 请</a>
										</c:otherwise>
									</c:choose>
								</c:if>
							</li>
							</c:if>
							</c:forEach>
						</ul>
						<div class="ret-pt">
							<p class="price">订单金额： ${orderDto.price }</p>
							<p class="time">下单时间：<fmt:formatDate value="${orderDto.createTime}" type="time" timeStyle="full" pattern="yyyy-MM-dd HH:mm"/></p>
						</div>
					</div>
				</c:forEach>
				<c:if test="${pageBean.pageSize > 1}">
				   <div class="load-more"><a href="javascript:findMoreRRGOrder();">查看更多</a></div>
				</c:if>
			</c:if>
	        
			<c:if test="${empty pageBean.result}">
				<div class="order_no">
		<div class="no"><img src="${path }/commons/img/order_no.png"></div>
	    <div class="text">
		         暂无退换货单
	   </div>
	</div>
			</c:if>
		</c:if>
		<c:if test="${repairType==2 }">
			<div class="return-list">
						<h4 class="ret-title">
							<span>订单编号：${orderDto.id }</span><i>已完成</i>
						</h4>
						<ul class="ret-lit">
						   <c:forEach var="orderItem" items="${orderDto.orderItemDTOs }">
						   <c:if test="${orderItem.productType==0}">
							<li>
								<dl class="ret-cent">
									<dt>
										<a href="<%=path%>/item/get/${orderItem.pid}" title="${orderItem.pName }"><img src="${picUrl2 }${orderItem.imgUrl }"></a>
									</dt>
									<dd>
										<h2>
											<a href="<%=path%>/item/get/${orderItem.pid}" title="${orderItem.pName }">${orderItem.pName }</a>
										</h2>
									</dd>
								</dl> <c:if test="${orderItem.rrgStatus}"><a href="<%=path%>/kf/rrg/${orderDto.id}-${orderItem.skuId}-1" class="apply2 apply">申 请</a></c:if>
									<c:if test="${!orderItem.rrgStatus}"><a  class="apply">已完成</a></c:if>
							</li>
							</c:if>
							</c:forEach>
						</ul>
						<div class="ret-pt">
							<p class="price">订单金额： ${orderDto.price }</p>
							<p class="time">下单时间：<fmt:formatDate value="${orderDto.createTime}" type="time" timeStyle="full" pattern="yyyy-MM-dd HH:mm"/></p>
						</div>
					</div>
		</c:if>
	</div>
	<script type="text/javascript" src="${staticFile_s}/commons/js/jquery.min.js"></script>
	<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
	<script type="text/javascript">
		var no = 1;
		function findMoreRRGOrder(){
			no+=1;
			$.ajax({
				type : "post",
				url :$("#path").val()+"/kf/rrg/list/"+no,
				async : false,
				dataType : "html",
				success : function(msg){
					if($.trim(msg)==""){
						no-=1;
						$.dialog({
							content : "已到最后页！",
							title : '众聚猫提示',
							time : 1000
						});
					}else{
						$(".return-list:last").after(msg);
					}
				}
			});
		}
	</script>
</body>
</html>