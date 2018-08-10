<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>新增发票登记</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/inventory.css">
     <script src="<%=path%>/commons/js/my97/WdatePicker.js"></script>
</head>
<script type="text/javascript">
$(document).ready(function(){
	//alert($("#purchase_entity",window.opener.document).val());
	//alert($("#purchase_entity",window.opener.document).find("option:selected").text());
	if($("#purchase_entity",window.opener.document).find("option:selected").text()=="")
	{
		$("#purchase_name").val($("#purchase_name",window.opener.document).val());
		$("#purchase_entity").val($("#purchase_entity",window.opener.document).val());
	}else{
		$("#purchase_name").val($("#purchase_entity",window.opener.document).find("option:selected").text());
		$("#purchase_entity").val($("#purchase_entity",window.opener.document).val());
	}
	$("#supplier_name").val($("#supplierName",window.opener.document).val());
	if($("#payment_function",window.opener.document).find("option:selected").text()=="")
	{
		$("#payment_name").val($("#payment_name",window.opener.document).val());
	}else{
		$("#payment_name").val($("#payment_function",window.opener.document).find("option:selected").text());
	}
	$("#payment_function").val($("#payment_function",window.opener.document).val());
});
function showOrder()
{
	var pro_array  = new Array();
	
	if($("#purchase_entity").val()!=""&&$("#purchase_entity").val()!=undefined)
	{
		pro_array.push("purchase_entity="+$("#purchase_entity").val());
	}
	
	if($("#supplier_name").val()!=""&&$("#supplier_name").val()!=undefined)
	{
		pro_array.push("supplier_name="+$("#supplier_name").val());
	}
	if($("#business_number_char").val()!=""&&$("#business_number_char").val()!=undefined)
	{
		pro_array.push("business_number_char="+$("#business_number_char").val());
	}
	
	if($("#startTime").val()!=""&&$("#startTime").val()!=undefined)
	{
		pro_array.push("startTime="+$("#startTime").val());
	}
	if($("#endTime").val()!=""&&$("#endTime").val()!=undefined)
	{
		pro_array.push("endTime="+$("#endTime").val());
	}
	if($("#sku_name_cn").val()!=""&&$("#sku_name_cn").val()!=undefined)
	{
		pro_array.push("sku_name_cn="+$("#sku_name_cn").val());
	}
	if($("#account_type").val()!=""&&$("#account_type").val()!=undefined)
	{
		pro_array.push("account_type="+$("#account_type").val());
	}
	if($("#payment_function").val()!=""&&$("#payment_function").val()!=undefined)
	{
		pro_array.push("payment_function="+$("#payment_function").val());
	}
	var url="";
	if($("#payment_function").val()=="002")
	{
		url="findFullOrderSku";
	}
	if($("#payment_function").val()=="001")
	{
		url="findByBatchOrderSku";
	}
	$.ajax({
		type : "post", 
		url : url, 
		dataType:"html",
		data:pro_array.join("&"),
		success : function(msg) { 
			$("#order").html(msg);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	});
}
function saveInvoice()
{
	var str=document.getElementsByName("order_check");
	var values="";
	var splet="";
	for(var i=0;i<str.length;i++)
	{
		if(str[i].checked == true)
		{
			values+=str[i].value+":";
			splet+=str[i].value.split(",")[3]+",";
		}
	}
	values=values.substring(0, values.length-1);
	splet=splet.substring(0, splet.length-1);
	var num1=splet.split(",")[0];
	var sarray=splet.split(",");
	for(var j=0;j<sarray.length;j++)
	{
		if(sarray[j]!=num1)
		{
			alert("采购单的供应商不同请选择统一供应商！！！！");
			return;
		}
	}
	//alert(window.encodeURI(values));
	//window.location.href="${path}/Invoice/InvoiceReg?values="+window.encodeURI(window.encodeURI(values))+"&payment_function="$("#payment_function").val();
	//showDialog("${path}/Invoice/InvoiceReg");
	
}
//function showDialog(url)
//{
//	var isChrome = window.navigator.userAgent.indexOf("Chrome") !=-1;
//	window.open(url,"登记发票","height="+window.screen.height*0.8+", width="+window.screen.width*0.8+", top="+(window.screen.availHeight-30-window.screen.height*0.8)/2+", left="+ (window.screen.availWidth-10-window.screen.width*0.8)/2+", toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");
	
//}
function opetatorCompe() {
	var str = document.getElementsByName("order_check");
	var objarray = str.length;
	// var chestr = "";
	for ( var i = 0; i < objarray; i++) {
		if (str[i].checked == true) {
			 var tr = $("#otab tr").eq(str[i].value);
			
			 var pWindow=window.dialogArguments;
			 if(pWindow != null){
				 
				 pWindow.doThingsAfterAdd(tr);
				 window.close();
			 }else{
				
				 window.opener.doThingsAfterAdd(tr);
				 window.close();
			 }
			//window.parent.document.test();
		}
	}
}
</script>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<div class="center">
		<div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp" %>
		</div>
		
		<!-- 左边 end -->
		<div class="right">
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp; </p>
					<p>商品储存&nbsp;&gt;&nbsp; </p>
					<p class="c1">采购订单查询</p>
				</div>
		    </div>

			<div class="pu_hd">
				<h3>查询条件</h3>
			</div>
			<div class="xia">
				<p class="p1">
					<span><em>*</em>公司名称：</span>
					<input type="text" name="purchase_name" id="purchase_name" readonly="readonly"/>
					<input type="hidden" name="purchase_entity" id="purchase_entity"/>
				</p>
				<p class="p1">
					<span>供应商名称：</span>
					<input type="text" class="w428" name="supplier_name" id="supplier_name">
				</p>
				
				
				<p class="p1">
					<!--  --><input type="hidden" name="account_type" id="account_type" value="1"/>
					<!-- <select name="account_type" id="account_type"><option value="1">未结清</option><option value="2">已结清</option></select> -->
					<span class="w140"><em>*</em>账期结算方法：</span>
					<input type="text" name="payment_name" id="payment_name" readonly="readonly"/>
					<input type="hidden" name="payment_function" id="payment_function"/>
					<!-- <select name="payment_function" id="payment_function"><option value="001">整批计算</option><option value="002">分批计算</option></select> -->
				</p>
				<p class="p2">
					<button type="button" onclick="showOrder();">查询</button>
				</p>
			</div>
			
			<div class="pu_h">
				   <h3>订单商品列表：</h3>
				   <div class="btn">
				       
				       <a href="javascript:void(0);" onclick="opetatorCompe();">确定</a>
				   </div>
			</div>
			<div class="pu_w">
			<div id="order">
			
			</div>
	  		<!--  <div class="pu_b">
	  			<table>
	  				<tbody>
	  				<tr class="order_hd">
	  					<th width="40px;">序号</th>
	  					<th width="40px;">选择</th>
	  					<th width="120px;">订单编号</th>
	  					<th width="120px;">商品编码</th>
	  					<th width="120px;">商品名称</th>
	  					<th width="120px;">商品条码</th>
	  					<th width="120px;">规格</th>
	  					<th width="100px;">单位</th>
	  					<th width="100px;">订单数量</th>
	  					<th width="100px;">已入库数量</th>
	  					<th width="100px;">已结算数量</th>
	  					<th width="100px;">已结算金额</th>
	  					<th width="100px;">未结算数量</th>
	  					<th width="100px;">未结算金额</th>
	  					<th width="110px;">批次号</th>
	  					<th width="100px;">入库日期</th>
	  					<th width="100px;">币别</th>
	  				</tr>
	  				<tr>
	  					<td>1</td>
		  				<td><input type="checkbox" class="sm"></td>
		  				<td></td>
		  				<td></td>
		  				<td></td>
		  				<td></td>
		  				<td></td>
		  				<td></td>
		  				<td></td>
		  				<td></td>
		  				<td></td>
		  				<td></td>
		  				<td></td>
		  				<td></td>
		  				<td></td>
		  				<td></td>
		  				<td></td>
	  				</tr>
	  			</tbody>
	  			</table>
	  		</div>-->
        </div>


		</div>
	</div>
</body>
</html>