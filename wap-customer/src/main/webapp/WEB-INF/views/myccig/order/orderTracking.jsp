<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<%@include file="/WEB-INF/views/commons/base.jsp" %>
<link href="${staticFile_s}/commons/css/base.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/user.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/my.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<script>
$(function(){
	//查看物流
	$(".view-box").each(function(){
	   var len=$(".view-box").length;
	   if(len==1){
		 	$('.mynews-m').show(); 
			$('.mess-up').addClass("xkwl-down"); 
	    }
	});
		$(".mess-up").on("click", function(){
			$(this).parent().parent().find('.mynews-m').toggle();
			$(this).toggleClass("xkwl-down");
			$(this).parent().parent().siblings().find('.mynews-m').hide();
			$(this).parent().parent().siblings().find('.xkwl-down').removeClass("xkwl-down");
	});
});
</script>
<title><spring:message code="title_logistics" /></title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>

<style type="text/css">
.h-title{
	background-color: #e63232;
	color: #ffffff;
}
.headly {
    width: 100%;
    height: 4.4rem;
    line-height: 4.4rem;
    font-size: 1.9rem;
    text-align: center;
    color: #505050;
    position: relative;
    background: #e60012;
}
.headly h3{color:#fff}

.headly span b {
    position: absolute;
    width: 1.1rem;
    height: 0;
    border-top: 0.1rem solid #ffffff;
    transform: rotate(-45deg);
    -webkit-transform: rotate(-45deg);
    top: 35%;
    left: 0.05rem;
}

.headly span i {
    position: absolute;
    width: 0;
    height: 1rem;
    border-left: 0.1rem solid #ffffff;
    transform: rotate(-45deg);
    -webkit-transform: rotate(-44deg);
    top: 50%;
    left: 0.6rem;
}
</style>
</head>

<body class="view-bg">
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>
<!-- <header id="header" class="head"><a href="javascript:goBack();" class="goback"><span class="icon i-goback"></span></a>
    <h2 class="h-title">查看物流</h2>
</header> -->
<div class="header headly">
		<span onclick="goBack();"><b></b><i></i></span>
	    <h3>查看物流</h3>
    </div>
<c:if test="${listOrd !=null }">
<div class="view_logistics">
<h3>订单号：${orderId}</h3>
<c:forEach items="${listOrd}" var="orderDto">
<c:if test="${orderDto.supplyType == '11' or orderDto.supplyType == '12' or orderDto.supplyType == '31'}">
<!-- 国外 保税区 -->
	<!-- <div class="wrap" id="orderDetail"> -->
	    <%-- <div class="order-item">
	        <div class="order-bd">
	        	<p>包裹编号：${orderDto.id}<p>
	        </div>
	    </div> --%>
	    <%-- <div class="order-item">
	        <div class="order-hd">
	            <h2>宝贝信息</h2>
	        </div>
	        <div class="order-bd">
	             <c:if test="${orderDto.orderItemDTOs !=null}">
	             
	                <div class="p-goods-scroll">
	                        <ul class="p-goods-list">
	                        <c:forEach items="${orderDto.orderItemDTOs }" var="item">
	                            <li>
	                             <a href="${path }/item/get/${item.pid}" class="link">
	                            <img src="${picUrl2 }${item.imgUrl}" />
	                           </a>
	                         		    <fmt:formatNumber value="${item.price }" pattern="#0.00" /> × ${item.skuQty}
	                            </li> 
	                         </c:forEach>
	                        </ul>
	                    </div>
	                    
	       	 	<c:forEach items="${orderDto.orderItemDTOs  }" var="item">
	            <div class="goods-item">
	             <a href="${path }/item/get/${item.pid}" class="link">
	                <div class="goods-pic"><img src="${picUrl2 }${item.imgUrl}" /></div>
	                <div class="goods-info">
	                    <p class="name double-row">${ item.skuNameCn }</p>
	                    <p class="price"><span> <fmt:formatNumber value="${fn:escapeXml(item.price)}" pattern="#0.00" /></span><!-- （约$40） --></p>
	                    <p class="sku"><span>尺码：S</span><span>数量：${item.skuQty}(${item.unit })</span></p>
	                </div>
	                </a>
	                 </div>
	           </c:forEach>
	           
	            
	         </c:if>
	                
	        </div>
	    </div> --%>
	    
	             <div class="view-box">
	             <c:if test="${ orderDto != null }">
	                <div class="view-nub">
			         <p>快递公司：${orderDto.logisticsCarriersName }</p>
			         <p>快递单号：${orderDto.logisticsNumber }</p>
			         <a href="javascript:void(0)" class="mess-up"></a>
	<%-- 	         <p>快递电话：${orderDto.logisticsNumber }</p> --%>
					</div>
	            </c:if>
	        <c:choose>
		        <c:when test="${ orderDto.orderLogisticsMsgs !=null && fn:length(orderDto.orderLogisticsMsgs) > 0 }">
		        <ul class="mynews-m">
				<c:forEach items="${ orderDto.orderLogisticsMsgs}" var="logist"> 
					<li>
						<div class="mynews-box">
						<fmt:formatDate value="${logist.createTime}" type="time" timeStyle="full" pattern="yyyy-MM-dd HH:mm"/>
						<br />
						${logist.msg }
						</div>
					</li>
				</c:forEach>
				</ul>
	
		        </c:when>
		        <c:otherwise>
		        <div class="view-nub mynews-m">
		        	<p>暂时无物流信息</p>
		       	</div>
		        </c:otherwise>
	        </c:choose>
	    </div>
</c:if>





<c:if test="${orderDto.supplyType  eq '1' or orderDto.supplyType  eq '21' or orderDto.supplyType eq '51'}">
<!-- 国内 -->
	    <%--<div class="order-item">--%>
	        <%--<div class="order-bd">--%>
	            <%--<p>包裹编号：${shipDto.packNo }</p>--%>
	        <%--</div>--%>
	    <%--</div>--%>
	    
	    <%--<div class="order-item">--%>
	        <%--<div class="order-hd">--%>
	            <%--<h2>宝贝信息</h2>--%>
	        <%--</div>--%>
	        <%----%>
	        <%--<div class="order-bd">--%>
	        <%----%>
	        <%--<c:if test="${shipDto.shipItemDtoList !=null}">--%>
	         			<%--<div class="p-goods-scroll">--%>
	                        <%--<ul class="p-goods-list">--%>
	                        <%--<c:forEach items="${shipDto.shipItemDtoList }" var="shipItem">--%>
	                            <%--<li>--%>
	                             <%--<a href="${path }/item/get/${shipItem.pid}" class="link">--%>
	                            <%--<img src="${picUrl2 }${shipItem.productUrl }" />--%>
	                            <%--</a>--%>
	                            <%-- <fmt:formatNumber value="${shipItem.pirce }" pattern="#0.00" /> × ${shipItem.qty}</li> --%>
	                         <%--</c:forEach>--%>
	                        <%--</ul>--%>
	                    <%--</div>--%>
	        <%----%>
	       	<%--&lt;%&ndash;  <c:forEach items="${shipDto.shipItemDtoList  }" var="item">--%>
	            <%--<div class="goods-item">--%>
	             <%--<a href="${path }/item/get/${item.pid}" class="link">--%>
	                <%--<div class="goods-pic"><img src="${picUrl2 }${item.productUrl}" /></div>--%>
	                <%--<div class="goods-info">--%>
	                    <%--<p class="name double-row">${ item.skuName }</p>--%>
	                    <%--<p class="price"><span> <fmt:formatNumber value="${item.pirce }" pattern="#0.00" /></span><!-- （约$40） --></p>--%>
	                    <%--<p class="sku"><!-- <span>尺码：S</span> --><span>数量：${item.qty} </span></p>--%>
	                <%--</div>--%>
	                <%--</a>--%>
	                 <%--</div>--%>
	           <%--</c:forEach>--%>
	            <%--&ndash;%&gt;--%>
	           <%----%>
	         <%--</c:if>--%>
	                <%----%>
	        <%--</div>--%>
	    <%--</div>--%>
	     
	    <div class="view-box">
	            <c:if test="${ orderDto != null }">
	                <div class="view-nub">
			         <p>快递公司：${orderDto.logisticsCarriersName }</p>
			         <p>快递单号：${orderDto.logisticsNumber }</p>
			         <a href="javascript:void(0)" class="mess-up"></a>
	<%-- 	         <p>快递电话：${orderDto.logisticsNumber }</p> --%>
					</div>
	            </c:if>
	        <c:choose>
		        <c:when test="${ orderDto.orderLogisticsMsgs !=null && fn:length(orderDto.orderLogisticsMsgs) > 0 }">
				<ul class="mynews-m">
				<c:forEach items="${ orderDto.orderLogisticsMsgs}" var="logist"> 
					<li>
						<div class="mynews-box">
						<fmt:formatDate value="${logist.createTime}" type="time" timeStyle="full" pattern="yyyy-MM-dd HH:mm"/>
						<br />
						${logist.msg }
						</div>
					</li>
				</c:forEach>
				</ul>
	
	<!-- 	          <div class="order-track"> -->
	<%-- 					<c:forEach items="${ orderDto.orderLogisticsMsgs}" var="logist"> --%>
	<!-- 	                <dl class="purple" style="margin-top:5px; border-bottom:1px dotted #ccc; "> -->
	<%-- 	                    <dd><fmt:formatDate value="${logist.createTime}" type="time" timeStyle="full" pattern="yyyy-MM-dd HH:mm"/></dd> --%>
	<%-- 	                    <dt>${logist.msg }</dt> --%>
	<!-- 	                </dl> -->
	<%-- 	                </c:forEach>    --%>
	<!-- 	            </div> -->
		        </c:when>
		        <c:otherwise>
		        <div class="view-nub mynews-m">
		        	<p>暂时无物流信息</p>
		       	</div>
		        </c:otherwise>
	        </c:choose>
<%-- 				<c:if test="${ shipDto.logisticsNumber != null }"> --%>
<%-- 				<p>快递公司：${shipDto.logisticsCarriersName }</p> --%>
<%-- 	            <p>快递单号：${shipDto.logisticsNumber }</p> --%>
<%-- 				<p>请到 ${shipDto.logisticsCarriersName } 官网查询</p>	 --%>
<%-- 				</c:if> --%>
				
<!-- 				<ul class="mynews-m"> -->
<%-- 					<c:forEach items="${ orderDto.orderLogisticsMsgs}" var="logist">  --%>
<!-- 						<li> -->
<!-- 							<div class="mynews-box"> -->
<%-- 							<fmt:formatDate value="${logist.createTime}" type="time" timeStyle="full" pattern="yyyy-MM-dd HH:mm"/> --%>
<!-- 							<br /> -->
<%-- 							${logist.msg } --%>
<!-- 							</div> -->
<!-- 						</li> -->
<%-- 					</c:forEach> --%>
<!-- 				</ul> -->
			
<%-- 				<c:if test="${ shipDto.logisticsNumber == null }"> --%>
<!-- 					暂时无物流信息 -->
<%-- 				</c:if> --%>
			</div>
	
	<%-- <c:if test="${shipDto.status == 2 }">
	<div class="go-pay"> <a href="#" onclick="confirmOrder(this,'${orderDto.id }','${shipDto.packNo}')"  class="pay-btn">确认收货</a> </div>
	</c:if> --%>
	
</c:if>
</c:forEach>
</div>
</c:if>
<%@include file="/WEB-INF/views/commons/footer.jsp" %>
<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
<script src="${staticFile_s}/commons/js/ccigorder/myOrder.js" type="text/javascript"></script>

</body>
</html>