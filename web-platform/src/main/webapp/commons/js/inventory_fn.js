var  CONTEXTPATH  = $("#conPath").val();
$(document).ready(function(){
	$('#ownerType') .change(checkwarehouseselect);
	$('.bt2') .live('click', closebox);
	$('.b_colse') .live('click', closebox);
	$('.top').find('a:eq(0)').click();
	var  matchnum = /^[0-9]*$/;
	$("#orderId").change(function(){
		var orderId = $("#orderId").val();
		if(!matchnum.test(orderId)){
			alert("订单编号只能是数字！");
			$("#orderId").val("");
			$("#orderId").focus();
		}
	});
	$(".b_colse").click(function(){
		$(".alert_shul1").hide();			
	});
	$(".bt2").click(function(){
		$(".alert_shul1").hide();			
	});
	$("#addline").click(addline);
	$("#locktbody .lockedinp").live("input propertychange",checklocked);
	$("#confimaddLock").click(function(){
		var flag = true;
		var lockQty = $("#lockQty").val();
		var  matchnum = /^[1-9][0-9]*$/;
		if(undefined != lockQty && !matchnum.test(lockQty)){
			alert("锁定库存量必须为正整数！");
			$("#lockQty").val("");
			$("#lockQty").focus();
			flag = false;
			return false;
		}
		var len = $("#fm").find("input[type='text']").length;
		if(len > 0){
			$("#fm").find("input[type='text']").each(function(){
				if($(this).val()=='' || !matchnum.test($(this).val())){
					flag = false;
				}
			});
		}else{
			$(".alert_lock").hide(50);
			return false;
		}
		if(flag){
			var fmdata = $("#fm").serialize();
			$.ajax({
				async:false,
				type : "post", 
				url    : CONTEXTPATH+"/inventory/addLockQty", 
				data :fmdata+"&random="+Math.random(),
				success : function(msg) {
					if(msg == '1'){
						alert("保存成功");
						var pageContext = $("#pageContext").val();
						clickSubmit(pageContext);
					}else{
						alert("保存失败");
					}
					$(".alert_lock").hide(50);
				},
				error: function(jqXHR, textStatus, errorThrown){
					alert("查询失败 ，请稍后再试。。");
				}
			});
		}else{
			alert('请将已添加的信息填写完整。如不需要请删除！');
		}
	});
});

