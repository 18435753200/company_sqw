<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<title>采购单_<c:if test="${target == 1 }">添加采购单</c:if><c:if test="${target == 2 }">修改采购单</c:if><c:if test="${target == 3 }">查看采购单</c:if></title>
	<link type="text/css" rel="stylesheet" href="${path}/commons/css/purchase.css">
	<script type="text/javascript" src="${path}/commons/js/purchaseorder_addpurchaseorder.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			
			var  target = ${target};
			if(target != "1"){
				$(".alert_bu").hide();
				loadPurchasePage('${pChaseOrderDTO.supplierId}','${pChaseOrderDTO.supplierName}');
			}
			if(target == "3"){
				$('#addTime').hide();
				$('#submit_addpurchase_fm').hide();
				$('input').attr('readonly','readonly');
			}else{
				$(".yfei input").live("input propertychange",checknum);
				$(".yfei input").live("blur", tofix);
				
				$("[name='receiveName']").live("blur", checktext);
				$("[name='receiveAddress']").live("blur", checktext);
				$("[name='receivePhone']").live("blur", checktel);
				
				$("[name='qty']").live("input propertychange", checknum);
				$("[name='skuPriceRmb']").live("input propertychange", checkrmb);
				$("[name='dutyRate']").live("input propertychange", checkshui);
				$("[name='consumptionTaxRate']").live("input propertychange", checkshui);
				$("[name='valueAddedTaxRate']").live("input propertychange", checkshui);
				
				
				$("[name='qty']").live("blur", qty);
				$("[name='skuPriceRmb']").live("blur", skuPriceRmb);
				$("[name='dutyRate']").live("blur", dutyRate);
				$("[name='consumptionTaxRate']").live("blur", consumptionTaxRate);
				$("[name='valueAddedTaxRate']").live("blur", valueAddedTaxRate);
				
				$("#ttr .order-bd a").live("click", del);
				
			}
			
		});
	
	
	</script>
</head>
<body>

