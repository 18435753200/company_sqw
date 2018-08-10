<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
<title>采购费用设置</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link rel="stylesheet" type="text/css"
	href="<%=path%>/commons/css/reset.css">
<link rel="stylesheet" type="text/css"
	href="<%=path%>/commons/css/Storage.css">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script src="<%=path%>/commons/js/my97/WdatePicker.js"></script>
<script type="text/javascript"
	src="<%=path%>/commons/js/uploadify/jquery.uploadify.js"></script>
<link rel="stylesheet"
	href="<%=path%>/commons/js/uploadify/uploadify.css">
</head>
<script type="text/javascript">
 	 var id='${ids}';
  	 var qty='${qty}';
  	 var price='${price}';
  	 
  	 var rowNum=0;
	 $(document).ready(function(){
	  	//CreateRow();
		var exchangeRate=$("#currencyCode1").find("option:selected").attr("song");
		$("#exchangeRate1").val(exchangeRate);
		pushValue('checked',qty,price);
	            $("#upfile").uploadify({
	        	'height': 24,
	            'swf': '<%=path%>/commons/js/uploadify/uploadify.swf',
	            'uploader': '<%=path%>/purchasecost/uploadCost',
	            'width': 63,
	            'cancelImg': '<%=path%>/commons/js/uploadify-cancel.png',
											'multi ' : false,
											'auto' : true,
											file_size_limit : "1024K",
											fileTypeExts : '*.gif;*.jpg;*.jpeg;*.png',
											file_types : "*.jpg;*.png;*.jpeg;",
											file_types_description : "*.jpg;*.jpeg;*.png;*.JPG;*.JPEG;*.PNG;",
											onUploadSuccess : function(file,
													data, response) {
												$("#imagePath" + rowNum).val(
														data);
												//alert('The file ' + file.name + ' was successfully uploaded with a response of ' + response + ':' + data); 
											},
											onUploadComplete : function() {
												closeWin();
											}
										});
					});

	function tan(row) {
		$(".alert_upload").show();
		rowNum = row;
	}

	function closeWin() {
		$(".alert_upload").hide();
	}

	function getFileName() {
		$("#filePath").val($("#upfile").val());
	}

	function totalAllocation(value, row, id) {
		if ($("#allocationCode" + row).val() == '001') {
			$("#allocationPrice" + row).val(
					parseFloat(value / $("#allocationQty" + row).val())
							.toFixed(2));
		} else if ($("#allocationCode" + row).val() == '003') {
			$("#allocationPrice" + row).val(
					parseFloat(value / $("#allocationQty" + row).val())
							.toFixed(2));
		} else if ($("#allocationCode" + row).val() == '002') {

		}
	}

	function selCurrency(exchangeRate, row) {
		$("#exchangeRate" + row).val(exchangeRate);
	}

	function DeleteRow() {
		var num = 0;
		$(':checkbox[name=delBox]').each(function() {
			if ($(this).attr('checked')) {
				$(this).closest('tr').remove();
				num++;
			}
		});
	}

	function CreateRow() {
		var t01;
		if ($("#detilTab tr:last").find("td:eq(0)").text() == '') {
			t01 = 1;
		} else {
			t01 = parseInt($("#detilTab tr:last").find("td:eq(0)").text()) + 1;
		}
		var html = "<tr><td>"
				+ t01
				+ "</td><td><input type=\"checkbox\" name=\"delBox\" id=\"delBox"
				+ t01
				+ "\" value=\""
				+ (t01)
				+ "\" style=\"border:0px;\" class=\"sm\"/></td><td><a href=\"\" onclick=\"openWindow("
				+ t01
				+ ",$(this));\">分摊</a></div></td><td><input type=\"text\" name=\"costDate\" id=\"costDate"
				+ t01
				+ "\" value=\"\" style=\"border:0px;\" onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd'});\"/></td><td><select name=\"costCode\" id=\"costCode"+t01+"\"><c:forEach items='${Cost }' var='Cost'><option value='${Cost.code }'>${Cost.costName }</option></c:forEach></select></td><td><input type=\"text\" name=\"totalFee\" id=\"totalFee"+t01+"\" value=\"\" style=\"border:0px;\"/></td><td><input type=\"text\" name=\"tax\" id=\"tax"
				+ t01
				+ "\" value=\"\" style=\"border:0px;\" onblur=\"countWaitPrice($(this),"
				+ t01
				+ ");\"/></td><td><input type=\"text\" name=\"waitAllocationPrice\" id=\"waitAllocationPrice"
				+ t01
				+ "\" value=\"\" style=\"border:0px;\" onBlur=\"totalAllocation($(this).val(),"
				+ t01
				+ ");\"/></td><td><select name=\"allocationCode\" id=\"allocationCode"
				+ t01
				+ "\" onchange=\"allocationChange($(this).attr('id'))\"><c:forEach items='${Dictionarys }' var='Dictionary'><option value='${Dictionary.dictValue }'>${Dictionary.dictName }</option></c:forEach></select></td><td><select name=\"currencyCode\" id=\"currencyCode"
				+ t01
				+ "\" onchange=\"selCurrency($(this).find('option:selected').attr('song'),"
				+ t01
				+ ")\"><c:forEach items='${Currency }' var='currency'><option value='${currency.code }' song='${currency.exchangeRate }'>${currency.currencyType }</option></c:forEach></select></td><td><input type=\"text\" name=\"exchangeRate\" id=\"exchangeRate"+t01+"\" value=\"\" style=\"border:0px;\"/></td><td><input type=\"text\" name=\"localPrice\" id=\"localPrice"+t01+"\" value=\"\" style=\"border:0px;\"/></td><td><input type=\"text\" name=\"imagePath\" id=\"imagePath"
				+ t01
				+ "\" value=\"上传\" style=\"border:0px;\ width:200px;\" onfocus=\"tan("
				+ t01 + ");\"/></td></tr>";
		var tab = $("#detilTab");
		tab.append(html);
		pushValue('checked', qty, price);
		selCurrency($("#currencyCode" + t01).find("option:selected").attr(
				"song"), t01);
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
											"#allocationQty"
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
											"#allocationQty"
													+ $(this).attr("id")
															.substring(14))
											.val("");
								}
							});
		}
	}

	function allocationChange(id) {
		if ($("#" + id).val() == '001') {
			if (qty != undefined) {
				$("#allocationQty" + id.substring(14)).val(qty);
			}
		} else if ($("#" + id).val() == '002') {

		} else if ($("#" + id).val() == '003') {
			if (price != undefined) {
				$("#allocationQty" + id.substring(14)).val(price);
			}
		}
	}

	function saveDetil() {
		var chkTrue = "";
		$("input[type='checkbox'][name='delBox']").each(function() {
			if (this.checked) {
				chkTrue = chkTrue + "1,";
			} else {
				chkTrue = chkTrue + "0,";
			}
		});

		chkTrue = chkTrue.substring(0, chkTrue.length - 1);
		if (chkTrue != "") {
			$("#chkTrue").val(chkTrue);
		}

		var flag = false;
		var opetion = $('#detilTab .sho');
		for ( var i = 0; i < opetion.length; i++) {
			var op0 = opetion[i].getElementsByTagName('input')[0];
			var op2 = opetion[i].getElementsByTagName('input')[2];
			var op3 = opetion[i].getElementsByTagName('input')[3];
			var op4 = opetion[i].getElementsByTagName('input')[4];

			if (op0.checked == true
					&& (op2.value == 0 || op3.value == ""
							|| op3.value == undefined || op4.value == 0)) {
				alert('费用项不能为空');
				return false;
			}

			if (op0.checked == true) {
				flag = true;
			};
		}

		if (!flag) {
			alert('请选择需要保存的记录');
			return false;
		}

		var value = $("#detilMess").serialize();
		$.ajax({
			type : "post",
			url : "${path}/purchasecost/savePurchaseCost",
			dataType : "json",
			data : value + "&purchaseId=" + id,
			success : function(data) {
				if (data.success) {
					//alert(data.message);
					window.location.href = "${path}/purchasecost/findPurchaseCost";
				} else {
					alert(data.message);
				};
			},
			error : function(json) {
				alert("处理异常");
			}
		});
	}

	function countWaitPrice(obj, row) {
		var waitFee = $("#totalFee" + row).val() * (1 + obj.val() / 100);
		$("#waitAllocationPrice" + row).val(waitFee.toFixed(2));

		var rate = ($("#exchangeRate" + row).val() * waitFee).toFixed(2);
		$("#localPrice" + row).val(rate);
	}

	function openWindow(row, obj) {
		var reg=/^\d+(\.\d+)?$/;
	
		if ($("#totalFee" + row).val() == '' || !$("#totalFee" + row).val().match(reg)) {
			alert("请输入正确格式的费用总金额!");
			return;
		}
		
		if ($("#tax" + row).val() == '' || !$("#tax" + row).val().match(reg)) {
			alert("请输入正确格式的税率!");
			return;
		}
		
		if ($("#costDate" + row).val() == '') {
			alert("请输入时间!");
			return;
		}

		obj.attr("href", "${path}/purchasecost/findSkuWindow?costCode="
				+ $("#costCode" + row).val() + "&allocationCode="
				+ $("#allocationCode" + row).val() + "&totalFee="
				+ $("#totalFee" + row).val() + "&tax=" + $("#tax" + row).val()
				+ "&localPrice=" + $("#localPrice" + row).val() + "&ids=" + id);
		obj.attr("target", "_blank");
		obj.click();
	}
