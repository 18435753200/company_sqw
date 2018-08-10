<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" >
	<title>UNICORN-订单详情</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=path %>/commons/css/reset.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path %>/commons/css/m_xiangq.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/kuang.css" />
 <%--   <script src="<%=path %>/commons/js/jquery-1.8.1.min.js"></script> --%>
	<script src="<%=path %>/commons/js/open.js"></script>
	<script src="<%=path %>/commons/js/buyorder.js"></script>
	<script type="text/javascript">
$(document).ready(function(){
/* 	var loaded = true;
	var top = $(".fix-bar-wrap").offset().top;
	$(window).scroll(Add_Data);
	function Add_Data() {
		var scrolla = $(window).scrollTop();
		var cha = parseInt(top) - parseInt(scrolla);
		if (loaded && cha > 530) {
			$(".fix-bar-wrap").addClass("fix-bar-current");
			loaded = false;
		}
		if (!loaded && cha <= 520) {
			$(".fix-bar-wrap").removeClass("fix-bar-current");
			loaded = true;
		}
	} */
	
	var floatBar=$(".fix-bar-wrap");
	var _pos=floatBar.position();
	var m=$(window).height();
	$(window).scroll(function(){
	var scrollTop=$(document).scrollTop();
	if(m+scrollTop>_pos.top){
	floatBar.removeClass("fix-bar-current");
	}else{
	floatBar.addClass("fix-bar-current");
	}}
	).scroll();
});
	//合并订单搜索数据
	function joinOrder(){
	 	var Ids = '';
	 	var totalMoney = $(".zhifu b").html();
		$("input[name='ck']:checkbox:checked").each(function(){ 
			 Ids+=$(this).val()+',';
		});  
		if(Ids!=''){
			 Ids=Ids.substring(0, Ids.length-1);
	    }
 		$.ajax({ 
 	 		async:false,
            type : "post", 
            url : '<%=path%>/buyOrder/joinOrder', 
            data:{"ids":Ids,"totalMoney":totalMoney},
            success : function(json) {
            	tipMessage(json,function(){
            		window.location.href="<%=path %>/waitOrder/SumitOrder";
            	});
			},
			error:function(jqXHR,textStatus,errorThrown){
		   		alert("网络异常,请稍后再试。。。。");
		 	}
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
<%@include file="/WEB-INF/views/include/header.jsp"%>
	<input type="hidden" value="${isRangeList }" id="istidu">
	<input type="hidden" value="${discount }" id="istiduprice" >
	<div class="center">
		<div class="logo3">
			<p class="p1">您的位置： 买家中心><a href="<%=path %>/waitOrder/SumitOrder">待合并订单></a><span>订单详情</span></p>
		</div>
		<div class="zong">
			<!-- 订单详情开始 -->
			<div class="c3">
				<!-- 最小起订量 -->
				<input type="hidden" value="${micQty}" id="minqty">
				<c:forEach var="order" items="${orderlist }">
					<input type="hidden" value="${order.pid }" id="pid">
						<div class="two">
						<div class="two1">
						
						<c:forEach items="${order.itemlist }" var="detail">
						<div class="skuId" style="display: none;">
							${detail.skuId }
						</div>
						<div class="skuQty" style="display: none;">${detail.skuQty }
						</div>		
					</c:forEach>
							<div class="ff">
							
							<c:if test="${!empty buttonsMap['待合并订单-选择订单'] }">
								<span class="f-input">
									<input type="checkbox" name="ck" value="${order.id}"class="check">
								</span>					
			 				</c:if>
								<span class="f1"><fmt:formatDate value="${order.createTime}"  type="both"/></span>
								<span class="f2">订单号：${order.orderId}</span>
								<span class="f3">价格</span>
								<span class="f6">数量</span>
								<span class="f8">预付款比率</span>
								<span class="f7">实付款(元)</span>
								<span class="f4">状态</span>
								<span class="f5">操作</span>
							</div>
							<dl>
								<dt><img src="${order.imgUrl }" style="width:80px; height:80px; margin-left:10px;"></dt>
								<dd>${fn:escapeXml(order.pName)}</dd>
							</dl>
							<div class="blink"></div>
							<p>
								<span class="s1">
									<c:choose>
										<c:when test="${order.productPriceMin==order.productPriceMax }">
											<fmt:formatNumber value="${order.productPriceMin }" pattern="0.00#"/>
										</c:when>
										<c:otherwise>
											<fmt:formatNumber value="${order.productPriceMin }" pattern="0.00#"/>~
											<fmt:formatNumber value="${order.productPriceMax }" pattern="0.00#"/>
										</c:otherwise>
									</c:choose>
								</span>
								<span class="s2">${order.qty}</span>
								
								
								<span class="s6">${fn:escapeXml(dto.retaiPrePayPercent)}%</span>
								
								
								<span class="s3" id="paidPrice">
									<fmt:formatNumber value="${order.paidPrice-order.realTransferprice}" pattern="0.00#"/>
									<%-- <fmt:formatNumber value="${order.paidPrice}" pattern="0.00#"/> --%>
								</span>
								<span class="s4">
									<c:if test="${order.status==1 }">等待支付</c:if>
									<c:if test="${order.status==21 }">已付首款</c:if>
									<c:if test="${order.status==31 }">订单已经确认</c:if>
									<c:if test="${order.status==32 }">订单已经确认</c:if>
									<c:if test="${order.status==33 }">订单已经确认</c:if>
									<c:if test="${order.status==34 }">订单已经确认</c:if>
									<c:if test="${order.status==41 }">等待供应商发货</c:if>
									<c:if test="${order.status==51 }">供应商发货已发货</c:if>
									<c:if test="${order.status==61 }">等待交付余款</c:if>
									<c:if test="${order.status==71 }">待发货</c:if>
									<c:if test="${order.status==81 }">已发货</c:if>
									<c:if test="${order.status==91 }">订单完成</c:if>
									<c:if test="${order.status==101 }">系统默认完成</c:if>	
									<c:if test="${order.status==100 }">系统取消</c:if>
							   </span>
							   <c:if test="${!empty buttonsMap['待合并订单-零售商订单详情'] }">
									<span class="s5"><input type="button" value="订单详情" class="ddxq" onclick="xiangqing('${order.orderId}')" /></span>
								</c:if>
							   <input type="hidden" value="${order.totalPrice}" id="skutotalMoney">
							</p>
						</div>
						<div class="blink"></div>
						
					</div>
					
				</c:forEach>
				<%@include file="/WEB-INF/views/dealerbuy/basePage/reOrderDetail.jsp"%>
		<div class="fix-bar-wrap">	
		<div id="viewmsg"></div>	
		<div class="fix-bar">			
			<div class="f-input">
			 <c:if test="${!empty buttonsMap['待合并订单-选择订单'] }">
				<input type="checkbox"  value="" id = "quanxuan"><label for="quanxuan">全选</label>					
			 </c:if>
			</div>
			<div class="fix-bar-table">
			<c:if test="${isRangeList==1 }">
			<div class="fix-table">
				<div class="fix-hd">
					<div class="th">梯度数量</div>
					<div class="th">价格(${moneyUnitSymbols.moneyUnitNameCn })</div>
					<div class="th">购买数量</div>
				</div>
				<div class="fix-bd">
				<c:forEach items="${wholesaleRanges }" var="wholesaleRanges">
					<div class="tr">
						<div class="td">
							<c:choose>
								<c:when test="${wholesaleRanges.endQty==0 }">
									&ge;${wholesaleRanges.startQty }
								</c:when>
							
								<c:otherwise>
								${wholesaleRanges.startQty }-${wholesaleRanges.endQty }
								</c:otherwise>
							</c:choose>
						</div>
						<div class="td">${moneyUnitSymbols.moneyUnitSymbols }  <fmt:formatNumber value="${wholesaleRanges.discount }" pattern="0.0000#"/></div>
					</div>
				</c:forEach>
					<div class="td onlyone">
						<div class="totalNum">
						   <span><b></b></span>
						</div>
					</div>
				</div>
			</div>
			</c:if>
			<c:if test="${isRangeList!=1 }">
				<input type="hidden" value="${skuList}" id="skuList">
					<table id="skuPrice">
						<tr>
						<th>sku编号</th>
						<th>规格</th>
						<th>价格(${moneyUnitSymbols.moneyUnitNameCn })</th>
						<th>购买数量</th>
						<th>金额</th>
						</tr>
					</table>
					<div id="skuNameCn"></div>
			</c:if>
			</div>
			 <div class="zhifu">
			   <span>采购金额: ${moneyUnitSymbols.moneyUnitSymbols } <b></b></span>
			    <input type="button" value="合并提交订单" class="sm"  onclick="joinOrder()"/>
			 </div>
		</div>
			
		</div>
			</div>
			
	</div>
</div>
	 <div class="blank10"></div>
	<!-- 底部 start -->
	<%@include file="/WEB-INF/views/include/foot.jsp"%>
	 <div class="blank10"></div>
	<!-- 底部 end -->
	<script>
		   //显示零售商的订单详情(sku/收货地址)
		   function xiangqing(orderId){
		    $(".alert_xqing").show();
		 	 $.ajax({ 
		 	 		  async:false,
		              type: "post", 
		              url: '<%=path %>/buyOrder/getRetailerOrderDetail', 
		              data:{"orderId":orderId},
		              dataType:"html",
		              success : function(addressJson) { 
			              $("#orderXq").html(addressJson);
			              $(".alert_xqing").show();		
					  },
					  error: function(jqXHR, textStatus, errorThrown){
						alert("查询失败 ，请稍后再试。。");
				 	  },
		 		});
		 	}
		 	
	</script>
</body>
</html>
