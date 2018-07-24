var  CONTEXTPATH  = $("#conPath").val();
/*	商品js	*/
$(document).ready(function(){
	$(".close-box").live('click',closebox);
	$("#stopReasonType").live('change',changeReasonType);

	$("#productId").change(function(){
		var productId=$("#productId").val();
		var  matchnum = /^[0-9]*$/;
		if(!matchnum.test(productId)){
			alert("商品ID只能是数字！");
			$("#productId").val("");
			$("#productId").focus();
		}
	});
	
	$(".alert_user3").hide();
	$(".sr").click(function(){
		$(".alert_user3").show();
	});
	$(".w_close").click(function(){
		$(".alert_user3").hide();
	});

	if ($('#li_POP_tong').hasClass('list')) {
        $("#exportExcelDiv").show();
	}else{
		$("#exportExcelDiv").hide();
	}
	
	/*  加载一级目录  */



	/* 加载二级目录 */
	$("#firstcategory").change(function(){
		$("#secondcategory").empty();
		$("#thirdcategory").empty();
		$("#fourthcategory").empty();
		$.ajax({
			type : "post", 
			url : CONTEXTPATH+"/product/getOtherDisp", 
			data:"parCateDispId="+$(this).val(),
			success : function(msg) { 
				var fistdisplist="<option value=''>请选择</option>";
				$.each(eval(msg),function(i,n){
					fistdisplist+="<option value='"+n.catePubId+"'>"+n.pubNameCn+"</option>";
				});	
				$("#secondcategory").append(fistdisplist);
			}
		}); 
	});


	/* 加载三级目录 */
	$("#secondcategory").change(function(){
		$("#thirdcategory").empty();
		$("#fourthcategory").empty();
		$.ajax({
			type : "post", 
			url : CONTEXTPATH+"/product/getOtherDisp", 
			data:"parCateDispId="+$(this).val(),
			success : function(msg) { 
				var fistdisplist="<option value=''>请选择</option>";
				$.each(eval(msg),function(i,n){
					fistdisplist+="<option value='"+n.catePubId+"'>"+n.pubNameCn+"</option>";
				});	
				$("#thirdcategory").append(fistdisplist);
			}
		}); 
	});

	/* 加载四级目录 */	
	$("#thirdcategory").change(function(){
		$("#fourthcategory").empty();
		$.ajax({
			type : "post", 
			url : CONTEXTPATH+"/product/getOtherDisp", 
			data:"parCateDispId="+$(this).val(),
			success : function(msg) { 
				var fistdisplist="<option value=''>请选择</option>";
				$.each(eval(msg),function(i,n){
					fistdisplist+="<option value='"+n.catePubId+"'>"+n.pubNameCn+"</option>";
				});	
				$("#fourthcategory").append(fistdisplist);
			}
		}); 
	});
	$('#cp3').find('a:eq(0)').click();
});


function alertProductStopReason(productId,stopType){
	
	$("#stopReasonType").find('option:first').attr('selected', true);
	$('#boxwarn').text('');
	$("#alertProductStopReasonId").val('');
	$("#stopReason").val("");
	$("#stopReason").attr('disabled','disabled');
	
	var pid_array = new Array();
	if(productId==""||productId == undefined){
		var data;
		if(stopType=="0"){
			data=$("input[name='topProB2B']:checked")
		}else{
			data=$("input[name='topProB2C']:checked")
		}
		data.each(function() {
			pid_array.push($(this).attr("id"));
		}); 
		if(pid_array.toString().length==0){
			alert("请先选择商品。");
			return false;
		}
	}else{
		pid_array.push(productId);
	}
	if(pid_array.length>0){
		var join=pid_array.join(",");
		$("#alertProductStopReasonId").val(join);
		$("#alertProductStopReasonId").attr('stopType',stopType);
		$("#goout-box").css("display","block");
	}
}




function close_editinv_box() {
	$("#inv-edit-box").fadeOut();
}

