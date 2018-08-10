<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
	<title>UNICORN-订单详情</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=path %>/commons/css/dingdan.css"/>
	<link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/kuang.css" />
	<script>
		$(document).ready(function(){
		    //根据状态加载订单状态的图片
	    	var status = $("#orderStatus").val();
	    	if(status==1){
	    		$("#p2 img").attr("src","<%=path %>/commons/images/buy_status1.png");
	    		$("#purOrderStatus").html("已合单");
	    	}else if(status==21){
	    		$("#p2 img").attr("src","<%=path %>/commons/images/buy_status2.png");
	    		$("#purOrderStatus").html("已分配");
	    	}else if(status==31){
	    		$("#p2 img").attr("src","<%=path %>/commons/images/buy_status3.png");
	    		$("#purOrderStatus").html("已提交采购单");
	    	}else if(status==32){
	    		$("#p2 img").attr("src","<%=path %>/commons/images/buy_status4.png");
	    		$("#purOrderStatus").html("经销商已填写合同");
	    	}else if(status==33){
	    		$("#p2 img").attr("src","<%=path %>/commons/images/buy_status5.png");
	    		$("#purOrderStatus").html("供应商已填写合同");
	    	}else if(status==34){
	    		$("#p2 img").attr("src","<%=path %>/commons/images/buy_status6.png");
	    		$("#purOrderStatus").html("经销商已确认合同");
	    	}else if(status==62){
	    		$("#p2 img").attr("src","<%=path %>/commons/images/buy_status7.png");
	    		$("#purOrderStatus").html("供应商已确认合同");
	    	}else if(status==71){
	    		$("#p2 img").attr("src","<%=path %>/commons/images/buy_status8.png");
	    		$("#purOrderStatus").html("供应商已发货");
	    	}else if(status==81){
	    		$("#p2 img").attr("src","<%=path %>/commons/images/buy_status9.png");
	    		$("#purOrderStatus").html("经销商已收货");
	    	}else{
	    	}
		});
		
		   //显示零售商的订单详情(sku/收货地址)
		   function xiangqing(orderId){
		 	 $.ajax({ 
	 	 		  async:false,
	              type : "post", 
	              url : '<%=path %>/buyOrder/getRetailerOrderDetail', 
	              data:{"orderId":orderId},
	              dataType:"html",
	              success : function(addressJson) { 
		              $(".alert_xqing").html(addressJson);
		              $(".alert_xqing").show();		
				  },
		 		});
		 	}
		function closexq(){
			$("#orderXq").hide();
		}
		
		function closexq1(){
			$("#orderXq").hide();
		}
	</script>
