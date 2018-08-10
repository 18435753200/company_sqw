<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="java.util.Date"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	request.setAttribute("path", path);
	request.setAttribute("basePath", basePath);
%>
<c:forEach var="orderDto" items="${pageBean.result }" varStatus="order_status">
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
				
				<c:set value="<%=new Date() %>" var="nowTime"/>
	         	<c:set value="${nowTime.time - orderDto.lastEditTime.time}" var="diffTime"/>
	         	
	         	<c:if test="${product.rrgStatus}">			
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