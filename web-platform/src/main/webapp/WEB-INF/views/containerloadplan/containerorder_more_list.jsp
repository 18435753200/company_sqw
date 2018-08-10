<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%  
    String path = request.getContextPath();
    String url = request.getServletPath();
	request.setAttribute("url",url);
	request.setAttribute("path",path);
%>
<c:if test="${pageBean.totalCount>1 }">
	<c:forEach items="${pageBean.result }" var="orders" begin="1">
	   <tr class="orderTr">
	   	<td colspan="1" id="bo">
	   	<input type="checkbox" name="ck" value="${orders.id}" class="check">
	   	<c:forEach items="${orders.orderItemDTOs }" var="orderItemDTO">
	   			<input type="hidden" name="" value="${orderItemDTO.skuId }" class="skuId">
            	<input type="hidden" name="" value="${orderItemDTO.skuNameCn }" class="skuNameCn">
            	<input type="hidden" name="" value="${orderItemDTO.skuQty }" class="skuQty">
            	<input type="hidden" name="" value="${orderItemDTO.imgUrl }" class="imgUrl">
            	<input type="hidden" name="" value="${orderItemDTO.weight }" class="weight">
            	<input type="hidden" name="" value="${orderItemDTO.volume }" class="volume">
            	<input type="hidden" name="" value="${orderItemDTO.pName }" class="pName">
            	<input type="hidden" name="" value="${orderItemDTO.pid }" class="pid">	
            	<input type="hidden" name="" value='${orders.checkTime }' class="createTime">
	   	</c:forEach>
	   	</td>
	   	<td colspan="1" id="bor">
	   		${orders.id}
	   		<input type="hidden" name="" value="${pageBean.page }" class="page">
	   	</td>
	   </tr>
	  </c:forEach>
</c:if>