//点击条件搜索函数
function clickSubmit(page){
	var array_inventory = new Array();
	var createTime = $("#screateTime").val();
	var endTime = $("#sstopTime").val();
	var sqty = $("#ssqty").val();
	var eqty = $("#seqtyNum").val();
	var pname = $("#spname").val();
	var skuCode = $("#skuCode").val();
	var warehouseName = $('#warehouseName').val();
	var ownerType = $("#ownerType").val();
	var  matchnum = /^[0-9]*$/;
	if(!matchnum.test(sqty)){
		alert("库存量只能是数字！");
		$("#ssqty").val("");
		$("#ssqty").focus();
		return false;
	}
	if(!matchnum.test(eqty)){
		alert("库存量只能是数字！");
		$("#seqtyNum").val("");
		$("#seqtyNum").focus();
		return false;
	}
	if(skuCode!=""&&skuCode!=undefined){
		if(skuCode.length!=19){
			if(!RegExp("^\\d{8}$|^\\d{12,13}$").test(skuCode)){
				alert("条形码应为8,12,13位数字或者系统生成!");
				$("#skuCode").val("");
				$("#skuCode").focus();
				return false;
			}
		}
	}
	if(page!=undefined&&page!=""){
		array_inventory.push("page="+page);
	}
	if(createTime!=""&&endTime!=""){
		if(createTime>=endTime){
			alert("开始时间不能大于结束时间");
			return false;
		}
	}
	if(createTime!=""){
		array_inventory.push("createTime="+createTime);
	}
	if(endTime!=""){
		array_inventory.push("endTime="+endTime);
	}
	if(sqty!=""){
		array_inventory.push("sqty="+sqty);
	}
	if(eqty!=""){
		array_inventory.push("eqty="+eqty);
	}
	if(pname!=""){
		array_inventory.push("pName="+pname);
	}
	if(skuCode!=""){
		array_inventory.push("skuCode="+skuCode);
	}
	if(warehouseName!=""&&warehouseName!=undefined){
		array_inventory.push("warehouseCode="+warehouseName);
	}
	var liststat = checkLable();
	//到岸库存
	if(liststat=="1"){
		if(ownerType!=""){
			array_inventory.push("ownerType="+ownerType);
		}
		var skuId = $("#skuID").val();
		if(skuId != ""){
			if(!matchnum.test(skuId)){
				alert("SKU ID必须是数字！");
				return false;
			}
			array_inventory.push("skuId=" + skuId);
		}

		$.ajax({
			async:false,
			type : "post", 
			url    : CONTEXTPATH+"/inventory/getAllInventoryList", 
			data :array_inventory.join("&")+"&random="+Math.random(),
			dataType: "html",
			success : function(msg) { 
				$("#c24").html(msg);
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("查询失败 ，请稍后再试。。");
			}
		});
	}
	//供应商现货库存
	if(liststat=="2"){
		var supplierName = $("#supplierName").val();
		if(supplierName!=""){
			array_inventory.push("supplierName="+supplierName);
		}
		$.ajax({
			async:false,
			type : "post", 
			data :array_inventory.join("&")+"&random="+Math.random(),
			url    : CONTEXTPATH+"/inventory/getSupplierStock", 
			dataType: "html",
			success : function(msg) { 
				$("#c24").html(msg);
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("查询失败 ，请稍后再试。。");
			}
		});
	}
	//供应商预售库存
	if(liststat=="3"){
		var supplierName = $("#supplierName").val();
		if(supplierName!=""){
			array_inventory.push("supplierName="+supplierName);
		}
		$.ajax({
			async:false,
			type : "post", 
			data :array_inventory.join("&")+"&random="+Math.random(),
			url    : CONTEXTPATH+"/inventory/getSupplierCollectionStock", 
			dataType: "html",
			success : function(msg) { 
				$("#c24").html(msg);
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("查询失败 ，请稍后再试。。");
			}
		});
	}
	//转运仓库存
	if(liststat=="4"){
		$.ajax({
			async:false,
			type : "post", 
			data :array_inventory.join("&")+"&random="+Math.random(),
			url    : CONTEXTPATH+"/inventory/getTransitDepotList", 
			dataType: "html",
			success : function(msg) { 
				$("#c24").html(msg);
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("查询失败 ，请稍后再试。。");
			}
		});
	}
	//保税区库存
	if(liststat=="5"){
		$.ajax({
			async:false,
			type : "post", 
			data :array_inventory.join("&")+"&random="+Math.random(),
			url    : CONTEXTPATH+"/inventory/getBondedAreaList", 
			dataType: "html",
			success : function(msg) { 
				$("#c24").html(msg);
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("查询失败 ，请稍后再试。。");
			}
		});
	}
	//保税区库存
	if(liststat=="7"){
		$.ajax({
			async:false,
			type : "post", 
			data :array_inventory.join("&")+"&random="+Math.random(),
			url    : CONTEXTPATH+"/inventory/getZhuoYueList", 
			dataType: "html",
			success : function(msg) { 
				$("#c24").html(msg);
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("查询失败 ，请稍后再试。。");
			}
		});
	}
	
	//赠品库存
	if(liststat=="6"){
		$.ajax({
			async:false,
			type : "post", 
			data :array_inventory.join("&")+"&random="+Math.random(),
			url    : CONTEXTPATH+"/inventory/findStockForGiftWofePage", 
			dataType: "html",
			success : function(msg) { 
				$("#c24").html(msg);
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("查询失败 ，请稍后再试。。");
			}
		});
	}

	//POP库存
	if(liststat == "8") {
		var pid = $("#pId").val();
		if(pid != ""){
			if(!matchnum.test(pid)){
				alert("商品ID必须是数字！");
				return false;
			}
			array_inventory.push("pId=" + pid);
		}
		var skuId = $("#skuID").val();
		if(skuId != ""){
			if(!matchnum.test(skuId)){
				alert("SKU ID必须是数字！");
				return false;
			}
			array_inventory.push("skuId=" + skuId);
		}

		$.ajax({
			async:false,
			type : "post",
			data :array_inventory.join("&")+"&random="+Math.random(),
			url    : CONTEXTPATH+"/inventory/getPopInventory",
			dataType: "html",
			success : function(msg) {
				$("#c24").html(msg);
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("查询失败 ，请稍后再试。。");
			}
		});
	}
}

//导出方法
function downExcel(downType){
	var array_inventory = new Array();
	var createTime = $("#screateTime").val();
	var endTime = $("#sstopTime").val();
	var sqty = $("#ssqty").val();
	var eqty = $("#seqtyNum").val();
	var pname = $("#spname").val();
	var skuCode = $("#skuCode").val();
	var warehouseName = $('#warehouseName').val();
	var ownerType = $("#ownerType").val();
	var  matchnum = /^[0-9]*$/;
	if(!matchnum.test(sqty)){
		alert("库存量只能是数字！");
		$("#ssqty").val("");
		$("#ssqty").focus();
		return false;
	}
	if(!matchnum.test(eqty)){
		alert("库存量只能是数字！");
		$("#seqtyNum").val("");
		$("#seqtyNum").focus();
		return false;
	}
	if(skuCode!=""&&skuCode!=undefined){
		if(skuCode.length!=19){
			if(!RegExp("^\\d{8}$|^\\d{12,13}$").test(skuCode)){
				alert("条形码应为8,12,13位数字或者系统生成!");
				$("#skuCode").val("");
				$("#skuCode").focus();
				return false;
			}
		}
	}
	if(createTime!=""&&endTime!=""){
		if(createTime>=endTime){
			alert("开始时间不能大于结束时间");
			return false;
		}
	}
	if(createTime!=""){
		array_inventory.push("createTime="+createTime);
	}
	if(endTime!=""){
		array_inventory.push("endTime="+endTime);
	}
	if(sqty!=""){
		array_inventory.push("sqty="+sqty);
	}
	if(eqty!=""){
		array_inventory.push("eqty="+eqty);
	}
	if(pname!=""){
		array_inventory.push("pName="+pname);
	}
	if(skuCode!=""){
		array_inventory.push("skuCode="+skuCode);
	}
	if(warehouseName!=""&&warehouseName!=undefined){
		array_inventory.push("warehouseCode="+warehouseName);
	}
	array_inventory.push("downType="+downType);
	var liststat = checkLable();
	if(liststat=="1"){
		if(ownerType!=""){
			array_inventory.push("ownerType="+ownerType);
		}
		window.open(CONTEXTPATH+"/inventory/downExcel?"+array_inventory.join("&")+"&math="+Math.random(),"_blank");
	}
	if(liststat=="6"){
		if(ownerType!=""){
			array_inventory.push("ownerType="+ownerType);
		}
		window.open(CONTEXTPATH+"/inventory/downExcel?"+array_inventory.join("&")+"&math="+Math.random(),"_blank");
	}
}

