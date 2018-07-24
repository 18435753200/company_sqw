<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<title>配送发货</title>
<%@include file="/WEB-INF/views/zh/include/base.jsp" %>
<script type="text/javascript" src="${path}/js/poporder/poporder2.js"></script>
<link href="${path}/css/poporder/orderdetails.css" rel="stylesheet" type="text/css">
<link href="${path}/css/zh/shang.css" rel="stylesheet" type="text/css">

<link rel="stylesheet" type="text/css" href="${path}/css/poporder/poporder.css"/>
<script>
$(document).ready(function(){
	$("input[name='sku-checkbox']").live("change",toggleCheckAll);
	$("input[name='checkAll']").live("change",checkAll);
		    //根据状态加载订单状态的图片
	    	var status = $("#orderStatus").val();
	    	if(status==1){
	    		$("#p2 img").attr("src","../images/c_orderstatus1.png");
	    	}else if(status==21 || status==31){
	    		$("#p2 img").attr("src","../images/c_orderstatus2.png");
	    	}else if(status==41){
	    		$("#p2 img").attr("src","../images/c_orderstatus3.png");
	    	}else if(status==81){
	    		$("#p2 img").attr("src","../images/c_orderstatus4.png");
	    	}else if(status==91){
	    		$("#p2 img").attr("src","../images/c_orderstatus5.png");
	    	}else if(status==99 || status==100){
	    		$("#p2 img").attr("src","../images/orderquxiao.png");
	    	}
	    	else if(status==67 || status==68 || status==69 || status==70){
	    		$("#p2 img").attr("src","../images/auditerror.png");
	    	}
	    	else if(status==79){
	    		$("#p2 img").attr("src","../images/refund1.png");
	    	}
	    	else{
	    	}
		});
//获取窗口的高度 
var windowHeight; 
// 获取窗口的宽度 
var windowWidth; 
// 获取弹窗的宽度 
var popWidth; 
// 获取弹窗高度 
var popHeight; 
function init(){ 
    windowHeight=$(window).height(); 
    windowWidth=$(window).width(); 
    popHeight=$(".window").height(); 
    popWidth=$(".window").width(); 
} 
//关闭窗口的方法 
function closeWindow(){ 
    $(".title img").click(function(){ 
        $(this).parent().parent().hide("slow"); 
    }); 
    $("#closeShipWinBtn").click(function(){ 
        $("#center").hide("slow"); 
    }); 
} 
//定义弹出居中窗口的方法 
function popCenterWindow(){ 
    init(); 
    //计算弹出窗口的左上角Y的偏移量 
	var popY=(windowHeight-popHeight)/2 + document.body.scrollTop; 
	var popX=(windowWidth-popWidth)/2; 
	//alert('jihua.cnblogs.com'); 
	//设定窗口的位置 
	$("#center").css("top",popY).css("left",popX).slideToggle("slow");  
	closeWindow(); 
} 

</script>

<style type="text/css">

	#liuy  {
			 margin: 0;
   			 padding: 0;
   			 border: 0;
   			
    		  background-color: #fff;
    		}  

</style>

