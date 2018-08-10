var  CONTEXTPATH  = $("#conPath").val();
$(document).ready(function(){
	clickSubmit();
	$("#orderId").change(function(){
		var orderId = $("#orderId").val();
		var  matchnum = /^[0-9]*$/;
	    if(!matchnum.test(orderId)){
	     		alert("订单编号只能是数字！");
	     		 $("#orderId").val("");
	     		 $("#orderId").focus();
	     }
	});
}); 
    	
function getOrderStatu(status){
	$.ajax({
		 type : "post", 
     	 url : CONTEXTPATH+"/orderqihuo/getOrder", 
     	 data:"statusToList="+status,
     	 dataType:"html",
         success : function(msg) {
         $(".c3").html(msg);
         if(status=='0'){
         	$("#suoyou").attr("class","list");
         	$("#dengdai").removeClass("list");
         	$("#wancheng").removeClass("list");
         	$(".p3").show();
         }else if(status=='2'){
         	$("#suoyou").removeClass("list");
         	$("#dengdai").attr("class","list");
         	$("#wancheng").removeClass("list");
         	$(".p3").hide();
         }else if(status=='91'){
     		$("#suoyou").removeClass("list");
         	$("#dengdai").removeClass("list");
         	$("#wancheng").attr("class","list");
         	$(".p3").hide();
         }
			
	},
		 error: function(jqXHR, textStatus, errorThrown){
	        alert("查询失败 ，请稍后再试。。");
	     }
		}); 
}

function clickSubmit(page){

	var payId = $.trim($("#orderId").val());
	var createTime  = $("#startTime").val();
	var endTime  = $("#endTime").val();
	var pName  = $.trim($("#pName").val());
	var status  = $("#status").val();
	var retailerName  = $.trim($("#retailerName").val());
	var invoiceType  = $.trim($("#invoiceType").val());
	
	var  matchnum = /^[0-9]*$/;
    if(!matchnum.test(payId)){
     		alert("订单编号只能是数字！");
     		 $("#orderId").val("");
     		 $("#orderId").focus();
     		 return false;
     	}
	if(createTime!=""&&endTime!=""){
		if(createTime>endTime){
			alert("开始时间不能大于结束时间！");
			return false;
		}
	}
	
	var click_array = new Array();
	
	if(page!=undefined&&page!=null&&page!=""){
		click_array.push("page="+page);
	}
	if(payId!=""){
		click_array.push("payId="+payId);
	}
	if(createTime!=""){
		click_array.push("createTime="+createTime);
	}
	if(endTime!=""){
		click_array.push("endTime="+endTime);
	}
	if(pName!=""){
		click_array.push("pName="+pName);
	}
	if(retailerName!=""){
		click_array.push("retailerName="+retailerName);
	}
	if(invoiceType!=""){
		click_array.push("invoiceType="+invoiceType);
	}
	if(status!=""){
		click_array.push("status="+status);
	}
	var  statucheck = getstatu();
	if(statucheck!="0"){
		status = statucheck;
		click_array.push("statusToList="+status);
	}else{
		click_array.push("statusToList="+status);
	}
	
	$.ajax({
		 type : "post", 
     	 url : CONTEXTPATH+"/orderqihuo/getOrder", 
     	 data:click_array.join('&')+"&random="+Math.random(),
     	 dataType:"html",
         success : function(msg){
			$(".c3").html(msg);
		},
		 error: function(jqXHR, textStatus, errorThrown){
	        alert("查询失败 ，请稍后再试。。");
	     }
	}); 
}

function showMessage(msg){
	$(".xinx").empty();
	$(".xinx").html(msg);
	$(".alert_xiaox").show();
}

function confirmOrder(orderId){
	tipMessage('确定要催款吗?',function(){
		$.ajax({
			type : "post", 
			url : CONTEXTPATH+"/orderqihuo/confirmOrder", 
			data:"orderId="+orderId,
			dataType:"html",
			success : function(msg) {
				alert(msg);
				var pageContext = $("#pageContext").val();
				clickSubmit(pageContext);
			},
			error: function(jqXHR, textStatus, errorThrown){
				alert("催款失败 ，请稍后再试。。");
			}
		}); 
	});
}
function clickSubmit_search(){
	checkstatu('0');
	clickSubmit();
}
function getstatu(){
	var nowstatu = '';
	var suoyou = $("#suoyou").attr("class");
	var wancheng = $("#wancheng").attr("class");
	
	if(suoyou=='list'){
		nowstatu='0';
	}
	if(wancheng=='list'){
		nowstatu='91';
	}
	
	return nowstatu;
}
function checkstatu(statu){
	  if(statu=='0'){
     	$("#suoyou").attr("class","list");
     	$("#dengdai").removeClass("list");
     	$("#wancheng").removeClass("list");
     }else if(statu=='2'){
     	$("#suoyou").removeClass("list");
     	$("#dengdai").attr("class","list");
     	$("#wancheng").removeClass("list");
     }else if(statu=='3'){
 		$("#suoyou").removeClass("list");
     	$("#dengdai").removeClass("list");
     	$("#wancheng").attr("class","list");
     }
}