//点击table也执行方法
function getInventoryByType(type){
	if(type!=""){
		if(type=="1"){
			loadWareSelect(1);
			$.ajax({
				async:false,
				type : "post", 
				data : "ownerType=1",
				url    : CONTEXTPATH+"/inventory/getAllInventoryList", 
				dataType: "html",
				success : function(msg) { 
					checkClick(type);
					resetfm();
					$("#c24").html(msg);
				},
				error: function(jqXHR, textStatus, errorThrown){
					alert("查询失败 ，请稍后再试。。");
				}
			});
		}
		if(type=="2"){
			$.ajax({
				async:false,
				type : "post", 
				url    : CONTEXTPATH+"/inventory/getSupplierStock", 
				dataType: "html",
				success : function(msg) { 
					checkClick(type);
					resetfm();
					$("#c24").html(msg);
				},
				error: function(jqXHR, textStatus, errorThrown){
					alert("查询失败 ，请稍后再试。。");
				}
			});
		}
		if(type=="3"){
			$.ajax({
				async:false,
				type : "post", 
				url    : CONTEXTPATH+"/inventory/getSupplierCollectionStock", 
				dataType: "html",
				success : function(msg) { 
					checkClick(type);
					resetfm();
					$("#c24").html(msg);
				},
				error: function(jqXHR, textStatus, errorThrown){
					alert("查询失败 ，请稍后再试。。");
				}
			});
		}
		if(type=="4"){
			$.ajax({
				async:false,
				type : "post", 
				url    : CONTEXTPATH+"/inventory/getTransitDepotList", 
				dataType: "html",
				success : function(msg) { 
					checkClick(type);
					resetfm();
					$("#c24").html(msg);
				},
				error: function(jqXHR, textStatus, errorThrown){
					alert("查询失败 ，请稍后再试。。");
				}
			});
		}
		if(type=="5"){
			$.ajax({
				async:false,
				type : "post", 
				url    : CONTEXTPATH+"/inventory/getBondedAreaList", 
				dataType: "html",
				success : function(msg) { 
					checkClick(type);
					resetfm();
					$("#c24").html(msg);
				},
				error: function(jqXHR, textStatus, errorThrown){
					alert("查询失败 ，请稍后再试。。");
				}
			});
		}
		if(type=="7"){
			$.ajax({
				async:false,
				type : "post", 
				url    : CONTEXTPATH+"/inventory/getZhuoYueList", 
				dataType: "html",
				success : function(msg) { 
					checkClick(type);
					resetfm();
					$("#c24").html(msg);
				},
				error: function(jqXHR, textStatus, errorThrown){
					alert("查询失败 ，请稍后再试。。");
				}
			});
		}

		if(type=="8"){
			$.ajax({
				async:false,
				type : "post",
				url    : CONTEXTPATH+"/inventory/getPopInventory",
				data : {warehouseCode: "-1"},
				dataType: "html",
				success : function(msg) {
					checkClick(type);
					resetfm();
					$("#c24").html(msg);
				},
				error: function(jqXHR, textStatus, errorThrown){
					alert("查询失败 ，请稍后再试。。");
				}
			});
		}
		if(type!="4" && type!="5" && type!="7"){
			checkwarehouseselect();
		}
		if(type=="6"){
			loadWareSelect(1);
			$.ajax({
				async:false,
				type : "post", 
				url    : CONTEXTPATH+"/inventory/findStockForGiftWofePage", 
				dataType: "html",
				success : function(msg) { 
					checkClick(type);
					resetfm();
					$("#c24").html(msg);
				},
				error: function(jqXHR, textStatus, errorThrown){
					alert("查询失败 ，请稍后再试。。");
				}
			});
		}
	}
}

//修改库存无批次展示
function editInventoryNotBatch(id,preSubQty,lockQty,qty,version,dealerId){
	$("#editNotBatchOrderOccupiedNum").html(preSubQty);
	$("#editNotBatchLockQty").html(lockQty);
	$("#editNotBatchQty").attr("key",id);
	$("#editNotBatchQty").attr("version",version);
	$("#editNotBatchQty").attr("dealerId",dealerId);
	$("#editNotBatchQty").attr("compareQty",qty);
	$("#editNotBatchQty").val(qty);
	$(".alert_shul").show(50);
}

