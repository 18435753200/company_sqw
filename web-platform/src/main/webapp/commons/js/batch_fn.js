var CONTEXTPATH  = $("#conPath").val();
$(document).ready(function(){
	var array_inventory = new Array();
	//$.ajax({
	//	async: false,
	//	type: "post",
	//	url: CONTEXTPATH + "/batchinventory/getAllBatchInventoryList",
	//	data: array_inventory.join("&")+"&random=" + Math.random(),
	//	dataType: "html",
	//	success: function(msg) {
	//		$("#c24").html(msg);
	//	},
	//	error: function(jqXHR, textStatus, errorThrown){
	//		alert("查询失败 ，请稍后再试。。");
	//	}
	//});
});

//点击条件搜索函数
function clickSubmit(page){
	var array_inventory = new Array();

	var pname = $("#batch_pname").val();
	var pid = $("#batch_pid").val();
	var sku_id = $("#batch_sku_id").val();
	var batch_no = $("#batch_no").val();
	var to_expiry = $("#to_expiry").val();

	var matchnum = /^[0-9]*$/;

	if(!matchnum.test(pid)){
		alert("商品ID只能是数字！");
		return;
	}
	if(!matchnum.test(sku_id)){
		alert("SKU ID只能是数字！");
		return;
	}
	if(!matchnum.test(to_expiry)){
		alert("离过期时间只能是数字！");
		return;
	}

	if(page != undefined && page != ""){
		array_inventory.push("page="+page);
	}
	if(pname != ""){
		array_inventory.push("pName="+pname);
	}
	if(pid != ""){
		array_inventory.push("pId="+pid);
	}
	if(sku_id != ""){
		array_inventory.push("skuId="+sku_id);
	}
	if(batch_no !=""){
		array_inventory.push("batchNo="+batch_no);
	}
	if(to_expiry !=""){
		array_inventory.push("toExpiry="+to_expiry);
	}

	if(array_inventory.length == 0) {
		alert("请填写至少一个查询条件");
		return;
	}

	array_inventory.push("warehouseCode=1");//只查北京仓

	$.ajax({
		async: false,
		type : "post",
		url  : CONTEXTPATH+"/batchinventory/getAllBatchInventoryList",
		data : array_inventory.join("&")+"&random="+Math.random(),
		dataType: "html",
		success : function(msg) {
			$("#c24").html(msg);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("查询失败 ，请稍后再试。。");
		}
	});
}

//导出方法
function downExcel(downType){
	var array_inventory = new Array();

	var pname = $("#batch_pname").val();
	var pid = $("#batch_pid").val();
	var sku_id = $("#batch_sku_id").val();
	var batch_no = $("#batch_no").val();
	var to_expiry = $("#to_expiry").val();

	var matchnum = /^[0-9]*$/;

	if(!matchnum.test(pid)){
		alert("商品ID只能是数字！");
		return;
	}
	if(!matchnum.test(sku_id)){
		alert("SKU ID只能是数字！");
		return;
	}
	if(!matchnum.test(to_expiry)){
		alert("离过期时间只能是数字！");
		return;
	}

	if(pname != ""){
		array_inventory.push("pName="+pname);
	}
	if(pid != ""){
		array_inventory.push("pId="+pid);
	}
	if(sku_id != ""){
		array_inventory.push("skuId="+sku_id);
	}
	if(batch_no !=""){
		array_inventory.push("batchNo="+batch_no);
	}
	if(to_expiry !=""){
		array_inventory.push("toExpiry="+to_expiry);
	}

	if(array_inventory.length == 0) {
		alert("请填写至少一个查询条件");
		return;
	}

	array_inventory.push("warehouseCode=1");//只查北京仓

	window.open(CONTEXTPATH+"/batchinventory/downExcel?"+array_inventory.join("&")+"&math="+Math.random(),"_blank");
}


