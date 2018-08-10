<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<script type="text/javascript" src="${path}/commons/js/addAllocatePrdNotifyPage.js"></script>
<script type="text/javascript" src="${path}/commons/js/allocatePrdBz.js"></script>	
<div class="pu_hd">
	<div class="btn">
		<a href="javascript:void(0)">当前库房：${transferOutWarehouseName}</a> <a
			href="javascript:void(0)" onclick="opetatorStock()">确定</a>
	</div>
</div>

<div class="pu_wrap">
	<div class="pu_bd">
		<table id="ope">
			<tbody>
				<tr class="order_hd">
					<th width="40px;">序号</th>
					<th width="40px;">选择</th>
					<th width="100px;">商品编码</th>
					<th width="100px;">商品条码</th>
					<th width="80px;">商品名称</th>
					<th width="80px;">SKU_ID</th>
					<th width="80px;">仓库条码</th>
					<th width="60px;">规格</th>
					<th width="40px;">单位</th>
					<th width="90px;">可用库存数量</th>
					<th width="80px;" style="display:none">批次号</th>
					<th width="80px;" style="display:none">生产批号</th>
					<th width="70px;" style="display:none">生产日期</th>
					<th width="60px;">商品状态</th>
					<th style="display:none">skuId</th>
					<th style="display:none">PID</th>
					<th style="display:none">单价</th>
					<th width="90px;">残品库存数量</th>
				</tr>

				<c:forEach items="${transferItems}" var="transferItems"
					varStatus="status">
					<tr>
						<td>${status.index + 1 }</td>

						<!--
						<input type="hidden" id="pid" name="pid" value="${transferItems.pid}" >
						<input type="hidden" id="pcode" name="pcode" value="${transferItems.pcode}" >
						<input type="hidden" id="barCode" name="barCode" value="${transferItems.barCode}" >
						<input type="hidden" id="pname" name="pname" value="${transferItems.pname}" >
						<input type="hidden" id="format" name="format" value="${transferItems.format}" >
						<input type="hidden" id="unit" name="unit" value="${transferItems.unit}" >
						<input type="hidden" id="stockQty" name="stockQty" value="${transferItems.stockQty}" >
						<input type="hidden" id="batchId" name="batchId" value="${transferItems.batchId}" >
						<input type="hidden" id="batchNumber" name="batchNumber" value="${transferItems.batchNumber}" >
						<input type="hidden" id="productionDate" name="productionDate" value="${transferItems.productionDate}" >
						<input type="hidden" id="isgenuine" name="isgenuine" value="${transferItems.isgenuine}" >
						<input type="hidden" id="skuId" name="skuId" value="${transferItems.skuId}" >
						 -->

						<td><input type="checkbox" name="skuId"
							value="${status.index + 1}">
						</td>
						<td>${transferItems.pcode}</td>
						<td>${transferItems.barCode}</td>
						<td>${transferItems.pname}</td>
						<td>${transferItems.skuId}</td>
						<td>${transferItems.barSkuId}</td>
						<td>${transferItems.format}</td>
						<td>${transferItems.unit}</td>
						<td>${transferItems.stockQty}</td>
						<td style="display:none">${transferItems.batchNumber}</td>
						<td style="display:none">${transferItems.batchId}</td>
						<td style="display:none"><fmt:formatDate value="${transferItems.productionDate}"
								pattern="yyyy-MM-dd" />
						</td>
						<c:choose>
							<c:when test="${transferItems.isgenuine==2}">
								<td>残品</td>
							</c:when>
							<c:otherwise>
								<td>正品</td>
							</c:otherwise>
						</c:choose>
						<td style="display:none">${transferItems.skuId}</td>
						<td style="display:none">${transferItems.pid}</td>
						<td style="display:none">${transferItems.price}</td>
						<td>${transferItems.goodsQty}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>





