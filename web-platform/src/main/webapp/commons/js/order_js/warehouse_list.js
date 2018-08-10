var  CONTEXTPATH  = $("#conPath").val();
var THISPAGE = $("#pageContext").val();
$(document).ready(function(){
	clickSubmit();
	editShow();
});

//获取库房分页显示数据.
function clickSubmit(page){
	var pagearray = new Array();
	if ( undefined != page && "" != page){
		pagearray.push('page='+page);
	}else{
		pagearray.push('page='+1);
	}
	$.ajax({
		type : "post", 
		url : CONTEXTPATH+"/warehouse/getWarehouseModel",
		data:pagearray.join('&')+"&mathran="+Math.random(),
		dataType:"html",
		success : function(msg) { 
			$(".c3").html(msg);
		},
		error:function(){
			alert("服务异常!");
		}
	});	
}

function closebox(){
	$("#add-CK").css('display','none');
}

function fuzhi(){
	var dealerId = $("#dealerId").find("option:selected").text();
	$("#dealerName").val(dealerId);	
}