var  CONTEXTPATH  = $("#conPath").val();
/*	商品js	*/
$(document).ready(function(){
	$(".close-box").live('click',closebox);
	$("#stopReasonType").live('change',changeReasonType);
	//违规下架原因
	$("#underReasonType").live('change',underReasonType);
	
	if ($('#li_POP_list').hasClass('list')) {
        $("#exportExcelDiv").show();
	}else{
		$("#exportExcelDiv").hide();
	}

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

	/*  加载一级目录  */
	$.ajax({
		type : "post", 
		url : CONTEXTPATH+"/product/getFirstDisp", 
		success : function(msg) { 
			$.each(eval(msg),function(i,n){
				$("#firstcategory").append("<option value='"+n.catePubId+"'>"+n.pubNameCn+"</option>");
			});	
		}
	}); 



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


/* 下架 */
function proDown(){

	var stopReasonType = $("#stopReasonType").val();

	if(stopReasonType=='其他'){

		stopReasonType = $('#stopReason').val();

	}
	var pid = $('#alertProductStopReasonId').val();

	if(pid==""){
		$('#boxwarn').text(" 数据异常！");
		return false;
	}else{
		
		var stopType = $("#alertProductStopReasonId").attr('stopType');
		var pid_array = new Array();

		pid_array.push('pid='+pid);
		pid_array.push('stopReason='+stopReasonType);
		pid_array.push('stopType='+stopType);

		$.dialog.confirm('下架后商品不再是出售状态，还下架么？', function(){
			$.ajax({
				type : "post", 
				url : CONTEXTPATH+"/POPproduct/updateProductTateByIds", 
				data:pid_array.join("&")+"&math="+Math.random(),
				success : function(msg) { 
					if(msg==1){
						alert("操作成功");
						$("#goout-box").css("display","none");
						var pageContext = $('#pageContext').val();
						clickSubmit(pageContext);
					}else{
						alert("操作失败 ，请稍后再试");
					}
				}
			});
		});

	}
}

function alertProductDelReason(productId){  
	var pidArray = new Array();
	pidArray.push(productId);
	pidArray.join(',');
	$('#alertProductdelReasonId').val(pidArray);
	$('#delReason').val('');
	$('#del-box').css("display","block");
}

function del_product(){
	var pids = $('#alertProductdelReasonId').val();
	var delReason =  $.trim($('#delReason').val());
	
	if(delReason == '' || pids == ''){
		alert('请填写删除原因!');
		return false;
	}
	var pid_array = new Array();
	
	pid_array.push('pid='+pids);
	pid_array.push('delReason='+delReason);
	
	$.dialog.confirm('确定要删除吗？', function(){
		$.ajax({
			type : "post", 
			url : CONTEXTPATH+"/product/deletePros", 
			data:pid_array.join("&")+"&math="+Math.random(),
			success : function(msg) { 
				if(msg==1){
					alert("操作成功");
					$("#del-box").css("display","none");
					var pageContext = $('#pageContext').val();
					clickSubmit(pageContext);
				}else{
					alert("操作失败 ，请稍后再试");
				}
			}
		});
	});
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
/* 上架 */
function proUp(pid,upType){
	if(pid==null&&pid==""){
		tipMessage("选择商品再操作",function(){});
		return false;
	}else{
		var pid_array = new Array();
		pid_array.push(pid);
		$.dialog.confirm('确定要上架吗？', function(){
			$.ajax({
				type : "post", 
				url : CONTEXTPATH+"/POPproduct/upProductByIds", 
				data:"pid="+pid_array.join(",")+'&upType='+upType,
				success : function(msg) { 
					if(msg == '1'){
						alert("操作成功");
						var pageContext = $('#pageContext').val();
						clickSubmit(pageContext);
					}else if(msg == '-1'){
						
						alert("部分商品状态存在异常！请重新尝试。");
						var pageContext = $('#pageContext').val();
						clickSubmit(pageContext);
						
					}else{
						alert("操作失败 ，请稍后再试");
					}
				}
			});
		},function(){});
	}
}

/*	label跳转	*/	
function getDownProduct(s){
	var currentPageId = "0";
	if($("#isReivewHidden").val()=='1'){
		s = 2;
		$("#isReivewHidden").val("");
	}
    currentPageId = $("#currentPageIdHidden").val();
	if(currentPageId==""){
		currentPageId="0";
	}
    $("#currentPageIdHidden").val("");
	
	checkProductIsTate(s);
	
	$.ajax({
		type : "post", 
		url  : CONTEXTPATH+"/POPproduct/getProductByConditions?auditStatus="+s, 
		data : "page="+currentPageId,
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
/*	查询条件	*/
//page  页数  istate 是否上架 0 否，1 是  statu 
//审核状态:0-待审核/审核中 1-审核不通过 2-审核通过 3-无效记录 4-审核中、5 新增的商品（待修改）
function clickSubmit(page){
	/* 获取选中类目 */
	var status = $(".top").find(".list").find("a").attr("liststatus");

	var businessProdId = $.trim($("#businessProdId").val());
	var productName = $.trim($("#productName").val());
	var b2cProductName = $.trim($("#b2cProductName").val());
	var suppliername = $.trim($("#suppliername").val());
	var productId = $.trim($("#productId").val());
	var barCode = $.trim($("#barCode").val());
	var prodType=$("#prodType").val();
//	var b2bIsTate = $('#b2bIsTate').val();
	var b2cIsTate = $('#popIsTate').val();
	var popshengIsTate = $('#popshengIsTate').val();
	
	var  matchnum = /^[0-9]*$/;
	if(!matchnum.test(productId)){
		alert("商品ID只能是数字！");
		$("#productId").val("");
		$("#productId").focus();
		return false;
	}

	
	if(barCode!=""&&barCode!=undefined){
		if(barCode.length!=19){
			if(!RegExp("^\\d{8}$|^\\d{10}$|^\\d{12,13}$").test(barCode)){
				alert("条形码应为8,10,12,13位数字或者系统生成!");
				$("#barCode").val("");
				$("#barCode").focus();
				return false;
			}
		}
	}
	
	

	var  cate = getCate();

	var pro_array  = new Array();

	if(businessProdId!=""){
		pro_array.push("businessProdId="+businessProdId);
	}
	if(productName!=""){
		pro_array.push("productName="+productName);
	}
	if(b2cProductName!=""){
		pro_array.push("b2cProductName="+b2cProductName);
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
	if(prodType!=""){
		pro_array.push("prodType="+prodType);
	}
	if(page!=""&&page!=undefined){
		pro_array.push("page="+page);
	}
	if(cate!=""&&cate!=undefined){
		pro_array.push("cate="+cate);
	}
	if(barCode!=""&&barCode!=undefined){
		pro_array.push("productBarCode="+barCode);
	}
//	pro_array.push("b2bIsTate="+b2bIsTate);
	pro_array.push("b2cIsTate="+b2cIsTate);
	
	pro_array.push("popshengIsTate="+popshengIsTate);
	
	$.ajax({
		type : "post", 
		url : CONTEXTPATH+"/POPproduct/getProductByConditions", 
		data:pro_array.join("&"),
		dataType:"html",
		success : function(msg) { 
			$("#c3").html(msg);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("对不起，数据异常请稍后再试!");
		}
	}); 


}

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
			if(!RegExp("^\\d{8}$|^\\d{10}$|^\\d{12,13}$").test(barCode)){
				alert("条形码应为8,10,12,13位数字或者系统生成!");
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

	window.open(CONTEXTPATH+"/product/POPdownProductExcel?"+pro_array.join("&")+"&math="+Math.random(),"_blank");
}

function checkClick(statu){
	if(statu=='1'){
		$("#li_POP_list").attr("class","list");
		$("#li_POP_sheng").removeClass("list");
	}
	if(statu=='2'){
		$("#li_POP_sheng").attr("class","list");
		$("#li_POP_list").removeClass("list");
	}
	
	if ($('#li_POP_list').hasClass('list')) {
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
	//关闭违规下架弹出框
	$("#goout-box-under").fadeOut();
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
/* 违规下架*/
function productIllegalUnderShelf(productId,stopType){
	//选中第一个下架原因
	$("#underReasonType").find('option:first').attr('selected', true);
	//提示信息置空
	$('#boxwarnunder').text('');
	//商品ID置空
	$("#productIllegalUnderShelfId").val('');
	//下架原因置空
	$("#underReason").val("");
	$("#underReason").attr('disabled','disabled');
	
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
		$("#productIllegalUnderShelfId").val(join);
		$("#productIllegalUnderShelfId").attr('stopType',stopType);
		$("#goout-box-under").css("display","block");
	}
}
/* 星级设置*/
function productIllegalUnderShelfxj(productId,stopType){
	//商品ID置空
	$("#productIllegalUnderShelfIdxj").val('');
	$("#xjid").html('')
	var list=null;
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
		$("#productIllegalUnderShelfIdxj").val(join);
		$("#productIllegalUnderShelfIdxj").attr('stopType',stopType);
		$("#goout-box-underxj").css("display","block");
		$(function(){
			$.ajax({
				type : "get", 
				url : CONTEXTPATH+"/POPproduct/productxj", 
				success : function(msg){
					list=msg;
					$(list).each(function(index){
						//alert(list[index].tagName)
						$("#xjid").append('&nbsp;').append("<input type='checkbox' id='xj"+index+"'  name='spCodeId' value='"+list[index].tagCode+"'>").append(list[index].tagName);
//						alert(index);
					})
					$.ajax({
						type : "get", 
						url : CONTEXTPATH+"/POPproduct/productxjselect?pid="+join, 
						success : function(msg){
							var rows=document.getElementsByName("spCodeId");
							for (var i = 0; i < rows.length; i++) {
									$(msg).each(function(index){
										if(rows[i].value==msg[index].tagCode){
											console.log(this);
											
											$("#xj"+i+"").attr("checked", true);
										}
									});
							}
						
						}
					})
					}
			})
		});
	}
}
function selectProductPutAwayxg(upType){
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
		productIllegalUnderShelfxg(pid_array.toString(),upType);
	}
}
function selectProductPutAwayxj(upType){
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
		productIllegalUnderShelfxj(pid_array.toString(),upType);
	}
}
function selectProductPutAwayzj(upType){
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
		productIllegalUnderShelfzj(pid_array.toString(),upType);
	}
}
function selectProductPutAwayhh(upType){
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
		productIllegalUnderShelfhh(pid_array.toString(),upType);
	}
}
function selectProductPutAwayhd(upType){
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
		productIllegalUnderShelfhd(pid_array.toString(),upType);
	}
}
function productIllegalUnderShelfhh(productId,stopType){
	$.dialog.confirm('确定增加选中商品的现金支付比例标签么？', function(){
		$.ajax({
			type : "get", 
			url : CONTEXTPATH+"/POPproduct/producthhselect?pid="+productId, 
			success : function(msg){
				window.location.reload();
			}
		})
		});
}
function productIllegalUnderShelfhd(productId,stopType){
	$.dialog.confirm('确定删除选中商品的现金支付比例标签么？', function(){
		$.ajax({
			type : "get", 
			url : CONTEXTPATH+"/POPproduct/producthhselectd?pid="+productId, 
			success : function(msg){
				window.location.reload();
			}
		})
	});
}
/* 限购设置*/
function productIllegalUnderShelfxg(productId,stopType){
	//商品ID置空
	$("#productIllegalUnderShelfIdxg").val('');
	$("#xgid").html('')
	var list=null;
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
		$("#productIllegalUnderShelfIdxg").val(join);
		$("#productIllegalUnderShelfIdxg").attr('stopType',stopType);
		$("#goout-box-underxg").css("display","block");
		$(function(){
			$.ajax({
				type : "get", 
				url : CONTEXTPATH+"/POPproduct/productxg", 
				success : function(msg){
					list=msg;
					$(list).each(function(index){
						//alert(list[index].tagName)
						$("#xgid").append('&nbsp;').append("<input type='checkbox' id='xg"+index+"'  name='xgCodeId' value='"+list[index].tagCode+"'>").append(list[index].tagName);
//						alert(index);
					})
					$.ajax({
						type : "get", 
						url : CONTEXTPATH+"/POPproduct/productxgselect?pid="+join, 
						success : function(msg){
							var rows=document.getElementsByName("xgCodeId");
							for (var i = 0; i < rows.length; i++) {
								$(msg).each(function(index){
									if(rows[i].value==msg[index].tagCode){
										console.log(this);
										$("#xg"+i+"").attr("checked", true);
									}
								});
							}
						}
					})
				}
			})
		});
	}
}
/* 赠券设置*/
function productIllegalUnderShelfzj(productId,stopType){
	//商品ID置空
	$("#productIllegalUnderShelfIdzj").val('');
	$("#zjid").html('')
	var list=null;
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
		$("#productIllegalUnderShelfIdzj").val(join);
		$("#productIllegalUnderShelfIdzj").attr('stopType',stopType);
		$("#goout-box-underzj").css("display","block");
		$(function(){
			$.ajax({
				type : "get", 
				url : CONTEXTPATH+"/POPproduct/productzj", 
				success : function(msg){
					list=msg;
					$("#zjid").append("<option >---不限定---</option>");
					$(list).each(function(index){
						$("#zjid").append("<option  id='zj"+index+"'  name='zjCodeId' value='"+list[index].tagCode+"'>"+list[index].tagName+"</option>");
					})
					$.ajax({
						type : "get", 
						url : CONTEXTPATH+"/POPproduct/productzjselect?pid="+join, 
						success : function(msg){
							var rows=document.getElementsByName("zjCodeId");
							for (var i = 0; i < rows.length; i++) {
								$(msg).each(function(index){
									if(rows[i].value==msg[index].tagCode){
										console.log(this);
										$("#zj"+i+"").attr("selected", true);
									}
								});
							}
							
						}
					})
				}
			})
		});
	}
}

