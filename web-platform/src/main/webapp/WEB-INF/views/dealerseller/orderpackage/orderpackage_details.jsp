<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>UNICORN-包裹查询-包裹详情</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/fahuodan.css"/>

    <script type="text/javascript">
    	$(document).ready(function(){
    		var purOrderStatus = $("#purOrderStatus").attr("nowstatus");
    		/* 
    		1.待发货
    		2.已发货
    		3.已完成
    		9.已取消
    		*/
    		if(purOrderStatus==1){
    	    	$("#p2 img").attr("src","<%=path %>/commons/images/spotStatus1.png");
    	    	$("#purOrderStatus").html("待发货");
    	    }else if(purOrderStatus==2){
    	    	$("#p2 img").attr("src","<%=path %>/commons/images/spotStatus2.png");
    	    	$("#purOrderStatus").html("已发货");
    	    }else if(purOrderStatus==3){
    	    	$("#p2 img").attr("src","<%=path %>/commons/images/spotStatus3.png");
    	    	$("#purOrderStatus").html("已完成");
    	    }else if(purOrderStatus==9){
    	    $("#p2 img").attr("src","<%=path %>/commons/images/spotStatus3.png");
    	    	$("#purOrderStatus").html("已取消");
    	    }
    	    else{
    	    }
    	});
    </script>
