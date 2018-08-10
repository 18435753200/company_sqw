function getRetailerOrderList(page){
	var payId = $.trim($("#payId").val());
	var createTime  = $("#createTime").val();
	var endTime  = $("#endTime").val();
	var pName  = $.trim($("#pName").val());
	var retailerName  = $.trim($("#retailerName").val());
	var status  = $("#status").val();
	var invoiceType = $("#invoiceType").val();
	var orderPlatform = $("#orderPlatform").val();
	var retailerOrder_array = new Array();
	var matchnum = /^[0-9]*$/;
	if(!matchnum.test(payId)){
  		alert("订单编号只能是数字！");
  		 $("#payId").val("");
  		 $("#payId").focus();
  		 return false;
  	}
	if(payId != ""){
		retailerOrder_array.push("payId="+payId);
	}
	if(invoiceType != ""){
		retailerOrder_array.push("invoiceType="+invoiceType);
	}
	if(orderPlatform != ""){
		retailerOrder_array.push("orderPlatform="+orderPlatform);
	}
	if(createTime != ""){
		retailerOrder_array.push("createTime="+createTime);
	}
	if(endTime != ""){
		retailerOrder_array.push("endTime="+endTime);
	}
	if(retailerName != ""){
		retailerOrder_array.push("retailerName="+retailerName);
	}
	if(pName != ""){
		retailerOrder_array.push("pName="+pName);
	}
	if(status!=""){
		retailerOrder_array.push("status="+status);
	}
	
	if(page!=undefined&&page!=null&&page!=""){
		retailerOrder_array.push("page="+page);
	}
	$.ajax({
		 type : "post", 
    	 url : "../retailerOrder/getRetailerOrderListByCondition", 
    	 data:retailerOrder_array.join('&')+"&random="+Math.random(),
    	 dataType:"html",
    	 success : function(retailerOrder) {
			$(".tab-bd").html(retailerOrder);
    	 },
		 error: function(jqXHR, textStatus, errorThrown){
	        alert("查询失败 ，请稍后再试。。");
		 }
	}); 
}

//wofe导出期货现货订单
function goExportOrderListByConditionExcel(){
	var isFutuere = $("input[type='radio']:checked").val();
	var payId = $.trim($("#payId").val());
	var createTime  = $("#createTime").val();
	var endTime  = $("#endTime").val();
	var pName  = $.trim($("#pName").val());
	var retailerName  = $.trim($("#retailerName").val());
	var status  = $("#status").val();
	var invoiceType = $("#invoiceType").val();
	var orderPlatform = $("#orderPlatform").val();
	var retailerOrder_array = new Array();
	var matchnum = /^[0-9]*$/;
	if(!matchnum.test(payId)){
  		alert("订单编号只能是数字！");
  		 $("#payId").val("");
  		 $("#payId").focus();
  		 return false;
  	}
	if(payId != ""){
		retailerOrder_array.push("payId="+payId);
	}
	if(isFutuere!=""){
		retailerOrder_array.push("isFutuere="+isFutuere);
	}
	if(invoiceType != ""){
		retailerOrder_array.push("invoiceType="+invoiceType);
	}
	if(orderPlatform != ""){
		retailerOrder_array.push("orderPlatform="+orderPlatform);
	}
	if(createTime != ""){
		retailerOrder_array.push("createTime="+createTime);
	}
	if(endTime != ""){
		retailerOrder_array.push("endTime="+endTime);
	}
	if(retailerName != ""){
		retailerOrder_array.push("retailerName="+retailerName);
	}
	if(pName != ""){
		retailerOrder_array.push("pName="+pName);
	}
	if(status!=""){
		retailerOrder_array.push("status="+status);
	}
	
	if(payId == "" && invoiceType == "" && createTime == "" && retailerName == "" && pName == "" && status=="" ){
		alert("请选择条件控制导出的数量,否则服务器超时!");
		return false;
	}else {
		retailerOrder_array.push("meath="+Math.random());
		window.location.href = "../retailerOrder/exportOrderListByConditionExcel?"+retailerOrder_array.join("&");
	}
}


//wofe取消订单功能
function goCancelOrderByWofe(payId){
	$.dialog.confirm('您确定要取消改订单?',function(){
		$.ajax({
			 type : "post", 
		   	 url : "../retailerOrder/goCancelOrderByWofe", 
		   	 data:{'payId':payId},
		   	 dataType:"html",
		   	 success : function(resultMsg) {
		   		 tipMessage(resultMsg,function(){
		   			getRetailerOrderList(1);
		   		 });
		   	 },
			 error: function(jqXHR, textStatus, errorThrown){
				 alert("网络异常,请稍后再试。。。。");
			 }
		}); 
	});
}


function showIsfuture(){
	$(".alert_user2").show();
}

function hideIsfuture(){
	$(".alert_user2").hide();
}

