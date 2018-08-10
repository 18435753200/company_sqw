<%@page import="java.util.Date"%>
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
<link href="${staticFile_s}/commons/css/w_base.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/my.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<title><spring:message code="title_my_order" /></title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
<style type="text/css">
	.ddzx{
		background-color: #e60012;
		color: #ffffff; 
	}
	.rDialog-wrap {
    position: relative;
    background: #404040;
    opacity: .9;
    background-clip: padding-box;
    border-radius: 10px;
    -moz-border-radius: 10px;
    -o-border-radius: 10px;
    -webkit-border-radius: 10px;
    box-shadow: 1px 1px 1px #000;
    padding: 1em 1em;
}
.rDialog-ok, .rDialog-ok:hover {
    background: #e60012;
    color: #fff;
}
	.headly {
	    width: 100%;
	    height: 4.2rem;
	    line-height: 4.4rem;
	    font-size: 1.9rem;
	    text-align: center;
	    color: #505050;
	    position: relative;
	    background: #fff;
	}
	.order_top ul li a {
	    display: block;
	    border-bottom: 0.1rem solid #999;
	    font-size: 1.4rem;
	    color: #000;
    }
    .order_t .redd {
    font-size: 1.2rem;
    color: #e63232;
    padding-left: 2.2rem;
    float: right;
}
.order_bb .lbb {
    font-size: 1.2rem;
    color: #e60012;
    float: right;
     margin-left: 0.7rem;
}

