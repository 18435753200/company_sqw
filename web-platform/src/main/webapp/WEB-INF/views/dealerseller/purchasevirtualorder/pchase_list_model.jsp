<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>

 <div class="pu_wrap">
		  		
		  		<div class="pu_bd">
			  		<table>
			  			<tr class="order_hd">
			  				<th width="115px">虚拟设置编号</th>
			  				<th width="95px">供应商名称</th>
			  				<th width="80px">操作时间</th>
			  				<th width="80px">操作人</th>
			  				<th width="70px">操作</th>
			  			</tr>
			  			<c:forEach items="${pb.result }" var="pChaseOrderVO" varStatus="index">
			  				<tr>
			  				
<%-- 			  				<td>${index.index+1 }</td> --%>
<!-- 			  				<td> -->
<%-- 			  				<c:choose> --%>
<%-- 			  				<c:when test="${pChaseOrderVO.orderStatus==15 && pChaseOrderVO.purchaseFee==1 && pChaseOrderVO.inlandFreight==1}"> --%>
<%-- 			  				<input type="checkbox" value="${pChaseOrderVO.id}" name="orderlist"> --%>
<%-- 			  				</c:when> --%>
<%-- 			  				<c:otherwise> --%>
<%-- 			  				<input type="checkbox" value="${pChaseOrderVO.id}" name="orderlist" disabled="disabled"> --%>
<%-- 			  				</c:otherwise> --%>
<%-- 			  				</c:choose> --%>
<!-- 			  				</td> -->
			  				<td><a href="<%=path%>/pchaseVirtualOrder/findVitrualOrderDetail?id=${pChaseOrderVO.id}" target="view_window">${pChaseOrderVO.businessNumberChar}</a></td>
			  				<td>${pChaseOrderVO.supplierName}</td>
			  				<td><fmt:formatDate value="${pChaseOrderVO.createTime}"   pattern="yyyy-MM-dd hh:mm:ss"/></td>
			  				<td>${pChaseOrderVO.createBy}</td>
			  				<td>
								<a href="<%=path%>/pchaseVirtualOrder/showOrder?id=${pChaseOrderVO.id}&type=2">查看</a>
			  				</td>
			  			</tr>
			  			</c:forEach>
			  			
			  		</table>
			  		<c:if test="${!empty pb.result}">
					<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
					</c:if>
		  		</div>
		  </div>