//显示物流补录框
function showWuLiu(id,receiveName,receivePhone,receiveAddress){
	$("#wuliu").html("<tr align='center'><td style='width: 50px;'>物流商ID</td><td style='width: 100px;'>物流公司</td><td style='width: 150px;'>运单号</td><td style='width: 50px;''>操作</td></tr>");
	$("#error").text("");
	$("#receiveNameField").text(receiveName);
	$("#receivePhoneField").text(receivePhone);
	$("#receiveAddressField").text(receiveAddress);
	$("#receiveNameField1").text(receiveName);
	$("#receivePhoneField1").text(receivePhone);
	$("#receiveAddressField1").text(receiveAddress);
	
	$("#logistic").val("");
	$("#logisticCode").val("");
	$("#shipOrderId").val("");
	$("#logistic").empty();
	getLogistic();
	$("#shipOrderId").val(id);
	$(".logw").show();
}

//异步加载所有物流商
function getLogistic(){
	$("#logistic").append("<option value='0'>请选择</option>"); 
	$.ajax({
		type:"post",
		url:"../customerOrder/getLogistic",
		dataType:"json",
		success:function(jsonStock){
			$.each(jsonStock,function(i,n){
				$("#logistic").append("<option value='"+n.providerId+"'>"+n.providerName+"</option>"); 
			});
		},
		error:function(jqXHR,textStatus,errorThrown){
			alert("网络异常,请稍后再试。。。。");
		}
	});
}
//添加物流商
function addwuliu(){
	var providerId = $("#logistic").val();
	var logisticCode = $("#logisticCode").val();
	var id = $("#shipOrderId").val();
	var providerName = $("#logistic").find("option:selected").text();
	var flag = true;
	$(".wlxx").each(function(){
		var tds = $(this).find("td");
		$(".wl").append("<span><p>物流公司："+$(tds[1]).text()+"</p><p>运单号："+$(tds[2]).text()+"</p></span>")
		if((providerId==$(tds[0]).text())&&(logisticCode==$(tds[2]).text())){
			$(".error").text("物流信息重复!");
			flag = false;
			return;
		}
	});
	
	if(providerId==0){
		$(".error").text("请选择物流商!");
		return false;
	}else if(logisticCode==""){
		$(".error").text("请输入物流单号!");
		return false;
	}else if(flag){
		$("#wuliu").append("<tr class='wlxx'><td>"+providerId+"</td><td>"+providerName+"</td><td>"+logisticCode+"</td><td><a href='javascript:void(0)' id='delete-tr' onclick='deleteLogistics()'>删除</a></td></tr>");
		$("#logisticCode").val("");
	}
}
//检查物流信息是否已经存在
function goCheckShipOrderLogistic(){
	$(".wl").empty();
	var providerIds = "";
	var logisticCodes = "";
	var providerNames = "";
	$(".wlxx").each(function(){
		var tds = $(this).find("td");
		$(".wl").append("<span><p>物流公司："+$(tds[1]).text()+"</p><p>运单号："+$(tds[2]).text()+"</p></span>")
		providerIds = providerIds+","+$(tds[0]).text();
		providerNames=providerNames+","+$(tds[1]).text();
		logisticCodes=logisticCodes+","+$(tds[2]).text();
	});
	if(providerIds==0){
		$(".error").text("请选择物流商!");
		return false;
	}else if(logisticCodes==""){
		$(".error").text("请输入物流单号!");
		return false;
	}else{
		$(".ecwl").show();
	}
}

//经销商补录物流信息
function goUpdateOverseasOrderLogistic(){
	$(".wl").empty();
	var providerIds = "";
	var logisticCodes = "";
	var providerNames = "";
	var id = $("#shipOrderId").val();
	$(".wlxx").each(function(){
		var tds = $(this).find("td");
		providerIds = providerIds+","+$(tds[0]).text();
		providerNames=providerNames+","+$(tds[1]).text();
		logisticCodes=logisticCodes+","+$(tds[2]).text();
	});
	if(providerIds==0){
		$(".error").text("请选择物流商!");
		return false;
	}else if(logisticCodes==""){
		$(".error").text("请输入物流单号!");
		return false;
	}else{
		providerIds = providerIds.substring(1);
		providerNames = providerNames.substring(1);
		logisticCodes = logisticCodes.substring(1);
		$(".error").text("");
		$.ajax({
			type:"post",
			url:"../retailerOrder/updateRetailerOrderLogisticsById",
			data:{'orderId':id,'logisticsCarriersNames':providerNames,'logisticsCarriersIds':providerIds,'logisticsNumbers':logisticCodes},
			dataType:"html",
			success:function(result){
				if(result=='1'){
					alert("修改成功!");
					$(".logw").hide();
					$(".ecwl").hide();
					$("#logistic").val("");
					$("#logisticCode").val("");
					$("#shipOrderId").val("");
					$("#logistic").val(0);
					getRetailerOrderList(1);
				}else{
					alert(result);
				}
			},
			error:function(jqXHR,textStatus,errorThrown){
				alert("网络异常,请稍后再试。。。。");
			}
		});
	}	
}
//关闭物流框
function close1(){
	$(".wl").empty();
	$(".ecwl").hide();
}
//删除已填物流信息
function deleteLogistics(){
	$("#delete-tr").parents("tr").remove();
}

//关闭物流补录跳出框
$(function(){
	$(".w_close").click(function(){
		$(".logw").hide();
	});
	$(".bt2").click(function(){
		$(".logw").hide();
	});
});