//修改库存提交有批次
function subEditinventoryNotBatch(){	
	var preSubQty = Number($("#editNotBatchOrderOccupiedNum").text());
	var lockQty = Number($("#editNotBatchLockQty").text());
	var compareQty = $("#editNotBatchQty").attr("compareQty");
	var id = $("#editNotBatchQty").attr("key");
	var qty = $("#editNotBatchQty").val();
	var  matchnum = /^[0-9]*$/;
	if(!matchnum.test(qty)){
		alert("库存数量必须是正整数！");
		$("#editNotBatchQty").val(compareQty);
		return false;
	}
	if(qty<preSubQty+lockQty){
		alert("修改库存数量需大于等于订单占用数量与锁定数量之和！");
		$("#editNotBatchQty").val(compareQty);
		return false;
	}
	$.ajax({
		type : "post", 
		url  : CONTEXTPATH+"/inventory/editStock", 
		data :"id="+id+"&qty="+qty,
		success : function(msg) { 
			if(msg==1){
				tipMessage("保存成功！",function(){
				});
			}else{
				tipMessage("数据保存异常！",function(){
				});
			}
			$(".alert_shul").hide(50);
			var pageContext = $("#pageContext").val();
			clickSubmit(pageContext);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("查询失败 ，请稍后再试。。");
		}
	});
}

//修改库存有批次展示
function editInventory(id,batchNo,preSubQty,qty,lockQty,version,dealerId){
	$("#editBatchNo").html(batchNo);
	$("#editPreSubQty").html(preSubQty);
	$("#editLockQty").html(lockQty);
	$("#editBatchQty").val(qty);
	$("#editBatchQty").attr("key",id);
	$("#editBatchQty").attr("version",version);
	$("#editBatchQty").attr("dealerId",dealerId);
	$("#editBatchQty").attr("compareQty",qty);
	$(".alert_shul1").show(50);

}

//转移库存到赠品展示
function moveInventoryToGift(id,batchNo,preSubQty,qty,lockQty,version,dealerId,
		skuId,pid,lockBatch,ownerType,pName,skuName){
	var liststat = checkLable();
	pName = $("#pName2").val();
	skuName = $("#skuName2").val();
	var stock_array = new Array();
	stock_array.push('skuId='+skuId);
	stock_array.push('pid='+pid);
	stock_array.push('lockBatch='+lockBatch);
	stock_array.push('ownerType='+ownerType);
	stock_array.push('dealerId='+dealerId);
	stock_array.push('id='+id);
	$("#editBatchNo_gift").html(batchNo);//批次号
	$("#editPreSubQty_gift").html(preSubQty);//锁定数量
	$("#editLockQty_gift").html(lockQty);//订单占用数量
	$("#editBatchQty_gift").val(qty);
	$("#editBatchQty_gift").attr("key",id);
	$("#editBatchQty_gift").attr("version",version);
	$("#editBatchQty_gift").attr("dealerId",dealerId);
	$("#editBatchQty_gift").attr("compareQty",qty);
	//调用可转移库存
	$.ajax({
		async:false,
		type : "post", 
		url  : CONTEXTPATH+"/inventory/loadLockQty", 
		data :stock_array.join('&')+"&random="+Math.random(),
		dataType:'json',
		success : function(msg) {
			if("" != msg.stockLocksDTO.qty){
				$("#editBatchQty_gift").val(msg.stockLocksDTO.qty);
				$("#editBatchQty_gift_hidden").val(msg.stockLocksDTO.qty);
			}else{
				$("#editBatchQty_gift").val(0);
				$("#editBatchQty_gift_hidden").val(0);
			}
		},error:function(){
			alert('查询失败');
		}
	});
	$(".alert_shul2").show(50);
}


//修改库存提交有批次没批次通用
function subEditInventory(){	
	var preSubQty = Number($("#editPreSubQty").text());
	var lockQty = Number($("#editLockQty").text());
	var compareQty = $("#editBatchQty").attr("compareQty");
	var qty = Number($("#editBatchQty").val());
	var id = $("#editBatchQty").attr("key");
	var  matchnum = /^[0-9]*$/;
	if(!matchnum.test(qty)){
		alert("修改数量必须是正整数！");
		$("#editNotBatchQty").val(compareQty);
		return false;
	}
	if(qty<preSubQty+lockQty){
		alert("修改库存数量需大于等于订单占用数量与锁定数量之和！");
		$("#editNotBatchQty").val(compareQty);
		return false;
	}
	$.ajax({
		async:false,
		type : "post", 
		url    : CONTEXTPATH+"/inventory/editStock", 
		data :"id="+id+"&qty="+qty+"&random="+Math.random(),
		success : function(msg) { 
			if(msg==1){
				tipMessage("保存成功！",function(){
				});
			}else{
				tipMessage("数据保存异常！",function(){
				});
			}
			$(".alert_shul1").hide(50);
			var pageContext = $("#pageContext").val();
			clickSubmit(pageContext);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("查询失败 ，请稍后再试。。");
		}
	});
}


