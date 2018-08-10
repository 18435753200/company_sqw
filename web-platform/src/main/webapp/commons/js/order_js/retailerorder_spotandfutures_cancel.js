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
    	 url : "../retailerOrder/getRetailerOrderListByCancel", 
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
		window.location.href = "../retailerOrder/exportOrderListByCancelExcel?"+retailerOrder_array.join("&");
	}
}


//wofe取消订单功能
function goCancelOrderByWofe(payId){
	$.dialog.confirm('您确定要取消改订单?',function(){
		$.ajax({
			 type : "post", 
		   	 url : "../retailerOrder/toCancelOrderByWofe", 
		   	 data:{'payId':payId},
		   	 dataType:"html",
		   	 success : function(json) {
		   		 var myObject =new Object();
	        	 myObject=eval('('+json+')');
		   		if(myObject.success){
		   			alert(myObject.message);   		
		    	 }else{
		    		 alert(myObject.message);
		    	 }
		   		 getRetailerOrderList(1);
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






