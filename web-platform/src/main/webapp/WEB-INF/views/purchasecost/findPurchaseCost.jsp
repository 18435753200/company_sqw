<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>费用归集</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link rel="stylesheet" type="text/css"
	href="<%=path%>/commons/css/reset.css">
<link rel="stylesheet" type="text/css"
	href="<%=path%>/commons/css/Storage.css">

<script src="${path}/commons/js/my97/WdatePicker.js"></script>
</head>

<script type="text/javascript">

	function selService() {
		var pro_array = new Array();
		if ($("#serName").val() != "" && $("#serName").val() != undefined) {
			pro_array.push("serviceName=" + $("#serName").val());
		}
		$.ajax({
			type : "post",
			url : "findServiceList",
			dataType : "html",
			data : pro_array.join("&"),
			success : function(msg) {
				$("#servicebox").html(msg);
				$("#serviceDiv").show();
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("对不起，数据异常请稍后再试!");
			}
		});

	}


	function selOrder() {
		var pro_array = new Array();
		if ($("#ChaseOrderId").val() != ""
				&& $("#ChaseOrderId").val() != undefined) {
			pro_array.push("businessNumber=" + $("#ChaseOrderId").val());
		}
		$.ajax({
			type : "post",
			url : "PChaseOrderList",
			dataType : "html",
			data : pro_array.join("&"),
			success : function(msg) {
				$("#orderbox").html(msg);
				$("#ChaseOrderDiv").show();
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("对不起，数据异常请稍后再试!");
			}
		});
	}

	function loadOrderPage() {
		var service = $('input[name="ChaseOrder"]:checked').val();
		var sers = service.split(",");
		$("#purchaseId").val(sers[0]);
		$("#createBy").val(sers[1]);
		$("#createTime").val(sers[2]);
		$("#ChaseOrderDiv").hide();
	}


	function loadPurchasePage() {
		var service = $('input[name="service"]:checked').val();
		var services = service.split(",");
		$("#supplierName").val(services[1]);
		$("#serviceDiv").hide();
	}
	
	
	function CreateRow() {
		var t01 = $("#detilTab tr").length;
		var html = "<tr><td>"
				+ t01
				+ "</td><td><input type=\"text\" name=\"costDate\" id=\"costDate"+t01+"\" value=\"\" style=\"border:0px;\"/></td><td><select name=\"costCode\" id=\"costCode"+t01+"\"><c:forEach items='${Cost }' var='Cost'><option value='${Cost.code }'>${Cost.costName }</option></c:forEach></select></td><td><input type=\"text\" name=\"waitAllocationPrice\" id=\"waitAllocationPrice"+t01+"\" value=\"\" style=\"border:0px;\"/></td><td><select name=\"allocationCode\" id=\"allocationCode"
				+ t01
				+ "\" onchange=\"allocationChange($(this).attr('id'))\"><c:forEach items='${Dictionarys }' var='Dictionary'><option value='${Dictionary.dictValue }'>${Dictionary.dictName }</option></c:forEach></select></td><td><input type=\"text\" name=\"allocationQty\" id=\"allocationQty"+t01+"\" value=\"\" style=\"border:0px;\"/></td><td><input type=\"text\" name=\"allocationTotalPrice\" id=\"allocationTotalPrice"+t01+"\" value=\"\" style=\"border:0px;\"/></td><td><input type=\"text\" name=\"allocationPrice\" id=\"allocationPrice"+t01+"\" value=\"\" style=\"border:0px;\"/></td><td><select name=\"currencyCode\" id=\"currencyCode"+t01+"\"><c:forEach items='${Currency }' var='currency'><option value='${currency.code }'>${currency.currencyType }</option></c:forEach></select></td><td><input type=\"text\" name=\"exchangeRate\" id=\"exchangeRate"+t01+"\" value=\"\" style=\"border:0px;\"/></td><td><input type=\"text\" name=\"localPrice\" id=\"localPrice"+t01+"\" value=\"\" style=\"border:0px;\"/></td><td><input type=\"text\" name=\"localTotalPrice\" id=\"localTotalPrice"+t01+"\" value=\"\" style=\"border:0px;\"/></td><td><input type=\"text\" name=\"imagePath\" id=\"imagePath"+t01+"\" value=\"\" style=\"border:0px;\"/></td></tr>";
		var tab = $("#detilTab");
		tab.append(html);
		pushValue($("input[name='PurchaseCostId']:checked").attr("checked"), $(
				"input[name='PurchaseCostId']:checked").attr("qty"), $(
				"input[name='PurchaseCostId']:checked").attr("price"));
	}
	
	
	function saveDetil() {
		var value = $("#detilMess").serialize();
		var s = '';
		var obj = document.getElementsByName('PurchaseCostId');
		for ( var i = 0; i < obj.length; i++) {
			if (obj[i].checked) {
				s += obj[i].value + ','; //如果选中，将value添加到变量s中 
			}
			//那么现在来检测s的值就知道选中的复选框的值了 
		}
		s = s.substring(0, s.length - 1);
		if (s == "") {
			alert("请选择入库通知记录!");
			return;
		}
		if (s.split(",").length > 1) {
			alert("请选择一调入库通知记录!");
			return;
		}
		if (s.split(",").length == 1) {
			var str = s.split(",")[0].split(",");

			$.ajax({
				type : "post",
				url : "savePurchaseCostDetil",
				dataType : "text",
				data : value + "&purchaseId=" + str,
				success : function(msg) {
					var tab = $("#detilTab");
					tab.hide();
				},
				error : function(jqXHR, textStatus, errorThrown) {
					alert("对不起，数据异常请稍后再试!");
				}
			});
		}

	}


	function editDetil() {
		var tab = $("#detilTab");
		tab.show();
	}


	function showCostList() {
		var pro_array = new Array();
		if ($("#purchaseId").val() != "" && $("#purchaseId").val() != undefined) {
			pro_array.push("purchaseId=" + $("#purchaseId").val());
		}
		if ($("#createBy").val() != "" && $("#createBy").val() != undefined) {
			pro_array.push("createBy=" + $("#createBy").val());
		}
		if ($("#startTime").val() != "" && $("#startTime").val() != undefined) {
			pro_array.push("startTime=" + $("#startTime").val());
		}
		if ($("#endTime").val() != "" && $("#endTime").val() != undefined) {
			pro_array.push("endTime=" + $("#endTime").val());
		}
		if ($("#supplierName").val() != ""
				&& $("#supplierName").val() != undefined) {
			pro_array.push("supplierName=" + $("#supplierName").val());
		}
		if ($("#totalPrice").val() != "" && $("#totalPrice").val() != undefined) {
			pro_array.push("totalPrice=" + $("#totalPrice").val());
		}
		$.ajax({
			type : "post",
			url : "findPurchaseCostList",
			dataType : "html",
			data : pro_array.join("&"),
			success : function(msg) {
				$("#purchase").html(msg);
				$("#fee").show();
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("对不起，数据异常请稍后再试!");
			}
		});
	}
	
	
	function clickSubmit(page) {
		var pro_array = new Array();
		if ($("#purchaseId").val() != "" && $("#purchaseId").val() != undefined) {
			pro_array.push("purchaseId=" + $("#purchaseId").val());
		}
		if ($("#createBy").val() != "" && $("#createBy").val() != undefined) {
			pro_array.push("createBy=" + $("#createBy").val());
		}
		if ($("#createTime").val() != "" && $("#createTime").val() != undefined) {
			pro_array.push("createTime=" + $("#createTime").val());
		}
		if ($("#supplierName").val() != ""
				&& $("#supplierName").val() != undefined) {
			pro_array.push("supplierName=" + $("#supplierName").val());
		}
		if ($("#totalPrice").val() != "" && $("#totalPrice").val() != undefined) {
			pro_array.push("totalPrice=" + $("#totalPrice").val());
		}
		if (page != "" && page != undefined) {
			pro_array.push("page=" + page);
		}
		$.ajax({
			type : "post",
			url : "findPurchaseCostList",
			dataType : "html",
			data : pro_array.join("&"),
			success : function(msg) {
				$("#purchase").html(msg);
				$("#fee").show();
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("对不起，数据异常请稍后再试!");
			}
		});
	}
	
	
	function checkPurchase() {
		var s = '';
		var obj = document.getElementsByName('PurchaseCostId');
		for ( var i = 0; i < obj.length; i++) {
			if (obj[i].checked) {
				s += obj[i].value + ','; //如果选中，将value添加到变量s中 
			}
			//那么现在来检测s的值就知道选中的复选框的值了 
		}
		s = s.substring(0, s.length - 1);
		if (s == "") {
			alert("请选择入库通知记录!");
			return;
		}
		if (s.split(",").length > 1) {
			alert("请选择一调入库通知记录!");
			return;
		}
		if (s.split(",").length == 1) {
			var str = s.split(",")[0].split(",");

			$.ajax({
				type : "post",
				url : "editPurchaseCostDetil",
				dataType : "text",
				data : "&id=" + str,
				success : function(msg) {
					var tab = $("#detilTab");
					tab.hide();
				},
				error : function(jqXHR, textStatus, errorThrown) {
					alert("对不起，数据异常请稍后再试!");
				}
			});
		}
	}
	
	
	function pushValue(checked, qty, price) {

		if (checked == 'checked') {

			$("select[name=allocationCode]")
					.each(
							function() {
								if ($(this).find("option:selected").val() == '001') {

									$(
											"#allocationQty"
													+ $(this).attr("id")
															.substring(14))
											.val(qty);
								} else if ($(this).find("option:selected")
										.val() == '002') {

								} else if ($(this).find("option:selected")
										.val() == '003') {
									$(
											"#allocationTotalPrice"
													+ $(this).attr("id")
															.substring(14))
											.val(price);
								}
							});
		} else {
			$("select[name=allocationCode]")
					.each(
							function() {
								if ($(this).find("option:selected").val() == '001') {

									$(
											"#allocationQty"
													+ $(this).attr("id")
															.substring(14))
											.val("");
								} else if ($(this).find("option:selected")
										.val() == '002') {

								} else if ($(this).find("option:selected")
										.val() == '003') {
									$(
											"#allocationTotalPrice"
													+ $(this).attr("id")
															.substring(14))
											.val("");
								}
							});
		}
	}
	
	
	function allocationChange(id) {

		if ($("#" + id).val() == '001') {
			if ($("input[name='PurchaseCostId']:checked").attr("qty") != undefined) {
				$("#allocationQty" + id.substring(14)).val(
						$("input[name='PurchaseCostId']:checked").attr("qty"));
				$("#allocationTotalPrice" + id.substring(14)).val("");
			}

		} else if ($("#" + id).val() == '002') {

		} else if ($("#" + id).val() == '003') {
			if ($("input[name='PurchaseCostId']:checked").attr("price") != undefined) {
				$("#allocationTotalPrice" + id.substring(14))
						.val(
								$("input[name='PurchaseCostId']:checked").attr(
										"price"));
				$("#allocationQty" + id.substring(14)).val("");
			}

		}
	}
	
	
	function getOrders(oo) {
		var obj = document.getElementsByName('PurchaseCostId'); //选择所有name="'test'"的对象，返回数组 
		//取到对象数组后，我们来循环检测它是不是被选中 
		var s = '';
		for ( var i = 0; i < obj.length; i++) {
			if (obj[i].checked) {
				s += obj[i].value + ',';
			}
			//如果选中，将value添加到变量s中 
		}
		s = s.substring(0, s.length - 1);
		if (s == '') {
			alert("请选择采购订单!");
			return;
		}
		oo.attr("href", "${path}/purchasecost/setCost?ids=" + s);
		oo.attr("target", "view_window");
		oo.click();
	}