</head>
<body>
<%@include file="/WEB-INF/views/zh/include/header.jsp" %>
<div class="wrap" id="orderDetail">
	<div class="logo3">
		<p class="p1">您的位置： 订单管理&gt;<a href="../order/getOrder">订单管理</a>&gt;<span>配送</span></p>
	<!-- 	<p class="p2" id="p2"><img src="/web-platform/commons/images/spotStatus1.png"></p> -->
	</div>	
    <%-- <div class="order-item">
        <div class="order-hd">
        <input type="hidden"  id="orderStatus" value="${orderDTO.status}">
            <h2>订单号：${orderDTO.id } &nbsp;&nbsp;&nbsp;&nbsp;
            	订单状态：<span>
            				<c:if test="${orderDTO.status eq  1}">待支付</c:if>
							<c:if test="${orderDTO.status eq  21}">已支付</c:if>
							<c:if test="${orderDTO.status eq  31}">待海关审核</c:if>
							<c:if test="${orderDTO.status eq  41}">待发货</c:if>
							<c:if test="${orderDTO.status eq  67}">海关审核订单失败(待退款)</c:if>
							<c:if test="${orderDTO.status eq  68}">海关支付单审核失败(待退款)</c:if>
							<c:if test="${orderDTO.status eq  69}">海关物流单审核失败(待退款)</c:if>
							<c:if test="${orderDTO.status eq  70}">海关审核失败(待退款)</c:if>
							<c:if test="${orderDTO.status eq  79}">已退款</c:if>
							<c:if test="${orderDTO.status eq  81}">待收货</c:if>
							<c:if test="${orderDTO.status eq  91}">已收货</c:if>
							<c:if test="${orderDTO.status eq  99}">用户取消</c:if>
							<c:if test="${orderDTO.status eq  100}">自动取消</c:if>
            	        </span> &nbsp;&nbsp;&nbsp;&nbsp;
            	配送方式：<span>
	            	        <c:if test="${orderDTO.shipType eq  0}">普通</c:if>
			            	<c:if test="${orderDTO.shipType eq  1}">自提</c:if>
			            	<c:if test="${orderDTO.shipType eq  2}">货到付款</c:if>
		            	</span> &nbsp;&nbsp;&nbsp;&nbsp;
            	支付方式：<span>
	            	        <c:if test="${orderDTO.payType eq  0}">线上</c:if>
			            	<c:if test="${orderDTO.payType eq  1}">货到付款</c:if>
		            	</span>
            </h2>
        </div>
        <div class="order-bd">
        	<p></p>
        	<p>成交时间：<span><fmt:formatDate value="${orderDTO.createTime }" type="both"/> </span></p>
            <p>订单金额：<span class="red">¥${orderDTO.orderPrice }</span></p>
            <p>运    &nbsp;&nbsp;&nbsp;费：¥${orderDTO.realTransferprice }</p>
        </div>
    </div> --%>
    
     <%-- <c:if test="${orderDTO.supplyType ne 1 and orderDTO.supplyType ne 51 }">
     
             <div class="order-item">
		       <div class="order-hd">
		           <h2>物流信息</h2>
		       </div>
		       <div class="order-bd">
		    
		           <div class="order-track">  
		           	<div class="order-ti">
				        	<span>物流编号：${orderDTO.logisticsCarriersId }</span>  
				        	<span>物流公司：${orderDTO.logisticsCarriersName }</span>
				        	<span>运单号码：${orderDTO.logisticsNumber }</span>
			        	</div>
		           <c:choose>
		            <c:when test="${empty orderDTO.orderLogisticsMsgs  }">
		            	暂无物流信息
		            </c:when>
		            <c:otherwise>
		               	<div class="order-ti">
				        	<span>物流编号：${orderDTO.logisticsCarriersId }</span>  
				        	<span>物流公司：${orderDTO.logisticsCarriersName }</span>
				        	<span>运单号码：${orderDTO.logisticsNumber }</span>
			        	</div>
			            <c:forEach items="${orderDTO.orderLogisticsMsgs }" var="logisticsMsgs" varStatus="count">
				            <c:if test="${count.last}">
					     		<dl class="purple">
				                    <dt></dt>
				                    <dd><fmt:formatDate value="${logisticsMsgs.createTime }" type="both"/>  &nbsp;&nbsp; ${logisticsMsgs.msg } </dd>
				                </dl>
				            </c:if> 
			               <c:if test="${!count.last}">
					     		<dl>
				                    <dt></dt>
				                    <dd><fmt:formatDate value="${logisticsMsgs.createTime }" type="both"/>  &nbsp;&nbsp; ${logisticsMsgs.msg }</dd>
				                </dl>
				            </c:if>
			            </c:forEach>
		            </c:otherwise>
		           </c:choose>     
		           </div>
		       </div>
		   </div>
     </c:if>  --%>
        
    <%-- <div class="order-item">
        <div class="order-hd">
            <h2>订单信息</h2>
        </div>
        <div class="order-bd">
        	<h4>收货人信息</h4>
            <p class="u_name">收  货 人：${orderDTO.receiveName }  </p>
            <p class="u_name">联系电话：${orderDTO.receiveMobilePhone }  </p>
            <p class="u_address">收货地址：${orderDTO.receiveAddress } </p>
            
        </div>
        
         <div class="order-bd">
            <h4>买家信息</h4>
            <p class="u_name">用户昵称：${user.userName  }  </p>
            <p class="u_name">真实姓名：${user.realName  }  </p>
            <p class="u_name">电子邮箱：${user.email  }  </p>
            <p class="u_name">联系电话：${user.mobile  }  </p>
            <p class="u_address">用户地址：${user.address  } </p>
        </div>
    </div>
	<div class="order-bd" id="liuy">
        	<h4>买家订单留言</h4>
            <p class="u_address">订单留言：${orderDTO.message } </p>
            
        </div>
	
    <div class="order-item">
        <div class="order-bd">
            <h4>付款信息</h4>
        </div>
        <div class="order-bd">
            <p>订单件数：${orderDTO.totalQty } </p>
            <p>订单金额：¥<fmt:formatNumber value="${orderDTO.orderPrice }" pattern="0.00#"/> </p>
            <p>实际运费：¥ <fmt:formatNumber value="${orderDTO.realTransferprice }" pattern="0.00#"/></p>
            <p>折扣金额：¥ <fmt:formatNumber value="${orderDTO.discountPrice }" pattern="0.00#"/></p>
            <p>商品关税：¥ <fmt:formatNumber value="${orderDTO.realTotalTax }" pattern="0.00#"/></p>
            <p>应付金额：<span class="red">¥ <fmt:formatNumber value="${orderDTO.price }" pattern="0.00#"/></span></p>
        </div>
    </div>
   <c:if test="${orderDTO.supplyType eq 1 or orderDTO.supplyType eq 51}">
     <div class="area" id="logisticsInfo">
        <div class="order-bd"><h4>物流信息</h4></div>
		<div class="area-bd">
		          <c:choose>
		         <c:when test="${empty orderDTO.shipOrderDTOs}">
		          	<div class="no-logistics"> 暂无物流信息</div>  
		         </c:when>
		         <c:otherwise>
			         <c:forEach items="${orderDTO.shipOrderDTOs}" var="shipOrderDTO" varStatus="count">
			         <div class="item">
	                    <div class="sub"> <span class="year">
	                    <fmt:formatDate value="${shipOrderDTO.createTime}" pattern="yyyy"/></span> <span class="month">
	                    <fmt:formatDate value="${shipOrderDTO.createTime}" pattern="MM/dd"/></span>
	                    <div class="clr"></div>
	                    <span class="total"><em>¥</em><fmt:formatNumber value="${shipOrderDTO.price }" pattern="0.00#"/></span> </div>
	                    <div class="main">
