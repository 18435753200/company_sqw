var  CONTEXTPATH  = $("#conPath").val();
$(document).ready(function(){
	$("#orderId").change(function(){
		var orderId = $("#orderId").val();
		var  matchnum = /^[0-9]*$/;
	    if(!matchnum.test(orderId)){
	     		alert("订单ID只能是数字！");
	     		 $("#orderId").val("");
	     		 $("#orderId").focus();
	     		 return false;
	     	}
	});
});

function clickSubmit(page){
	var issubmit = true;
	var  startTime = $("#startTime").val();
	var  payId = $("#payId").val();
	var  endTime = $("#endTime").val();
	var  orderId = $.trim($("#orderId").val());
	var  smallAmount =$.trim( $("#smallAmount").val());
	var  bigAmount = $.trim($("#bigAmount").val());
	//xiazai ye
	var  nowPage = $("#nowpage").val();
	var  matchnum = /^[0-9]*$/;
	if(!matchnum.test(orderId)){
		alert("订单ID只能是数字！");
		$("#orderId").val("");
		$("#orderId").focus();
		return false;
	}
	if(!matchnum.test(payId)){
		alert("支付号只能是数字！");
		$("#payId").val("");
		$("#payId").focus();
		return false;
	}

	var array_list = new Array();

	if(startTime!=''&&endTime!=''){
		if(startTime>endTime){
			alert("开始时间不能大于结束时间！");
			issubmit = false;
		}
	}

	if(payId!=''){
		array_list.push("payId="+payId);
	}
	if(startTime!=''){
		array_list.push("startTime="+startTime);
	}
	if(endTime!=''){
		array_list.push("endTime="+endTime);
	}
	if(orderId!=''){
		array_list.push("orderId="+orderId);
	}
	if(smallAmount!=''){
		array_list.push("smallAmount="+smallAmount);
	}
	if(bigAmount!=''){
		array_list.push("bigAmount="+bigAmount);
	}
	if(page!=undefined&&page!=""){
		array_list.push("page="+page);
	}
	if(issubmit){
		$.ajax({
			type : "post", 
			url : CONTEXTPATH+"/accounting/getAccountingList",
			data: array_list.join("&")+"&mathran="+Math.random(),
			dataType:"html",
			success : function(msg) { 
				$("#viewList").html(msg);
			}
		});
	}
}
  		function downAccountByPage(){
  			var issubmit = true;
  			var  startTime = $("#startTime").val();
  			var  payId = $("#payId").val();
  			var  endTime = $("#endTime").val();
  			var  orderId = $.trim($("#orderId").val());
  			var  smallAmount =$.trim( $("#smallAmount").val());
  			var  bigAmount = $.trim($("#bigAmount").val());
  			var  matchnum = /^[0-9]*$/;
  			if(!matchnum.test(orderId)){
  				alert("订单ID只能是数字！");
  				$("#orderId").val("");
  				$("#orderId").focus();
  				return false;
  			}
  			if(!matchnum.test(payId)){
  				alert("支付号只能是数字！");
  				$("#payId").val("");
  				$("#payId").focus();
  				return false;
  			}

  			var array_list = new Array();

  			if(startTime!=''&&endTime!=''){
  				if(startTime>endTime){
  					alert("开始时间不能大于结束时间！");
  					issubmit = false;
  				}
  			}

  			if(payId!=''){
  				array_list.push("payId="+payId);
  			}
  			if(startTime!=''){
  				array_list.push("startTime="+startTime);
  			}
  			if(endTime!=''){
  				array_list.push("endTime="+endTime);
  			}
  			if(orderId!=''){
  				array_list.push("orderId="+orderId);
  			}
  			if(smallAmount!=''){
  				array_list.push("smallAmount="+smallAmount);
  			}
  			if(bigAmount!=''){
  				array_list.push("bigAmount="+bigAmount);
  			}
  			array_list.push("meath="+Math.random());
  			if(issubmit){
  				$.ajax({
  					type : "post", 
  					url : CONTEXTPATH+"/accounting/getAccountingList",
  					data: array_list.join("&"),
  					dataType:"html",
  					success : function(msg) { 
  						$("#viewList").html(msg);
  					}
  				});
  			}
  			window.location.href=CONTEXTPATH+"/accounting/downAccountByPage?"+array_list.join("&");
  		}
