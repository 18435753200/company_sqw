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
<link href="${staticFile_s}/commons/css/my.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/user.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<title><spring:message code="title_order_detail" /></title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
<style type="text/css">
body{background:#f6f6f6}
	h3{
		font-size: 100%;
	    font-weight: normal;
	    color: #ffffff;
	    background-color: #e63232;
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
.fix .f_t {
    margin-bottom: 0;
    display: block;
    background: #ffffff;
    line-height: 2.4rem;
    height: 2.4rem;
    font-size: 1.4rem;
    padding: 1rem 1.5rem;
}
.fix .f_b .btn1 {
    width: 6.5rem;
    height: 3rem;
    line-height:3rem;
        background: #ececec;
    color: #000;
    font-weight: 600;
    display: inline-block;
    text-align: center;
    border-radius: 20px;
}
.fix .f_b .btn2 {
    width: 6.5rem;
    height: 3rem;
    line-height: 3rem;
    background: #f00;
    color: #fff;
    display: inline-block;
    text-align: center;
    border-radius: 20px;
}
.rDialog-ok, .rDialog-ok:hover {
    background: #dd191b;
    color: #fff;
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
.de_t h2 span {
    color: #e53216;
    font-weight: 600;
}
.d_item .nr {
    float: left;
    text-align: left;
    width: 75%;
    line-height: 30px;
}
#orderDetail p, #comment p {
    line-height: 33px;
}
.d_item {
    padding: 1rem 1.5rem;
   border-bottom: 1px solid #f3f3f3;
    font-size: 1.4rem;
    line-height: 2rem;
    overflow: hidden;
}
.headly {
    width: 100%;
    height: 4.4rem;
    line-height: 4.4rem;
    font-size: 1.9rem;
    text-align: center;
    color: #505050;
    position: fixed;
    background: #fff;
    z-index: 100;
}
.de_t {
    height: 3.8rem;
    line-height: 3.8rem;
    padding: 0 1.5rem;
    border-bottom: 1px solid #f3f3f3;
    position: relative;
}
.order-hd {
    min-height: 40px;
    padding: 0 10px;
    background-color: #ffffff;
    border-bottom: 1px solid #f3f3f3;
}
.payment .pay {
    padding: 0 1.5rem;
    border-bottom: 1px solid #f3f3f3;
    line-height: 3.7rem;
}
.fix .f_b {
    display: block;
    background: #ffffff;
    padding: 1rem 1.5rem;
    font-size: 1.2rem;
    border-top: 1px solid #f3f3f3;
    text-align: right;
}
b{font-weight: 600;
margin-right:1rem}
span{font-weight: 600}
</style>
</head>
<script type="text/javascript">
	$(document).ready(function(){
		$("#order_process").hide();
	});
	//查看订单流程
	function checkOrderProcess(type){
		//查看订单流程
		if(type =="checkOrder"){
			$("#orderDetail_main").hide();
			$("#order_process").show();
		}
		if(type =="closeOrder"){
			$("#orderDetail_main").show();
			$("#order_process").hide();
		}
	}
</script>
<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>
<!-- 订单详情 -->
<div id="orderDetail_main">

	<div class="headly">
		<a href="javascript:goBack();" class="goback"><span><b></b><i></i></span></a>
		<h3>订单详情</h3>
	</div>

<div class="wrap" id="orderDetail" style="overflow: auto;position: relative;    top: 4.4rem;
}">
<!-- 订单状态 -->
    <div class="details">
        <div class="de_t">
            <h2><span class="red">
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
		            <c:when test="${ orderDto.status == 79 }">    已退款 </c:when>
		            <c:when test="${ orderDto.status == 81 }">  待收货  </c:when>
		            <c:when test="${ orderDto.status == 91 }">  已收货  </c:when>
		            <c:when test="${ orderDto.status == 98 }"> 已退款 </c:when>
		            <c:when test="${ orderDto.status == 99 }"> 用户取消 </c:when>
		            <c:when test="${ orderDto.status == 100 }"> 自动取消  </c:when>
		            <c:when test="${ orderDto.status == 101 }">  结束 </c:when>
	            </c:choose></span>
            </h2>
        </div>
        <div class="de_b">
	        <a href="javascript:checkOrderProcess('checkOrder');">
		       	 <p><b>订单编号：</b>${orderDto.id }</p>
		       	 <p><b>订单时间：</b> <fmt:formatDate value="${orderDto.createTime}" type="time" timeStyle="full" pattern="yyyy-MM-dd HH:mm"/></p>
		       	 <em class="e"></em>
	        </a>
         </div>
    </div>
    <!-- 收货信息 -->
    <div class="details">
        <div class="d_item">
            <span class="nl"><b>收货信息:</b></span>
            <span class="nr" style="    font-weight: 400;">${orderDto.receiveName } ${orderDto.receiveMobilePhone } <br>${orderDto.receiveAddress }</span>
        </div>
        <div class="d_item">
           	<span class="nl"><b>备注:</b></span>
           	<span class="nr" style="    font-weight: 400;">${orderDto.message }</span>
        </div>
    </div>
	
	<!-- 包裹信息 -->
	<div class="order-item">
        <div class="order-hd">
        
        <h2 style="font-weight:500">物流信息</h2>
        </div>
        <div class="order-bd">
            <c:choose>
         <%-- 国内 或者 韩国 --%>
             <c:when test="${orderDto.supplyType eq 1 or orderDto.supplyType eq 21 }">
             <c:if test="${ orderDto.shipOrderDTOs !=null and fn:length(orderDto.shipOrderDTOs) > 0  }">
             <c:forEach items="${orderDto.shipOrderDTOs }" var="ship">
	            <div class="package-box">
	                <div class="package-hd">
	                    <h4><b>包裹编号：</b>${ship.packNo }</h4>
	                </div>
	                <div class="package-bd">
	                 <a href="${path }/cusOrder/trackOrder?orderId=${orderDto.id }&shipId=${ship.packNo}" class="link"> 
	                    <div class="p-goods-scroll">
	                        <ul class="p-goods-list">
	                        <c:forEach items="${ship.shipItemDtoList }" var="shipItem" varStatus="shipIndex">
	                        	<c:if test="${shipIndex.index < 3 }">
	                            <li><img src="${picUrl3 }${shipItem.productUrl }" /></li> 
	                            </c:if>
	                         </c:forEach>
	                        </ul>
	                    </div>
	                     </a>  
	                    </div>
	                <div class="package-ft">
	               
	      		      <a href="${path }/cusOrder/trackOrder?orderId=${orderDto.id }&shipId=${ship.packNo}" class="package-btn package-btn-link">查看物流</a>
	                <c:if test="${ship.status == 1 }">
					 <a href="javascript:;" class="package-btn package-btn-default">待发货</a>      
	               </c:if>
	                <c:if test="${ship.status == 2 }">
	                	<a href="javascript:;" onclick="confirmOrder(this,'${orderDto.id }','${ship.packNo}')" class="package-btn package-btn-link">确认收货</a>
	               </c:if>
	                <c:if test="${ship.status == 3 }">
	                	<a href="javascript:void(0);"  class="package-btn package-btn-default">已收货</a>
	               </c:if>
	               <c:if test="${ship.status == 9 }">
	                	<a href="javascript:void(0);" class="package-btn package-btn-default">已取消</a>
	               </c:if>
	                </div>
	            </div>
            </c:forEach>
            </c:if>
            <c:if test="${orderDto.shipOrderDTOs == null or fn:length(orderDto.shipOrderDTOs) <= 0 }">暂无包裹信息</c:if>
             </c:when>

				<c:when test="${orderDto.supplyType eq 51}">
					<c:if test="${ orderDto.shipOrderDTOs !=null and fn:length(orderDto.shipOrderDTOs) > 0  }">
						<c:forEach items="${orderDto.shipOrderDTOs }" var="ship">
							<div class="package-box">
								<div class="package-ft">
									<a href="${path }/cusOrder/trackOrder?orderId=${orderDto.id }&shipId=${ship.packNo}" class="package-btn package-btn-link">查看物流</a>
									<c:if test="${orderDto.status == 41}">
										<a href="javascript:;" class="package-btn package-btn-default">待发货</a>
									</c:if>
									<c:if test="${orderDto.status == 81  }">
										<a href="javascript:;" onclick="confirmOrder(this,'${orderDto.id }','${ship.packNo}')" class="package-btn package-btn-link">确认收货</a>
									</c:if>
									<c:if test="${orderDto.status == 91}">
										<a href="javascript:void(0);"  class="package-btn package-btn-default">已收货</a>
									</c:if>
									<c:if test="${orderDto.status == 99 or orderDto.status == 101 }">
										<a href="javascript:void(0);" class="package-btn package-btn-default">已取消</a>
									</c:if>
								</div>
							</div>
						</c:forEach>
					</c:if>
					<c:if test="${orderDto.shipOrderDTOs == null or fn:length(orderDto.shipOrderDTOs) <= 0 }">暂无包裹信息</c:if>
				</c:when>
             <%--  国外保税区  --%>
            
            <c:otherwise>
            	<div class="package-ft">
	                <a href="${path }/cusOrder/trackOrder?orderId=${orderDto.id }" class="package-btn package-btn-link">查看物流</a>
           			<c:if test="${orderDto.status == 41 }">
					<a href="javascript:;" class="package-btn package-btn-default">待发货</a>           
	               </c:if>
	                <c:if test="${orderDto.status == 81 }">
	                	<a href="javascript:;" onclick="confirmOrder(this,'${orderDto.id }')" class="package-btn package-btn-link">确认收货</a>
	               </c:if>
	                <c:if test="${orderDto.status == 91 }">
	                	<a href="javascript:void(0);"  class="package-btn package-btn-default">已收货</a>
	               </c:if>
	               <c:if test="${orderDto.status == 99 or orderDto.status == 101 }">
	                	<a href="javascript:void(0);" class="package-btn package-btn-default">已取消</a>
	               </c:if>
	            </div>
            </c:otherwise>
            
               </c:choose> 
             </div>
    </div>
      
      <!-- 商品信息 -->
	    <div class="details">
	        <div class="de_t">
				<h2 style="font-weight: 600;font-size:1.4rem">商品信息</h2>
			</div>
			<div class="orderbox">
		       	 <c:if test="${orderDto.orderItemDTOs !=null}">
			       	 <c:forEach items="${orderDto.orderItemDTOs  }" var="item">
			            <div class="orderd">
			             <a href="${path }/item/get/${item.pid}" class="link">
			                <img src="${picUrl3 }${item.imgUrl}" />
			                <div class="orderd_r">
			                    <p class="name" style="line-height:1.7rem;">
			                    	<c:if test="${item.productType eq 1}">
			                    		<i style="color:red;">赠品 - </i>
			                    	</c:if>
			                    	${fn:escapeXml(item.pName)}
			                    	<span class="pricep"> <fmt:formatNumber value="${fn:escapeXml(item.price)}" pattern="#0.00" /> </span>
			                    </p>
			                    <p class="size">
									<span class="fl">规格：${ item.skuNameCn } </span>
									<span class="fr">x${item.skuQty}</span>
								</p>
								
			                </div>
			              </a>
			             </div>
			           </c:forEach>
		           </c:if>
			</div>
		</div>
		
		<c:if test="${orderDto.needInvoice == 1 }">
			<div class="item" > 
				<div class="bill_specific">
			    	<ul>
			        	<li class="clearfix"><span class="bill_txt">发票信息</span><span class="ask"></span></li>
			        	<li><span class="bill_txt">发票抬头：</span><i>${orderDto.invoiceTitle }</i></li>
			        	<li><span class="bill_txt">发票内容：</span><i>${orderDto.invoiceDetail }</i></li>
			        </ul>
			    </div>
			</div>
		</c:if>
		
		<div class="details payment">
			<div class="pay" style="font-weight: 600;font-size:1.4rem">付款信息<!-- 支付方式<span>在线支付</span> --></div>
			<div class="mo_w">
				<p class="money"><b>订单总件数：</b><span>${orderDto.totalQty }件</span></p>
				<p class="money"><b>商品总额：</b><span> <fmt:formatNumber value="${fn:escapeXml(orderDto.orderPrice)}" pattern="#0.00" /></span></p>
				<p class="money"><b>运&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;费：</b><span> <fmt:formatNumber value="${fn:escapeXml(orderDto.realTransferprice)}" pattern="#0.00" /></span></p>
			</div>
		</div>
	   	<div class="fix">
			<p class="f_t"><b>订单金额</b><span> <fmt:formatNumber value="${fn:escapeXml(orderDto.price)}" pattern="#0.00" /></span></p>
			<p class="f_b">
				<c:choose>
					<c:when test="${orderDto.status ==1 }">
						<a onclick="cancleOrder('${fn:escapeXml(orderDto.id)}')" class="btn1">取消订单</a>
					</c:when>
					
				</c:choose>
				
				<c:choose>
					<c:when test="${orderDto.status == 1 }">
						 <a onclick="toPay('${fn:escapeXml(orderDto.id)}')" class="btn2">立即支付</a>
					</c:when>
				</c:choose>
			</p>
		</div>
	</div>
</div>
<!-- 订单流程 -->
<div id="order_process">
    <div class="headly">
		<a href="javascript:checkOrderProcess('closeOrder');"><span><b></b><i></i></span></a>
		<h3>订单流程</h3>
	</div>
	<div class="order-item" style="    margin: 0 ;
    background-color: #fff;
    top: 4.4rem;
    position: relative;">
		<div class="order-bd">
			<c:choose>
		        <c:when test="${ orderDto.orderMsgs !=null && fn:length(orderDto.orderMsgs) > 0 }">
		          <div class="order-track">
					<c:forEach items="${ orderDto.orderMsgs}" var="orderMsg" varStatus="count">         
		               <dl <c:if test="${count.index eq 0 }">class="purple"</c:if> >
		                  <dt>${orderMsg.msg }</dt>
		                  <dd><fmt:formatDate value="${orderMsg.createTime}" type="time" timeStyle="full" pattern="yyyy-MM-dd HH:mm"/></dd>
		               </dl>
		              </c:forEach>   
		            </div>
		        </c:when>
		        <c:otherwise>
		        	暂无订单流程信息
		        </c:otherwise>
        	</c:choose>  
        </div>
	</div>
</div>

<%@include file="/WEB-INF/views/commons/footer.jsp" %>
<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
<script src="${staticFile_s}/commons/js/ccigorder/myOrder.js" type="text/javascript"></script>
<script src="${staticFile_s}/commons/js/order/orederDetail.js" type="text/javascript"></script>
<script type="text/javascript">
	$(".bill_specific .ask").live("click", function(){
		window.location.href = $("#path").val() + "/cusOrder/invoiceInfo";
	});
</script>
</body>
</html>