<!-- 	                        <div class="main-content"> -->
<!-- 	                            <div class="mc-hd">  -->
		                            <span class="tips">国内包裹   ${count.index+1} </span>
		                            <span class="consignor">发货人：${shipOrderDTO.lastEditBy }</span> &nbsp;&nbsp;&nbsp;
		                            <span class="consignor">包裹号：${shipOrderDTO.packNo }</span> 
<!-- 	                            </div> -->
<!-- 	                            <div class="mc-bd"> -->
<!-- 	                                <ul class="goods-list clearfix"> -->
		                                <c:forEach items="${shipOrderDTO.shipItemDtoList }" var="shipItemDtoList">
<!-- 	                                     <li> -->
<!-- 	                                         <div class="goods-box"> -->
	                                             <div class="goods-pic"><a href="javascript:void(0)"><img src="${shipItemDtoList.productUrl }" width="60" height="60" /></a></div>
	                                             <div class="goods-txt"><span class="sku" title="${shipItemDtoList.pName}">商品：${shipItemDtoList.pName} </span><span class="amount"  title="${shipItemDtoList.qty}">数量：${shipItemDtoList.qty}</span></div>
<!-- 	                                         </div> -->
<!-- 	                                     </li> -->
		                                </c:forEach>
<!-- 	                                </ul> -->
<!-- 	                            </div> -->
<!-- 	                        </div> -->
	                        <div class="main-bar">
	                            <div class="box"> 
		                            <c:if test="${shipOrderDTO.logisticsCarriersName != null }">
		                            	<span class="kd-name">${shipOrderDTO.logisticsCarriersName }</span> 
		                             	<span class="kd-code">快递单号：<a href="javascript:void(0)"><em>${shipOrderDTO.logisticsNumber }</em></a></span>
		                            </c:if>
		                             <c:if test="${shipOrderDTO.logisticsCarriersName == null }">
		                            	<span class="kd-name">暂无物流信息</span>
		                            </c:if>
	                            </div>
	                        </div>
			              </div>
			            </div>
			         </c:forEach>
		       </c:otherwise>
		       </c:choose> 
           </div>
    </div>
  </c:if> --%>
  <!-- 补录物流信息开始 -->
				<div class="window" id="center" style="z-index:9;display: none;"> 
			    	<div id="title" class="title" style="z-index:9;"><img src="../images/uploadify-cancel.png" alt="关闭" />录入物流信息</div> 
			    	<div class="content" style="z-index:9;">
						<div class="log-c2">
							<div class="item">
								<i class="error" style="width: 100%;text-align: center;float: left;color: #f00;"></i>
							</div>
							<div class="item">
								<span style="float:left;">物流商：</span>
								
								<div >
								
							       	<select id="logistic3" class="selectpicker show-tick"  data-live-search="true">
							       		  <c:if test="${ not empty logisticTemps }">
							       		  		<c:forEach items="${logisticTemps }"  var="temp" > 
										  		 	<option value="${temp.providerId}">${temp.providerName}</option> 
							       				</c:forEach> 
							       			</c:if>
							       	</select>
							    </div>
							</div>
							<div class="item" style="margin-top:15px;">
								<span style="float:left;">物流单号:</span>
								<input type="text" id="logisticCode" class="">
							</div>
								<div class="item" style="margin-top:15px;"> 
								<span style="float:left;">数量:</span>
								<input type="text" id="qqty" class="" value="">
								
							</div>
							</div>
								<div class="item" style="margin-top:5px;"> 
							<!-- 	<span style="float:left;">订单号:</span> -->
								<input type="hidden" id="aa" class="" value="${orderDTO.id }">
								
							</div>
						<!-- 	<div class="item" style="font-size:12px;">
								<span style="height:15px;font-weight:bold;line-height:15px;">收货人：</span><font style="height:15px;" id="receiveNameField"> </font></br>
							</div>
							
							<div class="item" style="font-size:12px;">
								<span style="height:15px;font-weight:bold;line-height:15px;">联系电话：</span><font style="height:15px;" id="receivePhoneField"> </font></br>
							</div>
							<div class="item" style="font-size:12px;">
								<span style="height:15px;font-weight:bold;line-height:15px;float:left">收货地址：</span>
								<font style="height:15px;" >
									<table>
									<tr><td>
										<font id="receiveAddressField"></font>
									</td></tr>
								</table>
								</font>
							</div> -->
						</div>
						
						<div class="log-c3">
							<button type="button" onclick="goUpdateShipOrderLogistic4()">确定</button>
							<button type="button" id="closeShipWinBtn">取消</button>
						</div>
					</div> 
			    </div>
				<!-- 补录物流信息结束 -->
       
    
        <%-- <div class="order-bd">
            <h4>物流详情</h4>  
        </div>
        <form method="post" id="productAction" >
        <div class="order-bd">
        <c:forEach items="${orderDTO.shipOrderDTOs}" var="shipOrderDTO" varStatus="count">
			         <div class="item">
	                    <div class="sub"> <span class="year">
	                    <fmt:formatDate value="${shipOrderDTO.createTime}" pattern="yyyy"/></span> <span class="month">
	                    <fmt:formatDate value="${shipOrderDTO.createTime}" pattern="MM/dd"/></span>
	                    <div class="clr"></div>
	                   
	                    <div class="main">
