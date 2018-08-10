var  CONTEXTPATH  = $("#conPath").val();
$(document).ready(function(){
	clickSubmit();
	$("#purchaseId").change(function(){
		var orderId = $("#purchaseId").val();
		var  matchnum = /^[0-9]*$/;
	    if(!matchnum.test(orderId)){
	     		 $("#purchaseId").val("");
	     		 $("#purchaseId").focus();
	     }
	});
	
}); 

function addNotify(){
	var purchaseId = "";
	var index = 0;
	$("input[type='checkbox'][name='purchaseOrderId']").each(function() { 
		if(this.checked){
			if(index > 1){
				alert("不能选择两个采购单");
				return;
			}
			purchaseId = $(this).val();
			index = index+1;
		}
	});
	
	window.location.href = CONTEXTPATH+"/commoditystore/getPurchaseOrder?purchaseId=" + purchaseId;
}
function clickSubmit(page){
	var warehouseCode = $("#warehouseName").find("option:selected").val();
	var statusValue=$("#status").find("option:selected").val(); 
	var businessTypeValue=$("#businessType").find("option:selected").val(); 
	var orderComment=$("#orderComment").val();
	$("#statusValue").val(statusValue);
	$("#businessTypeValue").val(businessTypeValue);
	$("#warehouseCode").val(warehouseCode);
	$("#Comment").val(orderComment);
	var fm_data = $('#notificationoutorder_fm').serialize();

	if(page!=undefined){
		fm_data += "&page="+page;
	}
	$.ajax({
		 type : "post", 
     	 url : CONTEXTPATH+"/selloutstorage/getNotificationOutOrderPageBean", 
     	 data:fm_data+"&random="+Math.random(),
     	 dataType:"html",
         success : function(msg){
			$("#outStorageNotifyOrderList_model").html(msg);
		},
		 error: function(jqXHR, textStatus, errorThrown){
	        alert("查询失败 ，请稍后再试。。");
	     }
	}); 
}

function checkNotiticationOutOrder(notificatioinOutId){
	$.ajax({
		 type : "post", 
    	 url : CONTEXTPATH+"/selloutstorage/checkNotificationOrder", 
    	 data: "notificatioinOutId="+notificatioinOutId+"&random="+Math.random(),
    	 dataType:"text",
    	 success : function(msg){
        	if(msg == "succeed"){
        		alert("出库通知单审核成功");
        	}
		},
		 error: function(jqXHR, textStatus, errorThrown){
	        alert("出库通知单审核失败 ，请稍后再试。。");
	     }
	}); 
	
}

function getNotificationOutOrder(thisObj,page){
	var notificationId = thisObj.value;
	if(notificationId == undefined && notificationId == ""){
		return false;
	}
	$("[name='notifyId']").removeAttr("checked");
	notifyId.checked = true;
	$("#notificationId").val(notificationId);
	var fm_data = $('#notificationoutorder_fm').serialize();
	if(page!=undefined){
		fm_data += "&page="+page;
	}
	
	window.open(CONTEXTPATH+"/selloutstorage/getNotiticationOutItemList?"+fm_data+"&random="+Math.random(), "_blank");
	/*$.ajax({
		 type : "post", 
     	 url : CONTEXTPATH+"/selloutstorage/getNotiticationOutItemList", 
     	 data:fm_data+"&random="+Math.random(),
     	 dataType:"html",
         success : function(msg){
			$("#notificationOutItem_model").html(msg);
		},
		 error: function(jqXHR, textStatus, errorThrown){
			 alert(textStatus);
	        alert("查询失败 ，请稍后再试。。");
	     }
	}); */
	
	
}

function checkNotifyOrder(thisObj){
	window.location.href = CONTEXTPATH+"/selloutstorage/checkNotificationOrder?notificationId=" + notificationId;
}

function exportNotifyOutOrderList(){
	var warehouseName = $("#warehouseName").val();
	var outNotificationNumber = $("#outNotificationNumber").val();
	var status = $("#status").val();
	var orderNumber = $("#orderNumber").val();
	var businessType = $("#businessType").val();
	var orderComment = $("#orderComment").val();
	var thirdOrderId = $("#thirdOrderId").val();
	var firstDate=$("#beginOutTime").val();
	var lastDate=$("#endOutTime").val();
	var outType=$("#outType").val();
	var otherCusType=$("#otherCusType").val();
	window.open(CONTEXTPATH+"/selloutstorage/exportNotifyOutOrderList?firstDate=" + firstDate + "&lastDate="+lastDate
			 + "&warehouseName="+warehouseName + "&outNotificationNumber="+outNotificationNumber + "&status="+status + "&orderNumber="+orderNumber
			 + "&businessType="+businessType + "&orderComment="+orderComment + "&thirdOrderId="+thirdOrderId + "&outType="+outType + "&otherCusType="+otherCusType,"_blank");
}


