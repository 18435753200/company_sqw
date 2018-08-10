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
	
	<script type="text/javascript" src="${path}/commons/js/my97/WdatePicker.js"></script>
	<script type="text/javascript" src="${path}/commons/js/allocateNotifyEdit.js"></script>
	<script type="text/javascript" src="${path}/commons/js/map.js"></script>
	<script type="text/javascript" src="${path}/commons/js/allocatePrdBz_new.js"></script>
	<script type="text/javascript">
	function appendNewTr(obj) {
	var th0;
	if($("#sho tr:last").find("td:eq(0)").text()=="")
	{
		th0=1;
	}else{
		th0=parseInt($("#sho tr:last").find("td:eq(0)").text())+1;
	}
	//var html = "<tr><td >"+obj.children('td').eq(0).text()+"</td><td ><input type='checkbox' name='skuName' value='"+obj.children('td').eq(0).text()+"'></td><td >"+obj.children('td').eq(2).text()+"</td><td >"+obj.children('td').eq(3).text()+"</td><td >"+obj.children('td').eq(4).text()+"</td><td >"+obj.children('td').eq(5).text()+"</td><td >"+obj.children('td').eq(6).text()+"</td><td >"+obj.children('td').eq(7).text()+"</td><td ><input type='text' name='allQty' id='allQty"+obj.children('td').eq(0).text()+"'/></td><td >"+obj.children('td').eq(11).text()+"</td><td >"+obj.children('td').eq(8).text()+"</td><td >"+obj.children('td').eq(9).text()+"</td><td >"+obj.children('td').eq(10).text()+"</td><td >"+obj.children('td').eq(12).text()+"</td><td >"+obj.children('td').eq(13).text()+"</td></tr>";
	//var html = "<tr><td>"+obj.children('td').eq(0).text()+"</td><td><input type='checkbox' name='skuName' value='"+obj.children('td').eq(0).text()+"'></td><td><input type='text' id='pcode"+obj.children('td').eq(0).text()+"' name='pcode"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(2).text()+"' readonly='readonly'></td><td><input type='text' id='barCode"+obj.children('td').eq(0).text()+"' name='barCode"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(3).text()+"'readonly='readonly'></td><td><input type='text' id='pname"+obj.children('td').eq(0).text()+"' name='pname"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(4).text()+"'readonly='readonly'></td><td><input type='text' id='format"+obj.children('td').eq(0).text()+"' name='format"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(5).text()+"'readonly='readonly'></td><td><input type='text' id='unit"+obj.children('td').eq(0).text()+"' name='unit"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(6).text()+"'readonly='readonly'></td><td><input type='text' id='stockQty"+obj.children('td').eq(0).text()+"' name='stockQty"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(7).text()+"'readonly='readonly'></td><td><input type='text' id='transferQty"+obj.children('td').eq(0).text()+"' name='transferQty"+obj.children('td').eq(0).text()+"' value=''></td><td><input type='text' id='isgenuine"+obj.children('td').eq(0).text()+"' name='isgenuine"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(11).text()+"'readonly='readonly'></td><td><input type='text' id='batchNumber"+obj.children('td').eq(0).text()+"' name='batchNumber"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(8).text()+"'readonly='readonly'></td><td><input type='text' id='batchId"+obj.children('td').eq(0).text()+"' name='batchId"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(9).text()+"'readonly='readonly'></td><td><input type='text' id='productionDate"+obj.children('td').eq(0).text()+"' name='productionDate"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(10).text()+"'readonly='readonly'></td><td><input type='text' id='skuId"+obj.children('td').eq(0).text()+"' name='skuId"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(12).text()+"'readonly='readonly'></td><td><input type='text' id='pid"+obj.children('td').eq(0).text()+"' name='pid"+obj.children('td').eq(0).text()+"' value='"+obj.children('td').eq(13).text()+"'readonly='readonly'></td></tr>";
	// var html = "<tr class='sotClass'><td>"+th0+"</td><td><input type='checkbox' name='skuName' value='"+th0+"' class='sm'></td><td><input type='text' id='pcode"+th0+"' name='pcode' value='"+obj.children('td').eq(2).text().trim()+"' readonly='readonly'></td><td><input type='text' id='barCode"+th0+"' name='barCode' value='"+obj.children('td').eq(3).text().trim()+"'readonly='readonly'></td><td><input type='text' id='pname"+th0+"' name='pname' value='"+obj.children('td').eq(4).text().trim()+"'readonly='readonly'></td><td><input type='text' id='format"+th0+"' name='format' value='"+obj.children('td').eq(5).text().trim()+"'readonly='readonly'></td><td><input type='text' id='unit"+th0+"' name='unit' value='"+obj.children('td').eq(6).text().trim()+"'readonly='readonly'></td><td><input type='text' id='stockQty"+th0+"' name='stockQty' value='"+obj.children('td').eq(7).text().trim()+"'readonly='readonly'></td><td><input type='text' id='transferQty"+th0+"' name='transferQty'  onblur='onblurInfo($(this),"+obj.children('td').eq(7).text().trim()+");'  value=''></td><td><input type='text' id='isgenuine"+th0+"' name='isgenuine' value='"+obj.children('td').eq(11).text().trim()+"'readonly='readonly'></td><td><input type='text' id='batchNumber"+th0+"' name='batchNumber' value='"+obj.children('td').eq(8).text().trim()+"'readonly='readonly'></td><td><input type='text' id='batchId"+th0+"' name='batchId' value='"+obj.children('td').eq(9).text().trim()+"'readonly='readonly'></td><td><input type='text' id='productionDate"+th0+"' name='productionDate' value='"+obj.children('td').eq(10).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='skuId"+th0+"' name='skuId' value='"+obj.children('td').eq(12).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='pid"+th0+"' name='pid' value='"+obj.children('td').eq(15).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='price"+th0+"' name='price' value='"+obj.children('td').eq(16).text().trim()+"'readonly='readonly'></td></tr>";
	var html = "<tr class='sotClass'><td>"+th0+"</td><td><input type='checkbox' name='skuName' value='"+obj.children('td').eq(7).text().trim()+"' class='sm'></td><td><input type='text' id='pcode"+th0+"' name='pcode' value='"+obj.children('td').eq(2).text().trim()+"' readonly='readonly'></td><td><input type='text' id='barCode"+th0+"' name='barCode' value='"+obj.children('td').eq(3).text().trim()+"'readonly='readonly'></td><td><input type='text' id='pname"+th0+"' name='pname' value='"+obj.children('td').eq(4).text().trim()+"'readonly='readonly'></td><td><input type='text' id='format"+th0+"' name='format' value='"+obj.children('td').eq(7).text().trim()+"'readonly='readonly'></td><td><input type='text' id='unit"+th0+"' name='unit' value='"+obj.children('td').eq(8).text().trim()+"'readonly='readonly'></td><td><input type='text' id='stockQty"+th0+"' name='stockQty' value='"+obj.children('td').eq(9).text().trim()+"'readonly='readonly'></td><td><input type='text' id='transferQty"+th0+"' name='transferQty'  onblur='onblurInfo($(this),"+obj.children('td').eq(9).text().trim()+");'  value=''></td><td><input type='text' id='isgenuine"+th0+"' name='isgenuine' value='"+obj.children('td').eq(13).text().trim()+"'readonly='readonly'></td><td><input type='text' id='batchNumber"+th0+"' name='batchNumber' value='"+obj.children('td').eq(10).text().trim()+"'readonly='readonly'></td><td><input type='text' id='batchId"+th0+"' name='batchId' value='"+obj.children('td').eq(12).text().trim()+"'readonly='readonly'></td><td><input type='text' id='productionDate"+th0+"' name='productionDate' value='"+obj.children('td').eq(11).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='skuId"+th0+"' name='skuId' value='"+obj.children('td').eq(5).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='pid"+th0+"' name='pid' value='"+obj.children('td').eq(15).text().trim()+"'readonly='readonly'></td><td style='display:none'><input type='text' id='price"+th0+"' name='price' value='"+obj.children('td').eq(16).text().trim()+"'readonly='readonly'></td></tr>";
	//var html = "<tr class='sotClass'><td>"+obj.children('td').eq(0).text().trim()+"</td><td><input type='checkbox' name='skuName' value='"+obj.children('td').eq(0).text().trim()+"'></td><td><input type='text' id='pcode"+obj.children('td').eq(0).text().trim()+"' name='pcode' value='"+obj.children('td').eq(2).text().trim()+"' readonly='readonly'></td><td><input type='text' id='barCode"+obj.children('td').eq(0).text().trim()+"' name='barCode' value='"+obj.children('td').eq(3).text().trim()+"'readonly='readonly'></td><td><input type='text' id='pname"+obj.children('td').eq(0).text().trim()+"' name='pname' value='"+obj.children('td').eq(4).text().trim()+"'readonly='readonly'></td><td><input type='text' id='format"+obj.children('td').eq(0).text().trim()+"' name='format' value='"+obj.children('td').eq(5).text().trim()+"'readonly='readonly'></td><td><input type='text' id='unit"+obj.children('td').eq(0).text().trim()+"' name='unit' value='"+obj.children('td').eq(6).text().trim()+"'readonly='readonly'></td><td><input type='text' id='stockQty"+obj.children('td').eq(0).text().trim()+"' name='stockQty' value='"+obj.children('td').eq(7).text().trim()+"'readonly='readonly'></td><td><input type='text' id='transferQty"+obj.children('td').eq(0).text().trim()+"' name='transferQty'  onblur='onblurInfo("+obj.children('td').eq(0).text().trim()+");'  value=''></td><td><input type='text' id='isgenuine"+obj.children('td').eq(0).text().trim()+"' name='isgenuine' value='"+obj.children('td').eq(11).text().trim()+"'readonly='readonly'></td><td><input type='text' id='batchNumber"+obj.children('td').eq(0).text().trim()+"' name='batchNumber' value='"+obj.children('td').eq(8).text().trim()+"'readonly='readonly'></td><td><input type='text' id='batchId"+obj.children('td').eq(0).text().trim()+"' name='batchId' value='"+obj.children('td').eq(9).text().trim()+"'readonly='readonly'></td><td><input type='text' id='productionDate"+obj.children('td').eq(0).text().trim()+"' name='productionDate' value='"+obj.children('td').eq(10).text().trim()+"'readonly='readonly'></td><td><input type='text' id='skuId"+obj.children('td').eq(0).text().trim()+"' name='skuId' value='"+obj.children('td').eq(12).text().trim()+"'readonly='readonly'></td><td><input type='text' id='pid"+obj.children('td').eq(0).text().trim()+"' name='pid' value='"+obj.children('td').eq(13).text().trim()+"'readonly='readonly'></td></tr>";
	return html;
}
	function doThingsAfterAdd(config,skuID,butchNo,isgenuine){
		var len = $("#sho tr").length;
		var map = new Map();
		for ( var i = 1; i <= len; i++) {
			var tr = $('#sho tr').eq(i);
			var st8 = tr.children('td').eq(10).find("input").val();
			var st9 = tr.children('td').eq(9).find("input").val();
			var st12 = tr.children('td').eq(13).find("input").val();
			
			map.put(st12+st9,st8);
		}
		
		if(map.get(skuID+isgenuine)!=butchNo){
			$("#sho").append(appendNewTr(config));
			
			var transferOutWarehouseCodes = $("#transferOutWarehouseCode").val();
			var transferOutWarehouseNames = $("#transferOutWarehouseCode").find(
					"option:selected").text();
			var transferInWarehouseCodes = $("#transferInWarehouseCode").val();
			var transferInWarehouseNames = $("#transferInWarehouseCode").find(
					"option:selected").text();
		
			$("#transferOutWarehouseCodes").val(transferOutWarehouseCodes);
			$("#transferInWarehouseCodes").val(transferInWarehouseCodes);
			$("#transferOutWarehouseNames").val(transferOutWarehouseNames);
			$("#transferInWarehouseNames").val(transferInWarehouseNames);
	
			$("#transferOutWarehouseCode").attr("disabled","true");
			$("#transferInWarehouseCode").attr("disabled","true");
		}
		
	}
	
	function onblurInfo(index,value){
		var reg=/^[0-9]+$/;
		
    	if(index.val()=="" || !index.val().match(reg))
    	{
    		alert("请填写申请数量并且为正整数!");
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
		
		<form id="addAllocatePrdFrom">
			<div class="pu_hd">
				<h3>表头信息</h3>
				<div class="btn">
					<a href="javascript:void(0)" onclick="save()">保存</a>
					<!--  
					<a href="javascript:void(0)" onclick="audit()">审核</a>
					-->
					
					<!--
					<button  type="button" id="save" name="save"  value="保存" onclick="save()">保存</button>
					<button  type="button" id="audit" name="audit"  value="审核" onclick="audit()">审核</button>
					 -->
					<input type="hidden" id="transferOutWarehouseNames" name="transferOutWarehouseNames">
					<input type="hidden" id="transferInWarehouseNames" name="transferInWarehouseNames">
					<input type="hidden" id="transferOutWarehouseCodes" name="transferOutWarehouseCodes">
					<input type="hidden" id="transferInWarehouseCodes" name="transferInWarehouseCodes">
					<!-- 提交判断是否选中 -->
					<input type="hidden" id="chkTrue" name="chkTrue">
				</div>
			</div>

			<div class="xia">
				<p class="p1">
					<span>调拨单编号:</span> 
					<input type="text" id="transferNoChar" name="transferNoChar" value="${transferNoChar}" readonly="readonly"/> 
					
					<span>调拨时间:</span> 
					<input type="text" name="createTime" id="createTime" onClick="WdatePicker({el:'createTime',dateFmt:'yyyy-MM-dd'})">
					<!-- <input type="text" name="allocateDate" id="allocateDate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"> -->
				</p>
				
				<p class="p1">
					<span>调出库房:</span> 
					<select id="transferOutWarehouseCode" name="transferOutWarehouseCode">
						<option value="">请选择</option>
						<c:forEach items="${warehouseList }" var="warehouse">
							<option value="${warehouse.warehouseCode }">${warehouse.warehouseName}</option>
						</c:forEach>
						<option value="2">上海仓</option>
					</select> 
					
					<span>调入库房:</span> 
					<select id="transferInWarehouseCode" name="transferInWarehouseCode">
						<option value="">请选择</option>
						<c:forEach items="${warehouseList }" var="warehouse">
							<option value="${warehouse.warehouseCode }">${warehouse.warehouseName}</option>
						</c:forEach>
					</select> 
				</p>
			</div>
			
			<div class="pu_h">
					<h3>调拨商品列表</h3>
					<div class="btn">
						<!--<input  type="button" id="operation" name="operation"  value="操作" onclick="operationPage()"></input>
						<input  type="button" id="del" name="del"  value="删除" onclick="delOperation()"></input>-->
						<a href="javascript:void(0)" onclick="operationPage()">操作</a>
						<a href="javascript:void(0)" onclick="delOperation()">删除</a>
					</div>
			</div>
			<div class="pu_w">
			
				
				<div class="pu_b">
		  			<table id ="sho">
		  				<tbody>
			  				<tr class="order_hd">
			  					<th width="40px;">序号</th>
			  					<th width="40px;">选择</th>
			  					<th width="100px;">商品编码</th>
			  					<th width="100px;">商品条码</th>
			  					<th width="80px;">商品名称</th>
			  					<th width="40px;">规格</th>
			  					<th width="40px;">单位</th>
			  					<th width="60px;">库存数量</th>
			  					<th width="60px;">调拨数量</th>
			  					<th width="70px;">商品状态</th>
			  					<th width="70px;">批次号</th>
			  					<th width="70px;">生产批号</th>
			  					<th width="70px;">生产日期</th>
			  					<th width="70px;"  style="display:none">skuId</th>
			  					<th width="70px;" style="display:none">商品ID</th>
			  					<th width="60px;" style="display:none">单价</th>
			  				</tr>
			  				
		  				</tbody>
		  			</table>
		  		</div> 
		  		
		  		
			</div>
			<div class="er">
					<span>操作员:</span> <input type="text"  id ="createBy" name="createBy"  value="${createBy}" class="er_inp" readonly="readonly">
			</div>
		</form>
			
		</div>
	</div>
</body>
</html>