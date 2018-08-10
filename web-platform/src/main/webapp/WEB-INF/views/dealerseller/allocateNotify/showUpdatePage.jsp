<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>商品调拨(通知)单</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/reset.css">
	<link rel="stylesheet" type="text/css" href="${path}/commons/css/transfer.css">
	
	<script src="${path}/commons/js/my97/WdatePicker.js"></script>
	<!--
	<script type="text/javascript" src="${path}/commons/js/allocateShowPage.js"></script>
	  -->
	<script type="text/javascript" src="${path}/commons/js/allocateNotifyEdit.js"></script>
	<script type="text/javascript" src="${path}/commons/js/map.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		$("#transferOutWarehouseCode").attr("disabled","true");
		$("#transferInWarehouseCode").attr("disabled","true");
	});
	
	function appendNewTr(obj) {
	var th0;
	if($("#shoComp tr:last").find("td:eq(0)").text()=="")
	{
		th0=1;
	}else{
		th0=parseInt($("#shoComp tr:last").find("td:eq(0)").text())+1;
	}
	//var html = "<tr><td >"+obj.children('td').eq(0).text()+"</td><td ><input type='checkbox' name='skuName' value='"+obj.children('td').eq(0).text()+"'></td><td >"+obj.children('td').eq(2).text()+"</td><td >"+obj.children('td').eq(3).text()+"</td><td >"+obj.children('td').eq(4).text()+"</td><td >"+obj.children('td').eq(5).text()+"</td><td >"+obj.children('td').eq(6).text()+"</td><td >"+obj.children('td').eq(7).text()+"</td><td ><input type='text' name='allQty' id='allQty"+obj.children('td').eq(0).text()+"'/></td><td >"+obj.children('td').eq(11).text()+"</td><td >"+obj.children('td').eq(8).text()+"</td><td >"+obj.children('td').eq(9).text()+"</td><td >"+obj.children('td').eq(10).text()+"</td><td >"+obj.children('td').eq(12).text()+"</td><td >"+obj.children('td').eq(13).text()+"</td></tr>";
	//var html = "<tr><td>"+obj.children('td').eq(0).text()+"</td><td><input type='checkbox' name='skuName' value='"+obj.children('td').eq(0).text()+"'></td><td><input type='text' id='pcode"+obj.children('td').eq(0).text()+"' name='pcode"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(2).text()+"' readonly='readonly'></td><td><input type='text' id='barCode"+obj.children('td').eq(0).text()+"' name='barCode"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(3).text()+"'readonly='readonly'></td><td><input type='text' id='pname"+obj.children('td').eq(0).text()+"' name='pname"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(4).text()+"'readonly='readonly'></td><td><input type='text' id='format"+obj.children('td').eq(0).text()+"' name='format"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(5).text()+"'readonly='readonly'></td><td><input type='text' id='unit"+obj.children('td').eq(0).text()+"' name='unit"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(6).text()+"'readonly='readonly'></td><td><input type='text' id='stockQty"+obj.children('td').eq(0).text()+"' name='stockQty"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(7).text()+"'readonly='readonly'></td><td><input type='text' id='transferQty"+obj.children('td').eq(0).text()+"' name='transferQty"+obj.children('td').eq(0).text()+"' value=''></td><td><input type='text' id='isgenuine"+obj.children('td').eq(0).text()+"' name='isgenuine"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(11).text()+"'readonly='readonly'></td><td><input type='text' id='batchNumber"+obj.children('td').eq(0).text()+"' name='batchNumber"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(8).text()+"'readonly='readonly'></td><td><input type='text' id='batchId"+obj.children('td').eq(0).text()+"' name='batchId"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(9).text()+"'readonly='readonly'></td><td><input type='text' id='productionDate"+obj.children('td').eq(0).text()+"' name='productionDate"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(10).text()+"'readonly='readonly'></td><td><input type='text' id='skuId"+obj.children('td').eq(0).text()+"' name='skuId"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(12).text()+"'readonly='readonly'></td><td><input type='text' id='pid"+obj.children('td').eq(0).text()+"' name='pid"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(13).text()+"'readonly='readonly'></td></tr>";
	var html = "<tr class='sotClassComp'><td>"+th0+"</td><td><input type='checkbox' name='skuName' value='"+th0+"' class='sm'></td><td><input type='text' id='pcode"+th0+"' name='pcode' value='"+obj.children('td').eq(2).text().trim()+"' readonly='readonly'></td><td><input type='text' id='barCode"+th0+"' name='barCode' value='"+obj.children('td').eq(3).text().trim()+"'readonly='readonly'></td><td><input type='text' id='pname"+th0+"' name='pname' value='"+obj.children('td').eq(4).text().trim()+"'readonly='readonly'></td><td><input type='text' id='format"+th0+"' name='format' value='"+obj.children('td').eq(5).text().trim()+"'readonly='readonly'></td><td><input type='text' id='unit"+th0+"' name='unit' value='"+obj.children('td').eq(6).text().trim()+"'readonly='readonly'></td><td><input type='text' id='stockQty"+th0+"' name='stockQty' value='"+obj.children('td').eq(7).text().trim()+"'readonly='readonly'></td><td><input type='text' id='transferQty"+th0+"' name='transferQty'  onblur='onblurInfo($(this),"+obj.children('td').eq(7).text().trim()+");'  value=''></td><td><input type='text' id='isgenuine"+th0+"' name='isgenuine' value='"+obj.children('td').eq(11).text().trim()+"'readonly='readonly'></td><td><input type='text' id='batchNumber"+th0+"' name='batchNumber' value='"+obj.children('td').eq(8).text().trim()+"'readonly='readonly'></td><td><input type='text' id='batchId"+th0+"' name='batchId' value='"+obj.children('td').eq(9).text().trim()+"'readonly='readonly'></td><td><input type='text' id='productionDate"+th0+"' name='productionDate' value='"+obj.children('td').eq(10).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='skuId"+th0+"' name='skuId' value='"+obj.children('td').eq(12).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='pid"+th0+"' name='pid' value='"+obj.children('td').eq(13).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='price"+th0+"' name='price' value='"+obj.children('td').eq(14).text().trim()+"'readonly='readonly'></td></tr>";
	//var html = "<tr class='sotClass'><td>"+obj.children('td').eq(0).text().trim()+"</td><td><input type='checkbox' name='skuName' value='"+obj.children('td').eq(0).text().trim()+"'></td><td><input type='text' id='pcode"+obj.children('td').eq(0).text().trim()+"' name='pcode' value='"+obj.children('td').eq(2).text().trim()+"' readonly='readonly'></td><td><input type='text' id='barCode"+obj.children('td').eq(0).text().trim()+"' name='barCode' value='"+obj.children('td').eq(3).text().trim()+"'readonly='readonly'></td><td><input type='text' id='pname"+obj.children('td').eq(0).text().trim()+"' name='pname' value='"+obj.children('td').eq(4).text().trim()+"'readonly='readonly'></td><td><input type='text' id='format"+obj.children('td').eq(0).text().trim()+"' name='format' value='"+obj.children('td').eq(5).text().trim()+"'readonly='readonly'></td><td><input type='text' id='unit"+obj.children('td').eq(0).text().trim()+"' name='unit' value='"+obj.children('td').eq(6).text().trim()+"'readonly='readonly'></td><td><input type='text' id='stockQty"+obj.children('td').eq(0).text().trim()+"' name='stockQty' value='"+obj.children('td').eq(7).text().trim()+"'readonly='readonly'></td><td><input type='text' id='transferQty"+obj.children('td').eq(0).text().trim()+"' name='transferQty'  onblur='onblurInfo("+obj.children('td').eq(0).text().trim()+");'  value=''></td><td><input type='text' id='isgenuine"+obj.children('td').eq(0).text().trim()+"' name='isgenuine' value='"+obj.children('td').eq(11).text().trim()+"'readonly='readonly'></td><td><input type='text' id='batchNumber"+obj.children('td').eq(0).text().trim()+"' name='batchNumber' value='"+obj.children('td').eq(8).text().trim()+"'readonly='readonly'></td><td><input type='text' id='batchId"+obj.children('td').eq(0).text().trim()+"' name='batchId' value='"+obj.children('td').eq(9).text().trim()+"'readonly='readonly'></td><td><input type='text' id='productionDate"+obj.children('td').eq(0).text().trim()+"' name='productionDate' value='"+obj.children('td').eq(10).text().trim()+"'readonly='readonly'></td><td><input type='text' id='skuId"+obj.children('td').eq(0).text().trim()+"' name='skuId' value='"+obj.children('td').eq(12).text().trim()+"'readonly='readonly'></td><td><input type='text' id='pid"+obj.children('td').eq(0).text().trim()+"' name='pid' value='"+obj.children('td').eq(13).text().trim()+"'readonly='readonly'></td></tr>";
	return html;
}
	function doThingsAfterAdd(config,skuID,butchNo,isgenuine){
		var len = $("#shoComp tr").length;
		var map = new Map();
		for ( var i = 1; i <= len; i++) {
			var tr = $("#shoComp tr").eq(i);
			var st8 = tr.children('td').eq(10).find("input").val();
			
			var st9 = tr.children('td').eq(9).find("input").val();
			var st12 = tr.children('td').eq(13).find("input").val();
			
			map.put(st12+st9,st8);
		}
		
		if(map.get(skuID+isgenuine)!=butchNo){
			$("#shoComp").append(appendNewTr(config));
			
			$("#transferOutWarehouseCode").attr("disabled","true");
			$("#transferInWarehouseCode").attr("disabled","true");
		}
	}
	function onblurInfo(index,value)
    {
    	if(index.val()=="")
    	{
    		alert("请填写申请数量!");
    		return;
    	}
    	if(parseInt(index.val())>parseInt(value))
    	{
    		alert("申请数量不能大于可用库存数量!");
    		return ;
    	}
    }
    
	</script>
