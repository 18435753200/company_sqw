<%@page import="java.math.BigDecimal"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
		  <!-- <div class="pu_wrap" id="modelPurchaseOrderItemList"> -->
		  		<div class="pu_bd">
		  			<table  id="orderItem">
		  				<tr class="order_hd">
		  					<th width="40px;">序号</th>
		  					<th width="50px;">选择</th>
		  					<th width="110px;">商品编码</th>
		  					<th width="110px;">商品条码</th>
		  					<th width="120px;">商品名称</th>
		  					<th width="100px;">规格</th>
		  					<th width="90px;">单位</th>
		  					<th width="100px;">订单数量</th>
		  					<!-- <th width="70px;">已通知数量</th> -->
		  					<th width="110px;">本次通知送货数量</th>
		  					
		  				</tr>
		  				<c:forEach items="${pb.result }" var="pChaseOrderItemDTO" varStatus="status">
		  					<tr class="sot">
			  					<td>${status.index + 1 }</td>
				  				<td><input style="width:40px" type="checkbox" id="purchaseOrderItemId" name="purchaseOrderItemId" value="${pChaseOrderItemDTO.id }" onclick="updatePurchaseOrderItem(this)"></td>
				  				<td><input style="width:50px;border:0px" type="text" id="pcode" name="pcode" value="${pChaseOrderItemDTO.businessProdid }"  readonly="readonly"/> </td>
				  				<td><input style="width:110px;border:0px" type="text" id="barCode" name="barCode" value="${pChaseOrderItemDTO.barCode }" readonly="readonly"/> </td>
				  				<td><input style="width:120px;border:0px" type="text" id="pname" name="pname" value="${pChaseOrderItemDTO.pname }"  readonly="readonly"/> </td>
				  				<td><input style="width:100px;border:0px" type="text" id="format" name="format" value="${pChaseOrderItemDTO.format }"  readonly="readonly"/> </td>
				  				<td><input style="width:90px;border:0px" type="text" id="unit" name="unit" value="${pChaseOrderItemDTO.unit }" readonly="readonly" /> </td>
				  				<td><input style="width:100px;border:0px" type="text" id="qty" name="qty" value="${pChaseOrderItemDTO.qty }" readonly="readonly"/> </td>
				  				<%-- <td><input style="width:70px;border:0px" type="text" id="qtyReceived" name="qtyReceived" value="${pChaseOrderItemDTO.qtyReceived }" readonly="readonly"/> </td> --%>
				  				<td><span><i class="red">*</i><input style="width:110px" type="text" id="notifySendQty" name="notifySendQty" value="${pChaseOrderItemDTO.qty - pChaseOrderItemDTO.qtyReceived }"  onchange="updateNotifySendQty(this,${pChaseOrderItemDTO.qty - pChaseOrderItemDTO.qtyReceived })"/>
				  					</span>
				  					<input type="hidden" id="pid" name="pid" value="${pChaseOrderItemDTO.pid }"  style="border:0px" readonly="readonly"/>
					  				<input type="hidden" id="skuId" name="skuId" value="${pChaseOrderItemDTO.skuId }"  style="border:0px" readonly="readonly"/>
					  				<input type="hidden" id="price" name="price" value="${pChaseOrderItemDTO.skuPrice }"  style="border:0px" readonly="readonly"/>
					  				
					  				<input type="hidden" id="skuNameEn" name="skuNameEn" value="${pChaseOrderItemDTO.skuNameEn }"  style="border:0px" readonly="readonly"/>
					  				<%-- <input type="hidden" id="qtyReceived" name="qtyReceived" value="${pChaseOrderItemDTO.qtyReceived }"  style="border:0px" readonly="readonly"/> --%>
					  				<input type="hidden" id="itemId" name="itemId" value="${pChaseOrderItemDTO.id }"  style="border:0px" readonly="readonly"/>
				  					<input type="hidden" id="isGive" name="isGive" value="${pChaseOrderItemDTO.isGive }"  style="border:0px" readonly="readonly"/>
				  				</td>
				  				
				  				
			  				</tr>
		  				</c:forEach>
		  				
		  				<%-- <tr>
		  					<td>合计</td>
			  				<td></td>
			  				<td></td>
			  				<td></td>
			  				<td></td>
			  				<td></td>
			  				<td></td>
			  				<td></td>
			  				<td></td>
			  				<td><input style="width:110px" type="text" id="totalNotifyQty" name="totalNotifyQty" value="${totalNotifyQty }"></td>
		  				</tr> --%>
		  			</table>
		  		</div>
		 <!--  </div> -->
<c:if test="${!empty pb.result}">
	<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
</c:if>