//转移库存到赠品保存
function moveGiftToStock(){
	
	//判断转移数量是否超过了可转移数量
	var useableNum = $("#editBatchQty_gift_hidden").val();
	var writeNum = $("#editBatchQty_gift").val();
	var id = $("#editBatchQty_gift").attr("key");
	var  matchnum = /^[0-9]*$/;
	if(!matchnum.test(writeNum)){
		alert("修改数量必须是正整数！");
		return false;
	}
	
	if(parseInt(writeNum) > parseInt(useableNum)){
		alert("填写数量不能大于可转移库存量");
		return;
	}
	$.ajax({
		async:false,
		type : "post", 
		url    : CONTEXTPATH+"/inventory/moveGiftToStock",
		data :"id="+id+"&qty="+writeNum+"&random="+Math.random(),
		success : function(msg) { 
			if(msg=="操作成功!"){
				tipMessage("保存成功！",function(){
				});
			}else{
				tipMessage("数据保存异常！",function(){
				});
			}
			$(".alert_shul2").hide();
			var pageContext = $("#pageContext").val();
			clickSubmit(pageContext);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("查询失败 ，请稍后再试。。");
		}
	});

}

//转运仓锁定库存展示
function customerLock_fn(id,qty,preSubQty,lockQty){
	$('#lockNotBatchQty').text(Number(qty) - Number(preSubQty));
	$('#lockNotBatchLockQty').val(lockQty);
	$('#lockNotBatchLockQty').attr('key',id);
	$('#lockNotBatchLockQty').attr('compareData',lockQty);
	$('#alert_nolock').show(10);
}

//锁定库存无批次展示
function lockInventoryNotBatch(id,qty,lockQty,version,dealerId){
	//'${inv.id }','${inv.preSubQty }','${inv.lockQty }','${inv.version }'
	$("#lockNotBatchQty").html(qty);
	$("#lockNotBatchLockQty").attr("key",id);
	$("#lockNotBatchLockQty").attr("version",version);
	$("#lockNotBatchLockQty").attr("dealerId",dealerId);
	$("#lockNotBatchLockQty").attr("compareData",qty);
	$("#lockNotBatchLockQty").val(lockQty);
	$(".alert_nolock").show(50);
}

//锁定库存无批次提交
function sublockInventoryNotBatch(){
	var qty = Number($("#lockNotBatchQty").text());
	var compareQty = Number($("#lockNotBatchLockQty").attr("compareData"));
	var id = $("#lockNotBatchLockQty").attr("key");
	var lockQty = $("#lockNotBatchLockQty").val();
	var  matchnum = /^[0-9]*$/;
	if (!matchnum.test(lockQty)){
		alert("修改数量必须是正整数！");
		$("#lockNotBatchLockQty").val(compareQty);
		return false;
	}
	if (Number(lockQty) > qty){
		alert("修改锁定数量不能大于可锁定数量！");
		$("#lockNotBatchLockQty").val(compareQty);
		return false;
	}
	$.ajax({
		type : "post", 
		url  : CONTEXTPATH+"/inventory/addLockQtyForCustomer", 
		data :"id="+id+"&lockQty="+lockQty+"&random="+Math.random(),
		success : function(msg) { 
			if(msg==1){
				tipMessage("保存成功！",function(){
				});
			}else{
				tipMessage("数据保存异常！",function(){
				});
			}
			$(".alert_nolock").hide(50);
			var pageContext = $("#pageContext").val();
			clickSubmit(pageContext);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("查询失败 ，请稍后再试。。");
		}
	});
}

//锁定库存有批次展示
function lockInventory(id,skuId,pid,lockBatch,ownerType,dealerId,pName,skuName){
	pName = $("#pName1").val();
	skuName = $("#skuName1").val();
	var stock_array = new Array();
	stock_array.push('skuId='+skuId);
	stock_array.push('pid='+pid);
	stock_array.push('lockBatch='+lockBatch);
	stock_array.push('ownerType='+ownerType);
	stock_array.push('dealerId='+dealerId);
	stock_array.push('id='+id);
	$("#addline").attr("skuId",skuId);
	$("#addline").attr("pid",pid);
	$("#addline").attr("lockBatch",lockBatch);
	$("#addline").attr("ownerType",ownerType);
	$("#addline").attr("dealerId",dealerId);
	$("#addline").attr("pName",pName);
	$("#addline").attr("skuName",skuName);
	$("#addline").attr("usid",id);
	$.ajax({
		async:false,
		type : "post", 
		url  : CONTEXTPATH+"/inventory/loadLockQty", 
		data :stock_array.join('&')+"&random="+Math.random(),
		dataType:'json',
		success : function(msg) {
			$('#locktbody').empty();
			var $tr = '';
			if("" != msg.stockLocksDTO.qty){
				if(msg.stockLocksDTO.qty<=0){
					$(".lock-hd h2 span").text(0);
				}else{
					$(".lock-hd h2 span").text(msg.stockLocksDTO.qty);
				}
			}else{
				$(".lock-hd h2 span").text(0);
			}
			if(msg.stockDealerLockDTOList != '' && msg.stockDealerLockDTOList.length > 0){
				$.each(eval(msg.stockDealerLockDTOList),function(i,n){
					var stringreson = "";
					if(n.lockReason == '1'){
						stringreson = "商品破损数量";
					}
					if(n.lockReason == '2'){
						stringreson = "商品临期";
					}		
					if(n.lockReason == '3'){
						stringreson = "待退货商品";
					}
					$tr += '<tr>'+
						'<td><p class="one-line">'+pName+'</p></td>'+
						'<td><p class="one-line">'+skuName+'</p></td>'+
						'<td><p class="one-line">'+lockBatch+'</p></td>'+
						'<td>'+n.strTime+'</td>'+
						'<td>'+stringreson+'</td>'+
						'<td>'+n.lockQty+'</td>'+
						'<td><a onclick="del(event,\''+id+'\',\''+n.strid+'\')">删除</a></td>'+
						'</tr>';
				});	
				$('#locktbody').append($tr);
			} 
		},error:function(){
			alert('查询失败');
		}
	});
	$(".alert_lock").show(50);
}