/* 类型设置*/
function productIllegalUnderShelflx(productId,stopType){
	//商品ID置空
	$("#productIllegalUnderShelfIdlx").val('');
	$("#lxid").html('')
	var list=null;
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
		$("#productIllegalUnderShelfIdlx").val(join);
		$("#productIllegalUnderShelfIdlx").attr('stopType',stopType);
		$("#goout-box-underlx").css("display","block");
		$(function(){
			$.ajax({
				type : "get", 
				url : CONTEXTPATH+"/POPproduct/productlx", 
				success : function(msg){
					list=msg;
					$(list).each(function(index){
						$("#lxid").append('&nbsp;').append("<input type='checkbox' id='lx"+index+"'  name='lxCodeId' value='"+list[index].tagCode+"'>").append(list[index].tagName);
					})
					$.ajax({
						type : "get", 
						url : CONTEXTPATH+"/POPproduct/productlxselect?pid="+join, 
						success : function(msg){
							var rows=document.getElementsByName("lxCodeId");
							for (var i = 0; i < rows.length; i++) {
								$(msg).each(function(index){
									if(rows[i].value==msg[index].tagCode){
										console.log(this);
										
										$("#lx"+i+"").attr("checked", true);
									}
								});
							}
							
						}
					})
				}
			})
		});
	}
}
function closexj(){
	$("#goout-box-underxj").css("display","none");
}
function closezj(){
	$("#goout-box-underzj").css("display","none");
}
function closexg(){
	$("#goout-box-underxg").css("display","none");
}
function closelx(){
	$("#goout-box-underlx").css("display","none");
}
//违规下架原原因选则处理
var underReasonType = function(){
	var stopReasonType = $("#underReasonType").val();

	if(stopReasonType == '其他'){
		$("#underReason").removeAttr("disabled");
	}else{
		$("#underReason").val("");
		$("#underReason").attr('disabled','disabled');
	}
};
//违规下架保存
function productUnder(){
	//判断选择的违规下架原因
	var underReasonType = $("#underReasonType").val();
	//选择其他时，文本框内容为下架原因
	if(underReasonType=='其他'){
		underReasonType = $('#underReason').val();
	}
	//商品ID
	var pid = $('#productIllegalUnderShelfId').val();

	if(pid==""){//商品ID为空提示数据异常
		$('#boxwarnunder').text("数据异常！");
		return false;
	}else{
		var stopType = $("#productIllegalUnderShelfId").attr('stopType');
		var pid_array = new Array();

		pid_array.push('pid='+pid);
		pid_array.push('stopReason='+underReasonType);
		pid_array.push('stopType='+stopType);

		$.dialog.confirm('违规下架后，商品会被移动到审核列表，状态变为审核驳回状态，还下架么？', function(){
			$.ajax({
				type : "post", 
				url : CONTEXTPATH+"/POPproduct/productUnderByIds", 
				data:pid_array.join("&")+"&math="+Math.random(),
				success : function(msg) { 
					if(msg==1){
						alert("操作成功");
						$("#goout-box-under").css("display","none");
						var pageContext = $('#pageContext').val();
						clickSubmit(pageContext);
					}else{
						alert("操作失败 ，请稍后再试");
					}
				}
			});
		});

	}
}
//星级限定提交
function productUnderxj(){

	//商品ID
	var pid = $('#productIllegalUnderShelfIdxj').val();
	
	if(pid==""){//商品ID为空提示数据异常
		$('#boxwarnunder').text("数据异常！");
		return false;
	}else{
		var stopType = $("#productIllegalUnderShelfIdxj").attr('stopType');
		var pid_array = new Array();
		pid_array.push('pid='+pid);
		pid_array.push('stopReason='+underReasonType);
		pid_array.push('stopType='+stopType);
		
		$.dialog.confirm('确定给商品添加星级标签么？', function(){
			var rows=document.getElementsByName("spCodeId");
			var ids=[];
			for (var i = 0; i < rows.length; i++) {
				 if(rows[i].checked){
					 ids.push(rows[i].value);
					 }
				
			}
			$.ajax({
				type : "get", 
				url : CONTEXTPATH+"/POPproduct/updatetags?ids="+ids+"&pid="+pid, 
				success : function(msg) { 
					if(msg==1){
						$("#goout-box-underxj").css("display","none");
						window.location.reload();
					}else{
						alert("操作失败 ，请稍后再试");
					}
				}
			});
		});
		
	}
}
//限购限定提交
function productUnderxg(){
	
	//商品ID
	var pid = $('#productIllegalUnderShelfIdxg').val();
	if(pid==""){//商品ID为空提示数据异常
		$('#boxwarnunder').text("数据异常！");
		return false;
	}else{
		var stopType = $("#productIllegalUnderShelfIdxg").attr('stopType');
		var pid_array = new Array();
		pid_array.push('pid='+pid);
		pid_array.push('stopReason='+underReasonType);
		pid_array.push('stopType='+stopType);
		
		$.dialog.confirm('确定给商品添加限购标签么？', function(){
			var rows=document.getElementsByName("xgCodeId");
			var ids=[];
			for (var i = 0; i < rows.length; i++) {
				if(rows[i].checked){
					ids.push(rows[i].value);
				}
				
			}
			//alert(ids);
			$.ajax({
				type : "get", 
				url : CONTEXTPATH+"/POPproduct/updatetagsxg?ids="+ids+"&pid="+pid, 
				success : function(msg) { 
					if(msg==1){
						$("#goout-box-underxg").css("display","none");
						window.location.reload();
					}else{
						alert("操作失败 ，请稍后再试");
					}
				}
			});
		});
		
	}
}

