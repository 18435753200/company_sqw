<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>UNICORN-装箱详情</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/B2C_packingdetails.css"/>
</head>
<body style="background: #f0f1f3;">
<%@include file="/WEB-INF/views/include/header.jsp"%>
<div class="center">
	<div class="logo3">
		<p class="p1">您的位置： 系统管理&gt;装箱列表&gt;<span>装箱单详情</span></p>
	</div>
	<div class="order-top">
		<p class="or-l">装箱单号：${loadPlanDTO.id } &nbsp;&nbsp;&nbsp;
		状态:<span>
			<c:if test="${loadPlanDTO.status eq 0 }">创建</c:if>
			<c:if test="${loadPlanDTO.status eq 1 }">发运</c:if>
		</span></p>
	</div>
	<div class="order-item">
        <div class="order-hd">
            <h2>装箱单信息</h2>
        </div>
        <div class="order-bd">
            <p>操  作  人：${loadPlanDTO.createBy}</p>
            <p>总  数  量：${loadPlanDTO.totalQty }</p>
            <p>总  体  积：<fmt:formatNumber value="${loadPlanDTO.totalWeight }" pattern="0.00#"/> cm3</p>
            <p>总  重  量：${loadPlanDTO.totalVolume } kg</p>
            <p>装箱时间：<fmt:formatDate type="both" value="${loadPlanDTO.createTime }"/></p>
            <c:choose> 
			  <c:when test="${empty loadPlanDTO.loadCode }">   
			  	<p>暂无提单 </p>
			  </c:when> 
			  <c:otherwise>   
			   	<p>提单编号：${loadPlanDTO.loadCode }</p>
			  </c:otherwise> 
			</c:choose> 
        </div>
    </div>

	<div class="goodsWrap">
	   <table class="goodsTable">
	        <thead>
	            <tr class="goods-tr">
	                <th width="300">商品</th>
	                <th width="200">规格</th>
	                <th width="198">数量</th>
	                <th width="150">重量kg</th>
	                <th width="150">体积(cm3)</th>
	            </tr>
	        </thead>
	        <tbody>
	        	<tr class="f-h10">
                    <td colspan="5"></td>
                </tr>
                <c:forEach items="${loadPlanDTO.proList }" var="proList">
	            <tr class="goods-tr">
	            	
	                <td class="f-tl" colspan="3">
	                	<table class="skuTable">
	                		<colgroup>
								<col width="300">
								<col width="200">
								<col width="198">
							</colgroup>
							<c:forEach items="${proList.skuList }" var="skuList">
								<tr>
									<td>
										<ul class="goods-list clearfix">	
					                        <li>
					                           <div class="goods-box">
					                              <div class="goods-pic">
					                                  <a href="#">
					                                  <img src="${skuList.imgUrl }" width="60" height="60">
					                                  </a>
					                               </div>
					                              <div class="goods-txt">
					                                ${proList.pName }
					                              </div>
					                           </div>
					                        </li>
						                </ul>
									</td>
									<td>${skuList.skuNameCn }</td>
									<td>${skuList.qty }</td>
								</tr>
			 				</c:forEach>
	                	</table>
                    </td>
                    
	                <td class="bo"><fmt:formatNumber value="${proList.weight }" pattern="0.00#"/></td>
	                <td><fmt:formatNumber value="${proList.volume }" pattern="0.00#"/></td>
	               
	            </tr>
	          </c:forEach>  
	        </tbody>
	    </table>
	
	    <table class="goodsTable">
	    	 <thead>
	            <tr class="goods-tr">
	                <th width="300">商品</th>
	                <th width="200">规格</th>
	                <th width="198">数量</th>
	                <th width="150">重量(kg)</th>
	                <th width="150">体积(cm3)</th>
	            </tr>
	            <tr class="sep-row">
						<td colspan="5"></td>
				</tr>
	           
				<c:forEach items="${loadPlanDTO.orderIn }" var="orderIn">
			        <c:forEach items="${orderIn.orderItemDTOs }" var="orderItemDTO">
			        
					    <tr class="goods-hd">
			            	<td  colspan="5">
			            	        <span class="fl">下单时间:2015-4-8 14:01:45</span>
			            	        <span class="fl">订单号:${orderItemDTO.orderId}</span>
			            	        <span class="fr"></span>
			            	</td>
			            </tr>
				        <tr class="goods-tr">
							<td>
								<ul class="goods-list clearfix">	
			                        <li>
			                           <div class="goods-box">
			                              <div class="goods-pic">
			                                  <a href="#">
			                                  <img src="${orderItemDTO.imgUrl}" width="60" height="60">
			                                  </a>
			                               </div>
			                              <div class="goods-txt">
			                               ${orderItemDTO.pName}
			                              </div>
			                           </div>
			                         </li>
					             </ul>
							</td>
							<td>${orderItemDTO.skuNameCn}</td>
							<td>${orderItemDTO.skuQty}</td>
							<td><fmt:formatNumber value="${orderItemDTO.weight}" pattern="0.00#"/></td>
							<td><fmt:formatNumber value="${orderItemDTO.volume}" pattern="0.00#"/></td>
						</tr>
						<tr class="sep-row">
					     	<td colspan="5"></td>
				        </tr>
			        </c:forEach>
				</c:forEach>
	        </thead>
	    </table>
	</div>
</div>
<!-- 底部 start -->
<%@include file="/WEB-INF/views/include/foot.jsp"%>
<!-- 底部 end -->
</body>
</html>