//添加下一行
function addline(){
	var istruedel = $('#istruedel').val();
	var $this=$(this);
	var $tr ="";
	if(istruedel!=undefined&&istruedel=='1'){
		$tr = $("<tr><td><p class='one-line'>"+$this.attr("pName")+"</p>" +
				"<input type='hidden' name='id' value="+$this.attr('usid')+">" +
				"<input type='hidden' name='lockBatch' value="+$this.attr('lockbatch')+">" +
				"<input type='hidden' name='pName' value="+$this.attr('pName')+">" +
				"<input type='hidden' name='dealerId' value="+$this.attr('dealerId')+"></td>" +
				"<input type='hidden' name='ownerType' value="+$this.attr('ownerType')+"></td>" +
				"<input type='hidden' name='skuName' value="+$this.attr('skuName')+"></td>" +
				"<input type='hidden' name='skuId' value="+$this.attr('skuId')+"></td>" +
				"<input type='hidden' name='pid' value="+$this.attr('pid')+"></td>" +
				"<input type='hidden' name='skuType' value='1'></td>" +
				"<td><p class='one-line'>"+$this.attr("skuName")+"</p></td>" +
				"<td><p class='one-line'>"+$this.attr('lockbatch')+"</p></td>" +
				"<td><p class='one-line'></p></td>" +
				"<td><select name='lockReason'><option value='1'>商品破损数量</option><option value='2'>商品临期</option><option value='3'>待退货商品</option><select></td>" +
				"<td><input type='text' id='lockQty' name='lockQty' class='lockedinp lock-txt'></td>" +
				"<td><a onclick='del(event)'>删除</a></td></tr>");
	}else{
		$tr = $("<tr><td><p class='one-line'>"+$this.attr("pName")+"</p>" +
			"<input type='hidden' name='id' value="+$this.attr('usid')+">" +
			"<input type='hidden' name='lockBatch' value="+$this.attr('lockbatch')+">" +
			"<input type='hidden' name='pName' value="+$this.attr('pName')+">" +
			"<input type='hidden' name='dealerId' value="+$this.attr('dealerId')+"></td>" +
			"<input type='hidden' name='ownerType' value="+$this.attr('ownerType')+"></td>" +
			"<input type='hidden' name='skuName' value="+$this.attr('skuName')+"></td>" +
			"<input type='hidden' name='skuId' value="+$this.attr('skuId')+"></td>" +
			"<input type='hidden' name='pid' value="+$this.attr('pid')+"></td>" +
			"<input type='hidden' name='skuType' value='1'></td>" +
			"<td><p class='one-line'>"+$this.attr("skuName")+"</p></td>" +
			"<td><p class='one-line'>"+$this.attr('lockbatch')+"</p></td>" +
			"<td><p class='one-line'></p></td>" +
			"<td><select name='lockReason'><option value='1'>商品破损数量</option><option value='2'>商品临期</option><option value='3'>待退货商品</option><select></td>" +
			"<td><input type='text' id='lockQty' name='lockQty' class='lockedinp lock-txt'></td>" +
			"<td></td></tr>");
	}
	$("#locktbody").append($tr);
}


//删除
function del(event,stockId,id){
	var src = event.srcElement || event.target; 
	var delqtynum = 0;
	var qtytd = $(src).closest('tr').find('td:eq(5)').find('input[type="text"]');
	if(qtytd.length>0){
	}else{
		delqtynum = $(src).closest('tr').find('td:eq(5)').text();
	}
	if(stockId != '' && id != '' && id != undefined && stockId!= undefined){
		var stock_array = new Array();
		stock_array.push("stockId="+stockId);
		stock_array.push("id="+id);
		$.ajax({
			async:false,
			type : "post", 
			url  : CONTEXTPATH+"/inventory/delLockQty", 
			data :stock_array.join('&')+"&random="+Math.random(),
			success : function(msg) {
				if(msg=="1"){
					$(src).closest("tr").remove();
					$('.lock-hd').find('h2 span').text(Number($('.lock-hd').find('h2 span').text())+Number(delqtynum));
					var pageContext = $("#pageContext").val();
					clickSubmit(pageContext);
				}else{
					alert("删除失败了！");
				}
			}
		});
	}else{
		$(src).closest("tr").remove();
	}
}

function checklocked(){
	var $this = $(this), matchnum = /^[0-9]*$/;
	if(matchnum.test($this.val())){
		var val = Number($this.val()), sum = 0
		$(".lockedinp").each(function(){
			sum+= Number($(this).val());	
		})
		if(sum > Number($(".lock-hd h2 span").text())){
			$this.val(0);
			$(".lock-tips").css("color","red");
		}else{
			$this.val(val);
			$(".lock-tips").css("color","black");
		}
	}
} 