<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10" ></div>
	<div class="center">
		
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp;</p>
					<p>商品管理&nbsp;&gt;&nbsp;</p>
					<p class="c1">采购单列表</p>
				</div>
				
			</div>

			<c:choose> 
				  <c:when test="${target == 2 && pChaseOrderDTO.status != 1}">   
				  <form id="purchaseOrderfm">
					<div class="xia">
						<p class="p1">
							<span>供应商：</span>
							<input  type="hidden" name="id" value="${fn:escapeXml(pChaseOrderDTO.id) } ">
							<input type="text" readonly="readonly" id="supplierName" name="supplierName" checkedsupplierid = "" value="${fn:escapeXml(pChaseOrderDTO.supplierName) }">
							<input type="hidden" id="supplierId" name="supplierId" value="${fn:escapeXml(pChaseOrderDTO.supplierId) }">
							<input type="hidden" id="target"  value="${target}">
							<input type="hidden" id="status10"  value="${pChaseOrderDTO.status}">
							<span class="sv">仓 库：</span>
							<input readonly="readonly" type="text" name="warehouseName" id="warehouseName" value="${fn:escapeXml(pChaseOrderDTO.warehouseName) }">
							<input name="warehouseCode" type="hidden" id="warehouseCode" value="${fn:escapeXml(pChaseOrderDTO.warehouseCode) }">
							
						</p>
						<p class="p1">
							<span>制单人：</span>
							<c:if test="${target != 1 }">
								<input type="text" readonly="readonly" name="createBy" value="${fn:escapeXml(pChaseOrderDTO.createBy) }">
							</c:if>
							<c:if test="${target == 1 }">
								<input type="text" readonly="readonly" name="createBy" value="${fn:escapeXml(currentUser.username) }">
							</c:if>
							<span class="st">制单时间：</span>
							<input type="text" readonly="readonly" name="createTime"  value="<fmt:formatDate value="${pChaseOrderDTO.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>">
						</p>
		
						<p class="p1">
							<span>收货人：</span>
							<input type="text" name="receiveName" value="${fn:escapeXml(pChaseOrderDTO.receiveName) }">
							<span class="st">收货地址：</span>
							<input type="text" name="receiveAddress" value="${fn:escapeXml(pChaseOrderDTO.receiveAddress) }">
							<span class="st">收货人电话：</span>
							<input type="text" name="receivePhone" value="${fn:escapeXml(pChaseOrderDTO.receivePhone) }">
						</p>
		
					</div>
					<div class="pu-lie">
						<div class="hd-bar">
						</div>
						<table class="pu-liebiao">
							<tbody id = "ttr">
							<tr class="order-hd">
								<th>商品ID</th>
								<th>商品名称</th>
								<th>数量</th>
								<th>单价<br>（人民币）</th>
								<th>关税税率(%)</th>
								<th>关税小计<br>（人民币）</th>
								<th>消费税税率(%)</th>
								<th>消费税小计<br>（人民币）</th>
								<th>增值税税率(%)</th>
								<th>增值税小计<br>（人民币）</th>
								<th>不含税小计<br>（人民币）</th>
								<th>含税小计<br>（人民币）</th>
								<th class="operate" style="width:60px">操作</th>
							</tr>
							<c:forEach items="${pChaseOrderDTO.pCOItemList }" var="pCO">
								<tr class="order-bd">
									<td><input type="hidden" name="productid" value="${pCO.pid }"><input name="skuId" readonly="readonly" value="${pCO.skuId }"></td>
									<td><input name="skuNameCn" readonly="readonly" value="${pCO.pname}_${pCO.skuNameCn }"></td>
									<td><input name="qty" value="${pCO.qty }"  readonly="readonly"></td>
									<td><input name="skuPriceRmb" value="${pCO.skuPriceRmb }"  readonly="readonly"></td>
									<td><input name="dutyRate" value="${pCO.dutyRate }"></td>
									<td name="dutiesRmb" >${pCO.dutiesRmb }</td>
									<td><input name="consumptionTaxRate" value="${pCO.consumptionTaxRate }"></td>
									<td name="consumptionTaxRmb">${pCO.consumptionTaxRmb }</td>
									<td><input name="valueAddedTaxRate" value="${pCO.valueAddedTaxRate }"></td>
	
									<td name="valueAddedTaxRmb">${pCO.valueAddedTaxRmb }</td>
									<td name="subtotalPriceRmb">${pCO.subtotalPriceRmb }</td>
									<td name="totalPriceRmb">${pCO.totalPriceRmb  }</td>
									<td>--</td>
								</tr>
							</c:forEach>
		
								</tbody>
								<tbody>
							<tr class="order-bd">
								<td>合计</td>
								<td></td>
								<td><input readonly="readonly" name="sumQty" value="${fn:escapeXml(pChaseOrderDTO.qty) }"></td>
								<td></td>
								<td></td>
								<td><input readonly="readonly"  name="sumDutiesRmb" value="${fn:escapeXml(pChaseOrderDTO.totalDutiesRmb) }"></td>
								<td></td>
								<td><input readonly="readonly" name="sumConsumptionTaxRmb" value="${fn:escapeXml(pChaseOrderDTO.totalConsumptionTaxRmb) }"></td>
								<td></td>
								<td><input readonly="readonly"  name="sumValueAddedTaxRmb" value="${fn:escapeXml(pChaseOrderDTO.totalValueAddedTaxRmb) }"></td>
								<td><input readonly="readonly" name="sumSubtotalPriceRmb" value="${fn:escapeXml(pChaseOrderDTO.itemTotalPirceNoTax) }"></td>
								<td><input readonly="readonly" name="sumTotalPriceRmb" value="${fn:escapeXml(pChaseOrderDTO.itemTotalPrice) }"></td>
								<td></td>
							</tr>
							</tbody>
						</table>
					</div>
	
	
	
					<div class="yfei">
						<table>
							<tr class="yfei_hd">
									
								<th>采购费用</th>
								<th></th>
			
							</tr>
							<tr>
									
								<td>运费（至港口）</td>
								<td><span><input name="internationalFreightRmb"  id="" value="${fn:escapeXml(pChaseOrderDTO.internationalFreightRmb) }"></span></td>
							</tr>
							<tr>
									
								<td>运费（港口至库房）</td>
								<td><span><input name="inlandFreight"  id="" value="${fn:escapeXml(pChaseOrderDTO.inlandFreight) }"></span></td>
							</tr>
							<tr>
									
								<td>清关杂费</td>
								<td><span><input name="customsClearanceFee" id="" value="${fn:escapeXml(pChaseOrderDTO.customsClearanceFee) }"></span></td>
							
							</tr>
							<tr>
								
								<td>标签备案费</td>
								<td><span><input name="labelRegistrationFee"  id="" value="${fn:escapeXml(pChaseOrderDTO.labelRegistrationFee) }"></span></td>
						
							</tr>
							<tr>
								
								<td>标签贴标费</td>
								<td><span><input name="tagLabelingFee"  id="" value="${fn:escapeXml(pChaseOrderDTO.tagLabelingFee) }"></span></td>
						
							</tr>
							<tr>
								
								<td>第三方服务费</td>
								<td><span><input name="serviceFee"  id="" value="${fn:escapeXml(pChaseOrderDTO.serviceFee) }"></span></td>
							
							</tr>
						</table>
					</div>
				</form>
				  </c:when> 
				  <c:otherwise>  
				  <form id="purchaseOrderfm">
			
			
				<div class="xia">
					<p class="p1">
						<span>供应商：</span>
						<input type="hidden" name="id" value="${fn:escapeXml(pChaseOrderDTO.id) }">
						<input type="text" readonly="readonly" id="supplierName" name="supplierName" checkedsupplierid = "" value="${fn:escapeXml(pChaseOrderDTO.supplierName) }">
						<input type="hidden" id="supplierId" name="supplierId" value="${fn:escapeXml(pChaseOrderDTO.supplierId) }">
						<input type="hidden" id="target"  value="${target}">
						<input type="hidden" id="status10"  value="${pChaseOrderDTO.status}">
						<span class="sv">仓 库：</span>
						<input readonly="readonly" type="text" name="warehouseName" id="warehouseName" value="${fn:escapeXml(pChaseOrderDTO.warehouseName) }">
						<input name="warehouseCode" type="hidden" id="warehouseCode" value="${fn:escapeXml(pChaseOrderDTO.warehouseCode) }">
						
					</p>
	
					<p class="p1">
						<span>制单人：</span>
						<c:if test="${target != 1 }">
							<input type="text" readonly="readonly" name="createBy" value="${fn:escapeXml(pChaseOrderDTO.createBy) }">
						</c:if>
						<c:if test="${target == 1 }">
							<input type="text" readonly="readonly" name="createBy" value="${fn:escapeXml(currentUser.username) }">
						</c:if>
						<span class="st">制单时间：</span>
						<input type="text" readonly="readonly" name="createTime"  value="<fmt:formatDate value="${pChaseOrderDTO.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>">
					</p>
	
					<p class="p1">
						<span>收货人：</span>
						<input type="text" name="receiveName" value="${fn:escapeXml(pChaseOrderDTO.receiveName) }">
						<span class="st">收货地址：</span>
						<input type="text" name="receiveAddress" value="${fn:escapeXml(pChaseOrderDTO.receiveAddress) }">
						<span class="st">收货人电话：</span>
						<input type="text" name="receivePhone" value="${fn:escapeXml(pChaseOrderDTO.receivePhone) }">
					</p>
	
				</div>
				<div class="pu-lie">
					<div class="hd-bar">
					<div id="skuselect"></div>
					<div class="add-btn"><button  id="addTime" type="button">添加</button></div>
					</div>
					<table class="pu-liebiao">
						<tbody id = "ttr">
						<tr class="order-hd">
							<th>商品ID</th>
							<th>商品名称</th>
							<th>数量</th>
							<th>单价<br>（人民币）</th>
							<th>关税税率(%)</th>
							<th>关税小计<br>（人民币）</th>
							<th>消费税税率(%)</th>
							<th>消费税小计<br>（人民币）</th>
							<th>增值税税率(%)</th>
							<th>增值税小计<br>（人民币）</th>
							<th>不含税小计<br>（人民币）</th>
							<th>含税小计<br>（人民币）</th>
							<th class="operate" style="width:60px">操作</th>
						</tr>
						<c:forEach items="${pChaseOrderDTO.pCOItemList }" var="pCO">
							<tr class="order-bd">
								<td><input type="hidden" name="productid" value="${pCO.pid }"><input name="skuId" readonly="readonly" value="${pCO.skuId }"></td>
								<td><input name="skuNameCn" readonly="readonly" value="${pCO.pname}_${pCO.skuNameCn }"></td>
								<td><input name="qty" value="${pCO.qty }"></td>
								<td><input name="skuPriceRmb" value="${pCO.skuPriceRmb }"></td>
								<td><input name="dutyRate" value="${pCO.dutyRate }"></td>
								<td name="dutiesRmb" >${pCO.dutiesRmb }</td>
								<td><input name="consumptionTaxRate" value="${pCO.consumptionTaxRate }"></td>
								<td name="consumptionTaxRmb">${pCO.consumptionTaxRmb }</td>
								<td><input name="valueAddedTaxRate" value="${pCO.valueAddedTaxRate }"></td>

								<td name="valueAddedTaxRmb">${pCO.valueAddedTaxRmb }</td>
								<td name="subtotalPriceRmb">${pCO.subtotalPriceRmb }</td>
								<td name="totalPriceRmb">${pCO.totalPriceRmb  }</td>
								<td><a href="javascript:;">删除</a></td>
							</tr>
						</c:forEach>
	
							</tbody>
							<tbody>
						<tr class="order-bd">
							<td>合计</td>
							<td></td>
							<td><input readonly="readonly" name="sumQty" value="${fn:escapeXml(pChaseOrderDTO.qty) }"></td>
							<td></td>
							<td></td>
							<td><input readonly="readonly"  name="sumDutiesRmb" value="${fn:escapeXml(pChaseOrderDTO.totalDutiesRmb) }"></td>
							<td></td>
							<td><input readonly="readonly" name="sumConsumptionTaxRmb" value="${fn:escapeXml(pChaseOrderDTO.totalConsumptionTaxRmb) }"></td>
							<td></td>
							<td><input readonly="readonly"  name="sumValueAddedTaxRmb" value="${fn:escapeXml(pChaseOrderDTO.totalValueAddedTaxRmb) }"></td>
							<td><input readonly="readonly" name="sumSubtotalPriceRmb" value="${fn:escapeXml(pChaseOrderDTO.itemTotalPirceNoTax) }"></td>
							<td><input readonly="readonly" name="sumTotalPriceRmb" value="${fn:escapeXml(pChaseOrderDTO.itemTotalPrice) }"></td>
							<td></td>
						</tr>
						</tbody>
					</table>
				</div>



				<div class="yfei">
					<table>
						<tr class="yfei_hd">
								
							<th>采购费用</th>
							<th></th>
		
						</tr>
						<tr>
								
							<td>运费（至港口）</td>
							<td><span><input name="internationalFreightRmb" id="" value="${fn:escapeXml(pChaseOrderDTO.internationalFreightRmb) }"></span></td>
						</tr>
						<tr>
								
							<td>运费（港口至库房）</td>
							<td><span><input name="inlandFreight" id="" value="${fn:escapeXml(pChaseOrderDTO.inlandFreight) }"></span></td>
						</tr>
						<tr>
								
							<td>清关杂费</td>
							<td><span><input name="customsClearanceFee" id="" value="${fn:escapeXml(pChaseOrderDTO.customsClearanceFee) }"></span></td>
						
						</tr>
						<tr>
							
							<td>标签备案费</td>
							<td><span><input name="labelRegistrationFee" id="" value="${fn:escapeXml(pChaseOrderDTO.labelRegistrationFee) }"></span></td>
					
						</tr>
						<tr>
							
							<td>标签贴标费</td>
							<td><span><input name="tagLabelingFee" id="" value="${fn:escapeXml(pChaseOrderDTO.tagLabelingFee) }"></span></td>
					
						</tr>
						<tr>
							
							<td>第三方服务费</td>
							<td><span><input name="serviceFee" id="" value="${fn:escapeXml(pChaseOrderDTO.serviceFee) }"></span></td>
						
						</tr>
					</table>
				</div>

			</form> 
				  </c:otherwise> 
			</c:choose> 
			
			
			
		<div class="bu">
		
			<button type="button" class="sub" id="submit_addpurchase_fm">提交</button>
			
		</div>
		

</div>



<div class="alert_bu">

  <div class="bg"></div>
  
	<div class="wrap">
	
		<div class="pic"></div>
		
		<div class="box1">
		
			<div id="boxtitle">
			
				<h2>选择仓库 ( 一次填写只能选择一个仓库，请谨慎选择 )</h2>
				
			</div>
			
			<div id="suppliernametext" style="display: none;">
			
				<input type="text" id="getSupplierName">
				
				<button type="button" onclick="getSupplierList()">查询</button>
			
			</div>
			
		</div>
		
		<div class="box2">
		
			<table>
			
				<thead>
				
					<tr>
						<th class="t1">单选</th>
						<th class="t2">仓库名称</th>
					</tr>
					
				</thead>
				
				<tbody id="suppliertable">
				
					<c:forEach items="${warehouse }" var="warehouse">
					
						<tr>
							<td class='t1'><input type='radio' name='warehouseid' value="${warehouse.warehouseCode }"></td>
							<td class='t2' title='${warehouse.warehouseName}'><span>${warehouse.warehouseName}</span></td>
						</tr>
						
					</c:forEach>
					
				</tbody>
				
			</table>
			
		</div>
		
		<div class="box3">
		
			<c:if test="${! empty buttonsMap['货品列表-补录-查看经销商列表'] }">	
			
				<button type="button" class="bt1" id="warehouseclick">确定</button>
				
			</c:if>
			
			<c:if test="${! empty buttonsMap['货品列表-补录-查询库存列表'] }">	
			
				<button type="button" class="bt1" id="supplierclick" style="display: none;" onclick="loadPurchasePage()">确定</button>
			
			</c:if>
			
		</div>
		
	</div>
  
</div>

	
</body>
</html>