var  CONTEXTPATH  = $("#conPath").val();
$(document).ready(function(){
	clickSubmit();
}); 


function clickSubmit(page){
	var fm_data = $("#notificationorder_fm").serialize();
	if(page != null && page != undefined){
		fm_data = fm_data+"&page="+page;
	}
	$.ajax({
		 type : "post", 
    	 url : CONTEXTPATH+"/commoditystore/getNotificationItemUpdatePageBean", 
    	 data:fm_data+"&random="+Math.random(),
    	 dataType:"html",
        success : function(msg){
			$("#modelNotificationItemList").html(msg);
		},
		 error: function(jqXHR, textStatus, errorThrown){
	        alert("查询失败 ，请稍后再试。。");
	     }
	});
}

function checkReceivedQty(skuId, qty, thisObj){
	var purchaseId = $("#businessNumber").val();
	var qtyReceived = thisObj.value;
	var fm_data = "skuId="+skuId+"&qty="+qty+"&qtyReceived="+qtyReceived+"&purchaseId="+purchaseId;
	
	$.ajax({
		type : "post",
		url : CONTEXTPATH+"/commoditystore/checkReceivedQty",
		data : fm_data+"&random="+Math.random(),
		dataType : "text",
		success : function(msg){
			if(msg != "" && msg != undefined && msg != null){
				alert("通知数量超限，最多可通知"+msg);
				$(thisObj).focus();
			}
		}
	});
}


function updateNotificationOrder(){
	var fm_data = $("#notificationorder_fm").serialize();
	$.ajax({
		 type : "post", 
    	 url : CONTEXTPATH+"/commoditystore/updateNotificationItem", 
    	 data:fm_data+"&random="+Math.random(),
    	 dataType:"text",
        success : function(msg){
			if(msg = "success"){
				alert("修改成功");
			}else{
				alert("修改失败");
			}
		},
		 error: function(jqXHR, textStatus, errorThrown){
	        alert("查询失败 ，请稍后再试。。");
	     }
	});
}

function checkNotificationOrder(objButton){
	$(objButton).attr("disabled","disabled");
	var fm_data = $("#notificationorder_fm").serialize();
	$.ajax({
		 type : "post", 
    	 url : CONTEXTPATH+"/commoditystore/checkNotificationOrder", 
    	 data:fm_data+"&random="+Math.random(),
    	 dataType : "text",
        success : function(msg){
			if(msg = "success"){
				alert("审核成功");
				window.close();
			}else{
				alert("审核失败");
			}
		},
		 error: function(jqXHR, textStatus, errorThrown){
	        alert("审核失败 ，请稍后再试。。");
	     }
	});
	
}