//锁定库存提交有批次
function sublockInventory(){
	var id = $("#lockQty").attr("key");
	var lockQty = $("#lockQty").val();
	var compareQty = Number($("#lockQty").attr("compareData"));
	var  matchnum = /^[0-9]*$/;
	if(!matchnum.test(lockQty)){
		alert("修改数量必须是正整数！");
		$("#lockNotBatchLockQty").val(compareQty);
		return false;
	}
	if(compareQty<lockQty){
		alert("修改锁定数量不能大于可锁定数量！");
		$("#lockNotBatchLockQty").val(compareQty);
		return false;
	}
	$.ajax({
		async:false,
		type : "post", 
		url  : CONTEXTPATH+"/inventory/lockStock", 
		data :"id="+id+"&lockQty="+lockQty+"&random="+Math.random(),
		success : function(msg) { 
			if(msg==1){
				tipMessage("保存成功！",function(){
				});
			}else{
				tipMessage("数据保存异常！",function(){
				});
			}
			$(".alert_lock").hide(50);
			var pageContext = $("#pageContext").val();
			clickSubmit(pageContext);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("查询失败 ，请稍后再试。。");
		}
	});
}

function checkLable(){
	var liststatus = "";
	$(".top").find("li").each(function(){
		if($(this).attr("class")=="list"){
			liststatus =  $(this).find("a").attr("liststatus") ;
		}
	});
	return liststatus;
}

function checkClick(statu){
	if(statu=='1'){
		$("#li_wofeInventory").attr("class","list");
		$("#li_supplierInventory").removeClass("list");
		$("#inventorytypeSpan").show();
		$("#skuCodeSpan").show();
		$("#timeSpan").show();
		$("#stocknum").show();
		$("#supplierSpan").hide();
		$("#productID").hide();
		$("#stock_skuID").show();
		$("#inventoryText").text("库存数量：");	
	}
	if(statu=='2'){
		$("#li_supplierInventory").attr("class","list");
		$("#li_wofeInventory").removeClass("list");
		$("#inventorytypeSpan").hide();
		$("#skuCodeSpan").hide();
		$("#timeSpan").hide();
		$("#supplierSpan").show();
		$("#stocknum").show();
		$("#productID").hide();
		$("#stock_skuID").hide();
		$("#inventoryText").text("现货库存:");
	}
	if(statu=='3'){
		$("#stocknum").hide();
		$("#skuCodeSpan").hide();
		$("#inventorytypeSpan").hide();
		$("#timeSpan").hide();
		$("#inventoryText").text("最大预定量:");
		$("#supplierSpan").show();
		$("#productID").hide();
		$("#stock_skuID").hide();
	}
	if(statu=='4'){
		$("#stocknum").show();
		$("#skuCodeSpan").hide();
		$("#inventorytypeSpan").hide();
		$("#timeSpan").show();
		$("#inventoryText").text("最大预定量:");
		$("#supplierSpan").hide();
		$("#productID").hide();
		$("#stock_skuID").hide();
		loadWareSelect(3);
	}
	if(statu=='5'){
		$("#stocknum").show();
		$("#skuCodeSpan").hide();
		$("#inventorytypeSpan").hide();
		$("#timeSpan").show();
		$("#inventoryText").text("最大预定量:");
		$("#supplierSpan").hide();
		$("#productID").hide();
		$("#stock_skuID").hide();
		loadWareSelect(2);
	}
	if(statu=='6'){
		$("#li_wofeInventory").attr("class","list");
		$("#li_supplierInventory").removeClass("list");
		$("#inventorytypeSpan").show();
		$("#skuCodeSpan").show();
		$("#timeSpan").show();
		$("#stocknum").show();
		$("#supplierSpan").hide();
		$("#productID").hide();
		$("#stock_skuID").hide();
		$("#inventoryText").text("库存数量：");	
	}
	if(statu=='7'){
		$("#stocknum").show();
		$("#skuCodeSpan").hide();
		$("#inventorytypeSpan").hide();
		$("#timeSpan").show();
		$("#inventoryText").text("最大预定量:");
		$("#supplierSpan").hide();
		$("#productID").hide();
		$("#stock_skuID").hide();
		loadWareSelect(4);
	}
	if(statu=='8'){
		$("#li_wofeInventory").attr("class","list");
		$("#li_supplierInventory").removeClass("list");
		$("#inventorytypeSpan").hide();
		$("#productID").show();
		$("#skuCodeSpan").show();
		$("#stock_skuID").show();
		$("#timeSpan").hide();
		$("#stocknum").hide();
		$("#supplierSpan").hide();
		$("#inventoryText").text("库存数量：");
		loadWareSelect(7);
	}
}

