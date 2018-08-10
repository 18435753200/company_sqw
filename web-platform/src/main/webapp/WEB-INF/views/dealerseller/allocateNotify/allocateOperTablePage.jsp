<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>

<div class="pu_hd">
	<h3>查询条件</h3>
</div>

<div class="pu_wrap">
	<div class="pu_bd">
		<table>
			<tbody>
				<tr class="order_hd">
					<th width="40px;">序号</th>
					<th width="40px;">选择</th>
					<th width="150px;">商品编码</th>
					<th width="180px;">商品条码</th>
					<th width="180px;">商品名称</th>
					<th width="180px;">SKU_ID</th>
					<th width="180px;">仓库编码</th>
					<th width="150px;">规格</th>
					<th width="100px;">单位</th>
				</tr>
				<c:forEach items="${pb.result}" var="skuCodeDTOList"
					varStatus="status">
					<tr>
						<td>${status.index + 1 }</td>
						<!--<td><input type="checkbox" name="skuId" value="${skuCodeDTOList.skuId}" onclick="opetatorPrd(${skuCodeDTOList.skuId})">
						</td>-->
						 
						<td><input name="skuId" type="radio" value="${skuCodeDTOList.skuId}" onclick="opetatorPrd('${skuCodeDTOList.skuId}',${status.index + 1 })" />
						</td> 
						<td>${skuCodeDTOList.businessProdId}</td>
						<td>${skuCodeDTOList.skuCode}</td>
						<td>${skuCodeDTOList.supplierProdName}</td>
						<td>${skuCodeDTOList.skuId}</td>
						<td>${skuCodeDTOList.barSkuId}</td>
						<td>${skuCodeDTOList.skuNameCn}</td>
						<td>${skuCodeDTOList.measure.cname}
						
						<input type="hidden" id="pcode${status.index + 1 }" name="pcode" value="${skuCodeDTOList.businessProdId}" >
						<input type="hidden" id="barCode${status.index + 1 }" name="barCode" value="${skuCodeDTOList.skuCode}" >
						<input type="hidden" id="pname${status.index + 1 }" name="pname" value="${skuCodeDTOList.supplierProdName}" >
						<input type="hidden" id="format${status.index + 1 }" name="format" value="${skuCodeDTOList.skuNameCn}" >
						<input type="hidden" id="unit${status.index + 1 }" name="unit" value="${skuCodeDTOList.measure.cname}" >
						<input type="hidden" id="productId${status.index + 1 }" name="productId" value="${skuCodeDTOList.productId}" >
						<input type="hidden" id="barSkuId${status.index + 1 }" name="barSkuId" value="${skuCodeDTOList.barSkuId}" >
						<input type="hidden" id="price${status.index + 1 }" name="productId" value="${skuCodeDTOList.unitPrice}" >
						</td>
					</tr>
				</c:forEach>
						<input type="hidden" id="isgenuine" name="isgenuine" value="${isgenuine}" >
						<input type="hidden" id="transferOutWarehouseCode" name="transferOutWarehouseCode" value="${transferOutWarehouseCode}" >
						<input type="hidden" id="transferOutWarehouseName" name="transferOutWarehouseName" value="${transferOutWarehouseName}" >
			</tbody>
		</table>
		<c:if test="${!empty pb.result}">
			<%@include file="/WEB-INF/views/dealerseller/paging.jsp"%>
		</c:if>
	</div>
</div>

			<!-- DIV异步调用填充-->
			<div class="pu_bd" id="cbOpeStockList" ></div>
	