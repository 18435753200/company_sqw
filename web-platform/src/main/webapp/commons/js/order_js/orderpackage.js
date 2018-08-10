//初步加载此项目这个页面时
$(document).ready(function(){
	var orderType = $("#orderType").val();
	var send_array = new Array();
	if($.trim(orderType) != ""){
		send_array.push("orderType="+$.trim(orderType));
	}
	$.ajax({ 
	     async:false,
	     type : "post", 
	     url : "../orderPackage/getOrderPackagePageBeanByCondition", 
	     data:send_array.join("&"),
	     dataType:"html",
	     success : function(shopOrders) {
	     	$(".c3").html(shopOrders);
		   },
		 error:function(jqXHR,textStatus,errorThrown){
		  	alert("网络异常,请稍后再试。。。。");
		  }
     }); 
     $("#orderType").change(function(){    
    	 changeOrderType();
     });
});
	
function getOrderPackageListByCondition(page){
	var orderType = $("#orderType").val();
	var userName = $("#userName").val();
	var payId = $("#payId").val();
	var id = $("#id").val();
	var retailerName = $("#retailerName").val();
	var dealerName = $("#dealerName").val();
	var status = $("#status").val();
	var isFutures = $("#isFutures").val();
	var send_array = new Array();
	if($.trim(payId) != ""){
		send_array.push("payId="+$.trim(payId));
	}
	if($.trim(id) != ""){
		send_array.push("id="+$.trim(id));
	}
	if($.trim(retailerName) != ""){
		send_array.push("retailerName="+$.trim(retailerName));
	}
	if($.trim(dealerName) != ""){
		send_array.push("dealerName="+$.trim(dealerName));
	}
	if($.trim(userName) != ""){
		send_array.push("userName="+$.trim(userName));
	}
	if($.trim(status) != ""){
		send_array.push("status="+$.trim(status));
	}
	if($.trim(isFutures) != ""){
		send_array.push("isFutures="+$.trim(isFutures));
	}
	if($.trim(orderType) != ""){
		send_array.push("orderType="+$.trim(orderType));
	}
	
	send_array.push("page="+page);
	$.ajax({
		type:"post",
		url:"../orderPackage/getOrderPackagePageBeanByCondition",
		data:send_array.join("&"),
		dataType:"html",
		success:function(shopOrders){
			$(".c3").html(shopOrders);
		},
		error:function(jqXHR,textStatus,errorThrown){
			alert("网络异常,请稍后再试。。。。");
		}
	});
}
	
//去分页显示（参数page）
function toPage(page){
	getOrderPackageListByCondition(page);
}

function exportPackageListByConditionExcel(){
	var type = $("#type").val();
	var orderType = $("#orderType").val();
	
	var payId = $("#payId").val();
	var id = $("#id").val();
	var userName = $("#userName").val();
	var retailerName = $("#retailerName").val();
	var dealerName = $("#dealerName").val();
	var status = $("#status").val();
	var isFutures = $("#isFutures").val();
	var startTime = $(".startTime").val();
	var endTime = $(".endTime").val();
	var send_array = new Array();
	if($.trim(payId) != ""){
		send_array.push("payId="+$.trim(payId));
	}
	if($.trim(orderType) != ""){
		send_array.push("orderType="+$.trim(orderType));
	}
	if($.trim(id) != ""){
		send_array.push("id="+$.trim(id));
	}
	if($.trim(userName) != ""){
		send_array.push("userName="+$.trim(userName));
	}
	if($.trim(retailerName) != ""){
		send_array.push("retailerName="+$.trim(retailerName));
	}
	if($.trim(dealerName) != ""){
		send_array.push("dealerName="+$.trim(dealerName));
	}
	if($.trim(status) != ""){
		send_array.push("status="+$.trim(status));
	}
	if($.trim(isFutures) != ""){
		send_array.push("isFutures="+$.trim(isFutures));
	}
	if($.trim(startTime) != ""){
		send_array.push("startTime="+$.trim(startTime));
	}
	if($.trim(endTime) != ""){
		send_array.push("endTime="+$.trim(endTime));
	}
	send_array.push("type="+$.trim(type));
	send_array.push("meath="+Math.random());
	window.location.href = "../orderPackage/exportPackageListByConditionExcel?"+send_array.join("&");
	 $(".startTime").val("");
	$(".endTime").val("");
	$(".alert_user2").hide();	
}
function showIsfuture(){
	$(".alert_user2").show();
}

function hideIsfuture(){
	$(".startTime").val("");
	$(".endTime").val("");
	$(".alert_user2").hide();
}


function changeOrderType(){
	var orderType = $("#orderType").val();
	var userName = "<span id='userNameSpan'>用户名:</span><input type='text' class='text1' id='userName'>";
	var retailer = "<p class='p1' id='isFuturesp'><span id = 'retailerNameSpan'>期现货:</span>" +
				   "<select id='isFutures' class='text1'><option value=''>全部</option><option value='0'>现货</option>" +
				   "<option value='1'>期货</option></select></p>";
	var retailerName = "<span id = 'retailerNameSpan'>零售商:</span><input type='text' class='text1' id='retailerName'>";
	if(orderType=='2'){
		$("#retailerNameSpan").remove();
		$("#retailerName").remove();
		$("#isFuturesp").remove();
		$("#p1").append(userName);
		getOrderPackageListByCondition(1);
	}else{
		$("#userName").remove();
		$("#userNameSpan").remove();
		$("#p1").append(retailerName);
		$("#p1").after(retailer);
		getOrderPackageListByCondition(1);
	}		
}