.order_bb {
    line-height: 3.5rem;
    padding: 0 1.5rem;
    text-align: right;
}
.order_divv {
    background: #ffffff;
    margin-bottom: 1rem;
    color: #505050;
}
.order_top ul li {
    float: left;
    height: 4rem;
    line-height: 4rem;
    width: 33.3%;
    text-align: center;
    background: #fff;
}
.order_top ul li a.at {
    border-bottom: 2px solid #e63232;
    color: #e60012;
}
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
<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>	
	<div class="header headly">
		<a href="${path}/wealth/toweaithCenter">
		<span><b></b><i></i></span></a>
	    <h3 class="ddzx">
	    <c:choose>
		    <c:when test="${status ==null ||status =='' }">全部订单</c:when>
		    <c:when test="${status == '1' }">待付款</c:when>
		    <c:when test="${status == '21' }">已付款</c:when>
	    </c:choose>
	    </h3>
    </div>		
		<div class="order_top">
			<ul id="J_mycenterTabs">
	            <li class='<c:if test="${status ==null ||status =='' }">at</c:if>' onclick="findSupplierOrderByStatus(0)"><a class='<c:if test="${status ==null ||status =='' }">at</c:if>' >全部订单</a></li>
	            <li class='<c:if test="${status == '1' }">at</c:if>' onclick="findSupplierOrderByStatus(1)"><a class='<c:if test="${status == '1' }">at</c:if>'>待付款</a></li>
	            <li class='<c:if test="${status == '21' }">at</c:if>' onclick="findSupplierOrderByStatus(21)"><a class='<c:if test="${status == '21' }">at</c:if>'>已付款</a></li>
	        </ul>
		</div>
		
		<input type="hidden" name="pageNow" id="pageNow" value="${pageBean.page }">
    	<input type="hidden" name="status" id="status" value="${status }">
   		<input type="hidden"  id="totalPage" value="${pageBean.totalPage }">
   	
      <div class="order_w">
	  <c:if test="${not empty pageBean.result}">
	  <div id="orderShow">
	  <c:forEach var="orderDto" items="${pageBean.result }" varStatus="order_status">
		<div class="order_divv">
			<div class="order_t">
				<span>${fn:escapeXml(orderDto.merchantName)}</span>
				<span class="redd">
						 <c:choose>
	                        <c:when test="${ orderDto.status == 1 }">  待付款 </c:when>
	                        <c:when test="${ orderDto.status == 21 }">  已支付 </c:when>
	                         <c:when test="${ orderDto.status == 31 and orderDto.supplyType == 11 }">  待海关审核   </c:when>
	                         <c:when test="${ orderDto.status == 31 and orderDto.supplyType == 12  }">  待系统确认   </c:when>
	                         <c:when test="${ orderDto.status == 31 and orderDto.supplyType == 1  }">  待系统确认   </c:when>
	                         <c:when test="${ orderDto.status == 33 }">  取消中  </c:when>
	                         <c:when test="${ orderDto.status == 41 }">  待发货  </c:when>
	                         <c:when test="${ orderDto.status == 67 }">  海关审核订单失败（待退款） </c:when>
	                         <c:when test="${ orderDto.status == 68 }">  海关支付单审核失败（待退款） </c:when>
	                         <c:when test="${ orderDto.status == 69 }">  海关物流单审核失败（待退款） </c:when>
	                         <c:when test="${ orderDto.status == 70 }">  海关审核失败（待退款） </c:when>
	                         <c:when test="${ orderDto.status == 79 }">  已退款 </c:when>
	                         <c:when test="${ orderDto.status == 81 }">  已发货  </c:when>
	                         <c:when test="${ orderDto.status == 91 }">  已收货  </c:when>
	                         <c:when test="${ orderDto.status == 98 }">  已退款 </c:when>
	                         <c:when test="${ orderDto.status == 99 }">  用户取消 </c:when>
	                         <c:when test="${ orderDto.status == 100 }"> 自动取消  </c:when>
	                         <c:when test="${ orderDto.status == 101 }"> 结束 </c:when>
	                     </c:choose>
	           </span>
			</div>
			<c:forEach items="${orderDto.productList }" var="product">
			<div class="order_c">
				<a href="${path }/cusOrder/orderDetail?orderId=${orderDto.id}" class="link">
					<dl>
						<dt><img src="${ picUrl3 }${product.imgUrl }" /></dt>
						<dd>
							<div class="d_w">
								<span class="fl">${fn:escapeXml(product.productName) }</span>
								<span class="pr"><em class="l"> 规格：${product.pName } × ${product.count}</em></span>
							</div>
						</dd>
					</dl>
				</a>
			</div>
			</c:forEach>
			<div class="order_bb clearfix">
			<span class="lbb">订单金额：<em> <fmt:formatNumber value="${fn:escapeXml(orderDto.price)}" pattern="#0.00" /></em> </span>
			<c:choose>
			   <c:when test="${ orderDto.status == 1 && orderDto.supplyType !=200}"> 
                  <%-- <a href="#" onclick="toPay('${fn:escapeXml(orderDto.id)}')" style="background: #e60012;color: #fff;padding: 0.5rem 2.5rem;">付款</a> --%>
                  <%-- <a href="#" onclick="cancleOrder('${fn:escapeXml(orderDto.id)}')"  style="background: #5a5a5a;padding: 0.5rem 1.5rem;color: #fff;">取消订单</a> --%>
                  <input type="hidden" id="deleorderitemId" value="${fn:escapeXml(orderDto.id)}">
	           </c:when>
	           
            </c:choose>
			</div>
		</div>
		<%-- 需要改样式  --%>
		<c:if test="${order_status.index ge 5}">
			<div class="load-more"><a href="javascript:;" onclick="findSupplierOrderMore()">查看更多</a></div>
		</c:if>
	</c:forEach>
	</div>
	</c:if>
	</div> 
	
	<c:if test="${empty pageBean.result}">
	<div class="order_no">
		<div class="no"><img src="${path }/commons/img/order_no.png"></div>
	    <div class="text">
		    <c:choose>
			    <c:when test="${status ==null ||status =='' }">暂无订单</c:when>
			    <c:when test="${status == '1' }">暂无待付款订单</c:when>
			    <c:when test="${status == '81' }">暂无待收货订单</c:when>
		    </c:choose>
	   </div>
	</div>
	</c:if>
<%@include file="/WEB-INF/views/commons/footer.jsp" %>
<script src="${staticFile_s}/commons/js/zepto.min.js" type="text/javascript"></script> 
<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script> 
<script src="${staticFile_s}/commons/js/mycenter.js" type="text/javascript"></script>
<script src="${staticFile_s}/commons/js/ccigorder/myOrder.js" type="text/javascript"></script>
</body>
</html>