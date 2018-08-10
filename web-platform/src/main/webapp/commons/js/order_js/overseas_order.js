function byConditionSearchCustomerOrder(page){
	var orderId = $.trim($("#orderId").val());
	var startTime  = $("#startTime").val();
	var endTime  = $("#endTime").val();
	var pName  = $.trim($("#pName").val());
	var receiveName  = $.trim($("#receiveName").val());
	var status  = $("#status").val();
	var userName = $("#userName").val();
	var supplyType = 11;
	var customerOrder_array = new Array();
	var matchnum = /^[0-9]*$/;
	var shipType = $("#shipType").val();
	var payType = $("#payType").val();
	var orderPlatform = $("#orderPlatform").val();
	var merchantid = $("#popSupplier").val();
	var orderType = $("#orderType").val();
	var orderSource = $("#orderSource").val();
	
	if(!matchnum.test(orderId)){
  		alert("订单编号只能是数字！");
  		 $("#orderId").val("");
  		 $("#orderId").focus();
  		 return false;
  	}
	if(orderType != ""){
		customerOrder_array.push("orderType="+orderType);
	}
	if(orderSource != ""){
		customerOrder_array.push("orderSource="+orderSource);
	}
	if(orderId != ""){
		customerOrder_array.push("orderId="+orderId);
	}
	if(startTime != ""){
		customerOrder_array.push("startTime="+startTime);
	}
	if(endTime != ""){
		customerOrder_array.push("endTime="+endTime);
	}
	if(receiveName != ""){
		customerOrder_array.push("receiveName="+receiveName);
	}
	if(pName != ""){
		customerOrder_array.push("pName="+pName);
	}
	if(supplyType != ""){
		customerOrder_array.push("supplyType="+supplyType);
		if (supplyType == "51" && merchantid != "") {
			customerOrder_array.push("merchantid=" + merchantid);
		}
	}
	if(userName != ""){
		customerOrder_array.push("userName="+userName);
	}
	if(status!=""){
		customerOrder_array.push("status="+status);
	}
	if(shipType!=""){
		customerOrder_array.push("shipType="+shipType);
	}
	if(payType!=""){
		customerOrder_array.push("payType="+payType);
	}
	if(orderPlatform!=""){
		customerOrder_array.push("orderPlatform="+orderPlatform);
	}
	if(page!=undefined&&page!=null&&page!=""){
		customerOrder_array.push("page="+page);
	}
	 console.log("------"+orderPlatform);
	$.ajax({
		 type : "post", 
    	 url : "../customerOrder/getOverseasOrderPageBeanByCondition", 
    	 data:customerOrder_array.join('&')+"&random="+Math.random(),
    	 dataType:"html",
    	 success : function(customerOrder) {
			$(".tab-bd").html(customerOrder);
    	 },
		 error: function(jqXHR, textStatus, errorThrown){
	        alert("查询失败 ，请稍后再试。。");
		 }
	}); 
}

