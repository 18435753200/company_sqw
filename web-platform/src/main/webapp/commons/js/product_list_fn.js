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
				url : CONTEXTPATH+"/product/updateProductTateByIds", 
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
				url : CONTEXTPATH+"/product/upProductByIds", 
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
	checkProductIsTate(s);
	$.ajax({
		type : "post", 
		url  : CONTEXTPATH+"/product/getProductByConditions", 
		data : "auditStatus="+s,
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
	var b2bIsTate = $('#b2bIsTate').val();
	var b2cIsTate = $('#b2cIsTate').val();
	
	var startTime  = $("#startTime").val();
	var endTime  = $("#endTime").val();
	
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
	if(page!=""&&page!=undefined){
		pro_array.push("page="+page);
	}
	if(cate!=""&&cate!=undefined){
		pro_array.push("cate="+cate);
	}
	if(barCode!=""&&barCode!=undefined){
		pro_array.push("productBarCode="+barCode);
	}
	pro_array.push("b2bIsTate="+b2bIsTate);
	pro_array.push("b2cIsTate="+b2cIsTate);
	if(startTime != ""){
		pro_array.push("startTime="+startTime);
	}
	if(endTime != ""){
		pro_array.push("endTime="+endTime);
	}

	$.ajax({
		type : "post", 
		url : CONTEXTPATH+"/product/getProductByConditions", 
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

function downProductListExcel(b2bType){
	/* 获取选中类目 */
	var status = $(".top").find(".list").find("a").attr("liststatus");
	
	var businessProdId = $.trim($("#businessProdId").val());
	var productName = $.trim($("#productName").val());
	var suppliername = $.trim($("#suppliername").val());
	var productId = $.trim($("#productId").val());
	var barCode = $.trim($("#barCode").val());
	var b2cProductName = $.trim($("#b2cProductName").val());
	var startTime  = $("#startTime").val();
	var endTime  = $("#endTime").val();
	
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
	if(b2cProductName!=""){
		pro_array.push("b2cProductName="+b2cProductName);
	}
	if(productName!=""){
		pro_array.push("productName="+productName);
	}
	if(b2bType!=""){
		pro_array.push("b2bType="+b2bType);
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
	if(startTime != ""){
		pro_array.push("startTime="+startTime);
	}
	if(endTime != ""){
		pro_array.push("endTime="+endTime);
	}
	window.open(CONTEXTPATH+"/product/downB2bProductExcel?"+pro_array.join("&")+"&math="+Math.random(),"_blank");
}

function checkClick(statu){
	if(statu=='0'){
		$("#li_chushou_B2C").attr("class","list");
		$("#li_chushou_B2B").removeClass("list");
		$("#li_shenhe").removeClass("list");
		$("#li_meishenhe").removeClass("list");
		$("#li_xiajia").removeClass("list");
		$("#li_xinzeng").removeClass("list");
		$("#li_newsku").removeClass("list");
		$("#li_del").removeClass("list");
	}
	if(statu=='1'){
		$("#li_chushou_B2B").attr("class","list");
		$("#li_chushou_B2C").removeClass("list");
		$("#li_shenhe").removeClass("list");
		$("#li_meishenhe").removeClass("list");
		$("#li_xiajia").removeClass("list");
		$("#li_xinzeng").removeClass("list");
		$("#li_newsku").removeClass("list");
		$("#li_del").removeClass("list");
	}
	if(statu=='2'){
		$("#li_shenhe").attr("class","list");
		$("#li_chushou_B2B").removeClass("list");
		$("#li_chushou_B2C").removeClass("list");
		$("#li_meishenhe").removeClass("list");
		$("#li_xiajia").removeClass("list");
		$("#li_xinzeng").removeClass("list");
		$("#li_newsku").removeClass("list");
		$("#li_del").removeClass("list");
	}
	if(statu=='3'){
		$("#li_meishenhe").attr("class","list");
		$("#li_shenhe").removeClass("list");
		$("#li_chushou_B2B").removeClass("list");
		$("#li_chushou_B2C").removeClass("list");
		$("#li_newsku").removeClass("list");
		$("#li_xiajia").removeClass("list");
		$("#li_xinzeng").removeClass("list");
		$("#li_del").removeClass("list");
	}
	if(statu=='4'){
		$("#li_xiajia").attr("class","list");
		$("#li_shenhe").removeClass("list");
		$("#li_meishenhe").removeClass("list");
		$("#li_chushou_B2B").removeClass("list");
		$("#li_chushou_B2C").removeClass("list");
		$("#li_newsku").removeClass("list");
		$("#li_xinzeng").removeClass("list");
		$("#li_del").removeClass("list");
	}
	if(statu=='5'){
		$("#li_xinzeng").attr("class","list");
		$("#li_xiajia").removeClass("list");
		$("#li_shenhe").removeClass("list");
		$("#li_meishenhe").removeClass("list");
		$("#li_newsku").removeClass("list");
		$("#li_chushou_B2B").removeClass("list");
		$("#li_chushou_B2C").removeClass("list");
		$("#li_del").removeClass("list");
	}
	if(statu=='6'){
		$("#li_xinzeng").removeClass("list");
		$("#li_newsku").attr("class","list");
		$("#li_xiajia").removeClass("list");
		$("#li_shenhe").removeClass("list");
		$("#li_meishenhe").removeClass("list");
		$("#li_chushou_B2B").removeClass("list");
		$("#li_chushou_B2C").removeClass("list");
		$("#li_del").removeClass("list");
		
	}
	if(statu=='9'){
		$("#li_newsku").removeClass("list");
		$("#li_xinzeng").removeClass("list");
		$("#li_del").attr("class","list");
		$("#li_xiajia").removeClass("list");
		$("#li_shenhe").removeClass("list");
		$("#li_meishenhe").removeClass("list");
		$("#li_chushou_B2B").removeClass("list");
		$("#li_chushou_B2C").removeClass("list");

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
	if(status=='11'){
		$('#productIsTate').show();
	}else{
		$('#productIsTate').hide();
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