</script>
<body>
	<!-- 导航 start -->
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 end -->
	<div class="center">
		<!-- 左边 start -->
		<div class="left f_l">
			<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>
		</div>

		<!-- 左边 end -->
		<div class="right">

			<div class="c21">
				<div class="title">
					<p>卖家中心&nbsp;&gt;&nbsp;</p>
					<p>商品采购&nbsp;&gt;&nbsp;</p>
					<p class="c1">费用归集</p>
				</div>
			</div>

			<div class="pu_hd">
				<h3>采购费用</h3>
			</div>
			<div class="xia">
				<p class="p1">
					<span>采购订单编号:</span> 
					<input type="text" name="purchaseId" id="purchaseId"> 
						
					<span>制单人:</span>
					<input type="text" name="createBy" id="createBy">

				</p>
				<p class="p1">
					<span>供应商名称:</span> 
					<input type="text" name="supplierName" id="supplierName"> 
						
					<span>金额:</span>
					<input type="text" name="totalPrice" id="totalPrice">
				</p>
				<p class="p1">
					<span>制单日期:</span>
					<input type="text" name="startTime" id="startTime" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\',{d:-1});}',dateFmt:'yyyy-MM-dd'})">
					<i>至</i> 
					<input type="text" name="endTime" id="endTime" onClick="WdatePicker({minDate:'#F{$dp.$D(\'startTime\',{d:1});}',dateFmt:'yyyy-MM-dd'})">
				</p>
				<p class="p2">
					<button type="button"  id ='select' name='name' onclick="showCostList();">查询</button>
				</p>
			</div>

			<!--采购明细开始-->
			<div id="purchase"></div>
			<!--采购明细结束-->

			<!--采购费用登记开始-->
			<!-- <div class="pu_wrap" style="display: none;" id="fee">
			  	<div class="pu_hd"><h3>采购费用登记</h3></div>
	  		  	<div class="pu_bd">
	  		  	<form id="detilMess">
  		  			<table id="detilTab" >
  		  				<tr class="order_hd">
  		  					<th width="40px;">序号</th>
  		  					<th width="70px;">费用日期</th>
  		  					<th width="70px;">费用名称</th>
  		  					<th width="70px;">待分担金额</th>
  		  					<th width="130px;">分摊方式</th>
  		  					<th width="70px;">分摊数量</th>
  		  					<th width="70px;">分摊总金额</th>
  		  					<th width="70px;">分摊金额</th>
  		  					<th width="80px;">币别</th>
  		  					<th width="60px;">汇率</th>
  		  					<th width="60px;">本币单价</th>
  		  					<th width="60px;">本币总价</th>
  		  					<th width="60px;">凭据摄影</th>
  		  				</tr>
  		  				<tr>
  		  					<td>1</td>
  		  					<td><input type="text" name="costDate" id="costDate1" value="" style="border:0px;"/></td>
  			  				<td><select name="costCode" id="costCode1"><c:forEach items="${Cost }" var="Cost"><option value="${Cost.code }">${Cost.costName }</option></c:forEach></select></td>
  			  				<td><input type="text" name="waitAllocationPrice" id="waitAllocationPrice1" value="" style="border:0px;"/></td>
  			  				<td><select name="allocationCode" id="allocationCode1" onchange="allocationChange($(this).attr('id'))"><c:forEach items="${Dictionarys }" var="Dictionary"><option value="${Dictionary.dictValue }">${Dictionary.dictName }</option></c:forEach></select></td>
  			  				<td><input type="text" name="allocationQty" id="allocationQty1" value="" style="border:0px;"/></td>
  			  				<td><input type="text" name="allocationTotalPrice" id="allocationTotalPrice1" value="" style="border:0px;"/></td>
  			  				<td><input type="text" name="allocationPrice" id="allocationPrice1" value="" style="border:0px;"/></td>
  			  				<td><select name="currencyCode" id="currencyCode1"><c:forEach items="${Currency }" var="currency"><option value="${currency.code }">${currency.currencyType }</option></c:forEach></select></td>
  			  				<td><input type="text" name="exchangeRate" id="exchangeRate1" value="" style="border:0px;"/></td>
  			  				<td><input type="text" name="localPrice" id="localPrice1" value="" style="border:0px;"/></td>
  			  				<td><input type="text" name="localTotalPrice" id="localTotalPrice1" value="" style="border:0px;"/></td>
  			  				<td><input type="text" name="imagePath" id="imagePath1" value="" style="border:0px;"/></td>
  		  				</tr>
  		  				
  		  			</table>
  		  			</form>
	  		  	</div>
	  		</div> -->
			<!--采购费用登记结束-->




			<!--国内运费登记结束-->


		</div>
	</div>


	<div class="alert_bu" id="serviceDiv" style="display: none;">

		<div class="bg"></div>

		<div class="wrap">

			<div class="pic"></div>

			<div class="box1">

				<div id="boxtitle">

					<h2>选择服务商.</h2>

				</div>

				<div id="suppliernametext" style="display: block;">

					<input type="text" id="serName">

					<button type="button" onclick="selService();">查询</button>

				</div>

			</div>

			<div class="box2" id="servicebox"></div>

			<div class="box3">



				<button type="button" class="bt1" id="warehouseclick"
					style="display: none;">确定</button>





				<button type="button" class="bt1" id="supplierclick" style=""
					onclick="loadPurchasePage()">确定</button>



			</div>

		</div>

	</div>

	<div class="alert_bu" id="ChaseOrderDiv" style="display: none;";>

		<div class="bg"></div>

		<div class="wrap">

			<div class="pic"></div>

			<div class="box1">

				<div id="boxtitle">

					<h2>选择采购单.</h2>

				</div>

				<div id="suppliernametext" style="display: block;">

					<input type="text" id="ChaseOrderId">

					<button type="button" onclick="selOrder();">查询</button>

				</div>

			</div>

			<div class="box2" id="orderbox"></div>

			<div class="box3">



				<button type="button" class="bt1" id="warehouseclick"
					style="display: none;">确定</button>





				<button type="button" class="bt1" id="supplierclick" style=""
					onclick="loadOrderPage()">确定</button>



			</div>

		</div>

	</div>
</body>
</html>