<!-- 	                        <div class="main-content"> -->
<!-- 	                            <div class="mc-hd">  -->
		                            <span class="tips">国内包裹   ${count.index+1} </span>
		                            <span class="consignor">发货人：${shipOrderDTO.lastEditBy }</span> &nbsp;&nbsp;&nbsp;
		                            <span class="consignor">包裹号：${shipOrderDTO.packNo }</span> 
<!-- 	                            </div> -->
<!-- 	                            <div class="mc-bd"> -->
<!-- 	                                <ul class="goods-list clearfix"> -->
		                                <c:forEach items="${shipOrderDTO.shipItemDtoList }" var="shipItemDtoList">
<!-- 	                                     <li> -->
<!-- 	                                         <div class="goods-box"> -->
	                                             <div class="goods-pic"><a href="javascript:void(0)"><img src="${shipItemDtoList.productUrl }" width="60" height="60" /></a></div>
	                                             <div class="goods-txt"><span class="sku" title="${shipItemDtoList.pName}">商品：${shipItemDtoList.pName} </span><span class="amount"  title="${shipItemDtoList.qty}">数量：${shipItemDtoList.qty}</span></div>
<!-- 	                                         </div> -->
<!-- 	                                     </li> -->
		                                </c:forEach>
<!-- 	                                </ul> -->
<!-- 	                            </div> -->
<!-- 	                        </div> -->
	                        <div class="main-bar">
	                            <div class="box"> 
		                            <c:if test="${shipOrderDTO.logisticsCarriersName != null }">
		                            	<span class="kd-name">${shipOrderDTO.logisticsCarriersName }</span> 
		                             	<span class="kd-code">快递单号：<a href="javascript:void(0)"><em>${shipOrderDTO.logisticsNumber }</em></a></span>
		                            </c:if>
		                             <c:if test="${shipOrderDTO.logisticsCarriersName == null }">
		                             	<span class="kd-name">暂无物流信息</span>
		                            </c:if>
	                            </div>
	                        </div>
			              </div>
			            </div>
			         </c:forEach> --%>
			         <div class="order-item">
			         
			         <div class="order-bd">
		<c:if test="${orderDTO.status eq  41}">
            <table class="tb-void">
            	<tr>
            	<th width="50"><input type="checkbox" name="checkAll" />全选</th>
            		<!-- <th width="50">请选择</th> -->
            		<th width="10">数量</th>
            		<th width="120">商品</th>
            		<!-- <th width="50">发货数量</th> -->
            		<!-- <th width="150">物流商</th> -->
            		<!-- <th width="150">物流单号</th> -->
            		<th width="50">交易状态</th>
            		<!-- <th width="100">操作</th> -->
            	
            	</tr>
            	
            	
            	
            	<c:forEach items="${orderDTO.orderItemDTOs }" var="orderItemDTO">
	            	<tr>
	            	<!-- <td>
	            		<input name="checkbox" type="checkbox"   value="false" />
	            	</td> -->
	            	
	         			<td><input type="checkbox" class="ck"name="sku-checkbox"></td>
	         			<td >${orderItemDTO.skuQty}</td>
	            		<td>
	            		    <div class="goods-pic"><img src="${orderItemDTO.imgUrl }" /></div>
	            		      <div class="goods-info">
	                              <span class="name">${orderItemDTO.pName }</span>
	                        </div>
	            		</td>
	            		<!-- <td ><input type="text" name="qty" value="" size="1"/></td> -->
	            		<c:if test="${orderItemDTO.productType eq 1}">
	            		<td><span>${orderItemDTO.skuNameCn }<p class="red"><font style="color: red">赠品</font></p></span></td>
	            		</c:if>
	            		
	            	<%-- 	<td>  	<select id="logistic3" name="logisticsCarriersName" class="selectpicker show-tick"  data-live-search="true">
							       		  <c:if test="${ not empty logisticTemps }">
							       		  		<c:forEach items="${logisticTemps }"  var="temp" > 
										  		 	<option value="${temp.providerId}">${temp.providerName}</option> 
							       				</c:forEach> 
							       			</c:if>
							       	</select>
						</td> --%>
	            		
	            	<!-- 	<td ><input type="text" id="logisticCode" name="logisticsNumber" value=""/></td> -->
	            		<td>
            				
							
							<c:if test="${orderItemDTO.skuQty >  0}">待发货</c:if>
							
							<c:if test="${orderItemDTO.skuQty eq  0}">待收货</c:if>
	
	            		</td>
	            		
	            	</tr>
            	</c:forEach>
            </table>  
            
            <%-- <td><input type="hidden" value="${orderDTO.id}" id="shipOrderId" name="orderId"/></td> --%>
            </c:if>
            <div  style="text-align:center; width:100%;height:100%;margin:0px; "  >     
        	
            </div>
            <c:if test="${orderDTO.status eq  41}">
            	 <div  style="text-align:center; width:100%;height:100%;margin:0px; "  >   
            	 
	            		
           		 		<a style="font-size:1.8em; width: 15%;height: 20%;background: #fb4700;border-radius: 5px;" class="dc-btn" href="javascript:void(0)" onclick="showWuLiu('${orderList.id }', '${orderList.receiveName }','${orderList.receiveMobilePhone }','${orderList.receiveAddress }')">配送 </a>&nbsp
            			<!-- <input type="button" onclick="showWuLiu('1','2','3','4')" value="配送" /> -->
	            		
            <a  style="font-size:1.8em; width: 15%;height: 20%;background: #fb4700;border-radius: 5px;" type="button" href="../order/updateStatusPeisong?oid=${orderDTO.id }">配送完毕</a>
            </div>
            </c:if>
            <c:if test="${orderDTO.status eq  81}">
            <div  style="text-align:center; width:100%;height:100%;margin:0px; "  >
            <a style="font-size:1.8em; width: 15%;height: 20%;background: #fb4700;border-radius: 5px;" type="button">已成功发货</a>
            </div>
               <div  style="text-align:center; width:100%;height:100%;margin:0px; "  >   
            <a  style="font-size:1.8em; width: 15%;height: 20%;background: #fb4700;border-radius: 5px;" type="button" href="../order/getOrder">返回订单列表页</a>
            </div>
            </c:if>
        </div>
        </div>
       
        

    
     <%--  <div class="total">
			<ul>
				<li><span>总商品金额：</span>￥<fmt:formatNumber value="${orderDTO.orderPrice }" pattern="0.00#"/></li>
				<li><span> - 优惠：</span>￥<fmt:formatNumber value="${orderDTO.discountPrice }" pattern="0.00#"/></li>
				<li><span>+ 运费：</span>￥<fmt:formatNumber value="${orderDTO.realTransferprice }" pattern="0.00#"/></li>
				<li><span>+ 关税：</span>￥<fmt:formatNumber value="${orderDTO.realTotalTax }" pattern="0.00#"/></li>
				<li class="to"><span>应付总额：</span><b>￥ <fmt:formatNumber value="${orderDTO.price }" pattern="0.00#"/></b><li>
			</ul>
	  </div> --%>
</div>
<%@include file="/WEB-INF/views/zh/include/last.jsp"%>
</body>
</html>