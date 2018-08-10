<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>

 <div class="pu_wrap">
		  		
		  		<div class="pu_bd">
			  		<table>
			  			<tr class="order_hd">
			  			    <th width="40px">序号</th>
			  				<th width="40px">选择</th>
			  				<th width="115px">采购订单号</th>
			  				<th width="95px">供应商名称</th>
			  				<th width="80px">金额</th>
			  				<th width="80px">紧急程度</th>
			  				<th width="80px">订单日期</th>
			  				<th width="75px">采购费用</th>
			  				<th width="75px">国内运费</th>
			  				<th width="80px">订单状态</th>
			  				<th width="80px">制单人</th>
			  				<th width="70px">操作</th>
			  			</tr>
			  			<c:forEach items="${pb.result }" var="pChaseOrderVO" varStatus="index">
			  				<tr>
			  				
			  				<td>${index.index+1 }</td>
			  				<td>
			  				<c:choose>
			  				<c:when test="${pChaseOrderVO.orderStatus==15 && pChaseOrderVO.purchaseFee==1 && pChaseOrderVO.inlandFreight==1}">
			  				<input type="checkbox" value="${pChaseOrderVO.id}" name="orderlist">
			  				</c:when>
			  				<c:otherwise>
			  				<input type="checkbox" value="${pChaseOrderVO.id}" name="orderlist" disabled="disabled">
			  				</c:otherwise>
			  				</c:choose>
			  				</td>
			  				<td><a href="<%=path%>/pchaseOrder/findOrderDetail?id=${pChaseOrderVO.id}" target="view_window">${pChaseOrderVO.businessNumberChar}</a></td>
			  				<td>${pChaseOrderVO.supplierName}</td>
			  				<td>${pChaseOrderVO.itemTotalPrice }</td>
			  				<td><c:forEach items="${degreeOfEmergency}" var="Emergency">
			  				<c:if test="${Emergency.dictValue==pChaseOrderVO.emergency}">${Emergency.dictName }</c:if>
			  				</c:forEach>
			  				</td>
			  				<td><fmt:formatDate value="${pChaseOrderVO.createTime}"   pattern="yyyy-MM-dd"/></td>
			  				<td>
			  				<c:if test="${pChaseOrderVO.purchaseFee!=1}">
			  				<img src="<%=path%>/commons/images/flag_red.png" style="width:25px; height:25px;">
			  				</c:if>
			  				<c:if test="${pChaseOrderVO.purchaseFee==1}">
			  				<a href="<%=path%>/pchaseOrder/showFeeList?id=${pChaseOrderVO.id}" target="view_window">
			  				<img src="<%=path%>/commons/images/flag_yellow.png" style="width:25px; height:25px;">
			  				</a>
			  				</c:if>
			  				</td>
			  				<td>
			  				<c:if test="${pChaseOrderVO.inlandFreight!=1}">
			  				<img src="<%=path%>/commons/images/flag_red.png" style="width:25px; height:25px;">
			  				</c:if>
			  				<c:if test="${pChaseOrderVO.inlandFreight==1}">
			  				<a href="<%=path%>/pchaseOrder/showInLandFee?id=${pChaseOrderVO.id}" target="view_window">
			  				<img src="<%=path%>/commons/images/flag_yellow.png" style="width:25px; height:25px;">
			  				</a>
			  				</c:if>
			  				</td>
			  				<td>
			  				<c:if test="${pChaseOrderVO.orderStatus==1}">
			  				未审核
			  				</c:if>
			  				<c:if test="${pChaseOrderVO.orderStatus==10}">
			  				已审核
			  				</c:if>
			  				<c:if test="${pChaseOrderVO.orderStatus==15}">
			  				已到齐
			  				</c:if>
			  				<c:if test="${pChaseOrderVO.orderStatus==20}">
			  				已完成
			  				</c:if>
			  				
			  				</td>
			  				<td>${pChaseOrderVO.createBy}</td>
			  				<td>
							<c:if test="${pChaseOrderVO.orderStatus==1}">
							<a href="<%=path%>/pchaseOrder/showOrder?id=${pChaseOrderVO.id}&type=1">编辑</a>
							</c:if>
							<c:if test="${pChaseOrderVO.orderStatus!=1}">
							<a href="<%=path%>/pchaseOrder/showOrder?id=${pChaseOrderVO.id}&type=2">查看</a>
							</c:if>
			  				</td>
			  			</tr>
			  			</c:forEach>
			  			
			  		</table>
			  		<c:if test="${!empty pb.result}">
					<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
					</c:if>
		  		</div>
		  </div>