</head>
<body>
	<!-- 导航 START -->
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 END -->

	<div class="blank"></div>
	<div class="center">
		<!-- 左侧菜单 START -->
		<div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		</div>
		<!-- 左侧菜单 END -->
	
		<div class="right">
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp;</p>
					<p>商品储存&nbsp;&gt;&nbsp;</p>
					<p class="c1">商品调拨(通知)单</p>
				</div>
			</div>
		
		<form id="opeAllocatePrdFrom">
			<div class="pu_hd">
				<h3>表头信息</h3>
				<div class="btn">
					<a href="javascript:void(0)" onclick="saveComp()">保存</a>
					<a href="javascript:void(0)" onclick="auditComp()">审核</a>
					
					<!-- 提交判断是否选中 -->
					<input type="hidden" id="chkTrue" name="chkTrue">
					<input type="hidden" id="sid" name="sid" value="${transferOrder.sid}" >
				</div>
			</div>

			<div class="xia">
				<p class="p1">
					<span>调拨单编号:</span> 
					<input type="text" id="transferNoChar" name="transferNoChar" value ="${transferOrder.transferNoChar}"  readonly="readonly"/> 
					
					<span>调拨时间:</span> 
					<input type="text" name="createTime" id="createTime"  readonly="readonly"  value ="<fmt:formatDate value="${transferOrder.createTime}" pattern="yyyy-MM-dd"/>" >
				</p>
				
				<p class="p1">
					<span>调出库房:</span> 
					<select id="transferOutWarehouseCode" name="transferOutWarehouseCode">
						<c:forEach items="${warehouseList }" var="warehouse">
						<c:if test="${transferOrder.transferOutWarehouseCode eq warehouse.warehouseCode}">
							<option value="${warehouse.warehouseCode }" selected="selected">${warehouse.warehouseName }</option>
						</c:if>
						<c:if test="${transferOrder.transferOutWarehouseCode ne warehouse.warehouseCode}">
							<option value="${warehouse.warehouseCode }">${warehouse.warehouseName}</option>
						</c:if>
						</c:forEach>
					</select> 
					
					<span>调入库房:</span> 
					<select id="transferInWarehouseCode" name="transferInWarehouseCode">
						<c:forEach items="${warehouseList }" var="warehouse">
						<c:if test="${transferOrder.transferInWarehouseCode eq warehouse.warehouseCode}">
							<option value="${warehouse.warehouseCode }" selected="selected">${warehouse.warehouseName }</option>
						</c:if>
						<c:if test="${transferOrder.transferInWarehouseCode ne warehouse.warehouseCode}">
							<option value="${warehouse.warehouseCode }">${warehouse.warehouseName}</option>
						</c:if>
						</c:forEach>
					</select>
					
					<!-- 
					<span>调出库房:</span> 
					<input type="text" id="transferOutWarehouseName" name="transferOutWarehouseName"  readonly="readonly"  value ="${transferOrder.transferOutWarehouseName}" />  
					<input type="hidden" id="transferOutWarehouseCode" name="transferOutWarehouseCode"   value ="${transferOrder.transferOutWarehouseCode}" />  
					<span>调入库房:</span> 
					<input type="text" id="transferInWarehouseName" name="transferInWarehouseName" readonly="readonly" value ="${transferOrder.transferInWarehouseName}" />
					<input type="hidden" id="transferInWarehouseCode" name="transferInWarehouseCode"   value ="${transferOrder.transferInWarehouseCode}" /> 
					 -->
				</p>
			</div>

			<div class="pu_w">
				<div class="pu_h">
					<h3>调拨商品列表</h3>
					<div class="btn">
						<a href="javascript:void(0)" onclick="opeComp()">操作</a>
						<a href="javascript:void(0)" onclick="delComp()">删除</a>
					</div>
				</div>
				
				<div class="pu_b">
		  			<table id ="shoComp">
		  				<tbody>
			  				<tr class="order_hd">
			  					<th width="40px;">序号</th>
			  					<th width="40px;">选择</th>
			  					<th width="160px;">商品编码</th>
			  					<th width="160px;">商品条码</th>
			  					<th width="160px;">商品名称</th>
			  					<th width="60px;">规格</th>
			  					<th width="30px;">单位</th>
			  					<th width="60px;">库存数量</th>
			  					<th width="60px;">调拨数量</th>
			  					<th width="70px;">商品状态</th>
			  					<th width="100px;">批次号</th>
			  					<th width="100px;">生产批号</th>
			  					<th width="70px;">生产日期</th>
			  					<th width="70px;" style="display:none">skuId</th>
			  					<th width="70px;" style="display:none">商品ID</th>
			  					<th width="60px;" style="display:none">单价</th>
			  				</tr>
			  				
			  				<c:forEach items="${transferItems}" var="transferItems" varStatus="status">
								<tr class="sotClassComp">
									<td>${status.index + 1 }</td>
					  				<td><input type='checkbox' id ="checkName" name='checkName' value='${status.index + 1 }' class="sm"/></td>
					  				<td><input type="text" id="pcode${status.index + 1 }" name="pcode" value="${transferItems.pcode}" style="width:100px;" readonly='readonly'></input></td>
					  				<td><input type="text" id="barCode${status.index + 1 }" name="barCode" value="${transferItems.barCode}"  style="width:100px;" readonly='readonly'></input></td>
					  				<td><input type="text" id="pname${status.index + 1 }" name="pname" value="${transferItems.pname}" style="width:100px;" readonly='readonly'></input></td>
					  				<td><input type="text" id="format${status.index + 1 }" name="format" value="${transferItems.format}"  style="width:40px;" readonly='readonly'></input></td>
					  				<td><input type="text" id="unit${status.index + 1 }" name="unit" value="${transferItems.unit}"  style="width:30px;" readonly='readonly'></input></td>
					  				<td><input type="text" id="stockQty${status.index + 1 }" name="stockQty" value="${transferItems.stockQty}" style="width:60px;" readonly='readonly'></input></td>
					  				<td>
					  					<input type="text" id="transferQty${status.index + 1 }" name="transferQty" value="${transferItems.transferQty}" style="width:60px;" style="width:10px">
					  				</td>
					  				
					  				<c:choose>
										<c:when test="${transferItems.isgenuine==2}">
											<td><input type="text" id="isgenuine${status.index + 1 }" name="isgenuine" value="残品"  readonly='readonly'></input></td>
											<!-- <input type="hidden" id="batchNumber" name="batchNumber" value="${transferItems.batchNumber}"  readonly='readonly'>残品</input> -->
										</c:when>
										<c:otherwise>
											<td><input type="text" id="isgenuine${status.index + 1 }" name="isgenuine" value="正品"  readonly='readonly'></input></td>
										</c:otherwise>
									</c:choose>
									
									<td><input type="text" id="batchNumber${status.index + 1 }" name="batchNumber" value="${transferItems.batchNumber}"  style="width:70px;" readonly='readonly'></input></td>
					  				<td><input type="text" id="batchId${status.index + 1 }" name="batchId" value="${transferItems.batchId}" style="width:70px;" readonly='readonly'></input></td>
					  				<td><input type="text" id="productionDate${status.index + 1 }" name="productionDate" style="width:70px;" value="<fmt:formatDate value="${transferItems.productionDate}" pattern="yyyy-MM-dd"/>"  readonly='readonly'></input></td>
					  				<td style='display:none'><input type="text" id="skuId${status.index + 1 }" name="skuId" value="${transferItems.skuId}"  style="width:70px;" readonly='readonly'></input></td>
					  				<td style='display:none'><input type="text" id="pid${status.index + 1 }" name="pid" value="${transferItems.pid}"  style="width:70px;" readonly='readonly'></input></td>
			  						<td style='display:none'><input type="text" id="price${status.index + 1 }" name="price" value="${transferItems.price}"  style="width:70px;" readonly='readonly'></input></td>
			  					</tr>
		  					</c:forEach>
		  				</tbody>
		  			</table>
		  		</div> 
			</div>
		</form>
			</div>
			<div class="er">
					<span>操作员:</span> <input type="text"  value="${transferOrder.createBy}" class="er_inp" readonly="readonly">
				</div>
		</div>
	
</body>
</html>