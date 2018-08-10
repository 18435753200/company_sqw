	//初步加载此项目这个页面时
	$(document).ready(function(){
			$.ajax({ 
			     async:false,
			     type : "post", 
			     url : "../moveFinish/getMoveFinishListByCondition", 
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




function clickSubmit(page){
//	if($("#id").val().length>18){
//		alert("没有这么长的采购单号啊");
//		return false;
//	}
	
	var fm_data = $('#moveFinishPO_fm').serialize();
	
	if(page!=undefined){
		fm_data += "&page="+page;
	}
	
	$.ajax({
		 type : "post", 
     	 url : "../moveFinish/getMoveFinishListByCondition", 
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



function exportMoveFinishExcel(){
	var skuId = $("#skuId").val();
	var firstDate = $("#firstDate").val();
	var lastDate = $("#lastDate").val();
	var pName = $("#pName").val();
	var sku = $("#sku").val();
	var addwho = $("#addwho").val();
	var moveFinishArray = new Array();
	if(skuId != ""){
		moveFinishArray.push("skuId="+skuId)
	}
	if(firstDate != ""){
		moveFinishArray.push("firstDate="+firstDate)
	}
	if(lastDate != ""){
		moveFinishArray.push("lastDate="+lastDate)
	}
	if(pName != ""){
		moveFinishArray.push("pName="+pName)
	}
	if(sku != ""){
		moveFinishArray.push("sku="+sku)
	}
	if(addwho != ""){
		moveFinishArray.push("addwho="+addwho)
	}
	window.location.href = "../moveFinish/exportMoveFinishListForExcel?"+moveFinishArray.join("&");
	
}



