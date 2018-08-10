<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>费用分摊计算</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/inventory.css">
         <script src="<%=path%>/commons/js/my97/WdatePicker.js"></script>
     <script type="text/javascript" src="${path}/commons/js/map.js"></script>
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
	var html = "<tr><td>"+th0+"</td><td><input type='checkbox' class='sm'></td><td><input type='text' name='business_number' id='business_number"+th0+"' readonly='readonly' value='"+obj.children('td').eq(2).text()+"'/></td><td><input type='text' name='pcode' id='pcode"+th0+"' readonly='readonly' value='"+obj.children('td').eq(3).text()+"'/></td><td><input type='text' name='barCode' id='barCode"+th0+"' readonly='readonly' value='"+obj.children('td').eq(4).text()+"'/></td><td><input type='text' name='pname' id='pname"+th0+"' readonly='readonly' value='"+obj.children('td').eq(5).text()+"'/></td><td><input type='text' name='qty' id='qty"+th0+"' readonly='readonly' value='"+obj.children('td').eq(6).text()+"'/></td><td><input type='text' name='currency_type' id='currency_type"+th0+"' readonly='readonly' value='"+obj.children('td').eq(7).text()+"'/></td><td><input type='text' name='price' id='price"+th0+"' readonly='readonly' value='"+obj.children('td').eq(8).text()+"'/></td><td><input type='text' name='localPrice' id='localPrice"+th0+"' readonly='readonly' value='"+obj.children('td').eq(9).text()+"'/></td><td><input type='text' name='allocationPrice' id='allocationPrice"+th0+"' readonly='readonly' value='"+obj.children('td').eq(10).text()+"'/></td><td><input type='text' name='sku_id' id='sku_id"+th0+"' readonly='readonly' value='"+obj.children('td').eq(11).text()+"'/></td></tr>";
	//var html = "<tr class='sotClass'><td>"+obj.children('td').eq(0).text().trim()+"</td><td><input type='checkbox' name='skuName' value='"+obj.children('td').eq(0).text().trim()+"'></td><td><input type='text' id='pcode"+obj.children('td').eq(0).text().trim()+"' name='pcode' value='"+obj.children('td').eq(2).text().trim()+"' readonly='readonly'></td><td><input type='text' id='barCode"+obj.children('td').eq(0).text().trim()+"' name='barCode' value='"+obj.children('td').eq(3).text().trim()+"'readonly='readonly'></td><td><input type='text' id='pname"+obj.children('td').eq(0).text().trim()+"' name='pname' value='"+obj.children('td').eq(4).text().trim()+"'readonly='readonly'></td><td><input type='text' id='format"+obj.children('td').eq(0).text().trim()+"' name='format' value='"+obj.children('td').eq(5).text().trim()+"'readonly='readonly'></td><td><input type='text' id='unit"+obj.children('td').eq(0).text().trim()+"' name='unit' value='"+obj.children('td').eq(6).text().trim()+"'readonly='readonly'></td><td><input type='text' id='stockQty"+obj.children('td').eq(0).text().trim()+"' name='stockQty' value='"+obj.children('td').eq(7).text().trim()+"'readonly='readonly'></td><td><input type='text' id='transferQty"+obj.children('td').eq(0).text().trim()+"' name='transferQty'  onblur='onblurInfo("+obj.children('td').eq(0).text().trim()+");'  value=''></td><td><input type='text' id='isgenuine"+obj.children('td').eq(0).text().trim()+"' name='isgenuine' value='"+obj.children('td').eq(11).text().trim()+"'readonly='readonly'></td><td><input type='text' id='batchNumber"+obj.children('td').eq(0).text().trim()+"' name='batchNumber' value='"+obj.children('td').eq(8).text().trim()+"'readonly='readonly'></td><td><input type='text' id='batchId"+obj.children('td').eq(0).text().trim()+"' name='batchId' value='"+obj.children('td').eq(9).text().trim()+"'readonly='readonly'></td><td><input type='text' id='productionDate"+obj.children('td').eq(0).text().trim()+"' name='productionDate' value='"+obj.children('td').eq(10).text().trim()+"'readonly='readonly'></td><td><input type='text' id='skuId"+obj.children('td').eq(0).text().trim()+"' name='skuId' value='"+obj.children('td').eq(12).text().trim()+"'readonly='readonly'></td><td><input type='text' id='pid"+obj.children('td').eq(0).text().trim()+"' name='pid' value='"+obj.children('td').eq(13).text().trim()+"'readonly='readonly'></td></tr>";
	
	return html;
}
function doThingsAfterAdd(config){
	
	var len = $("#sho tr").length;
	//alert(config);
	if(checkAdd(config))
	{
		var htm=appendNewTr(config);	
	//alert(htm);
		$("#sho").append(htm);
	}else{
		alert("已经存在此商品信息");
	}
	
}
function checkAdd(config)
{
	var Tablen=$("#sho tr").length;
	var map = new Map();
	for(var i=1;i<Tablen;i++)
    {
		var tr = $("#sho tr").eq(i);
		var businessNumberChar = tr.children('td').eq(2).find("input").val();
		var skuId = tr.children('td').eq(11).find("input").val();
		map.put(businessNumberChar,skuId);
	}
	if(map.get(config.children('td').eq(2).text())==config.children('td').eq(11).text())
	{
		return false;
	}else{
		return true;
	}
}

     	function add()
     	{
     		showDialog("${path}/purchasecost/selectPro");
     	}
     function showDialog(url)
	{
	var isChrome = window.navigator.userAgent.indexOf("Chrome") !=-1;
	window.open(url,"登记发票","height="+window.screen.height*0.8+", width="+window.screen.width*0.8+", top="+(window.screen.availHeight-30-window.screen.height*0.8)/2+", left="+ (window.screen.availWidth-10-window.screen.width*0.8)/2+", toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");
	
	}
	
	function allOcation()
	{
		var data=$("#OcationForm").serialize();
		$.ajax({
		type : "post", 
		url : "setallOcation", 
		dataType:"json",
		data:data,
		success : function(msg) { 
			if(msg.success){
				var liverData = msg.dataMap.dataMap;
				var Tablen=$("#sho tr").length;
				$.each(liverData, function(i,v){
	            			
	            			for ( var i = 1; i < Tablen; i++) {
	            				var tr = $("#sho tr").eq(i);
								var op11 = tr.children('td').eq(11).find("input").val();
								var op10 = tr.children('td').eq(10).find("input");
								
								if(op11 ==v.skuId){
									op10.val(v.sharedFee);
									
								}
							}
	            	});
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	});
	}
	
	function Hand()
	{
		var chk=$("#shareHand").attr("checked");
		if(chk=="checked")
		{
			var Tablen=$("#sho tr").length;
			for(var i=1;i<Tablen;i++)
    		{
				var tr = $("#sho tr").eq(i);
				var fee=tr.children('td').eq(10).find("input");
				fee.val("");
				fee.attr("readonly",false);
			}
		}else{
			var Tablen=$("#sho tr").length;
			for(var i=1;i<Tablen;i++)
    		{
				var tr = $("#sho tr").eq(i);
				var fee=tr.children('td').eq(10).find("input");
				fee.val("");
				fee.attr("readonly",true);
			}
		}
		
		
	}
	function doSave()
	{
	if($("#supplierName").val()=="")
{
	alert("请选择服务商！！！！");
	return ;
}
		var invoiceTotalPrice=parseFloat($("#invoiceTotalPrice").val());
		var Tablen=$("#sho tr").length;
		var sumFee=0.0;
		for(var i=1;i<Tablen;i++)
    		{
				var tr = $("#sho tr").eq(i);
				var fee=parseFloat(tr.children('td').eq(10).find("input").val());
				sumFee+=fee;
			}
		if(invoiceTotalPrice!=sumFee)
		{
			alert("分摊金额存在差异建议手工填写");
			return;
		}
		var data=$("#OcationForm").serialize();
		$.ajax({
		type : "post", 
		url : "saveSkuAllocation", 
		dataType:"text",
		data:data,
		success : function(msg) { 
			if(msg=="OK"){
				window.close();
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	});
	}
     </script>
</head>
<body>
	<div class="center">
		
		<div class="right">
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp; </p>
					<p>商品储存&nbsp;&gt;&nbsp; </p>
					<p class="c1">费用分摊计算</p>
				</div>
		    </div>
			
			<div class="pu_hd">
				<h3>费用信息：</h3>
			</div>
			<form id="OcationForm">
			<div class="xia">
				<p class="p1">
					<span>费用名称：</span>
					<input type="text" name="costName" id="costName" value="${costName }" readonly="readonly">
					<input type="hidden" name="costCode" id="costCode" value="${costCode }">
					<span>分摊方法：</span>
					<input type="text" name="functionText" id="functionText" value="${functionText}" readonly="readonly">
					<input type="hidden" name="functionCode" id="functionCode" value="${functionCode}">
				</p>
				<p class="p1">
					<span>分摊金额：</span>
					<input type="text" name="invoiceTotalPrice" id="invoiceTotalPrice" value="${invoiceTotalPrice }" readonly="readonly">
					<input type="hidden" name="sid" id="sid" value="${sid}">
				</p>
			
			</div>
			
			<div class="pu_hd">
				   <h3>分摊列表：</h3>
				   <div class="btn">
				   	   <input type="checkbox" name="shareHand" id="shareHand" onclick="Hand();">手工分摊
				       <a href="javascript:void(0);" onclick="add();">添加</a>
				       <a href="javascript:void(0);" onclick="doSave();">保存</a>
				       <a href="javascript:void(0);" onclick="allOcation();">计算分摊</a>
				   </div>
			</div>
			<div class="pu_wrap">
				
	  		<div class="pu_bd">
	  			<table id="sho">
	  				<tbody>
	  				<tr class="order_hd">
	  					<th width="40px;">序号</th>
	  					<th width="40px;">选择</th>
	  					<th width="100px;">订单编号</th>
	  					<th width="100px;">商品编号</th>
	  					<th width="100px;">商品条码</th>
	  					<th width="100px;">商品名称</th>
	  					<th width="60px;">数量</th>
	  					<th width="60px;">币别</th>
	  					<th width="60px;">金额</th>
	  					<th width="60px;">本币金额</th>
	  					<th width="60px;">分摊金额</th>
	  					<th width="60px;" >sku_id</th>
	  				</tr>
	  				 
	  				<!-- <tr>
	  					<td>1</td>
		  				<td><input type="checkbox" class="sm"></td>
		  				<td><input type="text" name="business_number" id="business_number" readonly="readonly"/></td>
		  				<td><input type="text" name="pcode" id="pcode" readonly="readonly"/></td>
		  				<td><input type="text" name="barCode" id="barCode" readonly="readonly"/></td>
		  				<td><input type="text" name="pname" id="pname" readonly="readonly"/></td>
		  				<td><input type="text" name="qty" id="qty" readonly="readonly"/></td>
		  				<td><input type="text" name="currency_type" id="currency_type" readonly="readonly"/></td>
		  				<td><input type="text" name="price" id="price" readonly="readonly"/></td>
		  				<td><input type="text" name="localPrice" id="localPrice" readonly="readonly"/></td>
		  				<td><input type="text" name="allocationPrice" id="allocationPrice" readonly="readonly"/></td>
		  				<td><input type="text" name="sku_id" id="sku_id" readonly="readonly"/></td>
	  				</tr> -->
	  				
	  			</tbody>
	  			</table>
	  		</div>
        </div>
</form>

		</div>
	</div>
</body>
</html>