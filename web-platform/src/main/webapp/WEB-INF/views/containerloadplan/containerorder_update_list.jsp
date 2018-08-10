<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>装箱列表_修改</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/B2C_packingupdate.css"/>
	<script src="${path}/commons/js/loadplan/loadplan_update.js"></script>
	<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
</head>
<body style="background: #f0f1f3;">
<%@include file="/WEB-INF/views/include/header.jsp"%>
<div class="center">
   <div class="logo3">
		<p class="p1">您的位置： 系统管理&gt;装箱管理&gt;<span>修改装箱</span></p>
	</div>
		<div class="addw">
			<div class="add_title">
				<p>装  箱  单：${loadPlanDTO.id }</p>
				<p>操  作  人：${loadPlanDTO.createBy }</p>
				<p>操作时间：<fmt:formatDate value="${loadPlanDTO.createTime }" type="both"/></p>
				 <c:choose> 
				  <c:when test="${empty loadPlanDTO.loadCode }">   
				  	<p>暂无提单 </p>
				  </c:when> 
				  <c:otherwise>   
				   	<p>提单编号：${loadPlanDTO.loadCode }</p>
				  </c:otherwise> 
				</c:choose>
			</div>
			<div class="add_hd">
		      	温馨提示:请购选如下订单进行修改装箱单
			</div>
		
			<div class="goodsWrap">
				<table class="goodsTable">
					<tr class="goods-tr">
						<th width="100"><input type="checkbox" id="quanxuan" class="quanxuan" value="">全选</th>
						<th width="300">待装箱订单</th>
						<th colspan="2">
						    <span class="rl">装箱商品 </span> <span class="rr">装箱数量</span>
						</th>
					</tr>
					<tr class="goods-tr h" >
						<td  colspan="2"  valign="top">
							<div class="aul">
							<input type="hidden" value="${loadPlanDTO.id }" id="planId">
								<table class="orderTab">
									<colgroup>
	                                    <col width="100">
	                                    <col width="300">
	                                </colgroup>
	                                <c:forEach items="${loadPlanDTO.orderIn }" var="orderIn">
		                                <c:forEach items="${orderIn.orderItemDTOs }" var="orderItemDTO">
		                                	<tr class="orderTr">
			                                	<td colspan="1" id="bo">
			                                		<input type="checkbox" name="ck" value="${orderItemDTO.orderId}" class="check" checked="checked">
			                                		<input type="hidden" name="" value="${orderItemDTO.skuId }" class="skuId">
				                                	<input type="hidden" name="" value="${orderItemDTO.skuNameCn }" class="skuNameCn">
				                                	<input type="hidden" name="" value="${orderItemDTO.skuQty }" class="skuQty">
				                                	<input type="hidden" name="" value="${orderItemDTO.pName }" class="pName">
				                                	<input type="hidden" name="" value="${orderItemDTO.pid }" class="pid">
				                                	<input type="hidden" name="" value="${orderItemDTO.weight }" class="weight">
			                                		<input type="hidden" name="" value="${orderItemDTO.volume }" class="volume">
			                                		<input type="hidden" name="" value="${orderItemDTO.imgUrl }" class="imgUrl">
			                                		<input type="hidden" name="" value='${orders.checkTime }' class="createTime">	
			                                	 </td>
			                                	<td colspan="1" id="bor">
			                                		${orderItemDTO.orderId}
			                                	</td>
		                                	</tr>
		                                </c:forEach>
	                                </c:forEach>
	                                
	                                <c:forEach items="${pageBean.result }" var="orders">
		                                <tr>
		                                	<td colspan="1" id="bo">
		                                	<input type="checkbox" name="ck" value="${orders.id}" class="check">
		                                	<c:forEach items="${orders.orderItemDTOs }" var="orderItemDTO">
		                                		<input type="hidden" name="" value="${orderItemDTO.skuId }" class="skuId">
			                                	<input type="hidden" name="" value="${orderItemDTO.skuNameCn }" class="skuNameCn">
			                                	<input type="hidden" name="" value="${orderItemDTO.skuQty }" class="skuQty">
			                                	<input type="hidden" name="" value="${orderItemDTO.pName }" class="pName">
			                                	<input type="hidden" name="" value="${orderItemDTO.pid }" class="pid">
			                                	<input type="hidden" name="" value="${orderItemDTO.weight }" class="weight">
			                                	<input type="hidden" name="" value="${orderItemDTO.volume }" class="volume">
			                                	<input type="hidden" name="" value="${orderItemDTO.imgUrl }" class="imgUrl">
			                                	<input type="hidden" name="" value='${orders.checkTime }' class="createTime">	
		                                	</c:forEach>
		                                	</td>
		                                	<td colspan="1" id="bor">
		                                		${orders.id}
		                                	</td>
		                                </tr>
	                                </c:forEach>
								</table>
						    </div>
						</td>


						<td  colspan="2"  valign="top">

							<div class="aur">
								<table id="productTab">
									<colgroup>
	                                    <col width="395">
	                                    <col width="200">
	                                </colgroup>
	                                 <c:forEach items="${loadPlanDTO.proList }" var="proList">
	                                 <c:forEach items="${proList.skuList }" var="skuList">
		                                <tr class='productTr'>
										<td colspan="1" id="bo" >
											<ul class="goods-list clearfix">
						                        <li>
						                            <div class="goods-box">
						                            <input type="hidden" name="" value="${skuList.skuId }" id="skuId"  class="skuId">
						                                <div class="goods-pic">
						                                <a href="#">
						                                <img src="${skuList.imgUrl }" width="60" height="60">
						                                </a>
						                                </div>
						                                <div class="goods-txt">
							                               ${proList.pName } ${skuList.skuNameCn }
						                                </div>
						                            </div>
						                        </li>
						                    </ul>
										</td>
										<td colspan="1" id="bor"> <span id="skuQty">${skuList.qty }</span> </td>
									  </tr>
		                            </c:forEach>
	                                </c:forEach>
								</table>
							 </div>
						</td>
					</tr>
				
					<tr class="goods-tr"> 
						<td  colspan="2" style="height:40px;">
							<div class="showMore">
						    	<a href="#" onclick="goShowMoreOrderList()">
						        	<span class="hide"> 显示更多品牌</span>
						        </a>
						    </div>
						    
						 <!--    <div class="article">
									<p>已显示<span>15条</span>未显示<span>10条</span></p>
							</div> -->
						    
						</td>
						<td colspan="2" style="height:40px;" class="zong"> 总计：
						数量：<span id="totalCount">${loadPlanDTO.totalQty }</span> &nbsp;&nbsp;
						重量：<span id="totalWeghit">${loadPlanDTO.totalWeight }</span>kg &nbsp;&nbsp;
						体积：<span id="totalvolume">${loadPlanDTO.totalVolume }</span>cm3</td>
					</tr>
				</table>
			</div>
			<div class="ck-hd">
				<span class="add-ck-btn">
			    	<a href="#" onclick="goUpdateLoadPlanByPlanId()">确认修改</a>
				</span>
			</div>
			
		</div>
	</div>
<!-- 底部 start -->
<%@include file="/WEB-INF/views/include/foot.jsp"%>
<!-- 底部 end -->
</body>
</html>