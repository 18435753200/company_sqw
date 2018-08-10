<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>选择商品</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/inventory.css">
         <script src="<%=path%>/commons/js/my97/WdatePicker.js"></script>
     <script type="text/javascript" src="${path}/commons/js/map.js"></script>
     <script type="text/javascript">
     function showProList()
     {
     	var pro_array  = new Array();
	
	if($("#purchase_entity").val()!=""&&$("#purchase_entity").val()!=undefined)
	{
		pro_array.push("purchase_entity="+$("#purchase_entity").val());
	}
	
	if($("#business_number_char").val()!=""&&$("#business_number_char").val()!=undefined)
	{
		pro_array.push("business_number_char="+$("#business_number_char").val());
	}
	if($("#supplier_name").val()!=""&&$("#supplier_name").val()!=undefined)
	{
		pro_array.push("supplier_name="+$("#supplier_name").val());
	}
	
	if($("#startTime").val()!=""&&$("#startTime").val()!=undefined)
	{
		pro_array.push("startTime="+$("#startTime").val());
	}
	if($("#endTime").val()!=""&&$("#endTime").val()!=undefined)
	{
		pro_array.push("endTime="+$("#endTime").val());
	}
	if($("#business_prodid").val()!=""&&$("#business_prodid").val()!=undefined)
	{
		pro_array.push("business_prodid="+$("#business_prodid").val());
	}
	if($("#pname").val()!=""&&$("#pname").val()!=undefined)
	{
		pro_array.push("pname="+$("#pname").val());
	}
	if($("#bar_code").val()!=""&&$("#bar_code").val()!=undefined)
	{
		pro_array.push("bar_code="+$("#bar_code").val());
	}
	
	$.ajax({
		type : "post", 
		url : "selectProList", 
		dataType:"html",
		data:pro_array.join("&"),
		success : function(msg) { 
			$("#pro").html(msg);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	});
   }
     
     
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
</head>
<body>
	<div class="center">
		
		<div class="right">
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp; </p>
					<p>商品储存&nbsp;&gt;&nbsp; </p>
					<p class="c1">采购订单查询</p>
				</div>
		    </div>
			
			<div class="pu_hd">
				<h3>费用信息：</h3>
			</div>
			<div class="xia">
				<p class="p1">
					<span><em>*</em>公司名称：</span>
					<select class="w242" name="purchase_entity" id="purchase_entity"><c:forEach items="${Entity }" var="e"><option value="${e.dictValue }">${e.dictName }</option></c:forEach></select>
				</p>
				<p class="p1">
					<span>采购订单编号：</span>
					<input type="text" name="business_number_char" id="business_number_char">
					<span>供应商名称：</span>
					<input type="text" name="supplier_name" id="supplier_name">
				</p>
				<p class="p1">
					<span>商品编码：</span>
					<input type="text" name="business_prodid" id="business_prodid">
					<span>商品名称：</span>
					<input type="text" name="pname" id="pname">
					<span>商品条码：</span>
					<input type="text" name="bar_code" id="bar_code">
				</p>
				<p class="p1">
					<span>订单日期：</span><input type="text" name="startTime" id="startTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"><i>至</i>
					<input type="text" name="endTime" id="endTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});">
				</p>
				<p class="p2">
					<button type="button" onclick="showProList();">查询</button>
				</p>
			</div>
			
			<div class="pu_hd">
				   <h3>订单列表：</h3>
				   <div class="btn">
				       <a href="javascript:void(0);" onclick="opetatorCompe();">确定</a>
				   </div>
			</div>
			<div class="pu_wrap">
			<div id="pro"></div>
		  		<!-- <div class="pu_bd">
		  			<table>
		  				<tbody>
		  				<tr class="order_hd">
		  					<th width="40px;">序号</th>
		  					<th width="40px;">选择</th>
		  					<th width="120px;">订单编号</th>
		  					<th width="120px;">商品编号</th>
		  					<th width="120px;">商品条码</th>
		  					<th width="100px;">商品名称</th>
		  					<th width="60px;">数量</th>
		  					<th width="60px;">币别</th>
		  					<th width="60px;">金额</th>
		  					<th width="60px;">本币金额</th>
		  					<th width="60px;">分摊金额</th>
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
		  				</tr>
		  			</tbody>
		  			</table>
		  		</div> -->
        </div>


		</div>
	</div>
</body>
</html>