function byConditionSearchCustomerOrderPush(page,pageType){
	var orderId = $.trim($("#orderId").val());
	var startTime  = $("#startTime").val();
	var endTime  = $("#endTime").val();
	var pName  = $.trim($("#pName").val());
	var receiveName  = $.trim($("#receiveName").val());
	var status  = $("#status").val();
	var userName = $("#userName").val();
	var supplyType = $("#supplyType").val();
	var customerOrder_array = new Array();
	var matchnum = /^[0-9]*$/;
	var shipType = $("#shipType").val();
	var payType = $("#payType").val();

	if(!matchnum.test(orderId)){
  		alert("订单编号只能是数字！");
  		 $("#orderId").val("");
  		 $("#orderId").focus();
  		 return false;
  	}

	if(orderId != ""){
		customerOrder_array.push("orderId="+orderId);
	}
	if(startTime != ""){
		customerOrder_array.push("startTime="+startTime);
	}
	if(endTime != ""){
		customerOrder_array.push("endTime="+endTime);
	}
	if(receiveName != ""){
		customerOrder_array.push("receiveName="+receiveName);
	}
	if(pName != ""){
		customerOrder_array.push("pName="+pName);
	}
	if(supplyType != ""){
		customerOrder_array.push("supplyType="+supplyType);
	}
	if(userName != ""){
		customerOrder_array.push("userName="+userName);
	}
	if(status!=""){
		if(status.indexOf(",")!=-1){
			customerOrder_array.push("statusList="+status);
		}else{
			customerOrder_array.push("status="+status);
		}
	}
	if(shipType!=""){
		customerOrder_array.push("shipType="+shipType);
	}
	if(payType!=""){
		customerOrder_array.push("payType="+payType);
	}
	if(pageType!=""){
		customerOrder_array.push("pageType="+pageType);
	}
	if(page!=undefined&&page!=null&&page!=""){
		customerOrder_array.push("page="+page);
	}
//	 console.log("------"+payType);
	$.ajax({
		 type : "post", 
    	 url : "../customerOrder/getCustomerOrderPageBeanByCondition", 
    	 data:customerOrder_array.join('&')+"&random="+Math.random(),
    	 dataType:"html",
    	 success : function(customerOrder) {
			$(".tab-bd").html(customerOrder);
    	 },
		 error: function(jqXHR, textStatus, errorThrown){
	        alert("查询失败 ，请稍后再试。。");
		 }
	}); 
}
function byErrorReSearchCustomerOrderPush(page,pageType){
	var orderId = $.trim($("#orderId").val());
	var startTime  = $("#startTime").val();
	var endTime  = $("#endTime").val();
	var pName  = $.trim($("#pName").val());
	var receiveName  = $.trim($("#receiveName").val());
	var status  = $("#status").val();
	var userName = $("#userName").val();
	var supplyType = $("#supplyType").val();
	var customerOrder_array = new Array();
	var matchnum = /^[0-9]*$/;
	var shipType = $("#shipType").val();
	var payType = $("#payType").val();
	
	if(!matchnum.test(orderId)){
		alert("订单编号只能是数字！");
		$("#orderId").val("");
		$("#orderId").focus();
		return false;
	}
	
	if(orderId != ""){
		customerOrder_array.push("orderId="+orderId);
	}
	if(startTime != ""){
		customerOrder_array.push("startTime="+startTime);
	}
	if(endTime != ""){
		customerOrder_array.push("endTime="+endTime);
	}
	if(receiveName != ""){
		customerOrder_array.push("receiveName="+receiveName);
	}
	if(pName != ""){
		customerOrder_array.push("pName="+pName);
	}
	if(supplyType != ""){
		customerOrder_array.push("supplyType="+supplyType);
	}
	if(userName != ""){
		customerOrder_array.push("userName="+userName);
	}
	if(status!=""){
		if(status.indexOf(",")!=-1){
			customerOrder_array.push("statusList="+status);
		}else{
			customerOrder_array.push("status="+status);
		}
	}
	if(shipType!=""){
		customerOrder_array.push("shipType="+shipType);
	}
	if(payType!=""){
		customerOrder_array.push("payType="+payType);
	}
	if(pageType!=""){
		customerOrder_array.push("pageType="+pageType);
	}
	if(page!=undefined&&page!=null&&page!=""){
		customerOrder_array.push("page="+page);
	}
//	 console.log("------"+payType);
	$.ajax({
		type : "post", 
		url : "../customerOrder/getRechageErrorOrderPageBeanByCondition", 
		data:customerOrder_array.join('&')+"&random="+Math.random(),
		dataType:"html",
		success : function(customerOrder) {
			$(".tab-bd").html(customerOrder);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert("查询失败 ，请稍后再试。。");
		}
	}); 
}

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

function deleteLogistics(){
	$("#delete-tr").parents("tr").remove();
}

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
			url:"../customerOrder/updateOverseasOrderLogisticsById",
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
					byConditionSearchCustomerOrder(1);
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

function searchCustomerReErrorOrderPush(){
	$.ajax({ 
	     async:false,
	     type : "post", 
	     //data:{"statusList":"67,68,69,70,","pageType":"orderFaile"},
	     url : "../customerOrder/getRechageErrorOrderPageBeanByCondition", 
	     dataType:"html",
	     success : function(customerOrder){
	     	$(".tab-bd").html(customerOrder);
		 },
		 error:function(jqXHR,textStatus,errorThrown){
		  	alert("网络异常,请稍后再试。。。。");
		  }
    }); 
}

