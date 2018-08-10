$(function(){
	$("#productName").live('click',showSku);
});

//选择供应商后初次加载此供应商下所有商品
function showSku(event,page,supplierCode,supplierName){
	if(event == 1){
		var that = $(this);
		return that;
	}else{
		$.ajax({
			type : "post", 
			url :"../user/supplierList", 
			data:{"page":page,"supplierCode":supplierCode,"supplierName":supplierName},
			dataType:"html",
			success : function(msg) { 
				$("#skubox").html(msg);
				$("#skuDiv").show();
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("对不起，数据异常请稍后再试!");
			}
		});
	}	
}

function GoPage(page){
	var supplierCodeVal = $("#serSupplierCodeId").val();
	var supplierNameVal = $("#serSupplierNameId").val();
	hideShow();
	showSku(0,page,supplierCodeVal,supplierNameVal);
}

//选择商品赋值到文本框中去
function loadSku(){
	var sku = $('input[name="checkSupplierCode"]:checked');
	if(sku.length>0){
		var checkSupplierCode = sku.val();
		$("#sjSupplierCodeId").val(checkSupplierCode);
		hideShow();
	}else{
		alert("请选择一个企业!");
	}
}

//隐藏弹出框
function hideShow(){
	$(".alert_bu").hide();
	$(".alert_c").hide();
}