</head>
<body>
<!-- 导航 start -->
<%@include file="/WEB-INF/views/include/header.jsp"%>
	<!--logo开始-->
	<div class="logo3">
		<p class="p1">您的位置： 买家中心 > <a href="<%=path %>/buyOrder/buyOrder">已合并订单 ></a> <span>订单详情</span></p>
		<input type="hidden"  id="orderStatus" value="${purDto.status}">
		<p class="p2" id="p2"><img></p>
	</div>
	<!--logo结束-->
	<!--订单状态开始-->
	<div class="ding">
		<div class="ding1">
			采购单号：${purDto.poId } &nbsp;&nbsp;&nbsp; 状态：<span id="purOrderStatus"></span>
		</div>
		<div class="ding2">
			<p>
				<span class="s1">订单信息</span><br>
				 <c:forEach items="${orderMsg }" var="msg">
				 <p>
				  	<span class="time"><fmt:formatDate value="${msg.createTime }"  type="both"/> </span>
				  	<span class="time1" title="${fn:escapeXml(msg.createBy)}">${fn:escapeXml(msg.createBy)} </span>
				  	<span class="state">${fn:escapeXml(msg.msg)}</span>
				    <c:if test="${msg.msg==经销商已签收订单}">
				    	<span class="s2"><fmt:formatDate value="${msg.createTime }"  type="both"/>&nbsp;  ${msg.createBy }&nbsp;${fn:escapeXml(msg.msg)}</span><br>
				    </c:if>
				   </p>
				 </c:forEach> 
			</p>
		</div>
	</div>
	<div class="xing">
		<div class="xing3">
			<table>
			<c:choose>
				<c:when test="${purDto.dealerId!=''&&purDto.dealerId!=null}">
					<tr class="t1">
						<td>经销商信息</td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td title="${fn:escapeXml(dealer.companyName)}"><p class="ov">经销商名称：${fn:escapeXml(dealer.companyName)}</p></td>
						<td title="${fn:escapeXml(dealer.address)}"><p class="ov">公司地址：${fn:escapeXml(dealer.address)}</p></td>
						<td>联系电话：${fn:escapeXml(dealer.phone)}</td>
					</tr>
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
				<tr class="t1">
					<td>卖家信息</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td title="${fn:escapeXml(supplier.name)}"><p class="ov">商家：${fn:escapeXml(supplier.name)}</p></td>
					<td>联系电话：${fn:escapeXml(supplier.phone)}</td>
				</tr>
				<tr class="t1">
					<td>订单信息</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>采购单号：${purDto.poId }</td>
					<td>成交时间：<fmt:formatDate value="${purDto.createTime}" type="both"/></td>
					<td></td>
				</tr>
			</table>
		</div>
		<!--商品清单开始-->
		<div class="xing4">
			<div class="zong">
				 <div class="z1">
					<p>
					<span class="data"><fmt:formatDate value="${purDto.createTime}" type="both"/></span>
					 <span class="sp">采购单：${purDto.poId }</span>
					  <span class="jg">价格(${purDto.moneyUnitNameCn })</span>
					  <span class="sl">数量</span>
					  <span class="sfk">应付款(${purDto.moneyUnitNameCn })</span>
					  <span class="ztai">交易状态</span>
					</p>
				</div> 
				<div class="z2">
					<dl>
							<dt><img src="${purDto.imgUrl }" style="width:80px; heigth:80px;"></dt>
							<dd title="${fn:escapeXml(purDto.pName)}">${fn:escapeXml(purDto.pName)}</dd>
						</dl>
						<div class="blink"></div>
						<p>
							<span class="s1">
							<c:choose>
									<c:when test="${purDto.productPriceMin==purDto.productPriceMax }">
										<fmt:formatNumber pattern="0.0000#" value="${purDto.productPriceMin}"></fmt:formatNumber>
									</c:when>
									<c:otherwise>
									<fmt:formatNumber pattern="0.0000#" value="${purDto.productPriceMin}"></fmt:formatNumber>~
									<fmt:formatNumber pattern="0.0000#" value="${purDto.productPriceMax}"></fmt:formatNumber>
									</c:otherwise>
							</c:choose>
								
							</span>
							<span class="s2">${purDto.qty }</span>
							<span class="s3"><fmt:formatNumber pattern="0.0000#" value="${purDto.price }"></fmt:formatNumber></span>
							<span class="s4" style="">
							 <c:if test="${purDto.status==1 }">已合单</c:if> 
							 <c:if test="${purDto.status==21 }">已分配</c:if> 
							 <c:if test="${purDto.status==31 }">已提交采购单</c:if>
							 <c:if test="${purDto.status==32 }">经销商已填写合同</c:if> 
							 <c:if test="${purDto.status==33 }">供应商已填写合同</c:if> 
							 <c:if test="${purDto.status==34 }">经销商已确认合同</c:if>  
							 <c:if test="${purDto.status==62 }">供应商已确认合同</c:if> 
							 <c:if test="${purDto.status==71 }">供应商已发货</c:if> 
							 <c:if test="${purDto.status==81 }">经销商已收货</c:if>
						   </span>
						</p>
				</div>
				   <!-- 采购单SKU号开始 -->
				<div class="sk">
					<div class="sk1">
					     <span class="me1">商品</span>
					     <span class="me2">SKU号</span>
					     <span class="me3">规格</span>
					     <span class="me4">数量</span>
					     <span class="me5">价格(${purDto.moneyUnitNameCn })</span>
					     <span class="me6">金额(${purDto.moneyUnitNameCn })</span>
					</div>
					<c:forEach items="${purDto.itemlist}" var="purDtaoDetail" >
					<div class="sk2">
						<span class="me1" title="${purDto.pName }">${purDto.pName }</span>
						<span class="me2">${purDtaoDetail.skuId }</span>
						<span class="me3" title="${purDtaoDetail.skuNameCn }">${purDtaoDetail.skuNameCn }</span>
						<span class="me4">${purDtaoDetail.qty }</span>
						<span class="me5"><fmt:formatNumber pattern="0.0000#" value="${purDtaoDetail.price }"></fmt:formatNumber></span>
						<span class="me6"><fmt:formatNumber pattern="0.0000#" value="${purDtaoDetail.price*purDtaoDetail.qty}"></fmt:formatNumber></span>
					</div> 
					</c:forEach>
			     </div>	
			    <!-- SKU号结束 -->
			</div>
			<div class="c3">
			
				<div class="two">	
				<!-- 获取订单的下的信息显示出来 -->
				<c:forEach items="${purDto.retailerOrderDto }"  var="retailerOrder">
					<div class="two1">
						<div class="ff">
								<span class="f1"><fmt:formatDate value="${retailerOrder.createTime}" type="both"/></span>
								<span class="f2">订单号：${retailerOrder.orderId}</span>
								<span class="f3">零售价格(元)</span>
								<span class="f4">数量</span>
								<span class="f5">实付款(元)</span>
								<span class="f6">状态</span>
								<span class="f7">操作</span>
						</div>	
						<dl>
							<dt><img src="${purDto.imgUrl}"></dt>
							<dd title="${retailerOrder.pName }">${retailerOrder.pName }</dd>
						</dl>
						<div class="blink"></div>
						<p>
							<span class="s1">
							<c:choose>
									<c:when test="${retailerOrder.productPriceMin==retailerOrder.productPriceMax }">
										<fmt:formatNumber value="${retailerOrder.productPriceMin}" pattern="0.00#"/>
										
									</c:when>
									<c:otherwise>
										<fmt:formatNumber value="${retailerOrder.productPriceMin}" pattern="0.00#"/>~
										<fmt:formatNumber value="${retailerOrder.productPriceMax}" pattern="0.00#"/>
									</c:otherwise>
							</c:choose>
								
							</span>
							<span class="s2">${retailerOrder.qty }</span>
							<span class="s3">
							<%-- <fmt:formatNumber value="${retailerOrder.paidPrice}" pattern="0.00#"/> --%>
							<fmt:formatNumber value="${retailerOrder.paidPrice-retailerOrder.realTransferprice}" pattern="0.00#"/>
							</span>
							<span class="s4">
							<c:if test="${retailerOrder.status==21 }">已付首款</c:if>
							<c:if test="${retailerOrder.status==31 }">订单已经确认</c:if>
							<c:if test="${retailerOrder.status==32 }">订单已经确认</c:if>
							<c:if test="${retailerOrder.status==33 }">订单已经确认</c:if>
							<c:if test="${retailerOrder.status==34 }">订单已经确认</c:if>
							<c:if test="${retailerOrder.status==41 }">等待供应商发货</c:if>
							<c:if test="${retailerOrder.status==51 }">供应商已发货</c:if>
							<c:if test="${retailerOrder.status==61 }">等待交付余款</c:if>
							<c:if test="${retailerOrder.status==71 }">等待经销商发货</c:if>
							<c:if test="${retailerOrder.status==81 }">经销商已发货</c:if>
							<c:if test="${retailerOrder.status==91 }">订单完成</c:if>
							<c:if test="${retailerOrder.status==101 }">系统默认完成</c:if>
							<c:if test="${retailerOrder.status==100 }">系统取消</c:if><br>
						   </span>
						   <c:if test="${!empty buttonsMap['已合并订单-订单详情-单个订单详情'] }">
						   	<span class="s5"><input type="button" class="qi" value="查看详情" onclick="xiangqing('${retailerOrder.orderId}')"></span>
						   </c:if>
						</p>
					</div>
					
					<div class="blink"></div>
					</c:forEach> 
					
				</div>
			</div>
		</div>
		<div class="bink"></div>
		<%@include file="/WEB-INF/views/dealerbuy/basePage/reOrderDetail.jsp"%>
		<div class="xing5">
			<p>应付款 ${purDto.moneyUnitSymbols } <span><fmt:formatNumber pattern="0.0000#" value="${purDto.price }"></fmt:formatNumber>
			</span></p>
		</div>
	</div>
	<!--订单信息结束-->
  <!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
	<!-- 底部 end -->
</body>
</html>