function exportCustomerOrderExcel(){
	var orderId = $.trim($("#orderId").val());
	var orderType = $.trim($("#orderType").val());
	var startTime  = $("#startTime").val();
	var endTime  = $("#endTime").val();
	var pName  = $.trim($("#pName").val());
	var receiveName  = $.trim($("#receiveName").val());
	var status  = $("#status").val();
	var userName = $("#userName").val();
	var shipType = $("#shipType").val();
	var payType = $("#payType").val();
	var supplyType = "11";
	var orderPlatform = $("#orderPlatform").val();
	var merchantid = $("#popSupplier").val();
	var orderSource = $("#orderSource").val();
	var customerOrder_array = new Array();
	var matchnum = /^[0-9]*$/;
	if(!matchnum.test(orderId)){
  		alert("订单编号只能是数字！");
  		 $("#orderId").val("");
  		 $("#orderId").focus();
  		 return false;
  	}
	if(orderId != ""){
		customerOrder_array.push("orderId="+orderId);
	}
	if(orderType != ""){
		customerOrder_array.push("orderType="+orderType);
	}
	if(orderSource != ""){
		customerOrder_array.push("orderSource="+orderSource);
	}
	if(startTime != ""){
		customerOrder_array.push("startTime="+startTime);
	}
	if(endTime != ""){
		customerOrder_array.push("endTime="+endTime);
	}
	if(receiveName != ""){
		customerOrder_array.push("receiveName="+receiveName);
	}
	if(pName != ""){
		customerOrder_array.push("pName="+pName);
	}
	if(userName != ""){
		customerOrder_array.push("userName="+userName);
	}
	if(status!=""){
		customerOrder_array.push("status="+status);
	}
	if(shipType!=""){
		customerOrder_array.push("shipType="+shipType);
	}
    if(payType!=""){
    	customerOrder_array.push("payType="+payType);
    }
	if(supplyType!=""){
    	customerOrder_array.push("supplyType="+supplyType);
    	if ("51" == supplyType && merchantid != "") {
        	customerOrder_array.push("merchantid="+merchantid);
    	}
    }
	if(orderPlatform!=""){
		customerOrder_array.push("orderPlatform="+orderPlatform);
	}
    console.log("------"+orderPlatform);
   
	window.location.href = "../customerOrder/exportCustomerOrderExcel?"+customerOrder_array.join("&");
}

function exportCustomerOrderFailExcel(){
	var orderId = $.trim($("#orderId").val());
	var startTime  = $("#startTime").val();
	var endTime  = $("#endTime").val();
	var pName  = $.trim($("#pName").val());
	var receiveName  = $.trim($("#receiveName").val());
	var status  = $("#status").val();
	var userName = $("#userName").val();
	var shipType = $("#shipType").val();
	var payType = $("#payType").val();
	var customerOrder_array = new Array();
	var matchnum = /^[0-9]*$/;
	if(!matchnum.test(orderId)){
  		alert("订单编号只能是数字！");
  		 $("#orderId").val("");
  		 $("#orderId").focus();
  		 return false;
  	}
	if(orderId != ""){
		customerOrder_array.push("orderId="+orderId);
	}
	if(startTime != ""){
		customerOrder_array.push("startTime="+startTime);
	}
	if(endTime != ""){
		customerOrder_array.push("endTime="+endTime);
	}
	if(receiveName != ""){
		customerOrder_array.push("receiveName="+receiveName);
	}
	if(pName != ""){
		customerOrder_array.push("pName="+pName);
	}
	if(userName != ""){
		customerOrder_array.push("userName="+userName);
	}
	if(status!=""){
		if(status.indexOf(",")!=-1){
			customerOrder_array.push("statusList="+status);
		}else{
			customerOrder_array.push("status="+status);
		}
	}
	if(shipType!=""){
		customerOrder_array.push("shipType="+shipType);
	}
    if(payType!=""){
    	customerOrder_array.push("payType="+payType);
    }
//    console.log("------"+payType);
   
	window.location.href = "../customerOrder/exportCustomerOrderExcel?"+customerOrder_array.join("&");
}



function showUpdateXinxi(orderId){
	$("#upOrderId").val(orderId);
	$(".alert_user2").show();
}

function closeUpdateXinxi(){
	$(".alert_user2").hide();
}

