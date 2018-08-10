<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%
	String path = request.getContextPath();
	request.setAttribute("path", path);
	request.setAttribute("url", request.getServletPath());
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

				<c:if test="${not empty productList.result}">
					<c:forEach items="${productList.result}"  var="productList" varStatus="pro" >
						<c:if test="${productList.productTitle!=null&&productList.productPrice!=null&&productList.productImageUrl!=null}">
							<dl class="dl_temp">
								<dt><a href="<%=path%>/item/get/${productList.productid}"><img src="${productList.productImageUrl }" /></a></dt>
								<dd>
									<h3><a href="<%=path%>/item/get/${productList.productid}">${productList.productTitle}</a></h3>
									
									<p class="domeprice">
									   <c:if test="${productList.marketPrice != null}">
									   <span >市场价： <i><fmt:formatNumber value="${fn:escapeXml(productList.marketPrice)}" pattern="#0.00" /></i></span>
								      </c:if>
								    </p>
									
									<p><span> <i>${productList.productPrice}</i></span>
										<%-- <c:if test="${scene_flag=='falshsale'}"><a href="#">加入购物车</a> </c:if>	 --%>
									</p>
								</dd>
							</dl>
						</c:if>
					</c:forEach>
				</c:if>
