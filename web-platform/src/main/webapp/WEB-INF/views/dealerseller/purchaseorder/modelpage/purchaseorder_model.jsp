<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<table class="pu cgd-table">

	<tr class="order-hd">
		<th>采购单单号</th>
		<th>供应商</th>
		<th>仓库</th>
		<th>商品总数</th>
		<th>制单时间</th>
		<th>状态</th>
		<th>采购单费用</th>
		<th>操作</th>
	</tr>
	
	<c:forEach items="${pb.result }" var="pChaseOrderDTO">
	
		<tr class="order-bd">
			
				<td><span>${pChaseOrderDTO.id}</span></td>
				<td><span>${pChaseOrderDTO.supplierName}</span></td>
				<td><span>${pChaseOrderDTO.warehouseName}</span></td>
				<td><span>${pChaseOrderDTO.qty}</span></td>
				<td><span><fmt:formatDate value="${pChaseOrderDTO.createTime}" pattern="yyyy-MM-dd HH:mm"/></span></td>
				<td><span><c:if test="${pChaseOrderDTO.status==1}">创建</c:if><c:if test="${pChaseOrderDTO.status!=1}">确认</c:if></span></td>
				<td><span>${pChaseOrderDTO.totalPrice}</span></td>
				<td>
					<c:if test="${! empty buttonsMap['采购单管理-查看采购单'] }">	
					 	<a href="${path }/purchaseorder/addpurchaseorder?target=3&shipOrderId=${pChaseOrderDTO.id}" target="_blank">查看</a>
					</c:if>
					<c:if test="${! empty buttonsMap['采购单管理-修改采购单'] }">	
							 <a href="${path }/purchaseorder/addpurchaseorder?target=2&shipOrderId=${pChaseOrderDTO.id}" target="_blank">修改</a> 
					</c:if>
					<c:if test="${pChaseOrderDTO.status==1}">
						<c:if test="${! empty buttonsMap['采购单管理-删除采购单'] }">	
							 <a href="javascript:void(0)" onclick="delorder('${pChaseOrderDTO.id}')">删除</a>
						</c:if>
						<c:if test="${! empty buttonsMap['采购单管理-确认采购单'] }">		
							 <a href="javascript:void(0)" class="shenghe" onclick="confimorder('${pChaseOrderDTO.id}')">确认</a>
						</c:if>
					</c:if>
				 
				 </td>
					
			</tr>
		</c:forEach>
</table>
<c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
</c:if>