function updateB2cOrderStatus(){
	var orderId = $("#upOrderId").val();
	var status  = $("#upStatus").val();
	if(status==0){
		alert("请选择状态!");
		return false;
	}
	$.ajax({
		 type : "post", 
	   	 url : "../customerOrder/updateOrderStatusByOrderId", 
	   	 data:"orderId="+orderId+"&status="+status,
	   	 dataType:"html",
	   	 success : function(customerOrder) {
				alert(customerOrder);
				closeUpdateXinxi();
	   	 },
			 error: function(jqXHR, textStatus, errorThrown){
		        alert("操作失败 ，请稍后再试。。");
			 }
		}); 
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





//关闭物流补录跳出框
$(function(){
	$(".w_close").click(function(){
		$(".logw").hide();
	});
	$(".bt2").click(function(){
		$(".logw").hide();
	});
});
function close1(){
	$(".wl").empty();
	$(".ecwl").hide();
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

function pushOrderAgain(orderId,status){
	$.dialog.confirm('确定要海关订单重推?',function(){
		$.ajax({
		     async:false,
		     type : "get", 
		     data:{"id":orderId,"status":status},
		     url : "../customerOrder/pushOrderAgain", 
		     dataType:"json",
		     success : function(result){
		    	 if(result.success){
		    		 window.location.href="../customerOrder/getOrderPushFailuerList";
		    	 }else{
		    		 alert(result.message);
		    	 }
			 },
			 error:function(jqXHR,textStatus,errorThrown){
			  	alert("网络异常,请稍后再试。。。。");
			  }
		});
	})
}


$(function(){
	$("#logistic").blur(function(){
		var providerId = $("#logistic").val();
		if(providerId==0){
			$(".error").text("请选择物流商!");
			$("#logistic").addClass("error1");
		}else {
			$("#logistic").removeClass("error1");
			$(".error").text("");
		}
	});
	
	$("#logisticCode").blur(function(){
		var logisticCode = $("#logisticCode").val();
		if(logisticCode==""){
			$(".error").text("请填写物流单号!");
			$("#logisticCode").addClass("error1");
		}else if (logisticCode.length>500) {
			$(".error").text("物流单号在500个字符以内!");
			$("#logisticCode").addClass("error1");
		}
		else {
			$("#logisticCode").removeClass("error1");
			$(".error").text("");
		}
	});
	
	//充值失败订单重推
	/*$("#changeRechageErrorOrder").on("click",function(){
		var orderId = $(this).val();
		$.ajax({
			type:"get",
		    data:{},
		    url:"../customerOrder/changeRechageErrorOrderDetailById?orderId="+orderId,
		    success:function(result){
		    	
		    },
		    error:function(){
		    	alert("网络异常,请稍后再试。。。。");
		    }
		});
	});*/
});

//充值失败订单重推
function changeRechageErrorOrder(orderId,phone){
	
	$.ajax({
		type:"get",
	    data:{},
	    url:"../customerOrder/changeRechageErrorOrderDetailById?orderId="+orderId+"&phone="+phone,
	    success:function(result){
	    	alert(result+"，3秒后将刷新本页面");
	    	setTimeout(function(){window.location.href = "../customerOrder/platform_recharge_error_again";}, 3000); 
	    },
	    error:function(){
	    	alert("网络异常,请稍后再试。。。。");
	    }
	});
}

// 加载POP商家列表
function loadSupplyStore() {
	$.ajax({
	     async:false,
	     type : "get", 
	     data:{},
	     url : "../customerOrder/getAllPopSupplier", 
	     dataType:"json",
	     success : function(result){
	    	 console.log(result);
	    	 for (var i = 0; i < result.length; i++) {
	    		 $("#popSupplier").append("<option value='" +result[i].supplierId+ "'>"+result[i].name+"</option>");
	    	 }
		 },
		 error:function(jqXHR,textStatus,errorThrown){
		  	alert("网络异常,请稍后再试。。。。");
		  }
	});
}
function supplyTypeChange() {
	
	var supplyType = $("#supplyType").val();
	if (supplyType == 51) {
		$("#popSupplierLable").show();
		$("#popSupplier").show();
		$("#popSupplier").val("");
	}
	else {
		$("#popSupplierLable").hide();
		$("#popSupplier").hide();
		$("#popSupplier").val("");
	}
}