function editInventory(pId, supplierId) {
	$.ajax({
		type : "GET",
		cache:false,
		url  : CONTEXTPATH + "/inventory/editInventory",
		data : "pId=" + pId + "&supplierId=" + supplierId,
		//data : "pId=223919539923729&supplierId=" + supplierId,
		dataType: 'html',
		success : function(msg) {
			if(msg == undefined) {
				alert("查询失败 ，请稍后再试。。");
			} else {
				$("#edit_inv_qty").html(msg);
				$("#inv-edit-box").css("display","block");
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("查询失败 ，请稍后再试。。");
		}
	});
}

function editInventorySubmit(pId, warehouseCode){
	var requestStr = "";
	var  matchnum = /^[0-9]*$/;

	var array = $("#showSkuQty input[name='editNotBatchQty']");
	for(var i=0,len=array.length;i<len;i++){
		var sku = $(array[i]).attr("sku");
		var skuName = $(array[i]).attr("skuName");
		var lockQty = Number($(array[i]).attr("lockQty"));
		var preSubQty = Number($(array[i]).attr("preSubQty"));
		var qty = Number($(array[i]).val());

		if(!matchnum.test(qty)){
			alert("库存数量必须是正整数！");
			return false;
		}

		if(qty < preSubQty+lockQty){
			alert("修改库存数量需大于等于订单占用数量！");
			return false;
		}

		if(i != 0) {
			requestStr += "^";
		}

		requestStr += sku+";"+skuName+";"+lockQty+";"+preSubQty+";"+qty;
	}

	$.ajax({
		type : "post",
		url  : CONTEXTPATH + "/inventory/saveEditInventory",
		data : {data: requestStr, pId: pId, warehouseCode: warehouseCode},
		dataType: 'html',
		success : function(msg) {
			if(msg == 'true') {
				alert("修改成功");
				close_editinv_box();
			} else {
				alert("修改失败 ，请稍后再试。。");
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("修改失败，请稍后再试。。");
		}
	});
}



function alertProductDelReason(productId){  
	var pidArray = new Array();
	pidArray.push(productId);
	pidArray.join(',');
	$('#alertProductdelReasonId').val(pidArray);
	$('#delReason').val('');
	$('#del-box').css("display","block");
}


function checkProductIsEdit(productId,catePubId){
	var pid_array = new Array();

	if(catePubId!=""&&productId!=""){

		pid_array.push("productId="+productId);

		$.ajax({
			type : "post", 
			data: pid_array.join("&"),
			url : CONTEXTPATH+"/product/checkProductIsEdit", 
			dataType:'json',
			success : function(msg) {
				if(msg.statu=='-1'){
					alert(msg.name+"正在修改当前商品!");
				}else if(msg.statu=='-2'){
					alert("商品异常!");
				}else{
					window.open(CONTEXTPATH+"/product/toCreateUI?productId="+productId+"&catePubId="+catePubId);
				}
			},
			error:function(){
				alert("商品信息异常!");
			}
		});
	}
}
/*b2b批量上架*/
function selectProductPutAway(upType){
	var pid_array = new Array();
	var data;
	if(upType==0){
		data = $("input[name='topProB2B']:checked");
	}else{
		data = $("input[name='topProB2C']:checked");
	}
	data.each(function() {
		pid_array.push($(this).attr("id"));
	}); 
	if(pid_array.toString().length==0){
		tipMessage("请先选择商品。",function(){});
		return false;
	}else{
		proUp(pid_array.toString(),upType);
	}
}


/*	label跳转	*/	
function getDownProduct(s){
	var operator = $("#operatorHidden").val();
	resetfm();
	checkProductIsTate(s);
	$.ajax({
		type : "post", 
		url  : CONTEXTPATH+"/product/getPOPProductByConditions", 
		data : "auditStatus="+s+"&operator="+operator,
		dataType: "html",
		success : function(msg) { 
			checkClick(s);
			$("#c3").html(msg);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	}); 

}
/*	label跳转	*/	
function getBalanceOrder(s){
	$("#typeId").val(s);
//	var operator = $("#operatorHidden").val();
//	resetfm();
//	checkProductIsTate(s);
	$.ajax({
		type : "post", 
		url  : CONTEXTPATH+"/order/getBalanceOrderList", 
		data : "type="+s,
		dataType: "html",
		success : function(msg) { 
//			checkClick(s);
			$("#c3").html(msg);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	}); 
	
}
/*	查询条件	*/
//page  页数  istate 是否上架 0 否，1 是  statu 
//审核状态:0-待审核/审核中 1-审核不通过 2-审核通过 3-无效记录 4-审核中、5 新增的商品（待修改）


function downProductListExcel(){
	/* 获取选中类目 */
	var status = $(".top").find(".list").find("a").attr("liststatus");
	
	var productName = $.trim($("#productName").val());
	var suppliername = $.trim($("#suppliername").val());
	var productId = $.trim($("#productId").val());
	var barCode = $.trim($("#barCode").val());
	var b2cIsTate = $('#popIsTate').val();
	
	var  matchnum = /^[0-9]*$/;
	if(!matchnum.test(productId)){
		alert("商品ID只能是数字！");
		$("#productId").val("");
		$("#productId").focus();
		return false;
	}

	
	if(barCode!=""&&barCode!=undefined){
		if(barCode.length!=19){
			if(!RegExp("^\\d{8}$|^\\d{12,13}$").test(barCode)){
				alert("条形码应为8,12,13位数字或者系统生成!");
				$("#barCode").val("");
				$("#barCode").focus();
				return false;
			}
		}
	}
	
	

	var  cate = getCate();

	var pro_array  = new Array();

	if(productName!=""){
		pro_array.push("productName="+productName);
	}
	if(suppliername!=""){
		pro_array.push("suppliername="+suppliername);
	}
	if(productId!=""){
		pro_array.push("productId="+productId);
	}
	if(status!=""){
		pro_array.push("auditStatus="+status);
	}
	if(cate!=""&&cate!=undefined){
		pro_array.push("cate="+cate);
	}
	if(barCode!=""&&barCode!=undefined){
		pro_array.push("productBarCode="+barCode);
	}
	pro_array.push("b2cIsTate="+b2cIsTate);

	//window.open(CONTEXTPATH+"/product/POPdownProductExcel?"+pro_array.join("&")+"&math="+Math.random(),"_blank");
}
function clickSubmit(page){
	var startTime = $("#startTime").val().trim();
	//var oid = $("#oid").val().trim();
	var endTime = $("#endTime").val().trim();
	//var type = $("#typeId").val().trim();
	var pro_array  = new Array();
	/*if(page!=""&&page!=undefined){
		pro_array.push("page="+page);
	}*/
	if(startTime != ""){
		pro_array.push("startTime="+startTime);
	}
	if(endTime != ""){
		pro_array.push("endTime="+endTime);
	}
	
	$.ajax({
		type : "post", 
		url : "../supplier/sousuo",
		data:pro_array.join("&"),
		dataType:"html",
		success : function(msg) { 
			$("#c3").html(msg);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert(errorThrown);
			alert("对不起，数据异常请稍后再试555!");
		}
	}); 


}

function checkClick(statu){
	if(statu=='1'){
		$("#li_POP_tong").attr("class","list");
		$("#li_POP_sheng").removeClass("list");
	}
	if(statu=='2'){
		$("#li_POP_sheng").attr("class","list");
		$("#li_POP_tong").removeClass("list");
	}
	
	if ($('#li_POP_tong').hasClass('list')) {
        $("#exportExcelDiv").show();
	}else{
		$("#exportExcelDiv").hide();
	}
	
}

function getCate(){

	var cateId = "";
	var firstcategory =$("#firstcategory").val();
	var secondcategory = $("#secondcategory").val();
	var  thirdcategory = $("#thirdcategory").val();
	var fourthcategory  = $("#fourthcategory").val();
	if(fourthcategory!=""&&fourthcategory!=null){
		cateId = fourthcategory;
	}else if(thirdcategory!=""&&thirdcategory!=null){
		cateId = thirdcategory;
	}else if(secondcategory!=""&&thirdcategory!=null){
		cateId = secondcategory;
	}else{
		cateId=firstcategory;
	}
	return cateId;
}		
function addInventory(productId){
	location.href=CONTEXTPATH+"/product/getAddInventoryView?productId="+productId;
}
function showmsg(msg){
	$("#showmsgplat").empty();
	$("#showmsgplat").html(msg);
	$(".alert_user3").show();
}

var closebox = function(){
//	close-box
	$("#goout-box").fadeOut();
	$("#del-box").fadeOut();
	$("#copyData").css("display","none");
};

var changeReasonType = function(){
	var stopReasonType = $("#stopReasonType").val();

	if(stopReasonType == '其他'){
		$("#stopReason").removeAttr("disabled");
	}else{
		$("#stopReason").val("");
		$("#stopReason").attr('disabled','disabled');
	}
};

function checkProductIsTate(status){
	/*$('.top a').each(function(){
	
		var status = $(this).attr('liststatus');
	
		if(status==11){
			$('#productIsTate').hide();
		}else{
			
		}
	
	});*/
	if(status=='1'){
		$('#productIsTate').show();
		$("#shengheIsTate").hide();
	}else{
		$('#productIsTate').hide();
		$("#shengheIsTate").show();
	}
}


function getProductByCategory(productId,imgURL,productName,businessProdId){
	
	if(productId==="" || productId === undefined){
		return false;
	}
	
	var pro_array = new Array();
	
	pro_array.push("productId="+productId);
	pro_array.push("imgURL="+imgURL);
	pro_array.push("productName="+productName);
	pro_array.push("businessProdId="+businessProdId);
	pro_array.push("math="+Math.random());
	
	$.ajax({
		type : "post", 
		url : CONTEXTPATH+"/product/getProductByCategory", 
		data:pro_array.join("&"),
		success : function(msg) { 
			$("#copyData .lightbox-box").html(msg);
			$("#copyData").css("display","block");
			
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	}); 
	
}

function coverProduct(nProductId,cProductId){
	if(nProductId==="" || nProductId === undefined||cProductId==="" || cProductId === undefined){
		return false;
	}
	$("#copyData .lightbox-box").html("");
	$("#copyData").css("display","none");
	window.open(CONTEXTPATH+"/product/coverProduct?nProductId="+nProductId+"&cProductId="+cProductId+"&target=2&iseditproperty=0&bodytype=1");
}