//赠券限定提交
function productUnderzj(){

	//商品ID
	var pid = $('#productIllegalUnderShelfIdzj').val();
	
	if(pid==""){//商品ID为空提示数据异常
		$('#boxwarnunder').text("数据异常！");
		return false;
	}else{
		var stopType = $("#productIllegalUnderShelfIdzj").attr('stopType');
		var pid_array = new Array();
		pid_array.push('pid='+pid);
		pid_array.push('stopReason='+underReasonType);
		pid_array.push('stopType='+stopType);
		
		$.dialog.confirm('确定给商品添加赠券标签么？', function(){
			var rows=document.getElementsByName("zjCodeId");
			var ids=[];
			for (var i = 0; i < rows.length; i++) {
				 if(rows[i].selected){
					 ids.push(rows[i].value);
					 }
				
			}
			$.ajax({
				type : "get", 
				url : CONTEXTPATH+"/POPproduct/updatetagszj?ids="+ids+"&pid="+pid,
				success : function(msg) { 
					if(msg==1){
						$("#goout-box-underzj").css("display","none");
						window.location.reload();
					}else{
						alert("操作失败 ，请稍后再试");
					}
				}
			});
		});
		
	}
}
//类型限定提交
function productUnderlx(){
	
	//商品ID
	var pid = $('#productIllegalUnderShelfIdlx').val();
	
	if(pid==""){//商品ID为空提示数据异常
		$('#boxwarnunder').text("数据异常！");
		return false;
	}else{
		var stopType = $("#productIllegalUnderShelfIdlx").attr('stopType');
		var pid_array = new Array();
		pid_array.push('pid='+pid);
		pid_array.push('stopReason='+underReasonType);
		pid_array.push('stopType='+stopType);
		
		$.dialog.confirm('确定给商品添加类型标签么？', function(){
			var rows=document.getElementsByName("lxCodeId");
			var ids=[];
			for (var i = 0; i < rows.length; i++) {
				if(rows[i].checked){
					ids.push(rows[i].value);
				}
				
			}
			$.ajax({
				type : "get", 
				url : CONTEXTPATH+"/POPproduct/updatetagslx?ids="+ids+"&pid="+pid,
				success : function(msg) { 
					if(msg==1){
						alert("操作成功");
						$("#goout-box-underlx").css("display","none");
						window.location.reload();
					}else{
						alert("操作失败 ，请稍后再试");
					}
				}
			});
		});
		
	}
}