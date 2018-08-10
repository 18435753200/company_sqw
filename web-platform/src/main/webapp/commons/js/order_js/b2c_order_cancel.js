function byConditionSearchCustomerOrder(page){
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
	var orderPlatform = $("#orderPlatform").val();

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
	}else{
		customerOrder_array.push("statusList=21,41,81,");
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
    	 url : "../customerOrder/getCustomerOrderPageBeanByOrderCancelCondition", 
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



function exportCustomerOrderExcel(){
	var orderId = $.trim($("#orderId").val());
	var startTime  = $("#startTime").val();
	var endTime  = $("#endTime").val();
	var pName  = $.trim($("#pName").val());
	var receiveName  = $.trim($("#receiveName").val());
	var status  = $("#status").val();
	var userName = $("#userName").val();
	var shipType = $("#shipType").val();
	var payType = $("#payType").val();
	var supplyType = $("#supplyType").val();
	var orderPlatform = $("#orderPlatform").val();

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


//经销商补录物流信息
function goUpdateShipOrderLogistic(){
	var providerId = $("#logistic").val();
	var logisticCode = $("#logisticCode").val();
	var id = $("#shipOrderId").val();
	var providerName = $("#logistic").find("option:selected").text();
	if(providerId==0){
		$(".error").text("请选择物流商!");
		return false;
	}else if(logisticCode==""){
		$(".error").text("请输入物流单号!");
		return false;
	}else{
		$(".error").text("");
		$.ajax({
			type:"post",
			url:"../customerOrder/updateOrderLogisticsById",
			data:{'orderId':id,'logisticsCarriersName':providerName,'logisticsCarriersId':providerId,'logisticsNumber':logisticCode},
			dataType:"html",
			success:function(result){
				if(result=='1'){
					alert("修改成功!");
					$(".logw").hide();
					$("#logistic").val("");
					$("#logisticCode").val("");
					$("#shipOrderId").val("");
					$("#logistic").val(0);
					byConditionSearchCustomerOrder(1);
				}else{
					alert("修改失败!");
				}
			},
			error:function(jqXHR,textStatus,errorThrown){
				alert("网络异常,请稍后再试。。。。");
			}
		});
	}	
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


//显示物流补录框
function showWuLiu(id){
	$("#logistic").val("");
	$("#logisticCode").val("");
	$("#shipOrderId").val("");
	$("#logistic").empty();
	getLogistic();
	$("#shipOrderId").val(id);
	$(".logw").show();
}

function pushOrderAgain(orderId,payId){
	$.dialog.confirm('确定要进行订单取消功能?',function(){
	$.ajax({
	     async:false,
	     type : "get", 
	     data:{"id":orderId,"payId":payId},
	     url : "../customerOrder/orderCancel", 
	     dataType:"json",
	     success : function(result){
	    	 if(result.success){
	    		 window.location.href="../customerOrder/orderCancelByUnfilledOrder";
	    	 }else{
	    		 alert(result.message);
	    	 }
		 },
		 error:function(jqXHR,textStatus,errorThrown){
		  	alert("网络异常,请稍后再试。。。。");
		  }
	});})
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
});








