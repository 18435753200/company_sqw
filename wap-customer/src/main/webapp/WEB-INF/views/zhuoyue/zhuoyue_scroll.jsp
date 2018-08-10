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

				<c:if test="${not empty productList.items}">
					<c:forEach items="${productList.items}"  var="product" varStatus="pro" >
						<c:if test="${product.pname!=null&&product.unit_price!=null&&product.imageurl!=null}">
							<dl class="dl_temp">
								<dt>
									<a href="<%=path%>/item/get/${product.pid}">
									<img   class="lazy"  src="" data-original="${imgurl}${product.imageurl }"/></a></dt>
								<dd>
									<h3>
									<a href="<%=path%>/item/get/${product.pid}">
									${product.pname}</a></h3>
									<p><span> <i>${product.unit_price}</i></span>
									</p>
								</dd>
							</dl>
						</c:if>
					</c:forEach>
				</c:if>