function  loadWareSelect(type){
	$.ajax({
		type : "post",
		data: 'type='+type,
		url  : CONTEXTPATH+"/inventory/loadWareSelect", 
		dataType: 'json',
		success : function(msg) {
			if(msg.length>0){
				var tablelist = '<dt class="sv1">仓库 ：</dt><dd><select id="warehouseName"  class="search-sel"><option value="">全部仓库</option>';
				$.each(eval(msg),function(i,data){
					tablelist+="<option value="+data.warehouseCode+">"+data.warehouseName+"</option>";
				});
				tablelist+='</select></dd>';
				$('#warehouseNameSelect').html(tablelist);
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("加载失败 ，请稍后再试。。");
		}
	});
}

function closebox(){
	$(".alert_lock").hide(100);
	$(".alert_nolock").hide(100);
	$(".alert_ckan").hide(100);
	$(".alert_shul1").hide(100);
	$(".alert_shul").hide(100);
	$(".alert_shul2").hide(100);
	$(".alert_shul3").hide(100);
}

function checkwarehouseselect(){
	if($('#ownerType').val() == 1 && $('#inventorytypeSpan').css('display')!='none'){
		var liststat = checkLable();
		if(liststat == 1 || liststat ==4 || liststat ==5){
			loadWareSelect(liststat);
		}
	}else{
		$('#warehouseNameSelect').empty();
	}
}

function resetwarehouseselect(){
	var liststat = checkLable() ;
	if($('#warehouseNameSelect').css('display')!='none')  {
		if(liststat=='4'){
			loadWareSelect(3);
		}
		if(liststat=='5'){
			loadWareSelect(2);
		}
		if(liststat=='7'){
			loadWareSelect(4);
		}
	}else{
		$('#warehouseNameSelect').empty();
	}
	resetfm();
}


//转移库存到赠品展示
function moveGiftToInventory(id,batchNo,preSubQty,qty,lockQty,version,dealerId,
		skuId,pid,lockBatch,ownerType,pName,skuName){
	var liststat = checkLable();
	pName = $("#pName2").val();
	skuName = $("#skuName2").val();
	var stock_array = new Array();
	stock_array.push('skuId='+skuId);
	stock_array.push('pid='+pid);
	stock_array.push('lockBatch='+lockBatch);
	stock_array.push('ownerType='+ownerType);
	stock_array.push('dealerId='+dealerId);
	stock_array.push('id='+id);
	$("#editPName_gift1").html(pName);//批次号
	$("#editSkuName_gift1").html(skuName);//锁定数量
	$("#editBatchNo_gift1").html(batchNo);//批次号
	$("#editPreSubQty_gift1").html(preSubQty);//锁定数量
	$("#editLockQty_gift1").html(lockQty);//订单占用数量
	var moveableQty = qty - preSubQty - lockQty;
	$("#editBatchQty_gift1").val(moveableQty);
	$("#editBatchQty_gift1").attr("key",id);
	$("#editBatchQty_gift1").attr("version",version);
	$("#editBatchQty_gift1").attr("dealerId",dealerId);
	$("#editBatchQty_gift1").attr("compareQty",qty);
	$("#editBatchQty_gift_hidden1").val(moveableQty);
	$(".alert_shul3").show(50);
}

//转移库存到赠品保存
function moveGiftToStock(){
	
	//判断转移数量是否超过了可转移数量
	var useableNum = $("#editBatchQty_gift_hidden1").val();
	var writeNum = $("#editBatchQty_gift1").val();
	var id = $("#editBatchQty_gift1").attr("key");
	var  matchnum = /^[0-9]*$/;
	if(!matchnum.test(writeNum)){
		alert("修改数量必须是正整数！");
		return false;
	}

	if(parseInt(writeNum) > parseInt(useableNum)){
		alert("填写数量不能大于可转移库存量");
		return;
	}
	$.ajax({
		async:false,
		type : "post", 
		url    : CONTEXTPATH+"/inventory/moveGiftToStock",
		data :"id="+id+"&qty="+writeNum+"&random="+Math.random(),
		success : function(msg) { 
			if(msg=="操作成功!"){
				tipMessage("保存成功！",function(){
				});
			}else{
				tipMessage("数据保存异常！",function(){
				});
			}
			$(".alert_shul3").hide();
			var pageContext = $("#pageContext").val();
			clickSubmit(pageContext);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("查询失败 ，请稍后再试。。");
		}
	});

}



//转移库存到赠品保存
function moveToGiftSave(){
	//判断转移数量是否超过了可转移数量
	var writeNum = $("#editBatchQty_gift").val();
	var useableNum = $("#editBatchQty_gift_hidden").val();
	var id = $("#editBatchQty_gift").attr("key");
	var  matchnum = /^[0-9]*$/;
	if(!matchnum.test(writeNum)){
		alert("修改数量必须是正整数！");
		return false;
	}
	if(parseInt(writeNum) > parseInt(useableNum)){
		alert("填写数量不能大于可转移库存量");
		return;
	}
	$.ajax({
		async:false,
		type : "post", 
		url    : CONTEXTPATH+"/inventory/moveToGift",
		data :"id="+id+"&qty="+writeNum+"&random="+Math.random(),
		success : function(msg) { 
			if(msg==1){
				tipMessage("保存成功！",function(){
				});
			}else{
				tipMessage("数据保存异常！",function(){
				});
			}
			$(".alert_shul2").hide();
			var pageContext = $("#pageContext").val();
			clickSubmit(pageContext);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("查询失败 ，请稍后再试。。");
		}
	});
}

