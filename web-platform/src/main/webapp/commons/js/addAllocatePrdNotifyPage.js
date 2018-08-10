var CONTEXTPATH = $("#conPath").val();

$(document).ready(function() {

});

function clickSubmit(page) {
	var isgenuines = $("#isgenuines").val();
	$("#isgenuine").val(isgenuines);

	var fm_data = $('#queryallocateOpePrd').serialize();

	if (page != undefined) {
		fm_data += "&page=" + page;
	}

	$.ajax({
		type : "post",
		url : CONTEXTPATH + "/allocateNotify/getOperationDataToTable",
		data : fm_data + "&random=" + Math.random(),
		dataType : "html",
		success : function(msg) {
			$("#cbOpeAllocatePrdList").html(msg);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("操作失败");
		}
	});
}

function opetatorPrd(skuId, index) {
	var transferOutWarehouseCode = $("#transferOutWarehouseCode").val();
	var transferOutWarehouseName = $("#transferOutWarehouseName").val();
	var isgenuine = $("#isgenuines").val();

	var barCode = $("#barCode" + index).val();
	var pname = $("#pname" + index).val();
	var pcode = $("#pcode" + index).val();
	var format = $("#format" + index).val();
	var unit = $("#unit" + index).val();
	var productId = $("#productId" + index).val();
	var barSkuId = $("#barSkuId" + index).val();
	var price = $("#price" + index).val();
	$.ajax({
		type : "post",
		url : CONTEXTPATH + "/allocateNotify/operatorStock",
		data : {
			"transferOutWarehouseCode" : transferOutWarehouseCode,
			"transferOutWarehouseName" : transferOutWarehouseName,
			"pcode" : pcode,
			"barCode" : barCode,
			"pname" : pname,
			"isgenuine" : isgenuine,
			"format" : format,
			"unit" : unit,
			"productId" : productId,
			"skuId" : skuId,
			"price":price,
			"barSkuId":barSkuId
		},
		dataType : "html",
		success : function(msg) {
			$("#cbOpeStockList").html(msg);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("操作失败");
		}
	});
}