</head>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="center">
		<div class="right">
			<!--logo开始-->
			<div class="logo3">
				<p class="p1">您的位置： 订单管理&gt;<a href="#">包裹查询</a>&gt;<span>包裹详情</span></p>
				<p class="p2" id="p2"><img src=""></p>
			</div>
			<!--logo结束-->
			<!--订单状态开始-->
			<div class="ding">
				<div class="ding1">
					包裹号：${fn:escapeXml(shipOrderDetails.id)} &nbsp;&nbsp;
					状态：<span id="purOrderStatus" nowstatus="${fn:escapeXml(shipOrderDetails.status)}"></span>
				</div>
			</div>
			<!--订单状态结束-->
		<div class="xing">
			<div class="xing1"><p>包裹信息</p></div>
			<div class="xing2">
				<dl>
					<dt>收货人信息</dt>
					<dd>
						<ul>
							<li>收&nbsp;货&nbsp;人：${fn:escapeXml(shipOrderDetails.receiveName)}</li>
							<li>地&nbsp;&nbsp;&nbsp;&nbsp;址：${fn:escapeXml(shipOrderDetails.receiveAddress)}</li>
							<li>手机号码：${fn:escapeXml(shipOrderDetails.receiveMobilePhone)}</li>
							<li>固定电话：${fn:escapeXml(shipOrderDetails.receivePhone)}</li>
						</ul>
				</dl>
			</div>
			<div class="xing3">
				<table>
					<tr class="t1">
						<td>买家信息</td>
						<td></td>
						<td></td>
					</tr>
					<c:choose>
					<c:when test="${shipOrderDetails.orderType eq 2}">
						<tr>
							<td>用户昵称：${fn:escapeXml(user.userName)}</td>
							<td>真实姓名：${fn:escapeXml(user.realName)}</td>
						</tr>
						<tr>
							<td>电子邮箱：${fn:escapeXml(user.email)}</td>
							<td>联系电话：${fn:escapeXml(user.mobile)}</td>
						</tr>
						<tr>
							<td>用户地址：${fn:escapeXml(user.address)}</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<td>商家名称：${fn:escapeXml(retailer.name)}</td>
							<td>移动电话：${fn:escapeXml(retailer.mobile)}</td>
						</tr>
						<tr>
							<td>电子邮箱：${fn:escapeXml(retailer.email)}</td>
							<td>详细地址：${fn:escapeXml(retailer.address)}</td>
						</tr>
						<tr>
							<td>管理员名称：${fn:escapeXml(retailerUser.name)}</td>
						</tr>
					</c:otherwise>
					</c:choose>
			</table>
		</div>
		<div class="xing3">
				<table>
					<tr class="t1">
						<td>包裹信息</td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>包裹号：${fn:escapeXml(shipOrderDetails.id)}</td>
						<td>成交时间：<fmt:formatDate value="${shipOrderDetails.createTime }" pattern="yyyy年MM月dd日HH点mm分"/></td>
					</tr>
					<tr>
						<td>仓库名：${fn:escapeXml(shipOrderDetails.warehouseName)}</td>
					</tr>
				<c:if test="${shipOrderDetails.logisticsCarriersName!=null &&shipOrderDetails.logisticsCarriersId!=null &&shipOrderDetails.logisticsNumber !=null}">
					<tr>
						<td>物流商：${fn:escapeXml(shipOrderDetails.logisticsCarriersName)}</td>
						<td>物流费用：<fmt:formatNumber pattern="0.00#">${fn:escapeXml(shipOrderDetails.logisticsFee)}</fmt:formatNumber>元</td>
					</tr>
					<tr>
						<td>物流单号：${fn:escapeXml(shipOrderDetails.logisticsNumber)}</td>
					</tr>
				</c:if>
					
				</table>
			</div>
			<!--商品清单开始-->
			<div class="xing4">
				<div class="goodsWrap">
				    <table class="goodsTable">
				        <thead>
				            <tr class="goods-tr">
				                <th width="230">商品</th>
				                <th width="130">条码</th>
				                <th width="130">规格</th>
				                <th width="130">单价(元)</th>
				                <th width="130">数量</th>
				                <th width="130">金额(元)</th>
				                <th width="130">状态</th>
				            </tr>
				        </thead>
							<c:forEach items="${shipOrderDetails.shipOrderItemMap }" var="itemMap" >
							<c:set value="0" var="totalPrice"></c:set>
							        <tbody>
							            <tr class="f-h10">
							                <td colspan="6"></td>
							            </tr>
							            <tr class="goods-tr">
							                <td colspan="5"><table class="skuTable">
							                        <colgroup>
							                        <col width="230">
							                        <col width="130">
							                        <col width="130">
							                        <col>
							                        </colgroup>
							                        <c:forEach items="${itemMap.value }" var="details" varStatus="step">
							                     	<c:set var="totalPrice" value="${totalPrice+details.pirce*details.qty}"/>
							                     	<c:set var="sum" value="${sum+details.pirce*details.qty}"/>
								                        <tr>
								                            <td class="f-tl"><div class="p-goods">
								                                    <div class="p-img"><a href="#"><img src="${details.productUrl }"  width="60" height="60" ></a></div>
								                                    <div class="p-name" title="${details.pName }"><a href="#">${details.pName }</a> </div>
								                                </div>
								                             </td>
								                             <td><span>${details.skuCode }</span></td>
								                            <td><span>${details.skuName }<font style="color: red"><c:if test="${details.productType eq 1 }">(赠品)</c:if></font></span></td>
								                            <td><span><fmt:formatNumber value="${details.pirce }" pattern="0.00#"></fmt:formatNumber></span></td>
								                            <td><span>${details.qty }</span></td>
								                            
								                        </tr>
							                        </c:forEach> 
							                    </table>
							                <td>
								                <strong>
								                	<fmt:formatNumber value="${totalPrice }" pattern="0.00#"></fmt:formatNumber>
								                </strong>
							                </td>
							                <td>
								                <c:if test="${shipOrderDetails.status eq 1 }">待发货</c:if>
												<c:if test="${shipOrderDetails.status eq 2 }">已发货</c:if>
												<c:if test="${shipOrderDetails.status eq 3 }">已完成</c:if>
												<c:if test="${shipOrderDetails.status eq 9 }">已取消</c:if>
							                  </td>
							            </tr>
							        </tbody>
							 </c:forEach>
				    </table>
				</div>
			</div>
			<div class="xing5">
				<p>合计：<span><fmt:formatNumber value="${sum}" pattern="0.00#"/></span>元</p>
			</div>
		</div>
	<!--商品清单结束-->
	</div>
</div>

   <div class="blank10"></div>
	 <!-- 底部 start -->
	<div class="t_footer">
			<h1 class="logo_dl"></h1>
	</div>
	<!-- 底部 end -->
</body>
</html>