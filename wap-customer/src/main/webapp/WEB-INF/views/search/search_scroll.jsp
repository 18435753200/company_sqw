<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%-- <c:if test="${not empty searchResponse.items}">
  	<c:forEach items="${searchResponse.items}" var="item" varStatus="statu">
  		<div class="p-item">
   		<a href="<%=path%>/item/get/${item.pid}">
         <div class="p-item-pic"><img src="${picUrl1}${item.imageurl}" /></div>
         <div class="p-item-content">
             <div class="item-content-from"> <em><img src="${picUrl1}${item.cyImageurl}" /></em><span>${item.cyName}</span> </div>
             <div class="item-content-title">${item.highlightedPname}</div>
             <div class="item-content-price">
                 <div class="main-price"><b> </b><span>${item.realPrice}</span></div>
                 <div class="main-subPirce"><span class="price-gray">国内价：<i>${item.referencePrice}</i></span></div>
       		 </div>
     		   </div>
       	</a>
  		</div>
  	
  	</c:forEach>
</c:if> --%>

<c:choose>
<c:when test="${not empty searchResponse.items}">
<input id="totalPages" type="hidden" value="${totalPage}">
			<ul class="scr-list1">
        	<c:forEach items="${searchResponse.items}" var="item" varStatus="statu">
				<li>
				<a href="<%=path%>/item/get/${item.pid}">
				<c:if test="${item.promotion == 10}">
	        				<span class="act-icon act-xs"></span>
	        			</c:if>
					<p class="picl"><img src="${picUrl1}${item.imageurl}" /></p>
					<p class="txtr">
						
							    	<c:if test="${not empty item.product_iconsTxT }">
									<span class="sbtom"><b> </b>${item.unit_price}</span>
									<c:forEach items="${item.product_iconsTxT}" var="itemii">
										<span class="yiTuBiao" style="width:40px">${itemii}</span>
									</c:forEach>

								</c:if>
								<c:if test="${empty item.product_iconsTxT  }">
									<c:if test="${item.prod_type==6 }">
										<span class="sbtom"><b> </b> ${item.hqj}红旗券+${item.cash_Hqj}元</span>
										<span class="yiTuBiao">福</span><br>
									</c:if>
									<c:if test="${item.prod_type==5 }">
										<span class="sbtom"><b> </b>${item.unit_price}</span>
										<span class="yiTuBiao">易</span><br>
									</c:if>
									<c:if test="${item.prod_type==0 }">
										<span class="sbtom"><b> </b>${item.unit_price}</span>
									</c:if>

								</c:if>
								<span class="smidd" style="display:block;"><font size=3>${item.b2cPname}</font></span>
								<span class="sptop" style="display:block;"><b><%-- <img
										src="${picUrl1}${item.supplierLogoImgurl}"> --%></b><em>${item.supplierName}</em><i>${item.supplierCode}</i></span>
								
								
								<fmt:parseNumber type="NUMBER" var="price"
									value="${item.unit_price }" pattern=".00" />
								<c:if test="${item.domestic_price>price}">
									<%-- <span class="sdome">市场价：<b> </b><i>${fn:escapeXml(item.domestic_price)}</i></span> --%>
									<%-- <span class="sdome">市场价：<b> </b><i>${item.domestic_price}</i>${item.domestic_price}</span> --%>
								</c:if>
								<%-- <c:if test="${fn:escapeXml(item.domestic_price)>fn:escapeXml(item.unit_price)}">
						   <span class="sdome">市场价：<b> </b><i>${fn:escapeXml(item.domestic_price)}</i></span>
					    </c:if> --%>
								<%-- <span class="sbtom"><b> </b><i>${fn:escapeXml(item.unit_price)}</i></span> --%>


							

					</p>
	            	</a>
				</li>
        	</c:forEach>
			</ul>
	<script>
	
	$("#totalPage").val($("#totalPages").val());
	
	</script>
        </c:when>		
	<c:otherwise>
		
	</c:otherwise>
</c:choose>

