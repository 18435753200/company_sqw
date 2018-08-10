<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>采购差异登记</title>
	<%@include file="/WEB-INF/views/include/base.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/reset.css"> 
    <link rel="stylesheet" type="text/css" href="<%=path%>/commons/css/inventory.css">
    <style type="text/css">
    	.inputDisabled{background-color: #ebebe4}
    </style>
</head>
<script type="text/javascript">

function cleanDisabled(index,obj)
{
	if(obj.attr("checked"))
	{
		$("#errorQty"+index).attr("readonly",false);
		$("#errorQty"+index).removeClass("inputDisabled")
	}else{
		$("#errorQty"+index).val(0);
		$("#errorQty"+index).attr("readonly",true);
		$("#errorQty"+index).addClass("inputDisabled");
	}
	
}
function subForm()
{
	var len = $("#errTab tr").length;
	var r = /^[0-9]*[1-9][0-9]*$/;
	var count=0;
	for ( var i = 1; i <len; i++) {
		var tr = $('#errTab tr').eq(i);
		var st8 = tr.children('td').eq(3).find("input").val();
		var chk = tr.children('td').eq(1).find("input[name='errCheck']").attr("checked");
		//alert(chk);
		if(chk=="checked")
		{
			if(r.test(st8))
		{
			count=count+parseInt(st8);
		}else{
			alert("第"+i+"行请输入正整数!");
			return;
		}
		}
		
		
	}
	var err=parseInt($("#orderQty").val())-parseInt($("#stockInQty").val());
		if(count!=err)
		{
			alert("输入差异数量与订单数量减去实际入库数量不符合请重新填写");
			count=0;
			return ;
		}else{
			var data=$("#errForm").serialize();
			var chkTrue = "";
			
	$("input[type='checkbox'][name='errCheck']").each(function() {
		if (this.checked) {
			chkTrue = chkTrue + "1,";
		} else {
			chkTrue = chkTrue + "0,";
		}
	});

	chkTrue = chkTrue.substring(0, chkTrue.length - 1);
			$.ajax({
		type : "post", 
		url : "doSave", 
		dataType:"text",
		data:data+"&chkTrue="+chkTrue,
		success : function(msg) { 
		if(msg=='OK')
		{
			window.location.href="${path}/purchasebuy/findPurchaseBuy";
		}else{
			alert("对不起，数据异常请稍后再试!");
		}
			
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
		});
		}
}
</script>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 end -->
	<div class="center">
	<!-- 左边 start -->

			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp" %>
			
			<div class="right">
			<form id="errForm">
			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp; </p>
					<p>商品储存&nbsp;&gt;&nbsp; </p>
					<p class="c1">采购差异登记</p>
				</div>
		    </div>
			
			<div class="xia">
				<p class="p1">
					<span>采购订单号：</span>
					<input type="text" name="businessNumberChar" id="businessNumberChar" value="${itemDTO.businessNumberChar}" readonly="readonly">
				</p>
				<p class="p1">
					<span>商品编码：</span>
					<input type="text" name="pcode" id="pcode" value="${itemDTO.businessProdid }" readonly="readonly">
					<span>商品条码：</span>
					<input type="text" name="barCode" id="barCode" value="${itemDTO.barCode }" readonly="readonly">
				</p>
				<p class="p1">
					<span>商品名称：</span>
					<input type="text" style="width:428px;" name="pname" id="pname" value="${itemDTO.pname }" readonly="readonly">
				</p>
				<p class="p1">
					<span>订单数量：</span>
					<input type="text" name="orderQty" id="orderQty" value="${itemDTO.qty }" readonly="readonly">
					<span>实际入库数量：</span>
					<input type="text" name="stockInQty" id="stockInQty" value="${itemDTO.qtyReceived }" readonly="readonly">
					<span>差异数量：</span>
					<input type="text" name="err" id="err" value="${itemDTO.qty-itemDTO.qtyReceived }" readonly="readonly">
				</p>
				<input type="hidden" name="purchaseId" id="purchaseId" value="${itemDTO.purchaseId }" readonly="readonly"/>
				<input type="hidden" name="skuId" id="skuId" value="${itemDTO.skuId }" readonly="readonly"/>
			</div>


			<div class="pu_wrap">
				<div class="pu_hd" style="text-align:right;">
				   <div class="btn">
				   		<a href="javascript:void(0);" onclick="subForm();">保存</a>
				   </div>
				</div>
		  		<div class="pu_bd">
		  			<table id="errTab">
		  				<tbody>
		  				<tr class="order_hd">
		  					<th width="100px;">序号</th>
		  					<th width="100px;">选择</th>
		  					<th width="340px;">差异原因</th>
		  					<th width="300px;">差异数量</th>
		  				</tr>
		  				<c:forEach items="${errorDtos }" var="errorDtos" varStatus="status">
		  				<tr>
		  					<td>${status.index+1 }</td>
			  				<td><input type="checkbox" class="sm" onclick="cleanDisabled('${status.index+1 }',$(this))" name="errCheck"></td>
			  				<td><input type="hidden" name="errorCode" id="errorCode${status.index+1 }" value="${errorDtos.errorCode }" /><input type="text" name="errorName" id="errorName${status.index+1 }" value="${errorDtos.errorName }" style="text-align:center"/></td>
			  				<td><input type="text" class="inputDisabled" name="errorQty" id="errorQty${status.index+1 }" value="${errorDtos.errorQty }" style="text-align:center" readonly="readonly"/></td>
		  				</tr>
		  				</c:forEach>
		  			</tbody>
		  			</table>
		  		</div>
	        </div>
</form>

		</div>
</div>
		

	</div>
</body>
</html>