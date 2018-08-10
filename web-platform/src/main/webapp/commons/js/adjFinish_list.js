	//初步加载此项目这个页面时
	$(document).ready(function(){
			$.ajax({ 
			     async:false,
			     type : "post", 
			     url : "../adjFinish/getAdjFinishListByCondition", 
			     dataType:"html",
			     success : function(purchase) {
			     	$("#cs").html(purchase);
				 },
				 error:function(jqXHR,textStatus,errorThrown){
				  	alert("网络异常,请稍后再试。。。。");
				 }
		     });
		});

var  CONTEXTPATH  = $("#conPath").val();
function addOrder(){
	window.location.href = CONTEXTPATH+"/pchaseOrder/addpurchaseorder";
}




function clickSubmit(page){
//	if($("#id").val().length>18){
//		alert("没有这么长的采购单号啊");
//		return false;
//	}
	
	var fm_data = $('#adjFinish_fm').serialize();
	
	if(page!=undefined){
		fm_data += "&page="+page;
	}
	
	$.ajax({
		 type : "post", 
     	 url : "../adjFinish/getAdjFinishListByCondition", 
     	 data:fm_data+"&random="+Math.random(),
     	 dataType:"html",
         success : function(msg){
			$("#cs").html(msg);
		},
		 error: function(jqXHR, textStatus, errorThrown){
	        alert("查询失败 ，请稍后再试。。");
	     }
	});
	
}



function exportAdjFinishExcel(){
	var skuId = $("#skuId").val();
	var firstDate = $("#firstDate").val();
	var lastDate = $("#lastDate").val();
	var pName = $("#pName").val();
	var sku = $("#sku").val();
	var addwho = $("#addwho").val();
	var adjFinishArray = new Array();
	if(skuId != ""){
		adjFinishArray.push("skuId="+skuId)
	}
	if(firstDate != ""){
		adjFinishArray.push("firstDate="+firstDate)
	}
	if(lastDate != ""){
		adjFinishArray.push("lastDate="+lastDate)
	}
	if(pName != ""){
		adjFinishArray.push("pName="+pName)
	}
	if(sku != ""){
		adjFinishArray.push("sku="+sku)
	}
	if(addwho != ""){
		adjFinishArray.push("addwho="+addwho)
	}
	window.location.href = "../adjFinish/exportAdjFinishListForExcel?"+adjFinishArray.join("&");
	
}



