<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>费用发票登记查询</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/inventory.css">
     <script src="<%=path%>/commons/js/my97/WdatePicker.js"></script>
</head>
<script type="text/javascript">
function showInvoiceList()
{
	var pro_array  = new Array();
	
	if($("#companyCode").val()!=""&&$("#companyCode").val()!=undefined)
	{
		pro_array.push("companyCode="+$("#companyCode").val());
	}
	
	if($("#invoiceNumber").val()!=""&&$("#invoiceNumber").val()!=undefined)
	{
		pro_array.push("invoiceNumber="+$("#invoiceNumber").val());
	}
	if($("#invoiceChar").val()!=""&&$("#invoiceChar").val()!=undefined)
	{
		pro_array.push("invoiceChar="+$("#invoiceChar").val());
	}
	
	if($("#supplierName").val()!=""&&$("#supplierName").val()!=undefined)
	{
		pro_array.push("supplierName="+$("#supplierName").val());
	}
	if($("#startTime").val()!=""&&$("#startTime").val()!=undefined)
	{
		pro_array.push("startTime="+$("#startTime").val());
	}
	if($("#endTime").val()!=""&&$("#endTime").val()!=undefined)
	{
		pro_array.push("endTime="+$("#endTime").val());
	}
	if($("#status").val()!=""&&$("#status").val()!=undefined)
	{
		pro_array.push("status="+$("#status").val());
	}
	
	pro_array.push("invoiceType=2");
	var d1 = new Date($("#startTime").val().replace(/\-/g, "\/"));  
 	var d2 = new Date($("#endTime").val().replace(/\-/g, "\/"));  
	if(d1 >=d2)
	{
		alert("开始时间不能大于等于结束时间！");  
		return;
	}
	$.ajax({
		type : "post", 
		url : "findInvoiceOrderList", 
		dataType:"html",
		data:pro_array.join("&"),
		success : function(msg) { 
			$("#Invoice").html(msg);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	});
}
function clickSubmit(page)
{
	var pro_array  = new Array();
	
	if($("#companyCode").val()!=""&&$("#companyCode").val()!=undefined)
	{
		pro_array.push("companyCode="+$("#companyCode").val());
	}
	
	if($("#invoiceNumber").val()!=""&&$("#invoiceNumber").val()!=undefined)
	{
		pro_array.push("invoiceNumber="+$("#invoiceNumber").val());
	}
	if($("#invoiceChar").val()!=""&&$("#invoiceChar").val()!=undefined)
	{
		pro_array.push("invoiceChar="+$("#invoiceChar").val());
	}
	
	if($("#supplierName").val()!=""&&$("#supplierName").val()!=undefined)
	{
		pro_array.push("supplierName="+$("#supplierName").val());
	}
	if($("#startTime").val()!=""&&$("#startTime").val()!=undefined)
	{
		pro_array.push("startTime="+$("#startTime").val());
	}
	if($("#endTime").val()!=""&&$("#endTime").val()!=undefined)
	{
		pro_array.push("endTime="+$("#endTime").val());
	}
	if($("#status").val()!=""&&$("#status").val()!=undefined)
	{
		pro_array.push("status="+$("#status").val());
	}
	if(page!=""&&page!=undefined)
	{
		pro_array.push("page="+page);
	}
	var d1 = new Date($("#startTime").val().replace(/\-/g, "\/"));  
 	var d2 = new Date($("#endTime").val().replace(/\-/g, "\/"));  
	if(d1 >=d2)
	{
		alert("开始时间不能大于等于结束时间！");  
		return;
	}
	pro_array.push("invoiceType=2");
	$.ajax({
		type : "post", 
		url : "findInvoiceOrderList", 
		dataType:"html",
		data:pro_array.join("&"),
		success : function(msg) { 
			$("#Invoice").html(msg);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	});
}
function toNewOrder()
{
	window.location.href="${path}/purchasecost/CostReg";
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
					<p class="c1">费用发票登记查询</p>
				</div>
		    </div>

			<div class="xia">
				<p class="p1">
					<span><em>*</em>公司名称：</span>
					<select class="w242" name="companyCode" id="companyCode"><option value="">请选择</option><c:forEach items="${Entity }" var="e"><option value="${e.dictValue }">${e.dictName }</option></c:forEach></select>
					<span>发票号码：</span>
					<input type="text" class="w140" name="invoiceNumber" id="invoiceNumber">
					<span>发票登记编号：</span>
					<input type="text" class="w140" name="invoiceChar" id="invoiceChar">
				</p>
				<p class="p1">
					<span>供应商名称：</span>
					<input type="text" class="w428" name="supplierName" id="supplierName">
				</p>
				<p class="p1">
					<span>登记日期：</span>
					<input type="text" name="startTime" id="startTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"><i>至</i><input type="text" name="endTime" id="endTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});">
					<span>单据状态：</span>
					<select class="242" name="status" id="status"><option value="">请选择</option><option value="10">已审核</option><option value="1">未审核</option></select>
				</p>
				<p class="p2">
					<button type="button" onclick="showInvoiceList()">查询</button>
				</p>
			</div>
			
			<div class="pu_hd">
				   <h3>发票列表：</h3>
				   <div class="btn">
				       <div class="btn"><a href="javascript:void(0);" onclick="toNewOrder();">新增</a></div>
				   </div>
			</div>
			<div class="pu_wrap">
				<div id="Invoice">
	  		<!--  -->
	  		</div>
	  		<!-- <div class="pu_bd">
	  			<table>
	  				<tbody>
	  				<tr class="order_hd">
	  					<th width="40px;">序号</th>
	  					<th width="120px;">发票登记单编号</th>
	  					<th width="120px;">发票号码</th>
	  					<th width="180px;">供应商名称</th>
	  					<th width="100px;">发票金额(元)</th>
	  					<th width="100px;">登记日期</th>
	  					<th width="100px;">制单人</th>
	  					<th width="80px;">操作</th>
	  				</tr>
	  				<tr>
	  					<td>1</td>
		  				<td></td>
		  				<td></td>
		  				<td></td>
		  				<td></td>
		  				<td></td>
		  				<td></td>
		  				<td>
			  				<a href="#">修改</a>
			  				<a href="#">查看</a>
		  				</td>
		  			
	  				</tr>
	  			</tbody>
	  			</table>
	  		</div> -->
        </div>


		</div>
	</div>
</body>
</html>