</script>
<body>
	<%@include file="/WEB-INF/views/include/header.jsp"%>
	<div class="blank10"></div>
	<!-- 导航 end -->
	<div class="center">
		<!-- 左边 start -->
		<%@include file="/WEB-INF/views/dealerseller/leftPage.jsp"%>

		<!-- 左边 end -->
		<div class="right">

			<div class="pu_h">
				<h3>采购费用登记</h3>
				<div class="btn">
					<a href="javascript:void(0)" onclick="CreateRow();">新增</a> 
					<a href="javascript:void(0)" onclick="DeleteRow();">删除</a> 
					<a href="javascript:void(0)" onclick="saveDetil();">保存</a>
					<!-- 
					<a href="javascript:void(0)" onclick="auditDetil();">审核</a>
					 -->
				</div>
			</div>

			<div class="pu_w" style="" id="fee">
				<div class="pu_b">
					<form id="detilMess">
						<!-- 提交判断是否选中 -->
						<input type="hidden" id="chkTrue" name="chkTrue">

						<table id="detilTab">
							<tr class="order_hd">
								<th width="60px">序号</th>
								<th width="60px;">选择</th>
								<th width="80px">分摊计算</th>
								<th width="80px">费用日期</th>
								<th width="110px">费用名称</th>
								<th width="110px">费用总金额</th>
								<th width="80px">税率(%)</th>
								<th width="80px">待分摊总金额</th>
								<th width="130px">分摊方式</th>
								<th width="80px">币别</th>
								<th width="80px">汇率</th>
								<th width="80px">本币金额</th>
								<th width="200px">凭据摄影</th>
							</tr>
							<tr class="sho">
								<td>1</td>
								<td><input type="checkbox" name="delBox" id="delBox1"
									value="1" style="border:0px;" class="sm" /></td>
								<td><div class="btn">
										<a href="javascript:void(0)" onclick="openWindow(1,$(this));">分摊</a>
										<!-- target="_blank" -->
									</div></td>
								<td><input type="text" name="costDate" id="costDate1"
									style="border:0px;"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" /></td>
								<td><select name="costCode" id="costCode1">
										<c:forEach items="${Cost }" var="Cost">
											<option value="${Cost.code }">${Cost.costName }</option>
										</c:forEach>
								</select></td>
								<td><input type="text" name="totalFee" id="totalFee1"
									style="border:0px;" /></td>
								<td><input type="text" name="tax" id="tax1"
									style="border:0px;" onblur="countWaitPrice($(this),1);" value=""/></td>
								<td><input type="text" name="waitAllocationPrice"
									id="waitAllocationPrice1" value="" style="border:0px;"
									onBlur="totalAllocation($(this).val(),1);" /></td>
								<td><select name="allocationCode" id="allocationCode1"
									onchange="allocationChange($(this).attr('id'))"><c:forEach
											items="${Dictionarys }" var="Dictionary">
											<option value="${Dictionary.dictValue }">${Dictionary.dictName
												}</option>
										</c:forEach>
								</select></td>
								<td><select name="currencyCode" id="currencyCode1"
									onchange="selCurrency($(this).find('option:selected').attr('song'),1)"><c:forEach
											items="${Currency }" var="currency">
											<option value="${currency.code }"
												song="${currency.exchangeRate }">${currency.currencyType
												}</option>
										</c:forEach>
								</select></td>
								<td><input type="text" name="exchangeRate"
									id="exchangeRate1" style="border:0px;" /></td>
								<td><input type="text" name="localPrice" id="localPrice1"
									style="border:0px;" /></td>

								<td><input type="text" name="imagePath" id="imagePath1"
									value="上传" style="border:0px; width:200px;" onfocus="tan(1);" />
								</td>
							</tr>

						</table>
					</form>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		$(".right .pu_w").css("height", $(window).height() - 160 + 'px');
	</script>


	<div class="alert_upload" style="display:none;">
		<div class="bg"></div>
		<div class="w">

			<div class="box1">
				<h2>上传图片</h2>
				<img src="/web-platform/commons/images/close.jpg"
					class="w_close" onclick="closeWin()">
			</div>

			<div class="box2">
				<div class="fi">
					<input type="file" class="inputstyle" id="upfile">

				</div>
			</div>

			<div class="box3"></div>
		</div>
	</div>
</body>
</html>
